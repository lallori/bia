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
package org.medici.bia.controller.volbase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.bia.command.volbase.ShowVolumeRequestCommand;
import org.medici.bia.common.volume.VolumeSummary;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.bia.service.volbase.VolBaseService;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
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
		Map<String, Object> model = new HashMap<String, Object>(0);
		Volume volume = new Volume();

		if (command.getSummaryId() > 0) {
			try {
				volume = getVolBaseService().findVolume(command.getSummaryId());
				
				VolumeSummary volumeSummary = getVolBaseService().findVolumeSummary(volume);
				model.put("volumeSummary", volumeSummary);
				
				Schedone schedone = getVolBaseService().findSchedone(volume.getVolNum(), volume.getVolLetExt());
				model.put("schedone", schedone);

				model.put("volDocsRelated", getVolBaseService().findVolumeDocumentsRelated(volume.getSummaryId()));
				if (volume.getDigitized()) {
				Image image = getManuscriptViewerService().findVolumeImageSpine(volume.getVolNum(), volume.getVolLetExt());
					if (image != null) {
						model.put("spine", image);
					} else {
						image = getManuscriptViewerService().findVolumeImage(command.getSummaryId(), null, null, null, null, 1);
						model.put("image", image);
					}
				}

				model.put("historyNavigator", getVolBaseService().getHistoryNavigator(volume));
				
				if(getVolBaseService().ifVolumeAlreadyPresentInMarkedList(volume.getSummaryId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowVolume", model);
			}
		} else {
			//SummaryId equals to zero is 'New Volume'
			volume.setSummaryId(0);
			volume.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
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