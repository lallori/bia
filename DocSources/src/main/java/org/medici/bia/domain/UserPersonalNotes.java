/*
 * UserPersonalNotes.java
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
package org.medici.bia.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;

/**
 * Personal Notes entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblUserPersonalNotes\"" ) 
public class UserPersonalNotes implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1291475929350819638L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idPersonalNotes\"", length=10, nullable=false)
	private Integer idPersonalNotes;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"")
	private User user;

	@Column (name="\"personalNotes\"", columnDefinition="LONGTEXT")
	private String personalNotes;
	
	@Column (name="\"dateCreated\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column (name="\"lastUpdate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	/**
	 * Default Constructor 
	 */
	public UserPersonalNotes() {
		super();
	}

	/**
	 * 
	 * @param personalNotes
	 */
	public UserPersonalNotes(String personalNotes) {
		super();
		setPersonalNotes(personalNotes);
	}

	/**
	 * 
	 * @param idPersonalNotes
	 */
	public void setIdPersonalNotes(Integer idPersonalNotes) {
		this.idPersonalNotes = idPersonalNotes;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getIdPersonalNotes() {
		return idPersonalNotes;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param personalNotes the personalNotes to set
	 */
	public void setPersonalNotes(String personalNotes) {
		this.personalNotes = personalNotes;
	}

	/**
	 * @return the personalNotes
	 */
	public String getPersonalNotes() {
		return personalNotes;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		if (getUser() == null) {
			return "";
		}
		
		return getUser().toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getUser() == null) ? 0 : getUser().getAccount().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPersonalNotes other = (UserPersonalNotes) obj;
		if (getUser() == null) {
			if (other.getUser().getAccount() != null)
				return false;
		} else if (!getUser().getAccount().equals(other.getUser().getAccount()))
			return false;
		return true;
	}
}

