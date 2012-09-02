/*
 * ResetUserPasswordController.java
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

import org.medici.bia.command.user.ResetUserPasswordCommand;
import org.medici.bia.command.user.ResetUserPasswordRequestCommand;
import org.medici.bia.domain.PasswordChangeRequest;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.recaptcha.ReCaptchaService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
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
 * Controller to permit reset password sended by email.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/ResetUserPassword")
public class ResetUserPasswordController {
	@Autowired
	@Qualifier("org.springframework.security.authentication.ProviderManager#0")
	private ProviderManager authenticationManager;

	@Autowired
	private ReCaptchaService reCaptchaService;

	@Autowired(required = false)
	@Qualifier("resetUserPasswordRequestValidator")
	private Validator requestValidator;

	@Autowired
	private UserService userService;

	@Autowired(required = false)
	@Qualifier("resetUserPasswordValidator")
	private Validator validator;

	/**
	 * 
	 * @param request
	 * @param response
	 * @param username
	 * @param password
	 */
	private void autoLogin(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		try {
			// Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			token.setDetails(new WebAuthenticationDetails(request));
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
	}

	/**
	 * @return the authenticationManager
	 */
	public ProviderManager getAuthenticationManager() {
		return authenticationManager;
	}

	/**
	 * 
	 * @return
	 */
	public ReCaptchaService getReCaptchaService() {
		return reCaptchaService;
	}

	/**
	 * @return the requestValidator
	 */
	public Validator getRequestValidator() {
		return requestValidator;
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
	 * @param command
	 * @param result
	 * @return
	 */
	@InitBinder("command")
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		if (binder.getTarget().getClass().equals(ResetUserPasswordCommand.class)) {
			// Don't allow user to override the
			binder.setDisallowedFields("remoteAddr"); 

			((ResetUserPasswordCommand) binder.getTarget()).setRemoteAddress(request.getRemoteAddr());
		}
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") ResetUserPasswordCommand command, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));

			return new ModelAndView("user/ResetUserPassword", model);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			
			try {
				getUserService().updateUserPassword(command.getUuid(), command.getPassword());

				PasswordChangeRequest passwordChangeRequest = getUserService().findPasswordChangeRequest(command.getUuid());
				autoLogin(request, response, passwordChangeRequest.getUser().getAccount(), command.getPassword());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ResetUserPassword", model);
			}

			return new ModelAndView("user/ResetUserPasswordSuccess", model);
		}
	}

	/**
	 * @param authenticationManager the authenticationManager to set
	 */
	public void setAuthenticationManager(ProviderManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/**
	 * 
	 * @param reCaptchaService
	 */
	public void setReCaptchaService(ReCaptchaService reCaptchaService) {
		this.reCaptchaService = reCaptchaService;
	}

	/**
	 * @param requestValidator the requestValidator to set
	 */
	public void setRequestValidator(Validator requestValidator) {
		this.requestValidator = requestValidator;
	}

	/**
	 * This method receive the request to view the user form. This method has
	 * an important function called inside : the autologin. The form
	 * for updating user is callable only if the user is correctly authenticated.
	 * 
	 * @param command Command bean rappresenting input parameters
	 * @param request Http servlet request object
	 * @param response Http servlet response object
	 * @param model the Map that contains model names as keys and model objects
	 * as values
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@Valid @ModelAttribute("requestCommand") ResetUserPasswordRequestCommand command, BindingResult result) {
		getRequestValidator().validate(command, result);

		if (result.hasErrors()) {
			return new ModelAndView("error/ResetUserPassword");
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			ResetUserPasswordCommand resetUserPasswordCommand = new ResetUserPasswordCommand();
			resetUserPasswordCommand.setUuid(command.getUuid());

			model.put("command", resetUserPasswordCommand);
			model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));

			return new ModelAndView("user/ResetUserPassword", model);
		}
	}

	/**
	 * 
	 * @param userService
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}