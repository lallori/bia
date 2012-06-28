/*
 * PrintPlaceController.java
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

import org.medici.docsources.command.geobase.PrintPlaceRequestCommand;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.domain.Place;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Print place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/geobase/PrintPlace")
public class PrintPlaceController {
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
	 * 
	 * @param placeId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") PrintPlaceRequestCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		Place place = new Place();
		List<Place> placeNames;
		
		if(command.getPlaceAllId() > 0){
			try {
				place = getGeoBaseService().findPlace(command.getPlaceAllId());
				model.put("topicsPlace", getGeoBaseService().findNumberOfTopicsPlace(command.getPlaceAllId()));
				model.put("docInTopics", getGeoBaseService().findNumberOfDocumentsInTopicsPlace(command.getPlaceAllId()));
				model.put("senderPlace", getGeoBaseService().findNumberOfSenderDocumentsPlace(command.getPlaceAllId()));
				model.put("recipientPlace", getGeoBaseService().findNumberOfRecipientDocumentsPlace(command.getPlaceAllId()));
				model.put("birthPlace", getGeoBaseService().findNumberOfBirthInPlace(command.getPlaceAllId()));
				model.put("activeStartPlace", getGeoBaseService().findNumberOfActiveStartInPlace(command.getPlaceAllId()));
				model.put("deathPlace", getGeoBaseService().findNumberOfDeathInPlace(command.getPlaceAllId()));
				model.put("activeEndPlace", getGeoBaseService().findNumberOfActiveEndInPlace(command.getPlaceAllId()));
				
				if(place.getPlaceGeographicCoordinates() != null)
					model.put("linkGoogleMaps", HtmlUtils.generateLinkGoogleMaps(place.getPlaceGeographicCoordinates()));
				else
					model.put("linkGoogleMaps", null);
			} catch (ApplicationThrowable ath) {
				new ModelAndView("error/PrintPlace", model);
			}
		}else{
			place.setPlaceAllId(0);
			place.setPlSource(command.getPlSource());
			place.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
		}
		
		try{
			placeNames = getGeoBaseService().findPlaceNames(place.getGeogKey());
			model.put("placeNames", placeNames);
		}catch(ApplicationThrowable th){
			new ModelAndView("error/PrintPlace", model);
		}
		model.put("place", place);

		return new ModelAndView("geobase/PrintPlace", model);
	}
}