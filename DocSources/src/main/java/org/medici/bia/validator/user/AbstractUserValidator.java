/*
 * AbstractUserValidator.java
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
package org.medici.bia.validator.user;

import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.commons.lang.StringUtils;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.recaptcha.ReCaptchaService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * This is the general class for validations on command of package user.
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public abstract class AbstractUserValidator implements Validator {
	@Autowired
	private ReCaptchaService reCaptchaService;
	@Autowired
	private UserService userService;

	/**
	 * @return the reCaptchaService
	 */
	public ReCaptchaService getReCaptchaService() {
		return reCaptchaService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param reCaptchaService the reCaptchaService to set
	 */
	public void setReCaptchaService(ReCaptchaService reCaptchaService) {
		this.reCaptchaService = reCaptchaService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param account
	 * @param errors
	 */
	public void validateAccount(String account, Errors errors) {
		if (errors.hasErrors())
			return;

		try {
			if (getUserService().isAccountAvailable(account)) {
				errors.rejectValue("account", "error.account.notfound");
			}
		} catch(ApplicationThrowable ath) {
			errors.rejectValue("account", "error.account.notfound");
		}
	}

	/**
	 * 
	 * @param password
	 * @param confirmPassword
	 * @param errors
	 */
	public void validateConfirmPassword(String password, String confirmPassword, Errors errors) {
		if (errors.hasErrors())
			return;

		if (!password.equals(confirmPassword)) {
			errors.rejectValue("confirmPassword", "error.confirmPassword.mismatch");
		}
	}

	/**
	 * 
	 * @param firstName
	 * @param errors
	 */
	public void validateFirstName(String firstName, Errors errors) {
		if (errors.hasErrors())
			return;
		
		if (!StringUtils.isAlpha(firstName))
			errors.rejectValue("firstName", "error.firstName.onlyalphacharacters");
	}
	
	/**
	 * 
	 * @param lastName
	 * @param errors
	 */
	public void validateLastName(String lastName, Errors errors) {
		if (errors.hasErrors())
			return;

		if(!lastName.matches("[a-zA-z]+([ '][[:alpha:]]+)*"))
			errors.rejectValue("lastName", "error.lastName.onlyalphacharacters");
	}

	/**
	 * This make a checks to control if mail field is already registered
	 * in other account.
	 * 
	 * @param mail
	 * @param errors
	 */
	public void validateMail(String mail, Errors errors) {
		if (errors.hasErrors())
			return;

		try {
			User user = new User();
			user.setMail(mail);
			if (getUserService().findUser(user) != null) {
				errors.rejectValue("mail", "error.mail.alreadypresent");
			}
		} catch(ApplicationThrowable ath) {
			errors.rejectValue("mail", "error.mail.alreadypresent");
		}
	}

	/**
	 * This method validates ReCaptcha fields.
	 * 
	 * @param remoteAddress Remote ip client
	 * @param challenge
	 * @param response
	 * @param errors
	 */
	public void validateReCaptcha(String remoteAddress, String challenge, String response, Errors errors) {
		if (errors.hasErrors())
			return;

		ReCaptchaResponse reCaptchaResponse = getReCaptchaService().checkReCaptcha(remoteAddress, challenge, response);

		if (!reCaptchaResponse.isValid()) {
			errors.reject("mail", "error.incorrect-captcha-sol");
			/** @TODO :errors.rejectValue(arg0, arg1, arg2); */
		}
	}
}
