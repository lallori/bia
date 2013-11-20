/*
 * Source.java
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
 * This class defines open annotation sources (bodies or targets).
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public abstract class Source extends OpenAnnotationElement {
	
	/**
	 * The source title.
	 */
	@OASerializableField(valueFor = OAConstants.RDFS_LABEL)
	private String label;
	
	/**
	 * The source subject.
	 */
	@OASerializableField(value = OAConstants.DC_SUBJECT)
	private String subject;

	/**
	 * Returns the source title.
	 * 
	 * @return the source title
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the source title.
	 * 
	 * @param label the source title to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the source subject.
	 * 
	 * @return the source subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the source subject.
	 * 
	 * @param subject the source subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
