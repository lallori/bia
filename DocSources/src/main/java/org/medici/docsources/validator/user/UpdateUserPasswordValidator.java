/*
 * UpdateUserPasswordValidator.java
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

import org.medici.docsources.command.user.UpdateUserPasswordCommand;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class UpdateUserPasswordValidator extends AbstractUserValidator implements Validator {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
	 * validator supports only UpdaateUserCommand.
	 * 
	 * @param givenClass
	 *            the class to test for support
	 * @return true if supported; false otherwise
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class givenClass) {
		return givenClass.equals(UpdateUserPasswordCommand.class);
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
		UpdateUserPasswordCommand updateUserPasswordCommand = (UpdateUserPasswordCommand) object;

		validateAccount(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(), errors);
		validateOldPassword(updateUserPasswordCommand.getOldPassword(), errors);
		validateConfirmPassword(updateUserPasswordCommand.getPassword(), updateUserPasswordCommand.getConfirmPassword(), errors);
	}

	/**
	 * 
	 * @param account
	 * @param errors
	 */
	public void validateOldPassword(String password, Errors errors) {
		if (errors.hasErrors())
			return;

		User user = null;
		try {
			user = getUserService().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			
			if (!passwordEncoder.isPasswordValid(user.getPassword(), password, null)) {
				errors.rejectValue("oldPassword", "error.oldpassword.mismatch");
			}
		} catch(ApplicationThrowable ath) {

		}
	}

	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * @return the passwordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
}
