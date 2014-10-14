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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.community.ShowTopicForumCommand;
import org.medici.bia.common.access.ApplicationAccessContainer;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.util.CourseUtils;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
@RequestMapping(value={"/community/ShowTopicForum", "teaching/ShowTopicForum"})
public class ShowTopicForumController {
	@Autowired
	private ApplicationAccessContainer applicationAccessContainer;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
	@Autowired
	private TeachingService teachingService;
	
	public ApplicationAccessContainer getApplicationAccessContainer() {
		return applicationAccessContainer;
	}

	public void setApplicationAccessContainer(
			ApplicationAccessContainer applicationAccessContainer) {
		this.applicationAccessContainer = applicationAccessContainer;
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

	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setTeachingService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowTopicForumCommand command, BindingResult result, HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			User user = (User) httpServletRequest.getSession().getAttribute("user");
					//httpSession.getAttribute("user");

			if (user != null) {
				if (user.getForumJoinedDate() == null) {
					user = getCommunityService().joinUserOnForum();
					httpServletRequest.getSession().setAttribute("user", user);
				}
			}
			
			// Control to anonymous access
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().getClass().getName().equals("java.lang.String")) {
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

			ForumTopic forumTopic = getCommunityService().getForumTopicForView(command.getTopicId());
			model.put("topic", forumTopic);
			
			model.put("subscribed", getCommunityService().ifTopicSubscribed(forumTopic.getTopicId()));
			
			if (forumTopic.getDocument() != null || forumTopic.getForum().getDocument() != null) {
				//MD: Prepare the Manuscript Viewer
				Document document = forumTopic.getDocument() != null ? forumTopic.getDocument() : forumTopic.getForum().getDocument();
				if (getManuscriptViewerService().findDocumentImageThumbnail(document) != null) {
					DocumentExplorer documentExplorer = new DocumentExplorer(document.getEntryId(), document.getVolume().getVolNum(), document.getVolume().getVolLetExt());
					documentExplorer.setImage(new Image());
					documentExplorer.getImage().setInsertNum(document.getInsertNum());
					documentExplorer.getImage().setInsertLet(document.getInsertLet());
					documentExplorer.getImage().setImageProgTypeNum(document.getFolioNum());
					documentExplorer.getImage().setMissedNumbering(document.getFolioMod());
					documentExplorer.getImage().setImageRectoVerso(document.getFolioRectoVerso() == null ? null : Image.ImageRectoVerso.convertFromString(document.getFolioRectoVerso().toString()));
					documentExplorer.getImage().setImageType(ImageType.C);
					documentExplorer.setTotal(null);
					
					try {
						documentExplorer = getManuscriptViewerService().getDocumentExplorer(documentExplorer);
						model.put("documentExplorer", documentExplorer);
					} catch (ApplicationThrowable applicationThrowable) {
						model.put("applicationThrowable", applicationThrowable);
						return new ModelAndView("error/ShowTopicForum", model);
					}
				} else {
					model.put("documentExplorer", null);
				}
				
			} else if(forumTopic.getAnnotation() != null) {
				Annotation annotation = forumTopic.getAnnotation();
				Image image = new Image();
				image = getManuscriptViewerService().findImage(annotation.getImage().getImageId());
				VolumeExplorer volumeExplorer = new VolumeExplorer(image.getVolNum(), image.getVolLetExt());
				volumeExplorer.setSummaryId(null);
				volumeExplorer.setImage(new Image());
				volumeExplorer.getImage().setImageOrder(image.getImageOrder());
				volumeExplorer.getImage().setStoragePath(image.getStoragePath());
				try {
					volumeExplorer = getManuscriptViewerService().getVolumeExplorer(volumeExplorer);
					model.put("volumeExplorer", volumeExplorer);
				} catch (ApplicationThrowable applicationThrowable) {
					model.put("applicationThrowable", applicationThrowable);
					return new ModelAndView("error/ShowTopic", model);
				}
			}
			
			if (httpServletRequest.getServletPath().startsWith("/teaching")) {
				CourseTopicOption option = getTeachingService().getCourseTranscriptionTopicOption(forumTopic.getForum().getForumId());
				if (option != null) {
					model.put("courseTranscriptionURL", CourseUtils.getCourseTranscriptionURL(option));
				}
			}
			
			Page postsPage = getCommunityService().getForumPostsFromTopic(forumTopic, paginationFilterTopic);
			model.put("postsPage", postsPage);
			
			Set<String> accounts = new HashSet<String>();
			
			for(ForumPost post : ((List<ForumPost>)postsPage.getList())) {
				accounts.add(post.getUser().getAccount());
			}
			Map<String, UserAuthority> maxAuthorities = accounts.size() > 0 ? getCommunityService().findUsersMaximumAuthority(accounts) : new HashMap<String, UserAuthority>();
			model.put("maxAuthorities", maxAuthorities);
			
			model.put("onlineUsers", applicationAccessContainer.getCommunityOnlineUsers());
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

}