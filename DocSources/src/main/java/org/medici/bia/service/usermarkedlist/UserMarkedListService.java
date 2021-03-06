/*
 * UserMarkedListService.java
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
package org.medici.bia.service.usermarkedlist;

import java.util.List;

import org.medici.bia.domain.Document;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.bia.domain.UserMarkedList} and
 * {@link org.medici.bia.domain.UserMarkedListElement} objects.<br>
 * It defines every business methods needed to work on marked list.
 * With this service, you can :<br>
 * - add a new marked list<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface UserMarkedListService {

	/**
	 * 
	 * @param userMarkedList
	 * @param people
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList addNewDocumentToMarkedList(UserMarkedList userMarkedList, Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param userMarkedList
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList createMyMarkedList(UserMarkedList userMarkedList) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList getMarkedList(UserMarkedList userMarkedList) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList getMyMarkedList() throws ApplicationThrowable;

	/**
	 * 
	 * @param userMarkedList
	 * @param people
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList addNewPersonToMarkedList(UserMarkedList userMarkedList, People people) throws ApplicationThrowable;

	/**
	 * 
	 * @param userMarkedList
	 * @param place
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList addNewPlaceToMarkedList(UserMarkedList userMarkedList, Place place) throws ApplicationThrowable;


	/**
	 * 
	 * @param userMarkedList
	 * @param volume
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList addNewVolumeToMarkedList(UserMarkedList userMarkedList, Volume volume) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void deleteMyMarkedList() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param idElelements
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<UserMarkedListElement> getElementsFromMarkedList(UserMarkedList userMarkedList, List<Integer> idElements) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean ifDocumentAlreadyPresent(UserMarkedList userMarkedList, Integer entryId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean ifPersonAlreadyPresent(UserMarkedList userMarkedList, Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean ifPlaceAlreadyPresent(UserMarkedList userMarkedList, Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean ifVolumeAlreadyPresent(UserMarkedList userMarkedList, Integer summaryId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param people
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList removeDocumentFromMarkedList(UserMarkedList userMarkedList, Document document) throws ApplicationThrowable;

	/**
	 * 
	 * @param userMarkedList
	 * @param idElementsToRemove
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList removeElementsFromMarkedList(UserMarkedList userMarkedList, List<Integer> idElementsToRemove) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMarkedList
	 * @param people
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList removePersonFromMarkedList(UserMarkedList userMarkedList, People people) throws ApplicationThrowable;

	/**
	 * 
	 * @param userMarkedList
	 * @param place
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList removePlaceFromMarkedList(UserMarkedList userMarkedList, Place place) throws ApplicationThrowable;


	/**
	 * 
	 * @param userMarkedList
	 * @param place
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserMarkedList removeVolumeFromMarkedList(UserMarkedList userMarkedList, Volume volume) throws ApplicationThrowable;
}
