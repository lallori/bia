/*
 * CourseDAOJpaImpl.java
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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.Forum.SubType;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Repository
public class CourseDAOJpaImpl extends JpaDao<Integer, Course> implements CourseDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2521739884049011688L;
	
	@Override
	public Long countActiveCourses() throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT count(*) FROM Course WHERE active = 1");
		
		return (Long)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getActiveCourses() throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = 1");
		
		return (List<Course>)query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Course> getActiveCoursesByDocument(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ") AND active = 1");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Course> getCoursesByDocument(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ")");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return query.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Course getLastActiveCourse() throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = 1 ORDER BY courseId DESC");
		List<Course> courses = query.getResultList();
		
		if (courses.isEmpty()) {
			return null;
		}
		return courses.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Course getLastActiveCourseByDocument(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ") AND active = 1 ORDER BY courseId DESC");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		List<Course> courses = (List<Course>)query.getResultList();
		if (courses.isEmpty()) {
			return null;
		}
		
		return courses.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean isInActiveCourse(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ") AND active = 1");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		List<Course> courses = query.getResultList();
		if (courses.isEmpty()) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isInCourse(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ")");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return query.getResultList().size() > 0;
	}
	
	/* Privates */
	
	private String getParametrizedCourseSubQuery(String docIdName, String subTypeName, String modeName) {
		StringBuilder subquery = new StringBuilder("SELECT DISTINCT forum.forumParent FROM Forum as forum, ForumTopic as topic, CourseTopicOption as topicOption WHERE");
		subquery.append(" forum.subType = :").append(subTypeName)
			.append(" AND forum.document.entryId = :").append(docIdName)
			.append(" AND forum.logicalDelete = false")
			.append(" AND topic.forum = forum")
			.append(" AND topic = topicOption.courseTopic")
			.append(" AND topicOption.mode IN (:").append(modeName).append(")");
		return subquery.toString();
		
	}
	
	private List<CourseTopicMode> getFilteredModes() {
		List<CourseTopicMode> modes = new ArrayList<CourseTopicMode>();
		modes.add(CourseTopicMode.I);
		modes.add(CourseTopicMode.C);
		modes.add(CourseTopicMode.R);
		return modes;
	}
}
