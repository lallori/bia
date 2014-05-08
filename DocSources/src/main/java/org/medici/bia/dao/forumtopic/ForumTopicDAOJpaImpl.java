/*
 * ForumTopicDAOJpaImpl.java
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
package org.medici.bia.dao.forumtopic;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumTopic;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumTopicDAOJpaImpl</b> is a default implementation of <b>ForumTopicDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.ForumPost
 * {@link http://yensdesign.com/2008/10/making-mysql-forum-database-from-scratch/}
 *
 */
@Repository
public class ForumTopicDAOJpaImpl extends JpaDao<Integer, ForumTopic> implements ForumTopicDAO {
	
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
	@Override
	public Long countTotalActive() throws PersistenceException {
		String countQuery = "SELECT COUNT(*) from ForumTopic where logicalDelete=0";
        
		Query query = getEntityManager().createQuery(countQuery);
		return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteForumTopicsFromForum(Integer forumId) throws PersistenceException {
		Query query = getEntityManager().createQuery("UPDATE ForumTopic SET logicalDelete = true WHERE forum.forumId=:forumId");
		query.setParameter("forumId", forumId);
		
		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic findForumTopic(ForumTopic forumTopic) throws PersistenceException {
		if (forumTopic == null) {
			return null;
		}
		
		return findForumTopicById(forumTopic.getTopicId());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic getForumTopicByAnnotation(Integer annotationId) throws PersistenceException {
		String jpql = "FROM ForumTopic WHERE annotation.annotationId = :annotationId";
		
		logger.debug("JPQL Query : " + jpql);
		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("annotationId", annotationId);
        
        return getFirst(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumTopic findForumTopicById(Integer forumTopicId) throws PersistenceException {
		String queryString = "FROM ForumTopic WHERE topicId = :topicId AND logicalDelete = false ";

		if (forumTopicId == null) {
			return null;
		}
		
		Query query = null;
		String jpql = queryString;
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("topicId", forumTopicId);
        
        return getFirst(query);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findForumTopics(Forum forum, PaginationFilter paginationFilter) throws PersistenceException {
		String queryString = "FROM ForumTopic WHERE forum.forumId = :forumId AND logicalDelete=false ";

		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		if (forum == null) {
			return page;
		}
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + queryString;
	        
			query = getEntityManager().createQuery(countQuery);
	        query.setParameter("forumId", forum.getForumId());

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}

		String jpql = queryString + getOrderByQuery(paginationFilter.getSortingCriterias());
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("forumId", forum.getForumId());

        // We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)
		List<ForumTopic> list = getResultList(query);

		// We set search result on return method
		page.setList(list);
		
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumTopic> findMostRecentForumTopics(Integer numberOfElements) throws PersistenceException {
		String jpql = "FROM ForumTopic WHERE logicalDelete = false AND forum.type = 'FORUM' AND forum.subType != 'COURSE' ORDER BY lastUpdate desc";

		Query query = getEntityManager().createQuery(jpql);
		
        // We set pagination  
		query.setFirstResult(0);
		query.setMaxResults(numberOfElements);
		
		return getResultList(query);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumTopic> findTopForumTopics(Integer numberOfElements) throws PersistenceException {
		String jpql = "FROM ForumTopic WHERE logicalDelete = false AND forum.type = 'FORUM' AND forum.subType != 'COURSE' ORDER BY totalReplies desc";

		Query query = getEntityManager().createQuery(jpql);
		
        // We set pagination  
		query.setFirstResult(0);
		query.setMaxResults(numberOfElements);
		
		return getResultList(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getForumTopicsByParentForum(Forum forum, PaginationFilter paginationFilter) throws PersistenceException {
		String queryString = "FROM ForumTopic WHERE logicalDelete = false AND forum.forumParent.forumId = :forumId AND logicalDelete=false ";

		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		if (forum == null) {
			return page;
		}
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + queryString;
	        
			query = getEntityManager().createQuery(countQuery);
	        query.setParameter("forumId", forum.getForumId());

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}

		String jpql = queryString + getOrderByQuery(paginationFilter.getSortingCriterias());
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("forumId", forum.getForumId());

        // We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)
		List<ForumTopic> list = getResultList(query);

		// We set search result on return method
		page.setList(list);
		
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumTopic> getForumTopicsByParentForumAndDocument(Integer forumId, Integer documentId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM ForumTopic WHERE logicalDelete = false AND forum.forumId = :forumId AND document.entryId = :documentId ORDER BY dateCreated DESC");
        query.setParameter("forumId", forumId);
        query.setParameter("documentId", documentId);
        
        return getResultList(query);
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
		
		String jpql = objectsQuery + getOrderByQuery(paginationFilter.getSortingCriterias());
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE ForumTopic SET user.account=:newAccount WHERE user.account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}

}
