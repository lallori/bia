/*
 * JAXBTest.java
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

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.Test;
import org.medici.bia.common.europeana.model.LanguageResource;
import org.medici.bia.common.europeana.model.LiteralOrReference;
import org.medici.bia.common.europeana.model.ProvidedCHO;
import org.medici.bia.common.europeana.model.ValuableResource;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class EuropeanaResourceMarshalTest {
	
	@Test
	public void testValuableResourceMarshal() throws Exception {
		ValuableResource value = new ValuableResource("Popeye");
		String output = marshal(value);
		assertEquals("<ValuableResource>Popeye</ValuableResource>", output);
	}
	
	@Test
	public void testLiteralOrReferenceMarshal() throws Exception {
		LiteralOrReference litOrRef = new LiteralOrReference();
		litOrRef.setUrl("http://example.com");
		String output = marshal(litOrRef);
		assertEquals("<LiteralOrReference rdf:resource=\"http://example.com\"/>", output);
		litOrRef = new LiteralOrReference();
		litOrRef.setValue("value");
		output = marshal(litOrRef);
		assertEquals("<LiteralOrReference>value</LiteralOrReference>", output);
	}
	
	@Test
	public void testLanguageResourceMarshal() throws Exception {
		LanguageResource lang = new LanguageResource("en");
		lang.setValue("Popeye");
		String output = marshal(lang);
		assertEquals("<LanguageResource xml:lang=\"en\">Popeye</LanguageResource>",output);
	}
	
	@Test
	public void testCHOMarshal() throws Exception {
		ProvidedCHO cho = new ProvidedCHO();
		cho.setReference("http://refExaple.com");
		cho.setType("TEXT");
		String output = marshal(cho);
		assertEquals("<edm:ProvidedCHO rdf:about=\"http://refExaple.com\"><edm:type>TEXT</edm:type></edm:ProvidedCHO>", output);
	}
	
	private String marshal(Object obj) throws Exception {
		JAXBContext context = JAXBContext.newInstance(obj.getClass());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		//m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		final StringWriter w = new StringWriter();
		m.marshal(obj, w);
		System.out.println(w.toString());
		return w.toString();
	}

}
