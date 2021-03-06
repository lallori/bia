/*
 * CommunityService.java
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
package org.medici.bia.service.digitization;

import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.domain.Digitization;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.SerieList;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface DigitizationService {

	/**
	 * 
	 * @param id
	 * @throws ApplicationThrowable
	 */
	public Digitization activationOrDeactivationVolume(Digitization digitization) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param schedone
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone addNewSchedone(Schedone schedone) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param schedone
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone editDetailsSchedone(Schedone schedone) throws ApplicationThrowable;

	/**
	 * 
	 * @param schedone
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone editJpegImagesSchedone(Schedone schedone) throws ApplicationThrowable;

	/**
	 * 
	 * @param schedone
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone editPdfImagesSchedone(Schedone schedone) throws ApplicationThrowable;

	/**
	 * 
	 * @param schedone
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone editTiffImagesSchedone(Schedone schedone) throws ApplicationThrowable;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Digitization findActivateVolumeDigitized(Integer id) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param schedoneId
	 * @return
	 */
	public Schedone findSchedone(Integer schedoneId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Schedone findSchedone(Integer volNum, String volLetExt) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volNum
	 * @param volNumBetween
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<String, Schedone> findSchedoniMapByVolume(Integer volNum, Integer volNumBetween) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volNums
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<String, Boolean> findVolumesDigitizedBySchedoni(List<Integer> volNums, List<String> volLetExts) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @param volNum
	 * @param volNumBetween
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchActiveVolumes(Integer volNum, Integer volNumBetween, Boolean activated, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param activated
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchAllActiveVolumes(Boolean activated, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchSchedones(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
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
	 * @param volNum
	 * @param volLetExt
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Volume searchVolume(Integer volNum, String volLetExt) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchVolumes(Integer volNum, Integer volNumBetween, PaginationFilter paginationFilter) throws ApplicationThrowable;
}