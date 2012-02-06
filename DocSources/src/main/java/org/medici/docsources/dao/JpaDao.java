/*
 * JpaDao.java
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
package org.medici.docsources.dao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.CacheMode;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.PaginationFilter.Order;
import org.medici.docsources.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.docsources.domain.People;
import org.medici.docsources.domain.SearchFilter.SearchType;
import org.springframework.stereotype.Repository;

/**
 * @param <K>
 * @param <E>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Repository
public abstract class JpaDao<K, E> implements Dao<K, E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4380540983397163140L;

	protected Class<E> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	/**
	 * This method construct an hibernate search FullTextQuery.
	 * 
	 * @param entityManager The Entity Manager getted from persistent context. It's used to construct FullTextSession
	 * @param searchFields List of entity bean's properties where to search alias
	 * @param alias Text to search in searchFields
	 * @param outputFields List of entity bean's properties where to search alias
	 * @param searchedEntity Entity to search
	 * @return
	 * @throws PersistenceException
	 * 
	 * @deprecated since 0.0.9-aplha
	 */
	@SuppressWarnings("rawtypes")
	protected org.hibernate.search.FullTextQuery buildFullTextQuery(EntityManager entityManager, String[] searchFields, String alias, String[] outputFields, Class<?> searchedEntity) throws PersistenceException { 
		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)entityManager).getSession());

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity( searchedEntity ).get();
		BooleanJunction booleanJunction = queryBuilder.bool();
		org.apache.lucene.search.Query luceneQuery = null;
		
	    for (int i=0; i<searchFields.length; i++) {
	    	booleanJunction.should(queryBuilder.keyword().wildcard().onField(searchFields[i]).matching(alias.trim().toLowerCase() + "*").createQuery());
	    }
	    
	    luceneQuery = booleanJunction.createQuery();
	    
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, searchedEntity);
		fullTextQuery.setProjection(outputFields);

		return fullTextQuery; 
	}

	/**
	 * This method construct an hibernate search FullTextQuery.
	 * 
	 * @param entityManager The Entity Manager getted from persistent context. It's used to construct FullTextSession
	 * @param searchFields List of entity bean's properties where to search alias
	 * @param alias Text to search in searchFields
	 * @param outputFields List of entity bean's properties where to search alias
	 * @param searchedEntity Entity to search
	 * @return
	 * @throws PersistenceException
	 * 
	 * @deprecated since 0.0.9-aplha
	 */
	@SuppressWarnings("rawtypes")
	protected org.hibernate.search.FullTextQuery buildFullTextQuery(EntityManager entityManager, String[] searchFields, String[] queries, String[] outputFields, Class<?> searchedEntity) throws PersistenceException { 
		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)entityManager).getSession());

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity( searchedEntity ).get();
		BooleanJunction booleanJunction = queryBuilder.bool();
		org.apache.lucene.search.Query luceneQuery = null;
		
	    for (int i=0; i<searchFields.length; i++) {
	    	for (int j=0; j<queries.length; j++) {
	    		booleanJunction.should(queryBuilder.keyword().wildcard().onField(searchFields[i]).matching(queries[j].trim().toLowerCase() + "*").createQuery());
	    	}
	    }
	    
	    luceneQuery = booleanJunction.createQuery();
	    
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, searchedEntity);
		fullTextQuery.setProjection(outputFields);

		return fullTextQuery; 
	}

	/**
	 * This method extract a projection of a fullTextQuery.
	 * @param fullTextQuery
	 * @param outputFields
	 * @param searchedEntity
	 * @return
	 * @throws PersistenceException
	 * 
	 * @deprecated since 0.0.9-aplha
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List executeFullTextQuery(FullTextQuery fullTextQuery, String[] outputFields, Class<?> searchedEntity) throws PersistenceException {
	    List searchResult = fullTextQuery.list();
	    
	    List returnValues = new ArrayList<People>(searchResult.size());
	    
	    for (int i=0; i<searchResult.size(); i++) {
	    	Object entity = null;
	    	try {
		    	Constructor constructor = searchedEntity.getConstructor();
		    	entity = org.springframework.beans.BeanUtils.instantiateClass(constructor, new Object[0]);
		    	for (int j=0; j<outputFields.length; j++) {
		    		try {
		    			if (((Object[])searchResult.get(i))[j] != null) {
		    				BeanUtils.setProperty(entity, outputFields[j], ((Object[])searchResult.get(i))[j] );
		    			} else {
		    				BeanUtils.setProperty(entity, outputFields[j], "");
		    			}
		    		} catch (InvocationTargetException invocationTargetException) {
		    			logger.error(invocationTargetException);
		    		} catch (IllegalAccessException illegalAccessException) {
		    			logger.error(illegalAccessException);
		    		}
		    	}
	    	} catch (NoSuchMethodException noSuchMethodException) {
	    		logger.error(noSuchMethodException);
	    	}

	    	returnValues.add(entity);
	    }
		
		return returnValues;
	}

	/**
	 * 
	 */
	public E find(K id) throws PersistenceException {
		return getEntityManager().find(entityClass, id);
	}

	/**
	 * 
	 * @param total
	 * @param resultNumber
	 * @param fullTextSession
	 */
	protected void flushingFullTextSession(Long total, Integer resultNumber, FullTextSession fullTextSession) {
		logger.info("Initiating Lucene Index Flush... ");
	    fullTextSession.flushToIndexes();
	    fullTextSession.clear();
		logger.info("Finished the Lucene Index Flush...  Total records on index " + resultNumber + "/" + total);
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
			FullTextSession fullTextSession = Search.getFullTextSession(session);

			fullTextSession.createIndexer( entityClass )
			.batchSizeToLoadObjects( 30 )
			.threadsForSubsequentFetching( 8 )
			.threadsToLoadObjects( 4 )
			.threadsForIndexWriter( 3 )
			.cacheMode( CacheMode.IGNORE )
			.startAndWait();
		} catch (Throwable throwable) {
			logger.error(throwable);
		}
	}

	/**
	 * 
	 * @param paginationFilter
	 * @param searchType
	 * @return
	 */
	protected PaginationFilter generatePaginationFilterHibernateSearch(PaginationFilter paginationFilter) {

		if (paginationFilter.getSearchType().equals(SearchType.DOCUMENT)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					case 1:
						paginationFilter.addSortingCriteria("recipientPeople.mapNameLf_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					case 2:
						paginationFilter.addSortingCriteria("docYear_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("docMonthNum.monthNum", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("docDay_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						break;
					case 3:
						paginationFilter.addSortingCriteria("senderPlace.placeName_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					case 4:  
						paginationFilter.addSortingCriteria("recipientPlace.placeName_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					case 5:
						paginationFilter.addSortingCriteria("volume.volNum_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("volume.volLetExt_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						paginationFilter.addSortingCriteria("folioNum_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("folioMod_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					default:
						paginationFilter.addSortingCriteria("docYear_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("docMonthNum.monthNum", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("docDay_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						break;
				}
			}
		} else if (paginationFilter.getSearchType().equals(SearchType.PEOPLE)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("mapNameLf_Sort", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("gender", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("bornYear_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("bornMonthNum.monthNum", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("bornDay_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						break;
					case 3:
						paginationFilter.addSortingCriteria("deathYear_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("deathMonthNum.monthNum", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("deathDay_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						break;
					case 4:
						paginationFilter.addSortingCriteria("recipientPlace.placeName_Sort", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf", paginationFilter.getSortingDirection());
						break;
				}		
			}
		} else if (paginationFilter.getSearchType().equals(SearchType.PLACE)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("placeNameFull_Sort", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("plType", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("parentPlace.placeName", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("parentType", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("placeNameFull_Sort", paginationFilter.getSortingDirection());
						break;
				}
			}
		} else if (paginationFilter.getSearchType().equals(SearchType.VOLUME)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("serieList.title_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle1_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle2_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					case 1:
						paginationFilter.addSortingCriteria("volNum_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("volLetExt_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					case 2:
						paginationFilter.addSortingCriteria("startYear_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("startMonthNum.monthNum", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("startDay_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						break;
					case 3:
						paginationFilter.addSortingCriteria("endYear_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						//Month is an entity, so we don't have field with suffix _Sort
						paginationFilter.addSortingCriteria("endMonthNum.monthNum", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("endDay_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						break;
					case 4:
						paginationFilter.addSortingCriteria("digitized", paginationFilter.getSortingDirection(), SortField.STRING);
						paginationFilter.addSortingCriteria("volNum_Sort", paginationFilter.getSortingDirection(), SortField.INT);
						paginationFilter.addSortingCriteria("volLetExt_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
					default:
						paginationFilter.addSortingCriteria("serieList.title_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle1_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						paginationFilter.addSortingCriteria("serieList.subTitle2_Sort", paginationFilter.getSortingDirection(), SortField.STRING);
						break;
				}
			}
		}

		return paginationFilter;
	}

	/**
	 * 
	 * @param paginationFilter
	 * @param searchType
	 * @return
	 */
	protected PaginationFilter generatePaginationFilterMYSQL(PaginationFilter paginationFilter) {
		if (paginationFilter.getSearchType().equals(SearchType.DOCUMENT)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("recipientPeople.mapNameLf", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("docYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("docMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("docDay", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("senderPlace.placeName", paginationFilter.getSortingDirection());
						break;
					case 4:  
						paginationFilter.addSortingCriteria("recipientPlace.placeName", paginationFilter.getSortingDirection());
						break;
					case 5:
						paginationFilter.addSortingCriteria("volume.volNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volume.volLetExt", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("folioNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("folioMod", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("docYear", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("docMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("docDay", paginationFilter.getSortingDirection());
						break;
				}
			}
		} else if (paginationFilter.getSearchType().equals(SearchType.PEOPLE)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("mapNameLf", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("gender", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("bornYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("bornMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("bornDay", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("deathYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("deathMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("deathDay", paginationFilter.getSortingDirection());
						break;
					case 4:
						paginationFilter.addSortingCriteria("recipientPlace.placeName", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("senderPeople.mapNameLf", paginationFilter.getSortingDirection());
						break;
				}		
			}
		} else if (paginationFilter.getSearchType().equals(SearchType.PLACE)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("placeNameFull", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("plType", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("parentPlace.placeName", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("parentType", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("placeNameFull", paginationFilter.getSortingDirection());
						break;
				}
			}
		} else if (paginationFilter.getSearchType().equals(SearchType.VOLUME)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("serieList.title", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("serieList.subTitle1", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("serieList.subTitle2", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("volNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volLetExt", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("startYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("startMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("startDay", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("endYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("endMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("endDay", paginationFilter.getSortingDirection());
						break;
					case 4:
						paginationFilter.addSortingCriteria("digitized", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volLetExt", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("serieList.title", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("serieList.subTitle1", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("serieList.subTitle2", paginationFilter.getSortingDirection());
						break;
				}
			}
		}

		return paginationFilter;
	}


	/**
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * 
	 */
	public E merge(E entity) throws PersistenceException {
		return getEntityManager().merge(entity);
	}

	/**
	 * 
	 */
	public void optimizeIndex() throws PersistenceException {
		Session session = null;
		FullTextSession fullTextSession = null;
		ScrollableResults results = null;
		try {
			EntityManager entityManager = getEntityManager();
			session = ((HibernateEntityManager) entityManager).getSession();
			session = session.getSessionFactory().openSession();
			fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
			
			logger.info("Initiating Lucene Index Optimze...");
	        SearchFactory searchFactory = fullTextSession.getSearchFactory();
	        searchFactory.optimize(entityClass);
	        logger.info("Finished Lucene Index Optimze");
		} catch (Throwable throwable) {
			logger.error(throwable);
		} finally{
	        if (results != null) {
	        	results.close();
	        }
	        if (fullTextSession.isOpen()) {
	        	fullTextSession.close();
	        }
	        if (session.isOpen()) {
	        	session.close();
	        }
		}
	}
	
	/**
	 * 
	 */
	public void persist(E entity) throws PersistenceException {
		getEntityManager().persist(entity);
	}

	/**
	 * 
	 */
	public void refresh(E entity) throws PersistenceException {
		getEntityManager().refresh(entity);
	}

	/**
	 * 
	 */
	public void remove(E entity) throws PersistenceException {
		getEntityManager().remove(entity);
	}

	/**
	 * 
	 * @param searchContainer
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page search(org.medici.docsources.common.search.Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		// We convert AdvancedSearchContainer to luceneQuery
		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString()); 

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, People.class );
	
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
		
		return page;
	}

	/**
	 * 
	 */
	@Override
	public Page searchMYSQL(org.medici.docsources.common.search.Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
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
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if (sortingCriterias.size() > 0) {
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn());
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} else {
					orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				}
			}
		}
		
		String jpql = objectsQuery + orderBySQL.toString();
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql );
		// We set pagination  
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());

		// We manage sorting (this manages sorting on multiple fields)

		// We set search result on return method
		page.setList(query.getResultList());
		
		return page;
	}
		
	/**
	 * 
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * 
	 * @param fromDate
	 * @throws PersistenceException
	 */
	public void updateIndex(Date fromDate) throws PersistenceException {
		Session session = null;
		FullTextSession fullTextSession = null;
		ScrollableResults results = null;
		try {
			EntityManager entityManager = getEntityManager();
			session = ((HibernateEntityManager) entityManager).getSession();
			session = session.getSessionFactory().openSession();
			fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
			
			Query queryTotal = entityManager.createQuery("SELECT count(*) FROM " + entityClass + " where lastUpdate>=:lastUpdate");
			queryTotal.setParameter("lastUpdate", fromDate);
			Long total = (Long) queryTotal.getSingleResult();
			logger.info("Total Entities to be updated : " + total);

			if (total > 0) {
				Integer numberOfElements = 50;
				Integer numberOfElementsBeforeGC = 1000;
				org.hibernate.Query query = session.createQuery("FROM " + entityClass + "  where lastUpdate>=:lastUpdate");
				query.setParameter("lastUpdate", fromDate);
				
				Transaction tx = fullTextSession.beginTransaction();
				query.setReadOnly(true);
		        query.setLockMode("a", LockMode.NONE);
		        results = query.scroll(ScrollMode.FORWARD_ONLY);
		        Integer resultNumber=0;
		        while (results.next()) {
		        	Object entityClass = (Object) results.get(0);
				    fullTextSession.delete(entityClass);
				    fullTextSession.index(entityClass);
				    resultNumber++;

					if (resultNumber%numberOfElements==0) {
						flushingFullTextSession(total, resultNumber, fullTextSession);
					}
					
					if (resultNumber%numberOfElementsBeforeGC ==0) {
						System.gc();
						logger.info("Invoked Garbage collector to prevent OutOfMemory Errors");
					}
				}
		        
		        flushingFullTextSession(total, resultNumber, fullTextSession);
			    tx.commit();

				logger.info("Initiating Lucene Index Optimze...");
		        SearchFactory searchFactory = fullTextSession.getSearchFactory();
		        searchFactory.optimize(entityClass);
		        logger.info("Finished Lucene Index Optimze");
			} else {
				logger.info("No Entities found to be indexed.");
			}
			logger.info("Indexing documents terminated without errors.");
		} catch (Throwable throwable) {
			logger.error(throwable);
		} finally{
	        if (results != null) {
	        	results.close();
	        }
	        if (fullTextSession.isOpen()) {
	        	fullTextSession.close();
	        }
	        if (session.isOpen()) {
	        	session.close();
	        }
		}
	}
}