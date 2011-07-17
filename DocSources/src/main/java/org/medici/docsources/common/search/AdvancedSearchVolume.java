/*
 * AdvancedSearchVolume.java
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

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.medici.docsources.command.search.AdvancedSearchVolumesCommand;
import org.medici.docsources.common.util.DateUtils;
import org.medici.docsources.common.util.VolumeUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class AdvancedSearchVolume implements AdvancedSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5135090884608784944L;

	private List<WordType> wordsTypes;
	private List<String> words;
	private List<String> volumes;
	private List<String> volumesBetween;
	private List<VolumeType> volumesTypes;
	private List<Integer> datesDay;
	private List<Integer> datesMonth;
	private List<DateType> datesTypes;
	private List<Integer> datesYear;
	private List<Integer> datesYearBetween;
	private List<Integer> datesMonthBetween;
	private List<Integer> datesDayBetween;

	/**
	 * 
	 */
	public AdvancedSearchVolume() {
		super();
	}
	
	/**
	 * 
	 * @param command
	 */
	public void initFromAdvancedSearchVolumesCommand(AdvancedSearchVolumesCommand command) {
	}

	/**
	 * @return the wordsTypes
	 */
	public List<WordType> getWordsTypes() {
		return wordsTypes;
	}

	/**
	 * @param wordsTypes the wordsTypes to set
	 */
	public void setWordsTypes(List<WordType> wordsTypes) {
		this.wordsTypes = wordsTypes;
	}

	/**
	 * @return the words
	 */
	public List<String> getWords() {
		return words;
	}

	/**
	 * @param words the words to set
	 */
	public void setWords(List<String> words) {
		this.words = words;
	}

	/**
	 * @return the volumes
	 */
	public List<String> getVolumes() {
		return volumes;
	}

	/**
	 * @param volumes the volumes to set
	 */
	public void setVolumes(List<String> volumes) {
		this.volumes = volumes;
	}

	/**
	 * @return the volumesBetween
	 */
	public List<String> getVolumesBetween() {
		return volumesBetween;
	}

	/**
	 * @param volumesBetween the volumesBetween to set
	 */
	public void setVolumesBetween(List<String> volumesBetween) {
		this.volumesBetween = volumesBetween;
	}

	/**
	 * @return the volumesTypes
	 */
	public List<VolumeType> getVolumesTypes() {
		return volumesTypes;
	}

	/**
	 * @param volumesTypes the volumesTypes to set
	 */
	public void setVolumesTypes(List<VolumeType> volumesTypes) {
		this.volumesTypes = volumesTypes;
	}

	/**
	 * @return the datesDay
	 */
	public List<Integer> getDatesDay() {
		return datesDay;
	}

	/**
	 * @param datesDay the datesDay to set
	 */
	public void setDatesDay(List<Integer> datesDay) {
		this.datesDay = datesDay;
	}

	/**
	 * @return the datesMonth
	 */
	public List<Integer> getDatesMonth() {
		return datesMonth;
	}

	/**
	 * @param datesMonth the datesMonth to set
	 */
	public void setDatesMonth(List<Integer> datesMonth) {
		this.datesMonth = datesMonth;
	}

	/**
	 * @return the datesTypes
	 */
	public List<DateType> getDatesTypes() {
		return datesTypes;
	}

	/**
	 * @param datesTypes the datesTypes to set
	 */
	public void setDatesTypes(List<DateType> datesTypes) {
		this.datesTypes = datesTypes;
	}

	/**
	 * @return the datesYear
	 */
	public List<Integer> getDatesYear() {
		return datesYear;
	}

	/**
	 * @param datesYear the datesYear to set
	 */
	public void setDatesYear(List<Integer> datesYear) {
		this.datesYear = datesYear;
	}

	/**
	 * @return the datesYearBetween
	 */
	public List<Integer> getDatesYearBetween() {
		return datesYearBetween;
	}

	/**
	 * @param datesYearBetween the datesYearBetween to set
	 */
	public void setDatesYearBetween(List<Integer> datesYearBetween) {
		this.datesYearBetween = datesYearBetween;
	}

	/**
	 * @return the datesMonthBetween
	 */
	public List<Integer> getDatesMonthBetween() {
		return datesMonthBetween;
	}

	/**
	 * @param datesMonthBetween the datesMonthBetween to set
	 */
	public void setDatesMonthBetween(List<Integer> datesMonthBetween) {
		this.datesMonthBetween = datesMonthBetween;
	}

	/**
	 * @return the datesDayBetween
	 */
	public List<Integer> getDatesDayBetween() {
		return datesDayBetween;
	}

	/**
	 * @param datesDayBetween the datesDayBetween to set
	 */
	public void setDatesDayBetween(List<Integer> datesDayBetween) {
		this.datesDayBetween = datesDayBetween;
	}

	@Override
	public Query toLuceneQuery() {
		BooleanQuery booleanQuery = new BooleanQuery();

		if (words.size()>0) {
			BooleanQuery wordsQuery = new BooleanQuery();
			for (int i=0; i<words.size(); i++) {
				if (wordsTypes.get(i).equals(WordType.Titles)) {
					// (+synExtract.docExtract:med*)
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("serieList.Title", words.get(i).toLowerCase())), Occur.MUST);
					wordsQuery.add(booleanClause);
				} else if (wordsTypes.get(i).equals(WordType.Notes)) {
					// (synExtract.synopsis:med*)
					BooleanClause booleanClause = new BooleanClause(new PrefixQuery(new Term("ccontext", words.get(i).toLowerCase())), Occur.MUST);
					wordsQuery.add(booleanClause);
				} else if (wordsTypes.get(i).equals(WordType.TitlesAndNotes)) {
					BooleanQuery subQuery = new BooleanQuery();
					// +(+synExtract.docExtract:med* +synExtract.synopsis:med*) 
					BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("serieList.Title", words.get(i).toString())), Occur.SHOULD);
					subQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("serieList.subTitle1", words.get(i).toString())), Occur.SHOULD);
					subQuery.add(booleanClause);
					booleanClause = new BooleanClause(new TermQuery(new Term("serieList.subTitle2", words.get(i).toString())), Occur.SHOULD);
					subQuery.add(booleanClause);
					booleanClause = new BooleanClause(new PrefixQuery(new Term("ccontext", words.get(i).toLowerCase())), Occur.MUST);
					subQuery.add(booleanClause);
					
					wordsQuery.add(subQuery, Occur.MUST);
				}
			}
			booleanQuery.add(wordsQuery, Occur.MUST);

		}

		// Volume
		if (volumes.size()>0) {
			for (int i=0; i<volumes.size(); i++) {
				if (VolumeUtils.isVolumeFormat(volumes.get(i))) {
					if (volumesTypes.get(i).equals(VolumeType.Exactly)) {
						if (StringUtils.isNumeric(volumes.get(i))) {
							// (volume.volNum:1)
							BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("volume.volNum", volumes.get(i))), Occur.MUST);
							booleanQuery.add(booleanClause);
						} else {
							BooleanQuery subQuery = new BooleanQuery();
							// (volume.volNum:1 AND volume.volLetExt:a)
							BooleanClause booleanClause = new BooleanClause(new TermQuery(new Term("volume.volNum", VolumeUtils.extractVolNum(volumes.get(i)).toString())), Occur.MUST);
							subQuery.add(booleanClause);

							booleanClause.setQuery(new TermQuery(new Term("volume.volLetExt", VolumeUtils.extractVolNum(volumes.get(i)).toString())));
							booleanClause.setOccur(Occur.MUST);
							subQuery.add(booleanClause);

							booleanQuery.add(subQuery, Occur.MUST);
						}
					} else if (volumesTypes.get(i).equals(VolumeType.Between)) {
						// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
						NumericRangeQuery<Integer> volumeRangeQuery = NumericRangeQuery.newIntRange("volume.volNum_Sort", 4, 
								NumberUtils.toInt(volumes.get(i)), 
								NumberUtils.toInt(volumesBetween.get(i)), 
								true, 
								true);
						booleanQuery.add(volumeRangeQuery, Occur.MUST); 
					}
				} else {
					// if volume value is not in volume format we discard it!
					continue;
				}
			}
		}

		// Date
		if (datesTypes.size()>0) {
			BooleanQuery datesQuery = new BooleanQuery();

			for (int i=0; i<datesTypes.size(); i++) {
				if (datesTypes.get(i) == null) {
					continue;
				} else if (datesTypes.get(i).equals(DateType.After)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.MAX_DATE, 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.SHOULD); 
				} else if (datesTypes.get(i).equals(DateType.Before)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.MIN_DATE,
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.SHOULD);
				}else if (datesTypes.get(i).equals(DateType.Between)) {
					// Range query can be executed only on UN_TOKENIZED lucene field, so we use sort field.
					NumericRangeQuery<Integer> dateRangeQuery = NumericRangeQuery.newIntRange("docDate_Sort", 4, 
							DateUtils.getLuceneDate(datesYear.get(i), datesMonth.get(i), datesDay.get(i)), 
							DateUtils.getLuceneDate(datesYearBetween.get(i), datesMonthBetween.get(i), datesDayBetween.get(i)), 
							true, 
							true);
					datesQuery.add(dateRangeQuery, Occur.SHOULD); 
				}
			}
			
			booleanQuery.add(datesQuery, Occur.MUST);
		}

		return booleanQuery;
	} 

	/**
	 * 
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum DateType {
		After("After"), Before("Before"), Between("Between");
		
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
	public static enum VolumeType {
		Between("Between"), Exactly("Exactly");
		
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
	 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
	 *
	 */
	public static enum WordType {
		Notes("Notes"), Titles("VolumeTitles"), TitlesAndNotes("TitlesAndNotes");

		private final String wordType;

	    private WordType(String value) {
	    	wordType = value;
	    }

	    @Override
	    public String toString(){
	        return wordType;
	    }
	}
}

