/*
 * RegisterUserValidator.java
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
package org.medici.docsources.validator.user;

import org.medici.docsources.command.user.RegisterUserCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * This is validator class for action "Register New User".
 * This performs validations checks on fields, account, mail and antispam
 * Recaptcha.  
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class RegisterUserValidator extends AbstractUserValidator implements Validator {
	@Autowired
	private UserService userService;

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Indicates whether the given class is supported by this converter. This
	 * validator supports only RegisterUserCommand.
	 * 
	 * @param givenClass
	 *            the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(RegisterUserCommand.class);
	}

	/**
	 * Validate the supplied target object, which must be of a Class for which
	 * the supports(Class) method typically has (or would) return true. The
	 * supplied errors instance can be used to report any resulting validation
	 * errors.
	 * 
	 * @param obj
	 *            the object that is to be validated (can be null)
	 * @param errors
	 *            contextual state about the validation process (never null)
	 */
	public void validate(Object object, Errors errors) {
		RegisterUserCommand registerCommand = (RegisterUserCommand) object;

		validateAccount(registerCommand.getAccount(), errors);
		validateMail(registerCommand.getMail(), errors);
		validateReCaptcha(registerCommand.getRemoteAddress(), registerCommand.getRecaptcha_challenge_field(), registerCommand.getRecaptcha_response_field(), errors);
	}

	/**
	 * 
	 * @param account
	 * @param errors
	 */
	public void validateAccount(String account, Errors errors) {
		if (!errors.hasErrors()) {
			try {
				if (!getUserService().isAccountAvailable(account)) {
					errors.rejectValue("account", "error.account.notavailable");
				}
			} catch(ApplicationThrowable ath) {
				errors.rejectValue("account", "error.account.notavailable");
			}
		}
	}
}
