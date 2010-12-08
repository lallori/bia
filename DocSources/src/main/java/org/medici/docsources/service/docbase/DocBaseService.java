/*
 * DocBaseService.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.docsources.service.docbase;

import java.util.List;

import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.docsources.domain.Document} 
 * object.<br>
 * It defines every business methods needed to work on documents.
 * With this service, you can :<br>
 * - add a new document<br>
 * - modify an existing document<br> 
 * - search a document by is unique id<br>
 * - execute complex search on volumes<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface DocBaseService {

	/**
	 * This method add a new {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document
	 * @throws ApplicationThrowable
	 */
	public void addNewDocument(Document inputDocument) throws ApplicationThrowable;

	/**
	 * This method modify correspondents or people of an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public void editCorrespondentsOrPeopleDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method modify details of an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public void editDetailsDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method modify extract or Synopsis of an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public void editExtractOrSynopsisDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method modify fact checks of an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param volume {@link org.medici.docsources.domain.Document} to be modified
	 * @throws ApplicationThrowable
	 */
	public void editFactChecksDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method modify topics of an existing {@link org.medici.docsources.domain.Document}.
	 * 
	 * @param document
	 * @throws ApplicationThrowable
	 */
	public void editTopicsDocument(Document document) throws ApplicationThrowable;

	/**
	 * This method will search an existing document by his unique identifiers.
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Document findDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * This method last entry {@link org.medici.docsources.domain.Document}.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.Document}
	 * @throws ApplicationThrowable
	 */
	public Document findLastEntryDocument() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param text
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Document> searchDocuments(String text) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Month> getMonths() throws ApplicationThrowable;
}
