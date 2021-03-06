/*
 * LockedUserDAOJpaImpl.java
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
package org.medici.bia.dao.lockeduser;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.LockedUser;
import org.springframework.stereotype.Repository;

/**
 * <b>LockedUserDAOJpaImpl</b> is a default implementation of <b>LockedUserDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.LockedUser
 */
@Repository
public class LockedUserDAOJpaImpl extends JpaDao<Integer, LockedUser> implements LockedUserDAO {

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
	private static final long serialVersionUID = -240582362854135368L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LockedUser findByAccount(String account) throws PersistenceException {
		String jpql = "FROM LockedUser WHERE user.account=:account";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("account", account);

		List<LockedUser> result = query.getResultList();
		
		if (result.size() ==1) {
			return result.get(0);
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LockedUser> searchLockedUsers(LockedUser lockedUser) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<LockedUser> criteriaQuery = criteriaBuilder.createQuery(LockedUser.class);
		Root<LockedUser> root = criteriaQuery.from(LockedUser.class);

		// Define predicate's elements
		List<Predicate> criteria = new ArrayList<Predicate>();
		ParameterExpression<Boolean> parameterActive = criteriaBuilder.parameter(Boolean.class, "unlocked");
		ParameterExpression<Boolean> parameterMailSended = criteriaBuilder.parameter(Boolean.class, "mailSended");
		ParameterExpression<Boolean> parameterMailUnlockSended = criteriaBuilder.parameter(Boolean.class, "mailUnlockSended");
		criteria.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("unlocked"), parameterActive), criteriaBuilder.equal(root.get("mailSended"), parameterMailSended), criteriaBuilder.equal(root.get("mailUnlockSended"), parameterMailUnlockSended)));
		criteriaQuery.where(criteria.get(0));

		// Set values in predicate's elements  
		TypedQuery<LockedUser> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("unlocked", lockedUser.getUnlocked());
		typedQuery.setParameter("mailSended", lockedUser.getMailSended());
		typedQuery.setParameter("mailUnlockSended", lockedUser.getMailUnlockSended());

		//Execute query
		return typedQuery.getResultList();
	}

}
