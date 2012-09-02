/*
 * ShowPlaceFromHistoryController.java
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
package org.medici.bia.controller.geobase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.command.geobase.ShowPlaceFromHistoryRequestCommand;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.Place;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.bia.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "ShowPlaceFrom History".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/geobase/ShowPlaceFromHistory")
public class ShowPlaceFromHistoryController {
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
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowPlaceFromHistoryRequestCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		Place place = new Place();
		List<Place> placeNames;
		
		if(command.getIdUserHistory() > 0){
			try {
				place = getGeoBaseService().findPlaceFromHistory(command.getIdUserHistory());
				
				model.put("topicsPlace", getGeoBaseService().findNumberOfTopicsPlace(place.getPlaceAllId()));
				model.put("docInTopics", getGeoBaseService().findNumberOfDocumentsInTopicsPlace(place.getPlaceAllId()));
				model.put("senderPlace", getGeoBaseService().findNumberOfSenderDocumentsPlace(place.getPlaceAllId()));
				model.put("recipientPlace", getGeoBaseService().findNumberOfRecipientDocumentsPlace(place.getPlaceAllId()));
				model.put("birthPlace", getGeoBaseService().findNumberOfBirthInPlace(place.getPlaceAllId()));
				model.put("activeStartPlace", getGeoBaseService().findNumberOfActiveStartInPlace(place.getPlaceAllId()));
				model.put("deathPlace", getGeoBaseService().findNumberOfDeathInPlace(place.getPlaceAllId()));
				model.put("activeEndPlace", getGeoBaseService().findNumberOfActiveEndInPlace(place.getPlaceAllId()));
				
				if(place.getPlaceGeographicCoordinates() != null)
					model.put("linkGoogleMaps", HtmlUtils.generateLinkGoogleMaps(place.getPlaceGeographicCoordinates()));
				else
					model.put("linkGoogleMaps", null);
				
				model.put("historyNavigator", getGeoBaseService().getHistoryNavigator(command.getIdUserHistory()));
				
				if(getGeoBaseService().ifPlaceAlreadyPresentInMarkedList(place.getPlaceAllId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				new ModelAndView("error/ShowPlace", model);
			}
		}else{
			place.setPlaceAllId(0);
			place.setPlSource("");
			place.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
		}
		
		try{
			placeNames = getGeoBaseService().findPlaceNames(place.getGeogKey());
			model.put("placeNames", placeNames);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			new ModelAndView("error/ShowPlace", model);
		}

		model.put("place", place);

		return new ModelAndView("geobase/ShowPlace", model);
	}
}