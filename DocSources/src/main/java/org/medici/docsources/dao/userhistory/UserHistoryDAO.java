/*
 * UserHistoryDAO.java
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
package org.medici.docsources.dao.userhistory;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Category;

/**
 * UserHistory Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface UserHistoryDAO extends Dao<Integer, UserHistory> {

	/**
	 * 
	 * @throws PersistenceException
	 */
	public Integer deleteMyHistory() throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @throws PersistenceException
	 */
	public Integer deleteMyHistory(Category category) throws PersistenceException;

	/**
	 * 
	 * @throws PersistenceException
	 */
	public Integer deleteUserHistory(String username) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @throws PersistenceException
	 */
	public Integer deleteUserHistory(String username, Category category) throws PersistenceException;

	/**
	 * This method will return a list with size "resultSize", of user history on document base. 
	 * 
	 * @param category
	 * @param resultSize
	 * @return
	 * @throws PersistenceException
	 */
	public List<UserHistory> findHistory(Category category, Integer resultSize) throws PersistenceException;

	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page findHistory(Category category, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param category
	 * @param paginationFilter
	 * @param resultSize
	 * @return
	 * @throws PersistenceException
	 */
	public Page findHistory(Category category, PaginationFilter paginationFilter, Integer resultSize) throws PersistenceException;
	
	/**
	 * This method will return a list with size "resultSize", of user history on document base. 
	 * 
	 * @param resultSize
	 * @return
	 * @throws PersistenceException
	 */
	public List<UserHistory> findHistory(Integer resultSize) throws PersistenceException;


	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page findHistory(PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param document
	 * @param entryId
	 * @return
	 * @throws PersistenceException
	 */
	public UserHistory findHistoryFromEntity(Category category, Integer primaryKeyId) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	public UserHistory findLastEntry() throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	public UserHistory findLastEntry(Category category) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @param idUserHistory
	 * @return
	 * @throws PersistenceException
	 */
	public UserHistory findNextHistoryCursor(Category category, Integer idUserHistory) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @param idUserHistory
	 * @return
	 * @throws PersistenceException
	 */
	public UserHistory findPreviousHistoryCursor(Category category, Integer idUserHistory) throws PersistenceException;

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public Integer restoreMyHistory() throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	public Integer restoreMyHistory(Category category) throws PersistenceException;

	/**
	 * 
	 * @param username
	 * @return
	 * @throws PersistenceException
	 */
	public Integer restoreUserHistory(String username) throws PersistenceException;

	/**
	 * 
	 * @param username
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	public Integer restoreUserHistory(String username, Category category) throws PersistenceException;
}
