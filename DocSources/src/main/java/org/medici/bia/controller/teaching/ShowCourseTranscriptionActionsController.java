/*
 * ShowCourseTranscriptionActionsController.java
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

import org.medici.bia.command.teaching.CourseTranscriptionActionsCommand;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ShowCourseTranscriptionActionsController {
	
	@Autowired
	private TeachingService teachingService;
	
	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}
	
	@RequestMapping(value= "/teaching/ShowCourseTranscriptionActions", method = RequestMethod.GET)
	public ModelAndView showCourseTopicActions(@ModelAttribute("command") CourseTranscriptionActionsCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		ForumTopic courseTopic = null;
		try {
			if (command.getTopicId() != null) {
				courseTopic = getTeachingService().findCourseTopic(command.getTopicId());
			}
			
			if (courseTopic == null) {
				return new ModelAndView("error/ShowCourseTranscriptionActions", model);
			}
			
			model.put("resourcesForum", courseTopic.getForum().getForumId());
			
			switch (CourseTopicMode.findByName(command.getTranscriptionMode())) {
			case I:
				return new ModelAndView("teaching/ShowIncrementalActions", model);
			case C:
				return new ModelAndView("teaching/ShowCheckPointActions", model);
			case R:
				return new ModelAndView("teaching/ShowRoundRobinActions", model);
			default:
				return new ModelAndView("error/ShowCourseTranscriptionActions", model);
			}
			
		} catch (ApplicationThrowable th) {
			return new ModelAndView("error/ShowCourseTranscriptionActions", model);
		}
		
	}

}
