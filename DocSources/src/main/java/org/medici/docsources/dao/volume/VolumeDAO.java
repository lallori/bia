/*
 * VolumeDAO.java
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
package org.medici.docsources.dao.volume;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.AdvancedSearch;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.common.search.SimpleSearch;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.Volume;

/**
 * Volume Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface VolumeDAO extends Dao<Integer, Volume> {

	/**
	 * 
	 * @param advancedSearchContainer
	 * @param paginationFilter
	 * @return
	 */
	public Page advancedSearchVolumes(AdvancedSearch advancedSearchContainer, PaginationFilter paginationFilter);
	
	/**
	 * This method returns last entry {@link org.medici.docsources.domain.Volume}created on database.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Volume}
	 * @throws PersistenceException
	 */
	public Volume findLastEntryVolume() throws PersistenceException;

	/**
	 * This method searches a single volume identified by is unique identifiers.
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws PersistenceException
	 */
	public Volume findVolume(Integer volNum, String volLetExt) throws PersistenceException;

	/**
	 * This method searches volumes which contains text input parameter in one of his fields
	 * and return a result page.
	 * 
	 * @param text
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchVolumes(String text, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchVolumes(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param simpleSearchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page simpleSearchVolumes(SimpleSearch simpleSearchContainer, PaginationFilter paginationFilter) throws PersistenceException;
}
