/*
 * UserMessageSearch.java
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
import org.medici.docsources.domain.UserMessage.UserMessageCategory;
import org.medici.docsources.security.DocSourcesLdapUserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class UserMessageSearch implements GenericSearch {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1526279104072787935L;
	
	private UserMessageCategory userMessageCategory; 

	/**
	 * 
	 */
	public UserMessageSearch() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public UserMessageSearch(UserMessageCategory userMessageCategory) {
		super();
		if (userMessageCategory != null) {
			setUserMessageCategory(userMessageCategory);
		}
	}

	/**
	 * @return the userMessageCategory
	 */
	public UserMessageCategory getUserMessageCategory() {
		return userMessageCategory;
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromText(String text) {
		if (!StringUtils.isEmpty(text)) {
			try {
				setUserMessageCategory(UserMessageCategory.valueOf(text)); 
			} catch (Exception exception) {
				setUserMessageCategory(null);
			}
		}
	}


	/**
	 * @param userMessageCategory the userMessageCategory to set
	 */
	public void setUserMessageCategory(UserMessageCategory userMessageCategory) {
		this.userMessageCategory = userMessageCategory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		String account = ((DocSourcesLdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
		StringBuffer jpaQuery = new StringBuffer("FROM UserMessage ");
		
		if (getUserMessageCategory() != null) {
			jpaQuery.append(" WHERE ");

			switch (getUserMessageCategory()) {
				case DRAFT:
					jpaQuery.append(" sender = '");
					jpaQuery.append(account);
					jpaQuery.append("' and recipientStatus ='");
					jpaQuery.append(getUserMessageCategory());
					jpaQuery.append("'");
				break;
				
			case INBOX:
				jpaQuery.append("recipient = '");
				jpaQuery.append(account);
				jpaQuery.append("'");
				break;
			
			case OUTBOX:
				jpaQuery.append("sender = '");
				jpaQuery.append(account);
				jpaQuery.append("'");
				break;

			default:
				break;
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
		if (getUserMessageCategory() != null)
			return getUserMessageCategory().toString();
		else
			return "";
	}
}