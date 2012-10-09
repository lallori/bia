/*
 * ShowUserCommand.java
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
package org.medici.bia.command.admin;

import java.util.List;


/**
 * Command bean for action "show user".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.ShowUserController.docsources.controller.user.CreateUserController
 */
public class EditUserCommand {
	/** User's Account **/
	private String account;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String password;
	private String confirmPassword;
	private Boolean forcePswdChange;
	private Integer yearExpirationPassword;
	private Integer monthExpirationPassword;
	private Integer dayExpirationPassword;
	private Integer yearExpirationUser;
	private Integer monthExpirationUser;
	private Integer dayExpirationUser;
	private Boolean active;
	private Boolean locked;
	private Boolean approved;
	private List<String> userRoles;
	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * 
	 * @param forcePswdChange
	 */
	public void setForcePswdChange(Boolean forcePswdChange) {
		this.forcePswdChange = forcePswdChange;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean getForcePswdChange() {
		return forcePswdChange;
	}

	/**
	 * @return the yearExpirationPassword
	 */
	public Integer getYearExpirationPassword() {
		return yearExpirationPassword;
	}
	/**
	 * @param yearExpirationPassword the yearExpirationPassword to set
	 */
	public void setYearExpirationPassword(Integer yearExpirationPassword) {
		this.yearExpirationPassword = yearExpirationPassword;
	}
	/**
	 * @return the monthExpirationPassword
	 */
	public Integer getMonthExpirationPassword() {
		return monthExpirationPassword;
	}
	/**
	 * @param monthExpirationPassword the monthExpirationPassword to set
	 */
	public void setMonthExpirationPassword(Integer monthExpirationPassword) {
		this.monthExpirationPassword = monthExpirationPassword;
	}
	/**
	 * @return the dayExpirationPassword
	 */
	public Integer getDayExpirationPassword() {
		return dayExpirationPassword;
	}
	/**
	 * @param dayExpirationPassword the dayExpirationPassword to set
	 */
	public void setDayExpirationPassword(Integer dayExpirationPassword) {
		this.dayExpirationPassword = dayExpirationPassword;
	}
	/**
	 * @return the yearExpirationUser
	 */
	public Integer getYearExpirationUser() {
		return yearExpirationUser;
	}
	/**
	 * @param yearExpirationUser the yearExpirationUser to set
	 */
	public void setYearExpirationUser(Integer yearExpirationUser) {
		this.yearExpirationUser = yearExpirationUser;
	}
	/**
	 * @return the monthExpirationUser
	 */
	public Integer getMonthExpirationUser() {
		return monthExpirationUser;
	}
	/**
	 * @param monthExpirationUser the monthExpirationUser to set
	 */
	public void setMonthExpirationUser(Integer monthExpirationUser) {
		this.monthExpirationUser = monthExpirationUser;
	}
	/**
	 * @return the dayExpirationUser
	 */
	public Integer getDayExpirationUser() {
		return dayExpirationUser;
	}
	/**
	 * @param dayExpirationUser the dayExpirationUser to set
	 */
	public void setDayExpirationUser(Integer dayExpirationUser) {
		this.dayExpirationUser = dayExpirationUser;
	}
	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
	/**
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
	}
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	/**
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}
	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	/**
	 * @return the userRoles
	 */
	public List<String> getUserRoles() {
		return userRoles;
	}
	/**
	 * @param userRoles the userRoles to set
	 */
	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

}
