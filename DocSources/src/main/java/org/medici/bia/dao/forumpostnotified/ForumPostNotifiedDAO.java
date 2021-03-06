/*
 * ForumPostNotifiedDAO.java
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
package org.medici.bia.dao.forumpostnotified;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.dao.Dao;
import org.medici.bia.domain.ForumPostNotified;
import org.medici.bia.scheduler.ForumPostReplyMailJob;

/**
 * ForumPostNotified DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface ForumPostNotifiedDAO extends Dao<Integer, ForumPostNotified> {

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	List<ForumPostNotified> findForumPostRepliedNotNotified() throws PersistenceException;
	
	/**
	 * Returns the {@link ForumPostNotified} by the forum post identifier provided.
	 * 
	 * @param forumPostId the forum post identifier
	 * @return the {@link ForumPostNotified} found
	 * @throws PersistenceException
	 */
	ForumPostNotified getForumPostNotifiedByPost(Integer forumPostId) throws PersistenceException;
	
	/**
	 * Removes (phisically) all not sent forum post notifications linked to the provided posts.
	 * 
	 * @param postIds the list of post identifiers
	 * @return the number of removed elements
	 * @throws PersistenceException
	 */
	Integer removeAllNotSentForumPostNotifications(List<Integer> postIds) throws PersistenceException;
	
	/**
	 * Removes (phisically) all not sent forum post notifications linked to the provided forum.
	 * 
	 * @param forumId the forum identifier
	 * @param forumDepth the number of parent forum to consider from the post container
	 * @return the number of removed elements
	 * @throws PersistenceException
	 */
	Integer removeAllNotSentForumPostNotificationsByForum(Integer forumId, int forumDepth) throws PersistenceException;
	
	/**
	 * Removes (phisically) all not sent forum post notifications linked to the provided topic.
	 * 
	 * @param topicId the topic identifier
	 * @return number of removed elements
	 * @throws PersistenceException
	 */
	Integer removeAllNotSentForumPostNotificationsByTopic(Integer topicId) throws PersistenceException;

	/**
	 * Removes all bad {@link ForumPostNotified} elements.
	 * Possible causes are:
	 * <ul>
	 * <li>the post relative to the {@link ForumPostNotified} has been deleted</li>
	 * <li>the topic of the post relative to the {@link ForumPostNotified} has been deleted</li>
	 * <li>all user subscriptions of a topic have been removed (and the {@link ForumPostReplyMailJob}
	 * is not yet been fired)</li>
	 * <li>all subscribed users of a topic have not mail in their own accounts</li>
	 * </ul>
	 * 
	 * @return number of removed elements
	 * @throws PersistenceException
	 */
	Integer removeBadElements() throws PersistenceException;

}
