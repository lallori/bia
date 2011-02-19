/*
 * ImageDAO.java
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

import javax.persistence.PersistenceException;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.common.pagination.VolumeExplorer;
import org.medici.docsources.dao.Dao;
import org.medici.docsources.domain.Image;
import org.medici.docsources.domain.Image.ImageType;

/**
 * Image Dao.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public interface ImageDAO extends Dao<Integer, Image> {

	/**
	 *  
	 * @param volNum MDP
	 * @param volLetExt MDP Extension
	 * @param folioNum
	 * @param folioMod
	 * @return
	 * @throws PersistenceException
	 */
	public Image findDocumentImage(Integer volNum, String volLetExt, Integer folioNum, String folioMod) throws PersistenceException;

	/**
	 * This method returns a list of Images linked to a specific volume.
	 * 
	 * @param volNum MDP
	 * @param volLetExt MDP Extension
	 * @return
	 * @throws PersistenceException
	 */
	public List<Image> findImages(Integer volNum, String volLetExt) throws PersistenceException;

	/**
	 * This method returns a Paged list of Images linked to a specific volume .
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	public Page findImages(Integer volNum, String volLetExt, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * 
	 * @param volumeExplorer
	 * @return
	 * @throws PersistenceException
	 */
	public VolumeExplorer findImages(VolumeExplorer volumeExplorer) throws PersistenceException;

	/**
	 * 
	 * @param volNum
	 * @param volLetExt
	 * @param imageType
	 * @param imageProgTypeNum
	 * @return
	 * @throws PersistenceException
	 */
	public List<Image> findVolumeImages(Integer volNum, String volLetExt, ImageType imageType, Integer imageProgTypeNum) throws PersistenceException;
}
