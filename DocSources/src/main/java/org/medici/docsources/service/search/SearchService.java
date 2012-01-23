/*
 * SearchService.java
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
package org.medici.docsources.service.search;

import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.domain.TopicList;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on search module.
 * It defines every business methods needed to execute simple and advanced search.
 *  With this service, you can :<br>
 * - search documents<br>
 * - search people<br>
 * - search place<br>
 * - search volume<br>
 * - manage advanced search filter<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface SearchService {
	
	/**
	 * 
	 * @param searchFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public SearchFilter addSearchFilter(SearchFilter searchFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 */
	public List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<PlaceType> getPlaceTypes() throws ApplicationThrowable;

	/**
	 * 
	 * @param searchFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public SearchFilter getSearchFilter(SearchFilter searchFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<TopicList> getTopicsList() throws ApplicationThrowable;

	/**
	 * 
	 * @param idSearchFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public SearchFilter getUserSearchFilter(Integer idSearchFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public List<SearchFilter> getUserSearchFilters() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page getUserSearchFilters(PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param paginationFilter
	 * @param searchType
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page getUserSearchFilters(PaginationFilter paginationFilter, SearchType searchType) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchFilter
	 * @throws ApplicationThrowable
	 */
	public void replaceSearchFilter(SearchFilter searchFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchAdvancedDocuments(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchAdvancedPeople(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchAdvancedPlaces(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchAdvancedVolumes(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchPeople(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchPeople(String query) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchPlaces(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchPlaces(String query) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchTitleOrOccupation(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public List<TopicList> searchTopics(String query, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchVolumes(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;
}
