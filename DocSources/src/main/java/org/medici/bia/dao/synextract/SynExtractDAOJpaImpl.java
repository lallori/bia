/*
 * SynExtractDAOJpaImpl.java
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
package org.medici.bia.dao.synextract;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.SynExtract;
import org.springframework.stereotype.Repository;

/**
 * Implementazione di esempio di un dao applicativo. La classe deve estendere il
 * jpaDao che fornisce i servizi piu' comuni (persit, findById e delete) JPA
 * DAO.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
@Repository
public class SynExtractDAOJpaImpl extends JpaDao<Integer, SynExtract> implements SynExtractDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7705355526671429408L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public SynExtract findByEntryId(Integer entryId) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<SynExtract> criteriaQuery = criteriaBuilder.createQuery(SynExtract.class);
		Root from = criteriaQuery.from(SynExtract.class);
		Path<Object> path = from.join("document").get("entryId");
		 
		CriteriaQuery<SynExtract> select = criteriaQuery.select(from);
		select.where(criteriaBuilder.equal(path, entryId));
		 
		TypedQuery<SynExtract> typedQuery = getEntityManager().createQuery(select);
		List<SynExtract> result = typedQuery.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		}

		return null;
	}
}
