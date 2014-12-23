/*
 * ShowUploadNewImagesController.java
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
package org.medici.bia.controller.teaching;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.teaching.ImageConversionInvoker;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.StringUtils;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.teaching.TeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Controller
public class ShowUploadNewImagesController {
	
	private Logger logger = Logger.getLogger(ShowUploadNewImagesController.class);
	
	@Autowired
	private TeachingService teachingService;
	
	public TeachingService getTeachingService() {
		return teachingService;
	}

	public void setAdminService(TeachingService teachingService) {
		this.teachingService = teachingService;
	}

	@RequestMapping(value = "/teaching/ShowUploadNewImages", method = RequestMethod.GET)
	public ModelAndView setup() {
		Map<String, Object> model = new HashMap<String, Object>(0);
		
		return new ModelAndView("teaching/ShowUploadNewImages", model);
	}
	
	@RequestMapping(value = "/teaching/UploadImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadImage(
			@RequestParam("name") String name,
			@RequestParam("title") String title,
            @RequestParam("file") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		BufferedOutputStream stream = null;
		
		if (!file.isEmpty()) {
			try {
				String timeGenerated = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String fileName = timeGenerated + "_" + name;
				byte[] bytes = file.getBytes();
				stream = new BufferedOutputStream(new FileOutputStream(new File(getTempPath() + File.separator + fileName)));
				stream.write(bytes);
				model.put("response", "OK");
				model.put("fileName", fileName);
			} catch (Exception e) {
				model.put("response", "KO");
				model.put("error", e.toString());
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch(Exception ex) {
					}
				}
			}
		} else {
			model.put("response", "KO");
			model.put("error", "Empty file");
		}
		
		return model;
	}
	
	@RequestMapping(value = "/teaching/PrepareImage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> prepareImage(
			@RequestParam("fileName") String fileName,
			@RequestParam("fileTitle") String fileTitle,
			@RequestParam("imageOrder") Integer imageOrder) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			String storagePath = ApplicationPropertyManager.getApplicationProperty("iipimage.upload.storagePathNumber");
			if (StringUtils.isNullableString(storagePath)) {
				model.put("response", "KO");
				model.put("error", "Storage path not defined for uploaded images!");
				return model;
			}
			
			ImageConversionInvoker invoker = new ImageConversionInvoker(fileName, fileTitle, imageOrder, Integer.valueOf(storagePath), logger);
			int response = invoker.fire();
			model.put("response", response == 0 ? "OK" : "KO");
			if (response != 0) {
				model.put("error", "ERROR " + response);
			}
		} catch (Exception e) {
			model.put("response", "KO");
			model.put("error", e.toString());
		}
		
		return model;
	}
	
	/* Privates */
	
	private String getTempPath() {
		String tempPath = ApplicationPropertyManager.getApplicationProperty("iipimage.upload.path");
		if (StringUtils.isNullableString(tempPath)) {
			throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR, "Temporary upload path [iipimage.upload.path] is not defined!");
		}
		initializePathIfNeeded(tempPath);
		return tempPath;
	}
	
	private void initializePathIfNeeded(String path) {
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			try {
				pathFile.mkdirs();
			} catch (Exception e) {
				throw new ApplicationThrowable(ApplicationError.GENERIC_ERROR, "The path [" + path + "] is not accessible!!!");
			}
		}
	}

}
