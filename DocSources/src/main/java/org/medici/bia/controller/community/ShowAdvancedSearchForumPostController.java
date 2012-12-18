/*
 * ShowAdvancedSearchForumPostController.java
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
 * 
 */
package org.medici.bia.controller.community;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.medici.bia.command.community.AdvancedSearchForumPostCommand;
import org.medici.bia.common.search.AdvancedSearchForum;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.SearchFilter;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Show Advanced Search".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/community/ShowAdvancedSearchForumPost")
public class ShowAdvancedSearchForumPostController {
	@Autowired
	private CommunityService communityService;
	@Autowired(required = false)
	@Qualifier("advancedSearchValidator")
	private Validator validator;
	
	/**
	 * This method return form view with advanced search filled. This is used for
	 * first advanced search page and refine function.
	 * 
	 * @param command
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupPage(@ModelAttribute("command") AdvancedSearchForumPostCommand command, HttpSession session){
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		Forum forum = new Forum();
		List<Forum> subCategories = null;
		SearchFilter searchFilter = null;
		
		try{
			forum = getCommunityService().getFirstCategory();
			if(forum.getType().equals(Type.CATEGORY)){
				if(forum.getOption().getCanHaveSubForum()){
					subCategories = getCommunityService().getSubCategories(forum);
					model.put("subCategories", subCategories);
					List<Integer> subCategoriesIdsEnabledToSubForums = new ArrayList<Integer>(0);
					for (Forum category : subCategories) {
						if (category.getOption().getCanHaveSubForum()) {
							subCategoriesIdsEnabledToSubForums.add(category.getForumId());
						}
					}

					Map<Integer, List<Forum>> forumsHashMap = new HashMap<Integer, List<Forum>>(0);
					forumsHashMap = getCommunityService().getForumsGroupByCategory(subCategoriesIdsEnabledToSubForums);
					//MD: This is to populate the select
					model.put("subForums", forumsHashMap);
				}
			}

		}catch(ApplicationThrowable applicationThrowable){
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/AdvancedSearchForumPost", model);
		}
		
		// we get our map which contains all user's filter used at runtime. 
		Map<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);
		command.setSearchUUID(UUID.randomUUID().toString());
		command.setNewSearch(Boolean.TRUE);
		if(command.getAllTerms() == null){
			command.setAllTerms("true");
		}
		if(command.getWordsType() == null){
			command.setWordsType("SUBJECT_TEXT");
		}
		if(command.getDisplayResults() == null){
			command.setDisplayResults("Topics");
		}
		if(command.getSortResults() == null){
			command.setSortResults("POST_TIME");
		}
		if(command.getOrder() == null){
			command.setOrder("asc");
		}
		if(command.getLimitResults() == null){
			command.setLimitResults(0);
		}
		if(command.getReturnFirst() == null){
			command.setReturnFirst(300);
		}
		searchFilter = new SearchFilter(0, SearchType.FORUM);
		searchFilter.setDateCreated(new Date());
		searchFilter.setDateUpdated(new Date());
		searchFilter.setId(new Integer(0));
		searchFilter.setFilterData(new AdvancedSearchForum());
		searchFilter.setUser(new User(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()));
		// we update user map
		searchFilterMap.put(command.getSearchUUID(), searchFilter);
		// we update information in session
		session.setAttribute("searchFilterMap", searchFilterMap);
		
		model.put("searchFilter", searchFilter);
		model.put("UUID", command.getSearchUUID());
		

		return new ModelAndView("community/ShowAdvancedSearchForumPost", model);
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
	public Validator getAdvancedSearchDocumentsValidator() {
		return validator;
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