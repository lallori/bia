/*
 * CoursePostExtDAO.java
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
package org.medici.bia.dao.coursepostext;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface CoursePostExtDAO extends Dao<Integer, CoursePostExt> {
	
	/**
	 * This method counts the number of posts related to a check point (included check point post itself).
	 * 
	 * @param checkPointId the check point identifier
	 * @return the number of post related to the check point
	 * @throws PersistenceException
	 */
	Long countCheckPointPosts(Integer checkPointId) throws PersistenceException;

	/**
	 * This method returns a page of extended course topic posts (aka {@link CoursePostExt}) relative to
	 * the provided pagination filter.
	 * 
	 * @param courseTopic the course topic
	 * @param paginationFilter the pagination filter
	 * @return a {@link Page} of extended course topic posts
	 * @throws PersistenceException
	 */
	Page getExtendedCourseTopicPosts(ForumTopic courseTopic, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * This method searches for an extended course transcription post ({@link CoursePostExt}) whose wrapped post
	 * has the provided identifier.
	 * 
	 * @param postId the forum post identifier
	 * @return the extended course transcription post
	 * @throws ApplicationThrowable
	 */
	CoursePostExt getExtendedPostByForumPost(Integer postId) throws PersistenceException;
	
	/**
	 * This method returns the last extended post associated to the provided check point.
	 * 
	 * @param checkPointId the check point identifier
	 * @param byCreationDate if true returns the last post by creation date, otherwise the last post by last update
	 * @return the last extended post found
	 * @throws PersistenceException
	 */
	CoursePostExt getLastPostByCheckPoint(Integer checkPointId, boolean byCreationDate) throws PersistenceException;
	
	/**
	 * This method returns the last extended modified post (aka {@link CoursePostExt}) in the provided topic.
	 * 
	 * @param topicId the topic identifier
	 * @param byCreationDate if true returns the last post by creation date, otherwise the last post by last update
	 * @return the last extended modified post
	 * @throws PersistenceException
	 */
	CoursePostExt getLastPostInTopic(Integer topicId, boolean byCreationDate) throws PersistenceException;
	
}
