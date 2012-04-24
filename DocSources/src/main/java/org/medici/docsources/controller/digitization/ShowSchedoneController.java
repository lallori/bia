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
package org.medici.docsources.controller.digitization;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.medici.docsources.command.digitization.ShowSchedoneCommand;
import org.medici.docsources.common.property.ApplicationPropertyManager;
import org.medici.docsources.domain.Schedone;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Schedone".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/digitization/ShowSchedone")
public class ShowSchedoneController {
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
	public ModelAndView setupForm(@ModelAttribute("command") ShowSchedoneCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		Schedone schedone = new Schedone();
		if(command.getSchedoneId() > 0){
			try{
				schedone = getDigitizationService().findSchedone(command.getSchedoneId());
			}catch(ApplicationThrowable th){
				return new ModelAndView("error/ShowSchedone", model);
			}
		}else{
			schedone.setSchedoneId(0);
			schedone.setIstituto(ApplicationPropertyManager.getApplicationProperty("schedone.istituto"));
			schedone.setFondo(ApplicationPropertyManager.getApplicationProperty("schedonefondo"));
			schedone.setLegatura(ApplicationPropertyManager.getApplicationProperty("schedone.legatura"));
			schedone.setSupporto(ApplicationPropertyManager.getApplicationProperty("schedone.supporto"));
			schedone.setTipoRipresa(ApplicationPropertyManager.getApplicationProperty("schedone.tipoRipresa"));
			schedone.setColoreImmagine(ApplicationPropertyManager.getApplicationProperty("schedone.coloreImmagine"));
			schedone.setNomeFiles(ApplicationPropertyManager.getApplicationProperty("schedone.nomeFiles"));
			schedone.setResponsabileFotoRiproduzione(ApplicationPropertyManager.getApplicationProperty("schedone.responsabileFotoRiproduzione"));
			schedone.setOperatore(ApplicationPropertyManager.getApplicationProperty("schedone.operatore"));
			schedone.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			schedone.setDataCreazione(new Date());
		}

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