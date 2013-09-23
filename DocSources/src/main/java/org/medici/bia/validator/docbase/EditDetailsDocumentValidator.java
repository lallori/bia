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
import org.medici.bia.domain.Volume;
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
		// document start folio validation
		validateVolumeInsertAndFolio(editDetailsDocumentCommand.getVolume(), 
				editDetailsDocumentCommand.getInsertNum(), 
				editDetailsDocumentCommand.getInsertLet(), 
				editDetailsDocumentCommand.getFolioNum(), 
				editDetailsDocumentCommand.getFolioMod(), 
				editDetailsDocumentCommand.getFolioRectoVerso(), 
				errors);
		// transcription start folio validation
		validateFolio(editDetailsDocumentCommand.getVolume(),
				editDetailsDocumentCommand.getInsertNum(),
				editDetailsDocumentCommand.getInsertLet(),
				editDetailsDocumentCommand.getTranscribeFolioNum(),
				editDetailsDocumentCommand.getTranscribeFolioMod(),
				editDetailsDocumentCommand.getTranscribeFolioRectoVerso(),
				errors);
		validateDates(editDetailsDocumentCommand.getDocYear(), editDetailsDocumentCommand.getDocMonthNum(), editDetailsDocumentCommand.getDocDay(), errors);
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
	
	private void validateVolumeInsertAndFolio(String volume, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso, Errors errors) {
		boolean digitized = true;
		
		if (!errors.hasErrors()) {
			digitized = validateVolume(volume, errors);
		}
		
		if (digitized) {
			// validation of insert and folio must be done only for digitized volumes
			if (!errors.hasErrors()) {
				validateInsert(volume, insertNum, insertLet, errors);
			}
			
			if (!errors.hasErrors()) {
				validateFolio(volume, insertNum, insertLet, folioNum, folioMod, rectoVerso, errors);
			}
		}
	}
	
	private void validateFolio(String volume, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso, Errors errors) {
		if (!errors.hasErrors()) {
			try  {
				if (!getDocBaseService().checkFolio(VolumeUtils.extractVolNum(volume), 
						VolumeUtils.extractVolLetExt(volume), 
						insertNum.trim(), 
						insertLet != null ? insertLet.trim() : null, 
						folioNum, 
						folioMod != null ? folioMod.trim() : null, 
						rectoVerso)) {
					errors.rejectValue("folioNum", "error.folio.notfound", new  Object[]{(folioNum != null ? folioNum : "") + (folioMod != null ? " " + folioMod.trim() : "")}, null);
				}
			} catch (ApplicationThrowable ath) {
				errors.rejectValue("folioNum", "error.folio.notfound", new  Object[]{(folioNum != null ? folioNum : "") + (folioMod != null ? " " + folioMod.trim() : "")}, null);
			}
		}
	}
	
	private void validateInsert(String volume, String insertNum, String insertLet, Errors errors) {
		try  {
			if (insertNum != null && !"".equals(insertNum.trim())) {
				if (!getDocBaseService().checkInsert(VolumeUtils.extractVolNum(volume), VolumeUtils.extractVolLetExt(volume), insertNum.trim(), insertLet != null ? insertLet.trim() : null)) {
					errors.rejectValue("insertNum", "error.insert.notfound", new  Object[]{insertNum + (insertLet != null ? " " + insertLet.trim() : "")}, null);
				}
			}
		} catch (ApplicationThrowable ath) {
			errors.rejectValue("insertNum", "error.insert.notfound", new  Object[]{insertNum + (insertLet != null ? " " + insertLet.trim() : "")}, null);
		}
	}
	
	/**
	 * This method checks if the volume exists and returns true if it is digitized.
	 * 
	 * @param volume volume identifier and extension
	 * @param errors
	 * @return true if the volume exists and it is digitized
	 */
	private Boolean validateVolume(String volume, Errors errors) {
		if (StringUtils.isEmpty(volume)) {
			errors.rejectValue("volume", "error.volume.notfound");
		} else {
			try {
				Volume vol = getVolBaseService().findVolume(VolumeUtils.extractVolNum(volume), VolumeUtils.extractVolLetExt(volume));
				if (vol == null) {
					errors.rejectValue("volume", "error.volume.notfound");
				} else {
					return vol.getDigitized();
				}
			} catch (ApplicationThrowable ath) {
				errors.rejectValue("volume", "error.volume.notfound");
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * @return the docBaseService
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
