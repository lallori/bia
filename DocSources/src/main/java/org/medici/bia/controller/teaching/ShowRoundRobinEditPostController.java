/*
 * ShowRoundRobinPostEditController.java
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

import org.medici.bia.command.teaching.RoundRobinEditPostCommand;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.ForumTopic;
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
public class ShowRoundRobinEditPostController {
	
	@Autowired
	private TeachingService teachingService;
	
	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@RequestMapping(value="/teaching/ShowEditRoundRobinPost", method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") RoundRobinEditPostCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			ForumTopic courseTopic = getTeachingService().findCourseTopic(command.getTopicId());
			if (command.getPostId() == null || command.getPostId().equals(0)) {
				// new post without quoting
				command.setPostId(0);
				command.setSubject("Re: " + courseTopic.getSubject());
			} else {
				CoursePostExt extendedPost = getTeachingService().getCoursePostByForumPostId(command.getPostId());
				if (command.getQuote() != null && command.getQuote()) {
					// quoting a post: the 'quoted' text is generated client-side (by post identifier)
					command.setSubject("Re: " + courseTopic.getSubject());
					command.setText(null);
				} else {
					// edit an existent post
					command.setSubject(extendedPost.getPost().getSubject());
					command.setText(extendedPost.getPost().getText());
				}
				command.setVolNum(extendedPost.getVolNum());
				command.setVolLetExt(extendedPost.getVolLetExt());
				command.setInsertNum(extendedPost.getInsertNum());
				command.setInsertLet(extendedPost.getInsertLet());
				command.setFolioNum(extendedPost.getFolioNum());
				command.setFolioMod(extendedPost.getFolioMod());
				command.setFolioRV(extendedPost.getFolioRV());
			}
		} catch (ApplicationThrowable th) {
			return new ModelAndView("error/EditRoundRobinPost", model);
		}
		
		return new ModelAndView("teaching/EditRoundRobinPost", model);
	}
	
}
