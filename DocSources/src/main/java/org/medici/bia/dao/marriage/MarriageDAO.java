/*
 * MarriageDAO.java
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
package org.medici.bia.dao.marriage;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.People.Gender;

/**
 * Marriage Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface MarriageDAO extends Dao<Integer, Marriage> {

	/**
	 * This method searches a list of spouses of a person identified by a person identifier 
	 * and by gender. 
	 * 
	 * @param personId Person identifier
	 * @param gender Gender of person
	 * @return A List<Marriage> of a person.
	 * @throws PersistenceException
	 */
	List<Marriage> findMarriagesPerson(Integer personId, Gender gender) throws PersistenceException;

	/**
	 * This method searches the spouse of a person identified by a marriage identifier,
	 * a person identifier and by gender.
	 * 
	 * @param marriageId Marriage identifier
	 * @param personId Person identifier
	 * @param gender Gender of person
	 * @return A Marriage of a person
	 * @throws PersistenceException
	 */
	Marriage findMarriagePerson(Integer marriageId, Integer personId, Gender gender) throws PersistenceException;
}
