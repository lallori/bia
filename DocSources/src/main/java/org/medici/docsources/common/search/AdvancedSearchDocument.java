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
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;
import org.medici.docsources.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.VolumeUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class AdvancedSearchDocument extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8279978012929495622L;

	private List<Integer> datesDay;
	private List<Integer> datesDayBetween;
	private List<Integer> datesMonth;
	private List<Integer> datesMonthBetween;
	private List<DateType> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<String> docIds;
	private List<String> extract;
	private List<String> folios;
	private List<String> foliosBetween;
	private List<FolioType> foliosTypes;
	private List<String> from;
	private List<Integer> fromId;
	private Boolean logicalDelete;
	private List<String> person;
	private List<Integer> personId;
	private List<String> place;
	private List<Integer> placeId;
	private List<String> recipient;
	private List<Integer> recipientId;
	private List<String> refersTo;
	private List<Integer> refersToId;
	private List<String> sender;
	private List<Integer> senderId;
	private List<String> synopsis;
	private List<String> to;
	private List<Integer> toId;
	private List<String> topics;
	private List<Integer> topicsId;
	private List<String> topicsPlace;
	private List<Integer> topicsPlaceId;
	private List<String> volumes;
	private List<String> volumesBetween;
	private List<VolumeType> volumesTypes;
	private List<String> words;
	private List<WordType> wordsTypes;

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
		topicsPlaceId = new ArrayList<Integer>(0);
		topicsPlace = new ArrayList<String>(0);
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
		folios = new ArrayList<String>(0);
		foliosBetween = new ArrayList<String>(0);
		foliosTypes = new ArrayList<AdvancedSearchAbstract.FolioType>(0);
		docIds = new ArrayList<String>(0);
		logicalDelete = null;
	}

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
	 * @return the datesType
	 */
	public List<DateType> getDatesTypes() {
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
	 * @return the docIds
	 */
	public List<String> getDocIds() {
		return docIds;
	}

	/**
	 * @return the extract
	 */
	public List<String> getExtract() {
		return extract;
	}

	/**
	 * @return the folios
	 */
	public List<String> getFolios() {
		return folios;
	}

	/**
	 * @return the foliosBetween
	 */
	public List<String> getFoliosBetween() {
		return foliosBetween;
	}

	/**
	 * @return the foliosTypes
	 */
	public List<FolioType> getFoliosTypes() {
		return foliosTypes;
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
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
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
	 * @return the refersTo
	 */
	public List<String> getRefersTo() {
		return refersTo;
	}

	/**
	 * @return the refersToId
	 */
	public List<Integer> getRefersToId() {
		return refersToId;
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
	 * @return the topicsPlace
	 */
	public List<String> getTopicsPlace() {
		return topicsPlace;
	}

	/**
	 * @return the topicsPlaceId
	 */
	public List<Integer> getTopicsPlaceId() {
		return topicsPlaceId;
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
	 * @return the volumesType
	 */
	public List<VolumeType> getVolumesTypes() {
		return volumesTypes;
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
	 * {@inheritDoc}
	 */
	@Override
	public void initFromAdvancedSearchCommand(AdvancedSearchCommand command) {
		//Words
		if ((command.getWord() != null) && (command.getWord().size() >0)) {
			wordsTypes = new ArrayList<WordType>(command.getWord().size());
			words = new ArrayList<String>(command.getWord().size());
			
			for (String singleWord : command.getWord()) {
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 2) {
						wordsTypes.add(WordType.valueOf(stringTokenizer.nextToken()));
						words.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
						
					} else {
						continue;
					}
				}catch (URIException e) {
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
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						personId.add(new Integer(0));
						try {
							person.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
						} catch (URIException e) {
						}
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
						try {
							person.add(URIUtil.decode(singleText, "UTF-8"));
						} catch (URIException e) {
							personId.remove(personId.size()-1);
						}
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
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
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				
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
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				
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
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				
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
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
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
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
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
				singleWord = singleWord.replace("+", "%20");
				
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
		
		//Topics Place
		// Place
		if ((command.getTopicPlace() != null) && (command.getTopicPlace().size() >0)) {
			topicsPlaceId = new ArrayList<Integer>(command.getTopicPlace().size());
			topicsPlace = new ArrayList<String>(command.getTopicPlace().size());
			
			for (String singleWord : command.getTopicPlace()) {
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						topicsPlaceId.add(new Integer(0));
						topicsPlace.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(singleId)) { 
							topicsPlaceId.add(NumberUtils.createInteger(singleId));
						} else {
							//Empty placeId is equal to 0
							topicsPlaceId.add(new Integer(0));
						}
						topicsPlace.add(URIUtil.decode(singleText, "UTF-8"));
					} else {
						// we skip field
					}
				} catch (NumberFormatException nex) {
				} catch (URIException e) {
					topicsPlaceId.remove(topicsPlaceId.size()-1);
				}
			}
		} else {
			topicsPlaceId = new ArrayList<Integer>(0);
			topicsPlace = new ArrayList<String>(0);
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
		
		//Folio
		if((command.getFolio() != null) && (command.getFolio().size() > 0)){
			foliosTypes = new ArrayList<AdvancedSearchAbstract.FolioType>(command.getFolio().size());
			folios = new ArrayList<String>(command.getFolio().size());
			foliosBetween = new ArrayList<String>(command.getFolio().size());
			
			for(String singleWord : command.getFolio()){
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				if((stringTokenizer.countTokens() == 0) || (stringTokenizer.countTokens() == 1)){
					continue;
				}else if(stringTokenizer.countTokens() == 2){
					foliosTypes.add(FolioType.valueOf(stringTokenizer.nextToken()));
					folios.add(stringTokenizer.nextToken());
					foliosBetween.add("0");
				}else if(stringTokenizer.countTokens() == 3){
					foliosTypes.add(FolioType.valueOf(stringTokenizer.nextToken()));
					folios.add(stringTokenizer.nextToken());
					foliosBetween.add(stringTokenizer.nextToken());
				}
			}
		}else{
			foliosTypes = new ArrayList<AdvancedSearchAbstract.FolioType>(0);
			folios = new ArrayList<String>(0);
			foliosBetween = new ArrayList<String>(0);
		}
		
		//EntryId
		if((command.getDocId() != null) && (command.getDocId().size() > 0)){
			docIds = new ArrayList<String>(command.getDocId().size());
			
			for(String singleWord : command.getDocId()){
				try{
					docIds.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){					
				}catch(URIException e){
				}
			}
		}else{
			docIds = new ArrayList<String>(0);
		}
		
		//LogicalDelete
		if(command.getLogicalDelete() != null){
			if(command.getLogicalDelete().equals("true")){
				logicalDelete = Boolean.TRUE;
			}else{
				logicalDelete = Boolean.FALSE;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromSimpleSearchCommand(SimpleSearchCommand command) {
		if (command.getSimpleSearchPerimeter().equals(SimpleSearchPerimeter.EXTRACT)) {
			wordsTypes.add(WordType.Extract);
			words.add(command.getText());
		} else 	if (command.getSimpleSearchPerimeter().equals(SimpleSearchPerimeter.SYNOPSIS)) {
			wordsTypes.add(WordType.Synopsis);
			words.add(command.getText());
		}
	}

	@Override
	public Boolean isEmpty() {
		if (
				(words.size()>0)	||
				(personId.size()>0) ||
				(placeId.size()>0) ||
				(senderId.size()>0) ||
				(fromId.size()>0) ||
				(recipient.size()>0) ||
				(to.size()>0) ||
				(refersTo.size()>0) ||
				(extract.size()>0) ||
				(synopsis.size()>0) ||
				(topicsId.size()>0) || 
				(topicsPlaceId.size()>0) ||
				(datesTypes.size()>0) ||
				(volumes.size()>0) ||
				(folios.size()>0) ||
				(docIds.size()>0)) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
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
	 * @param datesType the datesType to set
	 */
	public void setDatesTypes(List<DateType> datesTypes) {
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
	 * @param docIds the docIds to set
	 */
	public void setDocIds(List<String> docIds) {
		this.docIds = docIds;
	}

	/**
	 * @param extract the extract to set
	 */
	public void setExtract(List<String> extract) {
		this.extract = extract;
	}

	/**
	 * @param folios the folios to set
	 */
	public void setFolios(List<String> folios) {
		this.folios = folios;
	}

	/**
	 * @param foliosBetween the foliosBetween to set
	 */
	public void setFoliosBetween(List<String> foliosBetween) {
		this.foliosBetween = foliosBetween;
	}

	/**
	 * @param foliosTypes the foliosTypes to set
	 */
	public void setFoliosTypes(List<FolioType> foliosTypes) {
		this.foliosTypes = foliosTypes;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(List<String> from) {
		this.from = from;
	}

	/**
	 * @param fromId the fromId to set
	 */
	public void setFromId(List<Integer> fromId) {
		this.fromId = fromId;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(List<String> person) {
		this.person = person;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(List<Integer> personId) {
		this.personId = personId;
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
	 * @param recipient the recipient to set
	 */
	public void setRecipient(List<String> recipient) {
		this.recipient = recipient;
	}

	/**
	 * @param recipientId the recipientId to set
	 */
	public void setRecipientId(List<Integer> recipientId) {
		this.recipientId = recipientId;
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
	 * @param sender the sender to set
	 */
	public void setSender(List<String> sender) {
		this.sender = sender;
	}

	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(List<Integer> senderId) {
		this.senderId = senderId;
	}

	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(List<String> synopsis) {
		this.synopsis = synopsis;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @param toId the toId to set
	 */
	public void setToId(List<Integer> toId) {
		this.toId = toId;
	}

	/**
	 * @param topics the topics to set
	 */
	public void setTopics(List<String> topics) {
		this.topics = topics;
	}

	/**
	 * @param topicsId the topicsId to set
	 */
	public void setTopicsId(List<Integer> topicsId) {
		this.topicsId = topicsId;
	}

	/**
	 * @param topicsPlace the topicsPlace to set
	 */
	public void setTopicsPlace(List<String> topicsPlace) {
		this.topicsPlace = topicsPlace;
	}

	/**
	 * @param topicsPlaceId the topicsPlaceId to set
	 */
	public void setTopicsPlaceId(List<Integer> topicsPlaceId) {
		this.topicsPlaceId = topicsPlaceId;
	}

	/**
	 * @param volumes the volumes to set
	 */
	public void setVolumes(List<String> volumes) {
		this.volumes = volumes;
	}

	/**
	 * @param volumesBetween the volumesBetween to set
	 */
	public void setVolumesBetween(List<String> volumesBetween) {
		this.volumesBetween = volumesBetween;
	}

	/**
	 * @param volumesType the volumesType to set
	 */
	public void setVolumesTypes(List<VolumeType> volumesTypes) {
		this.volumesTypes = volumesTypes;
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM Document WHERE ");

		if (words.size()>0) {
			StringBuilder wordsQuery = new StringBuilder("(");
			for (int i=0; i<words.size(); i++) {
				String[] wordsSingleWordSearch = StringUtils.split(words.get(i), " ");
				if (wordsQuery.length()>1) {
					wordsQuery.append(" AND ");
				}
				if (wordsTypes.get(i).equals(WordType.Extract)) {
					for(int j = 0; j < wordsSingleWordSearch.length; j++){
						wordsQuery.append("(synExtract.docExtract like '%");
						wordsQuery.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"));
						wordsQuery.append("%')");
						if(j < (wordsSingleWordSearch.length - 1)){
							wordsQuery.append(" AND ");
						}
					}
				} else if (wordsTypes.get(i).equals(WordType.Synopsis)) {
					for(int j = 0; j < wordsSingleWordSearch.length; j++){
						wordsQuery.append("(synExtract.synopsis like '%");
						wordsQuery.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"));
						wordsQuery.append("%')");
						if(j < (wordsSingleWordSearch.length - 1)){
							wordsQuery.append(" AND ");
						}
					}
				} else if (wordsTypes.get(i).equals(WordType.SynopsisAndExtract)) {
					for(int j = 0; j < wordsSingleWordSearch.length; j++){
						wordsQuery.append("((synExtract.docExtract like '%");
						wordsQuery.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"));
						wordsQuery.append("%') or ");
						wordsQuery.append("(synExtract.synopsis like '%");
						wordsQuery.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"));
						wordsQuery.append("%'))");
						if(j < (wordsSingleWordSearch.length - 1)){
							wordsQuery.append(" AND ");
						}
					}
				}
			}
			wordsQuery.append(")");
			if (!wordsQuery.toString().equals("")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(wordsQuery);
			}
		}

		// person;
		if (personId.size() >0) {
			StringBuilder personIdQuery = new StringBuilder("(");
			StringBuilder personQuery = new StringBuilder("(");
			for (int i=0; i<personId.size(); i++) {
				if (personId.get(i) > 0) {
					if (personIdQuery.length()>1) {
						personIdQuery.append(" AND ");
					}
					
					personIdQuery.append("(entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EpLink WHERE person.personId=");
					personIdQuery.append(personId.get(i).toString());
					personIdQuery.append(") or senderPeople.personId=");
					personIdQuery.append(personId.get(i).toString());
					personIdQuery.append(" or recipientPeople.personId=");
					personIdQuery.append(personId.get(i).toString());
					personIdQuery.append(")");
				} else {
					if (personQuery.length()>1) {
						personQuery.append(" AND ");
					}
					
					personQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EpLink WHERE person.mapNameLf like '%");
					personQuery.append(person.get(i).toLowerCase().replace("'", "''"));
					personQuery.append("%')"); 
					/*personQuery.append("%') or altName.altName like '%'");
					personQuery.append(person.get(i).toLowerCase());
					personQuery.append("%')");*/
				}
			}
			personIdQuery.append(")");
			personQuery.append(")");
			if (!personIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(personIdQuery);
			}
			if (!personQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(personQuery);
			}
		}

		// place;
		if (placeId.size() >0) {
			StringBuilder placeIdQuery = new StringBuilder("(");
			StringBuilder placeQuery = new StringBuilder("(");
			for (int i=0; i<placeId.size(); i++) {
				if (placeId.get(i) > 0) {
					if (placeIdQuery.length()>1) {
						placeIdQuery.append(" AND ");
					}
					
					placeIdQuery.append("(senderPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
					placeIdQuery.append(placeId.get(i).toString());
					placeIdQuery.append(") or recipientPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
					placeIdQuery.append(placeId.get(i).toString());
					placeIdQuery.append(") or entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EplToLink WHERE place.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
					placeIdQuery.append(placeId.get(i).toString());
					placeIdQuery.append(")))");
				} else {
					if (placeQuery.length()>1) {
						placeQuery.append(" AND ");
					}
					
					placeQuery.append("(senderPlace.placeName like '%");
					placeQuery.append(place.get(i).toLowerCase().replace("'", "''"));
					placeQuery.append("%' or recipientPlace.placeName like '%'");
					placeQuery.append(place.get(i).toLowerCase().replace("'", "''"));
					placeQuery.append("%' or entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EplToLink WHERE place.placeName like '%'");
					placeQuery.append(place.get(i).toLowerCase().replace("'", "''"));
					placeQuery.append("%'))");
				}
			}
			placeIdQuery.append(")");
			placeQuery.append(")");
			if (!placeIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(placeIdQuery);
			}
			if (!placeQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(placeQuery);
			}
		}
		
		//sender
		if (senderId.size() >0) {
			StringBuilder senderIdQuery = new StringBuilder("(");
			StringBuilder senderQuery = new StringBuilder("(");
			for (int i=0; i<senderId.size(); i++) {
				if (senderId.get(i) > 0) {
					if (senderIdQuery.length()>1) {
						senderIdQuery.append(" AND ");
					}
					
					senderIdQuery.append("(senderPeople.personId=");
					senderIdQuery.append(senderId.get(i).toString());
					senderIdQuery.append(")");
				} else {
					if (senderQuery.length()>1) {
						senderQuery.append(" AND ");
					}
					
					senderQuery.append("(senderPeople.mapNameLf like '%");
					senderQuery.append(sender.get(i).toLowerCase().replace("'", "''"));
					senderQuery.append("%')");
				}
			}
			senderIdQuery.append(")");
			senderQuery.append(")");
			if (!senderIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(senderIdQuery);
			}
			if (!senderQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(senderQuery);
			}
		}

		// from;
		if (fromId.size() >0) {
			StringBuilder fromIdQuery = new StringBuilder("(");
			StringBuilder fromQuery = new StringBuilder("(");
			for (int i=0; i<fromId.size(); i++) {
				if (fromId.get(i) > 0) {
					if (fromIdQuery.length()>1) {
						fromIdQuery.append(" AND ");
					}
					
					fromIdQuery.append("(senderPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
					fromIdQuery.append(fromId.get(i).toString());
					fromIdQuery.append("))");
				} else {
					if (fromQuery.length()>1) {
						fromQuery.append(" AND ");
					}
					
					fromQuery.append("(senderPlace.placeNameFull like '%");
					fromQuery.append(from.get(i).toLowerCase().replace("'", "''"));
					fromQuery.append("%')");
				}
			}
			fromIdQuery.append(")");
			fromQuery.append(")");
			if (!fromIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(fromIdQuery);
			}
			if (!fromQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(fromQuery);
			}
		}

		// recipient;
		if (recipient.size() >0) {
			StringBuilder recipientIdQuery = new StringBuilder("(");
			StringBuilder recipientQuery = new StringBuilder("(");
			for (int i=0; i<recipientId.size(); i++) {
				if (recipientId.get(i) > 0) {
					if (recipientIdQuery.length()>1) {
						recipientQuery.append(" AND ");
					}
					
					recipientIdQuery.append("(recipientPeople.personId=");
					recipientIdQuery.append(recipientId.get(i).toString());
					recipientIdQuery.append(")");
				} else {
					if (recipientQuery.length()>1) {
						recipientQuery.append(" AND ");
					}
					
					recipientQuery.append("(recipientPeople.mapNameLf like '%");
					recipientQuery.append(recipient.get(i).toLowerCase().replace("'", "''"));
					recipientQuery.append("%')");
				}
			}
			recipientIdQuery.append(")");
			recipientQuery.append(")");
			if (!recipientIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(recipientIdQuery);
			}
			if (!recipientQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(recipientQuery);
			}
		}

		// to;
		if (to.size() >0) {
			StringBuilder toIdQuery = new StringBuilder("(");
			StringBuilder toQuery = new StringBuilder("(");
			for (int i=0; i<toId.size(); i++) {
				if (toId.get(i) > 0) {
					if (toIdQuery.length()>1) {
						toIdQuery.append(" AND ");
					}
					
					toIdQuery.append("(recipientPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
					toIdQuery.append(toId.get(i).toString());
					toIdQuery.append("))");
				} else {
					if (toQuery.length()>0) {
						toQuery.append(" AND ");
					}
					
					toQuery.append("(recipientPlace.placeNameFull like '%");
					toQuery.append(from.get(i).toLowerCase().replace("'", "''"));
					toQuery.append("%')");
				}
			}
			toIdQuery.append(")");
			toQuery.append(")");
			if (!toIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(toIdQuery);
			}
			if (!toQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(toQuery);
			}
		}

		//refersTo
		if (refersTo.size() >0) {
			StringBuilder refersToIdQuery = new StringBuilder("(");
			StringBuilder refersToQuery = new StringBuilder("(");
			for (int i=0; i<refersToId.size(); i++) {
				if (refersToId.get(i) > 0) {
					if (refersToIdQuery.length()>1) {
						refersToIdQuery.append(" AND ");
					}
					
					refersToIdQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EpLink WHERE person.personId=");
					refersToIdQuery.append(refersToId.get(i).toString());
					refersToIdQuery.append(" AND docRole IS NULL))");
				} else {
					if (refersToQuery.length()>1) {
						refersToQuery.append(" AND ");
					}
					
					refersToQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EpLink WHERE person.mapNameLf like '%");
					refersToQuery.append(refersTo.get(i).toLowerCase().replace("'", "''"));
					refersToQuery.append("%' AND docRole IS NULL))");
				}
			}
			refersToIdQuery.append(")");
			refersToQuery.append(")");
			if (!refersToIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(refersToIdQuery);
			}
			if (!refersToQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(refersToQuery);
			}
		}

		// Extract
		if (extract.size()>0) {
			StringBuilder extractQuery = new StringBuilder("(");
			for (int i=0; i<extract.size(); i++) {
				String[] wordsSingleExtract = StringUtils.split(extract.get(i), " ");
				for (int j=0; j<wordsSingleExtract.length; j++) {
					extractQuery.append("(synExtract.docExtract like '%");
					extractQuery.append(wordsSingleExtract[j].replace("'", "''"));
					extractQuery.append("%')");
					if (j< (wordsSingleExtract.length-1)) {
						extractQuery.append(" AND ");
					}
				}
			}
			extractQuery.append(")");
			if (!extractQuery.toString().equals("")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(extractQuery);
			}
		}
		
		// synopsis;
		if (synopsis.size() >0) {
			StringBuilder synopsisQuery = new StringBuilder("(");
			for (int i=0; i<synopsis.size(); i++) {
				String[] wordsSingleSynopsis = StringUtils.split(synopsis.get(i), " ");
				for (int j=0; j<wordsSingleSynopsis.length; j++) {
					synopsisQuery.append("(synExtract.synopsis like '%");
					synopsisQuery.append(wordsSingleSynopsis[j].replace("'", "''"));
					synopsisQuery.append("%')");
					if (j< (wordsSingleSynopsis.length-1)) {
						synopsisQuery.append(" AND ");
					}
				}
			}
			synopsisQuery.append(")");
			if (!synopsisQuery.toString().equals("")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(synopsisQuery);
			}
		}

		// topics;
		if (topicsId.size() >0) {
			StringBuilder topicsIdQuery = new StringBuilder("(");
			StringBuilder topicsQuery = new StringBuilder("(");
			for (int i=0; i<topicsId.size(); i++) {
				if (topicsId.get(i) > 0) {
					if (topicsIdQuery.length()>1) {
						topicsIdQuery.append(" AND ");
					}
					
					topicsIdQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EplToLink WHERE topic.topicId=");
					topicsIdQuery.append(topicsId.get(i).toString());
					topicsIdQuery.append(")");
				} else {
					if (topicsQuery.length()>1) {
						topicsQuery.append(" AND ");
					}
					
					topicsQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EplToLink WHERE topic.topicTitle like '%");
					topicsQuery.append(topics.get(i).toLowerCase().replace("'", "''"));
					topicsQuery.append("%'))");
				}
			}
			topicsIdQuery.append(")");
			topicsQuery.append(")");
			if (!topicsIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(topicsIdQuery);
			}
			if (!topicsQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(topicsQuery);
			}
		}
		
		//Topic Place
		if (topicsPlaceId.size() > 0){
			StringBuilder topicsPlaceIdQuery = new StringBuilder("(");
			StringBuilder topicsPlaceQuery = new StringBuilder("(");
			for(int i = 0;i < topicsPlaceId.size(); i++){
				if(topicsPlaceIdQuery.length() > 1){
					topicsPlaceIdQuery.append(" AND ");
				}
				if(topicsPlaceId.get(i) > 0){
					topicsPlaceIdQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EplToLink WHERE place.placeAllId=");
					topicsPlaceIdQuery.append(topicsPlaceId.get(i).toString());
					topicsPlaceIdQuery.append(")");
				}else{
					topicsPlaceQuery.append("entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EplToLink WHERE place.placeName like '%");
					topicsPlaceQuery.append(topicsPlace.get(i));
					topicsPlaceQuery.append("%')");
				}
			}
			topicsPlaceIdQuery.append(")");
			topicsPlaceQuery.append(")");
			if (!topicsPlaceIdQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(topicsPlaceIdQuery);
			}
			if (!topicsPlaceQuery.toString().equals("()")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(topicsPlaceQuery);
			}
		}

		// Date
		if (datesTypes.size()>0) {
			StringBuilder datesQuery = new StringBuilder("(");
			for (int i=0; i<datesTypes.size(); i++) {
				if (datesTypes.get(i) == null) {
					continue;
				} 
				
				if (datesQuery.length()>1) {
					datesQuery.append(" AND ");
				}

				if (datesTypes.get(i).equals(DateType.From)) {
					//datesQuery.append("(STR_TO_DATE(CONCAT(docYear, ',' , docMonthNum, ',', docDay),'%Y,%m,%d')>");
					datesQuery.append("(sortableDateInt >=");
					datesQuery.append(DateUtils.getIntegerDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					datesQuery.append("(sortableDateInt <");
					datesQuery.append(DateUtils.getIntegerDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				}else if (datesTypes.get(i).equals(DateType.Between)) {
					datesQuery.append("(sortableDateInt >=");
					datesQuery.append(DateUtils.getIntegerDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") AND (sortableDateInt <");
					datesQuery.append(DateUtils.getIntegerDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(")");
				}else if (datesTypes.get(i).equals(DateType.InOn)){
					if(datesYear.get(i) != null){
						datesQuery.append("(yearModern =");
						datesQuery.append(datesYear.get(i) + " OR (docYear =");
						datesQuery.append(datesYear.get(i) + " AND yearModern IS NULL))");
						if(datesMonth.get(i) != null || datesDay.get(i) != null){
							datesQuery.append(" AND ");
						}
					}
					if(datesMonth.get(i) != null){
						datesQuery.append("(docMonthNum =");
						datesQuery.append(datesMonth.get(i) + " )");
						if(datesDay.get(i) != null){
							datesQuery.append(" AND ");
						}
					}
					if(datesDay.get(i) != null){
						datesQuery.append("(docDay =");
						datesQuery.append(datesDay.get(i) + " )");
					}
					
					
					//Old version without the sortableDate
//					if(datesYear.get(i) != null){
//						datesQuery.append("(docYear =");
//						datesQuery.append(datesYear.get(i) + " )");
//						if(datesMonth.get(i) != null || datesDay.get(i) != null){
//							datesQuery.append(" AND ");
//						}
//					}
//					if(datesMonth.get(i) != null){
//						datesQuery.append("(docMonthNum =");
//						datesQuery.append(datesMonth.get(i) + " )");
//						if(datesDay.get(i) != null){
//							datesQuery.append(" AND ");
//						}
//					}
//					if(datesDay.get(i) != null){
//						datesQuery.append("(docDay =");
//						datesQuery.append(datesDay.get(i) + " )");
//					}
				}
			}
			datesQuery.append(")");
			if (!datesQuery.toString().equals("")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(datesQuery);
			}
		}

		// Volume
		if (volumes.size()>0) {
			StringBuilder volumesQuery = new StringBuilder("(");
			for (int i=0; i<volumes.size(); i++) {
				if (VolumeUtils.isVolumeFormat(volumes.get(i))) {
					if (volumesQuery.length()>1) {
						//MD: I need to append an "OR" clause instead an "AND"
						volumesQuery.append(" OR ");
					}

					if (volumesTypes.get(i).equals(VolumeType.Exactly)) {
						if (StringUtils.isNumeric(volumes.get(i))) {
							volumesQuery.append("(volume.volNum=");
							volumesQuery.append(volumes.get(i));
							volumesQuery.append(")");
						} else {
							volumesQuery.append("(volume.volNum=");
							volumesQuery.append(volumes.get(i));
							volumesQuery.append(" AND volume.volLetExt='");
							volumesQuery.append(VolumeUtils.extractVolNum(volumes.get(i)).toString());
							volumesQuery.append("')");
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						volumesQuery.append("(volume.volNum>=");
						volumesQuery.append(volumes.get(i));
						volumesQuery.append(" AND volume.volNum<=");
						volumesQuery.append(volumesBetween.get(i));
						volumesQuery.append(")");
					}
				} else {
					// if volume value is not in volume format we discard it!
					continue;
				}
			}
			volumesQuery.append(")");
			if (!volumesQuery.toString().equals("")) {
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(volumesQuery);
			}
		}
		
		//Folio
		if(folios.size() > 0){
			StringBuilder foliosQuery = new StringBuilder("(");
			for(int i = 0; i < folios.size(); i++){
				if(StringUtils.isNumeric(folios.get(i))){
					if(foliosQuery.length() > 1){
						//MD: I need to append an "OR" clause instead an "AND"
						foliosQuery.append(" OR ");
					}
					
					if(foliosTypes.get(i).equals(FolioType.Exactly)){
						foliosQuery.append("(folioNum=");
						foliosQuery.append(folios.get(i));
						foliosQuery.append(")");
					}else if(foliosTypes.get(i).equals(FolioType.Between)){
						foliosQuery.append("(folioNum>=");
						foliosQuery.append(folios.get(i));
						foliosQuery.append(" AND folioNum<=");
						foliosQuery.append(foliosBetween.get(i));
						foliosQuery.append(")");
					}
				}else{
					continue;
				}
			}
			foliosQuery.append(")");
			if(!foliosQuery.toString().equals("")){
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(foliosQuery);
			}
		}
		
		//EntryId
		if(docIds.size() > 0){
			StringBuilder docIdQuery = new StringBuilder("(");
			for(int i = 0; i < docIds.size(); i++){
				if(StringUtils.isNumeric(docIds.get(i))){
					if(docIdQuery.length() > 1){
						//MD: I need to append an "OR" clause instead an "AND"
						docIdQuery.append(" OR ");
					}
					docIdQuery.append("(entryId=");
					docIdQuery.append(docIds.get(i));
					docIdQuery.append(")");
				}else{
					continue;
				}
			}
			docIdQuery.append(")");
			if(!docIdQuery.toString().equals("")){
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(docIdQuery);
			}
		}
		
		//LogicalDelete
		if(!ObjectUtils.toString(logicalDelete).equals("")){
			StringBuilder logicalDeleteQuery = new StringBuilder("(");
			if(logicalDelete.equals(Boolean.TRUE)){
				logicalDeleteQuery.append("(logicalDelete = true)");
			}else if(logicalDelete.equals(Boolean.FALSE)){
				logicalDeleteQuery.append("(logicalDelete = false)");
			}
			logicalDeleteQuery.append(")");
			if(!logicalDeleteQuery.toString().equals("")){
				if(jpaQuery.length() > 20){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(logicalDeleteQuery);
			}
		}

		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
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
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPlace.placeNameFull", from.get(i).toLowerCase())), Occur.SHOULD);
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
				} else if (datesTypes.get(i).equals(DateType.From)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.MAX_DATE, 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.MIN_DATE,
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				}else if (datesTypes.get(i).equals(DateType.Between)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> startDateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), 
							true, 
							true);
					datesQuery.add(startDateRangeQuery, Occur.MUST); 
					NumericRangeQuery<Integer> endDateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
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

	/**
	 * {@inheritDoc}
	 */
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		if(!words.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Words: ");
			for(int i = 0; i < words.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(words.get(i));
				stringBuilder.append(" ");
			}
		}
		
		if(!extract.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Extract: ");
			for(int i = 0; i < extract.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(extract.get(i));
				stringBuilder.append(" ");
			}
		}
		
		if(!synopsis.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Synopsis: ");
			for(int i = 0; i < synopsis.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(synopsis.get(i));
				stringBuilder.append(" ");
			}
		}
		
		if(!person.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Person: ");
			for(int i = 0; i < person.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(person.get(i));
				stringBuilder.append(" ");
			}
		}
		
		if(!place.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Place: ");
			for(int i = 0; i < place.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(place.get(i) + " ");
			}
		}
		
		if(!sender.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Sender: ");
			for(int i = 0; i < sender.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(sender.get(i) + " ");
			}
		}
		
		if(!from.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("From: ");
			for(int i = 0; i < from.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(from.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!recipient.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Recipient: ");
			for(int i = 0; i < recipient.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(recipient.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!to.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("To: ");
			for(int i = 0; i < to.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(to.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!refersTo.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Refers to: ");
			for(int i = 0; i < refersTo.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(refersTo.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!topics.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Topics: ");
			for(int i = 0; i < topics.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(topics.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!datesYear.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Date Year: ");
			for(int i = 0; i < datesYear.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(datesYear.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!datesMonth.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Date Month: ");
			for(int i = 0; i < datesMonth.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(datesMonth.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!datesDay.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Date Day: ");
			for(int i = 0; i < datesDay.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(datesDay.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!datesYearBetween.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Between Date Year: ");
			for(int i = 0; i < datesYearBetween.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(datesYearBetween.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!datesMonthBetween.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Between Date Month: ");
			for(int i = 0; i < datesMonthBetween.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(datesMonthBetween.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!datesDayBetween.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Between Date Day: ");
			for(int i = 0; i < datesDayBetween.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(datesDayBetween.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!volumes.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Volumes: ");
			for(int i = 0; i < volumes.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(volumes.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!volumesBetween.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Between Volumes: ");
			for(int i = 0; i < volumesBetween.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(volumesBetween.get(i));
				stringBuilder.append(" ");
			}
		}
		
		if(!folios.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Folios: ");
			for(int i = 0; i < folios.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(folios.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!foliosBetween.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Between Folios: ");
			for(int i = 0; i < foliosBetween.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(foliosBetween.get(i));
				stringBuilder.append(" ");
			}
		}
		if(!docIds.isEmpty()){
			if(stringBuilder.length()>0){
				stringBuilder.append("AND ");
			}
			stringBuilder.append("Doc ID: ");
			for(int i = 0; i < docIds.size(); i++){
				if(i > 0){
					stringBuilder.append("AND ");
				}
				stringBuilder.append(docIds.get(i));
				stringBuilder.append(" ");
			}
		}
		
		return stringBuilder.toString();
	}
}

