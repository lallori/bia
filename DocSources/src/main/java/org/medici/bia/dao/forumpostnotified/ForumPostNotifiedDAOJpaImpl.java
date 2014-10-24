/*
 * ForumPostNotifiedDAOJpaImpl.java
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
package org.medici.bia.dao.forumpostnotified;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.ForumPostNotified;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumPostNotifiedDAOJpaImpl</b> is a default implementation of <b>ForumPostNotifiedDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.ForumPostNotified
 * {@link http://yensdesign.com/2008/10/making-mysql-forum-database-from-scratch/}
 *
 */
@Repository
public class ForumPostNotifiedDAOJpaImpl extends JpaDao<Integer, ForumPostNotified> implements ForumPostNotifiedDAO {
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
	private static final long serialVersionUID = -954510449538455917L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForumPostNotified> findForumPostRepliedNotNotified() throws PersistenceException {
		String jpql = "FROM ForumPostNotified WHERE mailSended = :mailSended";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("mailSended", Boolean.FALSE);

		return getResultList(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ForumPostNotified getForumPostNotifiedByPost(Integer forumPostId) throws PersistenceException {
		String jpql = "FROM ForumPostNotified WHERE postId = :postId";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("postId", forumPostId);
		
		return getFirst(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeAllNotSentForumPostNotifications(List<Integer> postIds) throws PersistenceException {
		if (postIds == null || postIds.size() == 0) {
			return 0;
		}
		
		Query query = getEntityManager().createQuery("DELETE FROM ForumPostNotified WHERE mailSended = false AND postId IN ( :postIds )");
		query.setParameter("postIds", postIds);
		
		return query.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeAllNotSentForumPostNotificationsByForum(Integer forumId, int forumDepth) throws PersistenceException {
		String jpql = "DELETE FROM ForumPostNotified AS notified" +
				" WHERE notified.mailSended = false" +
				" AND notified.postId IN (" +
				" SELECT post.postId" +
				" FROM ForumPost AS post" +
				" WHERE" +
				" post.forum.forumId = :forumId";
		
		String forumDepthString = "post.forum";
		for (int i = 1; i < forumDepth; i++) {
			forumDepthString += ".forumParent";
			jpql += " OR (" + forumDepthString +" IS NOT NULL AND " + forumDepthString + ".forumId = :forumId)";
		}
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("forumId", forumId);
		
		return query.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeAllNotSentForumPostNotificationsByTopic(Integer topicId) throws PersistenceException {
		Query query = getEntityManager().createQuery("DELETE FROM ForumPostNotified AS notified" +
					" WHERE notified.mailSended = false" +
					" AND notified.postId IN (" +
						" SELECT post.postId" +
						" FROM ForumPost AS post" +
						" WHERE post.topic.topicId = :topicId" +
					")");
		query.setParameter("topicId", topicId);
		
		return query.executeUpdate();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeBadElements()  throws PersistenceException {
		String jpqlFirst = "DELETE FROM ForumPostNotified AS notified "
				+ "WHERE notified.mailSended = false AND "
				+ "notified.postId IN "
				+ "(SELECT DISTINCT post.postId FROM "
				+ "ForumPost AS post, "
				+ "ForumTopicWatch AS watch "
				+ "WHERE "
				+ "post.topic NOT IN (SELECT DISTINCT topic FROM ForumTopicWatch))";
		
		int removedFirst = getEntityManager().createQuery(jpqlFirst).executeUpdate();
		
		String jpqlSecond = "DELETE FROM ForumPostNotified AS notified "
				+ "WHERE notified.mailSended = false AND "
				+ "notified.postId IN "
				+ "(SELECT DISTINCT post.postId FROM "
				+ "ForumPost AS post, "
				+ "ForumTopicWatch AS watch "
				+ "WHERE "
				+ "watch.topic = post.topic AND "
				+ "(post.logicalDelete = true OR post.topic.logicalDelete = true))";
		
		int removedSecond = getEntityManager().createQuery(jpqlSecond).executeUpdate();
		
		return removedFirst + removedSecond;
	}


}
