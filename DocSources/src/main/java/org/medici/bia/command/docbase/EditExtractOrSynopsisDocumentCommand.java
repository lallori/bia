/*
 * EditExtractOrSynopsisDocumentCommand.java
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

import org.medici.bia.domain.Document;


/**
 * Command bean for action "Edit Extract Or Synopsis Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.controller.docbase.EditExtractOrSynopsisDocumentController
 */
public class EditExtractOrSynopsisDocumentCommand {
	private Integer synExtrId;
	private Integer entryId;
	private String docExtract;
	private String documentBibliography;
	private String synopsis;
	private Boolean modalWindow;
	private Document document;

	/**
	 * This method returns entryId property.
	 * 
	 * @return the entryId
	 */
	public Integer getEntryId() {
		return entryId;
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
	 * @param docExtract the docExtract to set
	 */
	public void setDocExtract(String docExtract) {
		this.docExtract = docExtract;
	}

	/**
	 * @return the docExtract
	 */
	public String getDocExtract() {
		return docExtract;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * 
	 * @return
	 */
	public String getDocumentBibliography() {
		return documentBibliography;
	}

	/**
	 * 
	 * @param documentBibliography
	 */
	public void setDocumentBibliography(String documentBibliography) {
		this.documentBibliography = documentBibliography;
	}

	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}

	/**
	 * @param synExtrId the synExtrId to set
	 */
	public void setSynExtrId(Integer synExtrId) {
		this.synExtrId = synExtrId;
	}

	/**
	 * @return the synExtrId
	 */
	public Integer getSynExtrId() {
		return synExtrId;
	}

	/**
	 * 
	 * @param modalWindow the modal window to set
	 */
	public void setModalWindow(Boolean modalWindow) {
		this.modalWindow = modalWindow;
	}

	/**
	 * 
	 * @return the modalWindow
	 */
	public Boolean getModalWindow() {
		return modalWindow;
	}
}
