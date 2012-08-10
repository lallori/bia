/*
 * AdminService.java
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
package org.medici.docsources.service.admin;

import java.util.HashMap;
import java.util.List;

import org.medici.docsources.common.pagination.Page;
import org.medici.docsources.common.pagination.PaginationFilter;
import org.medici.docsources.domain.Month;
import org.medici.docsources.domain.User;
import org.medici.docsources.domain.UserAuthority;
import org.medici.docsources.domain.UserAuthority.Authority;
import org.medici.docsources.exception.ApplicationThrowable;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public interface AdminService {

	/**
	 * 
	 * @throws ApplicationThrowable
	 */
	public void createAccessLogDailyStatistics() throws ApplicationThrowable;
	
	/**
	 * 
	 * @param user
	 * @param userInformation
	 * @throws ApplicationThrowable
	 */
	public void editUser(User user) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param account
	 * @return
	 * @throws ApplicationThrowable
	 */
	public User findUser(String account) throws ApplicationThrowable;

	/**
	 * 
	 * @param user
	 * @return
	 * @throws org.medici.docsources.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public List<User> findUsers(User user) throws ApplicationThrowable;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws org.medici.docsources.exception.ApplicationThrowable Exception throwed if an error is occured.
	 */
	public Page findUsers(User user, PaginationFilter paginationFilter) throws ApplicationThrowable;

	/**
	 * 
	 * @return
	 * @throws ApplicationThrowable
	 */
	public List<UserAuthority> getAuthorities() throws ApplicationThrowable;

	/**
	 * Extracts all months available.
	 *  
	 * @return {@link java.util.List} of {@link org.medici.docsources.domain.Month}
	 * object
	 * @throws ApplicationThrowable if an error occurs while the service is handling the request.
	 * 
	 */
	public List<Month> getMonths() throws ApplicationThrowable;

	/**
	 * 
	 * @param hashMap
	 * @throws ApplicationThrowable
	 */
	public void updateApplicationProperties(HashMap<String, String> hashMap) throws ApplicationThrowable;
}
