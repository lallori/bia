/*
 * ShowPortraitUserController.java
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
package org.medici.bia.controller.user;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.medici.bia.command.user.ShowPortraitUserCommand;
import org.medici.bia.domain.User;
import org.medici.bia.exception.ApplicationThrowable;
import org.medici.bia.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for action "Show Portrait User".
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @author Matteo Doni (<a href=mailto:donimatteo@gmail.com>donimatteo@gmail.com</a>)
 */
@Controller
@RequestMapping("/user/ShowPortraitUser")
public class ShowPortraitUserController {
	@Autowired
	private UserService userService;
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
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
	public void setupForm(@ModelAttribute("command") ShowPortraitUserCommand command, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		User user = null;

		if(command.getAccount() != null){
			try {
				user = getUserService().findUser(command.getAccount());
				
				if (user.getPortrait()) {
					BufferedImage bufferedImage = getUserService().getPortraitUser(user.getPortraitImageName());
				    httpServletResponse.setContentType("image/jpeg");
					ImageIO.write(bufferedImage, "jpg", httpServletResponse.getOutputStream());

					httpServletResponse.getOutputStream().flush();
				} else {
					BufferedImage bufferedImage = getUserService().getPortraitUserDefault();
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