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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.CourseCheckPoint;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on all teaching processes.<br>
 * With this service, you can :
 * <ul>
 * <li>manage courses, course topics and course topic posts</li>
 * <li>retrieve course topics extended details (aka {@link CourseTopicOption})</li>
 * </ul>
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface TeachingService {
	
	/**
	 * This method creates a new check point for the provided topic.
	 * 
	 * @param topicId the topoic identifier
	 * @param extendedPost the extended post of the check point
	 * @param date the check point time
	 * @return the created check point
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CourseCheckPoint addCourseCheckPoint(Integer topicId, CoursePostExt extendedPost, Date date) throws ApplicationThrowable;
	
	/**
	 * This method creates a new course topic ({@link ForumTopic}) in a new course container ({@link Forum}).<br/>
	 * NOTE: do not use this method when you only want to add a topic in a course container.
	 *  
	 * 
	 * @param courseId the course identifier
	 * @param document the document identifier
	 * @param topicTitle the course topic title
	 * @param mode the course topic mode
	 * @param remoteAddress the current user address
	 * @return the created course topic (as {@link ForumTopic})
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	ForumTopic addCourseTopic(
			Integer courseId, 
			Integer documentId, 
			String topicTitle, 
			CourseTopicMode mode, 
			String remoteAddress) throws ApplicationThrowable;
	
	/**
	 * This method creates a new post in a transcription topic.
	 * 
	 * @param courseTopicId the topic identifier
	 * @param postSubject the post subject
	 * @param postContent the post content (html)
	 * @param transcription the transcription
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folioNumber
	 * @param folioMod the folio extension
	 * @param folioRV the folio recto/verso detail
	 * @param remoteAddr the current user address
	 * @param mode the course topic mode
	 * @param relatedCheckPointPostId the related check point post identifier to join to the post 
	 * @return the course transcription post (as a {@link CoursePostExt})
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CoursePostExt addCourseTranscriptionPost(
			Integer courseTopicId, 
			String postSubject, 
			String postContent,
			String transcription,
			Integer volNum, 
			String volLetExt, 
			String insertNum,
			String insertLet, 
			Integer folioNum, 
			String folioMod,
			String folioRV, 
			String remoteAddr,
			CourseTopicMode mode,
			Integer relatedCheckPointPostId) throws ApplicationThrowable;
	
	/**
	 * This method counts the active courses.
	 * 
	 * @return the current number of active courses
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Long countActiveCourses() throws ApplicationThrowable;
	
	/**
	 * This method counts the number of post associated to a check point.
	 * 
	 * @param checkPointId the chek point identifier
	 * @return the number of post associated to a check point
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Long countCheckPointPosts(Integer checkPointId) throws ApplicationThrowable;
	
	/**
	 * This method removes logically a course topic post.<br/>
	 * It also updates course topic and course forum details.
	 * 
	 * @param postId the post identifier to remove
	 * @param mode the course topic mode
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	void deleteCourseTranscriptionPost(Integer postId, CourseTopicMode mode) throws ApplicationThrowable;
	
	/**
	 * This method returns the course by its identifier.
	 * 
	 * @param courseId the course identifier
	 * @return the {@link Course}, null if none or if the provided identifier is not associate to a course 
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Course findCourse(Integer courseId) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic by its identifier.<br/>
	 * It DOES NOT increase the topic total views (see also {@link #getCourseTopicForView(Integer)).
	 * 
	 * @param topicId the course topic identifier
	 * @return the {@link FourmTopic}, null if none or if the provided identifier is not associated to a course topic
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	ForumTopic findCourseTopic(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * This method returns all active courses
	 * 
	 * @return the list of active courses
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	List<Course> getActiveCourses() throws ApplicationThrowable;
	
	/**
	 * This method returns a list of active courses where a document is used in one or more topics.
	 * 
	 * @param entryId the document identifier
	 * @return a list of courses, empty list if none is found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	List<Course> getActiveCourses(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * Returns the check point associated to the provided post.
	 * 
	 * @param postId the post identifier (not the extended post identifier).
	 * @return the check point found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CourseCheckPoint getCheckPointByPost(Integer postId) throws ApplicationThrowable;
	
	/**
	 * This method searches for an extended course transcription post ({@link CoursePostExt}) whose wrapped post
	 * has the provided identifier.
	 * 
	 * @param postId the forum post identifier
	 * @return the extended course transcription post
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CoursePostExt getCoursePostByForumPostId(Integer postId) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic by its identifier.<br/>
	 * It also increases the topic total views.
	 * 
	 * @param courseTopicId the course topic identifier
	 * @return the course topic
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	ForumTopic getCourseTopicForView(Integer courseTopicId) throws ApplicationThrowable;
	
	/**
	 * This method tries to determine the {@link CourseTopicMode} of a topic.
	 * 
	 * @param topicId the topic identifier
	 * @return the {@link CourseTopicMode} found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CourseTopicMode getCourseTopicMode(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * This method tries to determine the {@link CourseTopicMode} of a set of topics.
	 * @param topics a set of topic identifiers
	 * @return a map representing the association between topic identifier and {@link CourseTopicMode}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Map<Integer, CourseTopicMode> getCourseTopicsMode(List<ForumTopic> topics) throws ApplicationThrowable;
	
	/**
	 * This method returns the course transcription resources container by the course transcription topic.
	 * 
	 * @param topicId the topic identifier
	 * @return the forum identifier found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Integer getCourseTranscriptionResourcesForum(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * This method returns the session user.
	 * 
	 * @return the {@link User}
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	User getCurrentUser() throws ApplicationThrowable;
	
	/**
	 * This method returns the {@link Image} with the provided order within the provided document.
	 * 
	 * @param entryId the document identifier
	 * @param imageOrder the image order
	 * @return the {@link Image} found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Image getDocumentImage(Integer entryId, Integer imageOrder) throws ApplicationThrowable;
	
	/**
	 * This method returns a list of course topic of a document associated to a course (as a forum).
	 *  
	 * @param entryId the document identifier
	 * @param courseId the course identifier
	 * @return the list of course topic found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	List<ForumTopic> getDocumentTopicsFromCourse(Integer entryId, Integer courseId) throws ApplicationThrowable;
	
	/**
	 * This method returns the last active course.
	 * 
	 * @return the last active course
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Course getLastActiveCourse() throws ApplicationThrowable;
	
	/**
	 * This method returns the last active course where a document is used in a topic.
	 * 
	 * @param documentId the document identifier
	 * @return the last active course found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Course getLastActiveCourse(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * This method return the last check point ({@link CourseCheckPoint}) associated to a topic.
	 * 
	 * @param topicId the topic identifier
	 * @return the last check point found, null if none
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CourseCheckPoint getLastCheckPoint(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * This method returns the last extended post of a topic course.
	 * 
	 * @param topicId the topic identifier
	 * @param byDateCreated if true returns the last post by creation date, otherwise the last post by last update
	 * @return the last extended post of a topic course
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CoursePostExt getLastPostOfTopic(Integer topicId, boolean byDateCreated) throws ApplicationThrowable;
	
	/**
	 * This method retrieves a list of {@link CourseTopicOption} associated to active courses
	 * where the provided document is used.
	 * 
	 * @param entryId the document identifier
	 * @return the list of {@link CourseTopicOption} found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	List<CourseTopicOption> getOptionsByDocumentForActiveCourses(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * This method returns a Page of extended posts of the course topic provided.
	 * 
	 * @param courseTopic the course topic
	 * @param filter the pagination filter
	 * @return a page of extended posts ({@link CoursePostExt})
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Page getPostsFromCourseTopic(ForumTopic courseTopic, PaginationFilter filter) throws ApplicationThrowable;
	
	/**
	 * This method returns the course topic post by its identifier.
	 * 
	 * @param postId the post identifier
	 * @return the course topic post found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	ForumPost getRoundRobinPost(Integer postId) throws ApplicationThrowable;
	
	/**
	 * This method returns the list of check points associated to the provided topic.
	 * 
	 * @param topicId the topic identifier
	 * @return the list of check points found
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	List<CourseCheckPoint> getTopicCheckPoints(Integer topicId) throws ApplicationThrowable;
	
	/**
	 * This method returns the user maximum authority for course transcriptions.
	 * 
	 * @param account the account identifier
	 * @return the user maximum authority for course transcriptions
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	UserAuthority getUserCourseAuthority(String account) throws ApplicationThrowable;
	
	/**
	 * This method returns the a map of users maximum authority for course transcriptions.
	 *  
	 * @param accountsId a set of account identifier
	 * @return the map of users maximum authority for course transcriptions
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	Map<String,UserAuthority> getUsersCourseAuthority(Set<String> accountIds) throws ApplicationThrowable;
	
	/**
	 * This method determines if a document is associated to an active course (by a course topic).
	 * 
	 * @param entryId the document identifier
	 * @return true if the document is associated to an active course, false otherwise
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	boolean isInActiveCourse(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * This method determines if a document is associated to a course (by a course topic).
	 * 
	 * @param entryId the document identifier
	 * @return true if the document is associated to a course, false otherwise
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	boolean isInCourse(Integer entryId) throws ApplicationThrowable;
	
	/**
	 * This method defines the current transcription status for incremental course transcription.
	 * 
	 * @param postExt the extended post ({@link CoursePostExt})
	 * @return the defined checkpoint ({@link CourseCheckPoint})
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CourseCheckPoint setIncrementalTranscription(CoursePostExt postExt) throws ApplicationThrowable;
	
	/**
	 * This method updates the existent post of a course topic (for Course Transcription Topic).
	 * 
	 * @param postId the post identifier (not the extended post identifier)
	 * @param postSubject the post subject
	 * @param postContent the post content (html)
	 * @param transcription the transcription
	 * @param volNum the volume number
	 * @param volLetExt the volume letter extension
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folioNumber
	 * @param folioMod the folio extension
	 * @param folioRV the folio recto/verso detail
	 * @param mode the course topic mode
	 * @return the course transcription post
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request
	 */
	CoursePostExt updateCourseTranscriptionPost(
			Integer postId,
			String postSubject, 
			String postContent, 
			String transcription, 
			Integer volNum,
			String volLetExt, 
			String insertNum, 
			String insertLet,
			Integer folioNum, 
			String folioMod, 
			String folioRV,
			CourseTopicMode mode) throws ApplicationThrowable;

}
