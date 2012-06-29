/*
 * TranscribeAndContextualizeDocumentController.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.command.docbase.TranscribeAndContextualizeDocumentCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Transcribe And Contextualize Document" from baloon Volume Explorer.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/TranscribeAndContextualizeDocument")
public class TranscribeAndContextualizeDocumentController {
	@Autowired
	private DocBaseService docBaseService;

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
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
	public ModelAndView setupForm(@ModelAttribute("command") TranscribeAndContextualizeDocumentCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Month> months = null;

		try {
			months = getDocBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/ShowDocument", model);
		}

		Document document = new Document();

		try {
			document = getDocBaseService().constructDocumentToTranscribe(command.getImageDocumentToCreate(), command.getImageDocumentFolioStart());

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
			command.setDocYear(document.getDocYear());
			// we set first month which is empty
			command.setDocMonthNum(months.get(0).getMonthNum());
			command.setDocDay(document.getDocDay());
			command.setYearModern(document.getYearModern());
			command.setDateUns(document.getDateUns());
			//command.setDateUndated(document.getDa);
			command.setDateNotes(document.getDateNotes());

			model.put("document", document);
			model.put("fromTranscribe", Boolean.TRUE);

			return new ModelAndView("docbase/TranscribeAndContextualizeDocument", model);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/EditDetailsDocument", model);
		}

	}
}