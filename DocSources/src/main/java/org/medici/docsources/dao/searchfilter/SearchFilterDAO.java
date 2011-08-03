/*
 * SearchFilterDAO.java
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
package org.medici.docsources.dao.searchfilter;


import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.SearchFilter;

/**
 * Search Filter DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface SearchFilterDAO extends Dao<String, SearchFilter> {

	/**
	 * This method searches every {@link org.medici.docsources.domain.SearchFilter} 
	 * of a user by its username.
	 * 
	 * @param username The name of the user
	 * @return A List<SearchFilter> of the user.
	 * @throws PersistenceException
	 */
	public List<SearchFilter> findUserSearchFilters(String username) throws PersistenceException;

	/**
	 * 
	 * @param username
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page findUserSearchFilters(String username, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method searches a {@link org.medici.docsources.domain.SearchFilter} 
	 * of a user by its username and the search filter's identifier.
	 * 
	 * @param username The name of the user 
	 * @param idSearchFilter A SearchFilter identifier
	 * @return The SearchFilter searched.
	 * @throws PersistenceException
	 */
	public SearchFilter findUserSearchFilter(String username, Integer idSearchFilter) throws PersistenceException;
}
