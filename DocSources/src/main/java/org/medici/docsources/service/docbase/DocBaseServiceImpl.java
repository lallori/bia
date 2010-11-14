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
import org.medici.docsources.domain.Document;
import org.medici.docsources.exception.ApplicationThrowable;
import org.springframework.beans.BeanUtils;
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
	public void editDocument(Document inputDocument) throws ApplicationThrowable {
		Document document = null;
		try {
			document = getDocumentDAO().find(inputDocument.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		BeanUtils.copyProperties(inputDocument, document);

		try {
			getDocumentDAO().merge(document);
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

}
