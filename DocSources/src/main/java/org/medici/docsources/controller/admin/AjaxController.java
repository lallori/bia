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
package org.medici.docsources.controller.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.admin.AdminService;
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
	@RequestMapping(value = "/admin/ShowUserSearchResult.json", method = RequestMethod.GET)
	public ModelAndView searchPagination(@RequestParam(value="fullName") String fullName,
								@RequestParam(value="userName") String userName,
								@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
					   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
					   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
					   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Page page = null;
		Map<String, Object> model = new HashMap<String, Object>();
		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);

		User user = new User();
		if(userName != null && userName.length() > 0)
			user.setAccount(userName);
		else
			user.setAccount("");
		if(fullName != null && fullName.length() > 0){
			user.setFirstName(fullName);
			user.setLastName(fullName);
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

		List resultList = new ArrayList();
		for(User currentUser : (List<User>) page.getList()){
			List singleRow = new ArrayList();
			singleRow.add(currentUser.getFirstName() + " " + currentUser.getLastName());
			singleRow.add(currentUser.getMail());
			singleRow.add(currentUser.getCity());
			singleRow.add(currentUser.getCountry());
			if (currentUser.getLastLoginDate() != null) {
				singleRow.add(currentUser.getLastLoginDate());
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