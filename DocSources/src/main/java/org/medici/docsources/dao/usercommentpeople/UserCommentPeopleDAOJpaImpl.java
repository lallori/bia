/*
 * UserCommentPlaceDAOJpaImpl.java
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
package org.medici.docsources.dao.usercommentpeople;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.UserCommentPeople;
import org.springframework.stereotype.Repository;

/**
 * <b>UserCommentPlaceDAOJpaImpl</b> is a default implementation of
 * <b>UserCommentPeopleDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.UserHistory
 */
@Repository
public class UserCommentPeopleDAOJpaImpl extends JpaDao<Integer, UserCommentPeople> implements UserCommentPeopleDAO {

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
	private static final long serialVersionUID = 1361144582997153273L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserCommentPeople> findPersonComments(Integer peopleId)  throws PersistenceException {
        String queryString = "FROM UserCommentPeople WHERE peopleId=:peopleId ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("peopleId", peopleId);
        
		return query.getResultList();
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Page findPersonComments(Integer peopleId, PaginationFilter paginationFilter) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(username) FROM UserCommentPeople WHERE peopleId=:peopleId ORDER BY dateAndTime DESC";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("peopleId", peopleId);
			page.setTotal(new Long((Long)query.getSingleResult()));
		}

        String queryString = "FROM UserCommentPeople WHERE peopleId=:peopleId ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("peopleId", peopleId);
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		page.setList(query.getResultList());

		return page;
	}
}