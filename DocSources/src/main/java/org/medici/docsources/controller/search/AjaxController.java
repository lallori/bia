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
import org.medici.docsources.common.search.SimpleSearchTitleOrOccupation;
import org.medici.docsources.common.search.SimpleSearchTopic;
import org.medici.docsources.common.search.SimpleSearchVolume;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.RoleCat;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.search.SearchService;
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
	private SearchService searchService;
	
	@Autowired
	private VolBaseService volBaseService;
	
	@Autowired
	private DocBaseService docBaseService;
	
	@Autowired
	private PeopleBaseService peopleBaseService;

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
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
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
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}

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
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>(), folioNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>(), folioMods = new ArrayList<String>();

		try {
			page = getSearchService().searchDocuments(searchFilter.getFilterData(), paginationFilter);
			
			for(Document currentDocument : (List<Document>)page.getList()){
				volNums.add(currentDocument.getVolume().getVolNum());
				volLetExts.add(currentDocument.getVolume().getVolLetExt());
				folioNums.add(currentDocument.getFolioNum());
				folioMods.add(currentDocument.getFolioMod());
			}
			
			stateDocumentsDigitized = getDocBaseService().getDocumentsDigitizedState(volNums, volLetExts, folioNums, folioMods);
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
			
			if (currentDocument.getMDPAndFolio() != null){
				if(stateDocumentsDigitized.get(currentDocument.getMDPAndFolio())){
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>&nbsp<img src=\"/DocSources/images/1024/img_digitized_small_document.png\">");
				}else{
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");
				}
				
			}
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
											@RequestParam(value="searchType") SearchType searchType,
											@RequestParam(value="searchUUID") String searchUUID,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType.equals(SearchType.DOCUMENT)) {
			advancedSearchDocuments(model, httpSession, paginationFilter, searchUUID);
		} else if (searchType.equals(SearchType.PEOPLE)) {
			advancedSearchPeople(model, httpSession, paginationFilter, searchUUID);
		} else if (searchType.equals(SearchType.PLACE)) {
			advancedSearchPlaces(model, httpSession, paginationFilter, searchUUID);
		} else if (searchType.equals(SearchType.VOLUME)) {
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
		HashMap<String, SearchFilter> searchFilterMap = (HashMap<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);
		
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
		HashMap<String, SearchFilter> searchFilterMap = (HashMap<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);

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
		HashMap<String, SearchFilter> searchFilterMap = (HashMap<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);
		
		Map<String, Boolean> stateVolumesDigitized = new HashMap<String, Boolean>();

		try {
			page = getSearchService().searchVolumes(searchFilter.getFilterData(), paginationFilter);
			
			stateVolumesDigitized = getVolBaseService().getVolumesDigitizedState((List<Integer>)ListBeanUtils.transformList(page.getList(), "volNum"), (List<String>)ListBeanUtils.transformList(page.getList(), "volLetExt"));
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
			if(stateVolumesDigitized.get(currentVolume.getMDP()))
				singleRow.add("YES");
			else
				singleRow.add("NO");
			
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
	private PaginationFilter generatePaginationFilter(SearchType searchType, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

		if (searchType.equals(SearchType.DOCUMENT)) {
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
		} else if (searchType.equals(SearchType.PEOPLE)) {
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
		} else if (searchType.equals(SearchType.PLACE)) {
			if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
				switch (sortingColumnNumber) {
					case 0:
						paginationFilter.addSortingCriteria("placeNameFull", sortingDirection);
						break;
					case 1:
						paginationFilter.addSortingCriteria("plType", sortingDirection);
						break;
					case 2:
						paginationFilter.addSortingCriteria("parentPlace.placeName", sortingDirection);
						break;
					case 3:
						paginationFilter.addSortingCriteria("parentType", sortingDirection);
						break;
					default:
						paginationFilter.addSortingCriteria("placeNameFull", sortingDirection);
						break;
				}
			}
		} else if (searchType.equals(SearchType.VOLUME)) {
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
					case 4:
						paginationFilter.addSortingCriteria("digitized_Sort", sortingDirection, SortField.STRING);
						paginationFilter.addSortingCriteria("volNum_Sort", sortingDirection, SortField.INT);
						paginationFilter.addSortingCriteria("volLetExt_Sort", sortingDirection, SortField.STRING);
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
	 * This method returns a list of ipotetical recipients. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing recipients.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/src/SearchTitleOrOccupation", method = RequestMethod.GET)
	public ModelAndView searchTitleOrOccupation(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			PaginationFilter paginationFilter = new PaginationFilter(0, Integer.MAX_VALUE);
			paginationFilter.addSortingCriteria("titleOcc", "DESC");
			
			Page page  = getSearchService().searchTitleOrOccupation(new SimpleSearchTitleOrOccupation(query), paginationFilter);
			model.put("query", query);
			model.put("count", page.getTotal());
			model.put("data", ListBeanUtils.transformList(page.getList(), "titleOccId"));
			model.put("suggestions", ListBeanUtils.transformList(page.getList(), "titleOcc"));
			// transformList does not support nested property, so we need to extract list of RoleCat, then we can extract every single field.
			List<RoleCat> roleCatList = (List<RoleCat>) ListBeanUtils.transformList(page.getList(), "roleCat");
			model.put("rolesCatMajor", ListBeanUtils.transformList(roleCatList, "roleCatMajor"));
			model.put("rolesCatMinor", ListBeanUtils.transformList(roleCatList, "roleCatMinor"));
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
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>(), folioNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>(), folioMods = new ArrayList<String>();
		try {
			page = getSearchService().searchDocuments(new SimpleSearchDocument(searchText), paginationFilter);
			for(Document currentDocument : (List<Document>)page.getList()){
				volNums.add(currentDocument.getVolume().getVolNum());
				volLetExts.add(currentDocument.getVolume().getVolLetExt());
				folioNums.add(currentDocument.getFolioNum());
				folioMods.add(currentDocument.getFolioMod());
			}
			
			stateDocumentsDigitized = getDocBaseService().getDocumentsDigitizedState(volNums, volLetExts, folioNums, folioMods);
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
			
			if (currentDocument.getMDPAndFolio() != null){
				if(stateDocumentsDigitized.get(currentDocument.getMDPAndFolio())){
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>&nbsp<img src=\"/DocSources/images/1024/img_digitized_small_document.png\">");
				}else{
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");
				}
				
			}
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
	public ModelAndView simpleSearchPagination(@RequestParam(value="searchType") SearchType searchType, 
								   		 @RequestParam(value="sSearch") String alias,
								   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType.equals(SearchType.DOCUMENT)) {
			simpleSearchDocuments(model, alias, paginationFilter);
		} else if (searchType.equals(SearchType.PEOPLE)) {
			simpleSearchPeople(model, alias, paginationFilter);
		} else if (searchType.equals(SearchType.PLACE)) {
			simpleSearchPlaces(model, alias, paginationFilter);
		} else if (searchType.equals(SearchType.VOLUME)) {
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
		List<Integer> personIds = new ArrayList<Integer>();
		Map<Integer,Integer> documentsRelated = new HashMap<Integer, Integer>();
		try {
			page = getSearchService().searchPeople(new SimpleSearchPeople(searchText), paginationFilter);
			for(People currentPerson : (List<People>)page.getList()){
				personIds.add(currentPerson.getPersonId());
			}
			documentsRelated = getPeopleBaseService().findNumbersOfDocumentsRelated(personIds);
			
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
			//singleRow.add(documentsRelated.get(currentPerson.getPersonId()).toString());
			
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
		Map<String, Boolean> stateVolumesDigitized = new HashMap<String, Boolean>();

		try {
			page = getSearchService().searchVolumes(new SimpleSearchVolume(searchText), paginationFilter);

			stateVolumesDigitized = getVolBaseService().getVolumesDigitizedState((List<Integer>)ListBeanUtils.transformList(page.getList(), "volNum"), (List<String>)ListBeanUtils.transformList(page.getList(), "volLetExt"));
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
			if(stateVolumesDigitized.get(currentVolume.getMDP())){
				singleRow.add("YES");
			}else{
				singleRow.add("NO");
			}
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
											@RequestParam(value="searchType", required=false) SearchType searchType,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		PaginationFilter paginationFilter = generatePaginationFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType == null){
			userSearchFiltersAll(model, httpSession, paginationFilter);
		} else if (searchType.equals(SearchType.DOCUMENT)) {
			userSearchFiltersDocuments(model, httpSession, paginationFilter);
		} else if (searchType.equals(SearchType.PEOPLE)) {
			userSearchFiltersPeople(model, httpSession, paginationFilter);
		} else if (searchType.equals(SearchType.PLACE)) {
			userSearchFiltersPlaces(model, httpSession, paginationFilter);
		} else if (searchType.equals(SearchType.VOLUME)) {
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