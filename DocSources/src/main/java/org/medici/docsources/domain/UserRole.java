/*
 * UserRole.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.medici.docsources.domain.UserAuthority.Authority;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table(name = "tblUserRole")
public class UserRole implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1440029135147763315L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"userRoleId\"", length=10, nullable=false)
	private Integer userRoleId;
	
	@ManyToOne
	@JoinColumn(name="\"account\"")
	private User user;

	@ManyToOne
	@JoinColumn(name="\"authority\"")
	private UserAuthority userAuthority;

	/**
	 * Default Constructor.
	 */
	public UserRole() {
		super();
	}

	/**
	 * 
	 * @param user
	 * @param userAuthority
	 */
	public UserRole(User user, UserAuthority userAuthority) {
		super();
		
		setUser(user);
		setUserAuthority(userAuthority);
	}

	/**
	 * @return the userRoleId
	 */
	public Integer getUserRoleId() {
		return userRoleId;
	}

	/**
	 * @param userRoleId the userRoleId to set
	 */
	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userAuthority
	 */
	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	/**
	 * @param userAuthority the userAuthority to set
	 */
	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}

	/**
	 * 
	 * @param administrators
	 * @return
	 */
	public Boolean containsAuthority(Authority authority) {
		if ((authority ==null) || (getUserAuthority() == null)) {
			return null;
		}
		
		if (getUserAuthority().getAuthority().equals(authority)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
	
	@Override
	public String toString() {
		if (getUserAuthority() != null) {
			return getUserAuthority().toString();
		}
		return null;
	}
}
