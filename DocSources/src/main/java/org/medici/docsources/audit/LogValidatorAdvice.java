/*
 * LogValidatorAdvice.java
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

package org.medici.docsources.audit;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.validation.BeanPropertyBindingResult;

/**
 * 
 * 
 * One of the many strengths of AOP is that it allows you to separate your
 * non-business concerns from your business logic. It also helps you in mundane
 * tasks such putting a logging logic in each of your method or place a
 * try-catch statement on each method. It's better to have a slower app that is
 * maintainable and scalable than having a faster app that would give you hell
 * in maintenance. Slowness could be compensated in many ways, such as upgrading
 * hardware and etc.
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * 
 */
public class LogValidatorAdvice implements AfterReturningAdvice, ThrowsAdvice {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * After returning advice. 
	 */
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if ((args == null) || (args.length != 2)) {
			logger.error("Advice configuration error. Method's sign is not public void validate(Object object, Errors errors).");
			return;
		}

		if (!args[1].getClass().getName().equals("org.springframework.validation.BeanPropertyBindingResult")) {
			logger.error("Advice configuration error. The second paramter is not of type org.springframework.validation.BeanPropertyBindingResult");
		}

		BeanPropertyBindingResult beanPropertyBindingResult = (BeanPropertyBindingResult) args[1];

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(target.getClass().getName());
		stringBuffer.append(":");
		stringBuffer.append(method.getName());
		
		if (beanPropertyBindingResult.hasErrors()) {
			stringBuffer.append("Validation object KO (" + beanPropertyBindingResult.getAllErrors().get(0) + ")");
			logger.error(stringBuffer.toString());
		} else {
			stringBuffer.append("Validation object OK.");
			logger.info(stringBuffer.toString());
		}
	}

	/**
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param ex
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Throwable ex) {
		logger.error("Exception is: " + ex.getMessage());
	}
}
