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
	long countPostsFromTopic(Integer forumTopicId);

	/**
	 * 
	 * @param forumId
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteForumPostsFromForum(Integer forumId) throws PersistenceException;
	
	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteForumPostsFromForumTopic(Integer topicId) throws PersistenceException;
	
	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws PersistenceException
	 */
	ForumPost findFirstPostByTopicId(Integer topicId) throws PersistenceException;
	
	/**
	 * 
	 * @param postId
	 * @return
	 * @throws PersistenceException
	 */
	Boolean findIfPostIsParent(Integer postId) throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	ForumPost findLastPostFromForum(Forum forum) throws PersistenceException;

	/**
	 * 
	 * @param forumTopic
	 * @return
	 * @throws PersistenceException
	 */
	ForumPost findLastPostFromForumTopic(ForumTopic forumTopic) throws PersistenceException;

	/**
	 * Returns list of posts on a specific topic.
	 * 
	 * @param forumTopic
	 * @param paginationFilterPost
	 * @return
	 * @throws PersistenceException
	 */
	Page findPostsFromTopic(ForumTopic forumTopic, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Map<Integer, List<Object>> getActiveTopicsInformations(Integer page, Integer numberOfTopicsForPage) throws PersistenceException;
	
	/**
	 * 
	 * @param postId
	 * @return
	 * @throws PersistenceException
	 */
	ForumPost getForumPost(Integer postId) throws PersistenceException;

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
	 * @param originalAccount
	 * @param newAccount
	 * @return
	 * @throws PersistenceException
	 */
	Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException;
}
