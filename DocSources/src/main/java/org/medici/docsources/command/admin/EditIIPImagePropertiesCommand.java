/*
 * EditIIPImagePropertiesCommand.java
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
package org.medici.docsources.command.admin;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 *
 */
public class EditIIPImagePropertiesCommand {
	private String serverFcgiBinPath;
	private String serverHostName;
	private String serverPort;
	private String serverProtocol;
	private String serverVersion;
	
	/**
	 * @return the serverFcgiBinPath
	 */
	public String getServerFcgiBinPath() {
		return serverFcgiBinPath;
	}
	
	/**
	 * @return the serverHostName
	 */
	public String getServerHostName() {
		return serverHostName;
	}
	
	/**
	 * @return the serverPort
	 */
	public String getServerPort() {
		return serverPort;
	}
	
	/**
	 * @return the serverProtocol
	 */
	public String getServerProtocol() {
		return serverProtocol;
	}
	
	/**
	 * @return the serverVersion
	 */
	public String getServerVersion() {
		return serverVersion;
	}
	
	/**
	 * @param serverFcgiBinPath the serverFcgiBinPath to set
	 */
	public void setServerFcgiBinPath(String serverFcgiBinPath) {
		this.serverFcgiBinPath = serverFcgiBinPath;
	}
	
	/**
	 * @param serverHostName the serverHostName to set
	 */
	public void setServerHostName(String serverHostName) {
		this.serverHostName = serverHostName;
	}
	
	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}
	
	/**
	 * @param serverProtocol the serverProtocol to set
	 */
	public void setServerProtocol(String serverProtocol) {
		this.serverProtocol = serverProtocol;
	}
	/**
	 * @param serverVersion the serverVersion to set
	 */
	public void setServerVersion(String serverVersion) {
		this.serverVersion = serverVersion;
	}
}
