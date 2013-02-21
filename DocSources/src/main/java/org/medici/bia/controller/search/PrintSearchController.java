/*
 * PrintSearchController.java
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
package org.medici.bia.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.command.search.PrintSearchCommand;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.geobase.GeoBaseService;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.medici.bia.service.search.SearchService;
import org.medici.bia.service.user.UserService;
import org.medici.bia.service.usermarkedlist.UserMarkedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to print elements form a search.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/PrintSearch")
public class PrintSearchController {
	@Autowired
	private GeoBaseService geoBaseService;
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired
	private SearchService searchService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") PrintSearchCommand command, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		SearchFilter searchFilter = null;
		
		// we get our map which contains all user's filter used at runtime. 
		Map<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);
		try{
			if(command.getSearchUUID() != null){
				if (searchFilterMap.get(command.getSearchUUID()) != null) {
					searchFilter = searchFilterMap.get(command.getSearchUUID());
				}
				
				List<Document> elementsToPrint = getSearchService().searchDocumentsToPrint(searchFilter.getFilterData());
				
				
				model.put("elementsToPrint", elementsToPrint);
//				//Place print values
//				Map<Integer, List<Integer>> placesValuesToPrint = new HashMap<Integer, List<Integer>>();
//				Map<Integer, List<Place>> placeNamesValuesToPrint = new HashMap<Integer, List<Place>>();
//				
//				//Person print values
//				Map<Integer, List<Marriage>> peopleMarriageValues = new HashMap<Integer, List<Marriage>>();
//				Map<Integer, List<People>> peopleChildrenValues = new HashMap<Integer, List<People>>();
//				Map<Integer, List<Integer>> peopleValuesToPrint = new HashMap<Integer, List<Integer>>();
//				
//				for(UserMarkedListElement currentElement : elementsToPrint){
//					if(currentElement.getPlace() != null){
//						List<Integer> values = new ArrayList<Integer>();
//						values.add(getGeoBaseService().findNumberOfTopicsPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfDocumentsInTopicsPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfSenderDocumentsPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfRecipientDocumentsPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfBirthInPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfActiveStartInPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfDeathInPlace(currentElement.getPlace().getPlaceAllId()));
//						values.add(getGeoBaseService().findNumberOfActiveEndInPlace(currentElement.getPlace().getPlaceAllId()));
//						placesValuesToPrint.put(currentElement.getId(), values);
//						placeNamesValuesToPrint.put(currentElement.getId(), getGeoBaseService().findPlaceNames(currentElement.getPlace().getGeogKey()));
//					}
//					if(currentElement.getPerson() != null){
//						peopleMarriageValues.put(currentElement.getId(), getPeopleBaseService().findMarriagesPerson(currentElement.getPerson().getPersonId(), currentElement.getPerson().getGender()));
//						peopleChildrenValues.put(currentElement.getId(), getPeopleBaseService().findChildrenPerson(currentElement.getPerson().getPersonId()));
//						List<Integer> values = new ArrayList<Integer>();
//						Integer senderDocs = getPeopleBaseService().findNumberOfSenderDocumentsRelated(currentElement.getPerson().getPersonId());
//						Integer recipientDocs = getPeopleBaseService().findNumberOfRecipientDocumentsRelated(currentElement.getPerson().getPersonId());
//						Integer referringDocs = getPeopleBaseService().findNumberOfReferringDocumentsRelated(currentElement.getPerson().getPersonId());
//						values.add(senderDocs + recipientDocs + referringDocs);
//						values.add(senderDocs);
//						values.add(recipientDocs);
//						values.add(referringDocs);
//						peopleValuesToPrint.put(currentElement.getId(), values);
//					}
//				}
//				model.put("placesValues", placesValuesToPrint);
//				model.put("placeNamesValues", placeNamesValuesToPrint);
//				model.put("peopleMarriageValues", peopleMarriageValues);
//				model.put("peopleChildrenValues", peopleChildrenValues);
//				model.put("peopleValues", peopleValuesToPrint);
				
			}	
		}catch(ApplicationThrowable ath){
			model.put("applicationThrowable", ath);
			return new ModelAndView("error/PrintElementsMyMarkedList", model);
		}

		
		return new ModelAndView("search/PrintElementsSearch", model);
	}
	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}
	/**
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}
	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}
	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}
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

}