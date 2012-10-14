/*
 * SendUserActivationCodeController.java
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
import javax.validation.Valid;

import org.medici.bia.command.user.SendUserActivationCodeCommand;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.mail.MailService;
import org.medici.bia.service.recaptcha.ReCaptchaService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
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
 * Controller for receiving activation code by mail.
 * 
 * 1. The user enters his mail address, captcha words and hits the submit.
 * 2. The system persists the request for sending activation code in an entity.
 *    
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/SendUserActivationCode")
public class SendUserActivationCodeController {
	@Autowired
	private MailService mailService;
	@Autowired
	private ReCaptchaService reCaptchaService;
	@Autowired
	private UserService userService;
	@Autowired(required = false)
	@Qualifier("sendUserActivationCodeValidator")
	private Validator validator;
	@Autowired
	private MessageSource messageSource;
	/**
	 * @return the mailService
	 */
	public MailService getMailService() {
		return mailService;
	}

	/**
	 * 
	 * @return RecaptchaService
	 */
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
		binder.setDisallowedFields("ip"); // Don't allow user to override the
		// value
		((SendUserActivationCodeCommand) binder.getTarget()).setRemoteAddress(request.getRemoteAddr());
	}

	/**
	 * This method process submitted request by user.
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") SendUserActivationCodeCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			User user = new User();
			user.setMail(command.getMail());

			try {
				getUserService().addActivationUserRequest(user, command.getRemoteAddress());
				model.put("user", user);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
			}

			return new ModelAndView("user/SendUserActivationCodeSuccess", model);
		}

	}

	/**
	 * @param mailService
	 *            the mailService to set
	 */
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	/**
	 * 
	 * @param reCaptchaService
	 */
	public void setReCaptchaService(ReCaptchaService reCaptchaService) {
		this.reCaptchaService = reCaptchaService;
	}

	/**
	 * This method returns link to ModelAndView which will show form for username
	 * or password retrieve.
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") SendUserActivationCodeCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));
		getMessageSource();
		return new ModelAndView("user/SendUserActivationCode", model);
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

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}
}