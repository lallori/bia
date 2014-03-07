/*
 * DOMNodeFinder.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class DOMNodeFinder implements DOMWalkCallback {
	
	private boolean byClass;
	private String selector;
	private List<Node> nodes;
	
	private static final Pattern pattern = Pattern.compile("\\s+");
	
	public DOMNodeFinder(Node root, String selector, boolean byClass) {
		this.selector = selector;
		this.byClass = byClass;
		nodes = new ArrayList<Node>();
		new DOMWalker(root).walk(this);
	}
	
	public List<Node> getNodes() {
		return nodes;
	}

	@Override
	public boolean walkElement(Node n, SelectionPath path) {
		NamedNodeMap attributes = n.getAttributes();
		if (attributes.getLength() > 0) {
			Node namedItem = attributes.getNamedItem(byClass ? "class" : "id");
			if (namedItem != null && containsSelector(namedItem.getNodeValue().replaceAll(","," "))) {
				nodes.add(n);
			}
		}
		return true;
	}

	@Override
	public void walkTextNode(Text t, SelectionPath path) {
		// nop
	}

	@Override
	public void walkChar(Text parent, char c, SelectionPath path) {
		// nop
	}
	
	private boolean containsSelector(String attributeValue) {
		if (byClass) {
			String [] attributes = pattern.split(attributeValue);
			for(String attribute : attributes) {
				if (selector.equals(attribute)) {
					return true;
				}
			}
			return false;
		}
		return selector.equals(attributeValue);
	}

}
