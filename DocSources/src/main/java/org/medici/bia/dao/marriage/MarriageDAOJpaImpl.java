/*
  * MarriageDAOJpaImpl.java
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
package org.medici.bia.dao.marriage;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Marriage;
import org.medici.bia.domain.People.Gender;
import org.springframework.stereotype.Repository;

/**
 * <b>MarriageDAOJpaImpl</b> is a default implementation of <b>MarriageDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Repository
public class MarriageDAOJpaImpl extends JpaDao<Integer, Marriage> implements MarriageDAO {

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
	private static final long serialVersionUID = 1983037420201461403L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Marriage> findMarriagesPerson(Integer personId, Gender gender) throws PersistenceException {
        String queryJPQL = "FROM Marriage WHERE ";
        if ((gender == null) || (gender.equals(Gender.X))) {
        	queryJPQL += "husband.personId = :husbandId or wife.personId = :wifeId ";
        } else if (gender.equals(Gender.M)) {
        	queryJPQL += "husband.personId = :husbandId";
        } else if (gender.equals(Gender.F)) {
        	queryJPQL += "wife.personId = :wifeId";
        }
        queryJPQL += " ORDER BY startYear asc, startMonthNum asc, startDay asc";
        
        Query query = getEntityManager().createQuery(queryJPQL);

        if ((gender == null) || (gender.equals(Gender.X))) {
	        query.setParameter("husbandId", personId);
	        query.setParameter("wifeId", personId);
        } else if (gender.equals(Gender.M)) {
	        query.setParameter("husbandId", personId);
        } else if (gender.equals(Gender.F)) {
	        query.setParameter("wifeId", personId);
        }

		List<Marriage> result = query.getResultList();
		
		return result;
	}

	@Override
	public Marriage findMarriagePerson(Integer marriageId, Integer personId, Gender gender) throws PersistenceException {
        String queryJPQL = "FROM Marriage WHERE marriageId = :marriageId";
        if ((gender == null) || (gender.equals(Gender.X))) {
        	queryJPQL += " AND (husband.personId = :husbandId or wife.personId = :wifeId) ";
        } else if (gender.equals(Gender.M)) {
        	queryJPQL += " AND (husband.personId = :husbandId)";
        } else if (gender.equals(Gender.F)) {
        	queryJPQL += " AND (wife.personId = :wifeId)";
        }
    	
        Query query = getEntityManager().createQuery(queryJPQL);
        query.setParameter("marriageId", marriageId);

        if ((gender == null) || (gender.equals(Gender.X))) {
	        query.setParameter("husbandId", personId);
	        query.setParameter("wifeId", personId);
        } else if (gender.equals(Gender.M)) {
	        query.setParameter("husbandId", personId);
        } else if (gender.equals(Gender.F)) {
	        query.setParameter("wifeId", personId);
        }

		return (Marriage)query.getSingleResult();
	}

}
