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

import java.util.List;

import org.junit.Test;
import org.medici.bia.exception.DOMHelperException;
import org.w3c.dom.Node;

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
	
	@Test
	public void testGetComments() {
		DOMHelper domHelper = new DOMHelper("<!-- <span class='comment'>COMMENT</span> --><p>first paragraph with comment <!-- inner comment --></p><p>second paragraph</p>", true, true);
		List<String> comments = domHelper.getComments();
		assertEquals(2, comments.size());
		assertEquals(" <span class='comment'>COMMENT</span> ", comments.get(0));
		assertEquals(" inner comment ", comments.get(1));
	}
	
	@Test
	public void testFindNodeByClass() {
		DOMHelper domHelper = new DOMHelper("<div class='foo'><span class='foo2'>text1</span><span id='outer' class='foo2'>text2<div class='foo2'>text3</div></span></div>", true, true);
		List<Node> nodes = domHelper.findNodesByClass("foo2");
		assertEquals(3, nodes.size());
		assertEquals("span", nodes.get(0).getNodeName());
		assertEquals("text1", nodes.get(0).getTextContent());
		assertEquals("span", nodes.get(1).getNodeName());
		assertEquals("text2text3", nodes.get(1).getTextContent());
		assertEquals("div", nodes.get(2).getNodeName());
		assertEquals("text3", nodes.get(2).getTextContent());
		
		nodes = domHelper.findNodesByClass("foo");
		assertEquals(1, nodes.size());
		assertEquals("div", nodes.get(0).getNodeName());
		
		nodes = domHelper.findNodesByClass("foo3");
		assertEquals(0, nodes.size());
		
		domHelper = new DOMHelper("<div class='other  ,  foo , foo2'>bla bla</div><span class='fooooo'>text</span>", true, true);
		nodes = domHelper.findNodesByClass("foo");
		assertEquals(1, nodes.size());
		assertEquals("div", nodes.get(0).getNodeName());
	}
	
	@Test
	public void testFoundNodeById() {
		DOMHelper domHelper = new DOMHelper("<div id='foo1'><div class='foo2'>text1</div><span id='foo2'>text2</span></div>", true, true);
		List<Node> nodes = domHelper.findNodesById("foo1");
		assertEquals(1, nodes.size());
		assertEquals("div", nodes.get(0).getNodeName());
		
		nodes = domHelper.findNodesById("foo2");
		assertEquals(1, nodes.size());
		assertEquals("span", nodes.get(0).getNodeName());
		assertEquals("text2", nodes.get(0).getTextContent());
	}
}
