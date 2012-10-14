/*
 * ShowMyHistoryByCategoryController.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.command.user.ShowMyHistoryByCategoryCommand;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view user operations history.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/ShowMyHistoryReportByCategory")
public class ShowMyHistoryByCategoryController {
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowMyHistoryByCategoryCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		// Add outputFields;
		List<String> outputFields = getOutputFields(command.getCategory());
		model.put("outputFields", outputFields);
		return new ModelAndView("user/ShowMyHistoryByCategoryModalWindow", model);
	}

	/**
	 * This method return a list of output fields by searchType
	 * @param searchType
	 * @return
	 */
	private List<String> getOutputFields(Category category) {
		List<String> outputFields = null;

		// Search operation is made by View with a jquery plugin to contextualized AjaxController
		if (category.equals(Category.DOCUMENT)) {
			outputFields = new ArrayList<String>(6);
			outputFields.add("Date");
			outputFields.add("Action");
			outputFields.add("Volume / Folio");
			outputFields.add("Sender");
			outputFields.add("Recipient");
		} else if (category.equals(Category.PEOPLE)) {
			outputFields = new ArrayList<String>(5);
			outputFields.add("Date");
			outputFields.add("Action");
			outputFields.add("Name");
			outputFields.add("Born / Active Start");
			outputFields.add("Death / Active End");
		} else if (category.equals(Category.PLACE)) {
			outputFields = new ArrayList<String>(4);
			outputFields.add("Date");
			outputFields.add("Action");
			outputFields.add("Place Name");
			outputFields.add("Place Type");
		} else {
			outputFields = new ArrayList<String>(7);
			outputFields.add("Date");
			outputFields.add("Action");
			outputFields.add("Volume Number");
			outputFields.add("Carteggio");
			outputFields.add("Start Date");
			outputFields.add("End Date");
			outputFields.add("Digitized");
		}
		
		return outputFields;
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

}