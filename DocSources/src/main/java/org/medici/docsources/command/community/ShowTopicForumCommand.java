/*
 * ShowTopicForumCommand.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.docsources.command.community;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class ShowTopicForumCommand {
	private Boolean completeDOM;
	private Integer topicId;
	private Integer forumId;
	private Integer postId;

	private Integer postsFirstRecord;
	private Integer postsLength;
	private Long postsTotal;
	/**
	 * @return the completeDOM
	 */
	public Boolean getCompleteDOM() {
		return completeDOM;
	}
	/**
	 * @param completeDOM the completeDOM to set
	 */
	public void setCompleteDOM(Boolean completeDOM) {
		this.completeDOM = completeDOM;
	}
	/**
	 * @return the topicId
	 */
	public Integer getTopicId() {
		return topicId;
	}
	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}
	/**
	 * @return the forumId
	 */
	public Integer getForumId() {
		return forumId;
	}
	/**
	 * @param forumId the forumId to set
	 */
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}
	/**
	 * @return the postId
	 */
	public Integer getPostId() {
		return postId;
	}
	/**
	 * @param postId the postId to set
	 */
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	/**
	 * @return the postsFirstRecord
	 */
	public Integer getPostsFirstRecord() {
		return postsFirstRecord;
	}
	/**
	 * @param postsFirstRecord the postsFirstRecord to set
	 */
	public void setPostsFirstRecord(Integer postsFirstRecord) {
		this.postsFirstRecord = postsFirstRecord;
	}
	/**
	 * @return the postsLength
	 */
	public Integer getPostsLength() {
		return postsLength;
	}
	/**
	 * @param postsLength the postsLength to set
	 */
	public void setPostsLength(Integer postsLength) {
		this.postsLength = postsLength;
	}
	/**
	 * @return the postsTotal
	 */
	public Long getPostsTotal() {
		return postsTotal;
	}
	/**
	 * @param postsTotal the postsTotal to set
	 */
	public void setPostsTotal(Long postsTotal) {
		this.postsTotal = postsTotal;
	}

}
