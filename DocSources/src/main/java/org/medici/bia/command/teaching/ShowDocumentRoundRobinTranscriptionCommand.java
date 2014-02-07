/*
 * ShowDocumentRoundRobinTranscriptionCommand.java
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
package org.medici.bia.command.teaching;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ShowDocumentRoundRobinTranscriptionCommand {

	private Integer entryId;
	
	private String courseTitle;
	
	private Integer topicId;
	
	private Boolean completeDOM;
	
	private Integer postsForPage;
	
	private Integer postPageNumber;
	
	private Integer postPageTotal;
	
	private Boolean editingMode;

	public Integer getEntryId() {
		return entryId;
	}

	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public Boolean getCompleteDOM() {
		return completeDOM;
	}

	public void setCompleteDOM(Boolean completeDOM) {
		this.completeDOM = completeDOM;
	}

	public Integer getPostsForPage() {
		return postsForPage;
	}

	public void setPostsForPage(Integer postsForPage) {
		this.postsForPage = postsForPage;
	}

	public Integer getPostPageNumber() {
		return postPageNumber;
	}

	public void setPostPageNumber(Integer postPageNumber) {
		this.postPageNumber = postPageNumber;
	}

	public Integer getPostPageTotal() {
		return postPageTotal;
	}

	public void setPostPageTotal(Integer postPageTotal) {
		this.postPageTotal = postPageTotal;
	}

	public Boolean getEditingMode() {
		return editingMode;
	}

	public void setEditingMode(Boolean editingMode) {
		this.editingMode = editingMode;
	}
}
