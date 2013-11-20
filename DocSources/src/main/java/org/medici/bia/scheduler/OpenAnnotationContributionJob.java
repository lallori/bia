/*
 * OpenAnnotationContributionJob.java
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

import org.apache.log4j.MDC;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.openannotation.AnnotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements the scheduler to perform the writing of the &quot;open annotation file&quot;.<br/>
 * The jobs is scheduled once a day (at 0:30:00 am).
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class OpenAnnotationContributionJob {
	@Autowired
	private AnnotationService annotationService;
	
	public AnnotationService getAnnotationService() {
		return annotationService;
	}

	public void setAnnotationService(AnnotationService annotationService) {
		this.annotationService = annotationService;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Scheduled(cron = "0 30 0 * * *")
	public void execute() {
		MDC.put("openannotation", "threadopenannotationcontribution");
		try {
			getAnnotationService().writeJsonLDFile();
		} catch (ApplicationThrowable ath) {
		}
	}
}
