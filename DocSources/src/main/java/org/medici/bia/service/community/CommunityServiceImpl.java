/*
 * CommunityServiceImpl.java
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
package org.medici.bia.service.community;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.medici.bia.common.access.ApplicationAccessContainer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.search.AdvancedSearchAbstract;
import org.medici.bia.common.search.AdvancedSearchAbstract.DateType;
import org.medici.bia.common.search.AdvancedSearchDocument;
import org.medici.bia.common.search.AdvancedSearchPeople;
import org.medici.bia.common.search.AdvancedSearchPlace;
import org.medici.bia.common.search.AdvancedSearchVolume;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.dom.DOMHelper;
import org.medici.bia.dao.accesslog.AccessLogDAO;
import org.medici.bia.dao.annotation.AnnotationDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.emailmessageuser.EmailMessageUserDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumpostnotified.ForumPostNotifiedDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.forumtopicwatch.ForumTopicWatchDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.reportedforumpost.ReportedForumPostDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userauthority.UserAuthorityDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermessage.UserMessageDAO;
import org.medici.bia.dao.userrole.UserRoleDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.EmailMessageUser;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.ForumTopicWatch;
import org.medici.bia.domain.ReportedForumPost;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.UserMessage.RecipientStatus;
import org.medici.bia.domain.UserRole;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on community (forums, messages).
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Service
@Transactional(readOnly=true)
public class CommunityServiceImpl implements CommunityService {
	@Autowired
	private ApplicationAccessContainer applicationAccessContainer;
	
	@Autowired
	private AccessLogDAO accessLogDAO;
	
	@Autowired
	private AnnotationDAO annotationDAO;
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private EmailMessageUserDAO emailMessageUserDAO;

	@Autowired
	private ForumDAO forumDAO;

	@Autowired
	private ForumPostDAO forumPostDAO;
	
	@Autowired
	private ForumPostNotifiedDAO forumPostNotifiedDAO;

	@Autowired
	private ForumTopicDAO forumTopicDAO;

	@Autowired
	private ForumTopicWatchDAO forumTopicWatchDAO;

	@Autowired
	private PeopleDAO peopleDAO;

	@Autowired
	private PlaceDAO placeDAO;

	@Autowired
	private ReportedForumPostDAO reportedForumPostDAO;

	@Autowired
	private VolumeDAO volumeDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserAuthorityDAO userAuthorityDAO;

	@Autowired
	private UserHistoryDAO userHistoryDAO;   

	@Autowired
	private UserMessageDAO userMessageDAO;   

	@Autowired
	private UserRoleDAO userRoleDAO;   

	/**
	 * @return the applicationAccessContainer
	 */
	public ApplicationAccessContainer getApplicationAccessContainer() {
		return applicationAccessContainer;
	}
	/**
	 * @param applicationAccessContainer the applicationAccessContainer to set
	 */
	public void setApplicationAccessContainer(
			ApplicationAccessContainer applicationAccessContainer) {
		this.applicationAccessContainer = applicationAccessContainer;
	}
	/**
	 * @return the accessLogDAO
	 */
	public AccessLogDAO getAccessLogDAO() {
		return accessLogDAO;
	}
	/**
	 * @param accessLogDAO the accessLogDAO to set
	 */
	public void setAccessLogDAO(AccessLogDAO accessLogDAO) {
		this.accessLogDAO = accessLogDAO;
	}
	/**
	 * @return the annotationDAO
	 */
	public AnnotationDAO getAnnotationDAO() {
		return annotationDAO;
	}
	/**
	 * @param annotationDAO the annotationDAO to set
	 */
	public void setAnnotationDAO(AnnotationDAO annotationDAO) {
		this.annotationDAO = annotationDAO;
	}
	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}
	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
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
	 * @return the forumDAO
	 */
	public ForumDAO getForumDAO() {
		return forumDAO;
	}
	/**
	 * @param forumDAO the forumDAO to set
	 */
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}
	/**
	 * @return the forumPostDAO
	 */
	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}
	/**
	 * @param forumPostDAO the forumPostDAO to set
	 */
	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
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
	 * @return the forumTopicDAO
	 */
	public ForumTopicDAO getForumTopicDAO() {
		return forumTopicDAO;
	}
	/**
	 * @param forumTopicDAO the forumTopicDAO to set
	 */
	public void setForumTopicDAO(ForumTopicDAO forumTopicDAO) {
		this.forumTopicDAO = forumTopicDAO;
	}
	/**
	 * @return the forumTopicWatchDAO
	 */
	public ForumTopicWatchDAO getForumTopicWatchDAO() {
		return forumTopicWatchDAO;
	}
	/**
	 * @param forumTopicWatchDAO the forumTopicWatchDAO to set
	 */
	public void setForumTopicWatchDAO(ForumTopicWatchDAO forumTopicWatchDAO) {
		this.forumTopicWatchDAO = forumTopicWatchDAO;
	}
	/**
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}
	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}
	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
	}
	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}
	/**
	 * @return the reportedForumPostDAO
	 */
	public ReportedForumPostDAO getReportedForumPostDAO() {
		return reportedForumPostDAO;
	}
	/**
	 * @param reportedForumPostDAO the reportedForumPostDAO to set
	 */
	public void setReportedForumPostDAO(ReportedForumPostDAO reportedForumPostDAO) {
		this.reportedForumPostDAO = reportedForumPostDAO;
	}
	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}
	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * @return the userAuthorityDAO
	 */
	public UserAuthorityDAO getUserAuthorityDAO() {
		return userAuthorityDAO;
	}
	/**
	 * @param userAuthorityDAO the userAuthorityDAO to set
	 */
	public void setUserAuthorityDAO(UserAuthorityDAO userAuthorityDAO) {
		this.userAuthorityDAO = userAuthorityDAO;
	}
	/**
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}
	/**
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}
	/**
	 * @return the userMessageDAO
	 */
	public UserMessageDAO getUserMessageDAO() {
		return userMessageDAO;
	}
	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}
	/**
	 * @return the userRoleDAO
	 */
	public UserRoleDAO getUserRoleDAO() {
		return userRoleDAO;
	}
	/**
	 * @param userRoleDAO the userRoleDAO to set
	 */
	public void setUserRoleDAO(UserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Forum addNewForum(Forum forum, Forum parentForum) throws ApplicationThrowable {
		try {
			forum.setForumId(null);

			parentForum = getForumDAO().find(parentForum.getForumId());
			
			forum.setHierarchyLevel(parentForum.getHierarchyLevel()+1);
			forum.setFullPath(parentForum.getFullPath());
			forum.setForumParent(parentForum);
			forum.setLogicalDelete(Boolean.FALSE);

			User user = getCurrentUser();
			
			getForumDAO().persist(forum);
			getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));

			forum.setFullPath(parentForum.getFullPath() + forum.getForumId() + ".");
			getForumDAO().merge(forum);
			
			getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);

			return forum;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost addNewPost(ForumPost forumPost) throws ApplicationThrowable {
		Date operationDate = new Date();
		try {
			User user = getCurrentUser();
			
			forumPost.setPostId(null);
			// forumPost.setLogicalDelete(Boolean.TRUE);
			Forum forum = getForumDAO().find(forumPost.getForum().getForumId());

			if (forumPost.getTopic().getTopicId() == 0) {
				//Create the first post of a new topic
				ForumTopic forumTopic = new ForumTopic(null);
				forumTopic.setForum(forum);
				forumTopic.setDateCreated(operationDate);
				forumTopic.setLastUpdate(operationDate);
				forumTopic.setIpAddress(forumPost.getIpAddress());
				
				forumTopic.setUser(user);
				forumTopic.setSubject(forumPost.getSubject());
				forumTopic.setTotalReplies(new Integer(0));
				forumTopic.setTotalViews(new Integer(0));
				forumTopic.setLastPost(null);
				forumTopic.setFirstPost(null);
				forumTopic.setLogicalDelete(Boolean.FALSE);
				forumTopic.setLocked(Boolean.FALSE);
				
				//MD: To attach entity from forum to topic
				if(forum.getDocument() != null){
					forumTopic.setDocument(forum.getDocument());
				}else if(forum.getVolume() != null){
					forumTopic.setVolume(forum.getVolume());
				}else if(forum.getPerson() != null){
					forumTopic.setPerson(forum.getPerson());
				}else if(forum.getPlace() != null){
					forumTopic.setPlace(forum.getPlace());
				}
				
				getForumTopicDAO().persist(forumTopic);
				
				forumPost.setTopic(forumTopic);
				
				//Increment the topicsNumber in forum
				getForumDAO().recursiveIncreaseTopicsNumber(forum);

				if (user.getForumTopicSubscription().equals(Boolean.TRUE)) {
					ForumTopicWatch forumTopicWatch = new ForumTopicWatch(forumTopic, user);
					getForumTopicWatchDAO().persist(forumTopicWatch);
				}
			} else {
				ForumTopic forumTopic = getForumTopicDAO().find(forumPost.getTopic().getTopicId());
				forumPost.setTopic(forumTopic);
				//To set the parent post Id
				ForumPost parentPost = getForumPostDAO().getFirstForumTopicPostByCreationDate(forumPost.getTopic().getTopicId());
				if(parentPost != null) {
					forumPost.setParentPost(getForumPostDAO().find(parentPost.getPostId()));
				} else if (forumTopic.getAnnotation() != null) {
					// RR: in this case the post is the first post associated to an annotation
					DOMHelper domHelper = new DOMHelper(forumPost.getText(), true, true);
					String plainText = domHelper.getAllPlainText(true);
					forumTopic.getAnnotation().setText(plainText);
				}
			}
			forumPost.setDateCreated(operationDate);
			forumPost.setLogicalDelete(Boolean.FALSE);
			forumPost.setLastUpdate(operationDate);
			forumPost.setUser(user);
			forumPost.setUpdater(user);
			getForumPostDAO().persist(forumPost);
			
			if (forumPost.getParentPost() != null) {
				ForumPostNotified forumPostNotified = new ForumPostNotified(forumPost.getPostId());
				forumPostNotified.setMailSended(Boolean.FALSE);
				
				getForumPostNotifiedDAO().persist(forumPostNotified);
			}

			getForumDAO().recursiveIncreasePostsNumber(forum);

			recursiveSetLastPost(forum, forumPost);
			
			if (forumPost.getTopic().getFirstPost() == null) {
				forumPost.getTopic().setFirstPost(getForumPostDAO().getFirstForumTopicPostByCreationDate(forumPost.getTopic().getTopicId()));
			}
			
			forumPost.getTopic().setLastPost(forumPost);
			forumPost.getTopic().setLastUpdate(operationDate);
			forumPost.getTopic().setTotalReplies(forumPost.getTopic().getTotalReplies() +1);
			// getForumTopicDAO().merge(forumPost.getTopic());

			// Update number of post 
			user.setForumNumberOfPost(user.getForumNumberOfPost()+1);
			user.setLastActiveForumDate(operationDate);
			user.setLastForumPostDate(operationDate);
			// getUserDAO().merge(user);

			getUserHistoryDAO().persist(new UserHistory(user, "Create new post", Action.CREATE, Category.FORUM_POST, forumPost));
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserMessage changeStatusMessage(UserMessage userMessage, RecipientStatus status) throws ApplicationThrowable {
		try{
			UserMessage userMessageToUpdate = getUserMessageDAO().find(userMessage.getMessageId());
			userMessageToUpdate.setRecipientStatus(status);
			if(status.equals(RecipientStatus.READ) && userMessageToUpdate.getReadedDate() == null){
				userMessageToUpdate.setReadedDate(new Date());
			}
			
			getUserMessageDAO().merge(userMessageToUpdate);
			return userMessageToUpdate;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Long checkNewMessages() throws ApplicationThrowable {
		try {
			return getUserMessageDAO().findNumberOfNewMessages();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewEmailMessage(EmailMessageUser emailMessageUser, List<String> accounts) throws ApplicationThrowable {
		try{
			for(String account : accounts){
				User user = getUserDAO().findUser(account);
				if(user != null){
					emailMessageUser.setUser(user);
					emailMessageUser.setMailSended(false);
					getEmailMessageUserDAO().persist(emailMessageUser);
				}
			}
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createNewEmailMessageUserForAll(EmailMessageUser emailMessageUser) throws ApplicationThrowable {
		try{
			getEmailMessageUserDAO().createNewEmailMessageUserForAll(emailMessageUser);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void createNewEmailMessageUserFromUserRole(List<String> userRole, EmailMessageUser emailMessageUser) throws ApplicationThrowable {
		try{
//			List<UserAuthority> authorities = getUserAuthorityDAO().getAuthorities();
//			List<User> usersToSendMail = new ArrayList<User>();
//			for(UserAuthority currentAuthority : authorities){
//				String role = currentAuthority.getAuthority().getValue();
//				if(userRole.contains(role)){
//					List<User> users = getUserRoleDAO().findUsers(currentAuthority);
//					for(User currentUser : users){
//						if(!usersToSendMail.contains(currentUser)){
//							usersToSendMail.add(currentUser);
//						}
//					}
//				}
//			}
//			for(User currentUser : usersToSendMail){
//				EmailMessageUser newEmail = new EmailMessageUser();
//				newEmail.setSubject(emailMessageUser.getSubject());
//				newEmail.setBody(emailMessageUser.getBody());
//				newEmail.setUser(currentUser);
//				newEmail.setMailSended(false);
//				getEmailMessageUserDAO().persist(newEmail);						
//			}
			getEmailMessageUserDAO().createNewEmailMessageUserFormUserRoles(userRole, emailMessageUser);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMessage createNewMessage(UserMessage userMessage) throws ApplicationThrowable {
		try{
			//MD: Persist two messages for both user that have the message in the inbox (recipient) and outbox(sender)
			User outBoxUser = getUserDAO().findUser(userMessage.getSender());
			User inBoxUser = getUserDAO().findUser(userMessage.getRecipient());
			if(!userMessage.getSender().equals("Staff")){
			UserMessage outBoxMessage = new UserMessage();
			outBoxMessage.setSubject(userMessage.getSubject());
			outBoxMessage.setBody(userMessage.getBody());
			outBoxMessage.setSender(userMessage.getSender());
			outBoxMessage.setRecipient(userMessage.getRecipient());
			outBoxMessage.setSendedDate(userMessage.getSendedDate());
			outBoxMessage.setRecipientStatus(userMessage.getRecipientStatus());
			outBoxMessage.setMessageId(null);
			//If the message is a reply
			if(userMessage.getParentMessage() != null){
				outBoxMessage.setParentMessage(getUserMessageDAO().find(userMessage.getParentMessage().getMessageId()));
			}
			outBoxMessage.setUser(outBoxUser);
			getUserMessageDAO().persist(outBoxMessage);
			}
			UserMessage inBoxMessage = new UserMessage();
			inBoxMessage.setSubject(userMessage.getSubject());
			inBoxMessage.setBody(userMessage.getBody());
			inBoxMessage.setSender(userMessage.getSender());
			inBoxMessage.setRecipient(userMessage.getRecipient());
			inBoxMessage.setSendedDate(userMessage.getSendedDate());
			inBoxMessage.setRecipientStatus(userMessage.getRecipientStatus());
			inBoxMessage.setMessageId(null);
			//If the message is a reply
			if(userMessage.getParentMessage() != null){
				inBoxMessage.setParentMessage(getUserMessageDAO().find(userMessage.getParentMessage().getMessageId()));
			}
			inBoxMessage.setUser(inBoxUser);
			getUserMessageDAO().persist(inBoxMessage);
			
			return userMessage;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteForum(Integer forumId) throws ApplicationThrowable {
		try{
			Forum forum = getForumDAO().find(forumId);
			forum.setLogicalDelete(Boolean.TRUE);
				
			getForumDAO().merge(forum);
			getForumDAO().deleteForumFromParent(forum.getForumId());
			getForumTopicDAO().deleteForumTopicsFromForum(forum.getForumId());
			getForumPostDAO().deleteAllForumPosts(forum.getForumId());
			if(forum.getForumParent() != null){
				//MD: Update the last Post and the number of posts
				Forum forumParent = forum.getForumParent();
//				ForumPost forumPost = getForumPostDAO().findLastPostFromForum(forumParent);
//				forumParent.setLastPost(forumPost);
//				getForumDAO().merge(forumParent);
				recursiveSetLastPost(forumParent);
				
//				forumParent.setPostsNumber(forumParent.getPostsNumber() - forum.getPostsNumber());
				getForumDAO().merge(forumParent);					
			}
			getForumDAO().recursiveDecreasePostsNumber(forum, forum.getPostsNumber());
			getForumDAO().recursiveDecreaseTopicsNumber(forum, forum.getTopicsNumber());
			getForumDAO().recursiveDecreaseSubForumsNumber(forum.getForumParent());
			
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}   
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteForumPost(Integer postId) throws ApplicationThrowable {
		try{
			ForumPost forumPost = getForumPostDAO().find(postId);
			Forum forum = forumPost.getForum();
			ForumTopic forumTopic = forumPost.getTopic(); 
			forumPost.setLogicalDelete(new Boolean(Boolean.TRUE));

			getForumPostDAO().merge(forumPost);

			ForumPost lastPost = getForumPostDAO().getLastForumTopicPostByCreationDate(forumTopic);
			forumTopic.setLastPost(lastPost);
			forumTopic.setTotalReplies(forumTopic.getTotalReplies()-1);
			getForumTopicDAO().merge(forumTopic);
			
			getForumDAO().recursiveDecreasePostsNumber(forum);
//			forum.setPostsNumber(forum.getPostsNumber() - 1);
			getForumDAO().merge(forum);
			
			recursiveSetLastPost(forum);

			User user = getCurrentUser();
			Date now = new Date();
			user.setLastActiveForumDate(now);
			user.setLastForumPostDate(now);
			user.setForumNumberOfPost(user.getForumNumberOfPost() - 1);
			getUserDAO().merge(user);
			
			getUserHistoryDAO().persist(new UserHistory(user, "Delete post", Action.DELETE, Category.FORUM_POST, forumPost));
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteForumTopic(Integer topicId) throws ApplicationThrowable {
		try {
			ForumTopic forumTopic = getForumTopicDAO().find(topicId);
			forumTopic.setLogicalDelete(Boolean.TRUE);
			if (forumTopic.getAnnotation() != null) {
				forumTopic.getAnnotation().setLogicalDelete(Boolean.TRUE);
			}
			
			// getForumTopicDAO().merge(forumTopic);
			getForumPostDAO().deleteAllForumTopicPosts(forumTopic.getTopicId());

			Forum forum = forumTopic.getForum();
			recursiveSetLastPost(forum);
			getForumDAO().recursiveDecreasePostsNumber(forum, forumTopic.getTotalReplies());
			getForumDAO().recursiveDecreaseTopicsNumber(forum);
//			forum.setPostsNumber(forum.getPostsNumber() - forumTopic.getTotalReplies());
			// getForumDAO().merge(forum);

			User user = getCurrentUser();
			user.setLastActiveForumDate(new Date());
			// getUserDAO().merge(user);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteMessage(Integer userMessageId) throws ApplicationThrowable {
		// TODO Auto-generated method stub

	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteMessages(List<Integer> idElementsToRemove) throws ApplicationThrowable {
		try{
			User user = getCurrentUser();
			
			getUserMessageDAO().removeMessages(user, idElementsToRemove);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost editPost(ForumPost postFromView) throws ApplicationThrowable {
		Date operationDate = new Date();
		try {
			User user = getCurrentUser();
			
			ForumPost forumPostToUpdate = getForumPostDAO().find(postFromView.getPostId());

			//Forum forum = getForumDAO().find(postFromView.getForum().getForumId());
			//forumPostToUpdate.setForum(forum);
			
			forumPostToUpdate.setLastUpdate(operationDate);
			forumPostToUpdate.setIpAddress(postFromView.getIpAddress());
			forumPostToUpdate.setSubject(postFromView.getSubject());
			forumPostToUpdate.setText(postFromView.getText());
			forumPostToUpdate.setUpdater(user);
			if (postFromView.getParentPost() != null) {
				forumPostToUpdate.setParentPost(getForumPostDAO().find(postFromView.getParentPost().getPostId()));
			} else {
				// we update the annotation text if needed
				Annotation annotation = forumPostToUpdate.getTopic().getAnnotation();
				if (annotation != null) {
					DOMHelper domHelper = new DOMHelper(forumPostToUpdate.getText(), true, true);
					String newAnnotationText = domHelper.getAllPlainText(true);
					if (!newAnnotationText.equals(annotation.getText())) {
						annotation.setText(newAnnotationText);
						annotation.setLastUpdate(operationDate);
					}
				}
			}

			//getForumPostDAO().merge(forumPostToUpdate);
			// Changing the user last forum
			user.setLastActiveForumDate(operationDate);
			user.setLastForumPostDate(operationDate);
			//getUserDAO().merge(user);

			getUserHistoryDAO().persist(new UserHistory(user, "Edit post", Action.MODIFY, Category.FORUM_POST, forumPostToUpdate));
			
			return forumPostToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void exportAnnotationDiscussion(Annotation annotation, String ipAddress) throws ApplicationThrowable {
		String paleographyForumIdString = ApplicationPropertyManager.getApplicationProperty("forum.identifier.paleography");
		Integer paleographyForumId = Integer.valueOf(paleographyForumIdString);
		if (annotation.getForumTopic().getForum().equals(paleographyForumId)) {
			return;
		}
		
		Forum paleographyForum = getForumDAO().find(paleographyForumId);
		Date now = new Date();
		User user = getCurrentUser();
		
		ForumTopic clonedDiscussion = new ForumTopic();
		Annotation annotationClone = new Annotation();
		
		// export the topic
		try {
			// annotation's reference and posts will be added after
			clonedDiscussion.setDateCreated(now);
			clonedDiscussion.setLastUpdate(now);
			clonedDiscussion.setUser(user);
			clonedDiscussion.setIpAddress(ipAddress);
			clonedDiscussion.setForum(paleographyForum);
			clonedDiscussion.setLocked(Boolean.TRUE);
			clonedDiscussion.setLogicalDelete(Boolean.FALSE);
			clonedDiscussion.setSubject(annotation.getForumTopic().getSubject());
			clonedDiscussion.setTotalReplies(annotation.getForumTopic().getTotalReplies());
			clonedDiscussion.setTotalViews(annotation.getForumTopic().getTotalViews());
			
			getForumTopicDAO().persist(clonedDiscussion);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		// export the annotation
		try {
			annotationClone.setDateCreated(now);
			annotationClone.setLastUpdate(now);
			annotationClone.setUser(user);
			annotationClone.setLogicalDelete(Boolean.FALSE);
			annotationClone.setTranscribed(Boolean.TRUE);
			annotationClone.setVisible(Boolean.TRUE);
			annotationClone.setType(Annotation.Type.GENERAL);
			annotationClone.setForumTopic(clonedDiscussion);
			annotationClone.setTitle(annotation.getTitle());
			annotationClone.setText(annotation.getText());
			annotationClone.setImage(annotation.getImage());
			annotationClone.setHeight(annotation.getHeight());
			annotationClone.setWidth(annotation.getWidth());
			annotationClone.setX(annotation.getX());
			annotationClone.setY(annotation.getY());
			
			getAnnotationDAO().persist(annotationClone);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		// adjust references
		clonedDiscussion.setAnnotation(annotationClone);
		annotation.setExportedTo(annotationClone);
		
		// export topic posts
		try {
			Map<ForumPost, ForumPost> postCorrenspondencesMap = new HashMap<ForumPost, ForumPost>();
			List<ForumPost> topicPosts = new ArrayList<ForumPost>();
			for(ForumPost currentPost : getForumPostDAO().getAllNotDeletedForumTopicPosts(annotation.getForumTopic().getTopicId())) {
				ForumPost clonedPost = new ForumPost();
				clonedPost.setDateCreated(currentPost.getDateCreated());
				clonedPost.setLastUpdate(currentPost.getLastUpdate());
				clonedPost.setLogicalDelete(Boolean.FALSE);
				clonedPost.setSubject(currentPost.getSubject());
				clonedPost.setText(currentPost.getText());
				clonedPost.setForum(paleographyForum);
				clonedPost.setTopic(clonedDiscussion);
				clonedPost.setUser(currentPost.getUser());
				clonedPost.setIpAddress(currentPost.getIpAddress());
				clonedPost.setUpdater(currentPost.getUpdater());
				clonedPost.setReplyNumber(currentPost.getReplyNumber());
				// parent post is temporary set to the original one: correct association is adjusted after
				clonedPost.setParentPost(currentPost.getParentPost());
				
				getForumPostDAO().persist(clonedPost);
				
				topicPosts.add(clonedPost);
				postCorrenspondencesMap.put(currentPost, clonedPost);
			}
			
			// set topic posts
			clonedDiscussion.setFirstPost(postCorrenspondencesMap.get(annotation.getForumTopic().getFirstPost()));
			clonedDiscussion.setLastPost(postCorrenspondencesMap.get(annotation.getForumTopic().getLastPost()));
			
			// adjust parent post references
			for(ForumPost post : topicPosts) {
				if (post.getParentPost() != null) {
					post.setParentPost(postCorrenspondencesMap.get(post.getParentPost()));
				}
			}
			
			// increment 'General Questions' forum topics number and posts number
			paleographyForum.setTopicsNumber(paleographyForum.getTopicsNumber() + 1);
			paleographyForum.setPostsNumber(paleographyForum.getPostsNumber() + topicPosts.size());
			
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Annotation findAnnotation(Integer annotationId) throws ApplicationThrowable {
		try {
			return getAnnotationDAO().find(annotationId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost findFirstPostTopic(Integer topicId) throws ApplicationThrowable {
		try {
			ForumPost forumPost = getForumPostDAO().getFirstForumTopicPostByCreationDate(topicId);
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost findPost(Integer id) throws ApplicationThrowable {
		try {
			ForumPost forumPost = getForumPostDAO().find(id);
			
			User user = getCurrentUser();
			
			getUserHistoryDAO().persist(new UserHistory(user, "Show post", Action.VIEW, Category.FORUM_POST, forumPost));

			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Forum findForum(Integer id) throws ApplicationThrowable {
		try {
			return getForumDAO().find(id);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(String account) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser(account);

			if (user != null) {
				return user;
			} else {
				throw new ApplicationThrowable(ApplicationError.USER_NOT_FOUND_ERROR);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAuthority findUserMaximumAuthority(String accountId) throws ApplicationThrowable {
		try {
			UserAuthority authority = getUserAuthorityDAO().getMaximumAuthority(accountId);
			if (authority != null)
				return authority;
			else
				throw new ApplicationThrowable(ApplicationError.USER_NOT_FOUND_ERROR);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, UserAuthority> findUsersMaximumAuthority(Set<String> accountsId) throws ApplicationThrowable {
		try {
			if (accountsId == null || accountsId.size() == 0) {
				return new HashMap<String, UserAuthority>();
			}
			return getUserAuthorityDAO().getMaximumAuthorities(accountsId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserMessage findUserMessage(Integer messageId) throws ApplicationThrowable {
		try{
			return getUserMessageDAO().find(messageId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsersToBeNotified(Integer postId) throws ApplicationThrowable {
		try {
			ForumPost forumPost = getForumPostDAO().find(postId);

			if (forumPost.getTopic() == null) {
				return new ArrayList<User>(0);
			}

			return getForumTopicWatchDAO().findUsersSubscribedOnForumTopic(forumPost.getTopic());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserAuthority> getAuthorities() throws ApplicationThrowable {
		try {
			return getUserAuthorityDAO().getAuthorities();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum getCategory(Forum category) throws ApplicationThrowable {
		try {
			return getForumDAO().getCategory(category);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Long> getDatabaseStatistics(Date fromDate) throws ApplicationThrowable {
		Map<String, Long> retValue = new HashMap<String, Long>(0);
		
		try {
			List<DateType> datesLastUpdateTypes = new ArrayList<AdvancedSearchAbstract.DateType>(0);
			datesLastUpdateTypes.add(DateType.Between);
			
			List<Date> fromDatesLastUpdate = new ArrayList<Date>();
			fromDatesLastUpdate.add(fromDate);
			List<Date> toDatesLastUpdate = new ArrayList<Date>();
			toDatesLastUpdate.add(Calendar.getInstance().getTime());

			AdvancedSearchDocument advancedSearchDocument = new AdvancedSearchDocument();
			advancedSearchDocument.setDatesLastUpdateTypes(datesLastUpdateTypes);
			advancedSearchDocument.setDatesLastUpdate(fromDatesLastUpdate);
			advancedSearchDocument.setDatesLastUpdateBetween(toDatesLastUpdate);
				
			AdvancedSearchPeople advancedSearchPeople = new AdvancedSearchPeople();
			advancedSearchPeople.setDatesLastUpdateTypes(datesLastUpdateTypes);
			advancedSearchPeople.setDatesLastUpdate(fromDatesLastUpdate);
			advancedSearchPeople.setDatesLastUpdateBetween(toDatesLastUpdate);
			
			AdvancedSearchPlace advancedSearchPlace = new AdvancedSearchPlace();
			advancedSearchPlace.setDatesLastUpdateTypes(datesLastUpdateTypes);
			advancedSearchPlace.setDatesLastUpdate(fromDatesLastUpdate);
			advancedSearchPlace.setDatesLastUpdateBetween(toDatesLastUpdate);
			
			AdvancedSearchVolume advancedSearchVolume = new AdvancedSearchVolume();
			advancedSearchVolume.setDatesLastUpdateTypes(datesLastUpdateTypes);
			advancedSearchVolume.setDatesLastUpdate(fromDatesLastUpdate);
			advancedSearchVolume.setDatesLastUpdateBetween(toDatesLastUpdate);
			
			retValue.put("DOCUMENT", getDocumentDAO().countSearchMYSQL(advancedSearchDocument));
			retValue.put("PEOPLE", getPeopleDAO().countSearchMYSQL(advancedSearchPeople));
			retValue.put("PLACE", getPlaceDAO().countSearchMYSQL(advancedSearchPlace));
			retValue.put("VOLUME", getVolumeDAO().countSearchMYSQL(advancedSearchVolume));
		}catch(Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}

		return retValue;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Forum getFirstCategory() throws ApplicationThrowable {
		try {
			return getForumDAO().getFirstCategory();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum getForumForView(Integer id) throws ApplicationThrowable {
		try{
			Forum forum = getForumDAO().find(id);
			if(forum.getTotalViews() == null){
				forum.setTotalViews(1);
			}else{
				forum.setTotalViews(forum.getTotalViews() + 1);
			}
			return forum;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumMembers(String letter, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserDAO().findForumMembers(letter, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumMembersByText(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getUserDAO().findForumMembersByText(text, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getForumPost(Integer postId) throws ApplicationThrowable {
		try {
			return getForumPostDAO().find(postId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumPostsFromTopic(ForumTopic forumTopic, PaginationFilter paginationFilterPost) throws ApplicationThrowable {
		try {
			return getForumPostDAO().getForumTopicPosts(forumTopic, paginationFilterPost);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Forum> getForumsByType(Type type) throws ApplicationThrowable {
		try {
			return getForumDAO().getForumsByType(type);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, List<Forum>> getForumsGroupByCategory(List<Integer> categoriesIds) throws ApplicationThrowable {
		try {
			return getForumDAO().findForumsGroupByCategory(categoriesIds);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public HashMap<String, Object> getForumsStatistics() throws ApplicationThrowable {
		try {
			HashMap<String, Object> hashMap = new HashMap<String, Object>(0);
			hashMap.putAll(getForumDAO().getTotalTopicsAndPosts());
			hashMap.put("newestMember", getUserDAO().getNewestMember().getAccount());
			hashMap.put("totalMembers", getUserDAO().countMembersForum());
			return hashMap;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, List<?>> getForumStatistics(Integer numberOfElements) throws ApplicationThrowable {
		Map<String, List<?>> forumStatistics = new HashMap<String, List<?>>(0);
		try {
			List<ForumTopic> topForumTopics = getForumTopicDAO().findTopForumTopics(numberOfElements);
			forumStatistics.put("TOP DISCUSSIONS", topForumTopics);
			List<ForumTopic> mostRecentForumTopics = getForumTopicDAO().findMostRecentForumTopics(numberOfElements);
			forumStatistics.put("MOST RECENT DISCUSSIONS", mostRecentForumTopics);
		} catch (Throwable th) {
			if (forumStatistics.get("TOP DISCUSSIONS") == null) {
				forumStatistics.put("TOP DISCUSSIONS", new ArrayList<ForumTopic>(0));
			}
			forumStatistics.put("MOST RECENT DISCUSSIONS", new ArrayList<ForumTopic>(0));
		}

		return forumStatistics;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getForumTopic(ForumTopic forumTopic) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().getNotDeletedForumTopic(forumTopic.getTopicId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getForumTopicById(Integer forumTopicId) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().getNotDeletedForumTopic(forumTopicId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getForumTopicForView(Integer forumTopicId)throws ApplicationThrowable {
		try{
			ForumTopic topic = getForumTopicDAO().getNotDeletedForumTopic(forumTopicId);
			if(topic.getTotalViews() == null){
				topic.setTotalViews(1);
			}else{
				topic.setTotalViews(topic.getTotalViews() + 1);
			}
			getForumTopicDAO().merge(topic);
			return topic;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumTopics(Forum forum, PaginationFilter paginationFilterTopics) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().findForumTopics(forum, paginationFilterTopics);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumTopicsByParentForum(Forum forum, PaginationFilter paginationFilterTopics) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().getForumTopicsByParentForum(forum, paginationFilterTopics);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getForumTopicByAnnotation(Integer annotationId) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().getForumTopicByAnnotation(annotationId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Object> getForumWhoIsOnline() throws ApplicationThrowable {
		try {
			HashMap<String, Object> hashMap = new HashMap<String, Object>(0);
			hashMap.put("onlineUsers", applicationAccessContainer.getCommunityOnlineUsers());
			// hashMap.put("onlineUsers", getUserDAO().whoIsOnlineForum());
			hashMap.put("guestUsers", applicationAccessContainer.countOnlineGuests());
			// hashMap.put("guestUsers", getAccessLogDAO().countGuestsForum());
			return hashMap;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum getMostActiveForumByUser(User user) throws ApplicationThrowable {
		try{
			return getForumPostDAO().getMostActiveForumByUser(user);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getMostActiveTopicByUser(User user) throws ApplicationThrowable {
		try{
			return getForumPostDAO().getMostActiveTopicByUser(user);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Forum> getSubCategories(Forum forum) throws ApplicationThrowable {
		try {
			return getForumDAO().findSubCategories(forum);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Forum> getSubForums(Integer forumParentId) throws ApplicationThrowable {
		try {
			return getForumDAO().findSubForums(forumParentId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getSubForums(Integer forumParentId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getForumDAO().findSubForums(forumParentId, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getSubForumsNumberWithTopics(Integer forumId) throws ApplicationThrowable {
		try{
			return getForumDAO().getSubForumsNumberWithTopics(forumId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserRole> getUserRoles(String account) throws ApplicationThrowable {
		try{
			return getUserRoleDAO().findUserRoles(account);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean ifPostIsParent(Integer postId) throws ApplicationThrowable {
		try{
			return getForumPostDAO().isParentPost(postId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean ifTopicSubscribed(Integer forumTopicId) throws ApplicationThrowable {
		try{
			ForumTopic forumTopic = getForumTopicDAO().getNotDeletedForumTopic(forumTopicId);
			
			if (forumTopic != null) {
				//This control is for anonymous user that look a topic 
				if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String){
					return false;
				}
				User user = getCurrentUser();
				if(user != null && getForumTopicWatchDAO().findByTopicAndUser(user, forumTopic) != null){
					return true;
				}
			}
			return false;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public User joinUserOnForum() throws ApplicationThrowable {
		try {
			User user = getCurrentUser();
			
			if (user != null) {
				user.setForumJoinedDate(new Date());
			}
			
			getUserDAO().merge(user);
			return user;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Annotation markAsNotTranscribed(Integer annotationId) throws ApplicationThrowable {
		Date now = new Date();
		try {
			Annotation annotation = getAnnotationDAO().find(annotationId);
			annotation.setLastUpdate(now);
			annotation.setTranscribed(Boolean.FALSE);
			
			return annotation;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Annotation markAsTranscribed(Integer annotationId, String title) throws ApplicationThrowable {
		Date now = new Date();
		try {
			Annotation annotation = getAnnotationDAO().find(annotationId);
			annotation.setLastUpdate(now);
			annotation.setTitle(title);
			annotation.setTranscribed(Boolean.TRUE);
			
			// update the topic title and lock the topic
			annotation.getForumTopic().setLastUpdate(now);
			annotation.getForumTopic().setSubject(title);
			annotation.getForumTopic().setLocked(Boolean.TRUE);
			
			return annotation;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void openCloseTopic(Integer topicId, Boolean closed) throws ApplicationThrowable {
		try {
			ForumTopic topic = getForumTopicDAO().find(topicId);
			if (!closed.equals(topic.getLocked())) {
				topic.setLastUpdate(new Date());
				topic.setLocked(closed);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	private void recursiveSetLastPost(Forum forum) throws ApplicationThrowable {
		if(forum.getType().equals(Type.CATEGORY)){
			return;
		}

		ForumPost lastPost = getForumPostDAO().getLastForumPostByCreationDate(forum);
		forum.setLastPost(lastPost);
		//last update must be updated to obtain a correct indexing of forum
		forum.setLastUpdate(new Date());
		getForumDAO().merge(forum);

		recursiveSetLastPost(forum.getForumParent(), lastPost);
	}

	/**
	 * {@inheritDoc}
	 */
	private void recursiveSetLastPost(Forum forum, ForumPost forumPost) throws ApplicationThrowable {
		if(forum.getType().equals(Type.CATEGORY)){
			return;
		}
		
		forum.setLastPost(forumPost);
		//last update must be updated to obtain a correct indexing of forum
		forum.setLastUpdate(new Date());
		getForumDAO().merge(forum);
		
		recursiveSetLastPost(forum.getForumParent(), forumPost);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void renameForum(Integer forumId, String title, String description) throws ApplicationThrowable {
		try {
			Date now = new Date();
			Forum forum = getForumDAO().find(forumId);
			if (forum == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Cannot retrieve forum [" + forumId + "]: impossible to complete the operation!");
			}
			forum.setTitle(title);
			forum.setDescription(description);
			forum.setLastUpdate(now);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void renameForumTopic(Integer topicId, String title) throws ApplicationThrowable {
		try {
			Date now = new Date();
			ForumTopic topic = getForumTopicDAO().find(topicId);
			if (topic == null) {
				throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Cannot retrieve topic [" + topicId + "]: impossible to complete the operation!");
			}
			topic.setSubject(title);
			topic.setLastUpdate(now);
			
			if (topic.getAnnotation() != null) {
				topic.getAnnotation().setTitle(title);
				topic.getAnnotation().setLastUpdate(now);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserMessage replyMessage(UserMessage userMessage, Integer parentUserMessageId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public ForumPost replyPost(ForumPost forumPost) throws ApplicationThrowable {
		try {
			forumPost.setPostId(null);
			
			Forum forum = getForumDAO().find(forumPost.getForum().getForumId());
			ForumPost parentPost = getForumPostDAO().find(forumPost.getParentPost().getPostId());
			User user = getCurrentUser();

			forumPost.setForum(forum);
			forumPost.setDateCreated(new Date());
			forumPost.setLastUpdate(new Date());
			forumPost.setParentPost(parentPost);
			forumPost.setUser(user);
			getForumPostDAO().persist(forumPost);
			
			parentPost.setReplyNumber(parentPost.getReplyNumber()+1);
			getForumPostDAO().merge(parentPost);

			getForumDAO().recursiveIncreasePostsNumber(forum);

			// Update number of post 
			user.setForumNumberOfPost(user.getForumNumberOfPost()+1);
			user.setLastActiveForumDate(new Date());
			user.setLastForumPostDate(new Date());
			getUserDAO().merge(user);

			getUserHistoryDAO().persist(new UserHistory(user, "Reply to post", Action.CREATE, Category.FORUM_POST, forumPost));
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReportedForumPost reportForumPost(Integer postId) throws ApplicationThrowable {
		try{
			ForumPost forumPost = getForumPostDAO().find(postId);
			User user = getCurrentUser();
			
			ReportedForumPost reportForumPost = new ReportedForumPost();
			reportForumPost.setId(null);
			reportForumPost.setForumPost(forumPost);
			reportForumPost.setMessageSended(false);
			reportForumPost.setUser(user);
			
			getReportedForumPostDAO().persist(reportForumPost);
			
			return reportForumPost;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Page searchForumPosts(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getForumPostDAO().searchMYSQL(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchForumTopics(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getForumTopicDAO().searchMYSQL(searchContainer, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Page searchMessages(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserMessageDAO().searchMYSQL(userMessageSearch, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public void showHideAnnotation(Integer annotationId, Boolean show) throws ApplicationThrowable {
		Date now = new Date();
		try {
			Annotation annotation = getAnnotationDAO().find(annotationId);
			if (!show.equals(annotation.getVisible())) {
				annotation.setVisible(show);
				annotation.setLastUpdate(now);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean subscribeForumTopic(Integer forumTopicId) throws ApplicationThrowable {
		try {
			ForumTopic forumTopic = getForumTopicDAO().getNotDeletedForumTopic(forumTopicId);
			
			if (forumTopic != null) {
				User user = getCurrentUser();

				ForumTopicWatch forumTopicWatch = new ForumTopicWatch();
				forumTopicWatch.setTopic(forumTopic);
				forumTopicWatch.setUser(user);
				
				getForumTopicWatchDAO().persist(forumTopicWatch);
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean unsubscribeForumTopic(Integer forumTopicId) throws ApplicationThrowable {
		try {
			ForumTopic forumTopic = getForumTopicDAO().getNotDeletedForumTopic(forumTopicId);
			
			if (forumTopic != null) {
				User user = getCurrentUser();
				ForumTopicWatch forumTopicWatch = getForumTopicWatchDAO().findByTopicAndUser(user, forumTopic);
				
				if (forumTopicWatch != null) {
					getForumTopicWatchDAO().remove(forumTopicWatch);
				}
				return Boolean.TRUE;
			} else {
				return Boolean.FALSE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean unsubscribeAllForumTopic() throws ApplicationThrowable {
		try {
			User user = getCurrentUser();

			getForumTopicWatchDAO().removeUserSubscribes(user);
			
			return Boolean.TRUE;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	private User getCurrentUser() {
		return getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
	}
}
