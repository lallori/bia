/*
 * EditUserPropertiesController.java
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
package org.medici.docsources.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.docsources.command.admin.EditUserPropertiesCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Edit Email Properties".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/admin/EditUserProperties")
public class EditUserPropertiesController {
	@Autowired
	private AdminService adminService;
	@Autowired(required = false)
	@Qualifier("EditUserPropertiesValidator")
	private Validator validator;

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditUserPropertiesCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			try {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("user.expiration.password.months", command.getExpirationPassword());
				hashMap.put("user.expiration.user.months", command.getExiprationUser());
				hashMap.put("user.maxBadLogin", command.getMaxBadLogin());
				getAdminService().updateApplicationProperties(hashMap);

				// We need to refresh ApplicationPropertyManager...
				ApplicationPropertyManager.refreshProperties();
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditUserProperties", model);
			}

			return new ModelAndView("admin/ShowApplicationProperties", model);
		}
	}

	/**
	 * @param adminService the adminService to set
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditUserPropertiesCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		command.setExpirationPassword(ApplicationPropertyManager.getApplicationProperty("user.expiration.password.months"));
		command.setExiprationUser(ApplicationPropertyManager.getApplicationProperty("user.expiration.user.months"));
		command.setMaxBadLogin(ApplicationPropertyManager.getApplicationProperty("user.maxBadLogin"));

		return new ModelAndView("admin/EditUserProperties", model);
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}