/*
 * ShowSearchTitlesOrOccupationsController.java
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
package org.medici.bia.controller.peoplebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.medici.bia.command.peoplebase.ShowSearchTitlesOrOccupationsCommand;
import org.medici.bia.domain.RoleCat;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Title Or Occupation Menu".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/peoplebase/ShowSearchTitlesOrOccupations")
public class ShowSearchTitlesOrOccupationsController {
	@Autowired
	private PeopleBaseService peopleBaseService;


	/**
	 * This controller act as a dispatcher for result view.
	 *  
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST})
	public ModelAndView processSubmit(@ModelAttribute("command") ShowSearchTitlesOrOccupationsCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		 
		try {
			command.setTextSearch(URIUtil.decode(command.getTextSearch(), "UTF-8"));
		} catch (URIException e) {
		}
		model.put("yourSearch", command.getTextSearch());
		
		if(command.getTextSearch().contains("\"")){
			command.setTextSearch(command.getTextSearch().replace("\"", "\\\""));
		}
		// This number is used to generate an unique id for new search
		UUID uuid = UUID.randomUUID();
		command.setSearchUUID(uuid.toString());
		model.put("searchUUID", uuid.toString());

		// Add outputFields;
		List<String> outputFields = getOutputFields();
		model.put("outputFields", outputFields);
		return new ModelAndView("peoplebase/ShowSearchResultTitlesOrOccupations",model);
	}

	/**
	 * 
	 * @return
	 */
	private List<String> getOutputFields() {
		List<String> result = new ArrayList<String>(2);
		result.add("Title/Occupation");
		result.add("Assigned People");
		return result;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowSearchTitlesOrOccupationsCommand command) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<RoleCat> roleCats = null;
		
		try{
			roleCats = getPeopleBaseService().getRoleCat();
			model.put("roleCat", roleCats);
		}catch(ApplicationThrowable applicationThrowable){
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/CreateNewTitleOrOccupationPerson", model);
		}

		return new ModelAndView("peoplebase/ShowSearchTitlesOrOccupations", model);
	}

	/**
	 * 
	 * @param peopleBaseService
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * 
	 * @return
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

}