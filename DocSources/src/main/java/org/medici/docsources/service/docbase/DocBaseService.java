/*
 * DocBaseService.java
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
package org.medici.docsources.service.docbase;

import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EpLink;
import org.medici.docsources.domain.EplToLink;
import org.medici.docsources.domain.FactChecks;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.SynExtract;
import org.medici.docsources.domain.TopicList;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on
 * {@link org.medici.docsources.domain.Document} object.<br>
 * It defines every business methods needed to work on documents. With this
 * service, you can :<br>
 * - add a new document<br>
 * - modify an existing document<br>
 * - search a document by is unique id<br>
 * - execute complex search on volumes<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface DocBaseService {

	/**
	 * This method add a new {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document
	 * @throws ApplicationThrowable
	 */
	public Document addNewDocument(Document inputDocument) throws ApplicationThrowable;

	/**
	 * 
	 * @param synExtract
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document addNewExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * 
	 * @param epLink
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document addNewPersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param eplToLink
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document addNewTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;
	
	/**
	 * This method will return a new Document constructed in runtime (no stored on database), from which, user
	 * can follow to editing new document.
	 * 
	 * @param imageDocumentToCreate
	 * @param imageDocumentFolioStart
	 * @return
	 */
	public Document constructDocumentToTranscribe(Integer imageDocumentToCreate, Integer imageDocumentFolioStart) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void deleteDocument(Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param epLink
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void deletePersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param eplToLink
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void deleteTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document editCorrespondentsDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method modify details of an existing
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document
	 *            {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public Document editDetailsDocument(Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param synExtract
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document editExtractDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * This method modify extract or Synopsis of an existing
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param volume
	 *            {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public Document editExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * 
	 * @param factChecks
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document editFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable;

	/**
	 * This method modify people of an existing
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document
	 *            {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public Document editPersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param synExtract
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document editSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * This method modify topics of an existing
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param eplToLink
	 * @throws ApplicationThrowable
	 */
	public Document editTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<EpLink> findCorrespondentsPeopleDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * This method will search an existing document by his unique identifiers.
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document findDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Image findDocumentImage(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public FactChecks findFactChecksDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * This method last entry {@link org.medici.docsources.domain.Document}.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Document}
	 * @throws ApplicationThrowable
	 */
	public Document findLastEntryDocument() throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param epLinkId
	 * @throws ApplicationThrowable
	 */
	public EpLink findPersonDocument(Integer entryId, Integer epLinkId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public SynExtract findSynExtractDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public TopicList findTopic(Integer topicId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param eplToLinkId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public EplToLink findTopicDocument(Integer entryId, Integer eplToLinkId) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<EplToLink> findTopicsDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexDocument() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexEpLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexEplToLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexFactChecks() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexSynExtract() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexTopicList() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @param pageTurner
	 * @return
	 * @throws ApplicationThrowable
	 */
	public DocumentExplorer getDocumentExplorer(DocumentExplorer pageTurner) throws ApplicationThrowable;

	/**
	 * 
	 * @param text
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchDocuments(String text, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchPersonLinkableToDocument(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchPlaceLinkableToTopicDocument(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<TopicList> searchTopicLinkableToDocument(Integer entryId, String query) throws ApplicationThrowable;
}
