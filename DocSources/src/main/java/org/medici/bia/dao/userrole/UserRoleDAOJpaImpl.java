/*
 * UserRoleDAOJpaImpl.java
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
package org.medici.bia.dao.userrole;

import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserRole;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Repository
public class UserRoleDAOJpaImpl extends JpaDao<Integer, UserRole> implements UserRoleDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3850321230372790874L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAllUserRoles(Set<UserRole> userRoles) throws PersistenceException {
		for (UserRole userRole : userRoles) {
			getEntityManager().persist(userRole);
		}
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserRole findUserRole(String account, Authority authority) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserRole WHERE user.account=:account AND userAuthority.authority=:authority");
		query.setParameter("account", account);
		query.setParameter("authority", authority);
		
		return getFirst(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserRole> filterUserRoles(Authority authority) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserRole WHERE userAuthority.authority=:authority");
		query.setParameter("authority", authority);
		
		return getResultList(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserRole> findUserRoles(String account) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserRole WHERE account=:account ORDER BY userAuthority.priority ASC");
		query.setParameter("account", account);

		return getResultList(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UserRole> getUserRolesNotInCourse(Integer courseId, List<Authority> filteredAuthorities) throws PersistenceException {
		String jpql = "FROM UserRole WHERE"
				+ " userAuthority.authority IN (:authorities)"
				+ " AND user NOT IN ("
					+ "SELECT DISTINCT userRole.user FROM CoursePeople WHERE"
					+ " course.courseId=:courseId)";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("courseId", courseId);
		query.setParameter("authorities", filteredAuthorities);
		
		return getResultList(query);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasRoleIn(String account, List<Authority> filteredAuthorities) throws PersistenceException {
		String jpql = "SELECT COUNT(*) FROM UserRole WHERE user.account = :account AND userAuthority.authority IN (:filteredAuthorities)";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("account", account);
		query.setParameter("filteredAuthorities", filteredAuthorities);
		
		return (Long)query.getSingleResult() > 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeAllUserRoles(String account) throws PersistenceException {
		String jpql = "DELETE FROM UserRole WHERE account=:account";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("account", account);
        
        return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE UserRole SET user.account=:newAccount WHERE user.account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}
}
