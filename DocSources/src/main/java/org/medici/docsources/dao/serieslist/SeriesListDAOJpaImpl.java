/*
 * SeriesListDAOJpaImpl.java
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
package org.medici.docsources.dao.serieslist;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.search.FullTextQuery;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.SerieList;
import org.springframework.stereotype.Repository;

/**
 * <b>SeriesListDAOJpaImpl</b> is a default implementation of <b>SeriesListDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Repository
public class SeriesListDAOJpaImpl extends JpaDao<Integer, SerieList> implements SeriesListDAO {
	/**
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
	private static final long serialVersionUID = 1677483115463135112L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SerieList> findSeries(String alias) throws PersistenceException {
		String[] searchFields = new String[]{"title", "subTitle1", "subTitle2"};
		String[] outputFields = new String[]{"seriesRefNum", "title", "subTitle1", "subTitle2"};

		FullTextQuery fullTextQuery = buildFullTextQuery(getEntityManager(), searchFields, alias, outputFields, SerieList.class);
		List<SerieList> listSenders = executeFullTextQuery(fullTextQuery, outputFields, SerieList.class);

		return listSenders;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({"unchecked", "rawtypes" })
	@Override
	public List<SerieList> findSeriesJpaImpl(String alias) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SerieList> criteriaQuery = criteriaBuilder.createQuery(SerieList.class);
		Root root = criteriaQuery.from(SerieList.class);

		//Construct literal and predicates
		Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal("%" + alias + "%"));
		Predicate predicateTitle = criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), literal);
		Predicate predicateSubTitle1 = criteriaBuilder.like(criteriaBuilder.upper(root.get("subTitle1")), literal);
		Predicate predicateSubTitle2 = criteriaBuilder.like(criteriaBuilder.upper(root.get("subTitle2")), literal);
		
		//Add where clause
		criteriaQuery.where(criteriaBuilder.or(predicateTitle,predicateSubTitle1, predicateSubTitle2)); 

		// create query using criteria   
		TypedQuery<SerieList> typedQuery = getEntityManager().createQuery(criteriaQuery);

		return typedQuery.getResultList();	
	}
}
