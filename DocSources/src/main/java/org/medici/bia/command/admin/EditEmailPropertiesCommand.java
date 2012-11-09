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
	private String mailServerHost;
	private Integer mailServerPort;
	private String mailServerUsername;
	private String mailServerPassword;
	private Boolean mailSmtpAuth;
	private Boolean mailSmtpStarttlsEnable;
	private String mailTransportProtocol;

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

	/**
	 * @return the mailServerHost
	 */
	public String getMailServerHost() {
		return mailServerHost;
	}

	/**
	 * @param mailServerHost the mailServerHost to set
	 */
	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	/**
	 * @return the mailServerPort
	 */
	public Integer getMailServerPort() {
		return mailServerPort;
	}

	/**
	 * @param mailServerPort the mailServerPort to set
	 */
	public void setMailServerPort(Integer mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	/**
	 * @return the mailServerUsername
	 */
	public String getMailServerUsername() {
		return mailServerUsername;
	}

	/**
	 * @param mailServerUsername the mailServerUsername to set
	 */
	public void setMailServerUsername(String mailServerUsername) {
		this.mailServerUsername = mailServerUsername;
	}

	/**
	 * @return the mailServerPassword
	 */
	public String getMailServerPassword() {
		return mailServerPassword;
	}

	/**
	 * @param mailServerPassword the mailServerPassword to set
	 */
	public void setMailServerPassword(String mailServerPassword) {
		this.mailServerPassword = mailServerPassword;
	}

	/**
	 * @return the mailSmtpAuth
	 */
	public Boolean getMailSmtpAuth() {
		return mailSmtpAuth;
	}

	/**
	 * @param mailSmtpAuth the mailSmtpAuth to set
	 */
	public void setMailSmtpAuth(Boolean mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	/**
	 * @return the mailSmtpStarttlsEnable
	 */
	public Boolean getMailSmtpStarttlsEnable() {
		return mailSmtpStarttlsEnable;
	}

	/**
	 * @param mailSmtpStarttlsEnable the mailSmtpStarttlsEnable to set
	 */
	public void setMailSmtpStarttlsEnable(Boolean mailSmtpStarttlsEnable) {
		this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
	}

	/**
	 * @return the mailTransportProtocol
	 */
	public String getMailTransportProtocol() {
		return mailTransportProtocol;
	}

	/**
	 * @param mailTransportProtocol the mailTransportProtocol to set
	 */
	public void setMailTransportProtocol(String mailTransportProtocol) {
		this.mailTransportProtocol = mailTransportProtocol;
	}
}
