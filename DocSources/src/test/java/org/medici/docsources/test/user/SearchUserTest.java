/*
 * SearchUserTest.java
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
package org.medici.docsources.test.user;

import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.medici.docsources.command.user.SearchUserCommand;
import org.medici.docsources.controller.user.SearchUserController;
import org.medici.docsources.service.user.UserService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.ModelAndView;

/**
 * This class implements test case for simple search user.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = 
{
		"file:WebContent/WEB-INF/config/applicationAOP.xml",
		"file:WebContent/WEB-INF/config/applicationCaptcha.xml",
		"file:WebContent/WEB-INF/config/applicationControllers.xml",
		"file:WebContent/WEB-INF/config/applicationDS.xml",
		"file:WebContent/WEB-INF/config/applicationMail.xml",
		"file:WebContent/WEB-INF/config/applicationSecurity.xml",
		"file:WebContent/WEB-INF/config/applicationServices.xml",
		"file:WebContent/WEB-INF/config/applicationTemplates.xml",
		"file:WebContent/WEB-INF/config/applicationValidators.xml"
}
)

public class SearchUserTest extends AbstractJUnit4SpringContextTests {
	private SearchUserCommand command;
	private SearchUserController controller;
	private Logger logger;
	private ModelMap model;
	private UserService userService;
	private Validator validator;

	/**
	 * @return the command
	 */
	public SearchUserCommand getCommand() {
		return command;
	}

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * @return the userService
	 */
	public UserService getUserService() {
		return userService;
	}

	/**
	 * @return the validator
	 */
	public Validator getValidator() {
		return validator;
	}

	/**
	 * This method is the user test for search function.
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void search() throws Exception {
		/*BindingResult bindingResult = new BeanPropertyBindingResult(getCommand(), "command");

		ModelAndView modelAndView = controller.processSubmit(getCommand(), bindingResult);
		getLogger().debug(model);
		getLogger().debug(modelAndView);

		Assert.assertFalse("Model should not be empty.", model.isEmpty());
		Assert.assertFalse("Destination View should not be empty.", modelAndView.isEmpty());        
		Errors errors = (Errors) model.get(BindingResult.MODEL_KEY_PREFIX + "environment");
		Assert.assertNull("There are errors where there should not be", errors);
		Assert.assertNotNull("User list is null.", model.get("userList"));*/
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(SearchUserCommand command) {
		this.command = command;
	}

	/**
	 * @param logger the logger to set
	 */
	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * This method prepare the enviroment for test.
	 * This will makes :
	 * - istantiate logger class;
	 * - Prepared particular objects for our Controller
	 * -
	 * 
	 */
	@Before
	public void setUp() {
		logger = Logger.getLogger(this.getClass());

		// Prepare data structures needed
		model = new ExtendedModelMap();

		// Prepare controller, it needs to receive service and validator from 
		// applicationContext. We can't use autowire :-( 
		controller = new SearchUserController();
		controller.setUserService((UserService)applicationContext.getBean("userService"));
		controller.setValidator((Validator)applicationContext.getBean("searchUserValidator"));

		// Prepare command bean with parameters
		setCommand(new SearchUserCommand());
		getCommand().setAlias("Lorenzo");
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * @param validator the validator to set
	 */
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

}
