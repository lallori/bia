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
package org.medici.docsources.scheduler;

import java.util.List;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.mail.MailService;
import org.medici.docsources.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * This class implements the scheduler to perform send mail to users who
 * request a reset password.
 * There are two tasks performed :
 * - extracting list of users with forgotten password.
 * - for every user in list, this job will send an mail with a personal link
 *   to application where he can update his password.
 * 
 * The system sends an email to the user which contains a link in it.
 *    
 * The link also contains the ID of the request. The link will be something like 
 * this:
 *  
 *    http://www.medici.org/DocSources/user/ResetUserPassword.do?uuid=7a68ff13-7aed-4d59-82a1-78b0463af9d5 
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class ResetUserPasswordEmailJob {
	@Autowired
	private MailService mailService;
	@Autowired
	private UserService userService;

	/**
	 * 
	 * Scheduled is defined in milliseconds
	 */
	@Scheduled(fixedRate=600000)
	public void execute() {
		try {
			List<PasswordChangeRequest> passwordChangeRequests = getUserService().findPasswordResetRequests();

			for(PasswordChangeRequest currentPasswordChange:passwordChangeRequests) {
				try {
					User user = getUserService().findUser(currentPasswordChange.getAccount());
					getMailService().sendUserPasswordResetMail(currentPasswordChange, user);
				} catch (ApplicationThrowable ath) {
				}
			}
			
		} catch (Throwable th) {
			new ApplicationThrowable(th);
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
