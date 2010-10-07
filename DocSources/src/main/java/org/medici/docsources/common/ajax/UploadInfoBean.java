/*
 * UploadBeanInfo.java
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

import org.medici.docsources.common.util.ClassUtils;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class UploadInfoBean {
	private Long bytes_total;
	private Long bytes_uploaded;
	private Integer est_sec;
	private String fieldname;
	private String filename;
	private Integer files_uploaded;
	private Double speed_average;
	private Long speed_last;
	private Long time_last;
	private Long time_start;
	private String upload_id;
	/**
	 * @return the bytes_total
	 */
	public Long getBytes_total() {
		return bytes_total;
	}
	/**
	 * @return the bytes_uploaded
	 */
	public Long getBytes_uploaded() {
		return bytes_uploaded;
	}
	/**
	 * @return the est_sec
	 */
	public Integer getEst_sec() {
		return est_sec;
	}
	/**
	 * @return the fieldname
	 */
	public String getFieldname() {
		return fieldname;
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @return the files_uploaded
	 */
	public Integer getFiles_uploaded() {
		return files_uploaded;
	}
	/**
	 * @return the speed_average
	 */
	public Double getSpeed_average() {
		return speed_average;
	}
	/**
	 * @return the speed_last
	 */
	public Long getSpeed_last() {
		return speed_last;
	}
	/**
	 * @return the time_last
	 */
	public Long getTime_last() {
		return time_last;
	}
	/**
	 * @return the time_start
	 */
	public Long getTime_start() {
		return time_start;
	}
	/**
	 * @return the upload_id
	 */
	public String getUpload_id() {
		return upload_id;
	}
	/**
	 * @param bytes_total the bytes_total to set
	 */
	public void setBytes_total(Long bytes_total) {
		this.bytes_total = bytes_total;
	}
	/**
	 * @param bytes_uploaded the bytes_uploaded to set
	 */
	public void setBytes_uploaded(Long bytes_uploaded) {
		this.bytes_uploaded = bytes_uploaded;
	}
	/**
	 * @param est_sec the est_sec to set
	 */
	public void setEst_sec(Integer est_sec) {
		this.est_sec = est_sec;
	}
	/**
	 * @param fieldname the fieldname to set
	 */
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @param files_uploaded the files_uploaded to set
	 */
	public void setFiles_uploaded(Integer files_uploaded) {
		this.files_uploaded = files_uploaded;
	}
	/**
	 * @param speed_average the speed_average to set
	 */
	public void setSpeed_average(Double speed_average) {
		this.speed_average = speed_average;
	}
	/**
	 * @param speed_last the speed_last to set
	 */
	public void setSpeed_last(Long speed_last) {
		this.speed_last = speed_last;
	}
	/**
	 * @param time_last the time_last to set
	 */
	public void setTime_last(Long time_last) {
		this.time_last = time_last;
	}
	/**
	 * @param time_start the time_start to set
	 */
	public void setTime_start(Long time_start) {
		this.time_start = time_start;
	}
	/**
	 * @param upload_id the upload_id to set
	 */
	public void setUpload_id(String upload_id) {
		this.upload_id = upload_id;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("[");
		stringBuffer.append(ClassUtils.toString(this));
		stringBuffer.append("]");
		return stringBuffer.toString();
	}

}
