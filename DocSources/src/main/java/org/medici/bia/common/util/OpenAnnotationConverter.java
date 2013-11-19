/*
 * OpenAnnotationConverter.java
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

import java.util.ArrayList;
import java.util.List;

import org.medici.bia.common.openannotation.OAConstants;
import org.medici.bia.common.openannotation.model.OAAnnotation;
import org.medici.bia.common.openannotation.model.OAPerson;
import org.medici.bia.common.openannotation.model.OpenAnnotationObject;
import org.medici.bia.common.openannotation.model.Text;
import org.medici.bia.domain.Annotation;
import org.medici.bia.domain.User;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OpenAnnotationConverter {
	
	private String contextPath;
	
	public OpenAnnotationConverter(String contextPath) {
		this.contextPath = contextPath;
	}
	
	public List<OAAnnotation<OAPerson, Text, String>> convertToOpenAnnotations(List<Annotation> biaAnnotations) {
		List<OAAnnotation<OAPerson, Text, String>> oaAnnotations = new ArrayList<OAAnnotation<OAPerson, Text, String>>();
		for (Annotation biaAnnotation : biaAnnotations) {
			oaAnnotations.add(convertToOpenAnnotation(biaAnnotation));
		}
		return oaAnnotations;
	}
	
	public OAAnnotation<OAPerson, Text, String> convertToOpenAnnotation(Annotation biaAnnotation) {
		OAAnnotation<OAPerson, Text, String> oaAnnotation = new OAAnnotation<OAPerson, Text, String>();
		oaAnnotation.setId(getOpenAnnotationId(biaAnnotation));
		oaAnnotation.setAnnotatedAt(biaAnnotation.getDateCreated());
		setAnnotatedBy(oaAnnotation, biaAnnotation.getUser());
		setMotivations(oaAnnotation, biaAnnotation.getType());
		setTextBody(oaAnnotation, biaAnnotation);
		setUrlTarget(oaAnnotation, getImageUrl(biaAnnotation));
		return oaAnnotation;
	}
	
	private String getOpenAnnotationId(Annotation biaAnnotation) {
		return contextPath + "src/openannotation/ShowAnnotation.do?annotId=" + biaAnnotation.getAnnotationId();
	}
	
	private String getImageUrl(Annotation biaAnnotation) {
		return contextPath + "src/openannotation/imageSrvl.do?"
				+ "imageName=" + biaAnnotation.getImage().getImageName()
				+ "&x=" + biaAnnotation.getX()
				+ "&y=" + biaAnnotation.getY()
				+ "&w=" + biaAnnotation.getWidth()
				+ "&h=" + biaAnnotation.getHeight();
	}
	
	private String getUserUrl(User user) {
		return contextPath + "community/ShowUserProfileCompleteDOM.do?account=" + user.getAccount();
	}
	
	private void setAnnotatedBy(OAAnnotation<OAPerson,?,?> oaAnnotation, User user) {
		OAPerson person = new OAPerson(user.getFirstName(), user.getLastName());
		person.setId(getUserUrl(user));
		oaAnnotation.setAnnotatedBy(new OpenAnnotationObject<OAPerson>(person));
	}
	
	private void setMotivations(OAAnnotation<?,?,?> oaAnnotation, Annotation.Type type) {
		if (Annotation.Type.GENERAL.equals(type)) {
			oaAnnotation.addMotivation(OAConstants.OA_MOTIVATED_BY_COMMENTING);
			oaAnnotation.addMotivation(OAConstants.OA_MOTIVATED_BY_QUESTIONING);
		} else if (Annotation.Type.PALEOGRAPHY.equals(type)) {
			oaAnnotation.addMotivation(OAConstants.OA_MOTIVATED_BY_QUESTIONING);
		}
	}
	
	private void setTextBody(OAAnnotation<?, Text, ?> oaAnnotation, Annotation annotation) {
		Text body = new Text(annotation.getText());
		body.setFormat(OAConstants.PLAIN_TEXT);
		body.setLabel(annotation.getTitle());
		body.setSubject(annotation.getType().toString());
		oaAnnotation.setBody(new OpenAnnotationObject<Text>(body));
	}
	
	private void setUrlTarget(OAAnnotation<?, ?, String> oaAnnotation, String imageUrl) {
		oaAnnotation.setTarget(new OpenAnnotationObject<String>(imageUrl));
	}

}
