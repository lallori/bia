/*
 * SendUserDetailsController.java
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.medici.docsources.command.user.SendUserPasswordResetCommand;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.recaptcha.ReCaptchaService;
import org.medici.docsources.service.user.UserService;
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
 * Controller for sending user password reset link by mail.
 * 
 * 1. The user enters his mail address and hits the submit. 
 * 
 * 2. The system has a table password_change_requests with the columns ID, 
 *    Time and UserID. The ID is a long random ID (GUID might do). The Time 
 *    column contains the time when the user pressed the "Forgot Password" 
 *    button. When the new user presses the button, a record is created 
 *    in the table.
 * 3. The system sends an email to the user which contains a link in it. 
 *    The link also contains the ID in the above mentioned table. The link 
 *    will be something like this: 
 *    http://www.mysite.com/forgotpassword.jsp?ID=01234567890ABCDEF. 
 *    The forgotpassword.jsp page should be able to retrieve the ID parameter. 
 * 4. When the user clicks the link in the email, he is moved to your page. 
 *    The page retrieves the ID from the URL and checks against the table. 
 *    If such a record is there and is no more than, say, 24 hours old, the 
 *    password is changed to a new random password. The record is also deleted, 
 *    so that it cannot be reused.
 * 5. Now you have a choice of how you want to display the password to the user. 
 *    You can either send another email with it, or display it on the screen. 
 *    Or both, just to be sure. I always like to send important information to 
 *    the email as well, because that makes it easier for the user not to forget 
 *    it.
 *    
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/SendUserPasswordReset")
public class SendUserPasswordResetController {
	@Autowired
	private ReCaptchaService reCaptchaService;
	@Autowired
	private UserService userService;
	@Autowired(required = false)
	@Qualifier("sendUserPasswordResetValidator")
	private Validator validator;
	@Autowired
	private MessageSource messageSource;

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
		((SendUserPasswordResetCommand) binder.getTarget()).setRemoteAddress(request.getRemoteAddr());
	}

	/**
	 * This method process submitted request by user.
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") SendUserPasswordResetCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			User user = new User();
			user.setMail(command.getMail());

			try {
				user = getUserService().findUser(user);
				getUserService().addPasswordChangeRequest(user, command.getRemoteAddress());
				model.put("user", user);
			} catch (ApplicationThrowable aex) {
			}

			return new ModelAndView("user/SendUserPasswordResetSuccess", model);
		}

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
	public ModelAndView setupForm(@ModelAttribute("command") SendUserPasswordResetCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reCaptchaHTML", getReCaptchaService().getReCaptchaObjectNoSSL().createRecaptchaHtml(null, null));
		getMessageSource();
		return new ModelAndView("user/SendUserPasswordReset", model);
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