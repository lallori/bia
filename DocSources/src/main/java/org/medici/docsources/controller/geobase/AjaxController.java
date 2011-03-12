/*
 * AjaxController.java
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
package org.medici.docsources.controller.geobase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Place;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for GeoBase.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("GeoBaseAjaxController")
public class AjaxController {
	@Autowired
	private GeoBaseService geoBaseService;

	/**
	 * This method returns a list of ipotetical senders places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/de/geobase/SearchSenderPlace", method = RequestMethod.GET)
	public ModelAndView searchSenders(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Place> places = getGeoBaseService().searchSendersPlace(query);
			model.put("query", query);
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(places, "placeNameFull", " ", " ", Boolean.TRUE));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * This method returns a list of ipotetical recipients places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing recipients.
	 */
	@RequestMapping(value = "/de/geobase/SearchRecipientPlace", method = RequestMethod.GET)
	public ModelAndView searchRecipients(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Place> places = getGeoBaseService().searchRecipientsPlace(query);
			model.put("query", query);
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(places, "placeNameFull", " ", " ", Boolean.TRUE));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * @param geoBaseService the geoBaseService to set
	 */
	public void setGeoBaseService(GeoBaseService geoBaseService) {
		this.geoBaseService = geoBaseService;
	}

	/**
	 * @return the geoBaseService
	 */
	public GeoBaseService getGeoBaseService() {
		return geoBaseService;
	}

}