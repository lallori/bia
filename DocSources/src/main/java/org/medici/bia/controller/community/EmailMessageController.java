/*
 * EmailMessageController.java
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
package org.medici.bia.controller.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.medici.bia.command.community.EmailMessageCommand;
import org.medici.bia.domain.EmailMessageUser;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
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
 * Controller to compose a mail.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/EmailMessage"})
public class EmailMessageController {
	@Autowired
	private CommunityService communityService;
	@Autowired(required = false)
	@Qualifier("emailMessageValidator")
	private Validator validator;
	
	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EmailMessageCommand command, BindingResult result, HttpServletRequest httpServletRequest) {
		getValidator().validate(command, result);
		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			try{
			
				EmailMessageUser emailMessageUser = new EmailMessageUser();
				emailMessageUser.setSubject(command.getSubject());
				emailMessageUser.setBody(command.getText());
				if(command.getAccount() == null || command.getAccount().equals("")){
					if(!command.getUserRoles().contains("All")){
						getCommunityService().createNewEmailMessageUserFromUserRole(command.getUserRoles(), emailMessageUser);
					}else{
						getCommunityService().createNewEmailMessageUserForAll(emailMessageUser);
					}
				}else{
					List<String> accounts = new ArrayList<String>();
					accounts.add(command.getAccount());
					getCommunityService().createNewEmailMessage(emailMessageUser, accounts);
				}


				return new ModelAndView("community/EmailMessage", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EmailMessage", model);
			}
		}
	}
		
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EmailMessageCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try{
			List<UserAuthority> authorities = getCommunityService().getAuthorities();
			model.put("authorities", authorities);
			
		}catch(ApplicationThrowable ath){
			model.put("applicationThrowable", ath);
			return new ModelAndView("error/EmailMessage", model);
		}
		
		return new ModelAndView("community/EmailMessage", model);
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
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