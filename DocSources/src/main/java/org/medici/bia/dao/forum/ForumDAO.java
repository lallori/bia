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
package org.medici.bia.dao.forum;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Course;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.Volume;

/**
 * Forum DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
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
	Forum addNewDocumentForum(Forum forumParent, Document document) throws PersistenceException;
	
	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	Forum addNewForum(Forum forum) throws PersistenceException;

	/**
	 * 
	 * @param forumParent
	 * @param person
	 * @return
	 * @throws PersistenceException
	 */
	Forum addNewPersonForum(Forum forumParent, People person) throws PersistenceException;

	/**
	 * 
	 * @param forumParent
	 * @param person
	 * @return
	 * @throws PersistenceException
	 */
	Forum addNewPlaceForum(Forum forumParent, Place place) throws PersistenceException;

	/**
	 * 
	 * @param forumParent
	 * @param volume
	 * @param schedone
	 * @return
	 * @throws PersistenceException
	 */
	Forum addNewVolumeForum(Forum forumParent, Volume volume, Schedone schedone) throws PersistenceException;
	
	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Long countTotalActive() throws PersistenceException;

	/**
	 * 
	 * @param forumParentId
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteForumFromParent(Integer forumParentId) throws PersistenceException;
	
	/**
	 * @param courseForumId
	 * @param isCourseForum
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page findCoursesElements(Integer courseForumId, boolean isCourseForum, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * 
	 * @param volumeForum
	 * @param imageProgTypeNum
	 * @param imageRectoVerso
	 * @return
	 * @throws PersistenceException
	 */
	Forum findFolioForumFromParent(Forum volumeForum, Integer imageId) throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	List<Forum> findForumCategories(Forum forum) throws PersistenceException;

	/**
	 * 
	 * @param categoriesIds
	 * @return
	 * @throws PersistenceException
	 */
	Map<Integer, List<Forum>> findForumsGroupByCategory(List<Integer> categoriesIds) throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	List<Forum> findSubCategories(Forum forum) throws PersistenceException;

	/**
	 * 
	 * @param forumParentId
	 * @return
	 * @throws PersistenceException
	 */
	List<Forum> findSubForums(Integer forumParentId) throws PersistenceException;
	
	/**
	 * Returns all subforums of a forum associated to a document.
	 * 
	 * @param parentForumId the parent forum identifier
	 * @param entryId the document identifier
	 * @return the list of subforums found, or empty list if none
	 * @throws PersistenceException
	 */
	List<Forum> findSubForumsByDocument(Integer parentForumId, Integer entryId) throws PersistenceException;

	/**
	 * 
	 * @param forumParentId
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page findSubForums(Integer parentForumId, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param generalQuestionsForum
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	Forum findVolumeForumFromParent(Forum generalQuestionsForum, Integer summaryId) throws PersistenceException;

	/**
	 * 
	 * @param i
	 * @param numberOfForumForPagethrows
	 * @return
	 */
	Map<Integer, Date> getActiveForumsInformations(Integer pageNumber, Integer numberOfForumForPage) throws PersistenceException;

	/**
	 * 
	 * @param category
	 * @return
	 * @throws PersistenceException
	 */
	Forum getCategory(Forum category) throws PersistenceException;
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	Forum getCoursesContainer() throws PersistenceException;

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Forum getFirstCategory() throws PersistenceException;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws PersistenceException
	 */
	Forum getForumDocument(Integer entryId) throws PersistenceException;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws PersistenceException
	 */
	Forum getForumPerson(Integer personId) throws PersistenceException;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws PersistenceException
	 */
	Forum getForumPlace(Integer placeAllId) throws PersistenceException;

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	List<Forum> getForumsByType(Type type) throws PersistenceException;

	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws PersistenceException
	 */
	Forum getForumVolume(Integer summaryId) throws PersistenceException;
	
	/**
	 * 
	 * @param forumId
	 * @return
	 * @throws PersistenceException
	 */
	Long getSubForumsNumberWithTopics(Integer forumId) throws PersistenceException;
	
	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Map<String, Long> getTotalTopicsAndPosts() throws PersistenceException;
	
	/**
	 * This method determines if the provided forum is contained in an active {@link Course}.
	 * 
	 * @param forumId the forum identifier
	 * @return true if the forum is contained in an active course, false otherwise
	 * @throws PersistenceException
	 */
	boolean isInActiveCourse(Integer forumId) throws PersistenceException;

	/**
	 * 
	 * @param parentForum
	 * @throws PersistenceException
	 */
	void recursiveDecreasePostsNumber(Forum parentForum) throws PersistenceException;
	
	/**
	 * 
	 * @param parentForum
	 * @param decreaseNumber
	 * @throws PersistenceException
	 */
	void recursiveDecreasePostsNumber(Forum parentForum, Integer decreaseNumber) throws PersistenceException;

	/**
	 * 
	 * @param parentForum
	 * @throws PersistenceException
	 */
	void recursiveDecreaseSubForumsNumber(Forum parentForum) throws PersistenceException;
	
	/**
	 * 
	 * @param parentForum
	 */
	void recursiveDecreaseTopicsNumber(Forum parentForum) throws PersistenceException;
	
	/**
	 * 
	 * @param parentForum
	 * @param decreaseNumber
	 * @throws PersistenceException
	 */
	void recursiveDecreaseTopicsNumber(Forum parentForum, Integer decreaseNumber) throws PersistenceException;

	/**
	 * 
	 * @param parentForum
	 * @throws PersistenceException
	 */
	void recursiveIncreasePostsNumber(Forum parentForum) throws PersistenceException;

	/**
	 * 
	 * @param parentForum
	 * @throws PersistenceException
	 */
	void recursiveIncreaseSubForumsNumber(Forum parentForum) throws PersistenceException;
	
	/**
	 * 
	 * @param parentForum
	 * @throws PersistenceException
	 */
	void recursiveIncreaseTopicsNumber(Forum parentForum) throws PersistenceException;

}
