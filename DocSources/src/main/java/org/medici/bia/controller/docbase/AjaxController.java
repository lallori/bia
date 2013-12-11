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
import java.text.SimpleDateFormat;
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
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.ListBeanUtils;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.TopicList;
import org.medici.bia.domain.VettingHistory;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.volbase.VolBaseService;
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
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private DocBaseService docBaseService;
	
	@Autowired
	private VolBaseService volBaseService;
	
	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}
	
	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
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
	 */
	@RequestMapping(value = "/src/docbase/CheckDocumentDigitized", method = RequestMethod.GET)
	public ModelAndView checkDocumentDigitized(
			@RequestParam(value="entryId", required=false) Integer entryId,
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRectoVerso", required=false) String rectoVerso) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		if (entryId != null) {
			try {
				Boolean digitized = getDocBaseService().checkDocumentDigitized(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, rectoVerso);
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
	
	@RequestMapping(value="/src/docbase/CheckInsert", method = RequestMethod.GET)
	public ModelAndView checkInsert(
			@RequestParam(value="volume", required=true) String volume,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		if (volume != null && volume.trim() != "") {
			try {
				Integer volNum = VolumeUtils.extractVolNum(volume);
				String volLetExt = VolumeUtils.extractVolLetExt(volume);
				if (volNum != null) {
					Boolean volumeDigitized = getVolBaseService().checkVolumeDigitized(volNum, volLetExt);
					model.put("volumeDigitized", volumeDigitized);
					if (volumeDigitized) {
						Boolean insertOK = getDocBaseService().checkInsert(volNum, volLetExt, insertNum, insertLet);
						model.put("insertOK", insertOK);
					}
				} else {
					model.put("error", "volume incorrect format");
				}
			} catch (ApplicationThrowable e) {
				model.put("error", e.getApplicationError().toString());
			}
		} else {
			model.put("error", "incorrect call");
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
				// RR: now we enable the document deletion even if the document is referred to people or places
				
				/*Integer numberOfTopicsOnDocument = getDocBaseService().findNumberOfTopicsOnDocument(document.getEntryId());
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
				}*/
				
				model.put("isDeletable", Boolean.TRUE.toString());
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
		
		try {
			Document document = getDocBaseService().checkVolumeFolio(summaryId);
			if (document != null) {
				model.put("folioMax", document.getFolioNum());
			} else {
				model.put("folioMax", "");
			}
		} catch (ApplicationThrowable applicationThrowable) {
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
	public ModelAndView findDocument(
			@RequestParam(value="volume", required=false) String volume,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRectoVerso", required=false) String folioRectoVerso) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Integer volNum = VolumeUtils.extractVolNum(volume);
			String volLetExt = VolumeUtils.extractVolLetExt(volume);
			Document.RectoVerso folioRV = Document.RectoVerso.convertFromString(folioRectoVerso);
			List<Document> documents = getDocBaseService().findDocument(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, folioRV);
			if(documents != null && documents.size() > 0){
				model.put("entryId", documents.get(0).getEntryId());
				model.put("countAlreadyEntered", documents.size());
				model.put("volNum", volNum);
				model.put("volLetExt", volLetExt == null || "".equals(volLetExt.trim()) ? "" : volLetExt);
				model.put("insertNum", insertNum == null || "".equals(insertNum.trim()) ? "" : insertNum);
				model.put("insertLet", insertLet == null || "".equals(insertLet.trim()) ? "" : insertLet);
				model.put("folioNum", folioNum);
				model.put("folioMod", folioMod == null || "".equals(folioMod.trim()) ? "" : folioMod);
				model.put("folioRectoVerso", folioRectoVerso == null || "".equals(folioRectoVerso.trim()) ? "" : folioRectoVerso);
			} else {
				model.put("entryId", "");
				model.put("countAlreadyEntered", 0);
			}			
		} catch (ApplicationThrowable th) {
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
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/src/docbase/GetLinkedForum", method = RequestMethod.GET)
	public ModelAndView getLinkedForum(@RequestParam(value="entryId") Integer entryId) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			Forum forum = getDocBaseService().getDocumentForum(entryId);
			if (forum != null && !forum.getLogicalDelete()) {
				model.put("isPresent", Boolean.TRUE.toString());
				model.put("forumId", forum.getForumId().toString());
				model.put("forumUrl", HtmlUtils.getShowForumUrl(forum));
				model.put("discussions", forum.getTopicsNumber());
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
	public ModelAndView searchPlaceLinkableToTopicDocument(
			@RequestParam("entryId") Integer entryId,
			@RequestParam("query") String query) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			query = new String(query.getBytes(), "UTF-8");
		} catch(UnsupportedEncodingException unsupportedEncodingException) {
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
	public ModelAndView searchTopicLinkableToDocument(
			@RequestParam("entryId") Integer entryId,
			@RequestParam("query") String query) {
		
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
	 * @param alias
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/docbase/LinkedDocumentsTopic.json", method = RequestMethod.GET)
	public ModelAndView showLinkedDocumentsTopic(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		Map<String, Boolean> stateDocumentsDigitized = new HashMap<String, Boolean>();
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		
		String place = null, topic = null;
		StringTokenizer stringTokenizer = new StringTokenizer(alias, "|");
		if (stringTokenizer.countTokens() == 2) {
			place = stringTokenizer.nextToken();
			topic = stringTokenizer.nextToken();
		}
		
		try {
			page = getDocBaseService().searchLinkedDocumentsTopic(place, topic, paginationFilter);
			stateDocumentsDigitized = getDocBaseService().getDocumentsDigitizedState((List<Document>)page.getList());
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList(0);
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList(0);
			if (currentDocument.getSenderPeople() != null) {
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPeople() != null) {
				if(!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getYearModern() != null) {
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

			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				} 
			} else {
				singleRow.add("");
			} 
			
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
	@RequestMapping(value = "/src/docbase/ShowSameFolioDocuments.json", method = RequestMethod.GET)
	public ModelAndView showSameFolioDocuments(
			@RequestParam(value="volNum", required=false) Integer volNum,
			@RequestParam(value="volLetExt", required=false) String volLetExt,
			@RequestParam(value="insertNum", required=false) String insertNum,
			@RequestParam(value="insertLet", required=false) String insertLet,
			@RequestParam(value="folioNum", required=false) Integer folioNum,
			@RequestParam(value="folioMod", required=false) String folioMod,
			@RequestParam(value="folioRectoVerso", required=false) String folioRectoVerso,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		List<Document> documents = null;
		try {
			documents = getDocBaseService().findDocument(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, Document.RectoVerso.convertFromString(folioRectoVerso));
		} catch (ApplicationThrowable applicationThrowable){
			logger.debug(applicationThrowable);
		}
		
		List resultList = new ArrayList(0);
		for (Document currentDocument : documents) {
			List singleRow = new ArrayList(0);
			if (currentDocument.getSenderPeople() != null) {
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPeople() != null) {
				if (!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
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
				if (!currentDocument.getSenderPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getSenderPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());	
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			singleRow.add("<b>"+DocumentUtils.toMDPInsertFolioFormat(currentDocument)+"</b>");				

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
	public ModelAndView ShowTopicsRelatedDocument(
			@RequestParam(value="sSearch") String alias,
			@RequestParam(value="topicId") Integer topicId,
			@RequestParam(value="placeAllId") Integer placeAllId,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection, SearchType.DOCUMENT);
		
		try {
			page = getDocBaseService().searchTopicsRelatedDocument(topicId, placeAllId, paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}
		
		List resultList = new ArrayList(0);
		for (Document currentDocument : (List<Document>)page.getList()) {
			List singleRow = new ArrayList(0);
			if (currentDocument.getSenderPeople() != null) {
				if(!currentDocument.getSenderPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")){
					singleRow.add(currentDocument.getSenderPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getRecipientPeople() != null) {
				if(!currentDocument.getRecipientPeople().getMapNameLf().equals("Person Name Lost, Not Indicated or Unidentifiable")) {
					singleRow.add(currentDocument.getRecipientPeople().getMapNameLf());
				} else {
					singleRow.add("Person Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			if (currentDocument.getYearModern() != null) {
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
			
			if (currentDocument.getRecipientPlace() != null) {
				if (!currentDocument.getRecipientPlace().getPlaceName().equals("Place Name Lost, Not Indicated or Unidentifable")) {
					singleRow.add(currentDocument.getRecipientPlace().getPlaceName());
				} else {
					singleRow.add("Place Name Lost");
				}
			} else {
				singleRow.add("");
			}
			
			StringBuilder titleLastColumn = new StringBuilder();
			StringBuilder lastColumn = new StringBuilder();
			lastColumn.append("<b>" + currentDocument.getVolume().getMDP());
			lastColumn.append("</b><br />");
			titleLastColumn.append("Volume " + currentDocument.getVolume().getMDP() + ", ");
			lastColumn.append("(");
			if (currentDocument.getInsertNum() != null && !currentDocument.getInsertNum().equals("")) {
				lastColumn.append(currentDocument.getInsertNum() + "/");
				titleLastColumn.append("Insert " + currentDocument.getInsertNum() + ", ");
				if (currentDocument.getInsertLet() != null) {
					lastColumn.append(currentDocument.getInsertLet());
					titleLastColumn.append("Part " + currentDocument.getInsertLet() + ", ");
				} else {
					lastColumn.append("-");
				}					
			} else {
				lastColumn.append("-/-");
			}
			lastColumn.append(")<br />");
			lastColumn.append("<b>");
			if (currentDocument.getFolioNum() != null) {
				lastColumn.append(currentDocument.getFolioNum());
				titleLastColumn.append("Folio " + currentDocument.getFolioNum());
				if (currentDocument.getFolioMod() != null) {
					lastColumn.append(currentDocument.getFolioMod());
					titleLastColumn.append(currentDocument.getFolioMod());
				}
				if (currentDocument.getFolioRectoVerso() != null) {
					lastColumn.append(" ").append(currentDocument.getFolioRectoVerso().toString());
					titleLastColumn.append(" ").append(currentDocument.getFolioRectoVerso().toString());
				}
			} else {
				lastColumn.append("NNF");
				titleLastColumn.append("Folio NNF");
			}
			lastColumn.append("</b>");
			if (currentDocument.getVolume().getDigitized()) {
				lastColumn.append("&nbsp;" + HtmlUtils.getImageDigitized());
			}
			singleRow.add(lastColumn.toString());
			
			resultList.add(HtmlUtils.showDocumentRelated(singleRow, currentDocument.getEntryId(), DocumentUtils.toMDPInsertFolioFormat(currentDocument), titleLastColumn.toString()));
		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);

		return new ModelAndView("responseOK", model);
	}
	
	@SuppressWarnings({"rawtypes", "unchecked" })
	@RequestMapping(value = "/src/docbase/ShowVettingHistoryDocument.json", method = RequestMethod.GET)
	public ModelAndView ShowVettingHistoryDocument(
			@RequestParam(value="entryId") Integer entryId,
			@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
			@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
			@RequestParam(value="iDisplayStart") Integer firstRecord,
			@RequestParam(value="iDisplayLength") Integer length) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord, length, sortingColumnNumber, sortingDirection);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		try {
			page = getDocBaseService().searchVettingHistoryDocument(entryId,
					paginationFilter);
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}

		List resultList = new ArrayList();
		for (VettingHistory currentVettingHistory : (List<VettingHistory>) page.getList()) {
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