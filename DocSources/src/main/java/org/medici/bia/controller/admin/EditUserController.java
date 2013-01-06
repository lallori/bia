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
package org.medici.bia.controller.admin;

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
import org.apache.commons.lang.math.NumberUtils;
import org.joda.time.DateTime;
import org.medici.bia.command.admin.EditUserCommand;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserRole;
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
		Map<String, Object> model = new HashMap<String, Object>(0);
		List<Month> months = null;
		User user = new User();
		
		try {
			months = getAdminService().getMonths();
			model.put("months", months);
			
			List<UserAuthority> authorities= getAdminService().getAuthorities();
			model.put("authorities", authorities);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/ShowDocument", model);
		}

		try {
			if (StringUtils.isNotBlank(command.getAccount())) {
				user = getAdminService().findUser(command.getAccount());

				if(user != null){
					command.setAccount(user.getAccount());
					command.setOriginalAccount(user.getAccount());
					command.setFirstName(user.getFirstName());
					command.setMiddleName(user.getMiddleName());
					command.setLastName(user.getLastName());
					command.setPassword(user.getPassword());
					Iterator<UserRole> iterator = user.getUserRoles().iterator();
					List<String> userRoles = new ArrayList<String>(0);
					while (iterator.hasNext()) {
						UserRole userRole = iterator.next();
						userRoles.add(userRole.getUserAuthority().getAuthority().toString());
					}
					command.setUserRoles(userRoles);
					DateTime expirationUserDate = new DateTime(user.getExpirationDate());
					command.setYearExpirationUser(expirationUserDate.getYear());
					command.setMonthExpirationUser(new Month(expirationUserDate.getMonthOfYear()).getMonthNum());
					command.setDayExpirationUser(expirationUserDate.getDayOfMonth());
					DateTime expirationPasswordDate = new DateTime(user.getExpirationPasswordDate());
					command.setYearExpirationPassword(expirationPasswordDate.getYear());
					command.setMonthExpirationPassword(new Month(expirationPasswordDate.getMonthOfYear()).getMonthNum());
					command.setDayExpirationPassword(expirationPasswordDate.getDayOfMonth());
					
					command.setActive(user.getActive());
					command.setApproved(user.getApproved());
					command.setLocked(user.getLocked());
				}				
			} else {
				// If account is blank, flow manage create new account
				command.setAccount("");
				command.setFirstName("");
				command.setLastName("");
				command.setPassword("");

				// Default user role is GUEST
				List<String> userRoles = new ArrayList<String>(0);
				userRoles.add(UserAuthority.Authority.COMMUNITY_USERS.toString());
				command.setUserRoles(userRoles);

				Integer expirationUserMonth = NumberUtils.toInt(ApplicationPropertyManager.getApplicationProperty("user.expiration.user.months"));
				DateTime expirationUserDate = (new DateTime()).plusMonths(expirationUserMonth);
				command.setYearExpirationUser(expirationUserDate.getYear());
				command.setMonthExpirationUser(new Month(expirationUserDate.getMonthOfYear()).getMonthNum());
				command.setDayExpirationUser(expirationUserDate.getDayOfMonth());

				Integer expirationPasswordMonth = NumberUtils.toInt(ApplicationPropertyManager.getApplicationProperty("user.expiration.password.months"));
				DateTime expirationPasswordDate = (new DateTime()).plusMonths(expirationPasswordMonth);
				command.setYearExpirationPassword(expirationPasswordDate.getYear());
				command.setMonthExpirationPassword(new Month(expirationPasswordDate.getMonthOfYear()).getMonthNum());
				command.setDayExpirationPassword(expirationPasswordDate.getDayOfMonth());

				command.setActive(Boolean.TRUE);
				command.setApproved(Boolean.TRUE);
				command.setLocked(Boolean.FALSE);
			}
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/EditUser", model);
		}

		return new ModelAndView("admin/EditUser", model);
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditUserCommand command, BindingResult result) {
		getValidator().validate(command, result);
	
		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);
			Calendar cal = Calendar.getInstance();

			User user = new User(command.getAccount());
			if (StringUtils.isBlank(command.getPassword())) {
				user.setPassword(null);
			} else {
				user.setPassword(command.getPassword());
			}
			user.setFirstName(command.getFirstName());
			user.setMiddleName(command.getMiddleName());
			user.setLastName(command.getLastName());
			List<String> inputRoles = command.getUserRoles();
			Set<UserRole> userRole = new HashSet<UserRole>();
			
			if (inputRoles != null) { 
				for (String singleRole : inputRoles) {
					userRole.add(new UserRole(user, new UserAuthority(Authority.valueOf(singleRole))));
				}
			}
			user.setUserRoles(userRole);

			cal.set(command.getYearExpirationUser(), command.getMonthExpirationUser() - 1, command.getDayExpirationUser());
			user.setExpirationDate(cal.getTime());

			cal.set(command.getYearExpirationPassword(), command.getMonthExpirationPassword() - 1, command.getDayExpirationPassword());
			user.setExpirationPasswordDate(cal.getTime());
			
			if ((command.getForcePswdChange() != null)  && (command.getForcePswdChange().equals(Boolean.TRUE))) {
				cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -1);
				user.setExpirationPasswordDate(cal.getTime());
			}

			user.setApproved(command.getApproved());
			user.setActive(command.getActive());
			user.setLocked(command.getLocked());
			
			try {
				if(getAdminService().findUser(command.getOriginalAccount()) != null){
					user = getAdminService().editUser(user, command.getOriginalAccount());
				}else{
					user = getAdminService().addNewUser(user);
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
