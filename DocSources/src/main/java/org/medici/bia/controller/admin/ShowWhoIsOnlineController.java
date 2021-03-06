/*
 * ShowWhoIsOnlineControllerController.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for user search.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/admin/ShowWhoIsOnline")
public class ShowWhoIsOnlineController {
	@Autowired
	@Qualifier("showUserSearchValidator")
	private Validator validator;


	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit() {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		//Add Output fields
		List<String> outputFields = getOutputFields();
		model.put("outputFields", outputFields);

		return new ModelAndView("admin/ShowWhoIsOnline", model);
	}
	
	/**
	 * This method return a list of output fields by simpleSearchPerimeter
	 * @param simpleSearchPerimeter
	 * @return
	 */
	private List<String> getOutputFields() {
		List<String> outputFields = new ArrayList<String>(5);
		outputFields.add("First/Last Name");
		outputFields.add("Email Address");
		outputFields.add("City/Town");
		outputFields.add("Country");
		outputFields.add("Last access");
		
		return outputFields;
	}

	/**
	 * @param validator
	 *            the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}