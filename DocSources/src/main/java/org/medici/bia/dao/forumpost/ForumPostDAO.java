/*
 * ForumPostDAO.java
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
package org.medici.bia.dao.forumpost;

import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.User;

/**
 * ForumPost DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
public interface ForumPostDAO extends Dao<Integer, ForumPost> {
	
	/**
	 * This method counts the number of active posts of a topic
	 * 
	 * @param forumTopicId the forum topic identifier
	 * @return the number of active posts
	 */
	long countTopicPosts(Integer forumTopicId);

	/**
	 * 
	 * @param forumId
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteAllForumPosts(Integer forumId) throws PersistenceException;
	
	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteAllForumTopicPosts(Integer topicId) throws PersistenceException;
	
	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Map<Integer, List<Object>> getActiveTopicsInformations(Integer page, Integer numberOfTopicsForPage) throws PersistenceException;
	
	/**
	 * Returns the list of not deleted posts of a topic.
	 * 
	 * @param topicId the topic identifier
	 * @return the list of not deleted posts of a topic
	 * @throws PersistenceException
	 */
	List<ForumPost> getAllNotDeletedForumTopicPosts(Integer topicId) throws PersistenceException;
	
	/**
	 * Returns the first created post of a topic.
	 * 
	 * @param topicId the topic identifier
	 * @return the first post
	 * @throws PersistenceException
	 */
	ForumPost getFirstForumTopicPostByCreationDate(Integer topicId) throws PersistenceException;
	
	/**
	 * Returns the first modified post of a topic.
	 *  
	 * @param topicId the topic identifier
	 * @return the first post
	 * @throws PersistenceException
	 */
	ForumPost getFirstForumTopicPostByLastUpdate(Integer topicId) throws PersistenceException;
	
	/**
	 * Returns a paginated list of topic posts.
	 * 
	 * @param forumTopic the forum topic
	 * @param paginationFilterPost the pagination filter
	 * @return the paginated list of topic posts
	 * @throws PersistenceException
	 */
	Page getForumTopicPosts(ForumTopic forumTopic, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * Retrieves the last created post in a forum.
	 * 
	 * @param forum the forum
	 * @return the last post found
	 * @throws PersistenceException
	 */
	ForumPost getLastForumPostByCreationDate(Forum forum) throws PersistenceException;
	
	/**
	 * Retrieves the last modified post in a forum.
	 * 
	 * @param forum the forum
	 * @return the last post found
	 * @throws PersistenceException
	 */
	ForumPost getLastForumPostByLastUpdate(Forum forum) throws PersistenceException;
	
	/**
	 * Returns the last created post of a topic.
	 * 
	 * @param forumTopic the topic
	 * @return the last post of the topic
	 * @throws PersistenceException
	 */
	ForumPost getLastForumTopicPostByCreationDate(ForumTopic forumTopic) throws PersistenceException;
	
	/**
	 * Returns the last modified post of a topic.
	 * 
	 * @param forumTopic the topic
	 * @return the last post of the topic
	 * @throws PersistenceException
	 */
	ForumPost getLastForumTopicPostByLastUpdate(ForumTopic forumTopic) throws PersistenceException;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws PersistenceException
	 */
	Forum getMostActiveForumByUser(User user) throws PersistenceException;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws PersistenceException
	 */
	ForumTopic getMostActiveTopicByUser(User user) throws PersistenceException;
	
	/**
	 * 
	 * @param postId
	 * @return
	 * @throws PersistenceException
	 */
	Boolean isParentPost(Integer postId) throws PersistenceException;

	/**
	 * 
	 * @param originalAccount
	 * @param newAccount
	 * @return
	 * @throws PersistenceException
	 */
	Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException;

}
