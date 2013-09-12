/*
 * ShowSameFolioDocumentsController.java
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
package org.medici.bia.controller.docbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.docbase.ShowSameFolioDocumentsCommand;
import org.medici.bia.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show same Folio Documents".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/docbase/ShowSameFolioDocuments")
public class ShowSameFolioDocumentsController {
	@Autowired
	private DocBaseService docBaseService;
	
	/**
	 * 
	 * @return
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * 
	 * @param documentId
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView processSubmit(@ModelAttribute("command") ShowSameFolioDocumentsCommand command, BindingResult result, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		List<String> outputFields = new ArrayList<String>(6);
		outputFields.add("Sender");
		outputFields.add("Recipient");
		outputFields.add("Date");
		outputFields.add("Sender Location");
		outputFields.add("Recipient Location");
		outputFields.add("Volume / Insert / Folio");
			
		model.put("outputFields", outputFields);
		model.put("volNum", command.getVolNum());
		model.put("volLetExt", ObjectUtils.toString(command.getVolLetExt()));
		model.put("insertNum", ObjectUtils.toString(command.getInsertNum()));
		model.put("insertLet", ObjectUtils.toString(command.getInsertLet()));
		model.put("folioNum", command.getFolioNum());
		model.put("folioMod", ObjectUtils.toString(command.getFolioMod()));		
		model.put("folioRectoVerso", ObjectUtils.toString(command.getFolioRectoVerso()));		

		return new ModelAndView("docbase/ShowSameFolioDocuments", model);
	}

	/**
	 * 
	 * @param docBaseService
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}
}