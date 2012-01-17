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
package org.medici.docsources.service.peoplebase;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.PersonUtils;
import org.medici.docsources.dao.altname.AltNameDAO;
import org.medici.docsources.dao.bibliot.BiblioTDAO;
import org.medici.docsources.dao.bioreflink.BioRefLinkDAO;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.eplink.EpLinkDAO;
import org.medici.docsources.dao.marriage.MarriageDAO;
import org.medici.docsources.dao.month.MonthDAO;
import org.medici.docsources.dao.parent.ParentDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.polink.PoLinkDAO;
import org.medici.docsources.dao.rolecat.RoleCatDAO;
import org.medici.docsources.dao.titleoccslist.TitleOccsListDAO;
import org.medici.docsources.dao.userhistorypeople.UserHistoryPeopleDAO;
import org.medici.docsources.domain.AltName;
import org.medici.docsources.domain.AltName.NameType;
import org.medici.docsources.domain.Marriage;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.Parent;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.People.Gender;
import org.medici.docsources.domain.PoLink;
import org.medici.docsources.domain.TitleOccsList;
import org.medici.docsources.domain.UserHistoryPeople;
import org.medici.docsources.domain.UserHistoryPeople.Action;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * action on people.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class PeopleBaseServiceImpl implements PeopleBaseService {
	@Autowired
	private AltNameDAO altNameDAO;
	
	@Autowired
	private BiblioTDAO biblioTDAO;
	
	@Autowired
	private BioRefLinkDAO bioRefLinkDAO;
	
	@Autowired
	private DocumentDAO documentDAO;
	
	@Autowired 
	private EpLinkDAO epLinkDAO;
	
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
	private UserHistoryPeopleDAO userHistoryPeopleDAO;
	
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add new alternative name", Action.M, altNameToPersist.getPerson()));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add children", Action.M, parent.getParent()));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add father", Action.M, parent.getParent()));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add marriage", Action.M,  marriage.getHusband()));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add mother", Action.M, parent.getParent()));

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
			person.setResearcher(((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getInitials());
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

			getPeopleDAO().persist(person);
			
			given.setPerson(getPeopleDAO().findLastEntryPerson());
			getAltNameDAO().persist(given);
			
			family.setPerson(getPeopleDAO().findLastEntryPerson());
			getAltNameDAO().persist(family);

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add person", Action.C, person));

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
			poLinkToCreate.setStartMonth(poLink.getStartMonth());
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Add new title or occupation", Action.M, poLinkToCreate.getPerson()));

			return poLinkToCreate.getPerson();
		} catch (Throwable th) {
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Delete child", Action.M, childToDelete.getParent()));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Delete father", Action.M, parentToDelete.getChild()));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Delete mother", Action.M, parentToDelete.getChild()));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Delete alternative name", Action.M, altName.getPerson()));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Deleted person", Action.D, personToDelete));
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
			
			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Delete spouse", Action.M, marriageToDelete.getHusband()));
			
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Delete title or occupation", Action.M, poLinkToDelete.getPerson()));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit child", Action.M, parent.getChild()));

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

			// fill fields to update person details section
			if(!personToUpdate.getFirst().equals(person.getFirst())){
				AltName current;
				Boolean found = Boolean.FALSE;
				Iterator<AltName> iterator = altNames.iterator();
				while(iterator.hasNext() && !found){
					current = iterator.next();
					if(current.getAltName().equals(personToUpdate.getFirst()) && current.getNameType().equals(NameType.Given.toString())){
						current.setAltName(person.getFirst());
						found = Boolean.TRUE;
					}
					getAltNameDAO().merge(current);
				}
			}
			
			if(!personToUpdate.getLast().equals(person.getLast())){
				AltName current;
				Boolean found = Boolean.FALSE;
				Iterator<AltName> iterator = altNames.iterator();
				while(iterator.hasNext() && !found){
					current = iterator.next();
					if(current.getAltName().equals(personToUpdate.getLast()) && current.getNameType().equals(NameType.Family.toString())){
						current.setAltName(person.getLast());
						found = Boolean.TRUE;
					}
					getAltNameDAO().merge(current);
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit details", Action.M, personToUpdate));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit father", Action.M, parent.getChild()));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit marriage", Action.M, marriageToUpdate.getHusband()));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit mother", Action.M, parent.getChild()));

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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit alternative name", Action.M, altName.getPerson()));
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
	public People editResearchNotesPerson(People person) throws ApplicationThrowable {
		try {
			People personToUpdate = getPeopleDAO().find(person.getPersonId());
			personToUpdate.setBioNotes(person.getBioNotes());

			// We update person object
			getPeopleDAO().merge(personToUpdate);

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit researh notes", Action.M, person));
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Edit title or occupation", Action.M, poLink.getPerson()));

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
	public People findLastEntryPerson() throws ApplicationThrowable {
		try {
			UserHistoryPeople userHistoryPeople = getUserHistoryPeopleDAO().findLastEntryPeople();
			
			if (userHistoryPeople != null) {
				return userHistoryPeople.getPeople();
			}
			
			// in case of no user History we extract last person created on database.
			return getPeopleDAO().findLastEntryPerson();
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
			
			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Show person", Action.V, people));

			return people;
		} catch (Throwable th) {
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

	/**
	 * @return the userHistoryPeopleDAO
	 */
	public UserHistoryPeopleDAO getUserHistoryPeopleDAO() {
		return userHistoryPeopleDAO;
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
			return getPeopleDAO().searchFamilyPerson(familyToSearch, paginationFilter);
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
	public List<People> searchRecipientsPeople(String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchRecipientsPeople(query);
		} catch (Throwable th) {
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
	public List<People> searchSendersPeople(String query) throws ApplicationThrowable {
		try {
			return getPeopleDAO().searchSendersPeople(query);
		} catch (Throwable th) {
			throw new ApplicationThrowable(th);
		}
	}

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
	 * @param altNameDAO the altNameDAO to set
	 */
	public void setAltNameDAO(AltNameDAO altNameDAO) {
		this.altNameDAO = altNameDAO;
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

	/**
	 * @param userHistoryPeopleDAO the userHistoryPeopleDAO to set
	 */
	public void setUserHistoryPeopleDAO(UserHistoryPeopleDAO userHistoryPeopleDAO) {
		this.userHistoryPeopleDAO = userHistoryPeopleDAO;
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

			getUserHistoryPeopleDAO().persist(new UserHistoryPeople("Recovered person", Action.M, personToUnDelete));
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
