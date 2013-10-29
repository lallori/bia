/*
 * DOMTextExtractor.java
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

import java.util.Stack;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

/**
 * Plain text extractor of a DOM.<br/>
 * This extractor writes carriage returns if the DOM portion to extract is
 * between returning tags (i.e. <i>p</i>, <i>h...</i>, <i>br</i> or <i>img</i>).
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public class DOMTextExtractor implements DOMWalkCallback {

	private static final String SEPARATOR = "\n";

	private Stack<Node> parents;
	private String text;
	private Node lastProcessedNode;
	private SelectionPath from;
	private SelectionPath to;

	private boolean luceneExtraction;

	private boolean activedExtraction;

	/**
	 * This constructor must be initialized with a root node; this the beginning
	 * node of the text extraction process.
	 * 
	 * @param root
	 *            the beginning node
	 * @param from
	 *            the beginning path of the selection; if null the beginning
	 *            selection is the root node
	 * @param to
	 *            the ending path of the selection; if null the ending selection
	 *            is the last node of the DOM
	 * @param luceneExtraction
	 *            if true the Lucene compatibility is enabled
	 */
	public DOMTextExtractor(Node root, SelectionPath from, SelectionPath to,
			boolean luceneExtraction) {
		init(root, from, to, luceneExtraction);
		new DOMWalker(root).walk(this);
	}

	/**
	 * This constructor must be initialized with a root node; this the beginning
	 * node of the text extraction process.
	 * 
	 * @param root
	 *            the beginning node
	 * @param luceneExtraction
	 *            if true the Lucene compatibility is enabled
	 */
	public DOMTextExtractor(Node root, boolean luceneExtraction) {
		this(root, null, null, luceneExtraction);
	}

	/**
	 * This method returns the extracted text
	 * 
	 * @return extracted text
	 */
	public String getText() {
		return text;
	}

	@Override
	public boolean walkElement(Element n, SelectionPath path) {
		boolean matched = updateActiveExtraction(n, path);
		if (parents.size() > 0)
			checkPathAndPutSeparator(path, matched || activedExtraction);
		if (matched && !activedExtraction && DOMHelper.isReturnTag(n)
				&& luceneExtraction) // toNode
			text += SEPARATOR;
		else {
			parents.push(n);
			if (n.isSameNode(lastProcessedNode))
				closeParents(activedExtraction);
		}
		return true;
	}

	private boolean updateActiveExtraction(Node n, SelectionPath path) {
		if (from.equals(path) && from.equals(to)) {
			if (n.hasChildNodes()) {
				to = to.append(n.getChildNodes().getLength() - 1);
				activedExtraction = true;
			}
			return true;
		}
		if (!activedExtraction && from.equals(path)) {
			activedExtraction = true;
			return true;
		}
		if (to != null && to.equals(path)) {
			if (n.hasChildNodes())
				to = to.append(n.getChildNodes().getLength() - 1);
			else
				activedExtraction = false;
			return true;
		}
		return false;
	}

	@Override
	public void walkTextNode(Text t, SelectionPath path) {
		boolean matched = updateActiveExtraction(t, path);
		if (matched)
			if (activedExtraction)
				from = from.append(0);
			else {
				checkPathAndPutSeparator(path, true);
				text += t.getTextContent();
			}
	}

	@Override
	public void walkChar(Text parent, char c, SelectionPath path) {
		boolean matched = updateActiveExtraction(parent, path);
		checkPathAndPutSeparator(path.getParentPath(), matched
				|| activedExtraction);
		if (matched || activedExtraction)
			text += c;
		if (parent.isSameNode(lastProcessedNode)
				&& path.getLastPathFragment() == parent.getTextContent()
						.length() - 1)
			closeParents(activedExtraction);
	}

	private void init(Node root, SelectionPath from, SelectionPath to,
			boolean luceneExtraction) {
		this.luceneExtraction = luceneExtraction;
		if (from != null)
			this.from = from;
		else
			this.from = SelectionPath.getRoot();
		this.to = to;

		parents = new Stack<Node>();
		text = "";
		activedExtraction = false;

		initLastNode(root);
	}

	private void initLastNode(Node node) {
		if (node.hasChildNodes())
			initLastNode(node.getLastChild());
		else
			lastProcessedNode = node;
	}

	private void checkPathAndPutSeparator(SelectionPath path,
			boolean addSeparator) {
		if (path.getDepth() <= parents.size())
			checkStack(path.getDepth(), addSeparator);
	}

	private void closeParents(boolean addSeparator) {
		checkStack(0, addSeparator);
	}

	private void checkStack(int depthLimit, boolean addSeparator) {
		while (parents.size() > depthLimit) {
			Node parent = parents.pop();
			if (DOMHelper.isReturnTag(parent) && addSeparator
					&& luceneExtraction)
				text += SEPARATOR;
		}
	}

}
