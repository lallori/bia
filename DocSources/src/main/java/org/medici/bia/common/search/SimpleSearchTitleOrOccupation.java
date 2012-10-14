/*
 * SimpleSearchPlace.java
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
 * resulting executa
 * ble to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
package org.medici.bia.common.search;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.common.util.SimpleSearchUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchTitleOrOccupation extends SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7868927736853094973L;

	private Integer roleCatId;
	private String textSearch;

	/**
	 * 
	 */
	public SimpleSearchTitleOrOccupation() {
		super();
	}

	/**
	 * 
	 * @param query
	 */
	public SimpleSearchTitleOrOccupation(String textSearch) {
		super();
		
		if (!StringUtils.isEmpty(textSearch)) {
			setTextSearch(textSearch.toLowerCase());
		}
	}

	/**
	 * 
	 * @param textSearch
	 * @param roleCatId
	 */
	public SimpleSearchTitleOrOccupation(String textSearch, Integer roleCatId) {
		super();
		
		if (!StringUtils.isEmpty(textSearch)) {
			setTextSearch(textSearch.toLowerCase());
		}
		
		if (roleCatId != null) {
			setRoleCatId(roleCatId);
		}
	}

	/**
	 * @return the roleCatId
	 */
	public Integer getRoleCatId() {
		return roleCatId;
	}

	/**
	 * @return the textSearch
	 */
	public String getTextSearch() {
		return textSearch;
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromText(String text) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean empty() {
		if (StringUtils.isEmpty(textSearch) || (!ObjectUtils.toString(roleCatId).equals(""))) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * @param roleCatId the roleCatId to set
	 */
	public void setRoleCatId(Integer roleCatId) {
		this.roleCatId = roleCatId;
	}

	/**
	 * @param textSearch the textSearch to set
	 */
	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();
		
		if (StringUtils.isEmpty(textSearch)) {
			return booleanQuery;
		}

		String[] stringFields = new String[]{			
			"titleOcc",
		};
			
		String[] numericFields = new String[]{
		};
			
		String[] yearFields = new String[]{
		};

		String[] monthFields = new String[]{
		};
			
		String[] dayFields = new String[]{
		};

		String[] words = RegExUtils.splitPunctuationAndSpaceChars(textSearch);
			
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
		return "";
	}

}
