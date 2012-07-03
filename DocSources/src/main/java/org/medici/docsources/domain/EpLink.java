/*
 * EpLink.java
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * EpLink entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblEPLink\"" ) 
public class EpLink implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6326212954483660974L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"EPLINKID\"",length=10, nullable=false)
	@DocumentId
	private Integer epLinkId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"ENTRYID\"")
	@ContainedIn
	private Document document;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"PERSONID\"")
	@ContainedIn
	private People person;
	
	@Column (name="\"PORTRAIT\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean portrait;
	
	@Column (name="\"ASSIGNUNSURE\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean AssignUnsure;
	
	@Column (name="\"DOCROLE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String DocRole;
	
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;

	/**
	 * 
	 */
	public EpLink(){
		super();
	}

	/**
	 * 
	 * @param epLinkId
	 */
	public EpLink(Integer epLinkId) {
		super();
		setEpLinkId(epLinkId);
	}

	/**
	 * 
	 * @param epLinkId
	 * @param entryId
	 */
	public EpLink(Integer epLinkId, Integer entryId) {
		super(); 
		setEpLinkId(epLinkId);
		setDocument(new Document(entryId));
	}

	/**
	 * @return the epLinkId
	 */
	public Integer getEpLinkId() {
		return epLinkId;
	}
	
	/**
	 * @param epLinkId the epLinkId to set
	 */
	public void setEpLinkId(Integer epLinkId) {
		this.epLinkId = epLinkId;
	}
	
	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	
	/**
	 * @return the people
	 */
	public People getPerson() {
		return person;
	}
	
	/**
	 * @param person the people to set
	 */
	public void setPerson(People person) {
		this.person = person;
	}
	/**
	 * @return the portrait
	 */
	public Boolean getPortrait() {
		return portrait;
	}
	/**
	 * @param portrait the portrait to set
	 */
	public void setPortrait(Boolean portrait) {
		this.portrait = portrait;
	}
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	/**
	 * @return the assignUnsure
	 */
	public Boolean getAssignUnsure() {
		return AssignUnsure;
	}
	
	/**
	 * @param assignUnsure the assignUnsure to set
	 */
	public void setAssignUnsure(Boolean assignUnsure) {
		AssignUnsure = assignUnsure;
	}
	
	/**
	 * @return the docRole
	 */
	public String getDocRole() {
		return DocRole;
	}
	
	/**
	 * @param docRole the docRole to set
	 */
	public void setDocRole(String docRole) {
		DocRole = docRole;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((epLinkId == null) ? 0 : epLinkId.hashCode());
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
		EpLink other = (EpLink) obj;
		if (epLinkId == null) {
			if (other.epLinkId != null)
				return false;
		} else if (!epLinkId.equals(other.epLinkId))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("");

		if (!ObjectUtils.toString(getPerson()).equals("")) {
			stringBuilder.append(getPerson());
		}

		if (getAssignUnsure()) {
			stringBuilder.append(" [unsure]");
		}
		if (getPortrait()) {
			stringBuilder.append(" [portrait]");
		}
		
		return stringBuilder.toString();
	}
}
