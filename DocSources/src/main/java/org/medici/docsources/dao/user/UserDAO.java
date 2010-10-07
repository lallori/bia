/*
 * UserDAO.java
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
import org.medici.docsources.common.ajax.Page;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.User.UserRole;
import org.medici.docsources.exception.TooManyUsersException;

/**
 * User Dao.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface UserDAO {

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User findUser(String account);

	/**
	 * 
	 * @param user
	 * @return
	 */
	public User findUser(User user) throws TooManyUsersException;

	/**
	 * 
	 * @param user
	 * @return
	 */
	public List<User> findUsers(User user);

	/**
	 * Search method which managed pagination.
	 * 
	 * @param user
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page findUsers(User user, Integer pageNumber, Integer pageSize);
	
	/**
	 * 
	 * @param user
	 */
	public void merge(User user);

	/**
	 * Metodo che salva le userRole sull'ldap.
	 * 
	 * @param account
	 * @param userRole
	 */
	public void persist(User user);

	/**
	 * Metodo che salva le userRole sull'ldap.
	 * 
	 * @param account
	 * @param userRole
	 */
	public void persistUserRoles(String account, List<UserRole> userRole);

	/**
	 * 
	 * @param user
	 */
	public void remove(User user);

	/**
	 * 
	 * @param account
	 * @param userRoles
	 */
	public void removeAllUserRoles(String account);

	/**
	 * 
	 * @param account
	 * @param userRoles
	 */
	public void removeUserRoles(String account, List<User.UserRole> userRoles);
}
