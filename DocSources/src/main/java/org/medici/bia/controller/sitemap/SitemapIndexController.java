/*
 * SitemapIndexController.java
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
 * 
 */
package org.medici.bia.controller.sitemap;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.medici.bia.common.property.ApplicationPropertyManager;
import org.medici.bia.domain.SitemapIndex;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.sitemap.SitemapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.redfin.sitemapgenerator.SitemapIndexGenerator;

/**
 * Controller for site map index".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 * 
 */
@Controller
@RequestMapping("/sitemap/SitemapIndex")
public class SitemapIndexController {
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired
	private SitemapService sitemapService;

	/**
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void setupPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		File sitemapIndexFile = new File(ApplicationPropertyManager.getApplicationProperty("path.tmpdir") + "sitemapIndex" + UUID.randomUUID() + ".xml");

		try {
			List<SitemapIndex> list = getSitemapService().getSitemapIndex();

			String website = ApplicationPropertyManager.getApplicationProperty("website.protocol") + "://" + ApplicationPropertyManager.getApplicationProperty("website.domain");

			SitemapIndexGenerator sitemapIndexGenerator = new SitemapIndexGenerator(website, sitemapIndexFile);
			for (int i=0; i<list.size(); i++) {
				sitemapIndexGenerator.addUrl(list.get(i).getLocation());
			}
			// Write temporary file...
			sitemapIndexGenerator.write();

			httpServletResponse.setContentType("application/xml");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			servletOutputStream.println(FileUtils.readFileToString(sitemapIndexFile));
			httpServletResponse.getOutputStream().flush();
		} catch (ApplicationThrowable applicationThrowable) {
			logger.error("ApplicationThrowable error: " + applicationThrowable.getMessage());
		} catch (MalformedURLException e) {
			logger.error("Error: " + e.getMessage());
		} catch (IOException e) {
			logger.error("Error: " + e.getMessage());
		} finally {
			sitemapIndexFile.delete();
		}
	}

	/**
	 * 
	 * @param sitemapService
	 */
	public void setSitemapService(SitemapService sitemapService) {
		this.sitemapService = sitemapService;
	}

	/**
	 * 
	 * @return
	 */
	public SitemapService getSitemapService() {
		return sitemapService;
	}

}