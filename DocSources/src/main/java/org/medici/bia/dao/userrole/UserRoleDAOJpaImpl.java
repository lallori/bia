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
	public List<UserRole> findUserRoles(String account) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserRole WHERE account=:account order by userAuthority.priority asc ");
		query.setParameter("account", account);

		return query.getResultList();
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
	public void addAllUserRoles(Set<UserRole> userRoles) throws PersistenceException {
		for (UserRole userRole : userRoles) {
			getEntityManager().persist(userRole);
		}
    }
}