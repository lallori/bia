/*
 * DocReference.java
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
 * DocReference entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblDocReference\"" ) 
public class DocReference implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8740212419256240155L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"DOCREFERENCEID\"",length=10, nullable=false)
	@DocumentId
	private Integer docReferenceId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"ENTRYIDFROM\"")
	@ContainedIn
	private Document documentFrom;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"ENTRYIDTO\"")
	@ContainedIn
	private Document documentTo;
	
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;

	/**
	 * 
	 */
	public DocReference(){
		super();
	}

	/**
	 * 
	 * @param epLinkId
	 */
	public DocReference(Integer docReferenceId) {
		super();
		setDocReferenceId(docReferenceId);
	}

	/**
	 * 
	 * @param epLinkId
	 * @param entryId
	 */
	public DocReference(Integer docReferenceId, Integer entryIdFrom, Integer entryIdTo) {
		super(); 
		setDocReferenceId(docReferenceId);
		setDocumentFrom(new Document(entryIdFrom));
		setDocumentTo(new Document(entryIdTo));
	}
	
	/**
	 * @return the docReferenceId
	 */
	public Integer getDocReferenceId() {
		return docReferenceId;
	}

	/**
	 * @param docReferenceId the docReferenceId to set
	 */
	public void setDocReferenceId(Integer docReferenceId) {
		this.docReferenceId = docReferenceId;
	}

	/**
	 * @return the document
	 */
	public Document getDocumentFrom() {
		return documentFrom;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocumentFrom(Document documentFrom) {
		this.documentFrom = documentFrom;
	}
	
	/**
	 * 
	 * @return
	 */
	public Document getDocumentTo() {
		return documentTo;
	}
	
	/**
	 * @param person the people to set
	 */
	public void setDocumentTo(Document documentTo) {
		this.documentTo = documentTo;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((docReferenceId == null) ? 0 : docReferenceId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}
		
		if (! (obj instanceof DocReference)) {
			return false;
		}

		DocReference other = (DocReference) obj;
		if (docReferenceId == null) {
			if (other.docReferenceId != null) {
				return false;
			}
		} else if (!docReferenceId.equals(other.docReferenceId)) {
			return false;
		}
		
		return true;
	}
}
