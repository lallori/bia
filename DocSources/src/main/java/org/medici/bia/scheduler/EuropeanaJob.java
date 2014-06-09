/*
 * EuropeanaJob.java
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
package org.medici.bia.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.europeana.EuropeanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements the scheduler to perform the writing of the &quot;Europeana file&quot;.<br/>
 * The jobs is scheduled once a week (on Sunday at 3:00:00 am).
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class EuropeanaJob {
	
	private Logger logger = Logger.getLogger(EuropeanaJob.class);
	
	@Autowired
	private EuropeanaService europeanaService;

	public EuropeanaService getEuropeanaService() {
		return europeanaService;
	}

	public void setEuropeanaService(EuropeanaService europeanaService) {
		this.europeanaService = europeanaService;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Scheduled(cron = "0 0 3 ? * SUN")
	public void execute() {
		MDC.put("europeana", "threadeuropeana");
		if (Boolean.valueOf(ApplicationPropertyManager.getApplicationProperty("europeana.active"))) {
			try {
				String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				logger.info("Europeana Job starts at " + now);
				getEuropeanaService().writeEuropeanaFile();
				now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				logger.info("Europeana Job ends at " + now);
			} catch (ApplicationThrowable ath) {
				logger.error("Europeana Job fails...", ath);
			}
		} else {
			logger.info("Europeana Job is not active so it has been skipped!");
		}
	}

}
