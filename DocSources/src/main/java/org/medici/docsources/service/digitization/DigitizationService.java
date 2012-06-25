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
package org.medici.docsources.service.digitization;

import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface DigitizationService {

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
	 * @param schedoneId
	 * @return
	 */
	public Schedone findSchedone(Integer schedoneId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volNum
	 * @param volNumBetween
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<Integer, Boolean> findSchedoniMapByVolume(Integer volNum, Integer volNumBetween) throws ApplicationThrowable;
	
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
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchAllActiveVolumes(PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchSchedones(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchVolumes(Integer volNum, Integer volNumBetween, PaginationFilter paginationFilter) throws ApplicationThrowable;
}
