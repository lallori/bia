/*
 * EplToLinkDAO.java
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
package org.medici.docsources.dao.epltolink;

import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.EplToLink;

/**
 * EplToLink Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface EplToLinkDAO extends Dao<Integer, EplToLink> {

		
	/**
	 * This method searches a topic linked to a document identified by his entryId
	 * and by a document identifier. 
	 * 
	 * @param entryId Document identifier.
	 * @param eplToId Topic linked identifier.
	 * @return A topic linked found.
	 * @throws PersistenceException
	 */
	public EplToLink find(Integer entryId, Integer eplToId) throws PersistenceException;

	/**
	 * This method searches every topics linked to a document identified by
	 * his entryId. 
	 * 
	 * @param entryId Document identifier.
	 * @return List of topic linked to a document.
	 * @throws PersistenceException
	 */
	public List<EplToLink> findByEntryId(Integer entryId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer findNumberOfTopicsByPlaceAllId(Integer placeAllId) throws PersistenceException;
	
	/**
	 * This method find the number of documents in topics with a specified place.
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer findNumberOfDocumentInTopicsByPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllIds
	 * @return
	 * @throws PersistenceException
	 */
	public Map<Integer, Long> findNumbersOfDocumentsInTopicsByPlace(List<Integer> placeAllIds) throws PersistenceException;
	
	/**
	 * 
	 * @param topic
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchTopics(String topic, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Map<String, Long> searchTopicsPlace(String placeToSearch) throws PersistenceException;

}
