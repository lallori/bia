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
package org.medici.bia.controller.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.common.util.ForumUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.UserMessage.UserMessageCategory;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.user.UserService;
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
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
	public ModelAndView checkNewMessages(HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		httpServletRequest.setAttribute("persistentAccessLogDisabled", Boolean.TRUE);
		
		try{
			Long numberOfNewMessages = getCommunityService().checkNewMessages();
			model.put("numberOfNewMessages", numberOfNewMessages.toString());
			model.put("newMessages", (numberOfNewMessages>0) ? "true" : "false");
		}catch(ApplicationThrowable th){
			model.put("numberOfNewMessages", "0");
			model.put("newMessages", "false");
		}

		return new ModelAndView("responseOK", model);		
	}
	
	/**
	 * 
	 * @param forumId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/de/community/DeleteForum", method = RequestMethod.POST)
	public ModelAndView deleteForum(@RequestParam(value="forumId", required=false) Integer forumId,
										HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try{
			getCommunityService().deleteForum(forumId);
			model.put("operation", "OK");
			
			return new ModelAndView("responseOK", model);		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
	}

	@RequestMapping(value = "/community/DeletePost", method = RequestMethod.POST)
	public ModelAndView deleteForumPost(@RequestParam(value="postId", required=false) Integer postId, 
										@RequestParam(value="topicId", required=false) Integer topicId,
										HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		ForumTopic forumTopic = null;
		try{
			getCommunityService().deleteForumPost(postId);
			forumTopic = getCommunityService().getForumTopic(new ForumTopic(topicId));
			model.put("topicId", topicId);
			model.put("postId", postId);
			model.put("operation", "OK");
			if (forumTopic != null){
				model.put("topicUrl", HtmlUtils.getShowTopicForumHrefUrl(forumTopic));
			}
			return new ModelAndView("responseOK", model);		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
	}
	
	/**
	 * 
	 * @param topicId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(value = "/de/community/DeleteForumTopic", method = RequestMethod.POST)
	public ModelAndView deleteForumTopic(@RequestParam(value="topicId", required=false) Integer topicId,
										HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try{
			getCommunityService().deleteForumTopic(topicId);
			model.put("operation", "OK");
			
			return new ModelAndView("responseOK", model);		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
	}
	
	@RequestMapping(value = "/community/EditPost", method = RequestMethod.POST)
	public ModelAndView editForumPost(	@RequestParam(value="postId", required=false) Integer postId, 
										@RequestParam(value="forumId", required=false) Integer forumId,
										@RequestParam(value="topicId", required=false) Integer topicId,
										@RequestParam(value="subject", required=false) String subject,
										@RequestParam(value="text", required=false) String text,
										HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		ForumPost forumPost = new ForumPost(postId);
		forumPost.setForum(new Forum(forumId));
		forumPost.setTopic(new ForumTopic(topicId));
		forumPost.setIpAddress(httpServletRequest.getRemoteAddr());
		forumPost.setText(text);
		forumPost.setSubject(subject);

		try {
			if (postId.equals(0)) {
				forumPost = getCommunityService().addNewPost(forumPost);
			} else {
				forumPost = getCommunityService().editPost(forumPost);
			}
			model.put("topicId", forumPost.getTopic().getTopicId());
			model.put("postId", forumPost.getPostId());
			model.put("topicUrl", HtmlUtils.getShowTopicForumHrefUrl(forumPost.getTopic())); 
			model.put("operation", "OK");

			return new ModelAndView("responseOK", model);		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
	}
	
	@RequestMapping(value="/community/EraseMessages", method = RequestMethod.POST)
	public ModelAndView eraseMessages(@RequestParam("idToErase") String idToErase) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			if(!idToErase.equals("")){
				StringTokenizer stringTokenizer = new StringTokenizer(idToErase, "+");
				List<Integer> idElements = new ArrayList<Integer>();
				while(stringTokenizer.hasMoreTokens()){
					String current = stringTokenizer.nextToken();
					if(NumberUtils.isNumber(current)){
						idElements.add(NumberUtils.createInteger(current));
					}
				}
				
				getCommunityService().deleteMessages(idElements);
			}
		}catch(ApplicationThrowable ath){
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);
		}
		model.put("operation", "OK");
		return new ModelAndView("responseOK", model);
	}

	/**
	 * @return the communityService
	 */
	public CommunityService getCommunityService() {
		return communityService;
	}
	
	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}
	
	@RequestMapping(value = "/community/ReportForumPost", method = RequestMethod.POST)
	public ModelAndView reportForumPost(	@RequestParam(value="postId", required=false) Integer postId, 
										@RequestParam(value="forumId", required=false) Integer forumId,
										@RequestParam(value="topicId", required=false) Integer topicId,
										HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);

		try {
			if (!postId.equals(0)) {
				getCommunityService().reportForumPost(postId);
				model.put("operation", "OK");
				return new ModelAndView("responseOK", model);
			} else {
				model.put("operation", "KO");
				return new ModelAndView("responseKO", model);
			}		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
	}
	
	/**
	 * @param communityService the communityService to set
	 */
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/community/GetForumChronology", method = RequestMethod.GET)
	public ModelAndView getForumChronology(@RequestParam(value="forumId", required=false) Integer forumId) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Forum forum = new Forum();
		
		try{
			if (forumId != null ) {
				forum = getCommunityService().findForum(forumId);
			} else {
				forum = getCommunityService().getFirstCategory();
			}

			model.put("forumId", forum.getForumId().toString());
			model.put("title", forum.getTitle());
			model.put("chronology", ForumUtils.getForumChronology(forum));
			model.put("selectChronology", ForumUtils.getSelectForumChronology(forum.getForumParent()));
		}catch(ApplicationThrowable th){
			model.put("error", th.getMessage());
		}

		return new ModelAndView("responseOK", model);		
	}
	
	@RequestMapping(value = "/de/community/RenameForum", method = RequestMethod.POST)
	public ModelAndView renameForum(
			@RequestParam(value="forumId", required = true) Integer forumId,
			@RequestParam(value="title", required = true) String title,
			@RequestParam(value="description", required = true) String description,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try{
			getCommunityService().renameForum(forumId, title, description);
			model.put("operation", "OK");
			
			return new ModelAndView("responseOK", model);		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
	}
	
	@RequestMapping(value = "/de/community/RenameForumTopic", method = RequestMethod.POST)
	public ModelAndView renameForumTopic(
			@RequestParam(value="topicId", required = true) Integer topicId,
			@RequestParam(value="title", required = true) String title,
			HttpServletRequest httpServletRequest) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try{
			getCommunityService().renameForumTopic(topicId, title);
			model.put("operation", "OK");
			
			return new ModelAndView("responseOK", model);		
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("operation", "KO");
			return new ModelAndView("responseKO", model);		
		}
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
		Map<String, Object> model = new HashMap<String, Object>(0);

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
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/community/SubscribeForumTopic.json", method = RequestMethod.POST)
	public ModelAndView subscribeForumTopic(@RequestParam(value="forumTopicId", required=false) Integer forumTopicId) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Boolean subscription = Boolean.FALSE;
		
		try {
			subscription = getCommunityService().subscribeForumTopic(forumTopicId);

			model.put("forumTopicId", forumTopicId);
			model.put("subscription", subscription);
		}catch(ApplicationThrowable th){
			model.put("error", th.getMessage());
		}

		return new ModelAndView("responseOK", model);		
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/community/UnsubscribeForumTopic.json", method = RequestMethod.POST)
	public ModelAndView unsubscribeForumTopic(@RequestParam(value="forumTopicId", required=false) Integer forumTopicId) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		Boolean unsubscription = Boolean.FALSE;
		
		try {
			unsubscription = getCommunityService().unsubscribeForumTopic(forumTopicId);

			model.put("forumTopicId", forumTopicId);
			model.put("subscription", unsubscription);
		}catch(ApplicationThrowable th){
			model.put("error", th.getMessage());
		}

		return new ModelAndView("responseOK", model);		
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/community/WhoIsOnlineForum.json", method = RequestMethod.GET)
	public ModelAndView whoIsOnlineForum(){
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		try{
			Map<String, Object> whoIsOnlineHashMap = getCommunityService().getForumWhoIsOnline();
			model.put("countOnlineUsers", ((List<String>) whoIsOnlineHashMap.get("onlineUsers")).size());
			model.put("countGuestUsers", whoIsOnlineHashMap.get("guestUsers"));
		}catch(ApplicationThrowable th){
			model.put("error", th.getMessage());
		}
		
		return new ModelAndView("responseOK", model);
	}
}