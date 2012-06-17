/*
 * ShowCategoryForumController.java
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

import org.medici.docsources.command.community.ShowCategoryForumCommand;
import org.medici.docsources.common.util.ListBeanUtils;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.UserInformation;
import org.medici.docsources.domain.Forum.Type;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view a forum category.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/", "/community/ShowCategoryForum"})
public class ShowCategoryForumController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowCategoryForumCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			UserInformation userInformation = (UserInformation) httpSession.getAttribute("userInformation");
			
			if (userInformation != null) {
				if (userInformation.getForumJoinedDate() == null) {
					userInformation = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("userInformation", userInformation);
				}
			}
			
			Forum category = null;
			if (command.getId() == null){
				category = getCommunityService().getFirstCategory();
			} else {
				category = getCommunityService().getCategory(new Forum(command.getId(), Type.CATEGORY));
			}
			model.put("category", category);

			List<Forum> subCategories = getCommunityService().getSubCategories(new Forum(category.getId(), Type.CATEGORY));
			model.put("subCategories", subCategories);

			if (subCategories.size() > 0) {
				HashMap<Integer, List<Forum>> forumsHashMap = new HashMap<Integer, List<Forum>>(0);
				forumsHashMap = getCommunityService().getForumsGroupByCategory((List<Integer>)ListBeanUtils.transformList(subCategories, "id"));
				model.put("forumsBySubCategories", forumsHashMap);
			}

			List<Forum> forums = new ArrayList<Forum>(0);
			forums = getCommunityService().getSubForums(category.getId());
			model.put("subForums", forums);

			HashMap<String, Object> statisticsHashMap = getCommunityService().getForumsStatistics();
			model.put("statisticsHashMap", statisticsHashMap);
		}catch (ApplicationThrowable applicationThrowable) {
			return new ModelAndView("error/ShowIndexForum", model);
		}

		return new ModelAndView("community/ShowCategoryForum", model);
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