/*
 * UploadPortraitPersonValidator.java
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

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.peoplebase.EditDetailsPersonCommand;
import org.medici.docsources.command.peoplebase.UploadPortraitPersonCommand;
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
public class UploadPortraitPersonValidator implements Validator {
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
		return givenClass.equals(UploadPortraitPersonCommand.class);
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
		UploadPortraitPersonCommand uploadPortraitPersonCommand = (UploadPortraitPersonCommand) object;
		validatePersonId(uploadPortraitPersonCommand.getPersonId(), errors);
		validateRemoteUrl(uploadPortraitPersonCommand.getLink(), errors);
		validateImageToLoad(uploadPortraitPersonCommand.getBrowse(), errors);
	}

	/**
	 * 
	 * @param browse
	 * @param errors
	 */
	private void validateImageToLoad(byte[] browse, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param link
	 * @param errors
	 */
	private void validateRemoteUrl(String link, Errors errors) {
		if (!ObjectUtils.toString(link).equals("")) {
			// We need to validate remote url image with an url connection to get image...
		}
	}

	/**
	 * 
	 * @param personId
	 * @param errors
	 */
	public void validatePersonId(Integer personId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId", "error.personId.null");

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
	
}
