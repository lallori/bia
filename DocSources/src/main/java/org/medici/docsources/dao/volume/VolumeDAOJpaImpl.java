/*
 * VolumeDAOJpaImpl.java
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
package org.medici.docsources.dao.volume;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Volume;
import org.springframework.stereotype.Repository;

/**
 * <b>VolumeDAOJpaImpl</b> is a default implementation of <b>VolumeDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Repository
public class VolumeDAOJpaImpl extends JpaDao<Integer, Volume> implements VolumeDAO {

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
	private static final long serialVersionUID = -7671104408958929124L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findLastEntryVolume() throws PersistenceException {
         Query query = getEntityManager().createQuery("FROM Volume ORDER BY dateCreated DESC");
         query.setMaxResults(1);

         return (Volume) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolume(Integer volNum, String volLetExt) {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Volume> criteriaQuery = criteriaBuilder.createQuery(Volume.class);
		Root<Volume> root = criteriaQuery.from(Volume.class);
	
		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLetExt = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		criteriaQuery.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(volLetExt) ? 
					criteriaBuilder.isNull(root.get("volLetExt")) : 
					criteriaBuilder.equal(root.get("volLetExt"), parameterVolLetExt)
			)
		);

		// Set values in predicate's elements  
		TypedQuery<Volume> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			typedQuery.setParameter("volLetExt", volLetExt);

		return typedQuery.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page searchVolumes(String text, PaginationFilter paginationFilter) {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Volume> rootCount = criteriaQueryCount.from(Volume.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

			List<Predicate> predicates = new ArrayList<Predicate>();
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("title"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("subTitle1"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("subTitle2"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("orgNotes"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("recips"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("researcher"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("senders"), "%" + text + "%" ));

	        //If we omiss criteriaBuilder.or every predicate is in conjunction with others  
	        criteriaQueryCount.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));

			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			page.setTotal(new Long((Long)typedQueryCount.getSingleResult()));
		}

		CriteriaQuery<Volume> criteriaQuery = criteriaBuilder.createQuery(Volume.class);
		Root<Volume> root = criteriaQuery.from(Volume.class);
	
		//We need to duplicate predicates beacause they are link to Root element
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.like((Expression) root.get("serieList").get("title"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("serieList").get("subTitle1"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("serieList").get("subTitle2"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("orgNotes"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("recips"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("researcher"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("senders"), "%" + text + "%" ));

        //If we omiss criteriaBuilder.or every predicate is in conjunction with others  
        criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("summaryId")));

		// Set values in predicate's elements  
		TypedQuery<Volume> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(paginationFilter.getFirstRecord());
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());

		return page;
	}
}
