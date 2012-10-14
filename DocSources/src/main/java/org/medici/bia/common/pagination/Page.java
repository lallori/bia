/*
 * Page.java
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
package org.medici.bia.common.pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class Page {
	private Integer firstRecordNumber;
	private Integer lastRecordNumber;
	private Integer elementsForPage;
	private List<?> results;
	private Integer thisPage;
	private Long total;
	private Integer totalPages;

	public Page() {
		super();
	}

	/**
	 * 
	 * @param inputListResult
	 * @param page
	 */
	public Page(List<?> list, Integer firstRecordNumber, Integer lastRecordNumber) {
		this.setTotal(new Long(list.size()));
		results = list;
	}

	/**
	 * 
	 * @param list
	 * @param totalResult
	 * @param page
	 */
	public Page(List<?> list, Long totalResult, Integer firstRecordNumber, Integer lastRecordNumber) {
		this.setTotal(totalResult);
		results = list;
		this.firstRecordNumber = firstRecordNumber;
		this.lastRecordNumber = lastRecordNumber;
	}
	
	/**
	 * 
	 * @param paginationFilter
	 */
	@SuppressWarnings("rawtypes")
	public Page(PaginationFilter paginationFilter) {
		if (paginationFilter == null) {
			setTotal(new Long(0));
			results = new ArrayList(0);
			firstRecordNumber = null;
			lastRecordNumber = null;
			elementsForPage = new Integer(10);
			thisPage = new Integer(1);
		} else {
			setTotal(paginationFilter.getTotal());
			results = new ArrayList(0);
			firstRecordNumber = paginationFilter.getFirstRecord();
			lastRecordNumber = paginationFilter.getLength();
			elementsForPage = paginationFilter.getElementsForPage();
			thisPage = paginationFilter.getThisPage();
		}
	}

	/**
	 * @return the firstRecordNumber
	 */
	public Integer getFirstRecordNumber() {
		return firstRecordNumber;
	}
	
	/**
	 * @return the lastRecordNumber
	 */
	public Integer getLastRecordNumber() {
		return lastRecordNumber;
	}

	public List<?> getList() {
		return results;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getElementsForPage() {
		return elementsForPage;
	}

	/**
	 * @return the thisPage
	 */
	public Integer getThisPage() {
		return thisPage;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}


	/**
	 * @param firstRecordNumber the firstRecordNumber to set
	 */
	public void setFirstRecordNumber(Integer firstRecordNumber) {
		this.firstRecordNumber = firstRecordNumber;
	}

	/**
	 * @param lastRecordNumber the lastRecordNumber to set
	 */
	public void setLastRecordNumber(Integer lastRecordNumber) {
		this.lastRecordNumber = lastRecordNumber;
	}

	/**
	 * 
	 * @param list
	 */
	public void setList(List<?> list) {
		results = list;
	}

	/**
	 * @param elementsForPage the elementsForPage to set
	 */
	public void setElementsForPage(Integer elementsForPage) {
		this.elementsForPage = elementsForPage;
	}

	/**
	 * @param thisPage the thisPage to set
	 */
	public void setThisPage(Integer thisPage) {
		this.thisPage = thisPage;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * @return the totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("[");
		stringBuilder.append("elementsForPage=");
		stringBuilder.append(getElementsForPage());
		stringBuilder.append(",firstRecordNumber=");
		stringBuilder.append(getFirstRecordNumber());
		stringBuilder.append(",lastRecordNumber=");
		stringBuilder.append(getLastRecordNumber());
		stringBuilder.append(",thisPage=");
		stringBuilder.append(getThisPage());
		stringBuilder.append(",total=");
		stringBuilder.append(getTotal());
		stringBuilder.append(",totalPages=");
		stringBuilder.append(getTotalPages());
		stringBuilder.append(']');
		return stringBuilder.toString();
	}
}
