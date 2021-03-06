/*
 * PeopleBaseService.java
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
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.image.PersonPortrait;
import org.medici.bia.common.pagination.HistoryNavigator;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.SimpleSearchTitleOrOccupation;
import org.medici.bia.domain.AltName;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.Month;
import org.medici.bia.domain.Parent;
import org.medici.bia.domain.People;
import org.medici.bia.domain.PoLink;
import org.medici.bia.domain.RoleCat;
import org.medici.bia.domain.TitleOccsList;
import org.medici.bia.domain.People.Gender;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.bia.domain.People} 
 * object.<br>
 * It defines every business methods needed to work on people.
 * With this service, you can :<br>
 * - add a new person<br>
 * - modify an existing person<br> 
 * - search a person by his unique id<br>
 * - execute complex search on people<br>
 * ...<br>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface PeopleBaseService {

	/**
	 * 
	 * @param altName
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People addNewAltNamePerson(AltName altName) throws ApplicationThrowable;

	/**
	 * 
	 * @param parent
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent addNewChildPerson(Parent parent) throws ApplicationThrowable;

	/**
	 * 
	 * @param parent
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent addNewFatherPerson(Parent parent) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param marriage
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People addNewMarriagePerson(Marriage marriage) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param parent
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent addNewMotherPerson(Parent parent) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param person
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People addNewPerson(People person) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param volume
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum addNewPersonForum(People person) throws ApplicationThrowable;

	/**
	 * 
	 * @param titleOccsList
	 * @throws ApplicationThrowable
	 */
	public TitleOccsList addNewTitleOrOccupation(TitleOccsList titleOccsList) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param poLink
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People addNewTitleOrOccupationPerson(PoLink poLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 */
	public People comparePerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @param w
	 * @param h
	 */
	public void cropPortraitPerson(Integer personId, Double x, Double y, Double x2, Double y2, Double w, Double h) throws ApplicationThrowable;

	/**
	 * 
	 * @param child
	 * @throws ApplicationThrowable
	 */
	public void deleteChildFromPerson(Parent parent) throws ApplicationThrowable;

	/**
	 * 
	 * @param parent
	 * @throws ApplicationThrowable
	 */
	public void deleteFatherFromPerson(Parent parent) throws ApplicationThrowable;

	/**
	 * 
	 * @param parent
	 * @throws ApplicationThrowable
	 */
	public void deleteMotherFromPerson(Parent parent) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param altName
	 * @throws ApplicationThrowable
	 */
	public void deleteNamePerson(AltName altName) throws ApplicationThrowable;

	/**
	 * This method mark a {@link org.medici.bia.domain.People} as deleted .
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People deletePerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param marriage
	 * @throws ApplicationThrowable
	 */
	public void deleteSpouseFromPerson(Marriage marriage) throws ApplicationThrowable;

	/**
	 * 
	 * @param poLink
	 * @throws ApplicationThrowable
	 */
	public void deleteTitleOrOccupationPerson(PoLink poLink)throws ApplicationThrowable;

	/**
	 * 
	 * @param child
	 * @param parent
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent editChildPerson(Parent parent) throws ApplicationThrowable;

	/**
	 * 
	 * @param person
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People editDetailsPerson(People person) throws ApplicationThrowable;

	/**
	 * 
	 * @param parent
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent editFatherPerson(Parent parent) throws ApplicationThrowable;

	/**
	 * 
	 * @param marriage
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Marriage editMarriagePerson(Marriage marriage) throws ApplicationThrowable;

	/**
	 * 
	 * @param parent
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent editMotherPerson(Parent parent) throws ApplicationThrowable;

	/**
	 * 
	 * @param altName
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People editNamePerson(AltName altName) throws ApplicationThrowable;

	/**
	 * 
	 * @param person
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People editOptionPortraitPerson(People person) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param person
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People editResearchNotesPerson(People person) throws ApplicationThrowable;

	/**
	 * 
	 * @param titleOccsList
	 * @throws ApplicationThrowable
	 */
	public TitleOccsList editTitleOrOccupation(TitleOccsList titleOccsList) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param poLink
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People editTitleOrOccupationPerson(PoLink poLink) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @param nameId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public AltName findAltNamePerson(Integer personId, Integer nameId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> findChildrenPerson(Integer personId) throws ApplicationThrowable;

	/**
	 * This method last entry {@link org.medici.bia.domain.People}.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.People}
	 * @throws ApplicationThrowable
	 */
	public People findLastEntryPerson() throws ApplicationThrowable;

	/**
	 * 
	 * @param marriageId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Marriage findMarriagePerson(Integer marriageId) throws ApplicationThrowable;
	
	/**
	 * This method searches for a specific marriage.
	 * 
	 * @param marriageId
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Marriage findMarriagePerson(Integer marriageId, Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Marriage> findMarriagesPerson(Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @param gender
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Marriage> findMarriagesPerson(Integer personId, Gender gender) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfDocumentsRelated(Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfRecipientDocumentsRelated(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfReferringDocumentsRelated(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Integer findNumberOfSenderDocumentsRelated(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personIds
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<Integer, Long> findNumbersOfDocumentsRelated(List<Integer> personIds) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent findParent(Integer id)throws ApplicationThrowable;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Parent findParentPerson(Integer id) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 */
	public People findPerson(Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 */
	public People findPersonForNames(Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 */
	public People findPersonForPortrait(Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People findPersonFromHistory(Integer idUserHistory) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param roleCatId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public RoleCat findRoleCat(Integer roleCatId) throws ApplicationThrowable;

	/**
	 * 
	 * @param titleOccId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public TitleOccsList findTitleOccList(Integer titleOccId) throws ApplicationThrowable;

	/**
	 * 
	 * @param titleOcc
	 * @return
	 * @throws ApplicationThrowable
	 */
	public TitleOccsList findTitleOccList(String titleOcc) throws ApplicationThrowable;

	/**
	 * 
	 * @param titleOccId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public TitleOccsList findTitleOrOccupation(Integer titleOccId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @param prfLinkId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public PoLink findTitleOrOccupationPerson(Integer personId, Integer prfLinkId) throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexAltName() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexBiblioT() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexBioRefLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexEpLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexParents() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexPeople() throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexPoLink() throws ApplicationThrowable;

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexRoleCat() throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void generateIndexTitleOccsList() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param person
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getCategoryHistoryNavigator(People person) throws ApplicationThrowable;

	/**
	 * This method checks if the documents provided are digitized or not
	 * 
	 * @param documents list of documents
	 * @return map of digitized and not digitized documents
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Map<String, Boolean> getDocumentsDigitizedState(List<Document> documents) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param idUserHistory
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Object getHistoryNavigator(Integer idUserHistory, People person) throws ApplicationThrowable;

	/**
	 * 
	 * @param person
	 * @return
	 * @throws ApplicationThrowable
	 */
	public HistoryNavigator getHistoryNavigator(People person) throws ApplicationThrowable;
	
	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Month> getMonths() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param peopleIds
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Map<Integer, List<PoLink>> getOccupationsDetails(String alias, List<Integer> peopleIds) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Forum getPersonForum(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public BufferedImage getPortraitPerson(String portraitImageName) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public BufferedImage getPortraitPersonDefault() throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<RoleCat> getRoleCat() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public boolean ifPersonALreadyPresentInMarkedList(Integer personId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void optimizeIndexPeople() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void removePortraitPerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personPortrait
	 * @return
	 * @throws ApplicationThrowable
	 */
	public BufferedImage savePortaitPerson(PersonPortrait personPortrait) throws ApplicationThrowable;

	/**
	 * 
	 * @param personPortrait
	 * @return
	 * @throws ApplicationThrowable
	 */
	public String saveTemporaryImage(PersonPortrait personPortrait) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchChildLinkableToPerson(Integer personId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param familyToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchFamilyPerson(String familyToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchFatherLinkableToPerson(Integer personId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchMotherLinkableToPerson(Integer personId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchRecipientDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchRecipientsPeople(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchReferringToDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param roleCatToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchRoleCatPeoplePerson(String roleCatToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param personToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchSenderDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchSendersPeople(Integer entryId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchSpouseLinkableToPerson(Integer personId, String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<TitleOccsList> searchTitleOrOccupation(String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param simpleSearchTitleOrOccupation
	 * @param paginationFilter
	 * @return
	 */
	public Page  searchTitlesOrOccupations(SimpleSearchTitleOrOccupation simpleSearchTitleOrOccupation, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param alias
	 * @param roleCatId
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchTitlesOrOccupations(String alias, Integer roleCatId, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param titleOccToSearch
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchTitlesOrOccupationsPeoplePerson(String titleOccToSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param personId
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchVettingHistoryPerson(Integer personId, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * This method mark a {@link org.medici.bia.domain.People} as available.
	 * 
	 * @param personId 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public People undeletePerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param fromDate
	 * @throws ApplicationThrowable
	 */
	public void updateIndexPeople(Date fromDate) throws ApplicationThrowable;
}
