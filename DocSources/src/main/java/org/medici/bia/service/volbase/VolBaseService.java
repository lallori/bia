/*
 * VolBaseService.java
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
package org.medici.bia.service.volbase;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.volume.VolumeSummary;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.SerieList;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.bia.domain.Volume} 
 * object.<br>
 * It defines every business methods needed to work on volumes.
 * With this service, you can :<br>
 * - add a new volume<br>
 * - modify an existing volume<br> 
 * - search a volume by is summaryId, volNum and volLetExt (this last is optional ndr)<br>
 * - execute complex search on volumes<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public interface VolBaseService {
	/**
	 * This method add a new {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.bia.domain.Volume} to be added
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume addNewVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * 
	 * @param volume
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum addNewVolumeForum(Volume volume) throws ApplicationThrowable;

	/**
	 * This method checks if {@link org.medici.bia.domain.Volume} identified 
	 * by his summaryId has been digitized. Control is implemented by searching  
	 * {@link org.medici.bia.domain.Image} entity.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return Boolean, true if volume is digitized, otherwise false.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Boolean checkVolumeDigitized(Integer summaryId) throws ApplicationThrowable; 

	/**
	 * This method checks if {@link org.medici.bia.domain.Volume} identified 
	 * by his volNum and volLetExt has been digitized. Control is implemented by searching  
	 * {@link org.medici.bia.domain.Image} entity.
	 * 
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @return Boolean, true if volume is digitized, otherwise false.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Boolean checkVolumeDigitized(Integer volNum, String volLetExt) throws ApplicationThrowable;
	
	/**
	 * This method checks which hierarchical configuration of folio number, folio extension and recto/verso are
	 * correct for the volume and insert provided. It returns a set of keys associated to the folio existance status.<br/>
	 * The returned key-set could contain the following keys (in a hierarchical order):
	 * <ol>
	 * <li><b>folioNumOk</b> - folio number is OK</li>
	 * <li><b>folioModOk</b> - folio extension is OK</li>
	 * <li><b>folioRVOk</b> - folio recto/verso is OK</li>
	 * </ol>
	 * With the returned key-set we intend to indicate the maximum hierarchical configuration accepted.
	 * So it is not possible to have returned the 'folioModOk' or 'folioRVOk' key without the 'folioNumOk' key (due to the
	 * hierarchical relevance of every key).<br/>
	 * Furthermore, for the folio details provided if null value is provided the check consider null value in database correspondence,
	 * while if empty value is provided that value is not considered.
	 * 
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @param insertNum Insert Number
	 * @param insertLet Insert Extension
	 * @param folioNum Folio Number
	 * @param folioMod Folio Extension
	 * @param folioRV Folio Recto or Verso
	 * @return maximum hierarchical configuration accepted as a key-set.
	 * @throws ApplicationThrowable
	 */
	public Set<String> checkChartaExistence(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String folioRV) throws ApplicationThrowable;
	
	/**
	 * This method checks if {@link org.medici.bia.domain.Volume} identified 
	 * by its volNum and volLetExt has an insert, identified by its insertNum and insertLet, with a
	 * folio identified by its folioNum, folioMod. Control is implemented by searching  
	 * {@link org.medici.bia.domain.Image} entity.<br />
	 * Note that this method does not consider the recto/verso information.
	 * 
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @param insertNum Insert Number
	 * @param insertLet Insert Extension
	 * @param folioNum Folio Number
	 * @param folioMod Folio Extension
	 * @param type the folio type (charta, rubricario, ...)
	 * @return Boolean, true if volume is digitized, otherwise false.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Boolean checkFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String type) throws ApplicationThrowable;
	
	/**
	 * This method checks if {@link org.medici.bia.domain.Volume} identified 
	 * by his summaryId has some linked documents.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return Boolean, true if volume has linked documents, otherwise false.
	 * @throws ApplicationThrowable
	 */
	public boolean checkVolumeHasLinkedDocuments(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method will search an existing {@link org.medici.bia.domain.Volume} 
	 * by his unique identifier.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.bia.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume compareVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method mark a volume as deleted {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.bia.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume deleteVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method modify context of an existing {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.bia.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editContextVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method modify correspondents of an existing {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.bia.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editCorrespondentsVolume(Volume volume) throws ApplicationThrowable;
	

	/**
	 * This method modify description of an existing {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.bia.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editDescriptionVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method modify details of an existing {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.bia.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editDetailsVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method last entry {@link org.medici.bia.domain.Volume}.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume findLastEntryVolume() throws ApplicationThrowable;

	/**
	 * This method find new digitized volumes.
	 * 
	 * @return List of Unique Volume Identifier.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public List<Integer> findNewDigitizedVolumes() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone findSchedone(Integer volNum, String volLetExt) throws ApplicationThrowable;

	/**
	 * This method will search an existing {@link org.medici.bia.domain.Volume} 
	 * by his unique identifier.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.bia.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume findVolume(Integer summaryId) throws ApplicationThrowable;
	
	/**
	 * This method will search an existing {@link org.medici.bia.domain.Volume} 
	 * by his volNum and volLetExt identifier.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @return {@link org.medici.bia.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume findVolume(Integer volNum, String volLetExt) throws ApplicationThrowable;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findVolumeDocumentsRelated(Integer summaryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Volume findVolumeFromHistory(Integer idUserHistory) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public VolumeSummary findVolumeSummary(Volume volume) throws ApplicationThrowable;

	/**
	 * This method generates lucene index for entity {@link org.medici.bia.domain.Month}.
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexMonth() throws ApplicationThrowable;

	/**
	 * This method generates lucene index for entity {@link org.medici.bia.domain.SerieList}.
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexSerieList() throws ApplicationThrowable;
	
	/**
	 * This method generates lucene index for entity {@link org.medici.bia.domain.Volume}.
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexVolume() throws ApplicationThrowable;

	/**
	 * 
	 * @param volume
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getCategoryHistoryNavigator(Volume volume) throws ApplicationThrowable;

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
	 * @param historyId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(Integer historyId, Volume volume) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volume
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(Volume volume) throws ApplicationThrowable;

	
	/**
	 * This method extracts all months available.
	 *  
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.Month}
	 * object
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Month> getMonths() throws ApplicationThrowable;
	
	
	/**
	 * 
	 * @param volumeExplorer
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public VolumeExplorer getVolumeExplorer(VolumeExplorer volumeExplorer) throws ApplicationThrowable;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getVolumeForum(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method searches if the volumes are digitized
	 * 
	 * @param summaries
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Map<String, Boolean> getVolumesDigitizedState(List<Integer> volNums, List<String> volLetExts) throws ApplicationThrowable;
	
	/**
	 *  This methods determines if a volume has inserts with the insert number provided (with or without insert extension).
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @return true if the volume has one or more inserts with the insert number provided 
	 * @throws ApplicationThrowable
	 */
	public boolean hasCandidateInsert(Integer volNum, String volLetExt, String insNum) throws ApplicationThrowable;
	
	/**
	 * This method checks if the volume provided contains the insert provided.
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param insertNum
	 * @param insertLet
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean hasInsert(Integer volNum, String volLetExt, String insertNum, String insertLet) throws ApplicationThrowable;
	
	/**
	 * This method checks if a volume contains inserts or not.
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Boolean hasInserts(Integer volNum, String volLetExt) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public boolean ifVolumeAlreadyPresentInMarkedList(Integer summaryId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void optimizeIndexVolume() throws ApplicationThrowable;

	/**
	 * 
	 * @param volumeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchDocumentsRelated(String volumeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method searches for existing {@link org.medici.bia.domain.SerieList}.
	 * 
	 * @param alias Text to search inside description fields of {@link org.medici.bia.domain.SerieList}
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.SerieList}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public List<SerieList> searchSeriesList(String alias) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param summaryId
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchVettingHistoryVolume(Integer summaryId, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method searches for existing {@link org.medici.bia.domain.Volume}
	 * containing input text and return a specific 
	 * {@link org.medici.bia.common.pagination.Page} of complete reulst 
	 * base on {@link org.medici.bia.common.pagination.PaginationFilter} input object.
	 * 
	 * @param text Text to search inside {@link org.medici.bia.domain.Volume}
	 * @param paginationFilter Object that contains information for filtering on
	 * pagination 
	 * @return {@link org.medici.bia.common.pagination.Page} containing 
	 * partial result
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchVolumes(String text, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method remove delete mark on a {@link org.medici.bia.domain.Volume}.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.bia.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume undeleteVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param fromDate
	 * @throws ApplicationThrowable
	 */
	public void updateIndexVolume(Date fromDate) throws ApplicationThrowable;

	/**
	 * This method set digitized information to true on a list of volumes.
	 * 
	 * @param summaryIds List of Unique Volume Identifier.
	 * @return {@link java.lang.Integer} Number of rows updated
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Integer updateNewDigitizedVolume(List<Integer> summaryIds) throws ApplicationThrowable;
}
