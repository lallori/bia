/*
 * EditSchedonePropertiesController.java
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

import org.medici.docsources.command.admin.EditSchedonePropertiesCommand;
import org.medici.docsources.common.property.ApplicationPropertyManager;
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
 * Controller for action "Edit Schedone Properties".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/admin/EditSchedoneProperties")
public class EditSchedonePropertiesController {
	@Autowired
	private AdminService adminService;
	@Autowired(required = false)
	@Qualifier("editSchedonePropertiesValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditSchedonePropertiesCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			try {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("schedone.istituto", command.getIstituto());
				hashMap.put("schedone.fondo", command.getFondo());
				hashMap.put("schedone.legatura", command.getLegatura());
				hashMap.put("schedone.supporto", command.getSupporto());
				hashMap.put("schedone.tipoRipresa", command.getTipoRipresa());
				hashMap.put("schedone.coloreImmagine", command.getColoreImmagine());
				hashMap.put("schedone.nomeFiles", command.getNomeFiles());
				hashMap.put("schedone.responsabileFotoRiproduzione", command.getResponsabileFotoRiproduzione());
				hashMap.put("schedone.operatore", command.getOperatore());

				getAdminService().updateApplicationProperties(hashMap);

				// We need to refresh ApplicationPropertyManager...
				ApplicationPropertyManager.refreshProperties();
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditSchedoneProperties", model);
			}

			return new ModelAndView("admin/ShowSchedoneProperties", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditSchedonePropertiesCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		command.setIstituto(ApplicationPropertyManager.getApplicationProperty("schedone.istituto"));
		command.setFondo(ApplicationPropertyManager.getApplicationProperty("schedone.fondo"));
		command.setLegatura(ApplicationPropertyManager.getApplicationProperty("schedone.legatura"));
		command.setSupporto(ApplicationPropertyManager.getApplicationProperty("schedone.supporto"));
		command.setTipoRipresa(ApplicationPropertyManager.getApplicationProperty("schedone.tipoRipresa"));
		command.setColoreImmagine(ApplicationPropertyManager.getApplicationProperty("schedone.coloreImmagine"));
		command.setNomeFiles(ApplicationPropertyManager.getApplicationProperty("schedone.nomeFiles"));
		command.setResponsabileFotoRiproduzione(ApplicationPropertyManager.getApplicationProperty("schedone.responsabileFotoRiproduzione"));
		command.setOperatore(ApplicationPropertyManager.getApplicationProperty("schedone.operatore"));

		return new ModelAndView("admin/EditSchedoneProperties", model);
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}