/*
 * EditSpousePersonCommand.java
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

/**
 * Command bean for action "Edit Spouse Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.peoplebase.EditSpousePersonController
 */
public class EditSpousePersonCommand {
	private Integer personId;
	private Integer marriageId;
	private Integer husbandId;
	private String husbandDescription;
	private Integer wifeId;
	private String wifeDescription;
	private Integer startYear;
	private Integer endYear;
	private String marriageTerm;
	private String gender;
	
	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	/**
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
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
	 * @return the husbandId
	 */
	public Integer getHusbandId() {
		return husbandId;
	}

	/**
	 * @param husbandId the husbandId to set
	 */
	public void setHusbandId(Integer husbandId) {
		this.husbandId = husbandId;
	}

	/**
	 * @return the wifeId
	 */
	public Integer getWifeId() {
		return wifeId;
	}

	/**
	 * @param wifeId the wifeId to set
	 */
	public void setWifeId(Integer wifeId) {
		this.wifeId = wifeId;
	}

	/**
	 * @param wifeDescription the wifeDescription to set
	 */
	public void setWifeDescription(String wifeDescription) {
		this.wifeDescription = wifeDescription;
	}

	/**
	 * @return the wifeDescription
	 */
	public String getWifeDescription() {
		return wifeDescription;
	}

	/**
	 * @param husbandDescription the husbandDescription to set
	 */
	public void setHusbandDescription(String husbandDescription) {
		this.husbandDescription = husbandDescription;
	}

	/**
	 * @return the husbandDescription
	 */
	public String getHusbandDescription() {
		return husbandDescription;
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
	 * @return the marriageTerm
	 */
	public String getMarriageTerm() {
		return marriageTerm;
	}

	/**
	 * @param marriageTerm the marriageTerm to set
	 */
	public void setMarriageTerm(String marriageTerm) {
		this.marriageTerm = marriageTerm;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
}
