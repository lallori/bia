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
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.analysis.ISOLatin1AccentFilterFactory;
import org.apache.solr.analysis.MappingCharFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.CharFilterDef;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.BooleanBridge;
import org.medici.docsources.common.util.VolumeUtils;

/**
 * Volume entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="volumeAnalyzer",
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
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer volNum;
	
	@Column (name="\"VOLLETEXT\"", length=1)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String volLetExt;
	
	@Column (name="\"RESID\"")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String researcher;
	
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;
	
	@Column (name="\"VOLTOBEVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date volTobeVettedDate;
	
	@Column (name="\"VOLTOBEVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean volTobeVetted;
	
	@Column (name="\"VOLVETID\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String volVetId;
	
	@Column (name="\"VOLVETBEGINS\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date volVetBegins;
	
	@Column (name="\"VOLVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean volVetted;
	
	@Column (name="\"VOLVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date volVettedDate;
	
	@Column (name="\"STATBOX\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String statBox;
	
	@Column (name="\"STARTYEAR\"", length=5)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startYear;
	
	@Column (name="\"STARTMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String startMonth;
	
	@Column (name="\"STARTMONTHNUM\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startMonthNum;
	
	@Column (name="\"STARTDAY\"", length=3, columnDefinition="TINYINT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startDay;
	
	@Column (name="\"ENDYEAR\"", length=5)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endYear;
	
	@Column (name="\"ENDMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String endMonth;
	
	@Column (name="\"ENDMONTHNUM\"", length=10)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endMonthNum;
	
	@Column (name="\"ENDDAY\"", length=3, columnDefinition="TINYINT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endDay;
	
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
	
	@Column (name="\"FOLIOCOUNT\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String folioCount;
	
	@Column (name="\"BOUND\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean bound;
	
	@Column (name="\"FOLSNUMBRD\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Boolean folsNumbrd;
	
	@Column (name="\"OLDALPHAINDEX\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean oldAlphaIndex;
	
	// Column condition renamed in ccondition beacause inital word is reserved on Mysql 
	@Column (name="\"CCONDITION\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String ccondition;
	
	@Column (name="\"ITALIAN\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean italian;
	
	@Column (name="\"SPANISH\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean spanish;
	
	@Column (name="\"ENGLISH\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean english;
	
	@Column (name="\"LATIN\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean latin;
	
	@Column (name="\"GERMAN\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean german;
	
	@Column (name="\"FRENCH\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean french;
	
	@Column (name="\"OTHERLANG\"", length=50)
	@Field(index=Index.TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String otherLang;
	
	@Column (name="\"CIPHER\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
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

	/**
	 * Default constructor
	 * 
	 */
	public Volume() {
		super();
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
	 * @return the summaryId
	 */
	public Integer getSummaryId() {
		return summaryId;
	}
	
	/**
	 * @param summaryId the summaryId to set
	 */
	public void setSummaryId(Integer summaryId) {
		this.summaryId = summaryId;
	}
	
	/**
	 * @return the serieList
	 */
	public SerieList getSerieList() {
		return serieList;
	}
	
	/**
	 * @param serieList the serieList to set
	 */
	public void setSerieList(SerieList serieList) {
		this.serieList = serieList;
	}
	
	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}
	
	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}
	
	/**
	 * @return the volLeText
	 */
	public String getVolLetExt() {
		return volLetExt;
	}
	
	/**
	 * @param volLetExt the volLetExt to set
	 */
	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
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
	 * @return the researcher
	 */
	public String getResearcher() {
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
	 * @return the volTobeVettedDate
	 */
	public Date getVolTobeVettedDate() {
		return volTobeVettedDate;
	}
	
	/**
	 * @param volTobeVettedDate the volTobeVettedDate to set
	 */
	public void setVolTobeVettedDate(Date volTobeVettedDate) {
		this.volTobeVettedDate = volTobeVettedDate;
	}
	
	/**
	 * @return the volTobeVetted
	 */
	public Boolean getVolTobeVetted() {
		return volTobeVetted;
	}
	
	/**
	 * @param volTobeVetted the volTobeVetted to set
	 */
	public void setVolTobeVetted(Boolean volTobeVetted) {
		this.volTobeVetted = volTobeVetted;
	}
	
	/**
	 * @return the volVetId
	 */
	public String getVolVetId() {
		return volVetId;
	}
	
	/**
	 * @param volVetId the volVetId to set
	 */
	public void setVolVetId(String volVetId) {
		this.volVetId = volVetId;
	}
	
	/**
	 * @return the volVetBegins
	 */
	public Date getVolVetBegins() {
		return volVetBegins;
	}
	
	/**
	 * @param volVetBegins the volVetBegins to set
	 */
	public void setVolVetBegins(Date volVetBegins) {
		this.volVetBegins = volVetBegins;
	}
	
	/**
	 * @return the volVetted
	 */
	public Boolean getVolVetted() {
		return volVetted;
	}
	
	/**
	 * @param volVetted the volVetted to set
	 */
	public void setVolVetted(Boolean volVetted) {
		this.volVetted = volVetted;
	}
	
	/**
	 * @return the volVettedDate
	 */
	public Date getVolVettedDate() {
		return volVettedDate;
	}
	
	/**
	 * @param volVettedDate the volVettedDate to set
	 */
	public void setVolVettedDate(Date volVettedDate) {
		this.volVettedDate = volVettedDate;
	}
	
	/**
	 * @return the statBox
	 */
	public String getStatBox() {
		return statBox;
	}
	
	/**
	 * @param statBox the statBox to set
	 */
	public void setStatBox(String statBox) {
		this.statBox = statBox;
	}
	
	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}
	
	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}
	
	/**
	 * @return the startMonth
	 */
	public String getStartMonth() {
		return startMonth;
	}
	
	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	
	/**
	 * @return the startMonthNum
	 */
	public Integer getStartMonthNum() {
		return startMonthNum;
	}
	
	/**
	 * @param startMonthNum the startMonthNum to set
	 */
	public void setStartMonthNum(Integer startMonthNum) {
		this.startMonthNum = startMonthNum;
	}
	
	/**
	 * This method return a string rappresentation of start date. 
	 * It's a concatenation of three fields : startYear + startMonth + startDay.
	 * If one field is null or empty, it's not concatenated into return value.
	 * It's a transient property (not stored on database ndr).
	 *  
	 * @return String rappresentation of volume identifiers.
	 */
	@Transient
    public String getStartDate() {
		StringBuffer returnValue = new StringBuffer("");
		
		if (getStartYear() != null) {
			returnValue.append(getStartYear());
		}
		if (StringUtils.isNotEmpty(getStartMonth())) {
			if (returnValue.length() > 0 ) {
				returnValue.append(" ");
			}
			returnValue.append(getStartMonth());
		}
		if (getStartDay() != null) {
			if (returnValue.length() > 0 ) {
				returnValue.append(" ");
			}
			returnValue.append(getStartDay());
		}
		
		return returnValue.toString();
    }

	/**
	 * @return the startDay
	 */
	public Integer getStartDay() {
		return startDay;
	}
	
	/**
	 * @param startDay the startDay to set
	 */
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}
	
	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return endYear;
	}
	
	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}
	
	/**
	 * @return the endMonth
	 */
	public String getEndMonth() {
		return endMonth;
	}
	
	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}
	
	/**
	 * @return the endMonthNum
	 */
	public Integer getEndMonthNum() {
		return endMonthNum;
	}
	
	/**
	 * @param endMonthNum the endMonthNum to set
	 */
	public void setEndMonthNum(Integer endMonthNum) {
		this.endMonthNum = endMonthNum;
	}
	
	/**
	 * @return the endDay
	 */
	public Integer getEndDay() {
		return endDay;
	}
	
	/**
	 * @param endDay the endDay to set
	 */
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}
	
	/**
	 * This method return a string rappresentation of end date. 
	 * It's a concatenation of three fields : endYear + endMonth + endDay.
	 * If one field is null or empty, it's not concatenated into return value.
	 * It's a transient property (not stored on database ndr).
	 *  
	 * @return String rappresentation of volume identifiers.
	 */
	@Transient
    public String getEndDate() {
		StringBuffer returnValue = new StringBuffer("");
		
		if (getEndYear() != null) {
			returnValue.append(getEndYear());
		}
		if (StringUtils.isNotEmpty(getEndMonth())) {
			if (returnValue.length() > 0 ) {
				returnValue.append(" ");
			}
			returnValue.append(getEndMonth());
		}
		if (getEndDay() != null) {
			if (returnValue.length() > 0 ) {
				returnValue.append(" ");
			}
			returnValue.append(getEndDay());
		}
		
		return returnValue.toString();
    }

	/**
	 * @return the dateNotes
	 */
	public String getDateNotes() {
		return dateNotes;
	}
	
	/**
	 * @param dateNotes the dateNotes to set
	 */
	public void setDateNotes(String dateNotes) {
		this.dateNotes = dateNotes;
	}
	
	/**
	 * @return the senders
	 */
	public String getSenders() {
		return senders;
	}
	
	/**
	 * @param senders the senders to set
	 */
	public void setSenders(String senders) {
		this.senders = senders;
	}
	
	/**
	 * @return the recips
	 */
	public String getRecips() {
		return recips;
	}
	
	/**
	 * @param recips the recips to set
	 */
	public void setRecips(String recips) {
		this.recips = recips;
	}
	
	/**
	 * @return the ccontext
	 */
	public String getCcontext() {
		return ccontext;
	}
	
	/**
	 * @param ccontext the ccontext to set
	 */
	public void setCcontext(String ccontext) {
		this.ccontext = ccontext;
	}
	
	/**
	 * @return the folioCount
	 */
	public String getFolioCount() {
		return folioCount;
	}
	/**
	 * @param folioCount the folioCount to set
	 */
	public void setFolioCount(String folioCount) {
		this.folioCount = folioCount;
	}
	/**
	 * @return the bound
	 */
	public Boolean getBound() {
		return bound;
	}
	/**
	 * @param bound the bound to set
	 */
	public void setBound(Boolean bound) {
		this.bound = bound;
	}
	
	/**
	 * @return the folsNumbrd
	 */
	public Boolean getFolsNumbrd() {
		return folsNumbrd;
	}
	
	/**
	 * @param folsNumbrd the folsNumbrd to set
	 */
	public void setFolsNumbrd(Boolean folsNumbrd) {
		this.folsNumbrd = folsNumbrd;
	}
	
	/**
	 * @return the oldAlphaIndex
	 */
	public Boolean getOldAlphaIndex() {
		return oldAlphaIndex;
	}
	
	/**
	 * @param oldAlphaIndex the oldAlphaIndex to set
	 */
	public void setOldAlphaIndex(Boolean oldAlphaIndex) {
		this.oldAlphaIndex = oldAlphaIndex;
	}
	
	/**
	 * @return the condition
	 */
	public String getCcondition() {
		return ccondition;
	}
	
	/**
	 * @param condition the condition to set
	 */
	public void setCcondition(String ccondition) {
		this.ccondition = ccondition;
	}
	
	/**
	 * @return the italian
	 */
	public Boolean getItalian() {
		return italian;
	}
	
	/**
	 * @param italian the italian to set
	 */
	public void setItalian(Boolean italian) {
		this.italian = italian;
	}
	
	/**
	 * @return the spanish
	 */
	public Boolean getSpanish() {
		return spanish;
	}
	
	/**
	 * @param spanish the spanish to set
	 */
	public void setSpanish(Boolean spanish) {
		this.spanish = spanish;
	}
	
	/**
	 * @return the english
	 */
	public Boolean getEnglish() {
		return english;
	}
	
	/**
	 * @param english the english to set
	 */
	public void setEnglish(Boolean english) {
		this.english = english;
	}
	
	/**
	 * @return the latin
	 */
	public Boolean getLatin() {
		return latin;
	}
	
	/**
	 * @param latin the latin to set
	 */
	public void setLatin(Boolean latin) {
		this.latin = latin;
	}
	
	/**
	 * @return the german
	 */
	public Boolean getGerman() {
		return german;
	}
	
	/**
	 * @param german the german to set
	 */
	public void setGerman(Boolean german) {
		this.german = german;
	}
	
	/**
	 * @return the french
	 */
	public Boolean getFrench() {
		return french;
	}
	
	/**
	 * @param french the french to set
	 */
	public void setFrench(Boolean french) {
		this.french = french;
	}
	/**
	 * @return the otherLang
	 */
	public String getOtherLang() {
		return otherLang;
	}
	
	/**
	 * @param otherLang the otherLang to set
	 */
	public void setOtherLang(String otherLang) {
		this.otherLang = otherLang;
	}
	
	/**
	 * @return the cipher
	 */
	public Boolean getCipher() {
		return cipher;
	}
	
	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(Boolean cipher) {
		this.cipher = cipher;
	}
	
	/**
	 * @return the cipherNotes
	 */
	public String getCipherNotes() {
		return cipherNotes;
	}
	
	/**
	 * @param cipherNotes the cipherNotes to set
	 */
	public void setCipherNotes(String cipherNotes) {
		this.cipherNotes = cipherNotes;
	}
	
	/**
	 * @return the orgNotes
	 */
	public String getOrgNotes() {
		return orgNotes;
	}
	
	/**
	 * @param orgNotes the orgNotes to set
	 */
	public void setOrgNotes(String orgNotes) {
		this.orgNotes = orgNotes;
	}
	
	/**
	 * @return the staffMemo
	 */
	public String getStaffMemo() {
		return staffMemo;
	}
	
	/**
	 * @param staffMemo the staffMemo to set
	 */
	public void setStaffMemo(String staffMemo) {
		this.staffMemo = staffMemo;
	}


	/**
	 * @param printedMaterial the printedMaterial to set
	 */
	public void setPrintedMaterial(Boolean printedMaterial) {
		this.printedMaterial = printedMaterial;
	}

	/**
	 * @return the printedMaterial
	 */
	public Boolean getPrintedMaterial() {
		return printedMaterial;
	}

	/**
	 * @param printedDrawings the printedDrawings to set
	 */
	public void setPrintedDrawings(Boolean printedDrawings) {
		this.printedDrawings = printedDrawings;
	}

	/**
	 * @return the printedDrawings
	 */
	public Boolean getPrintedDrawings() {
		return printedDrawings;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((summaryId == null) ? 0 : summaryId.hashCode());
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
		Volume other = (Volume) obj;
		if (summaryId == null) {
			if (other.summaryId != null)
				return false;
		} else if (!summaryId.equals(other.summaryId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		if (volNum != null) {
			stringBuffer.append(volNum);
			if (volLetExt != null) {
				stringBuffer.append(volLetExt);
			}
		}

		return stringBuffer.toString();
	}
}