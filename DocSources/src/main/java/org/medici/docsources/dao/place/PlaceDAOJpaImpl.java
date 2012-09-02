/*
 * PlaceDAOJpaImpl.java
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
package org.medici.docsources.dao.place;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.transform.Transformers;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.RegExUtils;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Place;
import org.springframework.stereotype.Repository;

/**
 * <b>PlaceDAOJpaImpl</b> is a default implementation of <b>PlaceDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Repository
public class PlaceDAOJpaImpl extends JpaDao<Integer, Place> implements PlaceDAO {

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
	private static final long serialVersionUID = -2993971980020334157L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countPlaceCreatedAfterDate(Date inputDate) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(placeAllId) FROM Place WHERE dateCreated>=:inputDate");
		query.setParameter("inputDate", inputDate);

		return (Long) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> findByGeogKey(Integer geogKey) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Place WHERE geogkey=:geogkey AND prefflag='P' AND logicalDelete = false");
		query.setParameter("geogkey", geogKey);
		List<Place> result = query.getResultList();
		query = getEntityManager().createQuery("FROM Place WHERE geogkey=:geogkey AND prefflag='V' AND logicalDelete = false");
		query.setParameter("geogkey", geogKey);
		result.addAll(query.getResultList());
		
		return result;
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findLastEntryPlace() throws PersistenceException {
        Query query = getEntityManager().createQuery("FROM Place WHERE logicalDelete = false ORDER BY dateEntered DESC");
        query.setMaxResults(1);

        return (Place) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findNewGeogKey(String plSource) throws PersistenceException {
		Query query = null;
		if(plSource.equals("MAPPLACE")){
			query = getEntityManager().createQuery("FROM Place WHERE plSource=:plSource AND geogkey>=100000 AND geogkey<=400000 ORDER BY geogkey DESC");
		}
		if(plSource.equals("MAPSITE")){
			query = getEntityManager().createQuery("FROM Place WHERE plSource=:plSource AND geogkey>=400000 AND geogkey<=1000000 AND logicalDelete = false ORDER BY geogkey DESC");
		}
		query.setParameter("plSource", plSource);
		query.setMaxResults(1);
		
		return (Place) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findPrinicipalPlace(Integer geogKey) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Place WHERE geogkey=:geogkey AND prefflag='P'");
		query.setParameter("geogkey", geogKey);
		
		query.setMaxResults(1);
		
		return (Place) query.getSingleResult();
		
		
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchBornPlace(String query) throws PersistenceException {
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(query);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchDeathPlace(String query) throws PersistenceException {
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(query);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchPlaceLinkableToTopicDocument(String searchText) throws PersistenceException {
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchPlaceParent(String query) throws PersistenceException{
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(query);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchPlaces(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString()); 

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, Place.class );

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
	public List<Place> searchPlaces(String searchText) throws PersistenceException {
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchRecipientsPlace(String searchText) throws PersistenceException {
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchSendersPlace(String searchText) throws PersistenceException {
//		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserPlaceName = new QueryParser(Version.LUCENE_30, "placeName", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserPlaceNameFull = new QueryParser(Version.LUCENE_30, "placeNameFull", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        QueryParser parserTermAccent = new QueryParser(Version.LUCENE_30, "termAccent", fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));
        

        try  {
	        BooleanQuery booleanQuery = new BooleanQuery();
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord;
	        	if(i == 0)
	        		singleWord = words[0];
	        	else
	        		singleWord = words[i] + "*";
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase()), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
	        booleanQuery = new BooleanQuery();
	        for (int i = 0; i < words.length; i++) {
	        	BooleanQuery text = new BooleanQuery();
	        	String singleWord = words[i];
	        	text.add(new BooleanClause(parserPlaceName.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserPlaceNameFull.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	text.add(new BooleanClause(parserTermAccent.parse(singleWord.toLowerCase() + "*"), BooleanClause.Occur.SHOULD));
	        	if(i == 0)
	        		booleanQuery.add(new BooleanClause(text, Occur.MUST));
	        	else
	        		booleanQuery.add(new BooleanClause(text, Occur.SHOULD));
	        }
	        final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery(booleanQuery, Place.class);
			// Projection permits to extract only a subset of domain class, tuning application.
//			fullTextQuery.setProjection(outputFields);
//			fullTextQueryWithWildCard.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));
			fullTextQueryWithWildCard.setResultTransformer(Transformers.aliasToBean(Place.class));

			List<Place> result = new ArrayList<Place>(0);
			List<Place> result1 = fullTextQuery.list();
			List<Place> result2 = fullTextQueryWithWildCard.list();
			if(result1.size() > 0){
				result.addAll(result1);
			}
			if(result2.size() > 0){
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0){
					result.addAll(result2);
				}
			}
			return result;
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}

}
