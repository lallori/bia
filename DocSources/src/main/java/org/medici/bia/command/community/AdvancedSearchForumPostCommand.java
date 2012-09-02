/*
 * AdvancedSearchForumPostCommand.java
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

import java.util.List;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class AdvancedSearchForumPostCommand {

	private String text;
	private String textAuthor;
	private Boolean allTerms;
	private List<Integer> forumsId;
	private Boolean searchSubForums;
	private String wordsType;
	private String displayResults;
	private String sortResults;
	private String order;
	private Integer limitResults;
	private Integer returnFirst;
	private Integer idSearchFilter;
	private String searchUUID;
	
	private Integer resultsForPage;
	private Integer resultPageNumber;
	private Integer resultPageTotal;
	private Boolean newSearch;
	
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the textAuthor
	 */
	public String getTextAuthor() {
		return textAuthor;
	}
	
	/**
	 * @param textAuthor the textAuthor to set
	 */
	public void setTextAuthor(String textAuthor) {
		this.textAuthor = textAuthor;
	}
	
	/**
	 * @return the allTerms
	 */
	public Boolean getAllTerms() {
		return allTerms;
	}
	
	/**
	 * @param allTerms the allTerms to set
	 */
	public void setAllTerms(Boolean allTerms) {
		this.allTerms = allTerms;
	}

	/**
	 * @return the forumsId
	 */
	public List<Integer> getForumsId() {
		return forumsId;
	}

	/**
	 * @param forumsId the forumsId to set
	 */
	public void setForumsId(List<Integer> forumsId) {
		this.forumsId = forumsId;
	}

	/**
	 * @return the searchSubForums
	 */
	public Boolean getSearchSubForums() {
		return searchSubForums;
	}

	/**
	 * @param searchSubForums the searchSubForums to set
	 */
	public void setSearchSubForums(Boolean searchSubForums) {
		this.searchSubForums = searchSubForums;
	}

	/**
	 * @return the wordsType
	 */
	public String getWordsType() {
		return wordsType;
	}

	/**
	 * @param wordsType the wordsType to set
	 */
	public void setWordsType(String wordsType) {
		this.wordsType = wordsType;
	}

	/**
	 * @return the displayResults
	 */
	public String getDisplayResults() {
		return displayResults;
	}

	/**
	 * @param displayResults the displayResults to set
	 */
	public void setDisplayResults(String displayResults) {
		this.displayResults = displayResults;
	}

	/**
	 * @return the sortResults
	 */
	public String getSortResults() {
		return sortResults;
	}

	/**
	 * @param sortResults the sortResults to set
	 */
	public void setSortResults(String sortResults) {
		this.sortResults = sortResults;
	}

	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @return the limitResults
	 */
	public Integer getLimitResults() {
		return limitResults;
	}

	/**
	 * @param limitResults the limitResults to set
	 */
	public void setLimitResults(Integer limitResults) {
		this.limitResults = limitResults;
	}

	/**
	 * @return the returnFirst
	 */
	public Integer getReturnFirst() {
		return returnFirst;
	}

	/**
	 * @param returnFirst the returnFirst to set
	 */
	public void setReturnFirst(Integer returnFirst) {
		this.returnFirst = returnFirst;
	}

	/**
	 * @return the idSearchFilter
	 */
	public Integer getIdSearchFilter() {
		return idSearchFilter;
	}

	/**
	 * @param idSearchFilter the idSearchFilter to set
	 */
	public void setIdSearchFilter(Integer idSearchFilter) {
		this.idSearchFilter = idSearchFilter;
	}

	/**
	 * @return the searchUUID
	 */
	public String getSearchUUID() {
		return searchUUID;
	}

	/**
	 * @param searchUUID the searchUUID to set
	 */
	public void setSearchUUID(String searchUUID) {
		this.searchUUID = searchUUID;
	}

	/**
	 * @return the newSearch
	 */
	public Boolean getNewSearch() {
		return newSearch;
	}

	/**
	 * @param newSearch the newSearch to set
	 */
	public void setNewSearch(Boolean newSearch) {
		this.newSearch = newSearch;
	}
}
