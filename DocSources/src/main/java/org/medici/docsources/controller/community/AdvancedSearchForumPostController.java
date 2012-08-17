/*
 * AdvancedSearchForumPostController.java
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
package org.medici.docsources.controller.community;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.medici.docsources.command.community.AdvancedSearchForumPostCommand;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.AdvancedSearchForum;
import org.medici.docsources.domain.SearchFilter;
import org.medici.docsources.domain.SearchFilter.SearchType;
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
 * Controller for action "Advanced Search".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/community/AdvancedSearchForumPost")
public class AdvancedSearchForumPostController {
	@Autowired
	private CommunityService communityService;
	@Autowired(required = false)
	@Qualifier("advancedSearchValidator")
	private Validator validator;
	
	/**
	 * 
	 * @param command
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView executeSearch(@Valid @ModelAttribute("command") AdvancedSearchForumPostCommand command, BindingResult result, HttpSession session) {
		Map<String, Object> model = new HashMap<String, Object>();
		SearchFilter searchFilter = null;  

		// we prelevate our map which contains all user's filter used at runtime. 
		HashMap<String, SearchFilter> searchFilterMap = (session.getAttribute("searchFilterMap") != null) ? (HashMap<String, SearchFilter>)session.getAttribute("searchFilterMap") : new HashMap<String, SearchFilter>(0);
		
		// if searchFilter is present in map we get  
		if (searchFilterMap.get(command.getSearchUUID()) != null) {
			searchFilter = searchFilterMap.get(command.getSearchUUID());
		}else{
			searchFilter = new SearchFilter(SearchType.FORUM);
			searchFilter.setDateCreated(new Date());
			searchFilter.setDateUpdated(new Date());
		}
		if(command.getNewSearch() == true){
			// we update runtime filter with input from form 
			AdvancedSearchForum advancedSearchForum = new AdvancedSearchForum();
			//we populate the advanced search forum
			advancedSearchForum.initFromAdvancedSearchForum(command);
			searchFilter.setFilterData(advancedSearchForum);
			// we update user map
			searchFilterMap.put(command.getSearchUUID(), searchFilter);
		}
		// we update information in session
		session.setAttribute("searchFilterMap", searchFilterMap);
		model.put("yourSearch", searchFilter.getFilterData().toString());
		
		PaginationFilter paginationFilter = new PaginationFilter();
		if (command.getResultsForPage() != null) {
			paginationFilter.setElementsForPage(command.getResultsForPage());
		} else {
			paginationFilter.setElementsForPage(new Integer(10));
		}
		if (command.getResultPageNumber() != null) {
			paginationFilter.setThisPage(command.getResultPageNumber());
		} else {
			paginationFilter.setThisPage(new Integer(1));
		}
		if (command.getResultPageTotal() != null) {
			paginationFilter.setPageTotal(command.getResultPageTotal());
		} else {
			paginationFilter.setPageTotal(null);
		}
		if(command.getSortResults().equals("POST_TIME")){
			paginationFilter.addSortingCriteria("dateCreated", command.getOrder());
		} else if(command.getSortResults().equals("AUTHOR")){
			paginationFilter.addSortingCriteria("author", command.getOrder());
		} else if(command.getSortResults().equals("FORUM")){
			paginationFilter.addSortingCriteria("forum.title", command.getOrder());
		} else if(command.getSortResults().equals("TOPIC_TITLE")){
			paginationFilter.addSortingCriteria("topic.subject", command.getOrder());
		} else if(command.getSortResults().equals("POST_SUBJECT")){
			paginationFilter.addSortingCriteria("subject", command.getOrder());
		}
		
		Page page = new Page(paginationFilter);
		
		try{
			page = getCommunityService().searchForumPosts(searchFilter.getFilterData(), paginationFilter);
		}catch(ApplicationThrowable applicationThrowable){
			model.put("applicationThrowable", applicationThrowable);
			page = new Page(paginationFilter);
		}

		model.put("searchResultPage", page);
		
		return new ModelAndView("community/AdvancedSearchResultForumPost", model);
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