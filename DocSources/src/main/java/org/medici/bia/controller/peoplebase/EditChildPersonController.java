/*
 * EditChildPersonController.java
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
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.peoplebase.EditChildPersonCommand;
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
 * Controller for action "Edit Child Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditChildPerson")
public class EditChildPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("editChildPersonValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditChildPersonCommand command, BindingResult result) {
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
					parent = getPeopleBaseService().addNewChildPerson(parent);
				} else {
					parent = getPeopleBaseService().editChildPerson(parent);
				}
				model.put("person", parent.getParent());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditChildPerson", model);
			}

			// We return block for edit Children
			return new ModelAndView("peoplebase/ShowChildrenPerson", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditChildPersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if (command.getId().equals(0)) {
			command.setChildId(null);
			command.setChildDescription(null);
			command.setBornYear(null);
			command.setDeathYear(null);
		} else {
			try {
				Parent parent = getPeopleBaseService().findParentPerson(command.getId());

				command.setChildId(parent.getChild().getPersonId());
				command.setChildDescription(parent.getChild().getMapNameLf());
				command.setBornYear(parent.getChild().getBornYear());
				command.setDeathYear(parent.getChild().getDeathYear());
				//Calculate age at death only if bornYear and deathYear are not null
				if ((!ObjectUtils.toString(command.getBornYear()).equals("")) && (!ObjectUtils.toString(command.getDeathYear()).equals(""))) {
					command.setAgeAtDeath(command.getDeathYear() - command.getBornYear());
				} else {
					command.setAgeAtDeath(null);
				}

				return new ModelAndView("peoplebase/EditChildPerson", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditChildPerson", model);
			}
		}

		return new ModelAndView("peoplebase/EditChildPerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}