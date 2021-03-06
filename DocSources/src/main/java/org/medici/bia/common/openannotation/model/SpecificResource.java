/*
 * SpecificResource.java
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
 * This class is for describing specific resources.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class SpecificResource extends Source {
	
	/**
	 * The source of this specific resource.
	 */
	@OASerializableField(value = "hasSource", valueFor = OAConstants.OA_HAS_SOURCE)
	private Source source;
	
	/**
	 * The selector of this specific resource (if needed).
	 */
	@OASerializableField(value = "hasSelector", valueFor = OAConstants.OA_HAS_SELECTOR)
	private Selector selector;
	
	public SpecificResource() {
		this.addType(OAConstants.OA_SPECIFIC_RESOURCE);
	}

	/**
	 * Returns the source.
	 * 
	 * @return the source
	 */
	public Source getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 * 
	 * @param source the source to set
	 */
	public void setSource(Source source) {
		this.source = source;
	}

	/**
	 * Returns the selector.
	 * 
	 * @return the selector
	 */
	public Selector getSelector() {
		return selector;
	}

	/**
	 * Sets the selector.
	 * 
	 * @param selector the selector to set
	 */
	public void setSelector(Selector selector) {
		this.selector = selector;
	}

}
