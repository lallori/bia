/*
 * OpenAnnotationElement.java
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

import java.util.ArrayList;
import java.util.List;

import org.medici.bia.common.openannotation.OASerializable;
import org.medici.bia.common.openannotation.OASerializableField;

/**
 * This class defines a generic element for open annotations.<br/>
 * It only establishes the following minimum contract of open annotation elements:
 * <ul>
 * <li><b>id</b> -&gt; the element identifier</li>
 * <li><b>types</b> -&gt; a collection of element types</li>
 * </ul>
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public abstract class OpenAnnotationElement implements OASerializable {
	
	/**
	 * The element identifier.
	 */
	@OASerializableField(value = "@id")
	private String id;
	
	/**
	 * A collection of element types.
	 */
	@OASerializableField(value = "@type")
	private List<String> types;

	/**
	 * Returns the element identifier.
	 * 
	 * @return the element identifier
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the element identifier.
	 * 
	 * @param id the element identifier to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns all the element types.
	 * 
	 * @return the element types
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * Associates a type to this element.
	 * 
	 * @param type the type to add
	 */
	public void addType(String type) {
		if (types == null) {
			types = new ArrayList<String>();
		}
		types.add(type);
	}
	
	/**
	 * Removes a type from the element types.
	 * 
	 * @param type the element type to remove
	 * @return true if the element had the type in its own collection.
	 */
	public boolean removeType(String type) {
		if (types != null) {
			return types.remove(type);
		}
		return false;
	}
	
}
