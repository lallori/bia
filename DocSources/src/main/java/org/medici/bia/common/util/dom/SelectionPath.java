/*
 * SelectionPath.java
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

import org.w3c.dom.Node;

/**
 * A specific location of an element of a xml content.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 * 
 */
public class SelectionPath {
	private static final char PATH_SEPARATOR = '/';

	private SelectionPath parentPath;
	private int lastPathFragment;

	private static final SelectionPath ROOT = new SelectionPath();

	private SelectionPath() {
		parentPath = null;
		lastPathFragment = -1;
	}

	private SelectionPath(SelectionPath parentPath, int lastPathFragment) {
		this.parentPath = parentPath;
		this.lastPathFragment = lastPathFragment;
	}

	public static SelectionPath getRoot() {
		return ROOT;
	}

	public static SelectionPath fromString(String path) {
		int index = path.lastIndexOf(PATH_SEPARATOR);
		if (index < 0) {
			throw new IllegalArgumentException("separator not found");
		}
		if (path.length() > 1 && index == path.length() - 1) {
			throw new IllegalArgumentException(
					"separator in last position is not allowed");
		}

		if (index == 0) {
			if (path.length() == 1)
				return ROOT;
			else
				return new SelectionPath(ROOT, convert(path.substring(1)));
		}

		return new SelectionPath(SelectionPath.fromString(path.substring(0,
				index)), convert(path.substring(index + 1)));
	}

	public static SelectionPath fromNode(Node node) {
		Node parentNode = node.getParentNode();
		if (parentNode == null)
			return null;

		SelectionPath parentPath = fromNode(parentNode);
		if (parentPath == null)
			return SelectionPath.getRoot();
		int index = findIndex(parentNode, node);
		if (index < 0)
			throw new IllegalStateException(
					"node selection fragment cannot be negative");
		return parentPath.append(index);
	}

	private static int findIndex(Node parent, Node node) {
		for (int i = 0, size = parent.getChildNodes().getLength(); i < size; i++)
			if (parent.getChildNodes().item(i) == node)
				return i;
		return -1;
	}

	private static int convert(String str) throws IllegalArgumentException {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(str + " is not an integer value");
		}
	}

	public SelectionPath getParentPath() {
		return parentPath;
	}

	public Integer getLastPathFragment() {
		return lastPathFragment;
	}

	public SelectionPath append(int fragment) {
		return new SelectionPath(this, fragment);
	}

	public boolean isRoot() {
		return parentPath == null;
	}

	public int getDepth() {
		if (isRoot())
			return 0;
		return parentPath.getDepth() + 1;
	}

	@Override
	public String toString() {
		if (isRoot())
			return Character.toString(PATH_SEPARATOR);
		if (parentPath.isRoot())
			return parentPath.toString() + lastPathFragment;
		return parentPath.toString() + PATH_SEPARATOR + lastPathFragment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + lastPathFragment;
		result = prime * result
				+ ((parentPath == null) ? 0 : parentPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SelectionPath))
			return false;
		SelectionPath other = (SelectionPath) obj;
		if (lastPathFragment != other.getLastPathFragment())
			return false;
		if (parentPath == null) {
			if (other.getParentPath() != null)
				return false;
		} else if (!parentPath.equals(other.getParentPath()))
			return false;
		return true;
	}

}
