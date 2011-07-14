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
import org.medici.docsources.command.search.AdvancedSearchDocumentsCommand;
import org.medici.docsources.command.search.SaveUserSearchFilterCommand;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.common.util.VolumeUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchDocument implements AdvancedSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private Logger logger = Logger.getLogger(this.getClass()); 

	private List<String> words;
	private List<WordType> wordsTypes;
	private List<String> volumes;
	private List<String> volumesBetween;
	private List<VolumeType> volumesTypes;
	private List<DateType> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesMonth;
	private List<Integer> datesDay;
	private List<Integer> datesYearBetween;
	private List<Integer> datesMonthBetween;
	private List<Integer> datesDayBetween;
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
	private List<String> refersTo;

	/**
	 * 
	 */
	public AdvancedSearchDocument() {
		super();
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromAdvancedSearchDocumentsCommand(AdvancedSearchDocumentsCommand command) {
		//Words
		if ((command.getWord() != null) && (command.getWord().size() >0)) {
			wordsTypes = new ArrayList<WordType>(command.getWord().size());
			words = new ArrayList<String>(command.getWord().size());
			
			for (String singleWord : command.getWord()) {
				String[] fields = singleWord.split("\\|");
				
				if (fields.length != 2) {
					logger.error("Wrong field words " + singleWord + " skipped.");
					continue;
				} else {
					wordsTypes.add(WordType.valueOf(fields[0]));
					words.add(fields[1]);
				}
			}
		} else {
			wordsTypes = new ArrayList<WordType>(0);
			words = new ArrayList<String>(0);
		}

		//Volume
		if ((command.getVolume() != null) && (command.getVolume().size() >0)) {
			volumesTypes = new ArrayList<VolumeType>(command.getVolume().size());
			volumes = new ArrayList<String>(command.getVolume().size());
			volumesBetween = new ArrayList<String>(command.getVolume().size());
			
			for (String singleWord : command.getVolume()) {
				String[] fields = singleWord.split("\\|");
				
				if (fields.length == 2) {
					volumesTypes.add(VolumeType.valueOf(fields[0]));
					volumes.add(fields[1]);
				} else if (fields.length == 3){
					volumesTypes.add(VolumeType.valueOf(fields[0]));
					volumes.add(fields[1]);
					volumesBetween.add(fields[2]);
				} else {
					logger.error("Wrong field volumes " + singleWord + " skipped.");
					continue;
				}
			}
		} else {
			volumesTypes = new ArrayList<VolumeType>(0);
			volumes = new ArrayList<String>(0);
			volumesBetween = new ArrayList<String>(0);
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
				String[] fields = singleWord.split("\\|");
				
				if (fields.length == 4) {
					datesTypes.add(DateType.valueOf(fields[0]));
					datesYear.add(NumberUtils.toInt(fields[1], 1));
					datesMonth.add(NumberUtils.toInt(fields[2], 1));
					datesDay.add(NumberUtils.toInt(fields[3], 1));
				} else if (fields.length == 7) {
					datesTypes.add(DateType.valueOf(fields[0]));
					datesYear.add(NumberUtils.toInt(fields[1], 1));
					datesMonth.add(NumberUtils.toInt(fields[2], 1));
					datesDay.add(NumberUtils.toInt(fields[3], 1));
					datesYearBetween.add(NumberUtils.toInt(fields[4], 1));
					datesMonthBetween.add(NumberUtils.toInt(fields[5], 1));
					datesDayBetween.add(NumberUtils.toInt(fields[6], 1));
				} else {
					logger.error("Wrong field dates " + singleWord + " skipped.");
					continue;
				}
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
		
		// Extract
		if ((command.getExtract() != null) &&  (command.getExtract().size() >0)) {
			extract = new ArrayList<String>(command.getExtract().size());
			
			for (String singleWord : command.getExtract()) {
				extract.add(singleWord);
			}
		} else {
			extract = new ArrayList<String>(0);
		}
		
		// Synopsis
		if ((command.getSynopsis() != null) && (command.getSynopsis().size() >0)) {
			synopsis = new ArrayList<String>(command.getSynopsis().size());
			
			for (String singleWord : command.getSynopsis()) {
				synopsis.add(singleWord);
			}
		} else {
			synopsis = new ArrayList<String>(0);
		}

		// Topics
		if ((command.getTopic() != null) && (command.getTopic().size() >0)) {
			topicsId = new ArrayList<Integer>(command.getTopic().size());
			topics = new ArrayList<String>(command.getTopic().size());
			
			for (String singleWord : command.getTopic()) {
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
		} else {
			topicsId = new ArrayList<Integer>(0);
			topics = new ArrayList<String>(0);
		}

		// Person
		if ((command.getPerson() != null) && (command.getPerson().size() >0)) {
			personId = new ArrayList<Integer>(command.getPerson().size());
			person = new ArrayList<String>(command.getPerson().size());
			
			for (String singleWord : command.getPerson()) {
				String[] fields = StringUtils.split(singleWord, '|');
				
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
		} else {
			personId = new ArrayList<Integer>(0);
			person = new ArrayList<String>(0);
		}

		// Place
		if ((command.getPlace() != null) && (command.getPlace().size() >0)) {
			placeId = new ArrayList<Integer>(command.getPlace().size());
			place = new ArrayList<String>(command.getPlace().size());
			
			for (String singleWord : command.getPlace()) {
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
		} else {
			placeId = new ArrayList<Integer>(0);
			place = new ArrayList<String>(0);
		}

		// Sender
		if ((command.getSender() != null) && (command.getSender().size() >0)) {
			senderId = new ArrayList<Integer>(command.getSender().size());
			sender = new ArrayList<String>(command.getSender().size());
			
			for (String singleWord : command.getSender()) {
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
						logger.error("Wrong senders id " + singleWord + " skipped.");
					}
					sender.add(fields[1]);
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
				String[] fields = singleWord.split("\\|");
				
				if (fields.length != 2) {
					logger.error("Wrong field from " + singleWord + " skipped.");
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
		} else {
			fromId = new ArrayList<Integer>(0);
			from= new ArrayList<String>(0);
		}

		// Recipient
		if ((command.getRecipient() != null) && (command.getRecipient().size() >0)) {
			recipientId = new ArrayList<Integer>(command.getRecipient().size());
			recipient = new ArrayList<String>(command.getRecipient().size());
			
			for (String singleWord : command.getRecipient()) {
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
		} else {
			recipientId = new ArrayList<Integer>(0);
			recipient = new ArrayList<String>(0);
		}

		// To
		if ((command.getTo() != null) && (command.getTo().size() >0)) {
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
		} else {
			toId = new ArrayList<Integer>(0);
			to = new ArrayList<String>(0);
		}

		// ResTo;
		if ((command.getRefersTo() != null) && (command.getRefersTo().size() >0)) {
			refersTo = new ArrayList<String>(command.getRefersTo().size());
			
			for (String singleWord : command.getRefersTo()) {
				refersTo.add(singleWord);
			}
		} else {
			refersTo = new ArrayList<String>(0);
		}
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromSaveUserSearchFilterCommand(SaveUserSearchFilterCommand command) {
		//Words
		if ((command.getWord() != null) && (command.getWord().size() >0)) {
			wordsTypes = new ArrayList<WordType>(command.getWord().size());
			words = new ArrayList<String>(command.getWord().size());
			
			for (String singleWord : command.getWord()) {
				String[] fields = singleWord.split("\\|");
				
				if (fields.length != 2) {
					logger.error("Wrong field words " + singleWord + " skipped.");
					continue;
				} else {
					wordsTypes.add(WordType.valueOf(fields[0]));
					words.add(fields[1]);
				}
			}
		} else {
			wordsTypes = new ArrayList<WordType>(0);
			words = new ArrayList<String>(0);
		}

		// Extract
		if ((command.getExtract() != null) &&  (command.getExtract().size() >0)) {
			extract = new ArrayList<String>(command.getExtract().size());
			
			for (String singleWord : command.getExtract()) {
				extract.add(singleWord);
			}
		} else {
			extract = new ArrayList<String>(0);
		}
		
		// synopsis
		if ((command.getSynopsis() != null) && (command.getSynopsis().size() >0)) {
			synopsis = new ArrayList<String>(command.getSynopsis().size());
			
			for (String singleWord : command.getSynopsis()) {
				synopsis.add(singleWord);
			}
		} else {
			synopsis = new ArrayList<String>(0);
		}

		// topics
		if ((command.getTopic() != null) && (command.getTopic().size() >0)) {
			topicsId = new ArrayList<Integer>(command.getTopic().size());
			topics = new ArrayList<String>(command.getTopic().size());
			
			for (String singleWord : command.getTopic()) {
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
		} else {
			topicsId = new ArrayList<Integer>(0);
			topics = new ArrayList<String>(0);
		}

		// person
		if ((command.getPerson() != null) && (command.getPerson().size() >0)) {
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
		} else {
			personId = new ArrayList<Integer>(0);
			person = new ArrayList<String>(0);
		}

		// place
		if ((command.getPlace() != null) && (command.getPlace().size() >0)) {
			placeId = new ArrayList<Integer>(command.getPlace().size());
			place = new ArrayList<String>(command.getPlace().size());
			
			for (String singleWord : command.getPlace()) {
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
		} else {
			placeId = new ArrayList<Integer>(0);
			place = new ArrayList<String>(0);
		}

		// sender;
		if ((command.getSender() != null) && (command.getSender().size() >0)) {
			senderId = new ArrayList<Integer>(command.getSender().size());
			sender = new ArrayList<String>(command.getSender().size());
			
			for (String singleWord : command.getSender()) {
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
		} else {
			senderId = new ArrayList<Integer>(0);
			sender = new ArrayList<String>(0);
		}

		// from;
		if ((command.getFrom() != null) && (command.getFrom().size() >0)) {
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
		} else {
			fromId = new ArrayList<Integer>(0);
			from= new ArrayList<String>(0);
		}

		// recipient;
		if ((command.getRecipient() != null) && (command.getRecipient().size() >0)) {
			recipientId = new ArrayList<Integer>(command.getRecipient().size());
			recipient = new ArrayList<String>(command.getRecipient().size());
			
			for (String singleWord : command.getRecipient()) {
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
		} else {
			recipientId = new ArrayList<Integer>(0);
			recipient = new ArrayList<String>(0);
		}

		// to;
		if ((command.getTo() != null) && (command.getTo().size() >0)) {
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
		} else {
			toId = new ArrayList<Integer>(0);
			to = new ArrayList<String>(0);
		}

		// resTo;
		if ((command.getRefersTo() != null) && (command.getRefersTo().size() >0)) {
			refersTo = new ArrayList<String>(command.getRefersTo().size());
			
			for (String singleWord : command.getRefersTo()) {
				refersTo.add(singleWord);
			}
		} else {
			refersTo = new ArrayList<String>(0);
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
	 * This method return a Lucene Query object. 
	 */
	public Query toLuceneQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();

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
				booleanQuery.add(wordsQuery, Occur.MUST);
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
				booleanQuery.add(volumesQuery, Occur.MUST);
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
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				}
			}
			if (!datesQuery.toString().equals("")) {
				booleanQuery.add(datesQuery, Occur.MUST);
			}
		}

		// Extract
		if (extract.size()>0) {
			for (int i=0; i<extract.size(); i++) {
				// (+synExtract.docExtract:med*)
				BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("synExtract.docExtract", extract.get(i).toLowerCase())), Occur.MUST);
				booleanQuery.add(booleanClause);
			}
		}
		
		// synopsis;
		if (synopsis.size() >0) {
			for (int i=0; i<synopsis.size(); i++) {
				// +(synExtract.synopsis:med*)
				BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("synExtract.synopsis", synopsis.get(i).toLowerCase())), Occur.MUST);
				booleanQuery.add(booleanClause);
			}
		}

		// topics;
		if (topicsId.size() >0) {
			BooleanQuery topicIdQuery = new BooleanQuery();
			BooleanQuery topicTitleQuery = new BooleanQuery();

			for (int i=0; i<topicsId.size(); i++) {
				if ((topicsId.get(i) != null) || (topicsId.get(i) > 0)) {
					// +(+eplToLink.topic.topicId: 23)
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("eplToLink.topic.topicId", topicsId.get(i).toString())), Occur.SHOULD);
					topicIdQuery.add(booleanClause);
				} else {
					// +(+eplToLink.topic.topicTitle
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("eplToLink.topic.topicTitle", topics.get(i).toLowerCase())), Occur.SHOULD);
					topicTitleQuery.add(booleanClause);
				}
			}
			if (!topicIdQuery.toString().equals("")) {
				booleanQuery.add(topicIdQuery, Occur.MUST);
			}
			if (!topicTitleQuery.toString().equals("")) {
				booleanQuery.add(topicTitleQuery, Occur.MUST);
			}
		}

		// person;
		if (personId.size() >0) {
			BooleanQuery personIdQuery = new BooleanQuery();
			BooleanQuery personQuery = new BooleanQuery();
			for (int i=0; i<personId.size(); i++) {
				if ((personId.get(i) != null) || (personId.get(i) > 0)) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("epLink.person.personId", personId.get(i).toString())), Occur.SHOULD);
					personIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("senderPeople.personId", personId.get(i).toString())), Occur.SHOULD);
					personIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("recipientPeople.personId", personId.get(i).toString())), Occur.SHOULD);
					personIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("epLink.person.mapNameLf", person.get(i).toLowerCase())), Occur.SHOULD);
					personQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("altName.altName", person.get(i).toLowerCase())), Occur.SHOULD);
					personQuery.add(booleanClause);
				}
			}
			if (!personIdQuery.toString().equals("")) {
				booleanQuery.add(personIdQuery, Occur.MUST);
			}
			if (!personQuery.toString().equals("")) {
				booleanQuery.add(personQuery, Occur.MUST);
			}
		}

		// place;
		if (placeId.size() >0) {
			BooleanQuery placeIdQuery = new BooleanQuery();
			BooleanQuery placeQuery = new BooleanQuery();

			for (int i=0; i<placeId.size(); i++) {
				if ((placeId.get(i) != null) || (placeId.get(i) > 0)) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("senderPlace.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					placeIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("recipientPlace.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					placeIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("eplToLink.place.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					placeIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPlace.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					placeQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("recipientPlace.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					placeQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("eplToLink.place.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					placeQuery.add(booleanClause);
				}
			}			
			if (!placeIdQuery.toString().equals("")) {
				booleanQuery.add(placeIdQuery, Occur.MUST);
			}
			if (!placeQuery.toString().equals("")) {
				booleanQuery.add(placeQuery, Occur.MUST);
			}
		}
		
		//sender
		if (senderId.size() >0) {
			BooleanQuery senderIdQuery = new BooleanQuery();
			BooleanQuery senderQuery = new BooleanQuery();
			
			for (int i=0; i<senderId.size(); i++) {
				if ((senderId.get(i) != null) || (senderId.get(i) > 0)) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("senderPeople.personId", senderId.get(i).toString())), Occur.SHOULD);
					senderIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPeople.mapNameLf", sender.get(i).toLowerCase())), Occur.SHOULD);
					senderQuery.add(booleanClause);
				}
			}
			if (!senderIdQuery.toString().equals("")) {
				booleanQuery.add(senderIdQuery, Occur.MUST);
			}
			if (!senderQuery.toString().equals("")) {
				booleanQuery.add(senderQuery, Occur.MUST);
			}
		}

		// from;
		if (fromId.size() >0) {
			BooleanQuery fromIdQuery = new BooleanQuery();
			BooleanQuery fromQuery = new BooleanQuery();
			for (int i=0; i<fromId.size(); i++) {
				if ((fromId.get(i) != null) || (fromId.get(i) > 0)) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("senderPlace.placeAllId", fromId.get(i).toString())), Occur.SHOULD);
					fromIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("senderPeople.mapNameLf", from.get(i).toLowerCase())), Occur.SHOULD);
					fromQuery.add(booleanClause);
				}
			}
			if (!fromIdQuery.toString().equals("")) {
				booleanQuery.add(fromIdQuery, Occur.MUST);
			}
			if (!fromQuery.toString().equals("")) {
				booleanQuery.add(fromQuery, Occur.MUST);
			}
		}

		// recipient;
		if (recipient.size() >0) {
			BooleanQuery recipientIdQuery = new BooleanQuery();
			BooleanQuery recipientQuery = new BooleanQuery();
			for (int i=0; i<recipientId.size(); i++) {
				if ((recipientId.get(i) != null) || (recipientId.get(i) > 0)) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("recipientPeople.personId", recipientId.get(i).toString())), Occur.SHOULD);
					recipientIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("recipientPeople.mapNameLf", recipient.get(i).toLowerCase())), Occur.SHOULD);
					recipientQuery.add(booleanClause);
				}
			}
			if (!recipientIdQuery.toString().equals("")) {
				booleanQuery.add(recipientIdQuery, Occur.MUST);
			}
			if (!recipientQuery.toString().equals("")) {
				booleanQuery.add(recipientQuery, Occur.MUST);
			}
		}

		// to;
		if (to.size() >0) {
			BooleanQuery toIdQuery = new BooleanQuery();
			BooleanQuery toQuery = new BooleanQuery();
			for (int i=0; i<toId.size(); i++) {
				if (toId.get(i) > 0) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("recipientPlace.placeAllId", toId.get(i).toString())), Occur.SHOULD);
					toIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("recipientPlace.placeNameFull", to.get(i).toLowerCase())), Occur.SHOULD);
					toQuery.add(booleanClause);
				}
			}
			if (!toIdQuery.toString().equals("")) {
				booleanQuery.add(toIdQuery, Occur.MUST);
			}
			if (!toQuery.toString().equals("")) {
				booleanQuery.add(toQuery, Occur.MUST);
			}
		}

		//refersTo
		if (refersTo.size() >0) {
			BooleanQuery refersToIdQuery = new BooleanQuery();
			BooleanQuery refersToQuery = new BooleanQuery();
			for (int i=0; i<personId.size(); i++) {
				if ((personId.get(i) != null) || (personId.get(i) > 0)) {
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("epLink.person.personId", personId.get(i).toString())), Occur.SHOULD);
					refersToIdQuery.add(booleanClause);
				} else {
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("epLink.person.mapNameLf", person.get(i).toLowerCase())), Occur.SHOULD);
					refersToQuery.add(booleanClause);
				}
			}
			if (!refersToIdQuery.toString().equals("")) {
				booleanQuery.add(refersToQuery, Occur.MUST);
			}
			if (!refersToQuery.toString().equals("")) {
				booleanQuery.add(refersToQuery, Occur.MUST);
			}
		}
		return booleanQuery;
	}
	
	/**
	 * It's more simple construct lucene Query with string.
	 */
	@Override
	public String toLuceneQueryString() {
		StringBuffer stringBuffer = new StringBuffer();

		//Words
		if (words.size()>0) {
			stringBuffer.append("(");
			for (int i=0; i<words.size(); i++) {
				if (wordsTypes.get(i).equals(WordType.Extract)) {
					stringBuffer.append("(synExtract.docExtract: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*) ");
				} else if (wordsTypes.get(i).equals(WordType.Synopsis)) {
					stringBuffer.append("(synExtract.synopsis: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*) ");
				} else if (wordsTypes.get(i).equals(WordType.SynopsisAndExtract)) {
					stringBuffer.append("((synExtract.docExtract: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*) AND (synExtract.synopsis: ");
					stringBuffer.append(words.get(i).toLowerCase());
					stringBuffer.append("*)) ");
				}
				if (i<(words.size()-1)) {
					stringBuffer.append(" OR ");
				}
			}
			stringBuffer.append(")");
		}

		// Volume
		if (volumes.size()>0) {
			for (int i=0; i<volumes.size(); i++) {
				if (VolumeUtils.isVolumeFormat(volumes.get(i))) {
					if (volumesTypes.get(i).equals(VolumeType.Exactly)) {
						if (StringUtils.isNumeric(volumes.get(i))) {
							stringBuffer.append("(volume.volNum: ");
							stringBuffer.append(VolumeUtils.extractVolNum(volumes.get(i)));
							stringBuffer.append(")");
						} else {
							stringBuffer.append("(volume.volNum: ");
							stringBuffer.append(VolumeUtils.extractVolNum(volumes.get(i)));
							stringBuffer.append(" and volume.volLetExt: ");
							stringBuffer.append(VolumeUtils.extractVolLetExt(volumes.get(i)));
							stringBuffer.append(")");
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						Query pageQueryRange = NumericRangeQuery.newIntRange("volume.volNum", NumberUtils.toInt(volumes.get(i)), NumberUtils.toInt(volumesBetween.get(i)), true, true);
						stringBuffer.append(pageQueryRange.toString());
					}

				}
			}
		}

		// Date
		if (datesYear.size()>0) {
			for (int i=0; i<datesTypes.size(); i++) {
				if (datesTypes.get(i) == null) {
					continue;
				} else if (datesTypes.get(i).equals(DateType.After)) {
					stringBuffer.append("(docDate> ");
					stringBuffer.append(DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					stringBuffer.append(")");
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					stringBuffer.append("(docDate<= ");
					stringBuffer.append(DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					stringBuffer.append(")");
				}else if (datesTypes.get(i).equals(DateType.Between)) {
					stringBuffer.append("(docDate<= ");
					stringBuffer.append(DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					stringBuffer.append(")");
				}
			}
		}

		// Extract
		if (extract.size()>0) {
			for (int i=0; i<extract.size(); i++) {
				stringBuffer.append("(synExtract.docExtract: ");
				stringBuffer.append(extract.get(i).toLowerCase());
				stringBuffer.append("*) ");
			}
		}
		
		// synopsis;
		if (synopsis.size() >0) {
			for (int i=0; i<synopsis.size(); i++) {
				stringBuffer.append("(synExtract.synopsis: ");
				stringBuffer.append(synopsis.get(i).toLowerCase());
				stringBuffer.append("*) ");
			}
		}

		// topics;
		if (topicsId.size() >0) {
			for (int i=0; i<topicsId.size(); i++) {
				if ((topicsId.get(i) == null) || (topicsId.get(i) > 0)) {
					stringBuffer.append("(eplToLink.topic.topicId: ");
					stringBuffer.append(topicsId.get(i));
					stringBuffer.append(") ");
				} else {
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
					stringBuffer.append("((epLink.person.personId: ");
					stringBuffer.append(personId.get(i));
					stringBuffer.append(") OR (senderPeople.personId: ");
					stringBuffer.append(personId.get(i));
					stringBuffer.append(") OR (recipientPeople.personId: ");
					stringBuffer.append(personId.get(i));
					stringBuffer.append(")) ");
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
					stringBuffer.append("(recipientPeople.personId: ");
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
		if (toId.size() > 0) {
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
		}

		// resTo;
		if (refersTo.size() > 0) {
			for (int i=0; i<refersTo.size(); i++) {
				
			}
		}

		//logger.info("Lucene Query : " + stringBuffer.toString());
		return stringBuffer.toString();
	}
	
	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum WordType {
		Extract("Extract"), Synopsis("Synopsis"), SynopsisAndExtract("SynopsisAndExtract");
		
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
		After("After"), Before("Before"), Between("Between");
		
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

