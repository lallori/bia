/*
 * SimpleSearchDocument.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.common.util.SimpleSearchUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class SimpleSearchDocument extends SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private String text; 

	private SimpleSearchPerimeter simpleSearchPerimeter;

	/**
	 * 
	 */
	public SimpleSearchDocument() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public SimpleSearchDocument(SimpleSearchPerimeter simpleSearchPerimeter, String text) {
		super();
		if (!StringUtils.isEmpty(text)) {
			setSimpleSearchPerimeter(simpleSearchPerimeter);
			setText(text.toLowerCase());
		}
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the simpleSearchPerimeter
	 */
	public SimpleSearchPerimeter getSimpleSearchPerimeter() {
		return simpleSearchPerimeter;
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromText(SimpleSearchPerimeter simpleSearchPerimeter, String text) {
		if (!StringUtils.isEmpty(text)) {
			setText(text.toLowerCase());
			setSimpleSearchPerimeter(simpleSearchPerimeter);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean empty() {
		if (StringUtils.isEmpty(text)) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * @param text the alias to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param simpleSearchPerimeter the simpleSearchPerimeter to set
	 */
	public void setSimpleSearchPerimeter(SimpleSearchPerimeter simpleSearchPerimeter) {
		this.simpleSearchPerimeter = simpleSearchPerimeter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM Document ");
		
		if (!empty()) {
			//MD: We need to re-convert the alias
			text = text.replace("\\\"", "\"");
			String toSearch = text;
			
			List<String> exactWords = new ArrayList<String>();
			
			//MD: This code is to identify the words between double quotes
			while(toSearch.contains("\"")){
				//First double quote
				int from = toSearch.indexOf('\"');
				//Second double quote
				int to = toSearch.indexOf('\"', from + 1);
				//If there is the second double quote or not
				if(to != -1){
					//Add the exact words to the list and remove them from the string
					exactWords.add(toSearch.substring(from + 1, to));
					toSearch = toSearch.substring(0, from) + toSearch.substring(to + 1, toSearch.length());
				}else{
					toSearch = toSearch.replace("\"", " ");
				}
			}
			String[] words = StringUtils.split(toSearch, " ");
			
			if (words.length >0 || exactWords.size() > 0) {
				jpaQuery.append(" WHERE ");
			}
			
			if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT)){
				for(int i = 0; i < exactWords.size(); i++){
					jpaQuery.append("(synExtract.docExtract LIKE '% ");
					jpaQuery.append(exactWords.get(i).trim().replace("'", "''"));
					jpaQuery.append(" %')");
					if(i < exactWords.size() - 1){
						jpaQuery.append(" AND ");
					}
				}			
				if(exactWords.size() > 0 && words.length > 0){
					jpaQuery.append(" AND ");
				}
				for(int i = 0; i < words.length; i++){
					jpaQuery.append("(synExtract.docExtract like '%");
					jpaQuery.append(words[i].replace("'", "''"));
					jpaQuery.append("%')");
					if(i < words.length-1){
						jpaQuery.append(" AND ");
					}
				}
			}else if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)){
				for(int i = 0; i < exactWords.size(); i++){
					jpaQuery.append("(synExtract.synopsis LIKE '% ");
					jpaQuery.append(exactWords.get(i).trim().replace("'", "''"));
					jpaQuery.append(" %')");
					if(i < exactWords.size() - 1){
						jpaQuery.append(" AND ");
					}
				}
				if (exactWords.size() > 0 && words.length > 0) {
					jpaQuery.append(" AND ");
				}
				for(int i = 0; i < words.length; i++){
					jpaQuery.append("(synExtract.synopsis like '%");
					jpaQuery.append(words[i].replace("'", "''"));
					jpaQuery.append("%')");
					if(i < words.length-1){
						jpaQuery.append(" AND ");
					}
				}
			}
			
			//To discard record deleted
			if(jpaQuery.indexOf("WHERE") != -1){
				jpaQuery.append(" AND logicalDelete = false");
			}
		} else {
			jpaQuery.append(" WHERE logicalDelete = false");
		}
		
		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		
		if (StringUtils.isEmpty(text)) {
			return booleanQuery;
		}

		String[] stringFieldExtract = new String[]{
			"synExtract.docExtract"
		};
		
		String[] stringFieldSynopsis = new String[]{
			"synExtract.synopsis"
		};
		
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(text);
		
		//E.g. (recipientPeople.mapNameLf: (+cosimo +medici +de) )
		Query stringQuery = null;
		if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT)) {
			stringQuery = SimpleSearchUtils.constructBooleanQueryOnStringFields(stringFieldExtract, words);
		} else if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)) {
			stringQuery = SimpleSearchUtils.constructBooleanQueryOnStringFields(stringFieldSynopsis, words);
		}

		if (!stringQuery.toString().equals("")) {
			booleanQuery.add(stringQuery,Occur.SHOULD);
		}

		return booleanQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (text != null) {
			return getText();
		} else {
			return "";
		}
	}
}