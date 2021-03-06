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
package org.medici.bia.domain;

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

import org.apache.commons.lang.ObjectUtils;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.bridge.builtin.BooleanBridge;

/**
 * PoLink entity. This entity links a Person with his correspondents TitleOccList
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblPoLink\"" )
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
	private People person;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="\"TITLEOCCID\"")
	@IndexedEmbedded
	private TitleOccsList titleOccList;
	
	@Column (name="\"STARTYEAR\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startYear;
	
	@Column (name="\"STARTMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String startMonth;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"STARTMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month startMonthNum;
	
	@Column (name="\"STARTDAY\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer startDay;
	
	@Column (name="\"ENDYEAR\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endYear;
	
	@Column (name="\"ENDMONTH\"", length=50)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String endMonth; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="\"ENDMONTHNUM\"", nullable=true)
	@IndexedEmbedded
	private Month endMonthNum;

	@Column (name="\"ENDDAY\"", length=10)
	@Field(index=Index.TOKENIZED, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private Integer endDay;
	
	@Column (name="\"PRTAG\"", length=1, columnDefinition="tinyint", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean preferredRole;
	
	@Column (name="\"PRLINKNOTES\"", columnDefinition="LONGTEXT")
	@Field(index=Index.TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	private String prLinkNotes;
	
	@Column (name="\"STARTAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean startApprox;
	
	@Column (name="\"STARTUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean startUns;
	
	@Column (name="\"ENDAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean endApprox;
	
	@Column (name="\"ENDUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@FieldBridge(impl=BooleanBridge.class)
	private Boolean endUns;
	
	@Column (name="\"DATECREATED\"")
	@Temporal (TemporalType.TIMESTAMP)
	@Field(index=Index.UN_TOKENIZED, store=Store.NO, indexNullAs=Field.DEFAULT_NULL_TOKEN)
	@DateBridge(resolution=Resolution.DAY) 
	private Date dateCreated;
	
	/**
	 * Default constructor.
	 */
	public PoLink() {
		super();
	}
	
	/**
	 * 
	 * @param prfLinkId
	 */
	public PoLink(Integer prfLinkId) {
		super();
		
		setPrfLinkId(prfLinkId);
	}

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
	 * @return the person
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
	 * @return the titleOccList
	 */
	public TitleOccsList getTitleOccList() {
		return titleOccList;
	}
	
	/**
	 * @param titleOccList the titleOccList to set
	 */
	public void setTitleOcc(TitleOccsList titleOccList) {
		this.titleOccList = titleOccList;
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
	 * This method returns start date. It's a concatenation of three fields,
	 * startDay, startMonth, and startYear. It's a transient property 
	 * (not stored on database ndr).
	 *  
	 * @return String rappresentation of volume identifiers.
	 */
	@Transient
	public String getStartDate() {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (startDay != null) {
			stringBuilder.append(startDay);
		}
		
		if (!ObjectUtils.toString(startMonth).equals("")) {
			if (stringBuilder.length() > 0 ) {
				stringBuilder.append(' ');
			}
			stringBuilder.append(startMonth);
		}

		if (startYear != null) {
			if (stringBuilder.length() > 0 ) {
				stringBuilder.append(' ');
			}
			stringBuilder.append(startYear);
		}

		return stringBuilder.toString();
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
	 * This method returns start date. It's a concatenation of three fields,
	 * startDay, startMonth, and startYear. It's a transient property 
	 * (not stored on database ndr).
	 *  
	 * @return String rappresentation of volume identifiers.
	 */
	@Transient
	public String getEndDate() {
		StringBuilder stringBuilder = new StringBuilder(0);
		if (endDay != null) {
			stringBuilder.append(endDay);
		}
		
		if (!ObjectUtils.toString(endMonth).equals("")) {
			if (stringBuilder.length() > 0 ) {
				stringBuilder.append(' ');
			}
			stringBuilder.append(endMonth);
		}

		if (endYear != null) {
			if (stringBuilder.length() > 0 ) {
				stringBuilder.append(' ');
			}
			stringBuilder.append(endYear);
		}

		return stringBuilder.toString();
	}	

	/**
	 * @return the prTag
	 */
	public Boolean getPreferredRole() {
		return preferredRole;
	}
	
	/**
	 * @param prTag the prTag to set
	 */
	public void setPreferredRole(Boolean preferredRole) {
		this.preferredRole = preferredRole;
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
		return startUns;
	}

	/**
	 * @param startUns the startUns to set
	 */
	public void setStartUns(Boolean startUns) {
		this.startUns = startUns;
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
	public Month getStartMonthNum() {
		return startMonthNum;
	}
	
	/**
	 * @param startMonthNum the startMonthNum to set
	 */
	public void setStartMonthNum(Month startMonthNum) {
		this.startMonthNum = startMonthNum;
	}
	
	/**
	 * @return the endMonthNum
	 */
	public Month getEndMonthNum() {
		return endMonthNum;
	}
	
	/**
	 * @param endMonthNum the endMonthNum to set
	 */
	public void setEndMonthNum(Month endMonthNum) {
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
