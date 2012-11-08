/*
 * AccessLogSearch.java
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

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.search.Query;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class AccessLogSearch implements Search {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8279978012929495622L;

	private Date fromDate;
	private Date toDate;
	private String account;
	private String action;

	private static Logger logger = Logger.getLogger(AccessLogSearch.class);
	
	/**
	 * 
	 */
	public AccessLogSearch() {
		super();

		account = null;
		fromDate = null;
		toDate = null;
	}

	/**
	 * 
	 * @param account
	 * @param fromDate
	 * @param toDate
	 */
	public AccessLogSearch(String account, String action, String fromDate, String toDate) {
		super();
		
		if (account != null) {
			setAccount(account);
		}
		if (action != null) {
			setAction(action);
		}
		if (fromDate != null) {
			// setFromDate(fromDate);
		}

		if(toDate != null) {
			// setToDate(toDate);
		}
	}
	/**
	 * @return the fromDate
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * 
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 
	 * @return
	 */
	public String getAction() {
		return action;
	}

	@Override
	public Boolean empty() {
		if ((StringUtils.isNotEmpty(account)) || 
			(StringUtils.isNotEmpty(action)) ||
			(fromDate != null) || 
			(toDate != null)
			) {
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		StringBuilder jpaQuery = new StringBuilder("FROM AccessLog ");

		if (!empty()) {
			jpaQuery.append(" WHERE ");

			if (StringUtils.isNotEmpty(account)) {
				if(jpaQuery.length() > 22){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append("(account like '%");
				jpaQuery.append(account);
				jpaQuery.append("%')");
			}

			if (StringUtils.isNotEmpty(action)) {
				if(jpaQuery.length() > 22){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append("(lower(action) like '%");
				jpaQuery.append(action);
				jpaQuery.append("%')");
			}

			if (fromDate != null) {
				if(jpaQuery.length() > 22){
					jpaQuery.append(" AND ");
				}
			}
	
			if (toDate != null) {
				if(jpaQuery.length() > 22){
					jpaQuery.append(" AND ");
				}
			}
		}
		
		return jpaQuery.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public Query toLuceneQuery() {
		// NOT IMPLEMENTED
		return null;
	}
}

