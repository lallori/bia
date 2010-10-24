/*
 * ClassUtils.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.docsources.common.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class to work with application class.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ClassUtils {

	/**
	 * This method convert a class object in string rappresentation.
	 * 
	 * @param object Input class object that will be converted in string format.
	 * @return String rappresentation of input Object.
	 */
	@SuppressWarnings("rawtypes")
	public static String toString(Object object) {
		if (object == null)
			return "";

		ArrayList list = new ArrayList();
		ClassUtils.toString(object, object.getClass(), list);
		return object.getClass().getName().concat(list.toString());
	}

	/**
	 * This method populate the input List using every declared field
	 * in object class which is defined as input clazz.
	 * The format of list element is name field equals value.
	 * @param object The istance where we take fields
	 * @param clazz Class type of previous parameter
	 * @param list list to populate with class's fields values.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void toString(Object object, Class clazz, List list) {
		Field f[] = clazz.getDeclaredFields();
		AccessibleObject.setAccessible(f, true);
		for (int i = 0; i < f.length; i++) {
			try {
				list.add(f[i].getName() + "=" + f[i].get(object));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (clazz.getSuperclass().getSuperclass() != null) {
			toString(object, clazz.getSuperclass(), list);
		}
	}

	/**
	 * Default constructor
	 */
	private ClassUtils() {
	}
}
