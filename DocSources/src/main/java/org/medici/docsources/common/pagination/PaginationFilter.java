/*
 * PaginationFilter.java
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
public class PaginationFilter {
	public enum Order {
        ASCENDING,
        DESCENDING
    }

    public class SortingCriteria {
        private String column;
        private Order order;

        public SortingCriteria(String column, Order order) {
            this.column = column;
            this.order = order;
        }

        public String getColumn() {
            return column;
        }

        public Order getOrder() {
            return order;
        }
    }

    private Integer firstRecord;
    private Integer length;

	private List<SortingCriteria> sortingCriterias;
    private Long total;
    
    public PaginationFilter(Integer firstRecord, Integer length) {
        this.firstRecord = firstRecord;
        this.length = length;

        this.sortingCriterias = new ArrayList<SortingCriteria>();
    }

    public PaginationFilter(Integer firstRecord, Integer length, Long total) {
        this.firstRecord = (firstRecord == null) ? new Integer(0) : firstRecord;
        this.length = length;
        this.total = total;

        this.sortingCriterias = new ArrayList<SortingCriteria>();
    }
    
    public PaginationFilter addSortingCriteria(String field, Order order) {
        this.sortingCriterias.add(new SortingCriteria(field, order));
        return this;
    }

    /**
	 * @return the firstRecord
	 */
	public Integer getFirstRecord() {
		return firstRecord;
	}

    public Integer getLength() {
        return length;
    }

    public List<SortingCriteria> getSortingCriterias() {
        return sortingCriterias;
    }

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
}
