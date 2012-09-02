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
package org.medici.bia.common.util;

import com.mchange.v1.util.StringTokenizerUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

/**
 * Utility class to work on list object containing java bean object.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
public class ListBeanUtils {
	/**
	 * Method to obtains a list of a specific field contained in input list
	 * inputObject. This method is able to manage simple property and nested
	 * property (first level only ndr).  
	 * 
	 * @param inputList
	 *            List object containing beans having the field to extract.
	 * @param fieldName Field name to extract
	 * @return List<Object> Result list containing the specific input field of input list.
	 **/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<?> transformList(List inputList, String fieldName) {
		if (inputList == null || inputList.size() == 0)
			return new ArrayList<Object>(0);

		ArrayList retValue = new ArrayList(inputList.size());

		if (!StringUtils.contains(fieldName, ".")) {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(inputList.get(0).getClass(), fieldName);
			Method method = pd.getReadMethod();
	
			for (int i = 0; i < inputList.size(); i++) {
				try {
					retValue.add(i, method.invoke(inputList.get(i), (Object[]) null));
					if ((ObjectUtils.toString(retValue.get(i)).equals("0")) || ((ObjectUtils.toString(retValue.get(i)).equals("")))) {
						retValue.set(i, "");
					}
				} catch (IllegalAccessException iaex) {
					retValue.set(i, null);
				} catch (InvocationTargetException itex) {
					retValue.set(i, null);
				}
			}
		}

		return retValue;
	}

	/**
	 * 
	 * @param beansList
	 * @param concatenatedFields
	 * @param fieldsSeparator
	 * @param outputFieldsSeparator
	 * @param addBlankSpace
	 * @return
	 */
	public static ArrayList<String> toStringListWithConcatenationFields(List<?> beansList, String concatenatedFields, String fieldsSeparator, String outputFieldsSeparator, Boolean addBlankSpace) {
		return toStringListWithConcatenationFields(beansList, concatenatedFields, fieldsSeparator, outputFieldsSeparator, addBlankSpace, Boolean.FALSE);
	}

	/**
	 * Method to obtains a plain list of multiple fields contained in a bean input list.
	 * The fields are marked by third parameter which is the separator.
	 * The outputString
	 * 
	 * @param beansList List of bean from which we estract the plain list of multiple fields. 
	 * @param concatenatedFields List of bean's fields we want to concatenate in outputlist
	 * @param fieldsSeparator String Separator of previous parameter
	 * @param outputFieldsSeparator Separator of fields in output list
	 * @param addBlankSpace A boolean parameter to insert a blank space before and after every separatore of output fields
	 * @param excludeZero If a field is numeric and his value is 0, the field is discarded from output. 
	 * @return
	 */
	public static ArrayList<String> toStringListWithConcatenationFields(List<?> beansList, String concatenatedFields, String fieldsSeparator, String outputFieldsSeparator, Boolean addBlankSpace, Boolean excludeZero) {
		if (beansList == null)
			return new ArrayList<String>(0);
		
		if ((beansList.size() == 0) || (StringUtils.isEmpty(fieldsSeparator)) || (StringUtils.isEmpty(outputFieldsSeparator)))
			return new ArrayList<String>(0);

		ArrayList<String> retValue = new ArrayList<String>(beansList.size());
		String[] fields = StringTokenizerUtils.tokenizeToArray(concatenatedFields, fieldsSeparator);
		StringBuilder stringBuilder = null;  

		for (int i = 0; i<beansList.size(); i++) {
			stringBuilder = new StringBuilder();
			for (int j=0; j<fields.length; j++) {
				PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(beansList.get(0).getClass(), fields[j]);
				Method method = pd.getReadMethod();
				try {
					Object fieldBeanValue =method.invoke(beansList.get(i), (Object[]) null);
					if (fieldBeanValue != null) {
						if (fieldBeanValue.getClass().equals(Integer.class)) {
							if (excludeZero.equals(Boolean.TRUE)) {
								if (fieldBeanValue.equals(0)) {
									continue;
								}
							}
						}
						if (j>0) {
							if (addBlankSpace) {
								stringBuilder.append(" ");
								stringBuilder.append(outputFieldsSeparator);
								stringBuilder.append(" ");
							} else {
								stringBuilder.append(outputFieldsSeparator);
							}
						}
						stringBuilder.append(fieldBeanValue.toString());
					}
				} catch (IllegalAccessException iaex) {
					retValue.set(i, null);
				} catch (InvocationTargetException itex) {
					retValue.set(i, null);
				}
			}
			retValue.add(i, stringBuilder.toString());
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
		if ((beansList == null) || (beansList.size() == 0))
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
