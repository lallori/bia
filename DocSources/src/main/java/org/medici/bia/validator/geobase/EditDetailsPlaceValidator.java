/*
 * EditDetailsPlaceValidator.java
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

import org.medici.bia.command.geobase.EditDetailsPlaceCommand;
import org.medici.bia.common.util.ValidationUtils;
import org.medici.bia.domain.Place;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class EditDetailsPlaceValidator implements Validator {
	@Autowired
	private GeoBaseService geoBaseService;

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
		return givenClass.equals(EditDetailsPlaceCommand.class);
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
		EditDetailsPlaceCommand editDetailsPlaceCommand = (EditDetailsPlaceCommand) object;
		validatePlaceAllId(editDetailsPlaceCommand.getPlaceAllId(), errors);
		validateGeogKey(editDetailsPlaceCommand.getGeogKey(), editDetailsPlaceCommand.getPlSource(), errors);
		validateParentPlace(editDetailsPlaceCommand.getParentPlaceAllId(), errors);
	}

	/**
	 * 
	 * @param placeAllId
	 * @param errors
	 */
	public void validatePlaceAllId(Integer placeAllId, Errors errors) {
		if (!errors.hasErrors()) {
			if(placeAllId > 0){
				try {
					if (getGeoBaseService().findPlace(placeAllId) == null) {
						errors.rejectValue("placeId", "error.placeId.notfound");
					}
				} catch (ApplicationThrowable ath) {
				}
			}
		}
	}
	
	public void validateGeogKey(Integer geogKey, String plSource, Errors errors){
		if(!errors.hasErrors()){
			if(geogKey == null){
				errors.rejectValue("geogKey", "error.geogKey.notvalid");
			}
			if(geogKey != null && plSource != null){
				if (plSource.equals("TGN") && geogKey < 1000000){
					errors.rejectValue("geogKey", "error.geogKey.notvalid");
				}
				if(plSource.equals("MAPSITE") && (geogKey < 400000 || geogKey > 1000000) ){
					errors.rejectValue("geogKey", "error.geogKey.notvalid");
				}
				if(plSource.equals("MAPPLACE") && (geogKey < 100000 || geogKey > 400000)){
					errors.rejectValue("geogKey", "error.geogKey.notvalid");
				}
			}
		}
	}
	
	public void validateParentPlace(Integer parentPlaceAllId, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "parentPlaceAllId", "error.parentPlaceAllId.null");
		
		if(!errors.hasErrors()){
			try{
				Place place = getGeoBaseService().findPlace(parentPlaceAllId);
				if(place == null){
					errors.rejectValue("parentPlaceAllId", "error.parentPlaceAllId.notfound");
				}
			}catch(ApplicationThrowable th){
				errors.rejectValue("parentPlaceAllId", "error.parentPlaceAllId.notfound");
			}
		}
	}
}
