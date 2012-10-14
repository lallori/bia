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
package org.medici.bia.dao.userhistory;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Category;

/**
 * UserHistory Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface UserHistoryDAO extends Dao<Integer, UserHistory> {

	/**
	 * 
	 * @throws PersistenceException
	 */
	Integer deleteMyHistory(User user) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @throws PersistenceException
	 */
	Integer deleteMyHistory(User user, Category category) throws PersistenceException;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteUserHistory(User user) throws PersistenceException;

	/**
	 * 
	 * @param user
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteUserHistory(User user, Category category) throws PersistenceException;

	/**
	 * 
	 * @param user
	 * @param category
	 * @param primaryKeyId
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findCategoryHistoryFromEntity(User user, Category category, Integer primaryKeyId) throws PersistenceException;

	/**
	 * This method will return a list with size "resultSize", of user history on document base. 
	 * 
	 * @param user
	 * @param category
	 * @param resultSize
	 * @return
	 * @throws PersistenceException
	 */
	List<UserHistory> findHistory(User user,Category category, Integer resultSize) throws PersistenceException;
	
	/**
	 * This method will return a list with size "resultSize", of user history on document base. 
	 * 
	 * @param resultSize
	 * @return
	 * @throws PersistenceException
	 */
	List<UserHistory> findHistory(User user, Integer resultSize) throws PersistenceException;
	
	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page findHistory(User user, PaginationFilter paginationFilter) throws PersistenceException;


	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page findHistory(User user, Category category, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @param paginationFilter
	 * @param resultSize
	 * @return
	 * @throws PersistenceException
	 */
	Page findHistory(User user, Category category, PaginationFilter paginationFilter, Integer resultSize) throws PersistenceException;
	
	/**
	 * 
	 * @param category
	 * @param primaryKeyId
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findHistoryFromEntity(User user, Category category, Integer primaryKeyId) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findLastEntry(User user) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findLastEntry(User user, Category category) throws PersistenceException;
	
	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findLastEntryBase(User user) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @param idUserHistory
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findNextCategoryHistoryCursor(User user, Category category, Integer idUserHistory) throws PersistenceException;
	
	/**
	 * 
	 * @param idUserHisotry
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findNextHistoryCursor(User user, Integer idUserHisotry) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @param idUserHistory
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findPreviousCategoryHistoryCursor(User user, Category category, Integer idUserHistory) throws PersistenceException;
	
	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws PersistenceException
	 */
	UserHistory findPreviousHistoryCursor(User user, Integer idUserHistory) throws PersistenceException;

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Integer restoreMyHistory(User user) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	Integer restoreMyHistory(User user, Category category) throws PersistenceException;

	/**
	 * 
	 * @param username
	 * @return
	 * @throws PersistenceException
	 */
	Integer restoreUserHistory(User user) throws PersistenceException;

	/**
	 * 
	 * @param username
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	Integer restoreUserHistory(User user, Category category) throws PersistenceException;
}
