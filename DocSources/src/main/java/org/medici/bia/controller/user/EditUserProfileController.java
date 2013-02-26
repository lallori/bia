/*
 * EditUserProfileController.java
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
package org.medici.bia.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import org.medici.bia.command.user.EditUserProfileCommand;
import org.medici.bia.domain.Country;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to permit user update profile action.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/EditUserProfile")
public class EditUserProfileController {
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("editUserProfileValidator")
	private Validator validator;
	

	/**
	 * 
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}
	
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditUserProfileCommand command, BindingResult result){
		getValidator().validate(command, result);
		
		if(result.hasErrors()){
			return setupForm(command);
		}
		else {
		
			Map<String, Object> model = new HashMap<String, Object>(0);
			User user = new User();

			user.setMail(command.getMail());
			user.setAddress(command.getAddress());
			user.setCountry(command.getCountryCode());
			user.setInterests(command.getInterests());
			user.setOrganization(command.getOrganization());
			user.setTitle(command.getTitle());

			//MD: I don't know if the location is the city
			user.setCity(command.getLocation());
			
			try{
				user = getUserService().updateUser(user);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
			}
			
			model.put("userProfile", user);
			model.put("time", System.currentTimeMillis());
			
			return new ModelAndView("user/ShowUserProfile", model);
		}
	}
	
	/**
	 * 
	 * @param command
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditUserProfileCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		User user = null;
		Country country = null;
		try {
			user= getUserService().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			country = getUserService().findCountry(user.getCountry());
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			user = new User();
		}

		command.setMail(user.getMail());
		command.setAddress(user.getAddress());
		if(country != null){		
			command.setCountry(country.getName());
			command.setCountryCode(country.getCode());
		}
		command.setInterests(user.getInterests());
		command.setOrganization(user.getOrganization());
		command.setTitle(user.getTitle());
		command.setLocation(user.getCity());

		return new ModelAndView("user/EditUserProfile", model);
	}

	/**
	 * 
	 * @param userService
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	
}