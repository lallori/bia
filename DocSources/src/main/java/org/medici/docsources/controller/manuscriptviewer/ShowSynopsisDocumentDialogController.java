/*
 * ShowSynopsisDocumentDialogController.java
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

import org.medici.docsources.command.manuscriptviewer.ShowSynopsisDocumentDialogCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.SynExtract;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Synopsis Document Dialog".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/mview/ShowSynopsisDocumentDialog")
public class ShowSynopsisDocumentDialogController {
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
	public ModelAndView setupForm(@ModelAttribute("command") ShowSynopsisDocumentDialogCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		if ((command != null) && (command.getEntryId() > 0)) {
			SynExtract synExtract = new SynExtract();

			try {
				synExtract = getDocBaseService().findSynExtractDocument(command.getEntryId());
				Document document = getDocBaseService().findDocument(command.getEntryId());
				
				if (synExtract != null) {
					model.put("synopsis", synExtract.getSynopsis());
				} else {
					model.put("synopsis", null);
				}
				
				model.put("folioNum", document.getFolioNum());
				model.put("volNum", document.getVolume().getVolNum());
				model.put("entryId", document.getEntryId());
			} catch (ApplicationThrowable ath) {
				model.put("applicationThrowable", ath);
				return new ModelAndView("error/ShowSynopsisDocumentDialog", model);
			}
		} else {
			// On Document creation, the research is always the current user.
			model.put("synopsis", null);
		}

		return new ModelAndView("mview/ShowSynopsisDocumentDialog", model);
	}

}