/*
 * EplToLinkDAOJpaImpl.java
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
package org.medici.bia.dao.epltolink;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.EplToLink;
import org.springframework.stereotype.Repository;

/**
 * <b>EplToLinkDAOJpaImpl</b> is a default implementation of
 * <b>EplToLinkDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.EplToLink
 */
@Repository
public class EplToLinkDAOJpaImpl extends JpaDao<Integer, EplToLink> implements EplToLinkDAO {

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
	private static final long serialVersionUID = 1576142793715132865L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public EplToLink find(Integer entryId, Integer eplToId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from EplToLink where eplToId=:eplToId and document.entryId=:entryId");
		query.setParameter("eplToId", eplToId);
		query.setParameter("entryId", entryId);

		List<EplToLink> result = query.getResultList();
		if (result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<EplToLink> findByEntryId(Integer entryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from EplToLink where document.entryId=:entryId ORDER BY topic.topicTitle");
		query.setParameter("entryId", entryId);

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfDocumentInTopicsByPlace(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("Select count(distinct document.entryId) from EplToLink where place.placeAllId=:placeAllId");
		query.setParameter("placeAllId", placeAllId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Integer findNumberOfTopicsByDocument(Integer entryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(DISTINCT topic.topicTitle) FROM EplToLink where document.entryId=:entryId");
		query.setParameter("entryId", entryId);
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfTopicsByPlaceAllId(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(DISTINCT topic.topicTitle) FROM EplToLink where place.placeAllId=:placeAllId");
		query.setParameter("placeAllId", placeAllId);
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, Long> findNumbersOfDocumentsInTopicsByPlace(List<Integer> placeAllIds) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder("SELECT place.placeAllId, COUNT(DISTINCT document.entryId) FROM EplToLink WHERE document.logicalDelete = false AND ");
		for(int i = 0; i < placeAllIds.size(); i++){
			if(i > 0){
				stringBuilder.append(" or ");
			}
			stringBuilder.append("(place.placeAllId=");
			stringBuilder.append(placeAllIds.get(i));
			stringBuilder.append(')');
		}
		stringBuilder.append(" GROUP BY place.placeAllId");
		
		Map<Integer, Long> returnValues = new HashMap<Integer, Long>();
		List tempValues;
		if(placeAllIds.size() > 0){
			Query query = getEntityManager().createQuery(stringBuilder.toString());
			tempValues = query.getResultList();
			for(Iterator i = tempValues.iterator(); i.hasNext();){
				Object[] data = (Object []) i.next();
				returnValues.put((Integer) data[0] , (Long) data[1]); 
			}
			
		}
		
		return returnValues;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTopics(String topic, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM EplToLink WHERE topicId=" + topic);
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder(0);
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
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Long> searchTopicsPlace(String placeToSearch) throws PersistenceException {
		Map<String, Long> returnValues = new LinkedHashMap<String, Long>();
		List tempValuesResult;
		Query query = null;
		String toSearch = new String("SELECT topic.topicTitle, COUNT(DISTINCT document.entryId) FROM EplToLink WHERE place.placeAllId=" + placeToSearch + " AND document.logicalDelete = false GROUP BY topic.topicTitle ORDER BY topic.topicTitle ASC");
		
		query = getEntityManager().createQuery(toSearch);
		tempValuesResult = query.getResultList();
		for(Iterator i = tempValuesResult.iterator(); i.hasNext();){
			Object[] data = (Object []) i.next();
			returnValues.put((String) data[0], (Long) data[1]); 
		}
		
		return returnValues;
		
	}
}
