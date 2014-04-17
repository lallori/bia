/*
 * CourseCheckPointDAOJpaImpl.java
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
package org.medici.bia.dao.coursecheckpoint;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.CourseCheckPoint;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Repository
public class CourseCheckPointDAOJpaImpl extends JpaDao<Integer, CourseCheckPoint> implements CourseCheckPointDAO {

	private static final long serialVersionUID = -9070512315316435816L;
	
	@Override
	public CourseCheckPoint getCheckPointByPost(Integer postId) throws PersistenceException {
		String jpql = "SELECT relatedCheckPoint FROM CoursePostExt WHERE post.postId = :postId";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("postId", postId);
		
		return getFirst(query);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CourseCheckPoint> getCheckPointsByTopic(Integer topicId) throws PersistenceException {
		String jpql = "FROM CourseCheckPoint WHERE checkPointPost.coursePost.topic.topicId = : topicId AND checkPointPost.coursePost.logicalDelete = false ORDER BY checkPointTime ASC";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("topicId", topicId);
		
		return (List<CourseCheckPoint>)query.getResultList();
	}

	@Override
	public CourseCheckPoint getLastCheckPointByTopicId(Integer topicId) throws PersistenceException {
		String jpql = "FROM CourseCheckPoint WHERE courseOption.courseTopic.topicId = :topicId ORDER BY checkPointTime DESC";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("topicId", topicId);
		
		return getFirst(query);
	}

}
