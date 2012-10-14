/*
 * ShowReferringToDocumentsPersonController.java
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.medici.bia.command.peoplebase.ShowDocumentsPersonCommand;
import org.medici.bia.common.search.AdvancedSearchDocument;
import org.medici.bia.domain.People;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show documents person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/peoplebase/ShowReferringToDocumentsPerson")
public class ShowReferringToDocumentsPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	
	
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
	 * 
	 * @param placeId
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowDocumentsPersonCommand command, BindingResult result, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		People person = new People();
		
		if(command.getPersonId() > 0){
			try {
				person = getPeopleBaseService().findPerson(command.getPersonId());
								
				List<String> outputFields = new ArrayList<String>(6);
				outputFields.add("Sender");
				outputFields.add("Recipient");
				outputFields.add("Date");
				outputFields.add("Sender Location");
				outputFields.add("Recipient Location");
				outputFields.add("Volume / Folio");
				
				model.put("outputFields", outputFields);

				model.put("mapNameLf", person.getMapNameLf());
				model.put("personId", person.getPersonId());
				
				//MD: The following code is to refine the search
				String searchUUID = UUID.randomUUID().toString();
				model.put("UUID", searchUUID);
				SearchFilter searchFilter = new SearchFilter(0, SearchType.DOCUMENT);
				searchFilter.setDateCreated(new Date());
				searchFilter.setDateUpdated(new Date());
				searchFilter.setId(new Integer(0));
				AdvancedSearchDocument advancedSearchDocument = new AdvancedSearchDocument();
				List<Integer> referringId = new ArrayList<Integer>();
				referringId.add(person.getPersonId());
				List<String> referringName = new ArrayList<String>();
				referringName.add(person.getMapNameLf());
				advancedSearchDocument.setRefersToId(referringId);
				advancedSearchDocument.setRefersTo(referringName);
				searchFilter.setFilterData(advancedSearchDocument);
				searchFilter.setUser(new User(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
				// we get our map which contains all user's filter used at runtime.
				Map<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);
				searchFilterMap.put(searchUUID, searchFilter);
				session.setAttribute("searchFilterMap", searchFilterMap);
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				new ModelAndView("error/ShowReferringToDocumentsPerson", model);
			}
		}

		return new ModelAndView("peoplebase/ShowReferringToDocumentsPerson", model);
	}

}