/*
 * ImageConversionInvoker.java
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

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.property.ApplicationPropertyManager;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class ImageConversionInvoker {
	
	public static final int NOT_SUPPORTED_OS = -2;
	public static final int TASK_ERROR = -1;
	
	private String fileName;
	private String fileTitle;
	private Integer imageOrder;
	private Integer storagePath;
	private Logger logger;
	
	public ImageConversionInvoker(String fileName, String fileTitle, Integer imageOrder, Integer staragePath, Logger logger) {
		this.fileName = fileName;
		this.fileTitle = fileTitle;
		this.imageOrder = imageOrder;
		this.storagePath = staragePath;
		this.logger = logger;
	}
	
	public int fire() {
		try {
			Process process = null;
			
			if (SystemUtils.IS_OS_LINUX) {
//				logger.info("IMAGE CONVERSION TASK: Linux Operating System detected...");
//				String[] env = {"PATH=/bin:/usr/bin/"};
//				String scriptCommand = ApplicationPropertyManager.getApplicationProperty("path.tmpdir") + 
//						(ApplicationPropertyManager.getApplicationProperty("path.tmpdir").endsWith("/") ? "upload_images.sh" : "/upload_images.sh");
//				String cmd = scriptCommand + " " + fileName +  " '" + fileTitle + "' " + imageOrder + " " + storagePath;
//				logger.info("IMAGE CONVERSION TASK: launching command [" + cmd + "]");
//				
//				process = rt.exec(cmd, env);
				logger.info("IMAGE CONVERSION TASK: Linux Operating System detected...");
				String[] env = {"PATH=/bin:/usr/bin/"};
				String[] commandArray = { ApplicationPropertyManager.getApplicationProperty("path.tmpdir") +
						//(ApplicationPropertyManager.getApplicationProperty("path.tmpdir").endsWith("/") ? "upload_images.sh" : "/upload_images.sh"), fileName, "'" + fileTitle + "'", "" + imageOrder, "" + storagePath} ;
						(ApplicationPropertyManager.getApplicationProperty("path.tmpdir").endsWith("/") ? ApplicationPropertyManager.getApplicationProperty("upload.script") : 
							"/" +  ApplicationPropertyManager.getApplicationProperty("upload.script")), fileName, "'" + fileTitle + "'", "" + imageOrder, "" + storagePath} ;
				try {
					logger.info("IMAGE CONVERSION TASK: launching command : " + ArrayUtils.toString(commandArray));
					process = Runtime.getRuntime().exec(commandArray, env);
				} 
				catch(Throwable th) {
					logger.error("errore di esecuzione", th);
				}
				
			} else if (SystemUtils.IS_OS_WINDOWS) {
				// XXX for development environment: we suppose the 'insert_after_upload.bat' file is
				// in the 'path.tmpdir' location
				String realCommand = "\"\"" + ApplicationPropertyManager.getApplicationProperty("path.tmpdir") + "insert_after_upload.bat\""
						+ " \"" + fileName + "\" \"" + fileTitle + "\" \"" + imageOrder + "\" \"" + storagePath + "\"\"" ;
						
				logger.info("IMAGE CONVERSION TASK: Windows Operating System detected...");
				String[] command = new String[3];
				command[0] = "cmd.exe";
				command[1] = "/C";
				command[2] = realCommand;
				
				try {
					logger.info("IMAGE CONVERSION TASK: launching command : " + ArrayUtils.toString(command));

					process = Runtime.getRuntime().exec(command);
				} 
				catch(Throwable th) {
					logger.error("errore di esecuzione", th);
				}
			} else {
				logger.error("IMAGE CONVERSION TASK: The detected Operating System is not supported...the task is aborted!");
				return NOT_SUPPORTED_OS;
			}
			
			
			ImageConversionProcessStreamLogger errorLoggerConsumer = new ImageConversionProcessStreamLogger(process.getErrorStream(), logger, ImageConversionProcessStreamLogger.LogLevel.ERROR);
			ImageConversionProcessStreamLogger infoLoggerConsumer = new ImageConversionProcessStreamLogger(process.getInputStream(), logger, ImageConversionProcessStreamLogger.LogLevel.INFO);
			
			errorLoggerConsumer.start();
			infoLoggerConsumer.start();
			
			return process.waitFor();
		} catch (Exception e) {
			return TASK_ERROR;
		}
	}

}
