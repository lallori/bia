/*
 * UserSearch.java
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

import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;
import org.joda.time.DateTime;
import org.medici.bia.common.util.DateUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class UserSearch implements GenericSearch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1526279104072787935L;
	
	private Boolean online;

	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 
	 */
	public UserSearch() {
		super();
		
		setOnline(Boolean.FALSE);
	}


	/**
	 * @param online the online to set
	 */
	public void setOnline(Boolean online) {
		this.online = online;
	}


	/**
	 * @return the online
	 */
	public Boolean getOnline() {
		return online;
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
		if (online == null) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM User ");
		
		if (!empty()) {
			jpaQuery.append("WHERE ");

			if (getOnline() != null) {
				DateTime dateTime = new DateTime(System.currentTimeMillis());
				dateTime = dateTime.minusMinutes(5);
				
				jpaQuery.append(" (dateAndTime > '");
				jpaQuery.append(DateUtils.getMYSQLDateTime(dateTime));
				jpaQuery.append("')");
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
		StringBuilder stringBuilder = new StringBuilder();
		
		if (empty()) {
			return "";
		}
		
		stringBuilder.append("[");
		if (getOnline() != null) {
			stringBuilder.append("online=");
			stringBuilder.append(getOnline());
		}
		stringBuilder.append("]");
	
		return stringBuilder.toString();
	}
}