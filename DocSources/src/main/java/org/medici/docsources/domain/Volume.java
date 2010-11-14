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

import org.hibernate.envers.Audited;

/**
 * Volume entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblVolumes\"" ) 
public class Volume implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3127626344157278734L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"SUMMARYID\"", length=10, nullable=false)
	private Integer summaryId;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="\"SERIESREFNUM\"")
	private SerieList serieList;
	@Column (name="\"VOLNUM\"", length=10)
	private Integer volNum;
	@Column (name="\"VOLLETEXT\"", length=1)
	private String volLeText;
	@Column (name="\"RESID\"")
	private String researcher;
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Column (name="\"VOLTOBEVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date volTobeVettedDate;
	@Column (name="\"VOLTOBEVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean volTobeVetted;
	@Column (name="\"VOLVETID\"", length=50)
	private String volVetId;
	@Column (name="\"VOLVETBEGINS\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date volVetBegins;
	@Column (name="\"VOLVETTED\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean volVetted;
	@Column (name="\"VOLVETTEDDATE\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date volVettedDate;
	@Column (name="\"STATBOX\"", length=50)
	private String statBox;
	@Column (name="\"STARTYEAR\"", length=5)
	private Integer startYear;
	@Column (name="\"STARTMONTH\"", length=50)
	private String startMonth;
	@Column (name="\"STARTMONTHNUM\"", length=10)
	private Integer startMonthNum;
	@Column (name="\"STARTDAY\"", length=3, columnDefinition="TINYINT")
	private Integer startDay;
	@Column (name="\"ENDYEAR\"", length=5)
	private Integer endYear;
	@Column (name="\"ENDMONTH\"", length=50)
	private String endMonth;
	@Column (name="\"ENDMONTHNUM\"", length=10)
	private Integer endMonthNum;
	@Column (name="\"ENDDAY\"", length=3, columnDefinition="TINYINT")
	private Integer endDay;
	@Column (name="\"DATENOTES\"", columnDefinition="LONGTEXT")
	private String dateNotes;
	@Column (name="\"SENDERS\"", columnDefinition="LONGTEXT")
	private String senders;
	@Column (name="\"RECIPS\"", columnDefinition="LONGTEXT")
	private String recips;
	@Column (name="\"CCONTEXT\"", columnDefinition="LONGTEXT")
	private String ccontext;
	@Column (name="\"FOLIOCOUNT\"", length=50)
	private String folioCount;
	@Column (name="\"BOUND\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean bound;
	@Column (name="\"FOLSNUMBRD\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean folsNumbrd;
	@Column (name="\"OLDALPHAINDEX\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean oldAlphaIndex;
	// Column condition renamed in ccondition beacause inital word is reserved on Mysql 
	@Column (name="\"CCONDITION\"", columnDefinition="LONGTEXT")
	private String ccondition;
	@Column (name="\"ITALIAN\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean italian;
	@Column (name="\"SPANISH\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean spanish;
	@Column (name="\"ENGLISH\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean english;
	@Column (name="\"LATIN\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean latin;
	@Column (name="\"GERMAN\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean german;
	@Column (name="\"FRENCH\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean french;
	@Column (name="\"OTHERLANG\"", length=50)
	private String otherLang;
	@Column (name="\"CIPHER\"", length=1, columnDefinition="TINYINT default '-1'", nullable=false)
	private Boolean cipher;
	@Column (name="\"CIPHERNOTES\"", length=255)
	private String cipherNotes;
	@Column (name="\"ORGNOTES\"", columnDefinition="LONGTEXT")
	private String orgNotes;
	@Column (name="\"STAFFMEMO\"", columnDefinition="LONGTEXT")
	private String staffMemo;

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
	public String getVolLeText() {
		return volLeText;
	}
	
	/**
	 * @param volLeText the volLeText to set
	 */
	public void setVolLeText(String volLeText) {
		this.volLeText = volLeText;
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
}