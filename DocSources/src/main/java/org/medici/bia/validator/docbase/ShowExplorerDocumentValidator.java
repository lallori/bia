/*
 * ShowExplorerDocumentValidator.java
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

import org.medici.bia.command.docbase.ShowExplorerDocumentCommand;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.bia.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Show Document Explorer".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ShowExplorerDocumentValidator implements Validator {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private VolBaseService volBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;

	/**
	 * @param docBaseService to docBaseService set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * 
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}
	
	/**
	 * @param volBaseService to volBaseService set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
	
	/**
	 * 
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}
	
	/**
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}

	/**
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only {@link ShowExplorerDocumentCommand}.
	 * 
	 * @param givenClass the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(ShowExplorerDocumentCommand.class);
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
		ShowExplorerDocumentCommand showExplorerDocumentCommand = (ShowExplorerDocumentCommand) object;
		Integer volNum = showExplorerDocumentCommand.getVolNum();
		String volLetExt = StringUtils.nullTrim(showExplorerDocumentCommand.getVolLetExt());
		String insertNum = StringUtils.nullTrim(showExplorerDocumentCommand.getInsertNum());
		String insertLet = StringUtils.nullTrim(showExplorerDocumentCommand.getInsertLet());
		Integer folioNum = showExplorerDocumentCommand.getImageProgTypeNum();
		String folioMod = StringUtils.nullTrim(showExplorerDocumentCommand.getMissedNumbering());
		String type = showExplorerDocumentCommand.getImageType() != null ? showExplorerDocumentCommand.getImageType().toString() : "C";
		
		validateInsert(volNum, volLetExt, insertNum, insertLet, errors);
		validateFolio(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, type, errors);
	}
	
	private void validateInsert(Integer volNum, String volLetExt, String insertNum, String insertLet, Errors errors) {
		if (!errors.hasErrors()) {
			try  {
				if (getDocBaseService().hasInserts(volNum, volLetExt)) {
					if (insertNum != null) {
						if (!getDocBaseService().hasInsert(volNum, volLetExt, insertNum, insertLet)) {
							if (getDocBaseService().hasCandidateInsert(volNum, volLetExt, insertNum)) {
								if (insertLet == null) {
									// insert number exists but insert letter should be specified
									errors.rejectValue(
										"insertNum",
										"error.insertletter.specify",
										new Object[] {insertNum},
										null);
								} else {
									// insert number exists but insert letter is missing
									errors.rejectValue(
										"insertNum",
										"error.insertletter.notfound",
										new  Object[] {insertLet, insertNum},
										null);
								}
							} else {
								// insert missing (with or without insert letter)
								errors.rejectValue(
									"insertNum",
									"error.insert.notfound",
									new  Object[] {insertNum},
									null);
							}
							
						}
					} else {
						// insert number sholud be specified
						errors.rejectValue("insertNum", "error.insert.specify");
					}
				} else if (insertNum != null || insertLet != null) {
					// the volume has no inserts so you should not specify insert details
					errors.rejectValue(
						"insertNum",
						"error.volume.volumewithnoinserts",
						new  Object[] {volNum + (volLetExt != null ? " " + insertLet : "")},
						null);
				}
			} catch (ApplicationThrowable ath) {
				errors.rejectValue("insertNum", "error.insert.validationerror");
			}
		}
	}
	
	private void validateFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String type, Errors errors) {
		if (!errors.hasErrors()) {
			try {
				String errorTarget = (type != null && "R".equalsIgnoreCase(type.trim())) ? "error.rubricario.notfound" : "error.folio.notfound";
				if (!getVolBaseService().checkFolio(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, type))
					errors.rejectValue("imageProgTypeNum", errorTarget, new Object[]{(folioNum != null ? folioNum : "{empty}") + " " + (folioMod != null ? folioMod : "")}, null);
			} catch (ApplicationThrowable applicationThrowable) {
				errors.rejectValue("imageProgTypeNum", "application error...please retry");
			}	
		}
	}
}
