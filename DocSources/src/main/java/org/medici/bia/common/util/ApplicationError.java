/*
 * ApplicationError.java
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
package org.medici.bia.common.util;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public enum ApplicationError {
	/** GENERIC ERRORS 001 - 010 **/
	GENERIC_ERROR("001"),
	UNKNOWN_ERROR("002"),

	/** LDAP ERRORS 031 - 040 **/
	LDAP_ATTRIBUTE_TYPE_UNDEFINED_ERROR("031"), 
	LDAP_NAME_NOT_FOUND_ERROR("032"), 
	LDAP_SERVER_NOT_RESPONDING_ERROR("033"), 
	
	/** RUNTIME ERRORS 021 - 030 **/
	NULLPOINTER_ERROR("021"),
	RECORD_NOT_FOUND_ERROR("023"),
	UNIQUE_CONSTRAINT_VIOLATED_ERROR("022"),
	MISSING_IDENTIFIER("024"),
	MISSING_PARAMETER("025"),
	ILLEGAL_STATUS("026"),
	ILLEGAL_CALL("027"),
	
	/** DATABASE ERRORS 011 - 020 **/
	DB_UNKNOWN_ERROR("011"), 
	DB_CONNECTION_LOST_ERROR("012"), 
	DB_INCORRECT_SQL_ERROR("013"),

	/** SERVICE ERRORS 100 - 101 **/
	USER_NOT_FOUND_ERROR("100"),
	USER_NAME_NOT_FOUND_ERROR("101");
	

	private final String errorCode;

	private ApplicationError(String value) {
		errorCode = value;
	}

	@Override
	public String toString() {
		return errorCode;
	}
}
