/*
 * EditNamesOrNameVariantsPlaceController.java
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

import javax.validation.Valid;

import org.medici.bia.command.geobase.EditGeographicCoordinatesPlaceCommand;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.PlaceGeographicCoordinates;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Place: Edit Names or Name Variants".
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a
 *         href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/geobase/EditGeographicCoordinatesPlace")
public class EditGeographicCoordinatesPlaceController {
	@Autowired
	private GeoBaseService geoBaseService;
	@Autowired(required = false)
	@Qualifier("editGeographicCoordinatesPlaceValidator")
	private Validator validator;

	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}

	/**
	 * This method returns the Validator class used by Controller to make
	 * business validation.
	 * 
	 * @return
	 */
	public Validator getValidator() {
		return validator;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditGeographicCoordinatesPlaceCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			PlaceGeographicCoordinates placeGeographicCoordinates;
			try {
				placeGeographicCoordinates = getGeoBaseService().findPlaceGeographicCoordinates(command.getPlaceAllId());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditGeographicCoordinates", model);
			}

			if (placeGeographicCoordinates.getPlace() == null) {
				placeGeographicCoordinates.setPlace(new Place(command.getPlaceAllId()));
			}
			
			if (command.getDegreeLatitude() != null) {
				placeGeographicCoordinates.setDegreeLatitude(command.getDegreeLatitude());
			} else {
				placeGeographicCoordinates.setDegreeLatitude(0);
			}
			
			if (command.getMinuteLatitude() != null) {
				placeGeographicCoordinates.setMinuteLatitude(command.getMinuteLatitude());
			} else {
				placeGeographicCoordinates.setMinuteLatitude(0);
			}
			
			if (command.getSecondLatitude() != null) {
				placeGeographicCoordinates.setSecondLatitude(command.getSecondLatitude());
			} else {
				placeGeographicCoordinates.setSecondLatitude(0f);
			}

			if (!command.getDirectionLatitude().equals("")) {
				placeGeographicCoordinates.setDirectionLatitude(command.getDirectionLatitude().toUpperCase());
			} else {
				placeGeographicCoordinates.setDirectionLatitude("N");
			}
			
			if (command.getDegreeLongitude() != null) {
				placeGeographicCoordinates.setDegreeLongitude(command.getDegreeLongitude());
			} else {
				placeGeographicCoordinates.setDegreeLongitude(0);
			}
			
			if (command.getMinuteLongitude() != null) {
				placeGeographicCoordinates.setMinuteLongitude(command.getMinuteLongitude());
			} else {
				placeGeographicCoordinates.setMinuteLongitude(0);
			}
			
			if (command.getSecondLongitude() != null) {
				placeGeographicCoordinates.setSecondLongitude(command.getSecondLongitude());
			} else {
				placeGeographicCoordinates.setSecondLongitude(0f);
			}
			
			if (!command.getDirectionLongitude().equals("")) {
				placeGeographicCoordinates.setDirectionLongitude(command.getDirectionLongitude().toUpperCase());
			} else {
				placeGeographicCoordinates.setDirectionLongitude("E");
			}

			try {
				Place place = null;
				if (command.getDegreeLatitude() != null || command.getMinuteLatitude() != null || command.getSecondLatitude() != null || !command.getDirectionLatitude().equals("")
						|| command.getSecondLongitude() != null || command.getMinuteLongitude() != null || command.getDegreeLongitude() != null || !command.getDirectionLongitude().equals("")) {
					if (placeGeographicCoordinates.getId() == null || placeGeographicCoordinates.getId().equals(0)) {
						place = getGeoBaseService().addNewPlaceGeographicCoordinates(placeGeographicCoordinates);
					} else {
						place = getGeoBaseService().editPlaceGeographicCoordinates(placeGeographicCoordinates);
					}
				} else {
					place = getGeoBaseService().findPlace(command.getPlaceAllId());
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

				if (place.getPlaceGeographicCoordinates() != null) {
					model.put("linkGoogleMaps", HtmlUtils.generateLinkGoogleMaps(place.getPlaceGeographicCoordinates()));
				} else {
					model.put("linkGoogleMaps", null);
				}

				HistoryNavigator historyNavigator = getGeoBaseService().getHistoryNavigator(place);
				model.put("historyNavigator", historyNavigator);
				
				if(getGeoBaseService().ifPlaceAlreadyPresentInMarkedList(place.getPlaceAllId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}

				return new ModelAndView("geobase/ShowPlace", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowGeographicCoordinatesPlace", model);
			}
		}

	}

	/**
	 * @param geoBaseService
	 *            the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditGeographicCoordinatesPlaceCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getPlaceAllId() > 0)) {
			PlaceGeographicCoordinates placeGeographicCoordinates = new PlaceGeographicCoordinates();

			try {
				placeGeographicCoordinates = getGeoBaseService().findPlaceGeographicCoordinates(command.getPlaceAllId());

				command.setPlaceGeographicCoordinatesId(placeGeographicCoordinates.getId());
			} catch (ApplicationThrowable th) {
				return new ModelAndView("error/EditGeographicCoordinates", model);
			}
			command.setDegreeLatitude(placeGeographicCoordinates.getDegreeLatitude());
			command.setMinuteLatitude(placeGeographicCoordinates.getMinuteLatitude());
			command.setSecondLatitude(placeGeographicCoordinates.getSecondLatitude());
			command.setDirectionLatitude(placeGeographicCoordinates.getDirectionLatitude());
			command.setDegreeLongitude(placeGeographicCoordinates.getDegreeLongitude());
			command.setMinuteLongitude(placeGeographicCoordinates.getMinuteLongitude());
			command.setSecondLongitude(placeGeographicCoordinates.getSecondLongitude());
			command.setDirectionLongitude(placeGeographicCoordinates.getDirectionLongitude());
		} else {
			command.setPlaceGeographicCoordinatesId(0);
		}

		return new ModelAndView("geobase/EditGeographicCoordinatesPlace", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}