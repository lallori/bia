/*
 * EditTiffImagesSchedoneController.java
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
package org.medici.bia.controller.digitization;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.medici.bia.command.digitization.EditTiffImagesSchedoneCommand;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.Schedone.Formato;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.digitization.DigitizationService;
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
 * Controller for action "Edit Details Schedone".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/digitization/EditTiffImagesSchedone")
public class EditTiffImagesSchedoneController {
	@Autowired
	private DigitizationService digitizationService;

	@Autowired(required = false)
	@Qualifier("editTiffImagesSchedoneValidator")
	private Validator validator;


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
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditTiffImagesSchedoneCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>(0);

			Schedone schedone = new Schedone(command.getSchedoneId());
			schedone.setDimMediaImmaginiTiff(command.getDimMediaImmaginiTiff());
			schedone.setDimTotaleImmaginiTiff(command.getDimTotaleImmaginiTiff());
			schedone.setNumeroTotaleImmaginiTiff(command.getNumeroTotaleImmaginiTiff());
			schedone.setCompressioneTiff(command.getCompressioneTiff());
			schedone.setFormatoMediaImmaginiTiff(command.getFormatoMediaImmaginiTiff());
			schedone.setFormatoTotaleImmaginiTiff(command.getFormatoTotaleImmaginiTiff());

			try {
				schedone = getDigitizationService().editTiffImagesSchedone(schedone);
				model.put("schedone", schedone);

				return new ModelAndView("digitization/ShowSchedone", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/EditPdfImagesSchedone", model);
			}
		}
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditTiffImagesSchedoneCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		if ((command != null) && (command.getSchedoneId() > 0)) {
			Schedone schedone = new Schedone();

			try {
				schedone = getDigitizationService().findSchedone(command.getSchedoneId());
				
				model.put("formato", Schedone.Formato.values());
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditPdfImagesSchedone", model);
			} finally {
				model.put("schedone", schedone);
			}

			command.setSchedoneId(schedone.getSchedoneId());
			command.setNumeroTotaleImmaginiTiff(schedone.getNumeroTotaleImmaginiTiff());
			command.setDimMediaImmaginiTiff(schedone.getDimMediaImmaginiTiff());
			command.setDimTotaleImmaginiTiff(schedone.getDimTotaleImmaginiTiff());
			command.setFormatoMediaImmaginiTiff(schedone.getFormatoMediaImmaginiTiff());
			command.setFormatoTotaleImmaginiTiff(schedone.getFormatoTotaleImmaginiTiff());
			if(schedone.getCompressioneTiff() != null){
				command.setCompressioneTiff(schedone.getCompressioneTiff());
			}else{
				command.setCompressioneTiff("1:1");
			}
		} else {
			command.setSchedoneId(0);
			command.setNumeroTotaleImmaginiTiff(null);
			command.setDimMediaImmaginiTiff(null);
			command.setDimTotaleImmaginiTiff(null);
			command.setCompressioneTiff("1:1");
			command.setFormatoMediaImmaginiTiff(Formato.MB);
			command.setFormatoTotaleImmaginiTiff(Formato.GB);
		}

		return new ModelAndView("digitization/EditTiffImagesSchedone", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @param digitizationService the digitizationService to set
	 */
	public void setDigitizationService(DigitizationService digitizationService) {
		this.digitizationService = digitizationService;
	}

	/**
	 * @return the digitizationService
	 */
	public DigitizationService getDigitizationService() {
		return digitizationService;
	}
}
