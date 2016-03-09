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
package org.medici.bia.service.mail;

import java.net.URLEncoder;
import java.util.Date;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.dao.activationuser.ActivationUserDAO;
import org.medici.bia.dao.approvationuser.ApprovationUserDAO;
import org.medici.bia.dao.coursetopicoption.CourseTopicOptionDAO;
import org.medici.bia.dao.emailmessageuser.EmailMessageUserDAO;
import org.medici.bia.dao.forumpostnotified.ForumPostNotifiedDAO;
import org.medici.bia.dao.lockeduser.LockedUserDAO;
import org.medici.bia.dao.passwordchangerequest.PasswordChangeRequestDAO;
import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.ApprovationUser;
import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.EmailMessageUser;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.LockedUser;
import org.medici.bia.domain.PasswordChangeRequest;
import org.medici.bia.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Service
@Transactional(readOnly=true)
public class MailServiceImpl implements MailService {
	@Autowired
	private ActivationUserDAO activationUserDAO;
	@Autowired
	private ApprovationUserDAO approvationUserDAO;
	@Autowired
	private EmailMessageUserDAO emailMessageUserDAO;
	@Autowired
	private ForumPostNotifiedDAO forumPostNotifiedDAO;
	@Autowired
	private CourseTopicOptionDAO courseTopicOptionDAO;
	@Autowired
	private JavaMailSender javaMailSender; 
	@Autowired
	private LockedUserDAO lockedUserDAO;
	@Autowired
	private String mailFrom;
	@Autowired
	@Qualifier("messageSource")
	private MessageSource messageSource;
	@Autowired
	private PasswordChangeRequestDAO passwordChangeRequestDAO; 

	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * @return the activationUserDAO
	 */
	public ActivationUserDAO getActivationUserDAO() {
		return activationUserDAO;
	}
	
	/**
	 * @param activationUserDAO the activationUserDAO to set
	 */
	public void setActivationUserDAO(ActivationUserDAO activationUserDAO) {
		this.activationUserDAO = activationUserDAO;
	}

	/**
	 * @return the approvationUserDAO
	 */
	public ApprovationUserDAO getApprovationUserDAO() {
		return approvationUserDAO;
	}
	
	/**
	 * @param approvationUserDAO the approvationUserDAO to set
	 */
	public void setApprovationUserDAO(ApprovationUserDAO approvationUserDAO) {
		this.approvationUserDAO = approvationUserDAO;
	}

	/**
	 * @return the emailMessageUserDAO
	 */
	public EmailMessageUserDAO getEmailMessageUserDAO() {
		return emailMessageUserDAO;
	}
	
	/**
	 * @param emailMessageUserDAO the emailMessageUserDAO to set
	 */
	public void setEmailMessageUserDAO(EmailMessageUserDAO emailMessageUserDAO) {
		this.emailMessageUserDAO = emailMessageUserDAO;
	}

	/**
	 * @return the forumPostNotifiedDAO
	 */
	public ForumPostNotifiedDAO getForumPostNotifiedDAO() {
		return forumPostNotifiedDAO;
	}
	
	/**
	 * @param forumPostNotifiedDAO the forumPostNotifiedDAO to set
	 */
	public void setForumPostNotifiedDAO(ForumPostNotifiedDAO forumPostNotifiedDAO) {
		this.forumPostNotifiedDAO = forumPostNotifiedDAO;
	}
	
	/**
	 * @return the courseTopicOptionDAO
	 */
	public CourseTopicOptionDAO getCourseTopicOptionDAO() {
		return courseTopicOptionDAO;
	}

	/**
	 * @param courseTopicOptionDAO the courseTopicOptionDAO to set
	 */
	public void setCourseTopicOptionDAO(CourseTopicOptionDAO courseTopicOptionDAO) {
		this.courseTopicOptionDAO = courseTopicOptionDAO;
	}

