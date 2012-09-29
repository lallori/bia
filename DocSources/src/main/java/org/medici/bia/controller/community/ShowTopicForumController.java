/*
 * ShowTopicForumController.java
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
package org.medici.bia.controller.community;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.community.ShowTopicForumCommand;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.User;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view a specific topic on a forum.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/community/ShowTopicForum")
public class ShowTopicForumController {
	@Autowired
	private CommunityService communityService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowTopicForumCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			User user = (User) httpSession.getAttribute("user");

			if (user != null) {
				if (user.getForumJoinedDate() == null) {
					user = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("user", user);
				}
			}
			
			// Control to anonymous access
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().getName().toString().equals("java.lang.String")) {
				model.put("account", null);
			} else {
				model.put("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			}

			PaginationFilter paginationFilterTopic = new PaginationFilter();
			if (command.getPostsForPage() != null) {
				paginationFilterTopic.setElementsForPage(command.getPostsForPage());
			} else {
				paginationFilterTopic.setElementsForPage(new Integer(10));
				command.setPostsForPage(paginationFilterTopic.getElementsForPage());
			}
			if (command.getPostPageNumber() != null) {
				paginationFilterTopic.setThisPage(command.getPostPageNumber());
			} else {
				paginationFilterTopic.setThisPage(new Integer(1));
				command.setPostPageNumber(paginationFilterTopic.getThisPage());
			}
			if (command.getPostPageTotal() != null) {
				paginationFilterTopic.setPageTotal(command.getPostPageTotal());
			} else {
				paginationFilterTopic.setPageTotal(null);
			}
			paginationFilterTopic.addSortingCriteria("postId", "asc");

			ForumTopic forumTopic = getCommunityService().getForumTopic(new ForumTopic(command.getTopicId()));
			model.put("topic", forumTopic);
			
			if(forumTopic.getDocument() != null || forumTopic.getForum().getDocument() != null){
				//MD: Prepare the Manuscript Viewer
				Document document = forumTopic.getForum().getDocument();
				if(getManuscriptViewerService().findDocumentImageThumbnail(document) != null){
					DocumentExplorer documentExplorer = new DocumentExplorer(document.getEntryId(), document.getVolume().getVolNum(), document.getVolume().getVolLetExt());
					documentExplorer.setImage(new Image());
					documentExplorer.getImage().setImageProgTypeNum(document.getFolioNum());
					documentExplorer.getImage().setImageType(ImageType.C);
					documentExplorer.setTotal(null);
				
					try {
						documentExplorer = getManuscriptViewerService().getDocumentExplorer(documentExplorer);
		
						model.put("documentExplorer", documentExplorer);
					} catch (ApplicationThrowable applicationThrowable) {
						model.put("applicationThrowable", applicationThrowable);
						return new ModelAndView("error/ShowTopicForum", model);
					}
				}else{
					model.put("documentExplorer", null);
				}
			}
			
			Page postsPage = getCommunityService().getForumPostsFromTopic(forumTopic, paginationFilterTopic);
			model.put("postsPage", postsPage);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowTopicForum", model);
		}

		if (ObjectUtils.toString(command.getCompleteDOM()).equals(Boolean.TRUE.toString())) {
			return new ModelAndView("community/ShowTopicForumCompleteDOM", model);
		} else {
			return new ModelAndView("community/ShowTopicForum", model);
		}
	}

	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	/**
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(
			ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}

	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}

	/**
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
	}
}