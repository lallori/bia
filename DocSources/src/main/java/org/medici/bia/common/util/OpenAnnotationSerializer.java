/*
 * OpenAnnotationSerializer.java
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

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.medici.bia.common.openannotation.OAException;
import org.medici.bia.common.openannotation.OASerializable;
import org.medici.bia.common.openannotation.OASerializableField;
import org.medici.bia.common.openannotation.model.OpenAnnotationElement;
import org.medici.bia.common.openannotation.model.OpenAnnotationObject;

/**
 * This class is a serializer for {@link OASerializable} elements.<br/>
 * It returns json serialization of the OpenAnnotation elements provided during the initialization.
 * The serialization conforms to the <a href='http://www.openannotation.org/'>Open Annotation Collaboration</a>
 * specification (see also 
 * <a href='http://www.openannotation.org/spec/core/publishing.html#Serialization'>Open Annotation Serialization</a>).
 * <br/>
 * <br/>
 * 
 * There are two ways of using this serializer:
 * <ol>
 * <li><b>One Pass Serialization (OPS):</b> serialize all elements.<br/>
 * <ul><li>In this case {@link #serializeAll()} should be called to get the serialization string.</li></ul>
 * </li>
 * <li><b>Step by Step Serialization (SSS):</b> serialize only one element at a time.<br/>
 * In this case you must follow this:
 * <ul>
 * <li>call {@link #start()} to begin the serialization process;</li>
 * <li>call {@link #serializeNext()} to get the next serialization output;</li>
 * <li>call {@link #hasNext()} to check if there are other elements to serialize;</li>
 * <li>call {@link #stop()} to finish the serialization process (there are no other elements to serialize).</li>
 * </ul>
 * </li>
 * </ol>
 * 
 * In both cases the serializer must be first initialized with {@link #init(Collection)} or {@link #init(OASerializable)}.
 * <br/>
 * <br/>
 *  
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OpenAnnotationSerializer {
	
	private List<OASerializable> datas;
	
	private int nextIdx = -1;
	
	private boolean internalTab;
	
	private boolean indent;
	
	/**
	 * Default constructor.<br/>
	 * This presets the serialization with no text indentation.
	 */
	public OpenAnnotationSerializer() {
		this(false);
	}
	
	/**
	 * This constructor initializes the serializer with the text indentation value provided.
	 * 
	 * @param indent true if text indentation is needed
	 */
	public OpenAnnotationSerializer(boolean indent) {
		this.indent = indent;
	}
	
	/**
	 * It returns the text indentation value.
	 * 
	 * @return the text indentation value
	 */
	public boolean isIndent() {
		return indent;
	}

	/**
	 * It sets the text indentation value.
	 * 
	 * @param indent the text indentation value to set
	 */
	public void setIndent(boolean indent) {
		this.indent = indent;
	}

	/**
	 * This method initializes this serializer.
	 * 
	 * @param data the {@link OASerializable} data to serialize
	 * @return the current serializer
	 * @throws OAException if data provided are null
	 */
	public OpenAnnotationSerializer init(OASerializable data) throws OAException {
		if (data == null) {
			throw new OAException(OAException.NULLDATA);
		}
		datas = new ArrayList<OASerializable>();
		datas.add(data);
		this.nextIdx = 0;
		return this;
	}
	
	/**
	 * This method initializes this serializer.
	 * 
	 * @param data the collection of {@link OASerializable} data to serialize
	 * @return the current serializer
	 * @throws OAException if data provided are null or empty
	 */
	public OpenAnnotationSerializer init(Collection<OASerializable> data) throws OAException {
		if (data == null || data.size() == 0) {
			throw new OAException(OAException.NULLDATA);
		}
		datas = new ArrayList<OASerializable>();
		datas.addAll(data);
		return this;
	}
	
	/**
	 * This method serializes all the data provided during the initialization.
	 * 
	 * @return the serialized string
	 * @throws OAException if serializer is not initialized or if there is a problem during the serialization process
	 */
	public String serializeAll() throws OAException {
		if (nextIdx == -1 && (datas == null || datas.size() == 0)) {
			throw new OAException(OAException.SERIALIZERNOTINITIALIZED);
		}
		internalTab = false;
		return serializeObject(null, datas, 0, true, false);
	}
	
	/**
	 * Defines the begin of the serialization process and returns the start serialization symbol (if exists).
	 * 
	 * @return the start serialization symbol (if exists, empty string otherwise)
	 * @throws OAException if this serializer is not initialized
	 */
	public String start() throws OAException {
		if (nextIdx == -1 && (datas == null || datas.size() == 0)) {
			throw new OAException(OAException.SERIALIZERNOTINITIALIZED);
		}
		nextIdx = 0;
		return (datas.size() > 0) ? "[" + getCR(true) :  "";
	}
	
	/**
	 * Defines the end of the serialization process and returns the stop serialization symbol (if exists).
	 * 
	 * @return the stop serialization symbol (if exists, empty string otherwise)
	 * @throws OAException if this serializer is not initialized or not started
	 */
	public String stop() throws OAException {
		if (nextIdx == -1 && (datas == null || datas.size() == 0)) {
			throw new OAException(OAException.SERIALIZERNOTINITIALIZED);
		}
		if (nextIdx == -1) {
			throw new OAException(OAException.SERIALIZERNOTSTARTED);
		}
		nextIdx = -1;
		return (datas.size() > 0) ? "]" : "";
	}
	
	/**
	 * Returns true if there are elements to serialize.
	 * 
	 * @return true if there are elements to serialize, false otherwise
	 * @throws OAException if this serializer is not initialized or not yet started 
	 */
	public boolean hasNext() throws OAException {
		if (nextIdx == -1 && (datas == null || datas.size() == 0)) {
			throw new OAException(OAException.SERIALIZERNOTINITIALIZED);
		}
		if (nextIdx == -1) {
			throw new OAException(OAException.SERIALIZERNOTSTARTED);
		}
		return nextIdx < datas.size();
	}
	
	/**
	 * Returns the serialization of next element.
	 * 
	 * @return element serialization string
	 * @throws OAException if this serializer is not initialized or not yet started or if it has no next element
	 */
	public String serializeNext() throws OAException {
		if (nextIdx == -1 && (datas == null || datas.size() == 0)) {
			throw new OAException(OAException.SERIALIZERNOTINITIALIZED);
		}
		if (nextIdx == -1) {
			throw new OAException(OAException.SERIALIZERNOTSTARTED);
		}
		if (nextIdx > datas.size()) {
			throw new OAException(OAException.SERIALIZEROUTOFBOUND);
		}
		internalTab = true;
		return serializeObject(null, datas.get(nextIdx++), 0, nextIdx == datas.size(), true);
	}
	
	
	// Privates
	
	/**
	 * This method serializes the elements of a collection.
	 * 
	 * @param elements the collection of elements to serialize
	 * @param tabIndentIdx the number of tab indentation needed (only for text indented json)
	 * @return the serialization of the collection
	 * @throws OAException
	 */
	private String serializeCollection(Collection<?> elements, int tabIndentIdx) throws OAException {
		String output = "";
		int currentIdx = 0;
		for (Object element : elements) {
			currentIdx++;
			output += serializeObject(null, element, tabIndentIdx, currentIdx == elements.size(), true);
		}
		return output;
	}
	
	/**
	 * This method serializes the element provided and adds additional informations (the key of
	 * the json serialization and the comma at the end if the element is the last of a list).
	 * 
	 * @param key the json key to show (if null it does not show the key)
	 * @param element the element to serialize
	 * @param tabIndentIdx the number of tab indentation needed (only for text indented json)
	 * @param last true if the element is the last of a list
	 * @param appendCR true if carriage return is needed at the end of the serialization
	 * @return the serialization string
	 * @throws OAException
	 */
	private String serializeObject(String key, Object element, int tabIndentIdx, boolean last, boolean appendCR) throws OAException {
		String tabIndent = getTabIndent(tabIndentIdx);
		String pre = tabIndent + getKeyString(key);
		String post = (last ? "" : ",") + getCR(appendCR);
		if (element instanceof String) {
			return pre + serializeString((String) element) + post;
		}
		if (element instanceof Date) {
			return pre + serializeDate((Date) element) + post;
		}
		if (element instanceof Number) {
			return pre + serializeNumber((Number) element) + post;
		}
		if (element instanceof OpenAnnotationElement) {
			return pre + "{" + getCR(true) + serializeOAElement((OpenAnnotationElement) element, tabIndentIdx) + tabIndent + "}" + post;
		}
		if (element instanceof Collection) {
			if (((Collection<?>) element).size() == 0) {
				return "";
			}
			if (((Collection<?>) element).size() == 1) {
				return serializeObject(key, ((Collection<?>) element).iterator().next(), tabIndentIdx, last, appendCR);
			}
			return pre + "[" + getCR(true) + serializeCollection((Collection<?>) element, ++tabIndentIdx) + tabIndent + "]" + post;
		}
		if (element instanceof OpenAnnotationObject) {
			return serializeObject(key, ((OpenAnnotationObject<?>) element).getAnnotationObject(), tabIndentIdx, last, appendCR);
		}
		return "";
	}
	
	/**
	 * This method returns the serialization of the element provided.
	 * 
	 * @param string the string to serialize
	 * @return the serialization string
	 */
	private String serializeString(String string) {
		return "\"" +  string + "\"";
	}
	
	/**
	 * This method returns the serialization of the element provided.
	 * 
	 * @param date the date to serialize
	 * @return the serialization string
	 */
	private String serializeDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		return "\"" + formatter.format(date) + "\"";
	}
	
	/**
	 * This method returns the serialization of the element provided.
	 * 
	 * @param number the number to serialize
	 * @return the serialization string
	 */
	private String serializeNumber(Number number) {
		return "\"" + number.toString() + "\"";
	}

	/**
	 * This method returns the serialization of the element provided.
	 * 
	 * @param element the element to serialize
	 * @param tabIndentIdx the number of tab indentation needed (only for text indented json)
	 * @return the serialization string
	 * @throws OAException
	 */
	private String serializeOAElement(OpenAnnotationElement element, int tabIndentIdx) throws OAException {
		String serialization = "";
		try {
			List<Field> annotatedFields = selectAnnotatedFields(element, element.getClass());
			int fieldIdx = 0;
			tabIndentIdx++;
			for (Field field : annotatedFields) {
				fieldIdx++;
				OASerializableField annotation = field.getAnnotation(OASerializableField.class);
				String annotationValue = annotation.value();
				if ("DEFAULT_VALUE".equals(annotationValue)) {
					annotationValue = field.getName();
				}
				field.setAccessible(true);
				serialization += serializeObject(annotationValue, field.get(element), tabIndentIdx, fieldIdx == annotatedFields.size(), true);
				field.setAccessible(false);
			}
		} catch (Exception e) {
			throw new OAException(OAException.SERIALIZATIONERROR, e);
		}
		return serialization;
	}
	
	/**
	 * This method provides the list of {@link Field} of the element provided that are annotated with the 
	 * {@link OASerializableField} annotation.<br/>
	 * Note that only not null fields are considered.
	 * 
	 * @param element the element to analyze
	 * @param clazz one of the superclass of the provided element
	 * @return a list of {@link Field} that are annotated with {@link OASerializable}.
	 * @throws Exception
	 */
	private <T extends OpenAnnotationElement> List<Field> selectAnnotatedFields(T element, Class<?> clazz) throws Exception {
		List<Field> fields = new ArrayList<Field>();
		if (clazz.getSuperclass() != null) {
			fields.addAll(selectAnnotatedFields(element, clazz.getSuperclass()));
		}
		fields.addAll(_selectFields(element, clazz.getDeclaredFields()));
		return fields;
	}
	
	/**
	 * This method selects the fields that are annotated with {@link OASerializable} and are not null.
	 * 
	 * @param element the element to analyze
	 * @param fields the array of fields to filter
	 * @return the filtered list of fields
	 * @throws Exception
	 */
	private <T extends OpenAnnotationElement> List<Field> _selectFields(T element, Field[] fields) throws Exception {
		List<Field> annotatedFields = new ArrayList<Field>();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(OASerializableField.class) && field.get(element) != null) {
				annotatedFields.add(field);
			}
			field.setAccessible(false);
		}
		return annotatedFields;
	}
	
	/**
	 * This method returns the json representation of the key.
	 * 
	 * @param key the key to show
	 * @return the json representation of the key
	 */
	private String getKeyString(String key) {
		String keyString = "";
		if (key != null) {
			keyString = "\"" + key + "\" : ";
		}
		return keyString;
	}
	
	/**
	 * This method returns the tab indentation (only for text indented json).
	 * 
	 * @param idx the number of tab needed
	 * @return tab indent indentation string
	 */
	private String getTabIndent(int idx) {
		String tabIndent = "";
		if (indent) {
			for (int i = 0; i < idx; i++) {
				tabIndent += "\t";
			}
			tabIndent += internalTab ? "\t" : "";
		}
		return tabIndent;
	}
	
	/**
	 * This method returns the carriage return string.
	 *  
	 * @param appendCR true if carriage return has to be showed
	 * @return the carriage return string
	 */
	private String getCR(boolean appendCR) {
		if (!appendCR) {
			return "";
		}
		return indent ? System.getProperty("line.separator") : " ";
	}
}
