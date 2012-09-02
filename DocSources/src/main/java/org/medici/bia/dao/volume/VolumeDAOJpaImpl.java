/*
 * VolumeDAOJpaImpl.java
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
package org.medici.bia.dao.volume;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.search.FullTextSession;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.PaginationFilter.Order;
import org.medici.bia.common.pagination.PaginationFilter.SortingCriteria;
import org.medici.bia.common.search.Search;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Volume;
import org.springframework.stereotype.Repository;

/**
 * <b>VolumeDAOJpaImpl</b> is a default implementation of <b>VolumeDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Repository
public class VolumeDAOJpaImpl extends JpaDao<Integer, Volume> implements VolumeDAO {

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
	private static final long serialVersionUID = -7671104408958929124L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countVolumeCreatedAfterDate(Date inputDate) throws PersistenceException {
		Query query = getEntityManager().createQuery("SELECT COUNT(summaryId) FROM Volume WHERE dateCreated>=:inputDate");
		query.setParameter("inputDate", inputDate);

		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findLastEntryVolume() throws PersistenceException {
         Query query = getEntityManager().createQuery("FROM Volume WHERE logicalDelete = false ORDER BY dateCreated DESC");
         query.setMaxResults(1);

         return (Volume) query.getSingleResult();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Volume findVolume(Integer volNum, String volLetExt) {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Volume> criteriaQuery = criteriaBuilder.createQuery(Volume.class);
		Root<Volume> root = criteriaQuery.from(Volume.class);
	
		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLetExt = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		criteriaQuery.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(volLetExt) ? 
					criteriaBuilder.isNull(root.get("volLetExt")) : 
					criteriaBuilder.equal(root.get("volLetExt"), parameterVolLetExt)
			)
		);

		// Set values in predicate's elements  
		TypedQuery<Volume> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			typedQuery.setParameter("volLetExt", volLetExt);

		List<Volume> result = typedQuery.getResultList();
		
		if (result.size() == 1) {
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
	public List<String> searchOtherLang(String query) throws PersistenceException {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT otherLang FROM Volume WHERE otherLang LIKE'%");
		stringBuilder.append(query.toLowerCase());
		stringBuilder.append("%'");
		Query result = getEntityManager().createQuery(stringBuilder.toString());
		if(result.getResultList().size() == 0){
			return null;
		}else{
			List<String> otherLang = result.getResultList();
			return otherLang;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVolumes(Search searchContainer, PaginationFilter paginationFilter) throws PersistenceException {
		// We prepare object of return method.
		Page page = new Page(paginationFilter);
		
		// We obtain hibernate-search session
		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());

		// We convert SearchContainer to luceneQuery
		org.apache.lucene.search.Query query = searchContainer.toLuceneQuery();
		logger.info("Lucene Query " + query.toString()); 

		// We execute search
		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery( query, Volume.class );

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
	
	//MD: Version with Lucene query
//	/**
//	 * {@inheritDoc}
//	 */
//	@SuppressWarnings({"unchecked"})
//	@Override
//	public List<Volume> searchVolumes(String query) throws PersistenceException {
//		String[] outputFields = new String[]{"volNum", "volLetExt"};
//		FullTextSession fullTextSession = org.hibernate.search.Search.getFullTextSession(((HibernateEntityManager)getEntityManager()).getSession());
//		
//		/*QueryParser parserTopicTitle = new QueryParser(Version.LUCENE_30, "topicTitle", fullTextSession.getSearchFactory().getAnalyzer("topicListAnalyzer"));
//		try{
//		org.apache.lucene.search.Query queryMapNameLf = parserTopicTitle.parse(alias.toLowerCase() + "*");
//		BooleanQuery booleanQuery = new BooleanQuery();
//		booleanQuery.add(new BooleanClause(queryMapNameLf, BooleanClause.Occur.SHOULD));
//		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
//		for (String singleWord:words) {
//        	booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("topicTitle", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
//        }*/
//		BooleanQuery booleanQuery = new BooleanQuery();
//		//String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);
//		//String singleWord;
//		//for(int i = 0; i < words.length; i++){
//		//	singleWord = words[i];
//		//	if(i == 0){
//		//		booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("topicTitle", singleWord.toLowerCase() + "*")), BooleanClause.Occur.MUST));
//		//	}
//		//	else{
//		//		booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("topicTitle", singleWord.toLowerCase() + "*")), BooleanClause.Occur.SHOULD));
//		//	}
//		//}
//		if(StringUtils.isNumeric(query)){
//			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("volNum", query.toLowerCase() + "*")), BooleanClause.Occur.MUST));
//		}else{
//			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("volNum", query.toLowerCase())), BooleanClause.Occur.SHOULD));
//			booleanQuery.add(new BooleanClause(new WildcardQuery(new Term("volLetExt", VolumeUtils.extractVolLetExt(query.toLowerCase()) + "*")), BooleanClause.Occur.SHOULD));
//		}
//
//		org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(booleanQuery, Volume.class);
//		fullTextQuery.setProjection(outputFields);
//		// Projection returns an array of Objects, using Transformer we can return a list of domain object  
//		fullTextQuery.setResultTransformer(Transformers.aliasToBean(Volume.class));
//
//		List<Volume> listVolumes = fullTextQuery.list();
//		
//		
//		Comparator fieldCompare = new BeanComparator( "volNum" );
//		Collections.sort(listVolumes, fieldCompare );
//
//		return listVolumes;
//	}
	
	//MD: Version with SQL query	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({"unchecked"})
	@Override
	public List<Volume> searchVolumes(String query) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder("FROM Volume WHERE (CONVERT(volNum, CHAR)) LIKE '");
		stringBuilder.append(query.toLowerCase());
		stringBuilder.append("%' ORDER BY volNum");
		Query result = getEntityManager().createQuery(stringBuilder.toString());
		if(result.getResultList().size() == 0){
			return null;
		}else{
			List<Volume> listVolumes = result.getResultList();
			return listVolumes;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Page searchVolumes(String text, PaginationFilter paginationFilter)  throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Volume> rootCount = criteriaQueryCount.from(Volume.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

			List<Predicate> predicates = new ArrayList<Predicate>();
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("title"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("subTitle1"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("serieList").get("subTitle2"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("orgNotes"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("recips"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("researcher"), "%" + text + "%" ));
	        predicates.add(criteriaBuilder.like((Expression) rootCount.get("senders"), "%" + text + "%" ));

	        //If we omiss criteriaBuilder.or every predicate is in conjunction with others  
	        criteriaQueryCount.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));

			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			page.setTotal(new Long((Long)typedQueryCount.getSingleResult()));
		}

		CriteriaQuery<Volume> criteriaQuery = criteriaBuilder.createQuery(Volume.class);
		Root<Volume> root = criteriaQuery.from(Volume.class);
	
		//We need to duplicate predicates beacause they are link to Root element
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(criteriaBuilder.like((Expression) root.get("serieList").get("title"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("serieList").get("subTitle1"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("serieList").get("subTitle2"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("orgNotes"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("recips"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("researcher"), "%" + text + "%" ));
        predicates.add(criteriaBuilder.like((Expression) root.get("senders"), "%" + text + "%" ));

        //If we omiss criteriaBuilder.or every predicate is in conjunction with others  
        criteriaQuery.where(criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("summaryId")));

		// Set values in predicate's elements  
		TypedQuery<Volume> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(paginationFilter.getFirstRecord());
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());

		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchVolumesByDigitization(Integer volNum, Integer volNumBetween, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		Query query = null;
		//String toSearch = new String("FROM People WHERE personId IN (SELECT DISTINCT person.personId FROM org.medici.bia.domain.PoLink WHERE titleOccList.titleOccId=" + titleOccToSearch + ")");
		//MD: The next query is builded for test if is possible order result by date of Title Occupation
		String toSearch = new String("FROM Volume WHERE volNum>=" + volNum + " AND volNum<=" + volNumBetween);
		
		if(paginationFilter.getTotal() == null){
			String countQuery = "SELECT COUNT(*) " + toSearch;
			query = getEntityManager().createQuery(countQuery);
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		//MD: We have a pagination filter already parameterized
		//paginationFilter = generatePaginationFilterMYSQL(paginationFilter);
		
		List<SortingCriteria> sortingCriterias = paginationFilter.getSortingCriterias();
		StringBuilder orderBySQL = new StringBuilder();
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

	@Override
	public Integer updateNewDigitizedVolume(List<Integer> summaryIds) throws PersistenceException {
        String hql = "UPDATE Volume set digitized=true where summaryId in (:summaryIds)";

        Query query = getEntityManager().createQuery(hql);

        query.setParameter("summaryIds", summaryIds);

		return query.executeUpdate();
	}
}
