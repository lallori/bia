/*
 * EditDetailsDocumentController.java
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
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.docsources.command.docbase.EditDetailsDocumentCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EplToLink;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.manuscriptviewer.ManuscriptViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Edit Details Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/EditDetailsDocument")
public class EditDetailsDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
	@Autowired(required = false)
	@Qualifier("editDetailsDocumentValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditDetailsDocumentCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Document document = new Document(command.getEntryId());
			document.setResearcher(command.getResearcher());
			document.setVolume(new Volume(command.getVolume()));
			// Insert/Part: 
			document.setInsertNum(command.getInsertNum());
			document.setInsertLet(command.getInsertLet());
			// Folio Start:
			document.setFolioNum(command.getFolioNum());
			document.setFolioMod(command.getFolioMod().toString());
			// Transcribe Folio Start:
			document.setTranscribeFolioNum(command.getTranscribeFolioNum());
			document.setTranscribeFolioMod(command.getTranscribeFolioMod().toString());
			// Paginated
			if(command.getUnpaged() == null){
				document.setUnpaged(Boolean.FALSE);
			}else{
				document.setUnpaged(command.getUnpaged());
			}
			//Disc. Cont'd
			document.setContDisc(command.getContDisc());
			//Document Typology
			document.setDocTypology(command.getDocTypology());
			// Date
			document.setDocYear(command.getDocYear());
			document.setDocMonthNum((command.getDocMonthNum() != null) ? new Month(command.getDocMonthNum()) : null);
			document.setDocDay(command.getDocDay());
			//Modern Dating
			document.setYearModern(command.getYearModern());
			// Date Uncertain or Approximate
			document.setDateUns(command.getDateUns());
			// Undated
			document.setUndated(command.getDateUndated());
			document.setDateNotes(command.getDateNotes());

			try {
				if (command.getEntryId().equals(0)) {
					document = getDocBaseService().addNewDocument(document);
					model.put("document", document);

					Image image = getManuscriptViewerService().findDocumentImageThumbnail(document);
					model.put("image", image);
					
					HistoryNavigator historyNavigator = getDocBaseService().getHistoryNavigator(document);
					model.put("historyNavigator", historyNavigator);

					return new ModelAndView("docbase/ShowDocument", model);
				} else {
					document = getDocBaseService().editDetailsDocument(document);
					model.put("document", document);
					
					List<EplToLink> topicsDocument = getDocBaseService().findTopicsDocument(document.getEntryId());
					model.put("topicsDocument", topicsDocument);

					Image image = getManuscriptViewerService().findDocumentImageThumbnail(document);
					model.put("image", image);
					
					HistoryNavigator historyNavigator = getDocBaseService().getHistoryNavigator(document);
					model.put("historyNavigator", historyNavigator);
					
					return new ModelAndView("docbase/ShowDocument", model);
				}
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsDocument", model);
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
	public ModelAndView setupForm(@ModelAttribute("command") EditDetailsDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Month> months = null;

		try {
			months = getDocBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowDocument", model);
		}

		if ((command != null) && (command.getEntryId() > 0)) {
			Document document = new Document();

			try {
				document = getDocBaseService().findDocument(command.getEntryId());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsDocument", model);
			} finally {
				model.put("document", document);
			}

			command.setResearcher(document.getResearcher());
			command.setDateCreated(document.getDateCreated());
			command.setEntryId(document.getEntryId());
			command.setVolume(document.getVolume().toString());
			command.setInsertNum(document.getInsertNum());
			command.setInsertLet(document.getInsertLet());
			command.setFolioNum(document.getFolioNum());
			command.setFolioMod(document.getFolioMod());
			command.setTranscribeFolioNum(document.getTranscribeFolioNum());
			command.setTranscribeFolioMod(document.getTranscribeFolioMod());
			command.setUnpaged(document.getUnpaged());
			command.setContDisc(document.getContDisc());
			command.setDocTypology(document.getDocTypology());
			command.setDocYear(document.getDocYear());
			command.setDocMonthNum((document.getDocMonthNum() != null) ? document.getDocMonthNum().getMonthNum(): null);
			command.setDocDay(document.getDocDay());
			command.setYearModern(document.getYearModern());
			command.setDateUns(document.getDateUns());
			command.setDateUndated(document.getUndated());
			command.setDateNotes(document.getDateNotes());
		} else {
			// On Document creation, the research is always the current user.
			command.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			command.setDateCreated(new Date());
			command.setEntryId(0);
			command.setInsertNum(null);
			command.setInsertLet(null);
			command.setFolioNum(null);
			command.setFolioMod(null);
			// Paginated
			command.setUnpaged(false);
			//Disc. Cont'd
			command.setContDisc(false);
			// Date
			command.setDocYear(null);
			// Empty month is in last positizion
			command.setDocMonthNum(null);
			command.setDocDay(null);
			//Modern Dating
			command.setYearModern(null);
			// Date Uncertain or Approximate?
			command.setDateUns(false);
			// Undated 
			command.setDateUndated(false);
			command.setDateNotes(null);
		}

		return new ModelAndView("docbase/EditDetailsDocument", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}

	/**
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
	}
}