/*
 * SitemapIndexDAO.java
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

import java.util.List;
import javax.persistence.PersistenceException;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Sitemap;
import org.medici.bia.domain.SitemapIndex;

/**
 * Site Map Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface SitemapIndexDAO extends Dao<String, SitemapIndex>{

	/**
	 * 
	 * @return
	 * @throws PersistenceException
	 */
	Integer deleteSitemapIndex() throws PersistenceException;

	/**
	 * 
	 * @param i
	 * @param list
	 * @throws PersistenceException
	 */
	void generatePage(Integer pageNumber, List<Sitemap> list) throws PersistenceException;

	/**
	 * 
	 * @return
	 */
	List<SitemapIndex> getAllSitemapIndex() throws PersistenceException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws PersistenceException
	 */
	SitemapIndex getSitemapIndex(Integer id) throws PersistenceException;
}
