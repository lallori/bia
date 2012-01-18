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
import java.util.StringTokenizer;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.VolumeUtils;


/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchVolume extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;

	private List<String> words;
	private List<WordType> wordsTypes;
	private List<Integer> datesDay;
	private List<Integer> datesMonth;
	private List<DateType> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<Integer> datesMonthBetween;
	private List<Integer> datesDayBetween;
	private List<VolumeType> volumesTypes;
	private List<String> volumes;
	private List<String> volumesBetween;
	private Boolean digitized;
	private List<String> languages;
	private String cipher;
	private String index;
	private List<String> fromVolume;
	private List<String> toVolume;
	private List<String> context;
	private List<String> inventario;
	private Boolean logicalDelete;

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
		volumesTypes = new ArrayList<AdvancedSearchDocument.VolumeType>(0);
		volumes = new ArrayList<String>(0);
		volumesBetween = new ArrayList<String>(0);
		digitized = null;
		languages = new ArrayList<String>(0);
		cipher = new String();
		index = new String();
		fromVolume = new ArrayList<String>(0);
		toVolume = new ArrayList<String>(0);
		context = new ArrayList<String>(0);
		inventario = new ArrayList<String>(0);
		logicalDelete = Boolean.FALSE;
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
				try{
					fromVolume.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){
				}catch(URIException e){
				}
			}
		}else{
			fromVolume = new ArrayList<String>(0);
		}
		
		//To
		if(command.getToVolume() != null && command.getToVolume().size() > 0){
			toVolume = new ArrayList<String>(command.getToVolume().size());
			for(String singleWord : command.getToVolume()){
				try{
					toVolume.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){
				}catch(URIException e){
				}
			}
		}else{
			toVolume = new ArrayList<String>(0);
		}
		
		//Context
		if(command.getContext() != null && command.getContext().size() > 0){
			context = new ArrayList<String>(command.getContext().size());
			for(String singleWord : command.getContext()){
				try{
					context.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){
				}catch(URIException e){
				}
			}
		}else{
			context = new ArrayList<String>(0);
		}
		
		//Inventario Sommario
		if(command.getInventario() != null && command.getInventario().size() > 0){
			inventario = new ArrayList<String>(command.getInventario().size());
			for(String singleWord : command.getInventario()){
				try{
					inventario.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){
				}catch(URIException e){
				}
			}
		}else{
			inventario = new ArrayList<String>(0);
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromSimpleSearchCommand(SimpleSearchCommand command) {
		wordsTypes.add(WordType.TitlesAndNotes);
		words.add(command.getText());
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
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuffer jpaQuery = new StringBuffer("FROM Volume WHERE ");
		
		// Volume
		if(volumes.size() > 0){
			StringBuffer volumesQuery = new StringBuffer("(");
			for(int i = 0; i < volumes.size(); i++){
				if(VolumeUtils.isVolumeFormat(volumes.get(i))){
					if(volumesQuery.length() > 1){
						volumesQuery.append(" AND ");
					}
					
					if(volumesTypes.get(i).equals(VolumeType.Exactly)){
						if(StringUtils.isNumeric(volumes.get(i))){
							volumesQuery.append("(volNum=");
							volumesQuery.append(volumes.get(i));
							volumesQuery.append(")");
						}else{
							volumesQuery.append("(volNum=");
							volumesQuery.append(volumes.get(i));
							volumesQuery.append(" AND volLetExt='");
							volumesQuery.append(VolumeUtils.extractVolLetExt(volumes.get(i)));
							volumesQuery.append("')");
						}
					}else if(volumesTypes.get(i).equals(VolumeType.Between)){
						volumesQuery.append("(volNum>=");
						volumesQuery.append(volumes.get(i));
						volumesQuery.append(" AND volNum<=");
						volumesQuery.append(volumesBetween.get(i));
						volumesQuery.append(")");
					}
				}else{
					continue;
				}
			}
			volumesQuery.append(")");
			if(!volumesQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(volumesQuery);
			}
		}
		
		// Date
		if(datesTypes.size() > 0){
			StringBuffer datesQuery = new StringBuffer("(");
			for(int i=0; i < datesTypes.size(); i++){
				if(datesTypes.get(i) == null){
					continue;
				}
				if(datesQuery.length() > 1){
					datesQuery.append(" AND ");
				}
				
				if(datesTypes.get(i).equals(DateType.After)){
					datesQuery.append("(startDate>");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				}else if(datesTypes.get(i).equals(DateType.Before)){
					datesQuery.append("(startDate<");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				}else if(datesTypes.get(i).equals(DateType.Between)){
					datesQuery.append("(startDate>");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(" AND startDate<");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				}
			}
			datesQuery.append(")");
			if(!datesQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(datesQuery);
			}
		}
		
		//Digitized
		if(!ObjectUtils.toString(digitized).equals("")){
			StringBuffer digitizedQuery = new StringBuffer("(");
			if(digitized.equals(Boolean.TRUE)){
				digitizedQuery.append("(digitized=true)");
			}else if(digitized.equals(Boolean.FALSE)){
				digitizedQuery.append("(digitized=false)");
			}
			digitizedQuery.append(")");
			if(!digitizedQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(digitizedQuery);
			}
		}
		
		//Languages
		if(languages.size() > 0){
			StringBuffer languagesQuery = new StringBuffer("(");
			for(int i = 0; i < languages.size(); i++){
				if(languagesQuery.length() > 1){
					languagesQuery.append(" AND ");
				}
				String[] worldSingleLanguages = StringUtils.split(languages.get(i), " ");
				for(int j = 0; j < worldSingleLanguages.length; j++){
					if(j > 0){
						languagesQuery.append(" AND ");
					}
					if(worldSingleLanguages[j].equals("italian")){
						languagesQuery.append("(italian=true)");
					}
					if(worldSingleLanguages[j].equals("french")){
						languagesQuery.append("(french=true)");
					}
					if(worldSingleLanguages[j].equals("german")){
						languagesQuery.append("(german=true)");
					}
					if(worldSingleLanguages[j].equals("spanish")){
						languagesQuery.append("(spanish=true)");
					}
					if(worldSingleLanguages[j].equals("latin")){
						languagesQuery.append("(latin=true)");
					}
					if(worldSingleLanguages[j].equals("english")){
						languagesQuery.append("(english=true)");
					}
				}
			}
			languagesQuery.append(")");
			if(!languagesQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(languagesQuery);
			}
		}
		
		// Cipher
		if(cipher.length() > 0){
			StringBuffer cipherQuery = new StringBuffer("(");
			if(cipher.equals("Yes")){
				cipherQuery.append("(cipher=true)");
			}else if(cipher.equals("No")){
				cipherQuery.append("(cipher=false)");
			}
			cipherQuery.append(")");
			if(!cipherQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(cipherQuery);
			}
		}
		
		// Index of Names
		if(index.length() > 0){
			StringBuffer indexQuery = new StringBuffer("(");
			if(index.equals("Yes")){
				indexQuery.append("(oldAlphaIndex=true)");
			}else if(index.equals("No")){
				indexQuery.append("(oldAlphaIndex=false)");
			}
			indexQuery.append(")");
			if(!indexQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(indexQuery);
			}
		}
		
		// From
		if(fromVolume.size() > 0){
			StringBuffer fromVolumeQuery = new StringBuffer("(");
			for(int i = 0; i < fromVolume.size(); i++){
				if(fromVolumeQuery.length() > 1){
					fromVolumeQuery.append(" AND ");
				}
				String[] wordsSingleFromVolume = StringUtils.split(fromVolume.get(i), " ");
				for(int j = 0; j < wordsSingleFromVolume.length; j++){
					if(j > 0){
						fromVolumeQuery.append(" AND ");
					}
					fromVolumeQuery.append("(senders like '%");
					fromVolumeQuery.append(wordsSingleFromVolume[j]);
					fromVolumeQuery.append("%')");
				}
			}
			fromVolumeQuery.append(")");
			if(!fromVolumeQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(fromVolumeQuery);
			}
		}
		
		// To
		if(toVolume.size() > 0){
			StringBuffer toVolumeQuery = new StringBuffer("(");
			for(int i = 0; i < toVolume.size(); i++){
				if(toVolumeQuery.length() > 1){
					toVolumeQuery.append(" AND ");
				}
				String[] wordsSingleToVolume = StringUtils.split(toVolume.get(i), " ");
				for(int j = 0; j < wordsSingleToVolume.length; j++){
					if(j > 0){
						toVolumeQuery.append(" AND ");
					}
					toVolumeQuery.append("(recips like '%");
					toVolumeQuery.append(wordsSingleToVolume[j]);
					toVolumeQuery.append("%')");
				}
			}
			toVolumeQuery.append(")");
			if(!toVolumeQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(toVolumeQuery);
			}
		}
		
		// Context
		if(context.size() > 0){
			StringBuffer contextQuery = new StringBuffer("(");
			for(int i = 0; i < context.size(); i++){
				if(contextQuery.length() > 1){
					contextQuery.append(" AND ");
				}
				String[] wordsSingleContext = StringUtils.split(context.get(i), " ");
				for(int j = 0; j < wordsSingleContext.length; j++){
					if(j > 0){
						contextQuery.append(" AND ");
					}
					contextQuery.append("(ccontext like '%");
					contextQuery.append(wordsSingleContext[j]);
					contextQuery.append("%')");
				}
			}
			contextQuery.append(")");
			if(!contextQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(contextQuery);
			}
		}
		
		// Inventario
		if(inventario.size() > 0){
			StringBuffer inventarioQuery = new StringBuffer("(");
			for(int i = 0; i < inventario.size(); i++){
				if(inventarioQuery.length() > 1){
					inventarioQuery.append(" AND ");
				}
				String[] wordsSingleInventario = StringUtils.split(inventario.get(i), " ");
				for(int j = 0; j < wordsSingleInventario.length; j++){
					if(j > 0){
						inventarioQuery.append(" AND ");
					}
					inventarioQuery.append("(inventarioSommarioDescription like '%");
					inventarioQuery.append(wordsSingleInventario[j]);
					inventarioQuery.append("%')");
				}
			}
			inventarioQuery.append(")");
			if(!inventarioQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(inventarioQuery);
			}
		}
		
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
		String toString = new String();
		if(!words.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Words: ";
			for(int i = 0; i < words.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += words.get(i) + " ";
			}
		}
		if(!datesYear.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Date Year: ");
			for(int i = 0; i < datesYear.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (datesYear.get(i) + " ");
			}
		}
		if(!datesMonth.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Date Month: ");
			for(int i = 0; i < datesMonth.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (datesMonth.get(i) + " ");
			}
		}
		if(!datesDay.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Date Day: ");
			for(int i = 0; i < datesDay.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (datesDay.get(i) + " ");
			}
		}
		if(!datesYearBetween.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Between Date Year: ");
			for(int i = 0; i < datesYearBetween.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (datesYearBetween.get(i) + " ");
			}
		}
		if(!datesMonthBetween.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Between Date Month: ");
			for(int i = 0; i < datesMonthBetween.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (datesMonthBetween.get(i) + " ");
			}
		}
		if(!datesDayBetween.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Between Date Day: ");
			for(int i = 0; i < datesDayBetween.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (datesDayBetween.get(i) + " ");
			}
		}
		if(!volumes.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Volumes: ");
			for(int i = 0; i < volumes.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (volumes.get(i) + " ");
			}
		}
		if(!volumesBetween.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Between Volumes: ");
			for(int i = 0; i < volumesBetween.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (volumesBetween.get(i) + " ");
			}
		}
		if(!ObjectUtils.toString(digitized).equals("")){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Digitized: " + digitized + " ";
		}
		if(!languages.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Languages: ");
			for(int i = 0; i < languages.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (languages.get(i) + " ");
			}
		}
		if(!cipher.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Cypher: " + cipher + " ";
		}
		if(!index.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Index: " + index + " ";
		}
		if(!fromVolume.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("From Volume: ");
			for(int i = 0; i < fromVolume.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (fromVolume.get(i) + " ");
			}
		}
		if(!toVolume.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("To Volume: ");
			for(int i = 0; i < toVolume.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (toVolume.get(i) + " ");
			}
		}
		if(!context.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Context: ");
			for(int i = 0; i < context.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (context.get(i) + " ");
			}
		}
		if(!inventario.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += ("Inventario: ");
			for(int i = 0; i < inventario.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += (inventario.get(i) + " ");
			}
		}
			
		return toString;
	}
}

