/*
 * SearchServiceServiceImpl.java
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

import java.util.Date;
import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.placetype.PlaceTypeDAO;
import org.medici.docsources.dao.searchfilter.SearchFilterDAO;
import org.medici.docsources.dao.titleoccslist.TitleOccsListDAO;
import org.medici.docsources.dao.topicslist.TopicsListDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.domain.TopicList;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * This class is the default implementation of service responsible for every 
 * action on document.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private DocumentDAO documentDAO;

	@Autowired
	private MonthDAO monthDAO;

	@Autowired
	private PeopleDAO peopleDAO;

	@Autowired
	private PlaceDAO placeDAO;
	
	@Autowired
	private PlaceTypeDAO placeTypeDAO;

	@Autowired
	private SearchFilterDAO searchFilterDAO;
	
	@Autowired
	private TitleOccsListDAO titleOccsListDAO;

	@Autowired
	private TopicsListDAO topicsListDAO;

	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SearchFilter addSearchFilter(SearchFilter searchFilter) throws ApplicationThrowable {
		try {
			// we need to perform an advanced search to obtain total number result
			Page page = null;
			if (searchFilter.getSearchType().equals(SearchType.DOCUMENT)) {
				page = this.searchDocuments(searchFilter.getFilterData(), new PaginationFilter(1, 1));
			} else if (searchFilter.getSearchType().equals(SearchType.PEOPLE)) {
				page = this.searchPeople(searchFilter.getFilterData(), new PaginationFilter(1, 1));
			} else if (searchFilter.getSearchType().equals(SearchType.PLACE)) {
				page = this.searchPlaces(searchFilter.getFilterData(), new PaginationFilter(1, 1));
			} else {
				page = this.searchVolumes(searchFilter.getFilterData(), new PaginationFilter(1, 1));
			}
			searchFilter.setId(null);
			searchFilter.setDateCreated(new Date());
			searchFilter.setDateUpdated(new Date());
			searchFilter.setTotalResult(page.getTotal());
			searchFilter.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
			getSearchFilterDAO().persist(searchFilter);

			return searchFilter;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	/**
	 * @return the monthDAO
	 */
	public MonthDAO getMonthDAO() {
		return monthDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Month> getMonths() throws ApplicationThrowable {
		try {
			List<Month> months = getMonthDAO().getAllMonths();
			
			months.add(0, new Month(null, ""));
			
			return months;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}

	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
	}
	
	/**
	 * @param placeTypeDAO the placeTypeDAO to set
	 */
	public void setPlaceTypeDAO(PlaceTypeDAO placeTypeDAO) {
		this.placeTypeDAO = placeTypeDAO;
	}

	/**
	 * @return the placeTypeDAO
	 */
	public PlaceTypeDAO getPlaceTypeDAO() {
		return placeTypeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PlaceType> getPlaceTypes() throws ApplicationThrowable {
		try{
			List<PlaceType> placeTypes = getPlaceTypeDAO().findPlaceTypes();
			
			return placeTypes;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SearchFilter getSearchFilter(SearchFilter searchFilter) throws ApplicationThrowable {
		try {
			return getSearchFilterDAO().findUserSearchFilter(searchFilter.getUsername(), searchFilter.getId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the searchFilterDAO
	 */
	public SearchFilterDAO getSearchFilterDAO() {
		return searchFilterDAO;
	}
	
	@Override
	public List<TopicList> getTopicsList() throws ApplicationThrowable {
		try{
			List<TopicList> topicsList = getTopicsListDAO().findTopicsList();
			
			return topicsList;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * @return the titleOccsListDAO
	 */
	public TitleOccsListDAO getTitleOccsListDAO() {
		return titleOccsListDAO;
	}

	/**
	 * @return the topicsListDAO
	 */
	public TopicsListDAO getTopicsListDAO() {
		return topicsListDAO;
	}

	/**
	 *{@inheritDoc} 
	 */
	@Override
	public SearchFilter getUserSearchFilter(Integer idSearchFilter) throws ApplicationThrowable {
		try {
			return getSearchFilterDAO().findUserSearchFilter(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(), idSearchFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SearchFilter> getUserSearchFilters() throws ApplicationThrowable {
		try {
			return getSearchFilterDAO().findUserSearchFilters(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getUserSearchFilters(PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getSearchFilterDAO().findUserSearchFilters(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(), paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * 
	 */
	@Override
	public void replaceSearchFilter(SearchFilter searchFilter) throws ApplicationThrowable {
		try {
			SearchFilter searchFilterToUpdate = getSearchFilterDAO().findUserSearchFilter(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(), searchFilter.getId());

			searchFilterToUpdate.setDateUpdated(new Date());
			searchFilterToUpdate.setSearchType(searchFilter.getSearchType());
			searchFilterToUpdate.setFilterData(searchFilter.getFilterData());

			// we need to perform an advanced search to obtain total number result
			Page page = null;
			if (searchFilterToUpdate.getSearchType().equals(SearchType.DOCUMENT)) {
				page = this.searchDocuments(searchFilterToUpdate.getFilterData(), new PaginationFilter(1, 1));
			} else if (searchFilterToUpdate.getSearchType().equals(SearchType.PEOPLE)) {
				page = this.searchPeople(searchFilterToUpdate.getFilterData(), new PaginationFilter(1, 1));
			} else if (searchFilterToUpdate.getSearchType().equals(SearchType.PLACE)) {
				page = this.searchPlaces(searchFilterToUpdate.getFilterData(), new PaginationFilter(1, 1));
			} else {
				page = this.searchVolumes(searchFilterToUpdate.getFilterData(), new PaginationFilter(1, 1));
			}
			searchFilterToUpdate.setTotalResult(page.getTotal());
			getSearchFilterDAO().merge(searchFilterToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getDocumentDAO().searchDocuments(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchPeople(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchPeople(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchPlaces(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchPlaces(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	@Override
	public Page searchTitleOrOccupation(Search searchContainer,	PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getTitleOccsListDAO().searchTitleOrOccupation(searchContainer, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTopics(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getTopicsListDAO().searchTopics(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVolumes(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getVolumeDAO().searchVolumes(searchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	/**
	 * @param searchFilterDAO the searchFilterDAO to set
	 */
	public void setSearchFilterDAO(SearchFilterDAO searchFilterDAO) {
		this.searchFilterDAO = searchFilterDAO;
	}
	
	/**
	 * @param titleOccsListDAO the titleOccsListDAO to set
	 */
	public void setTitleOccsListDAO(TitleOccsListDAO titleOccsListDAO) {
		this.titleOccsListDAO = titleOccsListDAO;
	}

	/**
	 * @param topicsListDAO the topicsListDAO to set
	 */
	public void setTopicsListDAO(TopicsListDAO topicsListDAO) {
		this.topicsListDAO = topicsListDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
}
