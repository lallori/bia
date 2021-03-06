/*
 * ApplicationPropertyManager.java
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
package org.medici.bia.common.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.common.context.ApplicationContextProvider;
import org.medici.bia.dao.applicationproperty.ApplicationPropertyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * 
 * @author Lorenzo Pasquinelli (<a
 *         href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@ContextConfiguration(locations="applicationProvider.xml")
public class ApplicationPropertyManager {
	@Autowired
	private ApplicationPropertyDAO applicationPropertyDAO;
	private final Map<String, String> simpleProperties = new HashMap<String, String>();
	protected static final ApplicationPropertyManager INSTANCE;

	static {
		INSTANCE = new ApplicationPropertyManager();
		INSTANCE.init(true);
	}
	/**
	 * 
	 * @return
	 */
	public static List<String> getApplicationPropertiesNames() {
		List<String> list = new ArrayList<String>();
		list.addAll(INSTANCE.simpleProperties.keySet());
		Collections.sort(list);

		return list;
	}

	/**
	 * This method return an application property.
	 * 
	 * @param propertyName
	 * @return
	 */
	public static String getApplicationProperty(String propertyName) {
		if (!INSTANCE.simpleProperties.containsKey(propertyName)) {
			String property = INSTANCE.getApplicationPropertyDAO().getApplicationProperty(propertyName);
			INSTANCE.simpleProperties.put(propertyName, property);
		}

		return INSTANCE.simpleProperties.get(propertyName);
	}

	/**
	 * 
	 * @param string
	 * @param strings
	 * @return
	 */
	public static String getApplicationProperty(String propertyName, String[] stringsToBeReplaced, String prefixPlaceHolder, String suffixPlaceHolder) {
		if (!INSTANCE.simpleProperties.containsKey(propertyName)) {
			String property = INSTANCE.getApplicationPropertyDAO().getApplicationProperty(propertyName);
			INSTANCE.simpleProperties.put(propertyName, property);
		}

		String property = INSTANCE.simpleProperties.get(propertyName);
		
		for (int i=0; i<stringsToBeReplaced.length; i++) {
			property = StringUtils.replace(property, prefixPlaceHolder + i + suffixPlaceHolder, stringsToBeReplaced[i]);
		}
		
		return property;
	}

	/**
     * 
     */
	public static final void init() {
		INSTANCE.init(false);
	}

	/**
     * 
     */
	public static void refreshProperties() {
		List<String> list = new ArrayList<String>();
		list.addAll(INSTANCE.simpleProperties.keySet());

		for (int i = 0; i < list.size(); i++) {
			INSTANCE.simpleProperties.remove(list.get(i));
			getApplicationProperty(list.get(i));
		}
	}

	/**
	 * ApplicationPropertyManager constructor.
	 */
	private ApplicationPropertyManager() {
		super();
	}

	/**
	 * @return the applicationPropertyDAO
	 */
	public ApplicationPropertyDAO getApplicationPropertyDAO() {
		return applicationPropertyDAO;
	}

	/**
     * 
     */
	private void init(boolean first) {
		if (!first) {
			// We clean previous properties...
			List<String> list = new ArrayList<String>();
			list.addAll(simpleProperties.keySet());

			for (int i = 0; i < list.size(); i++) {
				simpleProperties.remove(list.get(i));
			}
		} else {
			setApplicationPropertyDAO((ApplicationPropertyDAO)ApplicationContextProvider.getContext().getBean("applicationPropertyDAOJpaImpl"));
		}

		List<String> propertiesNames = getApplicationPropertyDAO().getApplicationPropertiesNames();

		for (int i = 0; i < propertiesNames.size(); i++) {
			getApplicationProperty(propertiesNames.get(i));
		}
	}

	/**
	 * @param applicationPropertyDAO
	 *            the applicationPropertyDAO to set
	 */
	public void setApplicationPropertyDAO(ApplicationPropertyDAO applicationPropertyDAO) {
		this.applicationPropertyDAO = applicationPropertyDAO;
	}
}
