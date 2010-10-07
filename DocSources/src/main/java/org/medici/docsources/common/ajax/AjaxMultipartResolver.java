/*
 * AjaxMultipartResolver.java
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
package org.medici.docsources.common.ajax;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
 
public class AjaxMultipartResolver extends CommonsMultipartResolver {
    private HttpServletRequest httpServletRequest;
    private UploadProgressListener uploadProgressListener;
 
    /**
	 * @return the httpServletRequest
	 */
	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public UploadProgressListener getUploadProgressListener() {
        return uploadProgressListener;
    }

	@Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
		FileUpload fileUpload = super.newFileUpload(fileItemFactory);
		
		if (getUploadProgressListener() != null){
			//first we remove previous uploadInfo from user session
			getHttpServletRequest().getSession().removeAttribute("uploadInfoBean");
			getUploadProgressListener().setHttpSession(getHttpServletRequest().getSession());
			
			getUploadProgressListener().setUploadInfoBean(new UploadInfoBean());
			getUploadProgressListener().getUploadInfoBean().setFieldname("file");
			getUploadProgressListener().getUploadInfoBean().setUpload_id(getHttpServletRequest().getParameter("idUpload"));
			getUploadProgressListener().getUploadInfoBean().setBytes_uploaded(new Long(0));
			getUploadProgressListener().getUploadInfoBean().setFiles_uploaded(new Integer(0));

			getUploadProgressListener().getUploadInfoBean().setTime_start(System.currentTimeMillis());
			fileUpload.setProgressListener(getUploadProgressListener());
		}
		
		
		return fileUpload;
    }
 
    public MultipartHttpServletRequest resolveMultipart(HttpServletRequest request) throws MultipartException {
        try {
            setHttpServletRequest(request);
            return super.resolveMultipart(request);
        } catch (Exception ex) {
            throw new MultipartException(ex.getMessage());
        }
    }
 
    /**
	 * @param httpServletRequest the httpServletRequest to set
	 */
	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}
    
    public void setUploadProgressListener(UploadProgressListener uploadProgressListener) {
        this.uploadProgressListener = uploadProgressListener;
    }
}
 
