/*
 * ShowUploadPortraitUserController.java
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

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.medici.bia.command.user.ShowUploadPortraitUserCommand;
import org.medici.bia.common.ajax.MultipartFileUploadEditor;
import org.medici.bia.common.image.UserPortrait;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Upload Portrait User".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/ShowUploadPortraitUser")
public class ShowUploadPortraitUserController {
	@Autowired
	private UserService userService;
	@Autowired(required = false)
	@Qualifier("showUploadPortraitUserValidator")
	private Validator validator;	

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

	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowUploadPortraitUserCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>(0);
		if(command.getAccount() != null){
			try{
				User user = getUserService().findUser(command.getAccount());
				model.put("user", user);
			
				return new ModelAndView("user/ShowUploadPortraitUserModalWindow", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowUploadPortraitUserModalWindow", model);
			}
		}
		return new ModelAndView("error/ShowUploadPortraitUserModalWindow", model);		
	}

	/**
	 * 
	 * @param peopleId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processRequest(@Valid @ModelAttribute("command") ShowUploadPortraitUserCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			setupForm(command, result);
		} else {
			UserPortrait userPortrait = null;
			if(command.getBrowse() != null || !command.getLink().equals("") || !command.getLink().equals("http://"))
				userPortrait = new UserPortrait(command.getAccount(), command.getBrowse(), command.getLink());
				
			try {
				BufferedImage bufferedImage = getUserService().savePortaitUser(userPortrait);
				
				model.put("portraitWidth", bufferedImage.getWidth());
				model.put("portraitHeight", bufferedImage.getHeight());
				model.put("time", System.currentTimeMillis());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				new ModelAndView("error/UploadPortraitUser", model);
			}
		}

		return new ModelAndView("user/CropPortraitUserModalWindow", model);
	}
	
	/**
	 * 
	 * @param binder
	 */
	@InitBinder("command")
    public void initBinder(WebDataBinder binder, HttpServletRequest request) throws ServletException {
        binder.registerCustomEditor(CommonsMultipartFile.class, new MultipartFileUploadEditor());
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