/*
 * EditExtractOrSynopsisInManuscriptViewerRequestCommand.java
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
package org.medici.bia.command.manuscriptviewer;

/**
 * Command bean for action "Request Show Volume".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.docsources.controller.volbase.EditDetailsVolumeController
 */
public class EditExtractDocumentDialogCommand {
	private Integer synExtrId;
	private Integer entryId;
	private String docExtract;
	/**
	 * @return the synExtrId
	 */
	public Integer getSynExtrId() {
		return synExtrId;
	}
	/**
	 * @param synExtrId the synExtrId to set
	 */
	public void setSynExtrId(Integer synExtrId) {
		this.synExtrId = synExtrId;
	}
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
	 * @return the docExtract
	 */
	public String getDocExtract() {
		return docExtract;
	}
	/**
	 * @param docExtract the docExtract to set
	 */
	public void setDocExtract(String docExtract) {
		this.docExtract = docExtract;
	}

}
