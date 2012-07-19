/*
 * UserMarkedListServiceImpl.java
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
package org.medici.docsources.service.usermarkedlist;

import java.util.Date;

import org.medici.docsources.dao.applicationproperty.ApplicationPropertyDAO;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.userhistory.UserHistoryDAO;
import org.medici.docsources.dao.usermarkedlist.UserMarkedListDAO;
import org.medici.docsources.dao.usermarkedlistelement.UserMarkedListElementDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Action;
import org.medici.docsources.domain.UserHistory.Category;
import org.medici.docsources.domain.UserMarkedList;
import org.medici.docsources.domain.UserMarkedListElement;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on people.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class UserMarkedListServiceImpl implements UserMarkedListService {
	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	@Autowired
	private UserMarkedListDAO userMarkedListDAO;
	@Autowired
	private UserMarkedListElementDAO userMarkedListElementDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList addNewDocumentToMarkedList(UserMarkedList userMarkedList, Document document) throws ApplicationThrowable {
		try {
			userMarkedList = getUserMarkedListDAO().find(userMarkedList.getIdMarkedList());
			Date creationDate = new Date();
			UserMarkedListElement userMarkedListElement = new UserMarkedListElement(userMarkedList);
			userMarkedListElement.setDateCreated(creationDate);
			userMarkedListElement.setDocument(getDocumentDAO().find(document.getEntryId()));
			getUserMarkedListElementDAO().persist(userMarkedListElement);
			
			userMarkedList.setDateLastUpdate(creationDate);
			getUserMarkedListDAO().merge(userMarkedList);

			getUserHistoryDAO().persist(new UserHistory("Add new document To Marked List", Action.MODIFY, Category.MARKED_LIST, userMarkedList.getIdMarkedList()));

			return userMarkedList;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList addNewPersonToMarkedList(UserMarkedList userMarkedList, People people) throws ApplicationThrowable {
		try {
			userMarkedList = getUserMarkedListDAO().find(userMarkedList.getIdMarkedList());
			Date creationDate = new Date();
			UserMarkedListElement userMarkedListElement = new UserMarkedListElement(userMarkedList);
			userMarkedListElement.setDateCreated(creationDate);
			userMarkedListElement.setPerson(getPeopleDAO().find(people.getPersonId()));
			getUserMarkedListElementDAO().persist(userMarkedListElement);
			
			userMarkedList.setDateLastUpdate(creationDate);
			getUserMarkedListDAO().merge(userMarkedList);

			getUserHistoryDAO().persist(new UserHistory("Add new person To Marked List", Action.MODIFY, Category.MARKED_LIST, userMarkedList.getIdMarkedList()));

			return userMarkedList;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList addNewPlaceToMarkedList(UserMarkedList userMarkedList, Place place) throws ApplicationThrowable {
		try {
			userMarkedList = getUserMarkedListDAO().find(userMarkedList.getIdMarkedList());
			Date creationDate = new Date();
			UserMarkedListElement userMarkedListElement = new UserMarkedListElement(userMarkedList);
			userMarkedListElement.setDateCreated(creationDate);
			userMarkedListElement.setPlace(getPlaceDAO().find(place.getPlaceAllId()));
			getUserMarkedListElementDAO().persist(userMarkedListElement);
			
			userMarkedList.setDateLastUpdate(creationDate);
			getUserMarkedListDAO().merge(userMarkedList);

			getUserHistoryDAO().persist(new UserHistory("Add new place To Marked List", Action.MODIFY, Category.MARKED_LIST, userMarkedList.getIdMarkedList()));

			return userMarkedList;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList addNewVolumeToMarkedList(UserMarkedList userMarkedList, Volume volume) throws ApplicationThrowable {
		try {
			userMarkedList = getUserMarkedListDAO().find(userMarkedList.getIdMarkedList());
			Date creationDate = new Date();
			UserMarkedListElement userMarkedListElement = new UserMarkedListElement(userMarkedList);
			userMarkedListElement.setDateCreated(creationDate);
			userMarkedListElement.setVolume(getVolumeDAO().find(volume.getSummaryId()));
			getUserMarkedListElementDAO().persist(userMarkedListElement);
			
			userMarkedList.setDateLastUpdate(creationDate);
			getUserMarkedListDAO().merge(userMarkedList);

			getUserHistoryDAO().persist(new UserHistory("Add new volume To Marked List", Action.MODIFY, Category.MARKED_LIST, userMarkedList.getIdMarkedList()));

			return userMarkedList;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * @return the applicationPropertyDAO
	 */
	public ApplicationPropertyDAO getApplicationPropertyDAO() {
		return applicationPropertyDAO;
	}
	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList getMarkedList(UserMarkedList userMarkedList) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
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
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList removeDocumentFromMarkedList(UserMarkedList userMarkedList, Document document) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList removePersonFromMarkedList(UserMarkedList userMarkedList, People people) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList removePlaceFromMarkedList(UserMarkedList userMarkedList, Place place) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc} 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public UserMarkedList removeVolumeFromMarkedList(UserMarkedList userMarkedList, Volume volume) throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param applicationPropertyDAO the applicationPropertyDAO to set
	 */
	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
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
	public void setUserMarkedListElementDAO(UserMarkedListElementDAO userMarkedListElementDAO) {
		this.userMarkedListElementDAO = userMarkedListElementDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}

}