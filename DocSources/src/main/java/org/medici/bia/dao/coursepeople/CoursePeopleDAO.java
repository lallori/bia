/*
 * CoursePeopleDAO.java
 *
 * Developed by The Medici Archive Project Inc. (2010-2012)
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
package org.medici.bia.dao.coursepeople;

import java.util.List;

import javax.persistence.PersistenceException;

import org.medici.bia.common.pagination.Page;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.dao.Dao;
import org.medici.bia.domain.CoursePeople;
import org.medici.bia.domain.UserAuthority.Authority;
import org.medici.bia.domain.UserRole;

/**
 * Course People Dao.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface CoursePeopleDAO extends Dao<Integer, CoursePeople> {
	
	/**
	 * Returns all the course people.
	 * 
	 * @param courseId the course identifier
	 * @param filteredAuth the filtered authorities (null if none); possible filtered authorities are STUDENTS or TEACHERS
	 * @return the course people found
	 * @throws PersistenceException
	 */
	List<CoursePeople> getCoursePeople(Integer courseId, List<Authority> filteredAuth) throws PersistenceException;

	/**
	 * Returns the paginated course people.
	 * 
	 * @param courseId the course identifier
	 * @param filteredAuth the filtered authorities (null if none); possible filtered authorities are STUDENTS or TEACHERS
	 * @param paginationFilter the pagination filter
	 * @return the paginated course people
	 */
	Page getCoursePeople(Integer courseId, List<Authority> filteredAuth, PaginationFilter paginationFilter) throws PersistenceException;

	/**
	 * Returns the {@link CoursePeople} by the provided filters.
	 * 
	 * @param courseId the course identifier
	 * @param account the user account
	 * @return true if the user is associated to the course, false otherwise
	 */
	CoursePeople getCoursePerson(Integer courseId, String account) throws PersistenceException;
	
	/**
	 * Returns true if the provided account is associated to the course.
	 * 
	 * @param courseId the course identifier
	 * @param account the user account
	 * @return true if the user is a {@link CoursePeople} or if he is a teacher, false otherwise
	 * @throws PersistenceException
	 */
	boolean isCoursePerson(Integer courseId, String account) throws PersistenceException;
	
	/**
	 * Removes all course people with roles correspondent to the provided filteredAuthorities.
	 * 
	 * @param courseId the course identifier
	 * @param filteredAuthorities the authorities to search
	 * @return the number of removed course people
	 * @throws PersistenceException
	 */
	int removeAllCoursePeople(Integer courseId, List<Authority> filteredAuthorities) throws PersistenceException;
	
	/**
	 * Removes the course people with the provided accounts.
	 * 
	 * @param courseId the course identifier
	 * @param accounts teh list of accounts to search
	 * @return the number of removed course people
	 * @throws PersistenceException
	 */
	int removeCoursePeople(Integer courseId, List<String> accounts) throws PersistenceException;

	/**
	 * Removes the course person entries (if exist) by the provided user role.
	 *  
	 * @param userRole the user role
	 * @return the number of removed course people entries
	 * @throws PersistenceException
	 */
	int removeCoursePersonByUserRole(UserRole userRole) throws PersistenceException;

}
