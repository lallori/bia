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
package org.medici.docsources.validator.peoplebase;

import org.medici.docsources.command.peoplebase.EditDetailsPersonCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
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
		EditDetailsPersonCommand modifyPersonCommand = (EditDetailsPersonCommand) object;
		validatePersonId(modifyPersonCommand.getPersonId(), errors);
		validateDates(modifyPersonCommand.getBornYear(), modifyPersonCommand.getBornMonth(), modifyPersonCommand.getBornDay(), modifyPersonCommand.getDeathYear(), modifyPersonCommand.getDeathMonth(), modifyPersonCommand.getDeathDay(), errors);
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
	
	private void validateDates(Integer bornYear, Integer bornMonthNum, Integer bornDay, Integer deathYear, Integer deathMonthNum, Integer deathDay,Errors errors) {
		if (!errors.hasErrors()) {
			if (bornYear != null) {
				if ((bornYear < 1200) || (bornYear > 1700)) {
					errors.reject("bornYear", "error.bornYear.invalid");
				}
			}
			if (bornMonthNum != null) {
				if ((bornMonthNum <1) || (bornMonthNum >12)) {
					errors.reject("bornMonth", "error.bornMonthNum.invalid");
				}
			}
			if (bornDay != null) {
				if ((bornDay < 0) || (bornDay > 31)) {
					errors.reject("bornDay", "error.bornDay.invalid");
				}
			}
			if (deathYear != null) {
				if ((deathYear < 1200) || (deathYear > 1700)) {
					errors.reject("deathYear", "error.deathYear.invalid");
				}
			}
			if (deathMonthNum != null) {
				if ((deathMonthNum <1) || (deathMonthNum >12)) {
					errors.reject("deathMonth", "error.deathMonthNum.invalid");
				}
			}
			if (deathDay != null) {
				if ((deathDay < 0) || (deathDay > 31)) {
					errors.reject("deathDay", "error.deathDay.invalid");
				}
			}
		}
	}
}
