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


/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchDocumentsCommand {
	private List<String> words;
	private List<String> volumes;
	private List<String> datesType;
	private List<String> extract;
	private List<String> synopsis;
	private List<String> topics;
	private List<String> person;
	private List<String> places;
	private List<String> senders;
	private List<String> from;
	private List<String> recipients;
	private List<String> to;
	private List<String> resTo;
	
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
	 * @return the volumes
	 */
	public List<String> getVolumes() {
		return volumes;
	}
	
	/**
	 * @param volumes the volumes to set
	 */
	public void setVolumes(List<String> volumes) {
		this.volumes = volumes;
	}
	
	/**
	 * @return the datesType
	 */
	public List<String> getDatesType() {
		return datesType;
	}
	
	/**
	 * @param datesType the datesType to set
	 */
	public void setDatesType(List<String> datesType) {
		this.datesType = datesType;
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
	public List<String> getTopics() {
		return topics;
	}
	
	/**
	 * @param topics the topics to set
	 */
	public void setTopics(List<String> topics) {
		this.topics = topics;
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
	 * @return the places
	 */
	public List<String> getPlaces() {
		return places;
	}
	
	/**
	 * @param places the places to set
	 */
	public void setPlaces(List<String> places) {
		this.places = places;
	}
	
	/**
	 * @return the senders
	 */
	public List<String> getSenders() {
		return senders;
	}
	
	/**
	 * @param senders the senders to set
	 */
	public void setSender(List<String> senders) {
		this.senders = senders;
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
	 * @return the recipients
	 */
	public List<String> getRecipients() {
		return recipients;
	}
	
	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
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
	public List<String> getResTo() {
		return resTo;
	}
	
	/**
	 * @param resTo the resTo to set
	 */
	public void setResTo(List<String> resTo) {
		this.resTo = resTo;
	}
}
