/*
 * CoursePeopleDAOJpaImpl.java
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
package org.medici.bia.dao.coursepeople;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.CoursePeople;
import org.medici.bia.domain.UserRole;
import org.medici.bia.domain.UserAuthority.Authority;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Repository
public class CoursePeopleDAOJpaImpl extends JpaDao<Integer, CoursePeople> implements CoursePeopleDAO {

	private static final long serialVersionUID = 5892985394887550635L;
	
	public List<CoursePeople> getCoursePeople(Integer courseId, List<Authority> filteredAuth) throws PersistenceException {
		String jpql = "FROM CoursePeople WHERE course.courseId = :courseId";
		if (filteredAuth != null && filteredAuth.size() > 0) {
			jpql += " AND userRole.userAuthority.authority IN (:authorities)";
		}
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("courseId", courseId);
		if (filteredAuth != null && filteredAuth.size() > 0) {
			query.setParameter("authorities", filteredAuth);
		}
		
		return getResultList(query);
	}

	@Override
	public Page getCoursePeople(Integer courseId, List<Authority> filteredAuth, PaginationFilter paginationFilter)  throws PersistenceException {
		Page page = new Page(paginationFilter);
		Query query = null;
		String jpql = "FROM CoursePeople WHERE course.courseId = :courseId";
		if (filteredAuth != null && filteredAuth.size() > 0) {
			jpql += " AND userRole.userAuthority.authority IN (:authorities)";
		}
		
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + jpql;
			query = getEntityManager().createQuery(countQuery);
			query.setParameter("courseId", courseId);
			if (filteredAuth != null && filteredAuth.size() > 0) {
				query.setParameter("authorities", filteredAuth);
			}

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(PageUtils.calculeTotalPages(paginationFilter.getTotal(), paginationFilter.getElementsForPage()));
		}
		
		query = getEntityManager().createQuery(jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
		query.setParameter("courseId", courseId);
		if (filteredAuth != null && filteredAuth.size() > 0) {
			query.setParameter("authorities", filteredAuth);
		}
		
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());
		
		page.setList(query.getResultList());
		
		return page;
	}

	@Override
	public CoursePeople getCoursePerson(Integer courseId, String account) throws PersistenceException {
		String jpql = "FROM CoursePeople WHERE course.courseId = :courseId AND userRole.user.account = :account";
		Query query = getEntityManager().createQuery(jpql);
		
		query.setParameter("courseId", courseId);
		query.setParameter("account", account);
		
		return getFirst(query);
	}
	
	@Override
	public boolean isCoursePerson(Integer courseId, String account) throws PersistenceException {
		// FIXME: if teachers will be added to CoursePeople then remove the search query in UserRole
		List<Authority> auths = new ArrayList<Authority>();
		auths.add(Authority.TEACHERS);
		auths.add(Authority.ADMINISTRATORS);
		
		String jpql = "SELECT COUNT(user) FROM User AS user WHERE user IN ("
			+ " SELECT cp.userRole.user FROM CoursePeople AS cp WHERE cp.course.courseId = :courseId AND cp.userRole.user.account = :account"
			+ " ) OR user IN ("
			+ " SELECT ur.user FROM UserRole AS ur WHERE ur.user.account = :account AND ur.userAuthority.authority IN ( :auths )"
			+ " )";
		
		Query query = getEntityManager().createQuery(jpql);
		
		query.setParameter("courseId", courseId);
		query.setParameter("account", account);
		query.setParameter("auths", auths);
		
		return (Long)query.getSingleResult() > 0;
	}
	
	@Override
	public int removeAllCoursePeople(Integer courseId, List<Authority> filteredAuthorities) throws PersistenceException {
		// RR: the DELETE query cannot reference properties in the where clause, only primary key are possible or entity selection via subqueries
		String jpql = "DELETE FROM CoursePeople WHERE course.courseId = :courseId AND userRole IN (SELECT role FROM UserRole AS role WHERE userAuthority.authority IN (:authorities))";
		Query query = getEntityManager().createQuery(jpql);
		
		query.setParameter("courseId", courseId);
		query.setParameter("authorities", filteredAuthorities);
		
		return query.executeUpdate();
	}
	
	@Override
	public int removeCoursePeople(Integer courseId, List<String> accounts) throws PersistenceException {
		// RR: the DELETE query cannot reference properties in the where clause, only primary key are possible or entity selection via subqueries
		String jpql = "DELETE FROM CoursePeople WHERE course.courseId = :courseId AND userRole IN (SELECT role FROM UserRole AS role WHERE role.user.account IN (:accounts))";
		Query query = getEntityManager().createQuery(jpql);
		
		query.setParameter("courseId", courseId);
		query.setParameter("accounts", accounts);
		
		return query.executeUpdate();
	}
	
	@Override
	public int removeCoursePersonByUserRole(UserRole userRole) throws PersistenceException {
		if (userRole == null) {
			return 0;
		}
		Query query = getEntityManager().createQuery("DELETE FROM CoursePeople WHERE userRole = :userRole");
		
		query.setParameter("userRole", userRole);
		
		return query.executeUpdate();
	}

}
