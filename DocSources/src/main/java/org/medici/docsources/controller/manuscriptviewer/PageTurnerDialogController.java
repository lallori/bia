/*
 * PageTurnerDialogController.java
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
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.manuscriptviewer.PageTurnerCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Image;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.manuscriptviewer.ManuscriptViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Page Turner Document Dialog".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/src/mview/PageTurnerDialog", "/de/mview/PageTurnerDialog"})
public class PageTurnerDialogController {
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") PageTurnerCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		Image image = null;

		try {
			// we set default image as empty string, so we need only to update the record.
			model.put("image", "");

			// If the request is made with entryId, we are asking a document
			if (!ObjectUtils.toString(command.getEntryId()).equals("")) {
				image = getManuscriptViewerService().findDocumentImage(command.getEntryId(), null, null, command.getImageType(), command.getImageProgTypeNum(), command.getImageOrder());
			} else {
				image = getManuscriptViewerService().findVolumeImage(command.getSummaryId(), command.getVolNum(), command.getVolLetExt(), command.getImageType(), command.getImageProgTypeNum(), command.getImageOrder());
			} 

			model.put("image", image);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
		}
		
		try {
			// We check if this image has a document linked...
			List<Document> documents = getManuscriptViewerService().findLinkedDocument(command.getVolNum(), command.getVolLetExt(), image);
			if(documents != null)
				model.put("entryId", documents.get(0).getEntryId());
			else
				model.put("entryId", null);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			model.put("entryId", null);
		}

		if (command.getModeEdit()) {
			model.put("caller", "/de/mview/EditDocumentInManuscriptViewer.do");
		} else {
			model.put("caller", "/src/mview/ShowDocumentInManuscriptViewer.do");
		}
		
		return new ModelAndView("mview/PageTurnerDialog", model);
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