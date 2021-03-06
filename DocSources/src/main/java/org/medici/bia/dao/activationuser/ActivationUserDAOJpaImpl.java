/*
 * ActivationUserDAOJpaImpl.java
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
package org.medici.bia.dao.activationuser;

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
import org.medici.bia.domain.ActivationUser;
import org.springframework.stereotype.Repository;

/**
 * <b>ActivationUserDAOJpaImpl</b> is a default implementation of <b>ActivationUserDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.ActivationUser
 */
@Repository
public class ActivationUserDAOJpaImpl extends JpaDao<String, ActivationUser> implements ActivationUserDAO {
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
	private static final long serialVersionUID = 325665150465723166L;

	/**
	 * 
	 */
	public List<ActivationUser> searchUsersToActivate(ActivationUser activationUser) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<ActivationUser> criteriaQuery = criteriaBuilder.createQuery(ActivationUser.class);
		Root<ActivationUser> root = criteriaQuery.from(ActivationUser.class);

		// Define predicate's elements
		List<Predicate> criteria = new ArrayList<Predicate>();
		ParameterExpression<Boolean> parameterActive = criteriaBuilder.parameter(Boolean.class, "active");
		ParameterExpression<Boolean> parameterMailSended = criteriaBuilder.parameter(Boolean.class, "mailSended");
		criteria.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("active"), parameterActive), criteriaBuilder.equal(root.get("mailSended"), parameterMailSended)));
		criteriaQuery.where(criteria.get(0));

		// Set values in predicate's elements  
		TypedQuery<ActivationUser> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("active", activationUser.getActive());
		typedQuery.setParameter("mailSended", activationUser.getMailSended());

		//Execute query
		return typedQuery.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE ActivationUser SET user.account=:newAccount WHERE user.account=:originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}
}
