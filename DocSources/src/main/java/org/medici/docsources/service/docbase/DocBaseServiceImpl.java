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
import org.medici.docsources.dao.eplink.EpLinkDAO;
import org.medici.docsources.dao.epltolink.EplToLinkDAO;
import org.medici.docsources.dao.factchecks.FactChecksDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.synextract.SynExtractDAO;
import org.medici.docsources.dao.topicslist.TopicsListDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EpLink;
import org.medici.docsources.domain.EplToLink;
import org.medici.docsources.domain.FactChecks;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.SynExtract;
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
	private EpLinkDAO epLinkDAO;
	@Autowired
	private EplToLinkDAO eplToLinkDAO;
	@Autowired
	private FactChecksDAO factChecksDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private SynExtractDAO synExtractDAO;
	@Autowired
	private TopicsListDAO topicsListDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document addNewCorrespondentsOrPeopleDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			epLink.setEpLinkId(null);
			epLink.setDateCreated(new Date());

			getEpLinkDAO().persist(epLink);

			return epLink.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

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
	public Document addNewExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			synExtract.setSynExtrId(null);
			synExtract.setDateCreated(new Date());
			synExtract.setLastUpdate(new Date());

			getSynExtractDAO().persist(synExtract);

			return synExtract.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document addNewTopicDocument(EplToLink eplToLink) throws ApplicationThrowable {
		try {
			eplToLink.setEplToId(null);
			eplToLink.setDateCreated(new Date());

			getEplToLinkDAO().persist(eplToLink);

			return eplToLink.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDocument(Document document) throws ApplicationThrowable {
		try {
			getDocumentDAO().remove(document);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deletePeopleDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			getEpLinkDAO().remove(epLink);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteTopicDocument(EplToLink eplToLink) throws ApplicationThrowable {
		try {
			getEplToLinkDAO().remove(eplToLink);
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
		//Setting fields that are defined as nullable = false
		document.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
		// We need to attach the correct volume istance by database extraction.
		document.setVolume(getVolumeDAO().findVolume(document.getVolume().getVolNum(), document.getVolume().getVolLetExt()));
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
	public Document editExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		SynExtract synExtractToUpdate = null;
		try {
			synExtractToUpdate = getSynExtractDAO().find(synExtract.getSynExtrId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		// fill fields to update document section
		synExtractToUpdate.setLastUpdate(new Date());
		synExtractToUpdate.setDocExtract(synExtract.getDocExtract());
		synExtractToUpdate.setSynopsis(synExtract.getSynopsis());
		
		try {
			getSynExtractDAO().merge(synExtractToUpdate);

			return synExtractToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable {
		FactChecks factChecksToUpdate = null;
		try {
			factChecksToUpdate = getFactChecksDAO().find(factChecks.getVetId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		// fill fields to update document section
		factChecksToUpdate.setAddLRes(factChecks.getAddLRes());

		try {
			getFactChecksDAO().merge(factChecksToUpdate);

			return factChecksToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("null")
	@Override
	public Document editTopicDocument(EplToLink eplToLink) throws ApplicationThrowable {
		EplToLink eplToLinkToUpdate = null;
		try {
			eplToLink = getEplToLinkDAO().find(eplToLink.getEplToId());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		// fill fields to update document section
		eplToLinkToUpdate.setTopic(eplToLink.getTopic());
		eplToLinkToUpdate.setPlace(eplToLink.getPlace());


		try {
			getEplToLinkDAO().merge(eplToLinkToUpdate);

			return eplToLinkToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EpLink> findCorrespondentsPeopleDocument(Integer entryId) throws ApplicationThrowable {
		try {
			return getEpLinkDAO().findByEntryId(entryId);
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
	public FactChecks findFactChecksDocument(Integer entryId) throws ApplicationThrowable {
		try {
			return getFactChecksDAO().findByEntryId(entryId);
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
	 * {@inheritDoc}
	 */
	@Override
	public SynExtract findSynExtractDocument(Integer entryId) throws ApplicationThrowable {
		try {
			return getSynExtractDAO().findByEntryId(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EplToLink findTopicDocument(Integer entryId, Integer eplToLinkId) throws ApplicationThrowable {
		try {
			return getEplToLinkDAO().find(entryId, eplToLinkId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EplToLink> findTopicsDocument(Integer entryId) throws ApplicationThrowable {
		try {
			return getEplToLinkDAO().findByEntryId(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexDocument() throws ApplicationThrowable {
		try {
			getDocumentDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexEpLink() throws ApplicationThrowable {
		try {
			getEpLinkDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexEplToLink() throws ApplicationThrowable {
		try {
			getEplToLinkDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexFactChecks() throws ApplicationThrowable {
		try {
			getFactChecksDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexSynExtract() throws ApplicationThrowable {
		try {
			getSynExtractDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexTopicList() throws ApplicationThrowable {
		try {
			getTopicsListDAO().generateIndex();
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
	 * @return the epLinkDAO
	 */
	public EpLinkDAO getEpLinkDAO() {
		return epLinkDAO;
	}

	/**
	 * @return the eplToLinkDAO
	 */
	public EplToLinkDAO getEplToLinkDAO() {
		return eplToLinkDAO;
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
	 * @return the synExtractDAO
	 */
	public SynExtractDAO getSynExtractDAO() {
		return synExtractDAO;
	}

	/**
	 * @return the topicsListDAO
	 */
	public TopicsListDAO getTopicsListDAO() {
		return topicsListDAO;
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
	 * @param epLinkDAO the epLinkDAO to set
	 */
	public void setEpLinkDAO(EpLinkDAO epLinkDAO) {
		this.epLinkDAO = epLinkDAO;
	}

	/**
	 * @param eplToLinkDAO the eplToLinkDAO to set
	 */
	public void setEplToLinkDAO(EplToLinkDAO eplToLinkDAO) {
		this.eplToLinkDAO = eplToLinkDAO;
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
	 * @param synExtractDAO the synExtractDAO to set
	 */
	public void setSynExtractDAO(SynExtractDAO synExtractDAO) {
		this.synExtractDAO = synExtractDAO;
	}

	/**
	 * @param topicsListDAO the topicsListDAO to set
	 */
	public void setTopicsListDAO(TopicsListDAO topicsListDAO) {
		this.topicsListDAO = topicsListDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
}
