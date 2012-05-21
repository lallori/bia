/*
 * PeopleDAOJpaImpl.java
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
package org.medici.docsources.dao.people;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.transform.Transformers;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.PaginationFilter.Order;
import org.medici.docsources.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.docsources.common.search.Search;
import org.medici.docsources.common.util.RegExUtils;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.People;
import org.springframework.stereotype.Repository;

/**
 * <b>PeopleDAOJpaImpl</b> is a default implementation of <b>PeopleDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.People
 */
@Repository
public class PeopleDAOJpaImpl extends JpaDao<Integer, People> implements PeopleDAO {
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
	private static final long serialVersionUID = -2964902298903431093L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countPeopleCreatedAfterDate(Date inputDate) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(personId) FROM People WHERE dateCreated>=:inputDate");
		query.setParameter("inputDate", inputDate);

		return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public People findLastEntryPerson() throws PersistenceException {
        Query query = getEntityManager().createQuery("FROM People where logicalDelete = false ORDER BY dateCreated DESC");
        query.setMaxResults(1);

        return (People) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfActiveEndInPlace(Integer placeAllId)	throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(personId) FROM People WHERE deathPlace.placeAllId =:placeAllId AND activeEnd!=NULL");
		query.setParameter("placeAllId", placeAllId);
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfActiveStartInPlace(Integer placeAllId) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(personId) FROM People WHERE bornPlace.placeAllId =:placeAllId AND activeStart!=NULL");
		query.setParameter("placeAllId", placeAllId);
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfBirthInPlace(Integer placeAllId)	throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(personId) FROM People WHERE bornPlace.placeAllId =:placeAllId AND activeStart=NULL");
		query.setParameter("placeAllId", placeAllId);
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer findNumberOfDeathInPlace(Integer placeAllId)	throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(personId) FROM People WHERE deathPlace.placeAllId =:placeAllId AND activeEnd=NULL");
		query.setParameter("placeAllId", placeAllId);
		Long result = (Long) query.getSingleResult();
		return new Integer(result.intValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<Integer, Long> findNumbersOfPeopleRelatedPlace(List<Integer> placeAllIds) throws PersistenceException {
		StringBuffer stringBufferBorn = new StringBuffer("SELECT bornPlace.placeAllId, COUNT(personId) FROM People WHERE");
		StringBuffer stringBufferDeath = new StringBuffer("SELECT deathPlace.placeAllId, COUNT(personId) FROM People WHERE");
		for(int i=0; i < placeAllIds.size(); i++){
			if(stringBufferBorn.indexOf("=") != -1){
    			stringBufferBorn.append(" or ");
    		}
			if(stringBufferDeath.indexOf("=") != -1){
				stringBufferDeath.append(" or ");
			}
			stringBufferBorn.append("(bornPlace.placeAllId=");
        	stringBufferBorn.append(placeAllIds.get(i) + ")");
        	stringBufferDeath.append("(deathPlace.placeAllId=");
        	stringBufferDeath.append(placeAllIds.get(i) + " AND bornPlace.placeAllId!=" + placeAllIds.get(i) + ")");
		}
		stringBufferBorn.append(" group by bornPlace.placeAllId");
		stringBufferDeath.append(" group by deathPlace.placeAllId");
		
		Map<Integer, Long> returnValues = new HashMap<Integer, Long>();
		List tempValuesBorn;
		if(stringBufferBorn.indexOf("=") != -1){
			Query query = getEntityManager().createQuery(stringBufferBorn.toString());
			tempValuesBorn = query.getResultList();
			for(Iterator i = tempValuesBorn.iterator(); i.hasNext();){
				Object [] data = (Object []) i.next();
				returnValues.put((Integer)data[0], (Long)data[1]);
			}
		}
		List tempValuesDeath;
		if(stringBufferDeath.indexOf("=") != -1){
			Query query = getEntityManager().createQuery(stringBufferDeath.toString());
			tempValuesDeath = query.getResultList();
			for(Iterator i = tempValuesDeath.iterator(); i.hasNext();){
				Object [] data = (Object []) i.next();
				if(returnValues.containsKey((Integer)data[0])){
					data[1] = (Long) returnValues.remove((Integer)data[0]) + (Long) data[1];
				}
				returnValues.put((Integer)data[0], (Long)data[1]);
			}
		}
		
		return returnValues;
	}
	
	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchActiveEndPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM People WHERE (deathPlace.placeAllId=" + placeToSearch + ") AND activeEnd!=NULL");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchActiveStartPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM People WHERE (bornPlace.placeAllId=" + placeToSearch + ") AND activeStart!=NULL");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchBirthPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM People WHERE (bornPlace.placeAllId=" + placeToSearch + ") AND activeStart=NULL");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
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
	public List<People> searchChildLinkableToPerson(Integer personId, String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

	        BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }

			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchDeathPeoplePlace(String placeToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM People WHERE (deathPlace.placeAllId=" + placeToSearch + ") AND activeEnd=NULL");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchFamilyPerson(String familyName, String familyNamePrefix, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		//MD: If the name of the family contains apostrophe, I replace it with two apostrophes for the SQL query
		if(familyName.contains("'"))
			familyName = familyName.replace("'", "\'\'");
		
		String toSearch = new String("FROM People WHERE personId IN (SELECT person.personId FROM org.medici.docsources.domain.AltName WHERE (altName.altName like '" + familyName + "%' OR altName.altName like '% " + familyName + "%' OR altName.altName like '%-" + familyName + "%') AND altName.nameType like 'Family')");
		
		//MD: We ignore the familyNamePrefix, because e.g. the family "Medici" is the same of "de' Medici"
		/*if(familyNamePrefix != null){
			if(familyNamePrefix.contains("'"))
				familyNamePrefix = familyNamePrefix.replace("'", "\'\'");
			toSearch += " AND altName.namePrefix like '" + familyNamePrefix + "')";
		}else{
			toSearch += ")";
		}*/
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
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
	public List<People> searchFatherLinkableToPerson(String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchMotherLinkableToPerson(String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchPeople(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		// We convert AdvancedSearchContainer to luceneQuery
		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString()); 

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, People.class );
	
		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			page.setTotal(new Long(fullTextQuery.getResultSize()));
		}

		// We set pagination  
		fullTextQuery.setFirstResult(paginationFilter.getFirstRecord());
		fullTextQuery.setMaxResults(paginationFilter.getLength());

		// We manage sorting (this manages sorting on multiple fields)
		paginationFilter = this.generatePaginationFilterHibernateSearch(paginationFilter);
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		if (sortingCriterias.size() > 0) {
			SortField[] sortFields = new SortField[sortingCriterias.size()];
			for (int i=0; i<sortingCriterias.size(); i++) {
				sortFields[i] = new SortField(sortingCriterias.get(i).getColumn(), sortingCriterias.get(i).getColumnType(), (sortingCriterias.get(i).getOrder().equals(Order.ASC) ? true : false));
			}
			fullTextQuery.setSort(new Sort(sortFields));
		}
		
		// We set search result on return method
		page.setList(fullTextQuery.list());
		
		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchPeople(String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchPersonLinkableToDocument(List<Integer> peopleIdList, String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");
			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
			for (int i=0; i<peopleIdList.size(); i++) {
				booleanQuery.add(new BooleanClause(new TermQuery(new Term("personId", peopleIdList.get(i).toString())), BooleanClause.Occur.MUST_NOT));
			}
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchRecipientsPeople(String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));
			

			fullTextQuery.toString();
			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchRoleCatPeople(String roleCatToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		String toSearch = new String("FROM People WHERE personId IN (SELECT DISTINCT person.personId FROM org.medici.docsources.domain.PoLink WHERE titleOccList.roleCat.roleCatId=" + roleCatToSearch + ")");
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery(toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}

	/**
	 * This method search senders using jpa implementation.
	 * 
	 * @param alias
	 * @return
	 * @throws PersistenceException
	 * @deprecated
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<People> searchSendersJpaImpl(String alias) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<People> criteriaQuery = criteriaBuilder.createQuery(People.class);
		Root root = criteriaQuery.from(People.class);
	
		//Construct literal and predicates
		Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal("%" + alias + "%"));
		Predicate predicateFirst = criteriaBuilder.like(criteriaBuilder.upper(root.get("first")), literal);
		Predicate predicateLast = criteriaBuilder.like(criteriaBuilder.upper(root.get("last")), literal);
		
		//Add where clause
		criteriaQuery.where(criteriaBuilder.or(predicateFirst,predicateLast)); 
	
		// create query using criteria   
		TypedQuery<People> typedQuery = getEntityManager().createQuery(criteriaQuery);
	
		return typedQuery.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchSendersPeople(String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));
        
        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));
			
			
			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<People> searchSpouseLinkableToPerson(String searchText) throws PersistenceException {
		String[] outputFields = new String[]{"personId", "mapNameLf", "activeStart", "activeEnd", "bornYear", "deathYear"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "mapNameLf", fullTextSession.getSearchFactory().getAnalyzer("peopleAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryMapNameLf = parserMapNameLf.parse(searchText.toLowerCase() + "*");

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (String singleWord:words) {
	        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("altName.altName", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
	        }
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, People.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(People.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + searchText, parseException);
        	return new ArrayList<People>(0);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTitlesOrOccupationsPeople(String titleOccToSearch, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		//String toSearch = new String("FROM People WHERE personId IN (SELECT DISTINCT person.personId FROM org.medici.docsources.domain.PoLink WHERE titleOccList.titleOccId=" + titleOccToSearch + ")");
		//MD: The next query is builded for test if is possible order result by date of Title Occupation
		String toSearch = new String("FROM People p, org.medici.docsources.domain.PoLink t WHERE p.personId = t.person.personId AND t.titleOccList.titleOccId=" + titleOccToSearch);
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		//MD: We have a pagination filter already parameterized
		//paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuffer orderBySQL = new StringBuffer();
		if(sortingCriterias.size() > 0){
			orderBySQL.append(" ORDER BY ");
			for (int i=0; i<sortingCriterias.size(); i++) {
				orderBySQL.append(sortingCriterias.get(i).getColumn() + " ");
				orderBySQL.append((sortingCriterias.get(i).getOrder().equals(Order.ASC) ? " ASC " : " DESC " ));
				if (i<(sortingCriterias.size()-1)) {
					orderBySQL.append(", ");
				} 
			}
		}
		
		query = getEntityManager().createQuery("SELECT t.person " + toSearch + orderBySQL);
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
}
