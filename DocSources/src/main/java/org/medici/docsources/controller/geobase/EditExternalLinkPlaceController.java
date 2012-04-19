/*
 * EditExternalLinkPlaceController.java
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
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.geobase.EditExternalLinkPlaceCommand;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.PlaceExternalLinks;
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
 * Controller for action "Place: Edit External Links".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/geobase/EditExternalLinkPlace")
public class EditExternalLinkPlaceController {
	@Autowired
	private GeoBaseService geoBaseService;
	@Autowired(required = false)
	@Qualifier("editExternalLinkPlaceValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditExternalLinkPlaceCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			PlaceExternalLinks placeExternalLinks = new PlaceExternalLinks(command.getPlaceExternalLinksId());
			placeExternalLinks.setPlace(new Place(command.getPlaceAllId()));
			String link = command.getExternalLink();
			if(!link.startsWith("http") && !link.startsWith("HTTP")){
				link = "http://" + link;
			}
			placeExternalLinks.setExternalLink(link);
			placeExternalLinks.setDescription(command.getDescription());
			
			try{
				if(command.getPlaceExternalLinksId().equals(0)){
					getGeoBaseService().addNewPlaceExternalLinks(placeExternalLinks);
				}else{
					getGeoBaseService().editPlaceExternalLinks(placeExternalLinks);
				}
			}catch(ApplicationThrowable th){
				return new ModelAndView("error/EditExternalLinkPlace", model);
			}
			

			return new ModelAndView("geobase/ShowPlace", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditExternalLinkPlaceCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		if((command != null) && (command.getPlaceAllId() > 0)){
			if(command.getPlaceExternalLinksId().equals(0)){
				command.setExternalLink(null);
			}else{
				try{
					PlaceExternalLinks placeExternalLinks = getGeoBaseService().findPlaceExternalLinks(command.getPlaceAllId(), command.getPlaceExternalLinksId());
					
					if(placeExternalLinks.getExternalLink() != null){
						command.setExternalLink(placeExternalLinks.getExternalLink());
					}
					else{
						command.setExternalLink(null);
					}
					
					if(placeExternalLinks.getDescription() != null){
						command.setDescription(placeExternalLinks.getDescription());
					}else{
						command.setDescription(null);
					}
					
				}catch(ApplicationThrowable th){
					return new ModelAndView("error/EditExternalLinkPlace", model);
				}
			}
		}else{
			if (ObjectUtils.toString(command).equals("")) {
				command = new EditExternalLinkPlaceCommand();
			}
			command.setPlaceExternalLinksId(null);
			command.setExternalLink(null);
			command.setDescription(null);
		}

		return new ModelAndView("geobase/EditExternalLinkPlace", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}