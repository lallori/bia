/*
 * ShowMyForumPostController.java
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

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.community.ShowMyForumPostCommand;
import org.medici.bia.command.community.ShowTopicForumCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.AdvancedSearchFactory;
import org.medici.bia.common.search.AdvancedSearchForum;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view a specific topic on a forum.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/community/ShowMyForumPost")
public class ShowMyForumPostController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowMyForumPostCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			User user = (User) httpSession.getAttribute("user");

			if (user != null) {
				if (user.getForumJoinedDate() == null) {
					user = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("user", user);
				}
			}
	
			// secondo paginationFilter to manage topics results..
			PaginationFilter paginationFilter = new PaginationFilter();
			if (command.getPostsForPage() != null) {
				paginationFilter.setElementsForPage(command.getPostsForPage());
			} else {
				paginationFilter.setElementsForPage(new Integer(10));
				command.setPostsForPage(paginationFilter.getElementsForPage());
			}
			if (command.getPostPageNumber() != null) {
				paginationFilter.setThisPage(command.getPostPageNumber());
			} else {
				paginationFilter.setThisPage(new Integer(1));
				command.setPostPageNumber(paginationFilter.getThisPage());
			}
			if (command.getPostPageTotal() != null) {
				paginationFilter.setPageTotal(command.getPostPageTotal());
			} else {
				paginationFilter.setPageTotal(null);
			}
			paginationFilter.addSortingCriteria("postId", "asc");

			AdvancedSearchForum advancedSearchForum = new AdvancedSearchForum();
			advancedSearchForum.setAuthor(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

			Page postsPage  = getCommunityService().searchForumPosts(advancedSearchForum, paginationFilter);
			model.put("postsPage", postsPage);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowTopicForum", model);
		}

		if (ObjectUtils.toString(command.getCompleteDOM()).equals(Boolean.TRUE.toString())) {
			return new ModelAndView("community/ShowTopicForumCompleteDOM", model);
		} else {
			return new ModelAndView("community/ShowTopicForum", model);
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