/*
 * Volume.java
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.StandardFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.apache.solr.analysis.StopFilterFactory;
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
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.BooleanBridge;
import org.medici.bia.common.util.VolumeUtils;

/**
 * Volume entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDefs({
	@AnalyzerDef(name="volumeAnalyzer",
		charFilters = {
			@CharFilterDef(factory = MappingCharFilterFactory.class, params = {
				@Parameter(name = "mapping", value = "org/medici/bia/mapping-chars.properties")
			})
		},
		tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
		filters = {
			@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
			@TokenFilterDef(factory = StopFilterFactory.class, params = {
                @Parameter(name="words",      value="org/medici/bia/stopWords.properties" ),
                @Parameter(name="ignoreCase", value="true")
            })
	}),
	@AnalyzerDef(name = "volumeNGram3Analyzer",
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
@Table ( name = "\"tblVolumes\"" ) 
public class Volume implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3127626344157278734L;
	@Id
	@DocumentId
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"SUMMARYID\"", length=10, nullable=false)
	private Integer summaryId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"SERIESREFNUM\"")
	@IndexedEmbedded
	private SerieList serieList;
	
	@Column (name="\"VOLNUM\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="volNum_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="volNum"),
		@NumericField(forField="volNum_Sort")
	})
	private Integer volNum;
	
	@Column (name="\"VOLLETEXT\"", length=1)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="volLetExt_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String volLetExt;
	
	@Column (name="\"RESID\"")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String researcher;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"createdBy\"", nullable=true)
	private User createdBy;

	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;
	
	@Column (name="\"LASTUPDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date lastUpdate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"lastUpdateBy\"", nullable=true)
	private User lastUpdateBy;
	
	@Column (name="\"VOLTOBEVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date volTobeVettedDate;
	
	@Column (name="\"VOLTOBEVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean volTobeVetted;
	
	@Column (name="\"VOLVETID\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String volVetId;
	
	@Column (name="\"VOLVETBEGINS\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date volVetBegins;
	
	@Column (name="\"VOLVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean volVetted;
	
	@Column (name="\"VOLVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date volVettedDate;
	
	@Column (name="\"STATBOX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String statBox;
	
	@Column (name="\"STARTYEAR\"", length=5)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="startYear_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="startYear"),
		@NumericField(forField="startYear_Sort")
	})
	private Integer startYear;
	
	@Column (name="\"STARTMONTH\"", length=50)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="startMonth_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String startMonth;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"STARTMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month startMonthNum;
	
	@Column (name="\"STARTDAY\"", length=3, columnDefinition="TINYINT")
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="startDay_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="startDay"),
		@NumericField(forField="startDay_Sort")
	})
	private Integer startDay;

	@Column (name="\"STARTDATE\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="startDate_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="startDate"),
		@NumericField(forField="startDate_Sort")
	})
	private Integer startDate;

	@Column (name="\"ENDYEAR\"", length=5)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="endYear_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="endYear"),
		@NumericField(forField="endYear_Sort")
	})
	private Integer endYear;
	
	@Column (name="\"ENDMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String endMonth;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"ENDMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month endMonthNum;
	
	@Column (name="\"ENDDAY\"", length=3, columnDefinition="TINYINT")
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="endDay_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="endDay"),
		@NumericField(forField="endDay_Sort")
	})
	private Integer endDay;
	
	@Column (name="\"ENDDATE\"", length=10)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="endDate_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	@NumericFields({
		@NumericField(forField="endDate"),
		@NumericField(forField="endDate_Sort")
	})
	private Integer endDate;

	@Column (name="\"DATENOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String dateNotes;
	
	@Column (name="\"SENDERS\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String senders;
	
	@Column (name="\"RECIPS\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String recips;
	
	@Column (name="\"CCONTEXT\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String ccontext;
	
	@Column (name="\"INVSOMDESC\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String inventarioSommarioDescription;

	@Column (name="\"FOLIOCOUNT\"", length=50)
	@Fields({
		@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN),
		@Field(name="folioCount_Sort", index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	})
	private String folioCount;
	
	@Column (name="\"BOUND\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean bound;
	
	@Column (name="\"FOLSNUMBRD\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Boolean folsNumbrd;
	
	@Column (name="\"OLDALPHAINDEX\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean oldAlphaIndex;
	
	// Column condition renamed in ccondition beacause inital word is reserved on Mysql 
	@Column (name="\"CCONDITION\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String ccondition;
	
	@Column (name="\"ITALIAN\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean italian;
	
	@Column (name="\"SPANISH\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean spanish;
	
	@Column (name="\"ENGLISH\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean english;
	
	@Column (name="\"LATIN\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean latin;
	
	@Column (name="\"GERMAN\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean german;
	
	@Column (name="\"FRENCH\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean french;
	
	@Column (name="\"OTHERLANG\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String otherLang;
	
	@Column (name="\"CIPHER\"", length=1, columnDefinition="TINYINT default '0'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean cipher;
	
	@Column (name="\"CIPHERNOTES\"", length=255)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String cipherNotes;
	
	@Column (name="\"ORGNOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String orgNotes;
	
	@Column (name="\"STAFFMEMO\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String staffMemo;
	
	@Column (name="\"PRINTEDMATERIAL\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean printedMaterial;
	
	@Column (name="\"PRINTEDDRAWINGS\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean printedDrawings;

	@Column (name="\"LOGICALDELETE\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean logicalDelete;
	
	@Column (name="\"DIGITIZED\"", length=1, columnDefinition="tinyint default 0", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean digitized;
	
	@OneToMany(mappedBy="volume", fetch=FetchType.LAZY)
    @ContainedIn
    private Set<Document> documents;

	/**
	 * Default constructor
	 * 
	 */
	public Volume() {
		super();
	}

	/**
	 * Constructor with an integer parameter rapresenting unique identifier.
	 * 
	 * @param summaryId Volume unique identifier
	 */
	public Volume(Integer summaryId) {
		super();
		setSummaryId(summaryId);
	}

	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 */
	public Volume(Integer volNum, String volLetExt) {
		super();
		setVolNum(volNum);
		setVolLetExt(volLetExt);
	}
	
	/**
	 * Constructor with a string parameter rapresenting volNum and VolLetExt
	 * 
	 * @param volume VolNum + VolLextExt
	 */
	public Volume(String volume) {
		super();
		setVolNum(VolumeUtils.extractVolNum(volume));
		setVolLetExt(VolumeUtils.extractVolLetExt(volume));
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
		
		if (! (obj instanceof Volume)) {
			return false;
		}

		Volume other = (Volume) obj;
		if (getSummaryId() == null) {
			if (other.getSummaryId() != null){
				return false;
			}
		} else if (!getSummaryId().equals(other.getSummaryId())){
			return false;
		}
		
		return true;
	}
	
	/**
	 * @return the bound
	 */
	public Boolean getBound() {
		return bound;
	}
	
	/**
	 * @return the condition
	 */
	public String getCcondition() {
		return ccondition;
	}
	
	/**
	 * @return the ccontext
	 */
	public String getCcontext() {
		return ccontext;
	}
	
	/**
	 * @return the cipher
	 */
	public Boolean getCipher() {
		return cipher;
	}
	
	/**
	 * @return the cipherNotes
	 */
	public String getCipherNotes() {
		return cipherNotes;
	}
	
	/**
	 * @return the createdBy
	 */
	public User getCreatedBy() {
		return createdBy;
	}
	
	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}
	
	/**
	 * @return the dateNotes
	 */
	public String getDateNotes() {
		return dateNotes;
	}

	/**
	 * @return the digitized
	 */
	public Boolean getDigitized() {
		return digitized;
	}
	
	/**
	 * @return the documents
	 */
	public Set<Document> getDocuments() {
		return documents;
	}

	/**
	 * @return the endDate
	 */
	public Integer getEndDate() {
		return endDate;
	}

	/**
	 * @return the endDay
	 */
	public Integer getEndDay() {
		return endDay;
	}

	/**
	 * @return the endMonth
	 */
	public String getEndMonth() {
		return endMonth;
	}

	/**
	 * @return the endMonthNum
	 */
	public Month getEndMonthNum() {
		return endMonthNum;
	}

	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return endYear;
	}
	
	/**
	 * @return the english
	 */
	public Boolean getEnglish() {
		return english;
	}
	
	/**
	 * @return the folioCount
	 */
	public String getFolioCount() {
		return folioCount;
	}
	
	/**
	 * @return the folsNumbrd
	 */
	public Boolean getFolsNumbrd() {
		return folsNumbrd;
	}
	
	/**
	 * @return the french
	 */
	public Boolean getFrench() {
		return french;
	}
	
	/**
	 * @return the german
	 */
	public Boolean getGerman() {
		return german;
	}
	
	public String getInventarioSommarioDescription() {
		return inventarioSommarioDescription;
	}
	
	/**
	 * @return the italian
	 */
	public Boolean getItalian() {
		return italian;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}
	
	/**
	 * @return the lastUpdateBy
	 */
	public User getLastUpdateBy() {
		return lastUpdateBy;
	}
	
	/**
	 * @return the latin
	 */
	public Boolean getLatin() {
		return latin;
	}
	
	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}
	
	/**
	 * This method return volume MDP information. It's a concatenation of volume
	 * number and volume letter extension. It's a transient property 
	 * (not stored on database ndr).
	 *  
	 * @return String rappresentation of volume identifiers.
	 */
	@Transient
    public String getMDP() {
		String returnValue = "";
		
		if (getVolNum() != null) {
			returnValue += getVolNum();
		}
		if (StringUtils.isNotEmpty(getVolLetExt())) {
			returnValue += getVolLetExt();
		}
		
		return returnValue;
    }
	
	/**
	 * @return the oldAlphaIndex
	 */
	public Boolean getOldAlphaIndex() {
		return oldAlphaIndex;
	}
	
	/**
	 * @return the orgNotes
	 */
	public String getOrgNotes() {
		return orgNotes;
	}
	
	/**
	 * @return the otherLang
	 */
	public String getOtherLang() {
		return otherLang;
	}
	
	/**
	 * @return the printedDrawings
	 */
	public Boolean getPrintedDrawings() {
		return printedDrawings;
	}
	
	/**
	 * @return the printedMaterial
	 */
	public Boolean getPrintedMaterial() {
		return printedMaterial;
	}
	
	/**
	 * @return the recips
	 */
	public String getRecips() {
		return recips;
	}
	
	/**
	 * @return the researcher
	 */
	public String getResearcher() {
		return researcher;
	}
	
	/**
	 * @return the senders
	 */
	public String getSenders() {
		return senders;
	}
	
	/**
	 * @return the serieList
	 */
	public SerieList getSerieList() {
		return serieList;
	}
	
	/**
	 * @return the spanish
	 */
	public Boolean getSpanish() {
		return spanish;
	}
	
	/**
	 * @return the staffMemo
	 */
	public String getStaffMemo() {
		return staffMemo;
	}
	
	/**
	 * @return the startDate
	 */
	public Integer getStartDate() {
		return startDate;
	}

	/**
	 * @return the startDay
	 */
	public Integer getStartDay() {
		return startDay;
	}

	/**
	 * @return the startMonth
	 */
	public String getStartMonth() {
		return startMonth;
	}
	
	/**
	 * @return the startMonthNum
	 */
	public Month getStartMonthNum() {
		return startMonthNum;
	}
	
	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}
	
	/**
	 * @return the statBox
	 */
	public String getStatBox() {
		return statBox;
	}
	
	/**
	 * @return the summaryId
	 */
	public Integer getSummaryId() {
		return summaryId;
	}
	
	/**
	 * @return the volLeText
	 */
	public String getVolLetExt() {
		return volLetExt;
	}
	
	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}
	
	/**
	 * @return the volTobeVetted
	 */
	public Boolean getVolTobeVetted() {
		return volTobeVetted;
	}
	
	/**
	 * @return the volTobeVettedDate
	 */
	public Date getVolTobeVettedDate() {
		return volTobeVettedDate;
	}

	/**
	 * @return the volVetBegins
	 */
	public Date getVolVetBegins() {
		return volVetBegins;
	}

	/**
	 * @return the volVetId
	 */
	public String getVolVetId() {
		return volVetId;
	}
	
	/**
	 * @return the volVetted
	 */
	public Boolean getVolVetted() {
		return volVetted;
	}
	
	/**
	 * @return the volVettedDate
	 */
	public Date getVolVettedDate() {
		return volVettedDate;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getSummaryId() == null) ? 0 : getSummaryId().hashCode());
		return result;
	}
	
	/**
	 * @param bound the bound to set
	 */
	public void setBound(Boolean bound) {
		this.bound = bound;
	}
	
	/**
	 * @param condition the condition to set
	 */
	public void setCcondition(String ccondition) {
		this.ccondition = ccondition;
	}
	
	/**
	 * @param ccontext the ccontext to set
	 */
	public void setCcontext(String ccontext) {
		this.ccontext = ccontext;
	}
	
	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(Boolean cipher) {
		this.cipher = cipher;
	}
	
	/**
	 * @param cipherNotes the cipherNotes to set
	 */
	public void setCipherNotes(String cipherNotes) {
		this.cipherNotes = cipherNotes;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	/**
	 * @param dateNotes the dateNotes to set
	 */
	public void setDateNotes(String dateNotes) {
		this.dateNotes = dateNotes;
	}

	/**
	 * @param digitized the digitized to set
	 */
	public void setDigitized(Boolean digitized) {
		this.digitized = digitized;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(Set<Document> documents) {
		this.documents = documents;
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * @param endDay the endDay to set
	 */
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}
	
	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	
	/**
	 * @param endMonthNum the endMonthNum to set
	 */
	public void setEndMonthNum(Month endMonthNum) {
		this.endMonthNum = endMonthNum;
	}
	
	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	
	/**
	 * @param english the english to set
	 */
	public void setEnglish(Boolean english) {
		this.english = english;
	}
	
	/**
	 * @param folioCount the folioCount to set
	 */
	public void setFolioCount(String folioCount) {
		this.folioCount = folioCount;
	}
	
	/**
	 * @param folsNumbrd the folsNumbrd to set
	 */
	public void setFolsNumbrd(Boolean folsNumbrd) {
		this.folsNumbrd = folsNumbrd;
	}
	
	/**
	 * @param french the french to set
	 */
	public void setFrench(Boolean french) {
		this.french = french;
	}
	
	/**
	 * @param german the german to set
	 */
	public void setGerman(Boolean german) {
		this.german = german;
	}
	
	public void setInventarioSommarioDescription(String inventarioSommarioDescription) {
		this.inventarioSommarioDescription = inventarioSommarioDescription;
	}
	
	/**
	 * @param italian the italian to set
	 */
	public void setItalian(Boolean italian) {
		this.italian = italian;
	}
	
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	/**
	 * @param lastUpdateBy the lastUpdateBy to set
	 */
	public void setLastUpdateBy(User lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
	/**
	 * @param latin the latin to set
	 */
	public void setLatin(Boolean latin) {
		this.latin = latin;
	}
	
	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}
	
	/**
	 * @param oldAlphaIndex the oldAlphaIndex to set
	 */
	public void setOldAlphaIndex(Boolean oldAlphaIndex) {
		this.oldAlphaIndex = oldAlphaIndex;
	}
	
	/**
	 * @param orgNotes the orgNotes to set
	 */
	public void setOrgNotes(String orgNotes) {
		this.orgNotes = orgNotes;
	}
	
	/**
	 * @param otherLang the otherLang to set
	 */
	public void setOtherLang(String otherLang) {
		this.otherLang = otherLang;
	}
	
	/**
	 * @param printedDrawings the printedDrawings to set
	 */
	public void setPrintedDrawings(Boolean printedDrawings) {
		this.printedDrawings = printedDrawings;
	}
	
	/**
	 * @param printedMaterial the printedMaterial to set
	 */
	public void setPrintedMaterial(Boolean printedMaterial) {
		this.printedMaterial = printedMaterial;
	}
	
	/**
	 * @param recips the recips to set
	 */
	public void setRecips(String recips) {
		this.recips = recips;
	}
	
	/**
	 * @param researcher the researcher to set
	 */
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}
	
	/**
	 * @param senders the senders to set
	 */
	public void setSenders(String senders) {
		this.senders = senders;
	}
	
	/**
	 * @param serieList the serieList to set
	 */
	public void setSerieList(SerieList serieList) {
		this.serieList = serieList;
	}
	
	/**
	 * @param spanish the spanish to set
	 */
	public void setSpanish(Boolean spanish) {
		this.spanish = spanish;
	}
	
	/**
	 * @param staffMemo the staffMemo to set
	 */
	public void setStaffMemo(String staffMemo) {
		this.staffMemo = staffMemo;
	}
	
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Integer startDate) {
		this.startDate = startDate;
	}

	/**
	 * @param startDay the startDay to set
	 */
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	/**
	 * @param startMonthNum the startMonthNum to set
	 */
	public void setStartMonthNum(Month startMonthNum) {
		this.startMonthNum = startMonthNum;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}


	/**
	 * @param statBox the statBox to set
	 */
	public void setStatBox(String statBox) {
		this.statBox = statBox;
	}

	/**
	 * @param summaryId the summaryId to set
	 */
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}

	/**
	 * @param volLetExt the volLetExt to set
	 */
	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
	}

	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}

	/**
	 * @param volTobeVetted the volTobeVetted to set
	 */
	public void setVolTobeVetted(Boolean volTobeVetted) {
		this.volTobeVetted = volTobeVetted;
	}

	/**
	 * @param volTobeVettedDate the volTobeVettedDate to set
	 */
	public void setVolTobeVettedDate(Date volTobeVettedDate) {
		this.volTobeVettedDate = volTobeVettedDate;
	}

	/**
	 * @param volVetBegins the volVetBegins to set
	 */
	public void setVolVetBegins(Date volVetBegins) {
		this.volVetBegins = volVetBegins;
	}

	/**
	 * @param volVetId the volVetId to set
	 */
	public void setVolVetId(String volVetId) {
		this.volVetId = volVetId;
	}

	/**
	 * @param volVetted the volVetted to set
	 */
	public void setVolVetted(Boolean volVetted) {
		this.volVetted = volVetted;
	}
	
	/**
	 * @param volVettedDate the volVettedDate to set
	 */
	public void setVolVettedDate(Date volVettedDate) {
		this.volVettedDate = volVettedDate;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (volNum != null) {
			stringBuilder.append(volNum);
			if (volLetExt != null) {
				stringBuilder.append(volLetExt);
			}
		}

		return stringBuilder.toString();
	}
}