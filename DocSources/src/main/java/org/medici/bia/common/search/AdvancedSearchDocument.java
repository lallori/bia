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
package org.medici.bia.common.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.command.search.SimpleSearchCommand;
import org.medici.bia.common.search.SimpleSearch.SimpleSearchPerimeter;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.URLTransformer;
import org.medici.bia.common.util.VolumeUtils;

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
	private List<Date> datesCreated;
	private List<Date> datesCreatedBetween;
	private List<DateType> datesCreatedTypes;
	private List<Date> datesLastUpdate;
	private List<Date> datesLastUpdateBetween;
	private List<DateType> datesLastUpdateTypes;
	private List<String> docIds;
	private List<String> extract;
	private List<String> folioMods;
	private List<String> folios;
	private List<String> foliosBetween;
	private List<FolioType> foliosTypes;
	private List<String> from;
	private List<Integer> fromId;
	private List<String> insertNums;
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
	private List<UserActionType> userActionTypes;
	private List<String> users;

	private static Logger logger = Logger.getLogger(AdvancedSearchDocument.class);
	
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
		datesLastUpdate = new ArrayList<Date>(0);
		datesLastUpdateBetween = new ArrayList<Date>(0);
		datesLastUpdateTypes = new ArrayList<DateType>(0);
		datesCreated = new ArrayList<Date>(0);
		datesCreatedBetween = new ArrayList<Date>(0);
		datesCreatedTypes = new ArrayList<DateType>(0);
		volumesTypes = new ArrayList<AdvancedSearchDocument.VolumeType>(0);
		volumes = new ArrayList<String>(0);
		volumesBetween = new ArrayList<String>(0);
		insertNums = new ArrayList<String>(0);
		folios = new ArrayList<String>(0);
		foliosBetween = new ArrayList<String>(0);
		foliosTypes = new ArrayList<AdvancedSearchAbstract.FolioType>(0);
		folioMods = new ArrayList<String>(0);
		docIds = new ArrayList<String>(0);
		logicalDelete = null;
		userActionTypes = new ArrayList<AdvancedSearchAbstract.UserActionType>(0);
		users = new ArrayList<String>(0);
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
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				//RR: And this is for replacing special characters with unicode values
				singleWord = URLTransformer.decode(singleWord);
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 2) {
						wordsTypes.add(WordType.valueOf(stringTokenizer.nextToken()));
						StringBuffer tempString = new StringBuffer(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
						if(StringUtils.countMatches(tempString.toString(), "\"")%2 != 0){
							tempString.setCharAt(tempString.lastIndexOf("\""), ' ');
						}
						words.add(tempString.toString());
						
					} else {
						continue;
					}
				}catch (URIException uriException) {
					logger.debug(uriException);
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
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleWord = singleWord.replace("+", "%20");
				//RR: And this is for replacing special characters with unicode values
				singleWord = URLTransformer.decode(singleWord);
				
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is |text
						personId.add(new Integer(0));
						try {
							person.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
						} catch (URIException uriException) {
							logger.debug(uriException);
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
						} catch (URIException uriException) {
							logger.debug(uriException);
							personId.remove(personId.size()-1);
						}
					}
				} catch (NumberFormatException nex) {
					logger.error(nex);
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
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleWord = singleWord.replace("+", "%20");
				//RR: And this is for replacing special characters with unicode values
				singleWord = URLTransformer.decode(singleWord);
				
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
					}
				} catch (NumberFormatException nex) {
					logger.debug(nex);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
					}
				} catch (NumberFormatException nex) {
					logger.debug(nex);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleWord = singleWord.replace("+", "%20");
				//RR: And this is for replacing special characters with unicode values
				singleWord = URLTransformer.decode(singleWord);
				
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
					}
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
					}
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleWord = singleWord.replace("+", "%20");
				//RR: And this is for replacing special characters with unicode values
				singleWord = URLTransformer.decode(singleWord);
				
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
					}
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
					}
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				//RR: And this is for replacing special characters with unicode values
				singleWord = URLTransformer.decode(singleWord);
				try {
					extract.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
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
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				singleWord = URLTransformer.decode(singleWord);
				try {
					synopsis.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
				}
			}
		} else {
			synopsis = new ArrayList<String>(0);
		}

		// Topics
		if ((command.getTopic() != null) && (command.getTopic().size() >0)) {
			topicsId = new ArrayList<Integer>(command.getTopic().size());
			topics = new ArrayList<String>(command.getTopic().size());
			topicsPlaceId = new ArrayList<Integer>(command.getTopic().size());
			topicsPlace = new ArrayList<String>(command.getTopic().size());
			
			
			for (String singleWord : command.getTopic()) {
				singleWord = singleWord.replace("+", "%20");
				
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					if (stringTokenizer.countTokens() == 0) {
						continue;
					} else if (stringTokenizer.countTokens() == 1) {
						// string format is number
						String topicId = stringTokenizer.nextToken();
						if (NumberUtils.isNumber(topicId)) { 
							topicsId.add(NumberUtils.createInteger(topicId));
						} else {
							//Empty topicsId is equal to 0
							topicsId.add(new Integer(0));
						}
//						topics.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					} else if (stringTokenizer.countTokens() == 2) {
						// string format is number|text
						String topicId = stringTokenizer.nextToken();
						String topicText = stringTokenizer.nextToken();
						// Check if field is correct
						if (NumberUtils.isNumber(topicId)) { 
							topicsId.add(NumberUtils.createInteger(topicId));
						} else {
							//Empty topicsId is equal to 0
							topicsId.add(new Integer(0));
						}
						topics.add(URIUtil.decode(topicText, "UTF-8"));
					}else if (stringTokenizer.countTokens() == 3){
						//string format is number|text|number
						String singleId = stringTokenizer.nextToken();
						String topicText = stringTokenizer.nextToken();
						String placeId = stringTokenizer.nextToken();
						
						if(NumberUtils.isNumber(singleId)){
							topicsId.add(NumberUtils.createInteger(singleId));
						}else{
							topicsId.add(new Integer(0));
						}
						topics.add(URIUtil.decode(topicText, "UTF-8"));
						if(NumberUtils.isNumber(placeId)){
							topicsPlaceId.add(NumberUtils.createInteger(placeId));
						}else{
							topicsPlaceId.add(new Integer(0));
						}
					}else if (stringTokenizer.countTokens() == 4){
						//string format is number|text|number|text
						String singleId = stringTokenizer.nextToken();
						String topicText = stringTokenizer.nextToken();
						String placeId = stringTokenizer.nextToken();
						String placeText = stringTokenizer.nextToken();
						
						if(NumberUtils.isNumber(singleId)){
							topicsId.add(NumberUtils.createInteger(singleId));
						}else{
							topicsId.add(new Integer(0));
						}
						topics.add(URIUtil.decode(topicText, "UTF-8"));
						if(NumberUtils.isNumber(placeId)){
							topicsPlaceId.add(NumberUtils.createInteger(placeId));
						}else{
							topicsPlaceId.add(new Integer(0));
						}
						topicsPlace.add(URIUtil.decode(placeText, "UTF-8"));
					}
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
					topicsId.remove(topicsId.size()-1);
					topicsPlaceId.remove(topicsPlaceId.size() - 1);
				}
			}
		} else {
			topicsId = new ArrayList<Integer>(0);
			topics = new ArrayList<String>(0);
		}
		
		//Topics Place
		// Place
