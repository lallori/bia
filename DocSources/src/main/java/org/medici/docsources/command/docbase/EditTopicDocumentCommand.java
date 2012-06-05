/*
 * EditTopicDocumentCommand.java
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

import java.util.Date;

/**
 * Command bean for action "Edit Topic Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.controller.docbase.EditTopicDocumentController
 */
public class EditTopicDocumentCommand {
	private Date dateCreated;
	private Integer entryId;
	private Integer eplToId;
	private String placeDescription;
	private String placePrefered;
	private Integer placeId;
	private Integer topicId;

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * This method returns entryId property.
	 * 
	 * @return the entryId
	 */
	public Integer getEntryId() {
		return entryId;
	}

	/**
	 * @return the eplToId
	 */
	public Integer getEplToId() {
		return eplToId;
	}

	/**
	 * @return the placeDescription
	 */
	public String getPlaceDescription() {
		return placeDescription;
	}

	/**
	 * @return the placeId
	 */
	public Integer getPlaceId() {
		return placeId;
	}

	/**
	 * @return the topicId
	 */
	public Integer getTopicId() {
		return topicId;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * This method sets document unique id property.
	 * 
	 * @param the entryId to set
	 */
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	/**
	 * @param eplToId the eplToId to set
	 */
	public void setEplToId(Integer eplToId) {
		this.eplToId = eplToId;
	}

	/**
	 * @param placeDescription the placeDescription to set
	 */
	public void setPlaceDescription(String placeDescription) {
		this.placeDescription = placeDescription;
	}

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	/**
	 * @param placePrefered the placePrefered to set
	 */
	public void setPlacePrefered(String placePrefered) {
		this.placePrefered = placePrefered;
	}

	/**
	 * @return the placePrefered
	 */
	public String getPlacePrefered() {
		return placePrefered;
	}
}
