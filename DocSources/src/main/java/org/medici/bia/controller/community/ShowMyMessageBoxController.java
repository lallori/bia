/*
 * ShowMyInboxController.java
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

import org.apache.commons.lang.ObjectUtils;
import org.medici.bia.command.community.ShowMyMessageBoxCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.domain.UserMessage.UserMessageCategory;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller to view user inbox messages.
 * It manages View and request's elaboration process.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/community/ShowMyMessageBox")
public class ShowMyMessageBoxController {
	@Autowired
	private CommunityService communityService;
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(@ModelAttribute("command") ShowMyMessageBoxCommand command) {
		Map<String, Object> model = new HashMap<String, Object>();
		
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
		
		Page page = new Page(paginationFilter);
		
		UserMessageSearch userMessageSearch = new UserMessageSearch();
//		userMessageSearch.setRecipient(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		if(command.getCategory() != null){
			if(command.getCategory().equals("inbox")){
				userMessageSearch.setUserMessageCategory(UserMessageCategory.INBOX);
			}else if(command.getCategory().equals("outbox")){
				userMessageSearch.setUserMessageCategory(UserMessageCategory.OUTBOX);
			}
		}else
			userMessageSearch.setUserMessageCategory(UserMessageCategory.INBOX);
		
		try{
			page = getCommunityService().searchMessages(userMessageSearch, paginationFilter);
		}catch(ApplicationThrowable applicationThrowable){
			model.put("applicationThrowable", applicationThrowable);
			page = new Page(paginationFilter);
		}
		
		model.put("messageboxPage", page);

		if (ObjectUtils.toString(command.getCompleteDOM()).equals(Boolean.TRUE.toString())) {
			return new ModelAndView("community/ShowMyMessageBoxCompleteDOM", model);
		} else {
			return new ModelAndView("community/ShowMyMessageBox", model);
		}
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