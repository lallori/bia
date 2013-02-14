/*
 * ModifyPersonValidator.java
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
package org.medici.bia.validator.peoplebase;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.command.peoplebase.EditDetailsPersonCommand;
import org.medici.bia.domain.People;
import org.medici.bia.domain.People.Gender;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditDetailsPersonValidator implements Validator {
	@Autowired
	private PeopleBaseService peopleBaseService;

	/**
	 * 
	 * @return
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * 
	 * @param peopleBaseService
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyPersonCommand.
	 * 
	 * @param givenClass
	 *            the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(EditDetailsPersonCommand.class);
	}

	/**
	 * Validate the supplied target object, which must be of a Class for which
	 * the supports(Class) method typically has (or would) return true. The
	 * supplied errors instance can be used to report any resulting validation
	 * errors.
	 * 
	 * @param object the object that is to be validated (can be null)
	 * @param errors contextual state about the validation process (never null)
	 */
	public void validate(Object object, Errors errors) {
		EditDetailsPersonCommand editDetailsPersonCommand = (EditDetailsPersonCommand) object;
		validatePersonId(editDetailsPersonCommand.getPersonId(), errors);
		validateDates(editDetailsPersonCommand.getBornYear(), editDetailsPersonCommand.getActiveStart(), editDetailsPersonCommand.getBornMonth(), editDetailsPersonCommand.getBornDay(), editDetailsPersonCommand.getDeathYear(), editDetailsPersonCommand.getActiveEnd(), editDetailsPersonCommand.getDeathMonth(), editDetailsPersonCommand.getDeathDay(), errors);
		validateGender(editDetailsPersonCommand.getPersonId(), editDetailsPersonCommand.getGender(), errors);
	}

	/**
	 * 
	 * @param personId
	 * @param errors
	 */
	public void validatePersonId(Integer personId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId",
		"error.personId.null");

		if (!errors.hasErrors()) {
			if (personId > 0) {
				try {
					if (getPeopleBaseService().findPerson(personId) == null) {
						errors.reject("peopleId", "error.peopleId.notfound");
					}
				} catch (ApplicationThrowable ath) {
					
				}
			}
		}
	}
	
	private void validateDates(Integer bornYear, String activeStart, Integer bornMonthNum, Integer bornDay, Integer deathYear, String activeEnd, Integer deathMonthNum, Integer deathDay,Errors errors) {
		if (!errors.hasErrors()) {
			if (bornYear != null) {
				if (bornYear > 1750) {
					errors.rejectValue("bornYear", "error.bornYear.invalid");
				}
				if(deathYear != null && bornYear > deathYear){
					errors.rejectValue("bornYear", "error.bornYear.order");
				}
			}
			if(activeStart != null && NumberUtils.isNumber(activeStart)){
				if(NumberUtils.createInteger(activeStart) > 1750){
					errors.rejectValue("activeStart", "error.activeStart.invalid");
				}
				if(activeEnd != null && NumberUtils.isNumber(activeEnd)){
					if(NumberUtils.createInteger(activeStart) > NumberUtils.createInteger(activeEnd)){
						errors.rejectValue("activeStart", "error.activeStart.order");
					}
				}
			}
			if (bornMonthNum != null) {
				if ((bornMonthNum <1) || (bornMonthNum >12)) {
					errors.rejectValue("bornMonth", "error.bornMonthNum.invalid");
				}
				if((deathMonthNum != null && bornYear != null && deathYear != null) && (bornYear.equals(deathYear) && bornMonthNum > deathMonthNum)){
					errors.rejectValue("bornMonth", "error.bornMonthNum.order");
				}				
			}
			if (bornDay != null) {
				if ((bornDay < 0) || (bornDay > 31)) {
					errors.rejectValue("bornDay", "error.bornDay.invalid");
				}
				if((deathDay != null && bornYear != null && deathYear != null && bornMonthNum != null && deathMonthNum != null) && (bornYear.equals(deathYear) && bornMonthNum.equals(deathMonthNum) && bornDay > deathDay)){
					errors.rejectValue("bornDay", "error.bornDay.order");
				}
			}
			if (deathYear != null) {
				if (deathYear > 1850) {
					errors.rejectValue("deathYear", "error.deathYear.invalid");
				}
			}
			if(activeEnd != null && NumberUtils.isNumber(activeEnd)){
				if(NumberUtils.createInteger(activeEnd) > 1850){
					errors.rejectValue("activeEnd", "error.activeEnd.invalid");
				}
			}
			if (deathMonthNum != null) {
				if ((deathMonthNum <1) || (deathMonthNum >12)) {
					errors.rejectValue("deathMonth", "error.deathMonthNum.invalid");
				}
			}
			if (deathDay != null) {
				if ((deathDay < 0) || (deathDay > 31)) {
					errors.rejectValue("deathDay", "error.deathDay.invalid");
				}
			}
		}
	}
	
	public void validateGender(Integer personId, Gender gender, Errors errors){
		if(!errors.hasErrors()){
			if(personId > 0){
				try{
					People person = getPeopleBaseService().findPerson(personId);
					if(!person.getGender().equals(gender)){
						if(person.getGender().equals(Gender.M)){
							if((person.getParents() != null && person.getParents().size() > 0) || (person.getChildren() != null && person.getChildren().size() > 0) || (person.getMarriagesAsHusband() != null && person.getMarriagesAsHusband().size() > 0)){
								errors.rejectValue("gender", "error.gender.invalid");
							}								
						}
						if(person.getGender().equals(Gender.F)){
							if((person.getParents() != null && person.getParents().size() > 0) || (person.getChildren() != null && person.getChildren().size() > 0) || (person.getMarriagesAsWife() != null && person.getMarriagesAsWife().size() > 0)){
								errors.rejectValue("gender", "error.gender.invalid");
							}
						}
					}
				}catch(ApplicationThrowable ath){
					
				}
			}
		}
	}
}
