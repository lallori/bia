/*
 * ShowUploadPortraitPersonController.java
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
package org.medici.bia.controller.peoplebase;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.medici.bia.command.peoplebase.ShowUploadPortraitPersonCommand;
import org.medici.bia.common.ajax.MultipartFileUploadEditor;
import org.medici.bia.common.image.PersonPortrait;
import org.medici.bia.domain.People;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Upload Portrait Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/de/peoplebase/ShowUploadPortraitPerson")
public class ShowUploadPortraitPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	@Autowired(required = false)
	@Qualifier("showUploadPortraitPersonValidator")
	private Validator validator;

	/**
	 * 
	 * @return
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * 
	 * @param peopleBaseService
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * 
	 * @param peopleId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowUploadPortraitPersonCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>(0);
		if(command.getPersonId() > 0){
			try{
				People person = getPeopleBaseService().findPerson(command.getPersonId());
				model.put("person", person);
			
				return new ModelAndView("peoplebase/ShowUploadPortraitPersonModalWindow", model);
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				return new ModelAndView("error/ShowUploadPortraitPersonModalWindow", model);
			}
		}
		return new ModelAndView("error/ShowUploadPortraitPersonModalWindow", model);		
	}

	/**
	 * 
	 * @param peopleId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processRequest(@Valid @ModelAttribute("command") ShowUploadPortraitPersonCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		getValidator().validate(command, result);

		if (result.hasErrors()) {
			setupForm(command, result);
		} else {
			PersonPortrait personPortrait = new PersonPortrait(command.getPersonId(), command.getBrowse(), command.getLink());
	
			try {
				BufferedImage bufferedImage = getPeopleBaseService().savePortaitPerson(personPortrait);
				
				model.put("portraitWidth", bufferedImage.getWidth());
				model.put("portraitHeight", bufferedImage.getHeight());
				model.put("time", System.currentTimeMillis());
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				new ModelAndView("error/UploadPortraitPerson", model);
			}
		}

		return new ModelAndView("peoplebase/CropPortraitPersonModalWindow", model);
	}
	
	/**
	 * 
	 * @param binder
	 */
	@InitBinder("command")
    public void initBinder(WebDataBinder binder, HttpServletRequest request) throws ServletException {
        binder.registerCustomEditor(CommonsMultipartFile.class, new MultipartFileUploadEditor());
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