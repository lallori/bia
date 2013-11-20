/*
 * OAException.java
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
package org.medici.bia.common.openannotation;

/**
 * This Exception can be launched during json-ld serialization.
 *  
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OAException extends Exception {

	private static final long serialVersionUID = -3859794350006967837L;
	
	public static final String NULLDATA = "It is not possible to serialize null data";
	public static final String SERIALIZATIONERROR = "Serialziation error";
	public static final String SERIALIZERNOTINITIALIZED = "Serializer not initialized";
	public static final String SERIALIZERNOTSTARTED = "Serializer not started";
	public static final String SERIALIZEROUTOFBOUND = "Serializer reached the data limits";
	
	public OAException(String message) {
		super(message);
	}
	
	public OAException(String message, Throwable th) {
		super(message, th);
	}

}
