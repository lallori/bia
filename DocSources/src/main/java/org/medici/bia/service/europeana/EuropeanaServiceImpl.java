/*
 * EuropeanaServiceImpl.java
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
package org.medici.bia.service.europeana;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.medici.bia.common.context.ApplicationContextVariableManager;
import org.medici.bia.common.europeana.EuropeanaException;
import org.medici.bia.common.europeana.EuropeanaItem;
import org.medici.bia.common.europeana.model.Aggregation;
import org.medici.bia.common.europeana.model.EuropeanaResourceContainer;
import org.medici.bia.common.europeana.model.ProvidedCHO;
import org.medici.bia.common.europeana.model.WebResource;
import org.medici.bia.common.pagination.PaginationFilter;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.common.util.ApplicationError;
import org.medici.bia.common.util.EuropeanaConverter;
import org.medici.bia.common.util.EuropeanaSerializer;
import org.medici.bia.common.util.ExceptionUtils;
import org.medici.bia.common.util.FileUtils;
import org.medici.bia.common.util.FileWriterHelper;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
@Service
@Transactional(readOnly=true)
public class EuropeanaServiceImpl implements EuropeanaService {
	
	private static Logger logger = Logger.getLogger(EuropeanaServiceImpl.class);
	
	private static final int GROUP_SIZE = 25;
	private static final String BASE_NAME = "Europeana";
	private static final String BACKUP_FILE = BASE_NAME + ".old";
	private static final String TMP_FILE = BASE_NAME + ".tmp";
	private static final String FILE = BASE_NAME + ".xml";
	
	private Map<Class<?>, EuropeanaSerializer<?>> serializerMap;
	
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private ImageDAO imageDAO;
	@Autowired
	private ApplicationContextVariableManager applicationContextVariableManager;
	
	private FileWriterHelper writer;
	
	public DocumentDAO getDocumentDAO() {
		return documentDAO;
	}

	public void setDocumentDAO(DocumentDAO documentDAO) {
		this.documentDAO = documentDAO;
	}

	public ImageDAO getImageDAO() {
		return imageDAO;
	}

	public void setImageDAO(ImageDAO imageDAO) {
		this.imageDAO = imageDAO;
	}
	
	public ApplicationContextVariableManager getApplicationContextVariableManager() {
		return applicationContextVariableManager;
	}

	public void setApplicationContextVariableManager(
			ApplicationContextVariableManager applicationContextVariableManager) {
		this.applicationContextVariableManager = applicationContextVariableManager;
	}

	@Override
	public long downladFile(OutputStream outputStream) throws ApplicationThrowable {
		String path = ApplicationPropertyManager.getApplicationProperty("europeana.path");
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		try {
			if (FileUtils.exists(path + FILE)) {
				return FileUtils.getContent(new File(path + FILE), outputStream);
			}
		} catch (Exception e) {
			throw new ApplicationThrowable(e);
		}
		return -1;
	}
	
	@Override
	public String getEuropeanaFileSize() throws ApplicationThrowable {
		String path = ApplicationPropertyManager.getApplicationProperty("europeana.path");
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		try {
			return FileUtils.fileSize(new File(path + FILE));
		} catch (Exception e) {
			throw new ApplicationThrowable(e);
		}
	}

	@Override
	public void writeEuropeanaFile() throws ApplicationThrowable {
		doWriteEuropeanaFile();
	}
	
	@Override
	@Async
	public void writeEuropeanaFileAsync() throws ApplicationThrowable {
		doWriteEuropeanaFile();
	}
	
	/* Privates */
	
	private boolean checkPath() throws ApplicationThrowable {
		String path = ApplicationPropertyManager.getApplicationProperty("europeana.path");
		if (path == null || "".equals(path)) {
			logger.error("The key 'europeana.path' is not defined in tblApplicationProperty table or it is empty");
			throw new ApplicationThrowable(ApplicationError.RECORD_NOT_FOUND_ERROR);
		}
		return FileUtils.checkPath(path, true);
	}
	
	private void closeWriter() throws EuropeanaException {
		try {
			writer.close();
		} catch (IOException e) {
			logger.error("Problems closing file writer!!!");
			throw new EuropeanaException(EuropeanaException.IOERROR, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> String doSerialize(Class<T> clazz, T item, String pre) throws EuropeanaException {
		EuropeanaSerializer<T> serializer = (EuropeanaSerializer<T>)serializerMap.get(clazz);
		if (serializer == null) {
			throw new EuropeanaException("Cannot retrieve serializer for class [" + clazz.toString() + "]");
		}
		String res = pre != null ? pre : "";
		serializer.addData(item);
		res += serializer.next();
		// it is important to empty serializer data in order to prevent memory leaks when large amount of items are processed
		serializer.clearData();
		return res + "\n";
	}
	
	private void doWriteEuropeanaFile() throws ApplicationThrowable {
		getApplicationContextVariableManager().add(ApplicationContextVariableManager.EUROPEANA_JOB, "running");
		getApplicationContextVariableManager().remove(EuropeanaService.ERROR);
		
		if (!checkPath()) {
			logger.error("It is not possible to access to the europeana path");
			throw new ApplicationThrowable(ApplicationError.GENERIC_ERROR, "It is not possible to access to the europeana path");
		}
		
		String lastLine = null;
		try {
			String path = ApplicationPropertyManager.getApplicationProperty("europeana.path");
			if (!path.endsWith(File.separator)) {
				path += File.separator;
			}
			Date timeStamp = new Date();
			initWriter(path + TMP_FILE);
			initSerializers();
			
			getApplicationContextVariableManager().add(EuropeanaService.PHASES, (int)2);
			lastLine = writeOpenContainer();
			logger.info("Europeana file generation...phase 1 of 2 (document to xml generation)");
			getApplicationContextVariableManager().add(EuropeanaService.CURRENT_PHASE, (int)1);
			boolean stop = writeDocuments(timeStamp);
			if (!stop) {
				logger.info("Europeana file generation...phase 2 of 2 (image to xml generation)");
				getApplicationContextVariableManager().add(EuropeanaService.CURRENT_PHASE, (int)2);
				stop = writeImages(timeStamp);
			}
			if (stop) {
				writeInterruptComment(true);
			}
			writeLastLine(lastLine);
			lastLine = null;
			
			// The writer has to be closed before the target file is renamed.
			closeWriter();
			if (!stop) {
				if (FileUtils.exists(path + FILE)) {
					FileUtils.renameTo(path + FILE, path + BACKUP_FILE);
				}
				FileUtils.renameTo(path + TMP_FILE, path + FILE);
			}
			
			getApplicationContextVariableManager().remove(ApplicationContextVariableManager.EUROPEANA_JOB);
			getApplicationContextVariableManager().remove(EuropeanaService.PHASES);
			getApplicationContextVariableManager().remove(EuropeanaService.CURRENT_PHASE);
			getApplicationContextVariableManager().remove(EuropeanaService.PROGRESS);
		} catch (Exception exc) {
			String stackTrace = ExceptionUtils.stackTraceToString(exc);
			getApplicationContextVariableManager().add(EuropeanaService.ERROR, "Europeana Job failed for:\n" + stackTrace);
			getApplicationContextVariableManager().remove(ApplicationContextVariableManager.EUROPEANA_JOB);
			getApplicationContextVariableManager().remove(EuropeanaService.PHASES);
			getApplicationContextVariableManager().remove(EuropeanaService.CURRENT_PHASE);
			getApplicationContextVariableManager().remove(EuropeanaService.PROGRESS);
			throw new ApplicationThrowable(exc);
		} finally {
			try {
				if (lastLine != null) {
					writeInterruptComment(false);
					writeLastLine(lastLine);
				}
			} catch (EuropeanaException e) {
				// cannot close tmp file correctly
			}
			if (writer != null && writer.isInitialized()) {
				try {
					writer.close();
				} catch (IOException e) {
					// do noting
				}
			}
		}
	}
	
	private void doWriteFile(String toWrite) throws EuropeanaException {
		try {
			writer.write(toWrite, true);
		} catch (IOException ioex) {
			logger.error("Problems writing file!!!");
			throw new EuropeanaException(EuropeanaException.IOERROR, ioex);
		}
	}
	
	private <S> EuropeanaSerializer<S> getSerializer(Class<S> clazz) throws EuropeanaException {
		EuropeanaSerializer<S> ser = new EuropeanaSerializer<S>();
		ser.init(clazz);
		return ser;
	}
	
	private void initSerializers()  throws EuropeanaException {
		serializerMap = new HashMap<Class<?>, EuropeanaSerializer<?>>();
		serializerMap.put(EuropeanaResourceContainer.class, getSerializer(EuropeanaResourceContainer.class));
		serializerMap.put(ProvidedCHO.class, getSerializer(ProvidedCHO.class));
		serializerMap.put(WebResource.class, getSerializer(WebResource.class));
		serializerMap.put(Aggregation.class, getSerializer(Aggregation.class));
	}
	
	private void initWriter(String fileName) throws EuropeanaException {
		try {
			writer = new FileWriterHelper();
			writer.init(fileName, false);
		} catch (IOException ioex) {
			logger.error("Problems opening file writer!!!");
			throw new EuropeanaException(EuropeanaException.IOERROR, ioex);
		}
	}
	
	private void logProgress(String prefix, float progress) {
		try {
			String format = String.format("%.2f", progress);
			logger.info(prefix +  format + "%");
		} catch (Exception e) {
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean writeDocuments(Date timeStamp) throws EuropeanaException, PersistenceException {
		Long count = getDocumentDAO().countDocumentCreatedBeforeDate(timeStamp);
		
		EuropeanaConverter converter = new EuropeanaConverter();
		PaginationFilter paginationFilter = new PaginationFilter(0, GROUP_SIZE, count);
		paginationFilter.addSortingCriteria("dateCreated", "ASC");
		
		int group = 0;
		boolean stop = false;
		do {
			group++;
			paginationFilter.setFirstRecord((group - 1) * GROUP_SIZE);
			
			// clean up the entity manager for performance purposes
			getDocumentDAO().clear();
			List<Document> candidates = (List<Document>) getDocumentDAO().searchDocumentsCreatedBefore(timeStamp, paginationFilter).getList();
			
			List<Integer> slice = new ArrayList<Integer>();
			List<Document> validDocs = new ArrayList<Document>();
			for (Document document : candidates) {
				if (document.getLogicalDelete() != null && Boolean.FALSE.equals(document.getLogicalDelete())) {
					slice.add(document.getEntryId());
					validDocs.add(document);
				}
			}
			
			if (slice.size() > 0) {
				Map<Integer, Integer> associatedImage = getDocumentDAO().getAssociatedImage(slice);
				
				List<EuropeanaItem> items = new ArrayList<EuropeanaItem>();
				for (Document document : validDocs) {
					items.add(converter.toEuropeanaItem(document, associatedImage.get(document.getEntryId())));
				}
	
				writeToFile(items, EuropeanaItem.class);
			}
			
			float completed = ((float)group * GROUP_SIZE * 100) / ((float)count);
			logProgress("Europeana file generation [phase 1 of 2]: ", completed);
			
			// We check if the execution has been externally stopped
			stop = getApplicationContextVariableManager().get(ApplicationContextVariableManager.EUROPEANA_JOB) == null;
			getApplicationContextVariableManager().add(EuropeanaService.PROGRESS, completed);
			
		} while (group * GROUP_SIZE < count && !stop);
		
		return stop;
		
	}
	
	@SuppressWarnings("unchecked")
	private boolean writeImages(Date timeStamp) throws EuropeanaException, PersistenceException {
		List<ImageType> imageTypes = new ArrayList<ImageType>();
		imageTypes.add(ImageType.C);
		imageTypes.add(ImageType.R);
		imageTypes.add(ImageType.A);
		
		Long count = getImageDAO().countImagesCreatedBeforeDate(timeStamp, imageTypes);
		
		EuropeanaConverter converter = new EuropeanaConverter();
		
		PaginationFilter paginationFilter = new PaginationFilter(0, GROUP_SIZE, count);
		paginationFilter.addSortingCriteria("imageId", "ASC");
		
		int group = 0;
		boolean stop = false;
		do {
			group++;
			paginationFilter.setFirstRecord((group - 1) * GROUP_SIZE);
			
			// clean up the entity manager for performance purposes
			getImageDAO().clear();
			List<Image> images = (List<Image>) getImageDAO().searchImagesCreatedBefore(timeStamp, imageTypes, paginationFilter).getList();
			
			List<EuropeanaItem> items = new ArrayList<EuropeanaItem>();
			for (Image image : images) {
				items.add(converter.toEuropeanaItem(image));
			}
			
			writeToFile(items, EuropeanaItem.class);
			
			float completed = ((float)group * GROUP_SIZE * 100) / ((float)count);
			logProgress("Europeana file generation [phase 2 of 2]: ", completed);
			
			// We check if the execution has been externally stopped
			stop = getApplicationContextVariableManager().get(ApplicationContextVariableManager.EUROPEANA_JOB) == null;
			getApplicationContextVariableManager().add(EuropeanaService.PROGRESS, completed);
			
		} while (group * GROUP_SIZE < count && !stop);
		
		return stop;
	}
	
	private void writeInterruptComment(boolean stop) throws EuropeanaException {
		String message = "Task aborted: " + (stop ? "the task was externally stopped" : "internal error occurred");
		doWriteFile("\n<!-- " + message + " -->\n");
	}
	
	private void writeLastLine(String lastLine) throws EuropeanaException {
		doWriteFile(lastLine);
	}
	
	@SuppressWarnings("serial")
	private String writeOpenContainer() throws EuropeanaException {
		return writeToFile(new ArrayList<EuropeanaResourceContainer>() {{add(new EuropeanaResourceContainer());}}, EuropeanaResourceContainer.class, true);
	}
	
	private <T> String writeToFile(List<T> items, Class<T> clazz) throws EuropeanaException {
		return writeToFile(items, clazz, false);
	}
	
	private <T> String writeToFile(List<T> items, Class<T> clazz, boolean leaveOpen) throws EuropeanaException {
		String notWrite = "";
		String ser = "";
		if (EuropeanaItem.class.equals(clazz)) {
			for (T item : items) {
				EuropeanaItem i = (EuropeanaItem) item;
				ser += doSerialize(ProvidedCHO.class, i.getCho(), "\n<!-- " + i.getItemComment() + " -->\n");
				ser += doSerialize(WebResource.class, i.getWebResource(), null);
				ser += doSerialize(Aggregation.class, i.getAggregation(), null);
			}
		} else if (EuropeanaResourceContainer.class.equals(clazz)) {
			ser = doSerialize(EuropeanaResourceContainer.class, (EuropeanaResourceContainer)items.get(0), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			ser = ser.substring(0, ser.lastIndexOf("/>")) + ">\n</rdf:RDF>";
		}
		if (leaveOpen) {
			notWrite = ser.substring(ser.lastIndexOf("</"));
			ser = ser.substring(0, ser.lastIndexOf("</"));
		}
		doWriteFile(ser);
		return notWrite;
	}

}
