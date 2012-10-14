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
package org.medici.bia.common.pagination;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.lucene.search.SortField;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.domain.SearchFilter.SearchType;

/**
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class PaginationFilter {
	private Integer firstRecord;
	private Integer length;
	private SearchType searchType;
	private Integer sortingColumn;
	private List<SortingCriteria> sortingCriterias;
	private String sortingDirection;
	private Long total;
	private Integer thisPage;
	private Integer pageTotal;
	private Integer elementsForPage;

	/**
 	 * 
 	 */
	public PaginationFilter() {
		super();
		sortingColumn = -1;
		sortingDirection = "";
		sortingCriterias = new ArrayList<SortingCriteria>();
	}

	/**
	 * 
	 * @param firstRecord
	 * @param length
	 */
	public PaginationFilter(Integer firstRecord, Integer length) {
		this.firstRecord = firstRecord;
		this.length = length;
		sortingColumn = -1;
		sortingDirection = "";
		sortingCriterias = new ArrayList<SortingCriteria>();
	}

	/**
	 * 
	 * @param firstRecord2
	 * @param length2
	 * @param sortingColumnNumber
	 * @param sortingDirection2
	 */
	public PaginationFilter(Integer firstRecord, Integer length, Integer sortingColumnNumber, String sortingDirection) {
		this.firstRecord = firstRecord;
		this.length = length;
		this.setSortingColumn(sortingColumnNumber);
		this.setSortingDirection(sortingDirection);
		sortingCriterias = new ArrayList<SortingCriteria>();
	}

	/**
	 * 
	 * @param firstRecord
	 * @param length
	 * @param sortingColumn
	 */
	public PaginationFilter(Integer firstRecord, Integer length, Integer sortingColumn, String sortingDirection, SearchType searchType) {
		this.firstRecord = firstRecord;
		this.length = length;
		this.setSortingColumn(sortingColumn);
		this.setSortingDirection(sortingDirection);
		this.setSearchType(searchType);
		sortingCriterias = new ArrayList<SortingCriteria>();
	}

	/**
	 * 
	 * @param firstRecord2
	 * @param length2
	 * @param sortingColumnNumber
	 * @param sortingDirection2
	 * @param simpleSearchPerimeter
	 */
	public PaginationFilter(Integer firstRecord, Integer length, Integer sortingColumn, String sortingDirection, SimpleSearchPerimeter simpleSearchPerimeter) {
		this.firstRecord = firstRecord;
		this.length = length;
		this.setSortingColumn(sortingColumn);
		this.setSortingDirection(sortingDirection);
		sortingCriterias = new ArrayList<SortingCriteria>();
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
	 * 
	 * @param firstRecord
	 * @param length
	 * @param total
	 */
	public PaginationFilter(Integer firstRecord, Integer length, Long total) {
		this.firstRecord = (firstRecord == null) ? new Integer(1) : firstRecord;
		this.length = length;
		this.total = total;

		sortingCriterias = new ArrayList<SortingCriteria>();
	}

	/**
	 * 
	 * @param field
	 * @param order
	 * @return
	 */
	public void addSortingCriteria(String field, String direction) {
		sortingCriterias.add(new SortingCriteria(field, Order.valueOf(direction.toUpperCase())));
	}

	/**
	 * 
	 * @param field
	 * @param order
	 * @return
	 */
	public void addSortingCriteria(String field, String direction, Integer fieldType) {
		sortingCriterias.add(new SortingCriteria(field, Order.valueOf(direction.toUpperCase()), fieldType));
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

	/**
	 * @return the searchType
	 */
	public SearchType getSearchType() {
		return searchType;
	}

	/**
	 * @return the sortingColumn
	 */
	public Integer getSortingColumn() {
		return sortingColumn;
	}

	public List<SortingCriteria> getSortingCriterias() {
		return sortingCriterias;
	}

	/**
	 * @return the sortingDirection
	 */
	public String getSortingDirection() {
		return sortingDirection;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param searchType
	 *            the searchType to set
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	/**
	 * @param sortingColumn
	 *            the sortingColumn to set
	 */
	public void setSortingColumn(Integer sortingColumn) {
		this.sortingColumn = sortingColumn;
	}

	/**
	 * @param sortingDirection
	 *            the sortingDirection to set
	 */
	public void setSortingDirection(String sortingDirection) {
		this.sortingDirection = sortingDirection;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @param thisPage
	 *            the thisPage to set
	 */
	public void setThisPage(Integer thisPage) {
		this.thisPage = thisPage;
	}

	/**
	 * @return the thisPage
	 */
	public Integer getThisPage() {
		return thisPage;
	}

	/**
	 * @param pageTotal
	 *            the pageTotal to set
	 */
	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	/**
	 * @return the pageTotal
	 */
	public Integer getPageTotal() {
		return pageTotal;
	}

	/**
	 * @param elementsForPage
	 *            the elementsForPage to set
	 */
	public void setElementsForPage(Integer elementsForPage) {
		this.elementsForPage = elementsForPage;
	}

	/**
	 * @return the elementsForPage
	 */
	public Integer getElementsForPage() {
		return elementsForPage;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a
	 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	public static enum Order {
		ASC("ascending"), DESC("descending");

		private final String order;

		private Order(String value) {
			if (!ObjectUtils.toString(value).equals("")) {
				order = value.toUpperCase();
			} else {
				order = "ASC";
			}
		}

		@Override
		public String toString() {
			return order;
		}

		public String toStringJPQL() {
			if (!ObjectUtils.toString(order).equals("")) {
				if (order.equalsIgnoreCase("ascending")) {
					return "ASC";
				} else {
					return "DESC";
				}
			}
			return "";
		}
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a
	 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	public static class SortingCriteria {
		private String column;
		private Integer columnType;
		private Order order;

		public SortingCriteria() {
			super();
		}

		public SortingCriteria(String column, Order order) {
			this.column = column;
			this.order = order;
			columnType = SortField.STRING;
		}

		public SortingCriteria(String column, Order order, Integer columnType) {
			this.column = column;
			this.order = order;
			this.columnType = columnType;
		}

		/**
		 * @return the column
		 */
		public String getColumn() {
			return column;
		}

		/**
		 * @return the columnType
		 */
		public Integer getColumnType() {
			return columnType;
		}

		/**
		 * @return the order
		 */
		public Order getOrder() {
			return order;
		}
	}
}
