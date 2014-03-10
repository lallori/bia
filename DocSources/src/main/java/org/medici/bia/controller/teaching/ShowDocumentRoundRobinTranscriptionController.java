/*
 * ShowDocumentRoundRobinTranscription.java
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.medici.bia.command.teaching.ShowDocumentRoundRobinTranscriptionCommand;
import org.medici.bia.common.access.ApplicationAccessContainer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class ShowDocumentRoundRobinTranscriptionController {
	
	@Autowired
	private ApplicationAccessContainer applicationAccessContainer;

	@Autowired
	private TeachingService teachingService;
	
	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/teaching/ShowDocumentRoundRobinTranscription", method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowDocumentRoundRobinTranscriptionCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model.put("editingMode", command.getEditingMode() != null ? command.getEditingMode() : Boolean.FALSE);
			
			// Control to anonymous access
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().getName().equals("java.lang.String")) {
				model.put("account", null);
			} else {
				model.put("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			}
			
			ForumTopic courseTopic = getTeachingService().getCourseTopicForView(command.getTopicId());
			
			model.put("topic", courseTopic);
			//model.put("subscribed", getCommunityService().ifTopicSubscribed(courseTopic.getTopicId()));
			
			if (Boolean.FALSE.equals(command.getCompleteDOM())) {
				PaginationFilter filter = getPaginationFilter(command);
				Page postsPage = getTeachingService().getPostsFromCourseTopic(courseTopic, filter);
				model.put("postsPage", postsPage);

				Set<String> accountIds = new HashSet<String>();
				for(ForumPost post : ((List<ForumPost>)postsPage.getList())) {
					accountIds.add(post.getUser().getAccount());
				}
				Map<String, UserAuthority> maxAuthorities = new HashMap<String, UserAuthority>();
				if (accountIds.size() > 0) {
					maxAuthorities = getTeachingService().getUsersRoundRobinAuthority(accountIds);
				}
				model.put("maxAuthorities", maxAuthorities);
				model.put("onlineUsers", applicationAccessContainer.getTeachingOnlineUsers());
				
				return new ModelAndView("teaching/ShowRoundRobinTranscription", model);
			}
			
			return new ModelAndView("teaching/ShowRoundRobinTranscriptionDOM", model);
			
		} catch (ApplicationThrowable th) {
			model.put("applicationThrowable", th);
			return new ModelAndView("error/ShowRoundRobinTranscription", model);
		}
	}
	
	private PaginationFilter getPaginationFilter(ShowDocumentRoundRobinTranscriptionCommand command) {
		if (command.getPostsForPage() == null) {
			command.setPostsForPage(10);
		}
		if (command.getPostPageNumber() == null) {
			command.setPostPageNumber(1);
		}
		
		PaginationFilter paginationFilterTopic = new PaginationFilter();
		paginationFilterTopic.setElementsForPage(command.getPostsForPage());
		paginationFilterTopic.setThisPage(command.getPostPageNumber());
		paginationFilterTopic.setPageTotal(command.getPostPageTotal());
		paginationFilterTopic.addSortingCriteria("postId", "asc");
		
		return paginationFilterTopic;
	}

}
