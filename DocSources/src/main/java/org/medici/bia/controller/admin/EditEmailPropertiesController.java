/*
 * EditEmailPropertiesController.java
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

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.command.admin.EditEmailPropertiesCommand;
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
@RequestMapping("/admin/EditEmailProperties")
public class EditEmailPropertiesController {
	@Autowired
	private AdminService adminService;
	@Autowired(required = false)
	@Qualifier("editEmailPropertiesValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditEmailPropertiesCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			try {
				Map<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("mail.activationUser.subject", command.getActivationSubject());
				hashMap.put("mail.activationUser.text", command.getActivationText());
				hashMap.put("mail.resetUserPassword.subject", command.getResetUserPasswordSubject());
				hashMap.put("mail.resetUserPassword.text", command.getResetUserPasswordText());
				hashMap.put("mail.server.host", command.getMailServerHost());
				hashMap.put("mail.server.port", command.getMailServerPort().toString());
				if(command.getMailSmtpAuth()){
					hashMap.put("mail.smtp.auth", "true");
					if(command.getMailSmtpStarttlsEnable())
						hashMap.put("mail.smtp.starttls.enable", "true");
					else
						hashMap.put("mail.smtp.starttls.enable", "false");
					hashMap.put("mail.server.username", command.getMailServerUsername());
					hashMap.put("mail.server.password", command.getMailServerPassword());
				}
				else{
					hashMap.put("mail.smtp.auth", "false");
					hashMap.put("mail.smtp.starttls.enable", "false");
				}
				hashMap.put("mail.transport.protocol", command.getMailTransportProtocol());

				getAdminService().updateApplicationProperties(hashMap);

				// We need to refresh ApplicationPropertyManager...
				ApplicationPropertyManager.refreshProperties();
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditEmailProperties", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditEmailPropertiesCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		command.setMailServerHost(ApplicationPropertyManager.getApplicationProperty("mail.server.host"));
		command.setMailServerPort(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("mail.server.port")));
		command.setMailTransportProtocol(ApplicationPropertyManager.getApplicationProperty("mail.transport.protocol"));
		if(ApplicationPropertyManager.getApplicationProperty("mail.smtp.auth").equals("true"))
			command.setMailSmtpAuth(true);
		else
			command.setMailSmtpAuth(false);
		if(ApplicationPropertyManager.getApplicationProperty("mail.smtp.starttls.enable").equals("true"))
			command.setMailSmtpStarttlsEnable(true);
		else
			command.setMailSmtpStarttlsEnable(false);
		command.setMailServerUsername(ApplicationPropertyManager.getApplicationProperty("mail.server.username"));
		command.setMailServerPassword(ApplicationPropertyManager.getApplicationProperty("mail.server.password"));
		command.setActivationSubject(ApplicationPropertyManager.getApplicationProperty("mail.activationUser.subject"));
		command.setActivationText(ApplicationPropertyManager.getApplicationProperty("mail.activationUser.text"));
		command.setResetUserPasswordSubject(ApplicationPropertyManager.getApplicationProperty("mail.resetUserPassword.subject"));
		command.setResetUserPasswordText(ApplicationPropertyManager.getApplicationProperty("mail.resetUserPassword.text"));

		return new ModelAndView("admin/EditEmailProperties", model);
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}