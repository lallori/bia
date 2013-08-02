/*
 * URLTransformer.java
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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class has utilities to perform encoding or decoding of strings with unicode characters.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class URLTransformer {
	
	private static final Map<String, String> mapping;
	static {
		Map<String, String> map = new HashMap<String, String>();
		map.put("%A0","\u00A0");
		map.put("%A1","\u00A1");
		map.put("%A2","\u00A2");
		map.put("%A3","\u00A3");
		map.put("%A4","\u00A4");
		map.put("%A5","\u00A5");
		map.put("%A6","\u00A6");
		map.put("%A7","\u00A7");
		map.put("%A8","\u00A8");
		map.put("%A9","\u00A9");
		map.put("%AA","\u00AA");
		map.put("%AB","\u00AB");
		map.put("%AC","\u00AC");
		map.put("%AD","\u00AD");
		map.put("%AE","\u00AE");
		map.put("%AF","\u00AF");
		map.put("%B0","\u00B0");
		map.put("%B1","\u00B1");
		map.put("%B2","\u00B2");
		map.put("%B3","\u00B3");
		map.put("%B4","\u00B4");
		map.put("%B5","\u00B5");
		map.put("%B6","\u00B6");
		map.put("%B7","\u00B7");
		map.put("%B8","\u00B8");
		map.put("%B9","\u00B9");
		map.put("%BA","\u00BA");
		map.put("%BB","\u00BB");
		map.put("%BC","\u00BC");
		map.put("%BD","\u00BD");
		map.put("%BE","\u00BE");
		map.put("%BF","\u00BF");
		map.put("%C0","\u00C0");
		map.put("%C1","\u00C1");
		map.put("%C2","\u00C2");
		map.put("%C3","\u00C3");
		map.put("%C4","\u00C4");
		map.put("%C5","\u00C5");
		map.put("%C6","\u00C6");
		map.put("%C7","\u00C7");
		map.put("%C8","\u00C8");
		map.put("%C9","\u00C9");
		map.put("%CA","\u00CA");
		map.put("%CB","\u00CB");
		map.put("%CC","\u00CC");
		map.put("%CD","\u00CD");
		map.put("%CE","\u00CE");
		map.put("%CF","\u00CF");
		map.put("%D0","\u00D0");
		map.put("%D1","\u00D1");
		map.put("%D2","\u00D2");
		map.put("%D3","\u00D3");
		map.put("%D4","\u00D4");
		map.put("%D5","\u00D5");
		map.put("%D6","\u00D6");
		map.put("%D7","\u00D7");
		map.put("%D8","\u00D8");
		map.put("%D9","\u00D9");
		map.put("%DA","\u00DA");
		map.put("%DB","\u00DB");
		map.put("%DC","\u00DC");
		map.put("%DD","\u00DD");
		map.put("%DE","\u00DE");
		map.put("%DF","\u00DF");
		map.put("%E0","\u00E0");
		map.put("%E1","\u00E1");
		map.put("%E2","\u00E2");
		map.put("%E3","\u00E3");
		map.put("%E4","\u00E4");
		map.put("%E5","\u00E5");
		map.put("%E6","\u00E6");
		map.put("%E7","\u00E7");
		map.put("%E8","\u00E8");
		map.put("%E9","\u00E9");
		map.put("%EA","\u00EA");
		map.put("%EB","\u00EB");
		map.put("%EC","\u00EC");
		map.put("%ED","\u00ED");
		map.put("%EE","\u00EE");
		map.put("%EF","\u00EF");
		map.put("%F0","\u00F0");
		map.put("%F1","\u00F1");
		map.put("%F2","\u00F2");
		map.put("%F3","\u00F3");
		map.put("%F4","\u00F4");
		map.put("%F5","\u00F5");
		map.put("%F6","\u00F6");
		map.put("%F7","\u00F7");
		map.put("%F8","\u00F8");
		map.put("%F9","\u00F9");
		map.put("%FA","\u00FA");
		map.put("%FB","\u00FB");
		map.put("%FC","\u00FC");
		map.put("%FD","\u00FD");
		map.put("%FE","\u00FE");
		map.put("%FF","\u00FF");
		mapping = Collections.unmodifiableMap(map);
	}
	
	private static Pattern p = Pattern.compile("%[A-F][0-9A-F]");
	
	/**
	 * Decodes an encoded string.
	 * 
	 * @param encoded the encoded string
	 * @return decoded string
	 */
	public static String decode(String encoded) {
		String e = encoded;
		if (e != null) {
			Matcher m = p.matcher(e);
			while (m.find())
				e = e.replace(m.group(), mapping.get(m.group()));
		}
		return e;
	}
	
	/**
	 * Encodes a decoded string.
	 * 
	 * @param decoded the decoded string
	 * @return encoded string
	 */
	public static String encode(String decoded) {
		String d = decoded;
		if (d != null) {
			for (Entry<String,String> entry : mapping.entrySet()) {
				d = d.replaceAll(entry.getValue(), entry.getKey());
			}
		}
		return d;
	}
	
}
