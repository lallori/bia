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
package org.medici.bia.common.search;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.ObjectUtils;
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
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.VolumeUtils;


/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 *
 */
public class AdvancedSearchVolume extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4234252971238598397L;

	private List<String> words;
	private List<WordType> wordsTypes;
	private List<Integer> datesDay;
	private List<Integer> datesMonth;
	private List<DateType> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<Integer> datesMonthBetween;
	private List<Integer> datesDayBetween;
	private List<Date> datesLastUpdate;
	private List<Date> datesLastUpdateBetween;
	private List<DateType> datesLastUpdateTypes;
	private List<Date> datesCreated;
	private List<Date> datesCreatedBetween;
	private List<DateType> datesCreatedTypes;
	private List<VolumeType> volumesTypes;
	private List<String> volumes;
	private List<String> volumesBetween;
	private List<String> insertNums;
	private Boolean digitized;
	private List<String> languages;
	private List<String> otherLang;
	private String cipher;
	private String index;
	private List<String> fromVolume;
	private List<String> toVolume;
	private List<String> context;
	private List<String> inventario;
	private List<String> volumesId;
	private Boolean logicalDelete;

	private static Logger logger = Logger.getLogger(AdvancedSearchVolume.class);
	
	/**
	 * 
	 */
	public AdvancedSearchVolume() {
		super();

		words = new ArrayList<String>(0);
		wordsTypes = new ArrayList<AdvancedSearchAbstract.WordType>(0);
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
		volumesId = new ArrayList<String>(0);
		digitized = null;
		languages = new ArrayList<String>(0);
		otherLang = new ArrayList<String>(0);
		cipher = new String();
		index = new String();
		fromVolume = new ArrayList<String>(0);
		toVolume = new ArrayList<String>(0);
		context = new ArrayList<String>(0);
		inventario = new ArrayList<String>(0);
		logicalDelete = null;
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
				} catch (URIException uriException) {
					logger.debug(uriException);
					wordsTypes.remove(wordsTypes.size()-1);
				}
			}
		} else {
			wordsTypes = new ArrayList<WordType>(0);
			words = new ArrayList<String>(0);
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
		
		//Insert
		if ((command.getInsert() != null && command.getInsert().size() > 0)) {
			insertNums = new ArrayList<String>(command.getInsert().size());
			
			for(String insert : command.getInsert()) {
				insertNums.add(insert);
			}
		} else {
			insertNums = new ArrayList<String>(0);
		}
		
		//Digitized
		if(command.getDigitized() != null){
			if(command.getDigitized().equals("Yes")){
				digitized = true;
			}else{
				digitized = false;
			}
		}
		
		//Languages
		if(command.getLanguages() != null && command.getLanguages().size() > 0){
			languages = new ArrayList<String>(command.getLanguages().size());
			languages.addAll(command.getLanguages());
		}else{
			languages = new ArrayList<String>(0);
		}
		
		//Other Languages
		if(command.getOtherLang() != null && command.getOtherLang().size() > 0){
			otherLang = new ArrayList<String>(command.getOtherLang().size());
			otherLang.addAll(command.getOtherLang());
		}else{
			otherLang = new ArrayList<String>(0);
		}
		
		//Cypher
		if(command.getCipher() != null){
			cipher = new String(command.getCipher());
		}else{
			cipher = new String();
		}
		
		//Index
		if(command.getIndex() != null){
			index = new String(command.getIndex());
		}else{
			index = new String();
		}
		
		//From
		if(command.getFromVolume() != null && command.getFromVolume().size() > 0){
			fromVolume = new ArrayList<String>(command.getFromVolume().size());
			for(String singleWord : command.getFromVolume()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "\u00E7" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "\u00E7");
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				try{
					fromVolume.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException numberFormatException){
					logger.debug(numberFormatException);
				} catch (URIException uriException){
					logger.debug(uriException);
				}
			}
		}else{
			fromVolume = new ArrayList<String>(0);
		}
		
		//To
		if(command.getToVolume() != null && command.getToVolume().size() > 0){
			toVolume = new ArrayList<String>(command.getToVolume().size());
			for(String singleWord : command.getToVolume()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "\u00E7" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "\u00E7");
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				try{
					toVolume.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch(NumberFormatException numberFormatException){
					logger.debug(numberFormatException);
				} catch(URIException uriException){
					logger.debug(uriException);
				}
			}
		}else{
			toVolume = new ArrayList<String>(0);
		}
		
		//Context
		if(command.getContext() != null && command.getContext().size() > 0){
			context = new ArrayList<String>(command.getContext().size());
			for(String singleWord : command.getContext()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "\u00E7" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "\u00E7");
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				try{
					context.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch(NumberFormatException numberFormatException){
					logger.debug(numberFormatException);
				} catch(URIException uriException){
					logger.debug(uriException);
				}
			}
		}else{
			context = new ArrayList<String>(0);
		}
		
		//Inventario Sommario
		if(command.getInventario() != null && command.getInventario().size() > 0){
			inventario = new ArrayList<String>(command.getInventario().size());
			for(String singleWord : command.getInventario()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "\u00E7" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "\u00E7");
				singleWord = singleWord.replace("\"", "%22");
				singleWord = singleWord.replace("'", "%27");
				try{
					inventario.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch(NumberFormatException numberFormatException){
					logger.debug(numberFormatException);
				} catch(URIException uriException){
					logger.debug(uriException);
				}
			}
		}else{
			inventario = new ArrayList<String>(0);
		}
		
		//SummaryId
		if((command.getVolumeId() != null) && (command.getVolumeId().size() > 0)){
			volumesId = new ArrayList<String>(command.getVolumeId().size());
			
			for(String singleWord : command.getVolumeId()){
				try{
					volumesId.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
				}
			}
		}else{
			volumesId = new ArrayList<String>(0);
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
//		wordsTypes.add(WordType.TitlesAndNotes);
//		words.add(command.getText());
		context.add(command.getText());
	}

	/**
	 * @return the digitized
	 */
	public Boolean getDigitized() {
		return digitized;
	}

	/**
	 * @param digitized the digitized to set
	 */
	public void setDigitized(Boolean digitized) {
		this.digitized = digitized;
	}

	/**
	 * @return the languages
	 */
	public List<String> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<String> languages) {
		this.languages = languages;
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
	 * @return the cipher
	 */
	public String getCipher() {
		return cipher;
	}

	/**
	 * @param cipher the cipher to set
	 */
	public void setCipher(String cipher) {
		this.cipher = cipher;
	}

	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	/**
	 * @return the fromVolume
	 */
	public List<String> getFromVolume() {
		return fromVolume;
	}

	/**
	 * @param fromVolume the fromVolume to set
	 */
	public void setFromVolume(List<String> fromVolume) {
		this.fromVolume = fromVolume;
	}

	/**
	 * @return the toVolume
	 */
	public List<String> getToVolume() {
		return toVolume;
	}

	/**
	 * @param toVolume the toVolume to set
	 */
	public void setToVolume(List<String> toVolume) {
		this.toVolume = toVolume;
	}

	/**
	 * @return the context
	 */
	public List<String> getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(List<String> context) {
		this.context = context;
	}

	/**
	 * @return the inventario
	 */
	public List<String> getInventario() {
		return inventario;
	}

	/**
	 * @param inventario the inventario to set
	 */
	public void setInventario(List<String> inventario) {
		this.inventario = inventario;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
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
	 * @return the insertNums
	 */
	public List<String> getInsertNums() {
		return insertNums;
	}

	/**
	 * @param insertNums the insertNums to set
	 */
	public void setInsertNums(List<String> insertNums) {
		this.insertNums = insertNums;
	}

	/**
	 * @return the volumesId
	 */
	public List<String> getVolumesId() {
		return volumesId;
	}

	/**
	 * @param volumesId the volumesId to set
	 */
	public void setVolumesId(List<String> volumesId) {
		this.volumesId = volumesId;
	}

	/**
	 * @return the volumesTypes
	 */
	public List<VolumeType> getVolumesTypes() {
		return volumesTypes;
	}

	/**
	 * @param volumesTypes the volumesTypes to set
	 */
	public void setVolumesTypes(List<VolumeType> volumesTypes) {
		this.volumesTypes = volumesTypes;
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
	 * @return the datesTypes
	 */
	public List<DateType> getDatesTypes() {
		return datesTypes;
	}

	/**
	 * @param datesTypes the datesTypes to set
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


	/**
	 * @return the datesCreated
	 */
	public List<Date> getDatesCreated() {
		return datesCreated;
	}


	/**
	 * @param datesCreated the datesCreated to set
	 */
	public void setDatesCreated(List<Date> datesCreated) {
		this.datesCreated = datesCreated;
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
		StringBuilder jpaQuery = new StringBuilder("FROM Volume WHERE ");
		
		// Volume
		appendToJpaQuery(jpaQuery, getVolumeSubQuery());
		
		// Insert
		appendToJpaQuery(jpaQuery, getInsertSubQuery());
		
		// Date
		appendToJpaQuery(jpaQuery, getDateSubQuery());

		// date created
		appendToJpaQuery(jpaQuery, getDateCreatedSubQuery());

		// Last update
		appendToJpaQuery(jpaQuery, getLastUpdateSubQuery());

		// Digitized
		appendToJpaQuery(jpaQuery, getDigitizedSubQuery());
		jpaQuery.append(" AND volNum <> 0"); //excludes volume for course images uploads!!
		
		//Languages
		appendToJpaQuery(jpaQuery, getLanguagesSubQuery());
		
		//Other Languages
		appendToJpaQuery(jpaQuery, getOtherLanguagesSubQuery());
		
		// Cipher
		appendToJpaQuery(jpaQuery, getCipherSubQuery());
		
		// Index of Names
		appendToJpaQuery(jpaQuery, getIndexSubQuery());
		
		// From
		appendToJpaQuery(jpaQuery, getFromSubQuery());
		
		// To
		appendToJpaQuery(jpaQuery, getToSubQuery());
		
		// Context
		appendToJpaQuery(jpaQuery, getContextSubQuery());
		
		// Inventario
		appendToJpaQuery(jpaQuery, getInventarioSubQuery());
		
		// SummaryId
		appendToJpaQuery(jpaQuery, getVolumeIdsSubQuery());
		
		// LogicalDelete
		appendToJpaQuery(jpaQuery, getLogicalDeleteSubQuery());
		
		return jpaQuery.toString();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	/**
	 * This method return a Lucene Query object. 
	 */
	public Query toLuceneQuery() {
		BooleanQuery luceneQuery = new BooleanQuery();

		if (words.size()>0) {
			BooleanQuery wordsQuery = new BooleanQuery();
			for (int i=0; i<words.size(); i++) {
				if (wordsTypes.get(i).equals(WordType.Titles)) {
					//
					BooleanQuery subQuery = new BooleanQuery();
					subQuery.add(new PrefixQuery(new Term("serieList.title", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("serieList.subTitle1", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("serieList.subTitle2", words.get(i).toLowerCase())), Occur.SHOULD);
					wordsQuery.add(subQuery, Occur.MUST);
				} else if (wordsTypes.get(i).equals(WordType.Notes)) {
					// 
					BooleanQuery subQuery = new BooleanQuery();
					subQuery.add(new PrefixQuery(new Term("ccondition", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("ccontext", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("orgNotes", words.get(i).toLowerCase())), Occur.SHOULD);
					wordsQuery.add(subQuery, Occur.MUST);
				} else if (wordsTypes.get(i).equals(WordType.TitlesAndNotes)) {
					BooleanQuery subQuery = new BooleanQuery();
					subQuery.add(new PrefixQuery(new Term("serieList.title", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("serieList.subTitle1", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("serieList.subTitle2", words.get(i).toLowerCase())), Occur.SHOULD);
					wordsQuery.add(subQuery, Occur.MUST);
					subQuery = new BooleanQuery();
					subQuery.add(new PrefixQuery(new Term("ccondition", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("ccontext", words.get(i).toLowerCase())), Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("orgNotes", words.get(i).toLowerCase())), Occur.SHOULD);
					wordsQuery.add(subQuery, Occur.MUST);
				}
			}
			if (!wordsQuery.toString().equals("")) {
				luceneQuery.add(wordsQuery, Occur.MUST);
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
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("startDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.MAX_DATE, 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("endDate_Sort", 4, 
							DateUtils.MIN_DATE,
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
				}else if (datesTypes.get(i).equals(DateType.Between)) {
					// We add two condition because we work on two different fields.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("startDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.MAX_DATE, 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 

					dateRangeQuery = NumericRangeQuery.newIntRange("endDate_Sort", 4, 
							DateUtils.MIN_DATE,
							DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.MUST); 
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
							// (volNum:1)
							BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("volNum", volumes.get(i))), Occur.MUST);
							volumesQuery.add(booleanClause);
						} else {
							BooleanQuery subQuery = new BooleanQuery();
							// (volNum:1 AND volLetExt:a)
							BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("volNum", VolumeUtils.extractVolNum(volumes.get(i)).toString())), Occur.MUST);
							subQuery.add(booleanClause);

							booleanClause.setQuery(new TermQuery(new Term("volLetExt", VolumeUtils.extractVolNum(volumes.get(i)).toString())));
							booleanClause.setOccur(Occur.MUST);
							subQuery.add(booleanClause);

							volumesQuery.add(subQuery, Occur.MUST);
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
						NumericRangeQuery<Integer> volumeRangeQuery = NumericRangeQuery.newIntRange("volNum_Sort", 4, 
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
		
		// Digitized is a new field on tblVolumes 
		if (!ObjectUtils.toString(digitized).equals("")) {
			BooleanQuery digitizedQuery = new BooleanQuery();
			TermQuery digitizedTermQuery;
			if (digitized.equals(Boolean.TRUE)) {
				digitizedTermQuery = new TermQuery(new Term("digitized", "true"));
				digitizedQuery.add(digitizedTermQuery, Occur.MUST);
			} else {
				digitizedTermQuery = new TermQuery(new Term("digitized", "false"));
				digitizedQuery.add(digitizedTermQuery, Occur.MUST);
			}
			luceneQuery.add(new BooleanClause(digitizedQuery, Occur.MUST));
		}
		
		//Languages
		if(languages.size() > 0){
			BooleanQuery languagesQuery = new BooleanQuery();
			for(int i = 0; i < languages.size(); i++){
				BooleanQuery singleLanguagesQuery = new BooleanQuery();
				String[] wordsSingleLanguages = StringUtils.split(languages.get(i), " ");
				for(int j = 0; j < wordsSingleLanguages.length; j++){
					TermQuery termQuery;
					if(wordsSingleLanguages[j].equals("italian")){
						termQuery = new TermQuery(new Term("italian", "true"));
						singleLanguagesQuery.add(termQuery, Occur.MUST);
					}
					if(wordsSingleLanguages[j].equals("french")){
						termQuery = new TermQuery(new Term("french", "true"));
						singleLanguagesQuery.add(termQuery, Occur.MUST);
					}
					if(wordsSingleLanguages[j].equals("german")){
						termQuery = new TermQuery(new Term("german", "true"));
						singleLanguagesQuery.add(termQuery, Occur.MUST);
					}
					if(wordsSingleLanguages[j].equals("spanish")){
						termQuery = new TermQuery(new Term("spanish", "true"));
						singleLanguagesQuery.add(termQuery, Occur.MUST);
					}
					if(wordsSingleLanguages[j].equals("latin")){
						termQuery = new TermQuery(new Term("latin", "true"));
						singleLanguagesQuery.add(termQuery, Occur.MUST);
					}
					if(wordsSingleLanguages[j].equals("english")){
						termQuery = new TermQuery(new Term("english", "true"));
						singleLanguagesQuery.add(termQuery, Occur.MUST);
					}
				}
				languagesQuery.add(new BooleanClause(singleLanguagesQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(languagesQuery, Occur.MUST));
		}
		
		//Cypher
		if(cipher.length() > 0){
			BooleanQuery cipherQuery = new BooleanQuery();
			TermQuery cipherTermQuery;
			if(cipher.equals("Yes")){
				cipherTermQuery = new TermQuery(new Term("cipher", "true"));
				cipherQuery.add(cipherTermQuery, Occur.MUST);
			}else if(cipher.equals("No")){
				cipherTermQuery = new TermQuery(new Term("cipher", "false"));
				cipherQuery.add(cipherTermQuery, Occur.MUST);
			}
			luceneQuery.add(new BooleanClause(cipherQuery, Occur.MUST));
		}
		
		
		//Index Of Names
		if(index.length() > 0){
			BooleanQuery indexQuery = new BooleanQuery();
			TermQuery indexTermQuery;
			if(index.equals("Yes")){
				indexTermQuery = new TermQuery(new Term("oldAlphaIndex", "true"));
				indexQuery.add(indexTermQuery, Occur.MUST);
			}else if(index.equals("No")){
				indexTermQuery = new TermQuery(new Term("oldAlphaIndex", "true"));
				indexQuery.add(indexTermQuery, Occur.MUST);
			}
			luceneQuery.add(new BooleanClause(indexQuery, Occur.MUST));
		}
		
		//From
		if(fromVolume.size() > 0){
			BooleanQuery fromVolumeQuery = new BooleanQuery();
			for(int i = 0; i < fromVolume.size(); i++){
				BooleanQuery singleFromVolumeQuery = new BooleanQuery();
				String[] wordsSingleFromVolume = StringUtils.split(fromVolume.get(i), " ");
				for(int j = 0; j < wordsSingleFromVolume.length; j++){
					TermQuery termQuery = new TermQuery(new Term("senders", wordsSingleFromVolume[j]));
					singleFromVolumeQuery.add(termQuery, Occur.MUST);
				}
				fromVolumeQuery.add(new BooleanClause(singleFromVolumeQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(fromVolumeQuery, Occur.MUST));
		}
		
		//To
		if(toVolume.size() > 0){
			BooleanQuery toVolumeQuery = new BooleanQuery();
			for(int i = 0; i < toVolume.size(); i++){
				BooleanQuery singleToVolumeQuery = new BooleanQuery();
				String[] wordsSingleToVolume = StringUtils.split(toVolume.get(i), " ");
				for(int j = 0; j < wordsSingleToVolume.length; j++){
					TermQuery termQuery = new TermQuery(new Term("recips", wordsSingleToVolume[j]));
					singleToVolumeQuery.add(termQuery, Occur.MUST);
				}
				toVolumeQuery.add(new BooleanClause(singleToVolumeQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(toVolumeQuery, Occur.MUST));
		}
		
		//Context
		if(context.size() > 0){
			BooleanQuery contextQuery = new BooleanQuery();
			for(int i = 0; i < context.size(); i++){
				BooleanQuery singleContextQuery = new BooleanQuery();
				String[] wordsSingleContext = StringUtils.split(context.get(i), " ");
				for(int j = 0; j < wordsSingleContext.length; j++){
					TermQuery termQuery = new TermQuery(new Term("ccontext", wordsSingleContext[j]));
					singleContextQuery.add(termQuery, Occur.MUST);
				}
				contextQuery.add(new BooleanClause(singleContextQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(contextQuery, Occur.MUST));
		}
		
		//Inventario
		if(inventario.size() > 0){
			BooleanQuery inventarioQuery = new BooleanQuery();
			for(int i = 0; i < inventario.size(); i++){
				BooleanQuery singleInventarioQuery = new BooleanQuery();
				String[] wordsSingleInventario = StringUtils.split(inventario.get(i), " ");
				for(int j = 0; j < wordsSingleInventario.length; j++){
					TermQuery termQuery = new TermQuery(new Term("inventarioSommarioDescription", wordsSingleInventario[j]));
					singleInventarioQuery.add(termQuery, Occur.MUST);
				}
				inventarioQuery.add(new BooleanClause(singleInventarioQuery, Occur.MUST));
			}
			luceneQuery.add(new BooleanClause(inventarioQuery, Occur.MUST));
		}
		
		//test
//		TermQuery test = new TermQuery(new Term("italian", "true"));
//		BooleanQuery test2 = new BooleanQuery();
//		test2.add(test, Occur.MUST);
//		luceneQuery.add(new BooleanClause(test2, Occur.MUST));

		return luceneQuery;
	}
	
	public String toString(){
		StringBuilder toString = new StringBuilder();
		
		appendToStringBuilder(toString, words, "Words: ");
		appendToStringBuilder(toString, datesYear, "Date Year: ");
		appendToStringBuilder(toString, datesMonth, "Date Month: ");
		appendToStringBuilder(toString, datesDay, "Date Day: ");
		appendToStringBuilder(toString, datesYearBetween, "Between Date Year: ");
		appendToStringBuilder(toString, datesMonthBetween, "Between Date Month: ");
		appendToStringBuilder(toString, datesDayBetween, "Between Date Day: ");
		appendToStringBuilder(toString, volumes, "Volumes: ");
		if (!volumesBetween.isEmpty() && !(volumesBetween.size() == 1 && volumesBetween.get(0).equals("0"))) {
			appendToStringBuilder(toString, volumesBetween, "Volumes: ");
		}
		appendToStringBuilder(toString, insertNums, "Inserts: ");
		if (!ObjectUtils.toString(digitized).equals("")) {
			if (toString.length() > 0) {
				toString.append("AND ");
			}
			toString.append("Digitized: ").append(digitized).append(" ");
		}
		appendToStringBuilder(toString, languages, "Languages: ");
		if (!cipher.isEmpty()) {
			if (toString.length() > 0) {
				toString.append("AND ");
			}
			toString.append("Cypher: ").append(cipher).append(" ");
		}
		if (!index.isEmpty()) {
			if (toString.length() > 0) {
				toString.append("AND ");
			}
			toString.append("Index: ").append(index).append(" ");
		}
		appendToStringBuilder(toString, fromVolume, "From Volume: ");
		appendToStringBuilder(toString, toVolume, "To Volume: ");
		appendToStringBuilder(toString, context, "Context: ");
		appendToStringBuilder(toString, inventario, "Inventario: ");
		appendToStringBuilder(toString, volumesId, "Volume ID: ");
			
		return toString.toString();
	}

	@Override
	public Boolean empty() {
		if (
				(volumes.size()>0)	||
				(datesTypes.size()>0) ||
				(datesLastUpdateTypes.size()>0) ||
				(!ObjectUtils.toString(digitized).equals("")) ||
				(languages.size()>0) ||
				(otherLang.size() > 0) ||
				(cipher.length() > 0) ||
				(index.length() > 0) ||
				(fromVolume.size() > 0) ||
				(toVolume.size() > 0) ||
				(context.size() > 0) ||
				(inventario.size() > 0) ||
				(volumesId.size() > 0)
			) {
				return Boolean.FALSE;
			}
		return Boolean.TRUE;
	}
	
	/* Privates */
	
	private void appendToJpaQuery(StringBuilder jpaQuery, String subQuery) {
		if (subQuery.length() > 0) {
			if (jpaQuery.length() > 18) {
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
							builder.append("(volNum = ")
								.append(volumes.get(i))
								.append(" AND volLetExt IS NULL)");
						} else {
							builder.append("(volNum = ")
								.append(VolumeUtils.extractVolNum(volumes.get(i)))
								.append(" AND volLetExt = '")
								.append(VolumeUtils.extractVolLetExt(volumes.get(i)))
								.append("')");
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						builder.append("(volNum >= ")
							.append(volumes.get(i))
							.append(" AND volNum <= ")
							.append(volumesBetween.get(i))
							.append(")");
					}
				} else {
					continue;
				}
			}
		}
		return builder.toString();
	}
	
	private String getInsertSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (insertNums.size() > 0) {
			for (int i = 0; i < insertNums.size(); i++) {
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				builder.append("(")
					.append("(summaryId IN (SELECT DISTINCT volume.summaryId FROM org.medici.bia.domain.Document WHERE volume.logicalDelete = false AND insertNum = '")
					.append(insertNums.get(i))
					.append("')) OR ")
					.append("( (volNum, volLetExt) IN (SELECT DISTINCT volNum, volLetExt FROM org.medici.bia.domain.Image WHERE insertNum = '")
					.append(insertNums.get(i))
					.append("'))")
					.append(")");
			}
		}
		return builder.toString();
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
					builder.append("(STR_TO_DATE(CONCAT(startYear, ',' , startMonthNum, ',', startDay),'%Y,%m,%d') >= ")
						.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)))
						.append(")");
				} else if(datesTypes.get(i).equals(DateType.Before)) {
					builder.append("(STR_TO_DATE(CONCAT(startYear, ',' , startMonthNum, ',', startDay),'%Y,%m,%d') < ")
						.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)))
						.append(")");
				} else if(datesTypes.get(i).equals(DateType.Between)) {
					builder.append("((STR_TO_DATE(CONCAT(startYear, ',' , startMonthNum, ',', startDay),'%Y,%m,%d') >= ")
						.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)))
						.append(") AND (STR_TO_DATE(CONCAT(startYear, ',' , startMonthNum, ',', startDay),'%Y,%m,%d') < ")
						.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)))
						.append("))");
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
					builder.append("(dateCreated >= '")
						.append(DateUtils.getMYSQLDate(datesCreated.get(i)))
						.append("')");
				} else if (datesCreatedTypes.get(i).equals(DateType.Before)) {
					builder.append("(dateCreated <= '")
						.append(DateUtils.getMYSQLDate(datesCreated.get(i)))
						.append("')");
				} else if (datesCreatedTypes.get(i).equals(DateType.Between)) {
					builder.append("(dateCreated BETWEEN '")
						.append(DateUtils.getMYSQLDate(datesCreated.get(i)))
						.append("' AND '")
						.append(DateUtils.getMYSQLDate(datesCreatedBetween.get(i)))
						.append("')");
				} else if (datesCreatedTypes.get(i).equals(DateType.InOn)){
					
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
						.append("')");
				} else if (datesLastUpdateTypes.get(i).equals(DateType.Before)) {
					builder.append("(DATE_FORMAT(lastUpdate, '%Y-%m-%d') <= '")
						.append(DateUtils.getMYSQLDate(datesLastUpdate.get(i)))
						.append("')");
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
	
	private String getDigitizedSubQuery() {
		return booleanToJPA(digitized, "digitized");
	}
	
	private String getLanguagesSubQuery() {
		StringBuilder builder = new StringBuilder("");
		if (languages.size() > 0) {
			for (int i = 0; i < languages.size(); i++) {
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				String[] worldSingleLanguages = StringUtils.split(languages.get(i), " ");
				for (int j = 0; j < worldSingleLanguages.length; j++) {
					if (j > 0) {
						builder.append(" AND ");
					}
					if ("Italian".equals(worldSingleLanguages[j])) {
						builder.append("(italian != false)");
					} else if ("French".equals(worldSingleLanguages[j])) {
						builder.append("(french != false)");
					} else if("German".equals(worldSingleLanguages[j])) {
						builder.append("(german != false)");
					} else if ("Spanish".equals(worldSingleLanguages[j])) {
						builder.append("(spanish != false)");
					} else if("Latin".equals(worldSingleLanguages[j])) {
						builder.append("(latin != false)");
					} else if ("English".equals(worldSingleLanguages[j])) {
						builder.append("(english != false)");
					}
				}
			}
		}
		return builder.toString();
	}
	
	private String getOtherLanguagesSubQuery() {
		return listStringLikeToJpa(otherLang, "otherLang", true);
	}
	
	private String getCipherSubQuery() {
		Boolean cipherBoolean = "Yes".equals(cipher) ? Boolean.TRUE : "No".equals(cipher) ? Boolean.FALSE : null;
		return booleanToJPA(cipherBoolean, "cipher");
	}
	
	private String getIndexSubQuery() {
		Boolean indexBoolean = "Yes".equals(index) ? Boolean.TRUE : "No".equals(index) ? Boolean.FALSE : null;
		return booleanToJPA(indexBoolean, "oldAlphaIndex");
	}
	
	private String getFromSubQuery() {
		return listWordsToJpa(fromVolume, "senders");
	}
	
	private String getToSubQuery() {
		return listWordsToJpa(toVolume, "recips");
	}
	
	private String getContextSubQuery() {
		return listWordsToJpa(context, "ccontext");
	}
	
	private String getInventarioSubQuery() {
		return listWordsToJpa(inventario, "inventarioSommarioDescription");
	}
	
	private String getVolumeIdsSubQuery() {
		return listIntegerToJpa(volumesId, "summaryId", false);
	}
	
	private String getLogicalDeleteSubQuery() {
		return booleanToJPA(logicalDelete == null ? Boolean.FALSE : logicalDelete, "logicalDelete");
	}
}

