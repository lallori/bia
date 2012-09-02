/*
 * TopicsListDAOJpaImpl.java
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
package org.medici.bia.dao.topicslist;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextSession;
import org.hibernate.transform.Transformers;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.Search;
import org.medici.bia.common.util.RegExUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.TopicList;
import org.springframework.stereotype.Repository;

/**
 * Implementazione di esempio di un dao applicativo. La classe deve estendere il
 * jpaDao che fornisce i servizi piu' comuni (persit, findById e delete) JPA
 * DAO.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Repository
public class TopicsListDAOJpaImpl extends JpaDao<Integer, TopicList> implements TopicsListDAO {

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
	private static final long serialVersionUID = 2651694660170054610L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TopicList> findTopicsList() throws PersistenceException {
		Query query = getEntityManager().createQuery("from TopicList ORDER BY topicTitle");
		
		return query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TopicList> findTopicsListForUsers() throws PersistenceException {
		Query query = getEntityManager().createQuery("FROM TopicList WHERE topicId!=43 ORDER BY topicTitle");
		
		return query.getResultList();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<TopicList> searchTopicLinkableToDocument(List<Integer> topicIdList, String alias) throws PersistenceException {
		String[] outputFields = new String[]{"topicId", "topicTitle"};
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());
		
		/*QueryParser parserTopicTitle = new QueryParser(Version.LUCENE_30, "topicTitle", fullTextSession.getSearchFactory().getAnalyzer("topicListAnalyzer"));
		try{
		org.apache.lucene.search.Query queryMapNameLf = parserTopicTitle.parse(alias.toLowerCase() + "*");
		BooleanQuery booleanQuery = new BooleanQuery();
		booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
		for (String singleWord:words) {
        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("topicTitle", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
        }*/
		BooleanQuery booleanQuery = new BooleanQuery();
		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
		String singleWord;
		for(int i = 0; i < words.length; i++){
			singleWord = words[i];
			if(i == 0){
				booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("topicTitle", singleWord.toLowerCase() + "*")), BooleanClause.Occur.MUST));
			}
			else{
				booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("topicTitle", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
			}
		}
		
		for (int i=0; i<topicIdList.size(); i++) {
			booleanQuery.add(new BooleanClause(new TermQuery(new Term("topicId", topicIdList.get(i).toString())), BooleanClause.Occur.MUST_NOT));
		}

		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, TopicList.class);
		fullTextQuery.setProjection(outputFields);
		// Projection returns an array of Objects, using Transformer we can return a list of domain object  
		fullTextQuery.setResultTransformer(Transformers.aliasToBean(TopicList.class));

		List<TopicList> listTopics = fullTextQuery.list();
		
//		Comparator fieldCompare = new BeanComparator( "topicTitle" );
//		Collections.sort(listTopics, fieldCompare );

		return listTopics; 
		/*} catch (ParseException parseException) {
        	logger.error("Unable to parse query with text " + alias, parseException);
        	return new ArrayList<TopicList>(0);
		}*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchTopics(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		// We convert SearchContainer to luceneQuery
		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString()); 

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, TopicList.class );

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

}
