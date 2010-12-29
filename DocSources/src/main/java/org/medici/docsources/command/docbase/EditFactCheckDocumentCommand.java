/*
 * EditFactChecksDocumentCommand.java
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
 * Command bean for action "Edit Fact Checks".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.controller.docbase.EditFactCheckDocumentController
 */
public class EditFactCheckDocumentCommand {
	private String addLRes;
	private Integer entryId;
	private Integer vetId;

	/**
	 * @return the addLRes
	 */
	public String getAddLRes() {
		return addLRes;
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
	 * @return the vetId
	 */
	public Integer getVetId() {
		return vetId;
	}

	/**
	 * @param addLRes the addLRes to set
	 */
	public void setAddLRes(String addLRes) {
		this.addLRes = addLRes;
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
	 * @param vetId the vetId to set
	 */
	public void setVetId(Integer vetId) {
		this.vetId = vetId;
	}
}
