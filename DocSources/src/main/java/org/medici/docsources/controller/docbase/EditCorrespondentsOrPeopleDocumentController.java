/*
 * EditCorrespondentsOrPeopleDocumentController.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.Valid;

import org.medici.docsources.command.docbase.EditCorrespondentsOrPeopleDocumentCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EpLink;
import org.medici.docsources.domain.EplToLink;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
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
 * Controller for action "Edit Correspondents Or People Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/EditCorrespondentsOrPeopleDocument")
public class EditCorrespondentsOrPeopleDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired(required = false)
	@Qualifier("editCorrespondentsOrPeopleDocumentValidator")
	private Validator validator;

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * This method returns the Validator class used by Controller to make
	 * business validation.
	 * 
	 * @return
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditCorrespondentsOrPeopleDocumentCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();
			
			Document document = new Document();
			document.setEntryId(command.getEntryId());
			//document.setSeCcontext(command.getCcontext());

			try {
				getDocBaseService().editCorrespondentsOrPeopleDocument(document);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/ShowDocument", model);
			}
			
			model.put("document", document);

			return new ModelAndView("docbase/ShowDocument", model);
		}
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditCorrespondentsOrPeopleDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		if ((command != null) && (command.getEntryId() > 0)) {
			Document document = new Document();

			try {
				document = getDocBaseService().findDocument(command.getEntryId());
				command.setDocument(document);
				
				// We set sender people in command object
				command.setSenderPeopleId(document.getSenderPeople().getPersonId());
				command.setSenderPeopleDescription(document.getSenderPeople().getMapNameLf());
				command.setSenderPeopleUnsure(document.getSenderPeopleUnsure());
				
				// We set sender place in command object
				command.setSenderPlaceId(document.getSenderPlace().getPlaceAllId());
				command.setSenderPlaceDescription(document.getSenderPlace().getPlaceNameFull());
				command.setSenderPlaceUnsure(document.getSenderPlaceUnsure());

				// We set recipient people in command object
				command.setRecipientPeopleId(document.getRecipientPeople().getPersonId());
				command.setRecipientPeopleDescription(document.getRecipientPeople().getMapNameLf());
				command.setRecipientPeopleUnsure(document.getRecipientPeopleUnsure());
				
				// We set recipient place in command object
				command.setRecipientPlaceId(document.getRecipientPlace().getPlaceAllId());
				command.setRecipientPlaceDescription(document.getRecipientPlace().getPlaceNameFull());
				command.setRecipientPlaceUnsure(document.getRecipientPlaceUnsure());

			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditCorrespondentsOrPeopleDocument", model);
			}

		} else {
			// On Document creation, sender is empty
			command.setSenderPeopleId(null);
			command.setSenderPeopleDescription("");
			command.setSenderPeopleUnsure(false);
			command.setSenderPlaceId(null);
			command.setSenderPlaceDescription("");
			command.setSenderPlaceUnsure(false);

			// On Document creation, recipient is empty
			command.setRecipientPeopleId(null);
			command.setRecipientPeopleDescription("");
			command.setRecipientPeopleUnsure(false);
			command.setRecipientPlaceId(null);
			command.setRecipientPlaceDescription("");
			command.setRecipientPlaceUnsure(false);

			// On Document creation, linked peoples to document are empty
			Document document = new Document(command.getEntryId());
			document.setEpLink(new HashSet<EpLink>());
			command.setDocument(document);
		}

		return new ModelAndView("docbase/EditCorrespondentsOrPeopleDocument", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}