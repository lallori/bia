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
package org.medici.bia.validator.docbase;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.command.docbase.EditDetailsDocumentCommand;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Details Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
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
		validateDocument(editDetailsDocumentCommand.getEntryId(), editDetailsDocumentCommand.getFolioRectoVerso(), errors);
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
						errors.rejectValue("volume", "error.volume.notfound");
					}
				} catch (ApplicationThrowable ath) {
					errors.rejectValue("volume", "error.volume.notfound");
				}
			}
		}
		
	}

	/**
	 * 
	 * @param entryId
	 * @param folioRectoVerso
	 * @param errors
	 */
	public void validateDocument(Integer entryId, String folioRectoVerso, Errors errors) {
		if (!errors.hasErrors()) {
			// entryId equals zero is 'New Document', it shouldn't be validated  
			if (entryId > 0) {
				try {
					if (getDocBaseService().findDocument(entryId) == null) {
						errors.rejectValue("entryId", "error.document.notfound", new  Object[]{entryId}, null);
					}
				} catch (ApplicationThrowable ath) {
					errors.rejectValue("entryId", "error.document.notfound", new  Object[]{entryId}, null);
				}
			}
			// validation of Recto / Verso field
			if (folioRectoVerso != null && !"".equals(folioRectoVerso.trim()) && !folioRectoVerso.trim().equalsIgnoreCase("R") && !folioRectoVerso.trim().equalsIgnoreCase("V"))
				errors.rejectValue("folioRectoVerso", "error.document.rectoVersoInvalid");
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
					errors.rejectValue("docYear", "error.docYear.invalid");
				}
			}
			if (startMonthNum != null) {
				if ((startMonthNum <1) || (startMonthNum >12)) {
					errors.rejectValue("docMonthNum", "error.docMonthNum.invalid");
				}
			}
			if (startDay != null) {
				if ((startDay < 0) || (startDay > 31)) {
					errors.rejectValue("docDay", "error.docDay.invalid");
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
