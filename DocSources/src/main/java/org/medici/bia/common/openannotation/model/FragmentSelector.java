/*
 * FragmentSelector.java
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
 * This class defines a fragment selector (i.e. of text or image) for annotation bodies or annotation targets.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class FragmentSelector extends Selector {
	
	/**
	 * The selector structure.
	 */
	@OASerializableField(value = "conformsTo", valueFor = OAConstants.DCTERMS_CONFORMS_TO)
	private String conformsSpec;
	
	/**
	 * The selector value.
	 */
	@OASerializableField(valueFor = OAConstants.RDF_VALUE)
	private String value;
	
	public FragmentSelector() {
		this.addType(OAConstants.OA_FRAGMENT_SELECTOR);
	}
	
	/**
	 * Returns the selector structure.
	 * 
	 * @return the selector structure
	 */
	public String getConformsSpec() {
		return conformsSpec;
	}

	/**
	 * Sets the selector structure.
	 * 
	 * @param conformsTo the selector structure to set
	 */
	public void setConformsSpec(String conformsSpec) {
		this.conformsSpec = conformsSpec;
	}

	/**
	 * Returns the selector value.
	 * 
	 * @return the selector value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the selector value.
	 * 
	 * @param value the selector value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
