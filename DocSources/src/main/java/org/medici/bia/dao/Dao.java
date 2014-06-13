/*
 * Dao.java
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
package org.medici.bia.dao;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;

/**
 * 
 * @param <K>
 * @param <E>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface Dao<K, E> extends Serializable {
	
	/**
	 * Clear the persistence context, causing all managed entities to become detached. 
	 * Changes made to entities that have not been flushed to the database will not be persisted.
	 * 
	 * @throws IllegalStateException if the EntityManager has been closed
	 */
	void clear() throws IllegalStateException;
	
	/**
	 * This method is used to count advanced search functions.
	 * 
	 * @param searchContainer
	 * @return
	 * @throws PersistenceException
	 */
	Long countSearchMYSQL(org.medici.bia.common.search.Search searchContainer) throws PersistenceException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	E find(K id) throws PersistenceException;

	/**
	 * 
	 * @throws PersistenceException
	 */
	void generateIndex() throws PersistenceException;

	/**
	 * 
	 * @param entity
	 * @throws PersistenceException
	 */
	E merge(E entity) throws PersistenceException;

	/**
	 * 
	 * @throws PersistenceException
	 */
	void optimizeIndex() throws PersistenceException;

	/**
	 * 
	 * @param entity
	 * @throws PersistenceException
	 */
	void persist(E entity) throws PersistenceException;	

	/**
	 * 
	 * @param entity
	 * @throws PersistenceException
	 */
	void refresh(E entity) throws PersistenceException;

	/**
	 * 
	 * @param entity
	 * @throws PersistenceException
	 */
	void remove(E entity) throws PersistenceException;	

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchMYSQL(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param fromDate
	 * @throws PersistenceException
	 */
	void updateIndex(Date fromDate) throws PersistenceException;
}
