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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.medici.docsources.command.volbase.ShowExplorerVolumeRequestCommand;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("requestCommand") ShowExplorerVolumeRequestCommand command, BindingResult result, HttpSession httpSession){
		Map<String, Object> model = new HashMap<String, Object>();
		
		if (!ObjectUtils.toString(command.getVolNum()).equals("")) {
			PagedListHolder volumeImages = null;
			try {
				if (!ObjectUtils.toString(command.getSummaryId()).equals("")) {
					volumeImages = new PagedListHolder(getVolBaseService().findVolumeImages(command.getSummaryId()));
				} else {
					volumeImages = new PagedListHolder(getVolBaseService().findVolumeImages(command.getVolNum(), command.getVolLeText()));
				}
				
				volumeImages.setPageSize(1);
				httpSession.setAttribute("ShowExplorerVolume_volumeImages", volumeImages);
				model.put("volumeImages", volumeImages);
			} catch (ApplicationThrowable ath) {
			}
			
		} else {
			PagedListHolder volumeImages = (PagedListHolder) httpSession.getAttribute("ShowExplorerVolume_volumeImages");
			if (volumeImages == null) {
				throw new IllegalStateException("Cannot find pre-loaded volumeImages");
			}
			if (StringUtils.equals(command.getPage(), "next")) {
				volumeImages.nextPage();
			} else if (StringUtils.equals(command.getPage(), "previous")) {
				volumeImages.previousPage();
			}
			model.put("volumeImages", volumeImages);
		}

		return new ModelAndView("volbase/ShowExplorerVolume", model);
	}

	/**
	 * 
	 * @param volBaseService
	 */
	public void setVolBaseService(VolBaseService volBaseService) {
		this.volBaseService = volBaseService;
	}
}