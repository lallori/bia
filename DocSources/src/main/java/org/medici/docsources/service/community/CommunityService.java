/*
 * CommunityService.java
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
package org.medici.docsources.service.community;

import java.util.ArrayList;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.search.UserMessageSearch;
import org.medici.docsources.domain.UserComment;
import org.medici.docsources.domain.UserMessage;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface CommunityService {

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public Long checkNewMessages() throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment createNewComment(UserComment userComment) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment createNewMessage(UserMessage userMessage) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment deleteComment(Integer commentId) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public void deleteMessage(Integer userMessageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param entryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnDocument(Integer entryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param personId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnPerson(Integer personId) throws ApplicationThrowable;

	/**
	 * 
	 * @param placeAllId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnPlace(Integer placeAllId) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param summaryId
	 * @return
	 * @throws ApplicationThrowable
	 */
	public ArrayList<UserComment> getCommentsOnVolume(Integer summaryId) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment replyComment(UserComment userComment, Integer parentUserCommentId) throws ApplicationThrowable;

	/**
	 * 
	 * @param userComment
	 * @return
	 * @throws ApplicationThrowable
	 */
	public UserComment replyMessage(UserMessage userMessage, Integer parentUserMessageId) throws ApplicationThrowable;

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 */
	public Page searchMessages(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws ApplicationThrowable;
}
