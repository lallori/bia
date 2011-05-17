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
package org.medici.docsources.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.lucene.search.SortField;
import org.medici.docsources.common.html.HtmlUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
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
 * AJAX Controller for search.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("SearchAjaxController")
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
	@RequestMapping(value = "/src/SimpleSearchPagination.json", method = RequestMethod.GET)
	public ModelAndView searchPagination(@RequestParam(value="searchType") String searchType, 
								   		 @RequestParam(value="sSearch") String alias,
								   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumn,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (searchType.toLowerCase().trim().equals("documents")) {
			simpleSearchDocuments(model, alias, sortingColumn, sortingDirection, firstRecord, length);
		}
		
		if (searchType.toLowerCase().trim().equals("people")) {
			simpleSearchPeople(model, alias, sortingColumn, sortingDirection, firstRecord, length);
		}
		
		if (searchType.toLowerCase().trim().equals("places")) {
			simpleSearchPlaces(model, alias, sortingColumn, sortingDirection, firstRecord, length);
		}
		
		if (searchType.toLowerCase().trim().equals("volumes")) {
			simpleSearchVolumes(model, alias, sortingColumn, sortingDirection, firstRecord, length);
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
	
	/**
	 * 
	 * @param model
	 * @param searchText
	 * @param firstRecord
	 * @param length
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void simpleSearchDocuments(Map<String, Object> model, String searchText, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);
		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
				case 0:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				case 1:
					paginationFilter.addSortingCriteria("recipientPeople.mapNameLf", sortingDirection);
					break;
				case 2:
					paginationFilter.addSortingCriteria("docYear", sortingDirection, SortField.INT);
					paginationFilter.addSortingCriteria("docMonth.monthNum", sortingDirection, SortField.STRING);
					paginationFilter.addSortingCriteria("docDay", sortingDirection, SortField.INT);
					break;
				case 3:
					paginationFilter.addSortingCriteria("senderPlace.placeName", sortingDirection);
					break;
				case 4:
					paginationFilter.addSortingCriteria("recipientPlace.placeName", sortingDirection);
					break;
				case 5:
					paginationFilter.addSortingCriteria("volume.volNum", sortingDirection, SortField.INT);
					paginationFilter.addSortingCriteria("volume.volLetExt", sortingDirection, SortField.STRING);
					//paginationFilter.addSortingCriteria("folioNum", sortingDirection, SortField.INT);
					break;
				/*case 6:
					paginationFilter.addSortingCriteria("folioNum", sortingDirection, SortField.INT);
					break;*/
				default:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
			}
		}

		try {
			page = getDocBaseService().simpleSearchDocuments(searchText, paginationFilter);
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

			if (currentDocument.getDocumentDate() != null) 
				singleRow.add(currentDocument.getDocumentDate());
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
			
			if (currentDocument.getVolume()!= null && currentDocument.getFolioNum() != null)
				singleRow.add(currentDocument.getVolume().toString() + "/" + currentDocument.getFolioNum().toString());
			else
			{
				if(currentDocument.getVolume() != null && currentDocument.getFolioNum() == null)
					singleRow.add(currentDocument.getVolume().toString() + "/NNF");
				else
					singleRow.add("");
			}
				
			/*
			if (currentDocument.getFolioNum() != null)
				singleRow.add(currentDocument.getFolioNum().toString());
			else
				singleRow.add("");
			*/
			resultList.add(HtmlUtils.showDocument(singleRow, currentDocument.getEntryId()));
		}
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * This method performs a simple search on people dictionary.
	 * 
	 * @param model
	 * @param searchText
	 * @param firstRecord
	 * @param length
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void simpleSearchPeople(Map<String, Object> model, String searchText, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);
		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
				case 0:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				case 1:
					paginationFilter.addSortingCriteria("recipientPeople.mapNameLf", sortingDirection);
					break;
				case 2:
					paginationFilter.addSortingCriteria("dateApprox", sortingDirection);
					break;
				case 3:
					paginationFilter.addSortingCriteria("senderPlace.placeName", sortingDirection);
					break;
				case 4:
					paginationFilter.addSortingCriteria("recipientPlace.placeName", sortingDirection);
					break;
				case 5:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				case 6:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				default:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
			}		
		}

		try {
			page = getPeopleBaseService().simpleSearchPeople(searchText, paginationFilter);
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

	/**
	 * This method performs a simple search on places dictionary.
	 * 
	 * @param model
	 * @param alias
	 * @param firstRecord
	 * @param length
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void simpleSearchPlaces(Map<String, Object> model, String searchText, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);
		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
				case 0:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				case 1:
					paginationFilter.addSortingCriteria("recipientPeople.mapNameLf", sortingDirection);
					break;
				case 2:
					paginationFilter.addSortingCriteria("dateApprox", sortingDirection);
					break;
				case 3:
					paginationFilter.addSortingCriteria("senderPlace.placeName", sortingDirection);
					break;
				case 4:
					paginationFilter.addSortingCriteria("recipientPlace.placeName", sortingDirection);
					break;
				case 5:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				case 6:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
				default:
					paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
					break;
			}
		}

		try {
			page = getGeoBaseService().simpleSearchPlaces(searchText, paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (Place currentPlace : (List<Place>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentPlace.getPlaceNameFull());
			singleRow.add(currentPlace.getPlType());
			singleRow.add(currentPlace.getParentPlace().getPlaceName());
			singleRow.add(currentPlace.getParentType());

			resultList.add(HtmlUtils.showPlace(singleRow, currentPlace.getPlaceAllId()));
		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * This method performs a simple search on volumes dictionary.
	 * 
	 * @param model
	 * @param searchText
	 * @param firstRecord
	 * @param length
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void simpleSearchVolumes(Map<String, Object> model, String searchText, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);
		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
				case 0:
					paginationFilter.addSortingCriteria("serieList", sortingDirection);
					break;
				case 1:
					paginationFilter.addSortingCriteria("mdp", sortingDirection);
					break;
				case 2:
					paginationFilter.addSortingCriteria("startDate", sortingDirection);
					break;
				case 3:
					paginationFilter.addSortingCriteria("endDate", sortingDirection);
					break;
				default:
					paginationFilter.addSortingCriteria("serieList", sortingDirection);
					break;
			}
		}

		try {
			page = getVolBaseService().simpleSearchVolumes(searchText, paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (Volume currentVolume : (List<Volume>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentVolume.getSerieList().toString());
			singleRow.add(currentVolume.getMDP());
			singleRow.add(currentVolume.getStartDate());
			singleRow.add(currentVolume.getEndDate());

			resultList.add(HtmlUtils.showVolume(singleRow, currentVolume.getSummaryId()));
		}
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}
}