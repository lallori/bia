/*
 * UserMarkedListElementDAOJpaImpl.java
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
package org.medici.bia.dao.usermarkedlistelement;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserMarkedListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <b>UserMarkedListElementDAOJpaImpl</b> is a default implementation of
 * <b>UserMarkedListDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.UserHistory
 */
@Repository
public class UserMarkedListElementDAOJpaImpl extends JpaDao<Integer, UserMarkedListElement> implements UserMarkedListElementDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4129744158492583655L;

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
	private final Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private PeopleDAO peopleDAO;
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private VolumeDAO volumeDAO;

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserMarkedListElement findDocumentInMarkedList(Integer idMarkedList, Integer entryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList AND document.entryId=:entryId");
		query.setParameter("idMarkedList", idMarkedList);
		query.setParameter("entryId", entryId);
		
		query.setMaxResults(1);
		List<UserMarkedListElement> result = query.getResultList();
		if(result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findMarkedList(User user, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		if(paginationFilter.getTotal() == null){
			String queryString = "SELECT count(id) FROM UserMarkedListElement WHERE idMarkedList IN (SELECT idMarkedList FROM UserMarkedList WHERE user=:user)";
			
			 Query query = getEntityManager().createQuery(queryString);
		     query.setParameter("user", user);
		     page.setTotal(new Long((Long)query.getSingleResult()));
		}
		
		String objectsQuery = "FROM UserMarkedListElement WHERE idMarkedList IN (SELECT idMarkedList FROM UserMarkedList WHERE user=:user)";
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		String jpql = objectsQuery + getOrderByQuery(paginationFilter.getSortingCriterias());
		logger.debug("JPQL Query : " + jpql);

        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("user", user);
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		page.setList(query.getResultList());

		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserMarkedListElement findPersonInMarkedList(Integer idMarkedList, Integer personId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList AND person.personId=:personId");
		query.setParameter("idMarkedList", idMarkedList);
		query.setParameter("personId", personId);
		
		query.setMaxResults(1);
		List<UserMarkedListElement> result = query.getResultList();
		if(result.size() > 0) {
			return result.get(0);
		}

		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserMarkedListElement findPlaceInMarkedList(Integer idMarkedList, Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList AND place.placeAllId=:placeAllId");
		query.setParameter("idMarkedList", idMarkedList);
		query.setParameter("placeAllId", placeAllId);
		
		query.setMaxResults(1);
		List<UserMarkedListElement> result = query.getResultList();
		if(result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserMarkedListElement findVolumeInMarkedList(Integer idMarkedList, Integer summaryId) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList AND volume.summaryId=:summaryId");
		query.setParameter("idMarkedList", idMarkedList);
		query.setParameter("summaryId", summaryId);
		
		query.setMaxResults(1);
		List<UserMarkedListElement> result = query.getResultList();
		if(result.size() > 0) {
			return result.get(0);
		}

		return null;
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
				paginationFilter.addSortingCriteria("dateCreated", paginationFilter.getSortingDirection());
				break;
			case 1:
				paginationFilter.addSortingCriteria("document.entryId", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("person.personId", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("place.placeAllId", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("volume.summaryId", paginationFilter.getSortingDirection());
				break;
			case 2:  
				paginationFilter.addSortingCriteria("document.volume.volNum", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("document.volume.volLetExt", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("document.folioNum", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("document.folioMod", paginationFilter.getSortingDirection());
				break;
			case 3:
				paginationFilter.addSortingCriteria("place.placeNameFull", paginationFilter.getSortingDirection());
				break;
			case 4:
				paginationFilter.addSortingCriteria("person.mapNameLf", paginationFilter.getSortingDirection());
				break;
			default:
				paginationFilter.addSortingCriteria("dateCreated", paginationFilter.getSortingDirection());
				break;
		}

		return paginationFilter;
	}
	
	/**
	 * @return the documentDAO
	 */
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserMarkedListElement> getMarkedListElements(Integer idMarkedList, List<Integer> idElements) throws PersistenceException {
		StringBuilder query = new StringBuilder("FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList AND (");
		for(int i = 0; i < idElements.size(); i++){
			query.append("id=" + idElements.get(i));
			if(i != idElements.size()-1){
				query.append(" OR ");
			}
		}
		Query toQuery = getEntityManager().createQuery(query.toString() + ")");
		toQuery.setParameter("idMarkedList", idMarkedList);
		
		if(toQuery.getResultList().size() == 0){
			return null;
		}else{
			return toQuery.getResultList();
		}
	}

	/**
	 * @return the peopleDAO
	 */
	public PeopleDAO getPeopleDAO() {
		return peopleDAO;
	}

	/**
	 * @return the placeDAO
	 */
	public PlaceDAO getPlaceDAO() {
		return placeDAO;
	}

	/**
	 * @return the volumeDAO
	 */
	public VolumeDAO getVolumeDAO() {
		return volumeDAO;
	}


	/**
	 * @param documentDAO the documentDAO to set
	 */
	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	/**
	 * @param peopleDAO the peopleDAO to set
	 */
	public void setPeopleDAO(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}

	/**
	 * @param placeDAO the placeDAO to set
	 */
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

	/**
	 * @param volumeDAO the volumeDAO to set
	 */
	public void setVolumeDAO(VolumeDAO volumeDAO) {
		this.volumeDAO = volumeDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeAllMarkedListElements(Integer idMarkedList) throws PersistenceException {
		Query query = getEntityManager().createQuery("DELETE FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList");
		query.setParameter("idMarkedList", idMarkedList);
		
		return query.executeUpdate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer removeMarkedListElements(Integer idMarkedList, List<Integer> idElements) throws PersistenceException {
		StringBuilder query = new StringBuilder("DELETE FROM UserMarkedListElement WHERE userMarkedList.idMarkedList=:idMarkedList AND (");
		for(int i = 0; i < idElements.size(); i++){
			query.append("id=" + idElements.get(i));
			if(i != idElements.size()-1){
				query.append(" OR ");
			}
		}
		Query toQuery = getEntityManager().createQuery(query.toString() + ")");
		toQuery.setParameter("idMarkedList", idMarkedList);
		
		return toQuery.executeUpdate();
	}
}
