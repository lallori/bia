/*
 * DOMHelperException.java
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
package org.medici.bia.exception;

import org.w3c.dom.DOMException;

/**
 * Exception launched by a {@link DOMHelper}.
 *
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class DOMHelperException extends DOMException {

	private static final long serialVersionUID = 2981062507274896345L;
	
	public static final short CONVERSION_PROBLEM = 100;
	public static final short NOT_HTML = 101;
	public static final short NOT_TEXT_NODE = 102;
	public static final short OUT_OF_BOUNDS = 103;
	public static final short DOM_CREATION_PROBLEM = 104;

	public DOMHelperException(short code, String message) {
		super(code, message);
	}

}