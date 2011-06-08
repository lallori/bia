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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.PaginationFilter.Order;
import org.medici.docsources.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.docsources.common.search.AdvancedSearch;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.common.search.SimpleSearch;
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

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page advancedSearchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		String luceneQuery = searchContainer.toLuceneQueryString();

		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		try {
			QueryParser queryParser = new QueryParser(Version.LUCENE_30, "entryId", fullTextSession.getSearchFactory().getAnalyzer("documentAnalyzer"));
	
			// We convert AdvancedSearchContainer to luceneQuery
			org.apache.lucene.search.Query query = queryParser.parse(luceneQuery);
	
			// We execute search
			org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, Document.class );
	
			// We set size of result.
			if (paginationFilter.getTotal() == null) {
				page.setTotal(new Long(fullTextQuery.getResultSize()));
			}
	
			// We set pagination  
			fullTextQuery.setFirstResult(paginationFilter.getFirstRecord());
			fullTextQuery.setMaxResults(paginationFilter.getLength());
	
			// We manage sorting (this manages sorting on multiple fields)
			List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
			if (sortingCriterias.size() > 0) {
				SortField[] sortFields = new SortField[sortingCriterias.size()];
				for (int i=0; i<sortingCriterias.size(); i++) {
					sortFields[i] = new SortField(sortingCriterias.get(i).getColumn(), sortingCriterias.get(i).getColumnType(), (sortingCriterias.get(i).getOrder().equals(Order.ASC) ? true : false));
				}
				fullTextQuery.setSort(new Sort(sortFields));
			}
			
			// We set search result on return method
			page.setList(fullTextQuery.list());
		} catch (ParseException parseException) {
			logger.error("Error parsing luceneQuery " + luceneQuery, parseException);
		}
		
		return page;
	}

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

	/**
	 * 
	 * @throws PersistenceException
	 */
	public void generateIndex() throws PersistenceException {
		try {
			EntityManager entityManager = getEntityManager();
			Session session = ((HibernateEntityManager) entityManager).getSession();
			session = session.getSessionFactory().openSession();
			FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(session);

			fullTextSession.createIndexer( Document.class )
			.batchSizeToLoadObjects( 10 )
			.cacheMode( CacheMode.IGNORE )
			.threadsToLoadObjects( 5 )
			.threadsForSubsequentFetching( 5 )
			.startAndWait();
		} catch (Throwable throwable) {
			logger.error(throwable);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocuments(String text, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Document.class).get();

		// We set search on fields .
		org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().onFields(
			"volume.serieList.title",
			"volume.serieList.subTitle1", 
			"volume.serieList.subTitle2",
			"senderPeople.mapNameLf", 
			"senderPeople.poLink.titleOccList.titleOcc",
			"senderPeople.altName.altName", 
			"senderPlace.placeName", 
			"senderPlace.placeNameFull",
			"recipientPeople.mapNameLf", 
			"recipientPeople.poLink.titleOccList.titleOcc",
			"recipientPeople.altName.altName",
			"recipientPlace.placeName", 
			"recipientPlace.placeNameFull"
		).matching(text + "*").createQuery();

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( luceneQuery, Document.class );

		// We set pagination  
		fullTextQuery.setFirstResult(paginationFilter.getFirstRecord());
		fullTextQuery.setMaxResults(paginationFilter.getLength());
		
		// We set search result on return method
		page.setList(fullTextQuery.list());

		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page simpleSearchDocuments(SimpleSearch simpleSearchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		String luceneQuery = simpleSearchContainer.toLuceneQueryString();

		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		try {
			QueryParser queryParser = new QueryParser(Version.LUCENE_30, "entryId", fullTextSession.getSearchFactory().getAnalyzer("documentAnalyzer"));
	
			// We convert AdvancedSearchContainer to luceneQuery
			org.apache.lucene.search.Query query = queryParser.parse(luceneQuery);
	
			// We execute search
			org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, Document.class );
	
			// We set size of result.
			if (paginationFilter.getTotal() == null) {
				page.setTotal(new Long(fullTextQuery.getResultSize()));
			}
	
			// We set pagination  
			fullTextQuery.setFirstResult(paginationFilter.getFirstRecord());
			fullTextQuery.setMaxResults(paginationFilter.getLength());
	
			// We manage sorting (this manages sorting on multiple fields)
			List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
			if (sortingCriterias.size() > 0) {
				SortField[] sortFields = new SortField[sortingCriterias.size()];
				for (int i=0; i<sortingCriterias.size(); i++) {
					sortFields[i] = new SortField(sortingCriterias.get(i).getColumn(), sortingCriterias.get(i).getColumnType(), (sortingCriterias.get(i).getOrder().equals(Order.ASC) ? true : false));
				}
				fullTextQuery.setSort(new Sort(sortFields));
			}
			
			// We set search result on return method
			page.setList(fullTextQuery.list());
		} catch (ParseException parseException) {
			logger.error("Error parsing luceneQuery " + luceneQuery, parseException);
		}
		
		return page;
	}

	/**
	 * 
	 */
	@Override
	public Page searchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		String luceneQuery = searchContainer.toLuceneQueryString();

		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		try {
			QueryParser queryParser = new QueryParser(Version.LUCENE_30, "entryId", fullTextSession.getSearchFactory().getAnalyzer("documentAnalyzer"));
	
			// We convert AdvancedSearchContainer to luceneQuery
			org.apache.lucene.search.Query query = queryParser.parse(luceneQuery);
	
			// We execute search
			org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, Document.class );
	
			// We set size of result.
			if (paginationFilter.getTotal() == null) {
				page.setTotal(new Long(fullTextQuery.getResultSize()));
			}
	
			// We set pagination  
			fullTextQuery.setFirstResult(paginationFilter.getFirstRecord());
			fullTextQuery.setMaxResults(paginationFilter.getLength());
	
			// We manage sorting (this manages sorting on multiple fields)
			List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
			if (sortingCriterias.size() > 0) {
				SortField[] sortFields = new SortField[sortingCriterias.size()];
				for (int i=0; i<sortingCriterias.size(); i++) {
					sortFields[i] = new SortField(sortingCriterias.get(i).getColumn(), sortingCriterias.get(i).getColumnType(), (sortingCriterias.get(i).getOrder().equals(Order.ASC) ? true : false));
				}
				fullTextQuery.setSort(new Sort(sortFields));
			}
			
			// We set search result on return method
			page.setList(fullTextQuery.list());
		} catch (ParseException parseException) {
			logger.error("Error parsing luceneQuery " + luceneQuery, parseException);
		}
		
		return page;
	}
}
