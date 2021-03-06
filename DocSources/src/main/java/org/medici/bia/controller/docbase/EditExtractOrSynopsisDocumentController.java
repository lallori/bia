/*
 * EditExtractOrSynopsisDocumentController.java
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

import org.apache.commons.lang.BooleanUtils;
import org.medici.bia.command.docbase.EditExtractOrSynopsisDocumentCommand;
import org.medici.bia.common.util.BiaTextUtils;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.SynExtract;
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
 * Controller for action "Edit Extract Or Synopsis Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/EditExtractOrSynopsisDocument")
public class EditExtractOrSynopsisDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired(required = false)
	@Qualifier("editExtractOrSynopsisDocumentValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditExtractOrSynopsisDocumentCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			SynExtract synExtract = new SynExtract(command.getSynExtrId());
			synExtract.setDocument(new Document(command.getEntryId()));
			synExtract.setSynopsis(command.getSynopsis());
			synExtract.setDocExtract(BiaTextUtils.normalize(command.getDocExtract()));
			synExtract.setDocumentBibliography(BiaTextUtils.normalize(command.getDocumentBibliography()));
			synExtract.setSynopsis(BiaTextUtils.normalize(command.getSynopsis()));

			try {
				Document document = null;

				if (command.getSynExtrId().equals(0)) {
					document = getDocBaseService().addNewExtractOrSynopsisDocument(synExtract);
				} else {
					document = getDocBaseService().editExtractOrSynopsisDocument(synExtract);
				}

				model.put("document", document);
				
				if(getDocBaseService().ifDocumentAlreadyPresentInMarkedList(document.getEntryId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}
				
				return new ModelAndView("docbase/ShowExtractOrSynopsisDocument", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditExtractOrSynopsisDocument", model);
			}
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
	public ModelAndView setupForm(@ModelAttribute("command") EditExtractOrSynopsisDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getEntryId() > 0)) {
			SynExtract synExtract = new SynExtract();

			try {
				synExtract = getDocBaseService().findSynExtractDocument(command.getEntryId());
				
				if (synExtract != null) {
					command.setSynExtrId(synExtract.getSynExtrId());
					command.setDocExtract(synExtract.getDocExtract());
					command.setDocumentBibliography(synExtract.getDocumentBibliography());
					command.setSynopsis(synExtract.getSynopsis());
				} else {
					command.setSynExtrId(0);
					command.setDocExtract(null);
					command.setDocumentBibliography(null);
					command.setSynopsis(null);
				}
				command.setDocument(getDocBaseService().findDocument(command.getEntryId()));
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditDetailsDocument", model);
			}
		} else {
			// On Document creation, the research is always the current user.
			command.setSynExtrId(0);
			command.setDocExtract(null);
			command.setSynopsis(null);
			command.setDocumentBibliography(null);
		}

		if (BooleanUtils.isTrue(command.getModalWindow())) {
			return new ModelAndView("docbase/EditExtractOrSynopsisDocumentModalWindow", model);
		} else {
			return new ModelAndView("docbase/EditExtractOrSynopsisDocument", model);
		}
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}