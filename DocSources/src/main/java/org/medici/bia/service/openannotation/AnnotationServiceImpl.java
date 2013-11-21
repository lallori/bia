/*
 * AnnotationServiceImpl.java
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
package org.medici.bia.service.openannotation;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.medici.bia.common.openannotation.OASerializable;
import org.medici.bia.common.openannotation.model.OAAnnotation;
import org.medici.bia.common.openannotation.model.OAPerson;
import org.medici.bia.common.openannotation.model.Text;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.OpenAnnotationConverter;
import org.medici.bia.common.util.OpenAnnotationSerializer;
import org.medici.bia.dao.annotation.AnnotationDAO;
import org.medici.bia.domain.Annotation;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is the default implementation of services associated to open annotations features.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class AnnotationServiceImpl implements AnnotationService {
	
	private final static String FILENAME = "BiaAnnotations";
	private final static String FILEEXT = "json";
	
	/**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 4096;
	
	private static Logger logger = Logger.getLogger(AnnotationServiceImpl.class);
	
	@Autowired
	private AnnotationDAO annotationDAO;

	public AnnotationDAO getAnnotationDAO() {
		return annotationDAO;
	}

	public void setAnnotationDAO(AnnotationDAO annotationDAO) {
		this.annotationDAO = annotationDAO;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readJsonLDFile() throws ApplicationThrowable {
		if (!checkOAPath(false)) {
			logger.error("It is not possible to access to the open annotation path");
		}
		ByteArrayOutputStream output = new ByteArrayOutputStream(BUFFER_SIZE);
		getJsonContent(output);
		String json = null;
		try {
			json = output.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
			}
		}
		return json;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void writeJsonLDFile() throws ApplicationThrowable {
		if (!checkOAPath(true)) {
			logger.error("It is not possible to access to the open annotation path");
		}
		List<Annotation> forumAnnotations = getAnnotationDAO().findForumAnnotations();
		if (forumAnnotations != null && forumAnnotations.size() > 0) {
			List<OASerializable> openAnnotations = new ArrayList<OASerializable>(getOpenAnnotations(forumAnnotations));
			writeToFile(openAnnotations);
		} else {
			logger.warn("There are no annotations to serialize!");
		}
		logger.info("Writing to Json-LD file process: END");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long writeJsonLDFileToOutputStream(OutputStream outputStream) throws ApplicationThrowable {
		if (!checkOAPath(false)) {
			logger.error("It is not possible to access to the open annotation path");
		}
		return getJsonContent(outputStream);
	}
	
	/* Privates */
	
	private boolean checkOAPath(boolean create) throws ApplicationThrowable {
		String path = ApplicationPropertyManager.getApplicationProperty("openannotation.path");
		if (path == null || "".equals(path)) {
			logger.error("The key 'openannotation.path' is not defined in tblApplicationProperty table or it is empty");
			throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR);
		}
		
		File oaPath = new File(path);
		if (!oaPath.exists() && create) { 
			return oaPath.mkdirs();
		}
		return true;
	}
	
	private String getFileName() {
		return ApplicationPropertyManager.getApplicationProperty("openannotation.path") + File.separator + FILENAME + "." + FILEEXT;
	}
	
	private long getJsonContent(OutputStream output) {
		File file = new File(getFileName());
		if (!file.exists()) {
			logger.warn("Open Annotation File does not exist!");
			return -1;
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
			logger.error("Problems reading Open Annotation File.", e);
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
	
	private List<OAAnnotation<OAPerson, Text, String>> getOpenAnnotations(List<Annotation> biaAnnotations) {
		String context = ApplicationPropertyManager.getApplicationProperty("website.protocol") + "://"
				+ ApplicationPropertyManager.getApplicationProperty("website.domain")
				+ ApplicationPropertyManager.getApplicationProperty("website.contextPath");
		OpenAnnotationConverter converter = new OpenAnnotationConverter(context);
		return converter.convertToOpenAnnotations(biaAnnotations);
	}
	
	private void writeToFile(List<OASerializable> openAnnotations) throws ApplicationThrowable {
		OpenAnnotationSerializer serializer = new OpenAnnotationSerializer(true);
		OutputStream output = null;
		try {
			serializer.init(openAnnotations);
			output = new BufferedOutputStream(new FileOutputStream(getFileName(), false));
			byte[] data = serializer.start().getBytes();
			output.write(data);
			while (serializer.hasNext()) {
				data = serializer.serializeNext().getBytes();
				output.write(data);
			}
			data = serializer.stop().getBytes();
			output.write(data);
			output.flush();
			output.close();
		} catch (Exception e) {
			throw new ApplicationThrowable(e);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
