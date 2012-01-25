/*
 * UserHistoryDAOJpaImpl.java
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
package org.medici.docsources.dao.userhistory;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.PaginationFilter.Order;
import org.medici.docsources.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.dao.document.DocumentDAO;
import org.medici.docsources.dao.people.PeopleDAO;
import org.medici.docsources.dao.place.PlaceDAO;
import org.medici.docsources.dao.volume.VolumeDAO;
import org.medici.docsources.domain.UserHistory;
import org.medici.docsources.domain.UserHistory.Category;
import org.medici.docsources.domain.UserHistory.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * <b>UserHistoryDAOJpaImpl</b> is a default implementation of
 * <b>UserHistoryDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.LastHistory
 */
@Repository
public class UserHistoryDAOJpaImpl extends JpaDao<Integer, UserHistory> implements UserHistoryDAO {
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
	private static final long serialVersionUID = 1361144582997153273L;

	@Autowired
	private DocumentDAO documentDAO;
	private final Logger logger = Logger.getLogger(this.getClass());
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
	public List<UserHistory> findHistory(Category category, Integer resultSize) throws PersistenceException {
        String queryString = "FROM UserHistory WHERE username=:username and category=:category ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("category", category); 
        query.setMaxResults(resultSize);
        
		return query.getResultList();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findHistory(Category category, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(username) FROM UserHistory WHERE username=:username and category=:category";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
	        query.setParameter("category", category); 
			page.setTotal(new Long((Long)query.getSingleResult()));
		}

        String objectsQuery = "FROM UserHistory WHERE username=:username and category=:category ";

        paginationFilter = generatePaginationFilterMYSQL(category, paginationFilter);
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if (sortingCriterias.size() > 0) {
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn());
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				}
			}
		}
		
		String jpql = objectsQuery + orderBySQL.toString();
		logger.info("JPQL Query : " + jpql);

        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("category", category); 
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
	public List<UserHistory> findHistory(Integer resultSize) {
        String queryString = "FROM UserHistory WHERE username=:username ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setMaxResults(resultSize);
        
		return query.getResultList();
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Page findHistory(PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(username) FROM UserHistory WHERE username=:username";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
			page.setTotal(new Long((Long)query.getSingleResult()));
		}

        String objectsQuery = "FROM UserHistory WHERE username=:username";

		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if (sortingCriterias.size() > 0) {
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn());
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} else {
					orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				}
			}
		}
		
		String jpql = objectsQuery + orderBySQL.toString();
		logger.info("JPQL Query : " + jpql);

        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
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
	public UserHistory findLastEntry() throws PersistenceException {
        String queryString = "FROM UserHistory WHERE username=:username ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setMaxResults(1);

		List<UserHistory> result = query.getResultList();
		
		if (result.size() == 1) {
			return result.get(0);
		} 

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistory findLastEntry(Category category) {
        String queryString = "FROM UserHistory WHERE username=:username and category=:category ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("username", ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        query.setParameter("category", category); 
        query.setMaxResults(1);

		List<UserHistory> result = query.getResultList();
		
		if (result.size() == 1) {
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
	protected PaginationFilter generatePaginationFilterMYSQL(Category category, PaginationFilter paginationFilter) {
		if (category.equals(Category.DOCUMENT)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("action", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("document.volume.volNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("document.volume.volLetExt", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("document.folioNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("document.folioMod", paginationFilter.getSortingDirection());
						break;
					case 3:  
						paginationFilter.addSortingCriteria("document.senderPeople.mapNameLf", paginationFilter.getSortingDirection());
						break;
					case 4:  
						paginationFilter.addSortingCriteria("document.recipientPeople.mapNameLf", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
				}
			}
		} else if (category.equals(Category.PEOPLE)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("action", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("person.mapNameLf", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("person.bornYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("person.bornMonth.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("person.bornDay", paginationFilter.getSortingDirection());
						break;
					case 4:
						paginationFilter.addSortingCriteria("person.deathYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("person.deathMonth.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("person.deathDay", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
				}		
			}
		} else if (category.equals(Category.PLACE)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("action", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("place.placeNameFull", paginationFilter.getSortingDirection());
						break;
					case 3:
						break;
					default:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
				}
			}
		} else if (category.equals(Category.VOLUME)) {
			if (!ObjectUtils.toString(paginationFilter.getSortingColumn()).equals("")) {
				switch (paginationFilter.getSortingColumn()) {
					case 0:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
					case 1:
						paginationFilter.addSortingCriteria("action", paginationFilter.getSortingDirection());
						break;
					case 2:
						paginationFilter.addSortingCriteria("volume.volNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volume.volLetExt", paginationFilter.getSortingDirection());
						break;
					case 3:
						paginationFilter.addSortingCriteria("volume.serieList.title", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volume.serieList.subTitle1", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volume.serieList.subTitle2", paginationFilter.getSortingDirection());
						break;
					case 4:
						paginationFilter.addSortingCriteria("volume.startYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("volume.startMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volume.startDay", paginationFilter.getSortingDirection());
						break;
					case 5:
						paginationFilter.addSortingCriteria("volume.endYear", paginationFilter.getSortingDirection());
						//Month is an entity, so we don't have field with suffix 
						paginationFilter.addSortingCriteria("volume.endMonthNum.monthNum", paginationFilter.getSortingDirection());
						paginationFilter.addSortingCriteria("volume.endDay", paginationFilter.getSortingDirection());
						break;
					case 6:
						paginationFilter.addSortingCriteria("volume.digitized", paginationFilter.getSortingDirection());
						break;
					default:
						paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
						break;
				}
			}
		}

		return paginationFilter;
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
				paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
				break;
			case 1:
				paginationFilter.addSortingCriteria("category", paginationFilter.getSortingDirection());
				break;
			case 2:
				paginationFilter.addSortingCriteria("action", paginationFilter.getSortingDirection());
				break;
			case 3:
				paginationFilter.addSortingCriteria("document.volume.volNum", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("document.volume.volLetExt", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("document.folioNum", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("document.folioMod", paginationFilter.getSortingDirection());
				break;
			case 4:  
				paginationFilter.addSortingCriteria("volume.volNum", paginationFilter.getSortingDirection());
				paginationFilter.addSortingCriteria("volume.volLetExt", paginationFilter.getSortingDirection());
				break;
			case 5:
				paginationFilter.addSortingCriteria("place.placeNameFull", paginationFilter.getSortingDirection());
				break;
			case 6:
				paginationFilter.addSortingCriteria("person.mapNameLf", paginationFilter.getSortingDirection());
				break;
			default:
				paginationFilter.addSortingCriteria("dateAndTime", paginationFilter.getSortingDirection());
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
	 * {@inheritDoc}
	 */
	@Override
	public void persist(UserHistory entity) throws PersistenceException {
		try {
			UserHistory lastUserHistory = findLastEntry(entity.getCategory());
			
			if (lastUserHistory != null) {
				if (lastUserHistory.getCategory().equals(entity.getCategory())) {
					if (lastUserHistory.getCategory().equals(Category.DOCUMENT)) {
						// if document is not the same, we persist action 
						if (!lastUserHistory.getDocument().getEntryId().equals(entity.getDocument().getEntryId())) {
							super.persist(entity);
						} else {
							// if document is not the same, we persist action
							if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
								super.persist(entity);
							} else if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.DELETE))) {
								super.persist(entity);
							}
							//otherwise we dont' persist
						}				
					} else if (lastUserHistory.getCategory().equals(Category.PEOPLE)) {
						// if person is not the same, we persist action 
						if (!lastUserHistory.getPerson().getPersonId().equals(entity.getPerson().getPersonId())) {
							super.persist(entity);
						} else {
							// if person is not the same, we persist action
							if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
								super.persist(entity);
							} else if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.DELETE))) {
								super.persist(entity);
							}
							//otherwise we dont' persist
						}				
					} else if (lastUserHistory.getCategory().equals(Category.PLACE)) {
						// if place is not the same, we persist action 
						if (!lastUserHistory.getPlace().getPlaceAllId().equals(entity.getPlace().getPlaceAllId())) {
							super.persist(entity);
						} else {
							// if place is not the same, we persist action
							if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
								super.persist(entity);
							} else if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.DELETE))) {
								super.persist(entity);
							}
							//otherwise we dont' persist
						}				
					} else if (lastUserHistory.getCategory().equals(Category.VOLUME)) {
						// if volume is not the same, we persist action 
						if (!lastUserHistory.getVolume().getSummaryId().equals(entity.getVolume().getSummaryId())) {
							super.persist(entity);
						} else {
							// if volume is not the same, we persist action
							if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
								super.persist(entity);
							} else if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.DELETE))) {
								super.persist(entity);
							}
							//otherwise we dont' persist
						}				
					}
				}
					

			} else {
				//this case is for first access
				super.persist(entity);
			}

		} catch (PersistenceException persistenceException) {
			logger.error("Exception during persisting history", persistenceException);
		}
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
}