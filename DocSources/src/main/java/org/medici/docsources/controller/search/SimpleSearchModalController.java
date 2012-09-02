/*
 * SimpleSearchModalController.java
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
package org.medici.docsources.controller.search;

import java.util.HashMap;
import java.util.Map;

import org.medici.bia.common.search.SimpleSearchDocument;
import org.medici.bia.common.search.SimpleSearchPeople;
import org.medici.bia.common.search.SimpleSearchPlace;
import org.medici.bia.common.search.SimpleSearchVolume;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.docsources.command.search.SimpleSearchModalCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
@Controller
@RequestMapping("/src/SimpleSearchModal")
public class SimpleSearchModalController {
	@Autowired
	SearchService searchService;

	

	/**
	 * @return the searchService
	 */
	public SearchService getSearchService() {
		return searchService;
	}



	/**
	 * @param searchService the searchService to set
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}



	/**
	 * This controller act as a dispatcher for result view.
	 *  
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView setupForm(@ModelAttribute("command") SimpleSearchModalCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Long totalResult = new Long(0);
		
		try{
			SimpleSearchDocument simpleSearchDocument = new SimpleSearchDocument(SimpleSearchPerimeter.SYNOPSIS, command.getText());
			totalResult = getSearchService().searchCount(simpleSearchDocument);
			model.put("documentsSynopsisCount", totalResult);
			simpleSearchDocument = new SimpleSearchDocument(SimpleSearchPerimeter.EXTRACT, command.getText());
			totalResult = getSearchService().searchCount(simpleSearchDocument);
			model.put("documentsExtractCount", totalResult);
			SimpleSearchVolume simpleSearchVolume = new SimpleSearchVolume(command.getText());
			totalResult = getSearchService().searchCount(simpleSearchVolume);
			model.put("volumesCount", totalResult);
			SimpleSearchPeople simpleSearchPeople = new SimpleSearchPeople(command.getText());
			totalResult = getSearchService().searchCount(simpleSearchPeople);
			model.put("peopleCount", totalResult);
			SimpleSearchPlace simpleSearchPlace = new SimpleSearchPlace(command.getText());
			totalResult = getSearchService().searchCount(simpleSearchPlace);
			model.put("placesCount", totalResult);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/SimpleSearchModalWindow", model);
		}
		
		model.put("yourSearch", command.getText());
		
		return new ModelAndView("search/SimpleSearchModalWindow",model);
	}

	
}
