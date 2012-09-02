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
package org.medici.bia.indexer;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.FlushEventListener;
import org.hibernate.search.event.FullTextIndexEventListener;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.docbase.DocBaseService;
import org.medici.bia.service.geobase.GeoBaseService;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.medici.bia.service.volbase.VolBaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class DocSourcesIndexer {

	private static Logger logger = Logger.getLogger(DocSourcesIndexer.class);
	
	public static void main(String[] args) {
		if ((args ==null) || (args.length != 2)) {
			System.out.println("usage {documents|volumes|people|places|all} {create|update|optimize}");
			return;
		} 
		
		DocSourcesIndexer docSourcesIndexer = new DocSourcesIndexer();
		
    	ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationIndexer.xml");   

		Long startTime = System.currentTimeMillis();

		if (args[0].equals("all")) {
			if (args[1].equals("create")) {
				docSourcesIndexer.createIndexVolumes(ctx);
				docSourcesIndexer.createIndexDocuments(ctx);
				docSourcesIndexer.createIndexPeople(ctx);
				docSourcesIndexer.createIndexPlaces(ctx);
			} else if (args[1].equals("update")) {
				docSourcesIndexer.updateIndexVolumes(ctx);
				docSourcesIndexer.updateIndexDocuments(ctx);
				docSourcesIndexer.updateIndexPeople(ctx);
				docSourcesIndexer.updateIndexPlaces(ctx);
			} else if (args[1].equals("optimize")) {
				docSourcesIndexer.optimizeIndexVolumes(ctx);
				docSourcesIndexer.optimizeIndexDocuments(ctx);
				docSourcesIndexer.optimizeIndexPeople(ctx);
				docSourcesIndexer.optimizeIndexPlaces(ctx);
			}
		} else if (args[0].equals("documents")) {
			if (args[1].equals("create")) {
				docSourcesIndexer.createIndexDocuments(ctx);
			} else if (args[1].equals("update")) {
				docSourcesIndexer.updateIndexDocuments(ctx);
			} else if (args[1].equals("optimize")) {
				
			}
		} else if (args[0].equals("people")) {
			if (args[1].equals("create")) {
				docSourcesIndexer.createIndexPeople(ctx);
			} else if (args[1].equals("update")) {
				docSourcesIndexer.updateIndexPeople(ctx);
			} else if (args[1].equals("optimize")) {
				
			}
		} else if (args[0].equals("places")) {
			if (args[1].equals("create")) {
				docSourcesIndexer.createIndexPlaces(ctx);
			} else if (args[1].equals("update")) {
				docSourcesIndexer.updateIndexPlaces(ctx);
			} else if (args[1].equals("optimize")) {
				
			}
		} else if (args[0].equals("volumes")) {
			if (args[1].equals("create")) {
				docSourcesIndexer.createIndexVolumes(ctx);
			} else if (args[1].equals("update")) {
				docSourcesIndexer.updateIndexVolumes(ctx);
			} else if (args[1].equals("optimize")) {
				
			}
		} 
		
		/* 
		 * It's very important to cleanup FullTextIndexEventListener, otherwise 
		 * Hibernate search Indexer will leave lock files on entity.
		 * 
		 * Fix code get on hibernate forum : 
		 * https://forum.hibernate.org/viewtopic.php?f=9&t=993557#p2413625
		 * 
		 * But we have a problem retrieving hibernate configuration, resolved
		 * with this trick :
		 * 
		 * http://forum.springsource.org/showthread.php?16998-Trying-to-get-Hibernate-SessionFactory-and-Configuration&p=29712#post29712
		 * 
		 */
		LocalSessionFactoryBean  lsfb = (LocalSessionFactoryBean) ctx.getBean("&sessionFactory");
		Configuration configuration = lsfb.getConfiguration();
		
		for (FlushEventListener fl : configuration.getEventListeners().getFlushEventListeners()) {
		   if (fl instanceof FullTextIndexEventListener) {
		      ((FullTextIndexEventListener) fl).cleanup();
		   }
		}

		logger.info("Indexing end (total index time " + (System.currentTimeMillis() - startTime) + ".");    		
	}
	
	/**
	 * 
	 * @param ctx
	 */
	private void createIndexDocuments(ApplicationContext ctx) {
       	try {
    		logger.info("Create index documents start");
        	DocBaseService docBaseService = (DocBaseService) ctx.getBean("docBaseService");
	
    		docBaseService.generateIndexTopicList();
    		docBaseService.generateIndexDocument();
    		logger.info("Create index documents stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void createIndexPeople(ApplicationContext ctx) {
       	try {
    		logger.info("Create index people start");
        	PeopleBaseService peopleBaseService = (PeopleBaseService) ctx.getBean("peopleBaseService");
	
    		peopleBaseService.generateIndexPeople();
    		peopleBaseService.generateIndexParents();
    		peopleBaseService.generateIndexTitleOccsList();
    		peopleBaseService.generateIndexRoleCat();
    		peopleBaseService.generateIndexAltName();
    		logger.info("Create index people stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void createIndexPlaces(ApplicationContext ctx) {
       	try {
    		logger.info("Create index places start");
        	GeoBaseService geoBaseService = (GeoBaseService) ctx.getBean("geoBaseService");
	
    		geoBaseService.generateIndexPlace();
    		geoBaseService.generateIndexPlaceExternalLinks();
    		geoBaseService.generateIndexPlaceGeographicCoordinates();
    		geoBaseService.generateIndexPlaceType();
    		logger.info("Create index places stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void createIndexVolumes(ApplicationContext ctx) {
       	try {
    		logger.info("Create index volumes start");
	    	VolBaseService volBaseService = (VolBaseService) ctx.getBean("volBaseService");
	
			volBaseService.generateIndexMonth();
			volBaseService.generateIndexSerieList();
	    	volBaseService.generateIndexVolume();
			logger.info("Create index volumes stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void optimizeIndexDocuments(ApplicationContext ctx) {
       	try {
    		logger.info("Optimize index documents start");
        	DocBaseService docBaseService = (DocBaseService) ctx.getBean("docBaseService");
	
    		docBaseService.optimizeIndexDocument();
    		logger.info("Optimize index documents stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void optimizeIndexPeople(ApplicationContext ctx) {
       	try {
    		logger.info("Optimize index people start");
        	PeopleBaseService peopleBaseService = (PeopleBaseService) ctx.getBean("peopleBaseService");
	
    		peopleBaseService.optimizeIndexPeople();
    		logger.info("Optimize index people stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void optimizeIndexPlaces(ApplicationContext ctx) {
		try {
			logger.info("Optimize index places start");
	    	GeoBaseService geoBaseService = (GeoBaseService) ctx.getBean("geoBaseService");
	
			geoBaseService.optimizeIndexPlace();
			logger.info("Optimize index places stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void optimizeIndexVolumes(ApplicationContext ctx) {
       	try {
    		logger.info("Optimize index volumes start");
	    	VolBaseService volBaseService = (VolBaseService) ctx.getBean("volBaseService");
	
	    	volBaseService.optimizeIndexVolume();
			logger.info("Optimize index volumes stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	@SuppressWarnings("deprecation")
	private void updateIndexDocuments(ApplicationContext ctx) {
       	try {
       		Date today = new Date(System.currentTimeMillis());
       		Date fromDate = new Date(today.getYear(), today.getMonth(), today.getDate(), 0, 0, 0);
    		logger.info("Update index documents start");
        	DocBaseService docBaseService = (DocBaseService) ctx.getBean("docBaseService");
	
    		docBaseService.generateIndexTopicList();
    		docBaseService.updateIndexDocument(fromDate);
    		logger.info("Update index documents stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void updateIndexPeople(ApplicationContext ctx) {
       	try {
       		Date fromDate = new Date();
    		logger.info("Update index people start");
        	PeopleBaseService peopleBaseService = (PeopleBaseService) ctx.getBean("peopleBaseService");
	
    		peopleBaseService.updateIndexPeople(fromDate);
    		peopleBaseService.generateIndexParents();
    		peopleBaseService.generateIndexTitleOccsList();
    		peopleBaseService.generateIndexRoleCat();
    		peopleBaseService.generateIndexAltName();
    		logger.info("Update index people stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void updateIndexPlaces(ApplicationContext ctx) {
		try {
			Date fromDate = new Date();
    		logger.info("Update index places start");
        	GeoBaseService geoBaseService = (GeoBaseService) ctx.getBean("geoBaseService");
	
    		geoBaseService.updateIndexPlace(fromDate);
    		geoBaseService.generateIndexPlaceExternalLinks();
    		geoBaseService.generateIndexPlaceGeographicCoordinates();
    		geoBaseService.generateIndexPlaceType();
    		logger.info("Update index places stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}

	/**
	 * 
	 * @param ctx
	 */
	private void updateIndexVolumes(ApplicationContext ctx) {
       	try {
       		Date fromDate = new Date();
    		logger.info("Update index volumes start");
	    	VolBaseService volBaseService = (VolBaseService) ctx.getBean("volBaseService");
	
			volBaseService.generateIndexMonth();
			volBaseService.generateIndexSerieList();
	    	volBaseService.updateIndexVolume(fromDate);
			logger.info("Update index volumes stop");
		} catch (ApplicationThrowable ath) {
			logger.error("", ath);
		}
	}
}
