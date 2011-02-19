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
package org.medici.docsources.dao.image;

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
import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.DocumentExplorer;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.VolumeExplorer;
import org.medici.docsources.common.util.ImageUtils;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;
import org.springframework.stereotype.Repository;

/**
 * <b>ImageDAOJpaImpl</b> is a default implementation of
 * <b>ImageDAO</b>.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 * @see org.medici.docsources.domain.HistoryLog
 * @see org.medici.docsources.audit.HistoryLogAction
 */
@Repository
public class ImageDAOJpaImpl extends JpaDao<Integer, Image> implements ImageDAO {

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

	@Override
	public Image findDocumentImage(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
        StringBuffer stringBuffer = new StringBuffer("FROM Image WHERE volNum = :volNum and volLetExt ");
        if (volLetExt != null)
        	stringBuffer.append(" = :volLetExt");
        else
        	stringBuffer.append(" is null");
    	stringBuffer.append(" and imageName like '%_C_");
    	stringBuffer.append(ImageUtils.formatFolioNumber(folioNum, folioMod));
    	stringBuffer.append("_R.tif'");
    	
        Query query = getEntityManager().createQuery(stringBuffer.toString());

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
		if (!StringUtils.isEmpty(volLetExt))
			typedQuery.setParameter("volLetExt", volLetExt);

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
			if (!StringUtils.isEmpty(volLetExt))
				typedQueryCount.setParameter("volLetExt", volLetExt);
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
		if (!StringUtils.isEmpty(volLetExt))
			typedQuery.setParameter("volLetExt", volLetExt);

		//Pagination will work with index [1 ... total] and not [0 ... total1-] 
		typedQuery.setFirstResult(paginationFilter.getFirstRecord()-1);
		typedQuery.setMaxResults(paginationFilter.getLength());
		page.setList(typedQuery.getResultList());

		return page;
	}

