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
package org.medici.bia.controller.docbase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ListBeanUtils;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EplToLink;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TopicList;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for DocBase.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller("DocBaseAjaxController")
public class AjaxController {
	@Autowired
	private DocBaseService docBaseService;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	@RequestMapping(value = "/src/docbase/CheckDocumentDigitized", method = RequestMethod.GET)
	public ModelAndView checkDocumentDigitized(	@RequestParam(value="entryId", required=false) Integer entryId,
												@RequestParam(value="volNum", required=false) Integer volNum, 
												@RequestParam(value="volLetExt", required=false) String volLetExt,
												@RequestParam(value="folioNum", required=false) Integer folioNum,
												@RequestParam(value="folioMod", required=false) String folioMod) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		if (entryId != null) {
			try {
				Boolean digitized = getDocBaseService().checkDocumentDigitized(volNum, volLetExt, folioNum, folioMod);
				model.put("digitized", digitized.toString());
			} catch (ApplicationThrowable aex) {
				model.put("digitized", "false");
				model.put("error", aex.getApplicationError().toString());
			}
		} else {
			model.put("digitized", "false");
			model.put("error", "incorrect call");
		}
		
		model.put("entryId", entryId.toString());
		model.put("folioNum", folioNum.toString());
		if(folioMod != null) {
			model.put("folioMod", folioMod);
		}
		model.put("volNum", volNum.toString()); 
		
		if(volLetExt != null) {
			model.put("volLetExt", volLetExt);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/docbase/CheckDocumentIsDeletable", method = RequestMethod.GET)
	public ModelAndView checkDocumentIsDeletable(@RequestParam(value="entryId") Integer entryId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Document document = getDocBaseService().findDocument(entryId);

			if (document != null) {
				Integer numberOfTopicsOnDocument = getDocBaseService().findNumberOfTopicsOnDocument(document.getEntryId());
				model.put("topicsOnDocument", numberOfTopicsOnDocument.toString());
				if (numberOfTopicsOnDocument>0) {
					model.put("isDeletable", Boolean.FALSE.toString());
				}
				
				Integer numberOfPeopleOnDocument = getDocBaseService().findNumberOfPeopleLinkedOnDocument(document.getEntryId());
				model.put("peopleOnDocument", numberOfTopicsOnDocument.toString());
				if (numberOfPeopleOnDocument>0) {
					model.put("isDeletable", Boolean.FALSE.toString());
				}

				if (document.getSenderPeople() != null) {
					model.put("isDeletable", Boolean.FALSE.toString());
				}
				if (document.getSenderPlace() != null) {
					model.put("isDeletable", Boolean.FALSE.toString());
				}
				if (document.getRecipientPeople() != null) {
					model.put("isDeletable", Boolean.FALSE.toString());
				}
				if (document.getRecipientPlace() != null) {
					model.put("isDeletable", Boolean.FALSE.toString());
				}
			} else {
				model.put("isDeletable", Boolean.FALSE.toString());
			}
			model.put("entryId", entryId.toString());
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * 
	 * @param summaryId
	 * @return
	 */
	@RequestMapping(value = "/src/docbase/CheckVolumeFolio", method = RequestMethod.GET)
	public ModelAndView checkVolumeFolio(	@RequestParam(value="summaryId", required=false) Integer summaryId) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			Document document = getDocBaseService().checkVolumeFolio(summaryId);
			if(document != null){
				model.put("folioMax", document.getFolioNum());
			}else{
				model.put("folioMax", "");
			}			
		}catch(ApplicationThrowable applicationThrowable){
			model.put("folioMax", "");
		}
		return new ModelAndView("responseOK", model);		
	}

