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
 */
public interface ForumTopicDAO extends Dao<Integer, ForumTopic> {

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public Long countTotalActive() throws PersistenceException;
	
	/**
	 * 
	 * @param forumId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer deleteForumTopicsFromForum(Integer forumId) throws PersistenceException;

	/**
	 * Returns a specific forum topic.
	 * 
	 * @param forumTopic
	 * @return
	 * @throws PersistenceException
	 */
	public ForumTopic findForumTopic(ForumTopic forumTopic) throws PersistenceException;

	/**
	 * Returns list of topics on a specific forum.
	 * 
	 * @param forum
	 * @param paginationFilterPost
	 * @return
	 * @throws PersistenceException
	 */
	public Page findForumTopics(Forum forum, PaginationFilter paginationFilterTopic) throws PersistenceException;

	/**
	 * 
	 * @param numberOfElements
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<ForumTopic> findMostRecentForumTopics(Integer numberOfElements) throws ApplicationThrowable;

	/**
	 * 
	 * @param numberOfElements
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<ForumTopic> findTopForumTopics(Integer numberOfElements) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @param paginationFilterTopics
	 * @return
	 */
	public Page getForumTopicsByParentForum(Forum forum, PaginationFilter paginationFilterTopics) throws PersistenceException;
}
