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
	private Integer forumId;
	private Boolean completeDOM;

	private Integer postFirstRecord;
	private Integer postLength;
	private Long postTotal;

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
