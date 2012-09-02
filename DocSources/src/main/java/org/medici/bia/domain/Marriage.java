/*
 * Marriage.java
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Marriage entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Audited
@Table ( name = "\"tblMarriages\"" )
public class Marriage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7088335299743801335L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"MARRIAGEID\"", length=10, nullable=false)
	private Integer marriageId;
	
	@ManyToOne
	@JoinColumn(name="\"HUSBANDID\"")
	private People husband;
	
	@ManyToOne
	@JoinColumn(name="\"WIFEID\"")
	private People wife;
	
	@Column (name="\"STARTDAY\"", length=10)
	private Integer startDay;
	
	@Column (name="\"STARTMONTH\"", length=10)
	private String startMonth;
	
	@Column (name="\"STARTYEAR\"", length=10)
	private Integer startYear;
	
	@Column (name="\"STARTUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean startUns;
	
	@Column (name="\"ENDDAY\"", length=10)
	private Integer endDay;
	
	@Column (name="\"ENDMONTH\"", length=10)
	private String endMonth;
	
	@Column (name="\"ENDYEAR\"", length=10)
	private Integer endYear;
	
	@Column (name="\"ENDUNS\"", length=1, columnDefinition="tinyint", nullable=false)
	private Boolean endUns;
	
	@Column (name="\"MARTERM\"", length=50, nullable=true)
	@Enumerated(EnumType.STRING)
	private MarriageTerm marTerm;
	
	@Column (name="\"REFID\"", length=10)
	private Integer refId;
	
	@Column (name="\"NOTES\"", columnDefinition="LONGTEXT")
	private String notes;
	
	@Column (name="\"STARTMONTHNUM\"", length=10)
	private Integer startMonthNum;
	
	@Column (name="\"ENDMONTHNUM\"", length=10)
	private Integer endMonthNum;
	
	@Column (name="\"DATECREATED\"")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	
	/**
	 * Default constructor
	 */
	public Marriage() {
		super();
	}

	/**
	 * 
	 * @param marriageId
	 */
	public Marriage(Integer marriageId) {
		this.marriageId = marriageId; 
	}

	/**
	 * @return the marriageId
	 */
	public Integer getMarriageId() {
		return marriageId;
	}
	
	/**
	 * @param marriageId the marriageId to set
	 */
	public void setMarriageId(Integer marriageId) {
		this.marriageId = marriageId;
	}
	
	/**
	 * @return the husband
	 */
	public People getHusband() {
		return husband;
	}
	
	/**
	 * @param husband the husband to set
	 */
	public void setHusband(People husband) {
		this.husband = husband;
	}
	
	/**
	 * @return the wife
	 */
	public People getWife() {
		return wife;
	}
	
	/**
	 * @param wife the wife to set
	 */
	public void setWife(People wife) {
		this.wife = wife;
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
	 * @return the marTerm
	 */
	public MarriageTerm getMarTerm() {
		return marTerm;
	}
	
	/**
	 * @param marTerm the marTerm to set
	 */
	public void setMarTerm(MarriageTerm marTerm) {
		this.marTerm = marTerm;
	}
	
	/**
	 * @return the refId
	 */
	public Integer getRefId() {
		return refId;
	}
	
	/**
	 * @param refId the refId to set
	 */
	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	
	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
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
	
	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum MarriageTerm {
		Death("Death"),
		Divorce("Divorce"),
		Annulment("Annulment");
		
		private final String marriageTerm;

	    private MarriageTerm(String value) {
	    	marriageTerm = value;
	    }

	    @Override
	    public String toString(){
	        return marriageTerm;
	    }
	}
}
