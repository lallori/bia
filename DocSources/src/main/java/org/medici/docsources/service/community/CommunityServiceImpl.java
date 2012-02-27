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

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.UserMessageSearch;
import org.medici.docsources.dao.forumcategory.ForumCategoryDAO;
import org.medici.docsources.dao.forumpost.ForumPostDAO;
import org.medici.docsources.dao.forumsubcategory.ForumSubCategoryDAO;
import org.medici.docsources.dao.usermessage.UserMessageDAO;
import org.medici.docsources.domain.UserComment;
import org.medici.docsources.domain.UserMessage;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Service
@Transactional(readOnly=true)
public class CommunityServiceImpl implements CommunityService {
	@Autowired
	private ForumCategoryDAO forumCategoryDAO;   
	@Autowired
	private ForumPostDAO forumPostDAO;   
	@Autowired
	private ForumSubCategoryDAO forumSubCategoryDAO;   
	@Autowired
	private UserMessageDAO userMessageDAO;   

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Integer checkNewMessages() throws ApplicationThrowable {
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
	public UserComment createNewComment(UserComment userComment) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
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
	public UserComment deleteComment(Integer commentId) throws ApplicationThrowable {
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
	 * @return the forumCategoryDAO
	 */
	public ForumCategoryDAO getForumCategoryDAO() {
		return forumCategoryDAO;
	}

	/**
	 * @return the forumPostDAO
	 */
	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	/**
	 * @return the forumSubCategoryDAO
	 */
	public ForumSubCategoryDAO getForumSubCategoryDAO() {
		return forumSubCategoryDAO;
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
	 * @param forumCategoryDAO the forumCategoryDAO to set
	 */
	public void setForumCategoryDAO(ForumCategoryDAO forumCategoryDAO) {
		this.forumCategoryDAO = forumCategoryDAO;
	}

	/**
	 * @param forumPostDAO the forumPostDAO to set
	 */
	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	/**
	 * @param forumSubCategoryDAO the forumSubCategoryDAO to set
	 */
	public void setForumSubCategoryDAO(ForumSubCategoryDAO forumSubCategoryDAO) {
		this.forumSubCategoryDAO = forumSubCategoryDAO;
	}

	/**
	 * @param userMessageDAO the userMessageDAO to set
	 */
	public void setUserMessageDAO(UserMessageDAO userMessageDAO) {
		this.userMessageDAO = userMessageDAO;
	}
}
