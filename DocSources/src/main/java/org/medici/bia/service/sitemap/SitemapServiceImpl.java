/*
 * SitemapServiceImpl.java
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
package org.medici.bia.service.sitemap;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.util.PageUtils;
import org.medici.bia.dao.forum.ForumDAO;
import org.medici.bia.dao.forumpost.ForumPostDAO;
import org.medici.bia.dao.forumtopic.ForumTopicDAO;
import org.medici.bia.dao.sitemap.SitemapDAO;
import org.medici.bia.dao.sitemapindex.SitemapIndexDAO;
import org.medici.bia.domain.Sitemap;
import org.medici.bia.domain.SitemapIndex;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is the default implementation of service responsible for every 
 * sitemap.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Service
@Transactional(readOnly=true)
public class SitemapServiceImpl implements SitemapService {

	@Autowired
	private ForumDAO forumDAO;

	@Autowired
	private ForumPostDAO forumPostDAO;

	@Autowired
	private ForumTopicDAO forumTopicDAO;

	@Autowired
	private SitemapDAO sitemapDAO;

	@Autowired
	private SitemapIndexDAO sitemapIndexDAO;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Integer deleteSitemap() throws ApplicationThrowable {
		try {
			return getSitemapDAO().deleteSitemap();
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer deleteSitemapIndex() throws ApplicationThrowable {
		try {
			return getSitemapIndexDAO().deleteSitemapIndex();
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * 
	 */
	@Override
	public Integer generateForumPostSitemap() throws ApplicationThrowable {
		return new Integer(0);
	}


	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Integer generateForumSitemap() throws ApplicationThrowable {
		try {
			Long total = getForumDAO().countTotalActive();
			Integer numberOfForumForPage = 50;
			Integer totalPages = PageUtils.calculeTotalPages(total, numberOfForumForPage);
			Integer counter = new Integer(0);
			for (int i=0; i<totalPages; i++) {
				Map<Integer, Date> activeForumsInformations = getForumDAO().getActiveForumsInformations(i+1, numberOfForumForPage);
			
				counter += getSitemapDAO().insertForumSitemap(activeForumsInformations);
			}

			return counter;
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Override
	public Integer generateForumTopicsSitemap() throws ApplicationThrowable {
		try {
			Long total = getForumTopicDAO().countTotalActive();
			Integer numberOfTopicsForPage = 50;
			Integer totalPages = PageUtils.calculeTotalPages(total, numberOfTopicsForPage);
			Integer counter = new Integer(0);
			for (int i=0; i<totalPages; i++) {
				Map<Integer, List<Object>> activeTopicsInformations= getForumPostDAO().getActiveTopicsInformations(i+1, numberOfTopicsForPage);
			
				counter += getSitemapDAO().insertForumTopicSitemap(activeTopicsInformations);
			}

			return counter;			
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void generateSitemapIndex() throws ApplicationThrowable {
		try {
			Long total = getSitemapDAO().countTotal();
			Integer numberOfSitemapForPage = 10000;
			Integer totalPages = PageUtils.calculeTotalPages(total, numberOfSitemapForPage);

			for (int i=0; i<totalPages; i++) {
				List<Sitemap> list = getSitemapDAO().getSitemaps(i+1, totalPages, numberOfSitemapForPage);
				getSitemapIndexDAO().generatePage(i, list);
			}
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * @return the forumDAO
	 */
	public ForumDAO getForumDAO() {
		return forumDAO;
	}

	/**
	 * @return the forumPostDAO
	 */
	public ForumPostDAO getForumPostDAO() {
		return forumPostDAO;
	}

	/**
	 * @return the forumTopicDAO
	 */
	public ForumTopicDAO getForumTopicDAO() {
		return forumTopicDAO;
	}

	/**
	 * 
	 * @return
	 */
	public SitemapDAO getSitemapDAO() {
		return sitemapDAO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SitemapIndex getSitemapFromSitemapIndex(Integer id) throws ApplicationThrowable {
		try {
			return getSitemapIndexDAO().getSitemapIndex(id);
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<SitemapIndex> getSitemapIndex() throws ApplicationThrowable {
		try {
			return getSitemapIndexDAO().getAllSitemapIndex();
		} catch (Throwable throwable) {
			throw new ApplicationThrowable(throwable);
		}
	}

	public SitemapIndexDAO getSitemapIndexDAO() {
		return sitemapIndexDAO;
	}

	/**
	 * @param forumDAO the forumDAO to set
	 */
	public void setForumDAO(ForumDAO forumDAO) {
		this.forumDAO = forumDAO;
	}

	/**
	 * @param forumPostDAO the forumPostDAO to set
	 */
	public void setForumPostDAO(ForumPostDAO forumPostDAO) {
		this.forumPostDAO = forumPostDAO;
	}

	/**
	 * @param forumTopicDAO the forumTopicDAO to set
	 */
	public void setForumTopicDAO(ForumTopicDAO forumTopicDAO) {
		this.forumTopicDAO = forumTopicDAO;
	}

	/**
	 * 
	 * @param siteMapDAO
	 */
	public void setSitemapDAO(SitemapDAO sitemapDAO) {
		this.sitemapDAO = sitemapDAO;
	}

	public void setSitemapIndexDAO(SitemapIndexDAO sitemapIndexDAO) {
		this.sitemapIndexDAO = sitemapIndexDAO;
	}
}
