/*
 * ForumUtils.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;
import org.medici.bia.domain.ForumTopic;
import org.medici.bia.domain.Forum.Type;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class ForumUtils {

	/**
	 * 
	 * @param forumResult
	 * @param categoriesIds
	 * @return
	 */
	public static HashMap<Integer, List<Forum>> convertToHashMapByCategory(List<Forum> forumResult, List<Integer> categoriesIds) {
		HashMap<Integer, List<Forum>> hashMap = new HashMap<Integer, List<Forum>>(0);

		if (categoriesIds == null) {
			return hashMap;
		}

		for (Integer currentCategory : 	categoriesIds)  {
			List<Forum> forums = new ArrayList<Forum>(0);
			
			for (Forum currentForum : forumResult) {
				if (currentForum.getForumParent().getForumId().equals(currentCategory)) {
					forums.add(currentForum);
				}
			}
			
			hashMap.put(currentCategory, forums);
		}

		return hashMap;
	}

	/**
	 * 
	 * @param forum
	 * @return
	 */
	public static String getForumChronology(Forum forum) {
		if (forum.getForumParent() == null) {
			return HtmlUtils.getShowForumIndexUrl(forum);
		}

		if (forum.getType().equals(Type.CATEGORY)) {
			return getForumChronology(forum.getForumParent());
		}
		
		return getForumChronology(forum.getForumParent()) + "<span class=\"arrowForum\" style=\"display:none;\" >&rarr; " + HtmlUtils.getShowForumHrefUrl(forum) + "</span>";
	}
	
	/**
	 * 
	 * @param forum
	 * @return
	 */
	public static String getSelectForumChronology(Forum forum) {
		if(forum == null)
			return "";
		if (forum.getForumParent() == null) {
			return "<option value='" + HtmlUtils.getShowForumUrl(forum) + "'>" + forum.getTitle() + "</option>"; 
		}
		
		if (forum.getType().equals(Type.CATEGORY)) {
			return getSelectForumChronology(forum.getForumParent());
		}
		
		return getSelectForumChronology(forum.getForumParent()) + "<option value='" + HtmlUtils.getShowForumUrl(forum) + "'>" + forum.getTitle() + "</option>";
	}

	/**
	 * 
	 * @param forumTopic
	 * @return
	 */
	public static String getForumChronology(ForumTopic forumTopic) {
		return getForumChronology(forumTopic.getForum()) + "<span class=\"arrowForum\">&rarr; " + HtmlUtils.getShowTopicForumHrefUrl(forumTopic) + "</span>";
	}
	
	public static String searchTextResultPost(ForumPost forumPost, String searchText){
		if(forumPost.getText().length() < 40){
			return forumPost.getText();
		}else{
			String [] wordArray = RegExUtils.splitPunctuationAndSpaceChars(searchText);
			StringBuffer returnText = new StringBuffer();
			for(String currentWord : wordArray){
				Integer indexToBeginResult = forumPost.getText().indexOf(currentWord);
				Integer indexToEndResult = forumPost.getText().length();
				if(indexToBeginResult > 10){
					String temp = forumPost.getText().substring(0, indexToBeginResult - 10);
					indexToBeginResult = temp.lastIndexOf(" ");
				}else{
					indexToBeginResult = 0;
				}
				if(indexToBeginResult + 40 < forumPost.getText().length()){
					String temp = forumPost.getText().substring(indexToBeginResult, indexToBeginResult + 40);
					indexToEndResult = temp.lastIndexOf(" ");
					if(indexToEndResult == -1){
						indexToEndResult = indexToBeginResult + 40;
					}
				}
				if(indexToBeginResult > 0 && indexToEndResult < forumPost.getText().length())
					returnText.append("..." + forumPost.getText().substring(indexToBeginResult, indexToEndResult) + "...\n");
				else if(indexToBeginResult > 0 && indexToEndResult >= forumPost.getText().length())
					returnText.append("..." + forumPost.getText().substring(indexToBeginResult, forumPost.getText().length()) + "\n");
				else if(indexToBeginResult <= 0 && indexToEndResult < forumPost.getText().length())
					returnText.append(forumPost.getText().substring(indexToBeginResult, indexToEndResult) + "...\n");
				else if(indexToBeginResult <= 0 && indexToEndResult >= forumPost.getText().length())
					returnText.append(forumPost.getText().substring(indexToBeginResult, forumPost.getText().length()));					
			}
			return returnText.toString();
		}
	}
}
