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

import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.util.Version;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.transform.Transformers;
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
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
	public List<Place> searchPlaceLinkableToTopicDocument(String searchText) throws PersistenceException {
        String[] searchFields = new String[]{"placeName", "placeNameFull", "termAccent"};
		String[] outputFields = new String[]{"placeAllId", "placeNameFull", "prefFlag", "plType"};

		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

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

		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

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

		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

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
	@Override
	public Page simpleSearchPlaces(String searchText, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		//String[] outputFields = new String[]{"personId", "mapNameLf", "gender", "bornYear", "bornMonth", "bornDay", "deathYear", "deathMonth", "deathDay", "poLink"};

		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Place.class).get();
        
		org.apache.lucene.search.Query baseQuery = queryBuilder.keyword().onFields(
				"placeName",
				"placeNameFull",
				"termAccent",
				"plType",
				"geogKey"
			).matching(searchText + "*").createQuery();
		
        /*
        // TODO : WE DON'T KNOW IF WE NEED AN ADDITIONAL QUERY..
        org.apache.lucene.search.PhraseQuery queryAltName = new PhraseQuery();
        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);
        for (String singleWord:words) {
        	queryAltName.add(new Term("altName.altName", singleWord));
        }
        */
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(new BooleanClause(baseQuery, BooleanClause.Occur.SHOULD));
		//booleanQuery.add(new BooleanClause(queryAltName, BooleanClause.Occur.SHOULD));

		final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( booleanQuery, Place.class );
		if (paginationFilter.getTotal() == null) {
			page.setTotal(new Long(fullTextQuery.getResultSize()));
		}

		fullTextQuery.setFirstResult(paginationFilter.getFirstRecord());
		fullTextQuery.setMaxResults(paginationFilter.getLength());

		page.setList(fullTextQuery.list());

        return page;
	}
}
