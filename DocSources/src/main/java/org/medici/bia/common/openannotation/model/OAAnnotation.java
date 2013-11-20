/*
 * OAAnnotation.java
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
package org.medici.bia.common.openannotation.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.medici.bia.common.openannotation.OAConstants;
import org.medici.bia.common.openannotation.OASerializableField;

/**
 * This class is a model for open annotations.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 * @param <P> the annotation creator type (i.e. {@link OAPerson} or simply a string) 
 * @param <B> the body type
 * @param <T> the target type
 */
public class OAAnnotation<P,B,T> extends OpenAnnotationElement {
	
	/**
	 * The context of the annotation.
	 */
	@OASerializableField(value = "@context")
	private String context;
	
	/**
	 * The date of the annotation.
	 */
	@OASerializableField(valueFor = OAConstants.OA_ANNOTATED_AT)
	private Date annotatedAt;
	
	/**
	 * The person who created the annotation.
	 */
	@OASerializableField(valueFor = OAConstants.OA_ANNOTATED_BY)
	private OpenAnnotationObject<P> annotatedBy;
	
	/**
	 * A set of motivations.
	 */
	@OASerializableField(value = "motivatedBy", valueFor = OAConstants.OA_MOTIVATED_BY)
	private Set<String> motivations;
	
	/**
	 * The annotation body.
	 */
	@OASerializableField(value = "hasBody", valueFor = OAConstants.OA_HAS_BODY)
	private OpenAnnotationObject<B> body;
	
	/**
	 * The annotation target.
	 */
	@OASerializableField(value = "hasTarget", valueFor = OAConstants.OA_HAS_TARGET)
	private OpenAnnotationObject<T> target;
	
	public OAAnnotation() {
		this.addType(OAConstants.OA_ANNOTATION);
		this.setContext(OAConstants.OA_CONTEXT);
	}

	/**
	 * Returns the annotation context.
	 * 
	 * @return the annotation context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Sets the annotation context.
	 * 
	 * @param context the annotation context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * Returns the creation date.
	 * 
	 * @return the creation date
	 */
	public Date getAnnotatedAt() {
		return annotatedAt;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param annotatedAt the creation date to set
	 */
	public void setAnnotatedAt(Date annotatedAt) {
		this.annotatedAt = annotatedAt;
	}

	/**
	 * Returns the annotation creator.
	 * 
	 * @return the annotation creator
	 */
	public OpenAnnotationObject<P> getAnnotatedBy() {
		return annotatedBy;
	}
	
	/**
	 * Sets the annotation creator.
	 * 
	 * @param annotatedBy the annotation creator to set
	 */
	public void setAnnotatedBy(OpenAnnotationObject<P> annotatedBy) {
		this.annotatedBy = annotatedBy;
	}
	
	/**
	 * Returns all the motivations.
	 * 
	 * @return the motivations
	 */
	public Set<String> getMotivations() {
		return motivations;
	}
	
	/**
	 * Adds a motivation.
	 * 
	 * @param motivation the motivation to add
	 */
	public void addMotivation(String motivation) {
		if (motivations == null) {
			motivations = new HashSet<String>();
		}
		motivations.add(motivation);
	}
	
	/**
	 * Removes a motivation.
	 * 
	 * @param motivation the motivation to remove
	 * @return true if the motivation was in the set
	 */
	public boolean removeMotivation(String motivation) {
		return motivations.remove(motivation);
	}

	/**
	 * Returns the annotation body.
	 * 
	 * @return the annotation body
	 */
	public OpenAnnotationObject<B> getBody() {
		return body;
	}

	/**
	 * Sets the annotation body.
	 * 
	 * @param body the annotation body to set 
	 */
	public void setBody(OpenAnnotationObject<B> body) {
		this.body = body;
	}

	/**
	 * Returns the annotation target.
	 * 
	 * @return the annotation target
	 */
	public OpenAnnotationObject<T> getTarget() {
		return target;
	}

	/**
	 * Sets the annotation target.
	 * 
	 * @param target the annotation target to set
	 */
	public void setTarget(OpenAnnotationObject<T> target) {
		this.target = target;
	}

}
