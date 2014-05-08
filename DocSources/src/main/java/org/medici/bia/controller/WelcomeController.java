/*
 * WelcomeController.java
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
package org.medici.bia.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.community.CommunityService;
import org.medici.bia.service.teaching.TeachingService;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for action "Welcome". 
 *  
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/Welcome")
public class WelcomeController {
	
	@Autowired 
	private CommunityService communityService;
	@Autowired 
	private TeachingService teachingService;
	@Autowired
	private UserService userService;
	
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
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView setupForm(HttpSession httpSession) {
		Map<String, Object> model = new HashMap<String, Object>(0);
		try {
			String account = null;
			User user = (User) httpSession.getAttribute("user");
			if (user != null) {
				account = user.getAccount();
			}
			
			boolean canAccessTeaching = getUserService().canAccessTeachingModule(account);

			Map<String, List<?>> forumStatistics = getCommunityService().getForumStatistics(10);
			model.put("forumStatistics", forumStatistics);
			
			if (canAccessTeaching) {
				Map<String, List<?>> teachingForumStatistics = getTeachingService().getTeachingForumStatistics(10, account);
				model.put("teachingForumStatistics", teachingForumStatistics);
			}
			
			LocalDate now = new LocalDate();
			now.minusMonths(1).toDateMidnight().toDate();
			
			Map<String, Long> lastLogonDBStatistics = getCommunityService().getDatabaseStatistics(DateUtils.getLastLogonDate());
			model.put("lastLogonDBStatistics", lastLogonDBStatistics);
			Map<String, String> lastLogonUrls = HtmlUtils.generateAdvancedSearchLinks(DateUtils.getLastLogonDate());
			model.put("lastLogonUrls", lastLogonUrls);

			Map<String, Long> currentWeekDBStatistics = getCommunityService().getDatabaseStatistics(DateUtils.getFirstDayOfCurrentWeek());
			model.put("currentWeekDBStatistics", currentWeekDBStatistics);
			Map<String, String> currentWeekUrls = HtmlUtils.generateAdvancedSearchLinks(DateUtils.getFirstDayOfCurrentWeek());
			model.put("currentWeekUrls", currentWeekUrls);

			Map<String, Long> currentMonthDBStatistics = getCommunityService().getDatabaseStatistics(DateUtils.getFirstDayOfCurrentMonth());
			model.put("currentMonthDBStatistics", currentMonthDBStatistics);
			Map<String, String> currentMonthUrls = HtmlUtils.generateAdvancedSearchLinks(DateUtils.getFirstDayOfCurrentMonth());
			model.put("currentMonthUrls", currentMonthUrls);
		} catch (ApplicationThrowable applicationThrowable) {
			model.put("applicationThrowable", applicationThrowable);
			return new ModelAndView("error/Welcome", model);
		}
		return new ModelAndView("Welcome", model);
	}

}