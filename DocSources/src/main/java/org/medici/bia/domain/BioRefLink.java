/*
 * BioRefLink.java
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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * BioRefLink entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblBioRefLink\"" ) 
public class BioRefLink implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4082659247909592476L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"REFID\"", length=10, nullable=false)
	@DocumentId
	private Integer refID;

	@ManyToOne
	@JoinColumn(name="\"PERSONID\"")
	@ContainedIn
	private People person;

	@ManyToOne
	@JoinColumn(name="\"BIBLIOID\"")
	@IndexedEmbedded
	private BiblioT biblioId;

	@Column (name="\"NOTES\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String notes;

	/**
	 * @return the refID
	 */
	public Integer getRefID() {
		return refID;
	}

	/**
	 * @param refID the refID to set
	 */
	public void setRefID(Integer refID) {
		this.refID = refID;
	}

	/**
	 * @return the person
	 */
	public People getPerson() {
		return person;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPerson(People person) {
		this.person = person;
	}

	/**
	 * @return the biblioId
	 */
	public BiblioT getBiblioId() {
		return biblioId;
	}

	/**
	 * @param biblioId the biblioId to set
	 */
	public void setBiblioId(BiblioT biblioId) {
		this.biblioId = biblioId;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
