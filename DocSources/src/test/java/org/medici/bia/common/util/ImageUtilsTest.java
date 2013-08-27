/*
 * ImageUtilsTest.java
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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ImageUtilsTest {

	@Test
	public void standardImageTest() {
		String imageName = "0594_C_416_R.tif";
		assertEquals(new Integer(416), ImageUtils.extractFolioNumber(imageName));
		assertNull(ImageUtils.extractFolioExtension(imageName));
	}
	
	@Test
	public void extendedFolioImageTest() {
		String imageName = "0586_C_410_BIS_V.tif";
		assertEquals(new Integer(410), ImageUtils.extractFolioNumber(imageName));
		assertEquals("BIS", ImageUtils.extractFolioExtension(imageName));
	}
	
	@Test
	public void anyInsertWithStandardFolioTest() {
		String imageName = "0422_[1]_C_175_R.tif";
		assertEquals(new Integer(175), ImageUtils.extractFolioNumber(imageName));
		assertNull(ImageUtils.extractFolioExtension(imageName));
		
		imageName = "0426_[A-13]_R_179_R.tif";
		assertEquals(new Integer(179), ImageUtils.extractFolioNumber(imageName));
		assertNull(ImageUtils.extractFolioExtension(imageName));
	}
	
	@Test
	public void anyInsertWithExtendedFolioTest() {
		String imageName = "0426_[A]_C_179_TER_R.tif";
		assertEquals(new Integer(179), ImageUtils.extractFolioNumber(imageName));
		assertEquals("TER", ImageUtils.extractFolioExtension(imageName));

		imageName = "0426_[A-13]_C_179_BIS_R.tif";
		assertEquals(new Integer(179), ImageUtils.extractFolioNumber(imageName));
		assertEquals("BIS", ImageUtils.extractFolioExtension(imageName));
	}

}
