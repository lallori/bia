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
package org.medici.docsources.service.volbase;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.HistoryNavigator;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.VolumeExplorer;
import org.medici.docsources.common.volume.VolumeSummary;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.docsources.domain.Volume} 
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
 * 
 */
public interface VolBaseService {
	/**
	 * This method add a new {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Volume} to be added
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
	 * This method checks if {@link org.medici.docsources.domain.Volume} identified 
	 * by his summaryId has been digitized. Control is implemented by searching  
	 * {@link org.medici.docsources.domain.Image} entity.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return Boolean, true if volume is digitized, otherwise false.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Boolean checkVolumeDigitized(Integer summaryId) throws ApplicationThrowable; 

	/**
	 * This method checks if {@link org.medici.docsources.domain.Volume} identified 
	 * by his volNum and volLetExt has been digitized. Control is implemented by searching  
	 * {@link org.medici.docsources.domain.Image} entity.
	 * 
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @return Boolean, true if volume is digitized, otherwise false.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Boolean checkVolumeDigitized(Integer volNum, String volLetExt) throws ApplicationThrowable;
	
	/**
	 * This method checks if {@link org.medici.docsources.domain.Volume} identified 
	 * by his summaryId has some linked documents.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return Boolean, true if volume has linked documents, otherwise false.
	 * @throws ApplicationThrowable
	 */
	public boolean checkVolumeHasLinkedDocuments(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method will search an existing {@link org.medici.docsources.domain.Volume} 
	 * by his unique identifier.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.docsources.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume compareVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method mark a volume as deleted {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.docsources.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume deleteVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * This method modify context of an existing {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editContextVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method modify correspondents of an existing {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editCorrespondentsVolume(Volume volume) throws ApplicationThrowable;
	

	/**
	 * This method modify description of an existing {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editDescriptionVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method modify details of an existing {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Volume} to be modified
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume editDetailsVolume(Volume volume) throws ApplicationThrowable;

	/**
	 * This method last entry {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Volume}
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
	 * This method will search an existing {@link org.medici.docsources.domain.Volume} 
	 * by his unique identifier.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.docsources.domain.Volume}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public Volume findVolume(Integer summaryId) throws ApplicationThrowable;
	
	/**
	 * This method will search an existing {@link org.medici.docsources.domain.Volume} 
	 * by his volNum and volLetExt identifier.
	 * 
	 * @param volNum Volume number identifier
	 * @param volLetExt Volume letter extension identifier.
	 * @return {@link org.medici.docsources.domain.Volume}
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
	 * This method generates lucene index for entity {@link org.medici.docsources.domain.Month}.
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexMonth() throws ApplicationThrowable;

	/**
	 * This method generates lucene index for entity {@link org.medici.docsources.domain.SerieList}.
	 * 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public void generateIndexSerieList() throws ApplicationThrowable;
	
	/**
	 * This method generates lucene index for entity {@link org.medici.docsources.domain.Volume}.
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
	 * 
	 * @param volNums
	 * @param volLetExts
	 * @param folioNums
	 * @param folioMods
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<String, Boolean> getDocumentsDigitizedState(List<Integer> volNums, List<String> volLetExts, List<Integer> folioNums, List<String> folioMods) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param historyId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(Integer historyId) throws ApplicationThrowable;
	
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
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Month}
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
	 * This method searches for existing {@link org.medici.docsources.domain.SerieList}.
	 * 
	 * @param alias Text to search inside description fields of {@link org.medici.docsources.domain.SerieList}
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.SerieList}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public List<SerieList> searchSeriesList(String alias) throws ApplicationThrowable;

	/**
	 * This method searches for existing {@link org.medici.docsources.domain.Volume}
	 * containing input text and return a specific 
	 * {@link org.medici.docsources.common.pagination.Page} of complete reulst 
	 * base on {@link org.medici.docsources.common.pagination.PaginationFilter} input object.
	 * 
	 * @param text Text to search inside {@link org.medici.docsources.domain.Volume}
	 * @param paginationFilter Object that contains information for filtering on
	 * pagination 
	 * @return {@link org.medici.docsources.common.pagination.Page} containing 
	 * partial result
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchVolumes(String text, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method remove delete mark on a {@link org.medici.docsources.domain.Volume}.
	 * 
	 * @param summaryId Unique Volume Identifier
	 * @return {@link org.medici.docsources.domain.Volume}
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
