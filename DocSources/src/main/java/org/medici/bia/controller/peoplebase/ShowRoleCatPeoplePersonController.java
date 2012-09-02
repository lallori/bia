/*
 * ShowRoleCatPeoplePersonController.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.command.peoplebase.ShowRoleCatPeoplePersonCommand;
import org.medici.bia.domain.RoleCat;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show role categories people".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/peoplebase/ShowRoleCatPeoplePerson")
public class ShowRoleCatPeoplePersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	
	
	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}


	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}


	/**
	 * 
	 * @param placeId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowRoleCatPeoplePersonCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		
		
		if(command.getRoleCatId() > 0){
			try{
				RoleCat roleCat = getPeopleBaseService().findRoleCat(command.getRoleCatId());
				List<String> outputFields = new ArrayList<String>(5);
				outputFields.add("Name");
				outputFields.add("Gender");
				outputFields.add("Born Date");
				outputFields.add("Death Date");
				
				model.put("outputFields", outputFields);

				
				model.put("roleCatId", roleCat.getRoleCatId());
				model.put("roleCat", roleCat.getRoleCatMinor());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowRoleCatPeoplePerson", model);
			}
		}

		return new ModelAndView("peoplebase/ShowRoleCatPeoplePerson", model);
	}

}