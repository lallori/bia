/*
 * CourseUtils.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.medici.bia.common.util.dom.DOMHelper;
import org.medici.bia.domain.ForumPost;
import org.w3c.dom.Node;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class CourseUtils {
	
	public static String generateFolioLocationComment(Integer postId, String volume, String insert, String folio) {
		String comment = "";
		comment += "<div class='folioDetailsContainer folioDetailsComment_" + postId + "'>Volume <span class='volumeFragment'>" + volume + "</span>";
		if (insert != null && insert.length() > 0) {
			comment += " - Insert <span class='insertFragment'>" + insert + "</span>";
		}
		comment += " - Folio <span class='folioFragment'>" + folio + "</span></div>";
		return comment + "";
	}
	
	public static Map<Fragment, String> getPostFolioLocation(ForumPost courseTopicPost) {
		Map<Fragment, String> returnMap = new HashMap<Fragment, String>();
		String comment = getPostFolioLocationComment(courseTopicPost);
		if (comment != null) {
			DOMHelper domHelper = new DOMHelper(comment, true, true);
			for (Fragment fragment : Fragment.values()) {
				String frag = getFragmentContent(fragment.getName(), domHelper);
				if (frag != null) {
					returnMap.put(fragment, frag);
				}
			}
		}
		return returnMap.entrySet().size() > 0 ? returnMap : null;
	}
	
	public static String getPostFolioLocationComment(ForumPost courseTopicPost) {
		if (courseTopicPost != null) {
			String text = courseTopicPost.getText();
			DOMHelper domHelper = new DOMHelper(text, true, true);
			List<String> comments = domHelper.getComments();
			if (comments.size() > 0) {
				for(String comment : comments) {
					if (comment.contains("folioDetailsComment_" + courseTopicPost.getPostId())) {
						return comment;
					}
				}
			}
		}
		return null;
	}
	
	private static String getFragmentContent(String fragmentName, DOMHelper domHelper) {
		List<Node> fragments = domHelper.findNodesByClass(fragmentName + "Fragment");
		return fragments.size() > 0 ? fragments.get(0).getTextContent() :null;
	}
	
	public static enum Fragment {
		VOLUME("volume"),
		INSERT("insert"),
		FOLIO("folio");
		
		private String name;
		
		private Fragment(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	}

}
