/*
 * AdvancedSearchDocuments.java
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
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.medici.docsources.command.search.AdvancedSearchDocumentsCommand;
import org.medici.docsources.common.search.AdvancedSearchDocument;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Advanced Search on Documents".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/AdvancedSearchDocuments")
public class AdvancedSearchDocumentsController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private SearchService searchService;
	@Autowired(required = false)
	@Qualifier("advancedSearchDocumentsValidator")
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
	public ModelAndView setupPage(@ModelAttribute("command") AdvancedSearchDocumentsCommand command, HttpSession session){
		Map<String, Object> model = new HashMap<String, Object>();
		SearchFilter searchFilter = null;
		List<Month> months = null;

		try {
			months = getDocBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/AdvancedSearchDocuments", model);
		}

		// we prelevate our map which contains all user's filter used at runtime. 
		HashMap<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);

		// if searchUUID is present, it's a refine search
		if (command.getSearchUUID() != null) {
			// if searchFilter is present in map we get  
			if (searchFilterMap.get(command.getSearchUUID()) != null) {
				searchFilter = searchFilterMap.get(command.getSearchUUID());
				// we don't need to update user map
			} else {
				// if search filter is not present in request, user make a new search filter 
				searchFilter = new SearchFilter(0, SearchType.DOCUMENT);
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
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/AdvancedSearchDocuments", model);
			}
		} else {
			// if searchUUID is not presentt, it's a new search
			command.setSearchUUID(UUID.randomUUID().toString());

			// User request a new request. 
			searchFilter = new SearchFilter(0, SearchType.DOCUMENT);
			searchFilter.setDateCreated(new Date());
			searchFilter.setDateUpdated(new Date());
			searchFilter.setId(new Integer(0));
			searchFilter.setFilterData(new AdvancedSearchDocument());
			// we update user map
			searchFilterMap.put(command.getSearchUUID(), searchFilter);
			// we update information in session
			session.setAttribute("searchFilterMap", searchFilterMap);
		}

		model.put("searchFilter", searchFilter);
		
		return new ModelAndView("search/AdvancedSearchDocuments", model);
	}
	
	/**
	 * 
	 * @param command
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView executeSearch(@Valid @ModelAttribute("command") AdvancedSearchDocumentsCommand command, BindingResult result, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>();
		SearchFilter searchFilter = null;  

		// we prelevate our map which contains all user's filter used at runtime. 
		HashMap<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);

		// if searchFilter is present in map we get  
		if (searchFilterMap.get(command.getSearchUUID()) != null) {
			searchFilter = searchFilterMap.get(command.getSearchUUID());
		} else {
			// if search filter is not present in request, user make a new search filter 
			searchFilter = new SearchFilter(SearchType.DOCUMENT);
			searchFilter.setDateCreated(new Date());
			searchFilter.setDateUpdated(new Date());
		}

		// we update runtime filter with input from form 
		AdvancedSearchDocument advancedSearchDocument = new AdvancedSearchDocument();
		advancedSearchDocument.initFromAdvancedSearchDocumentsCommand(command);
		searchFilter.setFilterData(advancedSearchDocument);
		// we update user map
		searchFilterMap.put(command.getSearchUUID(), searchFilter);
		// we update information in session
		session.setAttribute("searchFilterMap", searchFilterMap);
		
		return new ModelAndView("search/AdvancedSearchResultDocuments", model);
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}
	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
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