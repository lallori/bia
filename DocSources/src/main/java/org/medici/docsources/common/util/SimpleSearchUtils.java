/*
 * SimpleSearchUtil.java
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
package org.medici.docsources.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchUtils {
	/**
	 * 
	 * @param dayFields
	 * @param words
	 * @return
	 */
	public static BooleanQuery constructBooleanQueryOnDayFields(String[] dayFields, String[] words) {
		BooleanQuery query = new BooleanQuery();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1) || (number>31)) {
				continue;
			}
			
			BooleanClause booleanClause = null;
			for (int j=0; j<dayFields.length; j++) {
				booleanClause = new BooleanClause(new TermQuery(new Term(dayFields[j], words[i])), Occur.SHOULD);
				query.add(booleanClause);
			}
		}
		
		return query;
	}

	/**
	 * 
	 * @param monthFields
	 * @param words
	 * @return
	 */
	public static BooleanQuery constructBooleanQueryOnMonthFields(String[] monthFields, String[] words) {
		BooleanQuery query = new BooleanQuery();
		// We add conditions on month fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1) || (number>12)) {
				continue;
			}
			
			BooleanClause booleanClause = null;
			for (int j=0; j<monthFields.length; j++) {
				booleanClause = new BooleanClause(new TermQuery(new Term(monthFields[j], words[i])), Occur.SHOULD);
				query.add(booleanClause);
			}
		}
		
		return query;
	}

	/**
	 * 
	 * @param numericFields
	 * @param words
	 * @return
	 */
	public static BooleanQuery constructBooleanQueryOnNumericFields(String[] numericFields, String[] words) {
		BooleanQuery query = new BooleanQuery();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			
			BooleanClause booleanClause = null;
			for (int j=0; j<numericFields.length; j++) {
				booleanClause = new BooleanClause(new TermQuery(new Term(numericFields[j], words[i])), Occur.SHOULD);
				query.add(booleanClause);
			}
		}
		
		return query;
	}

	/**
	 * 
	 * @param stringFields
	 * @param words
	 * @return
	 */
	public static BooleanQuery constructBooleanQueryOnStringFields(String[] stringFields, String[] words, Occur queryOccur, Occur clauseOccur) {
		BooleanQuery query = new BooleanQuery();
		// We add conditions on string fields
		for (int i=0; i<stringFields.length; i++) {
			BooleanQuery singleFieldQuery = new BooleanQuery();
			for (int j=0; j<words.length; j++) {
				BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term(stringFields[i], words[j].toLowerCase())), clauseOccur);
				singleFieldQuery.add(booleanClause);
			}
			query.add(singleFieldQuery, queryOccur);
		}
		
		return query;
	}

	/**
	 * 
	 * @param stringFields
	 * @param words
	 * @return
	 */
	public static BooleanQuery constructBooleanQueryOnStringFields(String[] stringFields, String[] words) {
		BooleanQuery query = new BooleanQuery();
		// We add conditions on string fields
		for (int i=0; i<stringFields.length; i++) {
			BooleanQuery singleFieldQuery = new BooleanQuery();
			for (int j=0; j<words.length; j++) {
				BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term(stringFields[i], words[j].toLowerCase())), Occur.MUST);
				singleFieldQuery.add(booleanClause);
			}
			query.add(singleFieldQuery,Occur.SHOULD);
		}
		
		return query;
	}

	/**
	 * 
	 * @param yearFields
	 * @param words
	 * @return
	 */
	public static BooleanQuery constructBooleanQueryOnYearFields(String[] yearFields, String[] words) {
		BooleanQuery query = new BooleanQuery();

		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1200) || (number>1800)) {
				continue;
			}
			
			BooleanClause booleanClause = null;
			for (int j=0; j<yearFields.length; j++) {
				booleanClause = new BooleanClause(new TermQuery(new Term(yearFields[j], words[i])), Occur.SHOULD);
				query.add(booleanClause);
			}
		}
		
		return query;
	}

	/**
	 * 
	 * @param volumeFields
	 * @param words
	 * @return
	 */
	public static Query constructBooleanQueryOnVolumeFields(String[] volumeFields, String[] words) {
		BooleanQuery query = new BooleanQuery();
		// We add conditions on volume 
		for (int i=0; i<words.length; i++) {
			// if word is not in volume format we skip
			if (!VolumeUtils.isVolumeFormat(words[i])) {
				continue;
			}
			for (int j=0; j<volumeFields.length; j++) {
				BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term(volumeFields[j], VolumeUtils.extractVolNum(words[i]).toString())), Occur.SHOULD);
				query.add(booleanClause);
			}
		}
		
		return query;
	}

	/**
	 * 
	 * @param dayFields
	 * @param words
	 * @return
	 */
	public static StringBuilder constructConditionOnDayFields(String[] dayFields, String[] words) {
		StringBuilder stringBuilder = new StringBuilder();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1) || (number>31)) {
				continue;
			}
			
			for (int j=0; j<dayFields.length; j++) {
				stringBuilder.append("(");
				stringBuilder.append(dayFields[j]);
				stringBuilder.append(": ");
				stringBuilder.append(words[i]);
				stringBuilder.append(") ");
			}
		}
		
		return stringBuilder;
	}

	/**
	 * 
	 * @param monthFields
	 * @param words
	 * @return
	 */
	public static StringBuilder constructConditionOnMonthFields(String[] monthFields, String[] words) {
		StringBuilder stringBuilder = new StringBuilder();
		// We add conditions on month fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1) || (number>12)) {
				continue;
			}
			
			for (int j=0; j<monthFields.length; j++) {
				stringBuilder.append("(");
				stringBuilder.append(monthFields[j]);
				stringBuilder.append(": ");
				stringBuilder.append(words[i]);
				stringBuilder.append(") ");
			}
		}
		
		return stringBuilder;
	}

	/**
	 * 
	 * @param numericFields
	 * @param words
	 * @return
	 */
	public static StringBuilder constructConditionOnNumericFields(String[] numericFields, String[] words) {
		StringBuilder stringBuilder = new StringBuilder();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			
			for (int j=0; j<numericFields.length; j++) {
				stringBuilder.append("(");
				stringBuilder.append(numericFields[j]);
				stringBuilder.append(": ");
				stringBuilder.append(words[i]);
				stringBuilder.append(") ");
			}
		}
		
		return stringBuilder;
	}

	/**
	 * 
	 * @param stringFields
	 * @param words
	 * @return
	 */
	public static StringBuilder constructConditionOnStringFields(String[] stringFields, String[] words) {
		StringBuilder stringBuilder = new StringBuilder();
		// We add conditions on string fields
		for (int i=0; i<stringFields.length; i++) {
			// volume.serieList.title
			stringBuilder.append("(");
			stringBuilder.append(stringFields[i]);
			stringBuilder.append(": (");
			for (int j=0; j<words.length; j++) {
				stringBuilder.append("+");
				stringBuilder.append( words[j]);
				stringBuilder.append(" ");
			}
			stringBuilder.append(")) ");
		}
		
		return stringBuilder;
	}

	/**
	 * 
	 * @param volumeFields
	 * @param words
	 * @return
	 */
	public static StringBuilder constructConditionOnVolumeFields(String[] volumeFields, String[] words) {
		StringBuilder stringBuilder = new StringBuilder();
		// We add conditions on volume 
		for (int i=0; i<words.length; i++) {
			// if word is not in volume format we skip
			if (!VolumeUtils.isVolumeFormat(words[i])) {
				continue;
			}
			// if word contains volLetExt we manage with a specific condition
			if (StringUtils.isAlphanumeric(words[i])) {
				stringBuilder.append("(+(volume.volNum:");
				stringBuilder.append(VolumeUtils.extractVolNum(words[i]));
				stringBuilder.append(") +(volume.volLetExt:");
				stringBuilder.append(VolumeUtils.extractVolLetExt(words[i]));
				stringBuilder.append("))");
			} else {
				stringBuilder.append("(volume.volNum:");
				stringBuilder.append(VolumeUtils.extractVolNum(words[i]));
				stringBuilder.append(") ");
			}
		}
		
		return stringBuilder;
	}

	/**
	 * 
	 * @param yearFields
	 * @param words
	 * @return
	 */
	public static StringBuilder constructConditionOnYearFields(String[] yearFields, String[] words) {
		StringBuilder stringBuilder = new StringBuilder();
		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			Integer number = NumberUtils.toInt(words[i]);
			
			if ((number<1200) || (number>1800)) {
				continue;
			}
			
			for (int j=0; j<yearFields.length; j++) {
				stringBuilder.append("(");
				stringBuilder.append(yearFields[j]);
				stringBuilder.append(": ");
				stringBuilder.append(words[i]);
				stringBuilder.append(") ");
			}
		}
		
		return stringBuilder;
	}
}
