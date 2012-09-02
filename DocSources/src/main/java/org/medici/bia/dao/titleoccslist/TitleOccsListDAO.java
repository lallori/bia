/*
 * TitleOccListDAO.java
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
package org.medici.bia.dao.titleoccslist;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.search.SimpleSearchTitleOrOccupation;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.TitleOccsList;

/**
 * TitleOccList Dao.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface TitleOccsListDAO extends Dao<Integer, TitleOccsList> {

	/**
	 * 
	 * @param titleOcc
	 * @return
	 * @throws PersistenceException
	 */
	public TitleOccsList findTitleOcc(String titleOcc) throws PersistenceException;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchTitleOrOccupation(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param simpleSearchTitleOrOccupation
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchTitlesOrOccupations(SimpleSearchTitleOrOccupation simpleSearchTitleOrOccupation, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method searches a list of titles and occupations which could be related to a person 
	 * which contains a text parameter (String searchText). 
	 * 
	 * @param searchText Text to be searched
	 * @return A List<TitleOccsList> searched.
	 * @throws PersistenceException
	 */
	public List<TitleOccsList> searchTitleOrOccupationLinkableToPerson(String searchText) throws PersistenceException;

	/**
	 * 
	 * @param alias
	 * @param roleCatId
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchTitleOrOccupationWithAssignedPeople(String alias, Integer roleCatId, PaginationFilter paginationFilter) throws PersistenceException;

}