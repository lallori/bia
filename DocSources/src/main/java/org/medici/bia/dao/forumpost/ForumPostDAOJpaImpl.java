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
package org.medici.bia.dao.forumpost;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.User;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumPostDAOJpaImpl</b> is a default implementation of <b>ForumPostDAO</b>.
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
	@Override
	public long countTopicPosts(Integer forumTopicId) {
		Query query = getEntityManager().createQuery("SELECT COUNT(*) FROM ForumPost WHERE topic.topicId = :topicId AND logicalDelete = false");
		query.setParameter("topicId", forumTopicId);
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteAllForumPosts(Integer forumId) throws PersistenceException {
		Query query = getEntityManager().createQuery("UPDATE ForumPost SET logicalDelete = true WHERE forum.forumId = :forumId");
		query.setParameter("forumId", forumId);
		
		return query.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteAllForumTopicPosts(Integer topicId) throws PersistenceException {
		Query query = getEntityManager().createQuery("UPDATE ForumPost SET logicalDelete = true WHERE topic.topicId = :topicId");
		query.setParameter("topicId", topicId);
		
		return query.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> deleteAllForumTopicPosts(List<Integer> topicIds) throws PersistenceException {
		if (topicIds == null || topicIds.size() == 0) {
			return new ArrayList<Integer>();
		}
		Query query = getEntityManager().createQuery("SELECT postId FROM ForumPost WHERE logicalDelete = false AND topic.topicId IN ( :topicIds )");
		query.setParameter("topicIds", topicIds);
		List<Integer> postIds = (List<Integer>) query.getResultList();
		
		if (postIds.size() > 0) {
			query = getEntityManager().createQuery("UPDATE ForumPost SET logicalDelete = true WHERE postId IN ( :postIds )");
			query.setParameter("postIds", postIds);
			query.executeUpdate();
		}
		
		return postIds;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<Object>> getActiveTopicsInformations(Integer page, Integer numberOfTopicsForPage) throws PersistenceException {
		String jpql = "SELECT topic.topicId, ROUND(COUNT(postId)/10), lastUpdate FROM ForumPost WHERE logicalDelete = false GROUP BY topic ORDER BY lastUpdate ASC";

		Query query = getEntityManager().createQuery(jpql );
		query.setFirstResult(PageUtils.calculeStart(page, numberOfTopicsForPage));
		query.setMaxResults(numberOfTopicsForPage);

        List<Object> list = (List<Object>) query.getResultList();
        
        Map<Integer, List<Object>> retValue = new HashMap<Integer, List<Object>>(0);
        
        for (int i=0; i<list.size(); i++) {
        	Object[] singleRow = (Object[]) list.get(i);
        	List<Object> singleRowValues = new ArrayList<Object>(0);
        	singleRowValues.add((Long) singleRow[1]);
        	singleRowValues.add((Date) singleRow[2]);
        	retValue.put((Integer) singleRow[0], singleRowValues);
        }

        return retValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumPost> getAllNotDeletedForumTopicPosts(Integer topicId) throws PersistenceException {
		String jpql = "FROM ForumPost WHERE topic.topicId = :topicId AND logicalDelete = false ORDER BY postId ASC";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("topicId", topicId);
		
		return getResultList(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getFirstForumTopicPostByCreationDate(Integer topicId) throws PersistenceException {
		return getFirstForumTopicPost(topicId, true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getFirstForumTopicPostByLastUpdate(Integer topicId) throws PersistenceException {
		return getFirstForumTopicPost(topicId, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page getForumTopicPosts(ForumTopic forumTopic, PaginationFilter paginationFilter) throws PersistenceException {
		String queryString = "FROM ForumPost WHERE topic.topicId = :topicId AND logicalDelete = false ";

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

			page.setTotal((Long) query.getSingleResult());
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}

		String jpql = queryString + getOrderByQuery(paginationFilter.getSortingCriterias());
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
	public ForumPost getLastForumPostByCreationDate(Forum forum) throws PersistenceException {
		return getLastForumPost(forum.getForumId(), true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getLastForumPostByLastUpdate(Forum forum) throws PersistenceException {
		return getLastForumPost(forum.getForumId(), false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getLastForumTopicPostByCreationDate(ForumTopic forumTopic) throws PersistenceException {
		return getLastForumTopicPost(forumTopic.getTopicId(), true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPost getLastForumTopicPostByLastUpdate(ForumTopic forumTopic) throws PersistenceException {
		return getLastForumTopicPost(forumTopic.getTopicId(), false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getMostActiveForumByUser(User user) throws PersistenceException {
		String jpql = "SELECT p.forum FROM ForumPost p WHERE p.user.account = :account AND p.logicalDelete = false GROUP BY p.forum ORDER BY COUNT(*) DESC";

		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("account", user.getAccount());

        // We set pagination to obtain first post...  
		query.setFirstResult(0);
		query.setMaxResults(1);

		List<Forum> list = (List<Forum>) query.getResultList();

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
	public ForumTopic getMostActiveTopicByUser(User user) throws PersistenceException {
		String jpql = "SELECT p.topic FROM ForumPost p WHERE p.user.account = :account AND p.logicalDelete = false GROUP BY p.topic ORDER BY COUNT(*) DESC";

		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("account", user.getAccount());

        // We set pagination to obtain first post...  
		query.setFirstResult(0);
		query.setMaxResults(1);

		List<ForumTopic> list = (List<ForumTopic>) query.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isParentPost(Integer postId) throws PersistenceException {
		String jpql = "SELECT COUNT(*) FROM ForumPost WHERE parentPost.postId = :postId AND logicalDelete = false ";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("postId", postId);
		
		Long count = (Long)query.getSingleResult();
		return count > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE ForumPost SET user.account = :newAccount WHERE user.account = :originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
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
			page.setTotal((Long) query.getSingleResult());
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		}

		String jpql = searchContainer.toJPAQuery() + getOrderByQuery(paginationFilter.getSortingCriterias());
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
		// We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We set search result on return method
		page.setList(query.getResultList());
		
		return page;
	}


	/* Privates */
	
	/**
	 * Returns the first post of a topic (by creation date or by last update).
	 * 
	 * @param topicId the topic identifier
	 * @param byCreationDate if true the creation date is considered, otherwise the last update date is considered
	 * @return the first post of the topic
	 * @throws PersistenceException
	 */
	private ForumPost getFirstForumTopicPost(Integer topicId, boolean byCreationDate) throws PersistenceException {
		return getForumTopicBoundPost(topicId, byCreationDate, true);
	}
	
	/**
	 * Returns the last post of a topic (by creation date or by last update).
	 * 
	 * @param topicId the topic identifier
	 * @param byCreationDate if true the creation date is considered, otherwise the last update date is considered
	 * @return the last post of the topic
	 * @throws PersistenceException
	 */
	private ForumPost getLastForumTopicPost(Integer topicId, boolean byCreationDate) throws PersistenceException {
		return getForumTopicBoundPost(topicId, byCreationDate, false);
	}
	
	private ForumPost getForumTopicBoundPost(Integer topicId, boolean byCreationDate, boolean first) throws PersistenceException {
		String jpql = "FROM ForumPost WHERE topic.topicId = :topicId AND logicalDelete = false ORDER BY ";
		jpql += byCreationDate ? "dateCreated " : "lastUpdate ";
		jpql += first ? "ASC" : "DESC";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("topicId", topicId);
		
		return getFirst(query);
	}
	
	/**
	 * Returns the first post of a forum (by creation date or by last update).
	 * 
	 * @param forum the forum
	 * @param byCreationDate if true the creation date is considered, otherwise the last update date is considered
	 * @return the first post of the forum
	 */
	private ForumPost getLastForumPost(Integer forumId, boolean byCreationDate) {
		StringBuffer jpql = new StringBuffer("FROM ForumPost WHERE forum.fullPath LIKE '%.");
		jpql.append(forumId)
			.append(".%' AND logicalDelete = false ORDER BY ")
			.append(byCreationDate ? "dateCreated DESC" : "lastUpdate DESC");
		
		Query query = getEntityManager().createQuery(jpql.toString());
		
		return getFirst(query);
	}

}
