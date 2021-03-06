/*
 * SearchResult.java
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
package org.medici.bia.common.ajax;

import java.util.List;

import org.medici.bia.domain.User;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SearchResult {
	//private List aaData;
	private Integer iTotalDisplayRecords;
	private Integer iTotalRecords;
	private Integer sEcho;

	public SearchResult() {
		super();
	}

	/**
	 * @return the aaData
	 */
	/*public List getAaData() {
		return aaData;
	}
*/
	public SearchResult(List<User> list, Integer pageNumber, Integer pageSize) {
		setiTotalRecords(list.size());
		setiTotalDisplayRecords(0);
	/*	aaData = new ArrayList();
		for (int i=0; i<10; i++) {
			aaData.add(list.get(i));
		}*/
	}

	/**
	 * @return the iTotalDisplayRecords
	 */
	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	/**
	 * @return the iTotalRecords
	 */
	public Integer getiTotalRecords() {
		return iTotalRecords;
	}

	/**
	 * @return the sEcho
	 */
	public Integer getsEcho() {
		return sEcho;
	}
	
	/**
	 * @param aaData the aaData to set
	 */
	/*public void setAaData(List aaData) {
		this.aaData = aaData;
	}*/
	
	/**
	 * @param iTotalDisplayRecords the iTotalDisplayRecords to set
	 */
	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	
	/**
	 * @param iTotalRecords the iTotalRecords to set
	 */
	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	
	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(Integer sEcho) {
		this.sEcho = sEcho;
	}
	
}