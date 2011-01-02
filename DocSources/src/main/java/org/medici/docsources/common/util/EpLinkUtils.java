/*
 * EpLinkUtils.java
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

import java.util.ArrayList;
import java.util.List;

import org.medici.docsources.domain.EpLink;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class EpLinkUtils {

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
	public static List<Integer> getPeopleIdList(List<EpLink> inputList) {
		if (inputList.size() == 0)
			return new ArrayList<Integer>(0);

		List<Integer> retValue = new ArrayList(inputList.size());

		for (int i = 0; i < inputList.size(); i++) {
			if (inputList.get(i).getPeople() != null) {
				if (! retValue.contains(inputList.get(i).getPeople().getPersonId())) {
					retValue.add(inputList.get(i).getPeople().getPersonId());
				}
			}
		}

		return retValue;
	}
}
