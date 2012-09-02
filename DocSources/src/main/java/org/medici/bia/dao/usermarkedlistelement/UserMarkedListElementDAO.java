/*
 * UserMarkedListElementDAO.java
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
package org.medici.bia.dao.usermarkedlistelement;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserMarkedListElement;

/**
 * UserMarkedListElement Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface UserMarkedListElementDAO extends Dao<Integer, UserMarkedListElement> {
	
	/**
	 * 
	 * @param idMarkedList
	 * @param entryId
	 * @return
	 * @throws PersistenceException
	 */
	public UserMarkedListElement findDocumentInMarkedList(Integer idMarkedList, Integer entryId) throws PersistenceException;
	
	/**
	 * 
	 * @param idMarkedList
	 * @param personId
	 * @return
	 * @throws PersistenceException
	 */
	public UserMarkedListElement findPersonInMarkedList(Integer idMarkedList, Integer personId) throws PersistenceException;
	
	/**
	 * 
	 * @param idMarkedList
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	public UserMarkedListElement findPlaceInMarkedList(Integer idMarkedList, Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param idMarkedList
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	public UserMarkedListElement findVolumeInMarkedList(Integer idMarkedList, Integer summaryId) throws PersistenceException;
	
	/**
	 * 
	 * @param user
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page findMarkedList(User user, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param idMarkedList
	 * @param idElements
	 * @return
	 * @throws PersistenceException
	 */
	public List<UserMarkedListElement> getMarkedListElements(Integer idMarkedList, List<Integer> idElements) throws PersistenceException;
	
	/**
	 * 
	 * @param idMarkedList
	 * @return
	 * @throws PersistenceException
	 */
	public Integer removeAllMarkedListElements(Integer idMarkedList) throws PersistenceException;
	
	/**
	 * 
	 * @param idMarkedList
	 * @param idElements
	 * @return
	 * @throws PersistenceException
	 */
	public Integer removeMarkedListElements(Integer idMarkedList, List<Integer> idElements) throws PersistenceException;

}
