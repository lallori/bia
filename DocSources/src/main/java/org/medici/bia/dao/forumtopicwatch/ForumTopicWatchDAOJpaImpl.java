/*
 * ForumTopicWatchDAOJpaImpl.java
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
package org.medici.bia.dao.forumtopicwatch;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.ForumTopicWatch;
import org.medici.bia.domain.User;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumTopicWatchDAOJpaImpl</b> is a default implementation of <b>ForumTopicWatchDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.ForumPost
 * {@link http://yensdesign.com/2008/10/making-mysql-forum-database-from-scratch/}
 *
 */
@Repository
public class ForumTopicWatchDAOJpaImpl extends JpaDao<Integer, ForumTopicWatch> implements ForumTopicWatchDAO {
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
	private static final long serialVersionUID = -806211580033765020L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ForumTopicWatch findByTopicAndUser(User user, ForumTopic forumTopic) throws PersistenceException {
		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM ForumTopicWatch WHERE topic.topicId=:topicId AND user.account=:account";

		if (forumTopic == null) {
			return null;
		}
		
		logger.debug("JPQL Query : " + jpql);
		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("topicId", forumTopic.getTopicId());
        query.setParameter("account", user.getAccount());

		// We manage sorting (this manages sorting on multiple fields)
		List<ForumTopicWatch> list = (List<ForumTopicWatch>) query.getResultList();

		if (list.size() ==1) { 
			return (ForumTopicWatch) list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersSubscribedOnForumTopic(ForumTopic forumTopic) throws PersistenceException {
		String jpql = "SELECT DISTINCT(watch.user) FROM ForumTopicWatch watch WHERE watch.topic.topicId=:topicId";

		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("topicId", forumTopic.getTopicId());

        return (List<User>) query.getResultList();
	}

	/**
	 * 
	 */
	@Override
	public Integer removeUserSubscribes(User user) throws PersistenceException {
		String jpql = "DELETE FROM ForumTopicWatch WHERE user.account=:account";

        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("account", user.getAccount());
        
        return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE ForumTopicWatch SET user.account=:newAccount WHERE user.account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}
}
