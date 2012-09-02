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

import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.docsources.domain.Forum;

/**
  * Utility class to provides custom jstl functions.
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public final class JSTLFunctions {
    private JSTLFunctions() {}

    /**
     * This method encodes a string in UTF-8 charset.
     * 
     * @param stringToEncode String to be encoded.
     * @return input string encoded.
     */
    public static String encode(String stringToEncode) {
    	if (stringToEncode == null)
    		return "";
     
    	try {
    		return URLEncoder.encode(stringToEncode, "UTF-8");
    	} catch (UnsupportedEncodingException unsupportedEncodingException) {
    		return "unsupportedEncodingException";
    	}
     }
     
    /**
     * This method encodes a string in UTF-8 charset.
     * 
     * @param stringToEncode String to be encoded.
     * @return input string encoded.
     */
    public static String toString(List<Object> list) {
    	if (list == null)
    		return "";
    	 
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
}
