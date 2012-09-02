/*
 * ForumPostDAOJpaImpl.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.docsources.dao.forumpost;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.PageUtils;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.ForumPost;
import org.medici.docsources.domain.ForumTopic;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumPostDAOJpaImpl</b> is a default implementation of <b>ForumPostDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.ForumPost
 * {@link http://yensdesign.com/2008/10/making-mysql-forum-database-from-scratch/}
 *
 */
@Repository
public class ForumPostDAOJpaImpl extends JpaDao<Integer, ForumPost> implements ForumPostDAO {
	/**
	 * 
	 *  If a serializable class does not explicitly declare a serialVersionUID, 
	 *  then the serialization runtime will calculate a default serialVersionUID 
	 *  value for that class based on various aspects of the class, as described
	 *  in the Java(TM) Object Serialization Specification. However, it is 
	 *  strongly recommended that all serializable classes explicitly declare 
	 *  serialVersionUID values, since the default serialVersionUID computation 
	 *  is highly sensitive to class details that may vary depending on compiler
	 *  implementations, and can thus result in unexpected 
	 *  InvalidClassExceptions during deserialization. Therefore, to guarantee a
	 *   consistent serialVersionUID value across different java compiler 
	 *   implementations, a serializable class must declare an explicit 
	 *  serialVersionUID value. It is also strongly advised that explicit 
	 *  serialVersionUID declarations use the private modifier where possible, 
	 *  since such declarations apply only to the immediately declaring 
	 *  class--serialVersionUID fields are not useful as inherited members. 
	 */
	private static final long serialVersionUID = -7766262138445120927L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ForumPost findFirstPostByTopicId(Integer topicId) throws PersistenceException {
		String jpql = "FROM ForumPost WHERE topic.topicId = :topicId AND logicalDelete=false order by dateCreated asc";

		if (topicId == null) {
			return null;
		}
		
		Query query = null;
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("topicId", topicId);

        // We set pagination  
		query.setFirstResult(0);
		query.setMaxResults(1);

		// We manage sorting (this manages sorting on multiple fields)
		List<ForumPost> list = (List<ForumPost>) query.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Boolean findIfPostIsParent(Integer postId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM ForumPost WHERE parentPost.postId=:postId AND logicalDelete=false ");
		query.setParameter("postId", postId);
		
		List<ForumPost> result = query.getResultList();
		if(result.size() > 0)
			return Boolean.TRUE;
		else
			return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost findLastPostFromForum(Forum forum) throws PersistenceException {
		String jpql = "FROM ForumPost  WHERE topic.forum.forumId = :forumId AND logicalDelete=false order by dateCreated desc";
		
		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("forumId", forum.getForumId());

        // We set pagination to obtain first post...  
		query.setFirstResult(0);
		query.setMaxResults(1);

		List<ForumPost> list = (List<ForumPost>) query.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost findLastPostFromForumTopic(ForumTopic forumTopic) throws PersistenceException {
		String jpql = "FROM ForumPost  WHERE topic.topicId = :topicId AND logicalDelete=false order by dateCreated desc";

		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("topicId", forumTopic.getTopicId());

        // We set pagination to obtain first post...  
		query.setFirstResult(0);
		query.setMaxResults(1);

		List<ForumPost> list = (List<ForumPost>) query.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page findPostsFromTopic(ForumTopic forumTopic, PaginationFilter paginationFilter) throws PersistenceException {
		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String queryString = "FROM ForumPost WHERE topic.topicId = :topicId AND logicalDelete=false ";

		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		if (forumTopic == null) {
			return page;
		}
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + queryString;
	        
			query = getEntityManager().createQuery(countQuery);
	        query.setParameter("topicId", forumTopic.getTopicId());

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}

		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
		if (sortingCriterias.size() > 0) {
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		String jpql = queryString + orderBySQL.toString();
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("topicId", forumTopic.getTopicId());

        // We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)
		List<ForumPost> list = (List<ForumPost>) query.getResultList();

		// We set search result on return method
		page.setList(list);
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchMYSQL(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + searchContainer.toJPAQuery();
	        
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		}

		String objectsQuery = searchContainer.toJPAQuery();
//		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
//		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
//		StringBuilder orderBySQL = new StringBuilder();
//		if (sortingCriterias.size() > 0) {
//			orderBySQL.append(" ORDER BY ");
//			for (int i=0; i<sortingCriterias.size(); i++) {
//				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
//				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
//				if (i<(sortingCriterias.size()-1)) {
//					orderBySQL.append(", ");
//				} 
//			}
//		}
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for(int i = 0; i < sortingCriterias.size(); i++){
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if(i < (sortingCriterias.size() - 1)){
					orderBySQL.append(", ");
				}
			}
		}
		
		String jpql = objectsQuery + orderBySQL.toString();
//		String jpql = objectsQuery;
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql );
		// We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)
		
		// We set search result on return method
		page.setList(query.getResultList());
		
		return page;
	}
}
