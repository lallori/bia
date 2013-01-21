/*
 * EditUserValidator.java
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
package org.medici.bia.validator.admin;

import org.medici.bia.command.admin.EditUserCommand;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validator bean for action "Edit User Validator".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditUserValidator implements Validator {
	@Autowired
	private AdminService adminService;

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @param adminService the adminService to set
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
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
		return givenClass.equals(EditUserCommand.class);
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
		EditUserCommand editUserCommand = (EditUserCommand) object;
		validateAccount(editUserCommand.getOriginalAccount(), editUserCommand.getAccount(), errors);
		validateConfirmPassword(editUserCommand.getPassword(), editUserCommand.getConfirmPassword(), errors);
	}

	/**
	 * 
	 * @param originalAccount
	 * @param account
	 * @param errors
	 */
	public void validateAccount(String originalAccount, String account, Errors errors) {
		if(originalAccount == null){
			errors.reject("originalAccount", "error.account.null");
			return;
		}

		if(account == null){
			errors.reject("account", "error.account.null");
			return;
		}

		try {
			if(!originalAccount.equals("")){
				User user =  getAdminService().findUser(originalAccount);
				
				if (user != null) {
					if (!originalAccount.equals(account)) {
						try {
							if (getAdminService().findUser(account) != null) {
								errors.rejectValue("account", "error.account.alreadypresent");
							}
						} catch(ApplicationThrowable ath) {
							errors.rejectValue("account", "error.account.notfound");
						}
					}
				} else {
					errors.rejectValue("originalAccount", "error.originalAccount.notfound");
				}
			}
		} catch(ApplicationThrowable ath) {
			errors.rejectValue("originalAccount", "error.originalAccount.notfound");
		}
	}
	
	public void validateConfirmPassword(String password, String confirmPassword, Errors errors){
		if(password == null || password.equals("")){
			errors.rejectValue("password", "error.password.null");
			return;
		}
		
		if(confirmPassword == null || confirmPassword.equals("")){
			errors.rejectValue("confirmPassword", "error.confirmPassword.null");
			return;
		}
		
		if(!password.equals(confirmPassword)){
			errors.rejectValue("password", "error.password.invalid");
			return;
		}
		return;
	}
}