/*
 * CoursePostExt.java
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
package org.medici.bia.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Course Post entity.<br/>
 * This is an extension of the {@link ForumPost} managed for courses.
 * 
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Entity
@Table ( name = "\"tblCoursePostExt\"" ) 
public class CoursePostExt implements Serializable {
	
	private static final long serialVersionUID = 5636018718658528396L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "postExtId")
	private Integer postExtId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId", nullable = false)
	private ForumPost post;
	@Column(name = "volNum", nullable = true)
	private Integer volNum;
	@Column(name = "volLetExt", nullable = true)
	private String volLetExt;
	@Column(name = "insertNum", nullable = true)
	private String insertNum;
	@Column(name = "insertLet", nullable = true)
	private String insertLet;
	@Column(name = "folioNum", nullable = true)
	private Integer folioNum;
	@Column(name = "folioMod", nullable = true)
	private String folioMod;
	@Enumerated(EnumType.STRING)
	@Column (name = "folioRV", length = 1, nullable = true)
	private RectoVerso folioRV;
	@Column(name = "transcription", nullable = true, length=50000)
	private String transcription;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "checkPointId", nullable = true)
	private CourseCheckPoint relatedCheckPoint;
	/*
	   When it will be possible to create a course transcription with
	   external images we can use the 'imageUrl' field
	@Column(name = "imageUrl", nullable=true)
	private String imageUrl;
	*/
	
	/* Getters and setters */
	
	public Integer getPostExtId() {
		return postExtId;
	}

	public void setPostExtId(Integer postExtId) {
		this.postExtId = postExtId;
	}

	public ForumPost getPost() {
		return post;
	}

	public void setPost(ForumPost post) {
		this.post = post;
	}

	public Integer getVolNum() {
		return volNum;
	}

	public void setVolNum(Integer volNum) {
		this.volNum = volNum;
	}

	public String getVolLetExt() {
		return volLetExt;
	}

	public void setVolLetExt(String volLetExt) {
		this.volLetExt = volLetExt;
	}

	public String getInsertNum() {
		return insertNum;
	}

	public void setInsertNum(String insertNum) {
		this.insertNum = insertNum;
	}

	public String getInsertLet() {
		return insertLet;
	}

	public void setInsertLet(String insertLet) {
		this.insertLet = insertLet;
	}

	public Integer getFolioNum() {
		return folioNum;
	}

	public void setFolioNum(Integer folioNum) {
		this.folioNum = folioNum;
	}

	public String getFolioMod() {
		return folioMod;
	}

	public void setFolioMod(String folioMod) {
		this.folioMod = folioMod;
	}

	public RectoVerso getFolioRV() {
		return folioRV;
	}

	public void setFolioRV(RectoVerso folioRV) {
		this.folioRV = folioRV;
	}

	public String getTranscription() {
		return transcription;
	}

	public void setTranscription(String transcription) {
		this.transcription = transcription;
	}
	
	public CourseCheckPoint getRelatedCheckPoint() {
		return relatedCheckPoint;
	}

	public void setRelatedCheckPoint(CourseCheckPoint relatedCheckPoint) {
		this.relatedCheckPoint = relatedCheckPoint;
	}
	
	/* Constructors */
	
	public CoursePostExt() {
		super();
	}
	
	public CoursePostExt(ForumPost post) {
		super();
		setPost(post);
	}
	
	/* other methods */
	
	public String getFolioFragment() {
		return folioNum != null ? "" + folioNum + (folioMod != null ? " " + folioMod : "") + (folioRV != null ? " " + folioRV : "") : null;
	}
	
	public String getInsertFragment() {
		return insertNum != null && !"".equals(insertNum.trim()) ? insertNum + (insertLet != null ? " " + insertLet.trim() : "") : null; 
	}
	
	public String getVolumeFragment() {
		return volNum != null ? "" + volNum + (volLetExt != null ? " " + volLetExt : "") : null;
	}
	
	public static enum RectoVerso {
		R("R"),	// Recto 
		V("V")	// Verso
		;
		
		private final String code;

	    private RectoVerso(String code) {
	    	this.code = code;
	    }

	    @Override
	    public String toString(){
	        return code;
	    }
	    
	    public static RectoVerso find(String code) {
	    	if ("R".equalsIgnoreCase(code)) {
	    		return R;
	    	}
	    	if ("V".equalsIgnoreCase(code)) {
	    		return V;
	    	}
	    	return null;
	    }
	}

}
