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
package org.medici.docsources.service.community;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.UserMessageSearch;
import org.medici.docsources.dao.forum.ForumDAO;
import org.medici.docsources.dao.forumpost.ForumPostDAO;
import org.medici.docsources.dao.userhistory.UserHistoryDAO;
import org.medici.docsources.dao.userinformation.UserInformationDAO;
import org.medici.docsources.dao.usermessage.UserMessageDAO;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.Forum.Type;
import org.medici.docsources.domain.UserHistory.Action;
import org.medici.docsources.domain.UserHistory.Category;
import org.medici.docsources.domain.ForumPost;
import org.medici.docsources.domain.UserComment;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserInformation;
import org.medici.docsources.domain.UserMessage;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private ForumDAO forumDAO;   
	@Autowired
	private ForumPostDAO forumPostDAO;   
	@Autowired
	private UserHistoryDAO userHistoryDAO;   
	@Autowired
	private UserInformationDAO UserInformationDAO;
	@Autowired
	private UserMessageDAO userMessageDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost addNewPost(ForumPost forumPost) throws ApplicationThrowable {
		try {
			forumPost.setId(null);

			getForumPostDAO().persist(forumPost);

			getUserHistoryDAO().persist(new UserHistory("Create new post", Action.CREATE, Category.FORUM_POST, forumPost));
			
			return forumPost;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum addNewForum(Forum forum, Forum parentForum) throws ApplicationThrowable {
		try {
			forum.setId(null);

			parentForum = getForumDAO().find(parentForum.getId());
			
			forum.setForumParent(parentForum);

			getUserHistoryDAO().persist(new UserHistory("Create new forum", Action.CREATE, Category.FORUM, forum));
			
			return forum;
		} catch (Throwable th) {
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserComment createNewMessage(UserMessage userMessage) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
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
	@Override
	public ForumPost editPost(ForumPost forumPost) throws ApplicationThrowable {
		try {
			forumPost.setId(null);

			getForumPostDAO().persist(forumPost);

			getUserHistoryDAO().persist(new UserHistory("Edit post", Action.MODIFY, Category.FORUM_POST, forumPost));
			
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
			
			getUserHistoryDAO().persist(new UserHistory("Show post", Action.VIEW, Category.FORUM_POST, forumPost));

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
	public ArrayList<UserComment> getCommentsOnDocument(Integer entryId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public ArrayList<UserComment> getCommentsOnPerson(Integer personId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public ArrayList<UserComment> getCommentsOnPlace(Integer placeAllId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public ArrayList<UserComment> getCommentsOnVolume(Integer summaryId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
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
	 * @return the forumPostDAO
	 */
	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
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
			hashMap.put("newestMember", getUserInformationDAO().getNewestMember().getAccount());
			hashMap.put("totalMembers", getUserInformationDAO().countMembersForum());
			return hashMap;
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
	 * @return the userInformationDAO
	 */
	public UserInformationDAO getUserInformationDAO() {
		return UserInformationDAO;
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserInformation joinUserOnForum() throws ApplicationThrowable {
		try {
			UserInformation userInformation = getUserInformationDAO().find(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			
			if (userInformation != null) {
				userInformation.setForumJoinedDate(new Date());
			}
			
			getUserInformationDAO().merge(userInformation);
			return userInformation;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserComment replyComment(UserComment userComment, Integer parentUserCommentId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserComment replyMessage(UserMessage userMessage, Integer parentUserMessageId) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
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
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userInformationDAO the userInformationDAO to set
	 */
	public void setUserInformationDAO(UserInformationDAO userInformationDAO) {
		UserInformationDAO = userInformationDAO;
	}

	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}
}
