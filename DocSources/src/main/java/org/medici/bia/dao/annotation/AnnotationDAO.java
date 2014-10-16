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
	 * Returns the not deleted annotation with provided identifier.
	 * 
	 * @param annotationId annotation identifier
	 * @return the annotation found
	 * @throws PersistenceException
	 */
	Annotation findByAnnotationId(Integer annotationId) throws PersistenceException;
	
	/**
	 * Returns the annotation by its identifier in the provided image.
	 * 
	 * @param imageName the image name
	 * @param annotationId the annotation identifier
	 * @return the annotation found
	 * @throws PersistenceException
	 */
	Annotation getAnnotation(String imageName, Integer annotationId) throws PersistenceException;

	/**
	 * Returns the not deleted annotations linked to the provided image.
	 * 
	 * @param imageId the image identifier
	 * @return the list of annotations found
	 * @throws PersistenceException
	 */
	List<Annotation> getAnnotations(Integer imageId) throws PersistenceException;
	
	/**
	 * Returns the not deleted annotations linked to the provided image.<br/>
	 * Also returns personal annotation associated to the provided owner.
	 * 
	 * @param imageId the image identifier
	 * @param user the personal annotations owner
	 * @return the list of annotations found
	 * @throws PersistenceException
	 */
	List<Annotation> getAnnotations(Integer imageId, User user) throws PersistenceException;
	
	/**
	 * Returns the not deleted annotations linked to the provided image.<br/>
	 * Also returns personal annotation associated to the provided owner and does not consider the provided types.
	 * 
	 * @param imageId the image identifier
	 * @param user the personal annotations owner
	 * @param notConsideredTypes the not considered annotation types
	 * @return the list of annotations found
	 * @throws PersistenceException
	 */
	List<Annotation> getAnnotations(Integer imageId, User user, List<Annotation.Type> notConsideredTypes) throws PersistenceException;
	
	/**
	 * Returns the annotations linked to a community forum topic.
	 * 
	 * @return the list of annotations found
	 * @throws PersistenceException
	 */
	List<Annotation> getForumAnnotations() throws PersistenceException;

	/**
	 * Returns the not deleted personal annotations associated to the provided user.
	 * 
	 * @param user the personal annotations owner
	 * @param paginationFilter the pagination filter
	 * @return the {@link Page} with the annotations found
	 * @throws PersistenceException
	 */
	Page getPersonalAnnotations(User user, PaginationFilter paginationFilter) throws PersistenceException;
	
	/**
	 * Returns the list of annotations associated to the image provided.
	 * The annotations are filtered by topics contained in the provided forum (so personal annotations are never returned).
	 * Annotations could also filtered by a specific type.
	 * 
	 * @param imageName the image name
	 * @param forumId the forum container identifier
	 * @param type the annotation type
	 * @return the list of annotations found
	 * @throws PersistenceException
	 */
	List<Annotation> getTopicImageAnnotations(String imageName, Integer forumId, Annotation.Type type) throws PersistenceException;
	
	/**
	 * 
	 * @param originalAccount
	 * @param newAccount
	 * @return
	 * @throws PersistenceException
	 */
	Integer renameAccount(String originalAccount, String newAccount) throws PersistenceException;

}
