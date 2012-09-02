/*
 * DeleteTitleOrOccupationPersonValidator.java
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

import org.medici.docsources.command.peoplebase.DeleteTitleOrOccupationPersonCommand;
import org.medici.docsources.domain.PoLink;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Delete Title Or Occupation from Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class DeleteTitleOrOccupationPersonValidator implements Validator {
	@Autowired
	private PeopleBaseService peopleBaseService;

	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyDocumentCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(DeleteTitleOrOccupationPersonCommand.class);
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
		DeleteTitleOrOccupationPersonCommand deleteTitleOrOccupationPersonCommand = (DeleteTitleOrOccupationPersonCommand) object;
		validateTitle(deleteTitleOrOccupationPersonCommand.getPrfLinkId(), deleteTitleOrOccupationPersonCommand.getTitleOccId(), deleteTitleOrOccupationPersonCommand.getPersonId(), errors);
	}

	/**
	 * 
	 * @param nameId
	 * @param personId
	 * @param errors
	 */
	public void validateTitle(Integer prfLinkId, Integer titleOccId, Integer personId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prfLinkId", "error.prfLinkId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titleOccId", "error.titleOccId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId", "error.personId.null");

		if (!errors.hasErrors()) {
			try {
				PoLink poLink = getPeopleBaseService().findTitleOrOccupationPerson(personId, prfLinkId); 
				if (poLink == null) {
					errors.reject("nameId", "error.prfLinkId.notfound");
				}
			} catch (ApplicationThrowable ath) {
				errors.reject("nameId", "error.prfLinkId.notfound");
			}
		}
	}
}
