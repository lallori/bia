/*
 * OAPerson.java
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
package org.medici.bia.common.openannotation.model;

import org.medici.bia.common.openannotation.OAConstants;
import org.medici.bia.common.openannotation.OASerializableField;

/**
 * This class defines creator of open annotations.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OAPerson extends OpenAnnotationElement {
	
	/**
	 * The extended name.
	 */
	@OASerializableField
	private String name;
	
	/**
	 * The first name.
	 */
	@OASerializableField(value = OAConstants.FOAF_FIRST_NAME)
	private String firstName;
	
	/**
	 * The surname.
	 */
	@OASerializableField(value = OAConstants.FOAF_SURNAME)
	private String surname;
	
	/**
	 * Constructor with empty name.
	 */
	public OAPerson() {
		this.addType(OAConstants.FOAF_PERSON);
	}
	
	/**
	 * Constructor with provided name.
	 * 
	 * @param firstName the first name
	 * @param surname the surname
	 */
	public OAPerson(String firstName, String surname) {
		this();
		this.setFirstName(firstName);
		this.setSurname(surname);
		this.setName(firstName + " " + surname);
	}

	/**
	 * Returns the extended name.
	 * 
	 * @return the extended name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the extended name.
	 * 
	 * @param name the extended name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 * 
	 * @param firstName the first name to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the surname.
	 * 
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname.
	 * 
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

}
