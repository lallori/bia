/*
 * SaveUserSearchFilter.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.medici.docsources.command.search.SaveUserSearchFilterCommand;
import org.medici.docsources.command.search.SaveUserSearchFilterCommand.SaveType;
import org.medici.docsources.common.search.AdvancedSearchFactory;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.exception.ApplicationThrowable;
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
 * Controller for action "Save User Search Filter".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/SaveUserSearchFilter")
public class SaveUserSearchFilter {
	@Autowired
	private SearchService searchService;  
	@Autowired(required = false)
	@Qualifier("saveUserSearchFilterValidator")
	private Validator validator;

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") SaveUserSearchFilterCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm();
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				SearchFilter searchFilter = new SearchFilter();
				if (command.getSaveType().equals(SaveType.newSearch)) {
					searchFilter.setId(command.getIdSearchFilter());
				} else {
					searchFilter.setId(command.getIdSearchFilterToReplace());
				}
				searchFilter.setFilterName(command.getSaveAs());
				searchFilter.setSearchType(command.getSearchType());
				searchFilter.setFilterData(AdvancedSearchFactory.create(command));

				if (command.getSaveType().equals(SaveType.newSearch)) {
					getSearchService().addSearchFilter(searchFilter);
				} else {
					getSearchService().replaceSearchFilter(searchFilter);
				}
			} catch(ApplicationThrowable applicationThrowable) {
			}
			return new ModelAndView("response/SaveSearchFilterOK",model);
		}
	}	

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(){
		Map<String, Object> model = new HashMap<String, Object>();

		try{
			List<SearchFilter> searchFilters = getSearchService().getUserSearchFilters();
			
			model.put("searchFilters", searchFilters);
		} catch(ApplicationThrowable applicationThrowable) {
		}
		return new ModelAndView("search/SaveUserSearchFilterModalWindow", model);
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
	
	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}
}