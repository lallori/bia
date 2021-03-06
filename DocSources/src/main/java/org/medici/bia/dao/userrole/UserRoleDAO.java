/*
 * UserRoleDAO.java
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

import org.medici.bia.dao.Dao;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserRole;

/**
 * User Role DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface UserRoleDAO extends Dao<Integer, UserRole> {
	
	/**
	 * 
	 * @param userRoles
	 * @throws PersistenceException
	 */
	void addAllUserRoles(Set<UserRole> userRoles) throws PersistenceException;
	
	/**
	 * Returns the {@link UserRole} filtered by the provided {@link Authority} and user account.
	 * 
	 * @param account the user account
	 * @param authority the user authority to find
	 * @return the {@link UserRole} found
	 * @throws PersistenceException
	 */
	UserRole findUserRole(String account, Authority authority) throws PersistenceException;
	
	/**
	 * Returns all user roles filtered by the provided {@link Authority}.
	 * 
	 * @param authority the authority to filter
	 * @return the user roles found
	 * @throws PersistenceException
	 */
	List<UserRole> filterUserRoles(Authority authority) throws PersistenceException;
	
	/**
	 * 
	 * @param account
	 * @return
	 * @throws PersistenceException
	 */
	List<UserRole> findUserRoles(String account) throws PersistenceException;
	
	/**
	 * Returns user roles not associated to the provided course.
	 * 
	 * @param courseId the course identifier
	 * @param filteredAuthorities the authorities to search
	 * @return the list of user roles found
	 * @throws PersistenceException
	 */
	List<UserRole> getUserRolesNotInCourse(Integer courseId, List<Authority> filteredAuthorities) throws PersistenceException;
	
	/**
	 * Determines if a user has {@link UserRole}s with at least one of the provided {@link Authority}.
	 * 
	 * @param account the user account
	 * @param filteredAuthorities the authorities to filter
	 * @return true if the user has at least one of the provided roles, false otherwise
	 * @throws PersistenceException
	 */
	boolean hasRoleIn(String account, List<Authority> filteredAuthorities) throws PersistenceException;
	
	/**
	 * 
	 * @param account
	 * @throws PersistenceException
	 */
	Integer removeAllUserRoles(String account) throws PersistenceException;

	/**
	 * 
	 * @param originalAccount
	 * @param newAccount
	 * @return
	 * @throws PersistenceException
	 */
	Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException;

}
