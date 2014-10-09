/*
 * ForumPostReplyMailJob.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.medici.bia.scheduler;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.ForumTopicWatch;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Transactional(readOnly=true)
public class ForumPostReplyMailJob {
	
	private static final Logger log = Logger.getLogger(ForumPostReplyMailJob.class);
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private MailService mailService;
	
	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * 
	 * @param adminService
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}
	
	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate=300000)
	public void execute() {
		MDC.put("account", "threadforumpostreply");
		try {
			log.info("ForumPostReplyMailJob: removing bad elements procedure...");
			int removedElements = getAdminService().removeBadForumPostNotified();
			log.info("ForumPostReplyMailJob: " + removedElements + " elements were removed!");
			
			log.info("ForumPostReplyMailJob: sending mails...");
			List<ForumPostNotified> list = getAdminService().findForumPostRepliedNotNotified();

			for (ForumPostNotified forumPostReplied : list) {
				ForumPost forumPost = getCommunityService().getForumPost(forumPostReplied.getPostId());
				
				for (ForumTopicWatch forumTopicWatch : forumPost.getTopic().getTopicWatch()) {
					User currentUser = forumTopicWatch.getUser();
					log.info("ForumPostReplyMailJob: check if [" + currentUser.getAccount() + "] has to receive a mail...");

					// we exclude user who write new post
					if (!currentUser.equals(forumPost.getUser())) {
						try {
							log.info("ForumPostReplyMailJob: sending mail to [" + currentUser.getAccount() + "] for post [" + forumPost.getPostId() + "]");
							getMailService().sendForumPostReplyNotificationMail(forumPostReplied, forumPost, currentUser);
							log.info("ForumPostReplyMailJob: a mail has been sent to [" + currentUser.getAccount() + "]");
						} catch (Exception e) {
							log.error("ForumPostReplyMailJob: error during the mail delivery to [" + currentUser.getAccount() + "]");
						}
					}
				}
			}
			log.info("ForumPostReplyMailJob: job ended!");
		} catch (ApplicationThrowable ath) {
			log.error("ForumPostReplyMailJob: something went wrong", ath);
		}
	}

}
