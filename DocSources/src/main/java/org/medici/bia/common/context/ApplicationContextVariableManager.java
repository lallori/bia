/*
 * ApplicationContextVariable.java
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
package org.medici.bia.common.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class maintains application scope variables.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ApplicationContextVariableManager {
	
	public static final String EUROPEANA_JOB = "europeanaJob";
	
	private final Map<String, Object> variablesMap = new HashMap<String, Object>();
	
	/**
	 * Stores the variable associated to the provided key.
	 * 
	 * @param key the key
	 * @param value the variable
	 */
	public void add(String key, Object value) {
		_add(key, value);
	}
	
	/**
	 * Removes all the stored variables.
	 */
	public void clear() {
		_clear();
	}
	
	/**
	 * Returns the variable corresponding to the provided key.
	 * 
	 * @param key the key of the variable
	 * @return the variable
	 */
	public Object get(String key) {
		return variablesMap.get(key);
	}
	
	/**
	 * Removes the variable corresponding to the provided key.
	 * 
	 * @param key the key of the variable
	 * @return the variable
	 */
	public Object remove(String key) {
		return _remove(key);
	}
	
	/**
	 * Returns the variables map.
	 * 
	 * @return the variables map
	 */
	public Map<String, Object> getVariables() {
		return Collections.unmodifiableMap(variablesMap);
	}
	
	/* Privates */
	
	private synchronized void _add(String key, Object value) {
		variablesMap.put(key, value);
	}
	
	private synchronized void _clear() {
		variablesMap.clear();
	}
	
	private synchronized Object _remove(String key) {
		return variablesMap.remove(key);
	}
	
}
