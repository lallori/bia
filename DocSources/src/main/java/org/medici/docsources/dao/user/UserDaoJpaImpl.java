/*
 * UserDaoJpaImpl.java
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
package org.medici.docsources.dao.user;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.User.UserRole;
import org.medici.docsources.exception.TooManyUsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Repository
public class UserDaoJpaImpl extends JpaDao<String, User> implements UserDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5787775317601975421L;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(String account) {
		try {
			Query query = getEntityManager().createQuery("FROM User WHERE account=:account");
			query.setParameter("account", account);
			
			query.setMaxResults(1);
			return (User) query.getSingleResult();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}

	/**
	 * 
	 */
	public User findUser(User user) throws TooManyUsersException {
		try {
			StringBuffer conditionBuffer = new StringBuffer("");
			if (user.getAccount() != null) {
				conditionBuffer.append("account=:account");
			}
			if (user.getInitials() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("initials=:initials");
			}
			if (user.getFirstName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("firstName=:firstName");
			}
			if (user.getLastName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("lastName=:lastName");
			}
			if (user.getOrganization() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("organization:organization");
			}
			if (user.getMail() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("mail=:mail");
			}
			
			Query query = getEntityManager().createQuery("FROM User WHERE " + conditionBuffer.toString());
			if (user.getAccount() != null) {
				query.setParameter("account", user.getAccount());
			}
			if (user.getInitials() != null) {
				query.setParameter("initials", user.getInitials());
			}
			if (user.getFirstName() != null) {
				query.setParameter("firstName", user.getFirstName());
			}
			if (user.getLastName() != null) {
				query.setParameter("lastName", user.getLastName());
			}
			if (user.getOrganization() != null) {
				query.setParameter("organization", user.getOrganization());
			}
			if (user.getMail() != null) {
				query.setParameter("mail", user.getMail());
			}
			
			query.setMaxResults(1);
			return (User) query.getSingleResult();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}

	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public UserRole findUserRole(UserRole userRole) {
		return null;
	}

	/**
	 * Search for all users matching the supplied filter on following fields : -
	 * account - first name - last name - organization - mail address
	 * 
	 * The Attributes in each SearchResult is supplied to the specified
	 * AttributesMapper.
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<User> findUsers(User user) {
		try {
			StringBuffer conditionBuffer = new StringBuffer("");
			if (user.getAccount() != null) {
				conditionBuffer.append("account=:account");
			}
			if (user.getInitials() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("initials=:initials");
			}
			if (user.getFirstName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("firstName=:firstName");
			}
			if (user.getLastName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("lastName=:lastName");
			}
			if (user.getOrganization() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("organization:organization");
			}
			if (user.getMail() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("mail=:mail");
			}
			
			Query query = getEntityManager().createQuery("FROM User WHERE " + conditionBuffer.toString());
			if (user.getAccount() != null) {
				query.setParameter("account", user.getAccount());
			}
			if (user.getInitials() != null) {
				query.setParameter("initials", user.getInitials());
			}
			if (user.getFirstName() != null) {
				query.setParameter("firstName", user.getFirstName());
			}
			if (user.getLastName() != null) {
				query.setParameter("lastName", user.getLastName());
			}
			if (user.getOrganization() != null) {
				query.setParameter("organization", user.getOrganization());
			}
			if (user.getMail() != null) {
				query.setParameter("mail", user.getMail());
			}
			
			return query.getResultList();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}
	
	/**
	 * 
	 */
	@Override
	public Page findUsers(User user, Integer pageNumber, Integer pageSize) {
		return null;
	}

	/**
	 * @return the passwordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * 
	 * @param account
	 * @param userRole
	 */
	public void persistUserRoles(String account, List<User.UserRole> userRoles) {
		return;
	}


	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void removeAllUserRoles(String account) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserRoles(String account, List<UserRole> userRoles) {
		// TODO Auto-generated method stub
		
	}
}
