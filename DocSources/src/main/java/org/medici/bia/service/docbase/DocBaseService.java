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
package org.medici.bia.service.docbase;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.domain.DocReference;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EpLink;
import org.medici.bia.domain.EplToLink;
import org.medici.bia.domain.FactChecks;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.SynExtract;
import org.medici.bia.domain.TopicList;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on
 * {@link org.medici.bia.domain.Document} object.<br>
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
 *         @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public interface DocBaseService {
	/**
	 * 
	 * @param docReference
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document addNewDocReferenceDocument(DocReference docReference) throws ApplicationThrowable;
	
	/**
	 * Adds a new {@link org.medici.bia.domain.Document} entry.
	 * 
	 * @param inputDocument the {@link org.medici.bia.domain.Document} to be added.
	 * @return {@link org.medici.bia.domain.Document} entity.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Document addNewDocument(Document inputDocument) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum addNewDocumentForum(Document document) throws ApplicationThrowable;

	/**
	 * Adds a new {@link org.medici.bia.domain.SynExtract} entry.
	 * 
	 * @param synExtract the {@link org.medici.bia.domain.SynExtract} to be added.
	 * @return {@link org.medici.bia.domain.SynExtract} entity.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document addNewExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * Links a {@link org.medici.bia.domain.FactChecks} entry to an existing 
	 * {@link org.medici.bia.domain.Document}.
	 * 
	 * @param factChecks the {@link org.medici.bia.domain.FactChecks} to be linked.
	 * @return {@link org.medici.bia.domain.Document} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	 public Document addNewFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable;
	
	/**
	 * Links a {@link org.medici.bia.domain.Person} entry to an existing 
	 * {@link org.medici.bia.domain.Document}.
	 * 
	 * @param epLink the {@link org.medici.bia.domain.EpLink} to be linked.
	 * @return {@link org.medici.bia.domain.Document} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document addNewPersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * Links a {@link org.medici.bia.domain.Topic} entry and a {@link org.medici.bia.domain.Place} 
	 * to an existing {@link org.medici.bia.domain.Document}.
	 * 
	 * @param eplToLink the {@link org.medici.bia.domain.EpLink} to be linked.
	 * @return {@link org.medici.bia.domain.Document} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document addNewTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;

	
	/**
	 * @param volNum
	 * @param volLetExt
	 * @param insertNum
	 * @param insertLet
	 * @param folioNum
	 * @param folioMod
	 * @param rectoVerso
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean checkDocumentDigitized(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws ApplicationThrowable;
	
	/**
	 * @param volNum
	 * @param volLetExt
	 * @param insertNum
	 * @param insertLet
	 * @param folioNum
	 * @param folioMod
	 * @param rectoVerso
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean checkFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws ApplicationThrowable;
	
	/**
	 * @param volNum
	 * @param volLetExt
	 * @param insertNum
	 * @param insertLet
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean checkInsert(Integer volNum, String volLetExt, String insertNum, String insertLet) throws ApplicationThrowable;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document checkVolumeFolio(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method searches an existing document by his entryId.
	 * 
	 * @param entryId 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document compareDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * This method will return a new {@link org.medici.bia.domain.Document} constructed 
	 * in runtime, from which, user can follow to editing new document.
	 * 
	 * @param imageDocumentToCreate
	 * @param imageDocumentFolioStart
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document constructDocumentToTranscribe(Integer imageDocumentToCreate, Integer imageDocumentFolioStart) throws ApplicationThrowable;

	/**
	 * 
	 * @param docReference
	 * @throws ApplicationThrowable
	 */
	public void deleteDocReferenceDocument(DocReference docReference) throws ApplicationThrowable;
	
	/**
	 * This method mark a {@link org.medici.bia.domain.Document} as deleted .
	 * 
	 * @param entryId 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document deleteDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param epLink
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void deletePersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param eplToLink
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void deleteTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editCorrespondentsDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method modify details of an existing {@link org.medici.bia.domain.Document}.
	 * 
	 * @param document {@link org.medici.bia.domain.Document} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editDetailsDocument(Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param synExtract
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editExtractDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * This method modify extract or Synopsis of an existing
	 * {@link org.medici.bia.domain.Document}.
	 * 
	 * @param synExtract
	 *            {@link org.medici.bia.domain.SynExtract} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * 
	 * @param factChecks
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable;

	/**
	 * This method modify people of an existing
	 * {@link org.medici.bia.domain.Document}.
	 * 
	 * @param document {@link org.medici.bia.domain.Document} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editPersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param synExtract
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * This method modify topics of an existing
	 * {@link org.medici.bia.domain.Document}.
	 * 
	 * @param eplToLink
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document editTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<EpLink> findCorrespondentsPeopleDocument(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param entryIdFrom
	 * @param docReferenceId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public DocReference findDocReferenceDocument(Integer entryIdFrom, Integer docReferenceId) throws ApplicationThrowable;

	/**
	 * This method searches an existing document by his unique identifiers.
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document findDocument(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * This method searches an existing document by the volume and folio numbers.
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param folioNum
	 * @param folioMod
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Document> findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws ApplicationThrowable;

	/**
	 * @param volNum
	 * @param volLetExt
	 * @param insertNum
	 * @param insertLet
	 * @param folioNum
	 * @param folioMod
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Document> findDocument(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, Document.RectoVerso folioRectoVerso) throws ApplicationThrowable;
	
	/**
	 * This method returns a list of documents with the given start folio. If <code>alsoTranscribeFolio</code> is true
	 * result list also contains documents where the transcribe folio corresponds to the given filters.<br/>
	 * <strong>NOTE :</strong> <code>folioRectoVerso</code> filter is considered twice: with the given value (only if 
	 * it is not empty) and then with null value.<br/>
	 * Furthermore, the mandatory filters must be valorized (<code>volLetExt</code> can be null) while the others are
	 * not considered if they are empty strings.
	 * 
	 * @param volNum the volume number (mandatory)
	 * @param volLetExt the volume letter extension (mandatory)
	 * @param insertNum the insert number
	 * @param insertLet the insert letter
	 * @param folioNum the folio/transcribe folio number (mandatory)
	 * @param folioMod the folio/transcribe folio extension
	 * @param folioRectoVerso the folio/transcribe folio recto/verso
	 * @param alsoTranscribeFolio if true the documents with the given transcribe folio are added to the result list.
	 * @return a list of {@link Document}
	 * @throws ApplicationThrowable
	 */
	public List<Document> findDocumentsByFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String folioRectoVerso, boolean alsoTranscribeFolio) throws ApplicationThrowable;

	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document findDocumentFromHistory(Integer idUserHistory) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public FactChecks findFactChecksDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param imageId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Image findImage(Integer imageId) throws ApplicationThrowable;

	/**
	 * This method returns last entry {@link org.medici.bia.domain.Document}.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.Document}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document findLastEntryDocument() throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfPeopleLinkedOnDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfTopicsOnDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param epLinkId
	 * @throws ApplicationThrowable
	 * 
	 */
	public EpLink findPersonDocument(Integer entryId, Integer epLinkId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public SynExtract findSynExtractDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public TopicList findTopic(Integer topicId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param eplToLinkId
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public EplToLink findTopicDocument(Integer entryId, Integer eplToLinkId) throws ApplicationThrowable;

	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<EplToLink> findTopicsDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexDocument() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexEpLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexEplToLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public void generateIndexFactChecks() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexSynExtract() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexTopicList() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public boolean ifDocumentAlreadyPresentInMarkedList(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getCategoryHistoryNavigator(Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getDocumentForum(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * This method checks if the documents provided are digitized or not
	 * 
	 * @param documents list of documents
	 * @return map of digitized and not digitized documents
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Map<String, Boolean> getDocumentsDigitizedState(List<Document> documents) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param document
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(Document document) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(Integer idUserHistory, Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param historyLog
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(UserHistory historyLog) throws ApplicationThrowable;

	/**
	 * Extracts all months available.
	 *  
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.Month}
	 * object
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Month> getMonths() throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<TopicList> getTopicsList() throws ApplicationThrowable;
	
	/**
	 * This methods determines if a volume has inserts with the insert number provided (with or without insert extension).
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @return true if the volume has one or more inserts with the insert number provided 
	 * @throws ApplicationThrowable
	 */
	public Boolean hasCandidateInsert(Integer volNum, String volLetExt, String insertNum) throws ApplicationThrowable;
	
	/**
	 * This methods determines if the specified volume has the specified insert (with number and extension).
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @return true if the volume has the specified insert (with number and extension)
	 * @throws ApplicationThrowable
	 */
	public Boolean hasInsert(Integer volNum, String volLetExt, String insertNum, String insertLet) throws ApplicationThrowable;
	
	/**
	 * This method determines if the specified volume has inserts.
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @return true if the specified volume has inserts, false otherwise
	 * @throws ApplicationThrowable
	 */
	public boolean hasInserts(Integer volNum, String volLetExt) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void optimizeIndexDocument() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param place
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchLinkedDocumentsTopic(String place, String topic, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<People> searchPersonLinkableToDocument(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Place> searchPlaceLinkableToTopicDocument(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<TopicList> searchTopicLinkableToDocument(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param topic
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchTopicsRelatedDocument(Integer topicId, Integer placeAllId, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param entryId
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchVettingHistoryDocument(Integer entryId, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method mark a {@link org.medici.bia.domain.Document} as available.
	 * 
	 * @param entryId 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document undeleteDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param fromDate
	 * @throws ApplicationThrowable
	 */
	public void updateIndexDocument(Date fromDate) throws ApplicationThrowable;

	/**
	 * 
	 * @param fromDate
	 * @throws ApplicationThrowable
	 */
	public void updateIndexTopicList(Date fromDate) throws ApplicationThrowable;
}
