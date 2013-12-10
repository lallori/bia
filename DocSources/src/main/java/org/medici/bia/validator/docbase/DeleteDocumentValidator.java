/*
 * DeleteDocumentValidator.java
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

import org.medici.bia.command.docbase.DeleteDocumentCommand;
import org.medici.bia.domain.Document;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Delete Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class DeleteDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;

	/**
	 * x
	 * 
	 * @return
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * 
	 * @param docBaseService
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only DeleteDocumentCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(DeleteDocumentCommand.class);
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
		DeleteDocumentCommand deleteDocumentCommand = (DeleteDocumentCommand) object;
		validateDeleteOperation(deleteDocumentCommand.getEntryId(), errors);
	}

	/**
	 * 
	 * @param documentId
	 * @param errors
	 */
	public void validateDeleteOperation(Integer entryId, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "entryId", "error.entryId.null");

		if (!errors.hasErrors()) {
			try {
				Document document = getDocBaseService().findDocument(entryId); 
				if (document == null) {
					errors.reject("entryId", "error.entryId.notfound");
				} else {
					// RR: now we enable the document deletion even if the document is referred to people or places
					/*
					if (document.getEpLink().size()>0) {
						errors.reject("entryId", "error.deleteDocument.people.found");
					}
					if (document.getEplToLink().size()>0) {
						errors.reject("entryId", "error.deleteDocument.topics.found");
					}*/
				}
			} catch (ApplicationThrowable ath) {
				errors.reject("entryId", "error.entryId.notfound");
			}
		}
	}
}
