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

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
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
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Place;
import org.springframework.stereotype.Repository;

/**
 * <b>PlaceDAOJpaImpl</b> is a default implementation of <b>PlaceDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
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
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> findByGeogKey(Integer geogKey) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM Place WHERE geogkey=:geogkey AND prefflag='P'");
		query.setParameter("geogkey", geogKey);
		List<Place> result = query.getResultList();
		query = getEntityManager().createQuery("FROM Place WHERE geogkey=:geogkey AND prefflag='V'");
		query.setParameter("geogkey", geogKey);
		result.addAll(query.getResultList());
		
		return result;
		
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
			query = getEntityManager().createQuery("FROM Place WHERE plSource=:plSource AND geogkey>=400000 AND geogkey<=1000000 ORDER BY geogkey DESC");
		}
		query.setParameter("plSource", plSource);
		query.setMaxResults(1);
		
		return (Place) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Place findLastEntryPlace() throws PersistenceException {
        Query query = getEntityManager().createQuery("FROM Place ORDER BY dateEntered DESC");
        query.setMaxResults(1);

        return (Place) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Place> searchBornPlace(String query) throws PersistenceException {
        String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = query.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
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
        String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = query.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
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
        String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = searchText.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
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
		String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = query.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
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
		String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = searchText.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
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
        String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = searchText.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
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
        String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new MultiFieldQueryParser(Version.LUCENE_30, searchFields, fullTextSession.getSearchFactory().getAnalyzer("placeAnalyzer"));

        try  {
        	String searchTextWithWildCard = searchText.toLowerCase() + "*";
	        org.apache.lucene.search.Query queryPlace = parserMapNameLf.parse(searchTextWithWildCard);

	        final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( queryPlace, Place.class );
			// Projection permits to extract only a subset of domain class, tuning application.
			fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			fullTextQuery.setResultTransformer(Transformers.aliasToBean(Place.class));

			return fullTextQuery.list();
        } catch (ParseException parseException) {
			// TODO: handle exception
        	return null;
		}
	}
}
