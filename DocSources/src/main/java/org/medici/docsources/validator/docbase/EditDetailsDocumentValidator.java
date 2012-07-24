/*
 * EditDetailsDocumentValidator.java
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
package org.medici.docsources.validator.docbase;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.command.docbase.EditDetailsDocumentCommand;
import org.medici.docsources.common.util.VolumeUtils;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Details Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class EditDetailsDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private VolBaseService volBaseService;

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
		return givenClass.equals(EditDetailsDocumentCommand.class);
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
		EditDetailsDocumentCommand editDetailsDocumentCommand = (EditDetailsDocumentCommand) object;
		validateDocument(editDetailsDocumentCommand.getEntryId(), errors);
		validateLinkedVolume(editDetailsDocumentCommand.getVolume(), errors);
		validateDates(editDetailsDocumentCommand.getDocYear(), editDetailsDocumentCommand.getDocMonthNum(), editDetailsDocumentCommand.getDocDay(), errors);
	}

	/**
	 * 
	 * @param volume
	 * @param errors
	 */
	private void validateLinkedVolume(String volume, Errors errors) {
		if (!errors.hasErrors()) {
			if (StringUtils.isEmpty(volume)) {
				errors.rejectValue("volume", "error.volume.notfound");
			}else{
				try {
					if (getVolBaseService().findVolume(VolumeUtils.extractVolNum(volume), VolumeUtils.extractVolLetExt(volume)) == null) {
						errors.reject("volume", "error.volume.notfound");
					}
				} catch (ApplicationThrowable ath) {
					errors.reject("volume", "error.volume.notfound");
				}
			}
		}
		
	}

	/**
	 * 
	 * @param entryId
	 * @param errors
	 */
	public void validateDocument(Integer entryId, Errors errors) {
		if (!errors.hasErrors()) {
			// entryId equals zero is 'New Document', it shouldn't be validated  
			if (entryId > 0) {
				try {
					if (getDocBaseService().findDocument(entryId) == null) {
						errors.reject("entryId", "error.document.notfound");
					}
				} catch (ApplicationThrowable ath) {
					errors.reject("entryId", "error.document.notfound");
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param startYear
	 * @param startMonthNum
	 * @param startDay
	 * @param endYear
	 * @param endMonthNum
	 * @param endDay
	 * @param errors
	 */
	private void validateDates(Integer startYear, Integer startMonthNum, Integer startDay, Errors errors) {
		if (!errors.hasErrors()) {
			if (startYear != null) {
				if ((startYear < 1200) || (startYear > 1700)) {
					errors.reject("docYear", "error.docYear.invalid");
				}
			}
			if (startMonthNum != null) {
				if ((startMonthNum <1) || (startMonthNum >12)) {
					errors.reject("docMonthNum", "error.docMonthNum.invalid");
				}
			}
			if (startDay != null) {
				if ((startDay < 0) || (startDay > 31)) {
					errors.reject("docDay", "error.docDay.invalid");
				}
			}
		}
	}

	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}
}
