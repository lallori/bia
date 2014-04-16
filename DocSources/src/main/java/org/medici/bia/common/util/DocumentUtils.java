/*
 * DocumentUtils.java
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
package org.medici.bia.common.util;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.medici.bia.domain.Document;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class DocumentUtils {

	/**
	 * This method return a string in format volNum + volLetExt + folioNum + folioMod.
	 * 
	 * @param volNum Volume Number
	 * @param volLetExt Volume Letter Extension
	 * @param folioNum Folio Number
	 * @param folioMod Folio Mod
	 * @return
	 */
	public static String toMDPAndFolioFormat(Integer volNum, String volLetExt, Integer folioNum, String folioMod) {
		String returnValue = "";
		
		if (volNum != null) {
			returnValue += volNum;
		}
		if (StringUtils.isNotEmpty(volLetExt)) {
			returnValue += volLetExt;
		}
		if(folioNum != null){
			returnValue += " / " + folioNum.toString();
			if(folioMod != null){
				returnValue += " " + folioMod;
			}
		}
		else{
			returnValue += " / NNF";
		}
		
		return returnValue;
	}
	
	/**
	 * This method return a string relative to the document provided with the following style: <br/>
	 * <code> volNum + volLetExt / insertNum + insertLet / folioNum + folioMod + folioRectoVerso </code>
	 * 
	 * @param document the document
	 * @return formatted string
	 */
	public static String toMDPInsertFolioFormat(Document document) {
		return toMDPInsertFolioFormat(
				document.getVolume().getVolNum(), 
				document.getVolume().getVolLetExt(), 
				document.getInsertNum(), 
				document.getInsertLet(), 
				document.getFolioNum(), 
				document.getFolioMod(), 
				document.getFolioRectoVerso() != null ? document.getFolioRectoVerso().toString() : null);
	}
	
	/**
	 * This method return a string relative to the document provided with the following style: <br/>
	 * <code> volNum + volLetExt / insertNum + insertLet / folioNum + folioMod + folioRectoVerso </code>
	 * 
	 * @param volNum the volume number
	 * @param volLetExt the volume extension letter
	 * @param insertNum the insert number
	 * @param insertLet the insert extension
	 * @param folioNum the folio number
	 * @param folioMod the folio extension
	 * @param folioRectoVerso the folio recto/verso information
	 * @return formatted string
	 */
	public static String toMDPInsertFolioFormat(
			Integer volNum, 
			String volLetExt, 
			String insertNum, 
			String insertLet, 
			Integer folioNum, 
			String folioMod, 
			String folioRectoVerso) {
		
		StringBuilder s = new StringBuilder();
		s.append(volNum != null ? volNum : "");
		s.append(ObjectUtils.toString(volLetExt));
		if (!"".equals(ObjectUtils.toString(insertNum))) {
			s.append(" / ").append(insertNum);
			if (!"".equals(ObjectUtils.toString(insertLet)))
				s.append(" ").append(insertLet);
		} else
			s.append(" / NNF");
		if (folioNum != null) {
			s.append(" / ").append(folioNum);
			if (!"".equals(ObjectUtils.toString(folioMod)))
				s.append(" ").append(folioMod);
			if (folioRectoVerso != null)
				s.append(" ").append(folioRectoVerso);
		} else
			s.append(" / NNF");
		
		return s.toString();
	}
	
	/**
	 * 
	 * @param textDocument
	 * @param searchText
	 * @return
	 */
	public static String searchTextResultExpand(String textDocument, String searchText){
		//The length of the text to return is about 300 characters
		if(textDocument.length() < 200 || searchText == null || searchText.length() == 0){
			//MD: In this case we return all the text
			return textDocument;
		}else{
			//MD: This is to remove html tags
			String postText = textDocument;
			
			String [] wordArray = StringUtils.split(searchText, " ");
			StringBuffer returnText = new StringBuffer(0);
			//For every word we find where is positioned inside the post
			for(String currentWord : wordArray){
				Integer indexToBeginResult = postText.toLowerCase().indexOf(currentWord);
				Integer indexToEndResult = postText.length();
				//If the word isn't at the begin of the post
				if(indexToBeginResult > 100){
					String temp = postText.substring(0, indexToBeginResult - 100);
					//we find a blank space to "cut" the text of the post
					indexToBeginResult = temp.lastIndexOf(' ');
					if(indexToBeginResult == -1){
						indexToBeginResult = 0;
					}
				}else{
					indexToBeginResult = 0;
				}
				//if the word isn't at the end of the post 
				if(indexToBeginResult + 200 < postText.length()){
					String temp = postText.substring(indexToBeginResult, indexToBeginResult + 200);
					indexToEndResult = temp.lastIndexOf(' ');
					if(indexToEndResult == -1){
						indexToEndResult = indexToBeginResult + 200;
					}else{
						indexToEndResult += indexToBeginResult;
					}
				}
				if(indexToBeginResult > 0 && indexToEndResult < postText.length()) {
					returnText.append("[...]" + postText.substring(indexToBeginResult, indexToEndResult) + " [...]");
				} else if(indexToBeginResult > 0 && indexToEndResult >= postText.length()) {
					returnText.append("[...]" + postText.substring(indexToBeginResult, postText.length()) + "");
				} else if(indexToBeginResult <= 0 && indexToEndResult < postText.length()) {
					returnText.append(postText.substring(indexToBeginResult, indexToEndResult) + " [...]");
				} else if(indexToBeginResult <= 0 && indexToEndResult >= postText.length()) {
					returnText.append(postText.substring(indexToBeginResult, postText.length()));					
				}
			}
			return returnText.toString();
		}
	}
	
}