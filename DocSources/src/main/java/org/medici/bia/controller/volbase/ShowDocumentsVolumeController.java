/*
 * ShowDocumentsVolumeController.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.command.volbase.ShowDocumentsVolumeCommand;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show documents volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/volbase/ShowDocumentsVolume")
public class ShowDocumentsVolumeController {
	@Autowired
	private VolBaseService volBaseService;


	/**
	 * 
	 * @param placeId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowDocumentsVolumeCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		Volume volume = new Volume();
		
		if(command.getSummaryId() > 0){
			try {
				volume = getVolBaseService().findVolume(command.getSummaryId());
								
				List<String> outputFields = new ArrayList<String>(6);
				outputFields.add("Sender");
				outputFields.add("Recipient");
				outputFields.add("Date");
				outputFields.add("Sender Location");
				outputFields.add("Recipient Location");
				outputFields.add("Volume / Folio");
				
				model.put("outputFields", outputFields);

				model.put("volNum", volume.getVolNum());
				model.put("summaryId", volume.getSummaryId());
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				new ModelAndView("error/ShowDocumentsVolume", model);
			}
		}

		return new ModelAndView("volbase/ShowDocumentsVolume", model);
	}


	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}


	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

}