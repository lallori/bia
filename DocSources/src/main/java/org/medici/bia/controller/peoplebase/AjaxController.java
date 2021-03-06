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
package org.medici.bia.controller.peoplebase;

import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.SimpleSearchTitleOrOccupation;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ListBeanUtils;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.People;
import org.medici.bia.domain.PoLink;
import org.medici.bia.domain.RoleCat;
import org.medici.bia.domain.VettingHistory;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TitleOccsList;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for PeopleBase.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("PeoleBaseAjaxController")
public class AjaxController {
	@Autowired
	private PeopleBaseService peopleBaseService;

	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/src/peoplebase/GetPortraitPersonInformation", method = RequestMethod.GET) 
	public ModelAndView getPortraitPersonInformation(@RequestParam(value="personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			People person= getPeopleBaseService().findPerson(personId);
			if (person != null) {
				if (person.getPortrait()) {
					BufferedImage bufferedImage = getPeopleBaseService().getPortraitPerson(person.getPortraitImageName());

					model.put("portraitWidth", bufferedImage.getWidth());
					model.put("portraitHeight", bufferedImage.getHeight());
				} else {
					model.put("portraitWidth", new Integer(0));
					model.put("portraitHeight", new Integer(0));
				}
			} else {
				model.put("portraitWidth", new Integer(0));
				model.put("portraitHeight", new Integer(0));
			}
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/src/peoplebase/GetLinkedForum", method = RequestMethod.GET)
	public ModelAndView getLinkedForum(@RequestParam(value="personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Forum forum = getPeopleBaseService().getPersonForum(personId);
			if (forum != null && !forum.getLogicalDelete()) {
				model.put("isPresent", Boolean.TRUE.toString());
				model.put("forumId", forum.getForumId().toString());
				model.put("forumUrl", HtmlUtils.getShowForumUrl(forum));
				model.put("discussions", forum.getTopicsNumber());
				model.put("forumUrlCompleteDOM", HtmlUtils.getShowForumCompleteDOMUrl(forum));
				model.put("logicalDelete", forum.getLogicalDelete().toString());
			} else {
				model.put("isPresent", Boolean.FALSE.toString());
			}
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/CheckPersonIsDeletable", method = RequestMethod.GET)
	public ModelAndView checkPersonIsDeletable(@RequestParam(value="personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			People person = getPeopleBaseService().findPerson(personId);

			if (person != null) {
				model.put("mapNameLf", person.getMapNameLf());

				Integer numberOfSenderDocumentsRelated = getPeopleBaseService().findNumberOfSenderDocumentsRelated(personId);
				model.put("senderDocsRelated", numberOfSenderDocumentsRelated);
				Integer numberOfRecipientDocumentsRelated = getPeopleBaseService().findNumberOfRecipientDocumentsRelated(personId);
				model.put("recipientDocsRelated", numberOfRecipientDocumentsRelated);
				Integer numberOfReferringDocumentsRelated = getPeopleBaseService().findNumberOfReferringDocumentsRelated(personId);
				model.put("referringDocsRelated", numberOfReferringDocumentsRelated);

				model.put("senderDocsRelatedURL", HtmlUtils.showSenderDocumentsRelated(personId, "As Sender"));
				model.put("recipientDocsRelatedURL", HtmlUtils.showRecipientDocumentsRelated(personId, "As Recipient"));
				model.put("referringDocsRelatedURL", HtmlUtils.showReferringToDocumentsRelated(personId, "Referring To"));

				if ((numberOfSenderDocumentsRelated>0) || (numberOfRecipientDocumentsRelated>0) || (numberOfReferringDocumentsRelated>0)){
					model.put("isDeletable", Boolean.FALSE.toString());
				} else {
					model.put("isDeletable", Boolean.TRUE.toString());
				}
			} else {
				model.put("isDeletable", Boolean.FALSE.toString());
			}
			model.put("personId", personId.toString());
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
	private PaginationFilter generatePaginationFilterForTitleOcc(Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
			case 0:
				paginationFilter.addSortingCriteria("p.mapNameLf", sortingDirection);
				break;
			case 1:
				paginationFilter.addSortingCriteria("p.gender", sortingDirection);
				paginationFilter.addSortingCriteria("p.mapNameLf", sortingDirection);
				break;
			case 2:
				paginationFilter.addSortingCriteria("p.bornYear", sortingDirection);
				//Month is an entity, so we don't have field with suffix 
				paginationFilter.addSortingCriteria("p.bornMonth.monthNum", sortingDirection);
				paginationFilter.addSortingCriteria("p.bornDay", sortingDirection);
				break;
			case 3:
				paginationFilter.addSortingCriteria("p.deathYear", sortingDirection);
				//Month is an entity, so we don't have field with suffix 
				paginationFilter.addSortingCriteria("p.deathMonth.monthNum", sortingDirection);
				paginationFilter.addSortingCriteria("p.deathDay", sortingDirection);
				break;
			case 4:
				paginationFilter.addSortingCriteria("t.startYear", sortingDirection);
				break;
			case 5:
				paginationFilter.addSortingCriteria("t.endYear", sortingDirection);
				break;
			default:
				paginationFilter.addSortingCriteria("p.mapNameLf", "asc");
				break;
			}		
		}
		
		return paginationFilter;
	}


	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * This method returns a list of ipotetical children for a person. 
	 * 
	 * @param personId
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/SearchChildLinkableToPerson", method = RequestMethod.GET)
	public ModelAndView searchChildLinkableToPerson(@RequestParam("personId") Integer personId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			List<People> people = getPeopleBaseService().searchChildLinkableToPerson(personId, query);
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
	 * This method returns a list of ipotetical children for a person. 
	 * 
	 * @param personId
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/SearchFatherLinkableToPerson", method = RequestMethod.GET)
	public ModelAndView searchFatherLinkableToPerson(@RequestParam("personId") Integer personId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			List<People> people = getPeopleBaseService().searchFatherLinkableToPerson(personId, query);
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
	 * This method returns a list of ipotetical children for a person. 
	 * 
	 * @param personId
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/SearchMotherLinkableToPerson", method = RequestMethod.GET)
	public ModelAndView searchMotherLinkableToPerson(@RequestParam("personId") Integer personId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			List<People> people = getPeopleBaseService().searchMotherLinkableToPerson(personId, query);
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
	 * This method returns a list of ipotetical recipients. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing recipients.
	 */
	@RequestMapping(value = "/de/peoplebase/SearchRecipientPeople", method = RequestMethod.GET)
	public ModelAndView searchRecipients(@RequestParam("entryId") Integer entryId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			List<People> people = getPeopleBaseService().searchRecipientsPeople(entryId, query);
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
	 * This method returns a list of ipotetical senders. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/de/peoplebase/SearchSenderPeople", method = RequestMethod.GET)
	public ModelAndView searchSenders(@RequestParam("entryId") Integer entryId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			//<!-- Autocomplete (SELECT [tblPeople].[MAPnameLF], [tblPeople].[ACTIVESTART], [tblPeople].[BYEAR], [tblPeople].[DYEAR] FROM tblPeople ORDER BY [MAPnameLF];) -->

			List<People> people = getPeopleBaseService().searchSendersPeople(entryId, query);
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
	 * This method return a list of ipotetical spouse of a person
	 * 
	 * @param personId
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/SearchSpouseLinkableToPerson", method = RequestMethod.GET)
	public ModelAndView searchSpouseLinkableToPerson(@RequestParam("personId") Integer personId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			List<People> people = getPeopleBaseService().searchChildLinkableToPerson(personId, query);
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
	 * This method returns a list of ipotetical recipients. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing recipients.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/de/peoplebase/SearchTitleOrOccupation", method = RequestMethod.GET)
	public ModelAndView searchTitleOrOccupation(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
		}

		try {
			List<TitleOccsList> titlesOrOccupations = getPeopleBaseService().searchTitleOrOccupation(query);
			model.put("query", query);
			model.put("count", titlesOrOccupations.size());
			model.put("data", ListBeanUtils.transformList(titlesOrOccupations, "titleOccId"));
			model.put("suggestions", ListBeanUtils.transformList(titlesOrOccupations, "titleOcc"));
			// transformList does not support nested property, so we need to extract list of RoleCat, then we can extract every single field.
			List<RoleCat> roleCatList = (List<RoleCat>) ListBeanUtils.transformList(titlesOrOccupations, "roleCat");
			model.put("rolesCatMajor", ListBeanUtils.transformList(roleCatList, "roleCatMajor"));
			model.put("rolesCatMinor", ListBeanUtils.transformList(roleCatList, "roleCatMinor"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * This method returns specific information on mother. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/ShowChildDetails", method = RequestMethod.GET)
	public ModelAndView showChildDetails(@RequestParam("personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			People person = getPeopleBaseService().findPerson(personId);
			model.put("personId", (person.getPersonId() != null ) ? person.getPersonId().toString() : "");
			model.put("bornYear", (person.getBornYear() != null ) ? person.getBornYear().toString() : "");
			model.put("deathYear", (person.getDeathYear() != null ) ? person.getDeathYear().toString() : "");
			model.put("ageAtDeath", ((person.getBornYear() != null ) && (person.getDeathYear() != null)) ? "" + (person.getDeathYear() - person.getBornYear()) : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param alias
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/peoplebase/ShowDocumentsRelatedPerson.json", method = RequestMethod.GET)
	public ModelAndView showDocumentsRelatedPerson(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		
		try {
			page = getPeopleBaseService().searchDocumentsRelated(alias, paginationFilter);
			stateDocumentsDigitized = getPeopleBaseService().getDocumentsDigitizedState((List<Document>)page.getList());
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList();
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList();
			if (currentDocument.getSenderPeople() != null) {
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			} else
				singleRow.add("");
			
			if (currentDocument.getRecipientPeople() != null) {
				if (!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			} else
				singleRow.add("");
			
			if (currentDocument.getYearModern() != null) {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else {
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
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			}
			else
				singleRow.add("");
			
			String documentStringFormat = DocumentUtils.toMDPInsertFolioFormat(currentDocument);
			if (stateDocumentsDigitized.get(documentStringFormat)) {
				singleRow.add("<b>"+documentStringFormat+"</b>&nbsp" + HtmlUtils.getImageDigitized());
			} else {
				singleRow.add("<b>"+documentStringFormat+"</b>");
			}				

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param textSearch
	 * @param roleCatId
	 * @param searchUUID
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/src/peoplebase/SearchTitlesOrOccupationsPagination.json", method = RequestMethod.GET)
	public ModelAndView searchTitlesOrOccupationsPagination(@RequestParam(value="textSearch") String textSearch,
										 @RequestParam(value="roleCatId") Integer roleCatId, 
								   		 @RequestParam(value="searchUUID") String searchUUID,
								   		 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection);

		Page page = null;

		try {
			page = getPeopleBaseService().searchTitlesOrOccupations(new SimpleSearchTitleOrOccupation(textSearch, roleCatId), paginationFilter);
			
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List<List<String>> resultList = new ArrayList<List<String>>();
		for (List<Object> currentRecord : (List<List<Object>>)page.getList()) {
			TitleOccsList titleOccsList = (TitleOccsList) currentRecord.get(0);
			Long assignedPeople = (Long) currentRecord.get(1);

			List<String> singleRow = new ArrayList<String>();
			singleRow.add(HtmlUtils.showTitleOrOccupation(titleOccsList));
			singleRow.add(HtmlUtils.showTitleOrOccupationAssignedPeopleSearch(titleOccsList, assignedPeople));
			
			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
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
	@RequestMapping(value = "/src/peoplebase/ShowFamilyPerson.json", method = RequestMethod.GET)
	public ModelAndView ShowFamilyPerson(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Page page = null;
		Map<Integer, Long> documentsRelated = new HashMap<Integer, Long>();
		List<Integer> personIds = new ArrayList<Integer>();
		
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.PEOPLE);
		try {
			page = getPeopleBaseService().searchFamilyPerson(alias, paginationFilter);
			
			for(People currentPerson : (List<People>) page.getList()){
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
			resultList.add(HtmlUtils.showPeopleRelated(singleRow, currentPerson.getPersonId()));
		}
		
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on father. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/ShowFatherDetails", method = RequestMethod.GET)
	public ModelAndView showFatherDetails(@RequestParam("personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			People person = getPeopleBaseService().findPerson(personId);
			model.put("personId", (person.getPersonId() != null ) ? person.getPersonId().toString() : "");
			model.put("bornYear", (person.getBornYear() != null ) ? person.getBornYear().toString() : "");
			model.put("bornMonth", (person.getBornMonth() != null ) ? person.getBornMonth().toString() : "");
			model.put("bornDay", (person.getBornDay() != null ) ? person.getBornDay().toString() : "");
			model.put("deathYear", (person.getDeathYear() != null ) ? person.getDeathYear().toString() : "");
			model.put("deathMonth", (person.getDeathMonth() != null ) ? person.getDeathMonth().toString() : "");
			model.put("deathDay", (person.getDeathDay() != null ) ? person.getDeathDay().toString() : "");
			model.put("bioNotes", (person.getBioNotes() != null ) ? person.getBioNotes().toString() : "");
			model.put("gender", (person.getGender() != null) ? person.getGender().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on mother. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/ShowMotherDetails", method = RequestMethod.GET)
	public ModelAndView showMotherDetails(@RequestParam("personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			People person = getPeopleBaseService().findPerson(personId);
			model.put("personId", (person.getPersonId() != null ) ? person.getPersonId().toString() : "");
			model.put("bornYear", (person.getBornYear() != null ) ? person.getBornYear().toString() : "");
			model.put("bornMonth", (person.getBornMonth() != null ) ? person.getBornMonth().toString() : "");
			model.put("bornDay", (person.getBornDay() != null ) ? person.getBornDay().toString() : "");
			model.put("deathYear", (person.getDeathYear() != null ) ? person.getDeathYear().toString() : "");
			model.put("deathMonth", (person.getDeathMonth() != null ) ? person.getDeathMonth().toString() : "");
			model.put("deathDay", (person.getDeathDay() != null ) ? person.getDeathDay().toString() : "");
			model.put("bioNotes", (person.getBioNotes() != null ) ? person.getBioNotes().toString() : "");
			model.put("gender", (person.getGender() != null) ? person.getGender().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param alias
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/peoplebase/ShowRecipientDocumentsRelatedPerson.json", method = RequestMethod.GET)
	public ModelAndView ShowRecipientDocumentsRelatedPerson(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		
		try{
			page = getPeopleBaseService().searchRecipientDocumentsRelated(alias, paginationFilter);
			stateDocumentsDigitized = getPeopleBaseService().getDocumentsDigitizedState((List<Document>)page.getList());
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
			
			if (currentDocument.getRecipientPeople() != null) {
				if(!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			}
			else
				singleRow.add("");
			
			if (currentDocument.getYearModern() != null) {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null) {
				if(!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			}
			else
				singleRow.add("");
			
			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			}
			else
				singleRow.add("");
			
			String documentStringFormat = DocumentUtils.toMDPInsertFolioFormat(currentDocument);
			if (stateDocumentsDigitized.get(documentStringFormat)) {
				singleRow.add("<b>"+documentStringFormat+"</b>&nbsp;" + HtmlUtils.getImageDigitized());
			} else {
				singleRow.add("<b>"+documentStringFormat+"</b>");
			}				

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/peoplebase/ShowReferringToDocumentsRelatedPerson.json", method = RequestMethod.GET)
	public ModelAndView ShowReferringToDocumentsRelatedPerson(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		
		try {
			page = getPeopleBaseService().searchReferringToDocumentsRelated(alias, paginationFilter);
			stateDocumentsDigitized = getPeopleBaseService().getDocumentsDigitizedState((List<Document>)page.getList());
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList();
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList();
			if (currentDocument.getSenderPeople() != null) {
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			} else
				singleRow.add("");
			
			if (currentDocument.getRecipientPeople() != null) {
				if (!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			} else
				singleRow.add("");

			if (currentDocument.getYearModern() != null) {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null) {
				if (!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			} else
				singleRow.add("");
			
			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			} else
				singleRow.add("");
			
			String documentStringFormat = DocumentUtils.toMDPInsertFolioFormat(currentDocument);
			if (stateDocumentsDigitized.get(documentStringFormat)) {
				singleRow.add("<b>"+documentStringFormat+"</b>&nbsp;" + HtmlUtils.getImageDigitized());
			} else
				singleRow.add("<b>"+documentStringFormat+"</b>");

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
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
	@RequestMapping(value = "/src/peoplebase/ShowRoleCatPeoplePerson.json", method = RequestMethod.GET)
	public ModelAndView ShowRoleCatPeoplePerson(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.PEOPLE);
		try {
			page = getPeopleBaseService().searchRoleCatPeoplePerson(alias, paginationFilter);
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
	 * 
	 * @param alias
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/peoplebase/ShowSenderDocumentsRelatedPerson.json", method = RequestMethod.GET)
	public ModelAndView ShowSenderDocumentsRelatedPerson(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		
		try {
			page = getPeopleBaseService().searchSenderDocumentsRelated(alias, paginationFilter);
			stateDocumentsDigitized = getPeopleBaseService().getDocumentsDigitizedState((List<Document>)page.getList());
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList();
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList();
			if (currentDocument.getSenderPeople() != null) {
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			} else
				singleRow.add("");
			
			if (currentDocument.getRecipientPeople() != null) {
				if (!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable"))
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				else
					singleRow.add("Person Name Lost");
			} else
				singleRow.add("");

			if(currentDocument.getYearModern() != null) {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null) {
				if(!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			} else
				singleRow.add("");
			
			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable"))
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				else
					singleRow.add("Place Name Lost");
			}
			else singleRow.add("");
			
			String documentStringFormat = DocumentUtils.toMDPInsertFolioFormat(currentDocument);
			if (stateDocumentsDigitized.get(documentStringFormat)) {
				singleRow.add("<b>"+documentStringFormat+"</b>&nbsp;" + HtmlUtils.getImageDigitized());
			} else
				singleRow.add("<b>"+documentStringFormat+"</b>");

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		

		

		return new ModelAndView("responseOK", model);
	}
	
	@RequestMapping(value = "/de/peoplebase/ShowSpouseDetails", method = RequestMethod.GET)
	public ModelAndView showSpouseDetails(@RequestParam("personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			People person = getPeopleBaseService().findPerson(personId);
			model.put("personId", (person.getPersonId() != null ) ? person.getPersonId().toString() : "");
			model.put("bornYear", (person.getBornYear() != null ) ? person.getBornYear().toString() : "");
			model.put("deathYear", (person.getDeathYear() != null ) ? person.getDeathYear().toString() : "");
			model.put("gender", (person.getGender() != null) ? person.getGender().toString() : "");
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
	@RequestMapping(value = "/src/peoplebase/ShowTitlesOrOccupationsPeoplePerson.json", method = RequestMethod.GET)
	public ModelAndView showTitlesOrOccupationsPeoplePerson(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		Page page = null;
		List<Integer> peopleIds = new ArrayList<Integer>(); 
		Map<Integer, List<PoLink>> occupations = new HashMap<Integer, List<PoLink>>();

		PaginationFilter paginationFilter = generatePaginationFilterForTitleOcc(sortingColumnNumber, sortingDirection, firstRecord, length);
		try {
			page = getPeopleBaseService().searchTitlesOrOccupationsPeoplePerson(alias, paginationFilter);
			
			for(People currentPerson : (List<People>)page.getList()){
				peopleIds.add(currentPerson.getPersonId());
			}
			
			occupations = getPeopleBaseService().getOccupationsDetails(alias, peopleIds);
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
			List<PoLink> occupationsPerson = occupations.get(currentPerson.getPersonId());
			if (occupationsPerson != null) {
				if (occupationsPerson.get(0) != null) {
					singleRow.add(DateUtils.getStringDateHTMLForTable(occupationsPerson.get(0).getStartYear(), occupationsPerson.get(0).getStartMonthNum(), occupationsPerson.get(0).getStartDay()));
					singleRow.add(DateUtils.getStringDateHTMLForTable(occupationsPerson.get(0).getEndYear(), occupationsPerson.get(0).getEndMonthNum(), occupationsPerson.get(0).getEndDay()));
					occupationsPerson.remove(0);
					occupations.put(currentPerson.getPersonId(), occupationsPerson);
				} else {
					singleRow.add("");
					singleRow.add("");
				}
			}
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
	@RequestMapping(value = "/de/peoplebase/ShowTitlesOrOccupations.json", method = RequestMethod.GET)
	public ModelAndView showTitlesOrOccupations(
			@RequestParam(value="sSearch", required=false) String alias,
			@RequestParam(value="roleCatId", required=false) Integer roleCatId,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		Page page = null;

		PaginationFilter paginationFilter = generatePaginationFilterForTitleOcc(sortingColumnNumber, sortingDirection, firstRecord, length);
		try {
			page = getPeopleBaseService().searchTitlesOrOccupations(alias, roleCatId, paginationFilter);

		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (List<Object> currentRow : (List<List<Object>>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentRow.get(0));
			singleRow.add(currentRow.get(1));
		}
		
		model.put("iEcho", "" + 1);
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * 
	 * @param personId
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/peoplebase/ShowVettingHistoryPerson.json", method = RequestMethod.GET)
	public ModelAndView ShowVettingHistoryPerson(
			@RequestParam(value="personId") Integer personId,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		try {
			page = getPeopleBaseService().searchVettingHistoryPerson(personId, paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList();
		for (VettingHistory currentVettingHistory : (List<VettingHistory>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(simpleDateFormat.format(currentVettingHistory.getDateAndTime()));
			singleRow.add(currentVettingHistory.getDescription());
			singleRow.add(currentVettingHistory.getUser().getAccount());
			
			resultList.add(singleRow);
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
}
