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
package org.medici.bia.common.search;

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
import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.command.search.SimpleSearchCommand;
import org.medici.bia.common.search.AdvancedSearchAbstract.DateType;
import org.medici.bia.common.util.DateUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class AdvancedSearchPeople extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;

	private List<Integer> datesDay;
	private List<Integer> datesDayBetween;
	private List<Integer> datesMonth;
	private List<Integer> datesMonthBetween;
	private List<String> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<Integer> datesLastUpdateDay;
	private List<Integer> datesLastUpdateDayBetween;
	private List<Integer> datesLastUpdateMonth;
	private List<Integer> datesLastUpdateMonthBetween;
	private List<Integer> datesLastUpdateYear;
	private List<Integer> datesLastUpdateYearBetween;
	private List<DateType> datesLastUpdateTypes;
	private List<Gender> gender;
	private Boolean logicalDelete;
	private List<String> names;
	private List<NameType> namesTypes;
	private List<String> place;
	private List<Integer> placeId;
	private List<String> placeType;
	private List<String> researchNotes;
	private List<String> roleCategories;
	private List<String> titleOccWord;
	private List<String> titlesOcc;
	private List<Integer> titlesOccId;
	private List<String> words;
	private List<WordType> wordsTypes;

	/**
	 * Default constructor.
	 */
	public AdvancedSearchPeople() {
		super();

		names = new ArrayList<String>(0);
		namesTypes = new ArrayList<AdvancedSearchAbstract.NameType>(0);
		words = new ArrayList<String>(0);
		wordsTypes = new ArrayList<AdvancedSearchDocument.WordType>(0);
		datesTypes = new ArrayList<String>(0);
		datesYear = new ArrayList<Integer>(0);
		datesMonth = new ArrayList<Integer>(0);
		datesDay = new ArrayList<Integer>(0);
		datesYearBetween = new ArrayList<Integer>(0);
		datesMonthBetween = new ArrayList<Integer>(0);
		datesDayBetween = new ArrayList<Integer>(0);
		datesLastUpdateDay = new ArrayList<Integer>(0);
		datesLastUpdateDayBetween = new ArrayList<Integer>(0);
		datesLastUpdateMonth = new ArrayList<Integer>(0);
		datesLastUpdateMonthBetween = new ArrayList<Integer>(0);
		datesLastUpdateYear = new ArrayList<Integer>(0);
		datesLastUpdateYearBetween = new ArrayList<Integer>(0);
		datesLastUpdateTypes = new ArrayList<DateType>(0);
		placeId = new ArrayList<Integer>(0);
		place = new ArrayList<String>(0);
		placeType = new ArrayList<String>(0);
		roleCategories = new ArrayList<String>(0);
		titlesOcc = new ArrayList<String>(0);
		titlesOccId = new ArrayList<Integer>(0);
		titleOccWord = new ArrayList<String>(0);
		logicalDelete = null;
		researchNotes = new ArrayList<String>(0);
		gender = new ArrayList<AdvancedSearchAbstract.Gender>(0);
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
	 * @return the datesTypes
	 */
	public List<String> getDatesTypes() {
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
	 * @return the gender
	 */
	public List<Gender> getGender() {
		return gender;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * @return the names
	 */
	public List<String> getNames() {
		return names;
	}

	/**
	 * @return the namesTypes
	 */
	public List<NameType> getNamesTypes() {
		return namesTypes;
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
	 * @return the placeType
	 */
	public List<String> getPlaceType() {
		return placeType;
	}

	/**
	 * @return the researchNotes
	 */
	public List<String> getResearchNotes() {
		return researchNotes;
	}

	/**
	 * @return the roleCategories
	 */
	public List<String> getRoleCategories() {
		return roleCategories;
	}

	/**
	 * @return the titleOccWord
	 */
	public List<String> getTitleOccWord() {
		return titleOccWord;
	}

	/**
	 * @return the occupations
	 */
	public List<String> getTitlesOcc() {
		return titlesOcc;
	}

	/**
	 * @return the titleOccId
	 */
	public List<Integer> getTitlesOccId() {
		return titlesOccId;
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
		//Names
		if((command.getNameParts() != null) && (command.getNameParts().size() > 0)){
			namesTypes = new ArrayList<NameType>(command.getNameParts().size());
			names = new ArrayList<String>(command.getNameParts().size());
			
			for(String singleWord: command.getNameParts()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try{
					if(stringTokenizer.countTokens() == 2){
						namesTypes.add(NameType.valueOf(stringTokenizer.nextToken().replace(" ", "")));
						names.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					}else{
						continue;
					}
				}catch(URIException e){
					namesTypes.remove(namesTypes.size()-1);
				}
			}
		}else{
			namesTypes = new ArrayList<AdvancedSearchAbstract.NameType>(0);
			names = new ArrayList<String>(0);
		}
		
		//Words
		if ((command.getWord() != null) && (command.getWord().size() > 0)) {
			//wordsTypes = new ArrayList<WordType>(command.getWord().size());
			words = new ArrayList<String>(command.getWord().size());
			
			for (String singleWord : command.getWord()) {
				//StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try {
					//if (stringTokenizer.countTokens() == 2) {
						//wordsTypes.add(WordType.valueOf(stringTokenizer.nextToken()));
						//words.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
						words.add(URIUtil.decode(singleWord, "UTF-8"));
					//} else {
					//	continue;
					//}
				} catch (URIException e) {
					//wordsTypes.remove(wordsTypes.size()-1);
					words.remove(words.size()-1);
				}
			}
		} else {
			wordsTypes = new ArrayList<WordType>(0);
			words = new ArrayList<String>(0);
		}

		//Date
		if ((command.getDate() != null) && (command.getDate().size() >0)) {
			datesTypes = new ArrayList<String>(command.getDate().size());
			datesYear = new ArrayList<Integer>(command.getDate().size());
			datesMonth = new ArrayList<Integer>(command.getDate().size());
			datesDay = new ArrayList<Integer>(command.getDate().size());
			datesYearBetween = new ArrayList<Integer>(command.getDate().size());
			datesMonthBetween = new ArrayList<Integer>(command.getDate().size());
			datesDayBetween = new ArrayList<Integer>(command.getDate().size());
			
			for (String singleWord : command.getDate()) {
				//e.g. After|1222|01|12|1223|12|12
				String[] fields = StringUtils.splitPreserveAllTokens(singleWord,"|");
				try{
				datesTypes.add(URIUtil.decode(fields[0], "UTF-8"));
				}catch(URIException e){}
				datesYear.add(DateUtils.getDateYearFromString(fields[1]));
				datesMonth.add(DateUtils.getDateMonthFromString(fields[2]));
				datesDay.add(DateUtils.getDateDayFromString(fields[3]));
				datesYearBetween.add(DateUtils.getDateYearFromString(fields[4]));
				datesMonthBetween.add(DateUtils.getDateMonthFromString(fields[5]));
				datesDayBetween.add(DateUtils.getDateDayFromString(fields[6]));
			}
		} else {
			datesTypes = new ArrayList<String>(0);
			datesYear = new ArrayList<Integer>(0);
			datesMonth = new ArrayList<Integer>(0);
			datesDay = new ArrayList<Integer>(0);
			datesYearBetween = new ArrayList<Integer>(0);
			datesMonthBetween = new ArrayList<Integer>(0);
			datesDayBetween = new ArrayList<Integer>(0);
		}
		
		//Role Categories
		if((command.getRoleCategory() != null) && (command.getRoleCategory().size() > 0)){
			roleCategories = new ArrayList<String>(command.getRoleCategory().size());
			for(String singleWord : command.getRoleCategory()){
				try{
					roleCategories.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(URIException e){
					roleCategories.remove(roleCategories.size() - 1);
				}
			}
		}else{
			roleCategories = new ArrayList<String>(0);
		}
		
		//OccupationsWords
		if((command.getOccupationWord() != null) && (command.getOccupationWord().size() > 0)){
			titleOccWord = new ArrayList<String>(command.getOccupationWord().size());
			for(String singleWord : command.getOccupationWord()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				try{
					titleOccWord.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(URIException e){
					
				}
			}
		}else{
			titleOccWord = new ArrayList<String>(0);
		}
		
		//Occupations
		if((command.getOccupation() != null) && (command.getOccupation().size() > 0)){
			titlesOccId = new ArrayList<Integer>(command.getOccupation().size());
			titlesOcc = new ArrayList<String>(command.getOccupation().size());
			
			for(String singleWord : command.getOccupation()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try{
					if(stringTokenizer.countTokens() == 0){
						continue;
					}else if(stringTokenizer.countTokens() == 1){
						titlesOccId.add(new Integer(0));
						titlesOcc.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					}else if(stringTokenizer.countTokens() == 2){
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						if(NumberUtils.isNumber(singleId)){
							titlesOccId.add(NumberUtils.createInteger(singleId));
						}else{
							titlesOccId.add(new Integer(0));
						}
						titlesOcc.add(URIUtil.decode(singleText, "UTF-8"));
					}
				}catch(NumberFormatException nex){
													
				}catch(URIException e){
					titlesOccId.remove(titlesOccId.size() - 1);
				}
			}
		}else{
			titlesOcc = new ArrayList<String>(0);
			titlesOccId = new ArrayList<Integer>(0);
		}
		
		//Gender
		if((command.getGender() != null) && (command.getGender().size() > 0)){
			gender = new ArrayList<AdvancedSearchAbstract.Gender>(command.getGender().size());
			
			for(String singleWord : command.getGender()){
				try{
					gender.add(Gender.valueOf(URIUtil.decode(singleWord, "UTF-8")));
				}catch(URIException e){
					
				}
			}
		}else{
			gender = new ArrayList<AdvancedSearchAbstract.Gender>(0);
		}
		
		//Places
		if((command.getPlace() != null) && (command.getPlace().size() > 0)){
			placeId = new ArrayList<Integer>(command.getPlace().size());
			place = new ArrayList<String>(command.getPlace().size());
			placeType = new ArrayList<String>(command.getPlace().size());
			
			for(String singleWord : command.getPlace()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try{
					if(stringTokenizer.countTokens() == 0){
						continue;
					}else if(stringTokenizer.countTokens() == 1){
						placeId.add(new Integer(0));
						place.add(URIUtil.decode(stringTokenizer.nextToken(), "UTF-8"));
					}else if(stringTokenizer.countTokens() == 2){
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						if(NumberUtils.isNumber(singleId)){
							placeId.add(NumberUtils.createInteger(singleId));
						}else{
							placeId.add(new Integer(0));
						}
						place.add(URIUtil.decode(singleText, "UTF-8"));
					}else if(stringTokenizer.countTokens() == 3){
						placeType.add(stringTokenizer.nextToken());
						String singleId = stringTokenizer.nextToken();
						String singleText = stringTokenizer.nextToken();
						if(NumberUtils.isNumber(singleId)){
							placeId.add(NumberUtils.createInteger(singleId));
						}else{
							placeId.add(new Integer(0));
						}
						place.add(URIUtil.decode(singleText, "UTF-8"));
					}
				}catch(NumberFormatException nex){
					
				}catch(URIException e){
					placeId.remove(placeId.size() - 1);
				}
			}
		}else{
			placeId = new ArrayList<Integer>(0);
			place = new ArrayList<String>(0);
			placeType = new ArrayList<String>(0);
		}
		
		//Research Notes
		if ((command.getResearchNotes() != null) && (command.getResearchNotes().size() > 0)) {
			researchNotes = new ArrayList<String>(command.getResearchNotes().size());
			
			for (String singleWord : command.getResearchNotes()) {
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				try {
					researchNotes.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (URIException e) {
					researchNotes.remove(researchNotes.size()-1);
				}
			}
		} else {
			researchNotes = new ArrayList<String>(0);
		}
		
		//Logical Delete
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
		namesTypes.add(NameType.AllNameTypes);
		names.add(command.getText());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isEmpty() {
		if(
				(names.size()>0) ||
				(words.size()>0) ||
				(datesTypes.size()>0) ||
				(roleCategories.size()>0) ||
				(titleOccWord.size()>0) ||
				(titlesOccId.size()>0) ||
				(gender.size()>0) ||
				(placeId.size()>0) ||
				(researchNotes.size()>0)) {
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
	 * @param datesTypes the datesTypes to set
	 */
	public void setDatesTypes(List<String> datesTypes) {
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
	 * @param gender the gender to set
	 */
	public void setGender(List<Gender> gender) {
		this.gender = gender;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @param names the names to set
	 */
	public void setNames(List<String> names) {
		this.names = names;
	}

	/**
	 * @param namesTypes the namesTypes to set
	 */
	public void setNamesTypes(List<NameType> namesTypes) {
		this.namesTypes = namesTypes;
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
	 * @param placeType the placeType to set
	 */
	public void setPlaceType(List<String> placeType) {
		this.placeType = placeType;
	}

	/**
	 * @param researchNotes the researchNotes to set
	 */
	public void setResearchNotes(List<String> researchNotes) {
		this.researchNotes = researchNotes;
	}

	/**
	 * @param roleCategories the roleCategories to set
	 */
	public void setRoleCategories(List<String> roleCategories) {
		this.roleCategories = roleCategories;
	}

	/**
	 * @param titleOccWord the titleOccWord to set
	 */
	public void setTitleOccWord(List<String> titleOccWord) {
		this.titleOccWord = titleOccWord;
	}

	/**
	 * @param occupations the occupations to set
	 */
	public void setTitlesOcc(List<String> titlesOcc) {
		this.titlesOcc = titlesOcc;
	}

	/**
	 * @param titleOccId the titleOccId to set
	 */
	public void setTitlesOccId(List<Integer> titlesOccId) {
		this.titlesOccId = titlesOccId;
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
	 * @return the datesLastUpdateDay
	 */
	public List<Integer> getDatesLastUpdateDay() {
		return datesLastUpdateDay;
	}

	/**
	 * @param datesLastUpdateDay the datesLastUpdateDay to set
	 */
	public void setDatesLastUpdateDay(List<Integer> datesLastUpdateDay) {
		this.datesLastUpdateDay = datesLastUpdateDay;
	}

	/**
	 * @return the datesLastUpdateDayBetween
	 */
	public List<Integer> getDatesLastUpdateDayBetween() {
		return datesLastUpdateDayBetween;
	}

	/**
	 * @param datesLastUpdateDayBetween the datesLastUpdateDayBetween to set
	 */
	public void setDatesLastUpdateDayBetween(List<Integer> datesLastUpdateDayBetween) {
		this.datesLastUpdateDayBetween = datesLastUpdateDayBetween;
	}

	/**
	 * @return the datesLastUpdateMonth
	 */
	public List<Integer> getDatesLastUpdateMonth() {
		return datesLastUpdateMonth;
	}

	/**
	 * @param datesLastUpdateMonth the datesLastUpdateMonth to set
	 */
	public void setDatesLastUpdateMonth(List<Integer> datesLastUpdateMonth) {
		this.datesLastUpdateMonth = datesLastUpdateMonth;
	}

	/**
	 * @return the datesLastUpdateMonthBetween
	 */
	public List<Integer> getDatesLastUpdateMonthBetween() {
		return datesLastUpdateMonthBetween;
	}

	/**
	 * @param datesLastUpdateMonthBetween the datesLastUpdateMonthBetween to set
	 */
	public void setDatesLastUpdateMonthBetween(List<Integer> datesLastUpdateMonthBetween) {
		this.datesLastUpdateMonthBetween = datesLastUpdateMonthBetween;
	}

	/**
	 * @return the datesLastUpdateYear
	 */
	public List<Integer> getDatesLastUpdateYear() {
		return datesLastUpdateYear;
	}

	/**
	 * @param datesLastUpdateYear the datesLastUpdateYear to set
	 */
	public void setDatesLastUpdateYear(List<Integer> datesLastUpdateYear) {
		this.datesLastUpdateYear = datesLastUpdateYear;
	}

	/**
	 * @return the datesLastUpdateYearBetween
	 */
	public List<Integer> getDatesLastUpdateYearBetween() {
		return datesLastUpdateYearBetween;
	}

	/**
	 * @param datesLastUpdateYearBetween the datesLastUpdateYearBetween to set
	 */
	public void setDatesLastUpdateYearBetween(List<Integer> datesLastUpdateYearBetween) {
		this.datesLastUpdateYearBetween = datesLastUpdateYearBetween;
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
	 * This method return a JPA Query object. 
	 */
	@Override
	public String toJPAQuery() {
		// TODO Auto-generated method stub
		StringBuilder jpaQuery = new StringBuilder("FROM People WHERE ");
		
		//Names
		if(names.size() > 0){
			StringBuilder namesQuery = new StringBuilder("(");
			for(int i = 0; i < names.size(); i++){
				String [] wordsSingleNames = StringUtils.split(names.get(i), " ");
				if(namesQuery.length() > 1){
					namesQuery.append(" AND ");
				}
				if(namesTypes.get(i).equals(NameType.AllNameTypes)){
					for(int j = 0; j < wordsSingleNames.length; j++){
						namesQuery.append("(mapNameLf like '%");
						namesQuery.append(wordsSingleNames[j].toLowerCase().replace("'", "''"));
						namesQuery.append("%' OR ");
						namesQuery.append("(personId IN (SELECT person.personId FROM AltName WHERE altName like '%");
						namesQuery.append(wordsSingleNames[j].toLowerCase().replace("'", "''"));
						namesQuery.append("%')))");
						if( j < (wordsSingleNames.length - 1)){
							namesQuery.append(" AND ");
						}
					}
				}else{
					for(int j = 0; j < wordsSingleNames.length; j++){
						namesQuery.append("(personId IN (SELECT person.personId FROM AltName WHERE altName like '%");
						namesQuery.append(wordsSingleNames[j].toLowerCase().replace("'", "''"));
						namesQuery.append("%' AND nameType like '");
						namesQuery.append(namesTypes.get(i).toString().toLowerCase());
						namesQuery.append("'))");
						if(j < (wordsSingleNames.length - 1)){
							namesQuery.append(" AND ");
						}
					}
				}
			}
			namesQuery.append(")");
			if(!namesQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(namesQuery);
			}
			
		}
		
		//Words
		if(words.size() > 0){
			StringBuilder wordsQuery = new StringBuilder("(");
			for(int i = 0; i < words.size(); i++){
				if(wordsQuery.length() > 1){
					wordsQuery.append(" AND ");
				}
				wordsQuery.append("((mapNameLf like '%");
				wordsQuery.append(words.get(i).toLowerCase().replace("'", "''"));
				wordsQuery.append("%') OR ");
				wordsQuery.append("((bioNotes like '%");
				wordsQuery.append(words.get(i).toLowerCase().replace("'", "''"));
				wordsQuery.append("%') OR ");
				wordsQuery.append("(staffNotes like '%");
				wordsQuery.append(words.get(i).toLowerCase().replace("'", "''"));
				wordsQuery.append("%') OR ");
				wordsQuery.append("((mapNameLf like '%");
				wordsQuery.append(words.get(i).toLowerCase().replace("'", "''"));
				wordsQuery.append("%') OR ");
				wordsQuery.append("(personId IN (SELECT person.personId FROM AltName WHERE altName like '%");
				wordsQuery.append(names.get(i).toLowerCase().replace("'", "''"));
				wordsQuery.append("%'))");
			}
			wordsQuery.append(")");
			if(!wordsQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(wordsQuery);
			}			
		}
		
		//Dates
		if(datesTypes.size() > 0){
			StringBuilder datesQuery = new StringBuilder("(");
			for(int i = 0; i < datesTypes.size(); i++){
				if(datesTypes.get(i) == null){
					continue;
				}
				if(datesQuery.length() > 1){
					datesQuery.append(" AND ");
				}
				if(datesTypes.get(i).equals("Born after")){
					//For years that have two numbers
					datesQuery.append("((bornYear < 100 AND STR_TO_DATE(CONCAT('00', bornYear, ',' , bornMonth, ',', bornDay),'%Y,%m,%d')>");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") OR (bornYear < 1000 AND bornYear >= 100 AND STR_TO_DATE(CONCAT('0', bornYear, ',' , bornMonth, ',', bornDay),'%Y,%m,%d')>");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") OR (bornYear >= 1000 AND STR_TO_DATE(CONCAT(bornYear, ',' , bornMonth, ',', bornDay),'%Y,%m,%d')>");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")) AND bornDateBc = false");
				}else if(datesTypes.get(i).equals("Dead by")){
					datesQuery.append("((deathYear < 100 AND STR_TO_DATE(CONCAT('00', deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')<=");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") OR (deathYear < 1000 AND deathYear >= 100 AND STR_TO_DATE(CONCAT('0', deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')<=");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") OR (deathYear >= 1000 AND STR_TO_DATE(CONCAT(deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')<=");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")) AND deathDateBc = false");
				}else if(datesTypes.get(i).equals("Lived between")){
					datesQuery.append("(((bornYear < 100 AND STR_TO_DATE(CONCAT('00', bornYear, ',' , bornMonth, ',', bornDay),'%Y, %m ,%d')<");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(") OR (bornYear < 1000 AND bornYear >= 100 AND STR_TO_DATE(CONCAT('0', bornYear, ',' , bornMonth, ',', bornDay),'%Y, %m ,%d')<");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(") OR (bornYear >= 1000 AND STR_TO_DATE(CONCAT(bornYear, ',' , bornMonth, ',', bornDay),'%Y, %m ,%d')<");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(")) AND ((deathYear < 100 AND STR_TO_DATE(CONCAT('00', deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')>");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") OR (deathYear < 1000 AND deathYear >=100 AND STR_TO_DATE(CONCAT('0', deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')>");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(") OR (deathYear >= 1000 AND STR_TO_DATE(CONCAT(deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')>");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")) AND ((deathYear < 100 AND STR_TO_DATE(CONCAT('00', deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')<");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(") OR (deathYear < 1000 AND deathYear >= 100 AND STR_TO_DATE(CONCAT('0', deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')<");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(") OR (deathYear >= 1000 AND STR_TO_DATE(CONCAT(deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')<");
					datesQuery.append(DateUtils.getDateForSQLQuery(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append("))) AND bornDateBc = false AND deathDateBc = false");
				}else if(datesTypes.get(i).equals("Born/Died on")){
					StringBuilder bornQuery = new StringBuilder();
					StringBuilder deathQuery = new StringBuilder();
					if(datesYear.get(i) != null){
						bornQuery.append("(bornYear =");
						bornQuery.append(datesYear.get(i) + ")");
						deathQuery.append("(deathYear =");
						deathQuery.append(datesYear.get(i) + ")");
						if(datesMonth.get(i) != null || datesDay.get(i) != null){
							bornQuery.append(" AND ");
							deathQuery.append(" AND ");
						}
					}
					if(datesMonth.get(i) != null){
						bornQuery.append("(bornMonth =");
						bornQuery.append(datesMonth.get(i) + ")");
						deathQuery.append("(deathMonth =");
						deathQuery.append(datesMonth.get(i) + ")");
						if(datesDay.get(i) != null){
							bornQuery.append(" AND ");
							deathQuery.append(" AND ");
						}
					}
					if(datesDay.get(i) != null){
						bornQuery.append("(bornDay =");
						bornQuery.append(datesDay.get(i) + ")");
						deathQuery.append("(deathDay =");
						deathQuery.append(datesDay.get(i) + ")");
					}
					datesQuery.append(bornQuery + " OR " + deathQuery + "AND bornDateBc = false AND deathDateBc = false");
					//MD: Older version for "Born/Died on
//					datesQuery.append("((STR_TO_DATE(CONCAT(bornYear, ',' , bornMonth, ',', bornDay),'%Y, %m ,%d')=");
//					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
//					datesQuery.append(") OR (STR_TO_DATE(CONCAT(deathYear, ',' , deathMonth, ',', deathDay),'%Y, %m ,%d')=");
//					datesQuery.append(DateUtils.getDateForSQLQuery(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
//					datesQuery.append("))");
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
		
		//Role Categories
		if(roleCategories.size() > 0){
			StringBuilder roleCatQuery = new StringBuilder("(");
			for(int i = 0; i < roleCategories.size(); i++){
				if(roleCatQuery.length() > 1){
					roleCatQuery.append(" AND ");
				}
				if(roleCategories.get(i).equals("ARTISTS and ARTISANS") || roleCategories.get(i).equals("CORPORATE BODIES") || roleCategories.get(i).equals("ECCLESIASTICS") || roleCategories.get(i).equals("HEADS of STATE") || roleCategories.get(i).equals("MILITARY and NAVAL PERSONNEL") || roleCategories.get(i).equals("NOBLES") || roleCategories.get(i).equals("PROFESSIONS") || roleCategories.get(i).equals("SCHOLARLY and LITERARY") || roleCategories.get(i).equals("STATE and COURT PERSONNEL") || roleCategories.get(i).equals("UNASSIGNED")){
					roleCatQuery.append("(personId IN (SELECT person.personId FROM org.medici.bia.domain.PoLink WHERE titleOccList.roleCat.roleCatMajor like '%");
					roleCatQuery.append(roleCategories.get(i));
					roleCatQuery.append("%'))");
				}else{
					roleCatQuery.append("(personId IN (SELECT person.personId FROM org.medici.bia.domain.PoLink WHERE titleOccList.roleCat.roleCatMinor like '%");
					roleCatQuery.append(roleCategories.get(i));
					roleCatQuery.append("%'))");
				}
			}
			roleCatQuery.append(")");
			if(!roleCatQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(roleCatQuery);
			}	
		}
		
		//OccupationsWord
		if(titleOccWord.size() > 0){
			StringBuilder titleOccWordQuery = new StringBuilder("(");
			for(int i = 0; i < titleOccWord.size(); i++){
				String[] wordsOccupation = StringUtils.split(titleOccWord.get(i), " ");
				if(titleOccWordQuery.length() > 1){
					titleOccWordQuery.append(" AND ");
				}
				for(int j = 0; j < wordsOccupation.length; j++){
					titleOccWordQuery.append("(personId IN (SELECT person.personId FROM org.medici.bia.domain.PoLink WHERE titleOccList.titleOcc like '%");
					titleOccWordQuery.append(wordsOccupation[j].replace("'", "''"));
					titleOccWordQuery.append("%'))");
					if(j < (wordsOccupation.length - 1)){
						titleOccWordQuery.append(" AND ");
					}
				}
			}
			titleOccWordQuery.append(")");
			if(!titleOccWordQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(titleOccWordQuery);
			}
		}
		
		//Occupations
		if(titlesOccId.size() > 0){
			StringBuilder titleOccIdQuery = new StringBuilder("(");
			for(int i = 0; i < titlesOccId.size(); i++){
				if(titleOccIdQuery.length() > 1){
					titleOccIdQuery.append(" AND ");
				}
				if(titlesOccId.get(i) > 0){
					titleOccIdQuery.append("(personId IN (SELECT person.personId FROM org.medici.bia.domain.PoLink WHERE titleOccList.titleOccId=");
					titleOccIdQuery.append(titlesOccId.get(i));
					titleOccIdQuery.append("))");
				}else{
					titleOccIdQuery.append("(personId IN (SELECT person.personId FROM org.medici.bia.domain.PoLink WHERE titleOccList.titleOcc like '%");
					titleOccIdQuery.append(titlesOcc.get(i).replace("'", "''"));
					titleOccIdQuery.append("%'))");
				}
			}
			titleOccIdQuery.append(")");
			if(!titleOccIdQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(titleOccIdQuery);
			}
		}
		
		//Gender
		if(gender.size() > 0){
			StringBuilder genderQuery = new StringBuilder("(");
			for(int i = 0; i < gender.size(); i++){
				if(genderQuery.length() > 1){
					genderQuery.append(" AND ");
				}
				genderQuery.append("(gender like '");
				genderQuery.append(gender.get(i));
				genderQuery.append("' )");
			}
			genderQuery.append(")");
			if(!genderQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(genderQuery);
			}
		}
		
		//Places
		if(placeId.size() > 0){
			StringBuilder placeIdQuery = new StringBuilder("(");
			for(int i = 0; i < placeId.size(); i++){
				if(placeIdQuery.length() > 1){
					placeIdQuery.append(" AND ");
				}
				if(placeType.get(i).equals("Birth/Death Place")){
					if(placeId.get(i) > 0){
						placeIdQuery.append("(bornPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
						placeIdQuery.append(placeId.get(i));
						placeIdQuery.append(") OR deathPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
						placeIdQuery.append(placeId.get(i));
						placeIdQuery.append("))");
					}else{
						placeIdQuery.append("(bornPlace.placeName like '%");
						placeIdQuery.append(place.get(i).replace("'", "''"));
						placeIdQuery.append("%' OR deathPlace.placeName like '%");
						placeIdQuery.append(place.get(i).replace("'", "''"));
						placeIdQuery.append("%' )");
					}
				}else if(placeType.get(i).equals("Birth Place")){
					if(placeId.get(i) > 0){
						placeIdQuery.append("(bornPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
						placeIdQuery.append(placeId.get(i));
						placeIdQuery.append("))");
					}else{
						placeIdQuery.append("(bornPlace.placeName like '%");
						placeIdQuery.append(place.get(i).replace("'", "''"));
						placeIdQuery.append("%' )");
					}
				}else if(placeType.get(i).equals("Death Place")){
					if(placeId.get(i) > 0){
						placeIdQuery.append("(deathPlace.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId=");
						placeIdQuery.append(placeId.get(i));
						placeIdQuery.append("))");
					}else{
						placeIdQuery.append("(deathPlace.placeName like '%");
						placeIdQuery.append(place.get(i).replace("'", "''"));
						placeIdQuery.append("%' )");
					}
				}
			}
			placeIdQuery.append(")");
			if(!placeIdQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(placeIdQuery);
			}
		}
		
		//ResearchNotes
		if(researchNotes.size() > 0){
			StringBuilder researchNotesQuery = new StringBuilder("(");
			for(int i = 0; i < researchNotes.size(); i++){
				if(researchNotesQuery.length() > 1){
					researchNotesQuery.append(" AND ");
				}
				researchNotesQuery.append("(bioNotes like '%");
				researchNotesQuery.append(researchNotes.get(i).toLowerCase().replace("'", "''"));
				researchNotesQuery.append("%')");
			}
			researchNotesQuery.append(")");
			if(!researchNotesQuery.toString().equals("")){
				if(jpaQuery.length() > 18){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(researchNotesQuery);
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
		}else{
			jpaQuery.append(" AND logicalDelete = false");
		}
		
		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		BooleanQuery luceneQuery = new BooleanQuery();
		
		//Names
		if(names.size() > 0){
			BooleanQuery namesQuery = new BooleanQuery();
			for(int i = 0; i < names.size(); i++){
				if(namesTypes.get(i).equals(NameType.AllNameTypes)){
					BooleanQuery subQuery = new BooleanQuery();
					subQuery.add(new PrefixQuery(new Term("mapNameLf", names.get(i).toString().toLowerCase())),Occur.SHOULD);
					subQuery.add(new PrefixQuery(new Term("altName.altName", names.get(i).toLowerCase())), Occur.SHOULD);
					namesQuery.add(subQuery, Occur.SHOULD);
				}else{
					BooleanQuery subQuery = new BooleanQuery();
//					try{
//						//MultiFieldQueryParser multiField = new MultiFieldQueryParser(Version.LUCENE_31, new String[]{"altName.nameType","altName.altName"}, new StandardAnalyzer(Version.LUCENE_31));
//						//Query test = multiField.parse(namesTypes.get(i).toString().toLowerCase());
//						Query test = MultiFieldQueryParser.parse(Version.LUCENE_31, new String[]{namesTypes.get(i).toString().toLowerCase(), names.get(i).toLowerCase() + '*'}, new String[]{"altName.nameType","altName.altName"}, new StandardAnalyzer(Version.LUCENE_31));
//						
//						namesQuery.add(test, Occur.MUST);
//					}catch(Exception e){
//						
//					}
					subQuery.add(new TermQuery(new Term("altName.nameType", namesTypes.get(i).toString().toLowerCase())), Occur.MUST);
					subQuery.add(new TermQuery(new Term("altName.altName", names.get(i).toLowerCase())), Occur.MUST);
					namesQuery.add(subQuery, Occur.MUST);
				}
			}
			if(!namesQuery.toString().equals("")){
				luceneQuery.add(namesQuery, Occur.MUST);
			}
		}
		
		//Words
		if(words.size() > 0){
			BooleanQuery wordsQuery = new BooleanQuery();
			for(int i = 0; i < words.size(); i++){
				BooleanQuery subQuery = new BooleanQuery();
				subQuery.add(new PrefixQuery(new Term("mapNameLf", words.get(i).toLowerCase())), Occur.SHOULD);
				subQuery.add(new PrefixQuery(new Term("bioNotes", words.get(i).toLowerCase())), Occur.SHOULD);
				subQuery.add(new PrefixQuery(new Term("staffNotes", words.get(i).toLowerCase())), Occur.SHOULD);
				subQuery.add(new PrefixQuery(new Term("altName.altName", words.get(i).toLowerCase())), Occur.SHOULD);
				wordsQuery.add(subQuery, Occur.MUST);
			}
			if(!wordsQuery.toString().equals("")){
				luceneQuery.add(wordsQuery, Occur.MUST);
			}
		}
		
		//Dates
		if(datesTypes.size() > 0){
			BooleanQuery datesQuery = new BooleanQuery();
			for(int i = 0; i < datesTypes.size(); i++){
				if(datesTypes.get(i) == null){
					continue;
				}else if(datesTypes.get(i).equals("Born after")){
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("bornDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), DateUtils.MAX_DATE, true, true);
					datesQuery.add(dateRangeQuery, Occur.MUST);
				}else if(datesTypes.get(i).equals("Died by")){
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("deathDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), DateUtils.MAX_DATE, true,	true);
					datesQuery.add(dateRangeQuery, Occur.MUST);
				}else if(datesTypes.get(i).equals("Lived between")){
					NumericRangeQuery<Integer> startDateRangeQuery = NumericRangeQuery.newIntRange("bornDate_Sort", 4, DateUtils.MIN_DATE, DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), true, true);
					datesQuery.add(startDateRangeQuery, Occur.MUST);
					NumericRangeQuery<Integer> endDateRangeQuery = NumericRangeQuery.newIntRange("deathDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), true, true);
					datesQuery.add(endDateRangeQuery, Occur.MUST);
				}else if(datesTypes.get(i).equals("Born/Died on")){
					//if the month is null we don't consider the day
					if(datesMonth.get(i) == null){
						NumericRangeQuery<Integer> bornDateRangeQuery = NumericRangeQuery.newIntRange("bornDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), 1, 1), DateUtils.getLuceneDate(datesYear.get(i), 12, 12), true, true);
						datesQuery.add(bornDateRangeQuery, Occur.SHOULD);
						NumericRangeQuery<Integer> deathDateRangeQuery = NumericRangeQuery.newIntRange("deathDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), 1, 1), DateUtils.getLuceneDate(datesYear.get(i), 12, 12), true, true);
						datesQuery.add(deathDateRangeQuery, Occur.SHOULD);
					}else{
						if(datesDay.get(i) == null){
							NumericRangeQuery<Integer> bornDateRangeQuery = NumericRangeQuery.newIntRange("bornDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), 1), DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), 31), true, true);
							datesQuery.add(bornDateRangeQuery, Occur.SHOULD);
							NumericRangeQuery<Integer> deathDateRangeQuery = NumericRangeQuery.newIntRange("deathDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), 1), DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), 31), true, true);
							datesQuery.add(deathDateRangeQuery, Occur.SHOULD);
						}else{
							NumericRangeQuery<Integer> bornDateRangeQuery = NumericRangeQuery.newIntRange("bornDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), true, true);
							datesQuery.add(bornDateRangeQuery, Occur.SHOULD);
							NumericRangeQuery<Integer> deathDateRangeQuery = NumericRangeQuery.newIntRange("deathDate_Sort", 4, DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), true, true);
							datesQuery.add(deathDateRangeQuery, Occur.SHOULD);
						}
					}
					
				}
			}
			if(!datesQuery.toString().equals("")){
				luceneQuery.add(datesQuery, Occur.MUST);
			}
		}
		
		//Role Categories
		if(roleCategories.size() > 0){
			BooleanQuery roleCatQuery = new BooleanQuery();
			
			
			for(int i = 0; i < roleCategories.size(); i++){
				BooleanQuery singleRoleCatQuery = new BooleanQuery();
				if(roleCategories.get(i).equals("ARTISTS and ARTISANS") || roleCategories.get(i).equals("CORPORATE BODIES") || roleCategories.get(i).equals("ECCLESIASTICS") || roleCategories.get(i).equals("HEADS of STATE") || roleCategories.get(i).equals("MILITARY and NAVAL PERSONNEL") || roleCategories.get(i).equals("NOBLES") || roleCategories.get(i).equals("PROFESSIONS") || roleCategories.get(i).equals("SCHOLARLY and LITERARY") || roleCategories.get(i).equals("STATE and COURT PERSONNEL") || roleCategories.get(i).equals("UNASSIGNED")){
					String[] wordSingleRoleCat = StringUtils.split(roleCategories.get(i), " ");
					for(int j = 0; j < wordSingleRoleCat.length; j++){
						if(j == 0){
							TermQuery termQuery = new TermQuery(new Term("poLink.titleOccList.roleCat.roleCatMajor", wordSingleRoleCat[j].toLowerCase()));
							singleRoleCatQuery.add(termQuery, Occur.MUST);
							roleCatQuery.add(singleRoleCatQuery, Occur.MUST);
							singleRoleCatQuery = new BooleanQuery();
						}else{
							//Discard the words "and" or "of"
							if(wordSingleRoleCat[j].length() > 3){
								if(j != wordSingleRoleCat.length -1){
									TermQuery termQuery = new TermQuery(new Term("poLink.titleOccList.roleCat.roleCatMajor", wordSingleRoleCat[j].toLowerCase()));
									singleRoleCatQuery.add(termQuery, Occur.MUST);
									roleCatQuery.add(singleRoleCatQuery, Occur.MUST);
									singleRoleCatQuery = new BooleanQuery();
								}else{
									PrefixQuery prefixQuery = new PrefixQuery(new Term("poLink.titleOccList.roleCat.roleCatMajor", wordSingleRoleCat[j].toLowerCase()));
									singleRoleCatQuery.add(prefixQuery, Occur.SHOULD);
									roleCatQuery.add(singleRoleCatQuery, Occur.MUST);
									singleRoleCatQuery = new BooleanQuery();
								}
							}
						}
					}
				}else{
					String[] wordSingleRoleCat = StringUtils.split(roleCategories.get(i), " ");
					for(int j = 0; j < wordSingleRoleCat.length; j++){
						if(j == 0){
							TermQuery termQuery = new TermQuery(new Term("poLink.titleOccList.roleCat.roleCatMinor", wordSingleRoleCat[j].toLowerCase()));
							singleRoleCatQuery.add(termQuery, Occur.MUST);
							roleCatQuery.add(singleRoleCatQuery, Occur.MUST);
							singleRoleCatQuery = new BooleanQuery();
						}else{
							//Discard the words "and" or "of"
							if(wordSingleRoleCat[j].length() > 3){
								if(j != wordSingleRoleCat.length -1){
									TermQuery termQuery = new TermQuery(new Term("poLink.titleOccList.roleCat.roleCatMinor", wordSingleRoleCat[j].toLowerCase()));
									singleRoleCatQuery.add(termQuery, Occur.MUST);
									roleCatQuery.add(singleRoleCatQuery, Occur.MUST);
									singleRoleCatQuery = new BooleanQuery();
								}else{
									PrefixQuery prefixQuery = new PrefixQuery(new Term("poLink.titleOccList.roleCat.roleCatMinor", wordSingleRoleCat[j].toLowerCase()));
									singleRoleCatQuery.add(prefixQuery, Occur.MUST);
									roleCatQuery.add(singleRoleCatQuery, Occur.MUST);
									singleRoleCatQuery = new BooleanQuery();
							}
							}
						}
					}
				}
				
			}
			if(!roleCatQuery.toString().equals("")){
				luceneQuery.add(roleCatQuery, Occur.MUST);
			}
		}
		
		//Occupations
		if(titlesOccId.size() > 0){
			BooleanQuery titleOccIdQuery = new BooleanQuery();
			BooleanQuery titleOccQuery = new BooleanQuery();
			
			for(int i = 0; i < titlesOccId.size(); i++){
				if(titlesOccId.get(i) > 0){
					titleOccIdQuery.add(new BooleanClause(new TermQuery(new Term("poLink.titleOccList.titleOccId", titlesOccId.get(i).toString())), Occur.MUST));					
				}else{
					titleOccQuery.add(new BooleanClause(new PrefixQuery(new Term("poLink.titleOccList.titleOcc", titlesOcc.get(i).toLowerCase())), Occur.MUST));
				}
			}
			if(!titleOccIdQuery.toString().equals("")){
				luceneQuery.add(titleOccIdQuery, Occur.MUST);
			}
			if(!titleOccQuery.toString().equals("")){
				luceneQuery.add(titleOccQuery, Occur.MUST);
			}
		}
		
		//Places
		if(placeId.size() > 0){
			BooleanQuery placeIdQuery = new BooleanQuery();
			BooleanQuery placeQuery = new BooleanQuery();
			
			for(int i = 0; i < placeId.size(); i++){
				if(placeId.get(i) > 0){
					BooleanQuery singlePlaceIdQuery = new BooleanQuery();
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("bornPlace.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					singlePlaceIdQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("deathPlace.placeAllId", placeId.get(i).toString())), Occur.SHOULD);
					singlePlaceIdQuery.add(booleanClause);
					placeIdQuery.add(singlePlaceIdQuery, Occur.MUST);
				}else{
					BooleanQuery singlePlaceQuery = new BooleanQuery();
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("bornPlace.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					singlePlaceQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("deathPlace.placeName", place.get(i).toLowerCase())), Occur.SHOULD);
					singlePlaceQuery.add(booleanClause);
					placeQuery.add(singlePlaceQuery, Occur.MUST);
				}
			}
			if(!placeIdQuery.toString().equals("")){
				luceneQuery.add(placeIdQuery, Occur.MUST);
			}
			if(!placeQuery.toString().equals("")){
				luceneQuery.add(placeQuery, Occur.MUST);
			}
		}
		
		
		
		return luceneQuery;
	}

	public String toString(){
		StringBuilder toString = new StringBuilder();
		if(!names.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Names: ");
			for(int i = 0; i < names.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(names.get(i));
				toString.append(" ");
			}
		}
		if(!words.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Words: ");
			for(int i = 0; i < words.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(words.get(i) + " ");
			}
		}
		if(!datesYear.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Date Year: ");
			for(int i = 0; i < datesYear.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(datesYear.get(i) + " ");
			}
		}
		if(!datesMonth.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Date Month: ");
			for(int i = 0; i < datesMonth.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(datesMonth.get(i) + " ");
			}
		}
		if(!datesDay.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Date Day: ");
			for(int i = 0; i < datesDay.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(datesDay.get(i) + " ");
			}
		}
		if(!datesYearBetween.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Between Date Year: ");
			for(int i = 0; i < datesYearBetween.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(datesYearBetween.get(i) + " ");
			}
		}
		if(!datesMonthBetween.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Between Date Month: ");
			for(int i = 0; i < datesMonthBetween.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(datesMonthBetween.get(i) + " ");
			}
		}
		if(!datesDayBetween.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Between Date Day: ");
			for(int i = 0; i < datesDayBetween.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(datesDayBetween.get(i) + " ");
			}
		}
		if(!roleCategories.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Role Categories: ");
			for(int i = 0; i < roleCategories.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(roleCategories.get(i) + " ");
			}
		}
		if(!titlesOcc.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Occupations: ");
			for(int i = 0; i < titlesOcc.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(titlesOcc.get(i) + " ");
			}
		}
		if(!place.isEmpty()){
			if(toString.length()>0){
				toString.append("AND ");
			}
			toString.append("Places: ");
			for(int i = 0; i < place.size(); i++){
				if(i > 0){
					toString.append("AND ");
				}
				toString.append(place.get(i) + " ");
			}
		}
		return toString.toString();
	}
}
