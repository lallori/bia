/*
 * SimpleSearchForumController.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
import java.util.UUID;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.medici.bia.command.community.SimpleSearchForumPostCommand;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.SimpleSearchForumPost;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
@Controller
@RequestMapping("/community/SimpleSearchForumPost")
public class SimpleSearchForumPostController {
	@Autowired
	private CommunityService communityService;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * This controller act as a dispatcher for result view.
	 *  
	 * @param command
	 * @param result
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView processSubmit(@ModelAttribute("command") SimpleSearchForumPostCommand command) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		 
		try {
			command.setSearchForumAllText(URIUtil.decode(command.getSearchForumAllText(), "UTF-8"));
		} catch (URIException uriException) {
			logger.debug(uriException);
		}

		model.put("yourSearch", command.getSearchForumAllText());
		String [] toHighlight = StringUtils.split(command.getSearchForumAllText(), " ");
		model.put("toHighlight", toHighlight);
		
		if(command.getSearchForumAllText().contains("\"")){
			command.setSearchForumAllText(command.getSearchForumAllText().replace("\"", "\\\""));
		}
		// This number is used to generate an unique id for new search
		UUID uuid = UUID.randomUUID();
		command.setSearchUUID(uuid.toString());
		model.put("searchUUID", uuid.toString());

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

		try {
			if(command.getTopicId() == null) {
				page = getCommunityService().searchForumPosts(new SimpleSearchForumPost(command.getSearchForumAllText()), paginationFilter);
			} else {
				page = getCommunityService().searchForumPosts(new SimpleSearchForumPost(command.getSearchForumAllText(), command.getTopicId()), paginationFilter);
			}
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			page = new Page(paginationFilter);
		}

		model.put("simpleSearchResultPage", page);

		return new ModelAndView("community/SearchResultForumPost",model);
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
