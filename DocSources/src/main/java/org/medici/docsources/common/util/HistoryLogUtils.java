/*
 * HistoryLogUtils.java
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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.medici.docsources.domain.HistoryLog.ActionCategory;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class HistoryLogUtils {

	/**
	 * This method extract actionGroup from an user action request.
	 * 
	 * @param requestedURL
	 * @return
	 * 
	 */
	public static String extractAction(HttpServletRequest request, ActionCategory actionCategory) {
		if (actionCategory.equals(ActionCategory.DOCUMENTS)) {
			return extractActionDocument(request);
		} else if (actionCategory.equals(ActionCategory.PEOPLE)) {
			return extractActionPeople(request);
		} else if (actionCategory.equals(ActionCategory.PLACE)) {
			return extractActionPlace(request);
		} else if (actionCategory.equals(ActionCategory.VOLUME)) {
			return extractActionVolume(request);
		}

		return "Unknown action";
	}

	/**
	 * This method extract actionGroup from an user action request.
	 * 
	 * @param requestedURL
	 * @return
	 * 
	 */
	public static ActionCategory extractActionCategory(String requestedURL) {
		if (StringUtils.contains(requestedURL, "docbase")) {
			return ActionCategory.DOCUMENTS;
		} else if (StringUtils.contains(requestedURL, "peoplebase")) {
			return ActionCategory.PEOPLE;
		} else if (StringUtils.contains(requestedURL, "geobase")) {
			return ActionCategory.PLACE;
		} else if (StringUtils.contains(requestedURL, "volbase")) {
			return ActionCategory.VOLUME;
		} else if (StringUtils.contains(requestedURL, "SimpleSearchPagination") ) {
			return ActionCategory.SIMPLE_SEARCH;
		} else if (StringUtils.contains(requestedURL, "AdvancedSearchPagination") ) {
			return ActionCategory.ADVANCED_SEARCH;
		}

		return ActionCategory.UNKNOWN_CATEGORY;
	}

	private static String extractActionDocument(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (StringUtils.contains(uri, "Create")) {
			return "Create document";
		} else if (StringUtils.contains(uri, "Compare")) {
			return "Compare document";
		} else if (StringUtils.contains(uri, "Edit")) {
			if (request.getMethod().equals("GET")) {
				String[] action = uri.substring(0, uri.indexOf("Document")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Show form for ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			} else if (request.getMethod().equals("POST")) {
				String[] action = uri.substring(0, uri.indexOf("Document")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Apply ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			}
		}  else if (StringUtils.contains(uri, "Delete")) {
			String[] action = uri.substring(0, uri.indexOf("Document")).split("(?=[A-Z])");
			StringBuffer retValue = new StringBuffer("Apply ");
			for (int i=0; i<action.length; i++) {
				retValue.append(" ");
				retValue.append(action[i].toLowerCase());
			}
			return retValue.toString();
		} else 	if (StringUtils.contains(uri, "Show")) {
			if (StringUtils.contains(uri, "ShowDocument")) {
				return "Show document";
			} else if (StringUtils.contains(uri, "LastEntry")) {
				return "Show last document";
			} else if (StringUtils.contains(uri, "Vetting")) {
				return "Show vetting chronology";
			}
		}
		
		return "Not available";
	}

	private static String extractActionPeople(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (StringUtils.contains(uri, "Create")) {
			return "Create person";
		} else if (StringUtils.contains(uri, "Compare")) {
			return "Compare person";
		} else if (StringUtils.contains(uri, "Edit")) {
			if (request.getMethod().equals("GET")) {
				String[] action = uri.substring(0, uri.indexOf("Person")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Show form for ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			} else if (request.getMethod().equals("POST")) {
				String[] action = uri.substring(0, uri.indexOf("Person")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Apply ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			}
		}  else if (StringUtils.contains(uri, "Delete")) {
			String[] action = uri.substring(0, uri.indexOf("Person")).split("(?=[A-Z])");
			StringBuffer retValue = new StringBuffer("Delete ");
			for (int i=0; i<action.length; i++) {
				retValue.append(" ");
				retValue.append(action[i].toLowerCase());
			}
			return retValue.toString();
		} else 	if (StringUtils.contains(uri, "Show")) {
			if (StringUtils.contains(uri, "ShowPerson")) {
				return "Show person";
			} else if (StringUtils.contains(uri, "LastEntry")) {
				return "Show last person";
			} else if (StringUtils.contains(uri, "Vetting")) {
				return "Show vetting chronology";
			}
		}
		
		return "Not available";
	}

	private static String extractActionPlace(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (StringUtils.contains(uri, "Create")) {
			return "Create place";
		} else if (StringUtils.contains(uri, "Compare")) {
			return "Compare place";
		} else if (StringUtils.contains(uri, "Edit")) {
			if (request.getMethod().equals("GET")) {
				String[] action = uri.substring(0, uri.indexOf("Place")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Show form for ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			} else if (request.getMethod().equals("POST")) {
				String[] action = uri.substring(0, uri.indexOf("Place")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Apply ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			}
		}  else if (StringUtils.contains(uri, "Delete")) {
			String[] action = uri.substring(0, uri.indexOf("Place")).split("(?=[A-Z])");
			StringBuffer retValue = new StringBuffer("Delete ");
			for (int i=0; i<action.length; i++) {
				retValue.append(" ");
				retValue.append(action[i].toLowerCase());
			}
			return retValue.toString();
		} else 	if (StringUtils.contains(uri, "Show")) {
			if (StringUtils.contains(uri, "ShowPerson")) {
				return "Show place";
			} else if (StringUtils.contains(uri, "LastEntry")) {
				return "Show last place";
			} else if (StringUtils.contains(uri, "Vetting")) {
				return "Show vetting chronology";
			}
		}
		
		return "Not available";
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private static String extractActionVolume(HttpServletRequest request) {
		String uri = request.getRequestURI();
		if (StringUtils.contains(uri, "Create")) {
			return "Create volume";
		} else if (StringUtils.contains(uri, "Compare")) {
			return "Compare volume";
		} else if (StringUtils.contains(uri, "Edit")) {
			if (request.getMethod().equals("GET")) {
				String[] action = uri.substring(0, uri.indexOf("Volume")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Show form for ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			} else if (request.getMethod().equals("POST")) {
				String[] action = uri.substring(0, uri.indexOf("Volume")).split("(?=[A-Z])");
				StringBuffer retValue = new StringBuffer("Apply ");
				for (int i=0; i<action.length; i++) {
					retValue.append(" ");
					retValue.append(action[i].toLowerCase());
				}
				return retValue.toString();
			}
		}  else if (StringUtils.contains(uri, "Delete")) {
			String[] action = uri.substring(0, uri.indexOf("Volume")).split("(?=[A-Z])");
			StringBuffer retValue = new StringBuffer("Delete ");
			for (int i=0; i<action.length; i++) {
				retValue.append(" ");
				retValue.append(action[i].toLowerCase());
			}
			return retValue.toString();
		} else 	if (StringUtils.contains(uri, "Show")) {
			if (StringUtils.contains(uri, "ShowVolume")) {
				return "Show volume";
			} else if (StringUtils.contains(uri, "LastEntry")) {
				return "Show last volume";
			} else if (StringUtils.contains(uri, "Vetting")) {
				return "Show vetting chronology";
			}
		}

		return "Not available";
	}

	/**
	 * This method extract actionGroup from an user action request.
	 * 
	 * @param requestedURL
	 * @return
	 * 
	 */
	public static String extractActionUrl(HttpServletRequest request, ActionCategory actionCategory) {
		if (actionCategory.equals(ActionCategory.DOCUMENTS)) {
			return extractActionUrlDocument(request);
		} else if (actionCategory.equals(ActionCategory.PEOPLE)) {
			return extractActionUrlPeople(request);
		} else if (actionCategory.equals(ActionCategory.PLACE)) {
			return extractActionUrlPlace(request);
		} else if (actionCategory.equals(ActionCategory.VOLUME)) {
			return extractActionUrlVolume(request);
		}

		return null;
	}

	private static String extractActionUrlVolume(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String extractActionUrlPlace(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String extractActionUrlPeople(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String extractActionUrlDocument(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}