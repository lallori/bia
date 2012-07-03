/*
 * EpLinkDAOJpaImpl.java
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
package org.medici.docsources.dao.eplink;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.EpLink;
import org.springframework.stereotype.Repository;

/**
 * <b>EpLinkDAOJpaImpl</b> is a default implementation of <b>EpLinkDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.EpLink
 */
@Repository
public class EpLinkDAOJpaImpl extends JpaDao<Integer, EpLink> implements EpLinkDAO {

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
	private static final long serialVersionUID = 2580073517266183139L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EpLink find(Integer epLinkId, Integer entryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from EpLink where epLinkId=:epLinkId and document.entryId=:entryId");
		query.setParameter("epLinkId", epLinkId);
		query.setParameter("entryId", entryId);
		
		List<EpLink> result = query.getResultList();
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
	public List<EpLink> findByEntryId(Integer entryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from EpLink where document.entryId=:entryId");
		query.setParameter("entryId", entryId);
		
		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EpLink findByEntryIdAndPersonId(Integer entryId, Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from EpLink where document.entryId=:entryId AND person.personId=:personId");
		query.setParameter("entryId", entryId);
		query.setParameter("personId", personId);
		
		List<EpLink> result = (List<EpLink>) query.getResultList();

		if (result.size() >= 1) {
			return result.get(0);
		}

		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EpLink findByEntryIdAndRole(Integer entryId, String docRole)	throws PersistenceException {
		Query query = getEntityManager().createQuery("from EpLink where document.entryId=:entryId AND docRole like :docRole");
		query.setParameter("entryId", entryId);
		query.setParameter("docRole", docRole);
		
		List<EpLink> result = query.getResultList();
		if(result.size() == 0){
			return null;
		}else{
			return result.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfDocumentsRelated(Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(DISTINCT entryId) FROM EpLink WHERE person.personId=:personId))");
		query.setParameter("personId", personId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfReferringDocumentsRelated(Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(DISTINCT entryId) FROM EpLink WHERE person.personId =:personId AND docRole is null");
		query.setParameter("personId", personId);
		
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, Long> findNumbersOfDocumentsRelated(List<Integer> personIds) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder("SELECT person.personId, COUNT(document.entryId) FROM EpLink WHERE");
		for(int i=0; i < personIds.size(); i++){
			if(stringBuilder.indexOf("=") != -1){
    			stringBuilder.append(" or ");
    		}
			stringBuilder.append("(person.personId=");
        	stringBuilder.append(personIds.get(i) + ")");
		}
		stringBuilder.append(" group by person.personId");
		
		Map<Integer, Long> returnValues = new HashMap<Integer, Long>();
		List tempValues;
		if(stringBuilder.indexOf("=") != -1){
			Query query = getEntityManager().createQuery(stringBuilder.toString());
			tempValues = query.getResultList();
			for(Iterator i = tempValues.iterator(); i.hasNext();){
				Object [] data = (Object []) i.next();
				returnValues.put((Integer)data[0], (Long)data[1]);
			}
		}
		
		
		return returnValues;
	}

}
