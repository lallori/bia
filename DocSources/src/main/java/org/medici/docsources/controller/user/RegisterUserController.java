/*
 * RegisterUserController.java
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
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.docsources.command.user.RegisterUserCommand;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.recaptcha.ReCaptchaService;
import org.medici.docsources.service.user.UserService;
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
 * Controller to permit user register action.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/RegisterUser")
public class RegisterUserController {
	@Autowired
	private ReCaptchaService reCaptchaService;
	@Autowired
	private UserService userService;
	@Autowired(required = false)
	@Qualifier("registerUserValidator")
	private Validator validator;

	public ReCaptchaService getReCaptchaService() {
		return reCaptchaService;
	}

	/**
	 * 
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * This method returns the Validator class used by Controller to make
	 * business validation.
	 * 
	 * @return
	 */
	public Validator getValidator() {
		return validator;
	}

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
		((RegisterUserCommand) binder.getTarget()).setRemoteAddress(request.getRemoteAddr());
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") RegisterUserCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			User user = new User();
			try {
				BeanUtils.copyProperties(user, command);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}

			try {
				getUserService().registerUser(user);
				model.put("user", user);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
			}

			return new ModelAndView("user/RegisterUserSuccess",model);
		}

	}

	public void setReCaptchaService(ReCaptchaService reCaptchaService) {
		this.reCaptchaService = reCaptchaService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(RegisterUserCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		model.put("command", command);
		model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));
		return new ModelAndView("user/RegisterUser", model);
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