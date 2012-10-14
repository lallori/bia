/*
 * AdvancedSearchPlace.java
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
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.command.search.SimpleSearchCommand;
/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchPlace extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3644033567435491386L;

	private List<String> linkedToPeople;
	private Boolean logicalDelete;
	private List<String> placesName;
	private List<String> placeType;
	private List<Integer> datesLastUpdateDay;
	private List<Integer> datesLastUpdateDayBetween;
	private List<Integer> datesLastUpdateMonth;
	private List<Integer> datesLastUpdateMonthBetween;
	private List<Integer> datesLastUpdateYear;
	private List<Integer> datesLastUpdateYearBetween;
	private List<DateType> datesLastUpdateTypes;

	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public AdvancedSearchPlace() {
		super();
		
		placesName = new ArrayList<String>(0);
		placeType = new ArrayList<String>(0);
//		linkedToTopicsId = new ArrayList<Integer>(0);
//		linkedToTopics = new ArrayList<String>(0);
		linkedToPeople = new ArrayList<String>(0);
		logicalDelete = null;
		datesLastUpdateDay = new ArrayList<Integer>(0);
		datesLastUpdateDayBetween = new ArrayList<Integer>(0);
		datesLastUpdateMonth = new ArrayList<Integer>(0);
		datesLastUpdateMonthBetween = new ArrayList<Integer>(0);
		datesLastUpdateYear = new ArrayList<Integer>(0);
		datesLastUpdateYearBetween = new ArrayList<Integer>(0);
		datesLastUpdateTypes = new ArrayList<DateType>(0);
	}

	/**
	 * @return the linkedToPeople
	 */
	public List<String> getLinkedToPeople() {
		return linkedToPeople;
	}

	/**
	 * @return the logicalDelete
	 */
	public Boolean getLogicalDelete() {
		return logicalDelete;
	}

	/**
	 * @return the placesName
	 */
	public List<String> getPlacesName() {
		return placesName;
	}

	/**
	 * @return the placeType
	 */
	public List<String> getPlaceType() {
		return placeType;
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
	 * {@inheritDoc}
	 */
	@Override
	public void initFromAdvancedSearchCommand(AdvancedSearchCommand command) {
		// Place Name
		if((command.getPlaceName() != null) && (command.getPlaceName().size() > 0)){
			placesName = new ArrayList<String>(command.getPlaceName().size());
			
			for(String singleWord : command.getPlaceName()){
				//MD: This is for refine search when the URLencoder change the space in "+" and the special character "ç" in "%E7"
				singleWord = singleWord.replace("+", "%20");
				singleWord = singleWord.replace("%E7", "ç");
				try{
					placesName.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
				}
			}
		}else{
			placesName = new ArrayList<String>(0);
		}
		
		//Place Type
		if((command.getPlaceType() != null) && (command.getPlaceType().size() > 0)){
			placeType = new ArrayList<String>(command.getPlaceType().size());
			
			for(String singleWord : command.getPlaceType()){
				try{
					placeType.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
				}
			}
		}else{
			placeType = new ArrayList<String>(0);
		}
		
		//Linked To Topics
		//MD: Section deleted
//		if((command.getLinkedToTopics() != null) && (command.getLinkedToTopics().size() > 0)){
//			linkedToTopicsId = new ArrayList<Integer>(command.getLinkedToTopics().size());
//			
//			for(String singleWord: command.getLinkedToTopics()){
//				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
//				try{
//					if(stringTokenizer.countTokens() < 2){
//						continue;
//					}else if(stringTokenizer.countTokens() == 2){
//						String singleId = stringTokenizer.nextToken();
//						String singleText = stringTokenizer.nextToken();
//						
//						if(NumberUtils.isNumber(singleId)){
//							linkedToTopicsId.add(NumberUtils.createInteger(singleId));
//						}
//						linkedToTopics.add(URIUtil.decode(singleText, "UTF-8"));
//					}
//				}catch(NumberFormatException nex){
//				}catch(URIException e){
//				}
//			}
//		}else{
//			linkedToTopicsId = new ArrayList<Integer>(0);
//		}
		
		//Linked To People
		if((command.getLinkedToPeople() != null) && (command.getLinkedToPeople().size() > 0)){
			linkedToPeople = new ArrayList<String>(command.getLinkedToPeople().size());
			
			for(String singleWord : command.getLinkedToPeople()){
				try{
					linkedToPeople.add(URIUtil.decode(singleWord, "UTF-8"));
				} catch (NumberFormatException numberFormatException) {
					logger.debug(numberFormatException);
				} catch (URIException uriException) {
					logger.debug(uriException);
				}
			}
		}else{
			linkedToPeople = new ArrayList<String>(0);
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
		placesName.add(command.getText());
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean empty() {
		if (
				(placesName.size()>0) ||
				(placeType.size()>0) ||
				(linkedToPeople.size()>0)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * @param linkedToPeople the linkedToPeople to set
	 */
	public void setLinkedToPeople(List<String> linkedToPeople) {
		this.linkedToPeople = linkedToPeople;
	}

	/**
	 * @param logicalDelete the logicalDelete to set
	 */
	public void setLogicalDelete(Boolean logicalDelete) {
		this.logicalDelete = logicalDelete;
	}

	/**
	 * @param placesName the placesName to set
	 */
	public void setPlacesName(List<String> placesName) {
		this.placesName = placesName;
	}

	/**
	 * @param placeType the placeTypeId to set
	 */
	public void setPlaceType(List<String> placeType) {
		this.placeType = placeType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		// TODO Auto-generated method stub
		StringBuilder jpaQuery = new StringBuilder("FROM Place WHERE ");
		
		//Place Name
		if(placesName.size() > 0){
			StringBuilder placesNameQuery = new StringBuilder("(");
			for(int i = 0; i < placesName.size(); i++){
				String[] wordsSinglePlaceNames = StringUtils.split(placesName.get(i), " ");
				if(placesNameQuery.length() > 1){
					placesNameQuery.append(" AND ");
				}
				for(int j = 0; j < wordsSinglePlaceNames.length; j++){
					placesNameQuery.append("((placeNameFull like '%");
					placesNameQuery.append(wordsSinglePlaceNames[j].toLowerCase().replace("'", "''"));
					placesNameQuery.append("%') or ");
					placesNameQuery.append("(termAccent like '%");
					placesNameQuery.append(wordsSinglePlaceNames[j].toLowerCase().replace("'", "''"));
					placesNameQuery.append("%'))");
					if(j < (wordsSinglePlaceNames.length - 1)){
						placesNameQuery.append(" AND ");
					}
				}
			}
			placesNameQuery.append(')');
			if(!placesNameQuery.toString().equals("")){
				if(jpaQuery.length() > 17){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(placesNameQuery);
			}
		}
		
		//Place Type
		if(placeType.size() > 0){
			StringBuilder placeTypeQuery = new StringBuilder("(");
			for(int i = 0; i < placeType.size(); i++){
				if(placeTypeQuery.length() > 1){
					placeTypeQuery.append(" AND ");
				}
				placeTypeQuery.append("(plType like '%");
				placeTypeQuery.append(placeType.get(i).toLowerCase());
				placeTypeQuery.append("%')");
			}
			placeTypeQuery.append(')');
			if(!placeTypeQuery.toString().equals("")){
				if(jpaQuery.length() > 17){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(placeTypeQuery);
			}
		}
		
		//Linked to topics
		//MD: Section deleted
//		if(linkedToTopicsId.size() > 0){
//			StringBuilder linkedToTopicsIdQuery = new StringBuilder("(");
//			for(int i = 0; i < linkedToTopicsId.size(); i++){
//				if(linkedToTopicsIdQuery.length() > 1){
//					linkedToTopicsIdQuery.append(" AND ");
//				}
//				linkedToTopicsIdQuery.append("(placeAllId IN (SELECT place.placeAllId FROM org.medici.bia.domain.EplToLink WHERE topic.topicId=");
//				linkedToTopicsIdQuery.append(linkedToTopicsId.get(i).toString());
//				linkedToTopicsIdQuery.append("))");
//			}
//			linkedToTopicsIdQuery.append(')');
//			if(!linkedToTopicsIdQuery.toString().equals("")){
//				if(jpaQuery.length() > 17){
//					jpaQuery.append(" AND ");
//				}
//				jpaQuery.append(linkedToTopicsIdQuery);
//			}
//		}
		
		//Linked to people
		if(linkedToPeople.size() > 0){
			StringBuilder linkedToPeopleQuery = new StringBuilder("(");
			for(int i = 0; i < linkedToPeople.size(); i++){
				if(linkedToPeopleQuery.length() > 1){
					linkedToPeopleQuery.append(" AND ");
				}
				if(linkedToPeople.get(i).equals("Sender Location")){
					linkedToPeopleQuery.append("(geogKey IN (SELECT senderPlace.geogKey FROM Document WHERE senderPlace is not null))");
				}
				if(linkedToPeople.get(i).equals("Recipient Location")){
					linkedToPeopleQuery.append("(geogKey IN (SELECT recipientPlace.geogKey FROM Document WHERE recipientPlace is not null))");
				}
				if(linkedToPeople.get(i).equals("Birth Place")){
					linkedToPeopleQuery.append("(geogKey IN (SELECT bornPlace.geogKey FROM People WHERE bornPlace is not null))");
				}
				if(linkedToPeople.get(i).equals("Death Place")){
					linkedToPeopleQuery.append("(geogKey IN (SELECT deathPlace.geogKey FROM People WHERE deathPlace is not null))");
				}
			}
			linkedToPeopleQuery.append(')');
			if(!linkedToPeopleQuery.toString().equals("")){
				if(jpaQuery.length() > 17){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(linkedToPeopleQuery);
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
			logicalDeleteQuery.append(')');
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
		
		//Place Name
		if(placesName.size() > 0){
			BooleanQuery placesNameQuery = new BooleanQuery();
			for(int i = 0; i < placesName.size(); i++){
				BooleanQuery subQuery = new BooleanQuery();
				//MD: Two query because in the result page the first should be the places that contains the placeName in the field
				//"placeName", and after the places that contains the placeName in "placeNameFull", but I have a problem with sortingCriterias
				
				subQuery.add(new TermQuery(new Term("placeName", placesName.get(i).toLowerCase())), Occur.MUST);
				subQuery.add(new PrefixQuery(new Term("termAccent", placesName.get(i))), Occur.MUST);
				
				placesNameQuery.add(subQuery, Occur.SHOULD);
				
				subQuery = new BooleanQuery();
				subQuery.add(new PrefixQuery(new Term("placeNameFull", placesName.get(i).toLowerCase())), Occur.MUST);
				subQuery.add(new PrefixQuery(new Term("termAccent", placesName.get(i))), Occur.SHOULD);
				
				placesNameQuery.add(subQuery, Occur.MUST);
			}
			if(!placesNameQuery.toString().equals("")){
				luceneQuery.add(placesNameQuery, Occur.MUST);
			}
		}
		
		//Place Type
		if(placeType.size() > 0){
			BooleanQuery placeTypeQuery = new BooleanQuery();
			for(int i = 0; i < placeType.size(); i++){
				String[] wordSinglePlaceType = placeType.get(i).split(" ");
				for(int j = 0; j < wordSinglePlaceType.length; j++){
					PhraseQuery singlePlaceTypeQuery = new PhraseQuery();
					//BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("plType", placeType.get(i).toLowerCase())), Occur.MUST);
					//singlePlaceTypeQuery.add(booleanClause);
					singlePlaceTypeQuery.add(new Term("plType", wordSinglePlaceType[j].toLowerCase()));
					placeTypeQuery.add(singlePlaceTypeQuery, Occur.MUST);	
				}
			}
			if(!placeTypeQuery.toString().equals("")){
				luceneQuery.add(placeTypeQuery, Occur.MUST);
			}
		}
		
		//Linked To Topics
//		if(linkedToTopicsId.size() > 0){
//			BooleanQuery linkedToTopicsIdQuery = new BooleanQuery();
//			for(int i = 0; i < linkedToTopicsId.size(); i++){
//				BooleanQuery singleLinkedToTopicsIdQuery = new BooleanQuery();
//				BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("eplToLinks.topic.topicId", linkedToTopicsId.get(i).toString())), Occur.MUST);
//				singleLinkedToTopicsIdQuery.add(booleanClause);
//				linkedToTopicsIdQuery.add(singleLinkedToTopicsIdQuery, Occur.MUST);
//			}
//			if(!linkedToTopicsIdQuery.toString().equals("")){
//				luceneQuery.add(linkedToTopicsIdQuery, Occur.MUST);
//			}
//		}
		
		//TODO
		//Linked To People
		if(linkedToPeople.size() > 0){
			BooleanQuery linkedToPeopleQuery = new BooleanQuery();
			for(int i = 0; i < linkedToPeople.size(); i++){
				if(linkedToPeople.get(i).equals("Sender Location")){
					QueryParser query = new QueryParser(Version.LUCENE_31, "senderDocuments.senderPlace.placeName", new StandardAnalyzer(Version.LUCENE_31));
					query.setAllowLeadingWildcard(true);
					try {
						Query test = query.parse("*");
						linkedToPeopleQuery.add(test, Occur.MUST);
					} catch (ParseException parseException) {
						logger.debug(parseException);
					}
				}
			}
			if(!linkedToPeopleQuery.toString().equals("")){
				luceneQuery.add(linkedToPeopleQuery, Occur.MUST);
			}
		}
		
		
		return luceneQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString(){
		String toString = new String();
		
		if(!placesName.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Places Name: ";
			for(int i = 0; i < placesName.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += placesName.get(i) + " ";
			}
		}
		
		if(!placeType.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Place Type: ";
			for(int i = 0; i < placeType.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += placeType.get(i) + " ";
			}
		}
		
//		if(!linkedToTopics.isEmpty()){
//			if(!toString.isEmpty()){
//				toString += "AND ";
//			}
//			toString += "Linked to Topics: ";
//			for(int i = 0; i < linkedToTopics.size(); i++){
//				if(i > 0){
//					toString += "AND ";
//				}
//				toString += linkedToTopics.get(i) + " ";
//			}
//		}
		
		if(!linkedToPeople.isEmpty()){
			if(!toString.isEmpty()){
				toString += "AND ";
			}
			toString += "Linked to People: ";
			for(int i = 0; i < linkedToPeople.size(); i++){
				if(i > 0){
					toString += "AND ";
				}
				toString += linkedToPeople.get(i) + " ";
			}
		}
		
		return toString;
	}
}

