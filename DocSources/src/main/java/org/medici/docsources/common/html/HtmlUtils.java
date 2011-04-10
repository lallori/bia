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

import org.medici.docsources.common.pagination.DocumentExplorer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class is an utility to obtains specific application Url.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class HtmlUtils {

	/**
	 * 
	 * @param documentExplorer
	 * @return
	 */
	public static String getDocumentExplorerNextPageUrl(DocumentExplorer documentExplorer) {
		if (documentExplorer == null)
			return "";
		
		if (documentExplorer.getImage().getImageOrder() == documentExplorer.getTotal().intValue())
			return "";

		StringBuffer stringBuffer = new StringBuffer(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI());
		stringBuffer.append("?entryId=");
		stringBuffer.append(documentExplorer.getEntryId());
		stringBuffer.append("&volNum=");
		stringBuffer.append(documentExplorer.getVolNum());
		stringBuffer.append("&volLetExt=");
		stringBuffer.append(documentExplorer.getVolLetExt());
		stringBuffer.append("&imageOrder=");
		stringBuffer.append(documentExplorer.getImage().getImageOrder()+1);
		stringBuffer.append("&total=");
		stringBuffer.append(documentExplorer.getTotal());
		stringBuffer.append("&totalRubricario=");
		stringBuffer.append(documentExplorer.getTotalRubricario());
		stringBuffer.append("&totalCarta=");
		stringBuffer.append(documentExplorer.getTotalCarta());
		stringBuffer.append("&totalAppendix=");
		stringBuffer.append(documentExplorer.getTotalAppendix());
		stringBuffer.append("&totalOther=");
		stringBuffer.append(documentExplorer.getTotalOther());
		stringBuffer.append("&totalGuardia=");
		stringBuffer.append(documentExplorer.getTotalGuardia());
		
		return stringBuffer.toString();
	}

	/**
	 * 
	 * @param documentExplorer
	 * @return
	 */
	public static String getDocumentExplorerPreviousPageUrl(DocumentExplorer documentExplorer) {
		if (documentExplorer == null)
			return "";
		
		if (documentExplorer.getImage().getImageOrder() == 1)
			return "";

		StringBuffer stringBuffer = new StringBuffer(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI());
		stringBuffer.append("?entryId=");
		stringBuffer.append(documentExplorer.getEntryId());
		stringBuffer.append("&volNum=");
		stringBuffer.append(documentExplorer.getVolNum());
		stringBuffer.append("&volLetExt=");
		stringBuffer.append(documentExplorer.getVolLetExt());
		stringBuffer.append("&imageOrder=");
		stringBuffer.append(documentExplorer.getImage().getImageOrder()-1);
		stringBuffer.append("&total=");
		stringBuffer.append(documentExplorer.getTotal());
		stringBuffer.append("&totalRubricario=");
		stringBuffer.append(documentExplorer.getTotalRubricario());
		stringBuffer.append("&totalCarta=");
		stringBuffer.append(documentExplorer.getTotalCarta());
		stringBuffer.append("&totalAppendix=");
		stringBuffer.append(documentExplorer.getTotalAppendix());
		stringBuffer.append("&totalOther=");
		stringBuffer.append(documentExplorer.getTotalOther());
		stringBuffer.append("&totalGuardia=");
		stringBuffer.append(documentExplorer.getTotalGuardia());
		
		return stringBuffer.toString();
	}

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
		
		String anchorBegin = "<a class=\"searchResult\" href=\"";
		anchorBegin += ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath();
		anchorBegin += "/src/docbase/ShowDocument.do?entryId=";
		anchorBegin += entryId;
		anchorBegin += "\">";
		String hrefEnd = "</a>";
		
		for (int i=0; i<inputList.size(); i++) {
			retValue.add(anchorBegin + inputList.get(i) + hrefEnd);
		}
		
		return retValue;
	}

	/**
	 * 
	 * @param inputList
	 * @param entryId
	 * @return
	 */
	public static List<String> showPeople(List<String> inputList, Integer personId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuffer anchorBegin = new StringBuffer("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/peoplebase/ShowPerson.do?personId=");
		anchorBegin.append(personId);
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
	 * @param singleRow
	 * @param placeAllId
	 * @return
	 */
	public static List<String> showPlace(List<String> inputList, Integer placeAllId) {
		if (inputList == null)
			return null;

		ArrayList<String> retValue = new ArrayList<String>(inputList.size());
		
		StringBuffer anchorBegin = new StringBuffer("<a class=\"searchResult\" href=\"");
		anchorBegin.append(((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath());
		anchorBegin.append("/src/geobase/ShowPlace.do?placeAllId=");
		anchorBegin.append(placeAllId);
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
