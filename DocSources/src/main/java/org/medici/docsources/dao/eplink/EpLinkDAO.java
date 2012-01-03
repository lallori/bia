/*
 * EpLinkDAO.java
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
package org.medici.docsources.dao.eplink;

import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.EpLink;

/**
 * EpLink Dao.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface EpLinkDAO extends Dao<Integer, EpLink> {

	/**
	 * This method searches every people linked to a document identified by
	 * his entryId.
	 * 
	 * @param entryId Document identifier.
	 * @return List of people found
	 * @throws PersistenceException
	 */
	public List<EpLink> findByEntryId(Integer entryId) throws PersistenceException;

	/**
	 * This method searches a person linked to a document identified by his entryId
	 * and by a document identifier.
	 * 
	 * @param epLinkId Person linked identifier
	 * @param entryId Document identifier
	 * @return A person linked found
	 * @throws PersistenceException
	 */
	public EpLink find(Integer epLinkId, Integer entryId) throws PersistenceException;
	
	/**
	 * 
	 * @param entryId
	 * @param docRole
	 * @return
	 * @throws PersistenceException
	 */
	public EpLink findByEntryIdAndRole(Integer entryId, String docRole) throws PersistenceException;
	
	/**
	 * 
	 * @param personId
	 * @return
	 * @throws PersistenceException
	 */
	public Integer findNumberOfDocumentsRelated(Integer personId) throws PersistenceException;
	
	/**
	 * 
	 * @param personIds
	 * @return
	 * @throws PersistenceException
	 */
	public Map<Integer, Long> findNumbersOfDocumentsRelated(List<Integer> personIds) throws PersistenceException;

}
