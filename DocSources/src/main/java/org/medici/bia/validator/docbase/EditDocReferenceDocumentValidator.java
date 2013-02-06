/*
 * EditDocReferenceDocumentValidator.java
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

import org.medici.bia.command.docbase.EditDocReferenceDocumentCommand;
import org.medici.bia.command.docbase.EditPersonDocumentCommand;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EpLink;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Doc Reference Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditDocReferenceDocumentValidator implements Validator {
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
	 * validator supports only ModifyDocumentCommand.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(EditDocReferenceDocumentCommand.class);
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
		EditDocReferenceDocumentCommand editDocReferenceDocumentCommand = (EditDocReferenceDocumentCommand) object;
		validateEntryIdFrom(editDocReferenceDocumentCommand.getEntryIdFrom(), errors);
		validateEntryIdTo(editDocReferenceDocumentCommand.getEntryIdTo(), errors);
	}

	/**
	 * 
	 * @param documentId
	 * @param errors
	 */
	public void validateEntryIdFrom(Integer entryIdFrom, Errors errors) {
		if (!errors.hasErrors()) {
			// entryId equals zero is 'New Document', it shouldn't be validated  
			if (entryIdFrom > 0) {
				try {
					if (getDocBaseService().findDocument(entryIdFrom) == null) {
						errors.rejectValue("entryIdFrom", "error.documentFrom.notfound");
					}
				} catch (ApplicationThrowable ath) {
					errors.rejectValue("entryIdFrom", "error.documentFrom.notfound");
				}
			}
		}
	}

	private void validateEntryIdTo(Integer entryIdTo, Errors errors) {
		if (!errors.hasErrors()) {
			// entryId equals zero is 'New Document', it shouldn't be validated  
			if (entryIdTo > 0) {
				try {
					if (getDocBaseService().findDocument(entryIdTo) == null) {
						errors.rejectValue("entryIdTo", "error.documentTo.notfound");
					}
				} catch (ApplicationThrowable ath) {
					errors.rejectValue("entryIdTo", "error.documentTo.notfound");
				}
			}
		}
	}
}
