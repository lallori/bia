/*
 * ResetUserPasswordEmailJob.java
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
import org.medici.bia.domain.PasswordChangeRequest;
import org.medici.bia.service.mail.MailService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements the scheduler to perform send mail to users who
 * request a reset password.<br>
 * There are two tasks performed :<br>
 * - extracting list of users with forgotten password.<br>
 * - for every user in list, this job will send an mail with a personal link
 *   to application where he can update his password.<p>
 * 
 * The system sends an email to the user which contains a link in it.<p>
 *    
 * The link also contains the ID of the request. The link will be something like 
 * this:<p>
 *  
 *    http://www.medici.org/DocSources/user/ResetUserPassword.do?uuid=7a68ff13-7aed-4d59-82a1-78b0463af9d5 
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Transactional(readOnly=true)
public class ResetUserPasswordEmailJob {
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;

	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 
	 * Scheduled is defined in milliseconds
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate=300000)
	public void execute() {
		MDC.put("username", "threademail");
		try {
			List<PasswordChangeRequest> passwordChangeRequests = getUserService().findPasswordResetRequests();

			for(PasswordChangeRequest currentPasswordChange:passwordChangeRequests) {
				getMailService().sendUserPasswordResetMail(currentPasswordChange, currentPasswordChange.getUser());
			}
			
		} catch (Throwable th) {
			logger.error(th);
			return;
		}
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

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
}
