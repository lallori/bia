/*
 * TranscribeAndContextualizeDocumentCommand.java
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
 * Command bean for action "Transcribe And Contextualize Document".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.command.docbase.EditDetailsDocumentCommand
 * @see org.medici.docsources.controller.docbase.TranscribeAndContextualizeDocumentController
 */
public class TranscribeAndContextualizeDocumentCommand extends EditDetailsDocumentCommand {
	/** This is the correspondents transcribeFolioNum */
	private Integer imageDocumentToCreate;
	/** This is the correspondents folioNum */
	private Integer imageDocumentFolioStart;

	/**
	 * @return the imageDocumentToCreate
	 */
	public Integer getImageDocumentToCreate() {
		return imageDocumentToCreate;
	}

	/**
	 * @param imageDocumentToCreate the imageDocumentToCreate to set
	 */
	public void setImageDocumentToCreate(Integer imageDocumentToCreate) {
		this.imageDocumentToCreate = imageDocumentToCreate;
	}
	
	/**
	 * @return the imageDocumentFolioStart
	 */
	public Integer getImageDocumentFolioStart() {
		return imageDocumentFolioStart;
	}
	
	/**
	 * @param imageDocumentFolioStart the imageDocumentFolioStart to set
	 */
	public void setImageDocumentFolioStart(Integer imageDocumentFolioStart) {
		this.imageDocumentFolioStart = imageDocumentFolioStart;
	}
	
}
