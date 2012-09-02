/*
 * EditPersonDocumentValidator.java
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
package org.medici.bia.validator.docbase;

import java.util.Set;

import org.medici.docsources.command.docbase.EditPersonDocumentCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EpLink;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Person Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class EditPersonDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private PeopleBaseService peopleBaseService;

	/**
	 * x
	 * 
	 * @return
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * 
	 * @param docBaseService
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
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
		return givenClass.equals(EditPersonDocumentCommand.class);
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
		EditPersonDocumentCommand editPersonDocumentCommand = (EditPersonDocumentCommand) object;
		validatePerson(editPersonDocumentCommand.getEntryId(), editPersonDocumentCommand.getEpLinkId(), editPersonDocumentCommand.getPersonId(), errors);
	}

	/**
	 * 
	 * @param documentId
	 * @param errors
	 */
	public void validateDocumentId(Integer entryId, Errors errors) {
	}

	private void validatePerson(Integer entryId, Integer epLinkId, Integer personId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entryId", "error.entryId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "epLinkId", "error.epLinkId.null");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "personId", "error.personId.null");

		Document document = null;

		if (!errors.hasErrors()) {
			try {
				document = getDocBaseService().findDocument(entryId);
				if (document == null) {
					errors.reject("entryId", "error.entryId.notfound");
				} else {
					if ((epLinkId != null) && (epLinkId == 0)) {
						// if we are editing or creating, we check that user didn't specify an already linked person
						Set<EpLink> peopleLinked = document.getEpLink();
						
					    for (EpLink currentLink : peopleLinked) {
					    	if (currentLink.getPerson().getPersonId().equals(personId)) {
								errors.reject("personId", "error.personId.alreadyPresent");
					    	}
					    }
					}
				}
			} catch (ApplicationThrowable ath) {
				errors.reject("entryId", "error.entryId.notfound");
			}
		}

		if (!errors.hasErrors()) {
			try {
				// if place is specify, we check if it exists
				if (!personId.equals(0)) {
					if (getPeopleBaseService().findPerson(personId) == null) {
						errors.reject("topic", "error.personId.notfound");
					}
				}			
			} catch (ApplicationThrowable ath) {
				errors.reject("entryId", "error.personId.notfound");
			}
		}
	}
}
