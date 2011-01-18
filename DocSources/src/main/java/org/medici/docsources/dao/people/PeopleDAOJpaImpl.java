/*
 * PeopleDAOJpaImpl.java
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
package org.medici.docsources.dao.people;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.People;
import org.springframework.stereotype.Repository;

/**
 * <b>PeopleDAOJpaImpl</b> is a default implementation of <b>PeopleDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.People
 */
@Repository
public class PeopleDAOJpaImpl extends JpaDao<Integer, People> implements PeopleDAO {
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
	private static final long serialVersionUID = -2964902298903431093L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findLastEntryPerson() throws PersistenceException {
        Query query = getEntityManager().createQuery("FROM People ORDER BY dateCreated DESC");
        query.setMaxResults(1);

        return (People) query.getSingleResult();
	}
	
	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public Page searchPeople(String text, PaginationFilter paginationFilter) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<People> rootCount = criteriaQueryCount.from(People.class);
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

		CriteriaQuery<People> criteriaQuery = criteriaBuilder.createQuery(People.class);
		Root<People> root = criteriaQuery.from(People.class);
	
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
		TypedQuery<People> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(paginationFilter.getFirstRecord());
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchPersonLinkableToDocument(List<Integer> peopleIdList, String[] queries) throws PersistenceException {
		String[] searchFields = new String[]{"first", "last"};
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "bYear", "dYear"};
		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());
		org.hibernate.search.FullTextQuery fullTextQuery = null;

/*		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("first", alias.trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
		booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("last", alias.trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
		
		for (int i=0; i<peopleIdList.size(); i++) {
			booleanQuery.add(new BooleanClause(new TermQuery(new Term("personId", peopleIdList.get(i).toString())), BooleanClause.Occur.MUST_NOT));
		}
*/
		if (queries.length == 1) {
			
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
			
			for (int i=0; i<peopleIdList.size(); i++) {
				booleanQuery.add(new BooleanClause(new TermQuery(new Term("personId", peopleIdList.get(i).toString())), BooleanClause.Occur.MUST_NOT));
			}
			fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, People.class);
		} else if (queries.length == 2) {
			// Condition is : (first:query[0]* AND last:query[1]*) OR (first:query[1]* AND last:query[0]*)
			BooleanQuery booleanQuery1 = new BooleanQuery();
			booleanQuery1.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			booleanQuery1.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[1].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			
			BooleanQuery booleanQuery2 = new BooleanQuery();
			booleanQuery2.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[1].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			booleanQuery2.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(booleanQuery1, BooleanClause.Occur.SHOULD);
			booleanQuery.add(booleanQuery2, BooleanClause.Occur.SHOULD);
			for (int i=0; i<peopleIdList.size(); i++) {
				booleanQuery.add(new BooleanClause(new TermQuery(new Term("personId", peopleIdList.get(i).toString())), BooleanClause.Occur.MUST_NOT));
			}
			
			fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, People.class);
		} else {
			fullTextQuery = buildFullTextQuery(getEntityManager(), searchFields, queries, outputFields, People.class);
		}

		fullTextQuery.setProjection(outputFields);

		List<People> listPeopleLinkable = executeFullTextQuery(fullTextQuery, outputFields, People.class);

		return listPeopleLinkable; 
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchRecipientsPeople(String[] queries) throws PersistenceException {
		String[] searchFields = new String[]{"first", "last"};
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "bYear", "dYear"};

		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());
		org.hibernate.search.FullTextQuery fullTextQuery = null;

		if (queries.length == 1) {
			
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
			
			fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, People.class);
		} else if (queries.length == 2) {
			// Condition is : (first:query[0]* AND last:query[1]*) OR (first:query[1]* AND last:query[0]*)
			BooleanQuery booleanQuery1 = new BooleanQuery();
			booleanQuery1.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			booleanQuery1.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[1].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			
			BooleanQuery booleanQuery2 = new BooleanQuery();
			booleanQuery2.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[1].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			booleanQuery2.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(booleanQuery1, BooleanClause.Occur.SHOULD);
			booleanQuery.add(booleanQuery2, BooleanClause.Occur.SHOULD);
			
			fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, People.class);
		} else {
			fullTextQuery = buildFullTextQuery(getEntityManager(), searchFields, queries, outputFields, People.class);
		}

		fullTextQuery.setProjection(outputFields);
		List<People> listRecipients = executeFullTextQuery(fullTextQuery, outputFields, People.class);

		return listRecipients;
	}

	/**
	 * This method search senders using jpa implementation.
	 * 
	 * @param alias
	 * @return
	 * @throws PersistenceException
	 * @deprecated
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<People> searchSendersJpaImpl(String alias) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<People> criteriaQuery = criteriaBuilder.createQuery(People.class);
		Root root = criteriaQuery.from(People.class);
	
		//Construct literal and predicates
		Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal("%" + alias + "%"));
		Predicate predicateFirst = criteriaBuilder.like(criteriaBuilder.upper(root.get("first")), literal);
		Predicate predicateLast = criteriaBuilder.like(criteriaBuilder.upper(root.get("last")), literal);
		
		//Add where clause
		criteriaQuery.where(criteriaBuilder.or(predicateFirst,predicateLast)); 
	
		// create query using criteria   
		TypedQuery<People> typedQuery = getEntityManager().createQuery(criteriaQuery);
	
		return typedQuery.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchSendersPeople(String[] queries) throws PersistenceException {
		String[] searchFields = new String[]{"first", "last"};
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "bYear", "dYear"};
		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());
		org.hibernate.search.FullTextQuery fullTextQuery = null;

		// If user specify only one term, we search it on first and last
		if (queries.length == 1) {
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.SHOULD)); 
			
			fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, People.class);
		} else if (queries.length == 2) {
			// Condition is : (first:query[0]* AND last:query[1]*) OR (first:query[1]* AND last:query[0]*)
			BooleanQuery booleanQuery1 = new BooleanQuery();
			booleanQuery1.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			booleanQuery1.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[1].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			
			BooleanQuery booleanQuery2 = new BooleanQuery();
			booleanQuery2.add(new BooleanClause(new WildcardQuery(new Term(searchFields[0], queries[1].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 
			booleanQuery2.add(new BooleanClause(new WildcardQuery(new Term(searchFields[1], queries[0].trim().toLowerCase() + "*")), BooleanClause.Occur.MUST)); 

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(booleanQuery1, BooleanClause.Occur.SHOULD);
			booleanQuery.add(booleanQuery2, BooleanClause.Occur.SHOULD);
			
			fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, People.class);
		} else {
			fullTextQuery = buildFullTextQuery(getEntityManager(), searchFields, queries, outputFields, People.class);
		}

		fullTextQuery.setProjection(outputFields);
		List<People> listSenders = executeFullTextQuery(fullTextQuery, outputFields, People.class);

		return listSenders;
	}

}
