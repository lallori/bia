/*
 * ShowMakeTranscribedModalCommand.java
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
package org.medici.bia.command.annotation;


/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ShowMakeTranscribedModalCommand {

	private Integer annotationId;
	
	private String annotationTitle;
	
	private Boolean export;
	
	private Boolean makeTranscribed;

	/**
	 * @return the annotationId
	 */
	public Integer getAnnotationId() {
		return annotationId;
	}

	/**
	 * @param annotationId the annotationId to set
	 */
	public void setAnnotationId(Integer annotationId) {
		this.annotationId = annotationId;
	}

	/**
	 * @return the annotationTitle
	 */
	public String getAnnotationTitle() {
		return annotationTitle;
	}

	/**
	 * @param annotationTitle the annotationTitle to set
	 */
	public void setAnnotationTitle(String annotationTitle) {
		this.annotationTitle = annotationTitle;
	}

	/**
	 * @return the export
	 */
	public Boolean getExport() {
		return export;
	}

	/**
	 * @param export the export to set
	 */
	public void setExport(Boolean export) {
		this.export = export;
	}

	/**
	 * @return the makeTranscribed
	 */
	public Boolean getMakeTranscribed() {
		return makeTranscribed;
	}

	/**
	 * @param makeTranscribed the makeTranscribed to set
	 */
	public void setMakeTranscribed(Boolean makeTranscribed) {
		this.makeTranscribed = makeTranscribed;
	}
	
}