//		if ((command.getTopicPlace() != null) && (command.getTopicPlace().size() >0)) {
//			topicsPlaceId = new ArrayList<Integer>(command.getTopicPlace().size());
//			topicsPlace = new ArrayList<String>(command.getTopicPlace().size());
//			
//			for (String singleWord : command.getTopicPlace()) {
//				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "\u00E7" in "%E7"
//				singleWord = singleWord.replace("+", "%20");
//				singleWord = singleWord.replace("%E7", "\u00E7");
//				
//				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
//				try {
//					if (stringTokenizer.countTokens() == 0) {
//						continue;
//					} else if (stringTokenizer.countTokens() == 1) {
//						// string format is |text
//						topicsPlaceId.add(new Integer(0));
//						topicsPlace.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
//					} else if (stringTokenizer.countTokens() == 2) {
//						// string format is number|text
//						String singleId = stringTokenizer.nextToken();
//						String singleText = stringTokenizer.nextToken();
//						// Check if field is correct
//						if (NumberUtils.isNumber(singleId)) { 
//							topicsPlaceId.add(NumberUtils.createInteger(singleId));
//						} else {
//							//Empty placeId is equal to 0
//							topicsPlaceId.add(new Integer(0));
//						}
//						topicsPlace.add(URIUtil.decode(singleText, "UTF-8"));
//					}
//				} catch (NumberFormatException numberFormatException) {
//					logger.debug(numberFormatException);
//				} catch (URIException uriException) {
//					logger.debug(uriException);
//					topicsPlaceId.remove(topicsPlaceId.size()-1);
//				}
//			}
//		} else {
//			topicsPlaceId = new ArrayList<Integer>(0);
//			topicsPlace = new ArrayList<String>(0);
//		}

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

		//Date Created
		if ((command.getDateCreated() != null) && (command.getDateCreated().size() >0)) {
			datesCreatedTypes = new ArrayList<DateType>(command.getDateCreated().size());
			datesCreated = new ArrayList<Date>(command.getDateCreated().size());
			datesCreatedBetween = new ArrayList<Date>(command.getDateCreated().size());
			
			for (String singleWord : command.getDateCreated()) {
				//e.g. After|20120112|20120112
				String[] fields = StringUtils.splitPreserveAllTokens(singleWord,"|");
				datesCreatedTypes.add(DateType.valueOf(fields[0]));
				datesCreated.add(DateUtils.getDateFromString(fields[1]));
				datesCreatedBetween.add(DateUtils.getDateFromString(fields[2]));
			}
		} else {
			datesCreatedTypes = new ArrayList<DateType>(0);
			datesCreated = new ArrayList<Date>(0);
			datesCreatedBetween = new ArrayList<Date>(0);
		}

		//Date lastUpdate
		if ((command.getDateLastUpdate() != null) && (command.getDateLastUpdate().size() >0)) {
			datesLastUpdateTypes = new ArrayList<DateType>(command.getDateLastUpdate().size());
			datesLastUpdate = new ArrayList<Date>(command.getDateLastUpdate().size());
			datesLastUpdateBetween = new ArrayList<Date>(command.getDateLastUpdate().size());
			
			for (String singleWord : command.getDateLastUpdate()) {
				//e.g. After|20120112|20120112
				String[] fields = StringUtils.splitPreserveAllTokens(singleWord,"|");
				datesLastUpdateTypes.add(DateType.valueOf(fields[0]));
				datesLastUpdate.add(DateUtils.getDateFromString(fields[1]));
				datesLastUpdateBetween.add(DateUtils.getDateFromString(fields[2]));
			}
		} else {
			datesLastUpdateTypes = new ArrayList<DateType>(0);
			datesLastUpdate = new ArrayList<Date>(0);
			datesLastUpdateBetween = new ArrayList<Date>(0);
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
		
		// Insert
		if ((command.getInsert() != null && command.getInsert().size() > 0)) {
			insertNums = new ArrayList<String>(command.getInsert().size());
			
			for(String insert : command.getInsert()) {
				insertNums.add(insert);
			}
		} else {
			insertNums = new ArrayList<String>(0);
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
		
		//FolioMod
		if(command.getFolioMod() != null && command.getFolioMod().size() > 0){
			folioMods = new ArrayList<String>(command.getFolioMod().size());
			folioMods.addAll(command.getFolioMod());
		}else{
			folioMods = new ArrayList<String>(0);
		}
		
		//EntryId
		if((command.getDocId() != null) && (command.getDocId().size() > 0)){
			docIds = new ArrayList<String>(command.getDocId().size());
			
			for(String singleWord : command.getDocId()){
				try{
					docIds.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
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

		//Users
		if (command.getUser() != null){
			userActionTypes = new ArrayList<UserActionType>(command.getUser().size());
			users = new ArrayList<String>(command.getUser().size());
			
			for (String singleUser : command.getUser()) {
				//MD: This is for refine search when the URLencoder change the space in "+"
				singleUser = singleUser.replace("+", "%20");
				singleUser = singleUser.replace("\"", "%22");
				singleUser = singleUser.replace("'", "%27");
				//RR: And this is for replacing special characters with unicode values
				singleUser = URLTransformer.decode(singleUser);
				StringTokenizer stringTokenizer = new StringTokenizer(singleUser, "|");
				try {
					if (stringTokenizer.countTokens() == 2) {
						userActionTypes.add(UserActionType.valueOf(stringTokenizer.nextToken()));
						StringBuffer tempString = new StringBuffer(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
						if(StringUtils.countMatches(tempString.toString(), "\"")%2 != 0){
							tempString.setCharAt(tempString.lastIndexOf("\""), ' ');
						}
						users.add(tempString.toString());
						
					} else {
						continue;
					}
				}catch (URIException uriException) {
					logger.debug(uriException);
					wordsTypes.remove(wordsTypes.size()-1);
				} 
			}
		} else {
			userActionTypes = new ArrayList<UserActionType>(0);
			users = new ArrayList<String>(0);
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
	public Boolean empty() {
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
				(datesLastUpdateTypes.size()>0) ||
				(volumes.size()>0) ||
				(insertNums.size()>0) ||
				(folios.size()>0) ||
				(folioMods.size()>0) ||
				(docIds.size()>0) ||
				(users.size()>0)) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
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
	 * @return the folioMods
	 */
	public List<String> getFolioMods() {
		return folioMods;
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
	 * @return the insertNums
	 */
	public List<String> getInsertNums() {
		return insertNums;
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
	 * @return the users
	 */
	public List<String> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUser(List<String> users) {
		this.users = users;
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
	 * @param folioMods the folioMods to set
	 */
	public void setFolioMods(List<String> folioMods) {
		this.folioMods = folioMods;
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
	 * @param insertNums the insertNums to set
	 */
	public void setInsertNums(List<String> insertNums) {
		this.insertNums = insertNums;
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
	 * @return the datesLastUpdateTypes
	 */
	public List<DateType> getDatesLastUpdateTypes() {
		return datesLastUpdateTypes;
	}

	/**
	 * @param datesLastUpdateTypes the datesLastUpdateTypes to set
	 */
	public void setDatesLastUpdateTypes(List<DateType> datesLastUpdateTypes) {
		this.datesLastUpdateTypes = datesLastUpdateTypes;
	}

	/**
	 * @return the datesLastUpdate
	 */
	public List<Date> getDatesLastUpdate() {
		return datesLastUpdate;
	}

	/**
	 * @param datesLastUpdate the datesLastUpdate to set
	 */
	public void setDatesLastUpdate(List<Date> datesLastUpdate) {
		this.datesLastUpdate = datesLastUpdate;
	}

	/**
	 * @return the datesLastUpdateBetween
	 */
	public List<Date> getDatesLastUpdateBetween() {
		return datesLastUpdateBetween;
	}

	/**
	 * @param datesLastUpdateBetween the datesLastUpdateBetween to set
	 */
	public void setDatesLastUpdateBetween(List<Date> datesLastUpdateBetween) {
		this.datesLastUpdateBetween = datesLastUpdateBetween;
	}

	public void setDatesCreated(List<Date> datesCreated) {
		this.datesCreated = datesCreated;
	}

	public List<Date> getDatesCreated() {
		return datesCreated;
	}

	/**
	 * @return the datesCreatedBetween
	 */
	public List<Date> getDatesCreatedBetween() {
		return datesCreatedBetween;
	}

	/**
	 * @param datesCreatedBetween the datesCreatedBetween to set
	 */
	public void setDatesCreatedBetween(List<Date> datesCreatedBetween) {
		this.datesCreatedBetween = datesCreatedBetween;
	}

	/**
	 * @return the datesCreatedTypes
	 */
	public List<DateType> getDatesCreatedTypes() {
		return datesCreatedTypes;
	}

	/**
	 * @param datesCreatedTypes the datesCreatedTypes to set
	 */
	public void setDatesCreatedTypes(List<DateType> datesCreatedTypes) {
		this.datesCreatedTypes = datesCreatedTypes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM Document WHERE ");

		// Words
		appendToJpaQuery(jpaQuery, getWordSubQuery());

		// Person
		appendToJpaQuery(jpaQuery, getPersonSubQuery());

		// Place
		appendToJpaQuery(jpaQuery, getPlaceSubQuery());
		
		// Sender
		appendToJpaQuery(jpaQuery, getSenderSubQuery());

		// From
		appendToJpaQuery(jpaQuery, getFromSubQuery());

		// Recipient
		appendToJpaQuery(jpaQuery, getRecipientSubQuery());

		// To
		appendToJpaQuery(jpaQuery, getToSubQuery());

		// RefersTo
		appendToJpaQuery(jpaQuery, getRefersToSubQuery());

		// Extract
		appendToJpaQuery(jpaQuery, getExtractSubQuery());
		
		// Synopsis
		appendToJpaQuery(jpaQuery, getSynopsisSubQuery());

		// Topics
		appendToJpaQuery(jpaQuery, getTopicSubQuery());
		
		// Topic Place
//		appendToJpaQuery(jpaQuery, getTopicPlaceSubQuery());

		// Date
		appendToJpaQuery(jpaQuery, getDateSubQuery());

		// Last update
		appendToJpaQuery(jpaQuery, getLastUpdateSubQuery());

		// Date created
		appendToJpaQuery(jpaQuery, getDateCreatedSubQuery());

		// Volume
		appendToJpaQuery(jpaQuery, getVolumeSubQuery());
		
		// Insert
		appendToJpaQuery(jpaQuery, getInsertSubQuery());
		
		// Folio
		appendToJpaQuery(jpaQuery, getFolioSubQuery());
		
		// FolioMod
		appendToJpaQuery(jpaQuery, getFolioModSubQuery());
		
		// EntryId
		appendToJpaQuery(jpaQuery, getDocIdsSubQuery());
		
		// User
		appendToJpaQuery(jpaQuery, getUserSubQuery());
		
		// LogicalDelete
		appendToJpaQuery(jpaQuery, getLogicalDeleteSubQuery());

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
		StringBuilder stringBuilder = new StringBuilder(0);
		appendToStringBuilder(stringBuilder, words, "Words: ");

		appendToStringBuilder(stringBuilder, extract, "Transcription: ");

		appendToStringBuilder(stringBuilder, synopsis, "Synopsis: ");

		appendToStringBuilder(stringBuilder, person, "Person: ");

		appendToStringBuilder(stringBuilder, place, "Place: ");

		appendToStringBuilder(stringBuilder, sender, "Sender: ");
		appendToStringBuilder(stringBuilder, from, "From: ");
		
		appendToStringBuilder(stringBuilder, recipient, "Recipient: ");
		appendToStringBuilder(stringBuilder, to, "To: ");
		
		appendToStringBuilder(stringBuilder, refersTo, "Refers to: ");
		
		appendToStringBuilder(stringBuilder, topics, topicsPlace, "Topics: ");

		appendToStringBuilder(stringBuilder, datesYear, "Date Year: ");
		appendToStringBuilder(stringBuilder, datesMonth, "Date Month: ");
		appendToStringBuilder(stringBuilder, datesDay, "Date Day: ");
		appendToStringBuilder(stringBuilder, datesYearBetween, "Between Date Year: ");
		appendToStringBuilder(stringBuilder, datesMonthBetween, "Between Date Month: ");
		appendToStringBuilder(stringBuilder, datesDayBetween, "Between Date Day");

		appendToStringBuilder(stringBuilder, volumes, "Volumes: ");
		appendToStringBuilder(stringBuilder, volumesBetween, "Between Volumes: ");

		appendToStringBuilder(stringBuilder, insertNums, "Inserts: ");

		appendToStringBuilder(stringBuilder, folios, "Folios: ");
		appendToStringBuilder(stringBuilder, foliosBetween, "Between Folios: ");
		appendToStringBuilder(stringBuilder, folioMods, "Folio Mod: ");
		
		appendToStringBuilder(stringBuilder, docIds, "Doc ID: ");
		
		return stringBuilder.toString();
	}
	
	/* Privates */
	
	private String getWordSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (words.size() > 0) {
			for (int i = 0; i < words.size(); i++) {
				String currentWords = words.get(i);
				List<String> exactWords = new ArrayList<String>();
				
				//MD: This code is to identify the words between double quotes
				while (currentWords.contains("\"")) {
					//First double quote
					int from = currentWords.indexOf("\"");
					//Second double quote
					int to = currentWords.indexOf("\"", from + 1);
					//If there is the second double quote or not
					if (to != -1) {
						//Add the exact words to the list and remove them from the string
						exactWords.add(currentWords.substring(from + 1, to));
						currentWords = currentWords.substring(0, from) + currentWords.substring(to + 1, currentWords.length());
					} else {
						currentWords = currentWords.replace("\"", " ");
						
					}
				}
				
				String[] wordsSingleWordSearch = StringUtils.split(currentWords, " ");
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				if (wordsTypes.get(i).equals(WordType.Extract)) {
					for (int j = 0; j < exactWords.size(); j++) {
						builder.append("(synExtract.docExtract LIKE '% ")
							.append(exactWords.get(j).replace("'", "''"))
							.append(" %')");
						if (j < (exactWords.size() - 1)) {
							builder.append(" AND ");
						}
					}
					if (exactWords.size() > 0 && wordsSingleWordSearch.length > 0) {
						builder.append(" AND ");
					}
					for (int j = 0; j < wordsSingleWordSearch.length; j++) {
						builder.append("(synExtract.docExtract LIKE '%")
							.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"))
							.append("%')");
						if (j < (wordsSingleWordSearch.length - 1)) {
							builder.append(" AND ");
						}
					}
				} else if (wordsTypes.get(i).equals(WordType.Synopsis)) {
					for (int j = 0; j < exactWords.size(); j++) {
						builder.append("(synExtract.synopsis LIKE '% ")
							.append(exactWords.get(j).replace("'", "''"))
							.append(" %')");
						if (j < (exactWords.size() - 1)) {
							builder.append(" AND ");
						}
					}
					if (exactWords.size() > 0 && wordsSingleWordSearch.length > 0) {
						builder.append(" AND ");
					}
					for (int j = 0; j < wordsSingleWordSearch.length; j++) {
						builder.append("(synExtract.synopsis LIKE '%")
							.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"))
							.append("%')");
						if (j < (wordsSingleWordSearch.length - 1)) {
							builder.append(" AND ");
						}
					}
				} else if (wordsTypes.get(i).equals(WordType.SynopsisAndExtract)) {
					for (int j = 0; j < exactWords.size(); j++) {
						builder.append("((synExtract.docExtract LIKE '% ")
							.append(exactWords.get(j).replace("'", "''"))
							.append(" %') OR ")
							.append("(synExtract.synopsis LIKE '% ")
							.append(exactWords.get(j).replace("'", "''"))
							.append(" %'))");
						if (j < (exactWords.size() - 1)) {
							builder.append(" AND ");
						}
					}
					if (exactWords.size() > 0 && wordsSingleWordSearch.length > 0) {
						builder.append(" AND ");
					}					
					for (int j = 0; j < wordsSingleWordSearch.length; j++) {
						builder.append("((synExtract.docExtract LIKE '%")
							.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"))
							.append("%') OR ")
							.append("(synExtract.synopsis LIKE '%")
							.append(wordsSingleWordSearch[j].toLowerCase().replace("'", "''"))
							.append("%'))");
						if (j < (wordsSingleWordSearch.length - 1)) {
							builder.append(" AND ");
						}
					}
				}
			}
		}
		return builder.toString();
	}
	
	private String getPersonSubQuery() {
		String personIdQuery = "(entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EpLink WHERE person.personId = :xxx) OR senderPeople.personId = :xxx OR recipientPeople.personId = :xxx)";
		String personNameQuery = "entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EpLink WHERE person.mapNameLf like '%:yyy%')" /*"%') or altName.altName like '%':yyy%')*/;
		return getIdOrNamesToJpa(personId, personIdQuery, ":xxx", person, personNameQuery, ":yyy");
	}
	
	private String getPlaceSubQuery() {
		String placeIdQuery = "(senderPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = :xxx)" +
				" OR recipientPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = :xxx)" +
				" OR entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE place.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = :xxx)))";
		String placeNameQuery = "(senderPlace.placeName LIKE '%:yyy%'" +
				" OR recipientPlace.placeName LIKE '%':yyy%'" +
				" OR entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE place.placeName LIKE '%:yyy'%'))";
		return getIdOrNamesToJpa(placeId, placeIdQuery, ":xxx",
				place, placeNameQuery, ":yyy");
	}
	
	private String getSenderSubQuery() {
		return getIdOrNamesToJpa(senderId, "(senderPeople.personId = :xxx)", ":xxx",
				sender, "(senderPeople.mapNameLf LIKE '%:yyy%')", ":yyy");
	}
	
	private String getFromSubQuery() {
		return getIdOrNamesToJpa(fromId, "(senderPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = :xxx))", ":xxx", 
				from, "(senderPlace.placeNameFull LIKE '%:yyy%')", ":yyy");
	}
	
	private String getRecipientSubQuery() {
		return getIdOrNamesToJpa(recipientId, "(recipientPeople.personId = :xxx)", ":xxx", 
				recipient, "(recipientPeople.mapNameLf LIKE '%:yyy%')", ":yyy");
	}
	
	private String getToSubQuery() {
		return getIdOrNamesToJpa(toId, "(recipientPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = :xxx))", ":xxx", 
				to, "(recipientPlace.placeNameFull LIKE '%:yyy%')", ":yyy");
	}
	
	private String getRefersToSubQuery() {
		return getIdOrNamesToJpa(refersToId, "entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EpLink WHERE person.personId = :xxx AND docRole IS NULL))", ":xxx", 
				refersTo, "entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EpLink WHERE person.mapNameLf LIKE '%:yyy%' AND docRole IS NULL))", ":yyy");
	}
	
	private String getExtractSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (extract.size() > 0) {
			for (int i = 0; i < extract.size(); i++) {
				String currentWords = extract.get(i);
				List<String> exactWords = new ArrayList<String>();
				
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				
				//MD: This code is to identify the words between double quotes
				while (currentWords.contains("\"")) {
					//First double quote
					int from = currentWords.indexOf("\"");
					//Second double quote
					int to = currentWords.indexOf("\"", from + 1);
					//If there is the second double quote or not
					if (to != -1) {
						//Add the exact words to the list and remove them from the string
						exactWords.add(currentWords.substring(from + 1, to));
						currentWords = currentWords.substring(0, from) + currentWords.substring(to + 1, currentWords.length());
					} else {
						currentWords = currentWords.replace("\"", " ");
						
					}
				}
				
				String[] wordsSingleExtract = StringUtils.split(currentWords, " ");
				for (int j = 0; j < exactWords.size(); j++) {
					builder.append("(synExtract.docExtract LIKE '% ");
					builder.append(exactWords.get(j).replace("'", "''"));
					builder.append(" %')");
					if (j < (exactWords.size() - 1)) {
						builder.append(" AND ");
					}
				}
				if (exactWords.size() > 0 && wordsSingleExtract.length > 0) {
					builder.append(" AND ");
				}
				for (int j = 0; j < wordsSingleExtract.length; j++) {
					builder.append("(synExtract.docExtract LIKE '%");
					builder.append(wordsSingleExtract[j].replace("'", "''"));
					builder.append("%')");
					if ( j< (wordsSingleExtract.length - 1)) {
						builder.append(" AND ");
					}
				}
			}
		}
		return builder.toString();
	}
	
	private String getSynopsisSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (synopsis.size() > 0) {
			for (int i = 0; i < synopsis.size(); i++) {
				String currentWords = synopsis.get(i);
				List<String> exactWords = new ArrayList<String>();
				
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				
				//MD: This code is to identify the words between double quotes
				while (currentWords.contains("\"")) {
					//First double quote
					int from = currentWords.indexOf("\"");
					//Second double quote
					int to = currentWords.indexOf("\"", from + 1);
					//If there is the second double quote or not
					if (to != -1) {
						//Add the exact words to the list and remove them from the string
						exactWords.add(currentWords.substring(from + 1, to));
						currentWords = currentWords.substring(0, from) + currentWords.substring(to + 1, currentWords.length());
					} else {
						currentWords = currentWords.replace("\"", " ");
						
					}
				}
				
				String[] wordsSingleSynopsis = StringUtils.split(currentWords, " ");
				for (int j = 0; j < exactWords.size(); j++) {
					builder.append("(synExtract.synopsis LIKE '% ");
					builder.append(exactWords.get(j).replace("'", "''"));
					builder.append(" %')");
					if(j < (exactWords.size() - 1)){
						builder.append(" AND ");
					}
				}
				if (exactWords.size() > 0 && wordsSingleSynopsis.length > 0) {
					builder.append(" AND ");
				}
				for (int j = 0; j < wordsSingleSynopsis.length; j++) {
					builder.append("(synExtract.synopsis LIKE '%");
					builder.append(wordsSingleSynopsis[j].replace("'", "''"));
					builder.append("%')");
					if (j < (wordsSingleSynopsis.length - 1)) {
						builder.append(" AND ");
					}
				}
			}
		}
		return builder.toString();
	}
	
	private String getTopicSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (topicsId.size() > 0) {
			for (int i=0; i < topicsId.size(); i++) {
				if (topicsId.get(i) > 0) {
					if (builder.length() > 0) {
						builder.append(" AND ");
					}
					
					builder.append("entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE topic.topicId = ")
						.append(topicsId.get(i).toString());
					if (topicsPlaceId.size() > 0 && topicsPlaceId.get(i) != 0) {
						builder.append(" AND place.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = ")
							.append(topicsPlaceId.get(i).toString())
							.append(")");
					}
					builder.append(")");
				} 
			}
		}
		return builder.toString();
	}
	
	private String getTopicPlaceSubQuery() {
		return getIdOrNamesToJpa(topicsPlaceId, "entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE place.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = :xxx))", ":xxx",
				topicsPlace, "entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE place.placeName like '%:yyy%')", ":yyy");
	}
	
	private String getDateSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (datesTypes.size() > 0) {
			for (int i = 0; i < datesTypes.size(); i++) {
				if (datesTypes.get(i) == null) {
					continue;
				} 
				
				if (builder.length() > 0) {
					builder.append(" AND ");
				}

				if (datesTypes.get(i).equals(DateType.From)) {
					builder.append("(sortableDateInt >= ")
						.append(DateUtils.getIntegerDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)))
						.append(")");
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					builder.append("(sortableDateInt < ")
						.append(DateUtils.getIntegerDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)))
						.append(")");
				} else if (datesTypes.get(i).equals(DateType.Between)) {
					if (datesYear.get(i) != null && datesYearBetween.get(i) != null) {
						builder.append("(sortableDateInt >= ")
							.append(DateUtils.getIntegerDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)))
							.append(") AND (sortableDateInt < ")
							.append(DateUtils.getIntegerDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)))
							.append(")");
					} else {
						// RR: Months are specified due to client validation
						boolean moreThanOneMonth = !datesMonth.get(i).equals(datesMonthBetween.get(i)) || (datesDay.get(i) != null && datesDayBetween.get(i) != null && (datesDay.get(i) > datesDayBetween.get(i)));
						int mFrom = datesMonth.get(i) < 12 ? datesMonth.get(i) + 1 : 1;
						if (moreThanOneMonth && mFrom != datesMonthBetween.get(i)) {
							int mTo = datesMonthBetween.get(i) > 1 ? datesMonthBetween.get(i) - 1 : 12;
							builder.append("(docMonthNum >= " + mFrom + " AND docMonthNum <= " +  mTo + " OR ");
						}
						
						// FIXME: RR - include deleted conditions only when it is necessary to include not dated documents in the results
						builder.append("(docMonthNum = " + datesMonth.get(i) /* + " OR docMonthNum IS NULL " */);
						if (datesDay.get(i) != null)
							builder.append(" AND docDay >= " + datesDay.get(i) /* + " OR docDay IS NULL " */);
						if (moreThanOneMonth)
							builder.append(") OR (docMonthNum = " + datesMonthBetween.get(i) /* + " OR docMonthNum IS NULL " */);
						builder.append(datesDayBetween.get(i) != null ? (" AND docDay <= " + datesDayBetween.get(i) /* + " OR docDay IS NULL " */) + ")" : ")");
						if (moreThanOneMonth)
							builder.append(")");
					}
					
				} else if (datesTypes.get(i).equals(DateType.InOn)) {
					if (datesYear.get(i) != null) {
						builder.append("(yearModern = ")
							.append(datesYear.get(i) + " OR (docYear = ")
							.append(datesYear.get(i) + " AND yearModern IS NULL))");
						if(datesMonth.get(i) != null || datesDay.get(i) != null){
							builder.append(" AND ");
						}
					}
					if (datesMonth.get(i) != null) {
						builder.append("(docMonthNum = ")
							.append(datesMonth.get(i) + " )");
						if(datesDay.get(i) != null){
							builder.append(" AND ");
						}
					}
					if (datesDay.get(i) != null) {
						builder.append("(docDay = ")
							.append(datesDay.get(i) + " )");
					}
				}
			}
		}
		return builder.toString();
	}
	
	private String getLastUpdateSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (datesLastUpdateTypes.size() > 0) {
			for (int i = 0; i < datesLastUpdateTypes.size(); i++) {
				if (datesLastUpdateTypes.get(i) == null) {
					continue;
				} 
				
				if (builder.length() > 0) {
					builder.append(" AND ");
				}

				if (datesLastUpdateTypes.get(i).equals(DateType.From)) {
					builder.append("(DATE_FORMAT(lastUpdate, '%Y-%m-%d') >= '")
						.append(DateUtils.getMYSQLDate(datesLastUpdate.get(i)))
						.append(")");
				} else if (datesLastUpdateTypes.get(i).equals(DateType.Before)) {
					builder.append("(DATE_FORMAT(lastUpdate, '%Y-%m-%d') <= '")
						.append(DateUtils.getMYSQLDate(datesLastUpdate.get(i)))
						.append(")");
				} else if (datesLastUpdateTypes.get(i).equals(DateType.Between)) {
					builder.append("(DATE_FORMAT(lastUpdate, '%Y-%m-%d') BETWEEN '")
						.append(DateUtils.getMYSQLDate(datesLastUpdate.get(i)))
						.append("' AND '")
						.append(DateUtils.getMYSQLDate(datesLastUpdateBetween.get(i)))
						.append("')");
				} else if (datesLastUpdateTypes.get(i).equals(DateType.InOn)){
					
				}
			}
		}
		return builder.toString();
	}
	
	private String getDateCreatedSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (datesCreatedTypes.size() > 0) {
			for (int i = 0; i < datesCreatedTypes.size(); i++) {
				if (datesCreatedTypes.get(i) == null) {
					continue;
				} 
				
				if (builder.length() > 0) {
					builder.append(" AND ");
				}

				if (datesCreatedTypes.get(i).equals(DateType.From)) {
					builder.append("(dateCreated >= '").append(DateUtils.getMYSQLDate(datesCreated.get(i))).append(")");
				} else if (datesCreatedTypes.get(i).equals(DateType.Before)) {
					builder.append("(dateCreated <= '").append(DateUtils.getMYSQLDate(datesCreated.get(i))).append(")");
				} else if (datesCreatedTypes.get(i).equals(DateType.Between)) {
					builder.append("(dateCreated BETWEEN '")
						.append(DateUtils.getMYSQLDate(datesCreated.get(i))).append("' AND '").append(DateUtils.getMYSQLDate(datesCreatedBetween.get(i)))
						.append("')");
				} else if (datesCreatedTypes.get(i).equals(DateType.InOn)){
					
				}
			}
		}
		return builder.toString();
	}
	
	private String getVolumeSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (volumes.size() > 0) {
			for (int i = 0; i < volumes.size(); i++) {
				if (VolumeUtils.isVolumeFormat(volumes.get(i))) {
					if (builder.length() > 0) {
						//MD: I need to append an "OR" clause instead an "AND"
						builder.append(" OR ");
					}

					if (volumesTypes.get(i).equals(VolumeType.Exactly)) {
						if (StringUtils.isNumeric(volumes.get(i))) {
							builder.append("(volume.volNum = ")
								.append(volumes.get(i))
								.append(" AND volLetExt IS NULL)");
						} else {
							builder.append("(volume.volNum = ")
								.append(VolumeUtils.extractVolNum(volumes.get(i)))
								.append(" AND volume.volLetExt = '")
								.append(VolumeUtils.extractVolLetExt(volumes.get(i)))
								.append("')");
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						builder.append("(volume.volNum >= ")
							.append(volumes.get(i))
							.append(" AND volume.volNum <= ")
							.append(volumesBetween.get(i))
							.append(")");
					}
				} else {
					// if volume value is not in volume format we discard it!
					continue;
				}
			}
		}
		return builder.toString();
	}
	
	private String getInsertSubQuery() {
		return listStringEqualsToJpa(insertNums, "insertNum", false);
	}
	
	private String getFolioSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (folios.size() > 0) {
			for(int i = 0; i < folios.size(); i++) {
				if (StringUtils.isNumeric(folios.get(i))) {
					if (builder.length() > 0) {
						//MD: I need to append an "OR" clause instead an "AND"
						builder.append(" OR ");
					}
					
					if (foliosTypes.get(i).equals(FolioType.Exactly)) {
						builder.append("(folioNum = ").append(folios.get(i)).append(")");
					} else if (foliosTypes.get(i).equals(FolioType.Between)) {
						builder.append("(folioNum >= ").append(folios.get(i)).append(" AND folioNum <= ").append(foliosBetween.get(i)).append(")");
					}
				} else {
					continue;
				}
			}
		}
		return builder.toString();
	}
	
	private String getFolioModSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (folioMods.size() > 0) {
			for(int i = 0; i < folioMods.size(); i++) {
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				String[] wordsFolioMods = StringUtils.split(folioMods.get(i), " ");
				for (int j = 0; j < wordsFolioMods.length; j++) {
					if (j > 0) {
						builder.append(" AND ");
					}
					if (wordsFolioMods[j].equalsIgnoreCase("bis")) {
						builder.append("(folioMod LIKE 'bis')");
					}
					if (wordsFolioMods[j].equalsIgnoreCase("ter")) {
						builder.append("(folioMod LIKE 'ter')");
					}
					if (wordsFolioMods[j].equalsIgnoreCase("other")) {
						builder.append("(folioMod IS NOT NULL AND folioMod NOT LIKE 'bis' AND folioMod NOT LIKE 'ter')");
					}
				}
			}
		}
		return builder.toString();
	}
	
	private String getDocIdsSubQuery() {
		return listIntegerToJpa(docIds, "entryId", false);
	}
	
	private String getLogicalDeleteSubQuery() {
		return booleanToJPA(logicalDelete == null ? Boolean.FALSE : logicalDelete, "logicalDelete");
	}
	
	private String getUserSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (users.size() > 0) {
			for (int i = 0; i < users.size(); i++) {
				if (builder.length() > 0) {
					builder.append(" AND ");
				}

				if (userActionTypes.get(i).equals(UserActionType.CreatedBy)) {
					builder.append("(createdBy = ")
						.append("'").append(users.get(i)).append("'")
						.append(")");
				} else if (userActionTypes.get(i).equals(UserActionType.LastUpdateBy)) {
					builder.append("(lastUpdateBy = ")
						.append("'").append(users.get(i)).append("'")
						.append(")");
				}
			}
		}
		return builder.toString();
	}
	
	private void appendToJpaQuery(StringBuilder jpaQuery, String subQuery) {
		if (subQuery.length() > 0) {
			if (jpaQuery.length() > 20) {
				jpaQuery.append(" AND ");
			}
			jpaQuery.append("(").append(subQuery).append(")");
		}
	}
	
	private void appendToStringBuilder(StringBuilder stringBuilder, List<?> list, String title) {
		if (list != null && !list.isEmpty()) {
			if (stringBuilder.length() > 0) {
				stringBuilder.append("AND ");
			}
			stringBuilder.append(title);
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					stringBuilder.append("AND ");
				}
				stringBuilder.append(list.get(i)).append(" ");
			}
		}
	}
	
	private void appendToStringBuilder(StringBuilder stringBuilder, List<?> masterList, List<?> slaveList, String title) {
		if (masterList != null && !masterList.isEmpty()) {
			if (stringBuilder.length()>0) {
				stringBuilder.append("AND ");
			}
			stringBuilder.append(title);
			for (int i = 0; i < masterList.size(); i++) {
				if (i > 0) {
					stringBuilder.append("AND ");
				}
				stringBuilder.append(masterList.get(i));
				if (!slaveList.isEmpty() && slaveList.size() > i) {
					stringBuilder.append(" - ").append(slaveList.get(i));
				}
				stringBuilder.append(" ");
			}
		}
	}
}

