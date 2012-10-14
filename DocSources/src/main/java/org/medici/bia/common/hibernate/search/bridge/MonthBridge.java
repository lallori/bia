/*
 * MonthBridge.java
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
package org.medici.bia.common.hibernate.search.bridge;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.annotations.common.util.StringHelper;
import org.hibernate.search.bridge.TwoWayStringBridge;
import org.medici.bia.domain.Month;

/**
 * Map a Month field.
 *
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 */
public class MonthBridge implements TwoWayStringBridge {
	private Logger logger = Logger.getLogger(this.getClass());

	public Month stringToObject(String stringValue) {
		if ( StringHelper.isEmpty(stringValue) ) return null;
		
		try {
			return new Month(Integer.valueOf(stringValue));
		} catch(NumberFormatException numberFormatException) {
			logger.debug(numberFormatException);
			return null;
		}
	}

	/**
	 * 
	 */
	public String objectToString(Object object) {
		if (object == null){
			return null;
		}
		
		if (!object.getClass().equals(Month.class)) { 
			return null;
		}
		
		Month month = (Month) object;
		
		if (ObjectUtils.toString(month.getMonthNum()).equals("")) {
			return "";
		}
		
		return month.getMonthNum().toString();
	}
	
}