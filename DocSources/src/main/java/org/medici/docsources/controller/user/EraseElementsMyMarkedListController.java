/*
 * EraseElementsMyMarkedListController.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.docsources.domain.UserMarkedList;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
import org.medici.docsources.service.usermarkedlist.UserMarkedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to remove elements form user marked list.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/EraseElementsMyMarkedList")
public class EraseElementsMyMarkedListController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserMarkedListService userMarkedListService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@RequestParam("idToErase") String idToErase) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			StringTokenizer stringTokenizer = new StringTokenizer(idToErase, "+");
			List<Integer> idElements = new ArrayList<Integer>(); 
			while(stringTokenizer.hasMoreTokens()){
				String current = stringTokenizer.nextToken();
				if(NumberUtils.isNumber(current)){
					idElements.add(NumberUtils.createInteger(current));
				}
			}
			UserMarkedList userMarkedList = getUserMarkedListService().getMyMarkedList();
			
			getUserMarkedListService().removeElementsFromMarkedList(userMarkedList, idElements);
			
		}catch(ApplicationThrowable ath){
			model.put("applicationThrowable", ath);
			return new ModelAndView("error/EraseElementsMyMarkedList", model);
		}

		
		return new ModelAndView("user/ShowMyMarkedListModalWindow", model);
	}
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
	/**
	 * @return the userMarkedListService
	 */
	public UserMarkedListService getUserMarkedListService() {
		return userMarkedListService;
	}
	/**
	 * @param userMarkedListService the userMarkedListService to set
	 */
	public void setUserMarkedListService(UserMarkedListService userMarkedListService) {
		this.userMarkedListService = userMarkedListService;
	}

}