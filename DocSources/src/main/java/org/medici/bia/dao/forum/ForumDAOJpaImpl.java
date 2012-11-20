/*
 * ForumDAOJpaImpl.java
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
package org.medici.bia.dao.forum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.util.ForumUtils;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.Forum.Status;
import org.medici.bia.domain.Forum.SubType;
import org.medici.bia.domain.Forum.Type;
import org.medici.bia.domain.People;
import org.medici.bia.domain.Place;
import org.medici.bia.domain.Schedone;
import org.medici.bia.domain.Volume;
import org.springframework.stereotype.Repository;

/**
 * <b>ForumDAOJpaImpl</b> is a default implementation of <b>ForumDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.bia.domain.Forum
 * {@link http://yensdesign.com/2008/10/making-mysql-forum-database-from-scratch/}
 * 
 */
@Repository
public class ForumDAOJpaImpl extends JpaDao<Integer, Forum> implements ForumDAO {
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
	private static final long serialVersionUID = 5977718269527833285L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum addNewDocumentForum(Forum forumParent, Document document) throws PersistenceException {
		Forum forum = new Forum();
		forum.setDateCreated(new Date());
		forum.setTitle("DocID #" + document.getEntryId());
		if(document.getFolioNum() == null){
			forum.setDescription("Volume " + document.getVolume().getMDP() + " - Folio NNF");
		}else if(document.getFolioMod() != null){
			forum.setDescription("Volume " + document.getVolume().getMDP() + " - Folio " + document.getFolioNum() + document.getFolioMod());
		}else{
			forum.setDescription("Volume " + document.getVolume().getMDP() + " - Folio " + document.getFolioNum());
		}
		forum.setForumParent(forumParent);
		forum.setLastPost(null);
		forum.setLastUpdate(new Date());

		forum.setDocument(document);
		forum.setPerson(null);
		forum.setPlace(null);
		forum.setVolume(null);
		
		forum.setDispositionOrder(new Integer(0));
		forum.setPostsNumber(new Integer(0));
		forum.setStatus(Status.ONLINE);
		forum.setTopicsNumber(new Integer(0));
		forum.setSubForumsNumber(new Integer(0));
		forum.setType(Type.FORUM);
		forum.setSubType(SubType.DOCUMENT);
		forum.setLogicalDelete(Boolean.FALSE);

		forum.setHierarchyLevel(forum.getForumParent().getHierarchyLevel()+1);
		forum.setFullPath(forum.getForumParent().getFullPath());

		getEntityManager().persist(forum);

        return forum;
	}

