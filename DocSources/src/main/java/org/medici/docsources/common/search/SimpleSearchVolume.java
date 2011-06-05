/*
 * SimpleSearchVolume.java
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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.medici.docsources.common.util.RegExUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class SimpleSearchVolume implements SimpleSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;
	
	private String alias; 

	/**
	 * 
	 */
	public SimpleSearchVolume() {
		super();
	}

	/**
	 * 
	 * @param text
	 */
	public SimpleSearchVolume(String text) {
		super();
		if (!StringUtils.isEmpty(text)) {
			setAlias(text.toLowerCase());
		}
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * 
	 * @param command
	 */
	public void initFromText(String text) {
		if (!StringUtils.isEmpty(text)) {
			setAlias(text);
		}
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * It's more simple construct lucene Query with string.
	 */
	@Override
	public String toLuceneQueryString() {
		if (StringUtils.isEmpty(alias)) {
			return "";
		}

		String[] stringFields = new String[]{			
			"ccondition",
			"ccontext", 
			"orgNotes",
			"recips", 
			"senders", 
			"serieList.title", 
			"serieList.subTitle1",
			"serieList.subTitle2", 
		};
		String[] numericFields = new String[]{
			"summaryId",
			"startYear",
			"startMonthNum.monthName", 
			"startDay",
			"endYear",
			"endMonthNum.monthName", 
			"endDay"		
		};

		String[] words = RegExUtils.splitPunctuationAndSpaceChars(alias);

		//E.g. (recipientPeople.mapNameLf: (+cosimo +medici +de) )
		StringBuffer stringBuffer = new StringBuffer();

		// We add conditions on string fields
		for (int i=0; i<stringFields.length; i++) {
			// volume.serieList.title
			stringBuffer.append("(");
			stringBuffer.append(stringFields[i]);
			stringBuffer.append(": (");
			for (int j=0; j<words.length; j++) {
				stringBuffer.append("+");
				stringBuffer.append( words[j]);
				stringBuffer.append(" ");
			}
			stringBuffer.append(")) ");
		}

		// We add conditions on numeric fields only for input word which are numbers
		for (int i=0; i<words.length; i++) {
			if (!NumberUtils.isNumber(words[i])) {
				continue;
			}
			for (int j=0; j<numericFields.length; j++) {
				stringBuffer.append("(");
				stringBuffer.append(numericFields[j]);
				stringBuffer.append(": ");
				stringBuffer.append(words[i]);
				stringBuffer.append(") ");
			}
		}
		
		return stringBuffer.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (alias != null)
			return getAlias();
		else
			return "";
	}
}

