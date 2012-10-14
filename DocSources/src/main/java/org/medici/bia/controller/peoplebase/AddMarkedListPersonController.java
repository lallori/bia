/*
 * AddMarkedListPersonController.java
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
package org.medici.bia.controller.peoplebase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.bia.command.peoplebase.AddMarkedListPersonCommand;
import org.medici.bia.domain.People;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.medici.bia.service.usermarkedlist.UserMarkedListService;
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
 * Controller for action "Add marked list Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/peoplebase/AddMarkedListPerson")
public class AddMarkedListPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired
	private UserMarkedListService userMarkedListService;
	@Autowired(required = false)
	@Qualifier("addMarkedListPersonValidator")
	private Validator validator;

	/**
	 * 
	 * @return
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * @return the userMarkedListService
	 */
	public UserMarkedListService getUserMarkedListService() {
		return userMarkedListService;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * 
	 * @param peopleBaseService
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * 
	 * @param peopleId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") AddMarkedListPersonCommand command, BindingResult result){
		getValidator().validate(command, result);
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		if(!result.hasErrors()){
			People person = new People();
	
			if(command.getPersonId() > 0){
				try {
					UserMarkedList userMarkedList = getUserMarkedListService().getMyMarkedList();
					if(userMarkedList == null){
						userMarkedList = new UserMarkedList();
						userMarkedList.setDateCreated(new Date());
						userMarkedList = getUserMarkedListService().createMyMarkedList(userMarkedList);
					}
					
					person = getPeopleBaseService().findPerson(command.getPersonId());
					userMarkedList = getUserMarkedListService().addNewPersonToMarkedList(userMarkedList, person);
					
					model.put("person", person);
				} catch (ApplicationThrowable applicationThrowable) {
					model.put("applicationThrowable", applicationThrowable);
					new ModelAndView("response/MarkedListKO", model);
				}
			}		
		}
		model.put("category", "person");

		return new ModelAndView("response/MarkedListOK", model);
	}

	/**
	 * @param userMarkedListService the userMarkedListService to set
	 */
	public void setUserMarkedListService(UserMarkedListService userMarkedListService) {
		this.userMarkedListService = userMarkedListService;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}