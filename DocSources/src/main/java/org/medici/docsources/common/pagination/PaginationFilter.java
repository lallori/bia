 package org.medici.docsources.common.pagination;

 import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.lucene.search.SortField;
import org.medici.docsources.domain.SearchFilter.SearchType;

 /**
  * 
  * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
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

 	/**
 	 * 
 	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
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
 	    public String toString(){
 	        return order;
 	    }
 	    
 	    public String toStringJPQL(){
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
 	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 	 *
 	 */
     public class SortingCriteria {
         private String column;
         private Integer columnType;
         private Order order;

         public SortingCriteria(String column, Order order) {
             this.column = column;
             this.order = order;
             this.columnType = SortField.STRING;
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

     /**
      * 
      * @param firstRecord
      * @param length
      */
     public PaginationFilter(Integer firstRecord, Integer length) {
         this.firstRecord = firstRecord;
         this.length = length;
         this.sortingColumn = -1;
         this.sortingDirection="";
         this.sortingCriterias = new ArrayList<SortingCriteria>();
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
         this.sortingCriterias = new ArrayList<SortingCriteria>();
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

         this.sortingCriterias = new ArrayList<SortingCriteria>();
     }
     
     /**
      * 
      * @param field
      * @param order
      * @return
      */
     public void addSortingCriteria(String field, String direction) {
         this.sortingCriterias.add(new SortingCriteria(field, Order.valueOf(direction.toUpperCase())));
     }

     /**
      * 
      * @param field
      * @param order
      * @return
      */
     public void addSortingCriteria(String field, String direction, Integer fieldType) {
         this.sortingCriterias.add(new SortingCriteria(field, Order.valueOf(direction.toUpperCase()), fieldType));
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
 	 * @param searchType the searchType to set
 	 */
 	public void setSearchType(SearchType searchType) {
 		this.searchType = searchType;
 	}

 	/**
 	 * @param sortingColumn the sortingColumn to set
 	 */
 	public void setSortingColumn(Integer sortingColumn) {
 		this.sortingColumn = sortingColumn;
 	}

 	/**
 	 * @param sortingDirection the sortingDirection to set
 	 */
 	public void setSortingDirection(String sortingDirection) {
 		this.sortingDirection = sortingDirection;
 	}
     /**
 	 * @param total the total to set
 	 */
 	public void setTotal(Long total) {
 		this.total = total;
 	}
 }
