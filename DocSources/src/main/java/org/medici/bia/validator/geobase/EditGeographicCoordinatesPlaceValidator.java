/*
 * EditGeographicCoordinatesPlaceValidator.java
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

import org.medici.docsources.command.geobase.EditDetailsPlaceCommand;
import org.medici.docsources.command.geobase.EditGeographicCoordinatesPlaceCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditGeographicCoordinatesPlaceValidator implements Validator {
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
		EditGeographicCoordinatesPlaceCommand editGeographicCoordinatesPlaceCommand = (EditGeographicCoordinatesPlaceCommand) object;
		validatePlaceAllId(editGeographicCoordinatesPlaceCommand.getPlaceAllId(), errors);
		validateGeoCoorPlace(editGeographicCoordinatesPlaceCommand.getDegreeLongitude(), editGeographicCoordinatesPlaceCommand.getMinuteLongitude(), editGeographicCoordinatesPlaceCommand.getSecondLongitude(), editGeographicCoordinatesPlaceCommand.getDirectionLongitude(), editGeographicCoordinatesPlaceCommand.getDegreeLatitude(), editGeographicCoordinatesPlaceCommand.getMinuteLatitude(), editGeographicCoordinatesPlaceCommand.getSecondLatitude(), editGeographicCoordinatesPlaceCommand.getDirectionLatitude(), errors);
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
	
	public void validateGeoCoorPlace(Integer degLong, Integer minLong, Integer secLong, String dirLong, Integer degLat, Integer minLat, Integer secLat, String dirLat, Errors errors){
		if(!errors.hasErrors()){
			if(degLong == null || degLong < 0 || degLong > 180){
				errors.rejectValue("degreeLongitude", "error.degreeLongitude.invalid");
			}
			if(degLat == null || degLat < 0 || degLat > 90){
				errors.rejectValue("degreeLatitude", "error.degreeLatitude.invalid");
			}
			if(minLong != null && (minLong < 0 || minLong > 59)){
				errors.rejectValue("minuteLongitude", "error.minuteLongitude.invalid");
			}
			if(secLong != null && (secLong < 0 || secLong > 59)){
				errors.rejectValue("secondLongitude", "error.secondLongitude.invalid");
			}
			if(minLat != null && (minLat < 0 || minLat > 59)){
				errors.rejectValue("minuteLatitude", "error.minuteLatitude.invalid");
			}
			if(secLat != null && (secLat < 0 || secLat > 59)){
				errors.rejectValue("secondLatitude", "error.secondLatitude.invalid");
			}
			if(!dirLong.toLowerCase().equals("w") && !dirLong.toLowerCase().equals("e")){
				errors.rejectValue("directionLongitude", "error.directionLongitude.invalid");
			}
			if(!dirLat.toLowerCase().equals("n") && !dirLat.toLowerCase().equals("s")){
				errors.rejectValue("directionLatitude", "error.directionLatitude.invalid");
			}
		}
	}
	
}
