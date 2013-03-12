/*
 * PeopleBaseServiceImpl.java
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
package org.medici.bia.service.peoplebase;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.image.PersonPortrait;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.search.SimpleSearchTitleOrOccupation;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.EpLinkUtils;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.PersonUtils;
import org.medici.bia.dao.altname.AltNameDAO;
import org.medici.bia.dao.applicationproperty.ApplicationPropertyDAO;
import org.medici.bia.dao.bibliot.BiblioTDAO;
import org.medici.bia.dao.bioreflink.BioRefLinkDAO;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.eplink.EpLinkDAO;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumoption.ForumOptionDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.dao.marriage.MarriageDAO;
import org.medici.bia.dao.month.MonthDAO;
import org.medici.bia.dao.parent.ParentDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.polink.PoLinkDAO;
import org.medici.bia.dao.rolecat.RoleCatDAO;
import org.medici.bia.dao.titleoccslist.TitleOccsListDAO;
import org.medici.bia.dao.user.UserDAO;
import org.medici.bia.dao.userhistory.UserHistoryDAO;
import org.medici.bia.dao.usermarkedlist.UserMarkedListDAO;
import org.medici.bia.dao.usermarkedlistelement.UserMarkedListElementDAO;
import org.medici.bia.dao.vettinghistory.VettingHistoryDAO;
import org.medici.bia.domain.AltName;
import org.medici.bia.domain.AltName.NameType;
import org.medici.bia.domain.EpLink;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumOption;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Parent;
import org.medici.bia.domain.People;
import org.medici.bia.domain.People.Gender;
import org.medici.bia.domain.PoLink;
import org.medici.bia.domain.RoleCat;
import org.medici.bia.domain.TitleOccsList;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.medici.bia.domain.UserMarkedList;
import org.medici.bia.domain.UserMarkedListElement;
import org.medici.bia.domain.VettingHistory;
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
 * action on people.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class PeopleBaseServiceImpl implements PeopleBaseService {
	@Autowired
	private AltNameDAO altNameDAO;
	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;
	@Autowired
	private BiblioTDAO biblioTDAO;
	@Autowired
	private BioRefLinkDAO bioRefLinkDAO;
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired 
	private EpLinkDAO epLinkDAO;
	@Autowired
	private ForumDAO forumDAO;
	@Autowired
	private ForumOptionDAO forumOptionDAO;
	@Autowired
	private ImageDAO imageDAO;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private MarriageDAO marriageDAO;

	@Autowired
	private MonthDAO monthDAO;

	@Autowired
	private ParentDAO parentDAO;
	
	@Autowired
	private PeopleDAO peopleDAO;

	@Autowired
	private PlaceDAO placeDAO;

	@Autowired
	private PoLinkDAO poLinkDAO;
	
	@Autowired
	private RoleCatDAO roleCatDAO;
	
	@Autowired 
	private TitleOccsListDAO titleOccsListDAO;

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserHistoryDAO userHistoryDAO;
	
	@Autowired
	private UserMarkedListDAO userMarkedListDAO;
	
	@Autowired
	private UserMarkedListElementDAO userMarkedListElementDAO;

	@Autowired
	private VettingHistoryDAO vettingHistoryDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People addNewAltNamePerson(AltName altName) throws ApplicationThrowable {
		try {
			AltName altNameToPersist = new AltName(null, altName.getPerson().getPersonId());
			altNameToPersist.setAltName(altName.getAltName());
			altNameToPersist.setNamePrefix(altName.getNamePrefix());
			altNameToPersist.setNameType(altName.getNameType());
			altNameToPersist.setNotes(altName.getNotes());
			altNameToPersist.setPerson(getPeopleDAO().find(altName.getPerson().getPersonId()));
			getAltNameDAO().persist(altNameToPersist);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new alternative name", Action.MODIFY, Category.PEOPLE, altNameToPersist.getPerson()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add new alternative name", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, altNameToPersist.getPerson()));

			return altNameToPersist.getPerson();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Parent addNewChildPerson(Parent parent) throws ApplicationThrowable {
		try {
			parent.setId(null);
			parent.setDateCreated(new Date());
			parent.setLastUpdate(new Date());

			getParentDAO().persist(parent);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add children", Action.MODIFY, Category.PEOPLE, parent.getParent()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add children", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parent.getParent()));

			return parent;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Parent addNewFatherPerson(Parent parent) throws ApplicationThrowable {
		try {
			parent.setId(null);
			parent.setDateCreated(new Date());
			parent.setLastUpdate(new Date());

			getParentDAO().persist(parent);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add father", Action.MODIFY, Category.PEOPLE, parent.getParent()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add father", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parent.getParent()));

			return parent;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People addNewMarriagePerson(Marriage marriage) throws ApplicationThrowable {
		try {
			// Set marriageId to null to use generator value
			marriage.setMarriageId(null);
			marriage.setDateCreated(new Date());
			marriage.setStartUns(false);
			marriage.setEndUns(false);
			
			getMarriageDAO().persist(marriage);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add marriage", Action.MODIFY, Category.PEOPLE, marriage.getHusband()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add marriage", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, marriage.getHusband()));

			// TODO : We need to change sign method to inser specific person who invoked the add new Person 
			return marriage.getHusband();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Parent addNewMotherPerson(Parent parent) throws ApplicationThrowable {
		try {
			parent.setId(null);
			parent.setDateCreated(new Date());
			parent.setLastUpdate(new Date());

			getParentDAO().persist(parent);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add mother", Action.MODIFY, Category.PEOPLE, parent.getParent()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add mother", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parent.getParent()));

			return parent;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People addNewPerson(People person) throws ApplicationThrowable {
		try {
			person.setPersonId(null);
			
			//Setting fields that are defined as nullable = false
			person.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			person.setDateCreated(new Date());
			person.setLastUpdate(new Date());
			person.setMapNameLf(PersonUtils.generateMapNameLf(person));

			if (person.getGender().equals(Gender.NULL)) {
				person.setGender(null);
			}
			if (person.getBornApprox() == null) {
				person.setBornApprox(Boolean.FALSE);
			}
			if (person.getBornDateBc() == null) {
				person.setBornDateBc(Boolean.FALSE);
			}
			if (person.getBornPlaceUnsure() == null) {
				person.setBornPlaceUnsure(Boolean.FALSE);
			}
			if (person.getDeathApprox() == null) {
				person.setDeathApprox(Boolean.FALSE);
			}
			if (person.getDeathDateBc() == null) {
				person.setDeathDateBc(Boolean.FALSE);
			}
			if (person.getDeathPlaceUnsure() == null) {
				person.setDeathPlaceUnsure(Boolean.FALSE);
			}
			if (person.getPortrait() == null){
				person.setPortrait(false);
			}

			if (person.getBornMonth() != null) {
				Month month = getMonthDAO().find(person.getBornMonth().getMonthNum());
				person.setBornMonth(month);
			} else {
				person.setBornMonth(null);
			}
			person.setBornDate(DateUtils.getLuceneDate(person.getBornYear(), person.getBornMonth(), person.getBornDay()));

			if (person.getDeathMonth() != null) {
				Month month = getMonthDAO().find(person.getDeathMonth().getMonthNum());
				person.setDeathMonth(month);
			} else {
				person.setDeathMonth(null);
			}
			person.setDeathDate(DateUtils.getLuceneDate(person.getDeathYear(), person.getDeathMonth(), person.getDeathDay()));
			
			if (!ObjectUtils.toString(person.getBornPlace()).equals("")) {
				person.setBornPlace(getPlaceDAO().find(person.getBornPlace().getPlaceAllId()));
				if(person.getBornPlace().getPrefFlag().equals("V")){
					person.setBornPlace(getPlaceDAO().findPrinicipalPlace(person.getBornPlace().getGeogKey()));
				}
			} else {
				person.setBornPlace(null);
			}
			
			if (!ObjectUtils.toString(person.getDeathPlace()).equals("")) {
				person.setDeathPlace(getPlaceDAO().find(person.getDeathPlace().getPlaceAllId()));
				if(person.getDeathPlace().getPrefFlag().equals("V")){
					person.setDeathPlace(getPlaceDAO().findPrinicipalPlace(person.getDeathPlace().getGeogKey()));
				}
			} else {
				person.setDeathPlace(null);
			}

			if (ObjectUtils.toString(person.getActiveStart()).equals("")){
				person.setActiveStart(null);
			} else {
				person.setActiveStart(person.getActiveStart());
			}

			if (ObjectUtils.toString(person.getActiveEnd()).equals("")){
				person.setActiveEnd(null);
			} else {
				person.setActiveEnd(person.getActiveEnd());
			}

			person.setLogicalDelete(Boolean.FALSE);
			
			//Code for create altNames
			AltName given = new AltName();
			given.setAltName(person.getFirst());
			given.setNameType(NameType.Given.toString());
			
			AltName family = new AltName();
			family.setAltName(person.getLast());
			family.setNameType(NameType.Family.toString());
			
			AltName searchName = new AltName();
			searchName.setAltName(Normalizer.normalize(person.getMapNameLf(), Normalizer.Form.NFD));
			searchName.setAltName(searchName.getAltName().replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
			searchName.setAltName(searchName.getAltName().replace(",", ""));
			searchName.setAltName(searchName.getAltName().replace("(", ""));
			searchName.setAltName(searchName.getAltName().replace(")", ""));
			searchName.setAltName(searchName.getAltName().toUpperCase());
			searchName.setNameType(NameType.SearchName.toString());

			getPeopleDAO().persist(person);
			
			person = getPeopleDAO().findLastEntryPerson();
			
			given.setPerson(person);
			getAltNameDAO().persist(given);
			
			family.setPerson(person);
			getAltNameDAO().persist(family);
			
			searchName.setPerson(person);
			getAltNameDAO().persist(searchName);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add person", Action.CREATE, Category.PEOPLE, person));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add person", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.PEOPLE, person));

			return person;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Forum addNewPersonForum(People person) throws ApplicationThrowable {
		try {
			Forum forum = getForumDAO().getForumPerson(person.getPersonId());

			//this control is mandatory to prevent duplication records on forum
			if (forum == null) {
				person = getPeopleDAO().find(person.getPersonId());
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.people")));
				forum = getForumDAO().addNewPersonForum(parentForum, person);
				
				// we need to set new FullPath for recursive functions...
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

				// this method call is mandatory to increment topic number on parent forum
				getForumDAO().recursiveIncreaseTopicsNumber(parentForum);

				// we need to set new FullPath for recursive functions...
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);

				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.PEOPLE, forum));
			}else if(forum.getLogicalDelete()){
				Forum parentForum = getForumDAO().find(NumberUtils.createInteger(ApplicationPropertyManager.getApplicationProperty("forum.identifier.people")));
				
				forum.setLogicalDelete(Boolean.FALSE);
				forum.setTotalViews(0);
				forum.setLastUpdate(new Date());
				getForumDAO().merge(forum);
				
				// Increment the number of subforums
				getForumDAO().recursiveIncreaseSubForumsNumber(parentForum);
				
				User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Create new forum", Action.CREATE, Category.FORUM, forum));
				getVettingHistoryDAO().persist(new VettingHistory(user, "Create new forum", org.medici.bia.domain.VettingHistory.Action.CREATE, org.medici.bia.domain.VettingHistory.Category.PEOPLE, forum));
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
	public TitleOccsList addNewTitleOrOccupation(TitleOccsList titleOcc) throws ApplicationThrowable {
		try{
			TitleOccsList titleOccToPersist = new TitleOccsList(null);
			titleOccToPersist.setTitleOcc(titleOcc.getTitleOcc());
			titleOccToPersist.setRoleCat(getRoleCatDAO().find(titleOcc.getRoleCat().getRoleCatId()));

			titleOccToPersist.setResearcher(((BiaUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
			titleOccToPersist.setDateCreated(new Date());
			titleOccToPersist.setLastUpdate(new Date());
			getTitleOccsListDAO().persist(titleOccToPersist);
			return titleOccToPersist;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People addNewTitleOrOccupationPerson(PoLink poLink) throws ApplicationThrowable {
		try {
			PoLink poLinkToCreate = new PoLink();
			poLinkToCreate.setPrfLinkId(null);
			poLinkToCreate.setTitleOcc(getTitleOccsListDAO().find(poLink.getTitleOccList().getTitleOccId()));
			poLinkToCreate.setPerson(getPeopleDAO().find(poLink.getPerson().getPersonId()));
			poLinkToCreate.setPreferredRole(poLink.getPreferredRole());
			poLinkToCreate.setStartYear(poLink.getStartYear());
			if (poLink.getStartMonthNum() != null) {
				poLinkToCreate.setStartMonthNum(getMonthDAO().find(poLink.getStartMonthNum().getMonthNum()));
				poLinkToCreate.setStartMonth(poLinkToCreate.getStartMonthNum().getMonthName());
			} else {
				poLinkToCreate.setStartMonthNum(null);
				poLinkToCreate.setStartMonth(null);
			}
			poLinkToCreate.setStartDay(poLink.getStartDay());
			if (poLink.getStartApprox() == null) {
				poLinkToCreate.setStartApprox(Boolean.FALSE);
			} else {
				poLinkToCreate.setStartApprox(poLink.getStartApprox());
			}
			if (poLink.getStartUns() == null) {
				poLinkToCreate.setStartUns(Boolean.FALSE);
			} else {
				poLinkToCreate.setStartUns(poLink.getStartUns());
			}
			poLinkToCreate.setEndYear(poLink.getEndYear());
			if (poLink.getEndMonthNum() != null) {
				poLinkToCreate.setEndMonthNum(getMonthDAO().find(poLink.getEndMonthNum().getMonthNum()));
				poLinkToCreate.setEndMonth(poLinkToCreate.getEndMonthNum().getMonthName());
			} else {
				poLinkToCreate.setEndMonthNum(null);
				poLinkToCreate.setEndMonth(null);
			}
			poLinkToCreate.setEndDay(poLink.getEndDay());
			if (poLink.getEndApprox() == null) {
				poLinkToCreate.setEndApprox(Boolean.FALSE);
			} else {
				poLinkToCreate.setEndApprox(poLink.getEndApprox());
			}
			if (poLink.getEndUns() == null) {
				poLinkToCreate.setEndUns(Boolean.FALSE);
			} else {
				poLinkToCreate.setEndUns(poLink.getEndUns());
			}
			poLinkToCreate.setDateCreated(new Date());

			getPoLinkDAO().persist(poLinkToCreate);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Add new title or occupation", Action.MODIFY, Category.PEOPLE, poLinkToCreate.getPerson()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Add new title or occupation", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, poLinkToCreate.getPerson()));

			return poLinkToCreate.getPerson();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public People comparePerson(Integer personId) throws ApplicationThrowable {
		try {
			People people = getPeopleDAO().find(personId);
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Compare person", Action.COMPARE, Category.PEOPLE, people));

			return people;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void cropPortraitPerson(Integer personId, Double x, Double y, Double x2, Double y2, Double width, Double height) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personId);
			
			if ((person != null) && (person.getPortrait())) {
				String portraitPath = ApplicationPropertyManager.getApplicationProperty("portrait.person.path");
				File portraitFile = new File(portraitPath + "/" + person.getPortraitImageName());
				BufferedImage bufferedImage = ImageIO.read(portraitFile);
				//here code for cropping... TO BE TESTED...
				BufferedImage croppedBufferedImage = bufferedImage.getSubimage(x.intValue(), y.intValue(), width.intValue(), height.intValue());
				ImageIO.write(croppedBufferedImage, "jpg", portraitFile);
			}
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteChildFromPerson(Parent parent) throws ApplicationThrowable {
		try {
			Parent childToDelete = getParentDAO().find(parent.getId());

			getParentDAO().remove(childToDelete);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(childToDelete.getParent().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete child", Action.MODIFY, Category.PEOPLE, childToDelete.getParent()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete child", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, childToDelete.getParent()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteFatherFromPerson(Parent parent) throws ApplicationThrowable {
		try {
			Parent parentToDelete = getParentDAO().find(parent.getId());
			
			getParentDAO().remove(parentToDelete);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(parentToDelete.getChild().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete father", Action.MODIFY, Category.PEOPLE, parentToDelete.getChild()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete father", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parentToDelete.getParent()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteMotherFromPerson(Parent parent) throws ApplicationThrowable {
		try {
			Parent parentToDelete = getParentDAO().find(parent.getId());
			
			getParentDAO().remove(parentToDelete);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(parentToDelete.getChild().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete mother", Action.MODIFY, Category.PEOPLE, parentToDelete.getChild()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete mother", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parentToDelete.getParent()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteNamePerson(AltName altName) throws ApplicationThrowable {
		try {
			AltName altNameToDelete = getAltNameDAO().find(altName.getNameId());
			
			getAltNameDAO().remove(altNameToDelete);

			getPeopleDAO().merge(getPeopleDAO().find(altNameToDelete.getPerson().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete alternative name", Action.MODIFY, Category.PEOPLE, altName.getPerson()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete alternative name", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, altName.getPerson()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People deletePerson(Integer personId) throws ApplicationThrowable {
		People personToDelete = null;
		try {
			personToDelete = getPeopleDAO().find(personId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		personToDelete.setLogicalDelete(Boolean.TRUE);

		try {
			getPeopleDAO().merge(personToDelete);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Deleted person", Action.DELETE, Category.PEOPLE, personToDelete));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete person", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, personToDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return personToDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteSpouseFromPerson(Marriage marriage) throws ApplicationThrowable {
		try{
			Marriage marriageToDelete = getMarriageDAO().find(marriage.getMarriageId());
			
			getMarriageDAO().remove(marriageToDelete);
			
			getPeopleDAO().merge(getPeopleDAO().find(marriageToDelete.getHusband().getPersonId()));
			getPeopleDAO().merge(getPeopleDAO().find(marriageToDelete.getWife().getPersonId()));
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete spouse", Action.MODIFY, Category.PEOPLE, marriageToDelete.getHusband()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete spouse", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, marriageToDelete.getHusband()));
			
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void deleteTitleOrOccupationPerson(PoLink poLink) throws ApplicationThrowable {
		try {
			PoLink poLinkToDelete = getPoLinkDAO().find(poLink.getPrfLinkId());
			// Pasquinelli (05/01/2011) : I don't know why but withtout this access hibernate is unable to initi parent's set during index update.    
			poLinkToDelete.getPerson().getParents();
			
			getPoLinkDAO().remove(poLinkToDelete);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Delete title or occupation", Action.MODIFY, Category.PEOPLE, poLinkToDelete.getPerson()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Delete title or occupation", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, poLinkToDelete.getPerson()));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}	
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Parent editChildPerson(Parent parent) throws ApplicationThrowable {
		try {
			Parent parentToUpdate = getParentDAO().find(parent.getId());

			// fill mother property
			parentToUpdate.setChild(getPeopleDAO().find(parent.getChild().getPersonId()));
			parentToUpdate.setLastUpdate(new Date());

			getParentDAO().merge(parentToUpdate);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(parentToUpdate.getParent().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit child", Action.MODIFY, Category.PEOPLE, parent.getChild()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit child", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parent.getChild()));

			return parentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People editDetailsPerson(People person) throws ApplicationThrowable {
		try {
			People personToUpdate = getPeopleDAO().find(person.getPersonId());
			
			Set<AltName> altNames = personToUpdate.getAltName();
			AltName searchName = null;

			// fill fields to update person details section
			if(personToUpdate.getFirst() != null && !personToUpdate.getFirst().equals(person.getFirst())){
				AltName current;
				Boolean found = Boolean.FALSE;
				Iterator<AltName> iterator = altNames.iterator();
				while(iterator.hasNext() && !found){
					current = iterator.next();
					if(current.getAltName().equals(personToUpdate.getFirst()) && current.getNameType().equals(NameType.Given.toString())){
						current.setAltName(person.getFirst());
						found = Boolean.TRUE;
						getAltNameDAO().merge(current);
					}					
				}
			}
			
			if(personToUpdate.getLast() != null && !personToUpdate.getLast().equals(person.getLast())){
				AltName current;
				Boolean found = Boolean.FALSE;
				Iterator<AltName> iterator = altNames.iterator();
				while(iterator.hasNext() && !found){
					current = iterator.next();
					if(current.getAltName().equals(personToUpdate.getLast()) && current.getNameType().equals(NameType.Family.toString())){
						current.setAltName(person.getLast());
						found = Boolean.TRUE;
						getAltNameDAO().merge(current);
					}
				}
			}
			
			if(!personToUpdate.getMapNameLf().equals(PersonUtils.generateMapNameLf(person))){
				AltName current;
				Boolean found = Boolean.FALSE;
				Iterator<AltName> iterator = altNames.iterator();
				String toCompare = personToUpdate.getMapNameLf();
				toCompare = Normalizer.normalize(toCompare, Normalizer.Form.NFD);
				toCompare = toCompare.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
				toCompare = toCompare.replace(",", "");
				toCompare = toCompare.replace("(", "");
				toCompare = toCompare.replace(")", "");
				toCompare = toCompare.toUpperCase();
				while(iterator.hasNext() && !found){
					current = iterator.next();
					if(current.getAltName().equals(toCompare) && current.getNameType().equals(NameType.SearchName.toString())){
						current.setAltName(Normalizer.normalize(PersonUtils.generateMapNameLf(person), Normalizer.Form.NFD));
						current.setAltName(current.getAltName().replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
						current.setAltName(current.getAltName().replace(",", ""));
						current.setAltName(current.getAltName().replace("(", ""));
						current.setAltName(current.getAltName().replace(")", ""));
						current.setAltName(current.getAltName().toUpperCase());
						found = Boolean.TRUE;
						getAltNameDAO().merge(current);
					}					
				}
				if(found == Boolean.FALSE){
					searchName = new AltName();
					searchName.setAltName(Normalizer.normalize(PersonUtils.generateMapNameLf(person), Normalizer.Form.NFD));
					searchName.setAltName(searchName.getAltName().replaceAll("\\p{InCombiningDiacriticalMarks}+", ""));
					searchName.setAltName(searchName.getAltName().replace(",", ""));
					searchName.setAltName(searchName.getAltName().toUpperCase());
					searchName.setNameType(NameType.SearchName.toString());
				}
			}
			
			personToUpdate.setFirst(person.getFirst());
			personToUpdate.setSucNum(person.getSucNum());
			personToUpdate.setMidPrefix(person.getMidPrefix());
			personToUpdate.setMiddle(person.getMiddle());
			personToUpdate.setLastPrefix(person.getLastPrefix());
			personToUpdate.setLast(person.getLast());
			personToUpdate.setPostLastPrefix(person.getPostLastPrefix());
			personToUpdate.setPostLast(person.getPostLast());
			personToUpdate.setGender((!person.getGender().equals(People.Gender.NULL)) ? person.getGender() : null);
			//Update setMapNameLf
			personToUpdate.setMapNameLf(PersonUtils.generateMapNameLf(personToUpdate));
			
			personToUpdate.setBornYear(person.getBornYear());
			if (person.getBornMonth() != null) {
				personToUpdate.setBornMonth(getMonthDAO().find(person.getBornMonth().getMonthNum()));
			} else {
				personToUpdate.setBornMonth(null);
			}
			
			// Born Information
			personToUpdate.setBornDay(person.getBornDay());
			personToUpdate.setBornDate(DateUtils.getLuceneDate(personToUpdate.getBornYear(), personToUpdate.getBornMonth(), personToUpdate.getBornDay()));
			personToUpdate.setBornApprox(person.getBornApprox());
			personToUpdate.setBornDateBc(person.getBornDateBc());
			if (!ObjectUtils.toString(person.getBornPlace()).equals("")) {
				personToUpdate.setBornPlace(getPlaceDAO().find(person.getBornPlace().getPlaceAllId()));
				if(personToUpdate.getBornPlace().getPrefFlag().equals("V")){
					personToUpdate.setBornPlace(getPlaceDAO().findPrinicipalPlace(personToUpdate.getBornPlace().getGeogKey()));
				}
			}else {
				personToUpdate.setBornPlace(null);
			}
			personToUpdate.setBornPlaceUnsure(person.getBornPlaceUnsure());
			
			if (ObjectUtils.toString(person.getActiveStart()).equals("")){
				personToUpdate.setActiveStart(null);
			} else {
				personToUpdate.setActiveStart(person.getActiveStart());
			}

			// Death Information
			personToUpdate.setDeathYear(person.getDeathYear());
			if (person.getDeathMonth() != null)
				personToUpdate.setDeathMonth(getMonthDAO().find(person.getDeathMonth().getMonthNum()));
			else
				personToUpdate.setDeathMonth(null);
			personToUpdate.setDeathDay(person.getDeathDay());
			personToUpdate.setDeathDate(DateUtils.getLuceneDate(personToUpdate.getDeathYear(), personToUpdate.getDeathMonth(), personToUpdate.getDeathDay()));

			personToUpdate.setDeathApprox(person.getDeathApprox());
			personToUpdate.setDeathDateBc(person.getDeathDateBc());
			if (!ObjectUtils.toString(person.getDeathPlace()).equals("")) {
				personToUpdate.setDeathPlace(getPlaceDAO().find(person.getDeathPlace().getPlaceAllId()));
				if(personToUpdate.getDeathPlace().getPrefFlag().equals("V")){
					personToUpdate.setDeathPlace(getPlaceDAO().findPrinicipalPlace(personToUpdate.getDeathPlace().getGeogKey()));
				}
			} else {
				personToUpdate.setDeathPlace(null);
			}
			personToUpdate.setDeathPlaceUnsure(person.getDeathPlaceUnsure());

			if (ObjectUtils.toString(person.getActiveEnd()).equals("")){
				personToUpdate.setActiveEnd(null);
			} else {
				personToUpdate.setActiveEnd(person.getActiveEnd());
			}

			getPeopleDAO().merge(personToUpdate);
			
			if(searchName != null){
				searchName.setPerson(personToUpdate);
				getAltNameDAO().persist(searchName);
			}

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit details", Action.MODIFY,Category.PEOPLE, personToUpdate));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit details", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, personToUpdate));

			return personToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Parent editFatherPerson(Parent parent) throws ApplicationThrowable {
		try {
			Parent parentToUpdate = getParentDAO().find(parent.getId());

			// fill mother property
			parentToUpdate.setParent(getPeopleDAO().find(parent.getParent().getPersonId()));
			parentToUpdate.setLastUpdate(new Date());

			getParentDAO().merge(parentToUpdate);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(parentToUpdate.getChild().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit father", Action.MODIFY, Category.PEOPLE, parent.getChild()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit father", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parent.getChild()));
			return parentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Marriage editMarriagePerson(Marriage marriage) throws ApplicationThrowable {
		try {
			Marriage marriageToUpdate = getMarriageDAO().find(marriage.getMarriageId());
			
			marriageToUpdate.setHusband(marriage.getHusband());
			marriageToUpdate.setWife(marriage.getWife());
			marriageToUpdate.setStartYear(marriage.getStartYear());
			marriageToUpdate.setStartMonth(marriage.getStartMonth());
			marriageToUpdate.setStartDay(marriage.getStartDay());
			marriageToUpdate.setStartUns(false);
			marriageToUpdate.setEndYear(marriage.getEndYear());
			marriageToUpdate.setEndMonth(marriage.getEndMonth());
			marriageToUpdate.setEndDay(marriage.getEndDay());
			marriageToUpdate.setEndUns(false);
			marriageToUpdate.setMarTerm(marriage.getMarTerm());
			
			getMarriageDAO().merge(marriageToUpdate);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(marriageToUpdate.getWife().getPersonId()));
			getPeopleDAO().merge(getPeopleDAO().find(marriageToUpdate.getHusband().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit marriage", Action.MODIFY, Category.PEOPLE, marriageToUpdate.getHusband()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit marriage", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, marriageToUpdate.getHusband()));

			// TODO : We need to change sign method to inser specific person who invoked the add new Person 
			return marriage;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Parent editMotherPerson(Parent parent) throws ApplicationThrowable {
		try {
			Parent parentToUpdate = getParentDAO().find(parent.getId());

			// fill parent property
			parentToUpdate.setParent(getPeopleDAO().find(parent.getParent().getPersonId()));
			parentToUpdate.setLastUpdate(new Date());

			getParentDAO().merge(parentToUpdate);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(parentToUpdate.getChild().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit mother", Action.MODIFY, Category.PEOPLE, parent.getChild()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit mother", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, parent.getChild()));

			return parentToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People editNamePerson(AltName altName) throws ApplicationThrowable {
		try {
			AltName altNameToUpdate = getAltNameDAO().find(altName.getNameId());

			// fill fields to update document section
			altNameToUpdate.setAltName(altName.getAltName());
			altNameToUpdate.setNamePrefix(altName.getNamePrefix());
			altNameToUpdate.setNameType(altName.getNameType());
			
			getAltNameDAO().merge(altNameToUpdate);
			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(altNameToUpdate.getPerson().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit alternative name", Action.MODIFY, Category.PEOPLE, altName.getPerson()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit alternative name", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, altName.getPerson()));
			return altName.getPerson();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People editOptionPortraitPerson(People person) throws ApplicationThrowable {
		try{
			People personToUpdate = getPeopleDAO().find(person.getPersonId());
			personToUpdate.setPortraitAuthor(person.getPortraitAuthor());
			personToUpdate.setPortraitSubject(person.getPortraitSubject());
			
			getPeopleDAO().merge(personToUpdate);
			
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit option portrait", Action.MODIFY, Category.PEOPLE, person));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit option portrait", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, person));
			return personToUpdate;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People editResearchNotesPerson(People person) throws ApplicationThrowable {
		try {
			People personToUpdate = getPeopleDAO().find(person.getPersonId());
			personToUpdate.setBioNotes(person.getBioNotes());

			// We update person object
			getPeopleDAO().merge(personToUpdate);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit researh notes", Action.MODIFY, Category.PEOPLE, person));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit researh notes", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, person));
			return personToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public TitleOccsList editTitleOrOccupation(TitleOccsList titleOccsList) throws ApplicationThrowable {
		try {
			TitleOccsList titleOccsListToUpdate = getTitleOccsListDAO().find(titleOccsList.getTitleOccId());
			
			titleOccsListToUpdate.setLastUpdate(new Date());
			titleOccsListToUpdate.setTitleOcc(titleOccsList.getTitleOcc());
			titleOccsListToUpdate.setTitleVariants(titleOccsList.getTitleVariants());
			if (titleOccsList.getRoleCat() != null) {
				titleOccsListToUpdate.setRoleCat(getRoleCatDAO().find(titleOccsList.getRoleCat().getRoleCatId()));
			}
			
			getTitleOccsListDAO().merge(titleOccsListToUpdate);
			
			return titleOccsListToUpdate;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People editTitleOrOccupationPerson(PoLink poLink) throws ApplicationThrowable {
		try {
			PoLink poLinkToUpdate = getPoLinkDAO().find(poLink.getPrfLinkId());
			poLinkToUpdate.setTitleOcc(getTitleOccsListDAO().find(poLink.getTitleOccList().getTitleOccId()));
			poLinkToUpdate.setPerson(getPeopleDAO().find(poLink.getPerson().getPersonId()));
			poLinkToUpdate.setPreferredRole(poLink.getPreferredRole());
			poLinkToUpdate.setStartYear(poLink.getStartYear());
			if (poLink.getStartMonthNum() != null) {
				poLinkToUpdate.setStartMonthNum(getMonthDAO().find(poLink.getStartMonthNum().getMonthNum()));
				poLinkToUpdate.setStartMonth(poLinkToUpdate.getStartMonthNum().getMonthName());
			} else {
				poLinkToUpdate.setStartMonthNum(null);
				poLinkToUpdate.setStartMonth(null);
			}
			poLinkToUpdate.setStartDay(poLink.getStartDay());
			if (poLink.getStartApprox() == null) {
				poLinkToUpdate.setStartApprox(Boolean.FALSE);
			} else {
				poLinkToUpdate.setStartApprox(poLink.getStartApprox());
			}
			if (poLink.getStartUns() == null) {
				poLinkToUpdate.setStartUns(Boolean.FALSE);
			} else {
				poLinkToUpdate.setStartUns(poLink.getStartUns());
			}
			poLinkToUpdate.setEndYear(poLink.getEndYear());
			if (poLink.getEndMonthNum() != null) {
				poLinkToUpdate.setEndMonthNum(getMonthDAO().find(poLink.getEndMonthNum().getMonthNum()));
				poLinkToUpdate.setEndMonth(poLinkToUpdate.getEndMonthNum().getMonthName());
			} else {
				poLinkToUpdate.setEndMonthNum(null);
				poLinkToUpdate.setEndMonth(null);
			}
			poLinkToUpdate.setEndDay(poLink.getEndDay());
			if (poLink.getEndApprox() == null) {
				poLinkToUpdate.setEndApprox(Boolean.FALSE);
			} else {
				poLinkToUpdate.setEndApprox(poLink.getEndApprox());
			}
			if (poLink.getEndUns() == null) {
				poLinkToUpdate.setEndUns(Boolean.FALSE);
			} else {
				poLinkToUpdate.setEndUns(poLink.getEndUns());
			}

			// Person can have only one PreferredRole, so if this title is preferred we remove from others. 
			if (poLink.getPreferredRole()) {
				getPoLinkDAO().resetPreferredRoleForPersonTitles(poLink.getPrfLinkId(), poLink.getPerson().getPersonId());
			}
			getPoLinkDAO().merge(poLinkToUpdate);

			// we need to update person to permit correct lucene index update
			getPeopleDAO().merge(getPeopleDAO().find(poLinkToUpdate.getPerson().getPersonId()));

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Edit title or occupation", Action.MODIFY, Category.PEOPLE, poLink.getPerson()));
			getVettingHistoryDAO().persist(new VettingHistory(user, "Edit title or occupation", org.medici.bia.domain.VettingHistory.Action.MODIFY, org.medici.bia.domain.VettingHistory.Category.PEOPLE, poLink.getPerson()));

			return poLinkToUpdate.getPerson();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public AltName findAltNamePerson(Integer personId, Integer nameId) throws ApplicationThrowable {
		try {
			return getAltNameDAO().findAltNamePerson(personId, nameId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> findChildrenPerson(Integer personId) throws ApplicationThrowable {
		try{
			return getParentDAO().findChildren(personId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findLastEntryPerson() throws ApplicationThrowable {
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findLastEntry(user, Category.PEOPLE);
			
			if (userHistory != null) {
				return userHistory.getPerson();
			}
			
			// in case of no user History we return .
			return null;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Marriage findMarriagePerson(Integer marriageId) throws ApplicationThrowable {
		try {
			return getMarriageDAO().find(marriageId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Marriage findMarriagePerson(Integer marriageId, Integer personId) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personId);
			
			return getMarriageDAO().findMarriagePerson(marriageId, person.getPersonId(), person.getGender());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Marriage> findMarriagesPerson(Integer personId) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personId);
			
			return getMarriageDAO().findMarriagesPerson(person.getPersonId(), person.getGender());
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Marriage> findMarriagesPerson(Integer personId, Gender gender) throws ApplicationThrowable {
		try {
			return getMarriageDAO().findMarriagesPerson(personId, gender);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfDocumentsRelated(Integer personId)throws ApplicationThrowable {
		try{
			return getEpLinkDAO().findNumberOfDocumentsRelated(personId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfRecipientDocumentsRelated(Integer personId) throws ApplicationThrowable {
		try{
			return getDocumentDAO().findNumberOfRecipientDocumentsPerson(personId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfReferringDocumentsRelated(Integer personId) throws ApplicationThrowable {
		try{
			return getEpLinkDAO().findNumberOfReferringDocumentsRelated(personId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfSenderDocumentsRelated(Integer personId) throws ApplicationThrowable {
		try{
			return getDocumentDAO().findNumberOfSenderDocumentsPerson(personId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, Long> findNumbersOfDocumentsRelated(List<Integer> personIds) throws ApplicationThrowable {
		try{
			Map<Integer, Long> docsRel= getEpLinkDAO().findNumbersOfDocumentsRelated(personIds);
			return docsRel;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Parent findParent(Integer id) throws ApplicationThrowable {
		try {
			return getParentDAO().find(id);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Parent findParentPerson(Integer id) throws ApplicationThrowable {
		try {
			return getParentDAO().find(id);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findPerson(Integer personId)  throws ApplicationThrowable {
		try {
			People people = getPeopleDAO().find(personId);
			
			User user;
			if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails){
				user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

				getUserHistoryDAO().persist(new UserHistory(user, "Show person", Action.VIEW, Category.PEOPLE, people));
			}

			return people;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findPersonForNames(Integer personId)  throws ApplicationThrowable {
		try {
			People people = getPeopleDAO().find(personId);

			return people;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findPersonForPortrait(Integer personId)  throws ApplicationThrowable {
		try {
			People people = getPeopleDAO().find(personId);

			return people;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findPersonFromHistory(Integer idUserHistory) throws ApplicationThrowable {
		try{
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			return userHistory.getPerson();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoleCat findRoleCat(Integer roleCatId) throws ApplicationThrowable {
		try{
			return getRoleCatDAO().find(roleCatId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TitleOccsList findTitleOccList(Integer titleOccId) throws ApplicationThrowable {
		try{
			return getTitleOccsListDAO().find(titleOccId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TitleOccsList findTitleOccList(String titleOcc) throws ApplicationThrowable {
		try{
			return getTitleOccsListDAO().findTitleOcc(titleOcc);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TitleOccsList findTitleOrOccupation(Integer titleOccId) throws ApplicationThrowable {
		try {
			return getTitleOccsListDAO().find(titleOccId);
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PoLink findTitleOrOccupationPerson(Integer personId, Integer prfLinkId) throws ApplicationThrowable {
		try {
			return getPoLinkDAO().find(personId, prfLinkId);
		} catch(Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateIndexAltName() throws ApplicationThrowable {
		try {
			getAltNameDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexBiblioT() throws ApplicationThrowable {
		try {
			getBiblioTDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexBioRefLink() throws ApplicationThrowable {
		try {
			getBioRefLinkDAO().generateIndex();
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
	public void generateIndexParents() throws ApplicationThrowable {
		try {
			getParentDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexPeople() throws ApplicationThrowable {
		try {
			getPeopleDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexPoLink() throws ApplicationThrowable {
		try {
			getPoLinkDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexRoleCat() throws ApplicationThrowable {
		try {
			getRoleCatDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void generateIndexTitleOccsList() throws ApplicationThrowable {
		try {
			getTitleOccsListDAO().generateIndex();
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}

	/**
	 * @return the altNameDAO
	 */
	public AltNameDAO getAltNameDAO() {
		return altNameDAO;
	}
	
	/**
	 * @return the applicationPropertyDAO
	 */
	public ApplicationPropertyDAO getApplicationPropertyDAO() {
		return applicationPropertyDAO;
	}

	/**
	 * @return the biblioTDAO
	 */
	public BiblioTDAO getBiblioTDAO() {
		return biblioTDAO;
	}

	/**
	 * @return the bioRefLinkDAO
	 */
	public BioRefLinkDAO getBioRefLinkDAO() {
		return bioRefLinkDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryNavigator getCategoryHistoryNavigator(People person) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.PEOPLE, person.getPersonId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextCategoryHistoryCursor(user, userHistory.getCategory(), userHistory.getIdUserHistory());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
		}catch(Throwable th){
			logger.error(th);
		}

		return historyNavigator;
	}
	
	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	@Override
	public Map<String, Boolean> getDocumentsDigitizedState(List<Integer> volNums, List<String> volLetExts, List<Integer> folioNums, List<String> folioMods) throws ApplicationThrowable {
		Map<String, Boolean> retValue = new HashMap<String, Boolean>();
		try{
			for(int i=0; i<volNums.size();i++){
				retValue.put(DocumentUtils.toMDPAndFolioFormat(volNums.get(i), volLetExts.get(i), folioNums.get(i), folioMods.get(i)), Boolean.FALSE);
			}
			
			List<String> documentsDigitized = getImageDAO().findDocumentsDigitized(volNums, volLetExts, folioNums, folioMods);
			
			for(String MDPFolio : documentsDigitized){
				retValue.put(MDPFolio, Boolean.TRUE);
			}
			return retValue;
		}catch(Throwable th){
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
	 * 
	 */
	@Override
	public Object getHistoryNavigator(Integer idUserHistory, People person) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			UserHistory userHistory = getUserHistoryDAO().find(idUserHistory);
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromPerson(userHistory.getUser(), userHistory.getIdUserHistory(), person.getPersonId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromPerson(userHistory.getUser(), userHistory.getIdUserHistory(), person.getPersonId());
			
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
	public HistoryNavigator getHistoryNavigator(People person) throws ApplicationThrowable {
		HistoryNavigator historyNavigator = new HistoryNavigator();
		try {
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserHistory userHistory = getUserHistoryDAO().findHistoryFromEntity(user, Category.PEOPLE, person.getPersonId());
			
			UserHistory previousUserHistory = getUserHistoryDAO().findPreviousHistoryCursorFromPerson(user, userHistory.getIdUserHistory(), person.getPersonId());
			UserHistory nextUserHistory = getUserHistoryDAO().findNextHistoryCursorFromPerson(user, userHistory.getIdUserHistory(), person.getPersonId());
			
			historyNavigator.setPreviousHistoryUrl(HtmlUtils.getHistoryNavigatorPreviousPageUrl(previousUserHistory));
			historyNavigator.setNextHistoryUrl(HtmlUtils.getHistoryNavigatorNextPageUrl(nextUserHistory));

			return historyNavigator;
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
	 * @return the marriageDAO
	 */
	public MarriageDAO getMarriageDAO() {
		return marriageDAO;
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
	 * {@inheritDoc}
	 */
	@Override
	public Map<Integer, List<PoLink>> getOccupationsDetails(String alias, List<Integer> peopleIds) throws ApplicationThrowable {
		try{
			Map<Integer, List<PoLink>> result = new HashMap<Integer, List<PoLink>>();
			List<PoLink> occupations = getPoLinkDAO().getOccupationsDetails(alias, peopleIds);
			for(PoLink currentOccupation : occupations){
				List<PoLink> occupationsPerson;
				if(result.containsKey(currentOccupation.getPerson().getPersonId())){
					occupationsPerson = result.get(currentOccupation.getPerson().getPersonId());					
				}else{
					occupationsPerson = new ArrayList<PoLink>();					
				}
				occupationsPerson.add(currentOccupation);
				result.put(currentOccupation.getPerson().getPersonId(), occupationsPerson);
			}
			return result;
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the parentDAO
	 */
	public ParentDAO getParentDAO() {
		return parentDAO;
	}

	/**
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum getPersonForum(Integer personId) throws ApplicationThrowable {
		try{
			return getForumDAO().getForumPerson(personId);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
	}

	/**
	 * @return the poLinkDAO
	 */
	public PoLinkDAO getPoLinkDAO() {
		return poLinkDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BufferedImage getPortraitPerson(String portraitImageName) throws ApplicationThrowable {
		try {
			File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("portrait.person.path") + "/" + portraitImageName);
			
			return ImageIO.read(imageFile);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BufferedImage getPortraitPersonDefault() throws ApplicationThrowable {
		try {
			File imageFile = new File(ApplicationPropertyManager.getApplicationProperty("portrait.person.default"));
			
			return ImageIO.read(imageFile);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RoleCat> getRoleCat() throws ApplicationThrowable {
		try{
			return getRoleCatDAO().getAllRoleCat();
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * @return the roleCatsDAO
	 */
	public RoleCatDAO getRoleCatDAO() {
		return roleCatDAO;
	}

	/**
	 * @return the titleOccsListDAO
	 */
	public TitleOccsListDAO getTitleOccsListDAO() {
		return titleOccsListDAO;
	}

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
	 * {@inheritDoc}
	 */
	@Override
	public boolean ifPersonALreadyPresentInMarkedList(Integer personId) throws ApplicationThrowable {
		try{
			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			UserMarkedList userMarkedList = getUserMarkedListDAO().getMyMarkedList(user);
			if(userMarkedList == null){
				return Boolean.FALSE;
			}

			UserMarkedListElement userMarkedListElement = getUserMarkedListElementDAO().findPersonInMarkedList(userMarkedList.getIdMarkedList(), personId);
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
	public void optimizeIndexPeople() throws ApplicationThrowable {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void removePortraitPerson(Integer personId) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personId);
			
			if (person != null) {
				person.setPortrait(Boolean.FALSE);
				person.setPortraitImageName(null);
				person.setPortraitAuthor(null);
				person.setPortraitSubject(null);
				getPeopleDAO().merge(person);
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public BufferedImage savePortaitPerson(PersonPortrait personPortrait) throws ApplicationThrowable {
		try {
			People person = getPeopleDAO().find(personPortrait.getPersonId());
			
			if (person != null) {
				String tempPath = ApplicationPropertyManager.getApplicationProperty("portrait.person.path.tmp");
				String portraitPath = ApplicationPropertyManager.getApplicationProperty("portrait.person.path");
				File tempFile;
				
				String fileName = null;
				if(personPortrait.getFile() != null && personPortrait.getFile().getSize() > 0){
					fileName =  personPortrait.getPersonId() + "_" + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() +  "_" + personPortrait.getFile().getOriginalFilename();
					tempFile = new File(tempPath + "/" + fileName);
					FileUtils.writeByteArrayToFile(tempFile, personPortrait.getFile().getBytes());
				}else{
					fileName = personPortrait.getPersonId() + "_" + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
					String extension = personPortrait.getLink().substring(personPortrait.getLink().lastIndexOf("."), personPortrait.getLink().length());
					fileName = fileName.concat(extension);
					tempFile = new File(tempPath + "/" + fileName);
					FileUtils.copyURLToFile(new URL(personPortrait.getLink()), tempFile);
				}
	
				File portraitFile = new File(portraitPath + "/" + fileName);
				if(personPortrait.getFile() != null && personPortrait.getFile().getSize() > 0){
					FileUtils.writeByteArrayToFile(portraitFile, personPortrait.getFile().getBytes());
				}else{
					FileUtils.copyFile(tempFile, portraitFile);
				}
			
				person.setPortrait(Boolean.TRUE);
				person.setPortraitImageName(fileName);
				getPeopleDAO().merge(person);
				
				BufferedImage bufferedImage = ImageIO.read(portraitFile);
				return bufferedImage;
			} else {
				return null;
			}
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String saveTemporaryImage(PersonPortrait personPortrait) throws ApplicationThrowable {
		try {
			String tempPath = ApplicationPropertyManager.getApplicationProperty("portrait.person.path.tmp");
			
			String fileName;
			if(personPortrait.getFile() != null && personPortrait.getFile().getSize() > 0){
				fileName =  personPortrait.getPersonId() + "_" + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername() +  "_" + personPortrait.getFile().getOriginalFilename();
				File tempFile = new File(tempPath + "/" + fileName);
				FileUtils.writeByteArrayToFile(tempFile, personPortrait.getFile().getBytes());
			}else{
				fileName = personPortrait.getPersonId() + "_" + ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
				File tempFile = new File(tempPath + "/" + fileName);
				FileUtils.copyURLToFile(new URL(personPortrait.getLink()), tempFile);
			}

			return fileName;
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchChildLinkableToPerson(Integer personId, String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchChildLinkableToPerson(personId, query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchDocumentsRelated(personToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchFamilyPerson(String familyToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			AltName family = getAltNameDAO().find(NumberUtils.createInteger(familyToSearch));
			return getPeopleDAO().searchFamilyPerson(family.getAltName(), family.getNamePrefix(), paginationFilter);
			}catch(Throwable th){
				throw new ApplicationThrowable(th);
			}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchFatherLinkableToPerson(Integer personId, String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchFatherLinkableToPerson(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchMotherLinkableToPerson(Integer personId, String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchMotherLinkableToPerson(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchRecipientDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchRecipientDocumentsPerson(personToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchRecipientsPeople(Integer entryId, String query) throws ApplicationThrowable {
		try {
			List<EpLink> epLinkList = getEpLinkDAO().findByEntryId(entryId);
			
			return getPeopleDAO().searchRecipientsPeople(EpLinkUtils.getPeopleIdList(epLinkList),query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchReferringToDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchReferringToDocumentsPerson(personToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchRoleCatPeoplePerson(String roleCatToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchRoleCatPeople(roleCatToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchSenderDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getDocumentDAO().searchSenderDocumentsPerson(personToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchSendersPeople(Integer entryId, String query) throws ApplicationThrowable {
		try {
			List<EpLink> epLinkList = getEpLinkDAO().findByEntryId(entryId);
			
			return getPeopleDAO().searchSendersPeople(EpLinkUtils.getPeopleIdList(epLinkList), query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<People> searchSpouseLinkableToPerson(Integer personId, String query) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchSpouseLinkableToPerson(query);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TitleOccsList> searchTitleOrOccupation(String query) throws ApplicationThrowable {
		try {
			return getTitleOccsListDAO().searchTitleOrOccupationLinkableToPerson(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	@Override
	public Page searchTitlesOrOccupations(SimpleSearchTitleOrOccupation simpleSearchTitleOrOccupation, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getTitleOccsListDAO().searchTitlesOrOccupations(simpleSearchTitleOrOccupation, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * 
	 */
	@Override
	public Page searchTitlesOrOccupations(String alias, Integer roleCatId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try {
			return getTitleOccsListDAO().searchTitleOrOccupationWithAssignedPeople(alias, roleCatId, paginationFilter);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTitlesOrOccupationsPeoplePerson(String titleOccToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			return getPeopleDAO().searchTitlesOrOccupationsPeople(titleOccToSearch, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVettingHistoryPerson(Integer personId, PaginationFilter paginationFilter) throws ApplicationThrowable {
		try{
			People person = getPeopleDAO().find(personId);
			return getVettingHistoryDAO().getVettingHistoryPerson(person, paginationFilter);
		}catch(Throwable th){
			throw new ApplicationThrowable(th);
		}
	}

	/**
	 * @param altNameDAO the altNameDAO to set
	 */
	public void setAltNameDAO(AltNameDAO altNameDAO) {
		this.altNameDAO = altNameDAO;
	}

	/**
	 * @param applicationPropertyDAO the applicationPropertyDAO to set
	 */
	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}

	/**
	 * @param biblioTDAO the biblioTDAO to set
	 */
	public void setBiblioTDAO(BiblioTDAO biblioTDAO) {
		this.biblioTDAO = biblioTDAO;
	}

	/**
	 * @param bioRefLinkDAO the bioRefLinkDAO to set
	 */
	public void setBioRefLinkDAO(BioRefLinkDAO bioRefLinkDAO) {
		this.bioRefLinkDAO = bioRefLinkDAO;
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
	 * @param marriageDAO the marriageDAO to set
	 */
	public void setMarriageDAO(MarriageDAO marriageDAO) {
		this.marriageDAO = marriageDAO;
	}

	/**
	 * @param monthDAO the monthDAO to set
	 */
	public void setMonthDAO(MonthDAO monthDAO) {
		this.monthDAO = monthDAO;
	}

	/**
	 * @param parentDAO the parentDAO to set
	 */
	public void setParentDAO(ParentDAO parentDAO) {
		this.parentDAO = parentDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) throws ApplicationThrowable {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	/**
	 * @param poLinkDAO the poLinkDAO to set
	 */
	public void setPoLinkDAO(PoLinkDAO poLinkDAO) {
		this.poLinkDAO = poLinkDAO;
	}

	/**
	 * @param roleCatDAO the roleCatDAO to set
	 */
	public void setRoleCatDAO(RoleCatDAO roleCatDAO) {
		this.roleCatDAO = roleCatDAO;
	}

	/**
	 * @param titleOccsListDAO the titleOccsListDAO to set
	 */
	public void setTitleOccsListDAO(TitleOccsListDAO titleOccsListDAO) {
		this.titleOccsListDAO = titleOccsListDAO;
	}

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
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public People undeletePerson(Integer personId) throws ApplicationThrowable {
		People personToUnDelete = null;
		try {
			personToUnDelete = getPeopleDAO().find(personId);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}

		personToUnDelete.setLogicalDelete(Boolean.FALSE);

		try {
			getPeopleDAO().merge(personToUnDelete);

			User user = getUserDAO().findUser((((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()));

			getUserHistoryDAO().persist(new UserHistory(user, "Recovered person", Action.UNDELETE, Category.PEOPLE, personToUnDelete));
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
		
		return personToUnDelete;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public void updateIndexPeople(Date fromDate) throws ApplicationThrowable {
		try {
			getPeopleDAO().updateIndex(fromDate);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}		
	}
}
