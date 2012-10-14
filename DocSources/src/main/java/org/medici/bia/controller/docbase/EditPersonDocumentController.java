/*
 * EditPersonDocumentController.java
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
import java.util.Map;

import javax.validation.Valid;

import org.medici.bia.command.docbase.EditPersonDocumentCommand;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EpLink;
import org.medici.bia.domain.People;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
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
 * Controller for action "Edit Person Document". This Controller permits
 * link a person to document (section Edit Correspondets Or People). 
 * Attention : this not manage Sender or Recipient Person, they
 * are manage directly by org.medici.bia.controller.docbase.EditCorrespondetsOrPeopleDocumentController  
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/EditPersonDocument")
public class EditPersonDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired(required = false)
	@Qualifier("editPersonDocumentValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditPersonDocumentCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			EpLink epLink = new EpLink(command.getEpLinkId());
			epLink.setAssignUnsure(command.getAssignUnsure());
			epLink.setPortrait(command.getPortrait());
			epLink.setPerson(new People(command.getPersonId()));
			epLink.setDocument(new Document(command.getEntryId()));

			try {
				if (command.getEpLinkId().equals(0)) {
					getDocBaseService().addNewPersonDocument(epLink);
				} else {
					getDocBaseService().editPersonDocument(epLink);
				}

			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowDocument", model);
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
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditPersonDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getEntryId() > 0)) {

			if (command.getEpLinkId().equals(0)) {
				command.setPersonId(null);
				command.setPersonDescription("");
				command.setPortrait(false);
				command.setAssignUnsure(false);
			} else {
				try {
					EpLink epLink = getDocBaseService().findPersonDocument(command.getEntryId(), command.getEpLinkId());
					
					command.setPersonId(epLink.getPerson().getPersonId());
					command.setPersonDescription(epLink.getPerson().getMapNameLf());
					command.setAssignUnsure(epLink.getAssignUnsure());
					command.setPortrait(epLink.getPortrait());
				} catch (ApplicationThrowable applicationThrowable) {
					model.put("applicationThrowable", applicationThrowable);
					return new ModelAndView("error/EditPersonDocument", model);
				}
			}

			return new ModelAndView("docbase/EditPersonDocument", model);
		}

		return new ModelAndView("docbase/EditPersonDocument", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}