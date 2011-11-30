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
	private Integer idSearchFilter;
	private SearchType searchType;
	/**
	 * @return the namesTypes
	 */
	public List<NameType> getNamesTypes() {
		return namesTypes;
	}
	/**
	 * @param namesTypes the namesTypes to set
	 */
	public void setNamesTypes(List<NameType> namesTypes) {
		this.namesTypes = namesTypes;
	}
	/**
	 * @return the names
	 */
	public List<String> getNames() {
		return names;
	}
	/**
	 * @param names the names to set
	 */
	public void setNames(List<String> names) {
		this.names = names;
	}
	/**
	 * @return the wordsTypes
	 */
	public List<WordType> getWordsTypes() {
		return wordsTypes;
	}
	/**
	 * @param wordsTypes the wordsTypes to set
	 */
	public void setWordsTypes(List<WordType> wordsTypes) {
		this.wordsTypes = wordsTypes;
	}
	/**
	 * @return the words
	 */
	public List<String> getWords() {
		return words;
	}
	/**
	 * @param words the words to set
	 */
	public void setWords(List<String> words) {
		this.words = words;
	}
	/**
	 * @return the datesDay
	 */
	public List<Integer> getDatesDay() {
		return datesDay;
	}
	/**
	 * @param datesDay the datesDay to set
	 */
	public void setDatesDay(List<Integer> datesDay) {
		this.datesDay = datesDay;
	}
	/**
	 * @return the datesMonth
	 */
	public List<Integer> getDatesMonth() {
		return datesMonth;
	}
	/**
	 * @param datesMonth the datesMonth to set
	 */
	public void setDatesMonth(List<Integer> datesMonth) {
		this.datesMonth = datesMonth;
	}
	/**
	 * @return the datesTypes
	 */
	public List<String> getDatesTypes() {
		return datesTypes;
	}
	/**
	 * @param datesTypes the datesTypes to set
	 */
	public void setDatesTypes(List<String> datesTypes) {
		this.datesTypes = datesTypes;
	}
	/**
	 * @return the datesYear
	 */
	public List<Integer> getDatesYear() {
		return datesYear;
	}
	/**
	 * @param datesYear the datesYear to set
	 */
	public void setDatesYear(List<Integer> datesYear) {
		this.datesYear = datesYear;
	}
	/**
	 * @return the datesYearBetween
	 */
	public List<Integer> getDatesYearBetween() {
		return datesYearBetween;
	}
	/**
	 * @param datesYearBetween the datesYearBetween to set
	 */
	public void setDatesYearBetween(List<Integer> datesYearBetween) {
		this.datesYearBetween = datesYearBetween;
	}
	/**
	 * @return the datesMonthBetween
	 */
	public List<Integer> getDatesMonthBetween() {
		return datesMonthBetween;
	}
	/**
	 * @param datesMonthBetween the datesMonthBetween to set
	 */
	public void setDatesMonthBetween(List<Integer> datesMonthBetween) {
		this.datesMonthBetween = datesMonthBetween;
	}
	/**
	 * @return the datesDayBetween
	 */
	public List<Integer> getDatesDayBetween() {
		return datesDayBetween;
	}
	/**
	 * @param datesDayBetween the datesDayBetween to set
	 */
	public void setDatesDayBetween(List<Integer> datesDayBetween) {
		this.datesDayBetween = datesDayBetween;
	}
	/**
	 * @return the placeId
	 */
	public List<Integer> getPlaceId() {
		return placeId;
	}
	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(List<Integer> placeId) {
		this.placeId = placeId;
	}
	/**
	 * @return the place
	 */
	public List<String> getPlace() {
		return place;
	}
	/**
	 * @param place the place to set
	 */
	public void setPlace(List<String> place) {
		this.place = place;
	}
	/**
	 * @return the roleCategories
	 */
	public List<String> getRoleCategories() {
		return roleCategories;
	}
	/**
	 * @param roleCategories the roleCategories to set
	 */
	public void setRoleCategories(List<String> roleCategories) {
		this.roleCategories = roleCategories;
	}
	/**
	 * @return the titlesOcc
	 */
	public List<String> getTitlesOcc() {
		return titlesOcc;
	}
	/**
	 * @param titlesOcc the titlesOcc to set
	 */
	public void setTitlesOcc(List<String> titlesOcc) {
		this.titlesOcc = titlesOcc;
	}
	/**
	 * @return the titlesOccId
	 */
	public List<Integer> getTitlesOccId() {
		return titlesOccId;
	}
	/**
	 * @param titlesOccId the titlesOccId to set
	 */
	public void setTitlesOccId(List<Integer> titlesOccId) {
		this.titlesOccId = titlesOccId;
	}
	private List<NameType> namesTypes;
	private List<String> names;
	private List<WordType> wordsTypes;
	private List<String> words;
	private List<Integer> datesDay;
	private List<Integer> datesMonth;
	private List<String> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<Integer> datesMonthBetween;
	private List<Integer> datesDayBetween;
	private List<Integer> placeId;
	private List<String> place;
	private List<String> roleCategories;
	private List<String> titlesOcc;
	private List<Integer> titlesOccId;
	
	/**
	 * @param idSearchFilter the idSearchFilter to set
	 */
	public void setIdSearchFilter(Integer idSearchFilter) {
		this.idSearchFilter = idSearchFilter;
	}
	/**
	 * @return the idSearchFilter
	 */
	public Integer getIdSearchFilter() {
		return idSearchFilter;
	}
	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}
	/**
	 * @return the searchType
	 */
	public SearchType getSearchType() {
		return searchType;
	}
	

}
