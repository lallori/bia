/*
 * AddMarkedListDocumentController.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.docsources.command.docbase.AddMarkedListDocumentCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.UserMarkedList;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.usermarkedlist.UserMarkedListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Add marked list document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/docbase/AddMarkedListDocument")
public class AddMarkedListDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private UserMarkedListService userMarkedListService;
	@Autowired(required = false)
	@Qualifier("addMarkedListDocumentValidator")
	private Validator validator;

	/**
	 * 
	 * @return
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * 
	 * @param documentId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView processSubmit(@ModelAttribute("command") AddMarkedListDocumentCommand command, BindingResult result) {
		getValidator().validate(command, result);
		
		Map<String, Object> model = new HashMap<String, Object>();
		if(!result.hasErrors()){		
			Document document = new Document();
			
			if(command.getEntryId() > 0){
				try {
					// Details
					UserMarkedList userMarkedList = getUserMarkedListService().getMyMarkedList();
					if(userMarkedList == null){
						userMarkedList = new UserMarkedList();
						userMarkedList.setDateCreated(new Date());
						userMarkedList = getUserMarkedListService().createMyMarkedList(userMarkedList);
					}
					
					document = getDocBaseService().findDocument(command.getEntryId());
					userMarkedList = getUserMarkedListService().addNewDocumentToMarkedList(userMarkedList, document);
					
					model.put("document", document);
		
				} catch (ApplicationThrowable applicationThrowable) {
					model.put("applicationThrowable", applicationThrowable);
					return new ModelAndView("response/MarkedListKO", model);
				}
			}
		}	
		model.put("category", "document");
	
		return new ModelAndView("response/MarkedListOK", model);
		
	}

	/**
	 * 
	 * @param docBaseService
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * @return the userMarkedListService
	 */
	public UserMarkedListService getUserMarkedListService() {
		return userMarkedListService;
	}

	/**
	 * @param userMarkedListService the userMarkedListService to set
	 */
	public void setUserMarkedListService(UserMarkedListService userMarkedListService) {
		this.userMarkedListService = userMarkedListService;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}