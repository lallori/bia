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
package org.medici.bia.command.community;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class ShowForumCommand {
	private Boolean completeDOM;
	private Integer forumId;

	private Integer forumPageNumber;
	private Integer forumPageTotal;
	private Integer forumsForPage;
	
	private Integer topicPageNumber;
	private Integer topicPageTotal;
	private Integer topicsForPage;
	

	/**
	 * @return the completeDOM
	 */
	public Boolean getCompleteDOM() {
		return completeDOM;
	}
	
	/**
	 * @return the forumId
	 */
	public Integer getForumId() {
		return forumId;
	}
	
	/**
	 * @param completeDOM the completeDOM to set
	 */
	public void setCompleteDOM(Boolean completeDOM) {
		this.completeDOM = completeDOM;
	}
	
	/**
	 * @param forumId the forumId to set
	 */
	public void setForumId(Integer forumId) {
		this.forumId = forumId;
	}

	/**
	 * @return the forumPageNumber
	 */
	public Integer getForumPageNumber() {
		return forumPageNumber;
	}

	/**
	 * @param forumPageNumber the forumPageNumber to set
	 */
	public void setForumPageNumber(Integer forumPageNumber) {
		this.forumPageNumber = forumPageNumber;
	}

	/**
	 * @return the forumPageTotal
	 */
	public Integer getForumPageTotal() {
		return forumPageTotal;
	}

	/**
	 * @param forumPageTotal the forumPageTotal to set
	 */
	public void setForumPageTotal(Integer forumPageTotal) {
		this.forumPageTotal = forumPageTotal;
	}

	/**
	 * @return the forumsForPage
	 */
	public Integer getForumsForPage() {
		return forumsForPage;
	}

	/**
	 * @param forumsForPage the forumsForPage to set
	 */
	public void setForumsForPage(Integer forumsForPage) {
		this.forumsForPage = forumsForPage;
	}

	/**
	 * @return the topicPageNumber
	 */
	public Integer getTopicPageNumber() {
		return topicPageNumber;
	}

	/**
	 * @param topicPageNumber the topicPageNumber to set
	 */
	public void setTopicPageNumber(Integer topicPageNumber) {
		this.topicPageNumber = topicPageNumber;
	}

	/**
	 * @return the topicPageTotal
	 */
	public Integer getTopicPageTotal() {
		return topicPageTotal;
	}

	/**
	 * @param topicPageTotal the topicPageTotal to set
	 */
	public void setTopicPageTotal(Integer topicPageTotal) {
		this.topicPageTotal = topicPageTotal;
	}

	/**
	 * @return the topicsForPage
	 */
	public Integer getTopicsForPage() {
		return topicsForPage;
	}

	/**
	 * @param topicsForPage the topicsForPage to set
	 */
	public void setTopicsForPage(Integer topicsForPage) {
		this.topicsForPage = topicsForPage;
	}
	
}
