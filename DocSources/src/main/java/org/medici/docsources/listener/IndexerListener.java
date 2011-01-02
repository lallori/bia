package org.medici.docsources.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.geobase.GeoBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application Lifecycle Listener implementation class IndexerListener
 *
 */
public class IndexerListener implements ServletContextListener {
	Logger logger = Logger.getLogger(this.getClass());

    /**
     * Default constructor. 
     */
    public IndexerListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
    	ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sce.getServletContext());
    	PeopleBaseService peopleBaseService = (PeopleBaseService) ctx.getBean("peopleBaseService");
    	DocBaseService docBaseService = (DocBaseService) ctx.getBean("docBaseService");
    	GeoBaseService geoBaseService = (GeoBaseService) ctx.getBean("geoBaseService");
    	VolBaseService volBaseService = (VolBaseService) ctx.getBean("volBaseService");
    	/*
       	try {
    		logger.info("Indexing document start");
    		Long startTime = System.currentTimeMillis();

    		volBaseService.generateIndexVolume();
    		volBaseService.generateIndexSerieList();

    		peopleBaseService.generateIndexAltName();
    		peopleBaseService.generateIndexBioRefLink();
    		peopleBaseService.generateIndexBiblioT();
    		peopleBaseService.generateIndexRoleCat();
    		peopleBaseService.generateIndexTitleOccsList();
    		peopleBaseService.generateIndexPoLink();
    		peopleBaseService.generateIndexEpLink();
    		peopleBaseService.generateIndexPeople();

    		docBaseService.generateIndexDocument();
    		docBaseService.generateIndexSynExtract();
    		docBaseService.generateIndexEplToLink();
    		docBaseService.generateIndexFactChecks();
    		docBaseService.generateIndexTopicList();
    		docBaseService.generateIndexFactChecks();
    		geoBaseService.generateIndexPlace();
    		logger.info("Indexing document end (total index time " + (System.currentTimeMillis() - startTime) + ".");    		
		} catch (ApplicationThrowable ath) {
			//ath.printStackTrace();
		}
   		*/
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }

}
