/*
 * AddMarkedListPlaceValidator.java
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
package org.medici.bia.validator.geobase;

import org.medici.docsources.command.geobase.AddMarkedListPlaceCommand;
import org.medici.docsources.domain.UserMarkedList;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.medici.docsources.service.usermarkedlist.UserMarkedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class AddMarkedListPlaceValidator implements Validator {
	@Autowired
	private GeoBaseService geoBaseService;
	@Autowired
	private UserMarkedListService userMarkedListService;

	/**
	 * 
	 * @return
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}

	/**
	 * 
	 * @param geoBaseService
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyPlaceCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(AddMarkedListPlaceCommand.class);
	}

	/**
	 * Validate the supplied target object, which must be of a Class for which
	 * the supports(Class) method typically has (or would) return true. The
	 * supplied errors instance can be used to report any resulting validation
	 * errors.
	 * 
	 * @param object the object that is to be validated (can be null)
	 * @param errors
	 *            contextual state about the validation process (never null)
	 */
	public void validate(Object object, Errors errors) {
		AddMarkedListPlaceCommand addMarkedListPlaceCommand = (AddMarkedListPlaceCommand) object;
		validatePlaceAllId(addMarkedListPlaceCommand.getPlaceAllId(), errors);
		validateAddMarkedList(addMarkedListPlaceCommand.getPlaceAllId(), errors);
	}

	/**
	 * 
	 * @param placeAllId
	 * @param errors
	 */
	public void validatePlaceAllId(Integer placeAllId, Errors errors) {
		if (!errors.hasErrors()) {
			try {
				if (getGeoBaseService().findPlace(placeAllId) == null) {
					errors.reject("placeAllId", "error.placeAllId.notfound");
				}
			} catch (ApplicationThrowable ath) {
			}
		}
	}
	
	public void validateAddMarkedList(Integer placeAllId, Errors errors){
		if(!errors.hasErrors()){
			try{
				UserMarkedList userMarkedList = getUserMarkedListService().getMyMarkedList();
				if(userMarkedList != null){
					if(getUserMarkedListService().ifPlaceAlreadyPresent(userMarkedList, placeAllId)){
						errors.reject("placeAllId", "error.placeAllId.alreadyMarked");
					}
				}
			}catch(ApplicationThrowable ath){
				errors.reject("placeAllId", "error.placeAllId.alreadyMarked");
			}
		}
	}

	/**
	 * @return the userMarkedListService
	 */
	public UserMarkedListService getUserMarkedListService() {
		return userMarkedListService;
	}

	/**
	 * @param userMarkedListService the userMarkedListService to set
	 */
	public void setUserMarkedListService(UserMarkedListService userMarkedListService) {
		this.userMarkedListService = userMarkedListService;
	}
	
	
}