/*
 * EditDocReferenceDocumentCommand.java
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
package org.medici.bia.command.docbase;


/**
 * Command bean for action "Edit DocReference Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.docbase.EditPeopleDocumentController
 */
public class EditDocReferenceDocumentCommand {
	private Integer entryIdFrom;
	private Integer entryIdTo;
	private Integer docReferenceId;
	/**
	 * @return the entryIdFrom
	 */
	public Integer getEntryIdFrom() {
		return entryIdFrom;
	}
	/**
	 * @param entryIdFrom the entryIdFrom to set
	 */
	public void setEntryIdFrom(Integer entryIdFrom) {
		this.entryIdFrom = entryIdFrom;
	}
	/**
	 * @return the entryIdTo
	 */
	public Integer getEntryIdTo() {
		return entryIdTo;
	}
	/**
	 * @param entryIdTo the entryIdTo to set
	 */
	public void setEntryIdTo(Integer entryIdTo) {
		this.entryIdTo = entryIdTo;
	}
	/**
	 * @return the docReferenceId
	 */
	public Integer getDocReferenceId() {
		return docReferenceId;
	}
	/**
	 * @param docReferenceId the docReferenceId to set
	 */
	public void setDocReferenceId(Integer docReferenceId) {
		this.docReferenceId = docReferenceId;
	}
	
	
}
