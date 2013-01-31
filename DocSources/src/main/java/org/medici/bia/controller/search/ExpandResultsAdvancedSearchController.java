/*
 * ExpandResultsAdvancedSearchController.java
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
package org.medici.bia.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.command.search.ExpandResultsAdvancedSearchCommand;
import org.medici.bia.common.search.AdvancedSearchDocument;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.geobase.GeoBaseService;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.medici.bia.service.volbase.VolBaseService;
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
@RequestMapping("/src/ExpandResultsAdvancedSearch")
public class ExpandResultsAdvancedSearchController {
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
	@SuppressWarnings("unchecked")
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView processSubmit(@ModelAttribute("command") ExpandResultsAdvancedSearchCommand command, BindingResult result, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		SearchFilter searchFilter = null;
		StringBuffer yourSearch = new StringBuffer();
		List<String> outputFields = null;
		
		// we get our map which contains all user's filter used at runtime. 
		Map<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);
		
		if(command.getSearchUUID() != null){
			// if searchFilter is present in map we get  
			if (searchFilterMap.get(command.getSearchUUID()) != null) {
				searchFilter = searchFilterMap.get(command.getSearchUUID());
				if(searchFilter.getSearchType().equals(SearchType.DOCUMENT)){
					AdvancedSearchDocument advancedSearchDocument = (AdvancedSearchDocument) searchFilter.getFilterData();
					if(advancedSearchDocument.getExtract() != null){
						command.setSimpleSearchPerimeter(SimpleSearchPerimeter.EXTRACT);
					}else if(advancedSearchDocument.getSynopsis() != null){
						command.setSimpleSearchPerimeter(SimpleSearchPerimeter.SYNOPSIS);
					}else{
						command.setSimpleSearchPerimeter(SimpleSearchPerimeter.EXTRACT);
					}
				}
			}
		}
		
		model.put("yourSearch", yourSearch.toString());
		outputFields = getOutputFields(command.getSimpleSearchPerimeter());
		model.put("outputFields", outputFields);
		return new ModelAndView("search/ExpandAdvancedSearchResult",model);
	}

	/**
	 * This method return a list of output fields by simpleSearchPerimeter
	 * @param simpleSearchPerimeter
	 * @return
	 */
	private List<String> getOutputFields(SimpleSearchPerimeter simpleSearchPerimeter) {
		List<String> outputFields = null;

		// Search operation is made by View with a jquery plugin to contextualized AjaxController
		if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT)) {
			outputFields = new ArrayList<String>(6);
			outputFields.add("Sender");
			outputFields.add("Recipient");
			outputFields.add("Date");
			outputFields.add("Sender Location");
			outputFields.add("Recipient Location");
			outputFields.add("Volume <br /> (Ins/Pa) <br /> Folio");
			outputFields.add("Document Transcription");
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)) {
			outputFields = new ArrayList<String>(6);
			outputFields.add("Sender");
			outputFields.add("Recipient");
			outputFields.add("Date");
			outputFields.add("Sender Location");
			outputFields.add("Recipient Location");
			outputFields.add("Volume <br /> (Ins/Pa) <br /> Folio");
			outputFields.add("Document Synopsis");
		}
		
		return outputFields;
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
