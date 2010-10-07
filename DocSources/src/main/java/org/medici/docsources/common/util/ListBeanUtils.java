/*
 * ListBeanUtils.java
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

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * Utility class to work on list object containing java bean object.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public class ListBeanUtils {
	/**
	 * Method to obtains a list of a specific field contained in input list
	 * inputObject.
	 * 
	 * @param inputList
	 *            List object containing beans having the field to extract.
	 * @param fieldName Field name to extract
	 * @return List<Object> Result list containing the specific input field of input list.
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> transformList(List inputList, String fieldName) {
		if (inputList.size() == 0)
			return new ArrayList<Object>(0);

		ArrayList retValue = new ArrayList(inputList.size());

		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(inputList.get(0).getClass(), fieldName);
		Method method = pd.getReadMethod();

		for (int i = 0; i < inputList.size(); i++) {
			try {
				retValue.add(i, method.invoke(inputList.get(i), (Object[]) null));
			} catch (IllegalAccessException iaex) {
				retValue.set(i, null);
			} catch (InvocationTargetException itex) {
				retValue.set(i, null);
			}
		}

		return retValue;
	}

	/**
	 * Method to obtains a plain list fomr a bean input list.
	 * 
	 * @param beanList List object containing beans having the field to extract.
	 * @param fieldName Field name to extract
	 * @return List<Object> Result list containing the specific input field of input list.
	 **/
	public static List<Object> transformToPlainList(List<Object> beansList) {
		if (beansList.size() == 0)
			return new ArrayList<Object>(0);

		ArrayList<Object> arrayList = new ArrayList<Object>(beansList.size());

		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(beansList.get(0).getClass());
		
		for (int i=0; i<beansList.size(); i++) {
			ArrayList<Object> singleBean = new ArrayList<Object>(propertyDescriptors.length);
			
			for (int j=0; j<propertyDescriptors.length; j++) {
				Method method = propertyDescriptors[i].getReadMethod();
				try {
					singleBean.add(method.invoke(beansList.get(i), (Object[]) null));
				} catch (InvocationTargetException itex) {
					itex.printStackTrace();
					singleBean.add("");
				} catch (IllegalAccessException iaex) {
					iaex.printStackTrace();
					singleBean.add("");
				}
			}
		}

		return arrayList;
	}
}
