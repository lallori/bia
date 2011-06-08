/*
 * PeopleDAO.java
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
package org.medici.docsources.dao.people;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.People;

/**
 * Person Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface PeopleDAO extends Dao<Integer, People> {
	
	/**
	 * This method returns last entry {@link org.medici.docsources.domain.People} 
	 * created on database.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.People}
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public People findLastEntryPerson() throws PersistenceException;

	/**
	 * This method generate Hibernate search index.
	 * 
	 * @throws PersistenceException
	 */
	public void generateIndex() throws PersistenceException;

	/**
	 * 
	 * @param personId
	 * @param query
	 * @return
	 */
	public List<People> searchChildLinkableToPerson(Integer personId, String query) throws PersistenceException;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	public List<People> searchFatherLinkableToPerson(String query) throws PersistenceException;

	/**
	 * 
	 * @param personId
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	public List<People> searchMotherLinkableToPerson(String query) throws PersistenceException;
	
	/**
	 * 
	 * @param searchText
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchPeople(String searchText, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchPeople(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	public List<People> searchPersonLinkableToDocument(List<Integer> peopleIdList, String searchText) throws PersistenceException;

	/**
	 * 
	 * @param queries
	 * @return
	 * @throws PersistenceException
	 */
	public List<People> searchRecipientsPeople(String searchText) throws PersistenceException;

	/**
	 * 
	 * @param alias
	 * @return
	 * @throws PersistenceException
	 */
	public List<People> searchSendersPeople(String searchText) throws PersistenceException;

}
