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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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
import org.medici.bia.common.util.FileUtils;
import org.medici.bia.common.util.FileWriterHelper;
import org.medici.bia.dao.document.DocumentDAO;
import org.medici.bia.dao.image.ImageDAO;
import org.medici.bia.domain.Document;
import org.medici.bia.domain.Image;
import org.medici.bia.domain.Image.ImageType;
import org.medici.bia.exception.ApplicationThrowable;
import org.springframework.beans.factory.annotation.Autowired;
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
	private static final String BACKUP_FILE = "Europeana.old";
	private static final String TMP_FILE = "EuropeanaTmp.txt";
	private static final String FILE = "Europeana.xml";
	
	@Autowired
	private DocumentDAO documentDAO;
	@Autowired
	private ImageDAO imageDAO;
	
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

	@Override
	public void writeEuropeanaFile() throws ApplicationThrowable {
		if (!checkPath()) {
			logger.error("It is not possible to access to the europeana path");
			throw new ApplicationThrowable(ApplicationError.GENERIC_ERROR, "It is not possible to access to the europeana path");
		}
		
		try {
			String path = ApplicationPropertyManager.getApplicationProperty("europeana.path") + File.separator;
			Date timeStamp = new Date();
			initWriter(path + TMP_FILE);
			
			String lastLine = writeOpenContainer();
			writeDocuments(timeStamp);
			writeImages(timeStamp);
			writeLastLine(lastLine);
			
			if (FileUtils.exists(path + FILE)) {
				FileUtils.renameTo(path + FILE, path + BACKUP_FILE);
			}
			FileUtils.renameTo(path + TMP_FILE, path + FILE);
			
			closeWriter();
		} catch (EuropeanaException exc) {
			throw new ApplicationThrowable(exc);
		} catch (IOException exc) {
			throw new ApplicationThrowable(exc);
		} finally {
			if (writer != null && writer.isInitialized()) {
				try {
					writer.close();
				} catch (IOException e) {
					// do noting
				}
			}
		}
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
	
	private <T> String doSerialize(EuropeanaSerializer<T> serializer, T item, String pre) throws EuropeanaException {
		String res = pre != null ? pre : "";
		serializer.addData(item);
		res += serializer.next();
		return res + "\n";
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
	
	private void initWriter(String fileName) throws EuropeanaException {
		try {
			writer = new FileWriterHelper();
			writer.init(fileName, false);
		} catch (IOException ioex) {
			logger.error("Problems opening file writer!!!");
			throw new EuropeanaException(EuropeanaException.IOERROR, ioex);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void writeDocuments(Date timeStamp) throws EuropeanaException {
		Long count = getDocumentDAO().countDocumentCreatedBeforeDate(timeStamp);
		
		EuropeanaConverter converter = new EuropeanaConverter();
		int group = 0;
		do {
			group++;
			PaginationFilter paginationFilter = new PaginationFilter((group - 1) * GROUP_SIZE, GROUP_SIZE, count);
			paginationFilter.addSortingCriteria("dateCreated", "ASC");
			List<Document> candidates = (List<Document>) getDocumentDAO().searchDocumentsCreatedBefore(timeStamp, paginationFilter).getList();
			
			List<Integer> slice = new ArrayList<Integer>();
			for (Document document : candidates) {
				if (document.getLogicalDelete() != null && Boolean.FALSE.equals(document.getLogicalDelete())) {
					slice.add(document.getEntryId());
				}
			}
			
			if (slice.size() > 0) {
				Map<Integer, Integer> associatedImage = getDocumentDAO().getAssociatedImage(slice);
				
				List<EuropeanaItem> items = new ArrayList<EuropeanaItem>();
				for (Document document : candidates) {
					items.add(converter.toEuropeanaItem(document, associatedImage.get(document.getEntryId())));
				}
	
				writeToFile(items, EuropeanaItem.class);
			}
			
		} while (group * GROUP_SIZE < count);
		
	}
	
	@SuppressWarnings("unchecked")
	private void writeImages(Date timeStamp) throws EuropeanaException {
		List<ImageType> imageTypes = new ArrayList<ImageType>();
		imageTypes.add(ImageType.C);
		imageTypes.add(ImageType.R);
		imageTypes.add(ImageType.A);
		
		Long count = getImageDAO().countImagesCreatedBeforeDate(timeStamp, imageTypes);
		
		EuropeanaConverter converter = new EuropeanaConverter();
		int group = 0;
		do {
			group++;
			PaginationFilter paginationFilter = new PaginationFilter((group - 1) * GROUP_SIZE, GROUP_SIZE, count);
			paginationFilter.addSortingCriteria("imageId", "ASC");
			List<Image> images = (List<Image>) getImageDAO().searchImagesCreatedBefore(timeStamp, imageTypes, paginationFilter).getList();
			
			List<EuropeanaItem> items = new ArrayList<EuropeanaItem>();
			for (Image image : images) {
				items.add(converter.toEuropeanaItem(image));
			}
			
			writeToFile(items, EuropeanaItem.class);
			
		} while (group * GROUP_SIZE < count);
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
			EuropeanaSerializer<ProvidedCHO> choSer = getSerializer(ProvidedCHO.class);
			EuropeanaSerializer<WebResource> wrSer = getSerializer(WebResource.class);
			EuropeanaSerializer<Aggregation> aggSer = getSerializer(Aggregation.class);
			for (T item : items) {
				EuropeanaItem i = (EuropeanaItem) item;
				ser += doSerialize(choSer, i.getCho(), "\n<!-- " + i.getItemComment() + " -->\n");
				ser += doSerialize(wrSer, i.getWebResource(), null);
				ser += doSerialize(aggSer, i.getAggregation(), null);
			}
		} else if (EuropeanaResourceContainer.class.equals(clazz)) {
			EuropeanaSerializer<EuropeanaResourceContainer> contSer = getSerializer(EuropeanaResourceContainer.class);
			ser = doSerialize(contSer, (EuropeanaResourceContainer)items.get(0), "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
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
