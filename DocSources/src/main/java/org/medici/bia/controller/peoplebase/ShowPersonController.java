/*
 * ShowPersonController.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.command.peoplebase.ShowPersonRequestCommand;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.People;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/peoplebase/ShowPerson")
public class ShowPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;

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
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowPersonRequestCommand command, BindingResult result){
		Map<String, Object> model = new HashMap<String, Object>();
		People person = new People();

		if(command.getPersonId() > 0){
			try {
				person = getPeopleBaseService().findPerson(command.getPersonId());

				List<Marriage> marriages = getPeopleBaseService().findMarriagesPerson(person.getPersonId(), person.getGender());
				model.put("marriages", marriages);
				List<People> children = getPeopleBaseService().findChildrenPerson(person.getPersonId());
				model.put("children", children);
				Integer senderDocsRelated = getPeopleBaseService().findNumberOfSenderDocumentsRelated(person.getPersonId());
				model.put("senderDocsRelated", senderDocsRelated);
				Integer recipientDocsRelated = getPeopleBaseService().findNumberOfRecipientDocumentsRelated(person.getPersonId());
				model.put("recipientDocsRelated", recipientDocsRelated);
				Integer referringDocsRelated = getPeopleBaseService().findNumberOfReferringDocumentsRelated(person.getPersonId());
				model.put("referringDocsRelated", referringDocsRelated);
				Integer docsRelated = senderDocsRelated + recipientDocsRelated + referringDocsRelated;
				model.put("docsRelated", docsRelated);
				
				model.put("historyNavigator", getPeopleBaseService().getHistoryNavigator(person));
				
				if(getPeopleBaseService().ifPersonALreadyPresentInMarkedList(person.getPersonId())){
					model.put("inMarkedList", "true");
				}else{
					model.put("inMarkedList", "false");
				}
				
			} catch (ApplicationThrowable applicationThrowable) {
				model.put("applicationThrowable", applicationThrowable);
				new ModelAndView("error/ShowPerson", model);
			}
		} else {
			person.setPersonId(0);
			person.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			person.setDateCreated(new Date());
		}
		
		model.put("person", person);

		return new ModelAndView("peoplebase/ShowPerson", model);
	}
}