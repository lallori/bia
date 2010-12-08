/*
 * DocBaseServiceImpl.java
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

import java.util.ArrayList;
import java.util.List;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is the default implementation of service responsible for every 
 * action on document.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
public class DocBaseServiceImpl implements DocBaseService {
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired
	private MonthDAO monthDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNewDocument(Document inputDocument) throws ApplicationThrowable {
		try {
			getDocumentDAO().persist(inputDocument);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editCorrespondentsOrPeopleDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		
		try {
			getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editDetailsDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		
		try {
			getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editExtractOrSynopsisDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		
		try {
			getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editFactChecksDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		
		try {
			getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void editTopicsDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		
		try {
			getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocument(Integer documentId) throws ApplicationThrowable { 
		// TODO Auto-generated method stub
		return new Document();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findLastEntryDocument() throws ApplicationThrowable {
		try {
			return getDocumentDAO().findLastEntryDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> searchDocuments(String text) throws ApplicationThrowable  {
		// TODO Auto-generated method stub
		return new ArrayList<Document>(0);
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	@Override
	public List<Month> getMonths() throws ApplicationThrowable {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @return the monthDAO
	 */
	public MonthDAO getMonthDAO() {
		return monthDAO;
	}
}
