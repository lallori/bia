/*
 * BrowseDigitizedVolumesController.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.docsources.controller.digitization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.medici.docsources.command.digitization.BrowseDigitizedVolumesCommand;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Controller
@RequestMapping("/digitization/BrowseDigitizedVolumes")
public class BrowseDigitizedVolumesController {

	/**
	 * This controller act as a dispatcher for result view.
	 *  
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView processSubmit(@ModelAttribute("command") BrowseDigitizedVolumesCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		// This number is used to generate an unique id for new search 
		UUID uuid = UUID.randomUUID();
		command.setSearchUUID(uuid.toString());
		model.put("searchUUID", uuid.toString());

		// Add outputFields;
		List<String> outputFields = getOutputFields();
		model.put("outputFields", outputFields);
		return new ModelAndView("digitization/BrowseDigitizedVolumes",model);
	}

	/**
	 * This method return a list of output fields.
	 * 
	 * @return
	 */
	private List<String> getOutputFields() {
		List<String> outputFields = null;

		outputFields = new ArrayList<String>(6);
		outputFields.add("Filza N. (MDP)");
		outputFields.add("Catalog Description");
		outputFields.add("Active");
		outputFields.add("Edit Catalog Description");
		outputFields.add("Deactivate");
		return outputFields;
	}

}
