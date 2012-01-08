/*
 * DocSourcesListener.java
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
package org.medici.docsources.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 * Published when the ApplicationContext is initialized or refreshed, for example, 
 * using the refresh() method on the ConfigurableApplicationContext interface. 
 * "Initialized" here means that all beans are loaded, post-processor beans are 
 * detected and activated, singletons are pre-instantiated, and 
 * the ApplicationContext object is ready for use. 
 * As long as the context has not been closed, a refresh can be triggered 
 * multiple times, provided that the chosen ApplicationContext actually supports 
 * such "hot" refreshes. For example, XmlWebApplicationContext supports hot 
 * refreshes, but GenericApplicationContext does not.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class DocSourcesListener implements ServletContextListener {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * @return the logger
	 */
	public Logger getLogger() {
		return logger;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		getLogger().info("DocSources STARTED");
	}

	/**
	 * 
	 */
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		getLogger().info("Initiating Application Context close...");
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
		((ConfigurableApplicationContext) webApplicationContext).close();
		getLogger().info("Finished Application Context close"); 
		getLogger().info("DocSources STOPPED");
	}

}
