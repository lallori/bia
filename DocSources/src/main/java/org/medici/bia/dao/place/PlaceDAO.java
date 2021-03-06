/*
 * PlaceDAO.java
 * 
 * Developed by Medici Archive Project (2010-2012).
 * 
 * This file is part of DocSources.
 * 
 * DocSources is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General License for more details.
 * 
 * You should have received a copy of the GNU General License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General License.
 */
package org.medici.bia.dao.place;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Place;

/**
 * Place Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface PlaceDAO extends Dao<Integer, Place> {

	/**
	 * 
	 * @param inputDate
	 * @return
	 * @throws PersistenceException
	 */
	Long countPlaceCreatedAfterDate(Date inputDate) throws PersistenceException;
	
	/**
	 * This method returns a list of places with the same geogKey
	 * 
	 * @return List of {@link org.medici.bia.domain.Place}
	 * @throws PersistenceException
	 */
	List<Place> findByGeogKey(Integer geogKey) throws PersistenceException;
	
	/**
	 * This method returns last entry {@link org.medici.bia.domain.Place}created on database.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.Place}
	 * @throws PersistenceException
	 */
	Place findLastEntryPlace() throws PersistenceException;
	
	/**
	 * This method find a new geogKey for a new Place.
	 * 
	 * @param plSource
	 * @return
	 * @throws PersistenceException
	 */
	Place findNewGeogKey(String plSource) throws PersistenceException;

	/**
	 * This method returns the principal Place
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Place findPrinicipalPlace(Integer geogKey) throws PersistenceException;

	/**
	 * This method searches for born places which could be related to a person which contains 
	 * a text parameter (String query).
	 * 
	 * @param query Text to be searched
	 * @return A List<Place> that could be related to a person.
	 * @throws PersistenceException
	 */
	List<Place> searchBornPlace(String query) throws PersistenceException;

	/**
	 * This method searches for death places which could be related to a person which contains 
	 * a text parameter (String query). 
	 * 
	 * @param query Text to be searched
	 * @return A List<Place> that could be related to a person.
	 * @throws PersistenceException
	 */
	List<Place> searchDeathPlace(String query) throws PersistenceException;
	
	/**
	 * This method searches for places which could be related to a document which contains 
	 * a text parameter (String searchText). 
	 * 
	 * @param searchText Text to be searched
	 * @return A List<Place> that could be related to a document.
	 * @throws PersistenceException
	 */
	List<Place> searchPlaceLinkableToTopicDocument(String searchText) throws PersistenceException;
	
	/**
	 * This method searches for parent places which could be relataed to a place which contains
	 * a text parameter (String query).
	 * 
	 * @param query Text to be searched
	 * @return A List<Place> that could be related to a place.
	 * @throws PersistenceException
	 */
	List<Place> searchPlaceParent(String query) throws PersistenceException;

	/**
	 * This method searches places which contains the parameters set in {@link org.medici.bia.common.search}
	 * object and return a result page. 
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchPlaces(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method searches for places which contains a text parameter (String searchText). 
	 * 
	 * @param searchText Text to be searched
	 * @return A List<Place> searched
	 * @throws PersistenceException
	 */
	List<Place> searchPlaces(String searchText) throws PersistenceException;

	/**
	 * This method searches for places which could be related to a recipient which contains 
	 * a text parameter (String searchText). 
	 * 
	 * @param searchText Text to be searched
	 * @return A List<Place> that could be related to a recipient
	 * @throws PersistenceException
	 */
	List<Place> searchRecipientsPlace(String searchText) throws PersistenceException;

	/**
	 * This method searches for places which could be related to a sender which contains 
	 * a text parameter (String searchText). 
	 * 
	 * @param searchText Text to be searched
	 * @return A List<Place> that could be related to a sender
	 * @throws PersistenceException
	 */
	List<Place> searchSendersPlace(String searchText) throws PersistenceException;
}
