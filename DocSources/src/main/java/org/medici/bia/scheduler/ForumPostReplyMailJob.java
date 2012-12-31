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
 *
 */
@Transactional(readOnly=true)
public class ForumPostReplyMailJob {
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private MailService mailService;
	
	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate=300000)
	public void execute() {
		MDC.put("account", "threadforumpostreply");
		try {
			List<ForumPostNotified> list = getAdminService().findForumPostRepliedNotNotified();

			for (ForumPostNotified forumPostReplied : list) {
				ForumPost forumPost = getCommunityService().getForumPost(forumPostReplied.getPostId());
				
				for (ForumTopicWatch forumTopicWatch : forumPost.getTopic().getTopicWatch()) {
					User currentUser = forumTopicWatch.getUser();

					// we exclude user who write new post
					if (!currentUser.equals(forumPost.getUser())) {
						getMailService().sendForumPostReplyNotificationMail(forumPostReplied, forumPost, currentUser);
					}
				}
			}
		} catch (ApplicationThrowable ath) {
			
		}
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}

	/**
	 * 
	 * @param adminService
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 
	 * @return
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @param mailService the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}


}
