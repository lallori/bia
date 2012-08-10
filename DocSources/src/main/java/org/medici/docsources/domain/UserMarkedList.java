/*
 * UserMarkedList.java
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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.ContainedIn;

/**
 * UserMarkedList entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblUserMarkedList\"" ) 
public class UserMarkedList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3734728422460791279L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idMarkedList\"", length=10, nullable=false)
	private Integer idMarkedList;

	@Column (name="\"description\"", length=100, nullable=false)
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column (name="\"dateCreated\"")
	private Date dateCreated;

	@Column (name="\"dateLastUpdate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastUpdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"account\"", nullable=true)
	private User user;

    @OneToMany(mappedBy="userMarkedList", fetch=FetchType.LAZY)
    @ContainedIn
    private Set<UserMarkedListElement> userMarkedListElements;
    
    /**
	 * Default Constructor
	 */
	public UserMarkedList() {
		super();
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the idMarkedList
	 */
	public Integer getIdMarkedList() {
		return idMarkedList;
	}
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param idMarkedList the idMarkedList to set
	 */
	public void setIdMarkedList(Integer idMarkedList) {
		this.idMarkedList = idMarkedList;
	}
	
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @param userMarkedListElements the userMarkedListElements to set
	 */
	public void setUserMarkedListElements(Set<UserMarkedListElement> userMarkedListElements) {
		this.userMarkedListElements = userMarkedListElements;
	}

	/**
	 * @return the userMarkedListElements
	 */
	public Set<UserMarkedListElement> getUserMarkedListElements() {
		return userMarkedListElements;
	}

	/**
	 * @param dateLastUpdate the dateLastUpdate to set
	 */
	public void setDateLastUpdate(Date dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

	/**
	 * @return the dateLastUpdate
	 */
	public Date getDateLastUpdate() {
		return dateLastUpdate;
	}
}

