/*
 * AjaxController.java
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.ajax.SearchResult;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for User Actions.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("UserAjaxController")
public class AjaxController {
	@Autowired
	private UserService userService;

	/**
	 * This method calculate password rating. 
	 *  
	 * @param password Password to rate.
	 * @return Integer Rating level
	 */
	@RequestMapping(value = "/user/ajax/RatePassword", method = RequestMethod.GET)
	public @ResponseBody Integer ratePassword(@RequestParam("quavadis") String password) {
		return getUserService().ratePassword(password);
	}

	/**
	 * This method will search Country entity by name field. 
	 * @param name Description field of the country
	 * @return ModelAndView containing country result list.
	 */
	@RequestMapping(value = "/user/ajax/FindCountry", method = RequestMethod.GET)
	public ModelAndView searchCountries(@RequestParam("query") String name) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Country> countries = getUserService().findCountries(name);
			model.put("query", name);
			model.put("suggestions", ListBeanUtils.transformList(countries, "name"));
			model.put("data", ListBeanUtils.transformList(countries, "code"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method will make a check if account passed as parameter is not 
	 * already present in LDAP Tree.
	 *  
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/user/ajax/IsAccountAvailable", method = RequestMethod.GET)
	public @ResponseBody String checkAccount(@RequestParam("account") String account) {
		try {
			return (getUserService().isAccountAvailable(account)).toString();
		} catch (ApplicationThrowable aex) {
			return Boolean.FALSE.toString();
		}
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/ajax/PaginationUser.do", method = RequestMethod.GET)
	public String searchPagination(@RequestParam("sSearch") String alias, Model model) {
		List<User> searchResults = null;

		User user = new User();
		user.setAccount(alias);
		user.setFirstName(alias);
		user.setLastName(alias);
		user.setOrganization(alias);
		user.setMail(alias);

		try {
			searchResults = userService.findUsers(user);
		} catch (ApplicationThrowable aex) {
		}

		// Ordering results...
		PropertyComparator.sort(searchResults, new MutableSortDefinition("firstName", true, true));
		searchResults = Collections.unmodifiableList(searchResults);
		// Paging results...
		PagedListHolder<User> pagedListHolder = new PagedListHolder<User>(searchResults);

		Integer page = Integer.valueOf(1);
		pagedListHolder.setPage(page);
		int pageSize = 10;
		pagedListHolder.setPageSize(pageSize);
		SearchResult userSearchPagination = new SearchResult(searchResults, 1, 10);
		//model.addAttribute(userSearchPagination);
		List test = new ArrayList();
		for (int i=0; i<10; i++) {
			List singleRow = new ArrayList();
			singleRow.add("Lorenzo");
			singleRow.add("Pasquinelli");
			singleRow.add("pasquinelli");
			singleRow.add("pasquinelli@gmail.com");
			test.add(singleRow);
		}

		model.addAttribute("iEcho", 1);
		model.addAttribute("iTotalDisplayRecords", 10);
		model.addAttribute("iTotalRecords", 24);
		model.addAttribute("aaData", test);
		return "OK";
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}