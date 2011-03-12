/*
 * IndexesCreator.java
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
package org.medici.docsources.listener;

import org.apache.log4j.Logger;
import org.medici.docsources.exception.ApplicationThrowable;
import org.medici.docsources.service.docbase.DocBaseService;
import org.medici.docsources.service.peoplebase.PeopleBaseService;
import org.medici.docsources.service.volbase.VolBaseService;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class IndexesCreator {

	public static void main(String[] args) {
	    Logger logger = Logger.getLogger(IndexesCreator.class);

	    try {
	        String configurationFile = "config/applicationMassiveIndexer.xml";
	        ApplicationContext context =new ClassPathXmlApplicationContext(configurationFile);
			
	    	PeopleBaseService peopleBaseService = (PeopleBaseService) context.getBean("peopleBaseService");
	    	VolBaseService volBaseService = (VolBaseService) context.getBean("volBaseService");
	    	DocBaseService docBaseService = (DocBaseService) context.getBean("docBaseService");
	    	try { 
	    		volBaseService.generateIndexVolume();
	    		volBaseService.generateIndexSerieList();
	    		peopleBaseService.generateIndexPeople();
	    		docBaseService.generateIndexDocument();
	    		docBaseService.generateIndexEpLink();
	    		docBaseService.generateIndexEplToLink();
	    		docBaseService.generateIndexFactChecks();
	    		docBaseService.generateIndexSynExtract();
			} catch (ApplicationThrowable ath) {
				ath.printStackTrace();
			}
		} catch (Throwable th) {
			th.printStackTrace();
			logger.error(th);
		}
	}
}
