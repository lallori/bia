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
package org.medici.docsources.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

/**
 * PlaceGeographicCoordinates entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="placeGeographicCoordinatesAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/docsources/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "placeGeographicCoordinatesNGram3Analyzer",
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
@Table ( name = "\"tblPlaceGeographicCoordinates\"" ) 
public class PlaceGeographicCoordinates implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4400714671552207917L;

	@Id
	private Integer id;
	
	@MapsId 
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"PLACEALLID\"")
	@ContainedIn
	private Place place;

	@Column (name="\"DEGREE_LATITUDE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer degreeLatitude;

	@Column (name="\"MINUTE_LATITUDE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer minuteLatitude;

	@Column (name="\"SECOND_LATITUDE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer secondLatitude;

	@Column (name="\"DIRECTION_LATITUDE\"", length=1)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String directionLatitude;
	
	@Column (name="\"DEGREE_LONGITUDE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer degreeLongitude;

	@Column (name="\"MINUTE_LONGITUDE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer minuteLongitude;

	@Column (name="\"SECOND_LONGITUDE\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer secondLongitude;

	@Column (name="\"DIRECTION_LONGITUDE\"", length=1)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String directionLongitude;

	/**
	 * Default constructor
	 */
	public PlaceGeographicCoordinates() {
		super();
	}
	
	/**
	 * 
	 * @param placeAllId
	 */
	public PlaceGeographicCoordinates(Integer placeAllId){
		super();
		setId(placeAllId);
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
	 * @param degreeLatitude the degreeLatitude to set
	 */
	public void setDegreeLatitude(Integer degreeLatitude) {
		this.degreeLatitude = degreeLatitude;
	}

	/**
	 * 
	 * @return the degreeLatitude
	 */
	public Integer getDegreeLatitude() {
		return degreeLatitude;
	}

	/**
	 * 
	 * @param minuteLatitude the minuteLatitude to set
	 */
	public void setMinuteLatitude(Integer minuteLatitude) {
		this.minuteLatitude = minuteLatitude;
	}

	/**
	 * 
	 * @return the minuteLatitude
	 */
	public Integer getMinuteLatitude() {
		return minuteLatitude;
	}

	/**
	 * 
	 * @param secondLatitude the secondLatitude to set
	 */
	public void setSecondLatitude(Integer secondLatitude) {
		this.secondLatitude = secondLatitude;
	}

	/**
	 * 
	 * @return the secondLatitude
	 */
	public Integer getSecondLatitude() {
		return secondLatitude;
	}

	/**
	 * 
	 * @param directionLatitude the directionLatitude to set
	 */
	public void setDirectionLatitude(String directionLatitude) {
		this.directionLatitude = directionLatitude;
	}

	/**
	 * 
	 * @return the directionLatitude
	 */
	public String getDirectionLatitude() {
		return directionLatitude;
	}

	/**
	 * 
	 * @param degreeLongitude the degreeLongitude to set
	 */
	public void setDegreeLongitude(Integer degreeLongitude) {
		this.degreeLongitude = degreeLongitude;
	}

	/**
	 * 
	 * @return the degreeLongitude
	 */
	public Integer getDegreeLongitude() {
		return degreeLongitude;
	}

	/**
	 * 
	 * @param minuteLongitude the minuteLongitude to set
	 */
	public void setMinuteLongitude(Integer minuteLongitude) {
		this.minuteLongitude = minuteLongitude;
	}

	/**
	 * 
	 * @return the minuteLongitude
	 */
	public Integer getMinuteLongitude() {
		return minuteLongitude;
	}

	/**
	 * 
	 * @param secondLongitude the secondLongitude to set
	 */
	public void setSecondLongitude(Integer secondLongitude) {
		this.secondLongitude = secondLongitude;
	}

	/**
	 * 
	 * @return the secondLongitude
	 */
	public Integer getSecondLongitude() {
		return secondLongitude;
	}

	/**
	 * 
	 * @param directionLongitude the directionLongitude to set
	 */
	public void setDirectionLongitude(String directionLongitude) {
		this.directionLongitude = directionLongitude;
	}

	/**
	 * 
	 * @return the directionLongitude
	 */
	public String getDirectionLongitude() {
		return directionLongitude;
	}

	/**
	 * 
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	
}
