/*
 * UploadProgressListener.java
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
package org.medici.bia.common.ajax;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.log4j.Logger;

/**
 * Listener for upload progress bar.
 * 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class UploadProgressListener implements ProgressListener {
	public static final String STATUS_DONE = "DONE";
    public static final String STATUS_FAILED = "FAILED";
    public static final String STATUS_MAX_SIZE_EXCEEDS ="";
	public static final String STATUS_UPLOADING = "UPLOADING";
	private HttpSession httpSession;

	private final Logger logger = Logger.getLogger(this.getClass());
	private UploadInfoBean uploadInfoBean;
	
	/**
	 * @return the httpSession
	 */
	public HttpSession getHttpSession() {
		return httpSession;
	}

	/**
	 * @return the uploadInfoBean
	 */
	public UploadInfoBean getUploadInfoBean() {
		return uploadInfoBean;
	}

	/**
	 * @param httpSession the httpSession to set
	 */
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	/**
	 * @param uploadInfoBean the uploadInfoBean to set
	 */
	public void setUploadInfoBean(UploadInfoBean uploadInfoBean) {
		this.uploadInfoBean = uploadInfoBean;
	}
	
	public void update(long bytesRead, long pContentLength, int pItems){
		uploadInfoBean.setBytes_total(pContentLength);
		uploadInfoBean.setBytes_uploaded(bytesRead);
		uploadInfoBean.setEst_sec(new Integer(1));
		uploadInfoBean.setSpeed_average(0.0);
		uploadInfoBean.setSpeed_last(new Long(1000));
		uploadInfoBean.setTime_last(System.currentTimeMillis());
		
		logger.info("status upload : " + uploadInfoBean);
		getHttpSession().setAttribute("uploadInfoBean", uploadInfoBean);
	}
}
