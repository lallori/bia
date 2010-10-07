/*
 * UploadProgressBarAjaxController.java
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
package org.medici.docsources.controller.common;

import javax.servlet.http.HttpSession;

import org.medici.docsources.common.ajax.UploadInfoBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
@Controller
public class UploadProgressBarAjaxController {

	/*
	   "upload_id":"4c8e76ca1ff22",
	   "fieldname":"ulfile1",
	   "filename":"DSCF1046.JPG",
	   "time_start":"1284405013",
	   "time_last":"1284405016",
	   "speed_average":"36018",
	   "speed_last":"51190",
	   "bytes_uploaded":"108056",
	   "bytes_total":"1956254",
	   "files_uploaded":"0",
	   "est_sec":"51"
	 */
	/**
	 * 
	 * @param alias
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/common/upload.json", method = RequestMethod.GET)
	public String progressBar(@RequestParam("idUpload") Long idUpload, HttpSession session, Model model) {
		UploadInfoBean uploadInfoBean = (UploadInfoBean) session.getAttribute("uploadInfoBean");

		if (uploadInfoBean != null) {
			model.addAttribute("upload_id",uploadInfoBean.getUpload_id());
			model.addAttribute("fieldname",uploadInfoBean.getFieldname());
			model.addAttribute("filename", uploadInfoBean.getFilename());
			model.addAttribute("time_start",uploadInfoBean.getTime_start().toString());
			model.addAttribute("time_last",uploadInfoBean.getTime_last().toString());
			model.addAttribute("speed_average",uploadInfoBean.getSpeed_average().toString());
			model.addAttribute("speed_last",uploadInfoBean.getSpeed_last().toString());
			model.addAttribute("bytes_uploaded", uploadInfoBean.getBytes_uploaded().toString());
			model.addAttribute("bytes_total",uploadInfoBean.getBytes_total().toString());
			model.addAttribute("files_uploaded", uploadInfoBean.getFiles_uploaded().toString());
			model.addAttribute("est_sec", uploadInfoBean.getEst_sec().toString());

			if (uploadInfoBean.getBytes_total().equals(uploadInfoBean.getBytes_uploaded())) {
				session.removeAttribute("uploadInfoBean");
			}
		} else {
			//model.addAttribute("null");
		}
		return "OK";
	}
}