	/**
	 * @return the javaMailSender
	 */
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}
	
	/**
	 * @param javaMailSender
	 *            the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @return the lockedUserDAO
	 */
	public LockedUserDAO getLockedUserDAO() {
		return lockedUserDAO;
	}
	
	/**
	 * @param lockedUserDAO the lockedUserDAO to set
	 */
	public void setLockedUserDAO(LockedUserDAO lockedUserDAO) {
		this.lockedUserDAO = lockedUserDAO;
	}

	/**
	 * @return the mailFrom
	 */
	private String getMailFrom() {
		return mailFrom;
	}
	
	/**
	 * @param mailFrom the mailFrom to set
	 */
	@SuppressWarnings("unused")
	private void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}
	
	/**
	 * @param messageSource
	 *            the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @return the passwordChangeRequestDAO
	 */
	public PasswordChangeRequestDAO getPasswordChangeRequestDAO() {
		return passwordChangeRequestDAO;
	}
	
	/**
	 * @param passwordChangeRequestDAO the passwordChangeRequestDAO to set
	 */
	public void setPasswordChangeRequestDAO(PasswordChangeRequestDAO passwordChangeRequestDAO) {
		this.passwordChangeRequestDAO = passwordChangeRequestDAO;
	}

	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendActivationMail(ActivationUser activationUser) {
		try {
			if (!StringUtils.isBlank(activationUser.getUser().getMail())) { 
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(getMailFrom());
				message.setTo(activationUser.getUser().getMail());
				message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.activationUser.subject"));
				message.setText(ApplicationPropertyManager.getApplicationProperty("mail.activationUser.text", 
								new String[]{
									activationUser.getUser().getFirstName(), 
									activationUser.getUser().getAccount(), 
									URLEncoder.encode(activationUser.getUuid().toString(),"UTF-8"), 
									ApplicationPropertyManager.getApplicationProperty("website.protocol"),
									ApplicationPropertyManager.getApplicationProperty("website.domain"),
				 					ApplicationPropertyManager.getApplicationProperty("website.contextPath")}, "{", "}"));
				getJavaMailSender().send(message);
				
				activationUser.setMailSended(Boolean.TRUE);
				activationUser.setMailSendedDate(new Date());
				getActivationUserDAO().merge(activationUser);
			} else {
				logger.error("Mail activation user not sended for user " + activationUser.getUser().getAccount() + ". Check mail field on tblUser for account " + activationUser.getUser().getAccount());
			}
			return Boolean.TRUE;
		} catch (Throwable throwable) {
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendApprovedMail(ApprovationUser approvationUser) {
		try {
			if (!StringUtils.isBlank(approvationUser.getUser().getMail())) { 
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(getMailFrom());
				message.setTo(approvationUser.getUser().getMail());
				message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.approvedUser.subject"));
				message.setText(
					ApplicationPropertyManager.getApplicationProperty("mail.approvedUser.text", 
					new String[] {
						approvationUser.getUser().getFirstName(),
						ApplicationPropertyManager.getApplicationProperty("website.protocol"),
						ApplicationPropertyManager.getApplicationProperty("website.domain"),
						ApplicationPropertyManager.getApplicationProperty("website.contextPath"),
						approvationUser.getUser().getAccount()
					},
					"{",
					"}"));
				getJavaMailSender().send(message);
	
				approvationUser.setMailSended(Boolean.TRUE);
				approvationUser.setMailSendedDate(new Date());
				getApprovationUserDAO().merge(approvationUser);
			} else {
				logger.error("Mail approved not sended for user " + approvationUser.getUser().getAccount() + ". Check mail field on tblUser for account " + approvationUser.getUser().getAccount());
			}
			return Boolean.TRUE;
		} catch (Throwable throwable) {
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendEmailMessageUser(final EmailMessageUser emailMessageUser) {
		try{
			if(!StringUtils.isBlank(emailMessageUser.getUser().getMail())){
//				SimpleMailMessage message = new SimpleMailMessage();
//				message.setFrom(getMailFrom());
//				message.setTo(emailMessageUser.getUser().getMail());
//				message.setSubject(emailMessageUser.getSubject());
//				message.setText(emailMessageUser.getBody());
				
				MimeMessagePreparator preparator = new MimeMessagePreparator() {
					
					@Override
					public void prepare(MimeMessage mimeMessage) throws Exception {
						mimeMessage.setFrom(new InternetAddress(getMailFrom()));
						mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailMessageUser.getUser().getMail()));
						mimeMessage.setSubject(emailMessageUser.getSubject());
						mimeMessage.setText(emailMessageUser.getBody(), "utf-8", "html");
					}
				};
				
				getJavaMailSender().send(preparator);
				emailMessageUser.setMailSended(Boolean.TRUE);
				emailMessageUser.setMailSendedDate(new Date());
				getEmailMessageUserDAO().merge(emailMessageUser);
			}else{
				logger.error("Email message not sended for user " + emailMessageUser.getUser().getAccount() + ". Check mail field on tblUser for account " + emailMessageUser.getUser().getAccount());
			}
			return Boolean.TRUE;
		}catch(Throwable throwable){
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendForumPostReplyNotificationMail(ForumPostNotified forumPostReplied, ForumPost forumPost, User currentUser) {
		try {
			if (!StringUtils.isBlank(currentUser.getMail())) { 
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(getMailFrom());
				message.setTo(currentUser.getMail());
				if (!Forum.SubType.COURSE.equals(forumPost.getForum().getSubType())) {
					// message for a reply post
					message.setSubject(
							ApplicationPropertyManager.getApplicationProperty("mail.forumPostReplyNotification.subject",
							new String[] {
								forumPost.getUser().getFirstName(), 
								forumPost.getUser().getLastName(), 
								forumPost.getParentPost().getSubject()
							},
							"{",
							"}"));
					message.setText(
							ApplicationPropertyManager.getApplicationProperty("mail.forumPostReplyNotification.text", 
							new String[] {
								forumPost.getUser().getFirstName(),
								forumPost.getUser().getLastName(),
								forumPost.getParentPost().getSubject(),
								getForumTopicUrl(forumPost.getTopic(), Forum.SubType.COURSE.equals(forumPost.getForum().getSubType()))
							},
							"{",
							"}"));
				} else {
					// message for a course transcription post or a course question post
					message.setSubject(
							ApplicationPropertyManager.getApplicationProperty("mail.courseTranscriptionNotification.subject",
							new String[] {
								forumPost.getUser().getFirstName(),
								forumPost.getUser().getLastName(),
								forumPost.getTopic().getSubject()
							},
							"{",
							"}"));
					CourseTopicOption courseTopicOption = getCourseTopicOptionDAO().getOption(forumPost.getTopic().getTopicId());
					message.setText(
							ApplicationPropertyManager.getApplicationProperty("mail.courseTranscriptionNotification.text", 
							new String[] {
								forumPost.getUser().getFirstName(),
								forumPost.getUser().getLastName(),
								forumPost.getTopic().getSubject(),
								getCourseTopicUrl(courseTopicOption)
							},
							"{",
							"}"));
				}
				//getJavaMailSender().send(message);
	
			} else {
				//logger.error("Mail for ForumPost reply not sended for user " + currentUser.getAccount() + ". Check mail field on tblUser for account " + currentUser.getAccount());
			}
			
			//forumPostReplied.setMailSended(Boolean.TRUE);
			//forumPostReplied.setMailSendedDate(new Date());
			
			return Boolean.TRUE;
		} catch (Throwable throwable) {
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendMailLockedUser(LockedUser lockedUser) {
		try {
			if (!StringUtils.isBlank(lockedUser.getUser().getMail())) { 
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(getMailFrom());
				message.setTo(lockedUser.getUser().getMail());
				message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.lockedUser.subject"));
				message.setText(
						ApplicationPropertyManager.getApplicationProperty("mail.lockedUser.text", 
						new String[] {
							lockedUser.getUser().getAccount(),
							ApplicationPropertyManager.getApplicationProperty("mail.admin.to"),
						},
						"{",
						"}"));
				getJavaMailSender().send(message);
				SimpleMailMessage messageToAdmin = new SimpleMailMessage();
				messageToAdmin.setFrom(getMailFrom());
				messageToAdmin.setTo(ApplicationPropertyManager.getApplicationProperty("mail.admin.to"));
				messageToAdmin.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.lockedUserToAdmin.subject"));
				messageToAdmin.setText(
						ApplicationPropertyManager.getApplicationProperty("mail.lockedUserToAdmin.text",
						new String[] {
							lockedUser.getUser().getAccount()
						},
						"{",
						"}"));
				getJavaMailSender().send(messageToAdmin);
				lockedUser.setMailSended(Boolean.TRUE);
				lockedUser.setMailSendedDate(new Date());
				getLockedUserDAO().merge(lockedUser);
			} else {
				logger.error("Mail locked not sended for user " + lockedUser.getUser().getAccount() + ". Check mail field on tblUser for account " + lockedUser.getUser().getAccount());
			}
			return Boolean.TRUE;
		} catch (Throwable throwable) {
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendMailUnlockedUser(LockedUser lockedUser) {
		try {
			if (!StringUtils.isBlank(lockedUser.getUser().getMail())) { 
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(getMailFrom());
				message.setTo(lockedUser.getUser().getMail());
				message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.unlockedUser.subject"));
				message.setText(ApplicationPropertyManager.getApplicationProperty("mail.unlockedUser.text"));
				getJavaMailSender().send(message);
				
				lockedUser.setMailUnlockSended(Boolean.TRUE);
				lockedUser.setMailUnlockSendedDate(new Date());
				getLockedUserDAO().merge(lockedUser);
			} else {
				logger.error("Mail locked not sended for user " + lockedUser.getUser().getAccount() + ". Check mail field on tblUser for account " + lockedUser.getUser().getAccount());
			}
			return Boolean.TRUE;
		} catch (Throwable throwable) {
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Boolean sendUserPasswordResetMail(PasswordChangeRequest passwordChangeRequest) {
		try {
			if (!StringUtils.isBlank(passwordChangeRequest.getUser().getMail())) { 
				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(getMailFrom());
				message.setTo(passwordChangeRequest.getUser().getMail());
				message.setSubject(ApplicationPropertyManager.getApplicationProperty("mail.resetUserPassword.subject"));
				message.setText(ApplicationPropertyManager.getApplicationProperty("mail.resetUserPassword.text", 
								new String[]{passwordChangeRequest.getUser().getFirstName(), 
											 passwordChangeRequest.getUser().getAccount(), 
											 URLEncoder.encode(passwordChangeRequest.getUuid().toString(),"UTF-8"), 
											 ApplicationPropertyManager.getApplicationProperty("website.protocol"),
											 ApplicationPropertyManager.getApplicationProperty("website.domain"),
											 ApplicationPropertyManager.getApplicationProperty("website.contextPath")}, "{", "}"));
				getJavaMailSender().send(message);
	
				passwordChangeRequest.setMailSended(Boolean.TRUE);
				passwordChangeRequest.setMailSendedDate(new Date());
				getPasswordChangeRequestDAO().merge(passwordChangeRequest);
			} else {
				logger.error("Mail password reset not sended for user " + passwordChangeRequest.getUser().getAccount() + ". Check mail field on tblUser for account " + passwordChangeRequest.getUser().getAccount());
			}
			return Boolean.TRUE;
		} catch (Throwable throwable) {
			logger.error(throwable);
			return Boolean.FALSE;
		}
	}
	
	/* Privates */
	
	private String getForumTopicUrl(ForumTopic forumTopic, boolean isTeachingResource) {
		return ApplicationPropertyManager.getApplicationProperty("website.protocol") + "://"
				+ ApplicationPropertyManager.getApplicationProperty("website.domain")
				+ ApplicationPropertyManager.getApplicationProperty("website.contextPath")
				+ (isTeachingResource ? "teaching/" : "community/")
				+ "ShowCourseTranscription.do?topicId=" + forumTopic.getTopicId()
				+ "&completeDOM=true";
	}
	
	private String getCourseTopicUrl(CourseTopicOption topicOption) {
		boolean isCourseTranscription = CourseTopicMode.I.equals(topicOption.getMode()) ||
				CourseTopicMode.R.equals(topicOption.getMode());
		return ApplicationPropertyManager.getApplicationProperty("website.protocol") + "://"
				+ ApplicationPropertyManager.getApplicationProperty("website.domain")
				+ ApplicationPropertyManager.getApplicationProperty("website.contextPath")
				+ (isCourseTranscription ? "teaching/ShowCourseTranscription.do?topicId=" : "teaching/ShowTopicForum.do?topicId=") + topicOption.getCourseTopic().getTopicId()
				+ (isCourseTranscription ? "&entryId=" + topicOption.getCourseTopic().getDocument().getEntryId() : "")
				+ "&completeDOM=true";		
	}

}
