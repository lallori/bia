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

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Forum;
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

        if (forum.getForumParent() == null)
        	stringBuffer.append(" is null");
        else {
        	stringBuffer.append("=");
        	stringBuffer.append(forum.getForumParent().getId());
        }
    	
        Query query = getEntityManager().createQuery(stringBuffer.toString());

        return query.getResultList();
	}
}
