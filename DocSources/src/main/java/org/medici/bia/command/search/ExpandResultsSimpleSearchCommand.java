/*
 * ExpandResultsSimpleSearchCommand.java
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
package org.medici.bia.command.search;

import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class ExpandResultsSimpleSearchCommand {
	private SimpleSearchPerimeter simpleSearchPerimeter;
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	private String sSearch;
	private Integer iSortingCols;
	private Integer iSortCol_0;
	private String sSortDir_0;
	private String searchUUID;
	

	/**
	 * @param simpleSearchPerimeter the simple search perimeter to set
	 */
	public void setSimpleSearchPerimeter(SimpleSearchPerimeter simpleSearchPerimeter) {
		this.simpleSearchPerimeter = simpleSearchPerimeter;
	}
	
	/**
	 * @return the simpleSearchPerimeter
	 */
	public SimpleSearchPerimeter getSimpleSearchPerimeter() {
		return simpleSearchPerimeter;
	}

	/**
	 * @return the iDisplayStart
	 */
	public Integer getiDisplayStart() {
		return iDisplayStart;
	}

	/**
	 * @param iDisplayStart the iDisplayStart to set
	 */
	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	/**
	 * @return the iDisplayLength
	 */
	public Integer getiDisplayLength() {
		return iDisplayLength;
	}

	/**
	 * @param iDisplayLength the iDisplayLength to set
	 */
	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	/**
	 * @return the sSearch
	 */
	public String getsSearch() {
		return sSearch;
	}

	/**
	 * @param sSearch the sSearch to set
	 */
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}

	/**
	 * @return the iSortingCols
	 */
	public Integer getiSortingCols() {
		return iSortingCols;
	}

	/**
	 * @param iSortingCols the iSortingCols to set
	 */
	public void setiSortingCols(Integer iSortingCols) {
		this.iSortingCols = iSortingCols;
	}

	/**
	 * @return the iSortCol_0
	 */
	public Integer getiSortCol_0() {
		return iSortCol_0;
	}

	/**
	 * @param iSortCol_0 the iSortCol_0 to set
	 */
	public void setiSortCol_0(Integer iSortCol_0) {
		this.iSortCol_0 = iSortCol_0;
	}

	/**
	 * @return the sSortDir_0
	 */
	public String getsSortDir_0() {
		return sSortDir_0;
	}

	/**
	 * @param sSortDir_0 the sSortDir_0 to set
	 */
	public void setsSortDir_0(String sSortDir_0) {
		this.sSortDir_0 = sSortDir_0;
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
