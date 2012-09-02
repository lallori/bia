/*
 * EditFatherPersonCommand.java
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
package org.medici.bia.command.peoplebase;

import org.medici.bia.domain.People.Gender;

/**
 * Command bean for action "Edit Father Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.peoplebase.EditFatherPersonController
 */
public class EditFatherPersonCommand {
	private Integer id;
	private Integer childId;
	private Integer parentId;
	private String fatherDescription;
	private Integer bornYear;
	private Integer bornMonthNum;
	private String bornMonth;
	private Integer bornDay;
	private Integer deathYear;
	private Integer deathMonthNum;
	private String deathMonth;
	private Integer deathDay;
	private String bioNotes;
	private Gender gender;
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param childId the childId to set
	 */
	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	/**
	 * @return the childId
	 */
	public Integer getChildId() {
		return childId;
	}

	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * @return the fatherDescription
	 */
	public String getFatherDescription() {
		return fatherDescription;
	}
	/**
	 * @param fatherDescription the fatherDescription to set
	 */
	public void setFatherDescription(String fatherDescription) {
		this.fatherDescription = fatherDescription;
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
	 * @return the bornMonthNum
	 */
	public Integer getBornMonthNum() {
		return bornMonthNum;
	}
	
	/**
	 * @param bornMonthNum the bornMonthNum to set
	 */
	public void setBornMonthNum(Integer bornMonthNum) {
		this.bornMonthNum = bornMonthNum;
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
	 * @return the deathMonthNum
	 */
	public Integer getDeathMonthNum() {
		return deathMonthNum;
	}

	/**
	 * @param deathMonthNum the deathMonthNum to set
	 */
	public void setDeathMonthNum(Integer deathMonthNum) {
		this.deathMonthNum = deathMonthNum;
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
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param bornMonth the bornMonth to set
	 */
	public void setBornMonth(String bornMonth) {
		this.bornMonth = bornMonth;
	}

	/**
	 * @return the bornMonth
	 */
	public String getBornMonth() {
		return bornMonth;
	}

	/**
	 * @param deathMonth the deathMonth to set
	 */
	public void setDeathMonth(String deathMonth) {
		this.deathMonth = deathMonth;
	}

	/**
	 * @return the deathMonth
	 */
	public String getDeathMonth() {
		return deathMonth;
	}
}
