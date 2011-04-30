/*
 * DocSourcesIndexer.java
 * 
 * Developed by Medici Archive Project (2010-2012).
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
package org.medici.docsources.indexer;

import org.apache.log4j.Logger;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DocSourcesIndexer {

	private static Logger logger = Logger.getLogger(DocSourcesIndexer.class);
	
	public static void main(String[] args) {
		if ((args ==null) || (args.length == 0)) {
			System.out.println("usage {documents|volumes|people|places|all}");
			return;
		} 
		
		DocSourcesIndexer docSourcesIndexer = new DocSourcesIndexer();
		
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationIndexer.xml");   

		Long startTime = System.currentTimeMillis();

		if (args[0].equals("all")) {
			docSourcesIndexer.indexVolumes(ctx);
			docSourcesIndexer.indexDocuments(ctx);
			docSourcesIndexer.indexPeople(ctx);
			docSourcesIndexer.indexPlaces(ctx);
		} else if (args[0].equals("documents")) {
			docSourcesIndexer.indexDocuments(ctx);
		} else if (args[0].equals("people")) {
			docSourcesIndexer.indexPeople(ctx);
		} else if (args[0].equals("places")) {
			docSourcesIndexer.indexPlaces(ctx);
		} else if (args[0].equals("volumes")) {
			docSourcesIndexer.indexVolumes(ctx);
		} 
		
		logger.info("Indexing end (total index time " + (System.currentTimeMillis() - startTime) + ".");    		
	}
	
	/**
	 * 
	 * @param ctx
	 */
	private void indexDocuments(ApplicationContext ctx) {
       	try {
    		logger.info("Indexing documents start");
        	DocBaseService docBaseService = (DocBaseService) ctx.getBean("docBaseService");
	
    		docBaseService.generateIndexTopicList();
    		docBaseService.generateIndexDocument();
    		logger.info("Indexing documents stop");
		} catch (ApplicationThrowable ath) {
			ath.printStackTrace();
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void indexPlaces(ApplicationContext ctx) {
       	try {
    		logger.info("Indexing places start");
        	GeoBaseService geoBaseService = (GeoBaseService) ctx.getBean("geoBaseService");
	
    		geoBaseService.generateIndexPlace();
    		logger.info("Indexing places stop");
		} catch (ApplicationThrowable ath) {
			ath.printStackTrace();
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void indexPeople(ApplicationContext ctx) {
       	try {
    		logger.info("Indexing people start");
        	PeopleBaseService peopleBaseService = (PeopleBaseService) ctx.getBean("peopleBaseService");
	
    		peopleBaseService.generateIndexPeople();
    		peopleBaseService.generateIndexParents();
    		peopleBaseService.generateIndexTitleOccsList();
    		peopleBaseService.generateIndexRoleCat();
    		peopleBaseService.generateIndexAltName();
    		logger.info("Indexing people stop");
		} catch (ApplicationThrowable ath) {
			ath.printStackTrace();
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void indexVolumes(ApplicationContext ctx) {
       	try {
    		logger.info("Indexing volumes start");
	    	VolBaseService volBaseService = (VolBaseService) ctx.getBean("volBaseService");
	
	    	volBaseService.generateIndexVolume();
			volBaseService.generateIndexSerieList();
			logger.info("Indexing volumes stop");
		} catch (ApplicationThrowable ath) {
			ath.printStackTrace();
		}
	}
}
