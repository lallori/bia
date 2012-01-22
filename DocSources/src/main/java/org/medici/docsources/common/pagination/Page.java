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
package org.medici.docsources.common.pagination;

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
	private Integer pageSize;
	private List<?> results;
	private Long total;	
	
	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 
	 * @param inputListResult
	 * @param page
	 */
	public Page(List<?> list, Integer firstRecordNumber, Integer lastRecordNumber) {
		this.setTotal(new Long(list.size()));
		this.results = list;
	}

	/**
	 * 
	 * @param list
	 * @param totalResult
	 * @param page
	 */
	public Page(List<?> list, Long totalResult, Integer firstRecordNumber, Integer lastRecordNumber) {
		this.setTotal(totalResult);
		this.results = list;
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
		} else {
			setTotal(paginationFilter.getTotal());
			results = new ArrayList(0);
			firstRecordNumber = paginationFilter.getFirstRecord();
			lastRecordNumber = paginationFilter.getLength();
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
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNextPage() {
		return results.size() > pageSize;
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

	public void setList(List<?> list) {
		this.results = list;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
}
