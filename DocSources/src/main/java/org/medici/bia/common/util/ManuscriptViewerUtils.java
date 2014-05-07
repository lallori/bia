/*
 * ManuscriptViewerUtils.java
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
import java.util.List;

import org.medici.bia.domain.Annotation;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ManuscriptViewerUtils {
	
	public static List<Annotation.Type> getNotConsideredTypes(ManuscriptMode mode) {
		if (mode == null) {
			mode = ManuscriptMode.COMMUNITY;
		}
		
		List<Annotation.Type> types = new ArrayList<Annotation.Type>();
		switch (mode) {
			case ALL:
				break;
			case COMMUNITY:
				types.add(Annotation.Type.TEACHING);
				break;
			case FORUM:
				types.add(Annotation.Type.PERSONAL);
				types.add(Annotation.Type.TEACHING);
				break;
			case TEACHING:
				types.add(Annotation.Type.GENERAL);
				types.add(Annotation.Type.PALEOGRAPHY);
				types.add(Annotation.Type.PERSONAL);
				break;
		}
		
		return types;
	}
	
	public static enum ManuscriptMode {
		ALL("A"),
		COMMUNITY("C"),
		FORUM("F"),
		TEACHING("T");
		
		private String code;
		
		private ManuscriptMode(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}
		
		public static ManuscriptMode getByCode(String code) {
			if (code == null) {
				return null;
			}
			if ("A".equalsIgnoreCase(code.trim())) {
				return ALL;
			}
			if ("C".equalsIgnoreCase(code.trim())) {
				return COMMUNITY;
			}
			if ("T".equalsIgnoreCase(code.trim())) {
				return TEACHING;
			}
			return null;
		}
	}

}
