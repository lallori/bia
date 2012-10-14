/*
 * CompareDocumentController.java
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

import org.medici.bia.command.docbase.CompareDocumentRequestCommand;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Compare Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/docbase/CompareDocument")
public class CompareDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
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
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView processSubmit(@ModelAttribute("requestCommand") CompareDocumentRequestCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			// Details
			Document document = getDocBaseService().compareDocument(command.getEntryId());
			model.put("document", document);
	
			Image image = getManuscriptViewerService().findDocumentImageThumbnail(document);
			model.put("image", image);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/CompareDocument", model);
		}

		return new ModelAndView("docbase/CompareDocument", model);
	}

	/**
	 * 
	 * @param docBaseService
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
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