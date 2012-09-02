/*
 * EditParentsPersonCommand.java
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
 * Command bean for action "Edit Parents Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.peoplebase.EditParentsPersonController
 */
public class EditParentsPersonCommand {
	private Integer personId;
	private Integer fatherPersonId;
	private Integer motherPersonId;
	private Integer fatherRecordId;
	private Integer motherRecordId;
	private String fatherDescription;
	private Integer bornYearFather;
	private Integer bornMonthNumFather;
	private String bornMonthFather;
	private Integer bornDayFather;
	private Integer deathYearFather;
	private Integer deathMonthNumFather;
	private String deathMonthFather;
	private Integer deathDayFather;
	private String bioNotesFather;
	private Gender genderFather;
	private String motherDescription;
	private Integer bornYearMother;
	private Integer bornMonthNumMother;
	private String bornMonthMother;
	private Integer bornDayMother;
	private Integer deathYearMother;
	private Integer deathMonthNumMother;
	private String deathMonthMother;
	private Integer deathDayMother;
	private String bioNotesMother;
	private Gender genderMother;

	/**
	 * @return the motherPersonId
	 */
	public Integer getMotherPersonId() {
		return motherPersonId;
	}

	/**
	 * @param motherPersonId the motherPersonId to set
	 */
	public void setMotherPersonId(Integer motherPersonId) {
		this.motherPersonId = motherPersonId;
	}

	/**
	 * @return the motherRecordId
	 */
	public Integer getMotherRecordId() {
		return motherRecordId;
	}

	/**
	 * @param motherRecordId the motherRecordId to set
	 */
	public void setMotherRecordId(Integer motherRecordId) {
		this.motherRecordId = motherRecordId;
	}

	/**
	 * @return the motherDescription
	 */
	public String getMotherDescription() {
		return motherDescription;
	}

	/**
	 * @param motherDescription the motherDescription to set
	 */
	public void setMotherDescription(String motherDescription) {
		this.motherDescription = motherDescription;
	}

	/**
	 * @return the bornYearMother
	 */
	public Integer getBornYearMother() {
		return bornYearMother;
	}

	/**
	 * @param bornYearMother the bornYearMother to set
	 */
	public void setBornYearMother(Integer bornYearMother) {
		this.bornYearMother = bornYearMother;
	}

	/**
	 * @return the bornMonthNumMother
	 */
	public Integer getBornMonthNumMother() {
		return bornMonthNumMother;
	}

	/**
	 * @param bornMonthNumMother the bornMonthNumMother to set
	 */
	public void setBornMonthNumMother(Integer bornMonthNumMother) {
		this.bornMonthNumMother = bornMonthNumMother;
	}

	/**
	 * @return the bornMonthMother
	 */
	public String getBornMonthMother() {
		return bornMonthMother;
	}

	/**
	 * @param bornMonthMother the bornMonthMother to set
	 */
	public void setBornMonthMother(String bornMonthMother) {
		this.bornMonthMother = bornMonthMother;
	}

	/**
	 * @return the bornDayMother
	 */
	public Integer getBornDayMother() {
		return bornDayMother;
	}

	/**
	 * @param bornDayMother the bornDayMother to set
	 */
	public void setBornDayMother(Integer bornDayMother) {
		this.bornDayMother = bornDayMother;
	}

	/**
	 * @return the deathYearMother
	 */
	public Integer getDeathYearMother() {
		return deathYearMother;
	}

	/**
	 * @param deathYearMother the deathYearMother to set
	 */
	public void setDeathYearMother(Integer deathYearMother) {
		this.deathYearMother = deathYearMother;
	}

	/**
	 * @return the deathMonthNumMother
	 */
	public Integer getDeathMonthNumMother() {
		return deathMonthNumMother;
	}

	/**
	 * @param deathMonthNumMother the deathMonthNumMother to set
	 */
	public void setDeathMonthNumMother(Integer deathMonthNumMother) {
		this.deathMonthNumMother = deathMonthNumMother;
	}

	/**
	 * @return the deathMonthMother
	 */
	public String getDeathMonthMother() {
		return deathMonthMother;
	}

	/**
	 * @param deathMonthMother the deathMonthMother to set
	 */
	public void setDeathMonthMother(String deathMonthMother) {
		this.deathMonthMother = deathMonthMother;
	}

	/**
	 * @return the deathDayMother
	 */
	public Integer getDeathDayMother() {
		return deathDayMother;
	}

	/**
	 * @param deathDayMother the deathDayMother to set
	 */
	public void setDeathDayMother(Integer deathDayMother) {
		this.deathDayMother = deathDayMother;
	}

