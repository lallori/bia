/*
 * EditNameOrNameVariantPlaceController.java
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

import javax.validation.Valid;

import org.medici.docsources.command.geobase.EditNameOrNameVariantPlaceCommand;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
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
 * Controller for action "Place: Edit Name or Name Variant".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/geobase/EditNameOrNameVariantPlace")
public class EditNameOrNameVariantPlaceController {
	@Autowired
	private GeoBaseService geoBaseService;
	@Autowired(required = false)
	@Qualifier("editDetailsPlaceValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditNameOrNameVariantPlaceCommand command, BindingResult result) {
		//TODO: implement the method
		
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			
			getGeoBaseService();

			return new ModelAndView("geobase/ShowDetailsPlace", model);
		}

	}

	/**
	 * @param geoBaseService the geoBaseService to set
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
	public ModelAndView setupForm(@ModelAttribute("command") EditNameOrNameVariantPlaceCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<PlaceType> placeTypes;
		if((command != null) && (command.getPlaceAllId() > 0)){
			if(command.getCurrentPlaceAllId().equals(0)){
				command.setLatitude(null);
				command.setLongitude(null);
				command.setPlName(null);
				
			}else{
				try{
					Place place = getGeoBaseService().findPlace(command.getCurrentPlaceAllId());
				
					command.setPlName(place.getPlaceName());
					command.setPlType(place.getPlType());
					command.setLatitude(place.getPlaceGeographicCoordinates().getDegreeLatitude());
					command.setLongitude(place.getPlaceGeographicCoordinates().getDegreeLongitude());
				}catch(ApplicationThrowable ath){
					return new ModelAndView("error/EditNameOrNameVariantPlace" , model);
				}
			}
			
			try {
				placeTypes = getGeoBaseService().findPlaceTypes();
				model.put("placeTypes", placeTypes);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditNameOrNameVariantPlace", model);
			}
			
			return new ModelAndView("geobase/EditNameOrNameVariantPlace", model);
		}
		return new ModelAndView("geobase/EditNameOrNameVariantPlace", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}