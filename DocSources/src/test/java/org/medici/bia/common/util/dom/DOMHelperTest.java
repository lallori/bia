/*
 * DOMHelperTest.java
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
package org.medici.bia.common.util.dom;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.medici.bia.exception.DOMHelperException;

/**
 * This class implements test case for {@link DOMHelper} class.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class DOMHelperTest {
	
	@Test(expected = DOMHelperException.class)
	public void testBadHtml() {
		DOMHelper domHelper = new DOMHelper(
				"<html><head></head><xxx><p>abcd<b>efg</b>hij</p></xxx></html>");
		domHelper.getAllPlainTextFromBody(true);
	}

	@Test
	public void testAllText() {
		DOMHelper domHelper = new DOMHelper(
				"<html><head></head><body><p>abcd<b>efg</b>hij</p></body></html>");
		String planText = domHelper.getAllPlainText(true);
		assertEquals("abcdefghij\n", planText);

		domHelper
				.setXml("<html><head></head><body><p id='p_id'>abcd<b>ef<img src='a'/>gh</b>ij</p></body></html>");
		planText = domHelper.getAllPlainText(true);
		assertEquals("abcdef\nghij\n", planText);

		planText = domHelper.getAllPlainTextFromBody(true);
		assertEquals("abcdef\nghij\n", planText);

		domHelper
				.setXml("<html><head></head><BODY><p>abcd<b>efg</b>hij</p><p>klmno<br/>pqrst<p>uvwx</p>yz</p></BODY></html>");
		planText = domHelper.getAllPlainTextFromBody(true);
		assertEquals("abcdefghij\nklmno\npqrstuvwx\nyz\n", planText);
	}
	
	@Test
	public void testSimpleText() {
		DOMHelper domHelper = new DOMHelper("xxx<br/>yyy", false, true);
		String plainText = domHelper.getAllPlainText(true);
		assertEquals("xxx\nyyy", plainText);
	}
}
