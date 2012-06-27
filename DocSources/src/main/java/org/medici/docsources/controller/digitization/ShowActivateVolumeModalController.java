/*
 * ShowActivateVolumeModalController.java
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
 * 
 */
package org.medici.docsources.controller.digitization;

import java.util.HashMap;
import java.util.Map;
import org.medici.docsources.command.digitization.ShowActivateVolumeModalCommand;
import org.medici.docsources.domain.Digitization;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.digitization.DigitizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Activate Volume Modal".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/digitization/ShowActivateVolumeModal")
public class ShowActivateVolumeModalController {
	@Autowired
	private DigitizationService digitizationService;
	
	
	/**
	 * @return the digitizationService
	 */
	public DigitizationService getDigitizationService() {
		return digitizationService;
	}

	@RequestMapping(method = {RequestMethod.POST})
	public ModelAndView processSubmit(@ModelAttribute("command") ShowActivateVolumeModalCommand command, BindingResult result) {
				
		if(result.hasErrors()){
			return new ModelAndView("error/BrowseActivatedVolumes");
		}else{
			Map<String, Object> model = new HashMap<String, Object>();
			try{
				if(command.getId() != 0){
					Digitization digitization = getDigitizationService().findActivateVolumeDigitized(command.getId());
					digitization.setActive(Boolean.TRUE);
					digitization = getDigitizationService().activationOrDeactivationVolume(digitization);
				}
				
				return new ModelAndView("response/ActivateVolumeOK",model);
			}catch(ApplicationThrowable ath){
				return new ModelAndView("error/ActivateVolumeKO", model);
			}
		}
	}
	
	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupPage(@ModelAttribute("command")ShowActivateVolumeModalCommand command, BindingResult result){
		return new ModelAndView("digitization/ShowActivateVolumeModalWindow");
	}

	/**
	 * @param digitizationService the digitizationService to set
	 */
	public void setDigitizationService(DigitizationService digitizationService) {
		this.digitizationService = digitizationService;
	}

}