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
package org.medici.docsources.common.search;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchPlace extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private List<String> placesName;
	private List<String> placeType;
	private List<Integer> linkedToTopicsId;
	private List<String> linkedToPeople;
	

	/**
	 * 
	 */
	public AdvancedSearchPlace() {
		super();
		
		placesName = new ArrayList<String>(0);
		placeType = new ArrayList<String>(0);
		linkedToTopicsId = new ArrayList<Integer>(0);
		linkedToPeople = new ArrayList<String>(0);
		
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
				try{
					placesName.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){					
				}catch(URIException e){
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
				}catch(NumberFormatException nex){					
				}catch(URIException e){
				}
			}
		}else{
			placeType = new ArrayList<String>(0);
		}
		
		//Linked To Topics
		if((command.getLinkedToTopics() != null) && (command.getLinkedToTopics().size() > 0)){
			linkedToTopicsId = new ArrayList<Integer>(command.getLinkedToTopics().size());
			
			for(String singleWord: command.getLinkedToTopics()){
				StringTokenizer stringTokenizer = new StringTokenizer(singleWord, "|");
				try{
					if(stringTokenizer.countTokens() < 2){
						continue;
					}else if(stringTokenizer.countTokens() == 2){
						String singleId = stringTokenizer.nextToken();
						stringTokenizer.nextToken();
						
						if(NumberUtils.isNumber(singleId)){
							linkedToTopicsId.add(NumberUtils.createInteger(singleId));
						}
					}
				}catch(NumberFormatException nex){
				}
			}
		}else{
			linkedToTopicsId = new ArrayList<Integer>(0);
		}
		
		//Linked To People
		if((command.getLinkedToPeople() != null) && (command.getLinkedToPeople().size() > 0)){
			linkedToPeople = new ArrayList<String>(command.getLinkedToPeople().size());
			
			for(String singleWord : command.getLinkedToPeople()){
				try{
					linkedToPeople.add(URIUtil.decode(singleWord, "UTF-8"));
				}catch(NumberFormatException nex){
				}catch(URIException e){
				}
			}
		}else{
			linkedToPeople = new ArrayList<String>(0);
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
	 * @param linkedToPeople the linkedToPeople to set
	 */
	public void setLinkedToPeople(List<String> linkedToPeople) {
		this.linkedToPeople = linkedToPeople;
	}

	/**
	 * @return the linkedToPeople
	 */
	public List<String> getLinkedToPeople() {
		return linkedToPeople;
	}

	/**
	 * @param linkedToTopicsId the linkedToTopicsId to set
	 */
	public void setLinkedToTopicsId(List<Integer> linkedToTopicsId) {
		this.linkedToTopicsId = linkedToTopicsId;
	}

	/**
	 * @return the linkedToTopicsId
	 */
	public List<Integer> getLinkedToTopicsId() {
		return linkedToTopicsId;
	}

	/**
	 * @param placesName the placesName to set
	 */
	public void setPlacesName(List<String> placesName) {
		this.placesName = placesName;
	}

	/**
	 * @return the placesName
	 */
	public List<String> getPlacesName() {
		return placesName;
	}

	/**
	 * @param placeType the placeTypeId to set
	 */
	public void setPlaceType(List<String> placeType) {
		this.placeType = placeType;
	}

	/**
	 * @return the placeType
	 */
	public List<String> getPlaceType() {
		return placeType;
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
		if(linkedToTopicsId.size() > 0){
			BooleanQuery linkedToTopicsIdQuery = new BooleanQuery();
			for(int i = 0; i < linkedToTopicsId.size(); i++){
				BooleanQuery singleLinkedToTopicsIdQuery = new BooleanQuery();
				BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("eplToLinks.topic.topicId", linkedToTopicsId.get(i).toString())), Occur.MUST);
				singleLinkedToTopicsIdQuery.add(booleanClause);
				linkedToTopicsIdQuery.add(singleLinkedToTopicsIdQuery, Occur.MUST);
			}
			if(!linkedToTopicsIdQuery.toString().equals("")){
				luceneQuery.add(linkedToTopicsIdQuery, Occur.MUST);
			}
		}
		
		//TODO
		//Linked To People
		if(linkedToPeople.size() > 0){
			BooleanQuery linkedToPeopleQuery = new BooleanQuery();
			for(int i = 0; i < linkedToPeople.size(); i++){
				if(linkedToPeople.get(i).equals("Sender Location")){
					BooleanQuery singleLinkedToPeopleQuery = new BooleanQuery();
					QueryParser query = new QueryParser(Version.LUCENE_31, "senderDocuments.senderPlace.placeName", new StandardAnalyzer(Version.LUCENE_31));
					query.setAllowLeadingWildcard(true);
					try {
						Query test = query.parse("*");
						linkedToPeopleQuery.add(test, Occur.MUST);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(!linkedToPeopleQuery.toString().equals("")){
				luceneQuery.add(linkedToPeopleQuery, Occur.MUST);
			}
		}
		
		
		return luceneQuery;
	}
}

