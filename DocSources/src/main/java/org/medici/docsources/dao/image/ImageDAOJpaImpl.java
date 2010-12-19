/*
 * ImageDAOJpaImpl.java
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
package org.medici.docsources.dao.image;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Image;
import org.springframework.stereotype.Repository;

/**
 * <b>ImageDAOJpaImpl</b> is a default implementation of
 * <b>ImageDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.HistoryLog
 * @see org.medici.docsources.audit.HistoryLogAction
 */
@Repository
public class ImageDAOJpaImpl extends JpaDao<Integer, Image> implements ImageDAO {

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
	private static final long serialVersionUID = -8769762056162920397L;

	@Override
	public List<Image> findImages(Integer volNum, String volLetExt) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
		Root<Image> root = criteriaQuery.from(Image.class);
	
		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		criteriaQuery.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(volLetExt) ? 
					criteriaBuilder.isNull(root.get("volLetExt")) : 
					criteriaBuilder.equal(root.get("volLetExt"), parameterVolLeText)
			)
		);

		// Set values in predicate's elements  
		TypedQuery<Image> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			typedQuery.setParameter("volLetExt", volLetExt);

		return typedQuery.getResultList();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page findImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Image> rootCount = criteriaQueryCount.from(Image.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

			// Define predicate's elements
			ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
			ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

			criteriaQueryCount.where(
				criteriaBuilder.and(
					criteriaBuilder.equal(rootCount.get("volNum"), parameterVolNum),
					StringUtils.isEmpty(volLetExt) ? 
						criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
						criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
				)
			);

			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			typedQueryCount.setParameter("volNum", volNum);
			if (!StringUtils.isEmpty(volLetExt))
				typedQueryCount.setParameter("volLetExt", volLetExt);
			page.setTotal(new Long((Long)typedQueryCount.getSingleResult()));
		}

		CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
		Root<Image> root = criteriaQuery.from(Image.class);
	
		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		//We need to duplicate predicates beacause they are link to Root element
		criteriaQuery.where(
				criteriaBuilder.and(
					criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
					StringUtils.isEmpty(volLetExt) ? 
						criteriaBuilder.isNull(root.get("volLetExt")) : 
						criteriaBuilder.equal(root.get("volLetExt"), parameterVolLeText)
				)
			);

		// Set values in predicate's elements  
		TypedQuery<Image> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			typedQuery.setParameter("volLetExt", volLetExt);
		
		typedQuery.setFirstResult(paginationFilter.getFirstRecord());
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());

		return page;
	}
}
