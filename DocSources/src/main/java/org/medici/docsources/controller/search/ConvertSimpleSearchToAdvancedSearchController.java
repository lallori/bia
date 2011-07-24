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
package org.medici.docsources.controller.search;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;
import org.medici.docsources.common.search.AdvancedSearch;
import org.medici.docsources.common.search.AdvancedSearchFactory;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Advanced Search".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/ConvertSimpleSearchToAdvancedSearch")
public class ConvertSimpleSearchToAdvancedSearchController {
	@Autowired
	private SearchService searchService;
	
	/**
	 * This method return form view with advanced search filled. This is used for
	 * first advanced search page and refine function.
	 * 
	 * @param command Command containing parameters of simple search. It's not defined as model.
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupPage(SimpleSearchCommand command, HttpSession session){
		Map<String, Object> model = new HashMap<String, Object>();
		SearchFilter searchFilter = null;  
		List<Month> months = null;

		try {
			months = getSearchService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/ConvertSimpleSearchToAdvancedSearch", model);
		}

		// we prelevate our map which contains all user's filter used at runtime. 
		HashMap<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);

		// if search filter is not present in request, user make a new search filter 
		searchFilter = new SearchFilter(command.getSearchType());
		searchFilter.setDateCreated(new Date());
		searchFilter.setDateUpdated(new Date());

		// we update runtime filter with input from form 
		AdvancedSearch advancedSearch = AdvancedSearchFactory.create(command);
		searchFilter.setFilterData(advancedSearch);
		model.put("searchFilter", searchFilter);

		// we update user map
		searchFilterMap.put(command.getSearchUUID(), searchFilter);
		// we update information in session
		session.setAttribute("searchFilterMap", searchFilterMap);

		// Generate command for output page. 
		AdvancedSearchCommand advancedSearchCommand = new AdvancedSearchCommand(advancedSearch, command.getSearchType(), command.getSearchUUID());
		model.put("command", advancedSearchCommand);

		if (searchFilter.getSearchType().equals(SearchType.DOCUMENT)) {
			return new ModelAndView("search/AdvancedSearchDocuments", model);
		} else if (searchFilter.equals(SearchType.PEOPLE)) {
			return new ModelAndView("search/AdvancedSearchPeople", model);
		} else if (searchFilter.equals(SearchType.PLACE)) {
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