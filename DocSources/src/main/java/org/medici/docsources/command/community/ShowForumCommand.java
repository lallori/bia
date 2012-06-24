/*
 * ShowForumCommand.java
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

import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class ShowForumCommand {
	private Integer id;
	private Boolean completeDOM;

	private Integer forumFirstRecord;
	private Integer forumLength;
	private Long forumTotal;

	private Integer postFirstRecord;
	private Integer postLength;
	private Long postTotal;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
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
	 * @return the forumFirstRecord
	 */
	public Integer getForumFirstRecord() {
		return forumFirstRecord;
	}
	/**
	 * @param forumFirstRecord the forumFirstRecord to set
	 */
	public void setForumFirstRecord(Integer forumFirstRecord) {
		this.forumFirstRecord = forumFirstRecord;
	}
	/**
	 * @return the forumLength
	 */
	public Integer getForumLength() {
		return forumLength;
	}
	/**
	 * @param forumLength the forumLength to set
	 */
	public void setForumLength(Integer forumLength) {
		this.forumLength = forumLength;
	}
	/**
	 * @return the forumTotal
	 */
	public Long getForumTotal() {
		return forumTotal;
	}
	/**
	 * @param forumTotal the forumTotal to set
	 */
	public void setForumTotal(Long forumTotal) {
		this.forumTotal = forumTotal;
	}
	/**
	 * @return the postFirstRecord
	 */
	public Integer getPostFirstRecord() {
		return postFirstRecord;
	}
	/**
	 * @param postFirstRecord the postFirstRecord to set
	 */
	public void setPostFirstRecord(Integer postFirstRecord) {
		this.postFirstRecord = postFirstRecord;
	}
	/**
	 * @return the postLength
	 */
	public Integer getPostLength() {
		return postLength;
	}
	/**
	 * @param postLength the postLength to set
	 */
	public void setPostLength(Integer postLength) {
		this.postLength = postLength;
	}
	/**
	 * @return the postTotal
	 */
	public Long getPostTotal() {
		return postTotal;
	}
	/**
	 * @param postTotal the postTotal to set
	 */
	public void setPostTotal(Long postTotal) {
		this.postTotal = postTotal;
	}

	
}
