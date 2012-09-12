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
package org.medici.bia.dao.userhistory;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.people.PeopleDAO;
import org.medici.bia.dao.place.PlaceDAO;
import org.medici.bia.dao.volume.VolumeDAO;
import org.medici.bia.domain.User;
import org.medici.bia.domain.UserHistory;
import org.medici.bia.domain.UserHistory.Action;
import org.medici.bia.domain.UserHistory.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * <b>UserHistoryDAOJpaImpl</b> is a default implementation of
 * <b>UserHistoryDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.UserHistory
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
	@Override
	public Integer deleteMyHistory(User user) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and logicalDelete=false";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteMyHistory(User user, Category category) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and category=:category and logicalDelete=false";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteUserHistory(User user) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and logicalDelete=false";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteUserHistory(User user, Category category) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and category=:category and logicalDelete=false";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserHistory> findHistory(User user, Category category, Integer resultSize) throws PersistenceException {
        String queryString = "FROM UserHistory WHERE user=:user and category=:category and logicalDelete=false ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
        query.setMaxResults(resultSize);
        
		return query.getResultList();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findHistory(User user, Category category, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(user) FROM UserHistory WHERE user=:user and category=:category and logicalDelete=false ";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("user", user);
	        query.setParameter("category", category); 
			page.setTotal(new Long((Long)query.getSingleResult()));
		}

        String objectsQuery = "FROM UserHistory WHERE user=:user and category=:category and logicalDelete=false ";

        paginationFilter = generatePaginationFilterMYSQL(category, paginationFilter);
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
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
		logger.debug("JPQL Query : " + jpql);

        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("user", user);
        query.setParameter("category", category); 
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		page.setList(query.getResultList());

		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page findHistory(User user, Category category, PaginationFilter paginationFilter, Integer resultSize) throws PersistenceException {
		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(user) FROM UserHistory WHERE user=:user and category=:category and logicalDelete=false ";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("user", user);
	        query.setParameter("category", category); 
	        // we need to force to resultSize total if result is bigger
	        Long total = new Long((Long)query.getSingleResult());
	        if (total > resultSize)
	        	total=new Long(resultSize);

	        page.setTotal(total);
		}

        String queryString = "FROM UserHistory WHERE user=:user and category=:category and logicalDelete=false ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(page.getTotal().intValue());
		page.setList(query.getResultList());
        
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserHistory> findHistory(User user, Integer resultSize) {
        String queryString = "FROM UserHistory WHERE user=:user and logicalDelete=false ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("user", user);
        query.setMaxResults(resultSize);
        
		return query.getResultList();
	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public Page findHistory(User user, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
	        String queryString = "SELECT count(user) FROM UserHistory WHERE user=:user and logicalDelete=false ";

	        Query query = getEntityManager().createQuery(queryString);
	        query.setParameter("user", user);
			page.setTotal(new Long((Long)query.getSingleResult()));
		}

        String objectsQuery = "FROM UserHistory WHERE user=:user and logicalDelete=false ";

		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
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
	public UserHistory findCategoryHistoryFromEntity(User user, Category category, Integer primaryKeyId) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder("FROM UserHistory WHERE user=:user AND category=:category ");
        
        switch (category) {
			case DOCUMENT:
				stringBuilder.append(" AND document.entryId=:primaryKeyId");
				break;
			case PEOPLE:
				stringBuilder.append(" AND person.personId=:primaryKeyId");
				break;
			case PLACE:
				stringBuilder.append(" AND place.placeAllId=:primaryKeyId");
				break;
			case VOLUME:
				stringBuilder.append(" AND volume.summaryId=:primaryKeyId");
				break;
			case FORUM:
				stringBuilder.append(" AND forum.id=:primaryKeyId");
				break;
			case FORUM_POST:
				stringBuilder.append(" AND forumPost.id=:primaryKeyId");
				break;
			default:
				break;
		}

        stringBuilder.append(" ORDER BY idUserHistory DESC");

        Query query = getEntityManager().createQuery(stringBuilder.toString());

        query.setParameter("user", user);
        query.setParameter("category", category);
        query.setParameter("primaryKeyId", primaryKeyId);

		List<UserHistory> result = query.getResultList();
		
		if (result.size() >0) {
			return result.get(0);
		} 

		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistory findHistoryFromEntity(User user,Category category, Integer primaryKeyId) throws PersistenceException{
		StringBuilder stringBuilder = new StringBuilder("FROM UserHistory WHERE user=:user ");
		
		switch(category){
			case DOCUMENT:
				stringBuilder.append(" AND document.entryId=:primaryKeyId");
				break;
			case PEOPLE:
				stringBuilder.append(" AND person.personId=:primaryKeyId");
				break;
			case PLACE:
				stringBuilder.append(" AND place.placeAllId=:primaryKeyId");
				break;
			case VOLUME:
				stringBuilder.append(" AND volume.summaryId=:primaryKeyId");
				break;
			case FORUM:
				stringBuilder.append(" AND forum.id=:primaryKeyId");
				break;
			case FORUM_POST:
				stringBuilder.append(" AND forumPost.id=:primaryKeyId");
				break;
			default:
				break;
		}
		stringBuilder.append(" ORDER BY idUserHistory DESC");
		
		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("user", user);
		query.setParameter("primaryKeyId", primaryKeyId);
		
		List<UserHistory> result = query.getResultList();
		
		if(result.size() > 0){
			return result.get(0);
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistory findLastEntry(User user) throws PersistenceException {
        String queryString = "FROM UserHistory WHERE user=:user and logicalDelete=false ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("user", user);
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
	public UserHistory findLastEntry(User user, Category category) {
        String queryString = "FROM UserHistory WHERE user=:user and category=:category and logicalDelete=false ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
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
	public UserHistory findLastEntryBase(User user) throws PersistenceException {
        String queryString = "FROM UserHistory WHERE user=:user and logicalDelete=false  AND (category LIKE 'DOCUMENT' OR category LIKE 'VOLUME' OR category LIKE 'PEOPLE' OR category LIKE 'PLACE') ORDER BY dateAndTime DESC";

        Query query = getEntityManager().createQuery(queryString);

        query.setParameter("user", user);
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
	public UserHistory findNextCategoryHistoryCursor(User user, Category category, Integer idUserHistory) throws PersistenceException {
		// -- Next
		// SELECT * FROM tblUserHistory WHERE user.account='lpasquinelli' AND idUserHistory > 15 and entryId is not null ORDER BY idUserHistory ASC LIMIT 1
		StringBuilder stringBuilder = new StringBuilder("FROM UserHistory WHERE user.account='");
		stringBuilder.append(user.getAccount());
		stringBuilder.append("'");
		stringBuilder.append(" AND idUserHistory > ");
		stringBuilder.append(idUserHistory);        

        switch (category) {
			case DOCUMENT:
				stringBuilder.append(" AND document is not null ");
				break;
			case PEOPLE:
				stringBuilder.append(" AND person is not null ");
				break;
			case PLACE:
				stringBuilder.append(" AND place is not null ");
				break;
			case VOLUME:
				stringBuilder.append(" AND volume is not null ");
				break;
			case FORUM:
				stringBuilder.append(" AND forum is not null ");
				break;
			case FORUM_POST:
				stringBuilder.append(" AND forumPost is not null ");
				break;
			default:
				break;
		}
	
        stringBuilder.append(" ORDER BY idUserHistory ASC LIMIT 1");
	
	    Query query = getEntityManager().createQuery(stringBuilder.toString());	
        query.setMaxResults(1);

        List<UserHistory> result = query.getResultList();
		
		if (result.size()>0) {
			return result.get(0);
		} 
	
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistory findNextHistoryCursor(User user, Integer idUserHistory) throws PersistenceException {
		// -- Next
		// SELECT * FROM tblUserHistory WHERE user.account='lpasquinelli' AND idUserHistory > 15 and entryId is not null ORDER BY idUserHistory ASC LIMIT 1
		StringBuilder queryString = new StringBuilder("FROM UserHistory WHERE user.account='");
		queryString.append(user.getAccount());
		queryString.append("'");
		queryString.append(" AND (category NOT LIKE '");
		queryString.append(Category.FORUM.name());
		queryString.append("' AND category NOT LIKE '");
		queryString.append(Category.FORUM_TOPIC.name());
		queryString.append("' AND category NOT LIKE '");
		queryString.append(Category.FORUM_POST.name());
		queryString.append("' AND category NOT LIKE '");
		queryString.append(Category.MARKED_LIST.name());
		queryString.append("')");
		queryString.append(" AND idUserHistory > ");
		queryString.append(idUserHistory);        

        queryString.append(" ORDER BY idUserHistory ASC LIMIT 1");
	
	    Query query = getEntityManager().createQuery(queryString.toString());	
        query.setMaxResults(1);

        List<UserHistory> result = query.getResultList();
		
		if (result.size()>0) {
			return result.get(0);
		} 
	
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistory findPreviousCategoryHistoryCursor(User user, Category category, Integer idUserHistory) throws PersistenceException {
		// -- Previous
		// SELECT * FROM tblUserHistory WHERE user.account='lpasquinelli' AND idUserHistory < 15 and entryId is not null ORDER BY idUserHistory DESC LIMIT 1
		StringBuilder stringBuilder = new StringBuilder("FROM UserHistory WHERE user.account='");
		stringBuilder.append(user.getAccount());
		stringBuilder.append("' AND idUserHistory < ");
		stringBuilder.append(idUserHistory);
        
        switch (category) {
			case DOCUMENT:
				stringBuilder.append(" AND document is not null ");
				break;
			case PEOPLE:
				stringBuilder.append(" AND person is not null ");
				break;
			case PLACE:
				stringBuilder.append(" AND place is not null ");
				break;
			case VOLUME:
				stringBuilder.append(" AND volume is not null ");
				break;
			case FORUM:
				stringBuilder.append(" AND forum is not null ");
				break;
			case FORUM_POST:
				stringBuilder.append(" AND forumPost is not null ");
				break;

			default:
				break;
		}

        stringBuilder.append(" ORDER BY idUserHistory DESC LIMIT 1");

        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setMaxResults(1);

		List<UserHistory> result = query.getResultList();
		
		if (result.size()>0) {
			return result.get(0);
		} 

		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserHistory findPreviousHistoryCursor(User user, Integer idUserHistory) throws PersistenceException {
		// -- Previous
		// SELECT * FROM tblUserHistory WHERE user.account='lpasquinelli' AND idUserHistory < 15 and entryId is not null ORDER BY idUserHistory DESC LIMIT 1
		StringBuilder queryString = new StringBuilder("FROM UserHistory WHERE user.account='");
		queryString.append(user.getAccount());
		queryString.append("'");
		queryString.append(" AND (category NOT LIKE '");
		queryString.append(Category.FORUM.name());
		queryString.append("' AND category NOT LIKE '");
		queryString.append(Category.FORUM_TOPIC.name());
		queryString.append("' AND category NOT LIKE '");
		queryString.append(Category.FORUM_POST.name());
		queryString.append("' AND category NOT LIKE '");
		queryString.append(Category.MARKED_LIST.name());
		queryString.append("')");
		queryString.append(" AND idUserHistory < ");
		queryString.append(idUserHistory);
        
        queryString.append(" ORDER BY idUserHistory DESC LIMIT 1");

        Query query = getEntityManager().createQuery(queryString.toString());
        query.setMaxResults(1);

		List<UserHistory> result = query.getResultList();
		
		if (result.size()>0) {
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
			UserHistory lastUserHistory = findLastEntry(entity.getUser(), entity.getCategory());
			
			if (lastUserHistory != null) {
				if (lastUserHistory.getCategory().equals(entity.getCategory())) {
					if (lastUserHistory.getCategory().equals(Category.DOCUMENT)) {
						// if document is not the same, we persist action 
						if (!lastUserHistory.getDocument().getEntryId().equals(entity.getDocument().getEntryId())) {
							super.persist(entity);
						} else {
							if (((entity.getAction().equals(Action.DELETE)) || (entity.getAction().equals(Action.UNDELETE)))) {
								super.persist(entity);
							} else {
								// if document is not the same, we persist action
								if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
									super.persist(entity);
								}
							}
							//otherwise we dont' persist
						}				
					} else if (lastUserHistory.getCategory().equals(Category.PEOPLE)) {
						// if person is not the same, we persist action 
						if (!lastUserHistory.getPerson().getPersonId().equals(entity.getPerson().getPersonId())) {
							super.persist(entity);
						} else {
							if (((entity.getAction().equals(Action.DELETE)) || (entity.getAction().equals(Action.UNDELETE)))) {
								super.persist(entity);
							} else {
								// if person is not the same, we persist action
								if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
									super.persist(entity);
								}
							}
							//otherwise we dont' persist
						}				
					} else if (lastUserHistory.getCategory().equals(Category.PLACE)) {
						// if place is not the same, we persist action 
						if (!lastUserHistory.getPlace().getPlaceAllId().equals(entity.getPlace().getPlaceAllId())) {
							super.persist(entity);
						} else {
							if (((entity.getAction().equals(Action.DELETE)) || (entity.getAction().equals(Action.UNDELETE)))) {
								super.persist(entity);
							} else {
								// if place is not the same, we persist action
								if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
									super.persist(entity);
								}
							}
							//otherwise we dont' persist
						}				
					} else if (lastUserHistory.getCategory().equals(Category.VOLUME)) {
						// if volume is not the same, we persist action 
						if (!lastUserHistory.getVolume().getSummaryId().equals(entity.getVolume().getSummaryId())) {
							super.persist(entity);
						} else {
							if (((entity.getAction().equals(Action.DELETE)) || (entity.getAction().equals(Action.UNDELETE)))) {
								super.persist(entity);
							} else {
								// if volume is not the same, we persist action
								if ((lastUserHistory.getAction().equals(Action.VIEW)) && (entity.getAction().equals(Action.MODIFY))) {
									super.persist(entity);
								}
							}
							//otherwise we dont' persist
						}				
					}  else if (lastUserHistory.getCategory().equals(Category.FORUM)) {
						// if volume is not the same, we persist action 
						if (!lastUserHistory.getForum().getForumId().equals(entity.getForum().getForumId())) {
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
					} else if (lastUserHistory.getCategory().equals(Category.FORUM_TOPIC)) {
						// if volume is not the same, we persist action 
						if (!lastUserHistory.getForumTopic().getTopicId().equals(entity.getForumTopic().getTopicId())) {
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
					} else if (lastUserHistory.getCategory().equals(Category.FORUM_POST)) {
						// if volume is not the same, we persist action 
						if (!lastUserHistory.getForumPost().getPostId().equals(entity.getForumPost().getPostId())) {
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
	 * {@inheritDoc}
	 */
	@Override
	public Integer restoreMyHistory(User user) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and logicalDelete=true";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer restoreMyHistory(User user, Category category) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and category=:category and logicalDelete=true";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer restoreUserHistory(User user) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and logicalDelete=true";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        
		return query.executeUpdate();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer restoreUserHistory(User user, Category category) throws PersistenceException {
        String updateString = "UPDATE UserHistory set logicaldelete=true WHERE user=:user and category=:category and logicalDelete=true";

        Query query = getEntityManager().createQuery(updateString);

        query.setParameter("user", user);
        query.setParameter("category", category); 
        
		return query.executeUpdate();	
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
