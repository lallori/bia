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
package org.medici.docsources.controller.volbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.SerieList;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.user.UserService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for VolBase.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("VolBaseAjaxController")
public class AjaxController {
	@Autowired
	private UserService userService;
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * This method returns the summaryId of the volume searched by is MDP. 
	 *  
	 * @param volNum Volume Id
	 * @param volLeText Volume Filza
	 * @return ModelAndView containing input params and summaryId.
	 */
	@RequestMapping(value = "/de/volbase/FindVolume", method = RequestMethod.GET)
	public ModelAndView findSeriesList(@RequestParam("volNum") Integer volNum, @RequestParam("volLetExt") String volLetExt) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			Volume volume = getVolBaseService().findVolume(volNum, volLetExt);
			model.put("volNum", volNum);
			model.put("volLetExt", volLetExt);
			model.put("summaryId", (volume == null) ? "" : volume.getSummaryId());
		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}
	
	/**
	 * This method returns a list of seriesList. 
	 *  
	 * @param text Text to search in title, subTitle1 and subTitle2
	 * @return ModelAndView containing seriesList.
	 */
	@RequestMapping(value = "/de/volbase/FindSeries", method = RequestMethod.GET)
	public ModelAndView findSeriesList(@RequestParam("query") String query) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			List<SerieList> series = getVolBaseService().findSeries(query);
			model.put("query", query);
			model.put("data", ListBeanUtils.transformList(series, "seriesRefNum"));
			model.put("suggestions", ListBeanUtils.toStringListWithConcatenationFields(series, "title/subTitle1/subTitle2", "/", "/", Boolean.TRUE));

		} catch (ApplicationThrowable aex) {
			return new ModelAndView("responseKO", model);
		}

		return new ModelAndView("responseOK", model);
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @return the volBaseService
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param volBaseService the volBaseService to set
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}

}