/*
 * EditTopicDocumentController.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.docbase.EditTopicDocumentCommand;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EplToLink;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.TopicList;
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
 * Controller for action "Edit Topic Document". This controller manage editing
 * of a single topic linked to a document.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/EditTopicDocument")
public class EditTopicDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired(required = false)
	@Qualifier("editTopicDocumentValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditTopicDocumentCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			EplToLink eplToLink = new EplToLink(command.getEplToId());
			eplToLink.setDocument(new Document(command.getEntryId()));
			if (command.getTopicId() > 0) {
				eplToLink.setTopic(new TopicList(command.getTopicId()));
			} else {
				eplToLink.setTopic(null);
			}
			if (!ObjectUtils.toString(command.getPlaceDescription()).equals("")) {
				eplToLink.setPlace(new Place(command.getPlaceId())); 
			} else {
				eplToLink.setPlace(null); 
			}

			try {
				if (command.getEplToId().equals(0)) {
					getDocBaseService().addNewTopicDocument(eplToLink);
				} else {
					getDocBaseService().editTopicDocument(eplToLink);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditTopicDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<TopicList> topicsList = null;
		
		if ((command != null) && (command.getEntryId() > 0)) {

			if (command.getEplToId().equals(0)) {
				command.setPlaceDescription(null);
				command.setPlaceId(null);
				command.setTopicId(null);
			} else {
				try {
					EplToLink eplToLink = getDocBaseService().findTopicDocument(command.getEntryId(), command.getEplToId());

					if (eplToLink.getPlace() != null) {
						command.setPlaceDescription(eplToLink.getPlace().getPlaceNameFull());
						command.setPlaceId(eplToLink.getPlace().getPlaceAllId());
					} else {
						command.setPlaceDescription(null);
						command.setPlaceId(null);
					}

					if (eplToLink.getTopic() != null) {
						command.setTopicId(eplToLink.getTopic().getTopicId());
					}else {
						command.setTopicId(null);
					}
				} catch (ApplicationThrowable applicationThrowable) {
					return new ModelAndView("error/EditTopicDocument", model);
				}
			}
			
			try{
				topicsList = getDocBaseService().getTopicsList();
				model.put("topicsList", topicsList);
			}catch(ApplicationThrowable ath){
				return new ModelAndView("error/EditTopicDocument", model);
			}

			return new ModelAndView("docbase/EditTopicDocument", model);
		} else {
			// On Document creation, the research is always the current user.
			//command.setEntryId();
			if (ObjectUtils.toString(command).equals("")) {
				command = new EditTopicDocumentCommand();
			}
			command.setEplToId(null);
			command.setDateCreated(new Date());
			command.setTopicId(null);
			command.setPlaceDescription(null);
			command.setPlaceId(null);
		}

		return new ModelAndView("docbase/EditTopicDocument", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}