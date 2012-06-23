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
package org.medici.docsources.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.medici.docsources.domain.Forum;
import org.medici.docsources.domain.Forum.Type;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
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
				if (currentForum.getForumParent().getId().equals(currentCategory)) {
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
		
		return getForumChronology(forum.getForumParent()) + "<span class=\"arrowForum\">&rarr; " + HtmlUtils.getShowForumHrefUrl(forum) + "</span>";
	}
}
