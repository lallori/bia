/*
 * AdminService.java
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
package org.medici.bia.service.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.AccessLogSearch;
import org.medici.bia.common.search.Search;
import org.medici.bia.domain.AccessLog;
import org.medici.bia.domain.ActivationUser;
import org.medici.bia.domain.ApprovationUser;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface AdminService {

	/**
	 * 
	 * @param user
	 * @param userInformation
	 * @throws ApplicationThrowable
	 */
	User addNewUser(User user) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	User approveNewUser(User user) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	User approveUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param currentDate
	 * @throws ApplicationThrowable
	 */
	void createAccessLogDailyStatistics(Date currentDate) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @param userInformation
	 * @throws ApplicationThrowable
	 */
	User editUser(User user, String originalAccount) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param user
	 * @throws ApplicationThrowable
	 */
	User editUserMail(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param idAccessLog
	 * @return
	 * @throws ApplicationThrowable
	 */
	AccessLog findAccessLog(Integer idAccessLog) throws ApplicationThrowable;


	/**
	 * This method searchs for user to be activated. The condition is composed
	 * of "active" flag equals false and "mail sended" flag equals false.
	 *
	 * @return The {@link java.util.List} of users that needs to be activated 
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	List<ActivationUser> findActivationUsers() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<ForumPostNotified> findForumPostRepliedNotNotified() throws ApplicationThrowable;

	/**
	 * 
	 * @param numberMaxOfDay
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<Date> findMissingStatisticsDate(Integer numberMaxOfDay) throws ApplicationThrowable;

	/**
	 * 
	 * @param account
	 * @return
	 * @throws ApplicationThrowable
	 */
	User findUser(String account) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	List<User> findUsers(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	Page findUsers(User user, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<ApprovationUser> findUsersApprovedNotMailed() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<ApprovationUser> findUsersToApprove() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<UserAuthority> getAuthorities() throws ApplicationThrowable;

	/**
	 * Extracts all months available.
	 *  
	 * @return {@link java.util.List} of {@link org.medici.bia.domain.Month}
	 * object
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @param accessLogSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page searchAccessLog(AccessLogSearch accessLogSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page searchUser(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page searchWhoIsOnline(PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param usersToApprove
	 * @throws ApplicationThrowable
	 */
	void sendApprovationMessage(List<ApprovationUser> usersToApprove) throws ApplicationThrowable;

	/**
	 * 
	 * @param hashMap
	 * @throws ApplicationThrowable
	 */
	void updateApplicationProperties(Map<String, String> hashMap) throws ApplicationThrowable;
}
