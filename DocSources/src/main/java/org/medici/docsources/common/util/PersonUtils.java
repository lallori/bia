/*
 * PersonUtils.java
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
package org.medici.docsources.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.medici.docsources.domain.People;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class PersonUtils {
	
	/**
	 * 
	 * @param people
	 * @return
	 */
	public static String generateMapNameLf(People people) {
		if (people == null) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		if (!ObjectUtils.toString(people.getLast()).equals("")) {
			stringBuilder.append(people.getLast());
			if(!ObjectUtils.toString(people.getFirst()).equals("") || !ObjectUtils.toString(people.getSucNum()).equals("") || !ObjectUtils.toString(people.getMidPrefix()).equals("") || !ObjectUtils.toString(people.getMiddle()).equals("") || !ObjectUtils.toString(people.getLastPrefix()).equals(""))
				stringBuilder.append(",");
		}
		if (!ObjectUtils.toString(people.getFirst()).equals("")) {
			stringBuilder.append(" ");
			stringBuilder.append(people.getFirst()); 
		}
		if (!ObjectUtils.toString(people.getSucNum()).equals("")) {
			stringBuilder.append(" ");
			stringBuilder.append(people.getSucNum()); 
		}
		if (!ObjectUtils.toString(people.getMidPrefix()).equals("")) {
			stringBuilder.append(" ");
			stringBuilder.append(people.getMidPrefix()); 
		}
		if (!ObjectUtils.toString(people.getMiddle()).equals("")) {
			stringBuilder.append(" ");
			stringBuilder.append(people.getMiddle()); 
		}
		if (!ObjectUtils.toString(people.getLastPrefix()).equals("")) {
			stringBuilder.append(" ");
			stringBuilder.append(people.getLastPrefix()); 
		}
		if (!ObjectUtils.toString(people.getPostLast()).equals("")) {
			stringBuilder.append(" (");
			if (!ObjectUtils.toString(people.getPostLastPrefix()).equals("")) {
				stringBuilder.append(people.getPostLastPrefix() + " ");
			}
			stringBuilder.append(people.getPostLast());
			stringBuilder.append(")"); 
		}
		
		return stringBuilder.toString();
	}
}
