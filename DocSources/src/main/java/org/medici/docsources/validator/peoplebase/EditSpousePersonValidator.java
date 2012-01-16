/*
 * EditSpousePersonValidator.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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

import org.medici.docsources.command.peoplebase.EditSpousePersonCommand;
import org.medici.docsources.common.util.ValidationUtils;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class EditSpousePersonValidator implements Validator {
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
		return givenClass.equals(EditSpousePersonCommand.class);
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
		EditSpousePersonCommand editSpousePersonCommand = (EditSpousePersonCommand) object;
		validatePersonId(editSpousePersonCommand.getPersonId(), errors);
		validateHusbandId(editSpousePersonCommand.getHusbandId(), errors);
		validateWifeId(editSpousePersonCommand.getWifeId(), errors);
	}

	/**
	 * 
	 * @param personId
	 * @param errors
	 */
	public void validatePersonId(Integer peopleId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId", "error.personId.null");
		
		if (!errors.hasErrors()) {
			try {
				if (getPeopleBaseService().findPerson(peopleId) == null) {
					errors.reject("personId", "error.personId.notfound");
				}
			} catch (ApplicationThrowable ath) {
				
			}
		}
	}
	
	/**
	 * 
	 * @param husbandId
	 * @param errors
	 */
	public void validateHusbandId(Integer husbandId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "husbandId", "error.husbandId.null");
		
		if (!errors.hasErrors()) {
			try {
				if(husbandId != null && husbandId > 0){
					if (getPeopleBaseService().findPerson(husbandId) == null) {
						errors.reject("husbandId", "error.husbandId.notfound");
					}
				}else{
					errors.reject("husbandId", "error.husbandId.notfound");
				}
			} catch (ApplicationThrowable ath) {
				
			}
		}
	}
	
	/**
	 * 
	 * @param wifeId
	 * @param errors
	 */
	public void validateWifeId(Integer wifeId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wifeId", "error.wifeId.null");
		
		if (!errors.hasErrors()) {
			try {
				if(wifeId != null && wifeId > 0){
					if (getPeopleBaseService().findPerson(wifeId) == null) {
						errors.reject("wifeId", "error.wifeId.notfound");
					}
				}else{
					errors.reject("wifeId", "error.wifeId.notfound");
				}
			} catch (ApplicationThrowable ath) {
				
			}
		}
	}
}
