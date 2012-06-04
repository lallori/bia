/*
 * ForumDAOJpaImpl.java
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
package org.medici.docsources.dao.forum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.docsources.common.util.ForumUtils;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.Forum.Type;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumDAOJpaImpl</b> is a default implementation of <b>ForumDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.Forum
 * {@link http://yensdesign.com/2008/10/making-mysql-forum-database-from-scratch/}
 * 
 */
@Repository
public class ForumDAOJpaImpl extends JpaDao<Integer, Forum> implements ForumDAO {
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
	private static final long serialVersionUID = 5977718269527833285L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findForumCategories(Forum forum) throws PersistenceException {
        StringBuffer stringBuffer = new StringBuffer("FROM Forum WHERE type='");
        stringBuffer.append(forum.getType());
        stringBuffer.append("' and forumParent ");

        if (forum.getId() == null)
        	stringBuffer.append(" is null");
        else {
        	stringBuffer.append("=");
        	stringBuffer.append(forum.getId());
        }
    	
        Query query = getEntityManager().createQuery(stringBuffer.toString());

        return query.getResultList();
	}

	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HashMap<Integer, List<Forum>> findForumsGroupByCategory(List<Integer> categoriesIds) throws PersistenceException {
		if (categoriesIds == null){
			return new HashMap<Integer, List<Forum>>(0);
		}

		// we sort input categories...
		Collections.sort(categoriesIds);

		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM Forum WHERE type=:typeForum " +
        " and forumParent.id in (:forumParent) and forumParent.type=:forumParentTypeCategory " +
        " order by forumParent asc, title asc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("forumParent", categoriesIds);
        query.setParameter("forumParentTypeCategory", Type.CATEGORY);

        List<Forum> forumResult = (List<Forum>) query.getResultList();
        
        return ForumUtils.convertToHashMapByCategory(forumResult, categoriesIds);
	}

	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findSubCategories(Forum forum) throws PersistenceException {
		if (forum == null){
			return new ArrayList<Forum>(0);
		}

		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM Forum WHERE type=:type and forumParent.id = :parentId " +
		" and forumParent.type=:parentType order by dispositionOrder asc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("type", Type.CATEGORY);
        query.setParameter("parentId", forum.getId());
        query.setParameter("parentType", Type.CATEGORY);

        return (List<Forum>) query.getResultList();
	}

	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findSubForums(Integer parentForumId) throws PersistenceException {
		if (parentForumId == null){
			return new ArrayList<Forum>(0);
		}

		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM Forum WHERE type=:typeForum  and forumParent.id = :forumParentId " +
		"and forumParent.type=:forumParentTypeForum order by dispositionOrder asc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("forumParentId", parentForumId);
        query.setParameter("forumParentTypeForum", Type.FORUM);

        return (List<Forum>) query.getResultList();
  	}
	

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getCategory(Forum category) throws PersistenceException {
		if (category == null) {
			return null;
		}

		String stringQuery = "FROM Forum WHERE type=:typeForum  and id = :categoryId";
    	
        Query query = getEntityManager().createQuery(stringQuery);
        query.setParameter("typeForum", Type.CATEGORY);
        query.setParameter("categoryId", category.getId());

        List<Forum> list = query.getResultList();
		if (list.size() == 0) { 
			return null;
		} else {
			return (Forum) query.getResultList().get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getFirstCategory() throws PersistenceException {
		String stringQuery = "FROM Forum WHERE type=:typeForum order by id asc";
    	
        Query query = getEntityManager().createQuery(stringQuery);
        query.setParameter("typeForum", Type.CATEGORY);

        List<Forum> list = query.getResultList();
		if (list.size() == 0) { 
			return null;
		} else {
			return (Forum) query.getResultList().get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> getForumsByType(Type type) throws PersistenceException {
		String queryString = "FROM Forum WHERE type=:typeForum ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("typeForum", type);

        return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String, Long> getTotalTopicsAndPosts() throws PersistenceException {
		String queryString = "SELECT sum(topicsNumber), sum(postsNumber) FROM Forum";

        Query query = getEntityManager().createQuery(queryString);

        Object[] statisticsResult = (Object[]) query.getSingleResult();
        
        HashMap<String, Long> retValue = new HashMap<String, Long>(0);

        retValue.put("topicsNumber", (Long) statisticsResult[0]);
        retValue.put("postsNumber", (Long) statisticsResult[1]);

        return retValue;
	}
}
