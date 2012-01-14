/*
 * AdvancedSearchDocumentsCommand.java
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
package org.medici.docsources.command.search;

import java.util.List;

import org.medici.docsources.domain.SearchFilter.SearchType;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchVolumesCommand {
	private Integer idSearchFilter;
	private SearchType searchType;
	private List<String> volume;
	private List<String> date;
	private String digitized;
	private List<String> languages;
	private List<String> context;
	private List<String> fromVolume;
	private List<String> toVolume;
	private List<String> inventario;
	private String cipher;
	private String index;
	private Boolean logicalDelete;
	
	/**
	 * @return the idSearchFilter
	 */
	public Integer getIdSearchFilter() {
		return idSearchFilter;
	}
	/**
	 * @param idSearchFilter the idSearchFilter to set
	 */
	public void setIdSearchFilter(Integer idSearchFilter) {
		this.idSearchFilter = idSearchFilter;
	}
	/**
	 * @return the searchType
	 */
	public SearchType getSearchType() {
		return searchType;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the volume
	 */
	public List<String> getVolume() {
		return volume;
	}
	/**
	 * @param volume the volume to set
	 */
	public void setVolume(List<String> volume) {
		this.volume = volume;
	}
	/**
	 * @return the date
	 */
	public List<String> getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(List<String> date) {
		this.date = date;
	}
	/**
	 * @return the digitized
	 */
	public String getDigitized() {
		return digitized;
	}
	/**
	 * @param digitized the digitized to set
	 */
	public void setDigitized(String digitized) {
		this.digitized = digitized;
	}
	/**
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}
	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}
	/**
	 * @return the context
	 */
	public List<String> getContext() {
		return context;
	}
	/**
	 * @param context the context to set
	 */
	public void setContext(List<String> context) {
		this.context = context;
	}
	/**
	 * @return the fromVolume
	 */
	public List<String> getFromVolume() {
		return fromVolume;
	}
	/**
	 * @param fromVolume the fromVolume to set
	 */
	public void setFromVolume(List<String> fromVolume) {
		this.fromVolume = fromVolume;
	}
	/**
	 * @return the toVolume
	 */
	public List<String> getToVolume() {
		return toVolume;
	}
	/**
	 * @param toVolume the toVolume to set
	 */
	public void setToVolume(List<String> toVolume) {
		this.toVolume = toVolume;
	}
	/**
	 * @return the inventario
	 */
	public List<String> getInventario() {
		return inventario;
	}
	/**
	 * @param inventario the inventario to set
	 */
	public void setInventario(List<String> inventario) {
		this.inventario = inventario;
	}
	/**
	 * @return the cipher
	 */
	public String getCipher() {
		return cipher;
	}
	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(String cipher) {
		this.cipher = cipher;
	}
	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * 
	 * @param logicalDelete
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}
	
	/**
	 * 
	 * @return
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}

}
