/*
 * ShowExplorerVolume.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.manuscriptviewer.ShowManuscriptViewerRequestCommand;
import org.medici.docsources.domain.Image;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/ShowManuscriptViewer")
public class ShowManuscriptViewerController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * 
	 * @return
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowManuscriptViewerRequestCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Image> images = new ArrayList<Image>();
		
		try {
			// we set default image as empty string, so we need only to update the record.
			model.put("image", "");

			//If the request is made with imageName, we don't need to query database
			if (command.getImageName() != null) {
				model.put("image", command.getImageName());
			} else  {
				if (!ObjectUtils.toString(command.getEntryId()).equals("")) {
					images = getDocBaseService().findDocumentImages(command.getEntryId());
				} else if (!ObjectUtils.toString(command.getSummaryId()).equals("")) {
					images = getVolBaseService().findVolumeImages(command.getSummaryId());
				} else {
					images = getVolBaseService().findVolumeImages(command.getVolNum(), command.getVolLeText());
				}

				if ((images != null) && (images.size() >0)) {
					model.put("image", images.get(0).toString());
				}
			}
		} catch (ApplicationThrowable ath) {
		}

		if (command.getFlashVersion())
			return new ModelAndView("mview/ShowManuscriptViewerFlash", model);
		else
			return new ModelAndView("mview/ShowManuscriptViewerHtml", model);
	}

	/**
	 * 
	 * @param volBaseService
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}