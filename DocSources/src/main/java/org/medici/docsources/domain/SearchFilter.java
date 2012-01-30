/*
 * SearchFilter.java
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
package org.medici.docsources.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.medici.docsources.common.search.AdvancedSearch;
import org.medici.docsources.common.search.SimpleSearch.SimpleSearchPerimeter;

/**
 * SearchFilter entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblSearchFilter\"" ) 
public class SearchFilter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7879646812872287396L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column (name="\"id\"", length=10, nullable=false)
	private Integer id;

	@Column (name="\"username\"", length=20, nullable=false)
	private String username;

	@Column (name="\"searchType\"", length=15)
	@Enumerated(EnumType.STRING)
	private SearchType searchType;
	
	@Column (name="\"filterName\"", length=50, nullable=false)
	private String filterName;
	
	@Column (name="\"filterData\"")
	@Lob
	private AdvancedSearch filterData;

	@Column (name="\"totalResult\"", nullable=false)
	private Long totalResult;
	
	@Column (name="\"dateCreated\"", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column (name="\"dateUpdated\"", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	/**
	 * Default constructor
	 */
	public SearchFilter() {
		super();
	}
	
	/**
	 * 
	 * @param searchFilterType
	 */
	public SearchFilter(Integer id, SearchType searchType) {
		super();
		
		setId(id);
		setSearchType(searchType);
	}
	
	/**
	 * 
	 * @param searchFilterType
	 */
	public SearchFilter(SearchType searchType) {
		super();
		
		setSearchType(searchType);
	}

	/**
	 * 
	 * @param simpleSearchPerimeter
	 */
	public SearchFilter(SimpleSearchPerimeter simpleSearchPerimeter) {
		super();
		
		 if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT)) {
        	 this.setSearchType(SearchType.DOCUMENT);
         } else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)) {
        	 this.setSearchType(SearchType.DOCUMENT);
         } else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.PEOPLE)) {
        	 this.setSearchType(SearchType.PEOPLE);
         } else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.PLACE)) {
        	 this.setSearchType(SearchType.PLACE);
         } else if (simpleSearchPerimeter.equals(SimpleSearchPerimeter.VOLUME)) {
        	 this.setSearchType(SearchType.VOLUME);
         }
	}

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
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	/**
	 * @return the searchType
	 */
	public SearchType getSearchType() {
		return searchType;
	}

	/**
	 * @return the filterName
	 */
	public String getFilterName() {
		return filterName;
	}

	/**
	 * @param filterName the filterName to set
	 */
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	/**
	 * @return the filterData
	 */
	public AdvancedSearch getFilterData() {
		return filterData;
	}

	/**
	 * @param filterData the filterData to set
	 */
	public void setFilterData(AdvancedSearch filterData) {
		this.filterData = filterData;
	}

	/**
	 * @param totalResult the totalResult to set
	 */
	public void setTotalResult(Long totalResult) {
		this.totalResult = totalResult;
	}

	/**
	 * @return the totalResult
	 */
	public Long getTotalResult() {
		return totalResult;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the dateUpdated
	 */
	public Date getDateUpdated() {
		return dateUpdated;
	}

	/**
	 * @param dateUpdated the dateUpdated to set
	 */
	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum SearchType implements Serializable {
		VOLUME("VOLUME"), DOCUMENT("DOCUMENT"), PEOPLE("PEOPLE"), PLACE("PLACE");
		
		private final String searchType;

	    private SearchType(String value) {
	    	searchType = value;
	    }

	    @Override
	    public String toString(){
	        return searchType;
	    }
	}
}
