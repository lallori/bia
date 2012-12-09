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
package org.medici.bia.dao.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.UserSearch;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserRole;
import org.medici.bia.exception.TooManyUsersException;

/**
 * User Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface UserDAO extends Serializable {

	/**
	 * 
	 * @return
	 */
	Long countMembersForum();

	/**
	 * 
	 * @param letter
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page findForumMembers(String letter, PaginationFilter paginationFilter);

	/**
     * This method finds the user with input account.
	 * 
	 * @param account a {@link java.lang.String} object.
	 * @return a {@link org.medici.bia.domain.User} object.
	 */
	User findUser(String account);

	/**
     * This method searches a single user with paramter setted in input object.
	 * 
	 * @param user a {@link org.medici.bia.domain.User} object.
	 * @return a {@link org.medici.bia.domain.User} object or null if
	 * there isn't an user with the specified parameters.
	 * @throws org.medici.bia.exception.TooManyUsersException if the
	 *         search find more than one user.
	 */
	User findUser(User user) throws TooManyUsersException;

	/**
	 * 
	 * @param text
	 * @return
	 */
	List<User> findUsers(String text);
	
	/**
     * This method returns a list of User object that match with input parameter
	 * 
	 * @param user a {@link org.medici.bia.domain.User} object.
	 * @return a {@link java.util.List} object containing result of search.
	 */
	List<User> findUsers(User user);

	/**
	 * Search method which managed pagination.
	 * 
	 * @param user a {@link org.medici.bia.domain.User} object
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Page findUsers(User user, Integer pageNumber, Integer pageSize);
	
	/**
	 * 
	 * @param user
	 * @param paginationFilter
	 * @return
	 */
	Page findUsers(User user,PaginationFilter paginationFilter);

	/**
	 * 
	 * @return
	 */
	User getNewestMember();

	/**
	 * This method updates the input {@link org.medici.bia.domain.User} 
	 * object on the persistence layer. 
	 * 
	 * @param user the {@link org.medici.bia.domain.User} object to update.
	 */
	User merge(User user);

	/**
	 * This method persists the input {@link org.medici.bia.domain.User} 
	 * object on the persistence layer. 
	 * 
	 * @param user the {@link org.medici.bia.domain.User} object to persist.
	 */
	void persist(User user);

	/**
	 * This method associates the input user role to the user identified by
	 * the first input parameter.  
	 * 
	 * @param account a {@link java.lang.String} object.
	 * @param user a {@link java.util.List} object
	 */
	void persistUserRoles(String account, List<UserRole> userRole);

	/**
	 * This method removes input {@link org.medici.bia.domain.User} 
	 * object from the persistence layer. 
	 * 
	 * @param user {@link org.medici.bia.domain.User} object to remove.
	 */
	void remove(User user);

	/**
	 * This method removes all user's role from the specific user identified by 
	 * the account given in input to the method.
	 * 
	 * @param account a {@link java.lang.String} object.
	 */
	void removeAllUserRoles(String account);

	/**
	 * This method removes the list of user's role given in input from the 
	 * specific user.
	 * 
	 * @param account a {@link java.lang.String} object.
	 * @param user a {@link java.util.List} object
	 */
	void removeUserRoles(String account, List<UserRole> userRoles);

	/**
	 * 
	 * @param userSearch
	 * @param paginationFilter
	 * @return
	 */
	Page searchMYSQL(org.medici.bia.common.search.Search searchContainer, PaginationFilter paginationFilter);

}
