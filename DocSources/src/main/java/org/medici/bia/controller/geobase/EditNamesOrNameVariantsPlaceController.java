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
import java.util.List;
import java.util.Map;

import org.medici.bia.command.geobase.EditNamesOrNameVariantsPlaceCommand;
import org.medici.bia.domain.Place;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Place: Edit Names or Name Variants".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/geobase/EditNamesOrNameVariantsPlace")
public class EditNamesOrNameVariantsPlaceController {
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

	/*
	 * @RequestMapping(method = RequestMethod.POST) public ModelAndView
	 * processSubmit(@Valid @ModelAttribute("command")
	 * EditNamesOrNameVariantsPlaceCommand command, BindingResult result) {
	 * getValidator().validate(command, result);
	 * 
	 * if (result.hasErrors()) { return setupForm(command); } else { Map<String,
	 * Object> model = new HashMap<String, Object>(0);
	 * 
	 * 
	 * getGeoBaseService();
	 * 
	 * return new ModelAndView("geobase/ShowDetailsPlace", model); }
	 * 
	 * }
	 */

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
	public ModelAndView setupForm(@ModelAttribute("command") EditNamesOrNameVariantsPlaceCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getPlaceAllId() > 0)) {
			List<Place> placeNames;
			try {
				placeNames = getGeoBaseService().findPlaceNames(command.getGeogKey());
				model.put("placeNames", placeNames);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditNamesOrNameVariantsPlace", model);
			}
		}

		return new ModelAndView("geobase/EditNamesOrNameVariantsPlace", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}