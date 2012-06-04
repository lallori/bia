/*
 * AjaxController.java
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

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.UserMessageSearch;
import org.medici.docsources.common.util.ForumUtils;
import org.medici.docsources.common.util.HtmlUtils;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.UserMessage;
import org.medici.docsources.domain.UserMessage.UserMessageCategory;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.community.CommunityService;
import org.medici.docsources.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * AJAX Controller for Community.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Controller("CommunityAjaxController")
public class AjaxController {
	@Autowired
	private CommunityService communityService;
	@Autowired
	private UserService userService;

	/**
	 * 	
	 * @param volNum
	 * @param volLetExt
	 * @param folioNum
	 * @param folioMod
	 * @return
	 */
	@RequestMapping(value = "/community/CheckNewMessages", method = RequestMethod.GET)
	public ModelAndView checkNewMessages() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try{
			Long numberOfNewMessages = getCommunityService().checkNewMessages();
			model.put("numberOfNewMessages", numberOfNewMessages.toString());
			model.put("numberOfNewMessages", "0");
			model.put("newMessages", (numberOfNewMessages>0) ? "true" : "false");
		}catch(ApplicationThrowable th){
			model.put("numberOfNewMessages", "0");
			model.put("newMessages", "false");
		}

		return new ModelAndView("responseOK", model);		
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/community/GetForumChronology", method = RequestMethod.GET)
	public ModelAndView showForumChronology(@RequestParam(value="id", required=false) Integer id) {
		Map<String, Object> model = new HashMap<String, Object>();
		Forum forum = new Forum();
		
		try{
			if (id != null ) {
				forum = getCommunityService().getForum(id);
			} else {
				forum = getCommunityService().getFirstCategory();
			}

			model.put("id", forum.getId().toString());
			model.put("title", forum.getTitle());
			model.put("chronology", ForumUtils.getForumChronology(forum));
		}catch(ApplicationThrowable th){
			model.put("error", th.getMessage());
		}

		return new ModelAndView("responseOK", model);		
	}
	
	/**
	 * 
	 * @param httpSession
	 * @param alias
	 * @param sortingColumnNumber
	 * @param sortingDirection
	 * @param firstRecord
	 * @param length
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/community/ShowMessagesByCategoryPagination.json", method = RequestMethod.GET)
	public ModelAndView showMessagesByCategoryPagination(HttpSession httpSession,
											@RequestParam(value="category", required=false) UserMessageCategory category,
								   		 	@RequestParam(value="iSortCol_0", required=false) Integer sortingColumnNumber,
								   		 	@RequestParam(value="sSortDir_0", required=false) String sortingDirection,
								   		 	@RequestParam(value="iDisplayStart") Integer firstRecord,
								   		 	@RequestParam(value="iDisplayLength") Integer length) {
		Map<String, Object> model = new HashMap<String, Object>();

		Page page = null;
		PaginationFilter paginationFilter = new PaginationFilter(firstRecord,length, sortingColumnNumber, sortingDirection);

		try {
			page = getCommunityService().searchMessages(new UserMessageSearch(category), paginationFilter);
			
		} catch (ApplicationThrowable aex) {
			page = new Page(paginationFilter);
		}


		List<List<String>> resultList = new ArrayList<List<String>>();
		for (UserMessage currentUserMessage : (List<UserMessage>)page.getList()) {
			List<String> singleRow = new ArrayList<String>();
			// From
			singleRow.add(HtmlUtils.showMessage(currentUserMessage.getMessageId(), currentUserMessage.getSender()));         
			// Subject
			singleRow.add(HtmlUtils.showMessage(currentUserMessage.getMessageId(), currentUserMessage.getSubject()));
			// Date
			singleRow.add(currentUserMessage.getSendedDate().toString());      

			resultList.add(singleRow);
		}
		
		model.put("iEcho", "1");
		model.put("iTotalDisplayRecords", page.getTotal());
		model.put("iTotalRecords", page.getTotal());
		model.put("aaData", resultList);
		
		return new ModelAndView("responseOK", model);		
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
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
}