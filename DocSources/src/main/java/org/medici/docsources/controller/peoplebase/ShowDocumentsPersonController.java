/*
 * ShowDocumentsPersonController.java
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.medici.docsources.command.peoplebase.ShowDocumentsPersonCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EpLink;
import org.medici.docsources.domain.People;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
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
 */
@Controller
@RequestMapping("/de/peoplebase/ShowDocumentsPerson")
public class ShowDocumentsPersonController {
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
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowDocumentsPersonCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		People person = new People();
		
		if(command.getPersonId() > 0){
			try {
				person = getPeopleBaseService().findPerson(command.getPersonId());
								
				List<String> outputFields = new ArrayList<String>(5);
				outputFields.add("Doc ID");
				outputFields.add("Date");
				outputFields.add("Sender");
				outputFields.add("Recipient");
				outputFields.add("Volume/Folio");
				
				model.put("outputFields", outputFields);

				model.put("mapNameLf", person.getMapNameLf());
				model.put("SenderDocs", person.getSenderDocuments());
				model.put("RecipientDocs", person.getRecipientDocuments());
				Set<Document> personDocs = new HashSet<Document>();
				for(EpLink epLink : person.getEpLink()){
					personDocs.add(epLink.getDocument());
				}
				model.put("PersonDocs", personDocs);
				
			} catch (ApplicationThrowable ath) {
				new ModelAndView("error/ShowDocumentsPerson", model);
			}
		}

		return new ModelAndView("peoplebase/ShowDocumentsPerson", model);
	}

}