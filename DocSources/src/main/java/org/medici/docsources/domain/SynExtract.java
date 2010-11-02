/*
 * SynExtract.java
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
 * SynExtract entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblSynExtracts\"" ) 
public class SynExtract implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4992402820748980851L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"SynExtrID\"", length=100, nullable=false)
	private Integer synExtrId;
	@ManyToOne
	@JoinColumn(name="\"ENTRYID\"")
	private Document entryId;
	@Column(name="\"DOCEXTRACT\"", columnDefinition="LONGTEXT")
	private String docExtract;
	@Column(name="\"SYNOPSIS\"", columnDefinition="LONGTEXT")
	private String synopsis;
	@Column(name="\"LastUpdate\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	@Column(name="\"DateCreated\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	/**
	 * @return the synExtrId
	 */
	public Integer getSynExtrId() {
		return synExtrId;
	}
	/**
	 * @param synExtrId the synExtrId to set
	 */
	public void setSynExtrId(Integer synExtrId) {
		this.synExtrId = synExtrId;
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
	 * @return the docExtract
	 */
	public String getDocExtract() {
		return docExtract;
	}
	/**
	 * @param docExtract the docExtract to set
	 */
	public void setDocExtract(String docExtract) {
		this.docExtract = docExtract;
	}
	/**
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	/**
	 * @return the lastUpdate
	 */
	public Date getLastUpdate() {
		return lastUpdate;
	}
	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
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
	
}
