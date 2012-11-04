/*
 * EditUserEmailCommand.java
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
package org.medici.bia.command.admin;

/**
 * Command bean for action "Edit User mail".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 * @see org.ShowUserController.docsources.controller.user.CreateUserController
 */
public class EditUserEmailCommand {
	/** User's Account **/
	private String account;
	
	private String mail;
	private Boolean mailHide;
	private Boolean receiveNotificationByMail;

	/**
	 * @return the account
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * @param account the account to set
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	/**
	 * @return the mailHide
	 */
	public Boolean getMailHide() {
		return mailHide;
	}
	/**
	 * @param mailHide the mailHide to set
	 */
	public void setMailHide(Boolean mailHide) {
		this.mailHide = mailHide;
	}
	public void setReceiveNotificationByMail(Boolean receiveNotificationByMail) {
		this.receiveNotificationByMail = receiveNotificationByMail;
	}
	public Boolean getReceiveNotificationByMail() {
		return receiveNotificationByMail;
	}

}
