/*
 * CourseTopicOptionDAO.java
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
package org.medici.bia.dao.coursetopicoption;

import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;

import org.medici.bia.dao.Dao;
import org.medici.bia.domain.CourseTopicOption;

/**
 * Course Topic Option DAO.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface CourseTopicOptionDAO extends Dao<Integer, CourseTopicOption> {
	
	/**
	 * Determines the extended topic that contains the last post of the course fragment.
	 * The considered course fragment must have the provided identifier.
	 * 
	 * @param forumId the forum identifier
	 * @return the {@link CourseTopicOption} found
	 * @throws PersistenceException
	 */
	CourseTopicOption determineExtendedTopicWithLastPost(Integer forumId) throws PersistenceException;
	
	/**
	 * Returns the {@link CourseTopicOption} of a course forum resources container.
	 * 
	 * @param forumId the course forum resources container
	 * @return the {@link CourseTopicOption} found
	 * @throws PersistenceException
	 */
	CourseTopicOption getCourseTranscriptionOptionFromForum(Integer forumId) throws PersistenceException;
	
	/**
	 * Returns the {@link CourseTopicOption} associated to the provided document and course.
	 * 
	 * @param entryId the document identifier
	 * @param courseId the course identifier
	 * @return the list of {@link CourseTopicOption} found
	 * @throws PersistenceException
	 */
	List<CourseTopicOption> getMasterOptionsByDocumentAndCourse(Integer entryId, Integer courseId) throws PersistenceException;

	/**
	 * Returns the {@link CourseTopicOption} associated to the provided document in active courses.
	 * 
	 * @param entryId the document identifier
	 * @return the list of {@link CourseTopicOption} found
	 * @throws PersistenceException
	 */
	List<CourseTopicOption> getMasterOptionsByDocumentInActiveCourses(Integer entryId) throws PersistenceException;
	
	/**
	 * Returns the last <code>numberOfElements</code> elements of extended course topics where users activity has been recorded.
	 * The available returned extended topics are the only ones where the provided user can access.
	 * 
	 * @param numberOfElements the maximum number of extended topic to return
	 * @param account the user account
	 * @return last elements of extended course topics found
	 * @throws PersistenceException
	 */
	List<CourseTopicOption> getMostRecentExtendedCourseTopics(Integer numberOfElements, String account) throws PersistenceException;
	
	/**
	 * Returns the last <code>numberOfElements</code> elements of collaborative transcription topics where users activity has been recorded.
	 * The available returned extended topics are the only ones where the provided user can access.
	 * 
	 * @param numberOfElements the maximum number of extended topic to return
	 * @param account the user account
	 * @return last elements of extended course topics found
	 * @throws PersistenceException
	 */
	List<CourseTopicOption> getMostRecentCollaborativeTranscriptionTopics(Integer numberOfElements, String account) throws PersistenceException;
	
	/**
	 * Returns the last <code>numberOfElements</code> elements of course discussion topics (classroom discussions and course questions)
	 * where users activity has been recorded. The available returned extended topics are the only ones where the provided user can access.
	 * 
	 * @param numberOfElements the maximum number of extended topic to return
	 * @param account the user account
	 * @return last elements of extended course topics found
	 * @throws PersistenceException
	 */
	List<CourseTopicOption> getMostRecentCourseDiscussions(Integer numberOfElements, String account) throws PersistenceException;
	
	/**
	 * Returns the {@link CourseTopicOption} associated to the provided course topic.
	 * 
	 * @param topicId the course topic identifier
	 * @return the {@link CourseTopicOption} found
	 * @throws PersistenceException
	 */
	CourseTopicOption getOption(Integer topicId) throws PersistenceException;
	
	/**
	 * Returns the {@link CourseTopicOption} associated to the provided set of topic identifiers.
	 * 
	 * @param topicIds set of topic identifiers
	 * @return the list of {@link CourseTopicOption} found
	 * @throws PersistenceException
	 */
	List<CourseTopicOption> getOptions(Set<Integer> topicIds) throws PersistenceException;

}
