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
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public abstract class OpenAnnotationElement implements OASerializable {
	
	@OASerializableField(value = "@id")
	private String id;
	
	@OASerializableField(value = "@type")
	private List<String> types;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getTypes() {
		return types;
	}

	public void addType(String type) {
		if (types == null) {
			types = new ArrayList<String>();
		}
		types.add(type);
	}
	
	public boolean removeType(String type) {
		if (types != null) {
			return types.remove(type);
		}
		return false;
	}
	
}
