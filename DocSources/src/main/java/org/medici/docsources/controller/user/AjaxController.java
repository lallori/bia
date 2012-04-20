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

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Category;
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
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	
	@RequestMapping(value = "/user/LastEntryUser.json", method = RequestMethod.GET)
	public ModelAndView lastEntryUser(){
		Map<String, Object> model = new HashMap<String, Object>();
		UserHistory lastEntry = null;
		try{
			lastEntry = getUserService().searchLastUserHistoryEntry();
			
			// Check is necessary for new user which has not history... 
			if (lastEntry != null) {
				model.put("category", lastEntry.getCategory().toString());
			} else {
				model.put("category", "");
			}
			
			return new ModelAndView("responseOK", model);
		}catch(ApplicationThrowable aex){
			return new ModelAndView("responseKO", model);
		}
		
	}

	/**
	 * 
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyHistoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryPagination(@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;


		try {
			page = getUserService().searchUserHistory(paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistory currentUserHistory : (List<UserHistory>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			singleRow.add(currentUserHistory.getCategory().toString());
			singleRow.add(currentUserHistory.getAction().toString());
			if (currentUserHistory.getCategory().equals(Category.DOCUMENT)) {
				singleRow.add(currentUserHistory.getDocument().getMDPAndFolio());
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showDocument(singleRow, currentUserHistory.getDocument().getEntryId()));
			}  else if (currentUserHistory.getCategory().equals(Category.VOLUME)) {
				singleRow.add("");
				singleRow.add(currentUserHistory.getVolume().getMDP());
				singleRow.add("");
				singleRow.add("");
				resultList.add(HtmlUtils.showVolume(singleRow, currentUserHistory.getVolume().getSummaryId()));
			} else if (currentUserHistory.getCategory().equals(Category.PLACE)) {
				singleRow.add("");
				singleRow.add("");
				singleRow.add(currentUserHistory.getPlace().getPlaceNameFull());
				singleRow.add("");
				resultList.add(HtmlUtils.showPlace(singleRow, currentUserHistory.getPlace().getPlaceAllId()));
			} else if (currentUserHistory.getCategory().equals(Category.PEOPLE)) {
				singleRow.add("");
				singleRow.add("");
				singleRow.add("");
				singleRow.add(currentUserHistory.getPerson().getMapNameLf());
				resultList.add(HtmlUtils.showPeople(singleRow, currentUserHistory.getPerson().getPersonId()));
			}
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyHistoryByCategoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryReportByCategoryPagination(@RequestParam(value="category") Category category,
	   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
	   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
	   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
		     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistory(category, paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<Object> resultList = new ArrayList<Object>();
		for (UserHistory currentUserHistory : (List<UserHistory>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			singleRow.add(currentUserHistory.getAction().toString());
			if (currentUserHistory.getCategory().equals(Category.DOCUMENT)) {
				singleRow.add(currentUserHistory.getDocument().getVolume().getMDP());

				if (currentUserHistory.getDocument().getSenderPeople() != null) {
					singleRow.add(currentUserHistory.getDocument().getSenderPeople().toString());
				} else {
					singleRow.add("");
				}

				if (currentUserHistory.getDocument().getRecipientPeople() != null) {
					singleRow.add(currentUserHistory.getDocument().getRecipientPeople().toString());
				}else {
					singleRow.add("");
				}
				
				resultList.add(HtmlUtils.showDocument(singleRow, currentUserHistory.getDocument().getEntryId()));
			}  else if (currentUserHistory.getCategory().equals(Category.VOLUME)) {
				singleRow.add(currentUserHistory.getVolume().getMDP());
				singleRow.add((currentUserHistory.getVolume().getSerieList() == null) ? "" : currentUserHistory.getVolume().getSerieList().toString());
				singleRow.add(DateUtils.getStringDate(currentUserHistory.getVolume().getStartYear(), currentUserHistory.getVolume().getStartMonthNum(), currentUserHistory.getVolume().getStartDay()));
				singleRow.add(DateUtils.getStringDate(currentUserHistory.getVolume().getEndYear(), currentUserHistory.getVolume().getEndMonthNum(), currentUserHistory.getVolume().getEndDay()));
				singleRow.add(currentUserHistory.getVolume().getDigitized().toString());
				resultList.add(HtmlUtils.showVolume(singleRow, currentUserHistory.getVolume().getSummaryId()));
			} else if (currentUserHistory.getCategory().equals(Category.PLACE)) {
				singleRow.add(currentUserHistory.getPlace().getPlaceNameFull());
				singleRow.add(currentUserHistory.getPlace().getPlType());
				resultList.add(HtmlUtils.showPlace(singleRow, currentUserHistory.getPlace().getPlaceAllId()));
			} else if (currentUserHistory.getCategory().equals(Category.PEOPLE)) {
				singleRow.add(currentUserHistory.getPerson().getMapNameLf());
				singleRow.add(DateUtils.getStringDate(currentUserHistory.getPerson().getBornYear(), currentUserHistory.getPerson().getBornMonth(), currentUserHistory.getPerson().getBornDay()));
				singleRow.add(DateUtils.getStringDate(currentUserHistory.getPerson().getDeathYear(), currentUserHistory.getPerson().getDeathMonth(), currentUserHistory.getPerson().getDeathDay()));
				resultList.add(HtmlUtils.showPeople(singleRow, currentUserHistory.getPerson().getPersonId()));
			}
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		return new ModelAndView("responseOK", model);
	}

	/** This method returns 5 elements for History Category View Preview.
	 * 
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/MyHistoryFirstFourElementsByCategoryPagination.json", method = RequestMethod.GET)
	public ModelAndView myHistoryFirstFourElementsByCategoryPagination(@RequestParam(value="category") Category category,
	   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
	   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
	   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
		     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Page page = null;

		try {
			page = getUserService().searchUserHistory(category, paginationFilter, 4);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		
		 List<Object> resultList = new ArrayList<Object>();
		  for (UserHistory currentUserHistory : (List<UserHistory>)page.getList()) {
		   List<String> singleRow = new ArrayList<String>();
		   if (currentUserHistory.getCategory().equals(Category.DOCUMENT)) {
				singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			    singleRow.add(currentUserHistory.getAction().toString());
			    singleRow.add(currentUserHistory.getDocument().getVolume().getMDP());
			    resultList.add(HtmlUtils.showDocument(singleRow, currentUserHistory.getDocument().getEntryId()));
		   } else if (currentUserHistory.getCategory().equals(Category.PEOPLE)) {
			   	singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			   	singleRow.add(currentUserHistory.getAction().toString());
			   	singleRow.add(currentUserHistory.getPerson().getMapNameLf());
			    resultList.add(HtmlUtils.showPeople(singleRow, currentUserHistory.getPerson().getPersonId()));
		   } else if (currentUserHistory.getCategory().equals(Category.PLACE)) {
			   singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			   singleRow.add(currentUserHistory.getAction().toString());
			   singleRow.add(currentUserHistory.getPlace().getPlaceName() + " / " + currentUserHistory.getPlace().getPlParent());
			   resultList.add(HtmlUtils.showPlace(singleRow, currentUserHistory.getPlace().getPlaceAllId()));
		   } else if (currentUserHistory.getCategory().equals(Category.VOLUME)) {
			   singleRow.add(simpleDateFormat.format(currentUserHistory.getDateAndTime()));
			   singleRow.add(currentUserHistory.getAction().toString());
			   singleRow.add(currentUserHistory.getVolume().getMDP());
			   resultList.add(HtmlUtils.showVolume(singleRow, currentUserHistory.getVolume().getSummaryId()));
		   }
		  }

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		return new ModelAndView("responseOK", model);
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		//SearchResult userSearchPagination = new SearchResult(searchResults, 1, 10);
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