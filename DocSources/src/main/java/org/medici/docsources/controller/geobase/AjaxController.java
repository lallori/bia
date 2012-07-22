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
package org.medici.docsources.controller.geobase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for GeoBase.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("GeoBaseAjaxController")
public class AjaxController {
	@Autowired
	private GeoBaseService geoBaseService;


	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/CheckPlaceIsDeletable", method = RequestMethod.GET)
	public ModelAndView checkPlaceIsDeletable(@RequestParam(value="placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);

			if (place != null) {
				model.put("placeName", place.getPlaceName());
				model.put("placeNameFull", place.getPlaceNameFull());

				Integer numberOfTopicsPlace = getGeoBaseService().findNumberOfTopicsPlace(placeAllId);
				model.put("topicsPlace", numberOfTopicsPlace);
				Integer numberOfDocumentsInTopicsPlace = getGeoBaseService().findNumberOfDocumentsInTopicsPlace(placeAllId);
				model.put("documentsInTopicsPlace", numberOfDocumentsInTopicsPlace);
				Integer numberOfSenderDocumentsPlace = getGeoBaseService().findNumberOfSenderDocumentsPlace(placeAllId);
				model.put("senderDocumentsPlace", numberOfSenderDocumentsPlace);
				Integer numberOfRecipientDocumentsPlace = getGeoBaseService().findNumberOfRecipientDocumentsPlace(placeAllId);
				model.put("recipientDocumentsPlace", numberOfRecipientDocumentsPlace);
				Integer numberOfBirthInPlace = getGeoBaseService().findNumberOfBirthInPlace(placeAllId);
				model.put("birthPlace", numberOfBirthInPlace);
				Integer numberOfActiveStartInPlace = getGeoBaseService().findNumberOfActiveStartInPlace(placeAllId);
				model.put("activeStartPlace", numberOfActiveStartInPlace);
				Integer numberOfDeathInPlace = getGeoBaseService().findNumberOfDeathInPlace(placeAllId);
				model.put("deathPlace", numberOfDeathInPlace);
				Integer numberOfActiveEndInPlace = getGeoBaseService().findNumberOfActiveEndInPlace(placeAllId);
				model.put("activeEndPlace", numberOfActiveEndInPlace);
				
				model.put("documentsInTopicsPlaceURL", HtmlUtils.showDDocumentsInTopicsPlace(placeAllId, numberOfDocumentsInTopicsPlace, numberOfTopicsPlace, "Recipient"));
				model.put("senderDocumentsPlaceURL", HtmlUtils.showSenderDocumentsPlace(placeAllId, numberOfSenderDocumentsPlace, "Sender"));
				model.put("recipientDocumentsPlaceURL", HtmlUtils.showRecipientDocumentsPlace(placeAllId, numberOfRecipientDocumentsPlace, "Recipient"));
				model.put("birthPlaceURL", HtmlUtils.showBirthPeoplePlace(placeAllId, numberOfBirthInPlace, "Birth"));
				model.put("activeStartPlaceURL", HtmlUtils.showActiveStartPeoplePlace(placeAllId, numberOfActiveStartInPlace, "Active Starts"));
				model.put("deathPlaceURL", HtmlUtils.showDeathPeoplePlace(placeAllId, numberOfDeathInPlace, "Death"));
				model.put("activeEndPlaceURL", HtmlUtils.showActiveEndPeoplePlace(placeAllId, numberOfActiveEndInPlace, "Active Ends"));

				if ((numberOfTopicsPlace>0) || (numberOfDocumentsInTopicsPlace>0) || (numberOfSenderDocumentsPlace>0) ||
					(numberOfRecipientDocumentsPlace>0) || (numberOfBirthInPlace>0) || (numberOfActiveStartInPlace>0) ||
					(numberOfDeathInPlace>0) || (numberOfActiveEndInPlace>0)){
					model.put("isDeletable", Boolean.FALSE.toString());
				} else {
					model.put("isDeletable", Boolean.TRUE.toString());
				}
			} else {
				model.put("isDeletable", Boolean.FALSE.toString());
			}
			model.put("placeAllId", placeAllId.toString());
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
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
	@SuppressWarnings("unused")
	private PaginationFilter generatePaginationFilter(Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
				case 0:
					paginationFilter.addSortingCriteria("document.senderPeople.mapNameLf", sortingDirection);
					break;
				case 1:
					paginationFilter.addSortingCriteria("document.recipientPeople.mapNameLf", sortingDirection);
					break;
				case 2:
					paginationFilter.addSortingCriteria("document.sortableDateInt", sortingDirection);
//					paginationFilter.addSortingCriteria("document.docYear", sortingDirection);
//					//Month is an entity, so we don't have field with suffix 
//					paginationFilter.addSortingCriteria("document.docMonthNum.monthNum", sortingDirection);
//					paginationFilter.addSortingCriteria("document.docDay", sortingDirection);
					break;
				case 3:
					paginationFilter.addSortingCriteria("document.senderPlace.placeName", sortingDirection);
					paginationFilter.addSortingCriteria("document.recipientPlace.placeName", sortingDirection);
					break;
				case 4:
					paginationFilter.addSortingCriteria("topic.topicTitle", sortingDirection);
					paginationFilter.addSortingCriteria("document.sortableDateInt", sortingDirection);
//					paginationFilter.addSortingCriteria("document.docYear", "asc");
//					//Month is an entity, so we don't have field with suffix 
//					paginationFilter.addSortingCriteria("document.docMonthNum.monthNum", "asc");
//					paginationFilter.addSortingCriteria("document.docDay", "asc");
					break;
				case 5:
					paginationFilter.addSortingCriteria("document.volume.volNum", sortingDirection);
					paginationFilter.addSortingCriteria("document.volume.volLetExt", sortingDirection);
					paginationFilter.addSortingCriteria("document.folioNum", sortingDirection);
					paginationFilter.addSortingCriteria("document.folioMod", sortingDirection);
					break;
				default:
					paginationFilter.addSortingCriteria("topic.topicTitle", sortingDirection);
					break;
			}		
		}
		
		return paginationFilter;
	}
	
	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}
	
	/**
	 * 
	 * @param summaryId
	 * @return
	 */
	@RequestMapping(value = "/src/geobase/GetLinkedForum", method = RequestMethod.GET)
	public ModelAndView getLinkedForum(@RequestParam(value="placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Forum forum = getGeoBaseService().getPlaceForum(placeAllId);
			if (forum != null) {
				model.put("isPresent", Boolean.TRUE.toString());
				model.put("forumId", forum.getForumId().toString());
				model.put("forumUrl", HtmlUtils.getShowForumUrl(forum));
				model.put("forumUrlCompleteDOM", HtmlUtils.getShowForumCompleteDOMUrl(forum));
			} else {
				model.put("isPresent", Boolean.FALSE.toString());
			}
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns a list of ipotetical senders places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/de/geobase/SearchBornPlace", method = RequestMethod.GET)
	public ModelAndView searchBornPlace(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		try {
			List<Place> places = getGeoBaseService().searchBornPlace(query);
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
	 * This method returns a list of ipotetical senders places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/de/geobase/SearchDeathPlace", method = RequestMethod.GET)
	public ModelAndView searchDeathPlace(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		try {
			List<Place> places = getGeoBaseService().searchDeathPlace(query);
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
	 * This method returns a list of parent places linkable to a place. 
	 *  
	 * @param placeAllId Unique place identifier
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable parent places.
	 */
	@RequestMapping(value = "/de/geobase/SearchPlaceParent", method = RequestMethod.GET)
	public ModelAndView searchPlaceParent(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		try {
			List<Place> places = getGeoBaseService().searchPlaceParent(query);
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
	 * This method returns a list of ipotetical recipients places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing recipients.
	 */
	@RequestMapping(value = "/de/geobase/SearchRecipientPlace", method = RequestMethod.GET)
	public ModelAndView searchRecipients(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		try {
			List<Place> places = getGeoBaseService().searchRecipientsPlace(query);
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
	 * This method returns a list of ipotetical senders places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/de/geobase/SearchSenderPlace", method = RequestMethod.GET)
	public ModelAndView searchSenders(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}
		
		try {
			List<Place> places = getGeoBaseService().searchSendersPlace(query);
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
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
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
	@RequestMapping(value = "/de/geobase/ShowActiveEndPeoplePlace.json", method = RequestMethod.GET)
	public ModelAndView ShowActiveEndPeoplePlace(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.PEOPLE);
		try {
			page = getGeoBaseService().searchActiveEndPeoplePlace(alias, paginationFilter);
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
			resultList.add(HtmlUtils.showPeopleRelated(singleRow, currentPerson.getPersonId()));
		}
		
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
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
	@RequestMapping(value = "/de/geobase/ShowActiveStartPeoplePlace.json", method = RequestMethod.GET)
	public ModelAndView ShowActiveStartPeoplePlace(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.PEOPLE);
		try {
			page = getGeoBaseService().searchActiveStartPeoplePlace(alias, paginationFilter);
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
			resultList.add(HtmlUtils.showPeopleRelated(singleRow, currentPerson.getPersonId()));
		}
		
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
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
	@RequestMapping(value = "/de/geobase/ShowBirthPeoplePlace.json", method = RequestMethod.GET)
	public ModelAndView ShowBirthPeoplePlace(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.PEOPLE);
		try {
			page = getGeoBaseService().searchBirthPeoplePlace(alias, paginationFilter);
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
			resultList.add(HtmlUtils.showPeopleRelated(singleRow, currentPerson.getPersonId()));
		}
		
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on Birth Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowBirthPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showBirthPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
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
	@RequestMapping(value = "/de/geobase/ShowDeathPeoplePlace.json", method = RequestMethod.GET)
	public ModelAndView ShowDeathPeoplePlace(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.PEOPLE);
		try {
			page = getGeoBaseService().searchDeathPeoplePlace(alias, paginationFilter);
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
			resultList.add(HtmlUtils.showPeopleRelated(singleRow, currentPerson.getPersonId()));
		}
		
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on Death Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowDeathPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showDeathPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on Place linkable to topic. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowPlaceLinkableToTopicDocument", method = RequestMethod.GET)
	public ModelAndView showPlaceLinkableToTopicDocument(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/de/geobase/ShowRecipientDocumentsPlace.json", method = RequestMethod.GET)
	public ModelAndView ShowRecipientDocumentsPlace(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>(), folioNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>(), folioMods = new ArrayList<String>();
		
		try{
			page = getGeoBaseService().searchRecipientDocumentsPlace(alias, paginationFilter);
			
			for(Document currentDocument : (List<Document>)page.getList()){
				volNums.add(currentDocument.getVolume().getVolNum());
				volLetExts.add(currentDocument.getVolume().getVolLetExt());
				folioNums.add(currentDocument.getFolioNum());
				folioMods.add(currentDocument.getFolioMod());
			}
			
			stateDocumentsDigitized = getGeoBaseService().getDocumentsDigitizedState(volNums, volLetExts, folioNums, folioMods);
		}catch(ApplicationThrowable aex){
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
				if(stateDocumentsDigitized.get(currentDocument.getMDPAndFolio())){
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>&nbsp<img src=\"/DocSources/images/1024/img_digitized_small_document.png\">");
				}else{
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");
				}					
			}
			else
				singleRow.add("");

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		

		

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on recipient Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowRecipientPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showRecipientPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/de/geobase/ShowSenderDocumentsPlace.json", method = RequestMethod.GET)
	public ModelAndView ShowSenderDocumentsPlace(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>(), folioNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>(), folioMods = new ArrayList<String>();
		
		try{
			page = getGeoBaseService().searchSenderDocumentsPlace(alias, paginationFilter);
			
			for(Document currentDocument : (List<Document>)page.getList()){
				volNums.add(currentDocument.getVolume().getVolNum());
				volLetExts.add(currentDocument.getVolume().getVolLetExt());
				folioNums.add(currentDocument.getFolioNum());
				folioMods.add(currentDocument.getFolioMod());
			}
			
			stateDocumentsDigitized = getGeoBaseService().getDocumentsDigitizedState(volNums, volLetExts, folioNums, folioMods);
		}catch(ApplicationThrowable aex){
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
				if(stateDocumentsDigitized.get(currentDocument.getMDPAndFolio())){
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>&nbsp<img src=\"/DocSources/images/1024/img_digitized_small_document.png\">");
				}else{
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");
				}			
			}
			else
				singleRow.add("");

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		

		

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method returns specific information on sender Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowSenderPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showSenderPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/de/geobase/ShowTopicsPlace.json", method = RequestMethod.GET)
	public ModelAndView ShowTopicsPlace(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		//Page page = null;
		//PaginationFilter paginationFilter = generatePaginationFilter(sortingColumnNumber, sortingDirection, firstRecord, length);
		Map<String, Long> result = new HashMap<String, Long>();
		
		try{
			result = getGeoBaseService().searchTopicsPlace(alias);
		}catch(ApplicationThrowable aex){
			
		}
		
		List resultList = new ArrayList();
		for (Map.Entry<String, Long> currentElement : result.entrySet()) {
			List singleRow = new ArrayList();
			if(currentElement.getKey() != null){
				singleRow.add(currentElement.getKey());
				if(currentElement.getValue() > 1){
					singleRow.add(currentElement.getValue().toString());
				}else{
					singleRow.add(currentElement.getValue().toString());
				}
			}
			
			resultList.add(HtmlUtils.showTopicsDocumentRelated(singleRow, alias));
		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", result.size());
		model.put("iTotalRecords", result.size());
		model.put("aaData", resultList);
		

		

		return new ModelAndView("responseOK", model);
	}

}