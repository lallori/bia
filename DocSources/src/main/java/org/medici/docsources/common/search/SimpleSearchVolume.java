/*
 * SimpleSearchVolume.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.common.util.SimpleSearchUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class SimpleSearchVolume extends SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3768384416633997852L;
	
	private String alias; 

	/**
	 * 
	 */
	public SimpleSearchVolume() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public SimpleSearchVolume(String text) {
		super();
		if (!StringUtils.isEmpty(text)) {
			setAlias(text.toLowerCase());
		}
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromText(String text) {
		if (!StringUtils.isEmpty(text)) {
			setAlias(text);
		}
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM Volume ");
		
		//MD: We need to re-convert the alias
		alias = alias.replace("\\\"", "\"");
		
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
		
		if(words.length > 0){
			jpaQuery.append(" WHERE ");
		}
		
		for(int i = 0; i < words.length; i++){
			if(NumberUtils.isNumber(words[0])){
				jpaQuery.append("(volNum = ");
				jpaQuery.append(words[0]);
				jpaQuery.append(")");
			}else{
				if(words[0].length() == 1){
					jpaQuery.append("(volLetExt like '");
					jpaQuery.append(words[0]);
					jpaQuery.append("')");
				}else{
					jpaQuery.append("(ccondition like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(ccontext like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(orgNotes like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(recips like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(senders like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(serieList.title like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(serieList.subTitle1 like '%");
					jpaQuery.append(words[i] + "%') OR ");
					jpaQuery.append("(serieList.subTitle2 like '%");
					jpaQuery.append(words[i] + "%')");
				}
			}
			if(i < (words.length - 1)){
				jpaQuery.append(" AND ");
			}
			
		}
		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		
		if (StringUtils.isEmpty(alias)) {
			return booleanQuery;
		}

		String[] stringFields = new String[]{			
			"ccondition",
			"ccontext", 
			"orgNotes",
			"recips", 
			"senders", 
			"serieList.title", 
			"serieList.subTitle1",
			"serieList.subTitle2"
		};
		
		String[] numericFields = new String[]{
			"summaryId",
			"volNum"
		};
		
		String[] yearFields = new String[]{
			"startYear",
			"endYear"
		};

		String[] monthFields = new String[]{
			"startMonthNum.monthNum", 
			"endMonthNum.monthNum" 
		};
		
		String[] dayFields = new String[]{
			"startDay",
			"endDay"		
		};

		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
		
		//E.g. (recipientPeople.mapNameLf: (+cosimo +medici +de) )
		Query stringQuery = SimpleSearchUtils.constructBooleanQueryOnStringFields(stringFields, words);
		if (!stringQuery.toString().equals("")) {
			booleanQuery.add(stringQuery,Occur.SHOULD);
		}
		Query numericQuery = SimpleSearchUtils.constructBooleanQueryOnNumericFields(numericFields, words);
		if (!numericQuery.toString().equals("")) {
			booleanQuery.add(numericQuery,Occur.SHOULD);
		}
		Query yearQuery = SimpleSearchUtils.constructBooleanQueryOnYearFields(yearFields, words);
		if (!yearQuery.toString().equals("")) {
			booleanQuery.add(yearQuery,Occur.SHOULD);
		}
		Query monthQuery = SimpleSearchUtils.constructBooleanQueryOnMonthFields(monthFields, words);
		if (!monthQuery.toString().equals("")) {
			booleanQuery.add(monthQuery,Occur.SHOULD);
		}
		Query dayQuery = SimpleSearchUtils.constructBooleanQueryOnDayFields(dayFields, words);
		if (!dayQuery.toString().equals("")) {
			booleanQuery.add(dayQuery,Occur.SHOULD);
		}

		return booleanQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (alias != null)
			return getAlias();
		else
			return "";
	}
}