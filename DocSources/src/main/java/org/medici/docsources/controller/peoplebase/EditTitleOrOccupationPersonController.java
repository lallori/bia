/*
 * EditTitleOrOccupationsPersonController.java
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

import org.medici.docsources.command.peoplebase.EditTitleOrOccupationPersonCommand;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.PoLink;
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
 * Controller for action "Edit single Title Or Occupation Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditTitleOrOccupationPerson")
public class EditTitleOrOccupationPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("editTitleOrOccupationPersonValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditTitleOrOccupationPersonCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			/** TODO : Implement invocation business logic */
			getPeopleBaseService();

			return new ModelAndView("peoplebase/modifyperson", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditTitleOrOccupationPersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		if ((command != null) && (command.getPersonId() > 0)) {
			try {
				List<Month> months = getPeopleBaseService().getMonths();
				model.put("months", months);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/ShowDocument", model);
			}

			if (command.getPrfLinkId().equals(0)) {
				
				command.setTitleOccId(null);
				command.setTitleOrOccupationDescription(null);
				command.setStartYear(null);
				command.setStartMonth(null);
				command.setStartDay(null);
				command.setEndYear(null);
				command.setEndMonth(null);
				command.setEndDay(null);
				command.setPreferredRole(null);
			} else {
				try {
					PoLink poLink = getPeopleBaseService().findTitleOrOccupationPerson(command.getPersonId(), command.getPrfLinkId());

					if (poLink.getTitleOccList() != null) {
						command.setTitleOrOccupationDescription(poLink.getTitleOccList().getTitleOcc());
						command.setTitleOccId(poLink.getTitleOccList().getTitleOccId());
						command.setPreferredRole(poLink.getPreferredRole());
						command.setStartYear(poLink.getStartYear());
						command.setStartMonth(poLink.getStartMonth());
						command.setStartDay(poLink.getStartDay());
						command.setStartApprox(poLink.getStartApprox());
						command.setStartUns(poLink.getStartUns());
						command.setEndYear(poLink.getEndYear());
						command.setEndMonth(poLink.getEndMonth());
						command.setEndDay(poLink.getEndDay());
						command.setEndApprox(poLink.getEndApprox());
						command.setEndUns(poLink.getEndUns());
					} else {
						command.setTitleOrOccupationDescription(null);
						command.setTitleOrOccupationDescription(null);
					}

					return new ModelAndView("peoplebase/EditTitleOrOccupationPerson", model);
				} catch (ApplicationThrowable applicationThrowable) {
					return new ModelAndView("error/EditTopicDocument", model);
				}
			}

		} else {
			// On Title creation, every field is null
		}

		return new ModelAndView("peoplebase/EditTitleOrOccupationPerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}