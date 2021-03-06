/*
 * ChangeExpiratedUserPasswordController.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.medici.bia.command.user.ChangeExpiratedUserPasswordCommand;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.recaptcha.ReCaptchaService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to change expirated password.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/ChangeExpiratedUserPassword")
public class ChangeExpiratedUserPasswordController {
	@Autowired
	private UserService userService;
	@Autowired
	private ReCaptchaService reCaptchaService;

	@Autowired(required = false)
	@Qualifier("changeExpiratedUserPasswordValidator")
	private Validator validator;

	/**
	 * 
	 * @param binder
	 * @param request
	 */
	@InitBinder("command")
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		// Don't allow user to override the
		binder.setDisallowedFields("remoteAddr"); 
		// value
		((ChangeExpiratedUserPasswordCommand) binder.getTarget()).setRemoteAddress(request.getRemoteAddr());
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ChangeExpiratedUserPasswordCommand command, BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>(0);

		model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));

		return new ModelAndView("user/ChangeExpiratedUserPassword", model);
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") ChangeExpiratedUserPasswordCommand command, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			Map<String, Object> model = new HashMap<String, Object>(0);
			model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));

			return new ModelAndView("user/ChangeExpiratedUserPassword", model);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);
			
			try {
				getUserService().updateUserPassword(command.getPassword());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ChangeExpiratedUserPassword", model);
			}

			return new ModelAndView("user/ChangeExpiratedUserPasswordSuccess", model);
		}
	}
	
	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * 
	 * @param reCaptchaService
	 */
	public void setReCaptchaService(ReCaptchaService reCaptchaService) {
		this.reCaptchaService = reCaptchaService;
	}

	/**
	 * 
	 * @return
	 */
	public ReCaptchaService getReCaptchaService() {
		return reCaptchaService;
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * 
	 * @return
	 */
	public Validator getValidator() {
		return validator;
	}
}