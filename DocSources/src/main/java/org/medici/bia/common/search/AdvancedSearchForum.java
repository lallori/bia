/*
 * AdvancedSearchForum.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.medici.bia.command.community.AdvancedSearchForumPostCommand;
import org.medici.bia.command.search.AdvancedSearchCommand;
import org.medici.bia.command.search.SimpleSearchCommand;
import org.medici.bia.common.util.RegExUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class AdvancedSearchForum extends AdvancedSearchAbstract {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2294835434784505729L;

	private String author;
	private List<String> words;
	private Boolean allTerms;
	private String wordsType;
	private List<Integer> forumsId;
	private Integer limitResults;
	private String displayResults;

	/**
	 * 
	 */
	public AdvancedSearchForum() {
		super();

		forumsId = new ArrayList<Integer>(0);
		author = null;
		words = new ArrayList<String>(0);
		allTerms = null;
		wordsType = null;
		limitResults = null;
		displayResults = null;
	}

	/**
	 * @return the allTerms
	 */
	public Boolean getAllTerms() {
		return allTerms;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the displayResults
	 */
	public String getDisplayResults() {
		return displayResults;
	}

	/**
	 * @return the forumsId
	 */
	public List<Integer> getForumsId() {
		return forumsId;
	}

	/**
	 * @return the limitResults
	 */
	public Integer getLimitResults() {
		return limitResults;
	}

	/**
	 * @return the words
	 */
	public List<String> getWords() {
		return words;
	}

	/**
	 * @return the wordsType
	 */
	public String getWordsType() {
		return wordsType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromAdvancedSearchCommand(AdvancedSearchCommand command) {
	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initFromSimpleSearchCommand(SimpleSearchCommand command) {
	}
	
	/**
	 * 
	 * @param command
	 */
	public void initFromAdvancedSearchForum(AdvancedSearchForumPostCommand command){
		//Words
		if((command.getText() != null) && (command.getText().length() > 0)){
			words = new ArrayList<String>();
			wordsType = command.getWordsType();
			String [] wordArray = RegExUtils.splitPunctuationAndSpaceChars(command.getText());
			for(String currentWord : wordArray){
				words.add(currentWord);
			}
		} else {
			words = new ArrayList<String>(0);
		}
		
		//Author
		if(command.getTextAuthor() != null){
			author = command.getTextAuthor();
		}else{
			author = null;
		}
		
		//AllTerms
		if(command.getAllTerms() != null){
			allTerms = command.getAllTerms();
		}else{
			allTerms = Boolean.FALSE;
		}
		
		//ForumsId
		if((command.getForumsId() != null) && (command.getForumsId().size() > 0)){
			forumsId = new ArrayList<Integer>(command.getForumsId().size());
			
			for(Integer currentForumId : command.getForumsId()){
				forumsId.add(currentForumId);
			}
		} else {
			forumsId = new ArrayList<Integer>(0);
		}
		
		//LimitResults
		if(command.getLimitResults() != null){
			limitResults = command.getLimitResults();
		}else{
			limitResults = 0;
		}
		
		//DisplayResults
		if(command.getDisplayResults() != null){
			if(command.getDisplayResults().equals("Posts")){
				displayResults = "ForumPost";
			}else if(command.getDisplayResults().equals("Topics")){
				displayResults = "ForumTopic";
			}
		}
	}

	@Override
	public Boolean empty() {
		if ((forumsId.size()>0) || (author != null)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * @param allTerms the allTerms to set
	 */
	public void setAllTerms(Boolean allTerms) {
		this.allTerms = allTerms;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param displayResults the displayResults to set
	 */
	public void setDisplayResults(String displayResults) {
		this.displayResults = displayResults;
	}

	/**
	 * @param forumsId the forumsId to set
	 */
	public void setForumsId(List<Integer> forumsId) {
		this.forumsId = forumsId;
	}

	/**
	 * @param limitResults the limitResults to set
	 */
	public void setLimitResults(Integer limitResults) {
		this.limitResults = limitResults;
	}

	/**
	 * @param words the words to set
	 */
	public void setWords(List<String> words) {
		this.words = words;
	}

	/**
	 * @param wordsType the wordsType to set
	 */
	public void setWordsType(String wordsType) {
		this.wordsType = wordsType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toJPAQuery() {
		if(displayResults.equals("ForumPost")){
			StringBuilder jpaQuery = new StringBuilder("FROM ForumPost WHERE ");
	
			//Words
			if(words != null && words.size() > 0){
				StringBuilder textQuery = new StringBuilder("(");
				if(wordsType.equals("TEXT")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(text LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%')");
					}
				}else if(wordsType.equals("SUBJECT_TEXT")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(text LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%') OR (subject LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%')");
					}
				}else if(wordsType.equals("TITLE")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(topic.subject LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%')");
					}
				}else if(wordsType.equals("FIRST_POST")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(text LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%') AND (parentPost.postId IS NULL)");
					}
				}
				textQuery.append(')');
				if(!textQuery.toString().equals("")){
					if(jpaQuery.length() > 21){
						jpaQuery.append(" AND ");
					}
					jpaQuery.append(textQuery);
				}
			}
			
			//ForumsId
			if (forumsId != null && forumsId.size()>0) {
				StringBuilder forumsQuery = new StringBuilder("("); 
				for (int i=0; i<forumsId.size(); i++) {
					if(forumsQuery.length() > 1){
						forumsQuery.append(" OR ");
					}
					forumsQuery.append("(forum.fullPath LIKE '%.");
				
					forumsQuery.append(forumsId.get(i));
					forumsQuery.append(".%')");
				}
	
				forumsQuery.append(')');
				if(!forumsQuery.toString().equals("")){
					if(jpaQuery.length() > 21){
						jpaQuery.append(" AND ");
					}
					jpaQuery.append(forumsQuery);
				}
			}
	
			// person;
			if (author != null && author.length() > 0) {
				StringBuilder authorQuery = new StringBuilder("(user.account LIKE '%");
				authorQuery.append(author);
				authorQuery.append("%')");
				if(jpaQuery.length() > 21){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(authorQuery);
			}
			
			//LimitResults
			if(limitResults != null && limitResults > 0){
				StringBuilder dateQuery = new StringBuilder("(");
				Date limitDate = new Date();
				Calendar l = Calendar.getInstance();
				l.setTime(limitDate);
				l.set(l.get(Calendar.YEAR), l.get(Calendar.MONTH), l.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				switch(limitResults){
					case 30: 
						l.add(Calendar.MONTH, -1);
						break;
					case 90:
						l.add(Calendar.MONTH, -3);
						break;
					case 180:
						l.add(Calendar.MONTH, -6);
						break;
					case 365:
						l.add(Calendar.YEAR, -1);
						break;
					default:
						l.add(Calendar.DAY_OF_MONTH, (0 - limitResults));
						break;
				}
				dateQuery.append("lastUpdate BETWEEN '");
				dateQuery.append(l.get(Calendar.YEAR) + "/" + (l.get(Calendar.MONTH) + 1) + "/" + l.get(Calendar.DAY_OF_MONTH));
				dateQuery.append("' AND '");
				l.setTime(limitDate);
				dateQuery.append(l.get(Calendar.YEAR) + "/" + (l.get(Calendar.MONTH) + 1) + "/" + l.get(Calendar.DAY_OF_MONTH));
				dateQuery.append("' )");
				if(jpaQuery.length() > 21){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(dateQuery);
			}
			
			//Logical Delete
			if(jpaQuery.length() > 21){
				jpaQuery.append(" AND ");
			}
			jpaQuery.append("logicalDelete = false");
			
			return jpaQuery.toString();
		}else if(displayResults.equals("ForumTopic")){
			StringBuilder jpaQuery = new StringBuilder("FROM ForumTopic WHERE ");
			
			//Words
			if(words != null && words.size() > 0){
				StringBuilder textQuery = new StringBuilder("(");
				if(wordsType.equals("SUBJECT_TEXT")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(subject LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%') OR (topicId IN (SELECT topic.topicId FROM ForumPost WHERE (subject LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%') OR (text LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%')))");						
					}
				}else if(wordsType.equals("TEXT")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(topicId IN (SELECT topic.topicId FROM ForumPost WHERE (text LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%')))");
					}
				}else if(wordsType.equals("TITLE")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(subject LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%')");
					}
				}else if(wordsType.equals("FIRST_POST")){
					for(int i = 0; i < words.size(); i++){
						if(textQuery.length() > 1){
							textQuery.append(" AND ");
						}
						textQuery.append("(topicId IN (SELECT topic.topicId FROM ForumPost WHERE (text LIKE '%");
						textQuery.append(words.get(i));
						textQuery.append("%') AND (parentPost.postId IS NULL)))");
					}
				}
				textQuery.append(')');
				if(!textQuery.toString().equals("")){
					if(jpaQuery.length() > 22){
						jpaQuery.append(" AND ");
					}
					jpaQuery.append(textQuery);
				}
			}
			
			//ForumsId
			if (forumsId != null && forumsId.size()>0) {
				if (forumsId != null && forumsId.size()>0) {
					StringBuilder forumsQuery = new StringBuilder("("); 
					for (int i=0; i<forumsId.size(); i++) {
						if(forumsQuery.length() > 1){
							forumsQuery.append(" OR ");
						}
						forumsQuery.append("(forum.fullPath LIKE '%.");
					
						forumsQuery.append(forumsId.get(i));
						forumsQuery.append(".%')");
					}
		
					forumsQuery.append(')');
					if(!forumsQuery.toString().equals("")){
						if(jpaQuery.length() > 22){
							jpaQuery.append(" AND ");
						}
						jpaQuery.append(forumsQuery);
					}
				}
			}
			
			// person;
			if (author != null && author.length() > 0) {
				StringBuilder authorQuery = new StringBuilder("(user.account LIKE '%");
				authorQuery.append(author);
				authorQuery.append("%')");
				if(jpaQuery.length() > 22){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(authorQuery);
			}
			
			//LimitResults
			if(limitResults != null && limitResults > 0){
				StringBuilder dateQuery = new StringBuilder("(");
				Date limitDate = new Date();
				Calendar l = Calendar.getInstance();
				l.setTime(limitDate);
				l.set(l.get(Calendar.YEAR), l.get(Calendar.MONTH), l.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				switch(limitResults){
					case 30: 
						l.add(Calendar.MONTH, -1);
						break;
					case 90:
						l.add(Calendar.MONTH, -3);
						break;
					case 180:
						l.add(Calendar.MONTH, -6);
						break;
					case 365:
						l.add(Calendar.YEAR, -1);
						break;
					default:
						l.add(Calendar.DAY_OF_MONTH, (0 - limitResults));
						break;
				}
				dateQuery.append("(topicId IN (SELECT topic.topicId FROM ForumPost WHERE lastUpdate BETWEEN '");
				dateQuery.append(l.get(Calendar.YEAR) + "/" + (l.get(Calendar.MONTH) + 1) + "/" + l.get(Calendar.DAY_OF_MONTH));
				dateQuery.append("' AND '");
				l.setTime(limitDate);
				dateQuery.append(l.get(Calendar.YEAR) + "/" + (l.get(Calendar.MONTH) + 1) + "/" + l.get(Calendar.DAY_OF_MONTH));
				dateQuery.append("' )))");
				if(jpaQuery.length() > 22){
					jpaQuery.append(" AND ");
				}
				jpaQuery.append(dateQuery);
			}
			
			//Logical Delete
			if(jpaQuery.length() > 22){
				jpaQuery.append(" AND ");
			}
			jpaQuery.append("logicalDelete = false");
			
			return jpaQuery.toString();
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Query toLuceneQuery() {
		BooleanQuery luceneQuery = new BooleanQuery();

		return luceneQuery;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString(){
		StringBuilder stringBuilder = new StringBuilder(0);
		
		if(words != null && words.size() > 0){
			stringBuilder.append("Words: ");
			for(int i = 0; i < words.size(); i++){
				stringBuilder.append(words.get(i));
				if(i < words.size() - 1){
					stringBuilder.append(" AND ");
				}
			}
		}
		
		if(stringBuilder.length() > 0) {
			stringBuilder.append(" - ");
		}
		
		if(author != null && author.length() > 0){
			stringBuilder.append("Author: ");
			stringBuilder.append(author);
		}
		
		if(author != null && author.length() > 0) {
			stringBuilder.append(" - ");
		}
		
		if(forumsId != null && forumsId.size() > 0){
			StringBuilder forums = new StringBuilder(0);
			stringBuilder.append(" in Forum: ");
			for(int i : forumsId){
				if(forums.length() > 0) {
					forums.append(',');
				}

				switch(i){
					case 5: 
						forums.append(" Documents");
						break;
					case 6: 
						forums.append(" People");
						break;
					case 7:
						forums.append(" Places");
						break;
					case 8:
						forums.append(" Volumes");
						break;
					case 9:
						forums.append(" General Questions");
						break;
					case 10:
						forums.append(" Paleography");
						break;
					case 11:
						forums.append(" How to use BIA");
						break;
					case 12:
						forums.append(" Problems with BIA");
						break;
					default:
						break;
				}
			}
			stringBuilder.append(forums);
		}
		
		return stringBuilder.toString();
	}
}

