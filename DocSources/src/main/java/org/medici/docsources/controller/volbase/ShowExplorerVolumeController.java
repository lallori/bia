/*
 * ShowExplorerVolume.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.volbase.ShowExplorerVolumeRequestCommand;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Image;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/volbase/ShowExplorerVolume")
public class ShowExplorerVolumeController {
	@Autowired
	private VolBaseService volBaseService;

	/**
	 * 
	 * @return
	 */
	public VolBaseService getVolBaseService() {
		return volBaseService;
	}

	/**
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowExplorerVolumeRequestCommand command, BindingResult result, HttpSession httpSession){
		Map<String, Object> model = new HashMap<String, Object>();
		
		Page page = null;

		PaginationFilter paginationFilter = new PaginationFilter(command.getFirstRecord(), 1, command.getTotal());

		try {
			if (!ObjectUtils.toString(command.getSummaryId()).equals("")) {
				page = getVolBaseService().findVolumeImages(command.getSummaryId(), paginationFilter);
			} else {
				if (!ObjectUtils.toString(command.getVolNum()).equals("")) {
					page = getVolBaseService().findVolumeImages(command.getVolNum(), command.getVolLetExt(), paginationFilter);
				} else {
					page = new Page(new ArrayList<Image>(0), new Long(0), new Integer(0), new Integer(0));
				}
			}

			model.put("page", page);
			model.put("paginationFilter", paginationFilter);
		} catch (ApplicationThrowable ath) {
		}

		if (BooleanUtils.isTrue(command.getFancyBox())) {
			return new ModelAndView("volbase/ShowExplorerVolumeFancyBox", model);
		} else {
			return new ModelAndView("volbase/ShowExplorerVolume", model);
		}
	}

	/**
	 * 
	 * @param volBaseService
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}