/*
 * ShowPortraitPersonController.java
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
package org.medici.bia.controller.peoplebase;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.medici.bia.command.peoplebase.ShowPortraitPersonCommand;
import org.medici.bia.domain.People;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.peoplebase.PeopleBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for action "Show Portrait Person".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/src/peoplebase/ShowPortraitPerson")
public class ShowPortraitPersonController {
	@Autowired
	private PeopleBaseService peopleBaseService;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @return
	 */
	public PeopleBaseService getPeopleBaseService() {
		return peopleBaseService;
	}

	/**
	 * 
	 * @param peopleBaseService
	 */
	public void setPeopleBaseService(PeopleBaseService peopleBaseService) {
		this.peopleBaseService = peopleBaseService;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Logger getLogger() {
		return logger;
	}

	/**
	 * 
	 * 
	 * @param volumeId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void setupForm(@ModelAttribute("command") ShowPortraitPersonCommand command, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		People person = new People();

		if(command.getPersonId() > 0){
			try {
				person = getPeopleBaseService().findPersonForPortrait(command.getPersonId());
				
				if (person.getPortrait()) {
					BufferedImage bufferedImage = getPeopleBaseService().getPortraitPerson(person.getPortraitImageName());
				    httpServletResponse.setContentType("image/jpeg");
					ImageIO.write(bufferedImage, "jpg", httpServletResponse.getOutputStream());

					httpServletResponse.getOutputStream().flush();
				} else {
					BufferedImage bufferedImage = getPeopleBaseService().getPortraitPersonDefault();
				    httpServletResponse.setContentType("image/jpeg");
					ImageIO.write(bufferedImage, "jpg", httpServletResponse.getOutputStream());				
				}
			} catch (IOException ioException){
				getLogger().error("error on reading image", ioException);
				// need to return default image error
			} catch (ApplicationThrowable applicationThrowable){
				getLogger().error("error on reading image", applicationThrowable);
			}
		}
	}
}