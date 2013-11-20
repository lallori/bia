/*
 * Text.java
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
 * This class represents text content for open annotation sources (bodies or targets).
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public final class Text extends Source {
	
	/**
	 * The text content.
	 */
	@OASerializableField(valueFor = OAConstants.CNT_CHARS)
	private String chars;
	
	/**
	 * The format of the text content (i.e. plain text or html)
	 */
	@OASerializableField(valueFor = OAConstants.DC_FORMAT)
	private String format;
	
	/**
	 * Empty constructor.
	 */
	public Text() {
		this.addType(OAConstants.CNT_CONTENT_AS_TEXT);
		this.addType(OAConstants.DCTYPES_TYPE_TEXT);
	}
	
	/**
	 * Constructor with provided text content.
	 * 
	 * @param chars the text content
	 */
	public Text(String chars) {
		this();
		this.setChars(chars);
	}

	/**
	 * Returns the text content.
	 * 
	 * @return the text content
	 */
	public String getChars() {
		return chars;
	}

	/**
	 * Sets the text content.
	 * 
	 * @param chars the text content to set
	 */
	public void setChars(String chars) {
		this.chars = chars;
	}

	/**
	 * Returns the text format.
	 * 
	 * @return the text format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * Sets the text format.
	 * 
	 * @param format the text format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

}
