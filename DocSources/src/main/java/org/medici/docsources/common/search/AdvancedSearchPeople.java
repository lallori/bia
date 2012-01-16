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
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;
import org.medici.docsources.common.util.DateUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
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
	private List<String> names;
	private List<NameType> namesTypes;
	private List<String> place;
	private List<Integer> placeId;
	private List<String> roleCategories;
	private List<String> titlesOcc;
	private List<Integer> titlesOccId;
	private List<String> words;
	private List<WordType> wordsTypes;
	private Boolean logicalDelete;

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
		placeId = new ArrayList<Integer>(0);
		place = new ArrayList<String>(0);
		roleCategories = new ArrayList<String>(0);
		titlesOcc = new ArrayList<String>(0);
		titlesOccId = new ArrayList<Integer>(0);
		logicalDelete = Boolean.FALSE;
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
	 * @return the roleCategories
	 */
	public List<String> getRoleCategories() {
		return roleCategories;
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
		
		//Occupations
		if((command.getOccupation() != null) && (command.getOccupation().size() > 0)){
			titlesOccId = new ArrayList<Integer>(command.getOccupation().size());
			titlesOcc = new ArrayList<String>(command.getOccupation().size());
			
			for(String singleWord : command.getOccupation()){
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
		
		//Places
		if((command.getPlace() != null) && (command.getPlace().size() > 0)){
			placeId = new ArrayList<Integer>(command.getPlace().size());
			place = new ArrayList<String>(command.getPlace().size());
			
			for(String singleWord : command.getPlace()){
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
					}
				}catch(NumberFormatException nex){
					
				}catch(URIException e){
					placeId.remove(placeId.size() - 1);
				}
			}
		}else{
			placeId = new ArrayList<Integer>(0);
			place = new ArrayList<String>(0);
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
	 * @param roleCategories the roleCategories to set
	 */
	public void setRoleCategories(List<String> roleCategories) {
		this.roleCategories = roleCategories;
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
	 * This method return a JPA Query object. 
	 */
	@Override
	public String toJPAQuery() {
		// TODO Auto-generated method stub
		StringBuffer jpaQuery = new StringBuffer("FROM People WHERE ");
		
		//Names
		if(names.size() > 0){
			StringBuffer namesQuery = new StringBuffer("(");
			for(int i = 0; i < names.size(); i++){
				if(namesQuery.length() > 1){
					namesQuery.append(" AND ");
				}
				if(namesTypes.get(i).equals(NameType.AllNameTypes)){
					namesQuery.append("(mapNameLf like '%");
					namesQuery.append(names.get(i).toLowerCase());
					namesQuery.append("%' OR ");
					namesQuery.append("(personId IN (SELECT person.personId FROM AltName WHERE altName like '%");
					namesQuery.append(names.get(i).toLowerCase());
					namesQuery.append("%')))");
				}else{
					namesQuery.append("(personId IN (SELECT person.personId FROM AltName WHERE altName like '%");
					namesQuery.append(names.get(i).toLowerCase());
					namesQuery.append("%' AND nameType like '");
					namesQuery.append(namesTypes.get(i).toString().toLowerCase());
					namesQuery.append("'))");
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
			StringBuffer wordsQuery = new StringBuffer("(");
			for(int i = 0; i < words.size(); i++){
				if(wordsQuery.length() > 1){
					wordsQuery.append(" AND ");
				}
				wordsQuery.append("((mapNameLf like '%");
				wordsQuery.append(words.get(i).toLowerCase());
				wordsQuery.append("%') OR ");
				wordsQuery.append("((bioNotes like '%");
				wordsQuery.append(words.get(i).toLowerCase());
				wordsQuery.append("%') OR ");
				wordsQuery.append("(staffNotes like '%");
				wordsQuery.append(words.get(i).toLowerCase());
				wordsQuery.append("%') OR ");
				wordsQuery.append("((mapNameLf like '%");
				wordsQuery.append(words.get(i).toLowerCase());
				wordsQuery.append("%') OR ");
				wordsQuery.append("(personId IN (SELECT person.personId FROM AltName WHERE altName like '%");
				wordsQuery.append(names.get(i).toLowerCase());
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
			StringBuffer datesQuery = new StringBuffer("(");
			for(int i = 0; i < datesTypes.size(); i++){
				if(datesTypes.get(i) == null){
					continue;
				}
				if(datesQuery.length() > 1){
					datesQuery.append(" AND ");
				}
				if(datesTypes.get(i).equals("Born after")){
					datesQuery.append("(bornDate>");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				}else if(datesTypes.get(i).equals("Died by")){
					datesQuery.append("(deathDate>");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(")");
				}else if(datesTypes.get(i).equals("Lived between")){
					datesQuery.append("(bornDate<");
					datesQuery.append(DateUtils.getNumberDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(" AND deathDate>");
					datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
					datesQuery.append(" AND deathDate<");
					datesQuery.append(DateUtils.getNumberDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)));
					datesQuery.append(")");
				}else if(datesTypes.get(i).equals("Born/Died on")){
					if(datesMonth.get(i) == null){
						datesQuery.append("(bornYear=");
						datesQuery.append(datesYear.get(i));
						datesQuery.append(" OR deathYear=");
						datesQuery.append(datesYear.get(i));
						datesQuery.append(")");
					}else{
						if(datesDay.get(i)==null){
							datesQuery.append("((bornYear=");
							datesQuery.append(datesYear.get(i));
							datesQuery.append(" AND bornMonth.monthNum=");
							datesQuery.append(datesMonth.get(i));
							datesQuery.append(") OR (deathYear=");
							datesQuery.append(datesYear.get(i));
							datesQuery.append(" AND deathMonth.monthNum=");
							datesQuery.append(datesMonth.get(i));
							datesQuery.append("))");
						}else{
							datesQuery.append("(bornDate=");
							datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
							datesQuery.append("OR deathDate=");
							datesQuery.append(DateUtils.getNumberDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)));
							datesQuery.append(")");
						}
					}
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
			StringBuffer roleCatQuery = new StringBuffer("(");
			for(int i = 0; i < roleCategories.size(); i++){
				if(roleCatQuery.length() > 1){
					roleCatQuery.append(" AND ");
				}
				if(roleCategories.get(i).equals("ARTISTS and ARTISANS") || roleCategories.get(i).equals("CORPORATE BODIES") || roleCategories.get(i).equals("ECCLESIASTICS") || roleCategories.get(i).equals("HEADS of STATE") || roleCategories.get(i).equals("MILITARY and NAVAL PERSONNEL") || roleCategories.get(i).equals("NOBLES") || roleCategories.get(i).equals("PROFESSIONS") || roleCategories.get(i).equals("SCHOLARLY and LITERARY") || roleCategories.get(i).equals("STATE and COURT PERSONNEL") || roleCategories.get(i).equals("UNASSIGNED")){
					roleCatQuery.append("(personId IN (SELECT person.personId FROM org.medici.docsources.domain.PoLink WHERE titleOccList.roleCat.roleCatMajor like '%");
					roleCatQuery.append(roleCategories.get(i));
					roleCatQuery.append("%'))");
				}else{
					roleCatQuery.append("(personId IN (SELECT person.personId FROM org.medici.docsources.domain.PoLink WHERE titleOccList.roleCat.roleCatMinor like '%");
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
		
		//Occupations
		if(titlesOccId.size() > 0){
			StringBuffer titleOccIdQuery = new StringBuffer("(");
			for(int i = 0; i < titlesOccId.size(); i++){
				if(titleOccIdQuery.length() > 1){
					titleOccIdQuery.append(" AND ");
				}
				if(titlesOccId.get(i) > 0){
					titleOccIdQuery.append("(personId IN (SELECT person.personId FROM org.medici.docsources.domain.PoLink WHERE titleOccList.titleOccId=");
					titleOccIdQuery.append(titlesOccId.get(i));
					titleOccIdQuery.append("))");
				}else{
					titleOccIdQuery.append("(personId IN (SELECT person.personId FROM org.medici.docsources.domain.PoLink WHERE titleOccList.titleOcc like '%");
					titleOccIdQuery.append(titlesOcc.get(i));
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
		
		//Places
		if(placeId.size() > 0){
			StringBuffer placeIdQuery = new StringBuffer("(");
			for(int i = 0; i < placeId.size(); i++){
				if(placeIdQuery.length() > 1){
					placeIdQuery.append(" AND ");
				}
				if(placeId.get(i) > 0){
					placeIdQuery.append("(bornPlace.placeAllId=");
					placeIdQuery.append(placeId.get(i));
					placeIdQuery.append(" OR deathPlace.placeAllId=");
					placeIdQuery.append(placeId.get(i));
					placeIdQuery.append(")");
				}else{
					placeIdQuery.append("(bornPlace.placeName like '%");
					placeIdQuery.append(place.get(i));
					placeIdQuery.append("%' OR deathPlace.placeName like '%");
					placeIdQuery.append(place.get(i));
					placeIdQuery.append("%' )");
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
					subQuery.add(new PrefixQuery(new Term("mapNameLf", names.get(i).toString().toLowerCase())),Occur.MUST);
					subQuery.add(new PrefixQuery(new Term("altName.altName", names.get(i).toLowerCase())), Occur.MUST);
					namesQuery.add(subQuery, Occur.MUST);
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
		String toString = new String();
		if(!names.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Names: ";
			for(int i = 0; i < names.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += names.get(i) + " ";
			}
		}
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
		if(!roleCategories.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Role Categories: ";
			for(int i = 0; i < roleCategories.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += roleCategories.get(i) + " ";
			}
		}
		if(!titlesOcc.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Occupations: ";
			for(int i = 0; i < titlesOcc.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += titlesOcc.get(i) + " ";
			}
		}
		if(!place.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Places: ";
			for(int i = 0; i < place.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += place.get(i) + " ";
			}
		}
		return toString;
	}
}
