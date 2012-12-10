/*
 * JSTLFunctions.java
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.domain.Forum;
import org.medici.bia.domain.ForumPost;

/**
  * Utility class to provides custom jstl functions.
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public final class JSTLFunctions {
    
    private static Logger logger = Logger.getLogger(JSTLFunctions.class);

    /**
     * This method encodes a string in UTF-8 charset.
     * 
     * @param stringToEncode String to be encoded.
     * @return input string encoded.
     */
    public static String encode(String stringToEncode) {
    	if (stringToEncode == null) {
    		return "";
    	}
    	
    	try {
    		return URLEncoder.encode(stringToEncode, "UTF-8");
    	} catch (UnsupportedEncodingException unsupportedEncodingException) {
    		logger.error(unsupportedEncodingException);
    		return "unsupportedEncodingException";
    	}
     }

    /**
     * 
     * @param inputString
     * @param start
     * @param end
     * @return
     */
    public static String substring(String inputString, Integer start, Integer end) {
    	if ((start == null) || (end == null)) {
    		return inputString;
    	}

    	return StringUtils.abbreviate(inputString, start, end);
    }

    /**
     * This method encodes a string in UTF-8 charset.
     * 
     * @param stringToEncode String to be encoded.
     * @return input string encoded.
     */
    public static String toString(List<Object> list) {
    	if (list == null) {
    		return "";
    	}
    	 
    	StringBuilder stringBuilder = new StringBuilder("");
    	for (int i=0; i<list.size(); i++) {
    		stringBuilder.append(list.get(i));
    		if (i<(list.size()-1)) {
    			stringBuilder.append(", ");
    		}
    	}
    	
    	return stringBuilder.toString();
     }
    
    /**
     * This method return application property.
     * 
     * @param propertyName
     * @return
     */
    public static String getApplicationProperty(String propertyName) {
    	return ApplicationPropertyManager.getApplicationProperty(propertyName);
    }
    
    /**
     * 
     * @param forum
     * @return
     */
    public static String getForumChronology(Forum forum){
    	return ForumUtils.getForumChronology(forum);
    }
    
    public static String searchTextResultPost(ForumPost forumPost, String searchText){
    	return ForumUtils.searchTextResultPost(forumPost, searchText);
    }
    
    public static String abbreviateMessage(String messageBody){
    	return ForumUtils.abbreviateMessage(messageBody);
    }
}
