/*
 * AvancedSearchController.java
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

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.medici.bia.command.user.AdvancedSearchUserCommand;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
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
 * Controller for user advanced search action.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/AdvancedSearchUser.do")
public class AdvancedSearchUserController {
	@Autowired
	private UserService userService;
	@Autowired
	@Qualifier("advancedSearchUserValidator")
	private Validator validator;

	/**
	 * 
	 * @return
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	@InitBinder("command")
	public void initBinder(WebDataBinder binder, HttpServletRequest request) {
		binder.setDisallowedFields("ip"); // Don't allow user to override the
		// default value for pagination and sorting
		if (((AdvancedSearchUserCommand) binder.getTarget()).getPageNumber() == null) {
			((AdvancedSearchUserCommand) binder.getTarget()).setPageNumber(new Integer(0));
		}
		if (((AdvancedSearchUserCommand) binder.getTarget()).getSort() == null) {
			((AdvancedSearchUserCommand) binder.getTarget()).setSort("lastName");
			((AdvancedSearchUserCommand) binder.getTarget()).setSortAscending(Boolean.TRUE);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") AdvancedSearchUserCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			List<User> searchResults = null;

			User user = new User();
			user.setAccount(command.getAccount());
			user.setFirstName(command.getFirstName());
			user.setLastName(command.getLastName());
			user.setOrganization(command.getOrganization());
			user.setMail(command.getMail());

			try {
				searchResults = userService.findUsers(user);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
			}

			// Ordering results...
			PropertyComparator.sort(searchResults, new MutableSortDefinition(command.getSort(), true, command.getSortAscending()));
			searchResults = Collections.unmodifiableList(searchResults);
			// Paging results...
			PagedListHolder<User> pagedListHolder = new PagedListHolder<User>(searchResults);

			Integer page = command.getPageNumber();
			pagedListHolder.setPage(page);
			int pageSize = 10;
			pagedListHolder.setPageSize(pageSize);

			model.put("pagedListHolder", pagedListHolder);
			return new ModelAndView("user/searchResult", model);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") AdvancedSearchUserCommand command) {
		return new ModelAndView("user/AdvancedSearchUser");
	}

	/**
	 * 
	 * @param userService
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param validator
	 *            the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}