/*
 * URLTransformerTest.java
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

import org.junit.Assert;
import org.junit.Test;

/**
 * This class implements test case for {@link URLTransformer} class.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class URLTransformerTest {
	
	@Test
	public void decodeNullString() {
		String encoded = null;
		String decoded = URLTransformer.decode(encoded);
		Assert.assertNull(decoded);
	}
	
	@Test
	public void encodeNullString() {
		String decoded = null;
		String encoded = URLTransformer.encode(decoded);
		Assert.assertNull(encoded);
	}
	
	@Test
	public void decodeSingleChar() {
		String encoded = "%A0";
		String decoded = URLTransformer.decode(encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals("\u00A0", decoded);
	}
	
	@Test
	public void encodeSingleChar() {
		String decoded = "\u00A0";
		String encoded = URLTransformer.encode(decoded);
		Assert.assertNotNull(encoded);
		Assert.assertEquals("%A0", encoded);
	}
	
	@Test
	public void decodeSurroundedChar() {
		String encoded = "a%A0a";
		String decoded = URLTransformer.decode(encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals("a\u00A0a", decoded);
	}
	
	@Test
	public void encodeSurroundedChar() {
		String decoded = "a\u00A0a";
		String encoded = URLTransformer.encode(decoded);
		Assert.assertNotNull(encoded);
		Assert.assertEquals("a%A0a", encoded);
	}
	
	@Test
	public void decodeComplex() {
		String encoded = "a%A0a%B0%B1uuuu";
		String decoded = URLTransformer.decode(encoded);
		Assert.assertNotNull(decoded);
		Assert.assertEquals("a\u00A0a\u00B0\u00B1uuuu", decoded);
	}
	
	@Test
	public void encodeComplex() {
		String decoded = "a\u00A0a\u00B0\u00B1uuuu";
		String encoded = URLTransformer.encode(decoded);
		Assert.assertNotNull(encoded);
		Assert.assertEquals("a%A0a%B0%B1uuuu", encoded);
	}
	
}
