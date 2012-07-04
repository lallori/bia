/*
 * SaveUserSearchFilterCommand.java
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
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class SaveUserSearchFilterCommand {
	private Integer idSearchFilter;
	private SearchType searchType;
	private SaveType saveType;
	private String saveAs;
	private Integer idSearchFilterToReplace;
	private List<String> docId;
	private List<String> word;
	private List<String> volume;
	private List<String> folio;
	private List<String> date;
	private List<String> extract;
	private List<String> synopsis;
	private List<String> topic;
	private List<String> person;
	private List<String> place;
	private List<String> sender;
	private List<String> from;
	private List<String> recipient;
	private List<String> to;
	private List<String> refersTo;
	private List<String> name;
	private List<String> role;
	private List<String> placeName;
	private List<String> placeType;
	private List<String> linkedToTopics;
	private List<String> linkedToPeople;
	private List<Integer> datesDay;
	private List<Integer> datesDayBetween;
	private List<Integer> datesMonth;
	private List<Integer> datesMonthBetween;
	private List<String> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<String> names;
	private List<NameType> namesTypes;
	private List<Integer> placeId;
	private List<String> roleCategory;
	private List<String> titlesOcc;
	private List<Integer> titlesOccId;
	private List<String> words;
	private List<WordType> wordsTypes;
	private String digitized;
	private List<String> languages;
	private List<String> context;
	private List<String> fromVolume;
	private List<String> toVolume;
	private List<String> inventario;
	private String cipher;
	private String index;
	private List<String> nameParts;
	private List<String> occupationWord;
	private List<String> occupation;
	private List<String> researchNotes;
	private List<String> gender;

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
	 * @return the saveType
	 */
	public SaveType getSaveType() {
		return saveType;
	}

	/**
	 * @param saveType the saveType to set
	 */
	public void setSaveType(SaveType saveType) {
		this.saveType = saveType;
	}

	/**
	 * @return the saveAs
	 */
	public String getSaveAs() {
		return saveAs;
	}

	/**
	 * @param saveAs the saveAs to set
	 */
	public void setSaveAs(String saveAs) {
		this.saveAs = saveAs;
	}

	/**
	 * @return the idSearchFilterToReplace
	 */
	public Integer getIdSearchFilterToReplace() {
		return idSearchFilterToReplace;
	}

	/**
	 * @param idSearchFilterToReplace the idSearchFilterToReplace to set
	 */
	public void setIdSearchFilterToReplace(Integer idSearchFilterToReplace) {
		this.idSearchFilterToReplace = idSearchFilterToReplace;
	}

	/**
	 * @return the word
	 */
	public List<String> getWord() {
		return word;
	}

	/**
	 * @param word the word to set
	 */
	public void setWord(List<String> word) {
		this.word = word;
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
	 * @return the extract
	 */
	public List<String> getExtract() {
		return extract;
	}

	/**
	 * @param extract the extract to set
	 */
	public void setExtract(List<String> extract) {
		this.extract = extract;
	}

	/**
	 * @return the synopsis
	 */
	public List<String> getSynopsis() {
		return synopsis;
	}

	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(List<String> synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * @return the topic
	 */
	public List<String> getTopic() {
		return topic;
	}

	/**
	 * @param topic the topic to set
	 */
	public void setTopic(List<String> topic) {
		this.topic = topic;
	}

	/**
	 * @return the person
	 */
	public List<String> getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(List<String> person) {
		this.person = person;
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
	 * @return the sender
	 */
	public List<String> getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(List<String> sender) {
		this.sender = sender;
	}

	/**
	 * @return the from
	 */
	public List<String> getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(List<String> from) {
		this.from = from;
	}

	/**
	 * @return the recipient
	 */
	public List<String> getRecipient() {
		return recipient;
	}

	/**
	 * @param recipient the recipient to set
	 */
	public void setRecipient(List<String> recipient) {
		this.recipient = recipient;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @return the refersTo
	 */
	public List<String> getRefersTo() {
		return refersTo;
	}

	/**
	 * @param refersTo the refersTo to set
	 */
	public void setRefersTo(List<String> refersTo) {
		this.refersTo = refersTo;
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
	 * @return the roleCategories
	 */
	public List<String> getRoleCategory() {
		return roleCategory;
	}

	/**
	 * @param roleCategories the roleCategories to set
	 */
	public void setRoleCategory(List<String> roleCategory) {
		this.roleCategory = roleCategory;
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
	 * @return the folio
	 */
	public List<String> getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(List<String> folio) {
		this.folio = folio;
	}

	/**
	 * @return the name
	 */
	public List<String> getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(List<String> name) {
		this.name = name;
	}

	/**
	 * @return the role
	 */
	public List<String> getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(List<String> role) {
		this.role = role;
	}

	/**
	 * @return the placeName
	 */
	public List<String> getPlaceName() {
		return placeName;
	}

	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(List<String> placeName) {
		this.placeName = placeName;
	}

	/**
	 * @return the placeType
	 */
	public List<String> getPlaceType() {
		return placeType;
	}

	/**
	 * @param placeType the placeType to set
	 */
	public void setPlaceType(List<String> placeType) {
		this.placeType = placeType;
	}

	/**
	 * @return the linkedToTopics
	 */
	public List<String> getLinkedToTopics() {
		return linkedToTopics;
	}

	/**
	 * @param linkedToTopics the linkedToTopics to set
	 */
	public void setLinkedToTopics(List<String> linkedToTopics) {
		this.linkedToTopics = linkedToTopics;
	}

	/**
	 * @return the linkedToPeople
	 */
	public List<String> getLinkedToPeople() {
		return linkedToPeople;
	}

	/**
	 * @param linkedToPeople the linkedToPeople to set
	 */
	public void setLinkedToPeople(List<String> linkedToPeople) {
		this.linkedToPeople = linkedToPeople;
	}

	/**
	 * @return the nameParts
	 */
	public List<String> getNameParts() {
		return nameParts;
	}

	/**
	 * @param nameParts the nameParts to set
	 */
	public void setNameParts(List<String> nameParts) {
		this.nameParts = nameParts;
	}

	/**
	 * @return the occupationWord
	 */
	public List<String> getOccupationWord() {
		return occupationWord;
	}

	/**
	 * @param occupationWord the occupationWord to set
	 */
	public void setOccupationWord(List<String> occupationWord) {
		this.occupationWord = occupationWord;
	}

	/**
	 * @return the occupation
	 */
	public List<String> getOccupation() {
		return occupation;
	}

	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(List<String> occupation) {
		this.occupation = occupation;
	}

	/**
	 * @return the researchNotes
	 */
	public List<String> getResearchNotes() {
		return researchNotes;
	}

	/**
	 * @param researchNotes the researchNotes to set
	 */
	public void setResearchNotes(List<String> researchNotes) {
		this.researchNotes = researchNotes;
	}

	/**
	 * @return the gender
	 */
	public List<String> getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(List<String> gender) {
		this.gender = gender;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @param docId the docId to set
	 */
	public void setDocId(List<String> docId) {
		this.docId = docId;
	}

	/**
	 * @return the docId
	 */
	public List<String> getDocId() {
		return docId;
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum SaveType {
		newSearch("newSearch"), replaceSearch("replaceSearch");
		
		private final String saveType;

	    private SaveType(String value) {
	        saveType = value;
	    }

	    @Override
	    public String toString(){
	        return saveType;
	    }
	}
}
