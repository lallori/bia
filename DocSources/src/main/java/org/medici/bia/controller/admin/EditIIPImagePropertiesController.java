/*
 * EditIIPImagePropertiesController.java
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
package org.medici.bia.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.medici.bia.command.admin.EditIIPImagePropertiesCommand;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
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
@RequestMapping("/admin/EditIIPImageProperties")
public class EditIIPImagePropertiesController {
	@Autowired
	private AdminService adminService;
	@Autowired(required = false)
	@Qualifier("editIIPImagePropertiesValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditIIPImagePropertiesCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			try {
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("iipimage.reverseproxy.fcgi.path", command.getServerFcgiBinPath());
				hashMap.put("iipimage.reverseproxy.host", command.getServerHostName());
				hashMap.put("iipimage.reverseproxy.port", command.getServerPort());
				hashMap.put("iipimage.reverseproxy.protocol", command.getServerProtocol());
				hashMap.put("iipimage.reverseproxy.version", command.getServerVersion());
				
				getAdminService().updateApplicationProperties(hashMap);

				// We need to refresh ApplicationPropertyManager...
				ApplicationPropertyManager.refreshProperties();
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/IIPImageProperties", model);
			}

			return new ModelAndView("admin/ShowIIPImageProperties", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditIIPImagePropertiesCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		command.setServerFcgiBinPath(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.fcgi.path"));
		command.setServerHostName(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.host"));
		command.setServerPort(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.port"));
		command.setServerProtocol(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.protocol"));
		command.setServerVersion(ApplicationPropertyManager.getApplicationProperty("iipimage.reverseproxy.version"));

		return new ModelAndView("admin/EditIIPImageProperties", model);
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}