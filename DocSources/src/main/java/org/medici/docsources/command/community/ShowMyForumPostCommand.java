/*
 * ShowMyForumPostCommand.java
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
public class ShowMyForumPostCommand {
	private Boolean completeDOM;

	private Integer postPageNumber;
	private Integer postPageTotal;
	private Integer postsForPage;
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
	 * @return the postPageNumber
	 */
	public Integer getPostPageNumber() {
		return postPageNumber;
	}
	/**
	 * @param postPageNumber the postPageNumber to set
	 */
	public void setPostPageNumber(Integer postPageNumber) {
		this.postPageNumber = postPageNumber;
	}
	/**
	 * @return the postPageTotal
	 */
	public Integer getPostPageTotal() {
		return postPageTotal;
	}
	/**
	 * @param postPageTotal the postPageTotal to set
	 */
	public void setPostPageTotal(Integer postPageTotal) {
		this.postPageTotal = postPageTotal;
	}
	/**
	 * @return the postsForPage
	 */
	public Integer getPostsForPage() {
		return postsForPage;
	}
	/**
	 * @param postsForPage the postsForPage to set
	 */
	public void setPostsForPage(Integer postsForPage) {
		this.postsForPage = postsForPage;
	}
	
}
