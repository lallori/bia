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
	@Override
	public CourseTopicOption determineExtendedTopicWithLastPost(Integer forumId) throws PersistenceException {
		
		// We consider every last post (by creation date) of course fragment's topics whose course forum container has the provided identifier.
		String jpql = "FROM CourseTopicOption "
				+ "WHERE "
				+ "courseTopic.forum.forumId = :forumId AND "
				+ "courseTopic.logicalDelete = false AND "
				+ "courseTopic.lastPost IS NOT NULL "
				+ "ORDER BY "
				+ "courseTopic.lastPost.dateCreated DESC";
				// The following clause is to consider the last post by 'lastUpdate' (for incremental course transcription):
				// This clause had been deleted because we want to consider only the chronological order.
				// + "CASE WHEN mode = 'I' THEN courseTopic.lastPost.lastUpdate ELSE courseTopic.lastPost.dateCreated END DESC";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("forumId", forumId);
		
		return getFirst(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseTopicOption getCourseTranscriptionOptionFromForum(Integer forumId) throws PersistenceException {
		String jpql = "FROM CourseTopicOption WHERE courseTopic.forum.forumId = :forumId AND courseTopic.logicalDelete = false AND mode IN (:transcriptionModes)";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("forumId", forumId);
		query.setParameter("transcriptionModes", getFilteredModes());
		
		return getFirst(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> getMasterOptionsByDocumentAndCourse(Integer entryId, Integer courseId) throws PersistenceException {
		return findOptionsForDocument(entryId, courseId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> getMasterOptionsByDocumentInActiveCourses(Integer entryId) throws PersistenceException {
		return findOptionsForDocument(entryId, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> getMostRecentExtendedCourseTopics(Integer numberOfElements, String account) throws PersistenceException {
		return getMostRecentCourseTopics(numberOfElements, null, account);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> getMostRecentCollaborativeTranscriptionTopics(Integer numberOfElements, String account) throws PersistenceException {
		return getMostRecentCourseTopics(numberOfElements, getFilteredModes(), account);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CourseTopicOption> getMostRecentCourseQuestions(Integer numberOfElements, String account) throws PersistenceException {
		List<CourseTopicMode> transcriptionTypes = new ArrayList<CourseTopicMode>();
		transcriptionTypes.add(CourseTopicMode.Q);
		
		return getMostRecentCourseTopics(numberOfElements, transcriptionTypes, account);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public CourseTopicOption getOption(Integer topicId) throws PersistenceException {
		String jpql = "FROM CourseTopicOption WHERE courseTopic.topicId = :topicId";
		Query query = getEntityManager().createQuery(jpql.toString(), CourseTopicOption.class);
		query.setParameter("topicId", topicId);
		
		return getFirst(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseTopicOption> getOptions(Set<Integer> topicIds) throws PersistenceException {
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
	
	/* Privates */
	
	@SuppressWarnings("unchecked")
	private List<CourseTopicOption> findOptionsForDocument(Integer entryId, Integer courseId) throws PersistenceException {
		StringBuilder jpql = new StringBuilder("SELECT option FROM CourseTopicOption AS option, Forum AS forum, ForumTopic AS topic, Course AS course WHERE ");
		jpql.append(" topic.document IS NOT NULL AND topic.document.entryId = :entryId")
			.append(" AND topic.forum = forum")
			.append(" AND forum.logicalDelete = false")
			.append(" AND forum.subType = :type")
			.append(" AND topic = option.courseTopic")
			.append(" AND option.mode IN (:modeList)")
			.append(" AND course.forum = forum.forumParent");
		if (courseId != null) {
			jpql.append(" AND course.courseId = :courseId");
		} else {
			jpql.append(" AND course.active = true");
		}
		
		Query query = getEntityManager().createQuery(jpql.toString(), CourseTopicOption.class);
		query.setParameter("entryId", entryId);
		query.setParameter("type", SubType.COURSE);
		if (courseId != null) {
			query.setParameter("courseId", courseId);
		}
		query.setParameter("modeList", getFilteredModes());

		return (List<CourseTopicOption>)query.getResultList();
	}
	
	private List<CourseTopicMode> getFilteredModes() {
		List<CourseTopicMode> modes = new ArrayList<CourseTopicMode>();
		modes.add(CourseTopicMode.I);
		modes.add(CourseTopicMode.C);
		modes.add(CourseTopicMode.R);
		return modes;
	}
	
	private List<CourseTopicOption> getMostRecentCourseTopics(Integer numberOfElements, List<CourseTopicMode> courseTopicTypes, String account) throws PersistenceException {
		// TODO: add account filtering (when course topic accesses are implemented)
		String jpql = "SELECT option FROM CourseTopicOption AS option, ForumTopic AS topic, Course AS course WHERE "
				+ "topic.logicalDelete = false AND "
				+ "topic.forum.logicalDelete = false AND "
				+ "topic.forum.subType = 'COURSE' AND "
				+ "topic.forum.forumParent = course.forum AND "
				+ "course.active = true AND "
				+ "topic = option.courseTopic ";
		if (courseTopicTypes != null && courseTopicTypes.size() > 0) {
			jpql += "AND option.mode IN(:courseTopicTypes) ";
		}
		jpql += "ORDER BY topic.lastUpdate desc";
			
		Query query = getEntityManager().createQuery(jpql);
		if (courseTopicTypes != null && courseTopicTypes.size() > 0) {
			query.setParameter("courseTopicTypes", courseTopicTypes);
		}
		
        // We set pagination  
		query.setFirstResult(0);
		query.setMaxResults(numberOfElements);
		
		return getResultList(query);
	}
}
