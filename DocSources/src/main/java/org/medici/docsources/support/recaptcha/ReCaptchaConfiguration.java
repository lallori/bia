/*
 * ReCaptchaConfiguration.java
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
package org.medici.docsources.support.recaptcha;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class ReCaptchaConfiguration {
	private String privateKey;
	private String publicKey;
	private Integer siteId;

	/**
	 * 
	 * @return
	 */
	public String getPrivateKey() {
		return privateKey;
	}

	/**
	 * 
	 * @return
	 */
	public String getPublicKey() {
		return publicKey;
	}

	public Integer getSiteId() {
		return siteId;
	}

	/**
	 * 
	 * @param privateKey
	 */
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * 
	 * @param publicKey
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

}
