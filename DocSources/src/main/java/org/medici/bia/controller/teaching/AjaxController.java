/*
 * AjaxController.java
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

import javax.servlet.http.HttpServletRequest;

import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AJAX Controller for the Teaching module.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller("TeachingAjaxController")
public class AjaxController {
	
	@Autowired
	private TeachingService teachingService;

	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}
	
	@RequestMapping(value = "/teaching/DeleteRoundRobinPost", method = RequestMethod.POST)
	public Map<String, Object> deleteRoundRobinPost(
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="postId", required=true) Integer postId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			getTeachingService().deleteCourseTopicPost(postId);
			ForumTopic courseTopic = getTeachingService().getCourseTopic(topicId);
			model.put("topicId", topicId);
			model.put("operation", "OK");
			if (courseTopic != null){
				model.put("topicUrl", HtmlUtils.getShowCourseTopicHrefUrl(courseTopic));
			}
		} catch (ApplicationThrowable th) {
			model.put("operation", "KO");
		}
		return model;
	}
	
	@RequestMapping(value = "/teaching/EditRoundRobinPost", method = RequestMethod.POST)
	public Map<String, Object> editRoundRobinPost(
			@RequestParam(value="postId", required=false) Integer postId, 
			@RequestParam(value="topicId", required=true) Integer topicId,
			@RequestParam(value="subject", required=false) String subject,
			@RequestParam(value="text", required=false) String text,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			ForumPost post = null;
			if (postId == null || new Integer(0).equals(postId)) {
				post = getTeachingService().addNewTopicPost(topicId, subject != null ? subject.trim() : "", text != null ? text : "", httpServletRequest.getRemoteAddr());
			} else {
				post = getTeachingService().updateTopicPost(postId, subject != null ? subject.trim() : "", text != null ? text : "");
			}
			model.put("topicId", post.getTopic().getTopicId());
			model.put("postId", post.getPostId());
			model.put("topicUrl", HtmlUtils.getShowCourseTopicHrefUrl(post.getTopic()));
			model.put("operation", "OK");
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
		}
		return model;
	}
}
