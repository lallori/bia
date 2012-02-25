/*
 * AjaxController.java
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
package org.medici.docsources.controller.digitization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.SchedoneSearch;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for Digitization.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("DigitizationAjaxController")
public class AjaxController {
	@Autowired
	private DigitizationService digitizationService;

	/**
	 * @param digitizationService the digitizationService to set
	 */
	public void setDigitizationService(DigitizationService digitizationService) {
		this.digitizationService = digitizationService;
	}

	/**
	 * @return the digitizationService
	 */
	public DigitizationService getDigitizationService() {
		return digitizationService;
	}


	/**
	 * 
	 * @param schedoneId
	 * @return
	 */
	@RequestMapping(value = "/digitization/FindSchedone", method = RequestMethod.GET)
	public ModelAndView FindSchedone(@RequestParam(value="schedoneId", required=true) Integer schedoneId) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			Schedone schedone = getDigitizationService().findSchedone(schedoneId);
			if(schedone!= null){
				model.put("schedoneId", schedone.getSchedoneId());
			}else{
				model.put("schedoneId", "");
			}			
		}catch(ApplicationThrowable th){
			model.put("schedoneId", "");
		}

		return new ModelAndView("responseOK", model);		
	}

	/**
	 * 
	 * @param httpSession
	 * @param alias
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/digitization/SchedoneSearchPagination.json", method = RequestMethod.GET)
	public ModelAndView schedoneSearchPagination(HttpSession httpSession,
											@RequestParam(value="alias", required=false) String alias,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection);

		try {
			page = getDigitizationService().searchSchedones(new SchedoneSearch(alias), paginationFilter);
			
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}


		List<List<String>> resultList = new ArrayList<List<String>>();
		for (Schedone currentSchedone : (List<Schedone>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			// MDP
			singleRow.add(HtmlUtils.showSchedoneMDP(currentSchedone));         
			// Catalog Description
			singleRow.add(HtmlUtils.showSchedoneDescription(currentSchedone));
			// Active
			singleRow.add(HtmlUtils.showSchedoneActive(currentSchedone));      
			// Edit it
			singleRow.add(HtmlUtils.showSchedoneEditIt(currentSchedone));      
			// Deactive it
			singleRow.add(HtmlUtils.showSchedoneDeactivateIt(currentSchedone));     

			resultList.add(singleRow);
		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);		
	}
}