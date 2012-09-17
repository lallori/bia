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

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.dao.annotation.AnnotationDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermessage.UserMessageDAO;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
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
 *
 */
@Service
@Transactional(readOnly=true)
public class CommunityServiceImpl implements CommunityService {
	@Autowired
	private AnnotationDAO annotationDAO;
	@Autowired
	private ForumDAO forumDAO;   
	@Autowired
	private ForumPostDAO forumPostDAO;   
	@Autowired
	private ForumTopicDAO forumTopicDAO;   
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;   
	@Autowired
	private UserMessageDAO userMessageDAO;

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
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Forum addNewForum(Forum forum, Forum parentForum) throws ApplicationThrowable {
		try {
			forum.setForumId(null);

			parentForum = getForumDAO().find(parentForum.getForumId());
			
			forum.setForumParent(parentForum);
			forum.setLogicalDelete(Boolean.TRUE);

			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			
			getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
			
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
		try {
			forumPost.setPostId(null);
			forumPost.setLogicalDelete(Boolean.TRUE);
			Forum forum = getForumDAO().find(forumPost.getForum().getForumId());
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			forumPost.setForum(forum);
			
			if (forumPost.getTopic().getTopicId() == 0) {
				ForumTopic forumTopic = new ForumTopic(null);
				forumTopic.setForum(forum);
				forumTopic.setDateCreated(new Date());
				forumTopic.setLastUpdate(forumTopic.getDateCreated());
				forumTopic.setIpAddress(forumPost.getIpAddress());
				
				forumTopic.setUser(user);
				forumTopic.setSubject(forumPost.getSubject());
				forumTopic.setTotalReplies(new Integer(0));
				forumTopic.setTotalViews(new Integer(0));
				forumTopic.setLastPost(null);
				forumTopic.setFirstPost(null);
				forumTopic.setLogicalDelete(Boolean.FALSE);
				
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
			} else {
				forumPost.setTopic(getForumTopicDAO().find(forumPost.getTopic().getTopicId()));
				//To set the parent post Id
				ForumPost parentPost = getForumPostDAO().findFirstPostByTopicId(forumPost.getTopic().getTopicId());
				forumPost.setParentPost(getForumPostDAO().find(parentPost.getPostId()));
			}
			forumPost.setDateCreated(new Date());
			forumPost.setLogicalDelete(Boolean.FALSE);
			forumPost.setLastUpdate(new Date());
			forumPost.setUser(getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())));
			getForumPostDAO().persist(forumPost);

			getForumDAO().recursiveIncreasePostsNumber(forum);

			recursiveSetLastPost(forum, forumPost);
//			forum.setLastPost(forumPost);
//			getForumDAO().merge(forum);
			
			forumPost.getTopic().setLastPost(forumPost);
			forumPost.getTopic().setTotalReplies(forumPost.getTopic().getTotalReplies() +1);
			getForumTopicDAO().merge(forumPost.getTopic());

			// Update number of post 
			user.setForumNumberOfPost(user.getForumNumberOfPost()+1);
			getUserDAO().merge(user);

			getUserHistoryDAO().persist(new UserHistory(user, "Create new post", Action.CREATE, Category.FORUM_POST, forumPost));
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	

	/**
	 * {@inheritDoc}
	 */
	private void recursiveSetLastPost(Forum forum, ForumPost forumPost) throws ApplicationThrowable {
		if(forum.getType().equals(Type.CATEGORY)){
			return;
		}
		
		forum.setLastPost(forumPost);
		getForumDAO().merge(forum);
		
		recursiveSetLastPost(forum.getForumParent(), forumPost);
	}

	/**
	 * {@inheritDoc}
	 */
	private void recursiveSetLastPost(Forum forum) throws ApplicationThrowable {
		if(forum.getType().equals(Type.CATEGORY)){
			return;
		}

		ForumPost lastPost = getForumPostDAO().findLastPostFromForum(forum);
		forum.setLastPost(lastPost);
		getForumDAO().merge(forum);

		recursiveSetLastPost(forum.getForumParent(), lastPost);
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMessage createNewMessage(UserMessage userMessage) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
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

			ForumPost lastPost = getForumPostDAO().findLastPostFromForumTopic(forumTopic);
			forumTopic.setLastPost(lastPost);
			forumTopic.setTotalReplies(forumTopic.getTotalReplies()-1);
			getForumTopicDAO().merge(forumTopic);
			
			recursiveSetLastPost(forum);
		}catch(Throwable th){
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
	public ForumPost editPost(ForumPost forumPost) throws ApplicationThrowable {
		try {
			ForumPost forumPostToUpdate = getForumPostDAO().find(forumPost.getPostId());

			Forum forum = getForumDAO().find(forumPost.getForum().getForumId());

			forumPostToUpdate.setForum(forum);
			forumPostToUpdate.setDateCreated(new Date());
			forumPostToUpdate.setLastUpdate(new Date());
			forumPostToUpdate.setIpAddress(forumPost.getIpAddress());
			forumPostToUpdate.setSubject(forumPost.getSubject());
			forumPostToUpdate.setText(forumPost.getText());
			forumPostToUpdate.setUser(getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())));
			if (forumPost.getParentPost() != null) {
				forumPostToUpdate.setParentPost(getForumPostDAO().find(forumPost.getParentPost().getPostId()));
			}

			getForumPostDAO().merge(forumPostToUpdate);

			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			
			getUserHistoryDAO().persist(new UserHistory(user, "Edit post", Action.MODIFY, Category.FORUM_POST, forumPost));
			
			return forumPost;
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
			ForumPost forumPost = getForumPostDAO().findFirstPostByTopicId(topicId);
			
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
			
			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			
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
	public Forum getForum(Integer id) throws ApplicationThrowable {
		try {
			return getForumDAO().find(id);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the forumDAO
	 */
	public ForumDAO getForumDAO() {
		return forumDAO;
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
	 * @return the forumPostDAO
	 */
	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumPostsFromTopic(ForumTopic forumTopic, PaginationFilter paginationFilterPost) throws ApplicationThrowable {
		try {
			return getForumPostDAO().findPostsFromTopic(forumTopic, paginationFilterPost);
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
	public HashMap<Integer, List<Forum>> getForumsGroupByCategory(List<Integer> categoriesIds) throws ApplicationThrowable {
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
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.putAll(getForumDAO().getTotalTopicsAndPosts());
			hashMap.put("newestMember", getUserDAO().getNewestMember().getAccount());
			hashMap.put("totalMembers", getUserDAO().countMembersForum());
			return hashMap;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 * @param forumTopic
	 * @return
	 * @throws ApplicationThrowable
	 */
	@Override
	public ForumTopic getForumTopic(ForumTopic forumTopic) throws ApplicationThrowable {
		try {
			return getForumTopicDAO().findForumTopic(forumTopic);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the forumTopicDAO
	 */
	public ForumTopicDAO getForumTopicDAO() {
		return forumTopicDAO;
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
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @return the userMessageDAO
	 */
	public UserMessageDAO getUserMessageDAO() {
		return userMessageDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean ifPostIsParent(Integer postId) throws ApplicationThrowable {
		try{
			return getForumPostDAO().findIfPostIsParent(postId);
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
			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

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
	public Page searchMessages(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getUserMessageDAO().searchMYSQL(userMessageSearch, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param forumDAO the forumDAO to set
	 */
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	/**
	 * @param forumPostDAO the forumPostDAO to set
	 */
	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	/**
	 * @param forumTopicDAO the forumTopicDAO to set
	 */
	public void setForumTopicDAO(ForumTopicDAO forumTopicDAO) {
		this.forumTopicDAO = forumTopicDAO;
	}

	/**
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}
}
