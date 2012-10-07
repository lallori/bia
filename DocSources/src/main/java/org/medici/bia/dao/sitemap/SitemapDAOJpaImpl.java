/*
 * SitemapDAOJpaImpl.java
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
package org.medici.bia.dao.sitemap;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Sitemap;
import org.medici.bia.domain.Sitemap.ChangeFrequency;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a
 *         href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Repository
@Transactional(readOnly = true)
public class SitemapDAOJpaImpl extends JpaDao<String, Sitemap> implements SitemapDAO {

	/**
	 * 
	 * If a serializable class does not explicitly declare a serialVersionUID,
	 * then the serialization runtime will calculate a default serialVersionUID
	 * value for that class based on various aspects of the class, as described
	 * in the Java(TM) Object Serialization Specification. However, it is
	 * strongly recommended that all serializable classes explicitly declare
	 * serialVersionUID values, since the default serialVersionUID computation
	 * is highly sensitive to class details that may vary depending on compiler
	 * implementations, and can thus result in unexpected InvalidClassExceptions
	 * during deserialization. Therefore, to guarantee a consistent
	 * serialVersionUID value across different java compiler implementations, a
	 * serializable class must declare an explicit serialVersionUID value. It is
	 * also strongly advised that explicit serialVersionUID declarations use the
	 * private modifier where possible, since such declarations apply only to
	 * the immediately declaring class--serialVersionUID fields are not useful
	 * as inherited members.
	 */
	private static final long serialVersionUID = 7748919283250523252L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countTotal() throws PersistenceException {
		String countQuery = "SELECT COUNT(*) FROM Sitemap";
        
		Query query = getEntityManager().createQuery(countQuery);

		return (Long) query.getSingleResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteSitemap() throws PersistenceException {
		String sql = "TRUNCATE TABLE tblSitemap";

		Query query = getEntityManager().createNativeQuery(sql);
		Integer truncateResult = query.executeUpdate();

		return truncateResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Sitemap> getSitemaps(Integer pageNumber, Integer totalPages, Integer numberOfSitemapForPage) throws PersistenceException {
		String jpql = "FROM Sitemap ";

		Query query = null;
		logger.debug("JPQL Query : " + jpql);
		query = getEntityManager().createQuery(jpql);

        // We set pagination  
		query.setFirstResult(PageUtils.calculeStart(pageNumber, numberOfSitemapForPage));
		query.setMaxResults(numberOfSitemapForPage);

		// We manage sorting (this manages sorting on multiple fields)
		return (List<Sitemap>) query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer insertForumSitemap(HashMap<Integer, Date> activeForums) throws PersistenceException {
		Iterator<Map.Entry<Integer, Date>> iterator = activeForums.entrySet().iterator();
		Integer counter = new Integer(0);
		while (iterator.hasNext()) {
			Map.Entry<Integer, Date> pairs = (Map.Entry<Integer, Date>) iterator.next();

			Sitemap siteMap = new Sitemap();
			siteMap.setLocation(HtmlUtils.getSitemapForumUrl(pairs.getKey(), Boolean.TRUE));
			siteMap.setChangeFrequency(ChangeFrequency.HOURLY);
			siteMap.setDateCreated(new Date());
			siteMap.setLastModification(pairs.getValue());
			siteMap.setPriority(new Double(0.5));
			getEntityManager().persist(siteMap);
			
			counter++;
		}

		return counter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer insertForumTopicSitemap(HashMap<Integer, List<Object>> activeTopics) throws PersistenceException {
		Iterator<Map.Entry<Integer, List<Object>>> iterator = activeTopics.entrySet().iterator();
		Integer counter = new Integer(0);
		while (iterator.hasNext()) {
			Map.Entry<Integer, List<Object>> pairs = (Map.Entry<Integer, List<Object>>) iterator.next();
			List<Object> topicInformations = (List<Object>)pairs.getValue();
			Long numberOfPages = (Long)topicInformations.get(0);
			Date lastUpdate = (Date) topicInformations.get(1);

			//numberOfPages is extracted with round function...
			if (numberOfPages.equals(new Long(0)) || numberOfPages.equals(new Long(1))) {
				Sitemap siteMap = new Sitemap();
				siteMap.setLocation(HtmlUtils.getSitemapForumTopicUrl(pairs.getKey(), 1, numberOfPages.intValue(), 10, Boolean.TRUE));
				siteMap.setChangeFrequency(ChangeFrequency.HOURLY);
				siteMap.setLastModification(lastUpdate);
				siteMap.setDateCreated(new Date());
				siteMap.setPriority(new Double(0.5));
				getEntityManager().persist(siteMap);

				counter++;
			} else {
				for (int i=0; i<numberOfPages; i++) {
					Sitemap siteMap = new Sitemap();
					siteMap.setLocation(HtmlUtils.getSitemapForumTopicUrl(pairs.getKey(), i+1, numberOfPages.intValue(), 10, Boolean.TRUE));
					siteMap.setChangeFrequency(ChangeFrequency.HOURLY);
					siteMap.setLastModification(lastUpdate);
					siteMap.setDateCreated(new Date());
					siteMap.setPriority(new Double(0.5));
					getEntityManager().persist(siteMap);

					counter++;
				}
			}
		}

		return counter;
	}

}
