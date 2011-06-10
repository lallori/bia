/*
 * AdvancedSearchDocuments.java
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
package org.medici.docsources.controller.search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.medici.docsources.command.search.AdvancedSearchDocumentsCommand;
import org.medici.docsources.common.search.AdvancedSearchDocument;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Advanced Search on Documents".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/AdvancedSearchDocuments")
public class AdvancedSearchDocumentsController {
	@Autowired
	private DocBaseService docBaseService;
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupPage(){
		Map<String, Object> model = new HashMap<String, Object>();
		List<Month> months = null;

		try {
			months = getDocBaseService().getMonths();
			model.put("months", months);
		} catch (ApplicationThrowable ath) {
			return new ModelAndView("error/AdvancedSearchDocuments", model);
		}

		return new ModelAndView("search/AdvancedSearchDocuments", model);
	}
	
	/**
	 * 
	 * @param command
	 * @param session
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView executeSearch(@ModelAttribute("command") AdvancedSearchDocumentsCommand command, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>();

		AdvancedSearchDocument advancedSearchDocument = new AdvancedSearchDocument();
		advancedSearchDocument.initFromCommand(command);

		// This number is used to generate an unique id for datatable jquery plugin to use multiple object in tabs
		UUID uuid = UUID.randomUUID();
		model.put("searchNumber", uuid.toString());

		session.setAttribute("advancedSearchDocument" + uuid.toString(), advancedSearchDocument);

		return new ModelAndView("search/AdvancedSearchResultDocuments", model);
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}
	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

}