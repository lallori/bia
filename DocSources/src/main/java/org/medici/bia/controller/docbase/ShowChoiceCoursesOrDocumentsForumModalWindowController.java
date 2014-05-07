/*
 * ShowChoiceCoursesOrDocumentsForumModalWindowController.java
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
package org.medici.bia.controller.docbase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.command.docbase.CourseOrDiscussionChoiceCommand;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.Forum;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show courses or documents forum modal window".
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class ShowChoiceCoursesOrDocumentsForumModalWindowController {
	
	@Autowired
	private DocBaseService docBaseService;
	@Autowired
	private TeachingService teachingService;
	
	public DocBaseService getDocBaseService() {
		return docBaseService;
	}

	public void setDocBaseService(DocBaseService docBaseService) {
		this.docBaseService = docBaseService;
	}

	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@RequestMapping(value="/src/docbase/ShowChoiceCoursesOrDocumentsForumModalWindow", method = RequestMethod.GET)
	public ModelAndView chooseCourseOrForum(@ModelAttribute("command") CourseOrDiscussionChoiceCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			List<Course> activeCourses = getTeachingService().getActiveCourses();
			if (activeCourses.size() > 1) {
				model.put("activeCourses", activeCourses);
			} else if (activeCourses.size() > 0) {
				model.put("activeCourse", activeCourses.get(0));
			}
			
			List<Course> courses = getTeachingService().getActiveCourses(command.getEntryId());
			if (courses.size() > 0) {
				model.put("courses", courses);
				List<CourseTopicOption> extendedTopics = getTeachingService().getMasterOptionsByDocumentForActiveCourses(command.getEntryId());
				model.put("extendedTopics", extendedTopics);
			}
			
			Forum forum = getDocBaseService().getDocumentForum(command.getEntryId());
			if (forum != null && !forum.getLogicalDelete()) {
				model.put("hasForum", true);
				model.put("forumId", forum.getForumId());
				model.put("forumUrl", HtmlUtils.getShowForumUrl(forum));
				model.put("discussions", forum.getTopicsNumber());
				model.put("forumUrlCompleteDOM", HtmlUtils.getShowForumCompleteDOMUrl(forum));
			} else {
				model.put("hasForum", false);
			}
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowChoiceCoursesOrDocumentsForumModalWindow", model);
		}

		return new ModelAndView("docbase/ShowChoiceCoursesOrDocumentsForumModalWindow", model);
	}
	
}
