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

import org.medici.docsources.common.search.AdvancedSearchAbstract.NameType;
import org.medici.docsources.common.search.AdvancedSearchAbstract.WordType;
import org.medici.docsources.domain.SearchFilter.SearchType;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchPeopleCommand {
	private List<Integer> datesDay;
	private List<Integer> datesDayBetween;
	private List<Integer> datesMonth;
	private List<Integer> datesMonthBetween;
	private List<String> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private Integer idSearchFilter;
	private Boolean logicalDelete;
	private List<String> names;
	private List<NameType> namesTypes;
	private List<String> place;
	private List<Integer> placeId;
	private List<String> roleCategories;
	private SearchType searchType;
	private List<String> titlesOcc;
	private List<Integer> titlesOccId;
	private List<String> words;
	private List<WordType> wordsTypes;

	/**
	 * @return the datesDay
	 */
	public List<Integer> getDatesDay() {
		return datesDay;
	}
	/**
	 * @return the datesDayBetween
	 */
	public List<Integer> getDatesDayBetween() {
		return datesDayBetween;
	}
	/**
	 * @return the datesMonth
	 */
	public List<Integer> getDatesMonth() {
		return datesMonth;
	}
	/**
	 * @return the datesMonthBetween
	 */
	public List<Integer> getDatesMonthBetween() {
		return datesMonthBetween;
	}
	/**
	 * @return the datesTypes
	 */
	public List<String> getDatesTypes() {
		return datesTypes;
	}
	/**
	 * @return the datesYear
	 */
	public List<Integer> getDatesYear() {
		return datesYear;
	}
	/**
	 * @return the datesYearBetween
	 */
	public List<Integer> getDatesYearBetween() {
		return datesYearBetween;
	}
	/**
	 * @return the idSearchFilter
	 */
	public Integer getIdSearchFilter() {
		return idSearchFilter;
	}
	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}
	/**
	 * @return the names
	 */
	public List<String> getNames() {
		return names;
	}
	/**
	 * @return the namesTypes
	 */
	public List<NameType> getNamesTypes() {
		return namesTypes;
	}
	/**
	 * @return the place
	 */
	public List<String> getPlace() {
		return place;
	}
	/**
	 * @return the placeId
	 */
	public List<Integer> getPlaceId() {
		return placeId;
	}
	/**
	 * @return the roleCategories
	 */
	public List<String> getRoleCategories() {
		return roleCategories;
	}
	/**
	 * @return the searchType
	 */
	public SearchType getSearchType() {
		return searchType;
	}
	/**
	 * @return the titlesOcc
	 */
	public List<String> getTitlesOcc() {
		return titlesOcc;
	}
	/**
	 * @return the titlesOccId
	 */
	public List<Integer> getTitlesOccId() {
		return titlesOccId;
	}
	/**
	 * @return the words
	 */
	public List<String> getWords() {
		return words;
	}
	/**
	 * @return the wordsTypes
	 */
	public List<WordType> getWordsTypes() {
		return wordsTypes;
	}
	/**
	 * @param datesDay the datesDay to set
	 */
	public void setDatesDay(List<Integer> datesDay) {
		this.datesDay = datesDay;
	}
	/**
	 * @param datesDayBetween the datesDayBetween to set
	 */
	public void setDatesDayBetween(List<Integer> datesDayBetween) {
		this.datesDayBetween = datesDayBetween;
	}
	/**
	 * @param datesMonth the datesMonth to set
	 */
	public void setDatesMonth(List<Integer> datesMonth) {
		this.datesMonth = datesMonth;
	}
	/**
	 * @param datesMonthBetween the datesMonthBetween to set
	 */
	public void setDatesMonthBetween(List<Integer> datesMonthBetween) {
		this.datesMonthBetween = datesMonthBetween;
	}
	/**
	 * @param datesTypes the datesTypes to set
	 */
	public void setDatesTypes(List<String> datesTypes) {
		this.datesTypes = datesTypes;
	}
	/**
	 * @param datesYear the datesYear to set
	 */
	public void setDatesYear(List<Integer> datesYear) {
		this.datesYear = datesYear;
	}
	/**
	 * @param datesYearBetween the datesYearBetween to set
	 */
	public void setDatesYearBetween(List<Integer> datesYearBetween) {
		this.datesYearBetween = datesYearBetween;
	}
	/**
	 * @param idSearchFilter the idSearchFilter to set
	 */
	public void setIdSearchFilter(Integer idSearchFilter) {
		this.idSearchFilter = idSearchFilter;
	}
	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}
	/**
	 * @param names the names to set
	 */
	public void setNames(List<String> names) {
		this.names = names;
	}
	/**
	 * @param namesTypes the namesTypes to set
	 */
	public void setNamesTypes(List<NameType> namesTypes) {
		this.namesTypes = namesTypes;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(List<String> place) {
		this.place = place;
	}
	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(List<Integer> placeId) {
		this.placeId = placeId;
	}
	
	/**
	 * @param roleCategories the roleCategories to set
	 */
	public void setRoleCategories(List<String> roleCategories) {
		this.roleCategories = roleCategories;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	/**
	 * @param titlesOcc the titlesOcc to set
	 */
	public void setTitlesOcc(List<String> titlesOcc) {
		this.titlesOcc = titlesOcc;
	}
	/**
	 * @param titlesOccId the titlesOccId to set
	 */
	public void setTitlesOccId(List<Integer> titlesOccId) {
		this.titlesOccId = titlesOccId;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(List<String> words) {
		this.words = words;
	}
	/**
	 * @param wordsTypes the wordsTypes to set
	 */
	public void setWordsTypes(List<WordType> wordsTypes) {
		this.wordsTypes = wordsTypes;
	}
	

}
