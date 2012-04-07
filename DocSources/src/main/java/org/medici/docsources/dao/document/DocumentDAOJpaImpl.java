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

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.SearchFactory;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.PaginationFilter.Order;
import org.medici.docsources.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.docsources.common.search.Search;
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
	public Document checkVolumeFolio(Integer summaryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Document WHERE volume.summaryId=:summaryId ORDER BY folioNum DESC");
		query.setParameter("summaryId", summaryId);
		
		query.setMaxResults(1);
		return (Document) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countDocumentCreatedAfterDate(Date inputDate) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE dateCreated>=:inputDate");
		query.setParameter("inputDate", inputDate);

		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countDocumentsLinkedToAVolume(Integer summaryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE volume.summaryId =:summaryId");
		query.setParameter("summaryId", summaryId);
		
		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
		StringBuffer stringBuffer = new StringBuffer("FROM Document WHERE volume.volNum=" + volNum);
		if(ObjectUtils.toString(volLetExt).equals("")){
			stringBuffer.append(" AND volume.volLetExt is null ");
		} else {
			stringBuffer.append(" AND volume.volLetExt = '");
			stringBuffer.append(volLetExt);
			stringBuffer.append("' ");
		}
		stringBuffer.append(" AND folioNum=");
		stringBuffer.append(folioNum);
		if(ObjectUtils.toString(folioMod).equals("")){
			stringBuffer.append(" AND folioMod is null");
		} else {
			stringBuffer.append(" AND folioMod = '");
			stringBuffer.append(folioMod);
			stringBuffer.append("' ");
		}
		stringBuffer.append(" AND logicalDelete=false");
		Query query = getEntityManager().createQuery(stringBuffer.toString());

		if (query.getResultList().size() ==0) { 
			return null;
		} else {
			return (Document) query.getResultList().get(0);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findDocumentByFolioStart(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
		return this.findDocument(volNum, volLetExt, folioNum, folioMod);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Document findLastEntryDocument() throws PersistenceException {
        Query query = getEntityManager().createQuery("FROM Document WHERE logicalDelete = false ORDER BY dateCreated DESC");
        query.setMaxResults(1);

        return (Document) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfDocumentsRelatedVolume(Integer summaryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE summaryId =:summaryId");
		query.setParameter("summaryId", summaryId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfRecipientDocumentsPerson(Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE recipientPeople.personId =:personId");
		query.setParameter("personId", personId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfRecipientDocumentsPlace(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE recipientPlace.placeAllId =:placeAllId");
		query.setParameter("placeAllId", placeAllId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfSenderDocumentsPerson(Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE senderPeople.personId =:personId");
		query.setParameter("personId", personId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfSenderDocumentsPlace(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE senderPlace.placeAllId =:placeAllId");
		query.setParameter("placeAllId", placeAllId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, Long> findNumbersOfDocumentsRelatedPlace(List<Integer> placeAllIds) throws PersistenceException {
		StringBuffer stringBufferSender = new StringBuffer("SELECT senderPlace.placeAllId, COUNT(entryId) FROM Document WHERE");
		StringBuffer stringBufferRecipient = new StringBuffer("SELECT recipientPlace.placeAllId, COUNT(entryId) FROM Document WHERE");
		for(int i = 0; i < placeAllIds.size(); i++){
			if(stringBufferSender.indexOf("=") != -1){
				stringBufferSender.append(" or ");
			}
			if(stringBufferRecipient.indexOf("=") != -1){
				stringBufferRecipient.append(" or ");
			}
			stringBufferSender.append("(senderPlace.placeAllId=");
			stringBufferSender.append(placeAllIds.get(i) + ")");
			stringBufferRecipient.append("(recipientPlace.placeAllId=");
			stringBufferRecipient.append(placeAllIds.get(i) + " AND senderPlace.placeAllId!=" + placeAllIds.get(i) + ")");
		}
		stringBufferSender.append(" group by senderPlace.placeAllId");
		stringBufferRecipient.append(" group by recipientPlace.placeAllId");
		
		Map<Integer, Long> returnValues = new HashMap<Integer, Long>();
		List tempValuesSender;
		if(stringBufferSender.indexOf("=") != -1){
			Query query = getEntityManager().createQuery(stringBufferSender.toString());
			tempValuesSender = query.getResultList();
			for(Iterator i = tempValuesSender.iterator(); i.hasNext();){
				Object [] data = (Object []) i.next();
				returnValues.put((Integer)data[0], (Long)data[1]);
			}
		}
		List tempValuesRecipient;
		if(stringBufferRecipient.indexOf("=") != -1){
			Query query = getEntityManager().createQuery(stringBufferRecipient.toString());
			tempValuesRecipient = query.getResultList();
			for(Iterator i = tempValuesRecipient.iterator(); i.hasNext();){
				Object [] data = (Object []) i.next();
				if(returnValues.containsKey((Integer) data[0])){
					data[1] = (Long) returnValues.remove((Integer) data[0]) + (Long) data[1];
				}
				returnValues.put((Integer) data[0], (Long) data[1]);
			}
		}
		
		return returnValues;
	}

	/**
	 * 
	 * @throws PersistenceException
	 */
	public void generateIndex() throws PersistenceException {
		Session session = null;
		FullTextSession fullTextSession = null;
		ScrollableResults results = null;
		try {
			EntityManager entityManager = getEntityManager();
			session = ((HibernateEntityManager) entityManager).getSession();
			session = session.getSessionFactory().openSession();
			fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
			
			Long total = (Long) entityManager.createQuery("SELECT count(entryId) FROM Document").getSingleResult();
			logger.info("Total Entities to be indexed : " + total);

			if (total > 0) {
				Transaction tx = fullTextSession.beginTransaction();
				fullTextSession.purgeAll(Document.class);
				tx.commit();
				logger.info("Removed all documents.");
				Integer numberOfElements = 100;

				Integer numberOfElementsBeforeGC = 1000;
				String queryJPA = "FROM Document ORDER BY entryId asc";

				org.hibernate.Query query = session.createQuery(queryJPA);
				tx = fullTextSession.beginTransaction();
				query.setReadOnly(true);
		        query.setLockMode("a", LockMode.NONE);
		        results = query.scroll(ScrollMode.FORWARD_ONLY);
		        Integer resultNumber=0;
		        while (results.next()) {
		            Document document = (Document) results.get(0);
				    fullTextSession.index(document);
				    resultNumber++;

					if (resultNumber%numberOfElements==0) {
						logger.info("Initiating Lucene Index Flush... ");
					    fullTextSession.flushToIndexes();
					    fullTextSession.clear();
						logger.info("Finished the Lucene Index Flush...  Total records on index " + resultNumber + "/" + total);
					}
					
					if (resultNumber%numberOfElementsBeforeGC ==0) {
						System.gc();
						logger.info("Invoked Garbage collector to prevent OutOfMemory Errors");
					}
				}
		        
			    fullTextSession.flushToIndexes();
			    fullTextSession.clear();
			    tx.commit();

				logger.info("Initiating Lucene Index Optimze...");
		        SearchFactory searchFactory = fullTextSession.getSearchFactory();
		        searchFactory.optimize(Document.class);
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
	        if (fullTextSession != null) {
	        	fullTextSession.close();
	        }
	        if (session != null) {
	        	session.close();
	        }
		}
	}

	/**
	 * 
	 */
	@Override
	public Page searchDocuments(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString());
			
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
		paginationFilter = this.generatePaginationFilterHibernateSearch(paginationFilter);
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
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EpLink WHERE person.personId=" + personToSearch + ")");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	public Page searchDocumentsRelatedVolume(String volumeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE volume.summaryId=" + volumeToSearch);
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	public Page searchRecipientDocumentsPerson(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE (recipientPeople.personId=" + personToSearch + ")");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	public Page searchRecipientDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE (recipientPlace.placeAllId=" + placeToSearch + ")");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	public Page searchReferringToDocumentsPerson(String personToSearch,	PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE entryId IN (SELECT document.entryId FROM org.medici.docsources.domain.EpLink WHERE person.personId=" + personToSearch + " AND docRole is null)");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	public Page searchSenderDocumentsPerson(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE (senderPeople.personId=" + personToSearch + ")");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	public Page searchSenderDocumentsPlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE (senderPlace.placeAllId=" + placeToSearch + ")");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
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
	 * 
	 * @throws PersistenceException
	 */
	@Override
	public void updateIndex(Date fromDate) throws PersistenceException {
		Session session = null;
		FullTextSession fullTextSession = null;
		ScrollableResults results = null;
		try {
			EntityManager entityManager = getEntityManager();
			session = ((HibernateEntityManager) entityManager).getSession();
			session = session.getSessionFactory().openSession();
			fullTextSession = org.hibernate.search.Search.getFullTextSession(session);
			
			Query queryTotal = entityManager.createQuery("SELECT count(entryId) FROM Document where lastUpdate>=:lastUpdate");
			queryTotal.setParameter("lastUpdate", fromDate);
			Long total = (Long) queryTotal.getSingleResult();
			logger.info("Total Entities to be updated : " + total);

			if (total > 0) {
				Integer numberOfElements = 50;
				Integer numberOfElementsBeforeGC = 1000;
				org.hibernate.Query query = session.createQuery("FROM Document where lastUpdate>=:lastUpdate");
				query.setParameter("lastUpdate", fromDate);
				
				Transaction tx = fullTextSession.beginTransaction();
				query.setReadOnly(true);
		        query.setLockMode("a", LockMode.NONE);
		        results = query.scroll(ScrollMode.FORWARD_ONLY);
		        Integer resultNumber=0;
		        while (results.next()) {
		            Document document = (Document) results.get(0);
				    fullTextSession.delete(document);
				    fullTextSession.index(document);
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

		        
/*				logger.info("Initiating Lucene Index Optimze...");
		        SearchFactory searchFactory = fullTextSession.getSearchFactory();
		        searchFactory.optimize(Document.class);
*/
		        logger.info("Finished Lucene Index Optimze");

			    tx.commit();
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
	        /*if (fullTextSession.isOpen()) {
	        	fullTextSession.close();
	        }*/
	        if (session.isOpen()) {
	        	session.close();
	        }
		}
	}
}
