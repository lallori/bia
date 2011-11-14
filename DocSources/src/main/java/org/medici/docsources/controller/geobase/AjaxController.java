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
	@RequestMapping(value = "/de/geobase/SearchBornPlace", method = RequestMethod.GET)
	public ModelAndView searchBornPlace(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Place> places = getGeoBaseService().searchBornPlace(query);
			model.put("query", query);
			model.put("count", places.size());
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(places, "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(places, "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(places, "plType"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns a list of ipotetical senders places. 
	 *  
	 * @param text Text to search in ...
	 * @return ModelAndView containing senders.
	 */
	@RequestMapping(value = "/de/geobase/SearchDeathPlace", method = RequestMethod.GET)
	public ModelAndView searchDeathPlace(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Place> places = getGeoBaseService().searchDeathPlace(query);
			model.put("query", query);
			model.put("count", places.size());
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(places, "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(places, "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(places, "plType"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns a list of parent places linkable to a place. 
	 *  
	 * @param placeAllId Unique place identifier
	 * @param query Search string filled by user
	 * 
	 * @return ModelAndView containing linkable parent places.
	 */
	@RequestMapping(value = "/de/geobase/SearchPlaceParent", method = RequestMethod.GET)
	public ModelAndView searchPlaceParent(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<Place> places = getGeoBaseService().searchPlaceParent(query);
			model.put("query", query);
			model.put("count", places.size());
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(places, "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(places, "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(places, "plType"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
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
			model.put("count", places.size());
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(places, "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(places, "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(places, "plType"));
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
			model.put("count", places.size());
			model.put("data", ListBeanUtils.transformList(places, "placeAllId"));
			model.put("suggestions", ListBeanUtils.transformList(places, "placeNameFull"));
			model.put("prefFlags", ListBeanUtils.transformList(places, "prefFlag"));
			model.put("plTypes", ListBeanUtils.transformList(places, "plType"));
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on Birth Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowBirthPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showBirthPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on Death Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowDeathPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showDeathPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on sender Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowSenderPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showSenderPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on recipient Place. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowRecipientPlaceDetails", method = RequestMethod.GET)
	public ModelAndView showRecipientPlaceDetails(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns specific information on Place linkable to topic. 
	 * 
	 * @param personId
	 * @return
	 */
	@RequestMapping(value = "/de/geobase/ShowPlaceLinkableToTopicDocument", method = RequestMethod.GET)
	public ModelAndView showPlaceLinkableToTopicDocument(@RequestParam("placeAllId") Integer placeAllId) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Place place = getGeoBaseService().findPlace(placeAllId);
			model.put("placeAllId", (place.getPlaceAllId() != null ) ? place.getPlaceAllId().toString() : "");
			model.put("prefFlag", (place.getPrefFlag() != null ) ? place.getPrefFlag().toString() : "");
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