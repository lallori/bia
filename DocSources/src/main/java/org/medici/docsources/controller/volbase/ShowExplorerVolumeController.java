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
package org.medici.docsources.controller.volbase;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang.BooleanUtils;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.docsources.command.volbase.ShowExplorerVolumeCommand;
import org.medici.docsources.domain.Image;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.volbase.VolBaseService;
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
 * Controller for action "Show volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/volbase/ShowExplorerVolume")
public class ShowExplorerVolumeController {
	@Autowired(required = false)
	@Qualifier("showExplorerVolumeValidator")
	private Validator validator;
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * 
	 * @return
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView searchCarta(@Valid @ModelAttribute("command") ShowExplorerVolumeCommand command, BindingResult result){
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			// in case of errors we need to remove imageType and imageProgTypeNum, so we return imageOrder which is previous image.
			command.setImageType(null);
			command.setImageProgTypeNum(null);
			return setupForm(command, result);
		} else {
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
	
				model.put("volumeExplorer", volumeExplorer);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowExplorerVolume", model);
			}
	
			if (BooleanUtils.isTrue(command.getModalWindow())) {
				return new ModelAndView("volbase/ShowExplorerVolumeModalWindow", model);
			} else {
				return new ModelAndView("volbase/ShowExplorerVolume", model);
			}
		}
	}

	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowExplorerVolumeCommand command, BindingResult result){
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

			model.put("volumeExplorer", volumeExplorer);
		} catch (ApplicationThrowable ath) {
		}

		if (BooleanUtils.isTrue(command.getModalWindow())) {
			return new ModelAndView("volbase/ShowExplorerVolumeModalWindow", model);
		} else {
			return new ModelAndView("volbase/ShowExplorerVolume", model);
		}
	}

	/**
	 * 
	 * @param volBaseService
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}
}