	/**
	 * 
	 * @param forum
	 * @return
	 * @throws PersistenceException
	 */
	public Forum addNewForum(Forum forum) throws PersistenceException {
		getEntityManager().persist(forum);

        return forum;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum addNewPersonForum(Forum forumParent, People person) throws PersistenceException {
		Forum forum = new Forum();
		forum.setDateCreated(new Date());
		forum.setDescription(person.getMapNameLf());
		forum.setForumParent(forumParent);
		forum.setLastPost(null);
		forum.setLastUpdate(new Date());

		forum.setDocument(null);
		forum.setPerson(person);
		forum.setPlace(null);
		forum.setVolume(null);
		
		forum.setDispositionOrder(new Integer(0));
		forum.setPostsNumber(new Integer(0));
		forum.setStatus(Status.ONLINE);
		forum.setTopicsNumber(new Integer(0));
		forum.setSubForumsNumber(new Integer(0));
		forum.setType(Type.FORUM);
		forum.setTitle(person.getMapNameLf());
		forum.setSubType(SubType.PEOPLE);
		forum.setLogicalDelete(Boolean.FALSE);

		forum.setHierarchyLevel(forum.getForumParent().getHierarchyLevel()+1);
		forum.setFullPath(forum.getForumParent().getFullPath());

		getEntityManager().persist(forum);

        return forum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum addNewPlaceForum(Forum forumParent, Place place) throws PersistenceException {
		Forum forum = new Forum();
		forum.setDateCreated(new Date());
		forum.setDescription(place.getPlaceNameFull());
		forum.setTitle(place.getPlaceNameFull());
		forum.setForumParent(forumParent);
		forum.setLastPost(null);
		forum.setLastUpdate(new Date());

		forum.setDocument(null);
		forum.setPerson(null);
		forum.setPlace(place);
		forum.setVolume(null);
		
		forum.setDispositionOrder(new Integer(0));
		forum.setPostsNumber(new Integer(0));
		forum.setStatus(Status.ONLINE);
		forum.setTopicsNumber(new Integer(0));
		forum.setSubForumsNumber(new Integer(0));
		forum.setType(Type.FORUM);
		forum.setSubType(SubType.PLACE);
		forum.setLogicalDelete(Boolean.FALSE);

		forum.setHierarchyLevel(forum.getForumParent().getHierarchyLevel()+1);
		forum.setFullPath(forum.getForumParent().getFullPath());

		getEntityManager().persist(forum);

        return forum;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Forum addNewVolumeForum(Forum forumParent, Volume volume, Schedone schedone) throws PersistenceException {
		Forum forum = new Forum();
		forum.setDateCreated(new Date());
		if (volume.getSerieList() == null ) {
			forum.setDescription("");
		}
		else {
			forum.setDescription(volume.getSerieList().toString());
		}
		 if (schedone == null) {
			 	forum.setTitle("Mediceo del Principato: Volume n. " + volume.toString());
	        } 
		 else {
			 	forum.setTitle(schedone.getFondo() + ": Volume n. " + volume.toString());
		 }
		forum.setForumParent(forumParent);
		forum.setLastPost(null);
		forum.setLastUpdate(new Date());

		forum.setDocument(null);
		forum.setPerson(null);
		forum.setPlace(null);
		forum.setVolume(volume);
		
		forum.setDispositionOrder(new Integer(0));
		forum.setPostsNumber(new Integer(0));
		forum.setStatus(Status.ONLINE);
		forum.setTopicsNumber(new Integer(0));
		forum.setSubForumsNumber(new Integer(0));
		forum.setType(Type.FORUM);
		forum.setSubType(SubType.VOLUME);
		forum.setLogicalDelete(Boolean.FALSE);

		forum.setHierarchyLevel(forum.getForumParent().getHierarchyLevel()+1);
		forum.setFullPath(forum.getForumParent().getFullPath());

		getEntityManager().persist(forum);

        return forum;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countTotalActive() throws PersistenceException {
		String countQuery = "SELECT COUNT(*) from Forum where logicalDelete=0";
        
		Query query = getEntityManager().createQuery(countQuery);
		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteForumFromParent(Integer forumParentId) throws PersistenceException {
		Query query = getEntityManager().createQuery("UPDATE Forum SET logicalDelete = true WHERE forumParent.forumId=:forumParentId");
		query.setParameter("forumParentId", forumParentId);
			
		return query.executeUpdate();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Forum findFolioForumFromParent(Forum volumeForum, Integer imageId) throws PersistenceException {
		if (volumeForum == null){
			return null;
		}

		String jpql = "FROM Forum WHERE forumParent.id = (:forumParent) and forumParent.type=:forumParentTypeCategory " +
        " and type=:typeForum and image.imageId =:imageId";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("forumParent", volumeForum.getForumId());
        query.setParameter("forumParentTypeCategory", Type.FORUM);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("imageId", imageId);

        List<Forum> forumResult = (List<Forum>) query.getResultList();

        if (forumResult.size() == 1) {
        	return forumResult.get(0);
        }
        return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findForumCategories(Forum forum) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder("FROM Forum WHERE type='");
        stringBuilder.append(forum.getType());
        stringBuilder.append("' and forumParent ");

        if (forum.getForumId() == null) {
        	stringBuilder.append(" is null");
        } else {
        	stringBuilder.append('=');
        	stringBuilder.append(forum.getForumId());
        }
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());

        return query.getResultList();
	}

	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<Forum>> findForumsGroupByCategory(List<Integer> categoriesIds) throws PersistenceException {
		if (categoriesIds == null){
			return new HashMap<Integer, List<Forum>>(0);
		}

		// we sort input categories...
		Collections.sort(categoriesIds);

		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM Forum WHERE logicalDelete = false AND type=:typeForum " +
        " and forumParent.id in (:forumParent) and forumParent.type=:forumParentTypeCategory " +
        " order by forumParent asc, dispositionOrder asc, title asc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("forumParent", categoriesIds);
        query.setParameter("forumParentTypeCategory", Type.CATEGORY);

        List<Forum> forumResult = (List<Forum>) query.getResultList();
        
        return ForumUtils.convertToMapByCategory(forumResult, categoriesIds);
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findSubCategories(Forum forum) throws PersistenceException {
		if (forum == null){
			return new ArrayList<Forum>(0);
		}

		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM Forum WHERE type=:type and forumParent.forumId = :parentId " +
		" and forumParent.type=:parentType order by dispositionOrder asc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("type", Type.CATEGORY);
        query.setParameter("parentId", forum.getForumId());
        query.setParameter("parentType", Type.CATEGORY);

        return (List<Forum>) query.getResultList();
	}

	/**
	 * {@inheritDoc} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> findSubForums(Integer parentForumId) throws PersistenceException {
		if (parentForumId == null){
			return new ArrayList<Forum>(0);
		}

		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String jpql = "FROM Forum WHERE type=:typeForum  and forumParent.forumId = :forumParentId " +
		"and forumParent.type=:forumParentTypeForum order by dispositionOrder asc";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("forumParentId", parentForumId);
        query.setParameter("forumParentTypeForum", Type.FORUM);

        return (List<Forum>) query.getResultList();
  	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Page findSubForums(Integer parentForumId, PaginationFilter paginationFilter) throws PersistenceException {
		//select * from tblForum where type = 'FORUM' and forumParent in () group by forumParent order by forumParent asc, title asc
		String queryString = "FROM Forum WHERE logicalDelete = false AND type=:typeForum  and forumParent.id = :forumParentId " +
		"and forumParent.type=:forumParentTypeForum";

		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		Query query = null;
		// We set size of result.
		if (paginationFilter.getPageTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + queryString;
	        
			query = getEntityManager().createQuery(countQuery);
	        query.setParameter("typeForum", Type.FORUM);
	        query.setParameter("forumParentId", parentForumId);
	        query.setParameter("forumParentTypeForum", Type.FORUM);

			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(paginationFilter.getPageTotal());
		}

		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder(0);
		if (sortingCriterias.size() > 0) {
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		String jpql = queryString + orderBySQL.toString();
		logger.info("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("forumParentId", parentForumId);
        query.setParameter("forumParentTypeForum", Type.FORUM);

        // We set pagination  
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(page.getElementsForPage());

		// We manage sorting (this manages sorting on multiple fields)
		List<Forum> list = (List<Forum>) query.getResultList();

		// We set search result on return method
		page.setList(list);
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum findVolumeForumFromParent(Forum generalQuestionsForum, Integer summaryId) throws PersistenceException {
		if (generalQuestionsForum == null){
			return null;
		}

		String jpql = "FROM Forum WHERE forumParent.id = (:forumParent) and forumParent.type=:forumParentTypeCategory " +
        " and type=:typeForum and volume.summaryId =:summaryId";
    	
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter("forumParent", generalQuestionsForum.getForumId());
        query.setParameter("forumParentTypeCategory", Type.FORUM);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("summaryId", summaryId);

        List<Forum> forumResult = (List<Forum>) query.getResultList();

        if (forumResult.size() == 1) {
        	return forumResult.get(0);
        }

        return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, Date> getActiveForumsInformations(Integer pageNumber, Integer numberOfForumForPage) throws PersistenceException {
		String jpql = "SELECT forumId, lastUpdate from Forum where logicalDelete = 0 ORDER BY lastUpdate ASC";

		Query query = getEntityManager().createQuery(jpql );
		query.setFirstResult(PageUtils.calculeStart(pageNumber, numberOfForumForPage));
		query.setMaxResults(numberOfForumForPage);

        List<Object> list = (List<Object>) query.getResultList();
        
        Map<Integer, Date> retValue = new HashMap<Integer, Date>(0);
        
        for (int i=0; i<list.size(); i++) {
        	Object[] singleRow = (Object[]) list.get(i);
        	retValue.put((Integer) singleRow[0], (Date) singleRow[1]);
        }

        return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getCategory(Forum category) throws PersistenceException {
		if (category == null) {
			return null;
		}

		String stringQuery = "FROM Forum WHERE type=:typeForum  and forumId = :categoryId";
    	
        Query query = getEntityManager().createQuery(stringQuery);
        query.setParameter("typeForum", Type.CATEGORY);
        query.setParameter("categoryId", category.getForumId());

        List<Forum> list = query.getResultList();
		if (list.size() == 0) { 
			return null;
		} else {
			return (Forum) query.getResultList().get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getFirstCategory() throws PersistenceException {
		String stringQuery = "FROM Forum WHERE type=:typeForum order by forumId asc";
    	
        Query query = getEntityManager().createQuery(stringQuery);
        query.setParameter("typeForum", Type.CATEGORY);

        List<Forum> list = query.getResultList();
		if (list.size() == 0) { 
			logger.error("No first category found. Please check Forum database tables.");
			return null;
		} else {
			return (Forum) query.getResultList().get(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getForumDocument(Integer entryId) throws PersistenceException {
		String queryString = "FROM Forum WHERE type=:typeForum and subType=:subTypeForum and document.entryId=:entryId ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("subTypeForum", SubType.DOCUMENT);
        query.setParameter("entryId", entryId);

        List<Forum> list = (List<Forum>) query.getResultList();
        
        if (list.size()==1) {
        	return list.get(0);
        } else if (list.size()>1) {
        	logger.error("Found more than one forum for document with entryId " + entryId);
        }

        return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getForumPerson(Integer personId) throws PersistenceException {
		String queryString = "FROM Forum WHERE type=:typeForum and subType=:subTypeForum and person.personId=:personId ";
		
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("typeForum", Type.FORUM);
		query.setParameter("subTypeForum", SubType.PEOPLE);
		query.setParameter("personId", personId);
		
		List<Forum> list = (List<Forum>) query.getResultList();
		
		if (list.size()==1) {
			return list.get(0);
		} else if (list.size()>1) {
			logger.error("Found more than one forum for person with personId " +personId);
		}
		
		return null;	
	}


	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getForumPlace(Integer placeAllId) throws PersistenceException {
		String queryString = "FROM Forum WHERE type=:typeForum and subType=:subTypeForum and place.placeAllId=:placeAllId ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("subTypeForum", SubType.PLACE);
        query.setParameter("placeAllId", placeAllId);

        List<Forum> list = (List<Forum>) query.getResultList();
        
        if (list.size()==1) {
        	return list.get(0);
        } else if (list.size()>1) {
        	logger.error("Found more than one forum for place with id " +placeAllId);
        }

        return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Forum> getForumsByType(Type type) throws PersistenceException {
		String queryString = "FROM Forum WHERE type=:typeForum ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("typeForum", type);

        return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Forum getForumVolume(Integer summaryId) throws PersistenceException {
		String queryString = "FROM Forum WHERE type=:typeForum and subType=:subTypeForum and volume.summaryId=:summaryId ";

        Query query = getEntityManager().createQuery(queryString);
        query.setParameter("typeForum", Type.FORUM);
        query.setParameter("subTypeForum", SubType.VOLUME);
        query.setParameter("summaryId", summaryId);

        List<Forum> list = (List<Forum>) query.getResultList();
        
        if (list.size()==1) {
        	return list.get(0);
        } else if (list.size()>1) {
        	logger.error("Found more than one forum for volume with summaryId " +summaryId);
        }

        return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long getSubForumsNumberWithTopics(Integer forumId) throws PersistenceException {
		String countQuery = "SELECT COUNT(*) from Forum where forumId=:forumId AND topicsNumber != 0 AND logicalDelete=0";
        
		Query query = getEntityManager().createQuery(countQuery);
		query.setParameter("forumId", forumId);
		
		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String, Long> getTotalTopicsAndPosts() throws PersistenceException {
		String queryString = "SELECT topicsNumber, postsNumber FROM Forum WHERE forumParent IS NULL";

        Query query = getEntityManager().createQuery(queryString);

        Object[] statisticsResult = (Object[]) query.getSingleResult();
        
        HashMap<String, Long> retValue = new HashMap<String, Long>(0);

        retValue.put("topicsNumber", new Long((Integer) statisticsResult[0]));
        retValue.put("postsNumber", new Long ((Integer)statisticsResult[1]));

        return retValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveDecreasePostsNumber(Forum forum) throws PersistenceException {
		if (forum == null) {
			return;
		}
		
		forum.setPostsNumber(forum.getPostsNumber()-1);
		merge(forum);
		
		recursiveDecreasePostsNumber(forum.getForumParent());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveDecreasePostsNumber(Forum forum, Integer decreaseNumber) throws PersistenceException {
		if(forum == null){
			return;
		}
		
		forum.setPostsNumber(forum.getPostsNumber() - decreaseNumber);
		merge(forum);
		
		recursiveDecreasePostsNumber(forum.getForumParent(), decreaseNumber);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveDecreaseSubForumsNumber(Forum forum) throws PersistenceException {
		if(forum == null){
			return;
		}
		
		forum.setSubForumsNumber(forum.getSubForumsNumber() - 1);
		merge(forum);
		
		recursiveDecreaseSubForumsNumber(forum.getForumParent());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveDecreaseTopicsNumber(Forum forum) throws PersistenceException {
		if (forum == null) {
			return;
		}
		
		forum.setTopicsNumber(forum.getTopicsNumber()-1);
		merge(forum);
		
		recursiveDecreaseTopicsNumber(forum.getForumParent());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveDecreaseTopicsNumber(Forum forum, Integer decreaseNumber) throws PersistenceException {
		if(forum == null){
			return;
		}
		
		forum.setTopicsNumber(forum.getTopicsNumber() - decreaseNumber);
		merge(forum);
		
		recursiveDecreaseTopicsNumber(forum.getForumParent(), decreaseNumber);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveIncreasePostsNumber(Forum forum) {
		if (forum == null) {
			return;
		}
		
		forum.setPostsNumber(forum.getPostsNumber()+1);
		merge(forum);
		
		recursiveIncreasePostsNumber(forum.getForumParent());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveIncreaseSubForumsNumber(Forum forum) throws PersistenceException {
		if(forum == null){
			return;
		}
		
		forum.setSubForumsNumber(forum.getSubForumsNumber() + 1);
		merge(forum);
		
		recursiveIncreaseSubForumsNumber(forum.getForumParent());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recursiveIncreaseTopicsNumber(Forum forum) {
		if (forum == null) {
			return;
		}
		
		forum.setTopicsNumber(forum.getTopicsNumber()+1);
		merge(forum);
		
		recursiveIncreaseTopicsNumber(forum.getForumParent());
	}
}
