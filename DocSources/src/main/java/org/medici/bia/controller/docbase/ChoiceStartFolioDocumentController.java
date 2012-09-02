/*
 * ChoiceStartFolioDocumentController.java
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

import javax.servlet.http.HttpSession;

import org.medici.bia.command.docbase.ChoiceStartFolioDocumentRequestCommand;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.domain.Image;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Choice Start Folio" in Document Creation.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/docbase/ChoiceStartFolioDocument")
public class ChoiceStartFolioDocumentController {
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ChoiceStartFolioDocumentRequestCommand command, BindingResult result, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		VolumeExplorer volumeExplorer = new VolumeExplorer(command.getSummaryId(), command.getVolNum(), command.getVolLetExt());
		volumeExplorer.setImage(new Image());
		volumeExplorer.getImage().setImageProgTypeNum(command.getImageProgTypeNum());
		volumeExplorer.getImage().setImageOrder(command.getImageOrder());
		volumeExplorer.getImage().setImageType(command.getImageType());
		volumeExplorer.setTotal(command.getTotal());
		volumeExplorer.setTotalRubricario(command.getTotalRubricario());
		volumeExplorer.setTotalCarta(command.getTotalCarta());
		volumeExplorer.setTotalAppendix(command.getTotalAppendix());
		volumeExplorer.setTotalOther(command.getTotalOther());
		volumeExplorer.setTotalGuardia(command.getTotalGuardia());

		try {
			volumeExplorer = getVolBaseService().getVolumeExplorer(volumeExplorer);

			Image imageToCreate = getDocBaseService().findImage(command.getImageDocumentToCreate());

			model.put("volumeExplorer", volumeExplorer);
			model.put("imageToCreate", imageToCreate);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ChoiceStartFolioModalWindow", model);
		}

		return new ModelAndView("docbase/ChoiceStartFolioModalWindow", model);
	}

	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}