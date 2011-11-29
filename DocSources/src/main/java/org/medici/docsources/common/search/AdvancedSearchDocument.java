/*
 * AdvancedSearchDocument.java
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
import java.util.StringTokenizer;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.VolumeUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchDocument extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8279978012929495622L;

	private List<String> words;
	private List<WordType> wordsTypes;
	private List<String> extract;
	private List<String> synopsis;
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
	private List<String> refersTo;
	private List<Integer> refersToId;
	private List<String> topics;
	private List<Integer> topicsId;
	private List<DateType> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesMonth;
	private List<Integer> datesDay;
	private List<Integer> datesYearBetween;
	private List<Integer> datesMonthBetween;
	private List<Integer> datesDayBetween;
	private List<String> volumes;
	private List<String> volumesBetween;
	private List<VolumeType> volumesTypes;

	/**
	 * 
	 */
	public AdvancedSearchDocument() {
		super();

		words = new ArrayList<String>(0);
		wordsTypes = new ArrayList<AdvancedSearchDocument.WordType>(0);
		person = new ArrayList<String>(0);
		personId = new ArrayList<Integer>(0);
		place = new ArrayList<String>(0);
		placeId = new ArrayList<Integer>(0);
		sender = new ArrayList<String>(0);
		senderId = new ArrayList<Integer>(0);
		from = new ArrayList<String>(0);
		fromId = new ArrayList<Integer>(0);
		recipient = new ArrayList<String>(0);
		recipientId = new ArrayList<Integer>(0);
		to = new ArrayList<String>(0);
		toId = new ArrayList<Integer>(0);
		refersTo = new ArrayList<String>(0);
		refersToId = new ArrayList<Integer>(0);
		extract = new ArrayList<String>(0);
		synopsis = new ArrayList<String>(0);
		topics = new ArrayList<String>(0);
		topicsId = new ArrayList<Integer>(0);
		datesTypes = new ArrayList<AdvancedSearchDocument.DateType>(0);
		datesYear = new ArrayList<Integer>(0);
		datesMonth = new ArrayList<Integer>(0);
		datesDay = new ArrayList<Integer>(0);
		datesYearBetween = new ArrayList<Integer>(0);
		datesMonthBetween = new ArrayList<Integer>(0);
		datesDayBetween = new ArrayList<Integer>(0);
		volumesTypes = new ArrayList<AdvancedSearchDocument.VolumeType>(0);
		volumes = new ArrayList<String>(0);
		volumesBetween = new ArrayList<String>(0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromAdvancedSearchCommand(AdvancedSearchCommand command) {
		//Words
		if ((command.getWord() != null) && (command.getWord().size() >0)) {
			wordsTypes = new ArrayList<WordType>(command.getWord().size());
			words = new ArrayList<String>(command.getWord().size());
			
			for (String singleWord : command.getWord()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 2) {
						wordsTypes.add(WordType.valueOf(stringTokenizer.nextToken()));
						words.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else {
						continue;
					}
				} catch (URIException e) {
					wordsTypes.remove(wordsTypes.size()-1);
				}
			}
		} else {
			wordsTypes = new ArrayList<WordType>(0);
			words = new ArrayList<String>(0);
		}

		// Person
		if ((command.getPerson() != null) && (command.getPerson().size() >0)) {
			personId = new ArrayList<Integer>(command.getPerson().size());
			person = new ArrayList<String>(command.getPerson().size());
			
			for (String singleWord : command.getPerson()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						personId.add(new Integer(0));
						person.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
	
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							personId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty personId is equal to 0
							personId.add(new Integer(0));
						}
						person.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					personId.remove(personId.size()-1);
				}
			}
		} else {
			personId = new ArrayList<Integer>(0);
			person = new ArrayList<String>(0);
		}

		// Place
		if ((command.getPlace() != null) && (command.getPlace().size() >0)) {
			placeId = new ArrayList<Integer>(command.getPlace().size());
			place = new ArrayList<String>(command.getPlace().size());
			
			for (String singleWord : command.getPlace()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						placeId.add(new Integer(0));
						place.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							placeId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty placeId is equal to 0
							placeId.add(new Integer(0));
						}
						place.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					placeId.remove(placeId.size()-1);
				}
			}
		} else {
			placeId = new ArrayList<Integer>(0);
			place = new ArrayList<String>(0);
		}

		// Sender
		if ((command.getSender() != null) && (command.getSender().size() >0)) {
			senderId = new ArrayList<Integer>(command.getSender().size());
			sender = new ArrayList<String>(command.getSender().size());
			
			for (String singleWord : command.getSender()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						senderId.add(new Integer(0));
						sender.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							senderId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty senderId is equal to 0
							senderId.add(new Integer(0));
						}
						sender.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					senderId.remove(senderId.size()-1);
				}
			}
		} else {
			senderId = new ArrayList<Integer>(0);
			sender = new ArrayList<String>(0);
		}

		// From
		if ((command.getFrom() != null) && (command.getFrom().size() >0)) {
			fromId = new ArrayList<Integer>(command.getFrom().size());
			from = new ArrayList<String>(command.getFrom().size());
			
			for (String singleWord : command.getFrom()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						fromId.add(new Integer(0));
						from.add(stringTokenizer.nextToken());
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							fromId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty fromId is equal to 0
							fromId.add(new Integer(0));
						}
						from.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					fromId.remove(fromId.size()-1);
				}
			}
		} else {
			fromId = new ArrayList<Integer>(0);
			from= new ArrayList<String>(0);
		}

		// Recipient
		if ((command.getRecipient() != null) && (command.getRecipient().size() >0)) {
			recipientId = new ArrayList<Integer>(command.getRecipient().size());
			recipient = new ArrayList<String>(command.getRecipient().size());
			
			for (String singleWord : command.getRecipient()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						recipientId.add(new Integer(0));
						recipient.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							recipientId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty recipientId is equal to 0
							recipientId.add(new Integer(0));
						}
						recipient.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					recipientId.remove(recipientId.size()-1);
				}
			}
		} else {
			recipientId = new ArrayList<Integer>(0);
			recipient = new ArrayList<String>(0);
		}

		// To
		if ((command.getTo() != null) && (command.getTo().size() >0)) {
			toId = new ArrayList<Integer>(command.getTo().size());
			to = new ArrayList<String>(command.getTo().size());
			
			for (String singleWord : command.getTo()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						toId.add(new Integer(0));
						to.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							toId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty toId is equal to 0
							toId.add(new Integer(0));
						}
						to.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					toId.remove(toId.size()-1);
				}
			}
		} else {
			toId = new ArrayList<Integer>(0);
			to = new ArrayList<String>(0);
		}

		// ResTo;
		if ((command.getRefersTo() != null) && (command.getRefersTo().size() >0)) {
			refersToId = new ArrayList<Integer>(command.getRefersTo().size());
			refersTo = new ArrayList<String>(command.getRefersTo().size());
			
			for (String singleWord : command.getRefersTo()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				// string format is number|text
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						refersToId.add(new Integer(0));
						refersTo.add(stringTokenizer.nextToken());
					} else if (stringTokenizer.countTokens() == 2) {
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							refersToId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty refersToId is equal to 0
							refersToId.add(new Integer(0));
						}
						refersTo.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					refersToId.remove(refersToId.size()-1);
				}
			}
		} else {
			refersToId = new ArrayList<Integer>(0);
			refersTo = new ArrayList<String>(0);
		}

		// Extract
		if ((command.getExtract() != null) &&  (command.getExtract().size() >0)) {
			extract = new ArrayList<String>(command.getExtract().size());
			
			for (String singleWord : command.getExtract()) {
				try {
					extract.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
				}
			}
		} else {
			extract = new ArrayList<String>(0);
		}
		
		// Synopsis
		if ((command.getSynopsis() != null) && (command.getSynopsis().size() >0)) {
			synopsis = new ArrayList<String>(command.getSynopsis().size());
			
			for (String singleWord : command.getSynopsis()) {
				try {
					synopsis.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
				}
			}
		} else {
			synopsis = new ArrayList<String>(0);
		}

		// Topics
		if ((command.getTopic() != null) && (command.getTopic().size() >0)) {
			topicsId = new ArrayList<Integer>(command.getTopic().size());
			topics = new ArrayList<String>(command.getTopic().size());
			
			for (String singleWord : command.getTopic()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						topicsId.add(new Integer(0));
						topics.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							topicsId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty topicsId is equal to 0
							topicsId.add(new Integer(0));
						}
						topics.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					topicsId.remove(topicsId.size()-1);
				}
			}
		} else {
			topicsId = new ArrayList<Integer>(0);
			topics = new ArrayList<String>(0);
		}

		//Date
		if ((command.getDate() != null) && (command.getDate().size() >0)) {
			datesTypes = new ArrayList<DateType>(command.getDate().size());
			datesYear = new ArrayList<Integer>(command.getDate().size());
			datesMonth = new ArrayList<Integer>(command.getDate().size());
			datesDay = new ArrayList<Integer>(command.getDate().size());
			datesYearBetween = new ArrayList<Integer>(command.getDate().size());
			datesMonthBetween = new ArrayList<Integer>(command.getDate().size());
			datesDayBetween = new ArrayList<Integer>(command.getDate().size());
			
			for (String singleWord : command.getDate()) {
				//e.g. After|1222|01|12|1223|12|12
				String[] fields = StringUtils.splitPreserveAllTokens(singleWord,"|");
				datesTypes.add(DateType.valueOf(fields[0]));
				datesYear.add(DateUtils.getDateYearFromString(fields[1]));
				datesMonth.add(DateUtils.getDateMonthFromString(fields[2]));
				datesDay.add(DateUtils.getDateDayFromString(fields[3]));
				datesYearBetween.add(DateUtils.getDateYearFromString(fields[4]));
				datesMonthBetween.add(DateUtils.getDateMonthFromString(fields[5]));
				datesDayBetween.add(DateUtils.getDateDayFromString(fields[6]));
			}
		} else {
			datesTypes = new ArrayList<DateType>(0);
			datesYear = new ArrayList<Integer>(0);
			datesMonth = new ArrayList<Integer>(0);
			datesDay = new ArrayList<Integer>(0);
			datesYearBetween = new ArrayList<Integer>(0);
			datesMonthBetween = new ArrayList<Integer>(0);
			datesDayBetween = new ArrayList<Integer>(0);
		}
		
		//Volume
		if ((command.getVolume() != null) && (command.getVolume().size() >0)) {
			volumesTypes = new ArrayList<VolumeType>(command.getVolume().size());
			volumes = new ArrayList<String>(command.getVolume().size());
			volumesBetween = new ArrayList<String>(command.getVolume().size());
			
			for (String singleWord : command.getVolume()) {
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				if ((stringTokenizer.countTokens() == 0) || (stringTokenizer.countTokens() == 1)){
					continue;
				} else if (stringTokenizer.countTokens() == 2) {
					// string format is Exactly|12
					volumesTypes.add(VolumeType.valueOf(stringTokenizer.nextToken()));
					volumes.add(stringTokenizer.nextToken());
					volumesBetween.add("0");
				} else if (stringTokenizer.countTokens() == 3) {
					// string format is Exactly|12|16
					volumesTypes.add(VolumeType.valueOf(stringTokenizer.nextToken()));
					volumes.add(stringTokenizer.nextToken());
					volumesBetween.add(stringTokenizer.nextToken());
				}
			}
		} else {
			volumesTypes = new ArrayList<VolumeType>(0);
			volumes = new ArrayList<String>(0);
			volumesBetween = new ArrayList<String>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromSimpleSearchCommand(SimpleSearchCommand command) {
		wordsTypes.add(WordType.SynopsisAndExtract);
		words.add(command.getText());
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
	public List<VolumeType> getVolumesTypes() {
		return volumesTypes;
	}

	/**
	 * @param volumesType the volumesType to set
	 */
	public void setVolumesTypes(List<VolumeType> volumesTypes) {
		this.volumesTypes = volumesTypes;
	}

	/**
	 * @return the datesType
	 */
	public List<DateType> getDatesTypes() {
		return datesTypes;
	}

	/**
	 * @param datesType the datesType to set
	 */
	public void setDatesTypes(List<DateType> datesTypes) {
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
	 * @param refersToId the refersToId to set
	 */
	public void setRefersToId(List<Integer> refersToId) {
		this.refersToId = refersToId;
	}

	/**
	 * @return the refersToId
	 */
	public List<Integer> getRefersToId() {
		return refersToId;
	}

	/**
	 * This method return a Lucene Query object. 
	 */
	public Query toLuceneQuery() {
		BooleanQuery luceneQuery = new BooleanQuery();

		if (words.size()>0) {
			BooleanQuery wordsQuery = new BooleanQuery();
			for (int i=0; i<words.size(); i++) {
				if (wordsTypes.get(i).equals(WordType.Extract)) {
					// (+synExtract.docExtract:med*)
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("synExtract.docExtract", words.get(i).toLowerCase())), Occur.MUST);
					wordsQuery.add(booleanClause);
				} else if (wordsTypes.get(i).equals(WordType.Synopsis)) {
					// (synExtract.synopsis:med*)
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("synExtract.synopsis", words.get(i).toLowerCase())), Occur.MUST);
					wordsQuery.add(booleanClause);
				} else if (wordsTypes.get(i).equals(WordType.SynopsisAndExtract)) {
					BooleanQuery subQuery = new BooleanQuery();
					// +(+synExtract.docExtract:med* +synExtract.synopsis:med*) 
					subQuery.add(new PrefixQuery(new Term("synExtract.docExtract", words.get(i).toLowerCase())), Occur.MUST);
					subQuery.add(new PrefixQuery(new Term("synExtract.synopsis", words.get(i).toLowerCase())), Occur.MUST);

					wordsQuery.add(subQuery, Occur.MUST);
				}
			}
			if (!wordsQuery.toString().equals("")) {
				luceneQuery.add(wordsQuery, Occur.MUST);
			}
		}

		// person;
		if (personId.size() >0) {
			BooleanQuery personIdQuery = new BooleanQuery();
			BooleanQuery personQuery = new BooleanQuery();
			for (int i=0; i<personId.size(); i++) {
				if (personId.get(i) > 0) {
					BooleanQuery singlePersonIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("epLink.person.personId", personId.get(i).toString())), Occur.SHOULD);
					singlePersonIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("senderPeople.personId", personId.get(i).toString())), Occur.SHOULD);
					singlePersonIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("recipientPeople.personId", personId.get(i).toString())), Occur.SHOULD);
					singlePersonIdQuery.add(booleanClause);
					personIdQuery.add(singlePersonIdQuery, Occur.MUST);
				} else {
					BooleanQuery singlePersonQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("epLink.person.mapNameLf", person.get(i).toLowerCase())), Occur.SHOULD);
					singlePersonQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("altName.altName", person.get(i).toLowerCase())), Occur.SHOULD);
					singlePersonQuery.add(booleanClause);
					personQuery.add(singlePersonQuery, Occur.MUST);
				}
			}
			if (!personIdQuery.toString().equals("")) {
				luceneQuery.add(personIdQuery, Occur.MUST);
			}
			if (!personQuery.toString().equals("")) {
				luceneQuery.add(personQuery, Occur.MUST);
			}
		}

		// place;
		if (placeId.size() >0) {
			BooleanQuery placeIdQuery = new BooleanQuery();
			BooleanQuery placeQuery = new BooleanQuery();

			for (int i=0; i<placeId.size(); i++) {
				if (placeId.get(i) > 0) {
					BooleanQuery singlePlaceIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("senderPlace.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					singlePlaceIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("recipientPlace.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					singlePlaceIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("eplToLink.place.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					singlePlaceIdQuery.add(booleanClause);
					placeIdQuery.add(singlePlaceIdQuery, Occur.MUST);
				} else {
					BooleanQuery singlePlaceQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPlace.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					singlePlaceQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("recipientPlace.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					singlePlaceQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("eplToLink.place.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					singlePlaceQuery.add(booleanClause);
					placeQuery.add(singlePlaceQuery, Occur.MUST);
				}
			}			
			if (!placeIdQuery.toString().equals("")) {
				luceneQuery.add(placeIdQuery, Occur.MUST);
			}
			if (!placeQuery.toString().equals("")) {
				luceneQuery.add(placeQuery, Occur.MUST);
			}
		}
		
		//sender
		if (senderId.size() >0) {
			BooleanQuery senderIdQuery = new BooleanQuery();
			BooleanQuery senderQuery = new BooleanQuery();
			
			for (int i=0; i<senderId.size(); i++) {
				if (senderId.get(i) > 0) {
					BooleanQuery singleSenderIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("senderPeople.personId", senderId.get(i).toString())), Occur.SHOULD);
					singleSenderIdQuery.add(booleanClause);
					senderIdQuery.add(singleSenderIdQuery, Occur.MUST);
				} else {
					BooleanQuery singleSenderQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPeople.mapNameLf", sender.get(i).toLowerCase())), Occur.SHOULD);
					singleSenderQuery.add(booleanClause);
					senderQuery.add(singleSenderQuery, Occur.MUST);
				}
			}
			if (!senderIdQuery.toString().equals("")) {
				luceneQuery.add(senderIdQuery, Occur.MUST);
			}
			if (!senderQuery.toString().equals("")) {
				luceneQuery.add(senderQuery, Occur.MUST);
			}
		}

		// from;
		if (fromId.size() >0) {
			BooleanQuery fromIdQuery = new BooleanQuery();
			BooleanQuery fromQuery = new BooleanQuery();
			for (int i=0; i<fromId.size(); i++) {
				if (fromId.get(i) > 0) {
					BooleanQuery singleFromIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("senderPlace.placeAllId", fromId.get(i).toString())), Occur.SHOULD);
					singleFromIdQuery.add(booleanClause);
					fromIdQuery.add(singleFromIdQuery, Occur.MUST);
				} else {
					BooleanQuery singleFromQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPeople.mapNameLf", from.get(i).toLowerCase())), Occur.SHOULD);
					singleFromQuery.add(booleanClause);
					fromQuery.add(singleFromQuery, Occur.MUST);
				}
			}
			if (!fromIdQuery.toString().equals("")) {
				luceneQuery.add(fromIdQuery, Occur.MUST);
			}
			if (!fromQuery.toString().equals("")) {
				luceneQuery.add(fromQuery, Occur.MUST);
			}
		}

		// recipient;
		if (recipient.size() >0) {
			BooleanQuery recipientIdQuery = new BooleanQuery();
			BooleanQuery recipientQuery = new BooleanQuery();
			for (int i=0; i<recipientId.size(); i++) {
				if (recipientId.get(i) > 0) {
					BooleanQuery singleRecipientIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("recipientPeople.personId", recipientId.get(i).toString())), Occur.SHOULD);
					singleRecipientIdQuery.add(booleanClause);
					recipientIdQuery.add(singleRecipientIdQuery, Occur.MUST);
				} else {
					BooleanQuery singleRecipientQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("recipientPeople.mapNameLf", recipient.get(i).toLowerCase())), Occur.SHOULD);
					singleRecipientQuery.add(booleanClause);
					recipientQuery.add(singleRecipientQuery, Occur.MUST);
				}
			}
			if (!recipientIdQuery.toString().equals("")) {
				luceneQuery.add(recipientIdQuery, Occur.MUST);
			}
			if (!recipientQuery.toString().equals("")) {
				luceneQuery.add(recipientQuery, Occur.MUST);
			}
		}

		// to;
		if (to.size() >0) {
			BooleanQuery toIdQuery = new BooleanQuery();
			BooleanQuery toQuery = new BooleanQuery();
			for (int i=0; i<toId.size(); i++) {
				if (toId.get(i) > 0) {
					BooleanQuery singleToIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("recipientPlace.placeAllId", toId.get(i).toString())), Occur.SHOULD);
					singleToIdQuery.add(booleanClause);
					toIdQuery.add(singleToIdQuery, Occur.MUST);
				} else {
					BooleanQuery singleToQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("recipientPlace.placeNameFull", to.get(i).toLowerCase())), Occur.SHOULD);
					toQuery.add(booleanClause);
					singleToQuery.add(booleanClause);
					toIdQuery.add(singleToQuery, Occur.MUST);
				}
			}
			if (!toIdQuery.toString().equals("")) {
				luceneQuery.add(toIdQuery, Occur.MUST);
			}
			if (!toQuery.toString().equals("")) {
				luceneQuery.add(toQuery, Occur.MUST);
			}
		}

		//refersTo
		if (refersTo.size() >0) {
			BooleanQuery refersToIdQuery = new BooleanQuery();
			BooleanQuery refersToQuery = new BooleanQuery();
			for (int i=0; i<personId.size(); i++) {
				if (refersToId.get(i) > 0) {
					BooleanQuery singleRefersToIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("epLink.person.personId", personId.get(i).toString())), Occur.SHOULD);
					singleRefersToIdQuery.add(booleanClause);
					refersToIdQuery.add(singleRefersToIdQuery, Occur.MUST);
				} else {
					BooleanQuery singleRefersToQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("epLink.person.mapNameLf", person.get(i).toLowerCase())), Occur.SHOULD);
					singleRefersToQuery.add(booleanClause);
					refersToQuery.add(singleRefersToQuery, Occur.MUST);
				}
			}
			if (!refersToIdQuery.toString().equals("")) {
				luceneQuery.add(refersToQuery, Occur.MUST);
			}
			if (!refersToQuery.toString().equals("")) {
				luceneQuery.add(refersToQuery, Occur.MUST);
			}
		}

		// Extract
		if (extract.size()>0) {
			BooleanQuery extractQuery = new BooleanQuery();
			for (int i=0; i<extract.size(); i++) {
				BooleanQuery singleExtractQuery = new BooleanQuery();
				// 1 - (+synExtract.docExtract:med*)
				// 1 - BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("synExtract.docExtract", extract.get(i).toLowerCase())), Occur.MUST);
				// 1 - luceneQuery.add(booleanClause);
				// Extract: mi dice anchora che io dia el grano ==> +(+(+synExtract.docExtract:mi +synExtract.docExtract:dice +synExtract.docExtract:anchora +synExtract.docExtract:che +synExtract.docExtract:io +synExtract.docExtract:dia +synExtract.docExtract:el +synExtract.docExtract:grano))
				
				String[] wordsSingleExtract = StringUtils.split(extract.get(i), " ");
				for (int j=0; j<wordsSingleExtract.length; j++) {
					TermQuery termQuery = new TermQuery(new Term("synExtract.docExtract", wordsSingleExtract[j]));
					singleExtractQuery.add(termQuery, Occur.MUST);
					extractQuery.add(singleExtractQuery, Occur.MUST);
					singleExtractQuery = new BooleanQuery();
				}

				//extractQuery.add(new BooleanClause(singleExtractQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(extractQuery, Occur.MUST));
		}
		
		// synopsis;
		if (synopsis.size() >0) {
			BooleanQuery synopsisQuery = new BooleanQuery();
			for (int i=0; i<synopsis.size(); i++) {
				BooleanQuery singleSynopsisQuery = new BooleanQuery();
				// +(synExtract.synopsis:med*)
				// 1 - BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("synExtract.synopsis", synopsis.get(i).toLowerCase())), Occur.MUST);
				// 1 - luceneQuery.add(booleanClause);
				String[] wordsSingleSynopsis = StringUtils.split(synopsis.get(i), " ");
				for (int j=0; j<wordsSingleSynopsis.length; j++) {
					TermQuery termQuery = new TermQuery(new Term("synExtract.synopsis", wordsSingleSynopsis[j]));
					singleSynopsisQuery.add(termQuery, Occur.MUST);
					synopsisQuery.add(singleSynopsisQuery, Occur.MUST);
					singleSynopsisQuery = new BooleanQuery();
				}
				//synopsisQuery.add(new BooleanClause(singleSynopsisQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(synopsisQuery, Occur.MUST));
		}

		// topics;
		if (topicsId.size() >0) {
			BooleanQuery topicIdQuery = new BooleanQuery();
			BooleanQuery topicTitleQuery = new BooleanQuery();

			for (int i=0; i<topicsId.size(); i++) {
				if (topicsId.get(i) > 0) {
					// +(+eplToLink.topic.topicId: 23)
					BooleanQuery singleTopicIdQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("eplToLink.topic.topicId", topicsId.get(i).toString())), Occur.SHOULD);
					singleTopicIdQuery.add(booleanClause);
					topicIdQuery.add(singleTopicIdQuery, Occur.MUST);
				} else {
					// +(+eplToLink.topic.topicTitle
					BooleanQuery singleTopicQuery = new BooleanQuery(); 
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("eplToLink.topic.topicTitle", topics.get(i).toLowerCase())), Occur.SHOULD);
					topicTitleQuery.add(booleanClause);
					singleTopicQuery.add(booleanClause);
					topicIdQuery.add(singleTopicQuery, Occur.MUST);
				}
			}
			if (!topicIdQuery.toString().equals("")) {
				luceneQuery.add(topicIdQuery, Occur.MUST);
			}
			if (!topicTitleQuery.toString().equals("")) {
				luceneQuery.add(topicTitleQuery, Occur.MUST);
			}
		}

		// Date
		if (datesTypes.size()>0) {
			BooleanQuery datesQuery = new BooleanQuery();
			for (int i=0; i<datesTypes.size(); i++) {
				if (datesTypes.get(i) == null) {
					continue;
				} else if (datesTypes.get(i).equals(DateType.After)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("startDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.MAX_DATE, 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("startDate_Sort", 4, 
							DateUtils.MIN_DATE,
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				}else if (datesTypes.get(i).equals(DateType.Between)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> startDateRangeQuery = NumericRangeQuery.newIntRange("startDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), 
							true, 
							true);
					datesQuery.add(startDateRangeQuery, Occur.MUST); 
					NumericRangeQuery<Integer> endDateRangeQuery = NumericRangeQuery.newIntRange("endDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), 
							true, 
							true);
					datesQuery.add(endDateRangeQuery, Occur.MUST); 
				}
			}
			if (!datesQuery.toString().equals("")) {
				luceneQuery.add(datesQuery, Occur.MUST);
			}
		}

		// Volume
		if (volumes.size()>0) {
			BooleanQuery volumesQuery = new BooleanQuery();
			for (int i=0; i<volumes.size(); i++) {
				if (VolumeUtils.isVolumeFormat(volumes.get(i))) {
					if (volumesTypes.get(i).equals(VolumeType.Exactly)) {
						if (StringUtils.isNumeric(volumes.get(i))) {
							// (volume.volNum:1)
							BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("volume.volNum", volumes.get(i))), Occur.MUST);
							volumesQuery.add(booleanClause);
						} else {
							BooleanQuery subQuery = new BooleanQuery();
							// (volume.volNum:1 AND volume.volLetExt:a)
							BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("volume.volNum", VolumeUtils.extractVolNum(volumes.get(i)).toString())), Occur.MUST);
							subQuery.add(booleanClause);

							booleanClause.setQuery(new TermQuery(new Term("volume.volLetExt", VolumeUtils.extractVolNum(volumes.get(i)).toString())));
							booleanClause.setOccur(Occur.MUST);
							subQuery.add(booleanClause);

							volumesQuery.add(subQuery, Occur.MUST);
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
						NumericRangeQuery<Integer> volumeRangeQuery = NumericRangeQuery.newIntRange("volume.volNum_Sort", 4, 
								NumberUtils.toInt(volumes.get(i)), 
								NumberUtils.toInt(volumesBetween.get(i)), 
								true, 
								true);
						volumesQuery.add(volumeRangeQuery, Occur.MUST); 
					}
				} else {
					// if volume value is not in volume format we discard it!
					continue;
				}
			}
			if (!volumesQuery.toString().equals("")) {
				luceneQuery.add(volumesQuery, Occur.MUST);
			}
		}

		return luceneQuery;
	}
	
	public String toString(){
		String toString = new String();
		if(!words.isEmpty()){
			toString += "Words: ";
			for(String value : words){
				toString += value + " ";
			}
		}
		
		if(!extract.isEmpty()){
			toString += "AND Extract: ";
			for(String value : extract){
				toString += value + " ";
			}
		}
		
		if(!synopsis.isEmpty()){
			toString += "AND Synopsis: ";
			for(String value : synopsis){
				toString += value + " ";
			}
		}
		
		if(!person.isEmpty()){
			toString += "AND Person: ";
			for(String value : person){
				toString += value + " ";
			}
		}
		
		if(!place.isEmpty()){
			toString += "AND Place: ";
			for(String value : place){
				toString += value + " ";
			}
		}
		
		if(!sender.isEmpty()){
			toString += "AND Sender: ";
			for(String value : sender){
				toString += value + " ";
			}
		}
		
		if(!from.isEmpty()){
			toString += ("AND From: ");
			for(String value : from){
				toString += (value + " ");
			}
		}
		if(!recipient.isEmpty()){
			toString += ("AND Recipient: ");
			for(String value : recipient){
				toString += (value + " ");
			}
		}
		if(!to.isEmpty()){
			toString += ("AND To: ");
			for(String value : to){
				toString += (value + " ");
			}
		}
		if(!refersTo.isEmpty()){
			toString += ("AND Refers to: ");
			for(String value : refersTo){
				toString += (value + " ");
			}
		}
		if(!topics.isEmpty()){
			toString += ("AND Topics: ");
			for(String value : topics){
				toString += (value + " ");
			}
		}
		if(!datesYear.isEmpty()){
			toString += ("AND Date Year: ");
			for(Integer value : datesYear){
				toString += (value + " ");
			}
		}
		if(!datesMonth.isEmpty()){
			toString += ("AND Date Month: ");
			for(Integer value : datesMonth){
				toString += (value + " ");
			}
		}
		if(!datesDay.isEmpty()){
			toString += ("AND Date Day: ");
			for(Integer value : datesDay){
				toString += (value + " ");
			}
		}
		if(!datesYearBetween.isEmpty()){
			toString += ("AND Between Date Year: ");
			for(Integer value : datesYearBetween){
				toString += (value + " ");
			}
		}
		if(!datesMonthBetween.isEmpty()){
			toString += ("AND Between Date Month: ");
			for(Integer value : datesMonthBetween){
				toString += (value + " ");
			}
		}
		if(!datesDayBetween.isEmpty()){
			toString += ("AND Between Date Day: ");
			for(Integer value : datesDayBetween){
				toString += (value + " ");
			}
		}
		if(!volumes.isEmpty()){
			toString += ("AND Volumes: ");
			for(String value : volumes){
				toString += (value + " ");
			}
		}
		if(!volumesBetween.isEmpty()){
			toString += ("AND Between Volumes: ");
			for(String value : volumesBetween){
				toString += (value + " ");
			}
		}
		return toString;
	}
}

