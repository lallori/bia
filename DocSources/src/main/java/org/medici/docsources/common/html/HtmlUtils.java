/*
 * HtmlUtils.java
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
package org.medici.docsources.common.html;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class HtmlUtils {

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showDocument(List<String> inputList, Integer entryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuffer anchorBegin = new StringBuffer("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/docbase/ShowDocument.do?entryId=");
		anchorBegin.append(entryId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuffer stringBuffer = new StringBuffer(anchorBegin.toString());
			stringBuffer.append(inputList.get(i));
			stringBuffer.append(hrefEnd);
			retValue.add(stringBuffer.toString());
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showPeople(List<String> inputList, Integer entryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuffer anchorBegin = new StringBuffer("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/peoplebase/ShowDocument.do?entryId=");
		anchorBegin.append(entryId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuffer stringBuffer = new StringBuffer(anchorBegin.toString());
			stringBuffer.append(inputList.get(i));
			stringBuffer.append(hrefEnd);
			retValue.add(stringBuffer.toString());
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param summaryId
	 * @return
	 */
	public static List<String> showVolume(List<String> inputList, Integer summaryId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuffer anchorBegin = new StringBuffer("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/volbase/ShowVolume.do?summaryId=");
		anchorBegin.append(summaryId);
		anchorBegin.append("\">");
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			StringBuffer stringBuffer = new StringBuffer(anchorBegin.toString());
			stringBuffer.append(inputList.get(i));
			stringBuffer.append(hrefEnd);
			retValue.add(stringBuffer.toString());
		}
		
		return retValue;
	}
}
