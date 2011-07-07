/*
 * GeoBaseServiceImpl.java
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

import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.AdvancedSearch;
import org.medici.docsources.common.search.SimpleSearch;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.placetype.PlaceTypeDAO;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is the default implementation of service responsible for every 
 * action on Place.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class GeoBaseServiceImpl implements GeoBaseService {
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private PlaceTypeDAO placeTypeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page advancedSearchPlaces(AdvancedSearch advancedSearchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getPlaceDAO().advancedSearchPlaces(advancedSearchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findLastEntryPlace() throws ApplicationThrowable {
		try {
			return getPlaceDAO().findLastEntryPlace();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findPlace(Integer placeId) throws ApplicationThrowable {
		try {
			return getPlaceDAO().find(placeId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<PlaceType> findPlaceTypes() throws ApplicationThrowable {
		try {
			return getPlaceTypeDAO().findPlaceTypes();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexPlace() throws ApplicationThrowable {
		try {
			getPlaceDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexPlaceType() throws ApplicationThrowable {
		try {
			getPlaceTypeDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
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
	public List<Place> searchBornPlace(String query) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchBornPlace(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Place> searchDeathPlace(String query) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchDeathPlace(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Place> searchPlaces(String text) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchPlaces(text);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Place> searchRecipientsPlace(String query) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchRecipientsPlace(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Place> searchSendersPlace(String query) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchSendersPlace(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	/**
	 * @param placeTypeDAO the placeTypeDAO to set
	 */
	public void setPlaceTypeDAO(PlaceTypeDAO placeTypeDAO) {
		this.placeTypeDAO = placeTypeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page simpleSearchPlaces(SimpleSearch simpleSearchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getPlaceDAO().simpleSearchPlaces(simpleSearchContainer, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

}
