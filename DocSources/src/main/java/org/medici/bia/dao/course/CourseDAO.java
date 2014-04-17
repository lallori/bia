/*
 * CourseDAO.java
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
package org.medici.bia.dao.course;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Course;

/**
 * Course Dao.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface CourseDAO extends Dao<Integer, Course> {
	
	/**
	 * This method counts active courses.
	 * 
	 * @return the number of active courses
	 * @throws PersistenceException
	 */
	Long countActiveCourses() throws PersistenceException;
	
	/**
	 * This method returns all active courses.
	 * 
	 * @return the list of active courses
	 * @throws PersistenceException
	 */
	List<Course> getActiveCourses() throws PersistenceException;
	
	/**
	 * This method returns a list of active courses where a document is used in some topics.
	 * 
	 * @param docId the document identifier
	 * @return a list of active courses, empty list if none is found
	 * @throws PersistenceException
	 */
	List<Course> getActiveCoursesByDocument(Integer docId) throws PersistenceException;
	
	/**
	 * This method returns a list of courses associated to a document.
	 * 
	 * @param docId the document identifier
	 * @return a list of courses, empty list if none is found
	 * @throws PersistenceException
	 */
	List<Course> getCoursesByDocument(Integer docId) throws PersistenceException;
	
	/**
	 * This method returns the last active course.
	 * 
	 * @return the last active course
	 * @throws PersistenceException
	 */
	Course getLastActiveCourse() throws PersistenceException;
	
	/**
	 * This method returns the last active course where a document is used in a topic.
	 * 
	 * @param docId the document identifier
	 * @return the active course found
	 * @throws PersistenceException
	 */
	Course getLastActiveCourseByDocument(Integer docId) throws PersistenceException;
	
	/**
	 * This method determines if a document is associated to an active course (by a course topic).
	 * 
	 * @param docId the document identifier
	 * @return true if an active course is found, false otherwise
	 * @throws PersistenceException
	 */
	boolean isInActiveCourse(Integer docId) throws PersistenceException;

	/**
	 * This method determines if a document is associated to a course (by a course topic).
	 * 
	 * @param docId the document identifier
	 * @return true if a course (active or not) is found, false otherwise
	 * @throws PersistenceException
	 */
	boolean isInCourse(Integer docId) throws PersistenceException;
}
