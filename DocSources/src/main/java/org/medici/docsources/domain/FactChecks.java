/*
 * FactChecks.java
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

/**
 * FactChecks entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblFactChecks\"" ) 
public class FactChecks implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7007840653476072338L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"VETID\"", length=10, nullable=false)
	private Integer vetId;
	@ManyToOne
	@JoinColumn(name="\"ENTRYID\"")
	private Document entryId;
	@Column (name="\"PERSON\"", length=50)
	private String person; 
	@Column (name="\"PLACE\"", length=50)
	private String place;
	@Column (name="\"DATEINFO\"", length=50)
	private String dateInfo;
	@Column (name="\"ADDLRES\"", columnDefinition="LONGTEXT")
	private String addLRes;
	@Column (name="\"EDITCOMMENT\"", columnDefinition="LONGTEXT")
	private String editComment;

	/**
	 * @return the vetId
	 */
	public Integer getVetId() {
		return vetId;
	}
	/**
	 * @param vetId the vetId to set
	 */
	public void setVetId(Integer vetId) {
		this.vetId = vetId;
	}
	/**
	 * @return the entryId
	 */
	public Document getEntryId() {
		return entryId;
	}
	/**
	 * @param entryId the entryId to set
	 */
	public void setEntryId(Document entryId) {
		this.entryId = entryId;
	}
	/**
	 * @return the person
	 */
	public String getPerson() {
		return person;
	}
	/**
	 * @param person the person to set
	 */
	public void setPerson(String person) {
		this.person = person;
	}
	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(String place) {
		this.place = place;
	}
	/**
	 * @return the dateInfo
	 */
	public String getDateInfo() {
		return dateInfo;
	}
	/**
	 * @param dateInfo the dateInfo to set
	 */
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	/**
	 * @return the addLRes
	 */
	public String getAddLRes() {
		return addLRes;
	}
	/**
	 * @param addLRes the addLRes to set
	 */
	public void setAddLRes(String addLRes) {
		this.addLRes = addLRes;
	}
	/**
	 * @return the editComment
	 */
	public String getEditComment() {
		return editComment;
	}
	/**
	 * @param editComment the editComment to set
	 */
	public void setEditComment(String editComment) {
		this.editComment = editComment;
	}
}
