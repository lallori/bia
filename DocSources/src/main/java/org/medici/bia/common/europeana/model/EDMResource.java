/*
 * SimpleResource.java
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
package org.medici.bia.common.europeana.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Superclass of all Europeana elements.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@XmlRootElement(name = "EDMResource")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class EDMResource {
	
	/**
	 * The reference attribute of the Europeana element.
	 */
	@XmlAttribute(name = "rdf:about")
	private String reference;

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@SuppressWarnings("unchecked")
	protected <T extends LanguageResource> void _addLanguageValue(String lang, String value, Class<T> clazz, List<T> list) {
		T lngRes = null;
		if (LanguageResource.class.equals(clazz)) {
			lngRes = (T)new LanguageResource(lang);
		} else if (LanguageResourceOrReference.class.equals(clazz)) {
			lngRes = (T)new LanguageResourceOrReference();
			((LanguageResourceOrReference)lngRes).setLang(lang);
		}
		lngRes.setValue(value);
		list.add(lngRes);
	}
	
	protected void _addLiteral(String value, List<LiteralOrReference> list) {
		LiteralOrReference lit = new LiteralOrReference();
		lit.setValue(value);
		list.add(lit);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> void _addReference(String url, Class<T> clazz, List<T> list) {
		Object ref = null;
		if (LiteralOrReference.class.equals(clazz)) {
			ref = new LiteralOrReference();
			((LiteralOrReference)ref).setUrl(url);
		} else if (ReferenceableResource.class.equals(clazz)) {
			ref = new ReferenceableResource(url);
		} else if (LanguageResourceOrReference.class.equals(clazz)) {
			ref = new LanguageResourceOrReference();
			((LanguageResourceOrReference)ref).setReference(url);
		}
		list.add((T)ref);
	}
	
	protected void _addValue(String value, List<ValuableResource> list) {
		ValuableResource valuable = new ValuableResource(value);
		list.add(valuable);
	}
	
}
