/*
 * EditParentsPersonController.java
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
import java.util.Set;

import javax.validation.Valid;

import org.medici.bia.command.peoplebase.EditParentsPersonCommand;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Parent;
import org.medici.bia.domain.People;
import org.medici.bia.domain.People.Gender;
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
 * Controller for action "Edit Parents Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/EditParentsPerson")
public class EditParentsPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("editParentsPersonValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditParentsPersonCommand command, BindingResult result){
		getValidator().validate(command, result);
		
		if(result.hasErrors()){
			return setupForm(command);
		}else{
			Map<String, Object> model = new HashMap<String, Object>();
			
			Parent parentFather = new Parent(command.getFatherRecordId());
			parentFather.setParent(new People(command.getFatherPersonId()));
			parentFather.setChild(new People(command.getPersonId()));
			
			Parent parentMother = new Parent(command.getMotherRecordId());
			parentMother.setParent(new People(command.getMotherPersonId()));
			parentMother.setChild(new People(command.getPersonId()));
			
			try{
				if(command.getFatherRecordId().equals(0)){
					if(command.getFatherPersonId() != null){
						parentFather = getPeopleBaseService().addNewFatherPerson(parentFather);
					}
				}else{
					if(command.getFatherPersonId() == null){
						getPeopleBaseService().deleteFatherFromPerson(parentFather);
					}else{
						parentFather = getPeopleBaseService().editFatherPerson(parentFather);
					}
				}
				
				if(command.getMotherRecordId().equals(0)){
					if(command.getMotherPersonId() != null){
						parentMother = getPeopleBaseService().addNewMotherPerson(parentMother);
					}
				}else{
					if(command.getMotherPersonId() == null){
						getPeopleBaseService().deleteMotherFromPerson(parentMother);
					}else{
						parentMother = getPeopleBaseService().editMotherPerson(parentMother);
					}
				}
				
				model.put("person", parentFather.getChild());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditParentsPerson", model);
			}
			
			return new ModelAndView("peoplebase/ShowPerson", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditParentsPersonCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		List<Month> months = null;
		try {
			months = getPeopleBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/EditParentsPerson", model);
		}
		
		if ((command != null) && (command.getPersonId() > 0)) {
			try {
				People person = getPeopleBaseService().findPerson(command.getPersonId());
				model.put("person", person);
				
				Set<Parent> parents = person.getParents();
				if(parents.size() > 0){
					for (Parent parent: parents) {
						if (parent.getParent().getGender().equals(Gender.M)) {
							model.put("father", parent);
							command.setFatherPersonId(parent.getParent().getPersonId());
							command.setFatherRecordId(parent.getId());
							command.setFatherDescription(parent.getParent().toString());
							command.setBioNotesFather(parent.getParent().getBioNotes());
							command.setBornYearFather(parent.getParent().getBornYear());
							if (parent.getParent().getBornMonth()!= null){
								command.setBornMonthNumFather(parent.getParent().getBornMonth().getMonthNum());
								command.setBornMonthFather(parent.getParent().getBornMonth().getMonthName());
							}
							command.setBornDayFather(parent.getParent().getBornDay());
							command.setDeathYearFather(parent.getParent().getDeathYear());
							if (parent.getParent().getDeathMonth() != null) {
								command.setDeathMonthNumFather(parent.getParent().getDeathMonth().getMonthNum());
								command.setDeathMonthFather(parent.getParent().getDeathMonth().getMonthName());
							}
							command.setDeathDayFather(parent.getParent().getDeathDay());
						}else if (parent.getParent().getGender().equals(Gender.F)) {
							model.put("mother", parent);
							command.setMotherPersonId(parent.getParent().getPersonId());
							command.setMotherRecordId(parent.getId());
							command.setMotherDescription(parent.getParent().toString());
							command.setBioNotesMother(parent.getParent().getBioNotes());
							command.setBornYearMother(parent.getParent().getBornYear());
							if (parent.getParent().getBornMonth()!= null){
								command.setBornMonthNumMother(parent.getParent().getBornMonth().getMonthNum());
								command.setBornMonthMother(parent.getParent().getBornMonth().getMonthName());
							}
							command.setBornDayMother(parent.getParent().getBornDay());
							command.setDeathYearMother(parent.getParent().getDeathYear());
							if (parent.getParent().getDeathMonth() != null) {
								command.setDeathMonthNumMother(parent.getParent().getDeathMonth().getMonthNum());
								command.setDeathMonthMother(parent.getParent().getDeathMonth().getMonthName());
							}
							command.setDeathDayMother(parent.getParent().getDeathDay());
						}
						if(command.getFatherPersonId() == null){
							command.setFatherRecordId(0);
						}
						if(command.getMotherPersonId() == null){
							command.setMotherRecordId(0);
						}
					}
				}else{
					command.setFatherRecordId(0);
					command.setMotherRecordId(0);
				}

			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditParentsPerson", model);
			}

		} else {
			model.put("person", new People(0));
		}

		return new ModelAndView("peoplebase/EditParentsPerson", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}