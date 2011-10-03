/*
 * EditExternalLinkPlaceValidator.java
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
package org.medici.docsources.validator.geobase;

import java.util.Set;

import org.medici.docsources.command.geobase.EditExternalLinkPlaceCommand;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceExternalLinks;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit External Link Place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class EditExternalLinkPlaceValidator implements Validator {
	@Autowired
	private GeoBaseService geoBaseService;

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyDocumentCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(EditExternalLinkPlaceCommand.class);
	}

	/**
	 * Validate the supplied target object, which must be of a Class for which
	 * the supports(Class) method typically has (or would) return true. The
	 * supplied errors instance can be used to report any resulting validation
	 * errors.
	 * 
	 * @param object the object that is to be validated (can be null)
	 * @param errors contextual state about the validation process (never null)
	 */
	public void validate(Object object, Errors errors) {
		EditExternalLinkPlaceCommand editExternalLinkPlaceCommand = (EditExternalLinkPlaceCommand) object;
		validateExternalLink(	editExternalLinkPlaceCommand.getPlaceAllId(), 
						editExternalLinkPlaceCommand.getPlaceExternalLinksId(),
						editExternalLinkPlaceCommand.getExternalLink(),
						errors);
	}

	private void validateExternalLink(Integer placeAllId, Integer placeExternalLinksId, String externalLink, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeAllId", "error.placeAllId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placeExternalLinksId", "error.placeExternalLinksId.null");

		if (!errors.hasErrors()) {
			try {
				Place place = getGeoBaseService().findPlace(placeAllId);
				if (place == null) {
					errors.reject("placeAllId", "error.placeAllId.notfound");
				} else {
					//retrieving actual external links
					Set<PlaceExternalLinks> externalLinks = place.getPlaceExternalLinks();
					
					//Check if externalLinks is empty and user is trying to modify an existing link
					if (externalLinks == null && (!placeExternalLinksId.equals(0))) {
						errors.reject("placeExternalLinksId", "error.placeExternalLinksId.invalid");
					} 
				}

			} catch (ApplicationThrowable ath) {
				errors.reject("placeAllId", "error.placeAllId.notfound");
			}
		}
	}

	

	/**
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}
}
