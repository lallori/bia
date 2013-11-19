/*
 * AnnotationDAO.java
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
package org.medici.bia.dao.annotation;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.User;

/**
 * Annotation Dao.
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *         @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *         @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 */
public interface AnnotationDAO extends Dao<Integer, Annotation> {

	/**
	 * 
	 * @param imageName
	 * @return
	 * @throws PersistenceException
	 */
	List<Annotation> findAnnotationsByImage(String imageName) throws PersistenceException;
	
	/**
	 * This method retrieves annotations associated to the provided image.<br/>
	 * It retrieves also personal annotation associated to the provided owner.
	 * 
	 * @param imageName the name of the image
	 * @param user the owner of the annotations
	 * @return a list of annotations associated to the provided image and owner (for personal annotations)
	 * @throws PersistenceException
	 */
	List<Annotation> findAnnotationByImageAndUser(String imageName, User user) throws PersistenceException;

	/**
	 * 
	 * @param annotationId
	 * @return
	 * @throws PersistenceException
	 */
	Annotation findByAnnotationId(Integer annotationId) throws PersistenceException;
	
	/**
	 * 
	 * @param user
	 * @param paginationFilter
	 * @return
	 * @throws PersistenceException
	 */
	Page findPersonalAnnotations(User user, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * This method retrieves the annotations associated to a forum topic.
	 * 
	 * @return a list of annotations
	 * @throws PersistenceException
	 */
	List<Annotation> findForumAnnotations() throws PersistenceException;

	/**
	 * 
	 * @param originalAccount
	 * @param newAccount
	 * @return
	 * @throws PersistenceException
	 */
	Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException;
}
