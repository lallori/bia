/*
 * EditTitleOrOccupationsPersonCommand.java
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

/**
 * Command bean for action "Edit Title Or Occupations Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.peoplebase.EditTitlesOrOccupationsPersonController
 */
public class EditTitleOrOccupationPersonCommand {
	private Integer prfLinkId;
	private Integer personId;
	private Integer titleOccId;
	private String titleOrOccupationDescription;
	private Integer startDay;
	private String startMonth;
	private Integer startYear;
	private Integer endDay;
	private String endMonth;
	private Integer endYear;
	private Integer prefferedRole;

	/**
	 * @param prfLinkId the prfLinkId to set
	 */
	public void setPrfLinkId(Integer prfLinkId) {
		this.prfLinkId = prfLinkId;
	}

	/**
	 * @return the prfLinkId
	 */
	public Integer getPrfLinkId() {
		return prfLinkId;
	}

	/**
	 * @return the endDay
	 */
	public Integer getEndDay() {
		return endDay;
	}

	/**
	 * @return the endMonth
	 */
	public String getEndMonth() {
		return endMonth;
	}

	/**
	 * @return the endYear
	 */
	public Integer getEndYear() {
		return endYear;
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
	 * @return the prefferedRole
	 */
	public Integer getPrefferedRole() {
		return prefferedRole;
	}

	/**
	 * @return the startDay
	 */
	public Integer getStartDay() {
		return startDay;
	}

	/**
	 * @return the startMonth
	 */
	public String getStartMonth() {
		return startMonth;
	}

	/**
	 * @return the startYear
	 */
	public Integer getStartYear() {
		return startYear;
	}

	/**
	 * @return the titleOccId
	 */
	public Integer getTitleOccId() {
		return titleOccId;
	}

	/**
	 * @return the titleOrOccupationDescription
	 */
	public String getTitleOrOccupationDescription() {
		return titleOrOccupationDescription;
	}

	/**
	 * @param endDay the endDay to set
	 */
	public void setEndDay(Integer endDay) {
		this.endDay = endDay;
	}

	/**
	 * @param endMonth the endMonth to set
	 */
	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
	}

	/**
	 * @param endYear the endYear to set
	 */
	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
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
	 * @param prefferedRole the prefferedRole to set
	 */
	public void setPrefferedRole(Integer prefferedRole) {
		this.prefferedRole = prefferedRole;
	}

	/**
	 * @param startDay the startDay to set
	 */
	public void setStartDay(Integer startDay) {
		this.startDay = startDay;
	}

	/**
	 * @param startMonth the startMonth to set
	 */
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}

	/**
	 * @param startYear the startYear to set
	 */
	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	/**
	 * @param titleOccId the titleOccId to set
	 */
	public void setTitleOccId(Integer titleOccId) {
		this.titleOccId = titleOccId;
	}

	/**
	 * @param titleOrOccupationDescription the titleOrOccupationDescription to set
	 */
	public void setTitleOrOccupationDescription(String titleOrOccupationDescription) {
		this.titleOrOccupationDescription = titleOrOccupationDescription;
	}
}
