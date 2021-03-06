/*
 * PeopleDAO.java
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
package org.medici.bia.dao.people;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.People;

/**
 * Person Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface PeopleDAO extends Dao<Integer, People> {
	
	/**
	 * 
	 * @param inputDate
	 * @return
	 * @throws PersistenceException
	 */
	Long countPeopleCreatedAfterDate(Date inputDate) throws PersistenceException;
	
	/**
	 * This method returns last entry {@link org.medici.bia.domain.People} 
	 * created on database.
	 * 
	 * @return Last entry {@link org.medici.bia.domain.People}
	 * 
	 * @throws PersistenceException
	 */
	People findLastEntryPerson() throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfActiveEndInPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfActiveStartInPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfBirthInPlace(Integer placeAllId) throws PersistenceException;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Integer findNumberOfDeathInPlace(Integer placeAllId) throws PersistenceException;
	
	/**
	 * 
	 * @param placeAllIds
	 * @return
	 * @throws PersistenceException
	 */
	Map<Integer, Long> findNumbersOfPeopleRelatedPlace(List<Integer> placeAllIds) throws PersistenceException;
	
	/**
	 * This method generate Hibernate search index.
	 * 
	 * @throws PersistenceException
	 */
	void generateIndex() throws PersistenceException;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchActiveEndPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchActiveStartPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchBirthPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method searches for children which could be related to a person which contains 
	 * a text parameter (String query).  
	 * 
	 * @param personId Person identifier
	 * @param query Text to be searched
	 * @return A List<People> of children that could be related to a person.
	 * @throws PersistenceException
	 */
	List<People> searchChildLinkableToPerson(Integer personId, String query) throws PersistenceException;
	
	/**
	 * 
	 * @param placeToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchDeathPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param familyToSearch
	 * @param familyNamePrefix
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchFamilyPerson(String familyName, String familyNamePrefix, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * This method searches for fathers which could be related to a person which contains 
	 * a text parameter (String query).
	 * 
	 * @param query Text to be searched
	 * @return A List<People> of fathers that could be related to a person.
	 * @throws PersistenceException
	 */
	List<People> searchFatherLinkableToPerson(String query) throws PersistenceException;
	
	/**
	 * This method searches for mothers which could be related to a person which contains 
	 * a text parameter (String query).
	 * 
	 * @param query Text to be searched
	 * @return A List<People> of mothers that could be related to a person.
	 * @throws PersistenceException
	 */
	List<People> searchMotherLinkableToPerson(String query) throws PersistenceException;
	
	/**
	 * This method searches for person entities which contains the parameters set in {@link org.medici.bia.common.search}
	 * object and return a result page.
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return A result Page of people.
	 * @throws PersistenceException
	 */
	Page searchPeople(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * This method searches for person entities which contains the parameters set in query
	 * 
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	List<People> searchPeople(String searchText) throws PersistenceException;

	/**
	 * This method searches for person entities which could be related to a document which contains 
	 * a text parameter (String query).
	 *  
	 * @param peopleIdList List of person identifier
	 * @param searchText Text to be searched
	 * @return A List<People> of person entities that could be related to a document.
	 * @throws PersistenceException
	 */
	List<People> searchPersonLinkableToDocument(List<Integer> peopleIdList, String searchText) throws PersistenceException;

	/**
	 * This method searches for recipients which could be related to a document which contains 
	 * a text parameter (String searchText).
	 * 
	 * @param peopleIdList List of person identifier
	 * @param searchText Text to be searched
	 * @return A List<People> of recipients that could be related to a document.
	 * @throws PersistenceException
	 */
	List<People> searchRecipientsPeople(List<Integer> peopleIdList, String searchText) throws PersistenceException;
	
	/**
	 * 
	 * @param roleCatToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchRoleCatPeople(String roleCatToSearch, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * This method searches for senders which could be related to a document which contains 
	 * a text parameter (String searchText).
	 * 
	 * @param peopleIdList List of person identifier
	 * @param searchText Text to be searched
	 * @return A List<People> of senders that could be related to a document.
	 * @throws PersistenceException
	 */
	List<People> searchSendersPeople(List<Integer> peopleIdList, String searchText) throws PersistenceException;
	
	/**
	 * This method searches for spouse which could be related to a person which contains
	 * a text parameter (String query)
	 * 
	 * @param query Text to be searched
	 * @return A List<People> of spouses that could be related to a person.
	 * @throws PersistenceException
	 */
	List<People> searchSpouseLinkableToPerson(String query) throws PersistenceException;

	/**
	 * 
	 * @param titleOccToSearch
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page searchTitlesOrOccupationsPeople(String titleOccToSearch, PaginationFilter paginationFilter) throws PersistenceException;
}
