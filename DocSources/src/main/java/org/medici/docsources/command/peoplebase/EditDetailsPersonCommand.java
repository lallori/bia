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
	private String first;
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
	private Integer bornYear;
	// Birth Month
	private Integer bornMonth;
	// Birth Day
	private Integer bornDay;
	// Birth Approx 
	private Boolean bornApprox;
	// Birth BC?
	private Boolean bornDateBc;
	// Birth Place Id :
	private Integer bornPlaceId;
	// Birth Place Description :
	private String bornPlaceDescription;
	// Birth Place Prefered Flag :
	private String bornPlacePrefered;
	// Birth Active Start: 
	private String activeStart;
	//Active End:
	private String activeEnd;
	// Birth Unsure
	private Boolean bornPlaceUnsure;
	// Death Year 
	private Integer deathYear;
	// Death Month
	private Integer deathMonth;
	// Death Day
	private Integer deathDay;
	// Death Approx 
	private Boolean deathApprox;
	// Death BC?
	private Boolean deathDateBc;
	// Death Place Id :
	private Integer deathPlaceId;
	// Death Place Description :
	private String deathPlaceDescription;
	// Death Place Prefered Flag :
	private String deathPlacePrefered;
	// Death Unsure
	private Boolean deathPlaceUnsure;

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
	 * @return the bornMonth
	 */
	public Integer getBornMonth() {
		return bornMonth;
	}

	/**
	 * @param bornMonth the bornMonth to set
	 */
	public void setBornMonth(Integer bornMonth) {
		this.bornMonth = bornMonth;
	}
	/**
	 * @return the bornDay
	 */
	public Integer getBornDay() {
		return bornDay;
	}

	/**
	 * @param bornDay the bornDay to set
	 */
	public void setBornDay(Integer bornDay) {
		this.bornDay = bornDay;
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
	 * @return the bornPlaceId
	 */
	public Integer getBornPlaceId() {
		return bornPlaceId;
	}

	/**
	 * @param bornPlaceId the bornPlaceId to set
	 */
	public void setBornPlaceId(Integer bornPlaceId) {
		this.bornPlaceId = bornPlaceId;
	}

	/**
	 * @param bornPlaceDescription the bornPlaceDescription to set
	 */
	public void setBornPlaceDescription(String bornPlaceDescription) {
		this.bornPlaceDescription = bornPlaceDescription;
	}

	/**
	 * @return the bornPlaceDescription
	 */
	public String getBornPlaceDescription() {
		return bornPlaceDescription;
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
	 * @return the bornUnsure
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
	 * @return the deathMonth
	 */
	public Integer getDeathMonth() {
		return deathMonth;
	}

	/**
	 * @param deathMonth the deathMonth to set
	 */
	public void setDeathMonth(Integer deathMonth) {
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
	 * @return the deathApprox
	 */
	public Boolean getDeathApprox() {
		return deathApprox;
	}

	/**
	 * @param deathApprox the deathApprox to set
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
	 * @param deathPlaceId the deathPlaceId to set
	 */
	public void setDeathPlaceId(Integer deathPlaceId) {
		this.deathPlaceId = deathPlaceId;
	}
	
	/**
	 * @return the deathPlaceId
	 */
	public Integer getDeathPlaceId() {
		return deathPlaceId;
	}
	
	/**
	 * @param deathPlaceDescription the deathPlaceDescription to set
	 */
	public void setDeathPlaceDescription(String deathPlaceDescription) {
		this.deathPlaceDescription = deathPlaceDescription;
	}
	
	/**
	 * @return the deathPlaceDescription
	 */
	public String getDeathPlaceDescription() {
		return deathPlaceDescription;
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

	public void setActiveEnd(String activeEnd) {
		this.activeEnd = activeEnd;
	}

	public String getActiveEnd() {
		return activeEnd;
	}

	/**
	 * @param bornPlacePrefered the bornPlacePrefered to set
	 */
	public void setBornPlacePrefered(String bornPlacePrefered) {
		this.bornPlacePrefered = bornPlacePrefered;
	}

	/**
	 * @return the bornPlacePrefered
	 */
	public String getBornPlacePrefered() {
		return bornPlacePrefered;
	}

	/**
	 * @param deathPlacePrefered the deathPlacePrefered to set
	 */
	public void setDeathPlacePrefered(String deathPlacePrefered) {
		this.deathPlacePrefered = deathPlacePrefered;
	}

	/**
	 * @return the deathPlacePrefered
	 */
	public String getDeathPlacePrefered() {
		return deathPlacePrefered;
	}

}
