/*
 * SimpleSearchForumPostCommand.java
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class SimpleSearchForumPostCommand {
	private Boolean completeDOM;
	private String searchForumAllText;
	private String searchUUID;

	private Integer resultsForPage;
	private Integer resultPageNumber;
	private Integer resultPageTotal;

	/**
	 * @return the resultsForPage
	 */
	public Integer getResultsForPage() {
		return resultsForPage;
	}

	/**
	 * @param resultsForPage the resultsForPage to set
	 */
	public void setResultsForPage(Integer resultsForPage) {
		this.resultsForPage = resultsForPage;
	}

	/**
	 * @return the resultPageNumber
	 */
	public Integer getResultPageNumber() {
		return resultPageNumber;
	}

	/**
	 * @param resultPageNumber the resultPageNumber to set
	 */
	public void setResultPageNumber(Integer resultPageNumber) {
		this.resultPageNumber = resultPageNumber;
	}

	/**
	 * @return the resultPageTotal
	 */
	public Integer getResultPageTotal() {
		return resultPageTotal;
	}

	/**
	 * @param resultPageTotal the resultPageTotal to set
	 */
	public void setResultPageTotal(Integer resultPageTotal) {
		this.resultPageTotal = resultPageTotal;
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
	 * @param searchUUID the searchUUID to set
	 */
	public void setSearchUUID(String searchUUID) {
		this.searchUUID = searchUUID;
	}

	/**
	 * @return the searchUUID
	 */
	public String getSearchUUID() {
		return searchUUID;
	}

	/**
	 * @param searchForumAllText the searchForumAllText to set
	 */
	public void setSearchForumAllText(String searchForumAllText) {
		this.searchForumAllText = searchForumAllText;
	}

	/**
	 * @return the searchForumAllText
	 */
	public String getSearchForumAllText() {
		return searchForumAllText;
	}
}
