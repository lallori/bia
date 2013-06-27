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
package org.medici.bia.service.geobase;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.epltolink.EplToLinkDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumoption.ForumOptionDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.placeexternallinks.PlaceExternalLinksDAO;
import org.medici.bia.dao.placegeographiccoordinates.PlaceGeographicCoordinatesDAO;
import org.medici.bia.dao.placetype.PlaceTypeDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermarkedlist.UserMarkedListDAO;
import org.medici.bia.dao.usermarkedlistelement.UserMarkedListElementDAO;
import org.medici.bia.dao.vettinghistory.VettingHistoryDAO;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumOption;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.PlaceExternalLinks;
import org.medici.bia.domain.PlaceGeographicCoordinates;
import org.medici.bia.domain.PlaceType;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.domain.VettingHistory;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on Place.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class GeoBaseServiceImpl implements GeoBaseService {
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private EplToLinkDAO eplToLinkDAO;
	@Autowired
	private ForumDAO forumDAO;
	@Autowired
	private ForumOptionDAO forumOptionDAO;
	@Autowired
	private ImageDAO imageDAO;
	private final Logger logger = Logger.getLogger(this.getClass());
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
	private UserDAO userDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	@Autowired
	private UserMarkedListDAO userMarkedListDAO;
	@Autowired
	private UserMarkedListElementDAO userMarkedListElementDAO;
	@Autowired
	private VettingHistoryDAO vettingHistoryDAO;

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Place addNewPlace(Place place) throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			place.setPlaceAllId(null);
			place.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			place.setCreatedBy(user);
			place.setDateEntered(new Date());
			place.setDateCreated(new Date());
			place.setLastUpdate(new Date());
			place.setLastUpdateBy(user);
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

			getUserHistoryDAO().persist(new UserHistory(user, "Add new place", Action.CREATE, Category.PLACE, place));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Create place", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.PLACE, place));

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
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add external link", Action.MODIFY, Category.PLACE, placeExternalLinks.getPlace()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add external link", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PLACE, placeExternalLinks.getPlace()));

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
	public Forum addNewPlaceForum(Place place) throws ApplicationThrowable {
		try {
			Forum forum = getForumDAO().getForumPlace(place.getPlaceAllId());

			//this control is mandatory to prevent duplication records on forum
			if (forum == null) {
				place = getPlaceDAO().find(place.getPlaceAllId());
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.place")));
				forum = getForumDAO().addNewPlaceForum(parentForum, place);
				
				ForumOption forumOption = new ForumOption(forum);
				forumOption.setGroupBySubForum(Boolean.TRUE);
				forumOption.setCanHaveTopics(Boolean.TRUE);
				forumOption.setCanDeletePosts(Boolean.TRUE);
				forumOption.setCanDeleteTopics(Boolean.TRUE);
				forumOption.setCanEditPosts(Boolean.TRUE);
				forumOption.setCanPostReplys(Boolean.TRUE);
				getForumOptionDAO().persist(forumOption);

				// we need to set new FullPath for recursive functions...
				forum.setFullPath(parentForum.getFullPath() + forum.getForumId() + ".");
				getForumDAO().merge(forum);

				// thisi method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);

				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.FORUM, forum));
			}else if(forum.getLogicalDelete()){
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.place")));
				
				forum.setLogicalDelete(Boolean.FALSE);
				forum.setTotalViews(0);
				forum.setLastUpdate(new Date());
				getForumDAO().merge(forum);
				
				// this method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);
				
				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.FORUM, forum));
			}

			return forum;
		} catch (Throwable th) {
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
			PlaceGeographicCoordinates placeGeographicCoordinatesToPersist = new PlaceGeographicCoordinates(null);
			placeGeographicCoordinatesToPersist.setPlace(getPlaceDAO().find(placeGeographicCoordinates.getPlace().getPlaceAllId()));
			placeGeographicCoordinatesToPersist.setDegreeLatitude(placeGeographicCoordinates.getDegreeLatitude());
			placeGeographicCoordinatesToPersist.setMinuteLatitude(placeGeographicCoordinates.getMinuteLatitude());
			placeGeographicCoordinatesToPersist.setSecondLatitude(placeGeographicCoordinates.getSecondLatitude());
			placeGeographicCoordinatesToPersist.setDirectionLatitude(placeGeographicCoordinates.getDirectionLatitude());
			placeGeographicCoordinatesToPersist.setDegreeLongitude(placeGeographicCoordinates.getDegreeLongitude());
			placeGeographicCoordinatesToPersist.setMinuteLongitude(placeGeographicCoordinates.getMinuteLongitude());
			placeGeographicCoordinatesToPersist.setSecondLongitude(placeGeographicCoordinates.getSecondLongitude());
			placeGeographicCoordinatesToPersist.setDirectionLongitude(placeGeographicCoordinates.getDirectionLongitude());
			
			getPlaceGeographicCoordinatesDAO().persist(placeGeographicCoordinatesToPersist);
			
			
			getPlaceDAO().refresh(getPlaceDAO().find(placeGeographicCoordinatesToPersist.getPlace().getPlaceAllId()));
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add geographic coordinates", Action.MODIFY, Category.PLACE, placeGeographicCoordinatesToPersist.getPlace()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add geographic coordinates", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PLACE, placeGeographicCoordinatesToPersist.getPlace()));

			return placeGeographicCoordinatesToPersist.getPlace();
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

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Compare place", Action.COMPARE, Category.PLACE, place));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Compare place", org.medici.bia.domain.VettingHistory.Action.COMPARE, org.medici.bia.domain.VettingHistory.Category.PLACE, place));

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

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Deleted place", Action.DELETE, Category.PLACE, placeToDelete));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Deleted place", org.medici.bia.domain.VettingHistory.Action.DELETE, org.medici.bia.domain.VettingHistory.Category.PLACE, placeToDelete));
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

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete external link ", Action.MODIFY, Category.PLACE, placeExternalLinksToDelete.getPlace()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete external link", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PLACE, placeExternalLinksToDelete.getPlace()));
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

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
			
			placeToUpdate.setLastUpdate(new Date());
			placeToUpdate.setLastUpdateBy(user);
			getPlaceDAO().merge(placeToUpdate);

			getUserHistoryDAO().persist(new UserHistory(user, "Edit details ", Action.MODIFY, Category.PLACE, placeToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit details", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PLACE, placeToUpdate));

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
	public Place editPlaceExternalLinks(PlaceExternalLinks placeExternalLinks) throws ApplicationThrowable {
		try{
			PlaceExternalLinks placeExternalLinksToUpdate = getPlaceExternalLinksDAO().find(placeExternalLinks.getPlaceExternalLinksId());
			placeExternalLinksToUpdate.setExternalLink(placeExternalLinks.getExternalLink());
			placeExternalLinksToUpdate.setDescription(placeExternalLinks.getDescription());
			getPlaceExternalLinksDAO().merge(placeExternalLinksToUpdate);
			getPlaceDAO().refresh(placeExternalLinksToUpdate.getPlace());
			
			Place place = placeExternalLinksToUpdate.getPlace();
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit external links", Action.MODIFY, Category.PLACE, place));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit external links", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PLACE, place));

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
			PlaceGeographicCoordinates placeGeographicCoordinatesToUpdate = getPlaceGeographicCoordinatesDAO().find(placeGeographicCoordinates.getId());
			placeGeographicCoordinatesToUpdate.setId(placeGeographicCoordinates.getId());
			placeGeographicCoordinatesToUpdate.setDegreeLatitude(placeGeographicCoordinates.getDegreeLatitude());
			placeGeographicCoordinatesToUpdate.setMinuteLatitude(placeGeographicCoordinates.getMinuteLatitude());
			placeGeographicCoordinatesToUpdate.setSecondLatitude(placeGeographicCoordinates.getSecondLatitude());
			placeGeographicCoordinatesToUpdate.setDirectionLatitude(placeGeographicCoordinates.getDirectionLatitude());
			placeGeographicCoordinatesToUpdate.setDegreeLongitude(placeGeographicCoordinates.getDegreeLongitude());
			placeGeographicCoordinatesToUpdate.setMinuteLongitude(placeGeographicCoordinates.getMinuteLongitude());
			placeGeographicCoordinatesToUpdate.setSecondLongitude(placeGeographicCoordinates.getSecondLongitude());
			placeGeographicCoordinatesToUpdate.setDirectionLongitude(placeGeographicCoordinates.getDirectionLongitude());
			getPlaceGeographicCoordinatesDAO().merge(placeGeographicCoordinatesToUpdate);
			getPlaceDAO().refresh(placeGeographicCoordinatesToUpdate.getPlace());
			
			Place place = placeGeographicCoordinatesToUpdate.getPlace();
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit geographic coordinates", Action.MODIFY, Category.PLACE, place));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit geographic coordinates", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PLACE, place));

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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findLastEntry(user, Category.PLACE);
			
			if (userHistory != null) {
				return userHistory.getPlace();
			}
			
			return null;
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
	public Map<Integer, Long> findNumbersOfDocumentsRelated(List<Integer> placeAllIds) throws ApplicationThrowable {
		try{
			Map<Integer, Long> docsRel = getEplToLinkDAO().findNumbersOfDocumentsInTopicsByPlace(placeAllIds);
			return docsRel;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Long> findNumbersOfFromToDocumentsRelated(List<Integer> placeAllIds) throws ApplicationThrowable {
		try{
			Map<Integer, Long> docsRel = getDocumentDAO().findNumbersOfDocumentsRelatedPlace(placeAllIds);
			return docsRel;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Long> findNumbersOfPeopleRelated(List<Integer> placeAllIds) throws ApplicationThrowable {
		try{
			Map<Integer, Long> peopleRel = getPeopleDAO().findNumbersOfPeopleRelatedPlace(placeAllIds);
			return peopleRel;
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
			
			User user;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Show place", Action.VIEW, Category.PLACE, place));
			}

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
	public Place findPlaceForHierarchy(Integer placeId) throws ApplicationThrowable {
		try{
			Place place = getPlaceDAO().find(placeId);
			
			return place;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findPlaceFromHistory(Integer idUserHistory) throws ApplicationThrowable {
		try{
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			return userHistory.getPlace();
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
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getCategoryHistoryNavigator(Place place) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.PLACE, place.getPlaceAllId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	@Override
	public Map<String, Boolean> getDocumentsDigitizedState(List<Integer> volNums, List<String> volLetExts, List<Integer> folioNums, List<String> folioMods) throws ApplicationThrowable {
		Map<String, Boolean> retValue = new HashMap<String, Boolean>();
		try{
			for(int i=0; i<volNums.size();i++){
				retValue.put(DocumentUtils.toMDPAndFolioFormat(volNums.get(i), volLetExts.get(i), folioNums.get(i), folioMods.get(i)), Boolean.FALSE);
			}
			
			List<String> documentsDigitized = getImageDAO().findDocumentsDigitized(volNums, volLetExts, folioNums, folioMods);
			
			for(String MDPFolio : documentsDigitized){
				retValue.put(MDPFolio, Boolean.TRUE);
			}
			return retValue;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the eplToLinkDAO
	 */
	public EplToLinkDAO getEplToLinkDAO() {
		return eplToLinkDAO;
	}
	
	/**
	 * @return the forumDAO
	 */
	public ForumDAO getForumDAO() {
		return forumDAO;
	}

	/**
	 * @return the forumOptionDAO
	 */
	public ForumOptionDAO getForumOptionDAO() {
		return forumOptionDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getHistoryNavigator(Integer idUserHistory, Place place) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromPlace(userHistory.getUser(), userHistory.getIdUserHistory(), place.getPlaceAllId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromPlace(userHistory.getUser(), userHistory.getIdUserHistory(), place.getPlaceAllId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getHistoryNavigator(Place place) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.PLACE, place.getPlaceAllId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromPlace(user, userHistory.getIdUserHistory(), place.getPlaceAllId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromPlace(user, userHistory.getIdUserHistory(), place.getPlaceAllId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * @return the imageDAO
	 */
	public ImageDAO getImageDAO() {
		return imageDAO;
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
	 * 
	 */
	@Override
	public Forum getPlaceForum(Integer placeAllId) throws ApplicationThrowable {
		try{
			return getForumDAO().getForumPlace(placeAllId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
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

	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	/**
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}
	
	/**
	 * @return the userMarkedListDAO
	 */
	public UserMarkedListDAO getUserMarkedListDAO() {
		return userMarkedListDAO;
	}

	/**
	 * @return the userMarkedListElementDAO
	 */
	public UserMarkedListElementDAO getUserMarkedListElementDAO() {
		return userMarkedListElementDAO;
	}

	/**
	 * @return the vettingHistoryDAO
	 */
	public VettingHistoryDAO getVettingHistoryDAO() {
		return vettingHistoryDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean ifPlaceAlreadyPresentInMarkedList(Integer placeAllId) throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserMarkedList userMarkedList = getUserMarkedListDAO().getMyMarkedList(user);
			if(userMarkedList == null){
				return Boolean.FALSE;
			}

			UserMarkedListElement userMarkedListElement = getUserMarkedListElementDAO().findPlaceInMarkedList(userMarkedList.getIdMarkedList(), placeAllId);
			if(userMarkedListElement != null){
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
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
	public Map<String, Long> searchTopicsPlace(String placeToSearch) throws ApplicationThrowable {
		try{
			return getEplToLinkDAO().searchTopicsPlace(placeToSearch);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVettingHistoryPlace(Integer placeAllId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			Place place = getPlaceDAO().find(placeAllId);
			return getVettingHistoryDAO().getVettingHistoryPlace(place, paginationFilter);
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
	 * @param forumDAO the forumDAO to set
	 */
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	/**
	 * @param forumOptionDAO the forumOptionDAO to set
	 */
	public void setForumOptionDAO(ForumOptionDAO forumOptionDAO) {
		this.forumOptionDAO = forumOptionDAO;
	}

	/**
	 * @param imageDAO the imageDAO to set
	 */
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
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

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userMarkedListDAO the userMarkedListDAO to set
	 */
	public void setUserMarkedListDAO(UserMarkedListDAO userMarkedListDAO) {
		this.userMarkedListDAO = userMarkedListDAO;
	}

	/**
	 * @param userMarkedListElementDAO the userMarkedListElementDAO to set
	 */
	public void setUserMarkedListElementDAO(
			UserMarkedListElementDAO userMarkedListElementDAO) {
		this.userMarkedListElementDAO = userMarkedListElementDAO;
	}

	/**
	 * @param vettingHistoryDAO the vettingHistoryDAO to set
	 */
	public void setVettingHistoryDAO(VettingHistoryDAO vettingHistoryDAO) {
		this.vettingHistoryDAO = vettingHistoryDAO;
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

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Recovered place", Action.UNDELETE, Category.PLACE, placeToUndelete));
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
