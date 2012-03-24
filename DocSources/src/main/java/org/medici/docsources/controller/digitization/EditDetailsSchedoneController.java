/*
 * EditDetailsSchedoneController.java
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
package org.medici.docsources.controller.digitization;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.medici.docsources.command.digitization.EditDetailsSchedoneCommand;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.digitization.DigitizationService;
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
 */
@Controller
@RequestMapping("/digitization/EditDetailsSchedone")
public class EditDetailsSchedoneController {
	@Autowired
	private DigitizationService digitizationService;

	@Autowired(required = false)
	@Qualifier("editDetailsSchedoneValidator")
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
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") EditDetailsSchedoneCommand command, BindingResult result) {
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			Schedone schedone = new Schedone(command.getSchedoneId());
			schedone.setIstituto(command.getIstituto());
			schedone.setFondo(command.getFondo());
			schedone.setSerie(command.getSerie());
			schedone.setNumeroUnita(command.getNumeroUnita());
			schedone.setDataInizioAnno(command.getDataInizioAnno());
			schedone.setDataInizioMese((command.getDataInizioMese() != null) ? new Month(command.getDataInizioMese()) : null);
			schedone.setDataInizioGiorno(command.getDataInizioGiorno());
			schedone.setDataFineAnno(command.getDataFineAnno());
			schedone.setDataFineMese((command.getDataFineMese() != null) ? new Month(command.getDataFineMese()) : null);
			schedone.setDataFineGiorno(command.getDataFineGiorno());
			schedone.setDescrizioneContenuto(command.getDescrizioneContenuto());
			schedone.setDescrizioneContenutoEng(command.getDescrizioneContenutoEng());
			schedone.setLegatura(command.getLegatura());
			schedone.setSupporto(command.getSupporto());
			schedone.setCartulazione(command.getCartulazione());
			schedone.setNoteCartulazione(command.getNoteCartulazione());
			schedone.setNoteCartulazioneEng(command.getNoteCartulazioneEng());
			schedone.setCarteBianche(command.getCarteBianche());
			schedone.setCarteMancanti(command.getCarteMancanti());
			schedone.setDimensioniBase(command.getDimensioniBase());
			schedone.setDimensioniAltezza(command.getDimensioniAltezza());
			schedone.setTipoRipresa(command.getTipoRipresa());
			schedone.setColoreImmagine(command.getColoreImmagine());
			schedone.setRisoluzione(command.getRisoluzione());
			schedone.setNomeFiles(command.getNomeFiles());
			schedone.setResponsabileFotoRiproduzione(command.getResponsabileFotoRiproduzione());
			schedone.setDataRipresaAnno(command.getDataRipresaAnno());
			schedone.setDataRipresaMese((command.getDataRipresaMese() != null) ? new Month(command.getDataRipresaMese()) : null);
			schedone.setDataRipresaGiorno(command.getDataRipresaGiorno());
			schedone.setOperatore(command.getOperatore());

			try {
				if (command.getSchedoneId().equals(0)) {
					schedone = getDigitizationService().addNewSchedone(schedone);
					model.put("schedone", schedone);

					return new ModelAndView("digitization/ShowSchedone", model);
				} else {
					schedone = getDigitizationService().editDetailsSchedone(schedone);
					model.put("schedone", schedone);
					
					return new ModelAndView("digitization/ShowSchedone", model);
				}
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditDetailsSchedone", model);
			}
		}
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") EditDetailsSchedoneCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<Month> months = null;

		try {
			months = getDigitizationService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/EditDetailsSchedone", model);
		}

		if ((command != null) && (command.getSchedoneId() > 0)) {
			Schedone schedone = new Schedone();

			try {
				schedone = getDigitizationService().findSchedone(command.getSchedoneId());
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("error/EditDetailsSchedone", model);
			} finally {
				model.put("schedone", schedone);
			}

			command.setIstituto(schedone.getIstituto());
			command.setFondo(schedone.getFondo());
			command.setSerie(schedone.getSerie());
			command.setNumeroUnita(schedone.getNumeroUnita());
			command.setDataInizioAnno(schedone.getDataInizioAnno());
			command.setDataInizioMese((schedone.getDataInizioMese() != null) ? schedone.getDataInizioMese().getMonthNum() : null);
			command.setDataInizioGiorno(schedone.getDataInizioGiorno());
			command.setDataFineAnno(schedone.getDataFineAnno());
			command.setDataFineMese((schedone.getDataFineMese() != null) ? schedone.getDataFineMese().getMonthNum() : null);
			command.setDataFineGiorno(schedone.getDataFineGiorno());
			command.setDescrizioneContenuto(schedone.getDescrizioneContenuto());
			command.setDescrizioneContenutoEng(schedone.getDescrizioneContenutoEng());
			command.setLegatura(schedone.getLegatura());
			command.setSupporto(schedone.getSupporto());
			command.setCartulazione(schedone.getCartulazione());
			command.setNoteCartulazione(schedone.getNoteCartulazione());
			command.setNoteCartulazioneEng(schedone.getNoteCartulazioneEng());
			command.setCarteBianche(schedone.getCarteBianche());
			command.setCarteMancanti(schedone.getCarteMancanti());
			command.setDimensioniBase(schedone.getDimensioniBase());
			command.setDimensioniAltezza(schedone.getDimensioniAltezza());
			command.setTipoRipresa(schedone.getTipoRipresa());
			command.setColoreImmagine(schedone.getColoreImmagine());
			command.setRisoluzione(schedone.getRisoluzione());
			command.setNomeFiles(schedone.getNomeFiles());
			command.setResponsabileFotoRiproduzione(schedone.getResponsabileFotoRiproduzione());
			command.setDataRipresaAnno(schedone.getDataRipresaAnno());
			command.setDataRipresaMese((schedone.getDataRipresaMese() != null) ? schedone.getDataRipresaMese().getMonthNum() : null);
			command.setDataRipresaGiorno(schedone.getDataRipresaGiorno());
			command.setOperatore(schedone.getOperatore());
		} else {
			command.setIstituto(null);
			command.setFondo(null);
			command.setSerie(null);
			command.setNumeroUnita(null);
			command.setDataInizioAnno(null);
			command.setDataInizioMese(null);
			command.setDataInizioGiorno(null);
			command.setDataFineAnno(null);
			command.setDataFineMese(null);
			command.setDataFineGiorno(null);
			command.setDescrizioneContenuto(null);
			command.setDescrizioneContenutoEng(null);
			command.setLegatura(null);
			command.setSupporto(null);
			command.setCartulazione(null);
			command.setNoteCartulazione(null);
			command.setNoteCartulazioneEng(null);
			command.setCarteBianche(null);
			command.setCarteMancanti(null);
			command.setDimensioniBase(null);
			command.setDimensioniAltezza(null);
			command.setTipoRipresa(null);
			command.setColoreImmagine(null);
			command.setRisoluzione(null);
			command.setNomeFiles(null);
			command.setResponsabileFotoRiproduzione(null);
			command.setDataRipresaAnno(null);
			command.setDataRipresaMese(null);
			command.setDataRipresaGiorno(null);
			command.setOperatore(null);
		}

		return new ModelAndView("digitization/EditDetailsSchedone", model);
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
