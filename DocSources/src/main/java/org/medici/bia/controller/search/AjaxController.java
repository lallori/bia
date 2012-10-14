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
package org.medici.bia.controller.search;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.time.DateFormatUtils;
import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.AdvancedSearch;
import org.medici.bia.common.search.AdvancedSearchFactory;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.common.search.SimpleSearchDocument;
import org.medici.bia.common.search.SimpleSearchPeople;
import org.medici.bia.common.search.SimpleSearchPlace;
import org.medici.bia.common.search.SimpleSearchTitleOrOccupation;
import org.medici.bia.common.search.SimpleSearchVolume;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ListBeanUtils;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.RoleCat;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TopicList;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.geobase.GeoBaseService;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.medici.bia.service.search.SearchService;
import org.medici.bia.service.volbase.VolBaseService;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
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
	private SearchService searchService;
	
	@Autowired
	private VolBaseService volBaseService;

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
	@RequestMapping(value = "/src/AdvancedSearchCount.json", method = RequestMethod.POST)
	public ModelAndView advancedSearchCount(HttpSession httpSession, AdvancedSearchCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Long totalResult = new Long(0);

		AdvancedSearch advancedSearch = AdvancedSearchFactory.createFromAdvancedSearchCommand(command);

		try {
			totalResult = getSearchService().searchCount((Search) advancedSearch);
			
			model.put("totalResult", totalResult);
			model.put("response", "OK");
			return new ModelAndView("responseOK", model);
		} catch (ApplicationThrowable aex) {
			model.put("error", totalResult);
			model.put("response", "KO");
			return new ModelAndView("responseOK", model);
		}

		
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
		Map<String, SearchFilter> searchFilterMap = (Map<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>(), folioNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>(), folioMods = new ArrayList<String>();

		try {
			page = getSearchService().searchAdvancedDocuments(searchFilter.getFilterData(), paginationFilter);
			
			for(Document currentDocument : (List<Document>)page.getList()){
				volNums.add(currentDocument.getVolume().getVolNum());
				volLetExts.add(currentDocument.getVolume().getVolLetExt());
				folioNums.add(currentDocument.getFolioNum());
				folioMods.add(currentDocument.getFolioMod());
			}
			
			stateDocumentsDigitized = getDocBaseService().getDocumentsDigitizedState(volNums, volLetExts, folioNums, folioMods);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList(0);
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList(0);
			if (currentDocument.getSenderPeople() != null){
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPeople() != null){
				if(!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if(currentDocument.getYearModern() != null){
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else{
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null){
				if(!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPlace() != null){
				if(!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}

			if (currentDocument.getMDPAndFolio() != null){
				if(stateDocumentsDigitized.get(currentDocument.getMDPAndFolio())){
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>&nbsp<img src=\"/DocSources/images/1024/img_digitized_small_document.png\">");
				} else {
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");
				}
				
			} else {
				singleRow.add("");
			}

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
		Map<String, Object> model = new HashMap<String, Object>(0);

		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection, searchType);

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
		Map<String, SearchFilter> searchFilterMap = (Map<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);
		Map<Integer, Long> documentsRelated = new HashMap<Integer, Long>();
		List<Integer> personIds = new ArrayList<Integer>();
		
		try {
			page = getSearchService().searchAdvancedPeople(searchFilter.getFilterData(), paginationFilter);
			
			for(People currentPerson : (List<People>)page.getList()){
				personIds.add(currentPerson.getPersonId());
			}
			documentsRelated = getPeopleBaseService().findNumbersOfDocumentsRelated(personIds);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList(0);
		for (People currentPerson : (List<People>)page.getList()) {
			List singleRow = new ArrayList(0);
			singleRow.add(currentPerson.getMapNameLf());
			singleRow.add((currentPerson.getGender() != null) ? currentPerson.getGender().toString() : "");
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentPerson.getBornYear(), currentPerson.getBornMonth(), currentPerson.getBornDay()));
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentPerson.getDeathYear(), currentPerson.getDeathMonth(), currentPerson.getDeathDay()));
			if(documentsRelated.containsKey(currentPerson.getPersonId())) {
				singleRow.add(documentsRelated.get(currentPerson.getPersonId()).toString());
			} else {
				singleRow.add("0");
			}
			
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
		Map<String, SearchFilter> searchFilterMap = (HashMap<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);
		Map<Integer, Long> peopleRelated = new HashMap<Integer, Long>();
		Map<Integer, Long> fromToRelated = new HashMap<Integer, Long>();
		Map<Integer, Long> documentsRelated = new HashMap<Integer, Long>();
		List<Integer> placeAllIds = new ArrayList<Integer>();

		try {
			page = getSearchService().searchAdvancedPlaces(searchFilter.getFilterData(), paginationFilter);
			
			for(Place currentPlace : (List<Place>)page.getList()){
				placeAllIds.add(currentPlace.getPlaceAllId());
			}
			
			peopleRelated = getGeoBaseService().findNumbersOfPeopleRelated(placeAllIds);
			fromToRelated = getGeoBaseService().findNumbersOfFromToDocumentsRelated(placeAllIds);
			documentsRelated = getGeoBaseService().findNumbersOfDocumentsRelated(placeAllIds);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList(0);
		for (Place currentPlace : (List<Place>)page.getList()) {
			List singleRow = new ArrayList(0);
			singleRow.add(currentPlace.getPlaceName() + " / " + currentPlace.getPlParent());
			singleRow.add(currentPlace.getPlType());
			if(peopleRelated.containsKey(currentPlace.getPlaceAllId())) {
				singleRow.add(peopleRelated.get(currentPlace.getPlaceAllId()).toString());
			} else {
				singleRow.add("0");
			}
			
			if(fromToRelated.containsKey(currentPlace.getPlaceAllId())) {
				singleRow.add(fromToRelated.get(currentPlace.getPlaceAllId()).toString());
			} else {
				singleRow.add("0");
			}

			if(documentsRelated.containsKey(currentPlace.getPlaceAllId())) {
				singleRow.add(documentsRelated.get(currentPlace.getPlaceAllId()).toString());
			} else {
				singleRow.add("0");
			}

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
		Map<String, SearchFilter> searchFilterMap = (Map<String, SearchFilter>) httpSession.getAttribute("searchFilterMap");
		SearchFilter searchFilter = searchFilterMap.get(searchUUID);
		
		Map<String, Boolean> stateVolumesDigitized = new HashMap<String, Boolean>();

		try {
			page = getSearchService().searchAdvancedVolumes(searchFilter.getFilterData(), paginationFilter);
			
			if(page.getTotal() > 0){
				stateVolumesDigitized = getVolBaseService().getVolumesDigitizedState((List<Integer>)ListBeanUtils.transformList(page.getList(), "volNum"), (List<String>)ListBeanUtils.transformList(page.getList(), "volLetExt"));
			}
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList(0);
		for (Volume currentVolume : (List<Volume>)page.getList()) {
			List singleRow = new ArrayList(0);
			if(currentVolume.getSerieList() != null){
				singleRow.add(currentVolume.getSerieList().toString());
			}else{
				singleRow.add("");
			}
			singleRow.add(currentVolume.getMDP());
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentVolume.getStartYear(), currentVolume.getStartMonthNum(), currentVolume.getStartDay()));
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentVolume.getEndYear(), currentVolume.getEndMonthNum(), currentVolume.getEndDay()));
			if(stateVolumesDigitized.get(currentVolume.getMDP())) {
				singleRow.add("YES");
			} else {
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
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	private PaginationFilter generatePaginationUserSearchFilter(String searchType, Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

		switch (sortingColumnNumber) {
			case 0:
				paginationFilter.addSortingCriteria("filterName", sortingDirection);
				break;
			case 1:
				paginationFilter.addSortingCriteria("totalResult", sortingDirection);
				break;
			case 2:
				paginationFilter.addSortingCriteria("searchType", sortingDirection);
				break;
			case 3:
				paginationFilter.addSortingCriteria("dateUpdated", sortingDirection);
				break;
			default:
				break;
		}

		return paginationFilter;
	}

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

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
	 * @return the searchService
	 */
	public SearchService getSearchService() {
		return searchService;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}
	
	/**
	 * This method returns a list of volume numbers. 
	 *  
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable topics.
	 */
	@RequestMapping(value = "/src/SearchOtherLang", method = RequestMethod.GET)
	public ModelAndView searchOtherLang(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<String> otherLang = getSearchService().searchOtherLang(query);
			//MD: This code is to separate the other Languages
			List<String> separatedLang = new ArrayList<String>(0);
			if(otherLang != null){
				for(String currentString : otherLang){
					String [] comma = currentString.split(",\\s*");
					for(int i = 0; i < comma.length; i++){
						if(!separatedLang.contains(comma[i])){
							separatedLang.add(comma[i]);
						}
					}
				}
				model.put("count", separatedLang.size());
				model.put("data", separatedLang);
			}else{
				model.put("count", 0);
				model.put("data", null);				
			}
			model.put("query", query);		
			model.put("suggestions", separatedLang);
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method returns a list of person. It's used in autocompleter of advanced search. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/src/SearchPerson", method = RequestMethod.GET)
	public ModelAndView searchPerson(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
//			PaginationFilter paginationFilter = new PaginationFilter(0, Integer.MAX_VALUE);
//			paginationFilter.addSortingCriteria("mapNameLf_Sort", "DESC");
			
			try {
				query = new String(query.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				
			}

			List<People> people = getSearchService().searchPeople(query);
			model.put("query", query);
			model.put("count", people.size());
			model.put("data", ListBeanUtils.transformList(people, "personId"));
			model.put("suggestions", ListBeanUtils.transformList(people, "mapNameLf"));
			model.put("activeStarts", ListBeanUtils.transformList(people, "activeStart"));
			model.put("activeEnds", ListBeanUtils.transformList(people, "activeEnd"));
			model.put("bornYears", ListBeanUtils.transformList(people, "bornYear"));
			model.put("deathYears", ListBeanUtils.transformList(people, "deathYear"));

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
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
//			PaginationFilter paginationFilter = new PaginationFilter(0, Integer.MAX_VALUE);
//			paginationFilter.addSortingCriteria("placeNameFull_Sort", "DESC");

			try {
				query = new String(query.getBytes(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				
			}
			
			List<Place> places = getSearchService().searchPlaces(query);
			model.put("query", query);
			model.put("count", places.size());
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(places, "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(places, "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(places, "plType"));

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
		Map<String, Object> model = new HashMap<String, Object>(0);

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
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<TopicList> topics = getSearchService().searchTopics(query, new PaginationFilter(0, Integer.MAX_VALUE));
			model.put("query", query);
			model.put("count", topics.size());
			model.put("data", ListBeanUtils.transformList(topics, "topicId"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(topics, "topicTitle", " ", " ", Boolean.TRUE));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns a list of volume numbers. 
	 *  
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable topics.
	 */
	@RequestMapping(value = "/src/SearchVolume", method = RequestMethod.GET)
	public ModelAndView searchVolume(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<Volume> volumes = getSearchService().searchVolumes(query, new PaginationFilter(0, Integer.MAX_VALUE));
			model.put("query", query);
			model.put("count", volumes.size());
			model.put("data", ListBeanUtils.transformList(volumes, "volNum"));
			List<String> suggestions = new ArrayList<String>();
			for(Volume currentVolume : volumes){
				if(currentVolume.getVolLetExt() != null){
					suggestions.add(currentVolume.getVolNum().toString() + currentVolume.getVolLetExt());
				}else{
					suggestions.add(currentVolume.getVolNum().toString());
				}
			}
			model.put("suggestions", suggestions);

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}


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
	 * @param searchService the searchService to set
	 */
	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
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
	private void simpleSearchDocuments(Map<String, Object> model, SimpleSearchPerimeter simpleSearchPerimeter, String searchText, PaginationFilter paginationFilter) {
		Page page = null;
		
		try {
			searchText = new String(searchText.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			page = getSearchService().searchDocuments(new SimpleSearchDocument(simpleSearchPerimeter, searchText), paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList();
			if (currentDocument.getSenderPeople() != null){
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			}
			else
				singleRow.add("");
			
			if (currentDocument.getRecipientPeople() != null){
				if(!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			}
			else
				singleRow.add("");
			
			if(currentDocument.getYearModern() != null){
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}else{
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null){
				if(!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			}
			else
				singleRow.add("");
			
			if (currentDocument.getRecipientPlace() != null){
				if(!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			}
			else
				singleRow.add("");
			
			if (currentDocument.getMDPAndFolio() != null){
				if(currentDocument.getVolume().getDigitized()){
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
	public ModelAndView simpleSearchPagination(@RequestParam(value="simpleSearchPerimeter") SimpleSearchPerimeter simpleSearchPerimeter, 
								   		 @RequestParam(value="sSearch") String alias,
								   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection, simpleSearchPerimeter);

		if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT)) {
			simpleSearchDocuments(model, simpleSearchPerimeter, alias, paginationFilter);
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)) {
			simpleSearchDocuments(model, simpleSearchPerimeter, alias, paginationFilter);
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.PEOPLE)) {
			simpleSearchPeople(model, alias, paginationFilter);
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.PLACE)) {
			simpleSearchPlaces(model, alias, paginationFilter);
		} else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.VOLUME)) {
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
		Map<Integer, Long> documentsRelated = new HashMap<Integer, Long>();
		try {
			searchText = new String(searchText.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		
		try {
			page = getSearchService().searchPeople(new SimpleSearchPeople(searchText), paginationFilter);
			for(People currentPerson : (List<People>)page.getList()){
				personIds.add(currentPerson.getPersonId());
			}
			documentsRelated = getPeopleBaseService().findNumbersOfDocumentsRelated(personIds);
			
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (People currentPerson : (List<People>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentPerson.getMapNameLf());
			singleRow.add((currentPerson.getGender() != null) ? currentPerson.getGender().toString() : "");
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentPerson.getBornYear(), currentPerson.getBornMonth(), currentPerson.getBornDay()));
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentPerson.getDeathYear(), currentPerson.getDeathMonth(), currentPerson.getDeathDay()));
			if(documentsRelated.containsKey(currentPerson.getPersonId()))
				singleRow.add(documentsRelated.get(currentPerson.getPersonId()).toString());
			else
				singleRow.add("0");
			
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
		
		Map<Integer, Long> peopleRelated = new HashMap<Integer, Long>();
		Map<Integer, Long> fromToRelated = new HashMap<Integer, Long>();
		Map<Integer, Long> documentsRelated = new HashMap<Integer, Long>();
		List<Integer> placeAllIds = new ArrayList<Integer>();
		
		try {
			searchText = new String(searchText.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			page = getSearchService().searchPlaces(new SimpleSearchPlace(searchText), paginationFilter);
			
			for(Place currentPlace : (List<Place>)page.getList()){
				placeAllIds.add(currentPlace.getPlaceAllId());
			}
			
			peopleRelated = getGeoBaseService().findNumbersOfPeopleRelated(placeAllIds);
			fromToRelated = getGeoBaseService().findNumbersOfFromToDocumentsRelated(placeAllIds);
			documentsRelated = getGeoBaseService().findNumbersOfDocumentsRelated(placeAllIds);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (Place currentPlace : (List<Place>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentPlace.getPlaceName() + " / " + currentPlace.getPlParent());
			singleRow.add(currentPlace.getPlType());
			if(peopleRelated.containsKey(currentPlace.getPlaceAllId()))
				singleRow.add(peopleRelated.get(currentPlace.getPlaceAllId()).toString());
			else
				singleRow.add("0");
			if(fromToRelated.containsKey(currentPlace.getPlaceAllId()))
				singleRow.add(fromToRelated.get(currentPlace.getPlaceAllId()).toString());
			else
				singleRow.add("0");
			if(documentsRelated.containsKey(currentPlace.getPlaceAllId()))
				singleRow.add(documentsRelated.get(currentPlace.getPlaceAllId()).toString());
			else
				singleRow.add("0");

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
		// Lorenzo Pasquinelli : Now digitized information on volume is a property of volume entity ... We can comment next code
		//Map<String, Boolean> stateVolumesDigitized = new HashMap<String, Boolean>();
		
		try {
			searchText = new String(searchText.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			//MD: We search volumes only in volume number 
			page = getSearchService().searchVolumes(new SimpleSearchVolume(searchText), paginationFilter);

			// Lorenzo Pasquinelli : Now digitized information on volume is a property of volume entity ... We can comment next code
			// stateVolumesDigitized = getVolBaseService().getVolumesDigitizedState((List<Integer>)ListBeanUtils.transformList(page.getList(), "volNum"), (List<String>)ListBeanUtils.transformList(page.getList(), "volLetExt"));
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (Volume currentVolume : (List<Volume>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add((currentVolume.getSerieList() == null) ? "" : currentVolume.getSerieList().toString());
			singleRow.add(currentVolume.getMDP());
			//Dates column must be filled with a string concatenation
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentVolume.getStartYear(), currentVolume.getStartMonthNum(), currentVolume.getStartDay()));
			singleRow.add(DateUtils.getStringDateHTMLForTable(currentVolume.getEndYear(), currentVolume.getEndMonthNum(), currentVolume.getEndDay()));
			if(currentVolume.getDigitized().equals(Boolean.TRUE)) {
				singleRow.add("YES");
			 }else {
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
	@SuppressWarnings({"rawtypes", "unchecked" })
	private void userSearchFilters(Map<String, Object> model, HttpSession httpSession, PaginationFilter paginationFilter, SearchType searchType) {
		Page page = null;

		try {
			if (searchType == null) {
				page = getSearchService().getUserSearchFilters(paginationFilter);
			} else {
				page = getSearchService().getUserSearchFilters(paginationFilter, searchType);
			}
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (SearchFilter currentFilter : (List<SearchFilter>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentFilter.getFilterName());
			singleRow.add(currentFilter.getTotalResult());
			singleRow.add(currentFilter.getSearchType());
			singleRow.add(DateFormatUtils.format(currentFilter.getDateUpdated(), "MM/dd/yyyy"));

			resultList.add(HtmlUtils.showUserSearchFilter(singleRow, currentFilter.getId(), currentFilter.getSearchType()));
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
	@RequestMapping(value = "/src/UserSearchFiltersPagination.json", method = RequestMethod.GET)
	public ModelAndView userSearchFiltersPagination(HttpSession httpSession,
											@RequestParam(value="searchType", required=false) String searchType,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = generatePaginationUserSearchFilter(searchType, sortingColumnNumber, sortingDirection, firstRecord, length);

		if (searchType == null){
			userSearchFilters(model, httpSession, paginationFilter, null);
		} else if (searchType.equals("all")) {
			userSearchFilters(model, httpSession, paginationFilter, null);
		} else if (searchType.equals("document")) {
			userSearchFilters(model, httpSession, paginationFilter, SearchType.DOCUMENT);
		} else if (searchType.equals("people")) {
			userSearchFilters(model, httpSession, paginationFilter, SearchType.PEOPLE);
		} else if (searchType.equals("place")) {
			userSearchFilters(model, httpSession, paginationFilter, SearchType.PLACE);
		} else if (searchType.equals("volume")) {
			userSearchFilters(model, httpSession, paginationFilter, SearchType.VOLUME);
		}

		return new ModelAndView("responseOK", model);
	}
}