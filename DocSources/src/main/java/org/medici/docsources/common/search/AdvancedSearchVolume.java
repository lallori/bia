/*
 * AdvancedSearchVolume.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.medici.docsources.command.search.AdvancedSearchDocumentsCommand;
import org.medici.docsources.common.util.RegExUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchVolume implements AdvancedSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private Logger logger = Logger.getLogger(this.getClass()); 

	private List<String> words;
	private List<WordType> wordsTypes;
	private List<String> volumes;
	private List<String> volumesBetween;
	private List<VolumeType> volumesType;
	private List<DateType> datesType;
	private List<Integer> datesYear;
	private List<Integer> datesMonth;
	private List<Integer> datesDay;
	private List<String> extract;
	private List<String> synopsis;
	private List<String> topics;
	private List<Integer> topicsId;
	private List<String> person;
	private List<Integer> personId;
	private List<String> place;
	private List<Integer> placeId;
	private List<String> sender;
	private List<Integer> senderId;
	private List<String> from;
	private List<Integer> fromId;
	private List<String> recipient;
	private List<Integer> recipientId;
	private List<String> to;
	private List<Integer> toId;
	private List<String> resTo;

	/**
	 * 
	 */
	public AdvancedSearchVolume() {
		super();
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromCommand(AdvancedSearchDocumentsCommand command) {
		//Words
		if (command.getWords().size() >0) {
			wordsTypes = new ArrayList<WordType>(command.getWords().size());
			words = new ArrayList<String>(command.getWords().size());
			
			for (String singleWord : command.getWords()) {
				String[] fields = singleWord.split("\\|");
				
				if (fields.length != 2) {
					logger.error("Wrong field words " + singleWord + " skipped.");
					continue;
				} else {
					wordsTypes.add(WordType.valueOf(fields[0]));
					words.add(fields[1]);
				}
			}
		}

		// Extract
		if (command.getExtract().size() >0) {
			extract = new ArrayList<String>(command.getExtract().size());
			
			for (String singleWord : command.getWords()) {
				extract.add(singleWord);
			}
		}
		
		// synopsis;
		if (command.getSynopsis().size() >0) {
			synopsis = new ArrayList<String>(command.getSynopsis().size());
			
			for (String singleWord : command.getWords()) {
				synopsis.add(singleWord);
			}
		}

		// topics;
		topicsId = new ArrayList<Integer>(command.getTopics().size());
		topics = new ArrayList<String>(command.getTopics().size());
		
		for (String singleWord : command.getTopics()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field topics " + singleWord + " skipped.");
				continue;
			} else {
				try {
					// Check if field is correct
					if (NumberUtils.isNumber(fields[0])) { 
						topicsId.add(NumberUtils.createInteger(fields[0]));
					} else {
						//Empty topicId is equal to 0
						topicsId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong topic id " + singleWord + " skipped.");
				}
				topics.add(fields[1]);
			}
		}

		// person;
		personId = new ArrayList<Integer>(command.getPerson().size());
		person = new ArrayList<String>(command.getPerson().size());
		
		for (String singleWord : command.getPerson()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field person " + singleWord + " skipped.");
				continue;
			} else {
				try {
					// Check if field is correct
					if (NumberUtils.isNumber(fields[0])) { 
						personId.add(NumberUtils.createInteger(fields[0]));
					} else {
						//Empty personId is equal to 0
						personId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong person id " + singleWord + " skipped.");
				}
				person.add(fields[1]);
			}
		}

		// place;
		placeId = new ArrayList<Integer>(command.getPlaces().size());
		place = new ArrayList<String>(command.getPlaces().size());
		
		for (String singleWord : command.getWords()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field places " + singleWord + " skipped.");
				continue;
			} else {
				try {
					// Check if field is correct
					if (NumberUtils.isNumber(fields[0])) { 
						placeId.add(NumberUtils.createInteger(fields[0]));
					} else {
						// Empty placeId is equal to 0
						placeId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong place id " + singleWord + " skipped.");
				}
				place.add(fields[1]);
			}
		}

		// sender;
		senderId = new ArrayList<Integer>(command.getSenders().size());
		sender = new ArrayList<String>(command.getSenders().size());
		
		for (String singleWord : command.getWords()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field senders " + singleWord + " skipped.");
				continue;
			} else {
				try {
					if (NumberUtils.isNumber(fields[0])) { 
						senderId.add(NumberUtils.createInteger(fields[0]));
					} else {
						//Empty senderId is equal to 0
						senderId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong sender id " + singleWord + " skipped.");
				}
				sender.add(fields[1]);
			}
		}

		// from;
		fromId = new ArrayList<Integer>(command.getFrom().size());
		from = new ArrayList<String>(command.getFrom().size());
		
		for (String singleWord : command.getFrom()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field topics " + singleWord + " skipped.");
				continue;
			} else {
				try {
					if (NumberUtils.isNumber(fields[0])) { 
						fromId.add(NumberUtils.createInteger(fields[0]));
					} else {
						//Empty topicId is equal to 0
						fromId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong from id " + singleWord + " skipped.");
				}
				from.add(fields[1]);
			}
		}

		// recipient;
		recipientId = new ArrayList<Integer>(command.getRecipients().size());
		recipient = new ArrayList<String>(command.getRecipients().size());
		
		for (String singleWord : command.getRecipients()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field recipients " + singleWord + " skipped.");
				continue;
			} else {
				try {
					if (NumberUtils.isNumber(fields[0])) { 
						recipientId.add(NumberUtils.createInteger(fields[0]));
					} else {
						//Empty topicId is equal to 0
						recipientId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong recipient id " + singleWord + " skipped.");
				}
				recipient.add(fields[1]);
			}
		}

		// to;
		toId = new ArrayList<Integer>(command.getTo().size());
		to = new ArrayList<String>(command.getTo().size());
		
		for (String singleWord : command.getTo()) {
			String[] fields = singleWord.split("\\|");
			
			if (fields.length != 2) {
				logger.error("Wrong field to " + singleWord + " skipped.");
				continue;
			} else {
				try {
					if (NumberUtils.isNumber(fields[0])) { 
						toId.add(NumberUtils.createInteger(fields[0]));
					} else {
						//Empty toId is equal to 0
						toId.add(new Integer(0));
					}
				}catch (NumberFormatException nex) {
					logger.error("Wrong to id " + singleWord + " skipped.");
				}
				to.add(fields[1]);
			}
		}

		// resTo;
		resTo = new ArrayList<String>(command.getResTo().size());
		
		for (String singleWord : command.getResTo()) {
			resTo.add(singleWord);
		}
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
	 * @return the volumesBetween
	 */
	public List<String> getVolumesBetween() {
		return volumesBetween;
	}

	/**
	 * @param volumesBetween the volumesBetween to set
	 */
	public void setVolumesBetween(List<String> volumesBetween) {
		this.volumesBetween = volumesBetween;
	}

	/**
	 * @return the volumesType
	 */
	public List<VolumeType> getVolumesType() {
		return volumesType;
	}

	/**
	 * @param volumesType the volumesType to set
	 */
	public void setVolumesType(List<VolumeType> volumesType) {
		this.volumesType = volumesType;
	}

	/**
	 * @return the datesType
	 */
	public List<DateType> getDatesType() {
		return datesType;
	}

	/**
	 * @param datesType the datesType to set
	 */
	public void setDatesType(List<DateType> datesType) {
		this.datesType = datesType;
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
	 * @return the topicsId
	 */
	public List<Integer> getTopicsId() {
		return topicsId;
	}

	/**
	 * @param topicsId the topicsId to set
	 */
	public void setTopicsId(List<Integer> topicsId) {
		this.topicsId = topicsId;
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
	 * @return the personId
	 */
	public List<Integer> getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(List<Integer> personId) {
		this.personId = personId;
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
	 * @return the senderId
	 */
	public List<Integer> getSenderId() {
		return senderId;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(List<Integer> senderId) {
		this.senderId = senderId;
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
	 * @return the fromId
	 */
	public List<Integer> getFromId() {
		return fromId;
	}

	/**
	 * @param fromId the fromId to set
	 */
	public void setFromId(List<Integer> fromId) {
		this.fromId = fromId;
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
	 * @return the recipientId
	 */
	public List<Integer> getRecipientId() {
		return recipientId;
	}

	/**
	 * @param recipientId the recipientId to set
	 */
	public void setRecipientId(List<Integer> recipientId) {
		this.recipientId = recipientId;
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
	 * @return the toId
	 */
	public List<Integer> getToId() {
		return toId;
	}

	/**
	 * @param toId the toId to set
	 */
	public void setToId(List<Integer> toId) {
		this.toId = toId;
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

	/**
	 * It's more simple construct lucene Query with string.
	 */
	@Override
	public String toLuceneQueryString() {
		StringBuffer stringBuffer = new StringBuffer();
		//BooleanQuery booleanQuery = new BooleanQuery();

		//Words
		if (words.size()>0) {
			stringBuffer.append("(");
			for (int i=0; i<words.size(); i++) {
				//BooleanClause booleanClause = new BooleanClause(null, null);
				if (wordsTypes.get(i).equals(WordType.Extract)) {
					stringBuffer.append("(synExtract.docExtract: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*) ");
					/*booleanClause.setQuery(new WildcardQuery(new Term("synExtract.docExtract", words.get(i).toLowerCase() + "*")));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);*/
				} else if (wordsTypes.get(i).equals(WordType.Synopsis)) {
					stringBuffer.append("(synExtract.synopsis: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*) ");
					/*booleanClause.setQuery(new WildcardQuery(new Term("synExtract.synopsis", words.get(i).toLowerCase() + "*")));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);*/
				} else if (wordsTypes.get(i).equals(WordType.ExtractAndSynopsis)) {
					stringBuffer.append("((synExtract.docExtract: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*) AND (synExtract.synopsis: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*)) ");
					/*booleanClause.setQuery(new WildcardQuery(new Term("synExtract.docExtract", words.get(i).toLowerCase() + "*")));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);
					booleanClause.setQuery(new WildcardQuery(new Term("synExtract.synopsis", words.get(i).toLowerCase() + "*")));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);*/
				}
				if (i<(words.size()-1)) {
					stringBuffer.append(" OR ");
				}
			}
			stringBuffer.append(")");
		}

		// Extract
		if (extract.size()>0) {
			for (int i=0; i<extract.size(); i++) {
				/*BooleanClause booleanClause = new BooleanClause(null, null);
				booleanClause.setQuery(new WildcardQuery(new Term("synExtract.docExtract", extract.get(i).toLowerCase() + "*")));
				booleanClause.setOccur(BooleanClause.Occur.MUST);
				booleanQuery.add(booleanClause);*/
				stringBuffer.append("(synExtract.docExtract: ");
				stringBuffer.append(extract.get(i).toLowerCase());
				stringBuffer.append("*) ");
			}
		}
		
		// synopsis;
		if (synopsis.size() >0) {
			for (int i=0; i<extract.size(); i++) {
				/*BooleanClause booleanClause = new BooleanClause(null, null);
				booleanClause.setQuery(new WildcardQuery(new Term("synExtract.synopsis", synopsis.get(i).toLowerCase() + "*")));
				booleanClause.setOccur(BooleanClause.Occur.MUST);
				booleanQuery.add(booleanClause);*/
				stringBuffer.append("(synExtract.synopsis: ");
				stringBuffer.append(synopsis.get(i).toLowerCase());
				stringBuffer.append("*) ");
			}
		}

		// topics;
		if (topicsId.size() >0) {
			for (int i=0; i<topicsId.size(); i++) {
				if ((topicsId.get(i) == null) || (topicsId.get(i) > 0)) {
					/*BooleanClause booleanClause = new BooleanClause(null, null);
					booleanClause.setQuery(new TermQuery(new Term("eplToLink.topic.topicId", "" + topicsId.get(i))));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);*/
					stringBuffer.append("(eplToLink.topic.topicId: ");
					stringBuffer.append(topicsId.get(i));
					stringBuffer.append(") ");
				} else {
					/*BooleanClause booleanClause = new BooleanClause(null, null);
					booleanClause.setQuery(new WildcardQuery(new Term("eplToLink.topic.topicTitle", topics.get(i).toLowerCase() + "*")));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);*/
					stringBuffer.append("(eplToLink.topic.topicTitle: ");
					stringBuffer.append(topics.get(i));
					stringBuffer.append("*) ");
				}
			}
		}
		
		
		// person;
		if (personId.size() >0) {
			for (int i=0; i<personId.size(); i++) {
				if ((personId.get(i) == null) || (personId.get(i) > 0)) {
					stringBuffer.append("(epLink.person.personId: ");
					stringBuffer.append(personId.get(i));
					stringBuffer.append(") ");
				} else {
					String[] words = RegExUtils.splitPunctuationAndSpaceChars(person.get(i));
					stringBuffer.append("((");
			        for (int j=0; j<words.length; j++) {
			        	stringBuffer.append("(epLink.person.mapNameLf: ");
						stringBuffer.append(words[i]);
						stringBuffer.append("*)");
						if (j<(words.length)) {
							stringBuffer.append(" AND ");
						}
			        }
			        stringBuffer.append(") OR ");
			        for (int j=0; j<words.length; j++) {
			        	stringBuffer.append("(altName.altName: ");
						stringBuffer.append(words[i]);
						stringBuffer.append("*)");
						if (j<(words.length)) {
							stringBuffer.append(" AND ");
						}
			        }
			        stringBuffer.append("))");
				}
			}
		}

		// place;
		if (placeId.size() >0) {
			for (int i=0; i<placeId.size(); i++) {
				if ((placeId.get(i) == null) || (placeId.get(i) > 0)) {
					/*BooleanClause booleanClause = new BooleanClause(null, null);
					booleanClause.setQuery(new TermQuery(new Term("epLink.person.personId", "" + personId.get(i))));
					booleanClause.setOccur(BooleanClause.Occur.MUST);
					booleanQuery.add(booleanClause);*/					
					stringBuffer.append("((senderPlace.placeAllId: ");
					stringBuffer.append(placeId.get(i));
					stringBuffer.append(") OR (recipientPlace.placeAllId: ");
					stringBuffer.append(placeId.get(i));
					stringBuffer.append(") OR (eplToLink.place.placeAllId: ");
					stringBuffer.append(placeId.get(i));
					stringBuffer.append(")) ");
				} else {
					stringBuffer.append("((senderPlace.placeName: ");
					stringBuffer.append(place.get(i));
					stringBuffer.append("*) OR (recipientPlace.placeName: ");
					stringBuffer.append(place.get(i));
					stringBuffer.append("*) OR (eplToLink.place.placeName: ");
					stringBuffer.append(place.get(i));
					stringBuffer.append("*)) ");
				}
			}			
		}
		
		//sender
		if (senderId.size() >0) {
			for (int i=0; i<senderId.size(); i++) {
				if ((senderId.get(i) == null) || (senderId.get(i) > 0)) {
					stringBuffer.append("(senderPeople.personId: ");
					stringBuffer.append(senderId.get(i));
					stringBuffer.append(") ");
				} else {
					String[] words = RegExUtils.splitPunctuationAndSpaceChars(sender.get(i));
					stringBuffer.append("(");
			        for (int j=0; j<words.length; j++) {
			        	stringBuffer.append("(senderPeople.mapNameLf: ");
						stringBuffer.append(words[i]);
						stringBuffer.append("*)");
						if (j<(words.length)) {
							stringBuffer.append(" AND ");
						}
			        }
					stringBuffer.append(")");
				}
			}
		}

		// from;
		if (fromId.size() >0) {
			for (int i=0; i<fromId.size(); i++) {
				if ((fromId.get(i) == null) || (fromId.get(i) > 0)) {
					stringBuffer.append("(senderPlace.placeAllId: ");
					stringBuffer.append(fromId.get(i));
					stringBuffer.append(") ");
				} else {
					String[] words = RegExUtils.splitPunctuationAndSpaceChars(from.get(i));
					stringBuffer.append("(");
			        for (int j=0; j<words.length; j++) {
			        	stringBuffer.append("(senderPlace.placeNameFull: ");
						stringBuffer.append(words[i]);
						stringBuffer.append("*)");
						if (j<(words.length)) {
							stringBuffer.append(" AND ");
						}
			        }
					stringBuffer.append(")");
				}
			}
		}

		// recipient;
		if (recipient.size() >0) {
			for (int i=0; i<recipientId.size(); i++) {
				if ((recipientId.get(i) == null) || (recipientId.get(i) > 0)) {
					stringBuffer.append("(senderPeople.personId: ");
					stringBuffer.append(recipientId.get(i));
					stringBuffer.append(") ");
				} else {
					String[] words = RegExUtils.splitPunctuationAndSpaceChars(recipient.get(i));
					stringBuffer.append("(");
			        for (int j=0; j<words.length; j++) {
			        	stringBuffer.append("(recipientPeople.mapNameLf: ");
						stringBuffer.append(words[i]);
						stringBuffer.append("*)");
						if (j<(words.length)) {
							stringBuffer.append(" AND ");
						}
			        }
					stringBuffer.append(")");
				}
			}
		}

		// to;
		for (int i=0; i<toId.size(); i++) {
			if ((toId.get(i) == null) || (toId.get(i) > 0)) {
				stringBuffer.append("(recipientPlace.placeAllId: ");
				stringBuffer.append(fromId.get(i));
				stringBuffer.append(") ");
			} else {
				String[] words = RegExUtils.splitPunctuationAndSpaceChars(to.get(i));
				stringBuffer.append("(");
		        for (int j=0; j<words.length; j++) {
		        	stringBuffer.append("(recipientPlace.placeNameFull: ");
					stringBuffer.append(words[i]);
					stringBuffer.append("*)");
					if (j<(words.length)) {
						stringBuffer.append(" AND ");
					}
		        }
				stringBuffer.append(")");
			}
		}

		// resTo;
		for (int i=0; i<resTo.size(); i++) {
		}

		return stringBuffer.toString();
	}
	
	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum WordType {
		Extract("Extract"), Synopsis("Synopsis"), ExtractAndSynopsis("ExtractAndSynopsis");
		
		private final String wordType;

	    private WordType(String value) {
	    	wordType = value;
	    }

	    @Override
	    public String toString(){
	        return wordType;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum VolumeType {
		Exactly("Exactly"), Between("Between");
		
		private final String volumeType;

	    private VolumeType(String value) {
	    	volumeType = value;
	    }

	    @Override
	    public String toString(){
	        return volumeType;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum DateType {
		From("From"), To("To"), After("After"), Before("Before");
		
		private final String dateType;

	    private DateType(String value) {
	    	dateType = value;
	    }

	    @Override
	    public String toString(){
	        return dateType;
	    }
	}
}

