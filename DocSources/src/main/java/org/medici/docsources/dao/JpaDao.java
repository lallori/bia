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
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.medici.docsources.domain.People;
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
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = -4380540983397163140L;

	protected Class<E> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public E find(K id) throws PersistenceException {
		return getEntityManager().find(entityClass, id);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public E merge(E entity) throws PersistenceException {
		return getEntityManager().merge(entity);
	}

	public void persist(E entity) throws PersistenceException {
		getEntityManager().persist(entity);
	}

	public void remove(E entity) throws PersistenceException {
		getEntityManager().remove(entity);
	}

	public void refresh(E entity) throws PersistenceException {
		getEntityManager().refresh(entity);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
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
	 * This method extract a projection of a fullTextQuery.
	 * @param fullTextQuery
	 * @param outputFields
	 * @param searchedEntity
	 * @return
	 * @throws PersistenceException
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
	 * @throws PersistenceException
	 */
	public void generateIndex() throws PersistenceException {
		try {
			EntityManager entityManager = getEntityManager();
			Session session = ((HibernateEntityManager) entityManager).getSession();
			session = session.getSessionFactory().openSession();
			FullTextSession fullTextSession = Search.getFullTextSession(session);
	
			/*fullTextSession.setFlushMode(FlushMode.MANUAL);
			fullTextSession.setCacheMode(CacheMode.IGNORE);
			Transaction transaction = fullTextSession.beginTransaction();
			//Scrollable results will avoid loading too many objects in memory
			ScrollableResults results = fullTextSession.createCriteria( entityClass )
			    .setFetchSize(100)
			    .scroll( ScrollMode.FORWARD_ONLY );
			int index = 0;
			while( results.next() ) {
			    index++;
			    fullTextSession.index( results.get(0) ); //index each element
			    if (index % 100 == 0) {
			        fullTextSession.flushToIndexes(); //apply changes to indexes
			        fullTextSession.clear(); //free memory since the queue is processed
			    }
			}
			transaction.commit();
*/
			fullTextSession.createIndexer( entityClass )
			.batchSizeToLoadObjects( 50 )
			.cacheMode( CacheMode.NORMAL )
			.threadsToLoadObjects( 8 )
			.threadsForSubsequentFetching( 5 )
			.startAndWait();
		} catch (Throwable throwable) {
			logger.error(throwable);
		}
	}

}