/*
 * ExpandResultsSimpleSearchController.java
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
import java.util.UUID;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.medici.bia.command.search.ExpandResultsSimpleSearchCommand;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
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
@RequestMapping("/src/ExpandResultsSimpleSearch")
public class ExpandResultsSimpleSearchController {
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
	@RequestMapping(method = {RequestMethod.GET})
	public ModelAndView processSubmit(@ModelAttribute("command") ExpandResultsSimpleSearchCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		 
		try {
			command.setsSearch(URIUtil.decode(command.getsSearch(), "UTF-8"));
		} catch (URIException e) {
		}
		if(StringUtils.countMatches(command.getsSearch(), "\"")%2 != 0){
			StringBuffer tempString = new StringBuffer(command.getsSearch());
			tempString.setCharAt(tempString.lastIndexOf("\""), ' ');
			command.setsSearch(tempString.toString());			
		}
		//This code is for highlight the correct words
		StringBuffer yourSearch = new StringBuffer();
		if(command.getsSearch().contains("\"")){
			StringTokenizer stringTokenizer = new StringTokenizer(command.getsSearch().replace('"', ' '), " ");
			while(stringTokenizer.hasMoreTokens()){
				String currentToken = stringTokenizer.nextToken();
				if(currentToken.length() > 0 && currentToken != ""){
					if(yourSearch.toString().length() > 0)
						yourSearch.append(" " + currentToken);
					else
						yourSearch.append(currentToken);					
				}
			}
		}else{
			yourSearch.append(command.getsSearch());
		}
		model.put("yourSearch", yourSearch.toString());
		
		if(command.getsSearch().contains("\"")){
			command.setsSearch(command.getsSearch().replace("\"", "\\\""));
		}
		
		// This number is used to generate an unique id for new search
		UUID uuid = UUID.randomUUID();
		command.setSearchUUID(uuid.toString());
		model.put("searchUUID", uuid.toString());

		// Add outputFields;
		List<String> outputFields = getOutputFields(command.getSimpleSearchPerimeter());
		model.put("outputFields", outputFields);
		return new ModelAndView("search/ExpandSimpleSearchResult",model);
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
			outputFields.add("search.expandSimpleSearchResult.extract.column1");
			outputFields.add("search.expandSimpleSearchResult.extract.column2");
			outputFields.add("search.expandSimpleSearchResult.extract.column3");
			outputFields.add("search.expandSimpleSearchResult.extract.column4");
			outputFields.add("search.expandSimpleSearchResult.extract.column5");
			outputFields.add("search.expandSimpleSearchResult.extract.column6");
			outputFields.add("search.expandSimpleSearchResult.extract.column7");
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)) {
			outputFields = new ArrayList<String>(6);
			outputFields.add("search.expandSimpleSearchResult.synopsis.column1");
			outputFields.add("search.expandSimpleSearchResult.synopsis.column2");
			outputFields.add("search.expandSimpleSearchResult.synopsis.column3");
			outputFields.add("search.expandSimpleSearchResult.synopsis.column4");
			outputFields.add("search.expandSimpleSearchResult.synopsis.column5");
			outputFields.add("search.expandSimpleSearchResult.synopsis.column6");
			outputFields.add("search.expandSimpleSearchResult.synopsis.column7");
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.PEOPLE)) {
			outputFields = new ArrayList<String>(5);
			outputFields.add("search.expandSimpleSearchResult.people.column1");
			outputFields.add("search.expandSimpleSearchResult.people.column2");
			outputFields.add("search.expandSimpleSearchResult.people.column3");
			outputFields.add("search.expandSimpleSearchResult.people.column4");
			outputFields.add("search.expandSimpleSearchResult.people.column5");
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.PLACE)) {
			outputFields = new ArrayList<String>(5);
			outputFields.add("search.expandSimpleSearchResult.place.column1");
			outputFields.add("search.expandSimpleSearchResult.place.column2");
			outputFields.add("search.expandSimpleSearchResult.place.column3");
			outputFields.add("search.expandSimpleSearchResult.place.column4");
			outputFields.add("search.expandSimpleSearchResult.place.column5");
		} else {
			outputFields = new ArrayList<String>(4);
			outputFields.add("search.expandSimpleSearchResult.volume.column1");
			outputFields.add("search.expandSimpleSearchResult.volume.column2");
			outputFields.add("search.expandSimpleSearchResult.volume.column3");
			outputFields.add("search.expandSimpleSearchResult.volume.column4");
			outputFields.add("search.expandSimpleSearchResult.volume.column5");
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
