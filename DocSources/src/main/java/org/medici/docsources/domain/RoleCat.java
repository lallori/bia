/*
 * RoleCat.java
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
import javax.persistence.Table;

import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * RoleCat entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Indexed
@AnalyzerDef(name="roleCatsAnalyzer",
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
@Table (name="\"tblRoleCats\"")
public class RoleCat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6975717198010607239L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"ROLECATID\"", length=10, nullable=false)
	@DocumentId
	private Integer roleCatId;

	@Column (name="\"ROLECATMINOR\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String roleCatMinor;
	
	@Column (name="\"ROLECATMAJOR\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String roleCatMajor;
	
	@Column(name="\"ROLECATMAJORID\"")
	private Integer roleCatMajorId;
	
	@Column (name="\"SORTGROUS\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer sortGroups;
	
	/**
	 * @return the roleCatId
	 */
	public Integer getRoleCatId() {
		return roleCatId;
	}
	
	/**
	 * @param roleCatId the roleCatId to set
	 */
	public void setRoleCatId(Integer roleCatId) {
		this.roleCatId = roleCatId;
	}
	
	/**
	 * @return the roleCatMinor
	 */
	public String getRoleCatMinor() {
		return roleCatMinor;
	}
	
	/**
	 * @param roleCatMinor the roleCatMinor to set
	 */
	public void setRoleCatMinor(String roleCatMinor) {
		this.roleCatMinor = roleCatMinor;
	}
	
	/**
	 * @return the roleCatMajor
	 */
	public String getRoleCatMajor() {
		return roleCatMajor;
	}
	
	/**
	 * @param roleCatMajor the roleCatMajor to set
	 */
	public void setRoleCatMajor(String roleCatMajor) {
		this.roleCatMajor = roleCatMajor;
	}
	
	/**
	 * @return the roleCatMajorId
	 */
	public Integer getRoleCatMajorId() {
		return roleCatMajorId;
	}
	
	/**
	 * @param roleCatMajorId the roleCatMajorId to set
	 */
	public void setRoleCatMajorId(Integer roleCatMajorId) {
		this.roleCatMajorId = roleCatMajorId;
	}
	
	/**
	 * @return the sortGroups
	 */
	public Integer getSortGroups() {
		return sortGroups;
	}
	
	/**
	 * @param sortGroups the sortGroups to set
	 */
	public void setSortGroups(Integer sortGroups) {
		this.sortGroups = sortGroups;
	}
}
