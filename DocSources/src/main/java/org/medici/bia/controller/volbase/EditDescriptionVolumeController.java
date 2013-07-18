/*
 * EditDescriptionVolumeController.java
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

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.bia.command.volbase.EditDescriptionVolumeCommand;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.volbase.VolBaseService;
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
 * Controller for action "Edit Description Volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/volbase/EditDescriptionVolume")
public class EditDescriptionVolumeController {
	@Autowired(required = false)
	@Qualifier("editDescriptionVolumeValidator")
	private Validator validator;
	@Autowired
	private VolBaseService volBaseService;

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
	 * @return the volBaseService
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditDescriptionVolumeCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);
			Volume volume = new Volume();
			volume.setSummaryId(command.getSummaryId());
			volume.setOrgNotes(command.getOrgNotes());
			volume.setCcondition(command.getCcondition());
			volume.setBound(command.getBound());
			volume.setFolioCount(command.getFolioCount());
			volume.setFolsNumbrd(command.getFolsNumbrd());
			volume.setOldAlphaIndex(command.getOldAlphaIndex());
			volume.setPrintedMaterial(command.getPrintedMaterial());
			volume.setPrintedDrawings(command.getPrintedDrawings());
			volume.setItalian(command.getItalian());
			volume.setSpanish(command.getSpanish());
			volume.setEnglish(command.getEnglish());
			volume.setLatin(command.getLatin());
			volume.setGerman(command.getGerman());
			volume.setFrench(command.getFrench());
			volume.setOtherLang(command.getOtherLang());
			volume.setCipher(command.getCipher());
			volume.setCipherNotes(command.getCipherNotes());

			try {
				volume = getVolBaseService().editDescriptionVolume(volume);
				model.put("volume", volume);
				
				if(getVolBaseService().ifVolumeAlreadyPresentInMarkedList(volume.getSummaryId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}
				
				return new ModelAndView("volbase/ShowDescriptionVolume", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowVolume", model);
			}
			
		}

	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditDescriptionVolumeCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getSummaryId() > 0)) {
			Volume volume = new Volume();

			try {
				volume = getVolBaseService().findVolume(command.getSummaryId());
				model.put("volume", volume);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditDetailsVolume", model);
			}

			try {
				BeanUtils.copyProperties(command, volume);
			} catch (IllegalAccessException iaex) {
			} catch (InvocationTargetException itex) {
			}
		}

		return new ModelAndView("volbase/EditDescriptionVolume", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @param volBaseService
	 *            the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}