/*
 * ImageConversionProcessStreamLogger.java
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
package org.medici.bia.common.teaching;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.medici.bia.exception.ApplicationThrowable;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ImageConversionProcessStreamLogger extends Thread {
	
	private InputStream is;
	private Logger logger;
	private LogLevel level;
	
	public enum LogLevel {
		ERROR, INFO;
	}
	
	public ImageConversionProcessStreamLogger(InputStream is, Logger logger, LogLevel level) {
		this.is = is;
		this.logger = logger;
		this.level = level;
	}
	
	@Override
	public void run() {
		InputStreamReader isr = null;
		BufferedReader reader = null;
		try {
			isr = new InputStreamReader(is);
			reader = new BufferedReader(isr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (LogLevel.INFO.equals(level)) {
					logger.info(line);
				} else if (LogLevel.ERROR.equals(level)) {
					logger.error(line);
				}
			}
		} catch (Exception e) {
			throw new ApplicationThrowable(e);
		} finally {
			try {
				isr.close();
			} catch (Exception ex) {
				// NOP
			}
			try {
				reader.close();
			} catch (Exception ex) {
				// NOP
			}
		}
	}

}
