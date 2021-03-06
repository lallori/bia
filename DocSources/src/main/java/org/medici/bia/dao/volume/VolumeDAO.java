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
package org.medici.bia.dao.volume;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Volume;

/**
 * Volume Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface VolumeDAO extends Dao<Integer, Volume> {

	/**
	 * 
	 * @param inputDate
	 * @return
	 * @throws PersistenceException
	 */
	Long countVolumeCreatedAfterDate(Date inputDate) throws PersistenceException;

	/**
	 * This method returns last entry {@link org.medici.bia.domain.Volume}created on database.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.Volume}
	 * @throws PersistenceException
	 */
	Volume findLastEntryVolume() throws PersistenceException;

	/**
	 * This method searches a single volume identified by is unique identifiers.
	 * 
	 * @param volNum Volume identifier
	 * @param volLetExt Volume extension
	 * @return The Volume searched.
	 * @throws PersistenceException
	 */
	Volume findVolume(Integer volNum, String volLetExt) throws PersistenceException;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	List<String> searchOtherLang(String query) throws PersistenceException;
	
	/**
	 * This method searches volumes which contains the parameters set in {@link org.medici.bia.common.search}
	 * object and return a result page.
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchVolumes(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * 
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	List<Volume> searchVolumes(String query) throws PersistenceException;
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchVolumesByDigitization(Integer volNum, Integer volNumBetween, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method searches volumes which contains text input parameter in one of his fields
	 * and return a result page.
	 * 
	 * @param text Text to be searched
	 * @param paginationFilter
	 * @return A result page of volumes searched
	 * @throws PersistenceException
	 */
	Page searchVolumes(String text, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method set digitized information to true to a list of input volumes.
	 * 
	 * @param summaryIds
	 * @return
	 * @throws PersistenceException
	 */
	Integer updateNewDigitizedVolume(List<Integer> summaryIds) throws PersistenceException;
}
