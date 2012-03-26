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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.HistoryNavigator;
import org.medici.docsources.common.pagination.Page;
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
import org.medici.docsources.domain.UserHistory;
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
	 * Adds a new {@link org.medici.docsources.domain.Document} entry.
	 * 
	 * @param inputDocument the {@link org.medici.docsources.domain.Document} to be added.
	 * @return {@link org.medici.docsources.domain.Document} entity.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Document addNewDocument(Document inputDocument) throws ApplicationThrowable;

	/**
	 * Adds a new {@link org.medici.docsources.domain.SynExtract} entry.
	 * 
	 * @param synExtract the {@link org.medici.docsources.domain.SynExtract} to be added.
	 * @return {@link org.medici.docsources.domain.SynExtract} entity.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document addNewExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable;

	/**
	 * Links a {@link org.medici.docsources.domain.FactChecks} entry to an existing 
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param factChecks the {@link org.medici.docsources.domain.FactChecks} to be linked.
	 * @return {@link org.medici.docsources.domain.Document} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	 public Document addNewFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable;
	
	/**
	 * Links a {@link org.medici.docsources.domain.Person} entry to an existing 
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param epLink the {@link org.medici.docsources.domain.EpLink} to be linked.
	 * @return {@link org.medici.docsources.domain.Document} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document addNewPersonDocument(EpLink epLink) throws ApplicationThrowable;

	/**
	 * Links a {@link org.medici.docsources.domain.Topic} entry and a {@link org.medici.docsources.domain.Place} 
	 * to an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param eplToLink the {@link org.medici.docsources.domain.EpLink} to be linked.
	 * @return {@link org.medici.docsources.domain.Document} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document addNewTopicDocument(EplToLink eplToLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param folioNum
	 * @param folioMod
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean checkDocumentDigitized(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws ApplicationThrowable;

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
	 * This method will return a new {@link org.medici.docsources.domain.Document} constructed 
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
	 * This method mark a {@link org.medici.docsources.domain.Document} as deleted .
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
	 * This method modify details of an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document {@link org.medici.docsources.domain.Document} to be modified
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
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param synExtract
	 *            {@link org.medici.docsources.domain.SynExtract} to be modified
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
	 * {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document {@link org.medici.docsources.domain.Document} to be modified
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
	 * {@link org.medici.docsources.domain.Document}.
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
	public Document findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws ApplicationThrowable;


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
	 * This method returns last entry {@link org.medici.docsources.domain.Document}.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Document}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Document findLastEntryDocument() throws ApplicationThrowable;

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
	 * This method searches if the documents are digitized
	 * 
	 * @param volNums
	 * @param volLetExts
	 * @param folioNums
	 * @param folioMods
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Map<String, Boolean> getDocumentsDigitizedState(List<Integer> volNums, List<String> volLetExts, List<Integer> folioNums, List<String> folioMods) throws ApplicationThrowable;

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
	public HistoryNavigator getHistoryNavigator(Integer idUserHistory) throws ApplicationThrowable;
	
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
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Month}
	 * object
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void optimizeIndexDocument() throws ApplicationThrowable;

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
	public Page searchTopicsRelatedDocument(String topic, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method mark a {@link org.medici.docsources.domain.Document} as available.
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
