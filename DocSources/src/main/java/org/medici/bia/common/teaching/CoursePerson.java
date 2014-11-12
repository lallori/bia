/*
 * CoursePerson.java
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
package org.medici.bia.common.teaching;

import org.medici.bia.domain.CoursePeople;
import org.medici.bia.domain.UserAuthority;
import org.medici.bia.domain.UserAuthority.Authority;

/**
 * This class is a view of {@link CoursePeople}. It only contains data for the ShowCourseStudents.jsp
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class CoursePerson {
	
	private String name;
	
	private String account;
	
	private Boolean subscription;
	
	private Authority role;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}

	/**
	 * @return the subscription
	 */
	public Boolean getSubscription() {
		return subscription;
	}

	/**
	 * @param subscription the subscription to set
	 */
	public void setSubscription(Boolean subscription) {
		this.subscription = subscription;
	}
	
	/**
	 * @return the role
	 */
	public Authority getRole() {
		return role;
	}
	
	/**
	 * @param role the role to set
	 */
	public void setRole(UserAuthority.Authority role) {
		this.role = role;
	}
	
	public static CoursePerson convert(CoursePeople coursePeople) {
		CoursePerson person = new CoursePerson();
		
		person.setName(coursePeople.getUserRole().getUser().getLastName() + " " + coursePeople.getUserRole().getUser().getFirstName());
		person.setAccount(coursePeople.getUserRole().getUser().getAccount());
		person.setSubscription(coursePeople.getSubscription());
		person.setRole(coursePeople.getUserRole().getUserAuthority().getAuthority());
		
		return person;
	}

}
