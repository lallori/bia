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
package org.medici.bia.service.community;

import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public interface CommunityService {

	/**
	 * 
	 * @param forum
	 * @param parentForum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum addNewForum(Forum forum, Forum parentForum) throws ApplicationThrowable;

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
	public UserMessage createNewMessage(UserMessage userMessage) throws ApplicationThrowable;

	/**
	 * 
	 * @param postId
	 * @throws ApplicationThrowable
	 */
	public void deleteForumPost(Integer postId) throws ApplicationThrowable;
	
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
	 * @param topicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ForumPost findFirstPostTopic(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ForumPost findPost(Integer postId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getCategory(Forum category) throws ApplicationThrowable;

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
	 * @param letter
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page getForumMembers(String letter, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumTopic
	 * @param paginationFilterPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page getForumPostsFromTopic(ForumTopic forumTopic, PaginationFilter paginationFilterPost) throws ApplicationThrowable;

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
	public ForumTopic getForumTopic(ForumTopic forumTopic) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @param paginationFilterPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page getForumTopics(Forum forum, PaginationFilter paginationFilterPost) throws ApplicationThrowable;

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
	 * @param integer
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page getSubForums(Integer forumParentId, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param postId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean ifPostIsParent(Integer postId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public User joinUserOnForum()throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMessage replyMessage(UserMessage userMessage, Integer parentUserMessageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ForumPost replyPost(ForumPost forumPost) throws ApplicationThrowable;

	/**
	 * 
	 * @param simpleSearchForumPost
	 * @param paginationFilter
	 * @return
	 */
	public Page searchForumPosts(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchMessages(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

}