	/**
	 * 	
	 * @param volNum
	 * @param volLetExt
	 * @param folioNum
	 * @param folioMod
	 * @return
	 */
	@RequestMapping(value = "/src/docbase/FindDocument", method = RequestMethod.GET)
	public ModelAndView findDocument(	@RequestParam(value="volNum", required=false) Integer volNum, 
									@RequestParam(value="volLetExt", required=false) String volLetExt,
									@RequestParam(value="folioNum", required=false) Integer folioNum,
									@RequestParam(value="folioMod", required=false) String folioMod) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			List<Document> documents = getDocBaseService().findDocument(volNum, volLetExt, folioNum, folioMod);
			if(documents != null){
				model.put("entryId", documents.get(0).getEntryId());
				model.put("countAlreadyEntered", documents.size());
				model.put("volNum", volNum);
				if(volLetExt == null || volLetExt.equals("")){
					model.put("volLetExt", "");
				}else{
					model.put("volLetExt", volLetExt);
				}
				model.put("folioNum", folioNum);
				if(folioMod == null || folioMod.equals("")){
					model.put("folioMod", folioMod);
				}else{
					model.put("folioMod", folioMod);
				}
			}else{
				model.put("entryId", "");
				model.put("countAlreadyEntered", 0);
			}			
		}catch(ApplicationThrowable th){
			model.put("entryId", "");
			model.put("countAlreadyEntered", "");
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
	private PaginationFilter generatePaginationFilter(Integer sortingColumnNumber, String sortingDirection, Integer firstRecord, Integer length) {
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length);

		if (!ObjectUtils.toString(sortingColumnNumber).equals("")) {
			switch (sortingColumnNumber) {
			case 0:
				paginationFilter.addSortingCriteria("place.placeName", sortingDirection);
				break;
			case 1:
				paginationFilter.addSortingCriteria("document.senderPeople.mapNameLf", sortingDirection);
				break;
			case 2:
				paginationFilter.addSortingCriteria("document.recipientPeople.mapNameLf", sortingDirection);
				break;
			case 3:
				paginationFilter.addSortingCriteria("document.docYear", sortingDirection);
				//Month is an entity, so we don't have field with suffix 
				paginationFilter.addSortingCriteria("document.docMonthNum.monthNum", sortingDirection);
				paginationFilter.addSortingCriteria("document.docDay", sortingDirection);
				break;
			case 4:
				paginationFilter.addSortingCriteria("document.volume.volNum", sortingDirection);
				paginationFilter.addSortingCriteria("document.volume.volLetExt", sortingDirection);
				paginationFilter.addSortingCriteria("document.folioNum", sortingDirection);
				paginationFilter.addSortingCriteria("document.folioMod", sortingDirection);
				break;
			default:
				paginationFilter.addSortingCriteria("docYear", sortingDirection);
				paginationFilter.addSortingCriteria("docMonthNum.monthNum", sortingDirection);
				paginationFilter.addSortingCriteria("docDay", sortingDirection);
				break;
			}		
		}
		
		return paginationFilter;
	}

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}
	
	/**
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/src/docbase/GetLinkedForum", method = RequestMethod.GET)
	public ModelAndView getLinkedForum(@RequestParam(value="entryId") Integer entryId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Forum forum = getDocBaseService().getDocumentForum(entryId);
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
	 * This method returns a list of person to add to document. Result does not
	 * contains person already linked to document. 
	 * 
	 * @param entryId Unique document identifier
	 * @param text Text to search in ...
	 * @return ModelAndView containing linkable people.
	 */
	@RequestMapping(value = "/de/docbase/SearchPersonLinkableToDocument", method = RequestMethod.GET)
	public ModelAndView searchPersonLinkableToDocument(@RequestParam("entryId") Integer entryId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch(UnsupportedEncodingException unsupportedEncodingException){
			logger.debug(unsupportedEncodingException);
		}

		try {
			//<!-- Autocomplete (SELECT [tblPeople].[MAPnameLF], [tblPeople].[ACTIVESTART], [tblPeople].[BYEAR], [tblPeople].[DYEAR] FROM tblPeople ORDER BY [MAPnameLF];) -->

			List<People> people = getDocBaseService().searchPersonLinkableToDocument(entryId, query);
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
	 * This method returns a list of places linkable to a document's topic. 
	 *  
	 * @param entryId Unique document identifier
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable topics.
	 */
	@RequestMapping(value = "/de/docbase/SearchPlaceLinkableToTopicDocument", method = RequestMethod.GET)
	public ModelAndView searchPlaceLinkableToTopicDocument(@RequestParam("entryId") Integer entryId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			query = new String(query.getBytes(), "UTF-8");
		} catch(UnsupportedEncodingException unsupportedEncodingException){
			logger.debug(unsupportedEncodingException);
		}

		try {
			List<Place> places = getDocBaseService().searchPlaceLinkableToTopicDocument(entryId, query);
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
	 * This method returns a list of topics linkable to document. Result does not
	 * contains topics already linked to document. 
	 *  
	 * @param entryId Unique document identifier
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable topics.
	 */
	@RequestMapping(value = "/de/docbase/SearchTopicLinkableToDocument", method = RequestMethod.GET)
	public ModelAndView searchTopicLinkableToDocument(@RequestParam("entryId") Integer entryId, @RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			List<TopicList> topics = getDocBaseService().searchTopicLinkableToDocument(entryId, query);
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
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/docbase/LinkedDocumentsTopic.json", method = RequestMethod.GET)
	public ModelAndView showLinkedDocumentsTopic(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		List<Integer> volNums = new ArrayList<Integer>(), folioNums = new ArrayList<Integer>();
		List<String> volLetExts = new ArrayList<String>(), folioMods = new ArrayList<String>();
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		
		String place = null, topic = null;
		StringTokenizer stringTokenizer = new StringTokenizer(alias, "|");
		if(stringTokenizer.countTokens() == 2){
			place = stringTokenizer.nextToken();
			topic = stringTokenizer.nextToken();
		}
		
		try{
			page = getDocBaseService().searchLinkedDocumentsTopic(place, topic, paginationFilter);
			
			for(Document currentDocument : (List<Document>)page.getList()){
				volNums.add(currentDocument.getVolume().getVolNum());
				volLetExts.add(currentDocument.getVolume().getVolLetExt());
				folioNums.add(currentDocument.getFolioNum());
				folioMods.add(currentDocument.getFolioMod());
			}
			stateDocumentsDigitized = getDocBaseService().getDocumentsDigitizedState(volNums, volLetExts, folioNums, folioMods);
		}catch(ApplicationThrowable aex){
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
			}else{
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

			if (currentDocument.getRecipientPlace() != null) {
				if(!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				} 
			} else {
				singleRow.add("");
			} 
			
			if (currentDocument.getMDPAndFolio() != null) {
				if(stateDocumentsDigitized.get(currentDocument.getMDPAndFolio()) ){
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>&nbsp" + HtmlUtils.getImageDigitized());
				} else {
					singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");
				}
			} else {
				singleRow.add("");
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
	@RequestMapping(value = "/src/docbase/ShowSameFolioDocuments.json", method = RequestMethod.GET)
	public ModelAndView showSameFolioDocuments(@RequestParam(value="volNum", required=false) Integer volNum,
										 @RequestParam(value="volLetExt", required=false) String volLetExt,
										 @RequestParam(value="folioNum", required=false) Integer folioNum,
										 @RequestParam(value="folioMod", required=false) String folioMod,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		List<Document> documents = null;
		try{
			documents = getDocBaseService().findDocument(volNum, volLetExt, folioNum, folioMod);
		}catch(ApplicationThrowable applicationThrowable){
			logger.debug(applicationThrowable);
		}
		
		List resultList = new ArrayList(0);
		for (Document currentDocument : documents) {
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
			
			if(currentDocument.getYearModern() != null) {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getYearModern(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			} else {
				singleRow.add(DateUtils.getStringDateHTMLForTable(currentDocument.getDocYear(), currentDocument.getDocMonthNum(), currentDocument.getDocDay()));
			}
			
			if (currentDocument.getSenderPlace() != null) {
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
				singleRow.add("<b>"+currentDocument.getMDPAndFolio()+"</b>");				
			} else {
				singleRow.add("");
			}

			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", documents.size());
		model.put("iTotalRecords", documents.size());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/docbase/ShowTopicsRelatedDocument.json", method = RequestMethod.GET)
	public ModelAndView ShowTopicsRelatedDocument(@RequestParam(value="sSearch") String alias,
										 @RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 @RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 @RequestParam(value="iDisplayStart") Integer firstRecord,
									     @RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = generatePaginationFilter(sortingColumnNumber, sortingDirection, firstRecord, length);
		
		try{
			page = getDocBaseService().searchTopicsRelatedDocument(alias, paginationFilter);
		}catch(ApplicationThrowable aex){
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList();
		for (EplToLink currentEplToLink : (List<EplToLink>)page.getList()) {
			List singleRow = new ArrayList();
			singleRow.add(currentEplToLink.getPlace().getPlaceName());
			if (currentEplToLink.getDocument().getSenderPeople() != null){
				if(!currentEplToLink.getDocument().getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentEplToLink.getDocument().getSenderPeople().getMapNameLf());
				} else{
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentEplToLink.getDocument().getRecipientPeople() != null){
				if(!currentEplToLink.getDocument().getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")){
					singleRow.add(currentEplToLink.getDocument().getRecipientPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}

			singleRow.add(DateUtils.getStringDateHTMLForTable(currentEplToLink.getDocument().getDocYear(), currentEplToLink.getDocument().getDocMonthNum(), currentEplToLink.getDocument().getDocDay()));
			
			if (currentEplToLink.getDocument().getMDPAndFolio() != null) {
				singleRow.add("<b>"+currentEplToLink.getDocument().getMDPAndFolio()+"</b>");				
			} else {
				singleRow.add("");
			}
			
			
			resultList.add(HtmlUtils.showTopicsDocumentRelated(singleRow, currentEplToLink.getDocument().getEntryId()));
		}

		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
}