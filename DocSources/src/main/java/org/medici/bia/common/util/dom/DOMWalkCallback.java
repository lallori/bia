/*
 * IWalkCallback.java
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
import org.w3c.dom.Text;

/**
 * Callback for DOM navigation with {@link DOMWalker}
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public interface DOMWalkCallback {
	
	/**
	 * This method is called when the DOM walker is exploring a {@link Element}   
	 * 
	 * @param n the explored node
	 * @param path the node path
	 * @return if this method returns false the children of the element will be not explored
	 */
	public boolean walkElement(Element n, SelectionPath path);

	/**
	 * This method is called when the DOM walker is exploring a {@link Text} 
	 * 
	 * @param t the explored element
	 * @param path the element path
	 */
	public void walkTextNode(Text t, SelectionPath path);

	/**
	 * This method is called when the DOM walker is exploring a character
	 * 
	 * @param parent the parent node of the character
	 * @param c the explored character
	 * @param path the path of the character
	 */
	public void walkChar(Text parent, char c, SelectionPath path);

}
