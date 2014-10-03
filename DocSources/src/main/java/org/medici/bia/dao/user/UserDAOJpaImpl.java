/*
 * UserRoleDAOJpaImpl.java
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
package org.medici.bia.dao.user;

import java.util.List;
import java.util.Set;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.DateUtils;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.exception.TooManyUsersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Repository
@Transactional(readOnly=true)
public class UserDAOJpaImpl extends JpaDao<String, User> implements UserDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7451782750559737877L;

	private final Logger logger = Logger.getLogger(this.getClass());

	
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countMembersForum() {
		Query query = getEntityManager().createQuery("SELECT COUNT(account) FROM User");
		query.setMaxResults(1);
		return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countWhoIsOnlineForum() {
		DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(5);
		Query query = getEntityManager().createQuery("SELECT COUNT(DISTINCT user.account) FROM User user, AccessLog accessLog WHERE user.account = accessLog.account AND user.forumJoinedDate IS NOT NULL AND (accessLog.dateAndTime > '"+ DateUtils.getMYSQLDateTime(dateTime) + "')");
		query.setMaxResults(1);
		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page findForumMembers(String letter, PaginationFilter paginationFilter) {
		String queryString;
		if(letter.equals("All")){
			queryString = "FROM User WHERE forumJoinedDate IS NOT NULL ORDER BY account";
		}else{
			queryString = "FROM User WHERE forumJoinedDate IS NOT NULL AND account LIKE '" + letter + "%' ORDER BY account";
			
		}
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		Query query = null;
			
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + queryString;
			
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}
		
		query = getEntityManager().createQuery(queryString);
		
		// We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());
		// We manage sorting (this manages sorting on multiple fields)
		List<User> list = (List<User>) query.getResultList();
		// We set search result on return method
		page.setList(list);
			
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page findForumMembersByText(String text, PaginationFilter paginationFilter) {
		String queryString = "FROM User WHERE forumJoinedDate IS NOT NULL AND account LIKE '%" + text + "%' ORDER BY account";
				
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		Query query = null;
			
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + queryString;
			
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}
		
		query = getEntityManager().createQuery(queryString);
				
		// We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());
		// We manage sorting (this manages sorting on multiple fields)
		List<User> list = (List<User>) query.getResultList();
		// We set search result on return method
		page.setList(list);
			
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User findUser(String account) {
		try {
			Query query = getEntityManager().createQuery("FROM User WHERE account=:account");
			query.setParameter("account", account);
			
			query.setMaxResults(1);
			return (User) query.getSingleResult();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}

	/**
	 * 
	 */
	public User findUser(User user) throws TooManyUsersException {
		try {
			StringBuilder conditionBuffer = new StringBuilder("");
			if (user.getAccount() != null) {
				conditionBuffer.append("account=:account");
			}
			if (user.getInitials() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("initials=:initials");
			}
			if (user.getFirstName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("firstName=:firstName");
			}
			if (user.getLastName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("lastName=:lastName");
			}
			if (user.getOrganization() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("organization=:organization");
			}
			if (user.getMail() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("mail=:mail");
			}
			
			Query query = getEntityManager().createQuery("FROM User WHERE " + conditionBuffer.toString());
			if (user.getAccount() != null) {
				query.setParameter("account", user.getAccount());
			}
			if (user.getInitials() != null) {
				query.setParameter("initials", user.getInitials());
			}
			if (user.getFirstName() != null) {
				query.setParameter("firstName", user.getFirstName());
			}
			if (user.getLastName() != null) {
				query.setParameter("lastName", user.getLastName());
			}
			if (user.getOrganization() != null) {
				query.setParameter("organization", user.getOrganization());
			}
			if (user.getMail() != null) {
				query.setParameter("mail", user.getMail());
			}
			
			query.setMaxResults(1);
			return (User) query.getSingleResult();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}
	
	/**
	 * 
	 * @param userRole
	 * @return
	 */
	public UserRole findUserRole(UserRole userRole) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsers(String text) {
		try {
			StringBuilder toQuery = new StringBuilder("FROM User WHERE account LIKE '%");
			toQuery.append(text);
			toQuery.append("%' OR firstName LIKE '%");
			toQuery.append(text);
			toQuery.append("%' OR lastName LIKE '%");
			toQuery.append(text);
			toQuery.append("%'");
			Query query = getEntityManager().createQuery(toQuery.toString());
			
			return (List<User>) query.getResultList();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}

	/**
	 * Search for all users matching the supplied filter on following fields : -
	 * account - first name - last name - organization - mail address
	 * 
	 * The Attributes in each SearchResult is supplied to the specified
	 * AttributesMapper.
	 * 
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<User> findUsers(User user) {
		try {
			StringBuilder conditionBuffer = new StringBuilder("");
			if (user.getAccount() != null) {
				conditionBuffer.append("account=:account");
			}
			if (user.getInitials() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("initials=:initials");
			}
			if (user.getFirstName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("firstName=:firstName");
			}
			if (user.getLastName() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("lastName=:lastName");
			}
			if (user.getOrganization() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("organization:organization");
			}
			if (user.getMail() != null) {
				if (conditionBuffer.length() >0) {
					conditionBuffer.append(" AND ");
				}
				conditionBuffer.append("mail=:mail");
			}
			
			Query query = getEntityManager().createQuery("FROM User WHERE " + conditionBuffer.toString());
			if (user.getAccount() != null) {
				query.setParameter("account", user.getAccount());
			}
			if (user.getInitials() != null) {
				query.setParameter("initials", user.getInitials());
			}
			if (user.getFirstName() != null) {
				query.setParameter("firstName", user.getFirstName());
			}
			if (user.getLastName() != null) {
				query.setParameter("lastName", user.getLastName());
			}
			if (user.getOrganization() != null) {
				query.setParameter("organization", user.getOrganization());
			}
			if (user.getMail() != null) {
				query.setParameter("mail", user.getMail());
			}
			
			return query.getResultList();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> findUsers(UserAuthority userAuthority) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT u.user FROM UserRole u WHERE u.userAuthority=:userAuthority");
		query.setParameter("userAuthority", userAuthority);
		
		return getResultList(query);
	}

	/**
	 * 
	 */
	@Override
	public Page findUsers(User user, Integer pageNumber, Integer pageSize) {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findUsers(User user, Set<Authority> authorities, boolean searchAuthorities, PaginationFilter paginationFilter) {
		boolean authFilter = authorities != null && authorities.size() > 0;
		Page page = new Page(paginationFilter);
		Query query = null;
		String jpql = "FROM User ";
		String condition = getConditionOnUser(authFilter ? "user" : null, user);
		
		if (authFilter) {
			jpql += "AS user, UserRole AS role ";
		}
		
		if (!StringUtils.equals(condition, "")) {
			jpql = jpql + "WHERE " + condition;
		}
		
		if (authFilter) {
			if (!jpql.contains("WHERE")) {
				jpql += "WHERE ";
			} else {
				jpql += " AND ";
			}
			jpql += "user = role.user AND ";
			if (searchAuthorities) {
				jpql += "role.userAuthority.authority IN (:authorities)";
			} else {
				jpql += "NOT EXISTS (SELECT r FROM UserRole AS r WHERE r.user = user AND r.userAuthority.authority IN (:authorities))";
			}
		}
		
		if (paginationFilter.getTotal() == null){
			String countQuery = (authFilter ? "SELECT COUNT(user) " : "SELECT COUNT(*) ") + jpql;
			query = getEntityManager().createQuery(countQuery);
			if (authFilter) {
				query.setParameter("authorities", authorities);
			}
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery((authFilter ? "SELECT DISTINCT user " : "") + jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
		if (authFilter) {
			query.setParameter("authorities", authorities);
		}
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findUsers(User user, PaginationFilter paginationFilter) {
		Page page = new Page(paginationFilter);
		Query query = null;
		String jpql = "FROM User ";
		String condition = getConditionOnUser(user);
		
		if (!StringUtils.equals(condition, "")) {
			jpql = jpql + "WHERE " + condition;
		}
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + jpql;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	/**
	 * 
	 */
	protected PaginationFilter generatePaginationFilterMYSQL(PaginationFilter paginationFilter) {
		if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
			switch (paginationFilter.getSortingColumn()) {
				case 0:
					paginationFilter.addSortingCriteria("firstName", paginationFilter.getSortingDirection());
					paginationFilter.addSortingCriteria("lastName", paginationFilter.getSortingDirection());
					break;
				case 1:
					paginationFilter.addSortingCriteria("mail", paginationFilter.getSortingDirection());
					break;
				case 2:
					paginationFilter.addSortingCriteria("city", paginationFilter.getSortingDirection());
					break;
				case 3:
					paginationFilter.addSortingCriteria("country", paginationFilter.getSortingDirection());
					break;
				case 4:
					paginationFilter.addSortingCriteria("lastLoginDate", paginationFilter.getSortingDirection());
					break;
				default:
					paginationFilter.addSortingCriteria("account", "asc");
					break;
			}
		}

		return paginationFilter;
	}
	
	private String getConditionOnUser(User user) {
		return getConditionOnUser(null, user);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	private String getConditionOnUser(String prefix, User user) {
		if (prefix == null) {
			prefix = "";
		} else if (!prefix.endsWith(".")) {
			prefix += ".";
		}
		if (user ==null) {
			return "";
		}

		StringBuilder conditionBuffer = new StringBuilder("");
		if (user.getAccount() != null) {
			conditionBuffer.append(prefix + "account like '%");
			conditionBuffer.append(user.getAccount());
			conditionBuffer.append("%'");
		}
		if (user.getInitials() != null) {
			if (conditionBuffer.length() >0) {
				conditionBuffer.append(" AND ");
			}
			conditionBuffer.append(prefix + "initials like '%");
			conditionBuffer.append(user.getInitials());
			conditionBuffer.append("%'");
		}
		if (user.getFirstName() != null) {
			if (conditionBuffer.length() >0) {
				conditionBuffer.append(" AND ");
			}
			conditionBuffer.append("(" + prefix + "firstName like '%");
			conditionBuffer.append(user.getFirstName());
			conditionBuffer.append("%' or "+ prefix + "lastName like '%");
			conditionBuffer.append(user.getFirstName());
			conditionBuffer.append("%')");
		}
		if (user.getLastName() != null) {
			if (conditionBuffer.length() >0) {
				conditionBuffer.append(" AND ");
			}
			conditionBuffer.append(prefix + "lastName like '%");
			conditionBuffer.append(user.getLastName());
			conditionBuffer.append("%'");
		}
		if (user.getOrganization() != null) {
			if (conditionBuffer.length() >0) {
				conditionBuffer.append(" AND ");
			}
			conditionBuffer.append(prefix + "organization like '%");
			conditionBuffer.append(user.getOrganization());
			conditionBuffer.append("%'");
		}
		if (user.getMail() != null) {
			if (conditionBuffer.length() >0) {
				conditionBuffer.append(" AND ");
			}
			conditionBuffer.append(prefix + "mail like '%");
			conditionBuffer.append(user.getMail());
			conditionBuffer.append("%'");
		}
		
		return conditionBuffer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getNewestMember() {
		try {
			Query query = getEntityManager().createQuery("FROM User ORDER BY forumJoinedDate DESC");

			query.setMaxResults(1);
			return (User) query.getSingleResult();
		} catch (PersistenceException persistenceException) {
			
			return null;
		}
	}

	/**
	 * @return the passwordEncoder
	 */
	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public User merge(User user) throws PersistenceException {
		if (user != null) {
			return getEntityManager().merge(user);
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	public void persist(User user) throws PersistenceException {
		if (user != null) {
			getEntityManager().persist(user);
		}
	}

	/**
	 * 
	 * @param account
	 * @param userRole
	 */
	public void persistUserRoles(String account, List<UserRole> userRoles) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeAllUserRoles(String account) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeUserRoles(String account, List<UserRole> userRoles) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchMYSQL(org.medici.bia.common.search.Search searchContainer, PaginationFilter paginationFilter) {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + searchContainer.toJPAQuery();
	        
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}

		String objectsQuery = searchContainer.toJPAQuery();

		// We manage sorting (this manages sorting on multiple fields)
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		String jpql = objectsQuery + getOrderByQuery(paginationFilter.getSortingCriterias());
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql );
		// We set pagination  
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());

		// We set search result on return method
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchWhoIsOnline(PaginationFilter paginationFilter) {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// Number of minutes to check last update for people online
		//DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(5);
		DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(30);
		

		String baseQuery  = " FROM User user, AccessLog accessLog WHERE user.account = accessLog.account and (accessLog.dateAndTime > '"+ DateUtils.getMYSQLDateTime(dateTime) + "')";
	
		Query query = null;
		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(DISTINCT user) " + baseQuery;
	        
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}

		// We manage sorting (this manages sorting on multiple fields)
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		String jpql = "SELECT DISTINCT(user) " +baseQuery + getOrderByQuery(paginationFilter.getSortingCriterias(), "user");
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql );
		// We set pagination  
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());

		// We set search result on return method
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * @param passwordEncoder the passwordEncoder to set
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE User SET account=:newAccount WHERE account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameApprovedBy(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE User SET approvedBy.account=:newAccount WHERE approvedBy.account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> whoIsOnlineForum() {
		DateTime dateTime = (new DateTime(System.currentTimeMillis())).minusMinutes(30);
		Query query = getEntityManager().createQuery("SELECT DISTINCT user.account FROM User user, AccessLog accessLog WHERE user.forumJoinedDate IS NOT NULL AND user.account = accessLog.account AND (accessLog.dateAndTime > '"+ DateUtils.getMYSQLDateTime(dateTime) + "') AND accessLog.action LIKE '%community%'");
		return query.getResultList();
	}
}
