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
package org.medici.docsources.service.peoplebase;

import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Marriage;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.People.Gender;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * This interface is designed to work on {@link org.medici.docsources.domain.People} 
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
 */
public interface PeopleBaseService {

	/**
	 * 
	 * @param personId
	 * @param gender
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> findChildren(Integer personId, Gender gender) throws ApplicationThrowable;

	/**
	 * This method last entry {@link org.medici.docsources.domain.People}.
	 * 
	 * @return Last entry {@link org.medici.docsources.domain.People}
	 * @throws ApplicationThrowable
	 */
	public People findLastEntryPerson() throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 */
	public People findPerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @param gender
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Marriage> findPersonMarriages(Integer personId, Gender gender) throws ApplicationThrowable;

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
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @param text
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Page searchPeople(String text, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchRecipientsPeople(String query) throws ApplicationThrowable;

	/**
	 * 
	 * @param query
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<People> searchSendersPeople(String query) throws ApplicationThrowable;
}
