/*
 * UserInformation.java
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
package org.medici.docsources.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * UserInformation.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblUserInformation\"" ) 
public class UserInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8860668022533693786L;

	@Id
	@Column (name="\"account\"", length=30, nullable=false)
	private String account;
	@Column (name="\"activationDate\"")
	private Date activationDate;
	@Column (name="\"active\"")
	private Boolean active;
	@Column (name="\"approved\"")
	private Boolean approved;
	@Column (name="\"badLogin\"")
	private Integer badLogin;
	@Column (name="\"currentLoginDate\"")
	private Date currentLoginDate;
	@Column (name="\"expirationDate\"")
	private Date expirationDate;
	@Column (name="\"expirationPasswordDate\"")
	private Date expirationPasswordDate;
	@Column (name="\"lastLoginDate\"")
	private Date lastLoginDate;
	@Column (name="\"lastPasswordChangeDate\"")
	private Date lastPasswordChangeDate;
	@Column (name="\"locked\"")
	private Boolean locked;
	@Column (name="\"registrationDate\"")
	private Date registrationDate;

	/**
	 * 
	 */
	public UserInformation() {
		super();
	}

	/**
	 * 
	 * @param account
	 */
	public UserInformation(String account) {
		super();
		
		setAccount(account);
	}

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
	 * @return the activationDate
	 */
	public Date getActivationDate() {
		return activationDate;
	}

	/**
	 * @param activationDate the activationDate to set
	 */
	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	/**
	 * @return the approved
	 */
	public Boolean getApproved() {
		return approved;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the badLogin
	 */
	public Integer getBadLogin() {
		return badLogin;
	}

	/**
	 * @param badLogin the badLogin to set
	 */
	public void setBadLogin(Integer badLogin) {
		this.badLogin = badLogin;
	}

	/**
	 * @return the currentLoginDate
	 */
	public Date getCurrentLoginDate() {
		return currentLoginDate;
	}

	/**
	 * @param currentLoginDate the currentLoginDate to set
	 */
	public void setCurrentLoginDate(Date currentLoginDate) {
		this.currentLoginDate = currentLoginDate;
	}

	/**
	 * @return the expirationDate
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the expirationPasswordDate
	 */
	public Date getExpirationPasswordDate() {
		return expirationPasswordDate;
	}

	/**
	 * @param expirationPasswordDate the expirationPasswordDate to set
	 */
	public void setExpirationPasswordDate(Date expirationPasswordDate) {
		this.expirationPasswordDate = expirationPasswordDate;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return the lastPasswordChangeDate
	 */
	public Date getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}

	/**
	 * @param lastPasswordChangeDate the lastPasswordChangeDate to set
	 */
	public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
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
	 * @return the registrationDate
	 */
	public Date getRegistrationDate() {
		return registrationDate;
	}

	/**
	 * @param registrationDate the registrationDate to set
	 */
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

}
