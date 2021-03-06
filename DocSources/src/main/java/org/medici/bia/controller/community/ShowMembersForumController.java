/*
 * ShowMembersForumController.java
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
package org.medici.bia.controller.community;

import java.util.HashMap;
import java.util.Map;

import org.medici.bia.command.community.ShowMembersForumCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view a categort forum page.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/ShowMembersForum"})
public class ShowMembersForumController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowMembersForumCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			PaginationFilter paginationFilter = new PaginationFilter();
			if (command.getMembersForPage() != null) {
				paginationFilter.setElementsForPage(command.getMembersForPage());
			} else {
				paginationFilter.setElementsForPage(new Integer(10));
				command.setMembersForPage(paginationFilter.getElementsForPage());
			}
			if (command.getMemberPageNumber() != null) {
				paginationFilter.setThisPage(command.getMemberPageNumber());
			} else {
				paginationFilter.setThisPage(new Integer(1));
				command.setMemberPageNumber(paginationFilter.getThisPage());
			}
			if (command.getMemberPageTotal() != null) {
				paginationFilter.setPageTotal(command.getMemberPageTotal());
			} else {
				paginationFilter.setPageTotal(null);
			}
			paginationFilter.addSortingCriteria("username", "asc");

			Page membersPage;
			if(command.getMember() == null)
				membersPage = getCommunityService().getForumMembers(command.getLetter(), paginationFilter);
			else
				membersPage = getCommunityService().getForumMembersByText(command.getMember(), paginationFilter);
			model.put("membersPage", membersPage);

		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowIndexForum", model);
		}

		return new ModelAndView("community/ShowMembersForum", model);
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