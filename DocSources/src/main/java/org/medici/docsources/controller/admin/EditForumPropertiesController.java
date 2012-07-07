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
package org.medici.docsources.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.medici.docsources.command.admin.EditForumPropertiesCommand;
import org.medici.docsources.common.property.ApplicationPropertyManager;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.Forum.Type;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.admin.AdminService;
import org.medici.docsources.service.community.CommunityService;
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
@RequestMapping("/admin/EditForumProperties")
public class EditForumPropertiesController {
	@Autowired
	private AdminService adminService;
	@Autowired
	private CommunityService communityService;
	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	@Autowired(required = false)
	@Qualifier("editForumPropertiesValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditForumPropertiesCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("forum.identifier.document", command.getIdForumDocument());
				hashMap.put("forum.identifier.people", command.getIdForumPeople());
				hashMap.put("forum.identifier.place", command.getIdForumPlace());
				hashMap.put("forum.identifier.volume", command.getIdForumVolume());
				
				getAdminService().updateApplicationProperties(hashMap);

				// We need to refresh ApplicationPropertyManager...
				ApplicationPropertyManager.refreshProperties();
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsDocument", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditForumPropertiesCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Forum> forums = getCommunityService().getForumsByType(Type.FORUM);
			
			model.put("forums", forums);
		} catch (ApplicationThrowable applicationThrowable) {
			return new ModelAndView("error/EditForumProperties", model);
		}
		
		command.setIdForumDocument(ApplicationPropertyManager.getApplicationProperty("forum.identifier.document"));
		command.setIdForumPeople(ApplicationPropertyManager.getApplicationProperty("forum.identifier.people"));
		command.setIdForumPlace(ApplicationPropertyManager.getApplicationProperty("forum.identifier.place"));
		command.setIdForumVolume(ApplicationPropertyManager.getApplicationProperty("forum.identifier.volume"));

		return new ModelAndView("admin/EditForumProperties", model);
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}