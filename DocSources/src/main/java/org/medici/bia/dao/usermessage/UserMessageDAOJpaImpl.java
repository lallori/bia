/*
 * UserMessageDAOJpaImpl.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.bia.dao.usermessage;

import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.UserMessageSearch;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserMessage;
import org.medici.bia.domain.UserMessage.RecipientStatus;
import org.medici.bia.domain.UserMessage.UserMessageCategory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * <b>UserMessageDAOJpaImpl</b> is a default implementation of
 * <b>UserMessageDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.UserMessage
 */
@Repository
public class UserMessageDAOJpaImpl extends JpaDao<Integer, UserMessage> implements UserMessageDAO {

	/**
	 * 
	 *  If a serializable class does not explicitly declare a serialVersionUID, 
	 *  then the serialization runtime will calculate a default serialVersionUID 
	 *  value for that class based on various aspects of the class, as described
	 *  in the Java(TM) Object Serialization Specification. However, it is 
	 *  strongly recommended that all serializable classes explicitly declare 
	 *  serialVersionUID values, since the default serialVersionUID computation 
	 *  is highly sensitive to class details that may vary depending on compiler
	 *  implementations, and can thus result in unexpected 
	 *  InvalidClassExceptions during deserialization. Therefore, to guarantee a
	 *   consistent serialVersionUID value across different java compiler 
	 *   implementations, a serializable class must declare an explicit 
	 *  serialVersionUID value. It is also strongly advised that explicit 
	 *  serialVersionUID declarations use the private modifier where possible, 
	 *  since such declarations apply only to the immediately declaring 
	 *  class--serialVersionUID fields are not useful as inherited members. 
	 */

	private static final long serialVersionUID = -2293109540221001045L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countMessageReceivedAfterDate(Date inputDate) throws PersistenceException {
        String queryString = "SELECT COUNT(messageId) FROM UserMessage WHERE recipient=:recipient and recipientStatus=:recipientStatus and messageSendedDate>=:inputDate ORDER BY messageSendedDate DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("recipient", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("recipientStatus", RecipientStatus.NOT_READ); 
        query.setParameter("inputDate", inputDate);

        return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long findNumberOfNewMessages() throws PersistenceException {
        String queryString = "SELECT COUNT(messageId) FROM UserMessage WHERE recipient=:recipient and recipientStatus=:recipientStatus and user.account=:account ORDER BY messageSendedDate DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("recipient", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("account", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("recipientStatus", RecipientStatus.NOT_READ); 

		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long findNumberOfUnreadedMessages() throws PersistenceException {
		return this.findNumberOfNewMessages();
	}

	private PaginationFilter generatePaginationFilterMYSQL(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) {
		if (userMessageSearch.getUserMessageCategory().equals(UserMessageCategory.INBOX)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("sender", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("subject", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("messageSendedDate", paginationFilter.getSortingDirection());
						break;
					default:
						break;
				}
			}
		} else if (userMessageSearch.getUserMessageCategory().equals(UserMessageCategory.OUTBOX)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("recipient", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("subject", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("messageSendedDate", paginationFilter.getSortingDirection());
						break;
					default:
						break;
				}
			}
		} else if (userMessageSearch.getUserMessageCategory().equals(UserMessageCategory.DRAFT)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("recipient", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("subject", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("messageSendedDate", paginationFilter.getSortingDirection());
						break;
					default:
						break;
				}
			}
		}

		return paginationFilter;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeMessages(User user, List<Integer> idElements) throws PersistenceException {
		StringBuilder query = new StringBuilder("DELETE FROM UserMessage WHERE user.account=:account AND (");
		for(int i = 0; i < idElements.size(); i++){
			query.append("messageId=" + idElements.get(i));
			if(i != idElements.size()-1){
				query.append(" OR ");
			}
		}
		Query toQuery = getEntityManager().createQuery(query.toString() + ")");
		toQuery.setParameter("account", user.getAccount());
		
		return toQuery.executeUpdate();
	}

	/**
	 * 
	 */
	public Page searchMYSQL(UserMessageSearch userMessageSearch, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + userMessageSearch.toJPAQuery();
	        
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}

		String objectsQuery = userMessageSearch.toJPAQuery();
		paginationFilter = generatePaginationFilterMYSQL(userMessageSearch, paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder(0);
		if (sortingCriterias.size() > 0) {
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		String jpql = objectsQuery + orderBySQL.toString();
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql );
		// We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)

		// We set search result on return method
		page.setList(query.getResultList());
		
		return page;
	}
}
