/*
 * ApprovationUserMessageJob.java
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

import java.util.List;

import org.apache.log4j.MDC;
import org.medici.bia.domain.ApprovationUser;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class implements the scheduler to perform send message to all admin users
 * to inform new users to be approved.<br>
 * There are two tasks performed :<br>
 * - extracting list of users that needs to be activated.<br>
 * - for every user in this list, job will send the activation mail.<p>
 *
 * The system sends an email to the user which contains a link in it.<p>
 *    
 * The link also contains the ID of the request. The link will be something like 
 * this:<p>
 *  
 *    http://host:port/contextPath/user/ActivateUser.do?uuid=7a68ff13-7aed-4d59-82a1-78b0463af9d5 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Transactional(readOnly=true)
public class ApprovationUserMessageJob {
	@Autowired
	private AdminService adminService;
	
	/**
	 * 
	 */
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Scheduled(fixedRate=300000)
	public void execute() {
		MDC.put("username", "threadapprovationuser");
		try {
			List<ApprovationUser> approvationUsers = getAdminService().findUsersToApprove();

			getAdminService().sendApprovationMessage(approvationUsers);
		} catch (ApplicationThrowable ath) {
			
		}
	}

	/**
	 * 
	 * @param adminService
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * 
	 * @return
	 */
	public AdminService getAdminService() {
		return adminService;
	}


}