/*
 * ForumTopicDAO.java
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
package org.medici.bia.dao.forumtopic;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * ForumPost DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
public interface ForumTopicDAO extends Dao<Integer, ForumTopic> {

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Long countTotalActive() throws PersistenceException;
	
	/**
	 * 
	 * @param forumId
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteForumTopicsFromForum(Integer forumId) throws PersistenceException;
	
	/**
	 * Delete all topics directly related to the provided forum identifiers.
	 *  
	 * @param forumIds the list of forum identifiers
	 * @return the list of deleted topic identifiers
	 * @throws PersistenceException
	 */
	List<Integer> deleteForumTopicsFromForums(List<Integer> forumIds) throws PersistenceException;
	
	/**
	 * Deletes all topics related to an forum.<br/>
	 * The relationship can be direct (the forum contains topics) or not direct (the provided forum is an
	 * contains subforums with topics at any hierarchy level). 
	 * 
	 * 
	 * @param ancestorForumId the ancestor forum identifier
	 * @return the list of deleted topic identifiers
	 * @throws PersistenceException
	 */
	List<Integer> deleteForumTopicsFromAncestorForum(Integer ancestorForumId) throws PersistenceException;

	/**
	 * This method returns the forum linked to an annotation.
	 * 
	 * @param annotationId the annotation identifier
	 * @return the {@link Forum} found
	 * @throws PersistenceException
	 */
	ForumTopic getForumTopicByAnnotation(Integer annotationId) throws PersistenceException;
	
	/**
	 * Returns a specific forum topic by its identifier.
	 * 
	 * @param forumTopicId
	 * @return
	 * @throws PersistenceException
	 */
	ForumTopic getNotDeletedForumTopic(Integer forumTopicId) throws PersistenceException;

	/**
	 * Returns list of topics on a specific forum.
	 * 
	 * @param forum
	 * @param paginationFilterPost
	 * @return
	 * @throws PersistenceException
	 */
	Page findForumTopics(Forum forum, PaginationFilter paginationFilterTopic) throws PersistenceException;

	/**
	 * This method returns the list of forum topics with the most recent users activity.
	 * It does not consider topics of the course section.
	 * 
	 * @param numberOfElements the number of topics to retrieve
	 * @return the list of forum topics found
	 * @throws ApplicationThrowable
	 */
	List<ForumTopic> findMostRecentForumTopics(Integer numberOfElements) throws PersistenceException;

	/**
	 * This method returns the list of forum topics with the maximum number of post.
	 * It does not consider topics of the course section.
	 * 
	 * @param numberOfElements the number of topics to retrieve
	 * @return the list of forum topics found
	 * @throws ApplicationThrowable
	 */
	List<ForumTopic> findTopForumTopics(Integer numberOfElements) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param forum
	 * @param paginationFilterTopics
	 * @return
	 */
	Page getForumTopicsByParentForum(Forum forum, PaginationFilter paginationFilterTopics) throws PersistenceException;
	
	/**
	 * This method return a list of forum topics of a parent forum that are associated to a document.<br/>
	 * NOTE: the document has to be associated to the forum topic, not to the forum.<br/>
	 * The topics are ordered from the newer to the older (by creation date).
	 * 
	 * @param forumId the forum identifier
	 * @param documentId the document identifier
	 * @return
	 * @throws PersistenceException
	 */
	List<ForumTopic> getForumTopicsByParentForumAndDocument(Integer forumId, Integer documentId) throws PersistenceException;

	/**
	 * 
	 * @param originalAccount
	 * @param newAccount
	 * @return
	 * @throws PersistenceException
	 */
	Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException;

}
