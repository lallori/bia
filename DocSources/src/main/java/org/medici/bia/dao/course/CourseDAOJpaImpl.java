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

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.PageUtils;
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
		Query query = getEntityManager().createQuery("SELECT count(*) FROM Course WHERE active = true AND forum.logicalDelete = false");
		
		return (Long)query.getSingleResult();
	}
	
	@Override
	public List<Course> getActiveCourses() throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = true AND forum.logicalDelete = false");
		
		return getResultList(query);
	}
	
	@Override
	public List<Course> getActiveCoursesByDocument(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = true AND forum.logicalDelete = false AND forum.forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ")");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return getResultList(query);
	}
	
	@Override
	public Course getCourseByCourseFragment(Integer courseFragmentContainerIdentifier) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forum.logicalDelete = false AND forum.forumId = (SELECT forumParent FROM Forum WHERE forumId = :forumId)");
		query.setParameter("forumId", courseFragmentContainerIdentifier);
		
		return getFirst(query);
	}
	
	@Override
	public Page getCourses(Boolean onlyActives, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		Query query = null;
		String jpql = "FROM Course WHERE forum.logicalDelete = false";
		if (Boolean.TRUE.equals(onlyActives)) {
			jpql += " AND active = true";
		}
		
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + jpql;
			query = getEntityManager().createQuery(countQuery);

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(PageUtils.calculeTotalPages(paginationFilter.getTotal(), paginationFilter.getElementsForPage()));
		}
		
		query = getEntityManager().createQuery(jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	@Override
	public List<Course> getCoursesByDocument(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forum.logicalDelete = false AND forum.forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ")");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return getResultList(query);
	}
	
	@Override
	public Course getLastActiveCourse() throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = true AND forum.logicalDelete = false ORDER BY courseId DESC");
		return getFirst(query);
	}
	
	@Override
	public Course getLastActiveCourseByDocument(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = true AND forum.logicalDelete = false AND forum.forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ") ORDER BY courseId DESC");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return getFirst(query);
	}
	
	@Override
	public boolean isDocumentInActiveCourse(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE active = true AND forum.logicalDelete = false AND forum.forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ")");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return !getResultList(query).isEmpty();
	}

	@Override
	public boolean isDocumentInCourse(Integer docId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Course WHERE forum.logicalDelete = false AND forum.forumId IN (" + getParametrizedCourseSubQuery("entryId", "subType", "mode") + ")");
		query.setParameter("entryId", docId);
		query.setParameter("subType", SubType.COURSE);
		query.setParameter("mode", getFilteredModes());
		
		return query.getResultList().size() > 0;
	}
	
	@Override
	public boolean isForumInActiveCourse(Integer forumId) throws PersistenceException {
		if (forumId == null) {
			return false;
		}
		
		String queryString = "SELECT count(forum) FROM Forum AS forum, Course AS course " +
			"WHERE forum.forumId = :forumId AND " + 
			"(forum = course.forum OR forum.forumParent = course.forum) AND " +
			"course.active = true AND course.forum.logicalDelete = false";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("forumId", forumId);
		
		return (Long) query.getSingleResult() > 0;
	}

	@Override
	public boolean isForumInCourse(Integer forumId) throws PersistenceException {
		if (forumId == null) {
			return false;
		}
		
		String queryString = "SELECT count(forum) FROM Forum AS forum, Course AS course " +
			"WHERE forum.forumId = :forumId AND " + 
			"(forum = course.forum OR forum.forumParent = course.forum) AND " +
			"course.forum.logicalDelete = false";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("forumId", forumId);
		
		return (Long) query.getSingleResult() > 0;
	}
	
	/* Privates */
	
	private String getParametrizedCourseSubQuery(String docIdName, String subTypeName, String modeName) {
		StringBuilder subquery = new StringBuilder("SELECT DISTINCT forum.forumParent FROM Forum as forum, ForumTopic as topic, CourseTopicOption as topicOption WHERE");
		subquery.append(" forum.subType = :").append(subTypeName)
			.append(" AND forum.logicalDelete = false")
			.append(" AND topic.forum = forum")
			.append(" AND topic.document.entryId = :").append(docIdName)
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
