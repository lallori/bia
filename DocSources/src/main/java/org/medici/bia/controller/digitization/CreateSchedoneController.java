/*
 * CreateSchedoneController.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.domain.Schedone;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.bia.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Create Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/digitization/CreateSchedone")
public class CreateSchedoneController {
	@Autowired
	private DigitizationService digitizationService;
	@Autowired(required = false)
	@Qualifier("createSchedoneValidator")
	private Validator validator;

	/**
	 * @return the digitizationService
	 */
	public DigitizationService getDigitizationService() {
		return digitizationService;
	}

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
	 * @param digitizationService the digitizationService to set
	 */
	public void setDigitizationService(DigitizationService digitizationService) {
		this.digitizationService = digitizationService;
	}

	/**
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm() {
		Map<String, Object> model = new HashMap<String, Object>(0);

		Schedone schedone = new Schedone();
		schedone.setSchedoneId(0);
		schedone.setIstituto(ApplicationPropertyManager.getApplicationProperty("schedone.istituto"));
		schedone.setFondo(ApplicationPropertyManager.getApplicationProperty("schedone.fondo"));
		schedone.setLegatura(ApplicationPropertyManager.getApplicationProperty("schedone.legatura"));
		schedone.setSupporto(ApplicationPropertyManager.getApplicationProperty("schedone.supporto"));
		schedone.setTipoRipresa(ApplicationPropertyManager.getApplicationProperty("schedone.tipoRipresa"));
		schedone.setColoreImmagine(ApplicationPropertyManager.getApplicationProperty("schedone.coloreImmagine"));
		schedone.setNomeFiles(ApplicationPropertyManager.getApplicationProperty("schedone.nomeFiles"));
		schedone.setResponsabileFotoRiproduzione(ApplicationPropertyManager.getApplicationProperty("schedone.responsabileFotoRiproduzione"));
		schedone.setOperatore(ApplicationPropertyManager.getApplicationProperty("schedone.operatore"));
		schedone.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
		schedone.setDataCreazione(new Date());

		model.put("schedone", schedone);

		return new ModelAndView("digitization/ShowSchedone", model);
	}

	/**
	 * 
	 * @param validator
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}