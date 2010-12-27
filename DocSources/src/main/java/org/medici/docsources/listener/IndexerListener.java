package org.medici.docsources.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Application Lifecycle Listener implementation class IndexerListener
 *
 */
public class IndexerListener implements ServletContextListener {
	
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
    	VolBaseService volBaseService = (VolBaseService) ctx.getBean("volBaseService");
    	DocBaseService docBaseService = (DocBaseService) ctx.getBean("docBaseService");
    	try {
    		//volBaseService.generateIndexVolume();
    		//volBaseService.generateIndexSerieList();
    		peopleBaseService.generateIndexPeople();
    		/*peopleBaseService.generateIndexAltName();
    		peopleBaseService.generateIndexPoLink();
    		peopleBaseService.generateIndexTitleOccsList();
    		peopleBaseService.generateIndexRoleCat();
    		peopleBaseService.generateIndexBioRefLink();
    		peopleBaseService.generateIndexEpLink();
    		peopleBaseService.generateIndexBiblioT();
*/
    		//docBaseService.generateIndexDocument();
    		//docBaseService.generateIndexEpLink();
    		//docBaseService.generateIndexEplToLink();
    		//docBaseService.generateIndexFactChecks();
    		//docBaseService.generateIndexSynExtract();
		} catch (ApplicationThrowable ath) {
			//ath.printStackTrace();
		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }

}
