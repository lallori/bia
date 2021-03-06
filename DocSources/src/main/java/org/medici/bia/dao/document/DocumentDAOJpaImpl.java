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
package org.medici.bia.dao.document;

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
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageRectoVerso;
import org.medici.bia.domain.SearchFilter.SearchType;
import org.springframework.stereotype.Repository;

/**
 * <b>DocumentDAOJpaImpl</b> is a default implementation of <b>DocumentDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.Document
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
		Query query = getEntityManager().createQuery("FROM Document WHERE volume.summaryId=:summaryId AND logicalDelete=false ORDER BY folioNum DESC");
		query.setParameter("summaryId", summaryId);
		
		query.setMaxResults(1);
		return (Document) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countDocumentCreatedAfterDate(Date inputDate) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(*) FROM Document WHERE dateCreated >= :inputDate");
		query.setParameter("inputDate", inputDate);

		return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countDocumentCreatedBeforeDate(Date date) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(*) FROM Document WHERE dateCreated <= :inputDate");
		query.setParameter("inputDate", date);

		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countDocumentsLinkedToAVolume(Integer summaryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE logicalDelete=false AND volume.summaryId =:summaryId");
		query.setParameter("summaryId", summaryId);
		
		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocument(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder("FROM Document WHERE volume.volNum=" + volNum);
		if(ObjectUtils.toString(volLetExt).equals("")){
			stringBuilder.append(" AND volume.volLetExt is null ");
		} else {
			stringBuilder.append(" AND volume.volLetExt = '");
			stringBuilder.append(volLetExt);
			stringBuilder.append("' ");
		}
		stringBuilder.append(" AND folioNum=");
		stringBuilder.append(folioNum);
		if(ObjectUtils.toString(folioMod).equals("")){
			stringBuilder.append(" AND folioMod is null");
		} else {
			stringBuilder.append(" AND folioMod = '");
			stringBuilder.append(folioMod);
			stringBuilder.append("' ");
		}
		stringBuilder.append(" AND logicalDelete=false");
		Query query = getEntityManager().createQuery(stringBuilder.toString());

		if (query.getResultList().size() ==0) { 
			return null;
		} else {
			return query.getResultList();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Document> findDocument(
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod, 
			Document.RectoVerso folioRectoVerso) throws PersistenceException {
		
		boolean isEmptyVolLetExt = "".equals(ObjectUtils.toString(volLetExt).trim());
		boolean isEmptyInsertNum = "".equals(ObjectUtils.toString(insertNum).trim());
		boolean isEmptyInsertLet = "".equals(ObjectUtils.toString(insertLet).trim());
		boolean isEmptyFolioMod = "".equals(ObjectUtils.toString(folioMod).trim());
		boolean isRectoVersoNullable = Document.RectoVerso.N.equals(folioRectoVerso);
		
		StringBuilder stringBuilder = new StringBuilder("FROM Document WHERE volume.volNum = :volNum");
		stringBuilder.append(" AND volume.volLetExt ").append(!isEmptyVolLetExt ? "= :volLetExt" : "IS NULL");
		stringBuilder.append(" AND insertNum ").append(!isEmptyInsertNum ? "= :insertNum" : "IS NULL");
		stringBuilder.append(" AND insertLet ").append(!isEmptyInsertLet ? "= :insertLet" : "IS NULL");
		stringBuilder.append(" AND folioNum = :folioNum");
		stringBuilder.append(" AND folioMod ").append(!isEmptyFolioMod ? "= :folioMod" : "IS NULL");
		if (folioRectoVerso != null) {
			stringBuilder.append(" AND folioRectoVerso ").append(!isRectoVersoNullable ? "= :folioRectoVerso " : "IS NULL");
		}
		stringBuilder.append(" AND logicalDelete = false");
		
		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("volNum", volNum);
		if (!isEmptyVolLetExt) {
			query.setParameter("volLetExt", volLetExt);
		}
		if (!isEmptyInsertNum) {
			query.setParameter("insertNum", insertNum.trim());
			if (!isEmptyInsertLet) {
				query.setParameter("insertLet", insertLet.trim());
			}
		}
		query.setParameter("folioNum", folioNum);
		if (!isEmptyFolioMod) {
			query.setParameter("folioMod", folioMod);
		}
		if (folioRectoVerso != null && !isRectoVersoNullable) {
			query.setParameter("folioRectoVerso", folioRectoVerso);
		}

		List<Document> docs = query.getResultList();
			
		return docs;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> findDocumentsOnFolio(
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String rectoVerso) throws PersistenceException {
		return findDocumentsOnFolioOrTranscribeFolio(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, rectoVerso, true, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> findDocumentsOnFolioWithOrWithoutRectoVerso(
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String rectoVerso) throws PersistenceException {
		return findDocumentsWithOrWithoutRectoVerso(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, rectoVerso, true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> findDocumentsOnTranscribeFolio(
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer transcribeFolioNum,
			String transcribeFolioMod,
			String rectoVerso) throws PersistenceException {
		return findDocumentsOnFolioOrTranscribeFolio(volNum, volLetExt, insertNum, insertLet, transcribeFolioNum, transcribeFolioMod, rectoVerso, false, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> findDocumentsOnTranscribeFolioWithOrWithoutRectoVerso(
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String rectoVerso) throws PersistenceException {
		return findDocumentsWithOrWithoutRectoVerso(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, rectoVerso, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Document> findDocumentByFolioStart(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
		/*StringBuilder stringBuilder = new StringBuilder("FROM Document WHERE volume.volNum=" + volNum);
		if(ObjectUtils.toString(volLetExt).equals("")){
			stringBuilder.append(" AND volume.volLetExt is null ");
		} else {
			stringBuilder.append(" AND volume.volLetExt = '");
			stringBuilder.append(volLetExt);
			stringBuilder.append("' ");
		}
		stringBuilder.append(" AND folioNum=");
		stringBuilder.append(folioNum);
		if(ObjectUtils.toString(folioMod).equals("")){
			stringBuilder.append(" AND folioMod is null");
		} else {
			stringBuilder.append(" AND folioMod = '");
			stringBuilder.append(folioMod);
			stringBuilder.append("' ");
		}
		stringBuilder.append(" AND logicalDelete=false");
		Query query = getEntityManager().createQuery(stringBuilder.toString());

		if (query.getResultList().size() ==0) { 
			return null;
		} else {
			return (Document) query.getResultList().get(0);
		}*/
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
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE summaryId =:summaryId and logicalDelete = false");
		query.setParameter("summaryId", summaryId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfRecipientDocumentsPerson(Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE recipientPeople.personId =:personId and logicalDelete = false");
		query.setParameter("personId", personId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfRecipientDocumentsPlace(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE recipientPlace.placeAllId =:placeAllId and logicalDelete = false");
		query.setParameter("placeAllId", placeAllId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfSenderDocumentsPerson(Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE senderPeople.personId =:personId and logicalDelete = false");
		query.setParameter("personId", personId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfSenderDocumentsPlace(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(entryId) FROM Document WHERE senderPlace.placeAllId =:placeAllId and logicalDelete = false");
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
		StringBuilder stringBuilderSender = new StringBuilder("SELECT senderPlace.placeAllId, COUNT(entryId) FROM Document WHERE logicalDelete = false AND ");
		StringBuilder stringBuilderRecipient = new StringBuilder("SELECT recipientPlace.placeAllId, COUNT(entryId) FROM Document WHERE logicalDelete = false AND ");
		for(int i = 0; i < placeAllIds.size(); i++){
			if(i > 0){
				stringBuilderSender.append(" or ");
			}
			if(i > 0){
				stringBuilderRecipient.append(" or ");
			}
			stringBuilderSender.append("(senderPlace.placeAllId=");
			stringBuilderSender.append(placeAllIds.get(i) + ")");
			stringBuilderRecipient.append("(recipientPlace.placeAllId=");
			stringBuilderRecipient.append(placeAllIds.get(i) + " AND senderPlace.placeAllId!=" + placeAllIds.get(i) + ")");
		}
		stringBuilderSender.append(" group by senderPlace.placeAllId");
		stringBuilderRecipient.append(" group by recipientPlace.placeAllId");
		
		Map<Integer, Long> returnValues = new HashMap<Integer, Long>();
		List tempValuesSender;
		if(placeAllIds.size() > 0){
			Query query = getEntityManager().createQuery(stringBuilderSender.toString());
			tempValuesSender = query.getResultList();
			for(Iterator i = tempValuesSender.iterator(); i.hasNext();){
				Object [] data = (Object []) i.next();
				returnValues.put((Integer)data[0], (Long)data[1]);
			}
		}
		List tempValuesRecipient;
		if(placeAllIds.size() > 0){
			Query query = getEntityManager().createQuery(stringBuilderRecipient.toString());
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
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Integer> getAssociatedImage(List<Integer> entryIds) throws PersistenceException {
		String jpql = "SELECT doc.entryId, img.imageId FROM Document AS doc, Image AS img WHERE"
				+ " doc.entryId IN (:docIds)"
				+ " AND doc.volume.volNum = img.volNum"
				+ " AND ((doc.volume.volLetExt IS NULL AND img.volLetExt IS NULL) OR (doc.volume.volLetExt = img.volLetExt))"
				+ " AND ((doc.insertNum IS NULL AND img.insertNum IS NULL) OR (doc.insertNum = img.insertNum))"
				+ " AND ((doc.insertLet IS NULL AND img.insertLet IS NULL) OR (doc.insertLet = img.insertLet))"
				+ " AND doc.folioNum IS NOT NULL"
				+ " AND ((doc.folioMod IS NULL AND img.missedNumbering IS NULL) OR (doc.folioMod = img.missedNumbering))"
				+ " AND (doc.folioRectoVerso IS NULL OR doc.folioRectoVerso = img.imageRectoVerso)"
				+ " AND img.imageType = 'C'"
				+ " GROUP BY doc.entryId"
				+ " ORDER BY doc.entryId ASC";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("docIds", entryIds);
		List<Object[]> results = (List<Object[]>)query.getResultList();
		
		Map<Integer, Integer> resultMap = new HashMap<Integer, Integer>();
		for (Object[] result : results) {
			resultMap.put((Integer)result[0], (Integer)result[1]);
		}
		
		return resultMap;
	}
	
	@Override
	public List<Document> getDocumentsByImage(Image image) throws PersistenceException {
		String jpql = "SELECT doc FROM Document AS doc, Volume AS vol WHERE vol.volNum = :volNum"
			+ (image.getVolLetExt() != null ? " AND vol.volLetExt = :volLetExt" : " AND vol.volLetExt IS NULL")
			+ " AND doc.volume = vol AND doc.logicalDelete = false"
			+ (image.getInsertNum() != null ? " AND doc.insertNum = :insertNum" : " AND doc.insertNum IS NULL")
			+ (image.getInsertLet() != null ? " AND doc.insertLet = :insertLet" : " AND doc.insertLet IS NULL")
			+ " AND doc.transcribeFolioNum = :folioNum"
			+ (image.getMissedNumbering() != null ? " AND doc.transcribeFolioMod = :folioMod" : " AND doc.transcribeFolioMod IS NULL")
			+ (image.getImageRectoVerso() != null ? " AND doc.transcribeFolioRectoVerso = :folioRV" : " AND doc.transcribeFolioRectoVerso IS NULL");
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("volNum", image.getVolNum());
		if (image.getVolLetExt() != null) {
			query.setParameter("volLetExt", image.getVolLetExt());
		}
		if (image.getInsertNum() != null) {
			query.setParameter("insertNum", image.getInsertNum());
		}
		if (image.getInsertLet() != null) {
			query.setParameter("insertLet", image.getInsertLet());
		}
		query.setParameter("folioNum", image.getImageProgTypeNum());
		if (image.getMissedNumbering() != null) {
			query.setParameter("folioMod", image.getMissedNumbering());
		}
		if (image.getImageRectoVerso() != null) {
			query.setParameter("folioRV", ImageRectoVerso.R.equals(image.getImageRectoVerso()) ? Document.RectoVerso.R : ImageRectoVerso.V.equals(image.getImageRectoVerso()) ? Document.RectoVerso.V : Document.RectoVerso.N);
		}
		
		return getResultList(query);
	}
	
	@Override
	public Document getTeachingDocument(Integer forumContainerId) throws PersistenceException {
		String jpql = "SELECT option.courseTopic.document FROM CourseTopicOption AS option " +
			"WHERE (option.mode = 'I' or option.mode = 'R') AND " +
			"courseTopic.forum.forumId = :containerId";
				
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("containerId", forumContainerId);
		
		return getFirst(query);
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
	public Page searchDocumentsCreatedBefore(Date date, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		String jpql = "FROM Document WHERE dateCreated <= :dateTime";
		Query query = null;
		
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + jpql;
			query = getEntityManager().createQuery(countQuery);
			query.setParameter("dateTime", date);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		query = getEntityManager().createQuery(jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		query.setParameter("dateTime", date);

		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDocumentsRelated(String personToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM Document WHERE entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EpLink WHERE person.personId=" + personToSearch + ") AND logicalDelete=false");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
		String toSearch = new String("FROM Document WHERE volume.summaryId=" + volumeToSearch + " AND logicalDelete=false");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public List<Document> searchDocumentsToPrint(Search searchContainer) throws PersistenceException{
		Query query = null;
		// We set size of result.
//		String countQuery = "SELECT COUNT(*) " + searchContainer.toJPAQuery();
	        
		String objectsQuery = searchContainer.toJPAQuery();

		// We manage sorting (this manages sorting on multiple fields)
		PaginationFilter paginationFilter = new PaginationFilter(0, 0, 2, "asc", SearchType.DOCUMENT); 
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		String jpql = objectsQuery + getOrderByQuery(paginationFilter.getSortingCriterias());
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql );
		

		return query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchLinkedDocumentsTopic(String placeToSearch, String topicToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		Query query = null;
		
		String toSearch = "FROM Document WHERE entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE place.placeAllId = " + placeToSearch;
		if (topicToSearch != null) {
			toSearch += " AND topic.topicTitle LIKE '" + topicToSearch + "'";
		}
		toSearch+=") AND logicalDelete=false";
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchLinkedDocumentsTopic(Integer topicId, Integer placeAllId,	PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		Query query = null;
		
		String toSearch = "FROM Document WHERE entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EplToLink WHERE place.geogKey IN (SELECT geogKey FROM Place WHERE placeAllId = " + placeAllId + ") AND topic.topicId = " + topicId + ") AND logicalDelete=false";
		
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
		String toSearch = new String("FROM Document WHERE recipientPeople.personId=" + personToSearch + " AND logicalDelete=false");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
		String toSearch = new String("FROM Document WHERE recipientPlace.placeAllId=" + placeToSearch + " AND logicalDelete=false");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
		String toSearch = new String("FROM Document WHERE entryId IN (SELECT document.entryId FROM org.medici.bia.domain.EpLink WHERE person.personId=" + personToSearch + " AND docRole is null) AND logicalDelete=false ");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
		String toSearch = new String("FROM Document WHERE senderPeople.personId=" + personToSearch + " AND logicalDelete=false");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
		String toSearch = new String("FROM Document WHERE senderPlace.placeAllId=" + placeToSearch + " AND logicalDelete=false");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		query = getEntityManager().createQuery(toSearch + getOrderByQuery(paginationFilter.getSortingCriterias()));
		
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
	
	
	/* Privates */
	
	@SuppressWarnings("unchecked")
	private List<Document> findDocumentsOnFolioOrTranscribeFolio (
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String rectoVerso,
			boolean isFolio,
			boolean alsoNullFolio) throws PersistenceException {
		
		String insNum = org.medici.bia.common.util.StringUtils.safeTrim(insertNum);
		String insLet = org.medici.bia.common.util.StringUtils.safeTrim(insertLet);
		String folMod = org.medici.bia.common.util.StringUtils.safeTrim(folioMod);
		String rv = org.medici.bia.common.util.StringUtils.safeTrim(rectoVerso);
		
		if (!org.medici.bia.common.util.StringUtils.isNullableString(rv) && Document.RectoVerso.convertFromString(rv) == null) {
			throw new PersistenceException("Find document on " + (isFolio ? "" : "transcribe") + " folio query: Not possible to convert the given transcribe folio recto/verso [" + rv + "]");
		}
		
		StringBuilder sb = new StringBuilder("FROM Document WHERE volume.volNum = :volNum");
		sb.append(" AND volume.volLetExt ").append(org.medici.bia.common.util.StringUtils.isNullableString(volLetExt) ? "IS NULL" : "= :volLetExt");
		if (insNum != "") {
			sb.append(" AND insertNum ").append(insNum == null ? "IS NULL" : "= :insertNum");
		}
		if (insLet != "") {
			sb.append(" AND insertLet ").append(insLet == null ? "IS NULL" : "= :insertLet");
		}
		sb.append(" AND ").append(isFolio ? "folioNum " : "transcribeFolioNum ").append(folioNum == null ? "IS NULL" : "= :folioNum");
		if (folMod != "") {
			sb.append(" AND ").append(isFolio ? "folioMod " : "transcribeFolioMod ").append(folMod == null ? "IS NULL" : "= :folioMod");
		}
		if (rv != "" || alsoNullFolio) {
			sb.append(rv != null && alsoNullFolio ? " AND (" : " AND ").append(isFolio ? "folioRectoVerso " : "transcribeFolioRectoVerso ").append(rv == null ? "IS NULL" : "= :rectoVerso");
			if (rv != null && alsoNullFolio) {
				sb.append(" OR ").append(isFolio ? "folioRectoVerso " : "transcribeFolioRectoVerso ").append(" IS NULL)");
			}
		}
		
		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter("volNum", volNum);
		if (!org.medici.bia.common.util.StringUtils.isNullableString(volLetExt)) {
			query.setParameter("volLetExt", volLetExt.trim());
		}
		if (!org.medici.bia.common.util.StringUtils.isNullableString(insNum)) {
			query.setParameter("insertNum", insNum);
		}
		if (!org.medici.bia.common.util.StringUtils.isNullableString(insLet)) {
			query.setParameter("insertLet", insLet);
		}
		if (folioNum != null) {
			query.setParameter("folioNum", folioNum);
		}
		if (!org.medici.bia.common.util.StringUtils.isNullableString(folMod)) {
			query.setParameter("folioMod", folMod);
		}
		if (!org.medici.bia.common.util.StringUtils.isNullableString(rv)) {
			query.setParameter("rectoVerso", Document.RectoVerso.convertFromString(rv));
		}
		return query.getResultList();
	}
	
	private List<Document> findDocumentsWithOrWithoutRectoVerso(
			Integer volNum,
			String volLetExt,
			String insertNum,
			String insertLet,
			Integer folioNum,
			String folioMod,
			String rectoVerso,
			boolean isFolio) throws PersistenceException {
		
		return findDocumentsOnFolioOrTranscribeFolio(volNum, volLetExt, insertNum, insertLet, folioNum, folioMod, rectoVerso, isFolio, true);
	}
}
