/*
 * ShowTeachingUserController.java
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
package org.medici.bia.controller.teaching;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.command.admin.ShowUserCommand;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to permit user creation action.
 * It manages View and request's elaboration process.
 * This is an administration function.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/teaching/ShowUser")
public class ShowTeachingUserController {
	@Autowired
	private AdminService adminService;

	public AdminService getAdminService() {
		return adminService;
	}
	
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowUserCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		User user = new User();

		if (StringUtils.isNotBlank(command.getAccount())) {
			try {
				user = getAdminService().findUser(command.getAccount());
				Authority adminTeach = null;
				Authority student = null;
				for (UserRole role : user.getUserRoles()) {
					if (Authority.ADMINISTRATORS.equals(role.getUserAuthority().getAuthority()) ||
							Authority.TEACHERS.equals(role.getUserAuthority().getAuthority())) {
						adminTeach = role.getUserAuthority().getAuthority();
					}
					if (Authority.STUDENTS.equals(role.getUserAuthority().getAuthority())) {
						student = role.getUserAuthority().getAuthority();
					}
				}
				if (adminTeach != null || student != null) {
					model.put("teachingPolicy", adminTeach != null ? adminTeach.getValue() : student.getValue());
				}
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowUser", model);
			}
		} else {
			user.setAccount("");
		}

		model.put("user", user);
		
		return new ModelAndView("teaching/ShowUser", model);
	}

}