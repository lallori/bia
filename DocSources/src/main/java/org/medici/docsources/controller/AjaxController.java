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

import org.medici.docsources.common.html.HtmlUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
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
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Main Controller. It drives simple search.
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
	@RequestMapping(value = "/src/PaginationSearchData.json", method = RequestMethod.GET)
	public ModelAndView searchPagination(@RequestParam("searchType") String searchType, 
								   		 @RequestParam("sSearch") String alias, 
								   		 @RequestParam("iDisplayStart") Integer firstRecord,
									     @RequestParam("iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (searchType.toLowerCase().trim().equals("documents")) {
			Page page = null;

			PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

			try {
				page = getDocBaseService().simpleSearchDocuments(alias, paginationFilter);
			} catch (ApplicationThrowable aex) {
			}

			List resultList = new ArrayList();
			for (Document currentDocument : (List<Document>)page.getList()) {
				List singleRow = new ArrayList();
				if (currentDocument.getSenderPeople() != null)
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				else
					singleRow.add("");
				
				if (currentDocument.getRecipientPeople() != null)
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				else
					singleRow.add("");
				
				if (currentDocument.getDateApprox() != null)
					singleRow.add(currentDocument.getDateApprox());
				else
					singleRow.add("");
				
				if (currentDocument.getSenderPlace() != null)
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				else
					singleRow.add("");
				
				if (currentDocument.getRecipientPlace() != null)
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				else
					singleRow.add("");
				
				if (currentDocument.getVolume()!= null)
					singleRow.add(currentDocument.getVolume().toString());
				else
					singleRow.add("");
				
				if (currentDocument.getFolioNum() != null)
					singleRow.add(currentDocument.getFolioNum().toString());
				else
					singleRow.add("");
				
				resultList.add(HtmlUtils.showDocument(singleRow, currentDocument.getEntryId()));
			}
			model.put("iEcho", "1");
			model.put("iTotalDisplayRecords", page.getTotal());
			model.put("iTotalRecords", page.getTotal());
			model.put("aaData", resultList);
		}
		
		if (searchType.toLowerCase().trim().equals("people")) {
			Page page = null;

			PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

			try {
				page = getPeopleBaseService().searchPeople(alias, paginationFilter);
			} catch (ApplicationThrowable aex) {
			}

			List resultList = new ArrayList();
			for (People currentPerson : (List<People>)page.getList()) {
				List singleRow = new ArrayList();
				singleRow.add(currentPerson.getMapNameLf());
				singleRow.add((currentPerson.getGender() != null) ? currentPerson.getGender().toString() : "");
				singleRow.add(currentPerson.getBornDate());
				singleRow.add(currentPerson.getDeathDate());
				singleRow.add("" + currentPerson.getPoLink().size());
				resultList.add(HtmlUtils.showPeople(singleRow, currentPerson.getPersonId()));
			}
			model.put("iEcho", "" + 1);
			model.put("iTotalDisplayRecords", page.getTotal());
			model.put("iTotalRecords", page.getTotal());
			model.put("aaData", resultList);
		}
		
		if (searchType.toLowerCase().trim().equals("places")) {
		}
		
		if (searchType.toLowerCase().trim().equals("volumes")) {
			Page page = null;

			PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

			try {
				page = getVolBaseService().searchVolumes(alias, paginationFilter);
			} catch (ApplicationThrowable aex) {
			}

			List resultList = new ArrayList();
			for (Volume currentVolume : (List<Volume>)page.getList()) {
				List singleRow = new ArrayList();
				singleRow.add(currentVolume.getSerieList().getTitle());
				singleRow.add(currentVolume.getStartDate());
				singleRow.add(currentVolume.getEndDate());
				
				StringBuffer subTitle = new StringBuffer(currentVolume.getSerieList().toString());
				subTitle.append(" MdP ").append(currentVolume.getMDP());
				singleRow.add(subTitle.toString());
				
				resultList.add(HtmlUtils.showVolume(singleRow, currentVolume.getSummaryId()));
			}
			model.put("iEcho", "1");
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