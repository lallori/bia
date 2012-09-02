/*
 * LogServiceAdvice.java
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

package org.medici.bia.audit;

import java.lang.reflect.Method;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.medici.docsources.common.util.ClassUtils;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;

/**
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
public class LogDAOAdvice implements AfterReturningAdvice, ThrowsAdvice {
	private final Logger logger = Logger.getLogger(this.getClass());

	public LogDAOAdvice() {
	}

	/**
	 * 
	 */
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(target.getClass().getName());
		stringBuilder.append(":");
		stringBuilder.append(method.getName());
		stringBuilder.append(" OK ");
		appendReturns(stringBuilder, returnValue);

		logger.info(stringBuilder.toString());
	}

	/**
	 * 
	 * @param method
	 * @param args
	 * @param target
	 * @param throwable
	 */
	public void afterThrowing(Method method, Object[] args, Object target, Throwable throwable) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(target.getClass().getName());
		stringBuilder.append(":");
		stringBuilder.append(method.getName());
		stringBuilder.append(" KO ");
		appendThrowable(stringBuilder, throwable);

		logger.error(stringBuilder.toString());
	}

	/**
	 * 
	 * @param stringBuilder
	 * @param args
	 */
	@SuppressWarnings("unused")
	private void appendParameters(StringBuilder stringBuilder, Object[] args) {
		stringBuilder.append(" - Parameters : ");
		for (int i = 0; i < args.length; i++) {
			stringBuilder.append(ClassUtils.toString(args[i]));
			stringBuilder.append(" - ");
		}
		stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
	}

	/**
	 * 
	 * @param stringBuilder
	 * @param returnValue
	 */
	private void appendReturns(StringBuilder stringBuilder, Object returnValue) {
		if (returnValue != null) {
			stringBuilder.append(" - Return Object");
			// WE COMMENT THE COMPLETE OBJECT CONVERSION stringBuilder.append(ClassUtils.toString(returnValue));
			//stringBuilder.append(returnValue.toString());
		}
	}

	/**
	 * 
	 * @param stringBuilder
	 * @param target
	 * 
	 * @depracted
	 */
	@SuppressWarnings("unused")
	private void appendTarget(StringBuilder stringBuilder, Object target) {
		stringBuilder.append(" - Target : ");
		stringBuilder.append(target.getClass().getName());
	}

	/**
	 * 
	 * @param stringBuilder
	 * @param throwable
	 */
	private void appendThrowable(StringBuilder stringBuilder, Throwable throwable) {
		stringBuilder.append(" - Exception : ");
		stringBuilder.append(ExceptionUtils.getStackTrace(throwable));
	}
}
