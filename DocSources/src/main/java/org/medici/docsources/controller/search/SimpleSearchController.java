/*
 * SearchDataController.java
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

import org.medici.docsources.command.search.SimpleSearchCommand;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
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
 *
 */
@Controller
@RequestMapping("/src/SimpleSearch")
public class SimpleSearchController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private GeoBaseService geoBaseService;
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}

	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * This controller act as a dispatcher for result view.
	 *  
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView processSubmit(@ModelAttribute("command") SimpleSearchCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		// Search operation is made by View with a jquery plugin to contextualized AjaxController
		if (command.getSearchType().toLowerCase().trim().equals("documents")) {
			return new ModelAndView("search/SimpleSearchResultDocuments",model);
		}
		
		if (command.getSearchType().toLowerCase().trim().equals("people")) {
			return new ModelAndView("search/SimpleSearchResultPeople",model);
		}
		
		if (command.getSearchType().toLowerCase().trim().equals("places")) {
			return new ModelAndView("search/SimpleSearchResultPlaces",model);
		}
		
		if (command.getSearchType().toLowerCase().trim().equals("volumes")) {
			return new ModelAndView("search/SimpleSearchResultVolumes",model);
		}

		// Wee should never arrive at this point.
		return new ModelAndView("errorKO",model);
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}
