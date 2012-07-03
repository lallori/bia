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

import java.util.ArrayList;
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
import org.medici.docsources.common.util.DocumentUtils;
import org.medici.docsources.common.util.ImageUtils;
import org.medici.docsources.common.util.VolumeUtils;
import org.medici.docsources.common.volume.FoliosInformations;
import org.medici.docsources.dao.JpaDao;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;
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
	@SuppressWarnings("unchecked")
	@Override
	public Image findDocumentImage(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder("FROM Image WHERE volNum = :volNum and volLetExt ");
        if (StringUtils.isEmpty(volLetExt))
        	stringBuilder.append(" is null");
        else
        	stringBuilder.append(" = :volLetExt");

    	stringBuilder.append(" and imageName like '%_C_");
    	stringBuilder.append(ImageUtils.formatFolioNumber(folioNum, folioMod));
    	stringBuilder.append("_R.tif'");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());

        query.setParameter("volNum", volNum);
        if (!StringUtils.isEmpty(volLetExt))
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
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findDocumentImages(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException {
        StringBuilder stringBuilder = new StringBuilder("FROM Image WHERE volNum = :volNum and volLetExt ");
        if (volLetExt != null)
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" and imageName like '%_C_");
    	stringBuilder.append(ImageUtils.formatFolioNumber(folioNum, folioMod));
    	stringBuilder.append("_%.tif'");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());

        query.setParameter("volNum", volNum);
        if (volLetExt != null)
        	query.setParameter("volLetExt", volLetExt);

		List<Image> result = query.getResultList();
		
		return result;
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
		        	stringBuilder.append("'");
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
        		if(ImageUtils.extractFolioExtension(result.get(i).getImageName()) != null)
        			returnValues.add(DocumentUtils.toMDPAndFolioFormat(result.get(i).getVolNum(), result.get(i).getVolLetExt(), ImageUtils.extractFolioNumber(result.get(i).getImageName()), ImageUtils.extractFolioExtension(result.get(i).getImageName()).toLowerCase()));
        		else
        			returnValues.add(DocumentUtils.toMDPAndFolioFormat(result.get(i).getVolNum(), result.get(i).getVolLetExt(), ImageUtils.extractFolioNumber(result.get(i).getImageName()), null));
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
	@SuppressWarnings("unchecked")
	@Override
	public DocumentExplorer findImages(DocumentExplorer documentExplorer) throws PersistenceException {
		// If total is null we need to obtain total and partial total by type (rubricario and folio)...
		if (documentExplorer.getTotal() == null) {
			this.updateDocumentExplorerTotals(documentExplorer);
		} 

        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(documentExplorer.getVolLetExt()))
        	stringBuilder.append("=:volLetExt");
        else
        	stringBuilder.append(" is null");
        if (documentExplorer.getImage().getImageProgTypeNum() != null) {
        	stringBuilder.append(" and imageType=:imageType");
        	stringBuilder.append(" and imageProgTypeNum=:imageProgTypeNum");
        } else if (documentExplorer.getImage().getImageOrder() != null) {
        	stringBuilder.append(" and imageOrder=:imageOrder");
        } else {
        	stringBuilder.append(" and imageOrder = 1");
        }
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", documentExplorer.getVolNum());
        if (!StringUtils.isEmpty(documentExplorer.getVolLetExt())) {
        	query.setParameter("volLetExt", documentExplorer.getVolLetExt());
        }

        if (documentExplorer.getImage().getImageProgTypeNum() != null) {
        	query.setParameter("imageType", documentExplorer.getImage().getImageType());
        	query.setParameter("imageProgTypeNum", documentExplorer.getImage().getImageProgTypeNum());
			List<Image> result = (List<Image>) query.getResultList();
			
			if (result.size() > 0) {
				documentExplorer.setImage(result.get(0));
			}
        } else if (documentExplorer.getImage().getImageOrder() != null) {
        	query.setParameter("imageOrder", documentExplorer.getImage().getImageOrder());
			query.setFirstResult(0);
			query.setMaxResults(1);
			documentExplorer.setImage((Image) query.getSingleResult());
        } else {
			query.setFirstResult(0);
			query.setMaxResults(1);
			try {
				documentExplorer.setImage((Image) query.getSingleResult());
			} catch (NoResultException noResultExcepion) {
			}
        }

        return documentExplorer;
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

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public VolumeExplorer findImages(VolumeExplorer volumeExplorer) throws PersistenceException {
		// If total is null we need to obtain total and partial total by type (rubricario and folio)...
		if (volumeExplorer.getTotal() == null) {
			this.updateVolumeExplorerTotals(volumeExplorer);
		} 

        StringBuilder stringBuilder = new StringBuilder(" FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt()))
        	stringBuilder.append("=:volLetExt");
        else
        	stringBuilder.append(" is null");
        if (volumeExplorer.getImage().getImageProgTypeNum() != null) {
        	stringBuilder.append(" and imageType=:imageType");
        	stringBuilder.append(" and imageProgTypeNum=:imageProgTypeNum");
        } else if (volumeExplorer.getImage().getImageOrder() != null) {
        	stringBuilder.append(" and imageOrder=:imageOrder");
        } else {
        	stringBuilder.append(" and imageOrder = 1");
        }
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
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

		if (result.isEmpty())
			return new ArrayList<Integer>(0);
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Image findVolumeFirstImage(Integer volNum, String volLetExt) throws PersistenceException {
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
		stringBuilder = new StringBuilder("SELECT distinct(imageProgTypeNum) FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volLetExt))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" order by imageProgTypeNum ASC");
    	
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
		        	stringBuilder.append("'");
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
	 * This method updates every totals in input 
	 * {@link org.medici.docsources.common.pagination.DocumentExplorer}.
	 * 
	 * @param documentExplorer DocumentExplorer input object to be update.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateDocumentExplorerTotals(DocumentExplorer documentExplorer) {
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
				StringUtils.isEmpty(documentExplorer.getVolLetExt()) ? 
					criteriaBuilder.isNull(rootCount.get("volLetExt")) : 
					criteriaBuilder.equal(rootCount.get("volLetExt"), parameterVolLeText)
			)
		);

		TypedQuery typedQueryCount = getEntityManager().createQuery(criteriaQueryCount);
		typedQueryCount.setParameter("volNum", documentExplorer.getVolNum());
		if (!StringUtils.isEmpty(documentExplorer.getVolLetExt()))
			typedQueryCount.setParameter("volLetExt", documentExplorer.getVolLetExt());
		documentExplorer.setTotal((Long)typedQueryCount.getSingleResult());

        StringBuilder stringBuilder = new StringBuilder("SELECT imageType, imageRectoVerso, max(imageProgTypeNum) FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(documentExplorer.getVolLetExt()))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" group by imageType, imageRectoVerso");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
        query.setParameter("volNum", documentExplorer.getVolNum());
        if (!StringUtils.isEmpty(documentExplorer.getVolLetExt())) {
        	query.setParameter("volLetExt", documentExplorer.getVolLetExt());
        }

		List<Object[]> result = (List<Object[]>)query.getResultList();

		// We init every partial-total
		documentExplorer.setTotalRubricario(new Long(0));
		documentExplorer.setTotalCarta(new Long(0));
		documentExplorer.setTotalAppendix(new Long(0));
		documentExplorer.setTotalOther(new Long(0));
		documentExplorer.setTotalGuardia(new Long(0));
		
		// We set new partial-total values 
		for (int i=0; i<result.size(); i++) {
			// This is an array defined as [ImageType, Count by ImageType]
			Object[] singleGroup = result.get(i);

			if(((ImageType) singleGroup[0]).equals(ImageType.R)) {
				if (documentExplorer.getTotalRubricario() < new Long(singleGroup[2].toString())) {
					documentExplorer.setTotalRubricario(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.C)) {
				if (documentExplorer.getTotalCarta() < new Long(singleGroup[2].toString())) {
					documentExplorer.setTotalCarta(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.A)) {
				if (documentExplorer.getTotalAppendix() < new Long(singleGroup[2].toString())) {
					documentExplorer.setTotalAppendix(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.O)) {
				if (documentExplorer.getTotalOther() < new Long(singleGroup[2].toString())) {
					documentExplorer.setTotalOther(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.G)) {
				if (documentExplorer.getTotalGuardia() < new Long(singleGroup[2].toString())) {
					documentExplorer.setTotalGuardia(new Long(singleGroup[2].toString()));
				}
			}
		}
	}

	/**
	 * This method updates every totals in input 
	 * {@link org.medici.docsources.common.pagination.VolumeExplorer}.
	 * 
	 * @param volumeExplorer VolumeExplorer input object to be update.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateVolumeExplorerTotals(VolumeExplorer volumeExplorer) {
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

        StringBuilder stringBuilder = new StringBuilder("SELECT imageType, imageRectoVerso, max(imageProgTypeNum) FROM Image WHERE volNum=:volNum and volLetExt ");
        if (!StringUtils.isEmpty(volumeExplorer.getVolLetExt()))
        	stringBuilder.append(" = :volLetExt");
        else
        	stringBuilder.append(" is null");
    	stringBuilder.append(" group by imageType, imageRectoVerso");
    	
        Query query = getEntityManager().createQuery(stringBuilder.toString());
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
		volumeExplorer.setTotalGuardia(new Long(0));
		
		// We set new partial-total values 
		for (int i=0; i<result.size(); i++) {
			// This is an array defined as [ImageType, Count by ImageType]
			Object[] singleGroup = result.get(i);

			if(((ImageType) singleGroup[0]).equals(ImageType.R)) {
				if (volumeExplorer.getTotalRubricario() < new Long(singleGroup[2].toString())) {
					volumeExplorer.setTotalRubricario(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.C)) {
				if (volumeExplorer.getTotalCarta() < new Long(singleGroup[2].toString())) {
					volumeExplorer.setTotalCarta(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.A)) {
				if (volumeExplorer.getTotalAppendix() < new Long(singleGroup[2].toString())) {
					volumeExplorer.setTotalAppendix(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.O)) {
				if (volumeExplorer.getTotalOther() < new Long(singleGroup[2].toString())) {
					volumeExplorer.setTotalOther(new Long(singleGroup[2].toString()));
				}
			} else if(((ImageType) singleGroup[0]).equals(ImageType.G)) {
				if (volumeExplorer.getTotalGuardia() < new Long(singleGroup[2].toString())) {
					volumeExplorer.setTotalGuardia(new Long(singleGroup[2].toString()));
				}
			}
		}
	}

}
