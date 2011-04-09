/*
 * EditSpousePersonController.java
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

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.command.peoplebase.EditSpousePersonCommand;
import org.medici.docsources.domain.Marriage;
import org.medici.docsources.domain.Marriage.MarriageTerm;
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
 * Controller for action "Edit Spouse Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditSpousePerson")
public class EditSpousePersonController {
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditSpousePersonCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Marriage marriage = new Marriage(command.getMarriageId());
			marriage.setStartYear(command.getStartYear());
			marriage.setEndYear(command.getEndYear());
			marriage.setWife(new People(command.getWifeId()));
			marriage.setHusband(new People(command.getHusbandId()));
			if (StringUtils.isBlank(command.getMarriageTerm())) {
				marriage.setMarTerm(null);
			} else {
				marriage.setMarTerm(MarriageTerm.valueOf(command.getMarriageTerm()));
			}

			try {
				if (marriage.getMarriageId().equals(0)) {
					getPeopleBaseService().addNewMarriagePerson(marriage);
				} else {
					getPeopleBaseService().editMarriagePerson(marriage);
				}

			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditSpousePerson", model);
			}

			return new ModelAndView("peoplebase/EditSpousesPerson", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditSpousePersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		if ((command != null) && ((command.getWifeId() > 0) || (command.getHusbandId() > 0))) {

			if (command.getMarriageId().equals(0)) {
				command.setStartYear(null);
				command.setEndYear(null);
			} else {
				try {
					Marriage marriage = getPeopleBaseService().findMarriagePerson(command.getMarriageId());

					command.setStartYear(marriage.getStartYear());
					command.setEndYear(marriage.getEndYear());
					command.setMarriageTerm(marriage.getMarTerm().toString());
					command.setWifeId(marriage.getWife().getPersonId());
					command.setHusbandId(marriage.getHusband().getPersonId());

					return new ModelAndView("peoplebase/EditSpousePerson", model);
				} catch (ApplicationThrowable applicationThrowable) {
					return new ModelAndView("error/EditSpousePerson", model);
				}
			}

		} else {
			// On Name creation, every field is null
		}

		return new ModelAndView("peoplebase/EditSpousePerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}