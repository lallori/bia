/*
 * AdvancedSearchAbstract.java
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
package org.medici.bia.common.search;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public abstract class AdvancedSearchAbstract implements AdvancedSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8972103504467413548L;

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum DateType {
		From("From"), Before("Before"), Between("Between"), InOn("InOn");
		
		private final String dateType;

	    private DateType(String value) {
	    	dateType = value;
	    }

	    @Override
	    public String toString(){
	        return dateType;
	    }
	}
	
	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum NameType {
		AllNameTypes("All Name Types"), Appellative("Appellative"), Family("Family"), Given("Given"), Maiden("Maiden"), Married("Married"), Patronymic("Patronymic");
		
		private final String nameType;

	    private NameType(String value) {
	    	nameType = value;
	    }

	    @Override
	    public String toString(){
	        return nameType;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum WordType {
		Extract("Extract"), Synopsis("Synopsis"), SynopsisAndExtract("SynopsisAndExtract"),
		Titles("Titles"), Notes("Notes"), TitlesAndNotes("TitlesAndNotes"); 

		private final String wordType;

	    private WordType(String value) {
	    	wordType = value;
	    }

	    @Override
	    public String toString(){
	        return wordType;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum VolumeType {
		Exactly("Exactly"), Between("Between");
		
		private final String volumeType;

	    private VolumeType(String value) {
	    	volumeType = value;
	    }

	    @Override
	    public String toString(){
	        return volumeType;
	    }
	}
	
	/**
	 * 
	 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
	 *
	 */
	public static enum FolioType {
		Exactly("Exactly"), Between("Between");
		
		private final String folioType;

	    private FolioType(String value) {
	    	folioType = value;
	    }

	    @Override
	    public String toString(){
	        return folioType;
	    }
	}
	
	/**
	 * 
	 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
	 *
	 */
	public static enum Gender {
		M("M"), F("F"), X("X");
		
		private final String gender;

	    private Gender(String value) {
	        gender = value;
	    }

	    @Override
	    public String toString(){
	        return gender;
	    }
	}

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum UserActionType {
		CreatedBy("CreatedBy"), LastUpdateBy("LastUpdateBy"); 

		private final String accountActionType;

	    private UserActionType(String value) {
	    	accountActionType = value;
	    }

	    @Override
	    public String toString(){
	        return accountActionType;
	    }
	}
	
	protected String getIdOrNamesToJpa(List<Integer> ids, String idQuery, String idPlaceHolder, List<String> names, String nameQuery, String namePlaceHolder) {
		StringBuilder builderId = new StringBuilder("");
		StringBuilder builderName = new StringBuilder("");
		if (ids.size() > 0) {
			for (int i = 0; i < ids.size(); i++) {
				if (ids.get(i) > 0) {
					if (builderId.length() > 0) {
						builderId.append(" AND ");
					}
					
					String subq = idQuery.replaceAll(idPlaceHolder, ids.get(i).toString());
					builderId.append(subq);
				} else {
					if (builderName.length() > 0) {
						builderName.append(" AND ");
					}
					
					String subq = nameQuery.replaceAll(namePlaceHolder, names.get(i).toLowerCase().replace("'","''"));
					builderName.append(subq);
				}
			}
		}
		return builderId.length() > 0
				? builderName.length() > 0
						? "(" + builderId.append(") AND (").append(builderName.toString()).append(")").toString()
						: builderId.toString()
				: builderName.length() > 0
						? builderName.toString()
						: "";
	}
	
	protected String listStringLikeToJpa(List<String> values, String field, boolean and) {
		StringBuilder builder = new StringBuilder("");
		for(String value : values) {
			if (builder.length() > 0) {
				builder.append(and ? " AND " : " OR ");
			}
			builder.append("(").append(field).append(" LIKE '%").append(value).append("%')");
		}
		return builder.toString();
	}
	
	protected String listStringEqualsToJpa(List<String> values, String field, boolean and) {
		StringBuilder builder = new StringBuilder("");
		for(String value : values) {
			if (builder.length() > 0) {
				builder.append(and ? " AND " : " OR ");
			}
			builder.append("(").append(field).append(" = '").append(value).append("')");
		}
		return builder.toString();
	}
	
	protected String listIntegerToJpa(List<String> values, String field, boolean and) {
		StringBuilder builder = new StringBuilder("");
		for(String value : values) {
			if (StringUtils.isNumeric(value)) {
				if (builder.length() > 0) {
					builder.append(and ? " AND " : " OR ");
				}
				builder.append("(").append(field).append(" = ").append(value).append(")");
			} else {
				continue;
			}
		}
		return builder.toString();
	}
	
	protected String listWordsToJpa(List<String> list, String field) {
		StringBuilder builder = new StringBuilder("");
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String currentWords = list.get(i);
				List<String> exactWords = new ArrayList<String>();
				
				if (builder.length() > 0) {
					builder.append(" AND ");
				}
				
				//MD: This code is to identify the words between double quotes
				while (currentWords.contains("\"")) {
					//First double quote
					int from = currentWords.indexOf("\"");
					//Second double quote
					int to = currentWords.indexOf("\"", from + 1);
					//If there is the second double quote or not
					if (to != -1) {
						//Add the exact words to the list and remove them from the string
						exactWords.add(currentWords.substring(from + 1, to));
						currentWords = currentWords.substring(0, from) + currentWords.substring(to + 1, currentWords.length());
					} else {
						currentWords = currentWords.replace("\"", " ");
						
					}
				}
				String[] wordsSingle = StringUtils.split(currentWords, " ");
				for (int j = 0; j < exactWords.size(); j++) {
					builder.append("(").append(field).append(" LIKE '%")
						.append(exactWords.get(j).replace("'", "''"))
						.append("%')");
					if (j < (exactWords.size() - 1)) {
						builder.append(" AND ");
					}
				}
				if (exactWords.size() > 0 && wordsSingle.length > 0) {
					builder.append(" AND ");
				}
				for (int j = 0; j < wordsSingle.length; j++) {
					if (j > 0) {
						builder.append(" AND ");
					}
					builder.append("(").append(field).append(" LIKE '%")
						.append(wordsSingle[j].replace("'", "''"))
						.append("%')");
				}
			}
		}
		return builder.toString();
	}
	
	protected String booleanToJPA(Boolean value, String field) {
		StringBuilder builder = new StringBuilder("");
		if (value != null) {
			builder.append(field).append(Boolean.TRUE.equals(value) ? " = true" : " = false");
		}
		return builder.toString();
	}
}
