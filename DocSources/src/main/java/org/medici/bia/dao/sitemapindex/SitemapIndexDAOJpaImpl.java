/*
 * SitemapIndexDAOJpaImpl.java
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
package org.medici.bia.dao.sitemapindex;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.HtmlUtils;
import org.medici.bia.dao.JpaDao;
import org.medici.bia.domain.Sitemap;
import org.medici.bia.domain.SitemapIndex;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Repository
@Transactional(readOnly = true)
public class SitemapIndexDAOJpaImpl extends JpaDao<String, SitemapIndex> implements SitemapIndexDAO {

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
	private static final long serialVersionUID = -6862056262278209378L;

	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteSitemapIndex() throws PersistenceException {
		String sql = "TRUNCATE TABLE tblSitemapIndex";

		Query query = getEntityManager().createNativeQuery(sql);
		Integer truncateResult = query.executeUpdate();

		return truncateResult;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generatePage(Integer pageNumber, List<Sitemap> list) throws PersistenceException {
		String website = ApplicationPropertyManager.getApplicationProperty("website.protocol") + "://" + ApplicationPropertyManager.getApplicationProperty("website.domain");
		File workingDirectory = new File(System.getProperty("java.io.tmpdir"));
		
		try {
			WebSitemapGenerator webSitemapGenerator = new WebSitemapGenerator(website, workingDirectory);
			for (int i=0; i<list.size(); i++) {
				WebSitemapUrl webSitemapUrl = new WebSitemapUrl.
				Options(list.get(i).getLocation()).
				lastMod(list.get(i).getLastModification()).
				priority(list.get(i).getPriority()).
				changeFreq(ChangeFreq.valueOf(list.get(i).getChangeFrequency().toString())).
				build();

				webSitemapGenerator.addUrl(webSitemapUrl); // repeat multiple times
			}
			webSitemapGenerator.write();
			
			SitemapIndex siteMapIndex = new SitemapIndex();
			siteMapIndex.setId(pageNumber);
			siteMapIndex.setLocation(HtmlUtils.getSitemapUrl(pageNumber));
			siteMapIndex.setLastModification(new Date());
			File siteMapXmlFile = new File(System.getProperty("java.io.tmpdir") + "sitemap.xml");
			siteMapIndex.setXmlFile(FileUtils.readFileToString(siteMapXmlFile));
			getEntityManager().persist(siteMapIndex);
		} catch (MalformedURLException malformedURLException) {
			logger.error("Error during init of WebSitemapGenerator ", malformedURLException);
		} catch (Throwable throwable) {
			logger.error("Error ", throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SitemapIndex> getAllSitemapIndex() {
		String queryString = "FROM SitemapIndex";

		Query query = getEntityManager().createQuery(queryString);

		return (List<SitemapIndex>) query.getResultList();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SitemapIndex getSitemapIndex(Integer id) throws PersistenceException {
		String jpql = "FROM SitemapIndex WHERE id=:id";

		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("id", id);
		List<SitemapIndex> list = (List<SitemapIndex>)query.getResultList();

		if (list.size() == 1) {
			return list.get(0);
		}
		
		return null;
	}
}
