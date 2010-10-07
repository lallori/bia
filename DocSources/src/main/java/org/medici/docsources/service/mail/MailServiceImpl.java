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
import java.sql.Timestamp;
import java.util.Locale;
import java.util.UUID;

import org.medici.docsources.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Service
public class MailServiceImpl implements MailService {
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
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * 
	 */
	public Boolean sendUserInformationMail(User user, String remoteAddress) {
		try{
			PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
			passwordChangeRequest.setUuid(UUID.randomUUID().toString());
			passwordChangeRequest.setAccount(user.getAccount());
			passwordChangeRequest.setRequestDate(new Timestamp(System.currentTimeMillis()));
			passwordChangeRequest.setIpAddress(remoteAddress);

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom(getMailFrom());
			message.setTo(user.getMail());
			message.setSubject(getMessageSource().getMessage("mail.userDetails.subject", null, Locale.ENGLISH));
			message.setText(getMessageSource().getMessage("mail.userDetails.text", 
							new String[]{user.getFirstName(), 
							user.getAccount(), 
							user.getPassword(), 
							URLEncoder.encode(passwordChangeRequest.getUuid().toString(),"UTF-8") }
							, Locale.ENGLISH));
			getJavaMailSender().send(message);
			
			getPasswordChangeRequestDAO().persist(passwordChangeRequest);
			return Boolean.TRUE;
		} catch (Throwable th) {
			th.printStackTrace();
			return Boolean.FALSE;
		}
	}

	/**
	 * @param javaMailSender
	 *            the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @param mailFrom the mailFrom to set
	 */
	@SuppressWarnings("unused")
	private void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * @return the mailFrom
	 */
	private String getMailFrom() {
		return mailFrom;
	}

	/**
	 * @param passwordChangeRequestDAO the passwordChangeRequestDAO to set
	 */
	public void setPasswordChangeRequestDAO(PasswordChangeRequestDAO passwordChangeRequestDAO) {
		this.passwordChangeRequestDAO = passwordChangeRequestDAO;
	}

	/**
	 * @return the passwordChangeRequestDAO
	 */
	public PasswordChangeRequestDAO getPasswordChangeRequestDAO() {
		return passwordChangeRequestDAO;
	}
}
