/*
 * ShowVolumeController.java
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
package org.medici.docsources.controller.volbase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.docsources.command.volbase.ShowVolumeRequestCommand;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.medici.docsources.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/src/volbase/ShowVolume")
public class ShowVolumeController {
	@Autowired
	private VolBaseService volBaseService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;

	/**
	 * 
	 * @return
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
 	 * This method extracts volume identified by param command.summaryId,
 	 * and invoke view ShowVolume to render Volume information on client.
 	 * 
	 * @param command Object containing input parameters
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowVolumeRequestCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		Volume volume = new Volume();

		if (command.getSummaryId() > 0) {
			try {
				volume = getVolBaseService().findVolume(command.getSummaryId());

				model.put("volDocsRelated", getVolBaseService().findVolumeDocumentsRelated(volume.getSummaryId()));
				Image image = getManuscriptViewerService().findVolumeImageSpine(volume.getVolNum(), volume.getVolLetExt());
				model.put("image", image);

				model.put("historyNavigator", getVolBaseService().getHistoryNavigator(volume));
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/ShowVolume", model);
			}
		} else {
			//SummaryId equals to zero is 'New Volume'
			volume.setSummaryId(0);
			volume.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			volume.setDateCreated(new Date());
			model.put("volDocsRelated", 0);
		}

		model.put("volume", volume);
		return new ModelAndView("volbase/ShowVolume", model);
	}

	/**
	 * 
	 * @param volBaseService
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
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