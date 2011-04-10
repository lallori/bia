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
package org.medici.docsources.controller.peoplebase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.peoplebase.EditChildPersonCommand;
import org.medici.docsources.domain.People;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
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
	@Qualifier("modifyPersonValidator")
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
			Map<String, Object> model = new HashMap<String, Object>();

			People child = new People(command.getChildId());
			child.setBornYear(command.getBornYear());
			child.setDeathYear(command.getBornYear());

			try {
				getPeopleBaseService().editChildPerson(child, command.getParentId());

				List<People> children = getPeopleBaseService().findChildrenPerson(command.getParentId());
				model.put("children", children);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/ShowDocument", model);
			}

			// We return block for edit Children
			return new ModelAndView("peoplebase/EditChildrenPerson", model);
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
		Map<String, Object> model = new HashMap<String, Object>();

		if (command.getChildId().equals(0)) {
			command.setChildId(null);
			command.setChildDescription(null);
			command.setBornYear(null);
			command.setDeathYear(null);
		} else {
			try {
				People child = getPeopleBaseService().findChildPerson(command.getParentId(), command.getChildId());

				command.setChildId(child.getPersonId());
				command.setChildDescription(child.getMapNameLf());
				command.setBornYear(child.getBornYear());
				command.setDeathYear(child.getDeathYear());
				//Calculate age at death
				if ((!ObjectUtils.toString(command.getBornYear()).equals("")) && (!ObjectUtils.toString(command.getDeathYear()).equals(""))) {
					command.setAgeAtDeath(command.getDeathYear() - command.getBornYear());
				} else {
					command.setAgeAtDeath(command.getDeathYear() - command.getBornYear());
				}

				return new ModelAndView("peoplebase/EditChildPerson", model);
			} catch (ApplicationThrowable applicationThrowable) {
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