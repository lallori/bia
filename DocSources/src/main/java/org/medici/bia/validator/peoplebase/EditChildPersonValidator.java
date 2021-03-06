/*
 * EditChildPersonValidator.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.bia.validator.peoplebase;

import org.medici.bia.command.peoplebase.EditChildPersonCommand;
import org.medici.bia.domain.Parent;
import org.medici.bia.domain.People;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class EditChildPersonValidator implements Validator {
	@Autowired
	private PeopleBaseService peopleBaseService;

	/**
	 * 
	 * @return
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * 
	 * @param peopleBaseService
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only ModifyPersonCommand.
	 * 
	 * @param givenClass
	 *            the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(EditChildPersonCommand.class);
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
		EditChildPersonCommand editChildPersonCommand = (EditChildPersonCommand) object;
		validatePersonId(editChildPersonCommand.getId(), errors);
		validateChildId(editChildPersonCommand.getChildId(), errors);
		validateIfChildIsParent(editChildPersonCommand.getChildId(), editChildPersonCommand.getParentId(), errors);
		validateParentChild(editChildPersonCommand.getChildId(), editChildPersonCommand.getParentId(), errors);
//		validateDates(editChildPersonCommand.getParentId(), editChildPersonCommand.getChildId(), errors);
		
	}
	
	public void validateDates(Integer parentId, Integer childId, Errors errors){
		if(!errors.hasErrors()){
			try{
				People parent = getPeopleBaseService().findPerson(parentId);
				People child = getPeopleBaseService().findPerson(childId);
				
				if(child.getDeathDate() < parent.getBornDate()){
					errors.reject("deathDate", "error.deathDate.notVaild");
				}
				if(child.getBornDate() > parent.getDeathDate()){
					errors.reject("bornDate", "error.bornDate.notValid");
				}
			}catch(ApplicationThrowable ath){
				
			}
		}
	}

	/**
	 * 
	 * @param personId
	 * @param errors
	 */
	public void validatePersonId(Integer id, Errors errors) {
		if (!errors.hasErrors()) {
			try {
				if (id > 0) {
					if (getPeopleBaseService().findParent(id) == null) {
						errors.reject("personId", "error.personId.notfound");
					}
				}
			} catch (ApplicationThrowable ath) {
				
			}
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param errors
	 */
	public void validateChildId(Integer id, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "childId", "error.childId.null");
		if (!errors.hasErrors()) {
			try {
				if (id != null && id > 0) {
					if (getPeopleBaseService().findPerson(id) == null) {
						errors.reject("childId", "error.childId.notfound");
					}
				}else{
					errors.reject("childId", "error.childId.notfound");
				}
			} catch (ApplicationThrowable ath) {
				
			}
		}
	}
	
	/**
	 * 
	 * @param childId
	 * @param parentId
	 * @param errors
	 */
	public void validateIfChildIsParent(Integer childId, Integer parentId, Errors errors){
		if(!errors.hasErrors()){
			try{
				if(childId != null && childId > 0){
					People parent = getPeopleBaseService().findPerson(parentId);
					for(Parent currentParent: parent.getParents()){
						if(currentParent.getParent().getPersonId().equals(childId)){
							errors.rejectValue("childId", "error.childId.isParent");
						}
					}
				}
			}catch(ApplicationThrowable ath){
				
			}
		}
	}
	
	/**
	 * 
	 * @param childId
	 * @param parentId
	 * @param errors
	 */
	public void validateParentChild(Integer childId, Integer parentId, Errors errors){
		if(!errors.hasErrors()){
			try{
				if(childId != null && childId > 0){
					People parent = getPeopleBaseService().findPerson(parentId);
					People child = getPeopleBaseService().findPerson(childId);
					for(Parent currentParent : child.getParents()){
						if(currentParent.getParent().getGender().equals(parent.getGender())){
							errors.rejectValue("childId", "error.childId.invalid");
						}
					}
				}
			}catch(ApplicationThrowable ath){
				
			}
		}
	}
}
