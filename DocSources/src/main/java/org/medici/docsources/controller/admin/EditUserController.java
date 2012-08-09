/*
 * EditUserController.java
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.command.admin.EditUserCommand;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.UserAuthority;
import org.medici.docsources.domain.UserAuthority.Authority;
import org.medici.docsources.domain.UserRole;
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
 * Controller to permit user creation action.
 * It manages View and request's elaboration process.
 * This is an administration function.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/admin/EditUserControl")
public class EditUserController {
	@Autowired
	private AdminService adminService;
	@Autowired(required = false)
	@Qualifier("editUserValidator")
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
	public ModelAndView setupForm(@ModelAttribute("command") EditUserCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Month> months = null;
		User user = new User();
		
		try {
			months = getAdminService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/ShowDocument", model);
		}

		if (StringUtils.isNotBlank(command.getAccount())) {
			try {
				user = getAdminService().findUser(command.getAccount());

				if(user != null){
					command.setAccount(user.getAccount());
					command.setFirstName(user.getFirstName());
					command.setLastName(user.getLastName());
					command.setPassword(user.getPassword());
					Iterator<UserRole> iterator = user.getUserRoles().iterator();
					List<String> userRoles = new ArrayList(0);
					while (iterator.hasNext()) {
						UserRole userRole = iterator.next();
						userRoles.add(userRole.getUserAuthority().getAuthority().toString());
					}
					command.setUserRoles(userRoles);
					Calendar cal = Calendar.getInstance();
					cal.setTime(user.getExpirationDate());
					command.setYearExpirTime(cal.get(Calendar.YEAR));
					command.setMonthExpirTime(new Month(cal.get(Calendar.MONTH) + 1).getMonthNum());
					command.setDayExpirTime(cal.get(Calendar.DAY_OF_MONTH));
					cal.setTime(user.getExpirationPasswordDate());
					command.setYearPassExp(cal.get(Calendar.YEAR));
					command.setMonthPassExp(new Month(cal.get(Calendar.MONTH) + 1).getMonthNum());
					command.setDayPassExp(cal.get(Calendar.DAY_OF_MONTH));
				}
				command.setNewAccount(user.getAccount());
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditUser", model);
			}
		} else {
			command.setAccount("");
			command.setFirstName("");
			command.setLastName("");
			command.setPassword("");
		}
		
		//model.put("userRoles", UserRole.values());
		
		return new ModelAndView("admin/EditUser", model);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditUserCommand command, BindingResult result) {
		getValidator().validate(command, result);
	
		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			User user = new User(command.getNewAccount());
			user.setFirstName(command.getFirstName());
			user.setLastName(command.getLastName());
			user.setInitials(command.getFirstName().charAt(0) + "" + command.getLastName().charAt(0));
			List<String> inputRoles = command.getUserRoles();
			Set<UserRole> userRole = new HashSet<UserRole>();
			
			for (String singleRole : inputRoles) {
				userRole.add(new UserRole(user, new UserAuthority(Authority.valueOf(singleRole))));
			}
			user.setUserRoles(userRole);

			//TODO
			Calendar cal = Calendar.getInstance();
			cal.set(command.getYearExpirTime(), command.getMonthExpirTime() - 1, command.getDayExpirTime());
			user.setExpirationDate(cal.getTime());
			cal.set(command.getYearPassExp(), command.getMonthPassExp() - 1, command.getDayExpirTime());
			user.setExpirationPasswordDate(cal.getTime());
			//userInformation.setExpirationDate(Calendar.getInstance().getTime());
			//userInformation.setExpirationDate(command.getAccExpirTime());
			
			try {
				if(getAdminService().findUser(command.getNewAccount()) != null){
					getAdminService().editUser(user);
				}else{
//					getAdminService().addNewUser(user, userInformation);
				}
				
				model.put("user", user);

				return new ModelAndView("admin/ShowUser", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditUser", model);
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