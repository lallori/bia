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
package org.medici.bia.dao.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;

/**
 * Document Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface DocumentDAO extends Dao<Integer, Document> {

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	Document checkVolumeFolio(Integer summaryId) throws PersistenceException;
	
	/**
	 * 
	 * @param inputDate
	 * @return
	 * @throws PersistenceException
	 */
	Long countDocumentCreatedAfterDate(Date inputDate) throws PersistenceException;
	
	/**
	 * @param inputDate
	 * @return
	 * @throws PersistenceException
	 */
	Long countDocumentCreatedBeforeDate(Date inputDate) throws PersistenceException;
	
	/**
	 * This method is used to count how many documents are linked to a volume.
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	Long countDocumentsLinkedToAVolume(Integer summaryId) throws PersistenceException;

	/**
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @return a list of {@link Document}
	 * @throws PersistenceException
	 */
	List<Document> findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException;
	
	/**
	 * This method searches all the documents that have the provided details of volume, insert and folio.<br/>
	 * <strong>NOTE:</strong> if the <code>folioRectoVerso</code> provided is null documents are not filtered 
	 * by this detail. Furthermore if <code>folioRectoVerso</code> is <code>Document.RectoVerso.N</code> we 
	 * search for all documents with NULL recto/verso detail in the database (this is the case of old processes
	 * when it was not possible to specify the recto/verso detail). 
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param folioRectoVerso the folio {@link Document.RectoVerso} information 
	 * @return a list of {@link Document}
	 * @throws PersistenceException
	 */
	List<Document> findDocument(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, Document.RectoVerso folioRectoVerso) throws PersistenceException;
	
	/**
	 * This method searches all the documents that satisfy the provided filters of volume, insert and folio.<br/>
	 * <strong>NOTE:</strong> <code>volNum</code>, <code>volLetExt</code> and <code>folioNum</code> are mandatory
	 * (<code>volLetExt</code> and <code>folioNum</code> can be null), while other parameters are optional (in
	 * those cases they are not considered if they are equal to empty strings).
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param rectoVerso the recto/verso
	 * @return a list of {@link Document}, an empty list if no document is found.
	 * @throws PersistenceException
	 */
	List<Document> findDocumentsOnFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException;
	
	/**
	 * This method searches all the documents that satisfy the provided filters of volume, insert and folio.<br/>
	 * <strong>NOTE:</strong> <code>volNum</code>, <code>volLetExt</code> and <code>folioNum</code> are mandatory
	 * (<code>volLetExt</code> and <code>folioNum</code> can be null), while other parameters are optional (in
	 * those cases they are not considered if they are equal to empty strings).
	 * Furthermore, folio recto/verso filter is considered twice: with the value provided (only if it is not empty)
	 * and then with null value.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param rectoVerso the recto/verso
	 * @return a list of {@link Document}, an empty list if no document is found.
	 * @throws PersistenceException
	 */
	List<Document> findDocumentsOnFolioWithOrWithoutRectoVerso(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException;
	
	/**
	 * This method searches all the documents that satisfy the provided filters of volume, insert and transcribe folio.<br/>
	 * <strong>NOTE:</strong> <code>volNum</code>, <code>volLetExt</code> and <code>transcribeFolioNum</code> are mandatory
	 * (<code>volLetExt</code> and <code>transcribeFolioNum</code> can be null), while other parameters are optional (in
	 * those cases they are not considered if they are equal to empty strings).
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param transcribeFolioNum the transcribe folio number
	 * @param transcribeFolioMod the transcribe folio extension
	 * @param rectoVerso the transcribe recto/verso
	 * @return a list of {@link Document}, an empty list if no document is found.
	 * @throws PersistenceException
	 */
	List<Document> findDocumentsOnTranscribeFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer transcribeFolioNum, String transcribeFolioMod, String rectoVerso) throws PersistenceException;
	
	/**
	 * This method searches all the documents that satisfy the provided filters of volume, insert and transcribe folio.<br/>
	 * <strong>NOTE:</strong> <code>volNum</code>, <code>volLetExt</code> and <code>transcribeFolioNum</code> are mandatory
	 * (<code>volLetExt</code> and <code>transcribeFolioNum</code> can be null), while other parameters are optional (in
	 * those cases they are not considered if they are equal to empty strings).
	 * Furthermore, folio recto/verso filter is considered twice: with the value provided (only if it is not empty)
	 * and then with null value.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param transcribeFolioNum the transcribe folio number
	 * @param transcribeFolioMod the transcribe folio extension
	 * @param rectoVerso the transcribe recto/verso
	 * @return a list of {@link Document}, an empty list if no document is found.
	 * @throws PersistenceException
	 */
	List<Document> findDocumentsOnTranscribeFolioWithOrWithoutRectoVerso(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException;
	
	/**
	 * @param volNum the volume number
	 * @param volLetExt the volume extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @return
	 * @throws PersistenceException
	 */
	List<Document> findDocumentByFolioStart(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException;

	/**
	 * This method returns last entry {@link org.medici.bia.domain.Document} 
	 * created on database.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.Document}
	 * @throws PersistenceException
	 */
	Document findLastEntryDocument() throws PersistenceException;
	
	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfDocumentsRelatedVolume(Integer summaryId) throws PersistenceException;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfRecipientDocumentsPerson(Integer personId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfRecipientDocumentsPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfSenderDocumentsPerson(Integer personId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfSenderDocumentsPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllIds
	 * @return
	 * @throws PersistenceException
	 */
	Map<Integer, Long> findNumbersOfDocumentsRelatedPlace(List<Integer> placeAllIds) throws PersistenceException;
	
	/**
	 * This method returns the association between a document and the correspondent image based on the
	 * letter starting folio.
	 * 
	 * @param entryIds a list of document identifiers
	 * @return the association between document and image as a map
	 * @throws PersistenceException
	 */
	Map<Integer, Integer> getAssociatedImage(List<Integer> entryIds) throws PersistenceException;
	
	/**
	 * This method returns the documents associated to the provided image (as transcribed folio).
	 * 
	 * @param image the image
	 * @return the documents associated to the image
	 * @throws PersistenceException
	 */
	List<Document> getDocumentsByImage(Image image) throws PersistenceException;
	
	/**
	 * This method returns the document relative to the course transcription container.
	 * 
	 * @param forumContainerId the course transcription container identifier
	 * @return the document found
	 * @throws PersistenceException
	 */
	Document getTeachingDocument(Integer forumContainerId) throws PersistenceException;
	
	/**
	 * This method searches documents which contains the parameters set in {@link org.medici.bia.common.search}
	 * object and return a result page.
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * This method retrieves all documents created before the specified date. 
	 * Documents retrieved are returned in a result page.
	 * 
	 * @param date the date
	 * @param paginationFilter the filter
	 * @return the documents found in a result page
	 * @throws PersistenceException
	 */
	Page searchDocumentsCreatedBefore(Date date, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param volumeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchDocumentsRelatedVolume(String volumeToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * Alpha version!
	 */
	List<Document> searchDocumentsToPrint(Search searchContainer) throws PersistenceException;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchLinkedDocumentsTopic(String placeToSearch, String topicToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param topicId
	 * @param placeAllId
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchLinkedDocumentsTopic(Integer topicId, Integer placeAllId, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchRecipientDocumentsPerson(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchRecipientDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchReferringToDocumentsPerson(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchSenderDocumentsPerson(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchSenderDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method is used to update lucene index by removing and inserting document from a specific date.
	 *  
	 * @param fromDate Delete date
	 * @throws PersistenceException
	 */
	void updateIndex(Date fromDate) throws PersistenceException;
	
}
