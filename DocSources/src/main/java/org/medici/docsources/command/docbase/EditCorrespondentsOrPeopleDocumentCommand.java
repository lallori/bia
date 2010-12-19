/*
 * EditCorrespondentsOrPeopleDocumentCommand.java
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
 * Command bean for action "Edit Correspondents Or People Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.EditDetailsDocumentController.docsources.controller.docbase.ModifyDocumentController
 */
public class EditCorrespondentsOrPeopleDocumentCommand {
	private Integer entryId;
	private Integer senderPeopleId;
	private String senderPeopleDescription;
	private Boolean senderPeopleUnsure;
	private Integer senderPlaceId;
	private String senderPlaceDescription;
	private Boolean senderPlaceUnsure;
	private Integer recipientPeopleId;
	private String recipientPeopleDescription;
	private Boolean recipientPeopleUnsure;
	private Integer recipientPlaceId;
	private String recipientPlaceDescription;
	private Boolean recipientPlaceUnsure;
	
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
	 * @return the senderPeopleId
	 */
	public Integer getSenderPeopleId() {
		return senderPeopleId;
	}
	
	/**
	 * @param senderPeopleId the senderPeopleId to set
	 */
	public void setSenderPeopleId(Integer senderPeopleId) {
		this.senderPeopleId = senderPeopleId;
	}
	
	/**
	 * @return the senderPeopleDescription
	 */
	public String getSenderPeopleDescription() {
		return senderPeopleDescription;
	}
	
	/**
	 * @param senderPeopleDescription the senderPeopleDescription to set
	 */
	public void setSenderPeopleDescription(String senderPeopleDescription) {
		this.senderPeopleDescription = senderPeopleDescription;
	}
	
	/**
	 * @return the senderPeopleUnsure
	 */
	public Boolean getSenderPeopleUnsure() {
		return senderPeopleUnsure;
	}
	
	/**
	 * @param senderPeopleUnsure the senderPeopleUnsure to set
	 */
	public void setSenderPeopleUnsure(Boolean senderPeopleUnsure) {
		this.senderPeopleUnsure = senderPeopleUnsure;
	}
	
	/**
	 * @return the senderPlaceId
	 */
	public Integer getSenderPlaceId() {
		return senderPlaceId;
	}
	
	/**
	 * @param senderPlaceId the senderPlaceId to set
	 */
	public void setSenderPlaceId(Integer senderPlaceId) {
		this.senderPlaceId = senderPlaceId;
	}
	
	/**
	 * @return the senderPlaceDescription
	 */
	public String getSenderPlaceDescription() {
		return senderPlaceDescription;
	}
	
	/**
	 * @param senderPlaceDescription the senderPlaceDescription to set
	 */
	public void setSenderPlaceDescription(String senderPlaceDescription) {
		this.senderPlaceDescription = senderPlaceDescription;
	}
	
	/**
	 * @return the senderPlaceUnsure
	 */
	public Boolean getSenderPlaceUnsure() {
		return senderPlaceUnsure;
	}
	
	/**
	 * @param senderPlaceUnsure the senderPlaceUnsure to set
	 */
	public void setSenderPlaceUnsure(Boolean senderPlaceUnsure) {
		this.senderPlaceUnsure = senderPlaceUnsure;
	}
	
	/**
	 * @return the recipientPeopleId
	 */
	public Integer getRecipientPeopleId() {
		return recipientPeopleId;
	}
	
	/**
	 * @param recipientPeopleId the recipientPeopleId to set
	 */
	public void setRecipientPeopleId(Integer recipientPeopleId) {
		this.recipientPeopleId = recipientPeopleId;
	}
	
	/**
	 * @return the recipientPeopleDescription
	 */
	public String getRecipientPeopleDescription() {
		return recipientPeopleDescription;
	}
	
	/**
	 * @param recipientPeopleDescription the recipientPeopleDescription to set
	 */
	public void setRecipientPeopleDescription(String recipientPeopleDescription) {
		this.recipientPeopleDescription = recipientPeopleDescription;
	}
	
	/**
	 * @return the recipientPeopleUnsure
	 */
	public Boolean getRecipientPeopleUnsure() {
		return recipientPeopleUnsure;
	}
	
	/**
	 * @param recipientPeopleUnsure the recipientPeopleUnsure to set
	 */
	public void setRecipientPeopleUnsure(Boolean recipientPeopleUnsure) {
		this.recipientPeopleUnsure = recipientPeopleUnsure;
	}
	
	/**
	 * @return the recipientPlaceId
	 */
	public Integer getRecipientPlaceId() {
		return recipientPlaceId;
	}
	
	/**
	 * @param recipientPlaceId the recipientPlaceId to set
	 */
	public void setRecipientPlaceId(Integer recipientPlaceId) {
		this.recipientPlaceId = recipientPlaceId;
	}
	
	/**
	 * @return the recipientPlaceDescription
	 */
	public String getRecipientPlaceDescription() {
		return recipientPlaceDescription;
	}
	
	/**
	 * @param recipientPlaceDescription the recipientPlaceDescription to set
	 */
	public void setRecipientPlaceDescription(String recipientPlaceDescription) {
		this.recipientPlaceDescription = recipientPlaceDescription;
	}
	
	/**
	 * @return the recipientPlaceUnsure
	 */
	public Boolean getRecipientPlaceUnsure() {
		return recipientPlaceUnsure;
	}

	/**
	 * @param recipientPlaceUnsure the recipientPlaceUnsure to set
	 */
	public void setRecipientPlaceUnsure(Boolean recipientPlaceUnsure) {
		this.recipientPlaceUnsure = recipientPlaceUnsure;
	}
}
