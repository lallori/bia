/*
 * EraseSearchFiltersController.java
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

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to remove elements form user marked list.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/EraseSearchFilters")
public class EraseSearchFiltersController {
	@Autowired
	private SearchService searchService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@RequestParam("idToErase") String idToErase) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			if(!idToErase.equals("")){
				StringTokenizer stringTokenizer = new StringTokenizer(idToErase, "+");
				List<Integer> idElements = new ArrayList<Integer>(); 
				while(stringTokenizer.hasMoreTokens()){
					String current = stringTokenizer.nextToken();
					if(NumberUtils.isNumber(current)){
						idElements.add(NumberUtils.createInteger(current));
					}
				}
				getSearchService().removeSearchFiltersUser(idElements);				

			}	
		}catch(ApplicationThrowable ath){
			model.put("applicationThrowable", ath);
			return new ModelAndView("error/EraseSearchFilters", model);
		}

		
		return new ModelAndView("search/ShowUserSearchFiltersModalWindow", model);
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