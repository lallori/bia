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

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.lucene.search.SortField;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.SimpleSearchDocument;
import org.medici.docsources.common.search.SimpleSearchPeople;
import org.medici.docsources.common.search.SimpleSearchPlace;
import org.medici.docsources.common.search.SimpleSearchTopic;
import org.medici.docsources.common.search.SimpleSearchVolume;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.search.SearchService;
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
	private SearchService searchService;

	/**
	 * 
	 * @param model
	 * @param httpSession
	 * @param paginationFilter
	 * @param searchNumber
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void advancedSearchDocuments(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter, String searchUUID) {
		Page page = null;
		HashMap<String, SearchFilter> searchFilterMap = (HashMap<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);

		try {
			page = getSearchService().searchDocuments(searchFilter.getFilterData(), paginationFilter);
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

			singleRow.add(DateUtils.getStringDate(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			
			if (currentDocument.getSenderPlace() != null)
				singleRow.add(currentDocument.getSenderPlace().getPlaceName());
			else
				singleRow.add("");
			
			if (currentDocument.getRecipientPlace() != null)
				singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
			else
				singleRow.add("");
			
			if (currentDocument.getMDPAndFolio() != null)
				singleRow.add("<b>"+currentDocument.getMDPAndFolio()+ "</b>");
			else
				singleRow.add("");

			resultList.add(HtmlUtils.showDocument(singleRow, currentDocument.getEntryId()));
		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param searchType
	 * @param alias
	 * @param sortingColumn
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/src/AdvancedSearchPagination.json", method = RequestMethod.GET)
	public ModelAndView advancedSearchPagination(HttpSession httpSession,
											@RequestParam(value="searchType") String searchType,
											@RequestParam(value="searchUUID") String searchUUID,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType.toLowerCase().trim().equals("documents")) {
			advancedSearchDocuments(model, httpSession, paginationFilter, searchUUID);
		} else if (searchType.toLowerCase().trim().equals("people")) {
			advancedSearchPeople(model, httpSession, paginationFilter, searchUUID);
		} else if (searchType.toLowerCase().trim().equals("places")) {
			advancedSearchPlaces(model, httpSession, paginationFilter, searchUUID);
		} else if (searchType.toLowerCase().trim().equals("volumes")) {
			advancedSearchVolumes(model, httpSession, paginationFilter, searchUUID);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param model
	 * @param httpSession
	 * @param paginationFilter
	 * @param searchUUID
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void advancedSearchPeople(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter, String searchUUID) {
		Page page = null;
		SearchFilter searchFilter = (SearchFilter) httpSession.getAttribute("searchFilter" + searchUUID);
		
		try {
			page = getSearchService().searchPeople(searchFilter.getFilterData(), paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (People currentPerson : (List<People>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentPerson.getMapNameLf());
			singleRow.add((currentPerson.getGender() != null) ? currentPerson.getGender().toString() : "");
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDate(currentPerson.getBornYear(), currentPerson.getBornMonth(), currentPerson.getBornDay()));
			singleRow.add(DateUtils.getStringDate(currentPerson.getDeathYear(), currentPerson.getDeathMonth(), currentPerson.getDeathDay()));
			resultList.add(HtmlUtils.showPeople(singleRow, currentPerson.getPersonId()));
		}
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param model
	 * @param httpSession
	 * @param paginationFilter
	 * @param searchUUID
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void advancedSearchPlaces(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter, String searchUUID) {
		Page page = null;
		SearchFilter searchFilter = (SearchFilter) httpSession.getAttribute("searchFilter" + searchUUID);

		try {
			page = getSearchService().searchPlaces(searchFilter.getFilterData(), paginationFilter);
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
	 * 
	 * @param model
	 * @param httpSession
	 * @param paginationFilter
	 * @param searchUUID
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void advancedSearchVolumes(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter, String searchUUID) {
		Page page = null;
		SearchFilter searchFilter = (SearchFilter) httpSession.getAttribute("searchFilter" + searchUUID);

		try {
			page = getSearchService().searchVolumes(searchFilter.getFilterData(), paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (Volume currentVolume : (List<Volume>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentVolume.getSerieList().toString());
			singleRow.add(currentVolume.getMDP());
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDate(currentVolume.getStartYear(), currentVolume.getStartMonthNum(), currentVolume.getStartDay()));
			singleRow.add(DateUtils.getStringDate(currentVolume.getEndYear(), currentVolume.getEndMonthNum(), currentVolume.getEndDay()));

			resultList.add(HtmlUtils.showVolume(singleRow, currentVolume.getSummaryId()));
		}
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	private PaginationFilter generatePaginationFilter(String searchType, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

		if (searchType.toLowerCase().trim().equals("documents")) {
			if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
				switch (sortingColumnNumber) {
					case 0:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf_Sort", sortingDirection, SortField.STRING);
						break;
					case 1:
						paginationFilter.addSortingCriteria("recipientPeople.mapNameLf_Sort", sortingDirection, SortField.STRING);
						break;
					case 2:
						paginationFilter.addSortingCriteria("docYear_Sort", sortingDirection, SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("docMonthNum.monthNum", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("docDay_Sort", sortingDirection, SortField.INT);
						break;
					case 3:
						paginationFilter.addSortingCriteria("senderPlace.placeName_Sort", sortingDirection, SortField.STRING);
						break;
					case 4:
						paginationFilter.addSortingCriteria("recipientPlace.placeName_Sort", sortingDirection, SortField.STRING);
						break;
					case 5:
						paginationFilter.addSortingCriteria("volume.volNum_Sort", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("volume.volLetExt_Sort", sortingDirection, SortField.STRING);
						paginationFilter.addSortingCriteria("folioNum_Sort", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("folioMod_Sort", sortingDirection, SortField.STRING);
						break;
					default:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf_Sort", sortingDirection);
						break;
				}
			}
		} else if (searchType.toLowerCase().trim().equals("people")) {
			if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
				switch (sortingColumnNumber) {
					case 0:
						paginationFilter.addSortingCriteria("mapNameLf_Sort", sortingDirection);
						break;
					case 1:
						paginationFilter.addSortingCriteria("gender", sortingDirection);
						break;
					case 2:
						paginationFilter.addSortingCriteria("bornYear_Sort", sortingDirection, SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("bornMonthNum.monthNum", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("bornDay_Sort", sortingDirection, SortField.INT);
						break;
					case 3:
						paginationFilter.addSortingCriteria("deathYear_Sort", sortingDirection, SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("deathMonthNum.monthNum", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("deathDay_Sort", sortingDirection, SortField.INT);
						break;
					case 4:
						paginationFilter.addSortingCriteria("recipientPlace.placeName_Sort", sortingDirection);
						break;
					default:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf", sortingDirection);
						break;
				}		
			}
		} else if (searchType.toLowerCase().trim().equals("places")) {
			if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
				switch (sortingColumnNumber) {
					case 0:
						paginationFilter.addSortingCriteria("placeNameFull_Sort", sortingDirection);
						break;
					case 1:
						paginationFilter.addSortingCriteria("plType_Sort", sortingDirection);
						break;
					case 2:
						paginationFilter.addSortingCriteria("parentPlace.placeName_Sort", sortingDirection);
						break;
					case 3:
						paginationFilter.addSortingCriteria("parentType_Sort", sortingDirection);
						break;
					default:
						paginationFilter.addSortingCriteria("placeNameFull_Sort", sortingDirection);
						break;
				}
			}
		} else if (searchType.toLowerCase().trim().equals("volumes")) {
			if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
				switch (sortingColumnNumber) {
					case 0:
						paginationFilter.addSortingCriteria("serieList.title_Sort", sortingDirection, SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle1_Sort", sortingDirection, SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle2_Sort", sortingDirection, SortField.STRING);
						break;
					case 1:
						paginationFilter.addSortingCriteria("volNum_Sort", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("volLetExt_Sort", sortingDirection, SortField.STRING);
						break;
					case 2:
						paginationFilter.addSortingCriteria("startYear_Sort", sortingDirection, SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("startMonthNum.monthNum", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("startDay_Sort", sortingDirection, SortField.INT);
						break;
					case 3:
						paginationFilter.addSortingCriteria("endYear_Sort", sortingDirection, SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("endMonthNum.monthNum", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("endDay_Sort", sortingDirection, SortField.INT);
						break;
					default:
						paginationFilter.addSortingCriteria("serieList.title_Sort", sortingDirection, SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle1_Sort", sortingDirection, SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle2_Sort", sortingDirection, SortField.STRING);
						break;
				}
			}
		}

		return paginationFilter;
	}

	/**
	 * @return the searchService
	 */
	public SearchService getSearchService() {
		return searchService;
	}

	/**
	 * This method returns a list of person. It's used in autocompleter of advanced search. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/src/SearchPerson", method = RequestMethod.GET)
	public ModelAndView searchPerson(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			PaginationFilter paginationFilter = new PaginationFilter(0, Integer.MAX_VALUE);
			paginationFilter.addSortingCriteria("mapNameLf_Sort", "DESC");

			Page page = getSearchService().searchPeople(new SimpleSearchPeople(query), paginationFilter);
			model.put("query", query);
			model.put("count", page.getTotal());
			model.put("data", ListBeanUtils.transformList(page.getList(), "personId"));
			model.put("suggestions", ListBeanUtils.transformList(page.getList(), "mapNameLf"));
			model.put("activeStarts", ListBeanUtils.transformList(page.getList(), "activeStart"));
			model.put("activeEnds", ListBeanUtils.transformList(page.getList(), "activeEnd"));
			model.put("bornYears", ListBeanUtils.transformList(page.getList(), "bornYear"));
			model.put("deathYears", ListBeanUtils.transformList(page.getList(), "deathYear"));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method returns a list of places. It's used in autocompleter of advanced search. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/src/SearchPlace", method = RequestMethod.GET)
	public ModelAndView searchPlace(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			PaginationFilter paginationFilter = new PaginationFilter(0, Integer.MAX_VALUE);
			paginationFilter.addSortingCriteria("placeNameFull_Sort", "DESC");

			Page page = getSearchService().searchPlaces(new SimpleSearchPlace(query), paginationFilter);
			model.put("query", query);
			model.put("count", page.getTotal());
			model.put("data", ListBeanUtils.transformList(page.getList(), "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(page.getList(), "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(page.getList(), "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(page.getList(), "plType"));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method returns a list of topics linkable to document. Result does not
	 * contains topics already linked to document. 
	 *  
	 * @param entryId Unique document identifier
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable topics.
	 */
	@RequestMapping(value = "/src/SearchTopic", method = RequestMethod.GET)
	public ModelAndView searchTopic(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Page page = getSearchService().searchTopics(new SimpleSearchTopic(query), new PaginationFilter(0, Integer.MAX_VALUE));
			model.put("query", query);
			model.put("count", page.getTotal());
			model.put("data", ListBeanUtils.transformList(page.getList(), "topicId"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(page.getList(), "topicTitle", " ", " ", Boolean.TRUE));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * @param searchService the searchService to set
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	/**
	 * 
	 * @param model
	 * @param searchText
	 * @param firstRecord
	 * @param length
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void simpleSearchDocuments(Map<String, Object> model, String searchText, PaginationFilter paginationFilter) {
		Page page = null;

		try {
			page = getSearchService().searchDocuments(new SimpleSearchDocument(searchText), paginationFilter);
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

			if (currentDocument.getDocYear() != null) 
				singleRow.add(DateUtils.getStringDate(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			
			if (currentDocument.getSenderPlace() != null)
				singleRow.add(currentDocument.getSenderPlace().getPlaceName());
			else
				singleRow.add("");
			
			if (currentDocument.getRecipientPlace() != null)
				singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
			else
				singleRow.add("");
			
			if (currentDocument.getMDPAndFolio() != null)
				singleRow.add("<b>"+currentDocument.getMDPAndFolio()+ "</b>");
			else
				singleRow.add("");

			resultList.add(HtmlUtils.showDocument(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param searchType
	 * @param alias
	 * @param sortingColumn
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/src/SimpleSearchPagination.json", method = RequestMethod.GET)
	public ModelAndView simpleSearchPagination(@RequestParam(value="searchType") String searchType, 
								   		 @RequestParam(value="sSearch") String alias,
								   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType.toLowerCase().trim().equals("documents")) {
			simpleSearchDocuments(model, alias, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("people")) {
			simpleSearchPeople(model, alias, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("places")) {
			simpleSearchPlaces(model, alias, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("volumes")) {
			simpleSearchVolumes(model, alias, paginationFilter);
		}

		return new ModelAndView("responseOK", model);
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
	private void simpleSearchPeople(Map<String, Object> model, String searchText, PaginationFilter paginationFilter) {
		Page page = null;

		try {
			page = getSearchService().searchPeople(new SimpleSearchPeople(searchText), paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (People currentPerson : (List<People>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentPerson.getMapNameLf());
			singleRow.add((currentPerson.getGender() != null) ? currentPerson.getGender().toString() : "");
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDate(currentPerson.getBornYear(), currentPerson.getBornMonth(), currentPerson.getBornDay()));
			singleRow.add(DateUtils.getStringDate(currentPerson.getDeathYear(), currentPerson.getDeathMonth(), currentPerson.getDeathDay()));
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
	private void simpleSearchPlaces(Map<String, Object> model, String searchText, PaginationFilter paginationFilter) {
		Page page = null;

		try {
			page = getSearchService().searchPlaces(new SimpleSearchPlace(searchText), paginationFilter);
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
	private void simpleSearchVolumes(Map<String, Object> model, String searchText, PaginationFilter paginationFilter) {
		Page page = null;

		try {
			page = getSearchService().searchVolumes(new SimpleSearchVolume(searchText), paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (Volume currentVolume : (List<Volume>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentVolume.getSerieList().toString());
			singleRow.add(currentVolume.getMDP());
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDate(currentVolume.getStartYear(), currentVolume.getStartMonthNum(), currentVolume.getStartDay()));
			singleRow.add(DateUtils.getStringDate(currentVolume.getEndYear(), currentVolume.getEndMonthNum(), currentVolume.getEndDay()));

			resultList.add(HtmlUtils.showVolume(singleRow, currentVolume.getSummaryId()));
		}
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	/**
	 * 
	 * @param model
	 * @param httpSession
	 * @param paginationFilter
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void userSearchFiltersAll(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter) {
		Page page = null;

		try {
			page = getSearchService().getUserSearchFilters(paginationFilter);
		} catch (ApplicationThrowable aex) {
		}

		List resultList = new ArrayList();
		for (SearchFilter currentFilter : (List<SearchFilter>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentFilter.getFilterName());
			singleRow.add(currentFilter.getTotalResult());
			singleRow.add(currentFilter.getSearchType());
			singleRow.add(DateFormatUtils.format(currentFilter.getDateUpdated(), "MM/dd/yyyy"));

			//resultList.add(HtmlUtils.showAdvancedSearchResult(singleRow, currentFilter.getId()));
			resultList.add(singleRow);
		}
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
	}

	private void userSearchFiltersDocuments(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param searchType
	 * @param alias
	 * @param sortingColumn
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@RequestMapping(value = "/src/UserSearchFiltersPagination.json", method = RequestMethod.GET)
	public ModelAndView userSearchFiltersPagination(HttpSession httpSession,
											@RequestParam(value="searchType") String searchType,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType.toLowerCase().trim().equals("all")){
			userSearchFiltersAll(model, httpSession, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("documents")) {
			userSearchFiltersDocuments(model, httpSession, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("people")) {
			userSearchFiltersPeople(model, httpSession, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("places")) {
			userSearchFiltersPlaces(model, httpSession, paginationFilter);
		} else if (searchType.toLowerCase().trim().equals("volumes")) {
			userSearchFiltersVolumes(model, httpSession, paginationFilter);
		}

		return new ModelAndView("responseOK", model);
	}

	private void userSearchFiltersPeople(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter) {
		// TODO Auto-generated method stub
		
	}

	private void userSearchFiltersPlaces(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter) {
		// TODO Auto-generated method stub
		
	}

	private void userSearchFiltersVolumes(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter) {
		// TODO Auto-generated method stub
		
	}
}