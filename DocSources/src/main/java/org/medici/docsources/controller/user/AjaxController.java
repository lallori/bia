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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.ajax.SearchResult;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.domain.UserHistoryDocument;
import org.medici.docsources.domain.UserHistoryPeople;
import org.medici.docsources.domain.UserHistoryPlace;
import org.medici.docsources.domain.UserHistoryVolume;
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
	 * 
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	private PaginationFilter generatePaginationFilter(SearchType searchType, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);
		
		return paginationFilter;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * 
	 * @param model
	 * @param paginationFilter
	 */
	@SuppressWarnings("unchecked")
	private void myHistoryDocuments(Map<String, Object> model, PaginationFilter paginationFilter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistoryDocuments(paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistoryDocument currentUserHistoryDocument : (List<UserHistoryDocument>)page.getList()) {
			List<Object> singleRow = new ArrayList<Object>();
			singleRow.add(simpleDateFormat.format(currentUserHistoryDocument.getDateAndTime()));
			singleRow.add(currentUserHistoryDocument.getAction().toString());
			singleRow.add(currentUserHistoryDocument.getDocument().getVolume().getMDP());

			if (currentUserHistoryDocument.getDocument().getSenderPeople() != null) {
				singleRow.add(currentUserHistoryDocument.getDocument().getSenderPeople().toString());
			} else {
				singleRow.add("");
			}

			if (currentUserHistoryDocument.getDocument().getRecipientPeople() != null) {
				singleRow.add(currentUserHistoryDocument.getDocument().getRecipientPeople().toString());
			}else {
				singleRow.add("");
			}
			
			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param searchType
	 * @param alias
	 * @param sortingColumn
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/user/MyHistoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryPagination(@RequestParam(value="searchType") SearchType searchType,
								   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType.equals(SearchType.DOCUMENT)) {
			myHistoryDocuments(model, paginationFilter);
		} else if (searchType.equals(SearchType.PEOPLE)) {
			myHistoryPeople(model, paginationFilter);
		} else if (searchType.equals(SearchType.PLACE)) {
			myHistoryPlaces(model, paginationFilter);
		} else if (searchType.equals(SearchType.VOLUME)) {
			myHistoryVolumes(model, paginationFilter);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param model
	 * @param paginationFilter
	 */
	@SuppressWarnings("unchecked")
	private void myHistoryPeople(Map<String, Object> model, PaginationFilter paginationFilter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;


		try {
			page = getUserService().searchUserHistoryPeople(paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistoryPeople currentUserHistoryPeople : (List<UserHistoryPeople>)page.getList()) {
			List<Object> singleRow = new ArrayList<Object>();
			singleRow.add(simpleDateFormat.format(currentUserHistoryPeople.getDateAndTime()));
			singleRow.add(currentUserHistoryPeople.getAction().toString());
			singleRow.add(currentUserHistoryPeople.getPeople().getMapNameLf());
			singleRow.add(DateUtils.getStringDate(currentUserHistoryPeople.getPeople().getBornYear(), currentUserHistoryPeople.getPeople().getBornMonth(), currentUserHistoryPeople.getPeople().getBornDay()));
			singleRow.add(DateUtils.getStringDate(currentUserHistoryPeople.getPeople().getDeathYear(), currentUserHistoryPeople.getPeople().getDeathMonth(), currentUserHistoryPeople.getPeople().getDeathDay()));
			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param model
	 * @param paginationFilter
	 */
	@SuppressWarnings("unchecked")
	private void myHistoryPlaces(Map<String, Object> model, PaginationFilter paginationFilter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistoryPlace(paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistoryPlace currentUserHistoryPlace : (List<UserHistoryPlace>)page.getList()) {
			List<Object> singleRow = new ArrayList<Object>();
			singleRow.add(simpleDateFormat.format(currentUserHistoryPlace.getDateAndTime()));
			singleRow.add(currentUserHistoryPlace.getAction().toString());
			singleRow.add(currentUserHistoryPlace.getPlace().getPlaceNameFull());
			singleRow.add(currentUserHistoryPlace.getPlace().getPlType());
			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param model
	 * @param paginationFilter
	 */
	@SuppressWarnings("unchecked")
	private void myHistoryVolumes(Map<String, Object> model, PaginationFilter paginationFilter) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistoryVolumes(paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistoryVolume currentUserHistoryVolume : (List<UserHistoryVolume>)page.getList()) {
			List<Object> singleRow = new ArrayList<Object>();
			singleRow.add(simpleDateFormat.format(currentUserHistoryVolume.getDateAndTime()));
			singleRow.add(currentUserHistoryVolume.getAction().toString());
			singleRow.add(currentUserHistoryVolume.getVolume().getMDP());
			singleRow.add(currentUserHistoryVolume.getVolume().getSerieList().toString());
			singleRow.add(DateUtils.getStringDate(currentUserHistoryVolume.getVolume().getStartYear(), currentUserHistoryVolume.getVolume().getStartMonthNum(), currentUserHistoryVolume.getVolume().getStartDay()));
			singleRow.add(DateUtils.getStringDate(currentUserHistoryVolume.getVolume().getEndYear(), currentUserHistoryVolume.getVolume().getEndMonthNum(), currentUserHistoryVolume.getVolume().getEndDay()));
			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

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
	@RequestMapping(value = "/user/ajax/FindCountries", method = RequestMethod.GET)
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
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
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