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
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.epltolink.EplToLinkDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.placeexternallinks.PlaceExternalLinksDAO;
import org.medici.docsources.dao.placegeographiccoordinates.PlaceGeographicCoordinatesDAO;
import org.medici.docsources.dao.placetype.PlaceTypeDAO;
import org.medici.docsources.dao.userhistory.UserHistoryDAO;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceExternalLinks;
import org.medici.docsources.domain.PlaceGeographicCoordinates;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Action;
import org.medici.docsources.domain.UserHistory.Category;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on Place.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class GeoBaseServiceImpl implements GeoBaseService {
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private EplToLinkDAO eplToLinkDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private PlaceExternalLinksDAO placeExternalLinksDAO;
	@Autowired
	private PlaceGeographicCoordinatesDAO placeGeographicCoordinatesDAO;
	@Autowired
	private PlaceTypeDAO placeTypeDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place addNewPlace(Place place) throws ApplicationThrowable {
		try{
			place.setPlaceAllId(null);
			place.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			place.setDateEntered(new Date());
			place.setAddlRes(false);
			place.setLogicalDelete(Boolean.FALSE);
			if(place.getParentPlace() != null){
				place.setParentPlace(getPlaceDAO().find(place.getParentPlace().getPlaceAllId()));
				place.setPlParent(place.getParentPlace().getPlaceName());
				place.setParentType(place.getParentPlace().getPlType());
				place.setPlParentSubjectId(place.getParentPlace().getGeogKey());
				place.setPlParentTermId(place.getParentPlace().getPlaceNameId());
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
			place.setPlaceNameFull(place.getPlaceName() + " / " + place.getPlParent() + " / " + place.getgParent() + " / " + place.getGgp());
			place.setPlNameFullPlType(place.getPlaceName() + " (" + place.getPlType() + ") / " + place.getPlParent() + " (" + place.getParentType() + ") / " + place.getgParent() + " (" + place.getGpType() + ") / " + place.getGgp() + " (" + place.getGgpType() + ") /" + place.getGp2() + " (" + place.getGp2Ttype() + ")");
				
			getPlaceDAO().persist(place);

			getUserHistoryDAO().persist(new UserHistory("Add new place", Action.CREATE, Category.PLACE, place));

			return place;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place addNewPlaceExternalLinks(PlaceExternalLinks placeExternalLinks)
			throws ApplicationThrowable {
		try{
			placeExternalLinks.setPlaceExternalLinksId(null);
			placeExternalLinks.setPlace(getPlaceDAO().find(placeExternalLinks.getPlace().getPlaceAllId()));
			getPlaceExternalLinksDAO().persist(placeExternalLinks);
			
			getPlaceDAO().refresh(placeExternalLinks.getPlace());
			
			getUserHistoryDAO().persist(new UserHistory("Add external link", Action.MODIFY, Category.PLACE, placeExternalLinks.getPlace()));

			return placeExternalLinks.getPlace();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place addNewPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates)throws ApplicationThrowable {
		try{
			placeGeographicCoordinates.setId(null);
			placeGeographicCoordinates.setPlace(getPlaceDAO().find(placeGeographicCoordinates.getPlace().getPlaceAllId()));
			placeGeographicCoordinates.setId(placeGeographicCoordinates.getPlace().getPlaceAllId());
			
			getPlaceGeographicCoordinatesDAO().persist(placeGeographicCoordinates);
			
			
			getPlaceDAO().refresh(placeGeographicCoordinates.getPlace());
			
			getUserHistoryDAO().persist(new UserHistory("Add geographic coordinates", Action.MODIFY, Category.PLACE, placeGeographicCoordinates.getPlace()));

			return placeGeographicCoordinates.getPlace();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place comparePlace(Integer placeId) throws ApplicationThrowable {
		try {
			Place place = getPlaceDAO().find(placeId);

			getUserHistoryDAO().persist(new UserHistory("Compare place", Action.COMPARE, Category.PLACE, place));

			return place;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place deletePlace(Integer placeAllId) throws ApplicationThrowable {
		Place placeToDelete = null;
		try {
			placeToDelete = getPlaceDAO().find(placeAllId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		placeToDelete.setLogicalDelete(Boolean.TRUE);

		try {
			getPlaceDAO().merge(placeToDelete);

			getUserHistoryDAO().persist(new UserHistory("Deleted place", Action.DELETE, Category.PLACE, placeToDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return placeToDelete;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deletePlaceExternalLinks(PlaceExternalLinks placeExternalLinks)
			throws ApplicationThrowable {
		try{
			PlaceExternalLinks placeExternalLinksToDelete = getPlaceExternalLinksDAO().find(placeExternalLinks.getPlace().getPlaceAllId(), placeExternalLinks.getPlaceExternalLinksId());
			getPlaceExternalLinksDAO().remove(placeExternalLinksToDelete);
			placeExternalLinksToDelete.getPlace().setPlaceExternalLinks(null);

			getUserHistoryDAO().persist(new UserHistory("Delete external link ", Action.MODIFY, Category.PLACE, placeExternalLinksToDelete.getPlace()));
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
			placeToUpdate.setPrefFlag(place.getPrefFlag());
			placeToUpdate.setAddlRes(false);
			if(place.getParentPlace() != null){
				placeToUpdate.setParentPlace(getPlaceDAO().find(place.getParentPlace().getPlaceAllId()));
				placeToUpdate.setPlParent(placeToUpdate.getParentPlace().getPlaceName());
				placeToUpdate.setParentType(placeToUpdate.getParentPlace().getPlType());
				place.setPlParentSubjectId(place.getParentPlace().getGeogKey());
				place.setPlParentTermId(place.getParentPlace().getPlaceNameId());
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
			
			placeToUpdate.setPlaceNameFull(placeToUpdate.getPlaceName() + " / " + placeToUpdate.getPlParent() + " / " + placeToUpdate.getgParent() + " / " + placeToUpdate.getGgp());
			placeToUpdate.setPlNameFullPlType(placeToUpdate.getPlaceName() + " (" + placeToUpdate.getPlType() + ") / " + placeToUpdate.getPlParent() + " (" + placeToUpdate.getParentType() + ") / " + placeToUpdate.getgParent() + " (" + placeToUpdate.getGpType() + ") / " + placeToUpdate.getGgp() + " (" + placeToUpdate.getGgpType() + ") /" + placeToUpdate.getGp2() + " (" + placeToUpdate.getGp2Ttype() + ")");
			
			getPlaceDAO().merge(placeToUpdate);

			getUserHistoryDAO().persist(new UserHistory("Edit details ", Action.MODIFY, Category.PLACE, placeToUpdate));

			return placeToUpdate;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place editPlaceExternalLinks(PlaceExternalLinks placeExternalLinks)
			throws ApplicationThrowable {
		try{
			PlaceExternalLinks placeExternalLinksToUpdate = getPlaceExternalLinksDAO().find(placeExternalLinks.getPlaceExternalLinksId());
			placeExternalLinksToUpdate.setExternalLink(placeExternalLinks.getExternalLink());
			placeExternalLinksToUpdate.setDescription(placeExternalLinks.getDescription());
			getPlaceExternalLinksDAO().merge(placeExternalLinksToUpdate);
			getPlaceDAO().refresh(placeExternalLinksToUpdate.getPlace());
			
			Place place = placeExternalLinksToUpdate.getPlace();
			
			getUserHistoryDAO().persist(new UserHistory("Edit external links", Action.MODIFY, Category.PLACE, place));

			return place;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}	
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place editPlaceGeographicCoordinates(PlaceGeographicCoordinates placeGeographicCoordinates)throws ApplicationThrowable {
		try{
			PlaceGeographicCoordinates placeGeographicCoordinatesToUpdate = getPlaceGeographicCoordinatesDAO().findByPlaceAllId(placeGeographicCoordinates.getId());
			BeanUtils.copyProperties(placeGeographicCoordinatesToUpdate, placeGeographicCoordinates);
			placeGeographicCoordinatesToUpdate.setId(placeGeographicCoordinates.getId());
			getPlaceGeographicCoordinatesDAO().merge(placeGeographicCoordinatesToUpdate);
			
			Place place = placeGeographicCoordinatesToUpdate.getPlace();
			
			getUserHistoryDAO().persist(new UserHistory("Edit geographic coordinates", Action.MODIFY, Category.PLACE, place));

			return place;
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
			UserHistory userHistory = getUserHistoryDAO().findLastEntry(Category.PLACE);
			
			if (userHistory != null) {
				return userHistory.getPlace();
			}
			
			// in case of no user History we extract last place created on database.
			return getPlaceDAO().findLastEntryPlace();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNewGeogKey(String plSource) throws ApplicationThrowable {
		try{
			return getPlaceDAO().findNewGeogKey(plSource).getGeogKey() + 1;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfActiveEndInPlace(Integer placeAllId)	throws ApplicationThrowable {
		try{
			return getPeopleDAO().findNumberOfActiveEndInPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfActiveStartInPlace(Integer placeAllId) throws ApplicationThrowable {
		try{
			return getPeopleDAO().findNumberOfActiveStartInPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfBirthInPlace(Integer placeAllId)	throws ApplicationThrowable {
		try{
			return getPeopleDAO().findNumberOfBirthInPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	@Override
	public Integer findNumberOfDeathInPlace(Integer placeAllId) throws ApplicationThrowable {
		try{
			return getPeopleDAO().findNumberOfDeathInPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfDocumentsInTopicsPlace(Integer placeAllId)	throws ApplicationThrowable {
		try{
			return getEplToLinkDAO().findNumberOfDocumentInTopicsByPlace(placeAllId);
		}
		catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfRecipientDocumentsPlace(Integer placeAllId) throws ApplicationThrowable {
		try{
			return getDocumentDAO().findNumberOfRecipientDocumentsPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfSenderDocumentsPlace(Integer placeAllId)throws ApplicationThrowable {
		try{
			return getDocumentDAO().findNumberOfSenderDocumentsPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfTopicsPlace(Integer placeAllId) throws ApplicationThrowable {
		try{
			return getEplToLinkDAO().findNumberOfTopicsByPlaceAllId(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findPlace(Integer placeId) throws ApplicationThrowable {
		try {
			Place place = getPlaceDAO().find(placeId);

			getUserHistoryDAO().persist(new UserHistory("Show place", Action.VIEW, Category.PLACE, place));

			return place;
		} catch (Throwable th) {
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
	public List<Place> findPlaceNames(Integer geogKey) throws ApplicationThrowable {
		try{
			return getPlaceDAO().findByGeogKey(geogKey);
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexPlaceExternalLinks() throws ApplicationThrowable {
		try {
			getPlaceExternalLinksDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexPlaceGeographicCoordinates() throws ApplicationThrowable {
		try {
			getPlaceGeographicCoordinatesDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexPlaceType() throws ApplicationThrowable {
		try {
			getPlaceTypeDAO().generateIndex();
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
	 * @return the eplToLinkDAO
	 */
	public EplToLinkDAO getEplToLinkDAO() {
		return eplToLinkDAO;
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
	 * @return the placeExternalLinksDAO
	 */
	public PlaceExternalLinksDAO getPlaceExternalLinksDAO() {
		return placeExternalLinksDAO;
	}

	/**
	 * @return the placeGeographicCoordinatesDAO
	 */
	public PlaceGeographicCoordinatesDAO getPlaceGeographicCoordinatesDAO() {
		return placeGeographicCoordinatesDAO;
	}

	/**
	 * @return the placeTypeDAO
	 */
	public PlaceTypeDAO getPlaceTypeDAO() {
		return placeTypeDAO;
	}

	/**
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void optimizeIndexPlace() throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchActiveEndPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchActiveEndPeoplePlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchActiveStartPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchActiveStartPeoplePlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchBirthPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchBirthPeoplePlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
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
	public Page searchDeathPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchDeathPeoplePlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
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
	public Page searchRecipientDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchRecipientDocumentsPlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
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
	public Page searchSenderDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchSenderDocumentsPlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
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
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTopicsPlace(String placeToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getEplToLinkDAO().searchTopicsPlace(placeToSearch, paginationFilter);
		}catch(Throwable th){
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
	 * @param eplToLinkDAO the eplToLinkDAO to set
	 */
	public void setEplToLinkDAO(EplToLinkDAO eplToLinkDAO) {
		this.eplToLinkDAO = eplToLinkDAO;
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
	 * @param placeExternalLinksDAO the placeExternalLinksDAO to set
	 */
	public void setPlaceExternalLinksDAO(PlaceExternalLinksDAO placeExternalLinksDAO) {
		this.placeExternalLinksDAO = placeExternalLinksDAO;
	}

	/**
	 * @param placeGeographicCoordinatesDAO the placeGeographicCoordinatesDAO to set
	 */
	public void setPlaceGeographicCoordinatesDAO(
			PlaceGeographicCoordinatesDAO placeGeographicCoordinatesDAO) {
		this.placeGeographicCoordinatesDAO = placeGeographicCoordinatesDAO;
	}

	/**
	 * @param placeTypeDAO the placeTypeDAO to set
	 */
	public void setPlaceTypeDAO(PlaceTypeDAO placeTypeDAO) {
		this.placeTypeDAO = placeTypeDAO;
	}

	/**
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place undeletePlace(Integer placeAllId) throws ApplicationThrowable {
		Place placeToUndelete = null;
		try {
			placeToUndelete = getPlaceDAO().find(placeAllId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		placeToUndelete.setLogicalDelete(Boolean.FALSE);

		try {
			getPlaceDAO().merge(placeToUndelete);

			getUserHistoryDAO().persist(new UserHistory("Recovered place", Action.MODIFY, Category.PLACE, placeToUndelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return placeToUndelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateIndexPlace(Date fromDate) throws ApplicationThrowable {
		try {
			getPlaceDAO().updateIndex(fromDate);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

}
