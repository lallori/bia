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

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.medici.docsources.common.search.AdvancedSearch;
import org.medici.docsources.domain.SearchFilter.SearchType;


/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchCommand {
	private Integer idSearchFilter;
	private SearchType searchType;
	private List<String> word;
	private List<String> volume;
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
	private String searchUUID;
	
	private String digitized;
	private List<String> languages;
	private List<String> context;
	private List<String> fromVolume;
	private List<String> toVolume;
	private List<String> inventario;

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
		} catch (IllegalAccessException iaex) {
		} catch (InvocationTargetException itex) {
		}
		
		setSearchType(searchType);
		setSearchUUID(searchUUID);
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
	 * @param dateType the datesType to set
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
}
