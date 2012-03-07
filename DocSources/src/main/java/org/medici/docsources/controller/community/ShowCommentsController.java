/*
 * ShowComments.java
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
package org.medici.docsources.controller.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.community.ShowCommentsCommand;
import org.medici.docsources.domain.UserComment;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view user messages.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping("/community/ShowComments")
public class ShowCommentsController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowCommentsCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		if (!ObjectUtils.toString(command.getEntryId()).equals("")) {
			ArrayList<UserComment> userComments = new ArrayList<UserComment>(0); 

			try {
				userComments = getCommunityService().getCommentsOnDocument(command.getEntryId());
				model.put("userComments", userComments);
			} catch (ApplicationThrowable ath) {
			} finally {
			}

			return new ModelAndView("community/ShowCommentsOnDocument", model);
		} else if (!ObjectUtils.toString(command.getPersonId()).equals("")) {
			ArrayList<UserComment> userComments = new ArrayList<UserComment>(0); 

			try {
				userComments = getCommunityService().getCommentsOnPerson(command.getEntryId());
				model.put("userComments", userComments);
			} catch (ApplicationThrowable ath) {
			} finally {
			}

			return new ModelAndView("community/ShowCommentsOnPerson", model);
		} else if (!ObjectUtils.toString(command.getPlaceAllId()).equals("")) {
			ArrayList<UserComment> userComments = new ArrayList<UserComment>(0); 

			try {
				userComments = getCommunityService().getCommentsOnPlace(command.getEntryId());
				model.put("userComments", userComments);
			} catch (ApplicationThrowable ath) {
			} finally {
			}

			return new ModelAndView("community/ShowCommentsOnPlace", model);
		} else {

			ArrayList<UserComment> userComments = new ArrayList<UserComment>(0); 

			try {
				userComments = getCommunityService().getCommentsOnVolume(command.getEntryId());
				model.put("userComments", userComments);
			} catch (ApplicationThrowable ath) {
			} finally {
			}

			return new ModelAndView("community/ShowCommentsOnVolume", model);
		} 
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}
}