/*
 * AnnotationDAOJpaImpl.java
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
package org.medici.bia.dao.annotation;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.User;
import org.springframework.stereotype.Repository;

/**
 * <b>AnnotationDAOJpaImpl</b> is a default implementation of <b>AnnotationDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.Annotation
 */
@Repository
public class AnnotationDAOJpaImpl extends JpaDao<Integer, Annotation> implements AnnotationDAO {

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
	private static final long serialVersionUID = -6882494398469472058L;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Annotation> findAnnotationsByImage(String imageName) throws PersistenceException {
		String jpql = "FROM Annotation WHERE image.imageName = :imageName AND (type != 'PERSONAL' OR (type = 'PERSONAL' AND user.account = :account)) AND logicalDelete = false order by annotationId desc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("imageName", imageName);

		return (List<Annotation>) query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Annotation> findAnnotationByImageAndUser(String imageName, User user) throws PersistenceException {
		String jpql = "FROM Annotation WHERE image.imageName = :imageName AND (type != 'PERSONAL' OR (type = 'PERSONAL' AND user.account = :account)) AND logicalDelete = false order by annotationId desc";
		Query query = getEntityManager().createQuery(jpql);
        query.setParameter("imageName", imageName);
        query.setParameter("account", user.getAccount());

		return (List<Annotation>) query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Annotation findByAnnotationId(Integer annotationId) throws PersistenceException {
		String jpql = "FROM Annotation WHERE annotationId = :annotationId and logicalDelete = false";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("annotationId", annotationId);

		List<Annotation> resultList = (List<Annotation>) query.getResultList();
		
		if (resultList.size() == 1){
			return resultList.get(0);
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Annotation> findForumAnnotations() throws PersistenceException {
		String jpql = "FROM Annotation WHERE type IN ('" + Annotation.Type.GENERAL + "','" + Annotation.Type.PALEOGRAPHY + "') AND logicalDelete = false ORDER BY user ASC";
		
		Query query = getEntityManager().createQuery(jpql);

		List<Annotation> resultList = (List<Annotation>) query.getResultList();
		return resultList;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findPersonalAnnotations(User user, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		if(paginationFilter.getTotal() == null){
			String queryString = "SELECT count(annotationId) FROM Annotation WHERE type = 'Personal' AND user = :user AND logicalDelete = false";
			
			 Query query = getEntityManager().createQuery(queryString);
		     query.setParameter("user", user);
		     page.setTotal(new Long((Long)query.getSingleResult()));
		}
		
		String objectsQuery = "FROM Annotation WHERE type = 'Personal' AND user = :user AND logicalDelete = false";
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		String jpql = objectsQuery + getOrderByQuery(paginationFilter.getSortingCriterias());

        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("user", user);
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		page.setList(query.getResultList());

		return page;
	}
	
	/**
	 * 
	 * @param paginationFilter
	 * @param searchType
	 * @return
	 */
	protected PaginationFilter generatePaginationFilterMYSQL(PaginationFilter paginationFilter) {
		switch (paginationFilter.getSortingColumn()) {
			case 0:
				paginationFilter.addSortingCriteria("lastUpdate", paginationFilter.getSortingDirection());
				break;
			case 1:
				paginationFilter.addSortingCriteria("title", paginationFilter.getSortingDirection());
				break;
			case 2:  
				paginationFilter.addSortingCriteria("text", paginationFilter.getSortingDirection());
				break;
			case 3:
				paginationFilter.addSortingCriteria("image.volNum", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("image.volLetExt", paginationFilter.getSortingDirection());
				break;
			case 4:
				paginationFilter.addSortingCriteria("image.imageType", paginationFilter.getSortingDirection());
				break;
			case 5:
				paginationFilter.addSortingCriteria("image.imageProgTypeNum", paginationFilter.getSortingDirection());
				break;
			default:
				paginationFilter.addSortingCriteria("lastUpdate", paginationFilter.getSortingDirection());
				break;
		}

		return paginationFilter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException {
		String jpql = "UPDATE Annotation SET user.account = :newAccount WHERE user.account = :originalAccount";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("newAccount", newAccount);
		query.setParameter("originalAccount", originalAccount);

		return query.executeUpdate();
	}
}
