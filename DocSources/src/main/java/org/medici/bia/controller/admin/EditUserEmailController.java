/*
 * EditUserEmailController.java
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
import org.medici.bia.command.admin.EditUserEmailCommand;
import org.medici.bia.domain.User;
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
 * Controller to edit user mail.
 * It manages View and request's elaboration process.
 * This is an administration function.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/admin/EditUserEmail")
public class EditUserEmailController {
	@Autowired
	private AdminService adminService;
	@Autowired(required = false)
	@Qualifier("editUserEmailValidator")
	private Validator validator;

	/**
	 * 
	 * @return
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditUserEmailCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			User user = getAdminService().findUser(command.getAccount());

			if(user != null){
				command.setAccount(user.getAccount());
				command.setMail(user.getMail());
				command.setMailHide(user.getMailHide());
				command.setReceiveNotificationByMail(user.getMailNotification());
				command.setForumTopicSubscription(user.getForumTopicSubscription());
			}

			Map<Boolean, String> hideMail = new HashMap<Boolean, String>();
			hideMail.put(Boolean.TRUE, "Hide my email from everyone");
			hideMail.put(Boolean.FALSE, "Allow others to see my email");
			model.put("mailHide", hideMail);

			Map<Boolean, String> mailNotification = new HashMap<Boolean, String>();
			mailNotification.put(Boolean.TRUE, "Activated");
			mailNotification.put(Boolean.FALSE, "Deactivated");
		
			model.put("mailNotification", mailNotification);

			Map<Boolean, String> forumTopicSubscription = new HashMap<Boolean, String>();
			forumTopicSubscription.put(Boolean.TRUE, "Activated");
			forumTopicSubscription.put(Boolean.FALSE, "Deactivated");
			
			model.put("forumTopicSubscription", mailNotification);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/EditUserEmail", model);
		}

		return new ModelAndView("admin/EditUserEmail", model);
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditUserEmailCommand command, BindingResult result) {
		getValidator().validate(command, result);
	
		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);
			User user = new User();

			try {
				user.setAccount(command.getAccount());
				user.setMail(command.getMail());
				user.setMailHide(command.getMailHide());
				user.setMailNotification(command.getReceiveNotificationByMail());
				user.setForumTopicSubscription(command.getForumTopicSubscription());
				user = getAdminService().editUserMail(user);

				model.put("user", user);

				return new ModelAndView("admin/ShowUser", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditUserEmail", model);
			}
		}
	}
	/**
		}
	 * 
	 * @param adminService
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}
}
