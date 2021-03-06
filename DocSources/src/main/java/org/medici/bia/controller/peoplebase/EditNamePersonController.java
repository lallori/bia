/*
 * EditNamePersonController.java
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

import org.medici.bia.command.peoplebase.EditNamePersonCommand;
import org.medici.bia.domain.AltName;
import org.medici.bia.domain.AltName.NameType;
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
 * Controller for action "Edit Name Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditNamePerson")
public class EditNamePersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("editNamePersonValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditNamePersonCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			AltName altName = new AltName(command.getNameId(), command.getPersonId());
			altName.setAltName(command.getAltName());
			altName.setNamePrefix(command.getNamePrefix());
			altName.setNameType(NameType.valueOf(command.getNameType()).toString());

			try {
				if (command.getNameId().equals(0)) {
					getPeopleBaseService().addNewAltNamePerson(altName);
				} else {
					getPeopleBaseService().editNamePerson(altName);
				}
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowPerson", model);
			}

			return new ModelAndView("peoplebase/EditNamesPerson", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditNamePersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getPersonId() > 0)) {

			if (command.getNameId().equals(0)) {
				command.setNameType(null);
				command.setNamePrefix(null);
				command.setAltName(null);
			} else {
				try {
					AltName altName = getPeopleBaseService().findAltNamePerson(command.getPersonId(), command.getNameId());

					command.setNameType(altName.getNameType());
					command.setNamePrefix(altName.getNamePrefix());
					command.setAltName(altName.getAltName());
					
				} catch (ApplicationThrowable applicationThrowable) {
					return new ModelAndView("error/EditNamePerson", model);
				}
			}
			// We need nameType enumeration to populate relative combo-box
			model.put("nameTypes", AltName.NameType.values());

			return new ModelAndView("peoplebase/EditNamePerson", model);
		}

		return new ModelAndView("peoplebase/EditNamePerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}