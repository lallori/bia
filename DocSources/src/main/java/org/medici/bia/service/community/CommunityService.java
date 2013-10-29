/*
 * CommunityService.java
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
package org.medici.bia.service.community;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.domain.EmailMessageUser;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.ReportedForumPost;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.UserMessage.RecipientStatus;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public interface CommunityService {

	/**
	 * 
	 * @param forum
	 * @param parentForum
	 * @return
	 * @throws ApplicationThrowable
	 */
	Forum addNewForum(Forum forum, Forum parentForum) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumPost addNewPost(ForumPost forumPost) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userMessage
	 * @param status
	 * @return
	 * @throws ApplicationThrowable
	 */
	UserMessage changeStatusMessage(UserMessage userMessage, RecipientStatus status) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	Long checkNewMessages() throws ApplicationThrowable;

	/**
	 * 
	 * @param emailMessageUser
	 * @throws ApplicationThrowable
	 */
	void createNewEmailMessage(EmailMessageUser emailMessageUser, List<String> accounts) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param emailMessageUser
	 * @throws ApplicationThrowable
	 */
	void createNewEmailMessageUserForAll(EmailMessageUser emailMessageUser) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userRole
	 * @param emailMessageUser
	 * @throws ApplicationThrowable
	 */
	void createNewEmailMessageUserFromUserRole(List<String> userRole, EmailMessageUser emailMessageUser) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	UserMessage createNewMessage(UserMessage userMessage) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumId
	 * @throws ApplicationThrowable
	 */
	void deleteForum(Integer forumId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param postId
	 * @throws ApplicationThrowable
	 */
	void deleteForumPost(Integer postId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param topicId
	 * @throws ApplicationThrowable
	 */
	void deleteForumTopic(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	void deleteMessage(Integer userMessageId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param idElementsToRemove
	 * @throws ApplicationThrowable
	 */
	void deleteMessages(List<Integer> idElementsToRemove) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumPost editPost(ForumPost forumPost) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param topicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumPost findFirstPostTopic(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumPost findPost(Integer postId) throws ApplicationThrowable;
	
	/**
	 * Given in input user account, this method returns the user object.
	 * 
	 * @param account the {@link java.lang.String} user account that we are searching 
	 * @return
	 * @throws org.medici.bia.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	User findUser(String account) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param accountId
	 * @return
	 * @throws ApplicationThrowable
	 */
	UserAuthority findUserMaximumAuthority(String accountId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param messageId
	 * @return
	 * @throws ApplicationThrowable
	 */
	UserMessage findUserMessage(Integer messageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<User> findUsersToBeNotified(Integer postId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<UserAuthority> getAuthorities() throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	Forum getCategory(Forum category) throws ApplicationThrowable;

	/**
	 * 
	 * @param lastLogonDate
	 * @return
	 * @throws ApplicationThrowable
	 */
	Map<String, Long> getDatabaseStatistics(Date lastLogonDate) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	Forum getFirstCategory() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	Forum getForum(Integer id) throws ApplicationThrowable;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	Forum getForumForView(Integer id) throws ApplicationThrowable;

	/**
	 * 
	 * @param letter
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page getForumMembers(String letter, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param text
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page getForumMembersByText(String text, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param postId
	 * @throws ApplicationThrowable
	 */
	ForumPost getForumPost(Integer postId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumTopic
	 * @param paginationFilterPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page getForumPostsFromTopic(ForumTopic forumTopic, PaginationFilter paginationFilterPost) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<Forum> getForumsByType(Type type) throws ApplicationThrowable;

	/**
	 * 
	 * @param categoriesIds
	 * @return
	 * @throws ApplicationThrowable
	 */
	Map<Integer, List<Forum>> getForumsGroupByCategory(List<Integer> categoriesIds) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	Map<String, Object> getForumsStatistics() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param numberOfElements
	 * @return
	 * @throws ApplicationThrowable
	 */
	Map<String, List<?>> getForumStatistics(Integer numberOfElements) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	Map<String, Object> getForumWhoIsOnline() throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumTopic getForumTopic(ForumTopic forumTopic) throws ApplicationThrowable;
	
	/**
	 * @param forumTopicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumTopic getForumTopicById(Integer forumTopicId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param forumTopic
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumTopic getForumTopicForView(ForumTopic forumTopic) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param forum
	 * @param paginationFilterPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page getForumTopics(Forum forum, PaginationFilter paginationFilterTopics) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param forum
	 * @param paginationFilterTopic
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page getForumTopicsByParentForum(Forum forum, PaginationFilter paginationFilterTopics) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	Forum getMostActiveForumByUser(User user) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumTopic getMostActiveTopicByUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<Forum> getSubCategories(Forum forum) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param integer
	 * @return
	 * @throws ApplicationThrowable
	 */
	List<Forum> getSubForums(Integer forumParentId) throws ApplicationThrowable;

	/**
	 * 
	 * @param integer
	 * @return
	 * @throws ApplicationThrowable
	 */
	Page getSubForums(Integer forumParentId, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumId
	 * @return
	 * @throws ApplicationThrowable
	 */
	Long getSubForumsNumberWithTopics(Integer forumId) throws ApplicationThrowable;

	/**
	 * 
	 * @param postId
	 * @return
	 * @throws ApplicationThrowable
	 */
	Boolean ifPostIsParent(Integer postId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param forumTopicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	Boolean ifTopicSubscribed(Integer forumTopicId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	User joinUserOnForum()throws ApplicationThrowable;
	
	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	UserMessage replyMessage(UserMessage userMessage, Integer parentUserMessageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumPost
	 * @return
	 * @throws ApplicationThrowable
	 */
	ForumPost replyPost(ForumPost forumPost) throws ApplicationThrowable;

	/**
	 * 
	 * @param postId
	 * @return
	 * @throws ApplicationThrowable
	 */
	ReportedForumPost reportForumPost(Integer postId) throws ApplicationThrowable;

	/**
	 * 
	 * @param simpleSearchForumPost
	 * @param paginationFilter
	 * @return
	 */
	Page searchForumPosts(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param simpleSearchForumPost
	 * @param paginationFilter
	 * @return
	 */
	Page searchForumTopics(Search searchContainer, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	Page searchMessages(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param forumTopicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	Boolean subscribeForumTopic(Integer forumTopicId) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	Boolean unsubscribeAllForumTopic() throws ApplicationThrowable;

	/**
	 * 
	 * @param forumTopicId
	 * @return
	 * @throws ApplicationThrowable
	 */
	Boolean unsubscribeForumTopic(Integer forumTopicId) throws ApplicationThrowable;
}
