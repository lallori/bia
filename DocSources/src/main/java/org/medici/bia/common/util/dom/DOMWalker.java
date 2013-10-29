/*
 * DOMWalker.java
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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * This class explores the elements of a DOM.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public class DOMWalker {

	private Node root;

	private DOMWalkCallback callback;
	private SelectionPath selectionPath;

	public DOMWalker(Node root) {
		this.root = root;
	}

	/**
	 * This method starts the DOM exploration
	 * 
	 * @param callback
	 *            the callback called by the DOM exploration events
	 */
	public void walk(DOMWalkCallback callback) {
		this.callback = callback;
		this.selectionPath = SelectionPath.getRoot();
		walk(root);
	}

	private void walk(Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			callback.walkTextNode((Text) node, selectionPath);
			walkTextNode((Text) node);
		} else {
			if (callback.walkElement((Element) node, selectionPath)) {
				walkChildren(node);
			}
		}
	}

	private void walkChildren(Node node) {
		for (int i = 0, size = node.getChildNodes().getLength(); i < size; i++) {
			Node child = node.getChildNodes().item(i);
			selectionPath = selectionPath.append(i);
			walk(child);
			selectionPath = selectionPath.getParentPath();
		}
	}

	private void walkTextNode(Text node) {
		String content = node.getTextContent();
		for (int i = 0, size = content.length(); i < size; i++) {
			callback.walkChar(node, content.charAt(i), selectionPath.append(i));
		}
	}

}
