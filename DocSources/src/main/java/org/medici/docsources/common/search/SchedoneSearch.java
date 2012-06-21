/*
 * SchedoneSearch.java
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
import org.apache.lucene.search.Query;
import org.medici.docsources.common.util.RegExUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SchedoneSearch implements GenericSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private String alias; 
	
	private String searchType;
	private Integer volNum;
	private Integer volNumBetween;

	/**
	 * 
	 */
	public SchedoneSearch() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public SchedoneSearch(String text) {
		super();
		if (!StringUtils.isEmpty(text)) {
			setAlias(text.toLowerCase());
		}
	}
	
	public SchedoneSearch(String searchType, Integer volNum, Integer volNumBetween){
		super();
		if(!StringUtils.isEmpty(searchType)){
			setSearchType(searchType);
		}
		if(volNum != null){
			setVolNum(volNum);
		}
		if(volNumBetween != null){
			setVolNumBetween(volNumBetween);
		}
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * @return the volNum
	 */
	public Integer getVolNum() {
		return volNum;
	}

	/**
	 * @return the volNumBetween
	 */
	public Integer getVolNumBetween() {
		return volNumBetween;
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromText(String text) {
		if (!StringUtils.isEmpty(text)) {
			setAlias(text.toLowerCase());
		}
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * @param volNum the volNum to set
	 */
	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}

	/**
	 * @param volNumBetween the volNumBetween to set
	 */
	public void setVolNumBetween(Integer volNumBetween) {
		this.volNumBetween = volNumBetween;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuffer jpaQuery = new StringBuffer("FROM Schedone ");
		
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(getAlias());
		
		if (words.length >0) {
			jpaQuery.append(" WHERE ");
			// TODO : ...
		}else{
			if(searchType.equals("Exactly")){
				jpaQuery.append(" WHERE volNum=");
				jpaQuery.append(getVolNum());				
			}else if(searchType.equals("Between")){
				jpaQuery.append(" WHERE volNum>=");
				jpaQuery.append(getVolNum());
				jpaQuery.append(" AND volNum <=");
				jpaQuery.append(getVolNumBetween());
			}
		}		
		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		// NOT IMPLEMENTED
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (getAlias() != null)
			return getAlias();
		else
			return "";
	}
}