/*
 * EditEmailPropertiesCommand.java
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
package org.medici.bia.command.admin;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
public class EditEmailPropertiesCommand {
	private String activationSubject;
	private String activationText;
	private String resetUserPasswordSubject;
	private String resetUserPasswordText;
	/**
	 * @return the activationSubject
	 */
	public String getActivationSubject() {
		return activationSubject;
	}
	/**
	 * @param activationSubject the activationSubject to set
	 */
	public void setActivationSubject(String activationSubject) {
		this.activationSubject = activationSubject;
	}
	/**
	 * @return the activationText
	 */
	public String getActivationText() {
		return activationText;
	}
	/**
	 * @param activationText the activationText to set
	 */
	public void setActivationText(String activationText) {
		this.activationText = activationText;
	}
	/**
	 * @return the resetUserPasswordSubject
	 */
	public String getResetUserPasswordSubject() {
		return resetUserPasswordSubject;
	}
	/**
	 * @param resetUserPasswordSubject the resetUserPasswordSubject to set
	 */
	public void setResetUserPasswordSubject(String resetUserPasswordSubject) {
		this.resetUserPasswordSubject = resetUserPasswordSubject;
	}
	/**
	 * @return the resetUserPasswordText
	 */
	public String getResetUserPasswordText() {
		return resetUserPasswordText;
	}
	/**
	 * @param resetUserPasswordText the resetUserPasswordText to set
	 */
	public void setResetUserPasswordText(String resetUserPasswordText) {
		this.resetUserPasswordText = resetUserPasswordText;
	}
}
