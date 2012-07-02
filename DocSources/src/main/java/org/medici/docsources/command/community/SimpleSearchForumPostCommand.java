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

import java.util.UUID;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchForumPostCommand {
	private Boolean completeDOM;
	private String text;
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
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
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
}