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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Person entity.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Entity
@Table ( name = "\"tblPeople\"" ) 
public class People implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6007789289980534157L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"PERSONID\"", length=10, nullable=false)
	private Integer personId;
	@Column (name="\"MAPNameLF\"", length=150)
	private String mapNameLf;
	@Column (name="\"GENDER\"", length=1)
	private Gender gender;
	@Column (name="\"ACTIVESTART\"", length=50)
	private String activeStart;
	@Column (name="\"ACTIVEEND\"", length=50)
	private String activeEnd;
	@Column (name="\"BMONTHNUM\"", length=10)
	private Integer bMonthNum;
	@Column (name="\"BDAY\"", length=3, columnDefinition="TINYINT")
	private Integer bDay;
	@Column (name="\"BYEAR\"", length=15)
	private Integer bYear;
	@Column (name="\"BPLACEID\"", length=10)
	private Integer bPlaceId;
	@Column (name="\"BPLACE\"", length=50)
	private Integer bPlace;
	@Column (name="\"DMONTHNUM\"", length=10)
	private Integer dMonthNum;
	@Column (name="\"DDAY\"", length=3, columnDefinition="TINYINT")
	private Integer dDay;
	@Column (name="\"DYEAR\"", length=15)
	private Integer dYear;
	@Column (name="\"DPLACEID\"", length=10)
	private Integer dPlaceId;
	@Column (name="\"DPLACE\"", length=50)
	private String dPlace;
	@Column (name="\"FIRST\"", length=50)
	private String first;
	@Column (name="\"SUCNUM\"", length=6)
	private String sucNum;
	@Column (name="\"Middle\"", length=50)
	private String middle;
	@Column (name="\"midprefix\"", length=50)
	private String midPrefix;
	@Column (name="\"Last\"", length=50)
	private String last;
	@Column (name="\"lastprefix\"", length=50)
	private String lastPrefix;
	@Column (name="\"Postlast\"", length=50)
	private String postLast;
	@Column (name="\"postlastprefix\"", length=50)
	private String postLastPrefix;
	@Column (name="\"BAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean bApprox;
	@Column (name="\"BDATEBC\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean bDateBc;
	@Column (name="\"BPLACEUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean bPlaceUns;
	@Column (name="\"DAPPROX\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean dApprox;
	@Column (name="\"DYEARBC\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean dYearBc;
	@Column (name="\"DPLACEUNS\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean dPlaceUns;
	@Column (name="\"STATUS\"", length=15)
	private String status;
	@Column (name="\"BIONOTES\"", columnDefinition="LONGTEXT")
	private String bioNotes;
	@Column (name="\"STAFFNOTES\"", columnDefinition="LONGTEXT")
	private String staffNotes;
	@Column (name="\"PORTRAIT\"", length=1, columnDefinition="TINYINT", nullable=false)
	private Boolean portrait;
	@ManyToOne
	@JoinColumn(name="\"FATHERID\"")
	private People fatherId;
	@ManyToOne
	@JoinColumn(name="\"MOTHERID\"")
	private People motherId;
	@Column (name="\"RESID\"", length=50)
	private String resId;
	@Column (name="\"DateCreated\"")
	private Date dateCreated;
	@Column (name="\"LastUpdate\"")
	private Date lastUpdate;

	
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
	 * @return the bMonthNum
	 */
	public Integer getbMonthNum() {
		return bMonthNum;
	}


	/**
	 * @param bMonthNum the bMonthNum to set
	 */
	public void setbMonthNum(Integer bMonthNum) {
		this.bMonthNum = bMonthNum;
	}


	/**
	 * @return the bDay
	 */
	public Integer getbDay() {
		return bDay;
	}


	/**
	 * @param bDay the bDay to set
	 */
	public void setbDay(Integer bDay) {
		this.bDay = bDay;
	}


	/**
	 * @return the bYear
	 */
	public Integer getbYear() {
		return bYear;
	}


	/**
	 * @param bYear the bYear to set
	 */
	public void setbYear(Integer bYear) {
		this.bYear = bYear;
	}


	/**
	 * @return the bPlaceId
	 */
	public Integer getbPlaceId() {
		return bPlaceId;
	}


	/**
	 * @param bPlaceId the bPlaceId to set
	 */
	public void setbPlaceId(Integer bPlaceId) {
		this.bPlaceId = bPlaceId;
	}


	/**
	 * @return the bPlace
	 */
	public Integer getbPlace() {
		return bPlace;
	}


	/**
	 * @param bPlace the bPlace to set
	 */
	public void setbPlace(Integer bPlace) {
		this.bPlace = bPlace;
	}


	/**
	 * @return the dMonthNum
	 */
	public Integer getdMonthNum() {
		return dMonthNum;
	}


	/**
	 * @param dMonthNum the dMonthNum to set
	 */
	public void setdMonthNum(Integer dMonthNum) {
		this.dMonthNum = dMonthNum;
	}


	/**
	 * @return the dDay
	 */
	public Integer getdDay() {
		return dDay;
	}


	/**
	 * @param dDay the dDay to set
	 */
	public void setdDay(Integer dDay) {
		this.dDay = dDay;
	}


	/**
	 * @return the dYear
	 */
	public Integer getdYear() {
		return dYear;
	}


	/**
	 * @param dYear the dYear to set
	 */
	public void setdYear(Integer dYear) {
		this.dYear = dYear;
	}


	/**
	 * @return the dPlaceId
	 */
	public Integer getdPlaceId() {
		return dPlaceId;
	}


	/**
	 * @param dPlaceId the dPlaceId to set
	 */
	public void setdPlaceId(Integer dPlaceId) {
		this.dPlaceId = dPlaceId;
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
	 * @return the bApprox
	 */
	public Boolean getbApprox() {
		return bApprox;
	}


	/**
	 * @param bApprox the bApprox to set
	 */
	public void setbApprox(Boolean bApprox) {
		this.bApprox = bApprox;
	}


	/**
	 * @return the bDateBc
	 */
	public Boolean getbDateBc() {
		return bDateBc;
	}


	/**
	 * @param bDateBc the bDateBc to set
	 */
	public void setbDateBc(Boolean bDateBc) {
		this.bDateBc = bDateBc;
	}


	/**
	 * @return the bPlaceUns
	 */
	public Boolean getbPlaceUns() {
		return bPlaceUns;
	}


	/**
	 * @param bPlaceUns the bPlaceUns to set
	 */
	public void setbPlaceUns(Boolean bPlaceUns) {
		this.bPlaceUns = bPlaceUns;
	}


	/**
	 * @return the dApprox
	 */
	public Boolean getdApprox() {
		return dApprox;
	}


	/**
	 * @param dApprox the dApprox to set
	 */
	public void setdApprox(Boolean dApprox) {
		this.dApprox = dApprox;
	}


	/**
	 * @return the dYearBc
	 */
	public Boolean getdYearBc() {
		return dYearBc;
	}


	/**
	 * @param dYearBc the dYearBc to set
	 */
	public void setdYearBc(Boolean dYearBc) {
		this.dYearBc = dYearBc;
	}


	/**
	 * @return the dPlaceUns
	 */
	public Boolean getdPlaceUns() {
		return dPlaceUns;
	}


	/**
	 * @param dPlaceUns the dPlaceUns to set
	 */
	public void setdPlaceUns(Boolean dPlaceUns) {
		this.dPlaceUns = dPlaceUns;
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
	 * @return the fatherId
	 */
	public People getFatherId() {
		return fatherId;
	}


	/**
	 * @param fatherId the fatherId to set
	 */
	public void setFatherId(People fatherId) {
		this.fatherId = fatherId;
	}


	/**
	 * @return the motherId
	 */
	public People getMotherId() {
		return motherId;
	}


	/**
	 * @param motherId the motherId to set
	 */
	public void setMotherId(People motherId) {
		this.motherId = motherId;
	}


	/**
	 * @return the resId
	 */
	public String getResId() {
		return resId;
	}


	/**
	 * @param resId the resId to set
	 */
	public void setResId(String resId) {
		this.resId = resId;
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

	public static enum Gender {
		MALE("M"), FEMALE("F"), UNKNOWN("X");
		
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

