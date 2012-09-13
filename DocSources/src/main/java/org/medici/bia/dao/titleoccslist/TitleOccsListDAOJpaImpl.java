/*
 * TitleOccListDAOJpaImpl.java
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
package org.medici.bia.dao.titleoccslist;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.util.Version;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.SimpleSearchTitleOrOccupation;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.TitleOccsList;
import org.springframework.stereotype.Repository;

/**
 * <b>TitleOccsListDAOJpaImpl</b> is a default implementation of <b>TitleOccsListDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Repository
public class TitleOccsListDAOJpaImpl extends JpaDao<Integer, TitleOccsList> implements TitleOccsListDAO {

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
	private static final long serialVersionUID = -947341804163999355L;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TitleOccsList findTitleOcc(String titleOcc) throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM TitleOccsList WHERE titleOcc LIKE :titleOcc");
		query.setParameter("titleOcc", titleOcc);
		query.setMaxResults(1);
		if(query.getResultList().size() != 0)
			return (TitleOccsList) query.getSingleResult();
		else
			return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTitleOrOccupation(org.medici.bia.common.search.Search searchContainer,	PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString()); 

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, TitleOccsList.class );

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
	public Page searchTitlesOrOccupations(SimpleSearchTitleOrOccupation simpleSearchTitleOrOccupation, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// select a.TitleOccID, a.TitleOcc, count(b.personId) from tblTitleOccsList a left outer join tblPoLink b on b.TitleOccID = a.TitleOccID group by a.titleOccID
		StringBuilder stringBuilder = new StringBuilder("select a.titleOccID, a.titleOcc, count(b.personId) from tblTitleOccsList a");
		stringBuilder.append(" left join tblPoLink b on b.titleOccID = a.TitleOccID ");
		if (simpleSearchTitleOrOccupation.getTextSearch() != null) {
			stringBuilder.append(" where a.titleOcc LIKE '%");
			stringBuilder.append(simpleSearchTitleOrOccupation.getTextSearch());
			stringBuilder.append("%' ");
		} else if (simpleSearchTitleOrOccupation.getRoleCatId() != null) {
			stringBuilder.append(" where a.roleCatMinorId=");
			stringBuilder.append(simpleSearchTitleOrOccupation.getRoleCatId());
		}
		stringBuilder.append(" group by a.titleOccID ");

		// We set size of result.
		if (paginationFilter.getTotal() == null) {
			//select  count(*) from ( select count(*) from tblTitleOccsList a left outer join tblPoLink b on b.TitleOccID = a.TitleOccID group by a.titleOccID ) count
			StringBuilder queryCountBuilder = new StringBuilder("select count(*) from (");
			queryCountBuilder.append("select count(*) from tblTitleOccsList a left outer join tblPoLink b on b.TitleOccID = a.TitleOccID ");
			if (simpleSearchTitleOrOccupation.getTextSearch() != null) {
				queryCountBuilder.append(" where a.titleOcc LIKE '%");
				queryCountBuilder.append(simpleSearchTitleOrOccupation.getRoleCatId());
				queryCountBuilder.append("%' ");
			} else if (simpleSearchTitleOrOccupation.getRoleCatId() != null) {
				queryCountBuilder.append(" where a.roleCatMinorID=");
				queryCountBuilder.append(simpleSearchTitleOrOccupation.getRoleCatId());
			}
			queryCountBuilder.append(" group by a.titleOccID ");
			queryCountBuilder.append(") count");

			// In this case we use Native Query!!!
			Query query = getEntityManager().createNativeQuery(queryCountBuilder.toString());
			
			// Count(*) in native query is mapped as BigInteger, so we need to convert to Long...
			BigInteger result = (BigInteger) query.getSingleResult();
			page.setTotal(NumberUtils.createLong(result.toString()));
		}

		// We invoke native query beacuse we use left outer join with on condition 
		Query query = getEntityManager().createNativeQuery(stringBuilder.toString());
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());

		List<Object> list = (List<Object>) query.getResultList();

		List<Object> result = new ArrayList<Object>(list.size());
		
		for (int i=0; i<list.size(); i++) {
			Object[] singleRow = (Object[]) list.get(i);
			List<Object> row = new ArrayList<Object>(0);
			
			row.add(new TitleOccsList((Integer)singleRow[0], (String)singleRow[1]));
			row.add(NumberUtils.createLong(((BigInteger)singleRow[2]).toString()));
			result.add(row);
		}

		// We set search result on return method
		page.setList(result);

		return page;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TitleOccsList> searchTitleOrOccupationLinkableToPerson(String searchText) throws PersistenceException {
		//String[] outputFields = new String[]{"titleOccId", "titleOcc", "roleCat"};

		FullTextSession fullTextSession = Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

        QueryParser parserMapNameLf = new QueryParser(Version.LUCENE_30, "titleOcc", fullTextSession.getSearchFactory().getAnalyzer("titleOccsListAnalyzer"));

        try  {
	        org.apache.lucene.search.Query queryTitleOcc = parserMapNameLf.parse(searchText.toLowerCase());
        	org.apache.lucene.search.Query queryTitleOccWithWildCard = parserMapNameLf.parse(searchText.toLowerCase() + "*");
	        String[] words = RegExUtils.splitPunctuationAndSpaceChars(searchText);

	        org.apache.lucene.search.PhraseQuery queryRoleCatMajor = new PhraseQuery();
	        for (String singleWord:words) {
	        	queryRoleCatMajor.add(new Term("roleCat.roleCatMajor", singleWord.toLowerCase() + "*"));
	        }
	        org.apache.lucene.search.PhraseQuery queryRoleCatMinor = new PhraseQuery();
	        for (String singleWord:words) {
	        	queryRoleCatMinor.add(new Term("roleCat.roleCatMinor", singleWord.toLowerCase() + "*"));
	        }

			BooleanQuery booleanQuery = new BooleanQuery();
			booleanQuery.add(new BooleanClause(queryTitleOccWithWildCard, BooleanClause.Occur.MUST));
			booleanQuery.add(new BooleanClause(queryRoleCatMajor, BooleanClause.Occur.SHOULD));
			booleanQuery.add(new BooleanClause(queryRoleCatMinor, BooleanClause.Occur.SHOULD));
	
			final FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(queryTitleOcc, TitleOccsList.class);
			final FullTextQuery fullTextQueryWithWildCard = fullTextSession.createFullTextQuery( booleanQuery, TitleOccsList.class );
			
			// Pasquinelli 13/02/2012 Does we need sorting??
			//fullTextQuery.setSort(new Sort(new SortField("titleOcc", SortField.STRING, true)));
			//fullTextQueryWithWildCard.setSort(new Sort(new SortField("titleOcc", SortField.STRING, true)));
			
			
			// Projection permits to extract only a subset of domain class, tuning application.
			//fullTextQuery.setProjection(outputFields);
			// Projection returns an array of Objects, using Transformer we can return a list of domain object  
			//fullTextQuery.setResultTransformer(Transformers.aliasToBean(TitleOccsList.class));
			
			//Matteo it's better to control size before adding to result
			List<TitleOccsList> result = new ArrayList<TitleOccsList>(0);
			List<TitleOccsList> result1 = fullTextQuery.list();
			List<TitleOccsList> result2 = fullTextQueryWithWildCard.list();
			if (result1.size()>0) {
				result.addAll(result1);
			}
			if (result2.size()>0) {
				if(result.size() > 0){
					result2.removeAll(result);
				}
				if(result2.size() > 0)
					result.addAll(result2);
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
	public Page searchTitleOrOccupationWithAssignedPeople(String alias, Integer roleCatId, PaginationFilter paginationFilter) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}
}
