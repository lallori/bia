/*
 * FileWriterHelper.java
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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Helper in writing files operations.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class FileWriterHelper {
	
	private BufferedWriter writer;
	
	/**
	 * Initializes the writer of a file.
	 * 
	 * @param fileName the file name (including file path)
	 * @param append true if the writer has to be opened in append mode
	 * @throws IOException
	 */
	public void init(String fileName, boolean append) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName, append);
		writer = new BufferedWriter(fileWriter);
	}
	
	/**
	 * Closes the writer.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (!isInitialized()) {
			throw new IllegalStateException(this.getClass() + " IS NOT INITIALIZED!!!");
		}
		writer.close();
	}
	
	/**
	 * Returns true if the writer is initialized.
	 * 
	 * @return true if the writer is initialized, false otherwise
	 */
	public boolean isInitialized() {
		return writer != null;
	}
	
	/**
	 * Writes the provided string in the file.
	 * 
	 * @param toWrite the string to write
	 * @param flush true for flush mode, false otherwise
	 * @throws IOException
	 */
	public void write(String toWrite, boolean flush) throws IOException {
		if (!isInitialized()) {
			throw new IllegalStateException(this.getClass() + " IS NOT INITIALIZED!!!");
		}
		writer.append(toWrite);
		if (flush) {
			writer.flush();
		}
	}

}
