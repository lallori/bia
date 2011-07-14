/*
 * Person.java
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
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.NumericField;
import org.hibernate.search.annotations.NumericFields;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * Person entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="peopleAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/docsources/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class)
	}),
	@AnalyzerDef(name = "peopleNGram3Analyzer",
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
@Table ( name = "\"tblPeople\"" ) 
public class People implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6007789289980534157L;

	@Id
	@DocumentId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"PERSONID\"", length=10, nullable=false)
	private Integer personId;

	@Column (name="\"MAPNameLF\"", length=150)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="mapNameLf_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String mapNameLf;
	
	@Column (name="\"GENDER\"", length=1)
	@Enumerated(EnumType.STRING)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Gender gender;
	
	@Column (name="\"ACTIVESTART\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String activeStart;
	
	@Column (name="\"ACTIVEEND\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String activeEnd;
	
	@Column (name="\"BYEAR\"", length=4, nullable=true)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="bornYear_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="bornYear"),
		@NumericField(forField="bornYear_Sort")
	})
	private Integer bornYear;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"BMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month bornMonth;
	
	@Column (name="\"BDAY\"", length=3, columnDefinition="TINYINT")
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="bornDay_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="bornDay"),
		@NumericField(forField="bornDay_Sort")
	})
	private Integer bornDay;

	@Column (name="\"BORNDATE\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="bornDate_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="bornDate"),
		@NumericField(forField="bornDate_Sort")
	})
	private Integer bornDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"BPLACEID\"")
	@IndexedEmbedded(depth=1)
	private Place bornPlace;
	
	@Column (name="\"BPLACE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String bPlace;
	
	@Column (name="\"DYEAR\"", length=4, nullable=true)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="deathYear_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="deathYear"),
		@NumericField(forField="deathYear_Sort")
	})
	private Integer deathYear;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"DMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month deathMonth;
	
	@Column (name="\"DDAY\"", length=3, columnDefinition="TINYINT")
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="deathDay_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="deathDay"),
		@NumericField(forField="deathDay_Sort")
	})
	private Integer deathDay;

	@Column (name="\"DEATHDATE\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="deathDate_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="deathDate"),
		@NumericField(forField="deathDate_Sort")
	})
	private Integer deathDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"DPLACEID\"")
	@IndexedEmbedded(depth=1)
	private Place deathPlace;
	
	@Column (name="\"DPLACE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String dPlace;
	
	@Column (name="\"FIRST\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String first;
	
	@Column (name="\"SUCNUM\"", length=6)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String sucNum;
	
	@Column (name="\"MIDDLE\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String middle;
	
	@Column (name="\"MIDPREFIX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String midPrefix;
	
	@Column (name="\"LAST\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String last;
	
	@Column (name="\"LASTPREFIX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String lastPrefix;
	
	@Column (name="\"POSTLAST\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String postLast;
	
	@Column (name="\"POSTLASTPREFIX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String postLastPrefix;
	
	@Column (name="\"BAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean bornApprox;
	
	@Column (name="\"BDATEBC\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean bornDateBc;
	
	@Column (name="\"BPLACEUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean bornPlaceUnsure;
	
	@Column (name="\"DAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean deathApprox;
	
	@Column (name="\"DYEARBC\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean deathDateBc;
	
	@Column (name="\"DPLACEUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean deathPlaceUnsure;
	
	@Column (name="\"STATUS\"", length=15)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String status;
	
	@Column (name="\"BIONOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String bioNotes;
	
	@Column (name="\"STAFFNOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String staffNotes;
	
	@Column (name="\"PORTRAIT\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean portrait;
	
	@Column (name="\"RESID\"")
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String researcher;
	
	@Column (name="\"DATECREATED\"")
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;
	
	@Column (name="\"LASTUPDATE\"")
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date lastUpdate;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="child")
	@IndexedEmbedded(depth=1)
	private Set<Parent> parents;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="parent")
	@IndexedEmbedded(depth=1)
	//@OrderBy("child.last ASC")
	private Set<Parent> children;

	//Association alternative Names
	@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
	@OrderBy("altName ASC")
	@IndexedEmbedded(depth=1)
	private Set<AltName> altName;
	
	//Association Biographic
	@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
	@IndexedEmbedded(depth=1)
	private Set<BioRefLink> bioRefLink;

	//Association Linked Document
	@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
	@IndexedEmbedded(depth=1)
	private Set<EpLink> epLink;
	
	//Association titles and occupations
	@OneToMany(fetch=FetchType.LAZY, mappedBy="person")
	@OrderBy("preferredRole DESC, titleOccList ASC")
	@IndexedEmbedded
	private Set<PoLink> poLink;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="personId")
	@IndexedEmbedded
	private Set<PrcLink> prcLink;
	
	@OneToMany(mappedBy="senderPeople", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<Document> senderDocuments;

    @OneToMany(mappedBy="recipientPeople", fetch=FetchType.LAZY)
	@ContainedIn
    private Set<Document> recipientDocuments;

	@OneToMany(fetch=FetchType.LAZY, mappedBy="husband")
	@IndexedEmbedded(depth=1)
	private Set<Marriage> marriagesAsHusband;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="wife")
	@IndexedEmbedded(depth=1)
	private Set<Marriage> marriagesAsWife;

	/**
	 * Default Constructor 
	 */
	public People() {
		super();
	}

	/**
	 * 
	 * @param senderPeopleId
	 */
	public People(Integer senderPeopleId) {
		super();
		setPersonId(senderPeopleId);
	}

	/**
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}


	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}


	/**
	 * @return the mapNameLf
	 */
	public String getMapNameLf() {
		return mapNameLf;
	}


	/**
	 * @param mapNameLf the mapNameLf to set
	 */
	public void setMapNameLf(String mapNameLf) {
		this.mapNameLf = mapNameLf;
	}


	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}


	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}


	/**
	 * @return the activeStart
	 */
	public String getActiveStart() {
		return activeStart;
	}


	/**
	 * @param activeStart the activeStart to set
	 */
	public void setActiveStart(String activeStart) {
		this.activeStart = activeStart;
	}


	/**
	 * @return the activeEnd
	 */
	public String getActiveEnd() {
		return activeEnd;
	}

	/**
	 * @param activeEnd the activeEnd to set
	 */
	public void setActiveEnd(String activeEnd) {
		this.activeEnd = activeEnd;
	}

	/**
	 * @return the bornMonth
	 */
	public Month getBornMonth() {
		return bornMonth;
	}

	/**
	 * @param bMonth the bornMonth to set
	 */
	public void setBornMonth(Month bornMonth) {
		this.bornMonth = bornMonth;
	}

	/**
	 * @return the bornDay
	 */
	public Integer getBornDay() {
		return bornDay;
	}

	/**
	 * @param bDay the bornDay to set
	 */
	public void setBornDay(Integer bornDay) {
		this.bornDay = bornDay;
	}

	/**
	 * @return the bornYear
	 */
	public Integer getBornYear() {
		return bornYear;
	}

	/**
	 * @param bornYear the bornYear to set
	 */
	public void setBornYear(Integer bornYear) {
		this.bornYear = bornYear;
	}

	/**
	 * @param bornDate the bornDate to set
	 */
	public void setBornDate(Integer bornDate) {
		this.bornDate = bornDate;
	}

	/**
	 * @return the bornDate
	 */
	public Integer getBornDate() {
		return bornDate;
	}

	/**
	 * @return the bornPlace
	 */
	public Place getBornPlace() {
		return bornPlace;
	}


	/**
	 * @param bornPlace the bornPlace to set
	 */
	public void setBornPlace(Place bornPlace) {
		this.bornPlace = bornPlace;
	}


	/**
	 * @return the bPlace
	 */
	public String getbPlace() {
		return bPlace;
	}


	/**
	 * @param bPlace the bPlace to set
	 */
	public void setbPlace(String bPlace) {
		this.bPlace = bPlace;
	}


	/**
	 * @return the deathMonth
	 */
	public Month getDeathMonth() {
		return deathMonth;
	}


	/**
	 * @param deathMonthNum the deathMonthNum to set
	 */
	public void setDeathMonth(Month deathMonth) {
		this.deathMonth = deathMonth;
	}


	/**
	 * @return the deathDay
	 */
	public Integer getDeathDay() {
		return deathDay;
	}


	/**
	 * @param deathDay the deathDay to set
	 */
	public void setDeathDay(Integer deathDay) {
		this.deathDay = deathDay;
	}


	/**
	 * @return the deathYear
	 */
	public Integer getDeathYear() {
		return deathYear;
	}


	/**
	 * @param deathYear the deathYear to set
	 */
	public void setDeathYear(Integer deathYear) {
		this.deathYear = deathYear;
	}

	/**
	 * @param deathDate the deathDate to set
	 */
	public void setDeathDate(Integer deathDate) {
		this.deathDate = deathDate;
	}

	/**
	 * @return the deathDate
	 */
	public Integer getDeathDate() {
		return deathDate;
	}

	/**
	 * @param deathPlace the deathPlace to set
	 */
	public void setDeathPlace(Place deathPlace) {
		this.deathPlace = deathPlace;
	}

	/**
	 * @return the deathPlace
	 */
	public Place getDeathPlace() {
		return deathPlace;
	}


	/**
	 * @return the dPlace
	 */
	public String getdPlace() {
		return dPlace;
	}


	/**
	 * @param dPlace the dPlace to set
	 */
	public void setdPlace(String dPlace) {
		this.dPlace = dPlace;
	}


	/**
	 * @return the first
	 */
	public String getFirst() {
		return first;
	}


	/**
	 * @param first the first to set
	 */
	public void setFirst(String first) {
		this.first = first;
	}


	/**
	 * @return the sucNum
	 */
	public String getSucNum() {
		return sucNum;
	}


	/**
	 * @param sucNum the sucNum to set
	 */
	public void setSucNum(String sucNum) {
		this.sucNum = sucNum;
	}


	/**
	 * @return the middle
	 */
	public String getMiddle() {
		return middle;
	}


	/**
	 * @param middle the middle to set
	 */
	public void setMiddle(String middle) {
		this.middle = middle;
	}


	/**
	 * @return the midPrefix
	 */
	public String getMidPrefix() {
		return midPrefix;
	}


	/**
	 * @param midPrefix the midPrefix to set
	 */
	public void setMidPrefix(String midPrefix) {
		this.midPrefix = midPrefix;
	}


	/**
	 * @return the last
	 */
	public String getLast() {
		return last;
	}


	/**
	 * @param last the last to set
	 */
	public void setLast(String last) {
		this.last = last;
	}


	/**
	 * @return the lastPrefix
	 */
	public String getLastPrefix() {
		return lastPrefix;
	}


	/**
	 * @param lastPrefix the lastPrefix to set
	 */
	public void setLastPrefix(String lastPrefix) {
		this.lastPrefix = lastPrefix;
	}


	/**
	 * @return the postLast
	 */
	public String getPostLast() {
		return postLast;
	}


	/**
	 * @param postLast the postLast to set
	 */
	public void setPostLast(String postLast) {
		this.postLast = postLast;
	}


	/**
	 * @return the postLastPrefix
	 */
	public String getPostLastPrefix() {
		return postLastPrefix;
	}


	/**
	 * @param postLastPrefix the postLastPrefix to set
	 */
	public void setPostLastPrefix(String postLastPrefix) {
		this.postLastPrefix = postLastPrefix;
	}


	/**
	 * @return the bornApprox
	 */
	public Boolean getBornApprox() {
		return bornApprox;
	}


	/**
	 * @param bornApprox the bornApprox to set
	 */
	public void setBornApprox(Boolean bornApprox) {
		this.bornApprox = bornApprox;
	}


	/**
	 * @return the bornDateBc
	 */
	public Boolean getBornDateBc() {
		return bornDateBc;
	}


	/**
	 * @param bornDateBc the bornDateBc to set
	 */
	public void setBornDateBc(Boolean bornDateBc) {
		this.bornDateBc = bornDateBc;
	}


	/**
	 * @return the bornPlaceUnsure
	 */
	public Boolean getBornPlaceUnsure() {
		return bornPlaceUnsure;
	}


	/**
	 * @param bornPlaceUnsure the bornPlaceUnsure to set
	 */
	public void setBornPlaceUnsure(Boolean bornPlaceUnsure) {
		this.bornPlaceUnsure = bornPlaceUnsure;
	}


	/**
	 * @return the deathApprox
	 */
	public Boolean getDeathApprox() {
		return deathApprox;
	}


	/**
	 * @param dApprox the deathApprox to set
	 */
	public void setDeathApprox(Boolean deathApprox) {
		this.deathApprox = deathApprox;
	}


	/**
	 * @return the deathDateBc
	 */
	public Boolean getDeathDateBc() {
		return deathDateBc;
	}


	/**
	 * @param deathDateBc the deathDateBc to set
	 */
	public void setDeathDateBc(Boolean deathDateBc) {
		this.deathDateBc = deathDateBc;
	}


	/**
	 * @return the deathPlaceUnsure
	 */
	public Boolean getDeathPlaceUnsure() {
		return deathPlaceUnsure;
	}


	/**
	 * @param deathPlaceUnsure the deathPlaceUnsure to set
	 */
	public void setDeathPlaceUnsure(Boolean deathPlaceUnsure) {
		this.deathPlaceUnsure = deathPlaceUnsure;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the bioNotes
	 */
	public String getBioNotes() {
		return bioNotes;
	}


	/**
	 * @param bioNotes the bioNotes to set
	 */
	public void setBioNotes(String bioNotes) {
		this.bioNotes = bioNotes;
	}


	/**
	 * @return the staffNotes
	 */
	public String getStaffNotes() {
		return staffNotes;
	}


	/**
	 * @param staffNotes the staffNotes to set
	 */
	public void setStaffNotes(String staffNotes) {
		this.staffNotes = staffNotes;
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
	 * @return the researcher 
	 */
	public String getResearcher () {
		return researcher;
	}


	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
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
	 * @param altName the altName to set
	 */
	public void setAltName(Set<AltName> altName) {
		this.altName = altName;
	}

	/**
	 * @return the altName
	 */
	public Set<AltName> getAltName() {
		return altName;
	}

	/**
	 * @param bioRefLink the bioRefLink to set
	 */
	public void setBioRefLink(Set<BioRefLink> bioRefLink) {
		this.bioRefLink = bioRefLink;
	}

	/**
	 * @return the bioRefLink
	 */
	public Set<BioRefLink> getBioRefLink() {
		return bioRefLink;
	}

	/**
	 * @param epLink the epLink to set
	 */
	public void setEpLink(Set<EpLink> epLink) {
		this.epLink = epLink;
	}

	/**
	 * @return the epLink
	 */
	public Set<EpLink> getEpLink() {
		return epLink;
	}

	/**
	 * @param poLink the poLink to set
	 */
	public void setPoLink(Set<PoLink> poLink) {
		this.poLink = poLink;
	}

	/**
	 * @return the poLink
	 */
	public Set<PoLink> getPoLink() {
		return poLink;
	}

	/**
	 * @param prcLink the prcLink to set
	 */
	public void setPrcLink(Set<PrcLink> prcLink) {
		this.prcLink = prcLink;
	}

	/**
	 * @return the prcLink
	 */
	public Set<PrcLink> getPrcLink() {
		return prcLink;
	}

	/**
	 * 
	 * @param senderDocuments
	 */
	public void setSenderDocuments(Set<Document> senderDocuments) {
		this.senderDocuments = senderDocuments;
	}

	/**
	 * 
	 * @return
	 */
	public Set<Document> getSenderDocuments() {
		return senderDocuments;
	}

	/**
	 * @param recipientDocuments the recipientDocuments to set
	 */
	public void setRecipientDocuments(Set<Document> recipientDocuments) {
		this.recipientDocuments = recipientDocuments;
	}

	/**
	 * @return the recipientDocuments
	 */
	public Set<Document> getRecipientDocuments() {
		return recipientDocuments;
	}

	/**
	 * @param marriagesAsHusband the marriagesAsHusband to set
	 */
	public void setMarriagesAsHusband(Set<Marriage> marriagesAsHusband) {
		this.marriagesAsHusband = marriagesAsHusband;
	}

	/**
	 * @return the marriagesAsHusband
	 */
	public Set<Marriage> getMarriagesAsHusband() {
		return marriagesAsHusband;
	}

	/**
	 * @param marriagesAsWife the marriagesAsWife to set
	 */
	public void setMarriagesAsWife(Set<Marriage> marriagesAsWife) {
		this.marriagesAsWife = marriagesAsWife;
	}

	/**
	 * @return the marriagesAsWife
	 */
	public Set<Marriage> getMarriagesAsWife() {
		return marriagesAsWife;
	}

	/**
	 * @param parents the parents to set
	 */
	public void setParents(Set<Parent> parents) {
		this.parents = parents;
	}

	/**
	 * @return the parents
	 */
	public Set<Parent> getParents() {
		return parents;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(Set<Parent> children) {
		this.children = children;
	}

	/**
	 * @return the children
	 */
	public Set<Parent> getChildren() {
		return children;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		if (getLast() != null) {
			stringBuffer.append(getLast());
		}
		if ((stringBuffer.length() > 0) && ((getFirst() != null) || (getSucNum() != null))) {
			stringBuffer.append(", ");
		}
		if (getFirst() != null) {
			stringBuffer.append(getFirst());
		}

		if (getSucNum() != null) {
			if (stringBuffer.length() > 0) {
				stringBuffer.append(" ");
			}
			
			stringBuffer.append(getSucNum());
		}
		
		return stringBuffer.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
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
		People other = (People) obj;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		return true;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum Gender {
		NULL(null), M("M"), F("F"), X("X");
		
		private final String gender;

	    private Gender(String value) {
	        gender = value;
	    }

	    @Override
	    public String toString(){
	        return gender;
	    }
	}
}

