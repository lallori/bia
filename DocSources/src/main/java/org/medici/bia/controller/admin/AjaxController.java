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
package org.medici.bia.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.AccessLogSearch;
import org.medici.bia.common.search.UserSearch;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.domain.User;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for Admin Actions.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("AdminAjaxController")
public class AjaxController {
	@Autowired
	private AdminService adminService;
	
	/**
	 * @return the userService
	 */
	public AdminService getAdminService() {
		return adminService;
	}
	
	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/SearchAccessLog.json", method = RequestMethod.GET)
	public ModelAndView searchAccessLog(@RequestParam(value="account", required=false) String account,
								@RequestParam(value="action", required=false) String action,
								@RequestParam(value="fromDate", required=false) String fromDate,
								@RequestParam(value="toDate", required=false) String toDate,
								@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
					   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
					   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
					   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Page page = null;
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.ACCESS_LOG);

		try {
			AccessLogSearch accessLogSearch = new AccessLogSearch(account, action, fromDate, toDate);

			// Paging results...
			page = getAdminService().searchAccessLog(accessLogSearch, paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList(0);
		for(AccessLog currentAccessLog : (List<AccessLog>) page.getList()){
			List singleRow = new ArrayList(0);
			singleRow.add(currentAccessLog.getAction());
			singleRow.add(currentAccessLog.getHttpMethod());
			singleRow.add(currentAccessLog.getAccount());
			singleRow.add(currentAccessLog.getDateAndTime());
			resultList.add(HtmlUtils.showAccessLog(singleRow, currentAccessLog.getIdAccessLog()));
		}

		model.put("iEcho", 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK",model);
	}
	
	/**
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/admin/ApproveNewUser.json", method = RequestMethod.POST)
	public ModelAndView searchUser(@RequestParam(value="account") String account){
		Map<String, Object> model = new HashMap<String, Object>(0);

		if(account != null && account != ""){
			User user = new User(account);
			user.setApproved(Boolean.TRUE);
		
			try {
				user = getAdminService().approveNewUser(user);
				model.put("operation", "OK");
				
				return new ModelAndView("responseOK", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				model.put("operation", "KO");
				return new ModelAndView("responseKO", model);
			}
		}
		model.put("operation", "KO");
		return new ModelAndView("responseKO", model);
	}

	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/WhoIsOnline.json", method = RequestMethod.GET)
	public ModelAndView whoIsOnline(@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
					   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
					   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
					   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Page page = null;
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		try {
			// Paging results...
			page = getAdminService().searchWhoIsOnline(paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		// Ordering results... 
		// LP : la gestione dell'ordinamento va spostata nel blocco metodo del dao invocato nel service
		PropertyComparator.sort(page.getList(), new MutableSortDefinition("firstName", true, true));
		page.setList(Collections.unmodifiableList(page.getList()));

		List resultList = new ArrayList(0);
		for(User currentUser : (List<User>) page.getList()){
			List singleRow = new ArrayList(0);
			singleRow.add(currentUser.getFirstName() + " " + currentUser.getLastName());
			singleRow.add(currentUser.getMail());
			singleRow.add(currentUser.getCity());
			singleRow.add(currentUser.getCountry());
			if (currentUser.getLastLoginDate() != null) {
				singleRow.add(currentUser.getLastLoginDate().toString());
			} else {
				singleRow.add("");
			}
			resultList.add(HtmlUtils.showUser(singleRow, currentUser.getAccount()));
		}

		model.put("iEcho", 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK",model);
	}
	
	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/admin/SearchUser.json", method = RequestMethod.GET)
	public ModelAndView searchUser(@RequestParam(value="fullName") String fullName,
								@RequestParam(value="userName") String userName,
								@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
					   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
					   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
					   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Page page = null;
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		User user = new User();
		if(userName != null && userName.length() > 0) {
			user.setAccount(userName);
		}

		if(fullName != null && fullName.length() > 0){
			user.setFirstName(fullName);
		}

		try {
			// Paging results...
			page = getAdminService().findUsers(user, paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		// Ordering results... 
		// LP : la gestione dell'ordinamento va spostata nel blocco metodo del dao invocato nel service
		PropertyComparator.sort(page.getList(), new MutableSortDefinition("firstName", true, true));
		page.setList(Collections.unmodifiableList(page.getList()));

		List resultList = new ArrayList(0);
		for(User currentUser : (List<User>) page.getList()){
			List singleRow = new ArrayList(0);
			singleRow.add(currentUser.getFirstName() + " " + currentUser.getLastName());
			singleRow.add(currentUser.getMail());
			singleRow.add(currentUser.getCity());
			singleRow.add(currentUser.getCountry());
			if (currentUser.getLastLoginDate() != null) {
				singleRow.add(currentUser.getLastLoginDate().toString());
			} else {
				singleRow.add("");
			}
			resultList.add(HtmlUtils.showUser(singleRow, currentUser.getAccount()));
		}

		model.put("iEcho", 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK",model);
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

}