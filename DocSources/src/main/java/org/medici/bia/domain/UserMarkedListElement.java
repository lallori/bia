/*
 * UserMarkedListElement.java
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


/**
 * UserMarkedListElement entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblUserMarkedListElement\"" ) 
public class UserMarkedListElement implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6851886567190669198L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"idMarkedList\"", nullable=true)
	private UserMarkedList userMarkedList;
	@Column (name="\"dateCreated\"")
	private Date dateCreated;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"entryId\"", nullable=true)
	private Document document;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"peopleId\"", nullable=true)
	private People person;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"placeAllId\"", nullable=true)
	private Place place;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"summaryId\"", nullable=true)
	private Volume volume;

	/**
	 * Default Constructor
	 */
	public UserMarkedListElement() {
		super();
	}

	/**
	 * 
	 * @param userMarkedList
	 */
	public UserMarkedListElement(UserMarkedList userMarkedList) {
		super();
		
		setUserMarkedList(userMarkedList);
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param people the person to set
	 */
	public void setPerson(People person) {
		this.person = person;
	}

	/**
	 * @return the person
	 */
	public People getPerson() {
		return person;
	}

	/**
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Volume volume) {
		this.volume = volume;
	}

	/**
	 * @return the volume
	 */
	public Volume getVolume() {
		return volume;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return "";
	}

	/**
	 * @param userMarkedList the userMarkedList to set
	 */
	public void setUserMarkedList(UserMarkedList userMarkedList) {
		this.userMarkedList = userMarkedList;
	}

	/**
	 * @return the userMarkedList
	 */
	public UserMarkedList getUserMarkedList() {
		return userMarkedList;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
}

