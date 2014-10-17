/*
 * ShowForumController.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.community.ShowForumCommand;
import org.medici.bia.common.pagination.DocumentExplorer;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.SubType;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.Volume;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.manuscriptviewer.ManuscriptViewerService;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view a forum .
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
@Controller
@RequestMapping(value={"/community/ShowForum"})
public class ShowForumController {
	
	private static final Integer DEFAULT_ROWS_PER_PAGE = 10;
	
	@Autowired
	private CommunityService communityService;
	@Autowired
	private ManuscriptViewerService manuscriptViewerService;
	@Autowired
	private TeachingService teachingService;
	
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
	
	/**
	 * @return the manuscriptViewerService
	 */
	public ManuscriptViewerService getManuscriptViewerService() {
		return manuscriptViewerService;
	}
	
	/**
	 * @param manuscriptViewerService the manuscriptViewerService to set
	 */
	public void setManuscriptViewerService(
			ManuscriptViewerService manuscriptViewerService) {
		this.manuscriptViewerService = manuscriptViewerService;
	}
	
	/**
	 * @return the teachingService
	 */
	public TeachingService getTeachingService() {
		return teachingService;
	}

	/**
	 * @param teachingService the teachingService to set
	 */
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
	public ModelAndView setupForm(@ModelAttribute("command") ShowForumCommand command, HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Forum forum = new Forum(); 

		try {
			User userInformation = (User) httpSession.getAttribute("user");
			
			// RR (March 12th, 2014): we filter forum access in this controller so ShowForum.jsp is maintained pretty compact.
			// --> First we calculate the user authorities (none for anonymous user) and then we filter what to show or we
			// redirect to 'accessDenied' page.
			Set<Authority> userAuthorities = null;

			if (userInformation != null) {
				model.put("account", userInformation.getAccount());
				userAuthorities = getUserAuthorities(userInformation);
				if (userInformation.getForumJoinedDate() == null) {
					userInformation = getCommunityService().joinUserOnForum();
					httpSession.setAttribute("user", userInformation);
				}
			}
			
			if (command.getForumId() == null){
				forum = getCommunityService().getFirstCategory();
			} else {
				forum = getCommunityService().findForum(command.getForumId());
				if (Boolean.TRUE.equals(forum.getLogicalDelete()) ||
						(SubType.COURSE.equals(forum.getSubType()) && !Type.CATEGORY.equals(forum.getForumParent().getType()) && !getTeachingService().isForumInActiveCourse(command.getForumId()))) {
					// the forum to show was deleted or it is correspondent to a deactivated course
					return new ModelAndView("404", model); 
				}
				// we can update forum access informations
				forum = getCommunityService().getForumForView(command.getForumId());
			}

			if (forum.getType().equals(Type.CATEGORY)) {
				model.put("category", forum);

				if (forum.getOption().getCanHaveSubCategory()) {
					List<Forum> subCategories = getCommunityService().getSubCategories(new Forum(forum.getForumId()));
					// We filter forums with special access credentials
					List<Forum> visibleSubcategories = filterForumsForCurrentUser(subCategories, userAuthorities);
					model.put("subCategories", visibleSubcategories);

					//SubForums are extracted only if category is enabled to subForum...
					List<Integer> subCategoriesIdsEnabledToSubForums = new ArrayList<Integer>(0);
					for (Forum category : visibleSubcategories) {
						if (category.getOption().getCanHaveSubForum()) {
							subCategoriesIdsEnabledToSubForums.add(category.getForumId());
						}
					}

					Map<Integer, List<Forum>> forumsHashMap = getCommunityService().getForumsGroupByCategory(subCategoriesIdsEnabledToSubForums);
					//MD: To show the number of subforums that have one or more topics for the document forum
					model.put("documentSubForumsWithTopics", getCommunityService().getSubForumsNumberWithTopics(5));
					
					model.put("forumsBySubCategories", forumsHashMap);
					
					if (forum.getOption().getCanHaveSubForum()) {
						PaginationFilter paginationFilterForum = getPaginationFilter(command, forum.getOption().getPageLength() != null ? forum.getOption().getPageLength() : DEFAULT_ROWS_PER_PAGE, "dispositionOrder", true, true);
						Page page = getCommunityService().getSubForums(forum.getForumId(), paginationFilterForum);
						model.put("subForumsPage", page);
					}
				}
			} else if (forum.getType().equals(Type.FORUM)) {
				
				if (canAccessToForum(forum, userAuthorities)) {
					
					model.put("forum", forum);
					//MD: Prepare the Manuscript Viewer
					if (forum.getDocument() != null && !SubType.COURSE.equals(forum.getSubType())) {
						Document document = forum.getDocument();
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
								return new ModelAndView("error/ShowForum", model);
							}
						} else {
							model.put("documentExplorer", null);
						}
					} else if(forum.getVolume() != null) {
						Volume volume = forum.getVolume();
						if (volume.getDigitized()) {
							VolumeExplorer volumeExplorer = new VolumeExplorer(volume.getSummaryId(), volume.getVolNum(), volume.getVolLetExt());
							if (getManuscriptViewerService().findVolumeImageSpine(volume.getVolNum(), volume.getVolLetExt()) != null) {
								volumeExplorer.setImage(getManuscriptViewerService().findVolumeImageSpine(volume.getVolNum(), volume.getVolLetExt()));
							} else {
								volumeExplorer.setImage(new Image());
								volumeExplorer.getImage().setImageOrder(1);
								volumeExplorer.getImage().setImageType(ImageType.C);
							}
							volumeExplorer.setTotal(null);
							
							try {
								volumeExplorer = getManuscriptViewerService().getVolumeExplorer(volumeExplorer);
								model.put("volumeExplorer", volumeExplorer);
							} catch (ApplicationThrowable applicationThrowable) {
								model.put("applicationThrowable", applicationThrowable);
								return new ModelAndView("error/ShowForum", model);
							}
						} else {
							model.put("volumeExplorer", null);
						}
					}
					
					if (forum.getOption().getCanHaveSubForum()) {
						// All forum have group by excepted document...
						if (forum.getOption().getGroupBySubForum()) {
							PaginationFilter paginationFilterForum = null;
							Page page = null;
							if (SubType.COURSE.equals(forum.getSubType())) {
								paginationFilterForum = getPaginationFilter(command, forum.getOption().getPageLength() != null ? forum.getOption().getPageLength() : DEFAULT_ROWS_PER_PAGE, "courseForum.lastPost", false, true);
								page = getTeachingService().getCoursesElements(forum.getForumId(), paginationFilterForum);
							} else {
								paginationFilterForum = getPaginationFilter(command, forum.getOption().getPageLength() != null ? forum.getOption().getPageLength() : DEFAULT_ROWS_PER_PAGE, "lastPost", false, true);
								page = getCommunityService().getSubForums(forum.getForumId(), paginationFilterForum);
							}
							model.put("subForumsPage", page);
						} else {
							// paginationFilter to manage topics results..
							PaginationFilter paginationFilterTopic = getPaginationFilter(command, forum.getOption().getPageLength() != null ? forum.getOption().getPageLength() : DEFAULT_ROWS_PER_PAGE, "lastPost", false, false);
							Page topicPage = getCommunityService().getForumTopicsByParentForum(forum, paginationFilterTopic);
							model.put("subForumsTopicsPage", topicPage);
						}
					}
				} else {
					// TODO
					return new ModelAndView("403", model);
				}
				
			}

			if (forum.getOption().getCanHaveTopics()) {
				// paginationFilter to manage topics results..
				PaginationFilter paginationFilterTopic = getPaginationFilter(command, forum.getOption().getPageLength() != null ? forum.getOption().getPageLength() : DEFAULT_ROWS_PER_PAGE, "lastUpdate", false, false);
				Page topicPage = getCommunityService().getForumTopics(forum, paginationFilterTopic);
				model.put("topicsPage", topicPage);
				
				if (SubType.COURSE.equals(forum.getSubType())) {
					Map<Integer, CourseTopicMode> topicsMap = getTeachingService().getCourseTopicsMode((List<ForumTopic>)topicPage.getList());
					model.put("topicsMap", topicsMap);
					if (topicsMap.containsValue(CourseTopicMode.I) || topicsMap.containsValue(CourseTopicMode.R) || topicsMap.containsValue(CourseTopicMode.C)) {
						// the topicsMap contains a course transcription (the help resources does not contain transcriptions)
						model.put("containsTranscriptionTopic", true);
					}
				}
			}

			Map<String, Object> statisticsHashMap = getCommunityService().getForumsStatistics();
			model.put("statisticsHashMap", statisticsHashMap);
			
			Map<String, Object> whoIsOnlineHashMap = getCommunityService().getForumWhoIsOnline();
			model.put("whoIsOnlineHashMap", whoIsOnlineHashMap);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/ShowForum", model);
		}

		if (ObjectUtils.toString(command.getCompleteDOM()).equals(Boolean.TRUE.toString())) {
			return new ModelAndView("community/ShowForumCompleteDOM", model);
		} else {
			return new ModelAndView("community/ShowForum", model);
		}
	}
	

	/* Privates */
	
	@SuppressWarnings("serial")
	private boolean canAccessToForum(final Forum forum, Set<Authority> authorities) {
		return filterForumsForCurrentUser(new ArrayList<Forum>() {{add(forum);}}, authorities).size() == 1;
	}
	
	private List<Forum> filterForumsForCurrentUser(List<Forum> all, Set<Authority> authorities) {
		List<Forum> allowed = new ArrayList<Forum>();
		for (Forum current : all) {
			switch (current.getSubType()) {
				case COURSE:
					if (authorities != null && authorities.size() > 0 && 
						(authorities.contains(Authority.ADMINISTRATORS) 
						|| authorities.contains(Authority.TEACHERS)
						|| authorities.contains(Authority.STUDENTS))) {
						
						allowed.add(current);
					}
					break;
				default:
					allowed.add(current);
					break;
			}
		}
		return allowed;
	}
	
	private PaginationFilter getPaginationFilter(ShowForumCommand command, Integer defaultRowsPerPage, String sortingField, boolean ascSorting, boolean isForumFilter) {
		PaginationFilter paginationFilter = new PaginationFilter();
		if (!isForumFilter) {
			if (command.getTopicsForPage() != null) {
				paginationFilter.setElementsForPage(command.getTopicsForPage());
			} else {
				paginationFilter.setElementsForPage(defaultRowsPerPage);
				command.setTopicsForPage(paginationFilter.getElementsForPage());
			}
			if (command.getTopicPageNumber() != null) {
				paginationFilter.setThisPage(command.getTopicPageNumber());
			} else {
				paginationFilter.setThisPage(1);
				command.setTopicPageNumber(paginationFilter.getThisPage());
			}
			if (command.getTopicPageTotal() != null) {
				paginationFilter.setPageTotal(command.getTopicPageTotal());
			} else {
				paginationFilter.setPageTotal(null);
			}
		} else {
			if (command.getForumsForPage() != null) {
				paginationFilter.setElementsForPage(command.getForumsForPage());
			} else {
				paginationFilter.setElementsForPage(defaultRowsPerPage);
				command.setForumsForPage(paginationFilter.getElementsForPage());
			}
			if (command.getForumPageNumber() != null) {
				paginationFilter.setThisPage(command.getForumPageNumber());
			} else {
				paginationFilter.setThisPage(1);
				command.setForumPageNumber(paginationFilter.getThisPage());
			}
			if (command.getForumPageTotal() != null) {
				paginationFilter.setPageTotal(command.getForumPageTotal());
			} else {
				paginationFilter.setPageTotal(null);
			}
		}
		
		paginationFilter.addSortingCriteria(sortingField, ascSorting ? "asc" : "desc");
		return paginationFilter;
	}
	
	private Set<UserAuthority.Authority> getUserAuthorities(User user) {
		Set<UserAuthority.Authority> authorities = new HashSet<UserAuthority.Authority>();
		if (user != null) {
			try {
				List<UserRole> roles = getCommunityService().getUserRoles(user.getAccount());
				for(UserRole role : roles) {
					authorities.add(role.getUserAuthority().getAuthority());
				}
			} catch (ApplicationThrowable e) {
				// TODO: what to do?
			}
		}
		return authorities;
	}
}
