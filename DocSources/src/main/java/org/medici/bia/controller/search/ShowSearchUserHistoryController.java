/*
 * ShowSearchUserHistoryController.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.medici.bia.command.search.ShowSearchUserHistoryCommand;
import org.medici.bia.command.search.SimpleSearchCommand;
import org.medici.bia.common.search.AdvancedSearch;
import org.medici.bia.common.search.AdvancedSearchFactory;
import org.medici.bia.common.search.SimpleSearch;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.common.search.SimpleSearchDocument;
import org.medici.bia.common.search.SimpleSearchPeople;
import org.medici.bia.common.search.SimpleSearchPlace;
import org.medici.bia.common.search.SimpleSearchVolume;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.PlaceType;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TopicList;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.search.SearchService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/src/ShowSearchUserHistory")
public class ShowSearchUserHistoryController {
	@Autowired
	private SearchService searchService;
	@Autowired
	private UserService userService;
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

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
	public ModelAndView setupPage(@ModelAttribute("command") ShowSearchUserHistoryCommand command, HttpSession session){
		Map<String, Object> model = new HashMap<String, Object>(0);
		SearchFilter searchFilter = new SearchFilter();
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

		command.setSearchUUID(UUID.randomUUID().toString());
		UserHistory userHistorySearchEntry = null;
		if(command.getIdUserHistory() != null){
			try {
				userHistorySearchEntry = getSearchService().searchUserHistorySearchEntry(command.getIdUserHistory());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowSearchUserHistory", model);
			}
		}
		if(userHistorySearchEntry != null){
			if(userHistorySearchEntry.getSearchData() instanceof SimpleSearch){
				if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_DOCUMENT)){
					SimpleSearchDocument simpleSearch = (SimpleSearchDocument)userHistorySearchEntry.getSearchData();
					searchFilter.setFilterData(AdvancedSearchFactory.createFromSimpleSearchCommand(new SimpleSearchCommand(simpleSearch, simpleSearch.getSimpleSearchPerimeter(), command.getSearchUUID())));
					searchFilter.setSearchType(SearchType.DOCUMENT);
				}else if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_PEOPLE)){
					SimpleSearchPeople simpleSearch = (SimpleSearchPeople) userHistorySearchEntry.getSearchData();
					searchFilter.setFilterData(AdvancedSearchFactory.createFromSimpleSearchCommand(new SimpleSearchCommand(simpleSearch, SimpleSearchPerimeter.PEOPLE, command.getSearchUUID())));
					searchFilter.setSearchType(SearchType.PEOPLE);
				}else if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_PLACE)){
					SimpleSearchPlace simpleSearch = (SimpleSearchPlace) userHistorySearchEntry.getSearchData();
					searchFilter.setFilterData(AdvancedSearchFactory.createFromSimpleSearchCommand(new SimpleSearchCommand(simpleSearch, SimpleSearchPerimeter.PLACE, command.getSearchUUID())));
					searchFilter.setSearchType(SearchType.PLACE);
				}else if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_VOLUME)){
					SimpleSearchVolume simpleSearch = (SimpleSearchVolume) userHistorySearchEntry.getSearchData();
					searchFilter.setFilterData(AdvancedSearchFactory.createFromSimpleSearchCommand(new SimpleSearchCommand(simpleSearch, SimpleSearchPerimeter.VOLUME, command.getSearchUUID())));
					searchFilter.setSearchType(SearchType.VOLUME);
				}
			}else{
				searchFilter.setFilterData((AdvancedSearch) userHistorySearchEntry.getSearchData());
				if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_DOCUMENT)){
					searchFilter.setSearchType(SearchType.DOCUMENT);
				}else if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_PEOPLE)){
					searchFilter.setSearchType(SearchType.PEOPLE);
				}else if (userHistorySearchEntry.getCategory().equals(Category.SEARCH_PLACE)){
					searchFilter.setSearchType(SearchType.PLACE);
				}else if(userHistorySearchEntry.getCategory().equals(Category.SEARCH_VOLUME)){
					searchFilter.setSearchType(SearchType.VOLUME);
				}
			}
		}
		searchFilter.setDateCreated(new Date());
		searchFilter.setDateUpdated(new Date());
		command.setIdSearchFilter(0);
		command.setSearchType(searchFilter.getSearchType());
		
		// we update user map
		searchFilterMap.put(command.getSearchUUID(), searchFilter);
		// we update information in session
		session.setAttribute("searchFilterMap", searchFilterMap);
		

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