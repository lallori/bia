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

import java.util.Date;
import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.factchecks.FactChecksDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.FactChecks;
import org.medici.docsources.domain.Month;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private FactChecksDAO factChecksDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document addNewDocument(Document document) throws ApplicationThrowable {
		try {
			document.setEntryId(null);
			
			// We need to attach the correct volume istance by database extraction.
			document.setVolume(getVolumeDAO().findVolume(document.getVolume().getVolNum(), document.getVolume().getVolLetExt()));
			//Setting fields that are defined as nullable = false
			document.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			document.setDateCreated(new Date());
			document.setLastUpdate(new Date());
			document.setDocTobeVetted(true);
			document.setDocToBeVettedDate(new Date());
			document.setDocVetted(false);
			document.setNewEntry(true);
			document.setReckoning(false);
			document.setSenderPeopleUnsure(false);
			document.setSenderPlaceUnsure(false);
			document.setRecipientPeopleUnsure(false);
			document.setRecipientPlaceUnsure(false);
			document.setGraphic(false);

			if (document.getDocMonthNum().equals(0)) {
				document.setDocMonthNum(null);
			}

			getDocumentDAO().persist(document);

			return document;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editCorrespondentsOrPeopleDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		documentToUpdate.setLastUpdate(new Date());
		
		try {
			getDocumentDAO().merge(documentToUpdate);

			return documentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editDetailsDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());

		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		documentToUpdate.setLastUpdate(new Date());
		
		try {
			getDocumentDAO().merge(documentToUpdate);

			return documentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editExtractOrSynopsisDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		documentToUpdate.setLastUpdate(new Date());
		
		try {
			getDocumentDAO().merge(documentToUpdate);

			return documentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editFactChecksDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		try {
			FactChecks factChecks = documentToUpdate.getFactChecks();
			if (factChecks == null) {
				factChecks = new FactChecks();
				factChecks.setEntryId(document);
			}
			
			factChecks.setAddLRes(document.getFactChecks().getAddLRes());
			
			if (documentToUpdate.getFactChecks() == null) {
				getFactChecksDAO().persist(factChecks);
			} else {
				getFactChecksDAO().merge(factChecks);
			}

			documentToUpdate.setFactChecks(factChecks);
			return documentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editTopicsDocument(Document document) throws ApplicationThrowable {
		Document documentToUpdate = null;
		try {
			documentToUpdate = getDocumentDAO().find(document.getEntryId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		//TODO : fill fields to update document section
		documentToUpdate.setLastUpdate(new Date());

		try {
			getDocumentDAO().merge(documentToUpdate);

			return documentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocument(Integer entryId) throws ApplicationThrowable { 
		try {
			return getDocumentDAO().find(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
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
	 * @return the factChecksDAO
	 */
	public FactChecksDAO getFactChecksDAO() {
		return factChecksDAO;
	}

	/**
	 * @return the monthDAO
	 */
	public MonthDAO getMonthDAO() {
		return monthDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Month> getMonths() throws ApplicationThrowable {
		try {
			return getMonthDAO().getAllMonths();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocuments(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getDocumentDAO().searchDocuments(text, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	/**
	 * @param factChecksDAO the factChecksDAO to set
	 */
	public void setFactChecksDAO(FactChecksDAO factChecksDAO) {
		this.factChecksDAO = factChecksDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
}
