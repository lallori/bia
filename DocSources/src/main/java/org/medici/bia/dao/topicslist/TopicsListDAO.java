/*
 * TopicsListDAO.java
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
package org.medici.bia.dao.topicslist;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.TopicList;

/**
 * TopicsList Dao.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface TopicsListDAO extends Dao<Integer, TopicList> {

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	List<TopicList> findTopicsList() throws PersistenceException;
	
	/**
	 * This method return all the topicList excluding topic that can not inserted
	 * @return
	 * @throws PersistenceException
	 */
	List<TopicList> findTopicsListForUsers() throws PersistenceException;
	
	/**
	 * This method searches for topics which could be related to a document which contains 
	 * a text parameter (String alias) and returns a list of {@link org.medici.bia.domain.TopicList}.
	 * 
	 * @param topicIdList List of topics identifiers
	 * @param query Text to be searched
	 * @return A List<TopicList> that could be related to a document.
	 * @throws PersistenceException
	 */
	List<TopicList> searchTopicLinkableToDocument(List<Integer> topicIdList, String alias) throws PersistenceException;

	/**
	 * This method searches topics which contains the parameters set in {@link org.medici.bia.common.search}
	 * object and return a result page.
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchTopics(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;

}
