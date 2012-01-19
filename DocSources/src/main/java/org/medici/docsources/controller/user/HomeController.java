/*
 * HomeController.java
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
import java.util.List;
import java.util.Map;

import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.domain.UserHistoryDocument;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Home". 
 * This is the entry point after login.
 *  
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
//@Controller
//@RequestMapping("/Home")
//public class HomeController {
//	
//	@Autowired
//	private UserService userService;
//	
//	
//	/**
//	 * 
//	 * @return
//	 */
//	@RequestMapping(method = RequestMethod.GET)
//	public ModelAndView setupForm() {
//		Map<String, Object> model = new HashMap<String, Object>();
//		// We need genders enumeration to populate relative combo-box
//		model.put("searchTypes", SearchType.values());
//		
//		HashMap<String, List<?>> historyReport = null;
//		try {
//			historyReport = getUserService().getMyHistoryReport(1);
//			//List<?> test = historyReport.get("Document");
//			UserHistoryDocument document = (UserHistoryDocument) historyReport.get("Document").get(0);
//			Volume volume = (Volume) historyReport.get("Volume").get(0);
//			People person = (People) historyReport.get("People").get(0);
//			Place place = (Place) historyReport.get("Place").get(0);
//			
//		} catch (ApplicationThrowable ath) {
//			historyReport = new HashMap<String, List<?>>(1);
//		}
//		
//		
//		return new ModelAndView("Home", model);
//	}
//	/**
//	 * @param userService the userService to set
//	 */
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
//	/**
//	 * @return the userService
//	 */
//	public UserService getUserService() {
//		return userService;
//	}
//}