	/**
	 * @return the bioNotesMother
	 */
	public String getBioNotesMother() {
		return bioNotesMother;
	}

	/**
	 * @param bioNotesMother the bioNotesMother to set
	 */
	public void setBioNotesMother(String bioNotesMother) {
		this.bioNotesMother = bioNotesMother;
	}

	/**
	 * @return the genderMother
	 */
	public Gender getGenderMother() {
		return genderMother;
	}

	/**
	 * @param genderMother the genderMother to set
	 */
	public void setGenderMother(Gender genderMother) {
		this.genderMother = genderMother;
	}

	/**
	 * @return the fatherId
	 */
	public Integer getFatherPersonId() {
		return fatherPersonId;
	}

	/**
	 * @param fatherId the fatherId to set
	 */
	public void setFatherPersonId(Integer fatherPersonId) {
		this.fatherPersonId = fatherPersonId;
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
	 * @return the bornYearFather
	 */
	public Integer getBornYearFather() {
		return bornYearFather;
	}

	/**
	 * @param bornYearFather the bornYearFather to set
	 */
	public void setBornYearFather(Integer bornYearFather) {
		this.bornYearFather = bornYearFather;
	}

	/**
	 * @return the bornMonthNumFather
	 */
	public Integer getBornMonthNumFather() {
		return bornMonthNumFather;
	}

	/**
	 * @param bornMonthNumFather the bornMonthNumFather to set
	 */
	public void setBornMonthNumFather(Integer bornMonthNumFather) {
		this.bornMonthNumFather = bornMonthNumFather;
	}

	/**
	 * @return the bornMonthFather
	 */
	public String getBornMonthFather() {
		return bornMonthFather;
	}

	/**
	 * @param bornMonthFather the bornMonthFather to set
	 */
	public void setBornMonthFather(String bornMonthFather) {
		this.bornMonthFather = bornMonthFather;
	}

	/**
	 * @return the bornDayFather
	 */
	public Integer getBornDayFather() {
		return bornDayFather;
	}

	/**
	 * @param bornDayFather the bornDayFather to set
	 */
	public void setBornDayFather(Integer bornDayFather) {
		this.bornDayFather = bornDayFather;
	}

	/**
	 * @return the deathYearFather
	 */
	public Integer getDeathYearFather() {
		return deathYearFather;
	}

	/**
	 * @param deathYearFather the deathYearFather to set
	 */
	public void setDeathYearFather(Integer deathYearFather) {
		this.deathYearFather = deathYearFather;
	}

	/**
	 * @return the deathMonthNumFather
	 */
	public Integer getDeathMonthNumFather() {
		return deathMonthNumFather;
	}

	/**
	 * @param deathMonthNumFather the deathMonthNumFather to set
	 */
	public void setDeathMonthNumFather(Integer deathMonthNumFather) {
		this.deathMonthNumFather = deathMonthNumFather;
	}

	/**
	 * @return the deathMonthFather
	 */
	public String getDeathMonthFather() {
		return deathMonthFather;
	}

	/**
	 * @param deathMonthFather the deathMonthFather to set
	 */
	public void setDeathMonthFather(String deathMonthFather) {
		this.deathMonthFather = deathMonthFather;
	}

	/**
	 * @return the deathDayFather
	 */
	public Integer getDeathDayFather() {
		return deathDayFather;
	}

	/**
	 * @param deathDayFather the deathDayFather to set
	 */
	public void setDeathDayFather(Integer deathDayFather) {
		this.deathDayFather = deathDayFather;
	}

	/**
	 * @return the bioNotesFather
	 */
	public String getBioNotesFather() {
		return bioNotesFather;
	}

	/**
	 * @param bioNotesFather the bioNotesFather to set
	 */
	public void setBioNotesFather(String bioNotesFather) {
		this.bioNotesFather = bioNotesFather;
	}

	/**
	 * @return the genderFather
	 */
	public Gender getGenderFather() {
		return genderFather;
	}

	/**
	 * @param genderFather the genderFather to set
	 */
	public void setGenderFather(Gender genderFather) {
		this.genderFather = genderFather;
	}

	/**
	 * This method returns personId property.
	 * 
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * This method sets personId property.
	 * 
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @param fatherRecordId the fatherRecordId to set
	 */
	public void setFatherRecordId(Integer fatherRecordId) {
		this.fatherRecordId = fatherRecordId;
	}

	/**
	 * @return the fatherRecordId
	 */
	public Integer getFatherRecordId() {
		return fatherRecordId;
	}
}
