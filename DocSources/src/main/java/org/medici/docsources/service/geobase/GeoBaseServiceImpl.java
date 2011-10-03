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

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.placeexternallinks.PlaceExternalLinksDAO;
import org.medici.docsources.dao.placegeographiccoordinates.PlaceGeographicCoordinatesDAO;
import org.medici.docsources.dao.placetype.PlaceTypeDAO;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceExternalLinks;
import org.medici.docsources.domain.PlaceGeographicCoordinates;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	@Autowired
	private PlaceGeographicCoordinatesDAO placeGeographicCoordinatesDAO;
	@Autowired
	private PlaceExternalLinksDAO placeExternalLinksDAO;


	/**
	 * @return the placeExternalLinksDAO
	 */
	public PlaceExternalLinksDAO getPlaceExternalLinksDAO() {
		return placeExternalLinksDAO;
	}

	/**
	 * @param placeExternalLinksDAO the placeExternalLinksDAO to set
	 */
	public void setPlaceExternalLinksDAO(PlaceExternalLinksDAO placeExternalLinksDAO) {
		this.placeExternalLinksDAO = placeExternalLinksDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place addNewPlace(Place place) throws ApplicationThrowable {
		try{
			place.setPlaceAllId(null);
			place.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			place.setDateEntered(new Date());
			place.setAddlRes(false);
			if(place.getParentPlace() != null){
				place.setParentPlace(getPlaceDAO().find(place.getParentPlace().getPlaceAllId()));
				place.setParentType(place.getParentPlace().getPlType());
			}
			if(place.getParentPlace().getParentPlace() != null){			
				place.setgParent(place.getParentPlace().getParentPlace().getPlaceName());
				place.setGpType(place.getParentPlace().getParentPlace().getPlType());
			}
			if(place.getParentPlace().getParentPlace().getParentPlace() != null){
				place.setGgp(place.getParentPlace().getParentPlace().getParentPlace().getPlaceName());
				place.setGgpType(place.getParentPlace().getParentPlace().getParentPlace().getPlType());
			}
			if(place.getParentPlace().getParentPlace().getParentPlace().getParentPlace() != null){
				place.setGp2(place.getParentPlace().getParentPlace().getParentPlace().getParentPlace().getPlaceName());
				place.setGp2Ttype(place.getParentPlace().getParentPlace().getParentPlace().getParentPlace().getPlType());
			}
			place.setPlParentTermId(place.getParentPlace().getPlaceNameId());
			place.setPlParentSubjectId(place.getParentPlace().getGeogKey());
				
			getPlaceDAO().persist(place);
			return place;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place addNewPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates)throws ApplicationThrowable {
		try{
			placeGeographicCoordinates.setId(null);
			placeGeographicCoordinates.setPlace(getPlaceDAO().find(placeGeographicCoordinates.getPlace().getPlaceAllId()));
			placeGeographicCoordinates.setId(placeGeographicCoordinates.getPlace().getPlaceAllId());
			
			getPlaceGeographicCoordinatesDAO().persist(placeGeographicCoordinates);
			
			
			getPlaceDAO().refresh(placeGeographicCoordinates.getPlace());
			
			return placeGeographicCoordinates.getPlace();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place addNewPlaceExternalLinks(PlaceExternalLinks placeExternalLinks)
			throws ApplicationThrowable {
		try{
			placeExternalLinks.setPlaceExternalLinksId(null);
			placeExternalLinks.setPlace(getPlaceDAO().find(placeExternalLinks.getPlace().getPlaceAllId()));
			getPlaceExternalLinksDAO().persist(placeExternalLinks);
			
			getPlaceDAO().refresh(placeExternalLinks.getPlace());
			
			return placeExternalLinks.getPlace();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePlaceExternalLinks(PlaceExternalLinks placeExternalLinks)
			throws ApplicationThrowable {
		try{
			PlaceExternalLinks placeExternalLinksToDelete = getPlaceExternalLinksDAO().find(placeExternalLinks.getPlace().getPlaceAllId(), placeExternalLinks.getPlaceExternalLinksId());
			getPlaceExternalLinksDAO().remove(placeExternalLinksToDelete);
			placeExternalLinksToDelete.getPlace().setPlaceExternalLinks(null);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place editDetailsPlace(Place place) throws ApplicationThrowable {
		try{
			Place placeToUpdate = getPlaceDAO().find(place.getPlaceAllId());
			placeToUpdate.setPlacesMemo(place.getPlacesMemo());
			placeToUpdate.setGeogKey(place.getGeogKey());
			placeToUpdate.setPlaceNameId(place.getPlaceNameId());
			placeToUpdate.setPlaceName(place.getPlaceName());
			placeToUpdate.setTermAccent(place.getTermAccent());
			placeToUpdate.setPlType(place.getPlType());
			placeToUpdate.setAddlRes(false);
			if(place.getParentPlace() != null){
				placeToUpdate.setParentPlace(getPlaceDAO().find(place.getParentPlace().getPlaceAllId()));
				placeToUpdate.setParentType(placeToUpdate.getParentPlace().getPlType());
			}
			if(placeToUpdate.getParentPlace().getParentPlace() != null){			
				placeToUpdate.setgParent(placeToUpdate.getParentPlace().getParentPlace().getPlaceName());
				placeToUpdate.setGpType(placeToUpdate.getParentPlace().getParentPlace().getPlType());
			}
			if(placeToUpdate.getParentPlace().getParentPlace().getParentPlace() != null){
				placeToUpdate.setGgp(placeToUpdate.getParentPlace().getParentPlace().getParentPlace().getPlaceName());
				placeToUpdate.setGgpType(placeToUpdate.getParentPlace().getParentPlace().getParentPlace().getPlType());
			}
			if(placeToUpdate.getParentPlace().getParentPlace().getParentPlace().getParentPlace() != null){
				placeToUpdate.setGp2(placeToUpdate.getParentPlace().getParentPlace().getParentPlace().getParentPlace().getPlaceName());
				placeToUpdate.setGp2Ttype(placeToUpdate.getParentPlace().getParentPlace().getParentPlace().getParentPlace().getPlType());
			}
			placeToUpdate.setPlParentTermId(placeToUpdate.getParentPlace().getPlaceNameId());
			placeToUpdate.setPlParentSubjectId(placeToUpdate.getParentPlace().getGeogKey());
			
			getPlaceDAO().merge(placeToUpdate);
			return placeToUpdate;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place editPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates)throws ApplicationThrowable {
		try{
			PlaceGeographicCoordinates placeGeographicCoordinatesToUpdate = getPlaceGeographicCoordinatesDAO().findByPlaceAllId(placeGeographicCoordinates.getId());
			BeanUtils.copyProperties(placeGeographicCoordinatesToUpdate, placeGeographicCoordinates);
			placeGeographicCoordinatesToUpdate.setId(placeGeographicCoordinates.getId());
			getPlaceGeographicCoordinatesDAO().merge(placeGeographicCoordinatesToUpdate);
			
			return placeGeographicCoordinatesToUpdate.getPlace();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place editPlaceExternalLinks(PlaceExternalLinks placeExternalLinks)
			throws ApplicationThrowable {
		try{
			PlaceExternalLinks placeExternalLinksToUpdate = getPlaceExternalLinksDAO().find(placeExternalLinks.getPlaceExternalLinksId());
			placeExternalLinksToUpdate.setExternalLink(placeExternalLinks.getExternalLink());
			getPlaceExternalLinksDAO().merge(placeExternalLinksToUpdate);
			getPlaceDAO().refresh(placeExternalLinksToUpdate.getPlace());
			
			return placeExternalLinksToUpdate.getPlace();
		}catch(Throwable th){
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
	public PlaceGeographicCoordinates findPlaceGeographicCoordinates(Integer placeAllId) throws ApplicationThrowable {
		try{
			return getPlaceGeographicCoordinatesDAO().findByPlaceAllId(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public PlaceExternalLinks findPlaceExternalLinks(Integer placeAllId, Integer placeExternalLinksId) throws ApplicationThrowable {
		try{
			return getPlaceExternalLinksDAO().find(placeAllId, placeExternalLinksId);
		}catch(Throwable th){
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
	public List<Place> searchPlaceParent(String query) throws ApplicationThrowable{
		try{
			return getPlaceDAO().searchPlaceParent(query);
		}catch (Throwable th){
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
	 * @return the placeGeographicCoordinatesDAO
	 */
	public PlaceGeographicCoordinatesDAO getPlaceGeographicCoordinatesDAO() {
		return placeGeographicCoordinatesDAO;
	}

	/**
	 * @param placeGeographicCoordinatesDAO the placeGeographicCoordinatesDAO to set
	 */
	public void setPlaceGeographicCoordinatesDAO(
			PlaceGeographicCoordinatesDAO placeGeographicCoordinatesDAO) {
		this.placeGeographicCoordinatesDAO = placeGeographicCoordinatesDAO;
	}

}
