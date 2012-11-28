/*
 * ShowUploadPortraitUserValidator.java
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
package org.medici.bia.validator.user;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.user.ShowUploadPortraitUserCommand;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a
 *         href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class ShowUploadPortraitUserValidator implements Validator {
	@Autowired
	private UserService userService;

	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
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
		return givenClass.equals(ShowUploadPortraitUserCommand.class);
	}

	/**
	 * Validate the supplied target object, which must be of a Class for which
	 * the supports(Class) method typically has (or would) return true. The
	 * supplied errors instance can be used to report any resulting validation
	 * errors.
	 * 
	 * @param object
	 *            the object that is to be validated (can be null)
	 * @param errors
	 *            contextual state about the validation process (never null)
	 */
	public void validate(Object object, Errors errors) {
		ShowUploadPortraitUserCommand showUploadPortraitUserCommand = (ShowUploadPortraitUserCommand) object;
		validateAccount(showUploadPortraitUserCommand.getAccount(), errors);
		validateRemoteUrl(showUploadPortraitUserCommand.getLink(), errors);
		validateImageToLoad(showUploadPortraitUserCommand.getBrowse(), errors);
	}

	/**
	 * 
	 * @param browse
	 * @param errors
	 */
	private void validateImageToLoad(CommonsMultipartFile browse, Errors errors) {
		if (browse != null && browse.getSize() > 0) {
			if (!browse.getContentType().equals("image/jpeg") && !browse.getContentType().equals("image/png")) {
				errors.reject("browse", "error.browse.invalidImage");
			}
			// MD: Verify if the upload file is too big
			if (browse.getSize() > 15000000) {
				errors.reject("browse", "error.browse.fileDimension");
			}
		}

	}

	/**
	 * 
	 * @param link
	 * @param errors
	 */
	private void validateRemoteUrl(String link, Errors errors) {
		if (!ObjectUtils.toString(link).equals("") && !ObjectUtils.toString(link).equals("http://")) {
			try {
				URL url = new URL(link);
				HttpURLConnection huc = (HttpURLConnection) url.openConnection();
				int responseCode = huc.getResponseCode();

				if (responseCode != HttpURLConnection.HTTP_OK) {
					errors.reject("link", "error.link.notFound");
				}
			} catch (UnknownHostException uhe) {
				errors.reject("link", "error.link.notFound");
			} catch (IOException ioException) {
				errors.reject("link", "error.link.notFound");
			}
		}
	}

	/**
	 * 
	 * @param personId
	 * @param errors
	 */
	public void validateAccount(String account, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "account", "error.account.null");

		if (!errors.hasErrors()) {
			if (account != null) {
				try {
					if (getUserService().findUser(account) == null) {
						errors.reject("account", "error.account.notfound");
					}
				} catch (ApplicationThrowable ath) {

				}
			}
		}
	}

}
