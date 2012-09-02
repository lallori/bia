/*
 * DigitizationDAOJpaImpl.java
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
package org.medici.bia.dao.digitization;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Digitization;
import org.springframework.stereotype.Repository;

/**
 * <b>DigitizationDAOJpaImpl</b> is a default implementation of
 * <b>DigitizationDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.Digitization
 */
@Repository
public class DigitizationDAOJpaImpl extends JpaDao<Integer, Digitization> implements DigitizationDAO {

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
	private static final long serialVersionUID = 3853467802112011017L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchActiveVolumes(Integer volNum, Integer volNumBetween, Boolean activated, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Digitization WHERE volNum >= " + volNum + " AND volNum <= " + volNumBetween);
		if(activated)
			toSearch = toSearch.concat(" AND active = 1");
		else
			toSearch = toSearch.concat(" AND active = 0");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
//		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchAllActiveVolumes(Boolean activated, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Digitization WHERE active = " + activated);
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
//		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
}
