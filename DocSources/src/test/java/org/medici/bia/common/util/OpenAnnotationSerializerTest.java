/*
 * OpenAnnotationSerializerTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.medici.bia.common.openannotation.OAConstants;
import org.medici.bia.common.openannotation.OAException;
import org.medici.bia.common.openannotation.OASerializable;
import org.medici.bia.common.openannotation.model.OAAnnotation;
import org.medici.bia.common.openannotation.model.OAPerson;
import org.medici.bia.common.openannotation.model.OpenAnnotationObject;
import org.medici.bia.common.openannotation.model.Text;

/**
 * This class tests open annotation serializer.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OpenAnnotationSerializerTest {
	
	private OpenAnnotationSerializer serializer;
	
	@Before
	public void initSerializer() {
		serializer = new OpenAnnotationSerializer();
	}
	
	@Test
	public void nullSerialization() {
		try {
			OASerializable ser = null;
			serializer.init(ser);
			fail("Null data should not be serialized.");
		} catch (OAException e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void serializeString() throws OAException {
		OpenAnnotationObject<String> serializable = new OpenAnnotationObject<String>("prova");
		printToConsole(serializable);
		String serialization = serializer.init(serializable).serializeAll();
		assertEquals("\"prova\"", serialization);
	}
	
	@Test
	public void serializeListString() throws OAException {
		List<String> strings = new ArrayList<String>();
		strings.add("prova 1");
		strings.add("prova 2");
		strings.add("prova 3");
		OpenAnnotationObject<List<String>> serializable = new OpenAnnotationObject<List<String>>(strings);
		printToConsole(serializable);
		String serialization = serializer.init(serializable).serializeAll();
		assertEquals("[ \"prova 1\", \"prova 2\", \"prova 3\" ]", serialization);
	}
	
	@Test
	public void serializeListOfOneString() throws OAException {
		List<String> strings = new ArrayList<String>();
		strings.add("prova 1");
		OpenAnnotationObject<List<String>> serializable = new OpenAnnotationObject<List<String>>(strings);
		printToConsole(serializable);
		String serialization = serializer.init(serializable).serializeAll();
		assertEquals("\"prova 1\"", serialization);
	}
	
	@Test
	public void serializeDate() throws OAException {
		Date date = new Date(1384253490000L);
		String serialization = serializer.init(new OpenAnnotationObject<Date>(date)).serializeAll();
		assertEquals("\"2013-11-12T11:51:30\"", serialization);
	}
	
	@Test
	public void serializeOAText() throws OAException {
		Text oaText = new Text("text chars");
		oaText.setId("1234");
		oaText.setFormat("text/plain");
		printToConsole(oaText);
		String serialization = serializer.init(oaText).serializeAll();
		assertTrue(serialization.contains("\"@id\" : \"1234\","));
		assertTrue(serialization.contains("\"@type\" : [ \"" + OAConstants.CNT_CONTENT_AS_TEXT + "\", \"" + OAConstants.DCTYPES_TYPE_TEXT + "\" ]"));
		assertTrue(serialization.contains("\"chars\" : \"text chars\""));
		assertTrue(serialization.contains("\"format\" : \"text/plain\""));
	}
	
	@Test
	public void serializeOAAnnotation() throws OAException {
		OAAnnotation<OAPerson, Text, String> annotation = new OAAnnotation<OAPerson, Text, String>();
		annotation.setId("annotation1");
		annotation.setAnnotatedAt(new Date(1384253490000L));
		annotation.addMotivation(OAConstants.OA_MOTIVATED_BY_COMMENTING);
		annotation.addMotivation(OAConstants.OA_MOTIVATED_BY_QUESTIONING);
		annotation.setAnnotatedBy(new OpenAnnotationObject<OAPerson>(new OAPerson("Pippo","Pluto")));
		annotation.getAnnotatedBy().getAnnotationObject().setId("pippoId");
		annotation.setBody(new OpenAnnotationObject<Text>(new Text()));
		annotation.getBody().getAnnotationObject().setId("urn-xxx-yyy");
		annotation.getBody().getAnnotationObject().setChars("text chars");
		annotation.getBody().getAnnotationObject().setFormat(OAConstants.PLAIN_TEXT);
		annotation.setTarget(new OpenAnnotationObject<String>("http://bia.medici.org/imagesrv.do?imagId=image1.jpg&x=0&y=0&w=100&h=100"));
		printToConsole(annotation);
		String serialization = serializer.init(annotation).serializeAll();
		assertTrue(serialization.contains("\"@id\" : \"annotation1\""));
		assertTrue(serialization.contains("\"@type\" : \"oa:Annotation\""));
		assertTrue(serialization.contains("\"@context\" : \"http://www.w3.org/ns/oa-context-20130208.json\""));
		assertTrue(serialization.contains("\"annotatedAt\" : \"2013-11-12T11:51:30\""));
		assertTrue(serialization.contains("\"annotatedBy\" : { \"@id\" : \"pippoId\", \"@type\" : \"foaf:Person\", \"name\" : \"Pippo Pluto\", \"foaf:firstName\" : \"Pippo\", \"foaf:surname\" : \"Pluto\" }"));
		assertTrue(serialization.contains("\"motivatedBy\" : [ \"oa:questioning\", \"oa:commenting\" ]"));
		assertTrue(serialization.contains("\"hasBody\" : { \"@id\" : \"urn-xxx-yyy\", \"@type\" : [ \"cnt:ContentAsText\", \"dctypes:Text\" ], \"chars\" : \"text chars\", \"format\" : \"text/plain\" }"));
		assertTrue(serialization.contains("\"hasTarget\" : \"http://bia.medici.org/imagesrv.do?imagId=image1.jpg&x=0&y=0&w=100&h=100\""));
	}
	
	@Test
	public void serializeCollectionOAAnnotation() throws OAException {
		OAAnnotation<OAPerson, Text, String> annotation = new OAAnnotation<OAPerson, Text, String>();
		annotation.setId("annotation1");
		annotation.setAnnotatedAt(new Date(1384253490000L));
		annotation.addMotivation(OAConstants.OA_MOTIVATED_BY_COMMENTING);
		annotation.addMotivation(OAConstants.OA_MOTIVATED_BY_QUESTIONING);
		annotation.setAnnotatedBy(new OpenAnnotationObject<OAPerson>(new OAPerson("Pippo","Pluto")));
		annotation.getAnnotatedBy().getAnnotationObject().setId("pippoId");
		annotation.setBody(new OpenAnnotationObject<Text>(new Text()));
		annotation.getBody().getAnnotationObject().setId("urn-xxx-yyy");
		annotation.getBody().getAnnotationObject().setChars("text chars");
		annotation.getBody().getAnnotationObject().setFormat(OAConstants.PLAIN_TEXT);
		annotation.setTarget(new OpenAnnotationObject<String>("http://bia.medici.org/imagesrv.do?imagId=image1.jpg&x=0&y=0&w=100&h=100"));
		
		List<OASerializable> coll = new ArrayList<OASerializable>();
		coll.add(annotation);
		coll.add(annotation);
		coll.add(annotation);
		
		printToConsole(coll);
		String serialization = serializer.init(coll).serializeAll();
		
		int endFirst = serialization.indexOf("imagId=image1.jpg&x=0&y=0&w=100&h=100\"") + 40;
		int endSecond = serialization.indexOf("imagId=image1.jpg&x=0&y=0&w=100&h=100\"", endFirst) + 40;
		String first = serialization.substring(0, endFirst);
		String second = serialization.substring(endFirst, endSecond);
		String third = serialization.substring(endSecond);
		
		assertTrue(serialization.startsWith("["));
		assertTrue(serialization.endsWith("]"));
		
		assertTrue(first.contains("\"@id\" : \"annotation1\""));
		assertTrue(first.contains("\"@type\" : \"oa:Annotation\""));
		assertTrue(first.contains("\"@context\" : \"http://www.w3.org/ns/oa-context-20130208.json\""));
		assertTrue(first.contains("\"annotatedAt\" : \"2013-11-12T11:51:30\""));
		assertTrue(first.contains("\"annotatedBy\" : { \"@id\" : \"pippoId\", \"@type\" : \"foaf:Person\", \"name\" : \"Pippo Pluto\", \"foaf:firstName\" : \"Pippo\", \"foaf:surname\" : \"Pluto\" }"));
		assertTrue(first.contains("\"motivatedBy\" : [ \"oa:questioning\", \"oa:commenting\" ]"));
		assertTrue(first.contains("\"hasBody\" : { \"@id\" : \"urn-xxx-yyy\", \"@type\" : [ \"cnt:ContentAsText\", \"dctypes:Text\" ], \"chars\" : \"text chars\", \"format\" : \"text/plain\" }"));
		assertTrue(first.contains("\"hasTarget\" : \"http://bia.medici.org/imagesrv.do?imagId=image1.jpg&x=0&y=0&w=100&h=100\""));
		
		assertTrue(second.contains("\"@id\" : \"annotation1\""));
		assertTrue(second.contains("\"@type\" : \"oa:Annotation\""));
		assertTrue(second.contains("\"@context\" : \"http://www.w3.org/ns/oa-context-20130208.json\""));
		assertTrue(second.contains("\"annotatedAt\" : \"2013-11-12T11:51:30\""));
		assertTrue(second.contains("\"annotatedBy\" : { \"@id\" : \"pippoId\", \"@type\" : \"foaf:Person\", \"name\" : \"Pippo Pluto\", \"foaf:firstName\" : \"Pippo\", \"foaf:surname\" : \"Pluto\" }"));
		assertTrue(second.contains("\"motivatedBy\" : [ \"oa:questioning\", \"oa:commenting\" ]"));
		assertTrue(second.contains("\"hasBody\" : { \"@id\" : \"urn-xxx-yyy\", \"@type\" : [ \"cnt:ContentAsText\", \"dctypes:Text\" ], \"chars\" : \"text chars\", \"format\" : \"text/plain\" }"));
		assertTrue(second.contains("\"hasTarget\" : \"http://bia.medici.org/imagesrv.do?imagId=image1.jpg&x=0&y=0&w=100&h=100\""));
		
		assertTrue(third.contains("\"@id\" : \"annotation1\""));
		assertTrue(third.contains("\"@type\" : \"oa:Annotation\""));
		assertTrue(third.contains("\"@context\" : \"http://www.w3.org/ns/oa-context-20130208.json\""));
		assertTrue(third.contains("\"annotatedAt\" : \"2013-11-12T11:51:30\""));
		assertTrue(third.contains("\"annotatedBy\" : { \"@id\" : \"pippoId\", \"@type\" : \"foaf:Person\", \"name\" : \"Pippo Pluto\", \"foaf:firstName\" : \"Pippo\", \"foaf:surname\" : \"Pluto\" }"));
		assertTrue(third.contains("\"motivatedBy\" : [ \"oa:questioning\", \"oa:commenting\" ]"));
		assertTrue(third.contains("\"hasBody\" : { \"@id\" : \"urn-xxx-yyy\", \"@type\" : [ \"cnt:ContentAsText\", \"dctypes:Text\" ], \"chars\" : \"text chars\", \"format\" : \"text/plain\" }"));
		assertTrue(third.contains("\"hasTarget\" : \"http://bia.medici.org/imagesrv.do?imagId=image1.jpg&x=0&y=0&w=100&h=100\""));
	}
	
	@Test
	public void serializePieceToPiece() throws OAException {
		OAAnnotation<OAPerson, Text, String> annotation1 = new OAAnnotation<OAPerson, Text, String>();
		annotation1.setId("annotation1");
		annotation1.setAnnotatedAt(new Date(1384253490000L));
		annotation1.addMotivation(OAConstants.OA_MOTIVATED_BY_COMMENTING);
		annotation1.addMotivation(OAConstants.OA_MOTIVATED_BY_QUESTIONING);
		annotation1.setAnnotatedBy(new OpenAnnotationObject<OAPerson>(new OAPerson("Pippo","Pluto")));
		
		OAAnnotation<OAPerson, Text, String> annotation2 = new OAAnnotation<OAPerson, Text, String>();
		annotation2.setId("annotation2");
		annotation2.setAnnotatedAt(new Date(1384253490000L));
		annotation2.addMotivation(OAConstants.OA_MOTIVATED_BY_LINKING);
		annotation2.addMotivation(OAConstants.OA_MOTIVATED_BY_IDENTIFYING);
		annotation2.setAnnotatedBy(new OpenAnnotationObject<OAPerson>(new OAPerson("Tizio","Caio")));
		
		OAAnnotation<OAPerson, Text, String> annotation3 = new OAAnnotation<OAPerson, Text, String>();
		annotation3.setId("annotation3");
		annotation3.setAnnotatedAt(new Date(1384253490000L));
		annotation3.addMotivation(OAConstants.OA_MOTIVATED_BY_DESCRIBING);
		annotation3.setAnnotatedBy(new OpenAnnotationObject<OAPerson>(new OAPerson("Gino","Pino")));
		
		List<OASerializable> coll = new ArrayList<OASerializable>();
		coll.add(annotation1);
		coll.add(annotation2);
		coll.add(annotation3);
		
		serializer.init(coll);
		assertEquals("[ ", serializer.start());
		assertTrue(serializer.hasNext());
		
		String next = serializer.serializeNext();
		assertTrue(next.contains("\"@id\" : \"annotation1\""));
		assertFalse(next.contains("\"@id\" : \"annotation2\""));
		assertFalse(next.contains("\"@id\" : \"annotation3\""));
		assertTrue(serializer.hasNext());
		
		next = serializer.serializeNext();
		assertFalse(next.contains("\"@id\" : \"annotation1\""));
		assertTrue(next.contains("\"@id\" : \"annotation2\""));
		assertFalse(next.contains("\"@id\" : \"annotation3\""));
		assertTrue(serializer.hasNext());
		
		next = serializer.serializeNext();
		assertFalse(next.contains("\"@id\" : \"annotation1\""));
		assertFalse(next.contains("\"@id\" : \"annotation2\""));
		assertTrue(next.contains("\"@id\" : \"annotation3\""));
		assertFalse(serializer.hasNext());
		
		assertEquals("]", serializer.stop());
	}
	
	private void printToConsole(OASerializable serializable) throws OAException {
		boolean indent = serializer.isIndent();
		serializer.setIndent(true);
		String clazz = className(serializable);
		if (serializable instanceof OpenAnnotationObject<?>) {
			clazz = className(((OpenAnnotationObject<?>) serializable).getAnnotationObject());
		}
		System.out.println("<!-- OASerialization of " + clazz + " element -->");
		System.out.println(serializer.init(serializable).serializeAll());
		System.out.println();
		serializer.setIndent(indent);
		
	}
	
	private void printToConsole(Collection<OASerializable> serializable) throws OAException {
		boolean indent = serializer.isIndent();
		serializer.setIndent(true);
		String clazz = className(serializable.iterator().next());
		System.out.println("<!-- OASerialization of List<" + clazz + "> element -->");
		System.out.println(serializer.init(serializable).serializeAll());
		System.out.println();
		serializer.setIndent(indent);
	}
	
	private <T> String className(T object) {
		if (object instanceof Collection && ((Collection<?>)object).size() > 0) {
			return object.getClass().getSimpleName() + "<" + className(((Collection<?>)object).iterator().next()) + ">";
		}
		return object.getClass().getSimpleName();
	}

}
