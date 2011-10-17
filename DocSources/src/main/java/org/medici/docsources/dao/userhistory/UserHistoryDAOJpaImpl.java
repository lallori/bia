/*
 * UserHistoryDAOJpaImpl.java
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
package org.medici.docsources.dao.userhistory;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.util.ImageUtils;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.Volume;
import org.medici.docsources.domain.UserHistory.BaseCategory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * <b>UserHistoryDAOJpaImpl</b> is a default implementation of
 * <b>UserHistoryDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.UserHistory
 */
@Repository
public class UserHistoryDAOJpaImpl extends JpaDao<Integer, UserHistory> implements UserHistoryDAO {

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
	private static final long serialVersionUID = -8769762056162920397L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory findLastEntryDocument() {
        String queryString = "FROM UserHistory WHERE account=:account and baseCategory =:baseCategory ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("baseCategory", BaseCategory.DOCUMENT);
        query.setMaxResults(1);
        
		List<UserHistory> result = query.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		} 

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory findLastEntryPerson() {
        String queryString = "FROM UserHistory WHERE account=:account and baseCategory =:baseCategory ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("baseCategory", BaseCategory.PEOPLE);
        query.setMaxResults(1);
        
		List<UserHistory> result = query.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		} 

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory findLastEntryPlace() {
        String queryString = "FROM UserHistory WHERE account=:account and baseCategory =:baseCategory ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("baseCategory", BaseCategory.PLACE);
        query.setMaxResults(1);
        
		List<UserHistory> result = query.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		} 

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserHistory findLastEntryVolume() {
        String queryString = "FROM UserHistory WHERE account=:account and baseCategory =:baseCategory ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("baseCategory", BaseCategory.VOLUME);
        query.setMaxResults(1);
        
		List<UserHistory> result = query.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		} 

		return null;
	}
}
