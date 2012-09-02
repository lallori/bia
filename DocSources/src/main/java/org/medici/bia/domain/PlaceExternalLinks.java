/*
 * PlaceGeographicCoordinates.java
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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
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
 * PlaceExternalLinks entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="placeExternalLinksAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/bia/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "placeExternalLinksNGram3Analyzer",
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class ),
		filters = {
			@TokenFilterDef(factory = StandardFilterFactory.class),
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
			@TokenFilterDef(factory = NGramFilterFactory.class,
				params = { 
					@Parameter(name = "minGramSize", value = "3"),
					@Parameter(name = "maxGramSize", value = "3")
				})
		}
	)
})
@Audited
@Table ( name = "\"tblPlaceExternalLinks\"" ) 
public class PlaceExternalLinks implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3073983871421624820L;

	@Id
	@DocumentId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "\"PLACEEXTLINKSID\"", length=10, nullable=false)
	private Integer placeExternalLinksId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "\"PLACEALLID\"")
	@ContainedIn
	private Place place;
	
	@Column (name = "\"EXTERNAL_LINK\"", length=300)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String externalLink;
	
	@Column(name = "\"DESCRIPTION\"", length=100)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String description;
	
	/**
	 * Default constructor.
	 */
	public PlaceExternalLinks(){
		super();
	}
	
	/**
	 * 
	 * @param placeExternalLinksId
	 */
	public PlaceExternalLinks(Integer placeExternalLinksId){
		super();
		setPlaceExternalLinksId(placeExternalLinksId);
	}
	
	/**
	 * 
	 * @param placeExternalLinksId
	 * @param placeAllId
	 */
	public PlaceExternalLinks(Integer placeExternalLinksId, Integer placeAllId){
		super();
		setPlaceExternalLinksId(placeExternalLinksId);
		setPlace(new Place(placeAllId));
	}

	/**
	 * 
	 * @param placeExternalLinksId the placeExternalLinksId to set
	 */
	public void setPlaceExternalLinksId(Integer placeExternalLinksId) {
		this.placeExternalLinksId = placeExternalLinksId;
	}

	/**
	 * 
	 * @return the placeExternalLinksId
	 */
	public Integer getPlaceExternalLinksId() {
		return placeExternalLinksId;
	}

	/**
	 * 
	 * @param place the place to set
	 */
	public void setPlace(Place place) {
		this.place = place;
	}

	/**
	 * 
	 * @return the place
	 */
	public Place getPlace() {
		return place;
	}

	/**
	 * 
	 * @param externalLink the externalLink to set
	 */
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}

	/**
	 * 
	 * @return the externalLink
	 */
	public String getExternalLink() {
		return externalLink;
	}

	/**
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	
	
}
