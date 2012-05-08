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
package org.medici.docsources.common.search;

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
}
