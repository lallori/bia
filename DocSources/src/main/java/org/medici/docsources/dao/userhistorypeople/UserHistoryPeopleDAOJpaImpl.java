/*
 * UserHistoryPeopleDAOJpaImpl.java
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
package org.medici.docsources.dao.userhistorypeople;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.UserHistoryPeople;
import org.medici.docsources.domain.UserHistoryDocument.Action;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * <b>UserHistoryPeopleDAOJpaImpl</b> is a default implementation of
 * <b>UserHistoryPeopleDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.LastHistory
 */
@Repository
public class UserHistoryPeopleDAOJpaImpl extends JpaDao<Integer, UserHistoryPeople> implements UserHistoryPeopleDAO {
	private final Logger logger = Logger.getLogger(this.getClass());

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
	private static final long serialVersionUID = 8495652438368426749L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserHistoryPeople> findHistory(Integer resultSize) {
        String queryString = "FROM UserHistoryPeople WHERE username=:username ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setMaxResults(resultSize);
        
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistoryPeople findLastEntryPeople() {
        String queryString = "FROM UserHistoryPeople WHERE username=:username ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setMaxResults(1);

		List<UserHistoryPeople> result = query.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		} 

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findHistory(PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(username) FROM UserHistoryPeople WHERE username=:username ORDER BY dateAndTime DESC";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			page.setTotal(new Long((Long)query.getSingleResult()));
		}

        String queryString = "FROM UserHistoryPeople WHERE username=:username ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		page.setList(query.getResultList());

		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void persist(UserHistoryPeople entity) throws PersistenceException {
		try {
			UserHistoryPeople lastUserHistoryPeople = findLastEntryPeople();
			
			if (lastUserHistoryPeople != null) {
				// if document is not the same, we persist action 
				if (!lastUserHistoryPeople.getPeople().getPersonId().equals(entity.getPeople().getPersonId())) {
					super.persist(entity);
				} else {
					// if document is not the same, we persist action
					if ((lastUserHistoryPeople.getAction().equals(Action.V)) && (entity.getAction().equals(Action.M))) {
						super.persist(entity);
					} else if ((lastUserHistoryPeople.getAction().equals(Action.V)) && (entity.getAction().equals(Action.D))) {
						super.persist(entity);
					}
					//otherwise we dont' persist
				}
			} else {
				//this case is for first access
				super.persist(entity);
			}
		} catch (PersistenceException persistenceException) {
			logger.error("Exception during persisting history", persistenceException);
		}
	}
}
