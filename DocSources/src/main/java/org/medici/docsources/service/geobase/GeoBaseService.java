/*
 * GeoBaseService.java
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
package org.medici.docsources.service.geobase;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.HistoryNavigator;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceExternalLinks;
import org.medici.docsources.domain.PlaceGeographicCoordinates;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.docsources.domain.Place} 
 * object.<br>
 * It defines every business methods needed to work on places.
 * With this service, you can :<br>
 * - add a new place<br>
 * - modify an existing place<br> 
 * - search a place by his unique id<br>
 * - execute complex search on places<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface GeoBaseService {

	/**
	 * Adds a new {@link org.medici.docsources.domain.Place} entry.
	 * 
	 * @param inputPlace the {@link org.medici.docsources.domain.Place} to be added.
	 * @return {@link org.medici.docsources.domain.Place} entity.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Place addNewPlace(Place inputPlace) throws ApplicationThrowable;
	
	/**
	 * Links a {@link org.medici.docsources.domain.PlaceExternalLinks} entry to an existing {@link org.medici.docsources.domain.Place}.
	 * 
	 * @param placeExternalLinks the {@link org.medici.docsources.domain.PlaceExternalLinks} to be linked.
	 * @return {@link org.medici.docsources.domain.Place} entity. 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Place addNewPlaceExternalLinks(PlaceExternalLinks placeExternalLinks) throws ApplicationThrowable;
	
	/**
	 * Adds a new {@link org.medici.docsources.domain.PlaceGeographicCoordinates} entry to an existing
	 * {@link org.medici.docsources.domain.Place}
	 * 
	 * @param placeGeographicCoordinates the {@link org.medici.docsources.domain.PlaceGeographicCoordinates} to be linked
	 * @return {@link org.medici.docsources.domain.Place} entity.
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Place addNewPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates) throws ApplicationThrowable;

	/**
	 * 
	 * @param peopleId
	 * @return
	 */
	public Place comparePlace(Integer placeId) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Place deletePlace(Integer placeAllId) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeExternalLinks
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public void deletePlaceExternalLinks(PlaceExternalLinks placeExternalLinks) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param place
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Place editDetailsPlace(Place place) throws ApplicationThrowable;
	
	/**
	 * This method modify External Link of an existing
	 * {@link org.medici.docsources.domain.Place}.
	 * 
	 * @param placeExternalLinks
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Place editPlaceExternalLinks(PlaceExternalLinks placeExternalLinks) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeGeographicCoordinates
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Place editPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates) throws ApplicationThrowable;
	
	/**
	 * This method last entry {@link org.medici.docsources.domain.Place}.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Place}
	 * @throws ApplicationThrowable
	 */
	public Place findLastEntryPlace() throws ApplicationThrowable;
	
	/**
	 * This method find a new geogKey for a new Place.
	 * 
	 * @param plSource
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Integer findNewGeogKey(String plSource) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfActiveEndInPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfActiveStartInPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfBirthInPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfDeathInPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfDocumentsInTopicsPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfRecipientDocumentsPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfSenderDocumentsPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfTopicsPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllIds
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<Integer, Long> findNumbersOfDocumentsRelated(List<Integer> placeAllIds) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeAllIds
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<Integer, Long> findNumbersOfPeopleRelated(List<Integer> placeAllIds) throws ApplicationThrowable;
		
	/**
	 * 
	 * @param peopleId
	 * @return
	 */
	public Place findPlace(Integer placeId) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeAllId
	 * @return {@link org.medici.docsources.domain.PlaceExternalLinks}
	 * @throws ApplicationThrowable
	 */
	public PlaceExternalLinks findPlaceExternalLinks(Integer placeAllId, Integer placeExternalLinksId) throws ApplicationThrowable;

	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Place findPlaceFromHistory(Integer idUserHistory) throws ApplicationThrowable;
	
	
	/**
	 * 
	 * @param placeAllId
	 * @return {@link org.medici.docsources.domain.PlaceGeographicCoordinates}
	 * @throws ApplicationThrowable
	 */
	public PlaceGeographicCoordinates findPlaceGeographicCoordinates(Integer placeAllId) throws ApplicationThrowable;
	
	
	/**
	 * 
	 * @param geogKey
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public List<Place> findPlaceNames(Integer geogKey) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<PlaceType> findPlaceTypes() throws ApplicationThrowable;
	
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexPlace() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexPlaceExternalLinks() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexPlaceGeographicCoordinates() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexPlaceType() throws ApplicationThrowable;
	
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
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Object getHistoryNavigator(Integer idUserHistory) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param place
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(Place place) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void optimizeIndexPlace() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchActiveEndPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchActiveStartPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchBirthPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchBornPlace(String query) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchDeathPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchDeathPlace(String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchPlaceParent(String query) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param text
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchPlaces(String text) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchRecipientDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchRecipientsPlace(String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchSenderDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Place> searchSendersPlace(String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchTopicsPlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Place undeletePlace(Integer placeAllId) throws ApplicationThrowable;

	/**
	 * 
	 * @param fromDate
	 * @throws ApplicationThrowable
	 */
	public void updateIndexPlace(Date fromDate) throws ApplicationThrowable;
}
