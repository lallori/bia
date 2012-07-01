/*
 * ShowTopicForumController.java
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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.community.ShowTopicForumCommand;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.ForumTopic;
import org.medici.docsources.domain.UserInformation;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/community/ShowTopicForum")
public class ShowTopicForumController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowTopicForumCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			UserInformation userInformation = (UserInformation) httpSession.getAttribute("userInformation");

			if (userInformation != null) {
				if (userInformation.getForumJoinedDate() == null) {
					userInformation = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("userInformation", userInformation);
				}
			}
	
			// secondo paginationFilter to manage topics results..
			PaginationFilter paginationFilterTopic = new PaginationFilter();
			if (command.getTopicsForPage() != null) {
				paginationFilterTopic.setElementsForPage(command.getTopicsForPage());
			} else {
				paginationFilterTopic.setElementsForPage(new Integer(10));
				command.setTopicsForPage(paginationFilterTopic.getElementsForPage());
			}
			if (command.getTopicPageNumber() != null) {
				paginationFilterTopic.setThisPage(command.getTopicPageNumber());
			} else {
				paginationFilterTopic.setThisPage(new Integer(1));
				command.setTopicPageNumber(paginationFilterTopic.getThisPage());
			}
			if (command.getTopicPageTotal() != null) {
				paginationFilterTopic.setPageTotal(command.getTopicPageTotal());
			} else {
				paginationFilterTopic.setPageTotal(null);
			}
			paginationFilterTopic.addSortingCriteria("postId", "asc");

			ForumTopic forumTopic = getCommunityService().getForumTopic(new ForumTopic(command.getTopicId()));
			model.put("topic", forumTopic);
			
			Page postsPage = getCommunityService().getForumPostsFromTopic(forumTopic, paginationFilterTopic);
			model.put("postsPage", postsPage);

			HashMap<String, Object> statisticsHashMap = getCommunityService().getForumsStatistics();
			model.put("statisticsHashMap", statisticsHashMap);
		}catch (ApplicationThrowable applicationThrowable) {
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