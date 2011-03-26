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
import java.util.Date;
import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.EpLinkUtils;
import org.medici.docsources.common.util.EplToLinkUtils;
import org.medici.docsources.common.util.ImageUtils;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.eplink.EpLinkDAO;
import org.medici.docsources.dao.epltolink.EplToLinkDAO;
import org.medici.docsources.dao.factchecks.FactChecksDAO;
import org.medici.docsources.dao.image.ImageDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.synextract.SynExtractDAO;
import org.medici.docsources.dao.topicslist.TopicsListDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.EpLink;
import org.medici.docsources.domain.EplToLink;
import org.medici.docsources.domain.FactChecks;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.SynExtract;
import org.medici.docsources.domain.TopicList;
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
	private ImageDAO imageDAO;
	@Autowired
	private MonthDAO monthDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
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
			synExtract.setDocument(getDocumentDAO().find(synExtract.getDocument().getEntryId()));
			getSynExtractDAO().persist(synExtract);

			// We need to refresh linked document entity state, otherwise synExtract property will be null
			getDocumentDAO().refresh(synExtract.getDocument());

			return synExtract.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document addNewFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable {
		try {
			factChecks.setVetId(null);
			factChecks.setDateInfo((new Date()).toString());
			factChecks.setDocument(getDocumentDAO().find(factChecks.getDocument().getEntryId()));
			getFactChecksDAO().persist(factChecks);

			// We need to refresh linked document entity state, otherwise factChecks property will be null
			getDocumentDAO().refresh(factChecks.getDocument());
			
			return factChecks.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document addNewPersonDocument(EpLink epLink) throws ApplicationThrowable {
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
	public Document addNewTopicDocument(EplToLink eplToLink) throws ApplicationThrowable {
		try {
			eplToLink.setEplToId(null);
			eplToLink.setDateCreated(new Date());
			eplToLink.setDocument(getDocumentDAO().find(eplToLink.getDocument().getEntryId()));

			// fill fields to update document section
			if (eplToLink.getTopic() != null) {
				eplToLink.setTopic(getTopicsListDAO().find(eplToLink.getTopic().getTopicId()));				
			} else {
				eplToLink.setTopic(null);
			}
			if (eplToLink.getPlace() != null) {
				eplToLink.setPlace(getPlaceDAO().find(eplToLink.getPlace().getPlaceAllId()));
			} else {
				eplToLink.setPlace(null);
			}

			getEplToLinkDAO().persist(eplToLink);

			// We need to refresh linked document entity state, otherwise eplToLink property will be null
			getDocumentDAO().refresh(eplToLink.getDocument());

			return eplToLink.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document constructDocumentToTranscribe(Integer imageDocumentToCreate, Integer imageDocumentFolioStart) throws ApplicationThrowable {
		try {
			Document document = new Document();
			// New Document must have entryId set to zero
			document.setEntryId(0);
			document.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			document.setDateCreated(new Date());
			Image documentToCreateImage = getImageDAO().find(imageDocumentToCreate);
			Image documentFolioStartImage = getImageDAO().find(imageDocumentFolioStart);
			document.setVolume(getVolumeDAO().findVolume(documentToCreateImage.getVolNum(), documentToCreateImage.getVolLetExt()));
			document.setSubVol(documentToCreateImage.getVolLetExt());
			document.setFolioNum(ImageUtils.extractFolioNumber(documentFolioStartImage.getImageName()));
			document.setFolioMod(ImageUtils.extractFolioExtension(documentFolioStartImage.getImageName()));

			document.setTranscribeFolioNum(ImageUtils.extractFolioNumber(documentToCreateImage.getImageName()));
			document.setTranscribeFolioMod(ImageUtils.extractFolioExtension(documentToCreateImage.getImageName()));
			//document.setFolioNum(documen);
			return document;
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
	public void deletePersonDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			EpLink epLinkToDelete = getEpLinkDAO().find(epLink.getEpLinkId(), epLink.getDocument().getEntryId());
			getEpLinkDAO().remove(epLinkToDelete);
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
			EplToLink eplToLinkToDelete = getEplToLinkDAO().find(eplToLink.getDocument().getEntryId(), eplToLink.getEplToId());
			getEplToLinkDAO().remove(eplToLinkToDelete);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editCorrespondentsDocument(Document document) throws ApplicationThrowable {
		try {
			Document documentToUpdate = getDocumentDAO().find(document.getEntryId());

			// fill fields of correspondents section
			documentToUpdate.setLastUpdate(new Date());
			if (document.getSenderPeople().getPersonId() > 0)
				documentToUpdate.setSenderPeople(getPeopleDAO().find(document.getSenderPeople().getPersonId()));
			else
				documentToUpdate.setSenderPeople(null);
			documentToUpdate.setSenderPeopleUnsure(document.getSenderPeopleUnsure());
			if (document.getSenderPlace().getPlaceAllId() > 0)
				documentToUpdate.setSenderPlace(getPlaceDAO().find(document.getSenderPlace().getPlaceAllId()));
			else
				documentToUpdate.setSenderPlace(null);
			documentToUpdate.setSenderPlaceUnsure(document.getSenderPlaceUnsure());
			if (document.getRecipientPeople().getPersonId() > 0)
				documentToUpdate.setRecipientPeople(getPeopleDAO().find(document.getRecipientPeople().getPersonId()));
			else
				documentToUpdate.setRecipientPeople(null);
			documentToUpdate.setRecipientPeopleUnsure(document.getRecipientPeopleUnsure());
			if (document.getSenderPlace().getPlaceAllId() > 0)
				documentToUpdate.setRecipientPlace(getPlaceDAO().find(document.getRecipientPlace().getPlaceAllId()));
			else
				documentToUpdate.setRecipientPlace(null);
			documentToUpdate.setRecipientPlaceUnsure(document.getRecipientPlaceUnsure());
		
			return getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editDetailsDocument(Document document) throws ApplicationThrowable {
		try {
			Document documentToUpdate = getDocumentDAO().find(document.getEntryId());

			//fill fields to update document section
			documentToUpdate.setLastUpdate(new Date());
			// We need to attach the correct volume istance by database extraction.
			documentToUpdate.setVolume(getVolumeDAO().findVolume(document.getVolume().getVolNum(), document.getVolume().getVolLetExt()));
			// Insert/Part: 
			documentToUpdate.setInsertNum(document.getInsertNum());
			documentToUpdate.setInsertLet(document.getInsertLet());
			// Folio Start:
			documentToUpdate.setFolioNum(document.getFolioNum());
			documentToUpdate.setFolioMod(document.getFolioMod().toString());
			// Transcribe Folio Start:
			documentToUpdate.setTranscribeFolioNum(document.getTranscribeFolioNum());
			documentToUpdate.setTranscribeFolioMod(document.getTranscribeFolioMod().toString());
			// Paginated
			documentToUpdate.setUnpaged(document.getUnpaged());
			//Disc. Cont'd
			documentToUpdate.setContDisc(document.getContDisc());
			//Document Typology
			documentToUpdate.setDocTypology(document.getDocTypology());
			// Date
			documentToUpdate.setDocYear(document.getDocYear());
			documentToUpdate.setDocMonthNum(document.getDocMonthNum());
			documentToUpdate.setDocDay(document.getDocDay());
			//Modern Dating
			documentToUpdate.setYearModern(document.getYearModern());
			// Date Uncertain or Approximate
			documentToUpdate.setDateUns(document.getDateUns());
			// Undated
			documentToUpdate.setUndated(document.getUndated());
			documentToUpdate.setDateNotes(document.getDateNotes());

	
			return getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editExtractDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(synExtract.getDocument().getEntryId());

			SynExtract synExtractToUpdate = document.getSynExtract();

			// fill fields to update document section
			synExtractToUpdate.setLastUpdate(new Date());
			synExtractToUpdate.setDocExtract(synExtract.getDocExtract());
		
			getSynExtractDAO().merge(synExtractToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise factchecks property will be null
			getDocumentDAO().refresh(synExtractToUpdate.getDocument());

			return synExtractToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			SynExtract synExtractToUpdate = getSynExtractDAO().find(synExtract.getSynExtrId());

			// fill fields to update document section
			synExtractToUpdate.setLastUpdate(new Date());
			synExtractToUpdate.setDocExtract(synExtract.getDocExtract());
			synExtractToUpdate.setSynopsis(synExtract.getSynopsis());
		
			getSynExtractDAO().merge(synExtractToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise factchecks property will be null
			getDocumentDAO().refresh(synExtractToUpdate.getDocument());

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
		try {
			FactChecks factChecksToUpdate = getFactChecksDAO().findByEntryId(factChecks.getDocument().getEntryId());
			// fill fields to update fact check section
			factChecksToUpdate.setAddLRes(factChecks.getAddLRes());
			getFactChecksDAO().merge(factChecksToUpdate);
			
			return factChecksToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editPersonDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			EpLink epLinkToUpdate = getEpLinkDAO().find(epLink.getEpLinkId(), epLink.getDocument().getEntryId());

			// fill fields to update document section
			epLinkToUpdate.setAssignUnsure(epLink.getAssignUnsure());
			epLinkToUpdate.setPortrait(epLink.getPortrait());
			epLinkToUpdate.setPeople(epLink.getPeople());

			getEpLinkDAO().merge(epLinkToUpdate);

			return epLinkToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			SynExtract synExtractToUpdate = getSynExtractDAO().find(synExtract.getSynExtrId());

			// fill fields to update document section
			synExtractToUpdate.setLastUpdate(new Date());
			synExtractToUpdate.setSynopsis(synExtract.getSynopsis());
		
			getSynExtractDAO().merge(synExtractToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise synExtract property will be null
			getDocumentDAO().refresh(synExtractToUpdate.getDocument());

			return synExtractToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document editTopicDocument(EplToLink eplToLink) throws ApplicationThrowable {
		try {
			EplToLink eplToLinkToUpdate = getEplToLinkDAO().find(eplToLink.getEplToId());

			// fill fields to update document section
			if (eplToLink.getTopic() != null) {
				eplToLinkToUpdate.setTopic(getTopicsListDAO().find(eplToLink.getTopic().getTopicId()));				
			} else {
				eplToLinkToUpdate.setTopic(null);
			}
			if (eplToLink.getPlace() != null) {
				eplToLinkToUpdate.setPlace(getPlaceDAO().find(eplToLink.getPlace().getPlaceAllId()));
			} else {
				eplToLinkToUpdate.setPlace(null);
			}

			getEplToLinkDAO().merge(eplToLinkToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise eplToLink property will be null
			getDocumentDAO().refresh(eplToLinkToUpdate.getDocument());
			
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
	public List<Image> findDocumentImage(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws ApplicationThrowable {
		try {
			return getImageDAO().findVolumeImages(volNum, volLetExt, imageType, imageProgTypeNum);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findDocumentImages(Integer entryId) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(entryId);
			
			if (document != null) {
				// eilink not null is image linked to document
				/*if (document.getEiLink() != null) {
					return new ArrayList<Image>(0);
				} else {
					return getImageDAO().findDocumentImages(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), document.getFolioNum(), document.getFolioMod());
				}*/
				return getImageDAO().findDocumentImages(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), document.getFolioNum(), document.getFolioMod());
			} else {
				return new ArrayList<Image>(0);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImageThumbnail(Document document) throws ApplicationThrowable {
		try {
			if (document != null) {
				if ((document.getFolioNum() != null) && (document.getFolioNum() > 0)) {
					return getImageDAO().findImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt(), ImageType.C, document.getFolioNum());
				} else {
					return getImageDAO().findVolumeFirstImage(document.getVolume().getVolNum(), document.getVolume().getVolLetExt());
				}
			}
			
			return null;
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
	public Image findImage(Integer imageId) throws ApplicationThrowable {
		try {
			return getImageDAO().find(imageId);			
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
	public EpLink findPersonDocument(Integer entryId, Integer epLinkId) throws ApplicationThrowable {
		try {
			return getEpLinkDAO().find(epLinkId, entryId);
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
	public TopicList findTopic(Integer topicId) throws ApplicationThrowable {
		try {
			return getTopicsListDAO().find(topicId);
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
	 * {@inheritDoc}
	 */
	@Override
	public DocumentExplorer getDocumentExplorer(DocumentExplorer documentExplorer) throws ApplicationThrowable {
		try {
			if (documentExplorer.getVolNum() == null) {
				Document document = getDocumentDAO().find(documentExplorer.getEntryId());
				documentExplorer.setVolNum(document.getVolume().getVolNum());
				documentExplorer.setVolLetExt(document.getVolume().getVolLetExt());
				if ((documentExplorer.getImage().getImageOrder()==null) && (documentExplorer.getImage().getImageProgTypeNum()==null) && (documentExplorer.getImage().getImageName() ==null)) {
					if (document.getFolioNum() != null) {
						if (document.getFolioNum() > 0) {
							documentExplorer.getImage().setImageProgTypeNum(document.getFolioNum());
							documentExplorer.getImage().setImageType(ImageType.C);
						}
					}
				}
			}

			return getImageDAO().findImages(documentExplorer);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
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
	 * @return the imageDAO
	 */
	public ImageDAO getImageDAO() {
		return imageDAO;
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
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}

	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
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
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchPersonLinkableToDocument(Integer entryId, String query) throws ApplicationThrowable {
		try {
			List<EpLink> epLinkList = getEpLinkDAO().findByEntryId(entryId);
			
			return getPeopleDAO().searchPersonLinkableToDocument(EpLinkUtils.getPeopleIdList(epLinkList), RegExUtils.splitPunctuationChars(query));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Place> searchPlaceLinkableToTopicDocument(Integer entryId, String query) throws ApplicationThrowable {
		try {
			return getPlaceDAO().searchPlaceLinkableToTopicDocument(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TopicList> searchTopicLinkableToDocument(Integer entryId, String query) throws ApplicationThrowable {
		try {
			// TODO: Can we have a topic defined multiple times? List<EplToLink> eplToLinkList = getEplToLinkDAO().findByEntryId(entryId);
			List<EplToLink> eplToLinkList = new ArrayList<EplToLink>(0);
			
			return getTopicsListDAO().searchTopicLinkableToDocument(EplToLinkUtils.getTopicIdList(eplToLinkList), query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page simpleSearchDocuments(String text, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getDocumentDAO().simpleSearchDocuments(text, paginationFilter);
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
	 * @param imageDAO the imageDAO to set
	 */
	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
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
