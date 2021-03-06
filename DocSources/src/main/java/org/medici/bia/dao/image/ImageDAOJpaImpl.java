/*
 * ImageDAOJpaImpl.java
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
package org.medici.bia.dao.image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.pagination.VolumeExplorer;
import org.medici.bia.common.util.DocumentUtils;
import org.medici.bia.common.util.ImageUtils;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.common.util.VolumeUtils;
import org.medici.bia.common.volume.FoliosInformations;
import org.medici.bia.common.volume.VolumeInsert;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageRectoVerso;
import org.medici.bia.domain.Image.ImageType;
import org.springframework.stereotype.Repository;

/**
 * <b>ImageDAOJpaImpl</b> is a default implementation of
 * <b>ImageDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Repository
public class ImageDAOJpaImpl extends JpaDao<Integer, Image> implements ImageDAO {
	
	private Logger logger = Logger.getLogger(ImageDAOJpaImpl.class);

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
	private static final long serialVersionUID = -8769762056162920397L;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long countImages(Integer volNum, String volLetExt, String imageType, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException {
		Query query = getFindImageQuery(volNum, volLetExt, imageType, insertNum, insertLet, folioNum, folioMod, rectoVerso, true);
		
		Long count;
		try {
			count = (Long)query.getSingleResult();
		} catch (Exception e) {
			throw new PersistenceException("Count Images Query problem: The query [" + query.toString() + "] generates an error!");
		}
		
		return count;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countImagesCreatedBeforeDate(Date timeStamp, List<ImageType> types) throws PersistenceException {
		String jpql = "SELECT count(*) FROM Image WHERE dateCreated <= :timeStamp";
		if (types != null && types.size() > 0) {
			jpql += " AND imageType IN (:types)";
		}
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("timeStamp", timeStamp);
		if (types != null && types.size() > 0) {
			query.setParameter("types", types);
		}
		
		return (Long)query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findDocumentImage(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso) throws PersistenceException {
		return findImage(volNum, volLetExt, null, insertNum, insertLet, folioNum, folioMod, Image.ImageRectoVerso.convertFromString(rectoVerso), rectoVerso != null);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Image findDocumentImage(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, ImageType type) throws PersistenceException {
		Query query = getFindImageQuery(volNum, volLetExt, type != null ? type.toString() : "", insertNum, insertLet, folioNum, folioMod, "", false);
		List<Image> result = query.getResultList();
		
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Image findDocumentImage(Integer volNum, String volLetExt, String insertNum, String insertLet, Integer folioNum, String folioMod, ImageRectoVerso rectoVerso, ImageType type) throws PersistenceException {
		Query query = getFindImageQuery(volNum, volLetExt, type != null ? type.toString() : "", insertNum, insertLet, folioNum, folioMod, rectoVerso != null ? rectoVerso.toString() : "", false);
		List<Image> result = query.getResultList();
		
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findDocumentImages(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder("FROM Image WHERE volNum = :volNum and volLetExt ");
        if (volLetExt != null) {
        	stringBuilder.append(" = :volLetExt");
        } else {
        	stringBuilder.append(" is null");
        } 
    	stringBuilder.append(" and imageName like '%_C_");
    	stringBuilder.append(ImageUtils.formatFolioNumber(folioNum, folioMod));
    	stringBuilder.append("_%.tif'");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());

        query.setParameter("volNum", volNum);
        if (volLetExt != null) {
        	query.setParameter("volLetExt", volLetExt);
        }

		List<Image> result = query.getResultList();
		
		return result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> findDigitizedDocumentsFromImages(List<Document> documents) {
		List<String> returnValues = new ArrayList<String>(0);
		if (documents.size() > 0) {
			StringBuilder sb = new StringBuilder("FROM Image WHERE ");
			boolean first = true;
			for(Document doc : documents) {
				if (!first) {
					sb.append(" OR ");
				}
				sb.append("(volNum = ").append(doc.getVolume().getVolNum());
				sb.append(" AND volLetExt ").append(!StringUtils.isEmpty(doc.getVolume().getVolLetExt()) ? "= '"+doc.getVolume().getVolLetExt()+"'" : "IS NULL");
				sb.append(" AND insertNum ").append(!StringUtils.isEmpty(doc.getInsertNum()) ? "= '"+doc.getInsertNum()+"'" : "IS NULL");
				sb.append(" AND insertLet ").append(!StringUtils.isEmpty(doc.getInsertLet()) ? "= '"+doc.getInsertLet()+"'" : "IS NULL");
				sb.append(" AND imageProgTypeNum = ").append(doc.getFolioNum());
				sb.append(" AND missedNumbering ").append(!StringUtils.isEmpty(doc.getFolioMod()) ? "= '"+doc.getFolioMod()+"'" : " IS NULL");
				sb.append(" AND imageRectoVerso ").append(doc.getFolioRectoVerso() != null ? "= '"+doc.getFolioRectoVerso().toString()+"'" : " IS NULL");
				sb.append(")");
				first = false;
			}
			
			Query query = getEntityManager().createQuery(sb.toString());

        	List<Image> result = (List<Image>) query.getResultList();
        	for (int i=0; i<result.size(); i++) {
        		Image img = result.get(i);
        		returnValues.add(DocumentUtils.toMDPInsertFolioFormat(
        				img.getVolNum(), 
        				img.getVolLetExt(), 
        				img.getInsertNum(), 
        				img.getInsertLet(), 
        				img.getImageProgTypeNum(), 
        				img.getMissedNumbering(), 
        				img.getImageRectoVerso().toString()));
        	}
		}
		
		
		return returnValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findDocumentsDigitized(List<Integer> volNums, List<String> volLetExts, List<Integer> folioNums,	List<String> folioMods) {
		StringBuilder stringBuilder = new StringBuilder("FROM Image WHERE ");
        for(int i=0;i<volNums.size();i++){
        	if(folioNums.get(i)!= null){
        		if(stringBuilder.indexOf("volNum") != -1){
        			stringBuilder.append(" or ");
        		}
	        	stringBuilder.append("(volNum=");
	        	stringBuilder.append(volNums.get(i));
	        	stringBuilder.append(" and volLetExt ");
	        	if (StringUtils.isEmpty(volLetExts.get(i))) {
		        	stringBuilder.append("is null");
	        	} else {
		        	stringBuilder.append("='");
		        	stringBuilder.append(volLetExts.get(i));
		        	stringBuilder.append('\'');
	        	}
		
		    	stringBuilder.append(" and imageName like '%_C_");
		    	
		    	stringBuilder.append(ImageUtils.formatFolioNumber(folioNums.get(i), folioMods.get(i)));
		    	stringBuilder.append("_%.tif')");
        	}
        }
    	
        List<String> returnValues = new ArrayList<String>(0);
        if(stringBuilder.indexOf("volNum") != -1){
        	Query query = getEntityManager().createQuery(stringBuilder.toString());

        	List<Image> result = (List<Image>) query.getResultList();
		
        	
        	for (int i=0; i<result.size(); i++) {
        		if(ImageUtils.extractFolioExtension(result.get(i).getImageName()) != null) {
        			returnValues.add(DocumentUtils.toMDPAndFolioFormat(result.get(i).getVolNum(), result.get(i).getVolLetExt(), ImageUtils.extractFolioNumber(result.get(i).getImageName()), ImageUtils.extractFolioExtension(result.get(i).getImageName()).toLowerCase()));
        		} else {
        			returnValues.add(DocumentUtils.toMDPAndFolioFormat(result.get(i).getVolNum(), result.get(i).getVolLetExt(), ImageUtils.extractFolioNumber(result.get(i).getImageName()), null));
        		}
        	}
        }
		return returnValues;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Image findImage(Integer volNum, String volLetExt, ImageType imageType, Integer folioNum) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt)){
        	stringBuilder.append("=:volLetExt");
        } else {
        	stringBuilder.append(" is null");
        }

        stringBuilder.append(" and imageType=:imageType");
        stringBuilder.append(" and imageProgTypeNum=:imageProgTypeNum");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }

    	query.setParameter("imageType", imageType);
    	
    	
    	query.setParameter("imageProgTypeNum", folioNum);
		List<Image> result = (List<Image>) query.getResultList();
			
		if (result.size() > 0) {
			return result.get(0);
		}
        
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findImage(Integer volNum, String volLetExt, ImageType imageType, String insertNum, String insertLet, Integer folioNum, String folioMod) {
		return findImage(volNum, volLetExt, imageType, insertNum, insertLet, folioNum, folioMod, null, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findImage(Integer volNum, String volLetExt, ImageType imageType, String insertNum, String insertLet, Integer folioNum, String folioMod, Image.ImageRectoVerso rectoVerso) {
		return findImage(volNum, volLetExt, imageType, insertNum, insertLet, folioNum, folioMod, rectoVerso, true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends VolumeExplorer> Image findImage(T explorer) throws PersistenceException {
		// If total is null we need to obtain total and partial total by type (rubricario and folio)...
		if (explorer.getTotal() == null) {
			this.updateExplorerTotals(explorer);
		} 

		Image image = explorer.getImage();
		
        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE");
        stringBuilder.append(" volNum = :volNum");
        if (explorer.getVolLetExt() == null || !StringUtils.isEmpty(explorer.getVolLetExt().trim())) {
        	stringBuilder.append(" AND volLetExt").append(explorer.getVolLetExt() == null ? " IS NULL" : " = :volLetExt");
        }
		
        if (image.getImageProgTypeNum() != null) {
			// folio number is provided --> the query search by this criterium
			if (image.getInsertNum() == null || !StringUtils.isEmpty(image.getInsertNum().trim())) {
				stringBuilder.append(" AND insertNum").append(image.getInsertNum() == null ? " IS NULL" : " = :insertNum");
			}
			if (image.getInsertLet() == null || !StringUtils.isEmpty(image.getInsertLet().trim())) {
				stringBuilder.append(" AND insertLet").append(image.getInsertLet() == null ? " IS NULL" : " = :insertLet");
			}
			if (image.getImageType() != null) {
				stringBuilder.append(" AND imageType = :imageType");
			}
        	stringBuilder.append(" AND imageProgTypeNum = :imageProgTypeNum");
            if (image.getMissedNumbering() == null || !StringUtils.isEmpty(image.getMissedNumbering().trim())) {
            	stringBuilder.append(" AND missedNumbering").append(image.getMissedNumbering() == null ? " IS NULL" : " = :missedNumbering");
            }
        	if (image.getImageRectoVerso() != null) {
        		// imageRectoVerso cannot be null in tblImages
        		stringBuilder.append(" AND imageRectoVerso = :imageRectoVerso");
        	}
        } else  if (image.getImageOrder() != null) {
			// folio numnber is not provided --> we first consider image order search filter
			stringBuilder.append(" AND imageOrder = :imageOrder");
		} else if (!org.medici.bia.common.util.StringUtils.isNullableString(image.getImageName())) {
			// imageOrder is not provided --> we consider imageName search filter
			stringBuilder.append(" AND imageName LIKE '%").append(image.getImageName().trim()).append("%'");
		} else {
        	// no search filter is provided --> we set 1 for imageOrder
        	stringBuilder.append(" AND imageOrder = 1");
        }
        
        logger.debug("FindImages from documentExplorer query: " + stringBuilder.toString());
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", explorer.getVolNum());
        if (!org.medici.bia.common.util.StringUtils.isNullableString(explorer.getVolLetExt())) {
        	query.setParameter("volLetExt", explorer.getVolLetExt());
        }

        if (image.getImageProgTypeNum() != null) {
        	if (!org.medici.bia.common.util.StringUtils.isNullableString(image.getInsertNum())) {
        		query.setParameter("insertNum", image.getInsertNum().trim());
        	}
        	if (!org.medici.bia.common.util.StringUtils.isNullableString(image.getInsertLet())) {
        		query.setParameter("insertLet", image.getInsertLet().trim());
        	}
        	if (image.getImageType() != null) {
        		query.setParameter("imageType", image.getImageType());
        	}
        	query.setParameter("imageProgTypeNum", image.getImageProgTypeNum());
        	if (!org.medici.bia.common.util.StringUtils.isNullableString(image.getMissedNumbering())) {
        		query.setParameter("missedNumbering", image.getMissedNumbering());
        	}
        	if (image.getImageRectoVerso() != null/* && !Image.ImageRectoVerso.N.equals(image.getImageRectoVerso())*/) {
        		query.setParameter("imageRectoVerso", image.getImageRectoVerso());
        	}
			List<Image> result = (List<Image>) query.getResultList();
			
			if (result.size() > 0) {
				return result.get(0);
			}
			return null;
        }
        
        if (image.getImageOrder() != null) {
        	// imageOrder is provided
        	query.setParameter("imageOrder", image.getImageOrder());
        }
		
		// imageOrder provided or image name provided or no searching criteria is provided
		query.setFirstResult(0);
		query.setMaxResults(1);
		try {
			return (Image) query.getSingleResult();
		} catch (NoResultException noResultExcepion) { }

        return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findImages(List<Integer> imageIds) throws PersistenceException {
		String jpql = "FROM Image WHERE imageId IN (:imageIds) ORDER BY imageId";
		
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("imageIds", imageIds);
		
		return getResultList(query);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Image> findImages(Integer volNum, String volLetExt) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
		Root<Image> root = criteriaQuery.from(Image.class);
	
		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		criteriaQuery.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(volLetExt) ? 
					criteriaBuilder.isNull(root.get("volLetExt")) : 
					criteriaBuilder.equal(root.get("volLetExt"), parameterVolLeText)
			)
		);

		// Set values in predicate's elements  
		TypedQuery<Image> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt)) {
			typedQuery.setParameter("volLetExt", volLetExt);
		}

		return typedQuery.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Page findImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws PersistenceException {
		// Create criteria objects
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		Page page = new Page(paginationFilter);

		if (paginationFilter.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Image> rootCount = criteriaQueryCount.from(Image.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

			// Define predicate's elements
			ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
			ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

			criteriaQueryCount.where(
				criteriaBuilder.and(
					criteriaBuilder.equal(rootCount.get("volNum"), parameterVolNum),
					StringUtils.isEmpty(volLetExt) ? 
						criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
						criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
				)
			);

			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			typedQueryCount.setParameter("volNum", volNum);
			if (!StringUtils.isEmpty(volLetExt)) {
				typedQueryCount.setParameter("volLetExt", volLetExt);
			}
			page.setTotal(new Long((Long)typedQueryCount.getSingleResult()));
		}

		CriteriaQuery<Image> criteriaQuery = criteriaBuilder.createQuery(Image.class);
		Root<Image> root = criteriaQuery.from(Image.class);
	
		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		//We need to duplicate predicates beacause they are link to Root element
		criteriaQuery.where(
				criteriaBuilder.and(
					criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
					StringUtils.isEmpty(volLetExt) ? 
						criteriaBuilder.isNull(root.get("volLetExt")) : 
						criteriaBuilder.equal(root.get("volLetExt"), parameterVolLeText)
				)
			);

		// Set values in predicate's elements  
		TypedQuery<Image> typedQuery = getEntityManager().createQuery(criteriaQuery);
		typedQuery.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt)) {
			typedQuery.setParameter("volLetExt", volLetExt);
		}

		//Pagination will work with index [1 ... total] and not [0 ... total1-] 
		typedQuery.setFirstResult(paginationFilter.getFirstRecord()-1);
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());

		return page;
	}
	
	@SuppressWarnings("unchecked")
	public List<Image> findImages(Integer volNum, String volLetExt, String insertNum, String insertLet) {
		boolean isEmptyVolExt = StringUtils.isEmpty(volLetExt);
		boolean isEmptyInsertExt = StringUtils.isEmpty(insertLet);
		StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum = :volNum");
		stringBuilder.append(" AND volLetExt").append(!isEmptyVolExt ? " = :volLetExt" : " IS NULL");
		stringBuilder.append(" AND insertNum = :insertNum");
		stringBuilder.append(" AND insertLet").append(!isEmptyInsertExt ? " = :insertLet" : " IS NULL");
		
		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("volNum", volNum);
		if (!isEmptyVolExt)
			query.setParameter("volLetExt", volLetExt);
		query.setParameter("insertNum", insertNum);
		if (!isEmptyInsertExt)
			query.setParameter("insertLet", insertLet);
		
		return (List<Image>) query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> findNewDigitizedVolumes() throws PersistenceException {
		// we need to extract volLetExt equals
        String hql = "SELECT DISTINCT(b.summaryId) FROM Image a, Volume b WHERE a.volNum=b.volNum and a.volLetExt=b.volLetExt and b.digitized=false";

        Query query = getEntityManager().createQuery(hql);

        List<Integer> result = (List<Integer>) query.getResultList();

		// second query is for volLetExt equal to null (08/03/2012 added "and b.volLetExt is null" )
        hql = "SELECT DISTINCT(b.summaryId) FROM Image a, Volume b WHERE a.volNum=b.volNum and a.volLetExt is null and b.volLetExt is null and b.digitized=false";

        query = getEntityManager().createQuery(hql);
        
        result.addAll(query.getResultList());

		if (result.isEmpty()) {
			return new ArrayList<Integer>(0);
		}
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Image findVolumeFirstImage(Integer volNum, String volLetExt) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt)) {
        	stringBuilder.append("=:volLetExt");
        } else {
        	stringBuilder.append(" is null");
        }

        stringBuilder.append(" and imageOrder=:imageOrder");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }

    	query.setParameter("imageOrder", 1);
    	
		List<Image> result = (List<Image>) query.getResultList();
			
		if (result.size() > 0) {
			return result.get(0);
		}
        
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public FoliosInformations findVolumeFoliosInformations(Integer volNum, String volLetExt) throws PersistenceException {
		FoliosInformations foliosInformations = new FoliosInformations();
		
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
		Root<Image> rootCount = criteriaQueryCount.from(Image.class);
		criteriaQueryCount.select(criteriaBuilder.count(rootCount));

		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		criteriaQueryCount.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(rootCount.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(volLetExt) ? 
					criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
					criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
			)
		);

		TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
		typedQueryCount.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			typedQueryCount.setParameter("volLetExt", volLetExt);
		foliosInformations.setTotal((Long)typedQueryCount.getSingleResult());

        StringBuilder stringBuilder = new StringBuilder("SELECT imageType, imageRectoVerso, max(imageProgTypeNum) FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" GROUP BY imageType, imageRectoVerso");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }

		List<Object[]> result = (List<Object[]>)query.getResultList();

		// We init every partial-total
		foliosInformations.setTotalRubricario(new Long(0));
		foliosInformations.setTotalCarta(new Long(0));
		foliosInformations.setTotalAppendix(new Long(0));
		foliosInformations.setTotalOther(new Long(0));
		foliosInformations.setTotalGuardia(new Long(0));
		foliosInformations.setTotalMissingFolios(0);
		// We set new partial-total values 
		for (int i=0; i<result.size(); i++) {
			// This is an array defined as [ImageType, Count by ImageType]
			Object[] singleGroup = result.get(i);

			if(((ImageType) singleGroup[0]).equals(ImageType.R)) {
				if (foliosInformations.getTotalRubricario() < new Long(singleGroup[2].toString())) {
					foliosInformations.setTotalRubricario(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.C)) {
				if (foliosInformations.getTotalCarta() < new Long(singleGroup[2].toString())) {
					foliosInformations.setTotalCarta(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.A)) {
				if (foliosInformations.getTotalAppendix() < new Long(singleGroup[2].toString())) {
					foliosInformations.setTotalAppendix(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.O)) {
				if (foliosInformations.getTotalOther() < new Long(singleGroup[2].toString())) {
					foliosInformations.setTotalOther(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.G)) {
				if (foliosInformations.getTotalGuardia() < new Long(singleGroup[2].toString())) {
					foliosInformations.setTotalGuardia(new Long(singleGroup[2].toString()));
				}
			}
		}

		// Calculating missing folios start
		stringBuilder = new StringBuilder("SELECT DISTINCT(imageProgTypeNum) FROM Image WHERE volNum = :volNum AND volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" IS NULL");
        stringBuilder.append(" AND imageType = 'C'");	// we search only for folios
    	stringBuilder.append(" ORDER BY imageProgTypeNum ASC");
    	
        query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }
		List<Integer> foliosOnVolume = (List<Integer>)query.getResultList();

		for (long i=1; i<=foliosInformations.getTotalCarta(); i++) {
			for (int j=0; j<foliosOnVolume.size(); j++) {
				if (foliosOnVolume.get(j) == i) {
					break;
				} else if (foliosOnVolume.get(j) > i) {
					foliosInformations.setTotalMissingFolios(foliosInformations.getTotalMissingFolios()+1);
					//LP : Missing numbering is first counter!!!!
					foliosInformations.getMissingNumberingFolios().add(((Long)i).intValue());
					break;
				}
			}
				
		}
		// Calculating missing folios end

		//Extracting misnumbered Folios...
		stringBuilder = new StringBuilder("SELECT concat(imageProgTypeNum, missedNumbering) FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" and missedNumbering is not null ORDER BY imageProgTypeNum ASC");
    	
        query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }

		foliosInformations.setMisnumberedFolios((List<String>)query.getResultList());

		return foliosInformations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image findVolumeImage(Integer volNum, String volLetExt, Integer imageOrder) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
		if (!StringUtils.isEmpty(volLetExt))
			stringBuilder.append(" LIKE :volLetExt");
		else
			stringBuilder.append(" is null");
		stringBuilder.append(" and imageOrder=:imageOrder");
		
		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt)) {
			query.setParameter("volLetExt", volLetExt);
		}

    	query.setParameter("imageOrder", imageOrder);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return (Image) query.getSingleResult();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findVolumeImages(Integer volNum, String volLetExt) throws PersistenceException {
		StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
		if (!StringUtils.isEmpty(volLetExt))
			stringBuilder.append("=:volLetExt");
		else
			stringBuilder.append(" is null");

		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt)) {
			query.setParameter("volLetExt", volLetExt);
		}

		return query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findVolumeImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuilder.append("=:volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" and imageType=:imageType");
    	stringBuilder.append(" and imageProgTypeNum=:imageProgTypeNum");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }
    	query.setParameter("imageType", imageType);
    	query.setParameter("imageProgTypeNum", imageProgTypeNum);
		List<Image> result = (List<Image>) query.getResultList();

		if (result.isEmpty())
			return null;
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findVolumeImages(Integer volNum, String volLetExt, Integer imageOrder) {
		StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuilder.append("=:volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" and imageOrder=:imageOrder");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt)) {
        	query.setParameter("volLetExt", volLetExt);
        }
    	query.setParameter("imageOrder", imageOrder);
		List<Image> result = (List<Image>) query.getResultList();

		if (result.isEmpty())
			return null;
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findVolumesDigitized(List<Integer> volNums, List<String> volLetExts) {
        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE ");
        for (int i= 0; i<volNums.size(); i++) {
        	if(volNums.get(i) != null){
	        	if(stringBuilder.indexOf("volNum") != -1){
	        		stringBuilder.append(" or ");
	        	}
	        	stringBuilder.append("(volNum=");
	        	stringBuilder.append(volNums.get(i));
	        	stringBuilder.append(" and volLetExt ");
	        	if (StringUtils.isEmpty(volLetExts.get(i))) {
		        	stringBuilder.append(" is null");
	        	} else {
		        	stringBuilder.append("='");
		        	stringBuilder.append(volLetExts.get(i));
		        	stringBuilder.append('\'');
	        	}
	        	stringBuilder.append(" and imageOrder=1) ");
        	}
        }
        List<String> returnValues = new ArrayList<String>(0);
        
        if(stringBuilder.indexOf("volNum") != -1){
	        Query query = getEntityManager().createQuery(stringBuilder.toString());
	    	
			List<Image> result = (List<Image>) query.getResultList();
	
			for (int i=0; i<result.size(); i++) {
				returnValues.add(VolumeUtils.toMDPFormat(result.get(i).getVolNum(), result.get(i).getVolLetExt()));
			}
        }

		return returnValues;
	}
	
	/**
	 * {@inheritDoc}
	 */
	//@SuppressWarnings("unchecked")
	public List<VolumeInsert> findVolumeInserts(Integer volNum, String volLetExt) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 
		
		CriteriaQuery<VolumeInsert> cq = criteriaBuilder.createQuery(VolumeInsert.class);
		Root<Image> root = cq.from(Image.class);
		
		cq.select(criteriaBuilder.construct(VolumeInsert.class, root.get("volNum"), root.get("volLetExt"), root.get("insertNum"), root.get("insertLet"))).distinct(true);
		cq.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(root.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(volLetExt) ? 
					criteriaBuilder.isNull(root.get("volLetExt")) : 
					criteriaBuilder.equal(root.get("volLetExt"), parameterVolLeText),
				criteriaBuilder.isNotNull(root.get("insertNum"))
			)
		);
		cq.orderBy(criteriaBuilder.asc(root.get("insertNum")), criteriaBuilder.asc(root.get("insertLet")));
		
		/* The above query is equivalent to the following:
		 * SELECT DISTINCT volNum, volLetExt, insertNum, insertLet FROM Image
		 * WHERE 
		 * 		 volNum = :volNum
		 *   AND volLetExt (<nullable> ? 'IS NULL' : ':volLetExt')
		 *   AND insertNum IS NOT NULL
		 * ORDER BY
		 * 		insertNum ASC,
		 * 		insertLet ASC
		 */
		
		TypedQuery<VolumeInsert> tq = getEntityManager().createQuery(cq);
		tq.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			tq.setParameter("volLetExt", volLetExt);
		
		return tq.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Image findVolumeSpine(Integer volNum, String volLetExt) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder("FROM Image WHERE volNum = :volNum and volLetExt ");
        if (volLetExt != null)
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" and imageName like '%SPI.tif'");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());

        query.setParameter("volNum", volNum);
        if (volLetExt != null)
        	query.setParameter("volLetExt", volLetExt);

		List<Image> result = query.getResultList();
		
		if (result.size() >0) {
			return result.get(0);
		}
		
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page getVolumeImages(PaginationFilter paginationFilter,  Integer volNum, String volLetExt, String[] imageTitleFilters) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		String jpql = "FROM Image WHERE volNum = :volNum AND volLetExt "
				+ (volLetExt != null ? "= :volLetExt" : "IS NULL");
		if (imageTitleFilters != null) {
			for (int i = 0; i < imageTitleFilters.length; i++) {
				jpql += " AND imageTitle LIKE '%" + imageTitleFilters[i] + "%'";
			}
		}
		
		Query query = null;
		
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + jpql;
			query = getEntityManager().createQuery(countQuery);
			query.setParameter("volNum", volNum);
			if (volLetExt != null) {
				query.setParameter("volLetExt", volLetExt);
			}
			page.setTotal(new Long((Long) query.getSingleResult()));
			page.setTotalPages(PageUtils.calculeTotalPages(page.getTotal(), page.getElementsForPage()));
		} else {
			page.setTotal(paginationFilter.getTotal());
			page.setTotalPages(PageUtils.calculeTotalPages(paginationFilter.getTotal(), paginationFilter.getElementsForPage()));
		}
		
		query = getEntityManager().createQuery(jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		query.setParameter("volNum", volNum);
		if (volLetExt != null) {
			query.setParameter("volLetExt", volLetExt);
		}
		
		query.setFirstResult(PageUtils.calculeStart(page.getThisPage(), page.getElementsForPage()));
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Boolean hasInserts(Integer volNum, String volLetExt) {
		return findVolumeInserts(volNum, volLetExt).size() > 0;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page searchImagesCreatedBefore(Date timeStamp, List<ImageType> types, PaginationFilter paginationFilter) throws PersistenceException {
		Page page = new Page(paginationFilter);
		
		String jpql = "FROM Image WHERE dateCreated <= :timeStamp";
		if (types != null && types.size() > 0) {
			jpql += " AND imageType IN (:types)";
		}
		
		Query query = null;
		
		if (paginationFilter.getTotal() == null) {
			String countQuery = "SELECT COUNT(*) " + jpql;
			query = getEntityManager().createQuery(countQuery);
			query.setParameter("timeStamp", timeStamp);
			if (types != null && types.size() > 0) {
				query.setParameter("types", types);
			}
			page.setTotal(new Long((Long) query.getSingleResult()));
		}
		
		query = getEntityManager().createQuery(jpql + getOrderByQuery(paginationFilter.getSortingCriterias()));
		query.setParameter("timeStamp", timeStamp);
		if (types != null && types.size() > 0) {
			query.setParameter("types", types);
		}
		
		query.setFirstResult(paginationFilter.getFirstRecord());
		query.setMaxResults(paginationFilter.getLength());
		
		page.setList(query.getResultList());
		
		return page;
	}
	
	
	/* Privates */
	
	/**
	 * This method creates the find image query.<br/>
	 * NOTE: empty string fields are not considered in the query (only for string fields).
	 * 
	 * @param volNum volume number filter (mandatory)
	 * @param volLetExt volume letter extension filter
	 * @param imageType image type filter
	 * @param insertNum insert number filter
	 * @param insertLet insert letter filter
	 * @param folioNum folio number filter (mandatory or null)
	 * @param folioMod folio extension filter
	 * @param rectoVerso recto verso filter
	 * @param count true if coutn query is needed
	 * @return the query
	 */
	private Query getFindImageQuery(Integer volNum, String volLetExt, String imageType, String insertNum, String insertLet, Integer folioNum, String folioMod, String rectoVerso, boolean count) {
		String vLEFilter = org.medici.bia.common.util.StringUtils.nullTrim(volLetExt);
		String iTypeFilter = org.medici.bia.common.util.StringUtils.safeTrim(imageType);
		String insNumFilter = org.medici.bia.common.util.StringUtils.safeTrim(insertNum);
		String insLetFilter = org.medici.bia.common.util.StringUtils.safeTrim(insertLet);
		String folModFilter = org.medici.bia.common.util.StringUtils.safeTrim(folioMod);
		String rvFilter = org.medici.bia.common.util.StringUtils.safeTrim(rectoVerso);
		
		StringBuilder sb = new StringBuilder(count ? "SELECT COUNT(*) " : "");
		sb.append("FROM Image");
		sb.append(" WHERE volNum = :volNum");
		sb.append(" AND volLetExt ").append(vLEFilter != null ? "= :volLetExt" : "IS NULL");
		if (!"".equals(iTypeFilter)) {
			sb.append(" AND imageType ").append(iTypeFilter != null ? "= :imageType" : "IS NULL");
		}
		if (!"".equals(insNumFilter)) {
			sb.append(" AND insertNum ").append(insNumFilter != null ? "= :insertNum" : "IS NULL");
			if (!"".equals(insLetFilter)) {
				sb.append(" AND insertLet ").append(insLetFilter != null ? "= :insertLet" : "IS NULL");
			}
		}
		sb.append(" AND imageProgTypeNum ").append(folioNum != null ? "= :folioNum" : "IS NULL");
		if (!"".equals(folModFilter)) {
			sb.append(" AND missedNumbering ").append(folModFilter != null ? "= :folioMod" : "IS NULL");
		}
		if (!"".equals(rvFilter)) {
			sb.append(" AND imageRectoVerso ").append(rvFilter != null ? "= :folioRV" : "IS NULL");
		}
		
		Query query = getEntityManager().createQuery(sb.toString());
		query.setParameter("volNum", volNum);
		if (vLEFilter != null) {
			query.setParameter("volLetExt", vLEFilter);
		}
		if (isParameterFilter(iTypeFilter)) {
			query.setParameter("imageType", ImageType.convertFromString(iTypeFilter));
		}
		if (isParameterFilter(insNumFilter)) {
			query.setParameter("insertNum", insNumFilter);
			if (isParameterFilter(insLetFilter)) {
				query.setParameter("insertLet", insLetFilter);
			}
		}
		if (folioNum != null) {
			query.setParameter("folioNum", folioNum);
		}
		if (isParameterFilter(folModFilter)) {
			query.setParameter("folioMod", folModFilter);
		}
		if (isParameterFilter(rvFilter)) {
			query.setParameter("folioRV", ImageRectoVerso.convertFromString(rvFilter));
		}
		
		return query;
	}
	
	/**
	 * This method return the searched image.<br/>
	 * NOTE: all the filters are considered with only one exception: recto/verso filter is not considered
	 * if <code>considerRectoVerso</code> parameter is false.
	 * 
	 * @param volNum volume number
	 * @param volLetExt volume letter exception
	 * @param imageType imag type
	 * @param insertNum insert number
	 * @param insertLet insert letter
	 * @param folioNum folio number
	 * @param folioMod folio extension
	 * @param rectoVerso recto/verso
	 * @param considerRectoVerso true if recto/verso filter has to be considered
	 * @return the searched image or null if none
	 */
	@SuppressWarnings("unchecked")
	private Image findImage(Integer volNum, String volLetExt, ImageType imageType, String insertNum, String insertLet, Integer folioNum, String folioMod, Image.ImageRectoVerso rectoVerso, boolean considerRectoVerso) {
		StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum = :volNum");
		stringBuilder.append(" AND volLetExt ").append(!StringUtils.isEmpty(volLetExt) ? "= :volLetExt" : "IS NULL");
		if (imageType != null)
			stringBuilder.append(" AND imageType = :imageType");
		
		stringBuilder.append(" AND insertNum ").append(!StringUtils.isEmpty(insertNum) ? "= :insertNum" : "IS NULL");
		stringBuilder.append(" AND insertLet ").append(!StringUtils.isEmpty(insertLet) ? "= :insertLet" : "IS NULL");
		
		stringBuilder.append(" AND imageProgTypeNum = :folioNum");
		stringBuilder.append(" AND missedNumbering ").append(!StringUtils.isEmpty(folioMod) ? "= :folioMod" : "IS NULL");
		if (considerRectoVerso)
			stringBuilder.append(" AND imageRectoVerso = :imageRectoVerso");
		
		Query query = getEntityManager().createQuery(stringBuilder.toString());
		query.setParameter("volNum", volNum);
		if (!StringUtils.isEmpty(volLetExt))
			query.setParameter("volLetExt", volLetExt);
		if (imageType != null)
			query.setParameter("imageType", imageType);
		if (!StringUtils.isEmpty(insertNum))
			query.setParameter("insertNum", insertNum);
		if (!StringUtils.isEmpty(insertLet))
			query.setParameter("insertLet", insertLet);
		query.setParameter("folioNum", folioNum);
		if (!StringUtils.isEmpty(folioMod))
			query.setParameter("folioMod", folioMod);
		if (considerRectoVerso)
			query.setParameter("imageRectoVerso", rectoVerso);
		
		List<Image> result = (List<Image>) query.getResultList();
		
		if (result.size() > 0) {
			return result.get(0);
		}
        
		return null;
	}
	
	/**
	 * This method checks if the string filter is null or empty.
	 * 
	 * @param filter the string filter
	 * @return true if the filter is null or empty
	 */
	private boolean isParameterFilter(String filter) {
		return filter != null && !"".equals(filter);
	}
	
	/**
	 * This method updates every totals in input.
	 * 
	 * @param explorer input object to be update.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends VolumeExplorer> void updateExplorerTotals(T explorer) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
		Root<Image> rootCount = criteriaQueryCount.from(Image.class);
		criteriaQueryCount.select(criteriaBuilder.count(rootCount));

		// Define predicate's elements
		ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
		ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

		criteriaQueryCount.where(
			criteriaBuilder.and(
				criteriaBuilder.equal(rootCount.get("volNum"), parameterVolNum),
				StringUtils.isEmpty(explorer.getVolLetExt()) ? 
					criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
					criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
			)
		);

		TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
		typedQueryCount.setParameter("volNum", explorer.getVolNum());
		if (!StringUtils.isEmpty(explorer.getVolLetExt()))
			typedQueryCount.setParameter("volLetExt", explorer.getVolLetExt());
		explorer.setTotal((Long)typedQueryCount.getSingleResult());

        StringBuilder stringBuilder = new StringBuilder("SELECT imageType, imageRectoVerso, max(imageProgTypeNum) FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(explorer.getVolLetExt()))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" group by imageType, imageRectoVerso");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", explorer.getVolNum());
        if (!StringUtils.isEmpty(explorer.getVolLetExt())) {
        	query.setParameter("volLetExt", explorer.getVolLetExt());
        }

		List<Object[]> result = (List<Object[]>)query.getResultList();

		// We init every partial-total
		explorer.setTotalRubricario(new Long(0));
		explorer.setTotalCarta(new Long(0));
		explorer.setTotalAppendix(new Long(0));
		explorer.setTotalOther(new Long(0));
		explorer.setTotalGuardia(new Long(0));
		
		// We set new partial-total values 
		for (int i=0; i<result.size(); i++) {
			// This is an array defined as [ImageType, Count by ImageType]
			Object[] singleGroup = result.get(i);

			if(((ImageType) singleGroup[0]).equals(ImageType.R)) {
				if (explorer.getTotalRubricario() < new Long(singleGroup[2].toString())) {
					explorer.setTotalRubricario(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.C)) {
				if (explorer.getTotalCarta() < new Long(singleGroup[2].toString())) {
					explorer.setTotalCarta(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.A)) {
				if (explorer.getTotalAppendix() < new Long(singleGroup[2].toString())) {
					explorer.setTotalAppendix(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.O)) {
				if (explorer.getTotalOther() < new Long(singleGroup[2].toString())) {
					explorer.setTotalOther(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.G)) {
				if (explorer.getTotalGuardia() < new Long(singleGroup[2].toString())) {
					explorer.setTotalGuardia(new Long(singleGroup[2].toString()));
				}
			}
		}
	}
	
}
