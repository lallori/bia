/*
 * EditDetailsSchedoneValidator.java
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
package org.medici.docsources.validator.digitization;

import java.util.HashMap;
import java.util.Map;

import org.medici.docsources.command.digitization.EditDetailsSchedoneCommand;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit Details Schedone".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditDetailsSchedoneValidator implements Validator {
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
		return givenClass.equals(EditDetailsSchedoneCommand.class);
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
		EditDetailsSchedoneCommand editDetailsSchedoneCommand = (EditDetailsSchedoneCommand) object;
		validateSchedone(editDetailsSchedoneCommand.getSchedoneId(), editDetailsSchedoneCommand.getNumeroUnita(), errors);
		// TODO : implement validation logic
	}
	
	/**
	 * 
	 * @param schedoneId
	 * @param errors
	 */
	public void validateSchedone(Integer schedoneId, Integer volNum, Errors errors){
		if(!errors.hasErrors()){
			if(schedoneId > 0){
				try{
					if(getDigitizationService().findSchedone(schedoneId) == null){
						errors.reject("schedoneId", "error.schedone.notfound");
					}else{
						if(volNum != null){
							Map<Integer, Schedone> ifSchedone = new HashMap<Integer, Schedone>();
							ifSchedone = getDigitizationService().findSchedoniMapByVolume(volNum, volNum);
							if(ifSchedone.get(volNum) == null){
								errors.reject("volNum", "error.volNum.alreadyPresent");
							}
						}
					}
				}catch(ApplicationThrowable ath){
					errors.reject("schedoneId", "error.schedone.notfound");
				}
			}
		}
	}
}