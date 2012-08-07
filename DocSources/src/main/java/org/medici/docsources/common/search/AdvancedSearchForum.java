/*
 * AdvancedSearchForum.java
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

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.medici.docsources.command.search.AdvancedSearchCommand;
import org.medici.docsources.command.search.SimpleSearchCommand;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class AdvancedSearchForum extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2294835434784505729L;

	private String author;
	private List<Integer> forumsId;

	/**
	 * 
	 */
	public AdvancedSearchForum() {
		super();

		forumsId = new ArrayList<Integer>(0);
		author ="";
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the forumsId
	 */
	public List<Integer> getForumsId() {
		return forumsId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromAdvancedSearchCommand(AdvancedSearchCommand command) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromSimpleSearchCommand(SimpleSearchCommand command) {
	}

	@Override
	public Boolean isEmpty() {
		if ((forumsId.size()>0) || (author != null)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param forumsId the forumsId to set
	 */
	public void setForumsId(List<Integer> forumsId) {
		this.forumsId = forumsId;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM ForumPost WHERE ");

		if (forumsId.size()>0) {
			StringBuilder forumsQuery = new StringBuilder("(forum.forumId in (");
			for (int i=0; i<forumsId.size(); i++) {
				forumsQuery.append(forumsId.get(i));
				forumsQuery.append(",");
			}

			forumsQuery.delete(forumsQuery.length() - 1, forumsQuery.length());
			forumsQuery.append("))");
		}

		// person;
		if (author != null) {
			StringBuilder authorQuery = new StringBuilder("(userInformation.account LIKE '");
			authorQuery.append(author);
			authorQuery.append("')");
			if(jpaQuery.length() > 21){
				jpaQuery.append(" AND ");
			}
			jpaQuery.append(authorQuery);
		}

		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public Query toLuceneQuery() {
		BooleanQuery luceneQuery = new BooleanQuery();

		return luceneQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder();
		
		return stringBuilder.toString();
	}
}

