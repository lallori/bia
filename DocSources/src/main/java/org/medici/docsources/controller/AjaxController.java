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
package org.medici.docsources.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for User Actions.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("BasicAjaxController")
public class AjaxController {
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
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/ajax/PaginationSearchData.json", method = RequestMethod.GET)
	public ModelAndView searchPagination(@RequestParam("searchType") String searchType, 
								   		 @RequestParam("sSearch") String alias, 
								   		 @RequestParam("iDisplayStart") Integer firstRecord,
									     @RequestParam("iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (searchType.toLowerCase().trim().equals("documents")) {
		}
		
		if (searchType.toLowerCase().trim().equals("people")) {
		}
		
		if (searchType.toLowerCase().trim().equals("places")) {
		}
		
		if (searchType.toLowerCase().trim().equals("volumes")) {
			Page page = null;

			PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

			try {
				page = getVolBaseService().searchVolumes(alias, paginationFilter);
				//searchResults = getVolBaseService().findUsers(user);
			} catch (ApplicationThrowable aex) {
			}

			List resultList = new ArrayList();
			for (Volume currentVolume : (List<Volume>)page.getList()) {
				StringBuffer hrefBegin = new StringBuffer("<a href=\"");
				hrefBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
				hrefBegin.append("/src/volbase/ShowDocument?summaryId=").append(currentVolume.getSummaryId());
				hrefBegin.append("\">");
				String hrefEnd = "</a>";
				List singleRow = new ArrayList();
				singleRow.add(currentVolume.getSerieList().getTitle());

				String startDate = "";
				if (currentVolume.getStartYear() != null) {
					startDate += currentVolume.getStartYear();
				}
				if (StringUtils.isNotEmpty(currentVolume.getStartMonth())) {
					startDate += (startDate.length() > 0 ) ? " " : "";
					startDate += currentVolume.getStartMonth();
				}
				if (currentVolume.getStartDay() != null) {
					startDate += (startDate.length() > 0 ) ? " " : "";
					startDate += currentVolume.getStartDay();
				}
				singleRow.add(startDate);
				
				String endDate = "";
				if (currentVolume.getEndYear() != null) {
					endDate += currentVolume.getEndYear();
				}
				if (StringUtils.isNotEmpty(currentVolume.getEndMonth())) {
					endDate += (endDate.length() > 0 ) ? " " : "";
					endDate += currentVolume.getEndMonth();
				}
				if (currentVolume.getEndDay() != null) {
					endDate += (endDate.length() > 0 ) ? " " : "";
					endDate += currentVolume.getEndDay();
				}
				singleRow.add(endDate);
				
				StringBuffer subTitle = new StringBuffer("");
				if (StringUtils.isNotEmpty(currentVolume.getSerieList().getSubTitle1())) {
					subTitle.append(currentVolume.getSerieList().getSubTitle1());
					if (StringUtils.isNotEmpty(currentVolume.getSerieList().getSubTitle2()))
						subTitle.append(" / ").append(currentVolume.getSerieList().getSubTitle2());
				}
				subTitle.append(" MdP ").append(currentVolume.getVolNum());
				if (StringUtils.isNotEmpty(currentVolume.getVolLetExt())) {
					subTitle.append(currentVolume.getVolLetExt());
				}
				singleRow.add(subTitle.toString());
				resultList.add(singleRow);
			}
			model.put("iEcho", "" + 1);
			model.put("iTotalDisplayRecords", page.getTotal());
			model.put("iTotalRecords", page.getTotal());
			model.put("aaData", resultList);
		}

		return new ModelAndView("responseOK", model);
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