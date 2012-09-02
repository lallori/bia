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
package org.medici.bia.command.peoplebase;

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
	private Integer titleOccIdNew;
	private String titleOrOccupationDescription;
	private Boolean preferredRole;
	private Integer startDay;
	private Integer startMonthNum;
	private Integer startYear;
	private Boolean startApprox;
	private Boolean startUns;
	private Integer endDay;
	private Integer endMonthNum;
	private Integer endYear;
	private Boolean endApprox;
	private Boolean endUns;
	
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
	 * @return the titleOccId
	 */
	public Integer getTitleOccIdNew() {
		return titleOccIdNew;
	}
	
	/**
	 * @param titleOccId the titleOccId to set
	 */
	public void setTitleOccIdNew(Integer titleOccIdNew) {
		this.titleOccIdNew = titleOccIdNew;
	}
	
	/**
	 * @return the titleOrOccupationDescription
	 */
	public String getTitleOrOccupationDescription() {
		return titleOrOccupationDescription;
	}
	
	/**
	 * @param titleOrOccupationDescription the titleOrOccupationDescription to set
	 */
	public void setTitleOrOccupationDescription(String titleOrOccupationDescription) {
		this.titleOrOccupationDescription = titleOrOccupationDescription;
	}
	
	/**
	 * @return the preferredRole
	 */
	public Boolean getPreferredRole() {
		return preferredRole;
	}
	
	/**
	 * @param preferredRole the preferredRole to set
	 */
	public void setPreferredRole(Boolean preferredRole) {
		this.preferredRole = preferredRole;
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
}
