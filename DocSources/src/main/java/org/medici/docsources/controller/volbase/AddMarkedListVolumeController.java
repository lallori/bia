/*
 * AddMarkedListVolumeController.java
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
package org.medici.docsources.controller.volbase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.docsources.command.volbase.AddMarkedListVolumeCommand;
import org.medici.docsources.domain.UserMarkedList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.usermarkedlist.UserMarkedListService;
import org.medici.docsources.service.volbase.VolBaseService;
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
 * Controller for action "Add marked list volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/volbase/AddMarkedListVolume")
public class AddMarkedListVolumeController {
	@Autowired
	private VolBaseService volBaseService;
	@Autowired
	private UserMarkedListService userMarkedListService;
	@Autowired(required = false)
	@Qualifier("addMarkedListVolumeValidator")
	private Validator validator;


	/**
	 * @return the userMarkedListService
	 */
	public UserMarkedListService getUserMarkedListService() {
		return userMarkedListService;
	}


	/**
	 * 
	 * @param placeId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView setupForm(@ModelAttribute("requestCommand") AddMarkedListVolumeCommand command, BindingResult result) {
		getValidator().validate(command, result);
		
		Map<String, Object> model = new HashMap<String, Object>();
		if(!result.hasErrors()){
			Volume volume = new Volume();
			
			if(command.getSummaryId() > 0){
				try {
					UserMarkedList userMarkedList = getUserMarkedListService().getMyMarkedList();
					if(userMarkedList == null){
						userMarkedList = new UserMarkedList();
						userMarkedList.setDateCreated(new Date());
						userMarkedList = getUserMarkedListService().createMyMarkedList(userMarkedList);
					}
					
					volume = getVolBaseService().findVolume(command.getSummaryId());
									
					userMarkedList = getUserMarkedListService().addNewVolumeToMarkedList(userMarkedList, volume);
					
					model.put("volume", volume);
				} catch (ApplicationThrowable applicationThrowable) {
					model.put("applicationThrowable", applicationThrowable);
					return new ModelAndView("response/MarkedListKO", model);
				}
			}
		}
		model.put("category", "volume");
		

		return new ModelAndView("response/MarkedListOK", model);
	}


	/**
	 * @param userMarkedListService the userMarkedListService to set
	 */
	public void setUserMarkedListService(UserMarkedListService userMarkedListService) {
		this.userMarkedListService = userMarkedListService;
	}


	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}


	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}


	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}


	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

}