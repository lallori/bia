/*
 * MailServiceImpl.java
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
package org.medici.docsources.service.mail;

import java.net.URLEncoder;
import java.util.Date;

import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.docsources.dao.activationuser.ActivationUserDAO;
import org.medici.docsources.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.docsources.domain.ActivationUser;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for MailService. 
 * It's use two DAO objects, one java mail sender, one messagesource, and a common
 * mailFrom definition, to retrieve request, compose message (with retrieving
 * text as application message), and send mail.
 *   
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Service
@Transactional(readOnly=true)
public class MailServiceImpl implements MailService {
	@Autowired
	private ActivationUserDAO activationUserDAO;
	@Autowired
	private JavaMailSender javaMailSender; 
	@Autowired
	private PasswordChangeRequestDAO passwordChangeRequestDAO;
	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;
	@Autowired
	private String mailFrom; 

	/**
	 * @return the activationUserDAO
	 */
	public ActivationUserDAO getActivationUserDAO() {
		return activationUserDAO;
	}

	/**
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * @return the mailFrom
	 */
	private String getMailFrom() {
		return mailFrom;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @return the passwordChangeRequestDAO
	 */
	public PasswordChangeRequestDAO getPasswordChangeRequestDAO() {
		return passwordChangeRequestDAO;
	}

	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendActivationMail(ActivationUser activationUser, User user) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(getMailFrom());
			message.setTo(user.getMail());
			message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.activationUser.subject"));
			message.setText(ApplicationPropertyManager.getApplicationProperty("mail.activationUser.text", 
							new String[]{user.getFirstName(), 
										 user.getAccount(), 
										 URLEncoder.encode(activationUser.getUuid().toString(),"UTF-8"), 
										 ApplicationPropertyManager.getApplicationProperty("website.protocol"),
										 ApplicationPropertyManager.getApplicationProperty("website.domain")}, "{", "}"));
			getJavaMailSender().send(message);
			
			activationUser.setMailSended(Boolean.TRUE);
			activationUser.setMailSendedDate(new Date());
			getActivationUserDAO().merge(activationUser);
			return Boolean.TRUE;
		} catch (Throwable th) {
			th.printStackTrace();
			return Boolean.FALSE;
		}
	}

	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendUserPasswordResetMail(PasswordChangeRequest passwordChangeRequest, User user) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(getMailFrom());
			message.setTo(user.getMail());
			message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.resetUserPassword.subject"));
			message.setText(ApplicationPropertyManager.getApplicationProperty("mail.resetUserPassword.text", 
							new String[]{user.getFirstName(), 
										 user.getAccount(), 
										 URLEncoder.encode(passwordChangeRequest.getUuid().toString(),"UTF-8"), 
										 ApplicationPropertyManager.getApplicationProperty("website.protocol"),
										 ApplicationPropertyManager.getApplicationProperty("website.domain")}, "{", "}"));
			getJavaMailSender().send(message);

			passwordChangeRequest.setMailSended(Boolean.TRUE);
			passwordChangeRequest.setMailSendedDate(new Date());
			getPasswordChangeRequestDAO().merge(passwordChangeRequest);
			return Boolean.TRUE;
		} catch (Throwable th) {
			th.printStackTrace();
			return Boolean.FALSE;
		}
	}

	/**
	 * @param activationUserDAO the activationUserDAO to set
	 */
	public void setActivationUserDAO(ActivationUserDAO activationUserDAO) {
		this.activationUserDAO = activationUserDAO;
	}

	/**
	 * @param javaMailSender
	 *            the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @param mailFrom the mailFrom to set
	 */
	@SuppressWarnings("unused")
	private void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param passwordChangeRequestDAO the passwordChangeRequestDAO to set
	 */
	public void setPasswordChangeRequestDAO(PasswordChangeRequestDAO passwordChangeRequestDAO) {
		this.passwordChangeRequestDAO = passwordChangeRequestDAO;
	}
}
