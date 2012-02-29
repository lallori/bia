/*
 * ShowLastEntryPlaceController.java
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
package org.medici.docsources.controller.geobase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.domain.Place;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show last entry place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/geobase/ShowLastEntryPlace")
public class ShowLastEntryPlaceController {
	@Autowired
	private GeoBaseService geoBaseService;

	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(){
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findLastEntryPlace();
			model.put("place", place);
			model.put("topicsPlace", getGeoBaseService().findNumberOfTopicsPlace(place.getPlaceAllId()));
			model.put("docInTopics", getGeoBaseService().findNumberOfDocumentsInTopicsPlace(place.getPlaceAllId()));
			model.put("senderPlace", getGeoBaseService().findNumberOfSenderDocumentsPlace(place.getPlaceAllId()));
			model.put("recipientPlace", getGeoBaseService().findNumberOfRecipientDocumentsPlace(place.getPlaceAllId()));
			model.put("birthPlace", getGeoBaseService().findNumberOfBirthInPlace(place.getPlaceAllId()));
			model.put("activeStartPlace", getGeoBaseService().findNumberOfActiveStartInPlace(place.getPlaceAllId()));
			model.put("deathPlace", getGeoBaseService().findNumberOfDeathInPlace(place.getPlaceAllId()));
			model.put("activeEndPlace", getGeoBaseService().findNumberOfActiveEndInPlace(place.getPlaceAllId()));
			List<Place> placeNames;
			placeNames = getGeoBaseService().findPlaceNames(place.getGeogKey());
			model.put("placeNames", placeNames);
			
			if(place.getPlaceGeographicCoordinates() != null)
				model.put("linkGoogleMaps", HtmlUtils.generateLinkGoogleMaps(place.getPlaceGeographicCoordinates()));
			else
				model.put("linkGoogleMaps", null);

		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/ShowLastEntryPlace", model);
		}
		
		return new ModelAndView("geobase/ShowPlace", model);
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