/*
 * DocumentDAOJpaImpl.java
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
package org.medici.docsources.dao.document;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Document;
import org.springframework.stereotype.Repository;

/**
 * <b>DocumentDAOJpaImpl</b> is a default implementation of <b>DocumentDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.Document
 */
@Repository
public class DocumentDAOJpaImpl extends JpaDao<Integer, Document> implements DocumentDAO {

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
	private static final long serialVersionUID = 270290031716661534L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocumentByVolumeId(Integer volumeId) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findLastEntryDocument() throws PersistenceException {
        Query query = getEntityManager().createQuery("FROM Document ORDER BY dateCreated DESC");
        query.setMaxResults(1);

        return (Document) query.getSingleResult();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Page searchDocuments(String text, PaginationFilter paginationFilter) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Document> rootCount = criteriaQueryCount.from(Document.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

/*			List<Predicate> predicates = new ArrayList<Predicate>();
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("title"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("subTitle1"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("subTitle2"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("orgNotes"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("recips"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("researcher"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("senders"), "%" + text + "%" ));

	        //If we omiss criteriaBuilder.or every predicate is in conjunction with others  
	        criteriaQueryCount.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
*/
			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			page.setTotal(new Long((Long)typedQueryCount.getSingleResult()));
		}

		CriteriaQuery<Document> criteriaQuery = criteriaBuilder.createQuery(Document.class);
	
/*		//We need to duplicate predicates beacause they are link to Root element
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
*/
		// Set values in predicate's elements  
		TypedQuery<Document> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(paginationFilter.getFirstRecord());
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());

		return page;
	}

}
