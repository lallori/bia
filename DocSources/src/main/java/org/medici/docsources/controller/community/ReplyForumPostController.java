/*
 * ReplyForumPostController.java
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.medici.docsources.command.community.ReplyForumPostCommand;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.ForumPost;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to reply a post.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/ReplyForumPost"})
public class ReplyForumPostController {
	@Autowired
	private CommunityService communityService;
	@Autowired(required = false)
	@Qualifier("replyForumPostValidator")
	private Validator validator;
	
	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@Valid @ModelAttribute("command") ReplyForumPostCommand command, BindingResult result, HttpServletRequest httpServletRequest) {
		getValidator().validate(command, result);
		if (result.hasErrors()) {
			return setupForm(command);
		} else {
			Map<String, Object> model = new HashMap<String, Object>();

			ForumPost forumPost = new ForumPost();
			forumPost.setIpAddress(httpServletRequest.getRemoteAddr());
			forumPost.setText(command.getText());
			forumPost.setSubject(command.getSubject());
			//forumPost.setParentPost(new ForumPost(command.getParentPostId()));
			forumPost.setForum(new Forum(command.getForumId()));

			try {
				forumPost = getCommunityService().replyPost(forumPost);
				model.put("forumPost", forumPost);
				return new ModelAndView("response/ForumPostMessageOK", model);
			} catch (ApplicationThrowable ath) {
				return new ModelAndView("response/ForumPostMessageKO", model);
			}
		}
	}
		
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ReplyForumPostCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();

		ForumPost firstPostTopicToReply = new ForumPost();

		try {
			firstPostTopicToReply = getCommunityService().findFirstPostTopic(command.getTopicId());
		} catch (ApplicationThrowable applicationThrowable) {
			return new ModelAndView("error/EditPostForum", model);
			
		}
		//command.setParentPostId(postToReply.getParentPost().getId());
		command.setSubject(firstPostTopicToReply.getSubject());
		command.setText(firstPostTopicToReply.getText());

		return new ModelAndView("community/EditForumPost", model);
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

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}
}