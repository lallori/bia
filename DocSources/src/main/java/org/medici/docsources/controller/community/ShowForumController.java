/*
 * ShowForumController.java
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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.command.community.ShowForumCommand;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.Forum.Type;
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
 * Controller to view a forum .
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/ShowForum"})
public class ShowForumController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowForumCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();
		Forum forum = new Forum(); 

		try {
			UserInformation userInformation = (UserInformation) httpSession.getAttribute("userInformation");

			if (userInformation != null) {
				if (userInformation.getForumJoinedDate() == null) {
					userInformation = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("userInformation", userInformation);
				}
			}
			
			if (command.getForumId() == null){
				forum = getCommunityService().getFirstCategory();
			} else {
				forum = getCommunityService().getForum(command.getForumId());
			}

			if (forum.getType().equals(Type.CATEGORY)) {
				model.put("category", forum);

				if (forum.getOption().getCanHaveSubCategory()) {
					List<Forum> subCategories = getCommunityService().getSubCategories(new Forum(forum.getForumId()));
					model.put("subCategories", subCategories);

					//SubForums are extracted only if category is enabled to subForum...
					List<Integer> subCategoriesIdsEnabledToSubForums = new ArrayList<Integer>(0);
					for (Forum category : subCategories) {
						if (category.getOption().getCanHaveSubForum()) {
							subCategoriesIdsEnabledToSubForums.add(category.getForumId());
						}
					}

					HashMap<Integer, List<Forum>> forumsHashMap = new HashMap<Integer, List<Forum>>(0);
					forumsHashMap = getCommunityService().getForumsGroupByCategory(subCategoriesIdsEnabledToSubForums);
					model.put("forumsBySubCategories", forumsHashMap);
				}
			} else if (forum.getType().equals(Type.FORUM)) {
				model.put("forum", forum);
			}

			if (forum.getOption().getCanHaveSubForum()) {
				PaginationFilter paginationFilterForum = new PaginationFilter();
				if (command.getForumsForPage() != null) {
					paginationFilterForum.setElementsForPage(command.getForumsForPage());
				} else {
					paginationFilterForum.setElementsForPage(new Integer(10));
				}
				if (command.getForumPageNumber() != null) {
					paginationFilterForum.setThisPage(command.getForumPageNumber());
				} else {
					paginationFilterForum.setThisPage(new Integer(1));
				}
				if (command.getForumPageTotal() != null) {
					paginationFilterForum.setPageTotal(command.getForumPageTotal());
				} else {
					paginationFilterForum.setPageTotal(null);
				}
				paginationFilterForum.addSortingCriteria("dispositionOrder", "asc");
				
				Page page = getCommunityService().getSubForums(forum.getForumId(), paginationFilterForum);
				model.put("subForumsPage", page);
			}

			if (forum.getOption().getCanHaveTopics()) {
				// secondo paginationFilter to manage topics results..
				PaginationFilter paginationFilterTopic = new PaginationFilter();
				if (command.getTopicsForPage() != null) {
					paginationFilterTopic.setElementsForPage(command.getTopicsForPage());
				} else {
					paginationFilterTopic.setElementsForPage(new Integer(10));
				}
				if (command.getTopicPageNumber() != null) {
					paginationFilterTopic.setThisPage(command.getTopicPageNumber());
				} else {
					paginationFilterTopic.setThisPage(new Integer(1));
				}
				if (command.getTopicPageTotal() != null) {
					paginationFilterTopic.setPageTotal(command.getTopicPageTotal());
				} else {
					paginationFilterTopic.setPageTotal(null);
				}
				paginationFilterTopic.addSortingCriteria("forumId", "asc");
	
				Page topicPage = getCommunityService().getForumTopics(forum, paginationFilterTopic);
				model.put("topicsPage", topicPage);
				
				HashMap<String, Object> statisticsHashMap = getCommunityService().getForumsStatistics();
				model.put("statisticsHashMap", statisticsHashMap);
			}
		}catch (ApplicationThrowable applicationThrowable) {
			return new ModelAndView("error/ShowForum", model);
		}

		if (ObjectUtils.toString(command.getCompleteDOM()).equals(Boolean.TRUE.toString())) {
			return new ModelAndView("community/ShowForumCompleteDOM", model);
		} else {
			return new ModelAndView("community/ShowForum", model);
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
