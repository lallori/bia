/*
 * CreateUserController.java
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
package org.medici.docsources.controller.user;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.docsources.command.user.CreateUserCommand;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.User.UserRole;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
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
 */
@Controller
@RequestMapping("/user/CreateUser")
public class CreateUserController {
	@Autowired
	private UserService userService;

	/**
	 * 
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") CreateUserCommand command, BindingResult result) {

		if (result.hasErrors()) {
			return setupForm();
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			User user = new User();
			try {
				BeanUtils.copyProperties(user, command);

				List<UserRole> userRole = new ArrayList<UserRole>();
				userRole.add(UserRole.COMMUNITY_USERS);
				user.setUserRoles(userRole);
			} catch (InvocationTargetException itex) {
			} catch (IllegalAccessException iaex) {
				// TODO Auto-generated catch block
			} finally {
			}

			try {
				getUserService().registerNewUser(user);
				model.put("user", getUserService().findUser(user.getAccount()));
			} catch (ApplicationThrowable aex) {
			}

			return new ModelAndView("responseOK",model);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm() {

		return new ModelAndView("user/CreateUser");
	}

	/**
	 * 
	 * @param userService
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}