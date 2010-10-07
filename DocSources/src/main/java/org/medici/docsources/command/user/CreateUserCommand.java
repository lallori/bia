/*
 * CreateUserCommand.java
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
package org.medici.docsources.command.user;

import java.util.List;

import org.medici.docsources.domain.User.UserRole;

/**
 * Command bean for action "create user".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.merdici.docsources.controller.user.CreateUserController
 */
public class CreateUserCommand {
	/** User's Account **/
	private String account;
	/** User's Confirmation password **/
	private String confirmPassword;
	/** User's first name **/
	private String firstName;
	/** User's password **/
	private String password;
	/** User's security roles */
	private List<UserRole> userRole;

	/**
	 * This method returns account property.
	 * 
	 * @return the account name
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * This method returns confirmPassword property.
	 * 
	 * @return the confirmation password
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}

	/**
	 * This method returns firstName property.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * This method returns password property.
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method returns list of user's roles.
	 * 
	 * @return list of user's roles
	 */
	public List<UserRole> getUserRole() {
		return userRole;
	}

	/**
	 * This method sets account property.
	 * 
	 * @param account
	 *            the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * This method sets confirmPassword property.
	 * 
	 * @param confirmPassword
	 *            the confirmation password
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	/**
	 * This method sets first name.
	 * 
	 * @param firstName
	 *            the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * This method sets password property.
	 * 
	 * @param password
	 *            the user password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method sets list of user's roles.
	 * 
	 * @param userRole
	 *            the userRole to set
	 */
	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}
}
