/*
 * UserHistoryPeople.java
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserHistoryPeople entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblUserHistoryPeople\"" ) 
public class UserHistoryPeople implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001847076255349765L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idUserHistoryPeople\"", length=10, nullable=false)
	private Integer idUserHistoryPeople;
	@Column (name="\"dateAndTime\"", nullable=false)
	private Date dateAndTime;
	@Column (name="\"username\"", length=25, nullable=false)
	private String username;
	@Column (name="\"action\"", length=10, nullable=true)
	@Enumerated(EnumType.STRING)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Action action;
	@Column (name="\"description\"", length=100, nullable=false)
	private String description;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"peopleId\"")
	private People people;

	/**
	 * Default Constructor
	 */
	public UserHistoryPeople() {
		super();
	}

	/**
	 * 
	 * @param description
	 * @param action
	 * @param people
	 */
	public UserHistoryPeople(String description, Action action, People people) {
		this.setDateAndTime(new Date());
		this.setUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
		this.setDescription(description);
		this.setAction(action);
		this.setPeople(people);
	}

	/**
	 * @param idUserHistoryPeople the idUserHistoryPeople to set
	 */
	public void setIdUserHistoryPeople(Integer idUserHistoryPeople) {
		this.idUserHistoryPeople = idUserHistoryPeople;
	}

	/**
	 * @return the idUserHistoryPeople
	 */
	public Integer getIdUserHistoryPeople() {
		return idUserHistoryPeople;
	}

	/**
	 * @param dateAndTime the dateAndTime to set
	 */
	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	/**
	 * @return the dateAndTime
	 */
	public Date getDateAndTime() {
		return dateAndTime;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(Action action) {
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param people the people to set
	 */
	public void setPeople(People people) {
		this.people = people;
	}

	/**
	 * @return the document
	 */
	public People getPeople() {
		return people;
	}

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[ ");
		stringBuffer.append(getDateAndTime());
		stringBuffer.append(" - ");
		stringBuffer.append(getUsername());
		stringBuffer.append(" - ");
		stringBuffer.append(getDescription());
		stringBuffer.append(" - ");
		stringBuffer.append(getAction());
		stringBuffer.append(" - ");
		stringBuffer.append(getPeople());
		stringBuffer.append(" ]");
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Action {
		C("Created"),
		M("Modified"),
		V("Viewed"),
		D("Deleted");
		
		private final String action;

	    private Action(String value) {
	    	action = value;
	    }

	    @Override
	    public String toString(){
	        return action;
	    }
	}
}

