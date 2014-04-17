/*
 * CoursePostExtDAOJpaImpl.java
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

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.CoursePostExt;
import org.medici.bia.domain.ForumTopic;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Repository
public class CoursePostExtDAOJpaImpl extends JpaDao<Integer, CoursePostExt> implements CoursePostExtDAO {

	private static final long serialVersionUID = -3820829325210630955L;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Long countCheckPointPosts(Integer checkPointId) throws PersistenceException {
		String jpql = "SELECT COUNT(*) FROM CoursePostExt WHERE relatedCheckPoint IS NOT NULL AND relatedCheckPoint.checkPointId = :checkPointId AND post.logicalDelete = false";
		Query query = getEntityManager().createQuery(jpql, CoursePostExt.class);
        query.setParameter("checkPointId", checkPointId);
        
        return (Long)query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page getExtendedCourseTopicPosts(ForumTopic courseTopic, PaginationFilter paginationFilter) throws PersistenceException {
		String masterQuery = "FROM CoursePostExt WHERE post.topic.topicId = :topicId AND post.logicalDelete = false";

		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		if (courseTopic == null) {
			return page;
		}
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + masterQuery;
	        
			query = getEntityManager().createQuery(countQuery);
	        query.setParameter("topicId", courseTopic.getTopicId());

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}

		String jpql = masterQuery + getSortingClause(paginationFilter.getSortingCriterias());
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("topicId", courseTopic.getTopicId());

        // We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)
		List<CoursePostExt> list = (List<CoursePostExt>) query.getResultList();

		// We set search result on return method
		page.setList(list);
		
		return page;
	}
	
	@Override
	public CoursePostExt getExtendedPostByForumPost(Integer postId) throws PersistenceException {
		String jpql = "FROM CoursePostExt WHERE post.postId = :postId";
		Query query = getEntityManager().createQuery(jpql, CoursePostExt.class);
        query.setParameter("postId", postId);
        
        return getFirst(query);
	}
	
	@Override
	public CoursePostExt getLastPostByCheckPoint(Integer checkPointId, boolean byCreationDate) throws PersistenceException {
		String jpql = "FROM CoursePostExt WHERE relatedCheckPoint IS NOT NULL AND relatedCheckPoint.checkPointId = :checkPointId";
		jpql += byCreationDate ? " ORDER BY post.dateCreated DESC" : " ORDER BY post.lastUpdate DESC";
		Query query = getEntityManager().createQuery(jpql, CoursePostExt.class);
        query.setParameter("checkPointId", checkPointId);
        
        return getFirst(query);
	}
	
	@Override
	public CoursePostExt getLastPostInTopic(Integer topicId, boolean byCreationDate) throws PersistenceException {
		String jpql = "FROM CoursePostExt WHERE post.topic.topicId = :topicId AND post.logicalDelete = false";
		jpql += byCreationDate ? " ORDER BY post.dateCreated DESC" : " ORDER BY post.lastUpdate DESC";
		Query query = getEntityManager().createQuery(jpql, CoursePostExt.class);
		query.setParameter("topicId", topicId);
		
		return getFirst(query);
	}
	
	/* Privates */

	private String getSortingClause(List<SortingCriteria> sortingCriterias) {
		StringBuilder sortingSQL = new StringBuilder(0);
		if (sortingCriterias.size() > 0) {
			sortingSQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				sortingSQL.append(sortingCriterias.get(i).getColumn() + " ");
				sortingSQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					sortingSQL.append(", ");
				} 
			}
		}
		return sortingSQL.toString();
	}

}
