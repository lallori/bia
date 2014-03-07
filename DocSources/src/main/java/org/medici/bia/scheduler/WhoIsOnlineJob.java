/*
 * WhoIsOnlineJob.java
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

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.medici.bia.common.context.ApplicationContextVariableManager;
import org.medici.bia.common.util.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements the scheduler to determine all joined users and guests.
 * It stores this data in {@link ApplicationContextVariableManager} so it is available everywhere at runtime.
 * 
 * @author Ronny Rinaldi (<a href=mailto:rinaldi.ronny@gmail.com>rinaldi.ronny@gmail.com</a>)
 *
 */
public class WhoIsOnlineJob {
	
	private static final Logger log = Logger.getLogger(WhoIsOnlineJob.class);
	
	@Transactional(readOnly=true, propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate=300000)
	public void execute() {
		long start = System.currentTimeMillis();
		log.info("WHOISONLINEJOB starts at " + DateUtils.getMYSQLDateTime(new DateTime(start)));
		ApplicationContextVariableManager.refreshAllJoined();
		long end = System.currentTimeMillis();
		log.info("WHOISONLINEJOB ends at " + DateUtils.getMYSQLDateTime(new DateTime(end)));
		log.info("WHOISONLINEJOB work time: " + (new Float(end - start) / 1000));
	}

}
