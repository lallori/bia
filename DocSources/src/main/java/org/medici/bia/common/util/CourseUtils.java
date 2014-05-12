/*
 * CourseUtils.java
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

import org.medici.bia.domain.CourseTopicOption;
import org.medici.bia.domain.CourseTopicOption.CourseTopicMode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * Static method utilities to manage Teaching jobs.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class CourseUtils {
	
	/**
	 * Returns encoded transcription (it substitutes carriage returns with html break row).<br/>
	 * If null is provided it returns null. 
	 * 
	 * @param decodedTranscription the decoded transcription (with carriage returns)
	 * @return the encoded transcription
	 */
	public static String encodeCourseTranscriptionSafely(String decodedTranscription) {
		return decodedTranscription != null ? decodedTranscription.replaceAll("\r\n", "<br/>") : null;
	}
	
	/**
	 * Returns decoded transcription (it substitutes html break rows with carriage returns).<br/>
	 * if null is provided it returns null.
	 * 
	 * @param encodedTranscription the encoded transcription (with html break rows)
	 * @return the decoded transcription
	 */
	public static String decodeCourseTranscriptionSafely(String encodedTranscription) {
		return encodedTranscription != null ? encodedTranscription.replaceAll("<br/>", "\r\n") : null;
	}
	
	public static String getCourseTranscriptionURL(CourseTopicOption courseTranscriptionOption) {
		if (courseTranscriptionOption == null ||
				CourseTopicMode.D.equals(courseTranscriptionOption.getMode()) ||
				CourseTopicMode.Q.equals(courseTranscriptionOption.getMode())) {
			return null;
		}
		return ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getContextPath()
				+ "/teaching/ShowCourseTranscription.do?topicId=" + courseTranscriptionOption.getCourseTopic().getTopicId()
				+ "&entryId=" + courseTranscriptionOption.getCourseTopic().getDocument().getEntryId()
				+ "&transcriptionMode=" + courseTranscriptionOption.getMode().name()
				+ "&completeDOM=true";
	}
}