	@Override
	public DocumentExplorer findImages(DocumentExplorer pageTurner) throws PersistenceException {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// If total is null we need to obtain total and partial total by type (rubricario and folio)...
		if (pageTurner.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Image> rootCount = criteriaQueryCount.from(Image.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

			// Define predicate's elements
			ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
			ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

			criteriaQueryCount.where(
				criteriaBuilder.and(
					criteriaBuilder.equal(rootCount.get("volNum"), parameterVolNum),
					StringUtils.isEmpty(pageTurner.getVolLetExt()) ? 
						criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
						criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
				)
			);

			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			typedQueryCount.setParameter("volNum", pageTurner.getVolNum());
			if (!StringUtils.isEmpty(pageTurner.getVolLetExt()))
				typedQueryCount.setParameter("volLetExt", pageTurner.getVolLetExt());
			pageTurner.setTotal((Long)typedQueryCount.getSingleResult());

	        StringBuffer stringBuffer = new StringBuffer("SELECT imageType, count(imageId) FROM Image WHERE volNum=:volNum and volLetExt ");
	        if (!StringUtils.isEmpty(pageTurner.getVolLetExt()))
	        	stringBuffer.append(" = :volLetExt");
	        else
	        	stringBuffer.append(" is null");
	    	stringBuffer.append(" group by imageType");
	    	
	        Query query = getEntityManager().createQuery(stringBuffer.toString());
	        query.setParameter("volNum", pageTurner.getVolNum());
	        if (!StringUtils.isEmpty(pageTurner.getVolLetExt())) {
	        	query.setParameter("volLetExt", pageTurner.getVolLetExt());
	        }

			List<Object[]> result = (List<Object[]>)query.getResultList();

			// We init every partial-total
			pageTurner.setTotalRubricario(new Long(0));
			pageTurner.setTotalCarta(new Long(0));
			pageTurner.setTotalAppendix(new Long(0));
			pageTurner.setTotalOther(new Long(0));
			pageTurner.setTotalG(new Long(0));
			
			// We set new partial-total values 
			for (int i=0; i<result.size(); i++) {
				// This is an array defined as [ImageType, Count by ImageType]
				Object[] singleGroup = result.get(i);

				if(((ImageType) singleGroup[0]).equals(ImageType.R)) {
					pageTurner.setTotalRubricario(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.C)) {
					pageTurner.setTotalCarta(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.A)) {
					pageTurner.setTotalAppendix(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.O)) {
					pageTurner.setTotalOther(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.G)) {
					pageTurner.setTotalG(new Long(singleGroup[1].toString()));
				}
			}
		} 

        StringBuffer stringBuffer = new StringBuffer(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(pageTurner.getVolLetExt()))
        	stringBuffer.append("=:volLetExt");
        else
        	stringBuffer.append(" is null");
        if (pageTurner.getImage().getImageProgTypeNum() != null) {
        	stringBuffer.append(" and imageType=:imageType");
        	stringBuffer.append(" and imageProgTypeNum=:imageProgTypeNum");
        } else if (pageTurner.getImage().getImageOrder() != null) {
        	stringBuffer.append(" and imageOrder=:imageOrder");
        } else {
        	stringBuffer.append(" and imageOrder = 1");
        }
    	
        Query query = getEntityManager().createQuery(stringBuffer.toString());
        query.setParameter("volNum", pageTurner.getVolNum());
        if (!StringUtils.isEmpty(pageTurner.getVolLetExt())) {
        	query.setParameter("volLetExt", pageTurner.getVolLetExt());
        }

        if (pageTurner.getImage().getImageProgTypeNum() != null) {
        	query.setParameter("imageType", pageTurner.getImage().getImageType());
        	query.setParameter("imageProgTypeNum", pageTurner.getImage().getImageProgTypeNum());
			List<Image> result = (List<Image>) query.getResultList();
			
			if (result.size() > 0) {
				pageTurner.setImage(result.get(0));
			}
        } else if (pageTurner.getImage().getImageOrder() != null) {
        	query.setParameter("imageOrder", pageTurner.getImage().getImageOrder());
			query.setFirstResult(0);
			query.setMaxResults(1);
			pageTurner.setImage((Image) query.getSingleResult());
        } else {
			query.setFirstResult(0);
			query.setMaxResults(1);
			try {
				pageTurner.setImage((Image) query.getSingleResult());
			} catch (NoResultException noResultExcepion) {
			}
        }

        return pageTurner;
	}

	@Override
	public VolumeExplorer findImages(VolumeExplorer volumeExplorer) throws PersistenceException {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		
		// If total is null we need to obtain total and partial total by type (rubricario and folio)...
		if (volumeExplorer.getTotal() == null) {
			CriteriaQuery<Long> criteriaQueryCount = criteriaBuilder.createQuery(Long.class);
			Root<Image> rootCount = criteriaQueryCount.from(Image.class);
			criteriaQueryCount.select(criteriaBuilder.count(rootCount));

			// Define predicate's elements
			ParameterExpression<Integer> parameterVolNum = criteriaBuilder.parameter(Integer.class, "volNum");
			ParameterExpression<String> parameterVolLeText = StringUtils.isEmpty("volLetExt") ? null : criteriaBuilder.parameter(String.class, "volLetExt"); 

			criteriaQueryCount.where(
				criteriaBuilder.and(
					criteriaBuilder.equal(rootCount.get("volNum"), parameterVolNum),
					StringUtils.isEmpty(volumeExplorer.getVolLetExt()) ? 
						criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
						criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
				)
			);

			TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
			typedQueryCount.setParameter("volNum", volumeExplorer.getVolNum());
			if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt()))
				typedQueryCount.setParameter("volLetExt", volumeExplorer.getVolLetExt());
			volumeExplorer.setTotal((Long)typedQueryCount.getSingleResult());

	        StringBuffer stringBuffer = new StringBuffer("SELECT imageType, count(imageId) FROM Image WHERE volNum=:volNum and volLetExt ");
	        if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt()))
	        	stringBuffer.append(" = :volLetExt");
	        else
	        	stringBuffer.append(" is null");
	    	stringBuffer.append(" group by imageType");
	    	
	        Query query = getEntityManager().createQuery(stringBuffer.toString());
	        query.setParameter("volNum", volumeExplorer.getVolNum());
	        if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt())) {
	        	query.setParameter("volLetExt", volumeExplorer.getVolLetExt());
	        }

			List<Object[]> result = (List<Object[]>)query.getResultList();

			// We init every partial-total
			volumeExplorer.setTotalRubricario(new Long(0));
			volumeExplorer.setTotalCarta(new Long(0));
			volumeExplorer.setTotalAppendix(new Long(0));
			volumeExplorer.setTotalOther(new Long(0));
			volumeExplorer.setTotalG(new Long(0));
			
			// We set new partial-total values 
			for (int i=0; i<result.size(); i++) {
				// This is an array defined as [ImageType, Count by ImageType]
				Object[] singleGroup = result.get(i);

				if(((ImageType) singleGroup[0]).equals(ImageType.R)) {
					volumeExplorer.setTotalRubricario(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.C)) {
					volumeExplorer.setTotalCarta(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.A)) {
					volumeExplorer.setTotalAppendix(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.O)) {
					volumeExplorer.setTotalOther(new Long(singleGroup[1].toString()));
				} else if(((ImageType) singleGroup[0]).equals(ImageType.G)) {
					volumeExplorer.setTotalG(new Long(singleGroup[1].toString()));
				}
			}
		} 

        StringBuffer stringBuffer = new StringBuffer(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt()))
        	stringBuffer.append("=:volLetExt");
        else
        	stringBuffer.append(" is null");
        if (volumeExplorer.getImage().getImageProgTypeNum() != null) {
        	stringBuffer.append(" and imageType=:imageType");
        	stringBuffer.append(" and imageProgTypeNum=:imageProgTypeNum");
        } else if (volumeExplorer.getImage().getImageOrder() != null) {
        	stringBuffer.append(" and imageOrder=:imageOrder");
        } else {
        	stringBuffer.append(" and imageOrder = 1");
        }
    	
        Query query = getEntityManager().createQuery(stringBuffer.toString());
        query.setParameter("volNum", volumeExplorer.getVolNum());
        if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt())) {
        	query.setParameter("volLetExt", volumeExplorer.getVolLetExt());
        }

        if (volumeExplorer.getImage().getImageProgTypeNum() != null) {
        	query.setParameter("imageType", volumeExplorer.getImage().getImageType());
        	query.setParameter("imageProgTypeNum", volumeExplorer.getImage().getImageProgTypeNum());
			List<Image> result = (List<Image>) query.getResultList();
			
			if (result.size() > 0) {
				volumeExplorer.setImage(result.get(0));
			}
        } else if (volumeExplorer.getImage().getImageOrder() != null) {
        	query.setParameter("imageOrder", volumeExplorer.getImage().getImageOrder());
			query.setFirstResult(0);
			query.setMaxResults(1);
			volumeExplorer.setImage((Image) query.getSingleResult());
        } else {
			query.setFirstResult(0);
			query.setMaxResults(1);
			try {
				volumeExplorer.setImage((Image) query.getSingleResult());
			} catch (NoResultException noResultExcepion) {
			}
        }

        return volumeExplorer;
	}

	@Override
	public List<Image> findVolumeImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws PersistenceException {
        StringBuffer stringBuffer = new StringBuffer(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuffer.append("=:volLetExt");
        else
        	stringBuffer.append(" is null");
    	stringBuffer.append(" and imageType=:imageType");
    	stringBuffer.append(" and imageProgTypeNum=:imageProgTypeNum");
    	
        Query query = getEntityManager().createQuery(stringBuffer.toString());
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
}
