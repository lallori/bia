/*
 * CommunityService.java
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
package org.medici.docsources.service.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.UserMessageSearch;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.ForumPost;
import org.medici.docsources.domain.UserComment;
import org.medici.docsources.domain.UserMessage;
import org.medici.docsources.domain.Forum.Type;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface CommunityService {

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ForumPost addNewPost(ForumPost forumPost) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Long checkNewMessages() throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment createNewComment(UserComment userComment) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment createNewMessage(UserMessage userMessage) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment deleteComment(Integer commentId) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void deleteMessage(Integer userMessageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ForumPost editPost(ForumPost forumPost) throws ApplicationThrowable;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ForumPost findPost(Integer id) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getCategory(Forum category) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnPerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnPlace(Integer placeAllId) throws ApplicationThrowable;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getFirstCategory() throws ApplicationThrowable;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getForum(Integer id) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Forum> getForumsByType(Type type) throws ApplicationThrowable;

	/**
	 * 
	 * @param categoriesIds
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HashMap<Integer, List<Forum>> getForumsGroupByCategory(List<Integer> categoriesIds) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HashMap<String, Object> getForumsStatistics() throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Forum> getSubCategories(Forum forum) throws ApplicationThrowable;

	/**
	 * 
	 * @param integer
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Forum> getSubForums(Integer forumParentId) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment replyComment(UserComment userComment, Integer parentUserCommentId) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment replyMessage(UserMessage userMessage, Integer parentUserMessageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchMessages(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
}
