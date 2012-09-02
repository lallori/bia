/*
 * ShowSearchDigitizedVolumesValidator.java
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
package org.medici.bia.validator.digitization;

import org.medici.docsources.command.digitization.ShowSearchDigitizedVolumesCommand;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Search Digitized Volumes".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class ShowSearchDigitizedVolumesValidator implements Validator {
	@Autowired
	private DigitizationService digitizationService;

	/**
	 * @param digitizationService the digitizationService to set
	 */
	public void setDigitizationService(DigitizationService digitizationService) {
		this.digitizationService = digitizationService;
	}

	/**
	 * @return the digitizationService
	 */
	public DigitizationService getDigitizationService() {
		return digitizationService;
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
		return givenClass.equals(ShowSearchDigitizedVolumesCommand.class);
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
		ShowSearchDigitizedVolumesCommand showSearchDigitizedVolumesCommand = (ShowSearchDigitizedVolumesCommand) object;
		validateFields(showSearchDigitizedVolumesCommand.getSearchType(), showSearchDigitizedVolumesCommand.getVolNum(), showSearchDigitizedVolumesCommand.getVolNumBetween(), errors);
		// TODO : implement validation logic
		
	}
	
	/**
	 * 
	 * @param searchType
	 * @param volNum
	 * @param volNumBetween
	 * @param errors
	 */
	public void validateFields(String searchType, Integer volNum, Integer volNumBetween, Errors errors){
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "searchType", "error.searchType.null");
		
		if(!errors.hasErrors()){
			if(!searchType.equals("All") && !searchType.equals("Exactly") && !searchType.equals("Between")){
				errors.reject("searchType", "error.searchType.invalid");
			}else if(searchType.equals("Between")){
				if(volNumBetween < volNum){
					errors.rejectValue("volNumBetween", "error.volNumBetween.invalid");
				}
			}
			
			if(!searchType.equals("All") && (volNum == null || volNum <= 0)){
				errors.reject("volNum", "error.volNum.invalid");
			}
			
			if(searchType.equals("Between") && (volNumBetween == null || volNumBetween <= 0)){
				errors.reject("volNumBetween", "error.volNumBetween.invalid");
			}
		}
	}
}