/*
 * AdvancedSearchFilter.java
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

/**
 * AdvancedSearchFilter entity.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblAdvancedSearchFilter\"" ) 
public class AdvancedSearchFilter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2001847076255349765L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column (name="\"idAdvancedSearchFilter\"", length=10, nullable=false)
	private Integer idAdvancedSearchFilter;

	@Column (name="\"username\"", length=50, nullable=false)
	private String username;

	@Column (name="\"filterType\"", length=1)
	@Enumerated(EnumType.STRING)
	private AdvancedSearchFilterType filterType;
	
	@Column (name="\"filterName\"", length=50, nullable=false)
	private String filterName;
	
	@Column (name="\"filterData\"")
	@Lob
	private AdvancedSearch filterData;

	@Column (name="\"dateCreated\"", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column (name="\"dateUpdated\"", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	public AdvancedSearchFilter() {
		super();
	}
	
	/**
	 * 
	 * @param document
	 */
	public AdvancedSearchFilter(AdvancedSearchFilterType filterType) {
		super();
		
		setFilterType(filterType);
	}

	/**
	 * @return the idAdvancedSearchFilter
	 */
	public Integer getIdAdvancedSearchFilter() {
		return idAdvancedSearchFilter;
	}

	/**
	 * @param idAdvancedSearchFilter the idAdvancedSearchFilter to set
	 */
	public void setIdAdvancedSearchFilter(Integer idAdvancedSearchFilter) {
		this.idAdvancedSearchFilter = idAdvancedSearchFilter;
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
	 * @param filterType the filterType to set
	 */
	public void setFilterType(AdvancedSearchFilterType filterType) {
		this.filterType = filterType;
	}

	/**
	 * @return the filterType
	 */
	public AdvancedSearchFilterType getFilterType() {
		return filterType;
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
	public static enum AdvancedSearchFilterType {
		VOLUME("VOLUME"), DOCUMENT("DOCUMENT"), PEOPLE("PEOPLE"), PLACE("PLACE");
		
		private final String advancedSearchFilterType;

	    private AdvancedSearchFilterType(String value) {
	    	advancedSearchFilterType = value;
	    }

	    @Override
	    public String toString(){
	        return advancedSearchFilterType;
	    }
	}
}
