/*
 * MailService.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.bia.service.mail;

import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.ApprovationUser;
import org.medici.bia.domain.EmailMessageUser;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.PasswordChangeRequest;
import org.medici.bia.domain.User;

/**
 * This interface is designed to provide a service working on mail objects.
 * It defines two methods which manage send mail for activation and password
 * reset process.
 *  
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public interface MailService {

	/**
	 * This method will send an activation mail to user, and update the state
	 * of entity activationUser.
	 *  
	 * @param activationUser Entity representing the activation
	 * @param user User entity that contains email address
	 */
	Boolean sendActivationMail(ActivationUser activationUser);

	/**
	 * 
	 * @param approvationUser
	 * @return
	 */
	Boolean sendApprovedMail(ApprovationUser approvationUser);
	
	/**
	 * 
	 * @param emailMessageUser
	 * @return
	 */
	Boolean sendEmailMessageUser(EmailMessageUser emailMessageUser);

	/**
	 * 
	 * @param currentUser
	 */
	Boolean sendForumPostReplyNotificationMail(ForumPostNotified forumPostReplied, ForumPost forumPost, User currentUser);

	/**
	 * 
	 * @param user
	 * @return
	 */
	Boolean sendMailLockedUser(User user);
	
	/**
	 * This method will send an mail for password recovery , and update the state
	 * of entity passwordChangeRequest.
	 * 
	 * @param passwordChangeRequest Entity representing the request for password change
	 * @param user User entity that contains email address
	 */
	Boolean sendUserPasswordResetMail(PasswordChangeRequest passwordChangeRequest);
}
