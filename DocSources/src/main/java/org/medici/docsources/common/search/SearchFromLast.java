/*
 * SearchFromLast.java
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
package org.medici.docsources.common.search;

import java.io.Serializable;

import org.apache.lucene.search.Query;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public  class SearchFromLast implements Search {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7678720426728139824L;

	private FromLast fromLast;
	private SearchPerimeter searchPerimeter;

	/**
	 * Default constructor.
	 */
	public SearchFromLast() {
		super();
	}
	/**
	 * 
	 * @param fromLast
	 * @param searchPerimeter
	 */
	public SearchFromLast(FromLast fromLast, SearchPerimeter searchPerimeter) {
		super();
		setFromLast(fromLast);
		setSearchPerimeter(searchPerimeter);
	}

	public FromLast getFromLast() {
		return fromLast;
	}

	public void setSearchPerimeter(SearchPerimeter searchPerimeter) {
		this.searchPerimeter = searchPerimeter;
	}

	public SearchPerimeter getSearchPerimeter() {
		return searchPerimeter;
	}

	public void setFromLast(FromLast fromLast) {
		this.fromLast = fromLast;
	}

	@Override
	public Boolean isEmpty() {
		if (fromLast == null) {
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}

	/**
	 * 
	 */
	@Override
	public String toJPAQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * not implemented.
	 */
	@Override
	public Query toLuceneQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum FromLast implements Serializable {
		LASTMONTH("LASTMONTH"), LASTWEEK("LASTWEEK"), LOGON("LOGON");
		
		private final String searchType;

	    private FromLast(String value) {
	    	searchType = value;
	    }

	    @Override
	    public String toString(){
	        return searchType;
	    }
	}

	public static enum SearchPerimeter implements Serializable {
		DOCUMENT("DOCUMENT"), PEOPLE("PEOPLE"), PLACE("PLACE"), VOLUME("VOLUME");
		
		private final String searchType;

	    private SearchPerimeter(String value) {
	    	searchType = value;
	    }

	    @Override
	    public String toString(){
	        return searchType;
	    }
	}
}
