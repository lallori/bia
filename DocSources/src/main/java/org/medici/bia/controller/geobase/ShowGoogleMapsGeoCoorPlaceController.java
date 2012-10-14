/*
 * ShowGoogleMapsGeoCoorPlaceController.java
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
import java.util.Map;

import org.medici.bia.command.geobase.ShowGoogleMapsGeoCoorPlaceCommand;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.PlaceGeographicCoordinates;
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
 * Controller for action "Show Google Maps Geo Coor Place".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/geobase/ShowGoogleMapsGeoCoorPlace")
public class ShowGoogleMapsGeoCoorPlaceController {
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
	 * @param placeId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("command") ShowGoogleMapsGeoCoorPlaceCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PlaceGeographicCoordinates placeGeographicCoordinates = new PlaceGeographicCoordinates(command.getPlaceGeographicCoordinatesId());
				
		placeGeographicCoordinates.setPlace(new Place(command.getPlaceAllId()));
		
		if(command.getDegreeLatitude() != null) {
			placeGeographicCoordinates.setDegreeLatitude(command.getDegreeLatitude());
		} else {
			placeGeographicCoordinates.setDegreeLatitude(0);
		}

		if(command.getMinuteLatitude() != null) {
			placeGeographicCoordinates.setMinuteLatitude(command.getMinuteLatitude());
		} else {
			placeGeographicCoordinates.setMinuteLatitude(0);	
		}
		
		if(command.getSecondLatitude() != null) {
			placeGeographicCoordinates.setSecondLatitude(command.getSecondLatitude());
		} else {
			placeGeographicCoordinates.setSecondLatitude(0);
		}
		
		if(command.getDirectionLatitude() != null) {
			placeGeographicCoordinates.setDirectionLatitude(command.getDirectionLatitude());
		} else {
			placeGeographicCoordinates.setDirectionLatitude("N");
		}
		
		if(command.getDegreeLongitude() != null) {
			placeGeographicCoordinates.setDegreeLongitude(command.getDegreeLongitude());
		} else {
			placeGeographicCoordinates.setDegreeLongitude(0);
		}
		if(command.getMinuteLongitude() != null) {
			placeGeographicCoordinates.setMinuteLongitude(command.getMinuteLongitude());
		} else {
			placeGeographicCoordinates.setMinuteLongitude(0);
		}
		
		if(command.getSecondLongitude() != null) {
			placeGeographicCoordinates.setSecondLongitude(command.getSecondLongitude());
		} else {
			placeGeographicCoordinates.setSecondLongitude(0);
		}
		
		if(command.getDirectionLongitude() != null) {
			placeGeographicCoordinates.setDirectionLongitude(command.getDirectionLongitude());
		} else {
			placeGeographicCoordinates.setDirectionLongitude("E");
		}
		
		try{
			Place place = null;
			if(command.getPlaceGeographicCoordinatesId() == null || command.getPlaceGeographicCoordinatesId().equals(0)){
				place = getGeoBaseService().addNewPlaceGeographicCoordinates(placeGeographicCoordinates);
			}else{
				place = getGeoBaseService().editPlaceGeographicCoordinates(placeGeographicCoordinates);
			}
			model.put("place", place);
			
			model.put("topicsPlace", getGeoBaseService().findNumberOfTopicsPlace(place.getPlaceAllId()));
			model.put("docInTopics", getGeoBaseService().findNumberOfDocumentsInTopicsPlace(place.getPlaceAllId()));
			model.put("senderPlace", getGeoBaseService().findNumberOfSenderDocumentsPlace(place.getPlaceAllId()));
			model.put("recipientPlace", getGeoBaseService().findNumberOfRecipientDocumentsPlace(place.getPlaceAllId()));
			model.put("birthPlace", getGeoBaseService().findNumberOfBirthInPlace(place.getPlaceAllId()));
			model.put("activeStartPlace", getGeoBaseService().findNumberOfActiveStartInPlace(place.getPlaceAllId()));
			model.put("deathPlace", getGeoBaseService().findNumberOfDeathInPlace(place.getPlaceAllId()));
			model.put("activeEndPlace", getGeoBaseService().findNumberOfActiveEndInPlace(place.getPlaceAllId()));
			
			HistoryNavigator historyNavigator = getGeoBaseService().getHistoryNavigator(place);
			model.put("historyNavigator", historyNavigator);

			return new ModelAndView("geobase/ShowPlace", model);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowGoogleMapsGeoCoorPlace");
		}
		
		
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
	public ModelAndView setupForm(@ModelAttribute("command") ShowGoogleMapsGeoCoorPlaceCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		Place place = new Place();
				
		if(command.getPlaceAllId() > 0){
			try {
				place = getGeoBaseService().findPlace(command.getPlaceAllId());
								
			} catch (ApplicationThrowable ath) {
				new ModelAndView("error/ShowGoogleMapsGeoCoorPlace", model);
			}
		}else{
			place.setPlaceAllId(0);
			place.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
		}
		if(place.getPlaceGeographicCoordinates() != null){
			Double latitude = place.getPlaceGeographicCoordinates().getMinuteLatitude().doubleValue() * 60 + place.getPlaceGeographicCoordinates().getSecondLatitude().doubleValue();
			latitude = latitude / 3600;
			latitude = place.getPlaceGeographicCoordinates().getDegreeLatitude().doubleValue() + latitude;
			if(place.getPlaceGeographicCoordinates().getDirectionLatitude().equals("S")){
				model.put("latitude", "-" + latitude.toString());
			}else{
				model.put("latitude", latitude.toString());
			}
			Double longitude = place.getPlaceGeographicCoordinates().getMinuteLongitude().doubleValue() * 60 + place.getPlaceGeographicCoordinates().getSecondLongitude().doubleValue();
			longitude = longitude / 3600;
			longitude = place.getPlaceGeographicCoordinates().getDegreeLongitude().doubleValue() + longitude;
			if(place.getPlaceGeographicCoordinates().getDirectionLongitude().equals("W")){
				model.put("longitude", "-" + longitude.toString());
			}else{
				model.put("longitude", longitude.toString());
			}
			
			command.setPlaceGeographicCoordinatesId(place.getPlaceGeographicCoordinates().getId());
		}else{
			command.setPlaceGeographicCoordinatesId(0);
		}
		
		model.put("place", place);

		return new ModelAndView("geobase/ShowGoogleMapsGeoCoorPlace", model);
	}
}