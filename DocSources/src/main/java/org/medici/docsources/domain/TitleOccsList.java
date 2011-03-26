/*
 * TitleOccList.java
 * 
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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * TitleOccList entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="titleOccsListAnalyzer",
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
@Table ( name = "\"tblTitleOccsList\"" ) 
public class TitleOccsList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8339800362047066324L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"TITLEOCCID\"", length=10, nullable=false)
	@DocumentId
	private Integer titleOccId;
	
	@Column (name="\"TITLEOCC\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String titleOcc;
	
	@ManyToOne
	@JoinColumn(name="\"ROLECATMINORID\"")
	@IndexedEmbedded
	private RoleCat roleCat;
	
	@Column (name="\"TITLEVARIANTS\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String titleVariants;

    @OneToMany(mappedBy="titleOccList", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<PoLink> poLinks;
	
	/**
	 * @return the titleOccId
	 */
	public Integer getTitleOccId() {
		return titleOccId;
	}
	
	/**
	 * @param titleOccId the titleOccId to set
	 */
	public void setTitleOccId(Integer titleOccId) {
		this.titleOccId = titleOccId;
	}
	
	/**
	 * @return the titleOcc
	 */
	public String getTitleOcc() {
		return titleOcc;
	}
	
	/**
	 * @param titleOcc the titleOcc to set
	 */
	public void setTitleOcc(String titleOcc) {
		this.titleOcc = titleOcc;
	}
	
	/**
	 * @return the roleCat
	 */
	public RoleCat getRoleCat() {
		return roleCat;
	}
	
	/**
	 * @param roleCat the roleCat to set
	 */
	public void setRoleCat(RoleCat roleCat) {
		this.roleCat = roleCat;
	}
	
	/**
	 * @return the titleVariants
	 */
	public String getTitleVariants() {
		return titleVariants;
	}
	
	/**
	 * @param titleVariants the titleVariants to set
	 */
	public void setTitleVariants(String titleVariants) {
		this.titleVariants = titleVariants;
	}

	public void setPoLinks(Set<PoLink> poLinks) {
		this.poLinks = poLinks;
	}

	public Set<PoLink> getPoLinks() {
		return poLinks;
	}
}
