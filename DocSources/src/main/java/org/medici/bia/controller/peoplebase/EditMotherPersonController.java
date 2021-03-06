/*
 * EditMotherPersonController.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.medici.bia.command.peoplebase.EditMotherPersonCommand;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Parent;
import org.medici.bia.domain.People;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
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
 * Controller for action "Edit Mother Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditMotherPerson")
public class EditMotherPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("editMotherPersonValidator")
	private Validator validator;

	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
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

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditMotherPersonCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			Parent parent = new Parent(command.getId());
			parent.setParent(new People(command.getParentId()));
			parent.setChild(new People(command.getChildId()));

			try {
				if (command.getId().equals(0)) {
					parent = getPeopleBaseService().addNewMotherPerson(parent);
				} else {
					parent = getPeopleBaseService().editMotherPerson(parent);
				}
				model.put("person", parent.getChild());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditFatherPerson", model);
			}

			return new ModelAndView("peoplebase/ShowParentsPerson", model);
		}

	}

	/**
	 * @param peopleBaseService
	 *            the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditMotherPersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		List<Month> months = null;
		try {
			months = getPeopleBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/EditMotherPerson", model);
		}
		
		if ((command != null) && (command.getId() != null)) {
			try {
				Parent parent = getPeopleBaseService().findParent(command.getId());
				
				if(parent != null){				
					command.setMotherDescription(parent.getParent().toString());
					command.setBioNotes(parent.getParent().getBioNotes());
					command.setBornYear(parent.getParent().getBornYear());
					if (parent.getParent().getBornMonth()!= null){
						command.setBornMonthNum(parent.getParent().getBornMonth().getMonthNum());
						command.setBornMonth(parent.getParent().getBornMonth().getMonthName());
					}
					command.setBornDay(parent.getParent().getBornDay());
					command.setDeathYear(parent.getParent().getDeathYear());
					if (parent.getParent().getDeathMonth() != null) {
						command.setDeathMonthNum(parent.getParent().getDeathMonth().getMonthNum());
						command.setDeathMonth(parent.getParent().getDeathMonth().getMonthName());
					}
					command.setDeathDay(parent.getParent().getDeathDay());
				}

			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditMotherPerson", model);
			}
		} else {
			command.setId(0);
		}

		return new ModelAndView("peoplebase/EditMotherPerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}