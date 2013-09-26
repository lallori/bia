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
package org.medici.bia.service.docbase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.EpLinkUtils;
import org.medici.bia.common.util.EplToLinkUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.dao.docreference.DocReferenceDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.eplink.EpLinkDAO;
import org.medici.bia.dao.epltolink.EplToLinkDAO;
import org.medici.bia.dao.factchecks.FactChecksDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumoption.ForumOptionDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.synextract.SynExtractDAO;
import org.medici.bia.dao.topicslist.TopicsListDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermarkedlist.UserMarkedListDAO;
import org.medici.bia.dao.usermarkedlistelement.UserMarkedListElementDAO;
import org.medici.bia.dao.vettinghistory.VettingHistoryDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.DocReference;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.EpLink;
import org.medici.bia.domain.EplToLink;
import org.medici.bia.domain.FactChecks;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumOption;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.SynExtract;
import org.medici.bia.domain.TopicList;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.domain.VettingHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.security.BiaUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on document.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class DocBaseServiceImpl implements DocBaseService {
	@Autowired
	private DocumentDAO documentDAO;

	@Autowired
	private DocReferenceDAO docReferenceDAO;
	@Autowired
	private EpLinkDAO epLinkDAO;
	@Autowired
	private EplToLinkDAO eplToLinkDAO;
	@Autowired
	private FactChecksDAO factChecksDAO;
	@Autowired
	private ForumDAO forumDAO;
	@Autowired
	private ForumOptionDAO forumOptionDAO;
	@Autowired
	private ImageDAO imageDAO;
	private final Logger logger = Logger.getLogger(this.getClass());
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
	private UserDAO userDAO;
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	@Autowired
	private UserMarkedListDAO userMarkedListDAO;
	@Autowired
	private UserMarkedListElementDAO userMarkedListElementDAO;
	@Autowired
	private VolumeDAO volumeDAO;
	@Autowired
	private VettingHistoryDAO vettingHistoryDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document addNewDocReferenceDocument(DocReference docReference) throws ApplicationThrowable {
		try{
			docReference.setDocReferenceId(null);
			docReference.setDateCreated(new Date());
			docReference.setDocumentFrom(getDocumentDAO().find(docReference.getDocumentFrom().getEntryId()));
			docReference.setDocumentTo(getDocumentDAO().find(docReference.getDocumentTo().getEntryId()));
			getDocReferenceDAO().persist(docReference);
			DocReference docReferenceInverse = new DocReference(null);
			docReferenceInverse.setDateCreated(new Date());
			docReferenceInverse.setDocumentFrom(getDocumentDAO().find(docReference.getDocumentTo().getEntryId()));
			docReferenceInverse.setDocumentTo(getDocumentDAO().find(docReference.getDocumentFrom().getEntryId()));
			getDocReferenceDAO().persist(docReferenceInverse);
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new Document Reference", Action.MODIFY, Category.DOCUMENT, docReference.getDocumentFrom()));
			
			return docReference.getDocumentFrom();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document addNewDocument(Document document) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			document.setEntryId(null);

			// We need to attach the correct volume istance by database extraction.
			document.setVolume(getVolumeDAO().findVolume(document.getVolume().getVolNum(), document.getVolume().getVolLetExt()));
			//Setting fields that are defined as nullable = false
			document.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			document.setCreatedBy(user);
			Date dateCreated = new Date();
			document.setDateCreated(dateCreated);
			document.setLastUpdate(dateCreated);
			document.setLastUpdateBy(user);
			document.setNewEntry(true);
			document.setReckoning(false);
			document.setSenderPeopleUnsure(false);
			document.setSenderPlaceUnsure(false);
			document.setRecipientPeopleUnsure(false);
			document.setRecipientPlaceUnsure(false);
			document.setGraphic(false);
			document.setLogicalDelete(false);

			if (document.getDocMonthNum() != null) {
				Month month = getMonthDAO().find(document.getDocMonthNum().getMonthNum());
				document.setDocMonthNum(month);
			} else {
				document.setDocMonthNum(null);
			}
			document.setDocDate(DateUtils.getLuceneDate(document.getDocYear(), document.getDocMonthNum(), document.getDocDay()));
			
			//We set the sortableDateInt (and the sortableDate that it's used only in the old DB)
			if(document.getYearModern() != null){
				document.setSortableDateInt(DateUtils.getIntegerDate(document.getYearModern(), document.getDocMonthNum(), document.getDocDay()));
				document.setSortableDate(DateUtils.getStringForSortableDate(document.getYearModern(), document.getDocMonthNum(), document.getDocDay()));
			}else{
				document.setSortableDateInt(DateUtils.getIntegerDate(document.getDocYear(), document.getDocMonthNum(), document.getDocDay()));
				document.setSortableDate(DateUtils.getStringForSortableDate(document.getDocYear(), document.getDocMonthNum(), document.getDocDay()));
			}

			// We set InsertLet to null if it's an empty string. 
			if (ObjectUtils.toString(document.getInsertLet()).trim().equals("")){
				document.setInsertLet(null);
			} else {
				document.setInsertLet(document.getInsertLet());
			}
			// We set FolioMod to null if it's an empty string. 
			if (ObjectUtils.toString(document.getFolioMod()).trim().equals("")) {
				document.setFolioMod(null);
			}
			// We set FolioRV to null if it's an empty string. 
			if (ObjectUtils.toString(document.getFolioRectoVerso()).trim().equals("")) {
				document.setFolioRectoVerso(null);
			}

			getDocumentDAO().persist(document);

			getUserHistoryDAO().persist(new UserHistory(user, "Create document", Action.CREATE, Category.DOCUMENT, document));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Create document", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, document));

			return document;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Forum addNewDocumentForum(Document document) throws ApplicationThrowable {
		try {
			//this control is mandatory to prevent duplication records on forum
			Forum forum = getForumDAO().getForumDocument(document.getEntryId());
			
			if (forum == null) {
				document = getDocumentDAO().find(document.getEntryId());
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.document")));
				forum = getForumDAO().addNewDocumentForum(parentForum, document);

				forum.setFullPath(parentForum.getFullPath() + forum.getForumId() + ".");
				getForumDAO().merge(forum);

				ForumOption forumOption = new ForumOption(forum);
				forumOption.setGroupBySubForum(Boolean.TRUE);
				forumOption.setCanHaveTopics(Boolean.TRUE);
				forumOption.setCanDeletePosts(Boolean.TRUE);
				forumOption.setCanDeleteTopics(Boolean.TRUE);
				forumOption.setCanEditPosts(Boolean.TRUE);
				forumOption.setCanPostReplys(Boolean.TRUE);
				getForumOptionDAO().persist(forumOption);

				// thisi method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);

				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.FORUM, forum));
			}else if(forum.getLogicalDelete()){
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.document")));
				forum.setLogicalDelete(Boolean.FALSE);
				forum.setTotalViews(0);
				forum.setLastUpdate(new Date());
				getForumDAO().merge(forum);
				
				// this method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);
				
				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.FORUM, forum));
			}

			return forum;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document addNewExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			synExtract.setSynExtrId(null);
			Date dateCreated = new Date();
			synExtract.setDateCreated(dateCreated);
			synExtract.setLastUpdate(dateCreated);
			synExtract.setDocument(getDocumentDAO().find(synExtract.getDocument().getEntryId()));
			getSynExtractDAO().persist(synExtract);

			// We need to refresh linked document entity state, otherwise synExtract property will be null
			getDocumentDAO().refresh(synExtract.getDocument());

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new extract", Action.MODIFY, Category.DOCUMENT, synExtract.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add new extract", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, synExtract.getDocument()));

			return synExtract.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document addNewFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable {
		try {
			factChecks.setVetId(null);
			factChecks.setDateInfo((new Date()).toString());
			factChecks.setDocument(getDocumentDAO().find(factChecks.getDocument().getEntryId()));
			getFactChecksDAO().persist(factChecks);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new fact checks", Action.MODIFY, Category.DOCUMENT, factChecks.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add new fact checks", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, factChecks.getDocument()));

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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document addNewPersonDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			epLink.setEpLinkId(null);
			epLink.setDateCreated(new Date());
			
			epLink.setDocument(getDocumentDAO().find(epLink.getDocument().getEntryId()));
			epLink.setPerson(getPeopleDAO().find(epLink.getPerson().getPersonId()));

			getEpLinkDAO().persist(epLink);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new person", Action.MODIFY, Category.DOCUMENT, epLink.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add new person", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, epLink.getDocument()));

			return epLink.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
				if(eplToLink.getPlace().getPrefFlag().equals("V")){
					eplToLink.setPlace(getPlaceDAO().findPrinicipalPlace(eplToLink.getPlace().getGeogKey()));
				}
			} else {
				eplToLink.setPlace(null);
			}

			getEplToLinkDAO().persist(eplToLink);

			// We need to refresh linked document entity state, otherwise eplToLink property will be null
			getDocumentDAO().refresh(eplToLink.getDocument());

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new topic", Action.MODIFY, Category.DOCUMENT, eplToLink.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add new topic", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, eplToLink.getDocument()));

			return eplToLink.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkDocumentDigitized(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws ApplicationThrowable {
		Boolean digitized = Boolean.FALSE;
		try {
			Image firstImage = getImageDAO().findDocumentImage(volNum, volLetExt, folioNum, folioMod);
			if (firstImage != null) {
				digitized = Boolean.TRUE;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return digitized;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkFolio(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws ApplicationThrowable {
		Image.ImageRectoVerso rv = (rectoVerso != null && !"".equals(rectoVerso.trim())) ? 
			("R".equals(rectoVerso.trim().toUpperCase()) ? Image.ImageRectoVerso.R : Image.ImageRectoVerso.V) : null;
		if (rv != null) {
			try {
				return getImageDAO().findImage(volNum, volLetExt, null, insertNum, insertLet, folioNum, folioMod, rv) != null;
			} catch (Throwable th) {
				throw new ApplicationThrowable(th);
			}
		}
		return Boolean.FALSE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean checkInsert(Integer volNum, String volLetExt,
			String insertNum, String insertLet) throws ApplicationThrowable {
		try {
			return getImageDAO().findImages(volNum, volLetExt, insertNum, insertLet).size() > 0;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	@Override
	public Document checkVolumeFolio(Integer summaryId)	throws ApplicationThrowable {
		try{
			return getDocumentDAO().checkVolumeFolio(summaryId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document compareDocument(Integer entryId) throws ApplicationThrowable {
		try {
			Document document = getDocumentDAO().find(entryId);
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Compare document", Action.COMPARE, Category.DOCUMENT, document));

			return document;
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
			User user = getUserDAO().findUser(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());

			Document document = new Document();
			// New Document must have entryId set to zero
			document.setEntryId(0);
			document.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			document.setCreatedBy(user);
			Date dateCreated = new Date();
			document.setDateCreated(dateCreated);
			document.setLastUpdate(dateCreated);
			document.setLastUpdateBy(user);
			Image documentToCreateImage = getImageDAO().find(imageDocumentToCreate);
			Image documentFolioStartImage = getImageDAO().find(imageDocumentFolioStart);
			document.setVolume(getVolumeDAO().findVolume(documentToCreateImage.getVolNum(), documentToCreateImage.getVolLetExt()));
			document.setSubVol(documentToCreateImage.getVolLetExt());
			document.setFolioNum(documentFolioStartImage.getImageProgTypeNum());
			document.setFolioMod(documentFolioStartImage.getMissedNumbering());
			document.setFolioRectoVerso(Document.RectoVerso.convertFromString(documentFolioStartImage.getImageRectoVerso() != null ? documentFolioStartImage.getImageRectoVerso().toString() : null));
			
			if (documentToCreateImage != null) {
				document.setInsertNum(documentToCreateImage.getInsertNum());
				document.setInsertLet(documentToCreateImage.getInsertLet());
			}

			document.setTranscribeFolioNum(documentToCreateImage.getImageProgTypeNum());
			document.setTranscribeFolioMod(documentToCreateImage.getMissedNumbering());
			document.setTranscribeFolioRectoVerso(Document.RectoVerso.convertFromString(documentToCreateImage.getImageRectoVerso() != null ? documentToCreateImage.getImageRectoVerso().toString() : null));
			
			return document;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteDocReferenceDocument(DocReference docReference) throws ApplicationThrowable {
		try{
			DocReference docReferenceToDeleteFirst = getDocReferenceDAO().find(docReference.getDocumentFrom().getEntryId(), docReference.getDocReferenceId());
			DocReference docReferenceToDeleteSecond = getDocReferenceDAO().findFromDocuments(docReferenceToDeleteFirst.getDocumentTo().getEntryId(), docReferenceToDeleteFirst.getDocumentFrom().getEntryId());
			
			docReferenceToDeleteFirst.getDocumentFrom().setDocReference(null);
			docReferenceToDeleteFirst.getDocumentTo().setDocReference(null);
			
			getDocReferenceDAO().remove(docReferenceToDeleteFirst);
			
			docReferenceToDeleteSecond.getDocumentFrom().setDocReference(null);
			docReferenceToDeleteSecond.getDocumentTo().setDocReference(null);
			
			getDocReferenceDAO().remove(docReferenceToDeleteSecond);			
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Unlink document ", Action.MODIFY, Category.DOCUMENT, docReferenceToDeleteFirst.getDocumentFrom()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Unlink document", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, docReferenceToDeleteFirst.getDocumentFrom()));
		}catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document deleteDocument(Integer entryId) throws ApplicationThrowable {
		Document documentToDelete = null;
		try {
			documentToDelete = getDocumentDAO().find(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		documentToDelete.setLogicalDelete(Boolean.TRUE);

		try {
			getDocumentDAO().merge(documentToDelete);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Deleted document", Action.DELETE, Category.DOCUMENT, documentToDelete));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Deleted document", org.medici.bia.domain.VettingHistory.Action.DELETE, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, documentToDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return documentToDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deletePersonDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			EpLink epLinkToDelete = getEpLinkDAO().find(epLink.getEpLinkId(), epLink.getDocument().getEntryId());

			epLinkToDelete.getDocument().setEpLink(null);
			epLinkToDelete.getPerson().setEpLink(null);
			getEpLinkDAO().remove(epLinkToDelete);
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Unlink person ", Action.MODIFY, Category.DOCUMENT, epLink.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Unlink person", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, epLink.getDocument()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteTopicDocument(EplToLink eplToLink) throws ApplicationThrowable {
		try {
			EplToLink eplToLinkToDelete = getEplToLinkDAO().find(eplToLink.getDocument().getEntryId(), eplToLink.getEplToId());
			getEplToLinkDAO().remove(eplToLinkToDelete);
			// LP : We need to remove the association between parent and child first, 
			// otherwise Hibernate tries to persist the child again due to cascade = ALL
			// Tnx to http://stackoverflow.com/questions/4748426/cannot-remove-entity-which-is-target-of-onetoone-relation
			eplToLinkToDelete.getDocument().setEplToLink(null);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Unlink topic", Action.MODIFY, Category.DOCUMENT, eplToLink.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Unlink topic", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, eplToLink.getDocument()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editCorrespondentsDocument(Document document) throws ApplicationThrowable {
		try {
			Document documentToUpdate = getDocumentDAO().find(document.getEntryId());

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			// fill fields of correspondents section
			documentToUpdate.setLastUpdate(new Date());
			document.setLastUpdateBy(user);
			if ((!document.getSenderPeople().getMapNameLf().equals("")) && (document.getSenderPeople().getPersonId() >0)){
				People sender = getPeopleDAO().find(document.getSenderPeople().getPersonId());
				documentToUpdate.setSenderPeople(sender);
				if(document.getSenderPeople().getPersonId() != 198 && document.getSenderPeople().getPersonId() != 3905 && document.getSenderPeople().getPersonId() != 9285){
					EpLink epLinkSender = getEpLinkDAO().findByEntryIdAndRole(document.getEntryId(), "S");
					if(epLinkSender == null){
						epLinkSender = new EpLink(null);
						epLinkSender.setDateCreated(new Date());
						epLinkSender.setDocRole("S");
						epLinkSender.setDocument(documentToUpdate);
						epLinkSender.setPerson(getPeopleDAO().find(sender.getPersonId()));
						epLinkSender.setAssignUnsure(false);
						epLinkSender.setPortrait(false);
						getEpLinkDAO().persist(epLinkSender);
					}else{
						epLinkSender.setPerson(sender);
						getEpLinkDAO().merge(epLinkSender);
					}
				}
			} else {
				if ((document.getSenderPeople().getPersonId() >0)) {
					// We need to remove epLink before setting null sender...
					EpLink epLinkToDelete = getEpLinkDAO().findByEntryIdAndPersonId(documentToUpdate.getEntryId(), document.getSenderPeople().getPersonId());
					if(epLinkToDelete != null){
						epLinkToDelete.getDocument().setEpLink(null);
						epLinkToDelete.getPerson().setEpLink(null);
						getEpLinkDAO().remove(epLinkToDelete);

						getUserHistoryDAO().persist(new UserHistory(user, "Unlink person", Action.MODIFY, Category.DOCUMENT, epLinkToDelete.getDocument()));
						getVettingHistoryDAO().persist(new VettingHistory(user, "Unlink person", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, epLinkToDelete.getDocument()));
					}
					documentToUpdate.setSenderPeople(null);
					documentToUpdate.setSenderPeopleUnsure(Boolean.FALSE);
				}else{
					document.setSenderPeople(null);
				}
			}
				
			documentToUpdate.setSenderPeopleUnsure(document.getSenderPeopleUnsure());
			if (document.getSenderPlace().getPlaceAllId() > 0){
				/*
				if(documentToUpdate.getSenderPlace() != null){
					documentToUpdate.getSenderPlace().setSenderDocuments(null);
				}*/
				documentToUpdate.setSenderPlace(getPlaceDAO().find(document.getSenderPlace().getPlaceAllId()));
				if(documentToUpdate.getSenderPlace().getPrefFlag().equals("V")){
					documentToUpdate.setSenderPlace(getPlaceDAO().findPrinicipalPlace(documentToUpdate.getSenderPlace().getGeogKey()));
				}
			}
			else
				documentToUpdate.setSenderPlace(null);
			documentToUpdate.setSenderPlaceUnsure(document.getSenderPlaceUnsure());
			if(document.getSendNotes() != null){
				documentToUpdate.setSendNotes(document.getSendNotes());
			}
			
			if ((!document.getRecipientPeople().getMapNameLf().equals(""))  && (document.getRecipientPeople().getPersonId() >0)) { 
				People recipient = getPeopleDAO().find(document.getRecipientPeople().getPersonId());
				documentToUpdate.setRecipientPeople(recipient);
				if(document.getRecipientPeople().getPersonId() != 198 && document.getRecipientPeople().getPersonId() != 3905 && document.getRecipientPeople().getPersonId() != 9285){
					EpLink epLinkSender = getEpLinkDAO().findByEntryIdAndRole(document.getEntryId(), "R");
					if(epLinkSender == null){
						epLinkSender = new EpLink(null);
						epLinkSender.setDateCreated(new Date());
						epLinkSender.setDocRole("R");
						epLinkSender.setDocument(documentToUpdate);
						epLinkSender.setPerson(getPeopleDAO().find(recipient.getPersonId()));
						epLinkSender.setAssignUnsure(false);
						epLinkSender.setPortrait(false);
						getEpLinkDAO().persist(epLinkSender);
					}else{
						epLinkSender.setPerson(recipient);
						getEpLinkDAO().merge(epLinkSender);
					}
				}
			} else {
				if (document.getRecipientPeople().getPersonId() >0) {
					// We need to remove epLink before setting null recipient...
					EpLink epLinkToDelete = getEpLinkDAO().findByEntryIdAndPersonId(documentToUpdate.getEntryId(), document.getRecipientPeople().getPersonId());
					if(epLinkToDelete != null){
						epLinkToDelete.getDocument().setEpLink(null);
						epLinkToDelete.getPerson().setEpLink(null);
						getEpLinkDAO().remove(epLinkToDelete);

						getUserHistoryDAO().persist(new UserHistory(user, "Unlink person ", Action.MODIFY, Category.DOCUMENT, epLinkToDelete.getDocument()));
						getVettingHistoryDAO().persist(new VettingHistory(user, "Unlink person", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, epLinkToDelete.getDocument()));
					}
					documentToUpdate.setRecipientPeople(null);
					documentToUpdate.setRecipientPeopleUnsure(Boolean.FALSE);
				}else{
					document.setRecipientPeople(null);
				}
			}

			documentToUpdate.setRecipientPeopleUnsure(document.getRecipientPeopleUnsure());
			if (document.getRecipientPlace().getPlaceAllId() > 0){
				/*
				if(documentToUpdate.getRecipientPlace() != null){
					documentToUpdate.getRecipientPlace().setRecipientDocuments(null);
				}*/
				documentToUpdate.setRecipientPlace(getPlaceDAO().find(document.getRecipientPlace().getPlaceAllId()));
				if(documentToUpdate.getRecipientPlace().getPrefFlag().equals("V")){
					documentToUpdate.setRecipientPlace(getPlaceDAO().findPrinicipalPlace(documentToUpdate.getRecipientPlace().getGeogKey()));
				}
			} else {
				documentToUpdate.setRecipientPlace(null);
			}
			documentToUpdate.setRecipientPlaceUnsure(document.getRecipientPlaceUnsure());
			if(document.getRecipNotes() != null){
				documentToUpdate.setRecipNotes(document.getRecipNotes());
			}
		
			getUserHistoryDAO().persist(new UserHistory(user, "Edit Correspondents", Action.MODIFY, Category.DOCUMENT, documentToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit Correspondents", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, documentToUpdate));

			return getDocumentDAO().merge(documentToUpdate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editDetailsDocument(Document document) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			Document documentToUpdate = getDocumentDAO().find(document.getEntryId());

			//Update the title of the linked forum if exist
//			Forum forum = getForumDAO().getForumDocument(document.getEntryId());
//			Boolean changeTitleForum = Boolean.FALSE;
//			if(forum != null){
//				if((document.getVolume().getVolNum() != documentToUpdate.getVolume().getVolNum()) || (document.getVolume().getVolLetExt() != documentToUpdate.getVolume().getVolLetExt()) || (document.getFolioNum() != documentToUpdate.getFolioNum()) || (document.getFolioMod().equals("") && documentToUpdate.getFolioMod() != null) || (!document.getFolioMod().equals(documentToUpdate.getFolioMod()))){
//					changeTitleForum = Boolean.TRUE;
//				}
//			}
			
			//fill fields to update document section
			documentToUpdate.setLastUpdate(new Date());
			documentToUpdate.setLastUpdateBy(user);
			// We need to attach the correct volume istance by database extraction.
			documentToUpdate.setVolume(getVolumeDAO().findVolume(document.getVolume().getVolNum(), document.getVolume().getVolLetExt()));
			// Insert/Part: 
			documentToUpdate.setInsertNum(StringUtils.nullTrim(document.getInsertNum()));
			documentToUpdate.setInsertLet(StringUtils.nullTrim(document.getInsertLet()));
			// Folio Start:
			documentToUpdate.setFolioNum(document.getFolioNum());
			documentToUpdate.setFolioMod(StringUtils.nullTrim(document.getFolioMod()));
			documentToUpdate.setFolioRectoVerso(document.getFolioRectoVerso());
			// Transcribe Folio Start:
			documentToUpdate.setTranscribeFolioNum(document.getTranscribeFolioNum());
			documentToUpdate.setTranscribeFolioMod(StringUtils.nullTrim(document.getTranscribeFolioMod()));
			documentToUpdate.setTranscribeFolioRectoVerso(document.getTranscribeFolioRectoVerso());
			// Paginated
			documentToUpdate.setUnpaged(document.getUnpaged());
			//Disc. Cont'd
			documentToUpdate.setContDisc(document.getContDisc());
			//Document Typology
			documentToUpdate.setDocTypology(document.getDocTypology());
			// Date
			documentToUpdate.setDocYear(document.getDocYear());
			if (document.getDocMonthNum() != null) {
				Month month = getMonthDAO().find(document.getDocMonthNum().getMonthNum());
				documentToUpdate.setDocMonthNum(month);
			} else {
				documentToUpdate.setDocMonthNum(null);
			}
			documentToUpdate.setDocDay(document.getDocDay());
			documentToUpdate.setDocDate(DateUtils.getLuceneDate(documentToUpdate.getDocYear(), documentToUpdate.getDocMonthNum(), documentToUpdate.getDocDay()));
			
			//Modern Dating
			documentToUpdate.setYearModern(document.getYearModern());
			
			//Set SortableDateInt (and the sortableDate that it's used only in the old DB)
			if(documentToUpdate.getYearModern() != null){
				documentToUpdate.setSortableDateInt(DateUtils.getIntegerDate(documentToUpdate.getYearModern(), documentToUpdate.getDocMonthNum(), documentToUpdate.getDocDay()));
				documentToUpdate.setSortableDate(DateUtils.getStringForSortableDate(documentToUpdate.getYearModern(), documentToUpdate.getDocMonthNum(), documentToUpdate.getDocDay()));
			}else{
				documentToUpdate.setSortableDateInt(DateUtils.getIntegerDate(documentToUpdate.getDocYear(), documentToUpdate.getDocMonthNum(), documentToUpdate.getDocDay()));
				documentToUpdate.setSortableDate(DateUtils.getStringForSortableDate(documentToUpdate.getDocYear(), documentToUpdate.getDocMonthNum(), documentToUpdate.getDocDay()));
			}
			
			// Date Uncertain or Approximate
			documentToUpdate.setDateUns(document.getDateUns());
			// Undated
			documentToUpdate.setUndated(document.getUndated());
			documentToUpdate.setDateNotes(document.getDateNotes());

			getDocumentDAO().merge(documentToUpdate);
			
			//Update the title of the linked forum if exist
			Forum forum = getForumDAO().getForumDocument(document.getEntryId());
			if(forum != null){
				if(documentToUpdate.getFolioNum() == null){
					forum.setDescription("Volume " + documentToUpdate.getVolume().getMDP() + " - Folio NNF");
				}else if(documentToUpdate.getFolioMod() != null){
					forum.setDescription("Volume " + documentToUpdate.getVolume().getMDP() + " - Folio " + documentToUpdate.getFolioNum() + documentToUpdate.getFolioMod());
				}else{
					forum.setDescription("Volume " + documentToUpdate.getVolume().getMDP() + " - Folio " + documentToUpdate.getFolioNum());
				}
				getForumDAO().merge(forum);
			}
			
			getUserHistoryDAO().persist(new UserHistory(user, "Edit Details", Action.MODIFY, Category.DOCUMENT, documentToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit Details", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, documentToUpdate));
		
			return documentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editExtractDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			Document documentToUpdate = getDocumentDAO().find(synExtract.getDocument().getEntryId());
			documentToUpdate.setLastUpdate(new Date());
			documentToUpdate.setLastUpdateBy(user);
			getDocumentDAO().merge(documentToUpdate);
						
			SynExtract synExtractToUpdate = documentToUpdate.getSynExtract();

			// fill fields to update document section
			synExtractToUpdate.setLastUpdate(new Date());
			synExtractToUpdate.setDocExtract(synExtract.getDocExtract());
		
			getSynExtractDAO().merge(synExtractToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise factchecks property will be null
			getDocumentDAO().refresh(synExtractToUpdate.getDocument());

			getUserHistoryDAO().persist(new UserHistory(user, "Edit extract", Action.MODIFY, Category.DOCUMENT, synExtractToUpdate.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit extract", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, synExtractToUpdate.getDocument()));
			
			return synExtractToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editExtractOrSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			Document documentToUpdate = getDocumentDAO().find(synExtract.getDocument().getEntryId());
			documentToUpdate.setLastUpdate(new Date());
			documentToUpdate.setLastUpdateBy(user);
			getDocumentDAO().merge(documentToUpdate);

			SynExtract synExtractToUpdate = getSynExtractDAO().find(synExtract.getSynExtrId());

			// fill fields to update document section
			synExtractToUpdate.setLastUpdate(new Date());
			synExtractToUpdate.setDocExtract(synExtract.getDocExtract());
			synExtractToUpdate.setSynopsis(synExtract.getSynopsis());
		
			getSynExtractDAO().merge(synExtractToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise factchecks property will be null
			getDocumentDAO().refresh(synExtractToUpdate.getDocument());

			getUserHistoryDAO().persist(new UserHistory(user, "Edit extract or synopsis", Action.MODIFY, Category.DOCUMENT, synExtractToUpdate.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit extract or synopsis", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, synExtractToUpdate.getDocument()));
			
			return synExtractToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editFactChecksDocument(FactChecks factChecks) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			Document documentToUpdate = getDocumentDAO().find(factChecks.getDocument().getEntryId());
			documentToUpdate.setLastUpdate(new Date());
			documentToUpdate.setLastUpdateBy(user);
			getDocumentDAO().merge(documentToUpdate);

			FactChecks factChecksToUpdate = getFactChecksDAO().findByEntryId(factChecks.getDocument().getEntryId());
			// fill fields to update fact check section
			factChecksToUpdate.setAddLRes(factChecks.getAddLRes());
			getFactChecksDAO().merge(factChecksToUpdate);
			
			getUserHistoryDAO().persist(new UserHistory(user, "Edit fact checks", Action.MODIFY, Category.DOCUMENT, factChecksToUpdate.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit fact checks", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, factChecksToUpdate.getDocument()));

			return factChecksToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editPersonDocument(EpLink epLink) throws ApplicationThrowable {
		try {
			EpLink epLinkToUpdate = getEpLinkDAO().find(epLink.getEpLinkId(), epLink.getDocument().getEntryId());

			// fill fields to update document section
			epLinkToUpdate.setAssignUnsure(epLink.getAssignUnsure());
			epLinkToUpdate.setPortrait(epLink.getPortrait());
			epLinkToUpdate.setPerson(epLink.getPerson());

			getEpLinkDAO().merge(epLinkToUpdate);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit person linked", Action.MODIFY, Category.DOCUMENT, epLinkToUpdate.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit person linked", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, epLinkToUpdate.getDocument()));

			return epLinkToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document editSynopsisDocument(SynExtract synExtract) throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			Document documentToUpdate = getDocumentDAO().find(synExtract.getDocument().getEntryId());
			documentToUpdate.setLastUpdate(new Date());
			documentToUpdate.setLastUpdateBy(user);
			getDocumentDAO().merge(documentToUpdate);

			SynExtract synExtractToUpdate = getSynExtractDAO().find(synExtract.getSynExtrId());

			// fill fields to update document section
			synExtractToUpdate.setLastUpdate(new Date());
			synExtractToUpdate.setSynopsis(synExtract.getSynopsis());
		
			getSynExtractDAO().merge(synExtractToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise synExtract property will be null
			getDocumentDAO().refresh(synExtractToUpdate.getDocument());

			getUserHistoryDAO().persist(new UserHistory(user, "Edit synopsis", Action.MODIFY, Category.DOCUMENT, synExtractToUpdate.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit synopsis", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, synExtractToUpdate.getDocument()));

			return synExtractToUpdate.getDocument();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
				if(eplToLinkToUpdate.getPlace().getPrefFlag().equals("V")){
					eplToLinkToUpdate.setPlace(getPlaceDAO().findPrinicipalPlace(eplToLinkToUpdate.getPlace().getGeogKey()));
				}
			} else {
				eplToLinkToUpdate.setPlace(null);
			}

			getEplToLinkDAO().merge(eplToLinkToUpdate);

			// We need to refresh linked document to refresh entity state, otherwise eplToLink property will be null
			getDocumentDAO().refresh(eplToLinkToUpdate.getDocument());
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit topic linked", Action.MODIFY, Category.DOCUMENT, eplToLinkToUpdate.getDocument()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit topic linked", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.DOCUMENT, eplToLinkToUpdate.getDocument()));

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
	public DocReference findDocReferenceDocument(Integer entryIdFrom, Integer docReferenceId) throws ApplicationThrowable {
		try{
			return getDocReferenceDAO().find(entryIdFrom, docReferenceId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocument(Integer entryId) throws ApplicationThrowable { 
		try {
			Document document = getDocumentDAO().find(entryId);
			
			User user;			
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Show document", Action.VIEW, Category.DOCUMENT, document));
			}

			return document;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	@Override
	public List<Document> findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws ApplicationThrowable {
		try{
			return getDocumentDAO().findDocument(volNum, volLetExt, folioNum, folioMod);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	@Override
	public List<Document> findDocument(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, Document.RectoVerso folioRectoVerso) throws ApplicationThrowable {
		try{
			return getDocumentDAO().findDocument(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, folioRectoVerso);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocumentFromHistory(Integer idUserHistory) throws ApplicationThrowable {
		try{
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			return userHistory.getDocument();
		}catch(Throwable th){
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
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findLastEntry(user, Category.DOCUMENT);
			
			if (userHistory != null) {
				return userHistory.getDocument();
			}
			
			// in case of no user History we extract last document created on database.
			return null;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfPeopleLinkedOnDocument(Integer entryId) throws ApplicationThrowable {
		try {
			return getEpLinkDAO().findNumberOfPeopleLinkedOnDocument(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	@Override
	public Integer findNumberOfTopicsOnDocument(Integer entryId) throws ApplicationThrowable {
		try {
			return getEplToLinkDAO().findNumberOfTopicsByDocument(entryId);
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexTopicList() throws ApplicationThrowable {
		try {
			getTopicsListDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getCategoryHistoryNavigator(Document document) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));
			UserHistory userHistory = getUserHistoryDAO().findCategoryHistoryFromEntity(user, Category.DOCUMENT, document.getEntryId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * @return the docReferenceDAO
	 */
	public DocReferenceDAO getDocReferenceDAO() {
		return docReferenceDAO;
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
	public Forum getDocumentForum(Integer entryId) throws ApplicationThrowable {
		try{
			return getForumDAO().getForumDocument(entryId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, Boolean> getDocumentsDigitizedState(List<Document> documents) throws ApplicationThrowable {
		Map<String, Boolean> retValue = new HashMap<String, Boolean>();
		try {
			for(Document document : documents)
				retValue.put(DocumentUtils.toMDPInsertFolioFormat(document), Boolean.FALSE);
			
			List<String> documentsDigitized = getImageDAO().findDigitizedDocumentsFromImages(documents);
			
			for(String documentString : documentsDigitized)
				retValue.put(documentString, Boolean.TRUE);
			
			return retValue;
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
	 * @return the forumDAO
	 */
	public ForumDAO getForumDAO() {
		return forumDAO;
	}

	/**
	 * @return the forumOptionDAO
	 */
	public ForumOptionDAO getForumOptionDAO() {
		return forumOptionDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getHistoryNavigator(Document document) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.DOCUMENT, document.getEntryId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromDocument(user, userHistory.getIdUserHistory(), document.getEntryId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromDocument(user, userHistory.getIdUserHistory(), document.getEntryId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));
		}catch(Throwable th){
			logger.error(th);
		}
		
		return historyNavigator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getHistoryNavigator(Integer idUserHistory, Document document) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromDocument(userHistory.getUser(), userHistory.getIdUserHistory(), document.getEntryId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromDocument(userHistory.getUser(), userHistory.getIdUserHistory(), document.getEntryId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getHistoryNavigator(UserHistory historyLog) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			UserHistory userHistory = getUserHistoryDAO().find(historyLog.getIdUserHistory());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousCategoryHistoryCursor(userHistory.getUser(), userHistory.getCategory(), userHistory.getIdUserHistory());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextCategoryHistoryCursor(userHistory.getUser(), userHistory.getCategory(), userHistory.getIdUserHistory());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));
		}catch(Throwable th){
			logger.error(th);
		}
		
		return historyNavigator;
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
			List<Month> months = getMonthDAO().getAllMonths();
			
			months.add(0, new Month(null, ""));
			
			return months;
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
	 * {@inheritDoc}
	 */
	@Override
	public List<TopicList> getTopicsList() throws ApplicationThrowable {
		try{
			return getTopicsListDAO().findTopicsListForUsers();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the topicsListDAO
	 */
	public TopicsListDAO getTopicsListDAO() {
		return topicsListDAO;
	}

	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}
	
	/**
	 * @return the userHistoryDAO
	 */
	public UserHistoryDAO getUserHistoryDAO() {
		return userHistoryDAO;
	}

	/**
	 * @return the userMarkedListDAO
	 */
	public UserMarkedListDAO getUserMarkedListDAO() {
		return userMarkedListDAO;
	}

	/**
	 * @return the userMarkedListElementDAO
	 */
	public UserMarkedListElementDAO getUserMarkedListElementDAO() {
		return userMarkedListElementDAO;
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}

	/**
	 * @param vettingHistoryDAO the vettingHistoryDAO to set
	 */
	public void setVettingHistoryDAO(VettingHistoryDAO vettingHistoryDAO) {
		this.vettingHistoryDAO = vettingHistoryDAO;
	}
	/**
	 * @return the vettingHistoryDAO
	 */
	public VettingHistoryDAO getVettingHistoryDAO() {
		return vettingHistoryDAO;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean ifDocumentAlreadyPresentInMarkedList(Integer entryId) throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserMarkedList userMarkedList = getUserMarkedListDAO().getMyMarkedList(user);

			if(userMarkedList == null){
				return Boolean.FALSE;
			}
			
			UserMarkedListElement userMarkedListElement = getUserMarkedListElementDAO().findDocumentInMarkedList(userMarkedList.getIdMarkedList(), entryId);
			if(userMarkedListElement != null){
				return Boolean.TRUE;
			}else{
				return Boolean.FALSE;
			}
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void optimizeIndexDocument() throws ApplicationThrowable {
		try {
			getDocumentDAO().optimizeIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchLinkedDocumentsTopic(String place, String topic, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchLinkedDocumentsTopic(place, topic, paginationFilter);
		}catch(Throwable th){
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
			
			return getPeopleDAO().searchPersonLinkableToDocument(EpLinkUtils.getPeopleIdList(epLinkList), query);
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
	public Page searchTopicsRelatedDocument(Integer topicId, Integer placeAllId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchLinkedDocumentsTopic(topicId, placeAllId, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVettingHistoryDocument(Integer entryId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			Document document = getDocumentDAO().find(entryId);
			return getVettingHistoryDAO().getVettingHistoryDocument(document, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param docReferenceDAO the docReferenceDAO to set
	 */
	public void setDocReferenceDAO(DocReferenceDAO docReferenceDAO) {
		this.docReferenceDAO = docReferenceDAO;
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
	 * @param forumDAO the forumDAO to set
	 */
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	/**
	 * @param forumOptionDAO the forumOptionDAO to set
	 */
	public void setForumOptionDAO(ForumOptionDAO forumOptionDAO) {
		this.forumOptionDAO = forumOptionDAO;
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
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @param userHistoryDAO the userHistoryDAO to set
	 */
	public void setUserHistoryDAO(UserHistoryDAO userHistoryDAO) {
		this.userHistoryDAO = userHistoryDAO;
	}

	/**
	 * @param userMarkedListDAO the userMarkedListDAO to set
	 */
	public void setUserMarkedListDAO(UserMarkedListDAO userMarkedListDAO) {
		this.userMarkedListDAO = userMarkedListDAO;
	}

	/**
	 * @param userMarkedListElementDAO the userMarkedListElementDAO to set
	 */
	public void setUserMarkedListElementDAO(
			UserMarkedListElementDAO userMarkedListElementDAO) {
		this.userMarkedListElementDAO = userMarkedListElementDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Document undeleteDocument(Integer entryId) throws ApplicationThrowable {
		Document documentToUnDelete = null;
		try {
			documentToUnDelete = getDocumentDAO().find(entryId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		documentToUnDelete.setLogicalDelete(Boolean.FALSE);

		try {
			getDocumentDAO().merge(documentToUnDelete);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Recovered document", Action.UNDELETE, Category.DOCUMENT, documentToUnDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return documentToUnDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateIndexDocument(Date fromDate) throws ApplicationThrowable {
		try {
			getDocumentDAO().updateIndex(fromDate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateIndexTopicList(Date fromDate) throws ApplicationThrowable {
		try {
			getTopicsListDAO().updateIndex(fromDate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

}
