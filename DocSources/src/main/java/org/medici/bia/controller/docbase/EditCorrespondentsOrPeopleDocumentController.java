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
package org.medici.bia.controller.docbase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.docbase.EditCorrespondentsOrPeopleDocumentCommand;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EpLink;
import org.medici.bia.domain.EplToLink;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/EditCorrespondentsOrPeopleDocument")
public class EditCorrespondentsOrPeopleDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
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
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
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
			Map<String, Object> model = new HashMap<String, Object>(0);
			
			Document document = new Document();
			document.setEntryId(command.getEntryId());

			if (!ObjectUtils.toString(command.getSenderPeopleId()).equals("") || !ObjectUtils.toString(command.getSenderPeopleDescription()).equals("")) {
				document.setSenderPeople(new People(command.getSenderPeopleId()));
			} else {
				document.setSenderPeople(new People(0));
			}
			document.getSenderPeople().setMapNameLf(command.getSenderPeopleDescription());
			if (ObjectUtils.toString(command.getSenderPeopleDescription()).equals("")) {
				document.setSenderPeopleUnsure(false);
			} else {
				document.setSenderPeopleUnsure(command.getSenderPeopleUnsure());
			}

			if (ObjectUtils.toString(command.getSenderPlaceDescription()).equals("")) {
				document.setSenderPlace(new Place(0));
				document.setSenderPlaceUnsure(false);
			} else {
				document.setSenderPlace(new Place(command.getSenderPlaceId()));
				document.setSenderPlaceUnsure(command.getSenderPlaceUnsure());
			}
			
			document.setSendNotes(command.getSendNotes());
			
			if (!ObjectUtils.toString(command.getRecipientPeopleId()).equals("") || !ObjectUtils.toString(command.getRecipientPeopleDescription()).equals("")) {
				document.setRecipientPeople(new People(command.getRecipientPeopleId()));
			} else {
				document.setRecipientPeople(new People(0));
			}
			document.getRecipientPeople().setMapNameLf(command.getRecipientPeopleDescription());
			if (ObjectUtils.toString(command.getRecipientPeopleDescription()).equals("")) {
				document.setRecipientPeopleUnsure(false);
			} else {
				document.setRecipientPeopleUnsure(command.getRecipientPeopleUnsure());
			}

			if (ObjectUtils.toString(command.getRecipientPlaceDescription()).equals("")) {
				document.setRecipientPlace(new Place(0));
				document.setRecipientPlaceUnsure(false);
			} else {
				document.setRecipientPlace(new Place(command.getRecipientPlaceId()));
				document.setRecipientPlaceUnsure(command.getRecipientPlaceUnsure());
			}
			
			document.setRecipNotes(command.getRecipNotes());
			
			try {
				document = getDocBaseService().editCorrespondentsDocument(document);
				
				List<EplToLink> topicsDocument = getDocBaseService().findTopicsDocument(document.getEntryId());
				model.put("topicsDocument", topicsDocument);
				
				HistoryNavigator historyNavigator = getDocBaseService().getHistoryNavigator(document);
				Image image = getManuscriptViewerService().findDocumentImageThumbnail(document);

				model.put("document", document);
				model.put("image", image);
				model.put("historyNavigator", historyNavigator);
				
				if(getDocBaseService().ifDocumentAlreadyPresentInMarkedList(document.getEntryId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditCorrespondentsOrPeopleDocument", model);
			}
			
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
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(
			ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditCorrespondentsOrPeopleDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getEntryId() > 0)) {
			Document document = new Document();

			try {
				document = getDocBaseService().findDocument(command.getEntryId());
				command.setDocument(document);
				
				// We set sender people in command object
				if (document.getSenderPeople() != null) {
					command.setSenderPeopleId(document.getSenderPeople().getPersonId());
					command.setSenderPeopleDescription(document.getSenderPeople().getMapNameLf());
				}
				command.setSenderPeopleUnsure(document.getSenderPeopleUnsure());
				
				// We set sender place in command object
				if (document.getSenderPlace() != null) {
					command.setSenderPlaceId(document.getSenderPlace().getPlaceAllId());
					command.setSenderPlaceDescription(document.getSenderPlace().getPlaceNameFull());
					
				}
				command.setSenderPlaceUnsure(document.getSenderPlaceUnsure());
				command.setSendNotes(document.getSendNotes());

				// We set recipient people in command object
				if (document.getRecipientPeople() != null) {
					command.setRecipientPeopleId(document.getRecipientPeople().getPersonId());
					command.setRecipientPeopleDescription(document.getRecipientPeople().getMapNameLf());
				}
				command.setRecipientPeopleUnsure(document.getRecipientPeopleUnsure());
				
				// We set recipient place in command object
				if (document.getRecipientPlace() != null) {
					command.setRecipientPlaceId(document.getRecipientPlace().getPlaceAllId());
					command.setRecipientPlaceDescription(document.getRecipientPlace().getPlaceNameFull());
				}
				command.setRecipientPlaceUnsure(document.getRecipientPlaceUnsure());
				command.setRecipNotes(document.getRecipNotes());

			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditCorrespondentsOrPeopleDocument", model);
			}

		} else {
			// On Document creation, sender is empty
			command.setSenderPeopleId(null);
			command.setSenderPeopleDescription("");
			command.setSenderPeopleUnsure(false);
			command.setSenderPlaceId(null);
			command.setSenderPlaceDescription("");
			command.setSenderPlacePrefered(null);
			command.setSenderPlaceUnsure(false);

			// On Document creation, recipient is empty
			command.setRecipientPeopleId(null);
			command.setRecipientPeopleDescription("");
			command.setRecipientPeopleUnsure(false);
			command.setRecipientPlaceId(null);
			command.setRecipientPlaceDescription("");
			command.setRecipientPlaceUnsure(false);

			command.setDocument(new Document(command.getEntryId()));
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