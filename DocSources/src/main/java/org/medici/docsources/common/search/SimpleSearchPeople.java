/*
 * SimpleSearchPeople.java
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
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.BooleanClause.Occur;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.common.util.SimpleSearchUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchPeople implements SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private String alias; 

	/**
	 * 
	 */
	public SimpleSearchPeople() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public SimpleSearchPeople(String text) {
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
	 * It's more simple construct lucene Query with string.
	 */
	@Override
	public String toLuceneQueryString() {
		if (StringUtils.isEmpty(alias)) {
			return "";
		}

		String[] stringFields = new String[]{			
			"mapNameLf", 
			"altName.altName", 
		};

		String[] numericFields = new String[]{
			"personId"
		};
		
		String[] yearFields = new String[]{
			"bornYear",
			"deathYear"
		};

		String[] monthFields = new String[]{
			"bornMonth.monthNum", 
			"deathMonth.monthNum" 
		};
		
		String[] dayFields = new String[]{
			"bornDay",
			"deathDay"
		};

		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(SimpleSearchUtils.constructConditionOnStringFields(stringFields, words));
		stringBuffer.append(SimpleSearchUtils.constructConditionOnNumericFields(numericFields, words));
		stringBuffer.append(SimpleSearchUtils.constructConditionOnYearFields(yearFields, words));
		stringBuffer.append(SimpleSearchUtils.constructConditionOnMonthFields(monthFields, words));
		stringBuffer.append(SimpleSearchUtils.constructConditionOnDayFields(dayFields, words));

		return stringBuffer.toString();
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

	/**
	 * 
	 */
	@Override
	public Query toLuceneQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		
		if (StringUtils.isEmpty(alias)) {
			return booleanQuery;
		}

		String[] stringFields = new String[]{			
			"mapNameLf", 
			"altName.altName", 
		};

		String[] numericFields = new String[]{
			"personId"
		};
		
		String[] yearFields = new String[]{
			"bornYear",
			"deathYear"
		};

		String[] monthFields = new String[]{
			"bornMonth.monthNum", 
			"deathMonth.monthNum" 
		};
		
		String[] dayFields = new String[]{
			"bornDay",
			"deathDay"
		};

		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);

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
}

