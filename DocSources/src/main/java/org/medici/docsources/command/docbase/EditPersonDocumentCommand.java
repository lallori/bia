/*
 * EditPersonDocumentCommand.java
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
package org.medici.docsources.command.docbase;


/**
 * Command bean for action "Edit Person Document" and Add New PeopleDocument
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.controller.docbase.EditTopicDocumentController
 */
public class EditPersonDocumentCommand {
	private Integer entryId;
	private Integer epLinkId;
	private Integer personId;
	private String personDescription;
	private Boolean portrait;
	private Boolean assignUnsure;
	/**
	 * @return the entryId
	 */
	public Integer getEntryId() {
		return entryId;
	}
	/**
	 * @param entryId the entryId to set
	 */
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
	/**
	 * @return the epLinkId
	 */
	public Integer getEpLinkId() {
		return epLinkId;
	}
	/**
	 * @param epLinkId the epLinkId to set
	 */
	public void setEpLinkId(Integer epLinkId) {
		this.epLinkId = epLinkId;
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
	 * @return the personDescription
	 */
	public String getPersonDescription() {
		return personDescription;
	}
	
	/**
	 * @param personDescription the personDescription to set
	 */
	public void setPersonDescription(String personDescription) {
		this.personDescription = personDescription;
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
	 * @return the assignUnsure
	 */
	public Boolean getAssignUnsure() {
		return assignUnsure;
	}
	/**
	 * @param assignUnsure the assignUnsure to set
	 */
	public void setAssignUnsure(Boolean assignUnsure) {
		this.assignUnsure = assignUnsure;
	}

}
