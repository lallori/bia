/*
 * SimpleSearchForumPost.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.medici.bia.common.util.RegExUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class SimpleSearchForumPost extends SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7370430153372138750L;

	private String alias; 
	private Integer topicId;

	/**
	 * 
	 */
	public SimpleSearchForumPost() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public SimpleSearchForumPost(String text) {
		super();

		if (!StringUtils.isEmpty(text)) {
			setAlias(text.toLowerCase());
		}
		topicId = null;
	}
	
	/**
	 * 
	 * @param text
	 * @param topicId
	 */
	public SimpleSearchForumPost(String text, Integer topicId) {
		super();

		if (!StringUtils.isEmpty(text)) {
			setAlias(text.toLowerCase());
		}
		this.topicId = topicId;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @return the topicId
	 */
	public Integer getTopicId() {
		return topicId;
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
	 * {@inheritDoc}
	 */
	@Override
	public Boolean isEmpty() {
		if (StringUtils.isEmpty(alias))
			return Boolean.TRUE;

		return Boolean.FALSE;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @param topicId the topicId to set
	 */
	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM ForumPost ");
		
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
		
		if (words.length >0) {
			jpaQuery.append(" WHERE ");
		}
		
		for(int i = 0; i < words.length; i++){
			jpaQuery.append("((text LIKE '%");
			jpaQuery.append(words[i]);
			jpaQuery.append("%') OR (subject LIKE '%");
			jpaQuery.append(words[i]);
			jpaQuery.append("%'))");
			if(i < words.length-1){
				jpaQuery.append(" AND ");
			}
		}
		
		if(topicId != null){
			jpaQuery.append(" AND (topic.topicId = ");
			jpaQuery.append(topicId);
			jpaQuery.append(")");
		}
		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Query toLuceneQuery() {
		//NOT IMPLEMENTED LUCENE QUERY

		return new BooleanQuery();
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