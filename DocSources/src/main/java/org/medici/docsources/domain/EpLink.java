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

import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * EpLink entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="epLinkAnalyzer",
		  charFilters = {
		    @CharFilterDef(factory = MappingCharFilterFactory.class, params = {
		      @Parameter(name = "mapping", value = "org/medici/docsources/mapping-chars.properties")
		    })
		  },
		  tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		  filters = {
		    @TokenFilterDef(factory = ISOLatin1AccentFilterFactory.class)
		    })
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
	private People people;
	
	@Column (name="\"PORTRAIT\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean portrait;
	
	@Column (name="\"ASSIGNUNSURE\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean AssignUnsure;
	
	@Column (name="\"DOCROLE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String DocRole;
	
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;

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
	public People getPeople() {
		return people;
	}
	
	/**
	 * @param people the people to set
	 */
	public void setPeople(People people) {
		this.people = people;
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
	
}
