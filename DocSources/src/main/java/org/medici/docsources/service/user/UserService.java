/*
 * UserService.java
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
package org.medici.docsources.service.user;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.UUID;

import org.medici.docsources.common.ajax.Page;
import org.medici.docsources.domain.Country;
import org.medici.docsources.domain.PasswordChangeRequest;
import org.medici.docsources.domain.User;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface UserService {

	/**
	 * Removes Application User from database.
	 * 
	 * @param user the application user to remove.
	 * 
	 * @throws ApplicationThrowable
	 */
	public void deleteUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Country> findCountries(String description)
	throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Country findCountry(String countryCode) throws ApplicationThrowable;

	/**
	 * This method finds a "password change request user information to permit data recovery by mail.
	 * 
	 * @param uuid
	 * @return PasswordChangeRequest Request to change password 
	 */
	public PasswordChangeRequest findPasswordChangeRequest(UUID uuid) throws ApplicationThrowable;

	/**
	 * 
	 * @param account
	 * @return
	 * @throws ApplicationThrowable
	 */
	public User findUser(String account) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public User findUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<User> findUsers(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @param pageNumber
	 * @param pageSize
	 * @param cookie
	 * @return
	 */
	public Page findUsers(User user, Integer pageNumber, Integer pageSize) throws ApplicationThrowable;

	/**
	 * 
	 * @param account
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Boolean isAccountAvailable(String account) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @throws ApplicationThrowable
	 */
	public void lockUser(User user) throws ApplicationThrowable;

	/**
	 * This method implement business logic for password rating.
	 * 
	 * @param password The password object to be rated.
	 * @return An integer rappresenting the rate. Values are between 1 and 4. 
	 */
	public Integer ratePassword(String password);

	/**
	 * 
	 * @param user
	 * @throws ApplicationThrowable
	 */
	public void registerNewUser(User user) throws ApplicationThrowable;

	/**
	 * 
	 * @param uuid
	 * @param password
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void updateUserPassword(UUID uuid, String password) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @throws ApplicationThrowable
	 */
	public void updateUser(User user) throws ApplicationThrowable;


	/**
	 * 
	 * @param user
	 * @param newPassword
	 * @throws ApplicationThrowable
	 */
	public void updateUserPassword(User user, String newPassword) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @param read
	 * @throws ApplicationThrowable
	 */
	public void updateUserPhoto(User user, BufferedImage bufferedImage) throws ApplicationThrowable;
}
