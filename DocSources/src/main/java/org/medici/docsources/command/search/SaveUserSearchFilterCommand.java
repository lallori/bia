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

import org.medici.docsources.domain.SearchFilter.SearchFilterType;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SaveUserSearchFilterCommand {
	private SearchFilterType searchFilterType;
	private SaveType saveType;
	private String saveAs;
	private Integer searchFilter;
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
	
	/**
	 * @param searchFilterType the searchFilterType to set
	 */
	public void setSearchFilterType(SearchFilterType searchFilterType) {
		this.searchFilterType = searchFilterType;
	}

	/**
	 * @return the searchFilterType
	 */
	public SearchFilterType getSearchFilterType() {
		return searchFilterType;
	}

	/**
	 * @param searchFilter the searchFilter to set
	 */
	public void setSearchFilter(Integer searchFilter) {
		this.searchFilter = searchFilter;
	}

	/**
	 * @return the searchFilter
	 */
	public Integer getSearchFilter() {
		return searchFilter;
	}


	/**
	 * @param saveAs the saveAs to set
	 */
	public void setSaveAs(String saveAs) {
		this.saveAs = saveAs;
	}

	/**
	 * @return the saveAs
	 */
	public String getSaveAs() {
		return saveAs;
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
	 * @param saveType the saveType to set
	 */
	public void setSaveType(SaveType saveType) {
		this.saveType = saveType;
	}

	/**
	 * @return the saveType
	 */
	public SaveType getSaveType() {
		return saveType;
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
