/*
 * SearchUserPaginationAjaxCommand.java
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
package org.medici.bia.command.user;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SearchUserPaginationAjaxCommand {
	private String iColumns;
	private String iDisplayLength;
	private String iDisplayStart;
	private String sColumns;
	private String sEcho;
	private String sSearch;
	/**
	 * @return the iColumns
	 */
	public String getiColumns() {
		return iColumns;
	}
	/**
	 * @return the iDisplayLength
	 */
	public String getiDisplayLength() {
		return iDisplayLength;
	}
	/**
	 * @return the iDisplayStart
	 */
	public String getiDisplayStart() {
		return iDisplayStart;
	}
	/**
	 * @return the sColumns
	 */
	public String getsColumns() {
		return sColumns;
	}
	/**
	 * @return the sEcho
	 */
	public String getsEcho() {
		return sEcho;
	}
	/**
	 * @return the sSearch
	 */
	public String getsSearch() {
		return sSearch;
	}
	/**
	 * @param iColumns the iColumns to set
	 */
	public void setiColumns(String iColumns) {
		this.iColumns = iColumns;
	}
	/**
	 * @param iDisplayLength the iDisplayLength to set
	 */
	public void setiDisplayLength(String iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	/**
	 * @param iDisplayStart the iDisplayStart to set
	 */
	public void setiDisplayStart(String iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	/**
	 * @param sColumns the sColumns to set
	 */
	public void setsColumns(String sColumns) {
		this.sColumns = sColumns;
	}
	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	/**
	 * @param sSearch the sSearch to set
	 */
	public void setsSearch(String sSearch) {
		this.sSearch = sSearch;
	}
	 
}
