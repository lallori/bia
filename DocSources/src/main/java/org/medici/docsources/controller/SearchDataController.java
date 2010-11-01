/*
 * SearchDataController.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.docsources.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.medici.docsources.command.SearchDataCommand;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Controller
@RequestMapping("/SearchData")
public class SearchDataController {
	@Autowired
	private DocBaseService docBaseService;
	
	@Autowired
	private GeoBaseService geoBaseService;

	@Autowired
	private PeopleBaseService peopleBaseService;

	@Autowired
	private VolBaseService volBaseService;

	/**
	 * @return the docBaseService
	 */
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}

	/**
	 * @return the peopleBaseService
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("command") SearchDataCommand command, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (command.getSearch().toLowerCase().trim().equals("documents")) {
			List<Document> searchResult = new ArrayList<Document>(0);
			try {
				searchResult = getDocBaseService().searchDocuments(command.getText());
				model.put("documents", searchResult);
			} catch (ApplicationThrowable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ModelAndView("docbase/SearchDocumentResult.do",model);
		}
		
		if (command.getSearch().toLowerCase().trim().equals("people")) {
			List<People> searchResult = new ArrayList<People>(0);
			try {
				searchResult = getPeopleBaseService().searchPeople(command.getText());
				model.put("people", searchResult);
			} catch (ApplicationThrowable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ModelAndView("peoplebase/SearchPeopleResult.do",model);
		}
		
		if (command.getSearch().toLowerCase().trim().equals("places")) {
			List<Document> searchResult = new ArrayList<Document>(0);
			try {
				searchResult = getGeoBaseService().searchPlaces(command.getText());
				model.put("places", searchResult);
			} catch (ApplicationThrowable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ModelAndView("geobase/SearchPlaceResult.do",model);
		}
		
		if (command.getSearch().toLowerCase().trim().equals("volumes")) {
			List<Volume> searchResult = new ArrayList<Volume>(0);
			try {
				searchResult = getVolBaseService().searchVolumes(command.getText());
				model.put("volumes", searchResult);
			} catch (ApplicationThrowable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ModelAndView("volbase/SearchVolumeResult.do",model);
		}

		return new ModelAndView("errorKO",model);
	}

	/**
	 * @param docBaseService the docBaseService to set
	 */
	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	/**
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * @param peopleBaseService the peopleBaseService to set
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm() {
		return new ModelAndView("LoginUser");
	}

	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}
