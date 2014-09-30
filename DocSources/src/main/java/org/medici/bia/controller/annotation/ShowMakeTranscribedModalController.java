/*
 * ShowMakeTranscribedModalController.java
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
package org.medici.bia.controller.annotation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.medici.bia.command.annotation.ShowMakeTranscribedModalCommand;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.Annotation;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class ShowMakeTranscribedModalController {
	
	@Autowired
	private CommunityService communityService;
	
	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	@RequestMapping(value="/annotation/ShowMakeTranscribedModalWindow", method = RequestMethod.GET)
	public ModelAndView showMakeTranscribedModalWindow(@ModelAttribute("command") ShowMakeTranscribedModalCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Annotation annotation = getCommunityService().findAnnotation(command.getAnnotationId());
			if (annotation == null) {
				return new ModelAndView("error/ShowMakeTranscribedModalWindow", model);
			}
			model.put("annotation", annotation);
			
			command.setAnnotationTitle(annotation.getTitle());
			
			return new ModelAndView("annotation/ShowMakeTranscribedModalWindow", model);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowMakeTranscribedModalWindow", model);
		}
	}
	
	@RequestMapping(value = "/annotation/markAsTranscribed.json", method = RequestMethod.POST)
	public Map<String, Object> markAsTranscribed(
			@RequestParam(value="annotationId", required=true) Integer annotationId, 
			@RequestParam(value="annotationTitle", required=true) String annotationTitle, 
			@RequestParam(value="export", required=false) Boolean export, 
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Annotation annotation = getCommunityService().markAsTranscribed(annotationId, annotationTitle);
			if (Boolean.TRUE.equals(export)) {
				getCommunityService().exportAnnotationDiscussion(annotation, httpServletRequest.getRemoteAddr());
			}
			model.put("reloadURL", (Annotation.Type.TEACHING.equals(annotation.getType()) ? 
					HtmlUtils.getTeachingShowTopicForumHrefUrl(annotation.getForumTopic()) :
					HtmlUtils.getShowTopicForumHrefUrl(annotation.getForumTopic())) + "&completeDOM=true");
			model.put("operation", "OK");
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/annotation/markAsNotTranscribed.json", method = RequestMethod.POST)
	public Map<String, Object> markAsNotTranscribed(
			@RequestParam(value="annotationId", required=true) Integer annotationId,
			HttpServletRequest httpServletRequest) {
		
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try {
			Annotation annotation = getCommunityService().markAsNotTranscribed(annotationId);
			
			model.put("reloadURL", (Annotation.Type.TEACHING.equals(annotation.getType()) ? 
					HtmlUtils.getTeachingShowTopicForumHrefUrl(annotation.getForumTopic()) :
					HtmlUtils.getShowTopicForumHrefUrl(annotation.getForumTopic())) + "&completeDOM=true");
			model.put("operation", "OK");
		} catch (ApplicationThrowable th) {
			if (th.getApplicationError() != null) {
				model.put("error", th.getMessage());
			}
			model.put("operation", "KO");
		}
		
		return model;
	}

}
