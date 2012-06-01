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
package org.medici.docsources.common.search;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.common.util.SimpleSearchUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchDocument extends SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private String alias; 

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
			setAlias(text.toLowerCase());
			setSimpleSearchPerimeter(simpleSearchPerimeter);
		}
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
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
		StringBuffer jpaQuery = new StringBuffer("FROM Document ");
		
		//MD: We need to re-convert the alias
		alias = alias.replace("\\\"", "\"");
		
		String[] words = StringUtils.split(alias, " ");
		
		if (words.length >0) {
			jpaQuery.append(" WHERE ");
		}
		
		if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT)){
			for(int i = 0; i < words.length; i++){
				jpaQuery.append("(synExtract.docExtract like '%");
				jpaQuery.append(words[i]);
				jpaQuery.append("%')");
				if(i < words.length-1){
					jpaQuery.append(" AND ");
				}
			}
		}else if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS)){
			for(int i = 0; i < words.length; i++){
				jpaQuery.append("(synExtract.synopsis like '%");
				jpaQuery.append(words[i]);
				jpaQuery.append("%')");
				if(i < words.length-1){
					jpaQuery.append(" AND ");
				}
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

		String[] stringFieldExtract = new String[]{
			"synExtract.docExtract"
		};
		
		String[] stringFieldSynopsis = new String[]{
			"synExtract.synopsis"
		};
		
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
		
		//E.g. (recipientPeople.mapNameLf: (+cosimo +medici +de) )
		Query stringQuery = null;
		if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.EXTRACT))
			stringQuery = SimpleSearchUtils.constructBooleanQueryOnStringFields(stringFieldExtract, words);
		else if(simpleSearchPerimeter.equals(SimpleSearchPerimeter.SYNOPSIS))
			stringQuery = SimpleSearchUtils.constructBooleanQueryOnStringFields(stringFieldSynopsis, words);

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
		if (alias != null)
			return getAlias();
		else
			return "";
	}
}