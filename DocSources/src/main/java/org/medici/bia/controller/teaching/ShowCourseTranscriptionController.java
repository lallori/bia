/*
 * ShowCourseTranscriptionController.java
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

import org.medici.bia.command.teaching.CourseTranscriptionCommand;
import org.medici.bia.common.access.ApplicationAccessContainer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
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
public class ShowCourseTranscriptionController {

	@Autowired
	private ApplicationAccessContainer applicationAccessContainer;

	@Autowired
	private TeachingService teachingService;
	
	public ApplicationAccessContainer getApplicationAccessContainer() {
		return applicationAccessContainer;
	}

	public void setApplicationAccessContainer(
			ApplicationAccessContainer applicationAccessContainer) {
		this.applicationAccessContainer = applicationAccessContainer;
	}

	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/teaching/ShowCourseTranscription", method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") CourseTranscriptionCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model.put("editingMode", command.getEditingMode() != null ? command.getEditingMode() : Boolean.FALSE);
			
			ForumTopic courseTopic;
			if (!Boolean.FALSE.equals(command.getCompleteDOM())) {
				courseTopic = getTeachingService().getCourseTopicForView(command.getTopicId());
			} else {
				courseTopic = getTeachingService().findCourseTopic(command.getTopicId());
			}
			model.put("resourcesForum", courseTopic.getForum().getForumId());
			
			CourseTopicMode transcriptionMode = CourseTopicMode.findByName(command.getTranscriptionMode());
			if (transcriptionMode == null) {
				transcriptionMode = getTeachingService().getCourseTopicMode(command.getTopicId());
			}
			
			if (Boolean.FALSE.equals(command.getCompleteDOM())) {
				// User cannot be anonymous due to security access controls
				model.put("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
				model.put("topic", courseTopic);
				
				PaginationFilter filter = getPaginationFilter(command, CourseTopicMode.I.equals(transcriptionMode) ? "lastUpdate" : "post.postId", true);
				Page postsPage = getTeachingService().getPostsFromCourseTopic(courseTopic, filter);
				model.put("postsPage", postsPage);
				model.put("maxAuthorities", getMaxAuthorities((List<CoursePostExt>)postsPage.getList()));
				model.put("onlineUsers", applicationAccessContainer.getTeachingOnlineUsers());
				Boolean isSubscribed = getTeachingService().isSubscribedToCourseTranscription(command.getTopicId());
				if (isSubscribed != null) {
					model.put("subscribed", isSubscribed);
				}
				
				switch (transcriptionMode) {
					case C:
						return new ModelAndView("teaching/ShowCheckPointCourseTranscription", model);
					case I:
						return new ModelAndView("teaching/ShowIncrementalCourseTranscription", model);
					case R:
						return new ModelAndView("teaching/ShowRoundRobinCourseTranscription", model);
					default:
						return new ModelAndView("error/ShowCourseTranscription", model);
				}
			}
			
			switch (transcriptionMode) {
				case C:
					return new ModelAndView("teaching/ShowCheckPointCourseTranscriptionDOM", model);
				case I:
					return new ModelAndView("teaching/ShowIncrementalCourseTranscriptionDOM", model);
				case R:
					return new ModelAndView("teaching/ShowRoundRobinCourseTranscriptionDOM", model);
				default:
					return new ModelAndView("error/ShowCourseTranscriptionDOM", model);
				
			}
			
		} catch (ApplicationThrowable th) {
			model.put("applicationThrowable", th);
			return new ModelAndView("error/CourseTranscription", model);
		}
	}
	
	/* Privates */

	private Map<String, UserAuthority> getMaxAuthorities(List<CoursePostExt> posts) throws ApplicationThrowable {
		Set<String> accountIds = new HashSet<String>();
		for(CoursePostExt post : posts) {
			accountIds.add(post.getPost().getUser().getAccount());
		}
		Map<String, UserAuthority> maxAuthorities = new HashMap<String, UserAuthority>();
		if (accountIds.size() > 0) {
			maxAuthorities = getTeachingService().getUsersCourseAuthority(accountIds);
		}
		return maxAuthorities;
	}
	
	private PaginationFilter getPaginationFilter(CourseTranscriptionCommand command, String sortingField, boolean ascending) {
		if (command.getPostsForPage() == null) {
			command.setPostsForPage(10);
		}
		
		PaginationFilter paginationFilterTopic = new PaginationFilter();
		paginationFilterTopic.setElementsForPage(command.getPostsForPage());
		paginationFilterTopic.setThisPage(command.getPostPageNumber());
		paginationFilterTopic.setPageTotal(command.getPostPageTotal());
		paginationFilterTopic.addSortingCriteria(sortingField, ascending ? "ASC" : "DESC");
		
		return paginationFilterTopic;
	}
}
