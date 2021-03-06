/*
 * PoLinkDAOJpaImpl.java
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
package org.medici.bia.dao.polink;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.PoLink;
import org.springframework.stereotype.Repository;

/**
 * <b>PoLinkDAOJpaImpl</b> is a default implementation of <b>PoLinkDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Repository
public class PoLinkDAOJpaImpl extends JpaDao<Integer, PoLink> implements PoLinkDAO {

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
	private static final long serialVersionUID = 2663955366990151079L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PoLink find(Integer personId, Integer prfLinkId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from PoLink where prfLinkId=:prfLinkId and person.personId=:personId");
		query.setParameter("prfLinkId", prfLinkId);
		query.setParameter("personId", personId);

		List<PoLink> result = query.getResultList();
		if (result.size() == 0) {
			return null;
		} else {
			return result.get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void resetPreferredRoleForPersonTitles(Integer prfLinkId, Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("from PoLink where prfLinkId!=:prfLinkId and person.personId=:personId and preferredRole=:booleanValue");
		query.setParameter("prfLinkId", prfLinkId);  
		query.setParameter("personId", personId);
		query.setParameter("booleanValue", Boolean.TRUE);

		List<PoLink> result = query.getResultList();

		for (int i=0; i<result.size(); i++) {
			PoLink singleResult = result.get(i);
			singleResult.setPreferredRole(Boolean.FALSE);
			getEntityManager().merge(singleResult);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PoLink> getOccupationsDetails(String alias, List<Integer> peopleIds) throws PersistenceException {
		StringBuilder query = new StringBuilder("from PoLink WHERE titleOccList.titleOccId=:titleOccId AND (");
		for(int i = 0; i < peopleIds.size(); i++){
			query.append("person.personId=" + peopleIds.get(i));
			if(i != peopleIds.size()-1){
				query.append(" OR ");
			}
		}
		Query toQuery = getEntityManager().createQuery(query.toString() + ")");
		toQuery.setParameter("titleOccId", Integer.parseInt(alias));
		
		return toQuery.getResultList();
	}
}
