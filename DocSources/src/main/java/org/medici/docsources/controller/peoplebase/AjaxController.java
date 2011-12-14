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
package org.medici.docsources.controller.peoplebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.lucene.search.SortField;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.RoleCat;
import org.medici.docsources.domain.TitleOccsList;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
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
 */
@Controller("PeoleBaseAjaxController")
public class AjaxController {
	@Autowired
	private PeopleBaseService peopleBaseService;

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
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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
	public ModelAndView searchRecipients(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<People> people = getPeopleBaseService().searchRecipientsPeople(query);
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
	public ModelAndView searchSenders(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			//<!-- Autocomplete (SELECT [tblPeople].[MAPnameLF], [tblPeople].[ACTIVESTART], [tblPeople].[BYEAR], [tblPeople].[DYEAR] FROM tblPeople ORDER BY [MAPnameLF];) -->

			List<People> people = getPeopleBaseService().searchSendersPeople(query);
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
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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
	 * This method returns specific information on father. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/peoplebase/ShowFatherDetails", method = RequestMethod.GET)
	public ModelAndView showFatherDetails(@RequestParam("personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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
	
	@RequestMapping(value = "/de/peoplebase/ShowSpouseDetails", method = RequestMethod.GET)
	public ModelAndView showSpouseDetails(@RequestParam("personId") Integer personId) {
		Map<String, Object> model = new HashMap<String, Object>();

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
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/de/peoplebase/ShowDocumentsRelatedPerson.json", method = RequestMethod.GET)
	public ModelAndView ShowDocumentsRelatedPerson(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page page = null;
		PaginationFilter paginationFilter = generatePaginationFilter(sortingColumnNumber, sortingDirection, firstRecord, length);
		
		try{
			page = getPeopleBaseService().searchDocumentsRelated(alias, paginationFilter);
		}catch(ApplicationThrowable aex){
			
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
				singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");				
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
	 * This method performs a simple search on people dictionary.
	 * 
	 * @param model
	 * @param searchText
	 * @param firstRecord
	 * @param length
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/de/peoplebase/ShowRoleCatPeoplePerson.json", method = RequestMethod.GET)
	public ModelAndView ShowRoleCatPeoplePerson(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Page page = null;

		PaginationFilter paginationFilter = generatePaginationFilter(sortingColumnNumber, sortingDirection, firstRecord, length);
		try {
			page = getPeopleBaseService().searchRoleCatPeoplePerson(alias, paginationFilter);
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
	@RequestMapping(value = "/de/peoplebase/ShowTitlesOrOccupationsPeoplePerson.json", method = RequestMethod.GET)
	public ModelAndView ShowTitlesOrOccupationsPeoplePerson(@RequestParam(value="sSearch") String alias,
			 								  @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			 								  @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			 								  @RequestParam(value="iDisplayStart") Integer firstRecord,
			 								  @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();
		Page page = null;

		PaginationFilter paginationFilter = generatePaginationFilter(sortingColumnNumber, sortingDirection, firstRecord, length);
		try {
			page = getPeopleBaseService().searchTitlesOrOccupationsPeoplePerson(alias, paginationFilter);
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
	 * @param searchType
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	private PaginationFilter generatePaginationFilter(Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

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
		
		return paginationFilter;
	}
}