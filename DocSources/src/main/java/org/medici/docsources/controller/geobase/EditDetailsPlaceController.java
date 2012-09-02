/*
 * EditDetailsPlaceController.java
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

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.docsources.command.geobase.EditDetailsPlaceCommand;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Edit Details Place Controller".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/geobase/EditDetailsPlace")
public class EditDetailsPlaceController {
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditDetailsPlaceCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Place place = new Place(command.getPlaceAllId());
			place.setResearcher(command.getResearcher());
			place.setGeogKey(command.getGeogKey());
			place.setPlType(command.getPlType());
			place.setPlacesMemo(command.getPlacesMemo());
			place.setPlParent(command.getPlParent());
			place.setPlaceNameId(command.getPlaceNameId());
			place.setPlaceName(command.getPlaceName());
			if(command.getTermAccent().isEmpty()){
				place.setTermAccent(command.getPlaceName());
			}else{
				place.setTermAccent(command.getTermAccent());
			}
			place.setPlSource(command.getPlSource());
			place.setPrefFlag("P");
			// TODO: complete to save the parent place
			
			if(!ObjectUtils.toString(command.getParentPlaceAllId()).equals("")){
				place.setParentPlace(new Place(command.getParentPlaceAllId()));
			}else{
				place.setParentPlace(null);
			}
						
			try{
				if(command.getPlaceAllId().equals(0)){
					place = getGeoBaseService().addNewPlace(place);
					List<Place> placeNames;
					placeNames = getGeoBaseService().findPlaceNames(place.getGeogKey());
					model.put("placeNames", placeNames);
					model.put("place", place);
					
					HistoryNavigator historyNavigator = getGeoBaseService().getHistoryNavigator(place);
					model.put("historyNavigator", historyNavigator);
					return new ModelAndView("geobase/ShowPlace", model);
				}
				else{
					place = getGeoBaseService().editDetailsPlace(place);
					List<Place> placeNames = getGeoBaseService().findPlaceNames(place.getGeogKey());
					model.put("placeNames", placeNames);
					model.put("place", place);
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
					
					HistoryNavigator historyNavigator = getGeoBaseService().getHistoryNavigator(place);
					model.put("historyNavigator", historyNavigator);
					
					return new ModelAndView("geobase/ShowPlace", model);
				}
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsPlace", model);
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
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditDetailsPlaceCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<PlaceType> placeTypes; 
		try {
			placeTypes = getGeoBaseService().findPlaceTypes();
			model.put("placeTypes", placeTypes);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/EditDetailsPlace", model);
		}

		if ((command != null) && (command.getPlaceAllId() > 0)) {
			Place place = new Place();
	
			try {
				place = getGeoBaseService().findPlace(command.getPlaceAllId());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsPlace", model);
			}
	
			try {
				BeanUtils.copyProperties(command, place);
				command.setPlParent(place.getParentPlace().getPlaceNameFull());
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}

		} else {
			// On Place creation, the research is always the current user.
			command.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			// We need to expose dateCreated field because it must be rendered on view
			command.setDateEntered(new Date());
			command.setPlaceAllId(0);
			try {
				if(!command.getPlSource().equals("TGN")){
					Integer newGeogKey = getGeoBaseService().findNewGeogKey(command.getPlSource());
					command.setGeogKey(newGeogKey);
				}
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsPlace", model);
			}
		}

		return new ModelAndView("geobase/EditDetailsPlace", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}