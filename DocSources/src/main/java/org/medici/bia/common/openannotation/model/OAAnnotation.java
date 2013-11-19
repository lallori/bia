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
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OAAnnotation<P,B,T> extends OpenAnnotationElement {
	
	@OASerializableField(value = "@context")
	private String context;
	
	@OASerializableField(valueFor = OAConstants.OA_ANNOTATED_AT)
	private Date annotatedAt;
	
	@OASerializableField(valueFor = OAConstants.OA_ANNOTATED_BY)
	private OpenAnnotationObject<P> annotatedBy;
	
	@OASerializableField(value = "motivatedBy", valueFor = OAConstants.OA_MOTIVATED_BY)
	private Set<String> motivations;
	
	@OASerializableField(value = "hasBody", valueFor = OAConstants.OA_HAS_BODY)
	private OpenAnnotationObject<B> body;
	
	@OASerializableField(value = "hasTarget", valueFor = OAConstants.OA_HAS_TARGET)
	private OpenAnnotationObject<T> target;
	
	public OAAnnotation() {
		this.addType(OAConstants.OA_ANNOTATION);
		this.setContext(OAConstants.OA_CONTEXT);
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public Date getAnnotatedAt() {
		return annotatedAt;
	}

	public void setAnnotatedAt(Date annotatedAt) {
		this.annotatedAt = annotatedAt;
	}

	public OpenAnnotationObject<P> getAnnotatedBy() {
		return annotatedBy;
	}
	
	public void setAnnotatedBy(OpenAnnotationObject<P> annotatedBy) {
		this.annotatedBy = annotatedBy;
	}
	
	public Set<String> getMotivations() {
		return motivations;
	}
	
	public void addMotivation(String motivation) {
		if (motivations == null) {
			motivations = new HashSet<String>();
		}
		motivations.add(motivation);
	}
	
	public boolean removeMotivation(String motivation) {
		return motivations.remove(motivation);
	}

	public OpenAnnotationObject<B> getBody() {
		return body;
	}

	public void setBody(OpenAnnotationObject<B> body) {
		this.body = body;
	}

	public OpenAnnotationObject<T> getTarget() {
		return target;
	}

	public void setTarget(OpenAnnotationObject<T> target) {
		this.target = target;
	}

}
