/*
 * EditExtractDocumentDialogController.java
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
package org.medici.docsources.controller.manuscriptviewer;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.medici.docsources.command.manuscriptviewer.EditExtractDocumentDialogCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.SynExtract;
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
 * Controller for action "Edit Extract Document Dialog".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/mview/EditExtractDocumentDialog")
public class EditExtractDocumentDialogController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired(required = false)
	@Qualifier("editExtractDocumentDialogValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditExtractDocumentDialogCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			SynExtract synExtract = new SynExtract(command.getSynExtrId());
			synExtract.setDocument(new Document(command.getEntryId()));
			synExtract.setDocExtract(command.getDocExtract());

			try {
				Document document = null;

				if (command.getSynExtrId().equals(0)) {
					document = getDocBaseService().addNewExtractOrSynopsisDocument(synExtract);
				} else {
					document = getDocBaseService().editExtractDocument(synExtract);
				}

				command.setSynExtrId(document.getSynExtract().getSynExtrId());
				command.setDocExtract(document.getSynExtract().getDocExtract());
				return new ModelAndView("manuscriptviewer/EditExtractDocumentDialog", model);
			} catch (ApplicationThrowable ath) {
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
	public ModelAndView setupForm(@ModelAttribute("command") EditExtractDocumentDialogCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		if ((command != null) && (command.getEntryId() > 0)) {
			SynExtract synExtract = new SynExtract();

			try {
				synExtract = getDocBaseService().findSynExtractDocument(command.getEntryId());
				
				if (synExtract != null) {
					command.setSynExtrId(synExtract.getSynExtrId());
					command.setDocExtract(synExtract.getDocExtract());
				} else {
					command.setSynExtrId(null);
					command.setDocExtract(null);
				}
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditExtractDocumentDialog", model);
			}
		} else {
			// On Document creation, the research is always the current user.
			command.setSynExtrId(null);
			command.setDocExtract(null);
		}

		return new ModelAndView("manuscriptviewer/EditExtractDocumentDialog", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
}