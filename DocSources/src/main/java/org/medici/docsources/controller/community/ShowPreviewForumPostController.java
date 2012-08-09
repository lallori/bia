/*
 * ShowPreviewForumPostController.java
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.medici.docsources.command.community.ShowPreviewForumPostCommand;
import org.medici.docsources.domain.ForumPost;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to preview a forum post.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/ShowPreviewForumPost"})
public class ShowPreviewForumPostController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView processSubmit(@ModelAttribute("command") ShowPreviewForumPostCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();
		User user = (User) httpSession.getAttribute("user");

		try {
			if (user != null) {
				if (user.getForumJoinedDate() == null) {
					user = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("user", user);
				}
			}
		}catch (ApplicationThrowable applicationThrowable) {
			return new ModelAndView("error/ShowPreviewForumPost", model);
		}
		
		
		ForumPost forumPost = new ForumPost(command.getId());
		forumPost.setText(command.getText());
		forumPost.setSubject(command.getSubject());
		forumPost.setDateCreated(new Date());
		forumPost.setUser(user);

		model.put("forumPost", forumPost);

		return new ModelAndView("community/ShowPreviewForumPost", model);
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