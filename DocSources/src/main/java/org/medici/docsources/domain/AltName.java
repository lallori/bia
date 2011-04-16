/*
 * AltName.java
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * AltName entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="altNameAnalyzer",
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
@Table ( name = "\"tblAltNames\"" ) 
public class AltName implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5441152442133272419L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"NAMEID\"", length=10, nullable=false)
	@DocumentId
	private Integer nameId;

	@ManyToOne
	@JoinColumn(name="\"PERSONID\"")
	@ContainedIn
	private People person;

	@Column (name="\"NAMEPREFIX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String namePrefix;

	@Column (name="\"ALTNAME\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String altName;

	@Column (name="\"NAMETYPE\"", length=50, nullable=true)
	@Enumerated(EnumType.STRING)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private NameType nameType;

	@Column (name="\"NOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String notes;

	/**
	 * Default constructor.
	 * 
	 */
	public AltName() {
		super();
	}

	/**
	 * 
	 * @param nameId
	 */
	public AltName(Integer nameId) {
		setNameId(nameId);
	}

	/**
	 * @return the nameId
	 */
	public Integer getNameId() {
		return nameId;
	}
	
	/**
	 * @param nameId the nameId to set
	 */
	public void setNameId(Integer nameId) {
		this.nameId = nameId;
	}
	
	/**
	 * @return the personId
	 */
	public People getPerson() {
		return person;
	}
	
	/**
	 * @param person the person to set
	 */
	public void setPerson(People person) {
		this.person = person;
	}
	
	/**
	 * @return the namePrefix
	 */
	public String getNamePrefix() {
		return namePrefix;
	}
	
	/**
	 * @param namePrefix the namePrefix to set
	 */
	public void setNamePrefix(String namePrefix) {
		this.namePrefix = namePrefix;
	}
	
	/**
	 * @return the altName
	 */
	public String getAltName() {
		return altName;
	}
	
	/**
	 * @param altName the altName to set
	 */
	public void setAltName(String altName) {
		this.altName = altName;
	}
	
	/**
	 * @return the nameType
	 */
	public NameType getNameType() {
		return nameType;
	}
	
	/**
	 * @param nameType the nameType to set
	 */
	public void setNameType(NameType nameType) {
		this.nameType = nameType;
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

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum NameType {
		Patronymic("Patronymic"),
		Appellative("Appellative"),
		Family("Family"),
		Married("Married"),
		Given("Given"),	
		SearchName("SearchName"),
		Maiden("Maiden");
		
		private final String nameType;

	    private NameType(String value) {
	    	nameType = value;
	    }

	    @Override
	    public String toString(){
	        return nameType;
	    }
	}
}
