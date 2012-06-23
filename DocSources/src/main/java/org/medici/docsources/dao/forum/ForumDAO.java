/*
 * ForumDAO.java
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
package org.medici.docsources.dao.forum;

import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.Document;
import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.Forum.Type;
import org.medici.docsources.domain.Place;
import org.medici.docsources.domain.Volume;

/**
 * Forum DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public interface ForumDAO extends Dao<Integer, Forum> {

	/**
	 * 
	 * @param forumParent
	 * @param person
	 * @return
	 * @throws PersistenceException
	 */
	public Forum addNewDocumentForum(Forum forumParent, Document document) throws PersistenceException;
	
	/**
	 * 
	 * @param forumParent
	 * @param person
	 * @return
	 * @throws PersistenceException
	 */
	public Forum addNewPersonForum(Forum forumParent, People person) throws PersistenceException;

	/**
	 * 
	 * @param forumParent
	 * @param person
	 * @return
	 * @throws PersistenceException
	 */
	public Forum addNewPlaceForum(Forum forumParent, Place place) throws PersistenceException;

	/**
	 * 
	 * @param forumParent
	 * @param person
	 * @return
	 * @throws PersistenceException
	 */
	public Forum addNewVolumeForum(Forum forumParent, Volume volume) throws PersistenceException;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws PersistenceException
	 */
	public Forum getForumDocument(Integer entryId) throws PersistenceException;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws PersistenceException
	 */
	public Forum getForumPerson(Integer personId) throws PersistenceException;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	public Forum getForumPlace(Integer placeAllId) throws PersistenceException;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	public Forum getForumVolume(Integer summaryId) throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	public List<Forum> findForumCategories(Forum forum) throws PersistenceException;

	/**
	 * 
	 * @param categoriesIds
	 * @return
	 * @throws PersistenceException
	 */
	public HashMap<Integer, List<Forum>> findForumsGroupByCategory(List<Integer> categoriesIds) throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	public List<Forum> findSubCategories(Forum forum) throws PersistenceException;

	/**
	 * 
	 * @param forumParentId
	 * @return
	 * @throws PersistenceException
	 */
	public List<Forum> findSubForums(Integer forumParentId) throws PersistenceException;

	/**
	 * 
	 * @param forumParentId
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page findSubForums(Integer parentForumId, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	public Forum getCategory(Forum category) throws PersistenceException;

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public Forum getFirstCategory() throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	public List<Forum> getForumsByType(Type type) throws PersistenceException;

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	public HashMap<String, Long> getTotalTopicsAndPosts() throws PersistenceException;
}
