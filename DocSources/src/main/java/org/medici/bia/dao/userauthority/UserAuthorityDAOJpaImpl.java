/*
 * UserAuthorityDAOJpaImpl.java
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
package org.medici.bia.dao.userauthority;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Repository
public class UserAuthorityDAOJpaImpl extends JpaDao<Authority, UserAuthority> implements UserAuthorityDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5581140548283171672L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserAuthority> getAuthorities() throws PersistenceException {
		String queryString = "FROM UserAuthority ORDER BY priority desc";
		
		Query query = getEntityManager().createQuery(queryString);
		
		return (List<UserAuthority>) query.getResultList();	
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAuthority getMaximumAuthority(String accountId) {
		String queryString = "FROM UserAuthority WHERE priority = (SELECT MIN(uAuth.priority) FROM UserAuthority AS uAuth WHERE uAuth IN (SELECT userAuthority FROM UserRole where user.account = :accountId))";
		
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("accountId", accountId);
		
		return (UserAuthority) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, UserAuthority> getMaximumAuthorities(Set<String> accountsId) throws PersistenceException {
		return getMaxAuthority(accountsId, null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserAuthority getUserCourseAuthority(String account) throws PersistenceException {
		Set<String> accounts = new HashSet<String>();
		accounts.add(account);
		return getUsersCourseAuthority(accounts).get(account);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, UserAuthority> getUsersCourseAuthority(Set<String> accountsId) throws PersistenceException {
		UserAuthority.Authority[] filteredAuthorities = {UserAuthority.Authority.ADMINISTRATORS, UserAuthority.Authority.TEACHERS, UserAuthority.Authority.STUDENTS}; 
		return getMaxAuthority(accountsId, filteredAuthorities);
	}
	
	/* Privates */
	
	@SuppressWarnings("unchecked")
	private Map<String, UserAuthority> getMaxAuthority(Set<String> accounts, UserAuthority.Authority[] filterAuthorities) throws PersistenceException {
		String accountsStr = "";
		Iterator<String> accountsIter = accounts.iterator();
		while (accountsIter.hasNext()) {
			accountsStr += "'" + accountsIter.next() + "'";
			if (accountsIter.hasNext()) {
				accountsStr += ", ";
			}
		}
		
		String authorities = "";
		if (filterAuthorities != null) {
			for(int i = 0; i < filterAuthorities.length; i++) {
				authorities += "'" + filterAuthorities[i].getValue() + "'";
				if (i < filterAuthorities.length - 1) {
					authorities += ", ";
				}
			}
		}
		
		StringBuilder queryString = new StringBuilder("SELECT ur.user.account, ur.userAuthority FROM UserRole AS ur, UserAuthority AS uAuth")
			.append(" WHERE ur.userAuthority = uAuth.authority ")
			.append(" AND (ur.user.account, uAuth.priority) = (")
			.append(" SELECT iu.user.account AS account, MIN(iuAuth.priority) AS priority")
			.append(" FROM UserRole AS iu, UserAuthority AS iuAuth")
			.append(" WHERE iu.user.account IN (").append(accountsStr).append(")")
			.append(" AND iu.userAuthority = iuAuth.authority");
		if (filterAuthorities != null) {
			queryString.append(" AND iuAuth.authority IN (").append(authorities).append(")");
		}
		queryString.append(" AND iu.user.account = ur.user.account")
			.append(" GROUP BY iu.user.account)");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		List<Object[]> userAuthorities = query.getResultList();
		
		Map<String, UserAuthority> results = new HashMap<String, UserAuthority>();
		
		for (Object[] current : userAuthorities) {
			results.put((String)current[0], (UserAuthority)current[1]);
		}
		
		return results;
	}
}
