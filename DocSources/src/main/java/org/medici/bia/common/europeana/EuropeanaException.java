/*
 * EuropeanaException.java
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
package org.medici.bia.common.europeana;

/**
 * Custom Exception for the Europeana xml serialization process.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class EuropeanaException extends Exception {

	private static final long serialVersionUID = 8731895470099399706L;
	
	public static final String IOERROR = "Input/Output error";
	public static final String INITERROR = "JAXB initialization error";
	public static final String NULLDATA = "Serializer cannot manage null data";
	public static final String SERIALIZATIONERROR = "Serialziation error";
	public static final String SERIALIZERNOTINITIALIZED = "Serialzier not initialized";
	
	public EuropeanaException(String message) {
		super(message);
	}
	
	public EuropeanaException(String message, Throwable th) {
		super(message, th);
	}

}
