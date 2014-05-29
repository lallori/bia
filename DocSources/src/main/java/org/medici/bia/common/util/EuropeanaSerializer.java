/*
 * EuropeanaSerializer.java
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
package org.medici.bia.common.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.medici.bia.common.europeana.EuropeanaException;

/**
 * The xml serializer for the europeana serialization process. 
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 * @param <T> the type of the elements to serialize
 */
public class EuropeanaSerializer<T> {
	
	private Marshaller m;
	
	private List<T> data = new ArrayList<T>();
	private int nextIdx = -1;
	
	/**
	 * Adds an item to the data to serialize
	 * 
	 * @param item the item to add
	 */
	public void addData(T item) {
		data.add(item);
	}
	
	/**
	 * Adds the provided items to the data to serialize
	 * 
	 * @param items the items to add
	 */
	public void addDatas(Collection<T> items) {
		data.addAll(items);
	}
	
	/**
	 * Removes all the items provided from the data to serialize.
	 */
	public void clearData() {
		data.clear();
		nextIdx = 0;
	}
	
	/**
	 * Initializes the serializer.
	 * 
	 * @param clazz the type of the items to serialize
	 * @throws EuropeanaException if initialization process goes wrong
	 */
	public void init(Class<T> clazz) throws EuropeanaException {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FRAGMENT, true);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (JAXBException jaxbex) {
			throw new EuropeanaException(EuropeanaException.INITERROR, jaxbex);
		}
		nextIdx = 0;
	}
	
	/**
	 * Returns true if there are others element to serialize.
	 * 
	 * @return true if there are other elements to serialize, false otherwise
	 * @throws EuropeanaException if serializer is not initialized
	 */
	public boolean hasNext() throws EuropeanaException {
		if (nextIdx == -1) {
			throw new EuropeanaException(EuropeanaException.SERIALIZERNOTINITIALIZED);
		}
		return nextIdx < data.size();
	}
	
	/**
	 * Returns the xml serialization of the current pointed item.
	 * 
	 * @return the xml serialization of the current pointed item; if serializer has no more items
	 * it returns null
	 * @throws EuropeanaException if serializer is not initialized of something goes wrong with the
	 * xml serialization
	 */
	public String next() throws EuropeanaException {
		if (nextIdx == -1) {
			throw new EuropeanaException(EuropeanaException.SERIALIZERNOTINITIALIZED);
		}
		if (nextIdx >= data.size()) {
			return null;
		}
		StringWriter w = new StringWriter();
		try {
			m.marshal((T)data.get(nextIdx), w);
		} catch (JAXBException jaxbex) {
			throw new EuropeanaException(EuropeanaException.SERIALIZATIONERROR, jaxbex);
		}
		nextIdx++;
		return w.toString();
	}
	
}
