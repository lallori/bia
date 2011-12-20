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
package org.medici.docsources.controller.docbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.TopicList;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
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
 */
@Controller("DocBaseAjaxController")
public class AjaxController {
	@Autowired
	private DocBaseService docBaseService;

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/src/docbase/CheckDocumentDigitized", method = RequestMethod.GET)
	public ModelAndView checkDocumentDigitized(	@RequestParam(value="entryId", required=false) Integer entryId,
												@RequestParam(value="volNum", required=false) Integer volNum, 
												@RequestParam(value="volLetExt", required=false) String volLetExt,
												@RequestParam(value="folioNum", required=false) Integer folioNum,
												@RequestParam(value="folioMod", required=false) String folioMod) {
		Map<String, Object> model = new HashMap<String, Object>();
		
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
		if(folioMod != null)
			model.put("folioMod", folioMod.toString());
		model.put("volNum", volNum.toString()); 
		if(volLetExt != null)
		model.put("volLetExt", volLetExt.toString());

		return new ModelAndView("responseOK", model);
	}
	
	@RequestMapping(value = "/src/docbase/FindDocument", method = RequestMethod.GET)
	public ModelAndView findDocument(	@RequestParam(value="volNum", required=false) Integer volNum, 
									@RequestParam(value="volLetExt", required=false) String volLetExt,
									@RequestParam(value="folioNum", required=false) Integer folioNum,
									@RequestParam(value="folioMod", required=false) String folioMod) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			Document document = getDocBaseService().findDocument(volNum, volLetExt, folioNum, folioMod);
			if(document != null){
				model.put("entryId", document.getEntryId());
			}else{
				model.put("entryId", "");
			}			
		}catch(ApplicationThrowable th){
			model.put("entryId", "");
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
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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
		Map<String, Object> model = new HashMap<String, Object>();

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

}