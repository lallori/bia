/*
 * PoLink.java
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
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * PoLink entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Indexed
@AnalyzerDef(name="poLinkAnalyzer",
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
@Table ( name = "\"tblPOLink\"" )
public class PoLink implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8677892193617889369L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"PRLINKID\"", length=10, nullable=false)
	@DocumentId
	private Integer prfLinkId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"PERSONID\"")
	@ContainedIn
	private People personId;
	
	@ManyToOne
	@JoinColumn(name="\"TITLEOCCID\"")
	@IndexedEmbedded
	private TitleOccsList titleOccId;
	
	@Column (name="\"STARTYEAR\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startYear;
	
	@Column (name="\"STARTMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String startMonth;
	
	@Column (name="\"STARTDAY\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startDay;
	
	@Column (name="\"ENDYEAR\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endYear;
	
	@Column (name="\"ENDMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String endMonth; 
	
	@Column (name="\"ENDDAY\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endDay;
	
	@Column (name="\"PRTAG\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer prTag;
	
	@Column (name="\"PRLINKNOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String prLinkNotes;
	
	@Column (name="\"STARTAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean startApprox;
	
	@Column (name="\"STARTUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean StartUns;
	
	@Column (name="\"ENDAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean endApprox;
	
	@Column (name="\"ENDUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.YES, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean endUns;
	
	@Column (name="\"STARTMONTHNUM\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startMonthNum;
	
	@Column (name="\"ENDMONTHNUM\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endMonthNum;
	
	@Column (name="\"DATECREATED\"")
	@Temporal (TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;
	
	/**
	 * @return the prfLinkId
	 */
	public Integer getPrfLinkId() {
		return prfLinkId;
	}
	
	/**
	 * @param prfLinkId the prfLinkId to set
	 */
	public void setPrfLinkId(Integer prfLinkId) {
		this.prfLinkId = prfLinkId;
	}
	
	/**
	 * @return the personId
	 */
	public People getPersonId() {
		return personId;
	}
	
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(People personId) {
		this.personId = personId;
	}
	
	/**
	 * @return the titleOccId
	 */
	public TitleOccsList getTitleOccId() {
		return titleOccId;
	}
	/**
	 * @param titleOccId the titleOccId to set
	 */
	public void setTitleOccId(TitleOccsList titleOccId) {
		this.titleOccId = titleOccId;
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
	 * @return the prTag
	 */
	public Integer getPrTag() {
		return prTag;
	}
	/**
	 * @param prTag the prTag to set
	 */
	public void setPrTag(Integer prTag) {
		this.prTag = prTag;
	}
	/**
	 * @return the prLinkNotes
	 */
	public String getPrLinkNotes() {
		return prLinkNotes;
	}
	/**
	 * @param prLinkNotes the prLinkNotes to set
	 */
	public void setPrLinkNotes(String prLinkNotes) {
		this.prLinkNotes = prLinkNotes;
	}
	/**
	 * @return the startApprox
	 */
	public Boolean getStartApprox() {
		return startApprox;
	}
	/**
	 * @param startApprox the startApprox to set
	 */
	public void setStartApprox(Boolean startApprox) {
		this.startApprox = startApprox;
	}
	/**
	 * @return the startUns
	 */
	public Boolean getStartUns() {
		return StartUns;
	}
	/**
	 * @param startUns the startUns to set
	 */
	public void setStartUns(Boolean startUns) {
		StartUns = startUns;
	}
	/**
	 * @return the endApprox
	 */
	public Boolean getEndApprox() {
		return endApprox;
	}
	/**
	 * @param endApprox the endApprox to set
	 */
	public void setEndApprox(Boolean endApprox) {
		this.endApprox = endApprox;
	}
	/**
	 * @return the endUns
	 */
	public Boolean getEndUns() {
		return endUns;
	}
	/**
	 * @param endUns the endUns to set
	 */
	public void setEndUns(Boolean endUns) {
		this.endUns = endUns;
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
