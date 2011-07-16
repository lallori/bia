/*
 * AdvancedSearchPeople.java
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
package org.medici.docsources.common.search;

import java.util.List;
import org.apache.lucene.search.Query;
import org.medici.docsources.command.search.AdvancedSearchPeopleCommand;
import org.medici.docsources.command.search.SaveUserSearchFilterCommand;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class AdvancedSearchPeople implements AdvancedSearch {
	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a
	 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	public static enum DateType {
		After("After"), Before("Before"), From("From"), To("To");

		private final String dateType;

		private DateType(String value) {
			dateType = value;
		}

		@Override
		public String toString() {
			return dateType;
		}
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a
	 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 * 
	 */
	public static enum WordType {
		Extract("Extract"), ExtractAndSynopsis("ExtractAndSynopsis"), Synopsis("Synopsis");

		private final String wordType;

		private WordType(String value) {
			wordType = value;
		}

		@Override
		public String toString() {
			return wordType;
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	private List<Integer> datesDay;
	private List<Integer> datesMonth;
	private List<DateType> datesType;
	private List<Integer> datesYear;
	private List<String> extract;
	private List<String> from;
	private List<Integer> fromId;
	private List<String> person;
	private List<Integer> personId;
	private List<String> place;
	private List<Integer> placeId;
	private List<String> recipient;
	private List<Integer> recipientId;
	private List<String> resTo;
	private List<String> sender;
	private List<Integer> senderId;
	private List<String> synopsis;
	private List<String> to;
	private List<Integer> toId;
	private List<String> topics;
	private List<Integer> topicsId;
	private List<String> volumes;
	private List<String> volumesBetween;

	private List<String> words;

	private List<WordType> wordsTypes;

	/**
	 * 
	 */
	public AdvancedSearchPeople() {
		super();
	}

	/**
	 * @return the datesDay
	 */
	public List<Integer> getDatesDay() {
		return datesDay;
	}

	/**
	 * @return the datesMonth
	 */
	public List<Integer> getDatesMonth() {
		return datesMonth;
	}

	/**
	 * @return the datesType
	 */
	public List<DateType> getDatesType() {
		return datesType;
	}

	/**
	 * @return the datesYear
	 */
	public List<Integer> getDatesYear() {
		return datesYear;
	}

	/**
	 * @return the extract
	 */
	public List<String> getExtract() {
		return extract;
	}

	/**
	 * @return the from
	 */
	public List<String> getFrom() {
		return from;
	}

	/**
	 * @return the fromId
	 */
	public List<Integer> getFromId() {
		return fromId;
	}

	/**
	 * @return the person
	 */
	public List<String> getPerson() {
		return person;
	}

	/**
	 * @return the personId
	 */
	public List<Integer> getPersonId() {
		return personId;
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
	 * @return the recipient
	 */
	public List<String> getRecipient() {
		return recipient;
	}

	/**
	 * @return the recipientId
	 */
	public List<Integer> getRecipientId() {
		return recipientId;
	}

	/**
	 * @return the resTo
	 */
	public List<String> getResTo() {
		return resTo;
	}

	/**
	 * @return the sender
	 */
	public List<String> getSender() {
		return sender;
	}

	/**
	 * @return the senderId
	 */
	public List<Integer> getSenderId() {
		return senderId;
	}

	/**
	 * @return the synopsis
	 */
	public List<String> getSynopsis() {
		return synopsis;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @return the toId
	 */
	public List<Integer> getToId() {
		return toId;
	}

	/**
	 * @return the topics
	 */
	public List<String> getTopics() {
		return topics;
	}

	/**
	 * @return the topicsId
	 */
	public List<Integer> getTopicsId() {
		return topicsId;
	}

	/**
	 * @return the volumes
	 */
	public List<String> getVolumes() {
		return volumes;
	}

	/**
	 * @return the volumesBetween
	 */
	public List<String> getVolumesBetween() {
		return volumesBetween;
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
	 * 
	 * @param command
	 */
	public void initFromCommand(AdvancedSearchPeopleCommand command) {
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromSaveUserSearchFilterCommand(SaveUserSearchFilterCommand command) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param datesDay
	 *            the datesDay to set
	 */
	public void setDatesDay(List<Integer> datesDay) {
		this.datesDay = datesDay;
	}

	/**
	 * @param datesMonth
	 *            the datesMonth to set
	 */
	public void setDatesMonth(List<Integer> datesMonth) {
		this.datesMonth = datesMonth;
	}

	/**
	 * @param datesType
	 *            the datesType to set
	 */
	public void setDatesType(List<DateType> datesType) {
		this.datesType = datesType;
	}

	/**
	 * @param datesYear
	 *            the datesYear to set
	 */
	public void setDatesYear(List<Integer> datesYear) {
		this.datesYear = datesYear;
	}

	/**
	 * @param extract
	 *            the extract to set
	 */
	public void setExtract(List<String> extract) {
		this.extract = extract;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(List<String> from) {
		this.from = from;
	}

	/**
	 * @param fromId
	 *            the fromId to set
	 */
	public void setFromId(List<Integer> fromId) {
		this.fromId = fromId;
	}

	/**
	 * @param person
	 *            the person to set
	 */
	public void setPerson(List<String> person) {
		this.person = person;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(List<Integer> personId) {
		this.personId = personId;
	}

	/**
	 * @param place
	 *            the place to set
	 */
	public void setPlace(List<String> place) {
		this.place = place;
	}

	/**
	 * @param placeId
	 *            the placeId to set
	 */
	public void setPlaceId(List<Integer> placeId) {
		this.placeId = placeId;
	}

	/**
	 * @param recipient
	 *            the recipient to set
	 */
	public void setRecipient(List<String> recipient) {
		this.recipient = recipient;
	}

	/**
	 * @param recipientId
	 *            the recipientId to set
	 */
	public void setRecipientId(List<Integer> recipientId) {
		this.recipientId = recipientId;
	}

	/**
	 * @param resTo
	 *            the resTo to set
	 */
	public void setResTo(List<String> resTo) {
		this.resTo = resTo;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(List<String> sender) {
		this.sender = sender;
	}

	/**
	 * @param senderId
	 *            the senderId to set
	 */
	public void setSenderId(List<Integer> senderId) {
		this.senderId = senderId;
	}

	/**
	 * @param synopsis
	 *            the synopsis to set
	 */
	public void setSynopsis(List<String> synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @param toId
	 *            the toId to set
	 */
	public void setToId(List<Integer> toId) {
		this.toId = toId;
	}

	/**
	 * @param topics
	 *            the topics to set
	 */
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	/**
	 * @param topicsId
	 *            the topicsId to set
	 */
	public void setTopicsId(List<Integer> topicsId) {
		this.topicsId = topicsId;
	}

	/**
	 * @param volumes
	 *            the volumes to set
	 */
	public void setVolumes(List<String> volumes) {
		this.volumes = volumes;
	}

	/**
	 * @param volumesBetween
	 *            the volumesBetween to set
	 */
	public void setVolumesBetween(List<String> volumesBetween) {
		this.volumesBetween = volumesBetween;
	}

	/**
	 * @param words
	 *            the words to set
	 */
	public void setWords(List<String> words) {
		this.words = words;
	}

	/**
	 * @param wordsTypes
	 *            the wordsTypes to set
	 */
	public void setWordsTypes(List<WordType> wordsTypes) {
		this.wordsTypes = wordsTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @param advancedSearchPeopleCommand
	 */
	public void initFromAdvancedSearchPeopleCommand(AdvancedSearchPeopleCommand advancedSearchPeopleCommand) {
		// TODO Auto-generated method stub
		
	}

}
