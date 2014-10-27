/*
 * ShowPersonalNotesController.java
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
package org.medici.bia.controller.teaching;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.medici.bia.domain.UserPersonalNotes;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class ShowPersonalNotesController {
	
	@Autowired
	private TeachingService teachingService;
	
	/**
	 * @return the teachingService
	 */
	public TeachingService getTeachingService() {
		return teachingService;
	}

	/**
	 * @param teachingService the teachingService to set
	 */
	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@RequestMapping(value="/teaching/ShowPersonalNotes", method = RequestMethod.GET)
	public ModelAndView setupPage() {
		Map<String, Object> model = new HashMap<String, Object>();
		UserPersonalNotes note = getTeachingService().getUserPersonalNotes(null);
		if (note != null) {
			model.put("userNoteId", note.getIdPersonalNotes());
			model.put("userNote", note.getPersonalNotes());
			model.put("userNoteUpdate", note.getLastUpdate());
		}
		return new ModelAndView("teaching/ShowPersonalNotes", model);
	}
	
	@RequestMapping(value="/teaching/SavePersonalNotes", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> savePersonalNotes(
		@RequestParam(value="personalNotesId", required=true) Integer personalNotesId,
		@RequestParam(value="personalNotes", required=true) String personalNotes,
		HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			UserPersonalNotes note = getTeachingService().saveUserPersonalNotes(personalNotesId, personalNotes);
			model.put("userNoteId", note.getIdPersonalNotes());
			model.put("userNote", note.getPersonalNotes());
			model.put("userNoteUpdate", note.getLastUpdate());
			model.put("operation", "OK");
		} catch (ApplicationThrowable th) {
			model.put("error", th.toString());
			model.put("operation", "KO");
		}
		
		return model;
	}

}
