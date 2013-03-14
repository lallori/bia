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
package org.medici.bia.command.search;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.search.AdvancedSearch;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.domain.SearchFilter.SearchType;


/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class AdvancedSearchCommand {
	private Integer idSearchFilter;
	private SearchType searchType;
	private List<String> docId;
	private List<String> word;
	private List<String> volume;
	private List<String> folio;
	private List<String> folioMod;
	private List<String> date;
	private List<String> dateLastUpdate;
	private List<String> dateCreated;
	private List<String> extract;
	private List<String> synopsis;
	private List<String> topic;
	private List<String> topicPlace;
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
	private List<String> exactPlaceName;
	private List<String> placeType;
	private List<String> linkedToTopics;
	private List<String> linkedToPeople;
	private List<String> placeId;
	private String searchUUID;
	
	private String digitized;
	private List<String> languages;
	private List<String> otherLang;
	private List<String> context;
	private List<String> fromVolume;
	private List<String> toVolume;
	private List<String> inventario;
	private String cipher;
	private String index;
	private List<String> volumeId;
	
	private List<String> nameParts;
	private List<String> exactName;
	private List<String> roleCategory;
	private List<String> occupationWord;
	private List<String> occupation;
	private List<String> researchNotes;
	private List<String> gender;
	private List<String> personId;

	private String logicalDelete;
	
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public AdvancedSearchCommand() {
		super();
	}

	/**
	 * 
	 * @param advancedSearch
	 * @param searchType
	 * @param searchUUID
	 */
	public AdvancedSearchCommand(AdvancedSearch advancedSearch, SearchType searchType, String searchUUID) {
		try {
			BeanUtils.copyProperties(this, advancedSearch);
		} catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}
		
		setSearchType(searchType);
		setSearchUUID(searchUUID);
	}

	/**
	 * 
	 * @param advancedSearch
	 * @param simpleSearchPerimeter
	 * @param searchUUID2
	 */
	public AdvancedSearchCommand(AdvancedSearch advancedSearch, SimpleSearchPerimeter simpleSearchPerimeter, String searchUUID2) {
		try {
			BeanUtils.copyProperties(this, advancedSearch);
		} catch (IllegalAccessException illegalAccessException) {
			logger.error(illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			logger.error(invocationTargetException);
		}

		setSearchUUID(searchUUID2);

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

	/**
	 * @param docId the entryId to set
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
	 * @return the folioMod
	 */
	public List<String> getFolioMod() {
		return folioMod;
	}

	/**
	 * @param folioMod the folioMod to set
	 */
	public void setFolioMod(List<String> folioMod) {
		this.folioMod = folioMod;
	}

	/**
	 * @return the date
	 */
	public List<String> getDate() {
		return date;
	}
	
	/**
	 * @param dateType the datesType to set
	 */
	public void setDate(List<String> date) {
		this.date = date;
	}
	
	public void setDateLastUpdate(List<String> dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}

	public List<String> getDateLastUpdate() {
		return dateLastUpdate;
	}

	public void setDateCreated(List<String> dateCreated) {
		this.dateCreated = dateCreated;
	}

	public List<String> getDateCreated() {
		return dateCreated;
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
	 * @return the topics
	 */
	public List<String> getTopic() {
		return topic;
	}
	
	/**
	 * @param topics the topics to set
	 */
	public void setTopic(List<String> topic) {
		this.topic = topic;
	}
	
	/**
	 * @param topicPlace the topicPlace to set
	 */
	public void setTopicPlace(List<String> topicPlace) {
		this.topicPlace = topicPlace;
	}

	/**
	 * @return the topicPlace
	 */
	public List<String> getTopicPlace() {
		return topicPlace;
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
	 * @return the resTo
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
	 * @param searchUUID the searchUUID to set
	 */
	public void setSearchUUID(String searchUUID) {
		this.searchUUID = searchUUID;
	}

	/**
	 * @return the searchUUID
	 */
	public String getSearchUUID() {
		return searchUUID;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(List<String> name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public List<String> getName() {
		return name;
	}

	/**
	 * @return the exactName
	 */
	public List<String> getExactName() {
		return exactName;
	}

	/**
	 * @param exactName the exactName to set
	 */
	public void setExactName(List<String> exactName) {
		this.exactName = exactName;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(List<String> role) {
		this.role = role;
	}

	/**
	 * @return the role
	 */
	public List<String> getRole() {
		return role;
	}

	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(List<String> placeName) {
		this.placeName = placeName;
	}

	/**
	 * @return the placeName
	 */
	public List<String> getPlaceName() {
		return placeName;
	}

	/**
	 * @return the exactPlaceName
	 */
	public List<String> getExactPlaceName() {
		return exactPlaceName;
	}

	/**
	 * @param exactPlaceName the exactPlaceName to set
	 */
	public void setExactPlaceName(List<String> exactPlaceName) {
		this.exactPlaceName = exactPlaceName;
	}

	/**
	 * @param placeType the placeType to set
	 */
	public void setPlaceType(List<String> placeType) {
		this.placeType = placeType;
	}

	/**
	 * @return the placeType
	 */
	public List<String> getPlaceType() {
		return placeType;
	}

	/**
	 * @param linkedToTopics the linkedToTopics to set
	 */
	public void setLinkedToTopics(List<String> linkedToTopics) {
		this.linkedToTopics = linkedToTopics;
	}

	/**
	 * @return the linkedToTopics
	 */
	public List<String> getLinkedToTopics() {
		return linkedToTopics;
	}

	/**
	 * @param linkedToPeople the linkedToPeople to set
	 */
	public void setLinkedToPeople(List<String> linkedToPeople) {
		this.linkedToPeople = linkedToPeople;
	}

	/**
	 * @return the linkedToPeople
	 */
	public List<String> getLinkedToPeople() {
		return linkedToPeople;
	}

	/**
	 * @return the placeId
	 */
	public List<String> getPlaceId() {
		return placeId;
	}

	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(List<String> placeId) {
		this.placeId = placeId;
	}

	/**
	 * @param digitized the digitized to set
	 */
	public void setDigitized(String digitized) {
		this.digitized = digitized;
	}

	/**
	 * @return the digitized
	 */
	public String getDigitized() {
		return digitized;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
	}

	/**
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/**
	 * @param otherLang the otherLang to set
	 */
	public void setOtherLang(List<String> otherLang) {
		this.otherLang = otherLang;
	}

	/**
	 * @return the otherLang
	 */
	public List<String> getOtherLang() {
		return otherLang;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(List<String> context) {
		this.context = context;
	}

	/**
	 * @return the context
	 */
	public List<String> getContext() {
		return context;
	}

	/**
	 * @param fromVolume the fromVolume to set
	 */
	public void setFromVolume(List<String> fromVolume) {
		this.fromVolume = fromVolume;
	}

	/**
	 * @return the fromVolume
	 */
	public List<String> getFromVolume() {
		return fromVolume;
	}

	/**
	 * @param toVolume the toVolume to set
	 */
	public void setToVolume(List<String> toVolume) {
		this.toVolume = toVolume;
	}

	/**
	 * @return the toVolume
	 */
	public List<String> getToVolume() {
		return toVolume;
	}

	/**
	 * @param inventario the inventario to set
	 */
	public void setInventario(List<String> inventario) {
		this.inventario = inventario;
	}

	/**
	 * @return the inventario
	 */
	public List<String> getInventario() {
		return inventario;
	}

	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	/**
	 * @return the cipher
	 */
	public String getCipher() {
		return cipher;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @return the volumeId
	 */
	public List<String> getVolumeId() {
		return volumeId;
	}

	/**
	 * @param volumeId the volumeId to set
	 */
	public void setVolumeId(List<String> volumeId) {
		this.volumeId = volumeId;
	}

	/**
	 * @param namePerson the namePerson to set
	 */
	public void setNameParts(List<String> nameParts) {
		this.nameParts = nameParts;
	}

	/**
	 * @return the namePerson
	 */
	public List<String> getNameParts() {
		return nameParts;
	}

	/**
	 * @param occupation the occupation to set
	 */
	public void setOccupation(List<String> occupation) {
		this.occupation = occupation;
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
	 * @return the occupationWord
	 */
	public List<String> getOccupationWord() {
		return occupationWord;
	}

	/**
	 * @return the personId
	 */
	public List<String> getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(List<String> personId) {
		this.personId = personId;
	}

	/**
	 * @param roleCategory the roleCategory to set
	 */
	public void setRoleCategory(List<String> roleCategory) {
		this.roleCategory = roleCategory;
	}

	/**
	 * @return the roleCategory
	 */
	public List<String> getRoleCategory() {
		return roleCategory;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(String logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @return the logicalDelete
	 */
	public String getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * @param researchNotes the researchNotes to set
	 */
	public void setResearchNotes(List<String> researchNotes) {
		this.researchNotes = researchNotes;
	}

	/**
	 * @return the researchNotes
	 */
	public List<String> getResearchNotes() {
		return researchNotes;
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
}
