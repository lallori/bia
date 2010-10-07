/*
 * JpaDao.java
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
package org.medici.docsources.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import org.springframework.stereotype.Repository;

/**
 * @param <K>
 * @param <E>
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@Repository
public abstract class JpaDao<K, E> implements Dao<K, E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4380540983397163140L;

	protected Class<E> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
		.getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass
		.getActualTypeArguments()[1];
	}

	public E find(K id) throws PersistenceException {
		return getEntityManager().find(entityClass, id);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void merge(E entity) throws PersistenceException {
		getEntityManager().merge(entity);
	}

	public void persist(E entity) throws PersistenceException {
		getEntityManager().persist(entity);
	}

	public void remove(E entity) throws PersistenceException {
		getEntityManager().remove(entity);
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}