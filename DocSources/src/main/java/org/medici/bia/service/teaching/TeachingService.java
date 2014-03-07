/*
 * TeachingService.java
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
package org.medici.bia.service.teaching;

import java.util.Map;
import java.util.Set;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface TeachingService {
	
	/**
	 * This method creates a new post in a course topic.
	 * 
	 * @param courseTopicId the course topic identifier
	 * @param postSubject the post subject
	 * @param postContent the post content (html)
	 * @param volume the volume detail (number and extension letter)
	 * @param insert the insert detail (number and extension)
	 * @param folio the folio detail (number, extension and recto/verso)
	 * @param remoteAddress the current user address
	 * @return the created post (as {@link ForumPost})
	 * @throws ApplicationThrowable
	 */
	ForumPost addNewTopicPost(
			Integer courseTopicId,
			String postSubject,
			String postContent,
			String volume,
			String insert,
			String folio,
			String remoteAddress) throws ApplicationThrowable;
	
	/**
	 * This method creates a new course topic.
	 * 
	 * @param courseId the parent course identifier
	 * @param document the document identifier
	 * @param topicTitle the course topic title
	 * @param remoteAddress the current user address
	 * @return the created course topic (as {@link ForumTopic})
	 * @throws ApplicationThrowable
	 */
	ForumTopic addNewCourseTopic(Integer courseId, Integer documentId, String topicTitle, String remoteAddress) throws ApplicationThrowable;
	
	/**
	 * This method counts the active courses.
	 * 
	 * @return the current number of active courses
	 * @throws ApplicationThrowable
	 */
	Long countActiveCourses() throws ApplicationThrowable;
	
	/**
	 * This method removes logically a course topic post.
	 * 
	 * @param postId the post identifier to remove
	 * @throws ApplicationThrowable
	 */
	void deleteCourseTopicPost(Integer postId) throws ApplicationThrowable;
	
	/**
	 * This method returns the course by its identifier.
	 * 
	 * @param courseId the course identifier
	 * @return the {@link Course}, null if none or if the provided identifier is not associate to a course 
	 * @throws ApplicationThrowable
	 */
	Course findCourse(Integer courseId) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic by its identifier.
	 * 
	 * @param topicId the course topic identifier
	 * @return the {@link FourmTopic}, null if none or if the provided identifier is not associated to a course topic
	 * @throws ApplicationThrowable
	 */
	ForumTopic findCourseTopic(Integer topicId) throws ApplicationThrowable;
	
	Image getDocumentImage(Integer entryId, Integer imageOrder) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic by its identifier.<br/>
	 * It DOES NOT increase the topic total views (see also {@link #getCourseTopicForView(Integer)).
	 * 
	 * @param courseTopicId the course topic identifier
	 * @return the course topic
	 * @throws ApplicationThrowable
	 */
	ForumTopic getCourseTopic(Integer courseTopicId) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic by its identifier.<br/>
	 * It also increases the topic total views.
	 * 
	 * @param courseTopicId the course topic identifier
	 * @return the course topic
	 * @throws ApplicationThrowable
	 */
	ForumTopic getCourseTopicForView(Integer courseTopicId) throws ApplicationThrowable;
	
	/**
	 * This method returns the session user.
	 * 
	 * @return the {@link User}
	 * @throws ApplicationThrowable
	 */
	User getCurrentUser() throws ApplicationThrowable;
	
	/**
	 * This method returns the last active course.
	 * 
	 * @return the last active course
	 * @throws ApplicationThrowable
	 */
	Course getLastActiveCourse() throws ApplicationThrowable;
	
	/**
	 * This method returns a Page of posts of the course topic provided.
	 * 
	 * @param courseTopic the course topic
	 * @param filter the pagination filter
	 * @return a page of posts
	 * @throws ApplicationThrowable
	 */
	Page getPostsFromCourseTopic(ForumTopic courseTopic, PaginationFilter filter) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic post by its identifier.
	 * 
	 * @param postId the post identifier
	 * @return the course topic post found
	 * @throws ApplicationThrowable
	 */
	ForumPost getRoundRobinPost(Integer postId) throws ApplicationThrowable;
	
	Map<String,UserAuthority> getUsersRoundRobinAuthority(Set<String> accountIds) throws ApplicationThrowable;
	
	/**
	 * This method updates subject and text of a course topic post.
	 * 
	 * @param postId the post identifier
	 * @param postSubject the post subject
	 * @param postContent the post text
	 * @param volume the volume detail (number and extension letter)
	 * @param insert the insert detail (number and extension)
	 * @param folio the folio detail (number, extension and recto/verso)
	 * @return the course topic post
	 * @throws ApplicationThrowable
	 */
	ForumPost updateTopicPost(
			Integer postId,
			String postSubject,
			String postContent,
			String volume,
			String insert,
			String folio) throws ApplicationThrowable;

}
