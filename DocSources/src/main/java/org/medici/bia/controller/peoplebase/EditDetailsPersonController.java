/*
 * EditDetailsPersonController.java
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

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.peoplebase.EditDetailsPersonCommand;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Edit Details Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditDetailsPerson")
public class EditDetailsPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("editDetailsPersonValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditDetailsPersonCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			People person = new People(command.getPersonId());
			person.setResearcher(command.getResearcher());
			person.setFirst(command.getFirst());
			person.setSucNum(command.getSucNum());
			person.setMidPrefix(command.getMidPrefix());
			person.setMiddle(command.getMiddle());
			person.setLastPrefix(command.getLastPrefix());
			person.setLast(command.getLast());
			person.setPostLastPrefix(command.getPostLastPrefix());
			person.setPostLast(command.getPostLast());
			person.setGender(command.getGender());
			person.setBornYear(command.getBornYear());
			person.setBornMonth((command.getBornMonth() != null) ? new Month(command.getBornMonth()) : null);
			person.setBornDay(command.getBornDay());
			person.setBornApprox(command.getBornApprox());
			person.setBornDateBc(command.getBornDateBc());
			
			if(ObjectUtils.toString(command.getBornPlaceDescription()).equals("")){
				person.setBornPlace(null);
				person.setBornPlaceUnsure(false);
			} else {
				person.setBornPlace((command.getBornPlaceId() != null) ? new Place(command.getBornPlaceId()) : null);
				person.setBornPlaceUnsure(command.getBornPlaceUnsure());
			}
			
			if(command.getActiveStart()!= null && !command.getActiveStart().equals(""))
				person.setActiveStart(command.getActiveStart());
			else
				person.setActiveStart(null);
			
			person.setDeathYear(command.getDeathYear());
			person.setDeathMonth((command.getDeathMonth() != null) ? new Month(command.getDeathMonth()) : null);
			person.setDeathDay(command.getDeathDay());
			person.setDeathApprox(command.getDeathApprox());
			person.setDeathDateBc(command.getDeathDateBc());
			
			if(ObjectUtils.toString(command.getDeathPlaceDescription()).equals("")){
				person.setDeathPlace(null);
				person.setDeathPlaceUnsure(false);
			} else {
				person.setDeathPlace((command.getDeathPlaceId() != null) ? new Place(command.getDeathPlaceId()) : null);
				person.setDeathPlaceUnsure(command.getDeathPlaceUnsure());
			}
			
			if(command.getActiveEnd()!= null && !command.getActiveEnd().equals(""))
				person.setActiveEnd(command.getActiveEnd());
			else
				person.setActiveEnd(null);
			

			try {
				if (command.getPersonId().equals(0)) {
					person = getPeopleBaseService().addNewPerson(person);
					
					model.put("person", person);
					//MD: I set to 0 the docsRelated because in this case we have a new Person entry
					model.put("docsRelated", 0);
					
					HistoryNavigator historyNavigator = getPeopleBaseService().getHistoryNavigator(person);
					model.put("historyNavigator", historyNavigator);

					return new ModelAndView("peoplebase/ShowPerson", model);
				} else {
					person = getPeopleBaseService().editDetailsPerson(person);
					model.put("person", person);
					
					List<Marriage> marriages = getPeopleBaseService().findMarriagesPerson(person.getPersonId(), person.getGender());
					model.put("marriages", marriages);
					List<People> children = getPeopleBaseService().findChildrenPerson(person.getPersonId());
					model.put("children", children);
					Integer senderDocsRelated = getPeopleBaseService().findNumberOfSenderDocumentsRelated(person.getPersonId());
					model.put("senderDocsRelated", senderDocsRelated);
					Integer recipientDocsRelated = getPeopleBaseService().findNumberOfRecipientDocumentsRelated(person.getPersonId());
					model.put("recipientDocsRelated", recipientDocsRelated);
					Integer referringDocsRelated = getPeopleBaseService().findNumberOfReferringDocumentsRelated(person.getPersonId());
					model.put("referringDocsRelated", referringDocsRelated);
					Integer docsRelated = senderDocsRelated + recipientDocsRelated + referringDocsRelated;
					model.put("docsRelated", docsRelated);
					
					HistoryNavigator historyNavigator = getPeopleBaseService().getHistoryNavigator(person);
					model.put("historyNavigator", historyNavigator);

					return new ModelAndView("peoplebase/ShowPerson", model);
				}
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsPerson", model);
			}
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
	public ModelAndView setupForm(@ModelAttribute("command") EditDetailsPersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		List<Month> months = null;
		try {
			months = getPeopleBaseService().getMonths();
			model.put("months", months);

			// We need genders enumeration to populate relative combo-box
			model.put("genders", People.Gender.values());
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/EditDetailsPerson", model);
		}

		
		if ((command != null) && (command.getPersonId() > 0)) {
			People person = new People();
	
			try {
				person = getPeopleBaseService().findPerson(command.getPersonId());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsPerson", model);
			}
	
			try {
				BeanUtils.copyProperties(command, person);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}

			if (person.getBornPlace() != null) {
				command.setBornPlaceDescription(person.getBornPlace().getPlaceNameFull());
				command.setBornPlaceId(person.getBornPlace().getPlaceAllId());
			}
			if (person.getDeathPlace() != null) {
				command.setDeathPlaceDescription(person.getDeathPlace().getPlaceNameFull());
				command.setDeathPlaceId(person.getDeathPlace().getPlaceAllId());
			}

			if (command.getBornYear() == 0) {
				command.setBornYear(null);
			}
			if (person.getBornMonth() != null) {
				command.setBornMonth(person.getBornMonth().getMonthNum());
			}
			if (command.getBornDay() == 0) {
				command.setBornDay(null);
			}
			if (command.getDeathYear() == 0) {
				command.setDeathYear(null);
			}
			if (person.getDeathMonth() != null) {
				command.setDeathMonth(person.getDeathMonth().getMonthNum());
			}
			if (command.getDeathDay() == 0) {
				command.setDeathDay(null);
			}
		} else {
			// On Volume creation, the research is always the current user.
			command.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			// We need to expose dateCreated field because it must be rendered on view
			command.setDateCreated(new Date());
		}

		return new ModelAndView("peoplebase/EditDetailsPerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}