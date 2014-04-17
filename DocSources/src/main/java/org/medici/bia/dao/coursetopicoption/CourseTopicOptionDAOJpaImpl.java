/*
 * CourseTopicOptionDAOJpaImpl.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.medici.bia.domain.Forum.SubType;
import org.springframework.stereotype.Repository;

/**
 * <b>CourseTopicOptionDAOJpaImpl</b> is a default implementation of <b>CourseTopicOptionDAO</b>.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.CourseTopicOption
 *
 */
@Repository
public class CourseTopicOptionDAOJpaImpl extends JpaDao<Integer, CourseTopicOption> implements CourseTopicOptionDAO {

	private static final long serialVersionUID = -6595014972924741276L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTopicOption> findOptions(Set<Integer> topicIds) throws PersistenceException {
		String ids = null;
		for(Integer id : topicIds) {
			if (ids == null) {
				ids = "" + id;
			} else {
				ids += ", " + id;
			}
		}
		String jpql = "FROM CourseTopicOption WHERE courseTopic.topicId IN (" + ids + ")";
		
		Query query = getEntityManager().createQuery(jpql, CourseTopicOption.class);
		return (List<CourseTopicOption>)query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> findOptionsByDocumentAndCourse(Integer entryId, Integer courseId) throws PersistenceException {
		return findOptionsForDocument(entryId, courseId, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> findOptionsByDocumentInActiveCourses(Integer entryId) throws PersistenceException {
		return findOptionsForDocument(entryId, null, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> findMasterOptionsByDocumentAndCourse(Integer entryId, Integer courseId) throws PersistenceException {
		return findOptionsForDocument(entryId, courseId, true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> findMasterOptionsByDocumentInActiveCourses(Integer entryId) throws PersistenceException {
		return findOptionsForDocument(entryId, null, true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CourseTopicOption findTopicOption(Integer topicId) throws PersistenceException {
		String jpql = "FROM CourseTopicOption WHERE courseTopic.topicId = :topicId";
		Query query = getEntityManager().createQuery(jpql.toString(), CourseTopicOption.class);
		query.setParameter("topicId", topicId);
		
		List<CourseTopicOption> options = (List<CourseTopicOption>)query.getResultList();
		if (options.size() == 0) {
			return null;
		}
		return options.get(0);
	}
	
	/* Privates */
	
	@SuppressWarnings("unchecked")
	private List<CourseTopicOption> findOptionsForDocument(Integer entryId, Integer courseId, boolean filterToMasterFragments) throws PersistenceException {
		StringBuilder jpql = new StringBuilder("SELECT option FROM CourseTopicOption AS option, Forum AS forum, ForumTopic AS topic, Course AS course WHERE ");
		jpql.append(" forum.subType = :type")
			.append(" AND forum.document.entryId = :entryId")
			.append(" AND forum.logicalDelete = false")
			.append(" AND topic.forum = forum")
			.append(" AND topic = option.courseTopic");
		if (filterToMasterFragments) {
			jpql.append(" AND option.mode IN (:modeList)");
		}
		jpql.append(" AND course.forum = forum.forumParent");
		if (courseId != null) {
			jpql.append(" AND course.courseId = :courseId");
		} else {
			jpql.append(" AND course.active = true");
		}
		
		Query query = getEntityManager().createQuery(jpql.toString(), CourseTopicOption.class);
		query.setParameter("entryId", entryId);
		if (courseId != null) {
			query.setParameter("courseId", courseId);
		}
		query.setParameter("type", SubType.COURSE);
		if (filterToMasterFragments) {
			query.setParameter("modeList", getFilteredModes());
		}
		return (List<CourseTopicOption>)query.getResultList();
	}
	
	private List<CourseTopicMode> getFilteredModes() {
		List<CourseTopicMode> modes = new ArrayList<CourseTopicMode>();
		modes.add(CourseTopicMode.I);
		modes.add(CourseTopicMode.C);
		modes.add(CourseTopicMode.R);
		return modes;
	}
}
