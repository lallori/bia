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

import org.medici.bia.command.docbase.EditDetailsDocumentCommand;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.domain.Image.ImageRectoVerso;
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
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public class EditDetailsDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private VolBaseService volBaseService;
	
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
		boolean digitized = validateVolume(editDetailsDocumentCommand.getVolume(), errors);
		validateRectoVerso(editDetailsDocumentCommand.getFolioRectoVerso(), false, digitized, errors);
		validateRectoVerso(editDetailsDocumentCommand.getTranscribeFolioRectoVerso(), true, digitized, errors);
		if (digitized) {
			Integer volNum = VolumeUtils.extractVolNum(editDetailsDocumentCommand.getVolume());
			String volLetExt = VolumeUtils.extractVolLetExt(editDetailsDocumentCommand.getVolume());
			
			// validation of insert and folio must be done only for digitized volumes
			validateInsert(volNum,
					volLetExt,
					editDetailsDocumentCommand.getInsertNum(), 
					editDetailsDocumentCommand.getInsertLet(),
					errors);
			// document start folio validation
			validateFolio(volNum,
					volLetExt,
					editDetailsDocumentCommand.getInsertNum(), 
					editDetailsDocumentCommand.getInsertLet(), 
					editDetailsDocumentCommand.getFolioNum(), 
					editDetailsDocumentCommand.getFolioMod(), 
					editDetailsDocumentCommand.getFolioRectoVerso(), 
					false,
					errors);
			// transcription start folio validation
			validateFolio(volNum,
					volLetExt,
					editDetailsDocumentCommand.getInsertNum(),
					editDetailsDocumentCommand.getInsertLet(),
					editDetailsDocumentCommand.getTranscribeFolioNum(),
					editDetailsDocumentCommand.getTranscribeFolioMod(),
					editDetailsDocumentCommand.getTranscribeFolioRectoVerso(),
					true,
					errors);
		}
		validateDates(editDetailsDocumentCommand.getDocYear(), editDetailsDocumentCommand.getDocMonthNum(), editDetailsDocumentCommand.getDocDay(), errors);
	}

	/**
	 * This method checks if the document with the indentifier provided is valid.
	 * 
	 * @param entryId the document identifier
	 * @param errors
	 */
	private void validateDocument(Integer entryId, Errors errors) {
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
		}
	}
	
	/**
	 * This method checks if the provided recto/verso detail is correct.
	 * 
	 * @param folioRectoVerso the recto/verso detail.
	 * @param transcribeFolioCheck true if the check is done for the transcribe folio.
	 * @param isDigitized true if check is done in a digitized volume. 
	 * @param errors
	 */
	private void validateRectoVerso(String folioRectoVerso, boolean transcribeFolioCheck, boolean isDigitized, Errors errors) {
		if (!errors.hasErrors()) {
			String rv = folioRectoVerso != null ? folioRectoVerso.trim() : "";
			if ((isDigitized && ImageRectoVerso.convertFromString(rv) == null) || (!isDigitized && rv.length() > 0 && ImageRectoVerso.convertFromString(rv) == null))
				errors.rejectValue(
					transcribeFolioCheck ? "transcribeFolioRectoVerso" : "folioRectoVerso",
					transcribeFolioCheck ? "error.transcribefolio.rectoversoinvalid" : "error.folio.rectoversoinvalid");
		}
	}

	/**
	 * This method checks if date is correct.
	 * 
	 * @param startYear the year (between 1200 and 1700)
	 * @param startMonthNum the month number (1 to 12)
	 * @param startDay the day (1 to 31)
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
	 * This method checks if the folio (of the insert of a volume) is found in the digitized volumes.
	 * 
	 * @param volume the volume (number + extension letter)
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param rectoVerso the folio recto/verso
	 * @param transcribeFolioCheck true if the above details are true for the transcribe folio
	 * @param errors
	 */
	private void validateFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso, boolean transcribeFolioCheck, Errors errors) {
		if (!errors.hasErrors()) {
			try  {
				// the volume is digitized so recto/verso detail should be specified
				boolean completeFolioCheck = getDocBaseService().checkDocumentDigitized(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, rectoVerso);
				if (completeFolioCheck) {
					if (StringUtils.nullTrim(rectoVerso) == null) {
						// recto/verso is not specified
						errors.rejectValue(
							transcribeFolioCheck ? "transcribeFolioNum" : "folioNum",
							transcribeFolioCheck ? "error.transcribefolio.specifyrectoverso" : "error.folio.specifyrectoverso");
					}
				} else {
					if (getDocBaseService().checkDocumentDigitized(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, null)) {
						// the recto/verso detail is not correct for the existent folio
						String rvString = StringUtils.nullTrim(rectoVerso).toUpperCase();
						errors.rejectValue(
							transcribeFolioCheck ? "transcribeFolioNum" : "folioNum",
							transcribeFolioCheck ? "error.transcribefolio.rectoversomissing" : "error.folio.rectoversomissing",
							new  Object[] {
								folioNum + " " + StringUtils.safeTrim(folioMod),
								"R".equals(rvString) ? "Recto" : "V".equals(rvString) ? "Verso" : rvString
							},
							null);
					} else {
						// the folio is missing
						if (folioNum != null) {
							errors.rejectValue(
								transcribeFolioCheck ? "transcribeFolioNum" : "folioNum",
								transcribeFolioCheck ? "error.transcribefolio.notfound" : "error.folio.notfound",
								new  Object[] {folioNum + " " + StringUtils.safeTrim(folioMod)}, null);
						} else {
							errors.rejectValue(
								transcribeFolioCheck ? "transcribeFolioNum" : "folioNum",
								transcribeFolioCheck ? "error.transcribefolio.specifyfolio" : "error.folio.specifyfolio");
						}
					}
				}
			} catch (ApplicationThrowable ath) {
				errors.rejectValue(
					transcribeFolioCheck ? "transcribeFolioNum" : "folioNum",
					transcribeFolioCheck ? "error.transcribefolio.validationerror" : "error.folio.validationerror");
			}
		}
	}
	
	/**
	 * This method checks if the insert of a volume is found in the digitized volumes.
	 * 
	 * @param volume the volume (number + extension letter)
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param errors
	 */
	private void validateInsert(Integer volNum, String volLetExt, String insertNum, String insertLet, Errors errors) {
		try  {
			String insNum = StringUtils.nullTrim(insertNum);
			String insLet = StringUtils.nullTrim(insertLet);
			
			if (getDocBaseService().hasInserts(volNum, volLetExt)) {
				if (insNum != null) {
					if (!getDocBaseService().hasInsert(volNum, volLetExt, insNum, insLet)) {
						if (getDocBaseService().hasCandidateInsert(volNum, volLetExt, insNum)) {
							if (insLet == null) {
								// insert number exists but insert letter should be specified
								errors.rejectValue(
									"insertNum",
									"error.insertletter.specify",
									new Object[] {insNum},
									null);
							} else {
								// insert number exists but insert letter is missing
								errors.rejectValue(
									"insertNum",
									"error.insertletter.notfound",
									new  Object[] {insLet, insNum},
									null);
							}
						} else {
							// insert missing (with or without insert letter)
							errors.rejectValue(
								"insertNum",
								"error.insert.notfound",
								new  Object[] {insNum},
								null);
						}
						
					}
				} else {
					// insert number sholud be specified
					errors.rejectValue("insertNum", "error.insert.specify");
				}
			} else if (insNum != null || insLet != null) {
				// the volume has no inserts so you should not specify insert details
				errors.rejectValue(
					"insertNum",
					"error.volume.volumewithnoinserts",
					new  Object[] {volNum + (volLetExt != null ? " " + insLet : "")},
					null);
			}
		} catch (ApplicationThrowable ath) {
			errors.rejectValue("insertNum", "error.insert.validationerror");
		}
	}
	
	/**
	 * This method checks if the volume exists and returns true if it is digitized.
	 * 
	 * @param volume the volume (number + extension letter)
	 * @param errors
	 * @return true if the volume exists and it is digitized
	 */
	private Boolean validateVolume(String volume, Errors errors) {
		if (StringUtils.isNullableString(volume)) {
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
				errors.rejectValue("volume", "error.volume.validationerror");
			}
		}
		return Boolean.FALSE;
	}
}
