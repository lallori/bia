/*
 * EditDetailsPersonCommand.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.docsources.command.peoplebase;

import java.util.Date;

import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.People.Gender;

/**
 * Command bean for action "Edit Details Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.peoplebase.EditDetailsPersonController
 */
public class EditDetailsPersonCommand {
	private Integer personId;
	private String researcher;
	private Date dateCreated;
	private String firstName;
	private String sucNum;
	//Prefix Pre-Id: 
	private String midPrefix;
	//Pre-Id: 
	private String middle;
	//Prefix Lastname:
	private String lastPrefix;
	//Lastname:
	private String last;
	//Prefix Post-Id:
	private String postLastPrefix;
	//Post-Id:
	private String postLast;
	//Gender:
	private Gender gender;
	// Birth Year 
	private Integer bYear;
	// Birth Month
	private Month bMonth;
	// Birth Day
	private Integer bDay;
	// Birth Approx 
	private Boolean bApprox;
	// Birth BC?
	private Boolean bDateBc;
	// Birth Place:
	private Integer bPlace;
	// Birth Active Start: 
	private String activeStart;
	// Birth Unsure
	private Boolean bUnsure;
	// Death Year 
	private Integer dYear;
	// Death Month
	private Month dMonth;
	// Death Day
	private Integer dDay;
	// Death Approx 
	private Boolean dApprox;
	// Death BC?
	private Boolean dDateBc;
	// Death Place:
	private Integer dPlace;
	// Death Unsure
	private Boolean dUnsure;

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
	
	public void setResearcher(String researcher) {
		this.researcher = researcher;
	}

	public String getResearcher() {
		return researcher;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
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
	 * @return the bMonth
	 */
	public Month getbMonth() {
		return bMonth;
	}
	
	/**
	 * @param bMonth the bMonth to set
	 */
	public void setbMonth(Month bMonth) {
		this.bMonth = bMonth;
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
	 * @return the bUnsure
	 */
	public Boolean getbUnsure() {
		return bUnsure;
	}
	
	/**
	 * @param bUnsure the bUnsure to set
	 */
	public void setbUnsure(Boolean bUnsure) {
		this.bUnsure = bUnsure;
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
	 * @return the dMonth
	 */
	public Month getdMonth() {
		return dMonth;
	}
	
	/**
	 * @param dMonth the dMonth to set
	 */
	public void setdMonth(Month dMonth) {
		this.dMonth = dMonth;
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
	 * @return the dDateBc
	 */
	public Boolean getdDateBc() {
		return dDateBc;
	}
	
	/**
	 * @param dDateBc the dDateBc to set
	 */
	public void setdDateBc(Boolean dDateBc) {
		this.dDateBc = dDateBc;
	}
	
	/**
	 * @return the dPlace
	 */
	public Integer getdPlace() {
		return dPlace;
	}
	
	/**
	 * @param dPlace the dPlace to set
	 */
	public void setdPlace(Integer dPlace) {
		this.dPlace = dPlace;
	}
	
	/**
	 * @return the dUnsure
	 */
	public Boolean getdUnsure() {
		return dUnsure;
	}
	
	/**
	 * @param dUnsure the dUnsure to set
	 */
	public void setdUnsure(Boolean dUnsure) {
		this.dUnsure = dUnsure;
	}
}
