/*
 * FileUtils.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Utilities for manages file system files and paths.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class FileUtils {
	
	/**
     * Size of a byte buffer to read/write file
     */
	private static final int BUFFER_SIZE = 4096;
	
	/**
	 * Checks the existence of a path.
	 * 
	 * @param path the path to check
	 * @param createIfNotExist true for creating the path if it does not exist, false otherwise
	 * @return true if path the exists, false otherwise
	 */
	public static boolean checkPath(String path, boolean createIfNotExist) {
		if (path == null || "".equals(path)) {
			throw new IllegalArgumentException("You should provide valid path");
		}
		
		File oaPath = new File(path);
		if (!oaPath.exists() && createIfNotExist) { 
			return oaPath.mkdirs();
		}
		return oaPath.exists();
	}
	
	public static long getContent(File file, OutputStream output) throws FileNotFoundException, IOException {
		if (!file.exists()) {
			throw new FileNotFoundException("File [" + (file != null ? file.getAbsolutePath() : "???") + "] does not exist");
		}
		BufferedReader reader = null;
		try {
			FileInputStream input = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(input));
			byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}
		} catch (Exception e) {
			throw new IOException(e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}
		return file.length();
	}
	
	public static String fileSize(File file) {
		if (file == null || !file.exists() || file.isDirectory()) {
			return null;
		}
		return convertFileSize(file.length(), false);
	}
	
	/**
	 * Renames a file with new file name. If the target file exists it is overridden.
	 * 
	 * @param fileName the file name (including path) to rename
	 * @param targetFileName the target file name (including path) 
	 * @return true if and only if the renaming succeeded
	 * @throws IOException
	 */
	public static boolean renameTo(String fileName, String targetFileName) throws IOException {
		File current = new File(fileName);
		File newF = new File(targetFileName);
		
		if (!current.exists()) {
			throw new IOException("File [" + fileName + "] does not exits");
		}
		
		if (newF.exists()) {
			newF.delete();
		}
		
		return current.renameTo(new File(targetFileName));
	}
	
	/**
	 * Returns true if the file exists.
	 * 
	 * @param fileName the file name (including path) to check
	 * @return true if the file exists
	 * @throws IOException
	 */
	public static boolean exists(String fileName) throws IOException {
		return new File(fileName).exists();
	}
	
	/* Privates */
	
	private static String convertFileSize(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

}
