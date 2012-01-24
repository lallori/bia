/*
 * DocumentDAO.java
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
package org.medici.docsources.dao.document;

import java.util.Date;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.Document;

/**
 * Document Dao.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface DocumentDAO extends Dao<Integer, Document> {

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	public Document checkVolumeFolio(Integer summaryId) throws PersistenceException;
	
	/**
	 * This method is used to count how many documents are linked to a volume.
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	public Long countDocumentsLinkedToAVolume(Integer summaryId) throws PersistenceException;
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param folioNum
	 * @param folioMod
	 * @return
	 * @throws PersistenceException
	 */
	public Document findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException;

	/**
	 * 
	 * @param folioNum
	 * @param folioMod
	 * @return
	 * @throws PersistenceException
	 */
	public Document findDocumentByFolioStart(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException;
	
	/**
	 * This method returns last entry {@link org.medici.docsources.domain.Document} 
	 * created on database.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Document}
	 * @throws PersistenceException
	 */
	public Document findLastEntryDocument() throws PersistenceException;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer findNumberOfDocumentsRelatedVolume(Integer summaryId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer findNumberOfRecipientDocumentsPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer findNumberOfSenderDocumentsPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * This method searches documents which contains the parameters set in {@link org.medici.docsources.common.search}
	 * object and return a result page.
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param volumeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchDocumentsRelatedVolume(String volumeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchRecipientDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page searchSenderDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method is used to update lucene index by removing and inserting document from a specific date.
	 *  
	 * @param fromDate Delete date
	 * @throws PersistenceException
	 */
	public void updateIndex(Date fromDate) throws PersistenceException;
}
