/*
 * AdvancedSearch.java
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
 * 
 */
package org.medici.bia.controller.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.common.search.AdvancedSearch;
import org.medici.bia.common.search.AdvancedSearchDocument;
import org.medici.bia.common.search.AdvancedSearchFactory;
import org.medici.bia.common.search.AdvancedSearchPeople;
import org.medici.bia.common.search.AdvancedSearchPlace;
import org.medici.bia.common.search.AdvancedSearchVolume;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.PlaceType;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TopicList;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Advanced Search".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/AdvancedSearch")
public class AdvancedSearchController {
	@Autowired
	private SearchService searchService;
	@Autowired(required = false)
	@Qualifier("advancedSearchValidator")
	private Validator validator;
	
	/**
	 * This method return form view with advanced search filled. This is used for
	 * first advanced search page and refine function.
	 * 
	 * @param command
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupPage(@ModelAttribute("command") AdvancedSearchCommand command, HttpSession session){
		Map<String, Object> model = new HashMap<String, Object>(0);
		SearchFilter searchFilter = null;
		List<Month> months = null;
		List<PlaceType> placeTypes = null;
		List<TopicList> topicsList = null;

		try {
			months = getSearchService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/AdvancedSearchDocuments", model);
		}

		// we get our map which contains all user's filter used at runtime. 
		Map<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);

		// if searchUUID is present, it's a refine search
		if (command.getSearchUUID() != null) {
			// if searchFilter is present in map we get  
			if (searchFilterMap.get(command.getSearchUUID()) != null) {
				searchFilter = searchFilterMap.get(command.getSearchUUID());
				// we don't need to update user map
			} else {
				// if search filter is not present in request, user choice a new search filter 
				searchFilter = new SearchFilter(0, command.getSearchType());
				searchFilter.setDateCreated(new Date());
				searchFilter.setDateUpdated(new Date());
				// we update user map
				searchFilterMap.put(command.getSearchUUID(), searchFilter);
				// we update information in session
				session.setAttribute("searchFilterMap", searchFilterMap);
			}
		} else if ((command.getIdSearchFilter()!=null) && (command.getIdSearchFilter()>0)){
			// if searchUUID is not presentt, it's a new search
			command.setSearchUUID(UUID.randomUUID().toString());
			try {
				searchFilter = getSearchService().getUserSearchFilter(command.getIdSearchFilter());
				// we update user map
				searchFilterMap.put(command.getSearchUUID(), searchFilter);
				// we update information in session
				session.setAttribute("searchFilterMap", searchFilterMap);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/AdvancedSearch", model);
			}
		} else if ((command.getIdSearchFilter()!=null) && (command.getIdSearchFilter()>0)){
			// if searchUUID is not presentt, it's a new search
			command.setSearchUUID(UUID.randomUUID().toString());
			try {
				searchFilter = getSearchService().getUserSearchFilter(command.getIdSearchFilter());
				// we update user map
				searchFilterMap.put(command.getSearchUUID(), searchFilter);
				// we update information in session
				session.setAttribute("searchFilterMap", searchFilterMap);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/AdvancedSearch", model);
			}
		} else {
			// if searchUUID is not presentt, it's a new search
			command.setSearchUUID(UUID.randomUUID().toString());

			// User request a new search function. 
			searchFilter = new SearchFilter(0, command.getSearchType());
			searchFilter.setDateCreated(new Date());
			searchFilter.setDateUpdated(new Date());
			searchFilter.setId(new Integer(0));
			if(searchFilter.getSearchType().equals(SearchType.DOCUMENT)){
				searchFilter.setFilterData(new AdvancedSearchDocument());
			}else if(searchFilter.getSearchType().equals(SearchType.VOLUME)){
				searchFilter.setFilterData(new AdvancedSearchVolume());
			}else if(searchFilter.getSearchType().equals(SearchType.PEOPLE)){
				searchFilter.setFilterData(new AdvancedSearchPeople());
			}else{
				searchFilter.setFilterData(new AdvancedSearchPlace());
			}
			searchFilter.setUser(new User(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
			// we update user map
			searchFilterMap.put(command.getSearchUUID(), searchFilter);
			// we update information in session
			session.setAttribute("searchFilterMap", searchFilterMap);
		}

		model.put("searchFilter", searchFilter);
		
		if (searchFilter.getSearchType().equals(SearchType.DOCUMENT)) {
			try{
				topicsList = getSearchService().getTopicsList();
				model.put("topicsList", topicsList);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/AdvancedSearchDocuments", model);
			}
			return new ModelAndView("search/AdvancedSearchDocuments", model);
		} else if (searchFilter.getSearchType().equals(SearchType.PEOPLE)) {
			return new ModelAndView("search/AdvancedSearchPeople", model);
		} else if (searchFilter.getSearchType().equals(SearchType.PLACE)) {
			try {
				placeTypes = getSearchService().getPlaceTypes();
				model.put("placeTypes", placeTypes);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/AdvancedSearchPlaces", model);
			}
			return new ModelAndView("search/AdvancedSearchPlaces", model);
		} else {
			return new ModelAndView("search/AdvancedSearchVolumes", model);
		}
	}
	
	/**
	 * 
	 * @param command
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView executeSearch(@Valid @ModelAttribute("command") AdvancedSearchCommand command, BindingResult result, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		SearchFilter searchFilter = null;  

		// we prelevate our map which contains all user's filter used at runtime. 
		Map<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);

		// if searchFilter is present in map we get  
		if (searchFilterMap.get(command.getSearchUUID()) != null) {
			searchFilter = searchFilterMap.get(command.getSearchUUID());
			// we need to set correct searchType in command
			command.setSearchType(searchFilter.getSearchType());
		} else {
			// if search filter is not present in request, user make a new search filter 
			searchFilter = new SearchFilter(command.getSearchType());
			searchFilter.setDateCreated(new Date());
			searchFilter.setDateUpdated(new Date());
		}

		// we update runtime filter with input from form 
		AdvancedSearch advancedSearch = AdvancedSearchFactory.createFromAdvancedSearchCommand(command);
		searchFilter.setFilterData(advancedSearch);
		// we update user map
		searchFilterMap.put(command.getSearchUUID(), searchFilter);
		// we update information in session
		session.setAttribute("searchFilterMap", searchFilterMap);
		
		// Add outputFields;
		List<String> outputFields = getOutputFields(searchFilter.getSearchType());
		model.put("outputFields", outputFields);
		model.put("yourSearch", searchFilter.getFilterData().toString());
		
		return new ModelAndView("search/AdvancedSearchResult", model);
	}

	/**
	 * This method return a list of output fields by searchType
	 * @param searchType
	 * @return
	 */
	private List<String> getOutputFields(SearchType searchType) {
		List<String> outputFields = null;

		// Search operation is made by View with a jquery plugin to contextualized AjaxController
		if (searchType.equals(SearchType.DOCUMENT)) {
			outputFields = new ArrayList<String>(6);
			outputFields.add("Sender");
			outputFields.add("Recipient");
			outputFields.add("Date");
			outputFields.add("Sender Location");
			outputFields.add("Recipient Location");
			outputFields.add("Volume / Folio");
		} else if (searchType.equals(SearchType.PEOPLE)) {
			outputFields = new ArrayList<String>(5);
			outputFields.add("Name");
			outputFields.add("Gender");
			//outputFields.add("Date");
			outputFields.add("Born Date");
			outputFields.add("Death Date");
			outputFields.add("Documents Related");
		} else if (searchType.equals(SearchType.PLACE)) {
			outputFields = new ArrayList<String>(5);
			outputFields.add("Place Name");
			outputFields.add("Place Type");
			outputFields.add("People Related");
			outputFields.add("From/To Documents Related");
			outputFields.add("Documents Related (Topics)");
		} else {
			outputFields = new ArrayList<String>(5);
			outputFields.add("Carteggio");
			outputFields.add("Filza N.(MDP)");
			outputFields.add("Start Date");
			outputFields.add("End Date");
			outputFields.add("Digitized");
		}
		
		return outputFields;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the validator
	 */
	public Validator getAdvancedSearchDocumentsValidator() {
		return validator;
	}

	/**
	 * @param searchService the searchService to set
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	/**
	 * @return the searchService
	 */
	public SearchService getSearchService() {
		return searchService;
	}

}