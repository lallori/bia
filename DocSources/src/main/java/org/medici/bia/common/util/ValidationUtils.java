/*
 * ValidationUtils.java
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
package org.medici.bia.common.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

/**
 * This is an extension of Spring Framework's ValidationUtils to implements
 * advanced validations. 
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 * @since 2010
 * @see org.springframework.validation.ValidationUtils
 *
 */
public class ValidationUtils extends org.springframework.validation.ValidationUtils {
	/**
	 * Reject the given field with the given error code if the value is empty
	 * or not contains alphabetic charactes.
	 * <p>An 'empty' value in this context means either <code>null</code>,
	 * the empty string "", or consisting wholly of whitespace.
	 * <p>The object whose field is being validated does not need to be passed
	 * in because the {@link Errors} instance can resolve field values by itself
	 * (it will usually hold an internal reference to the target object).
	 * @param errors the <code>Errors</code> instance to register errors on
	 * @param field the field name to check
	 * @param errorCode the error code, interpretable as message key
	 */
	public static void rejectIfEmptyOrNotAlphaCharactes(Errors errors, String field, String errorCode) {
		rejectIfEmptyOrNotAlphaCharactes(errors, field, errorCode, null, null);
	}

	/**
	 * Reject the given field with the given error code, error arguments
	 * and default message if the value is empty or not contains alphabetic charactes.
	 * <p>An 'empty' value in this context means either <code>null</code>,
	 * the empty string "", or consisting wholly of whitespace.
	 * <p>The object whose field is being validated does not need to be passed
	 * in because the {@link Errors} instance can resolve field values by itself
	 * (it will usually hold an internal reference to the target object).
	 * @param errors the <code>Errors</code> instance to register errors on
	 * @param field the field name to check
	 * @param errorCode the error code, interpretable as message key
	 * @param errorArgs the error arguments, for argument binding via MessageFormat
	 * (can be <code>null</code>)
	 * @param defaultMessage fallback default message
	 */
	public static void rejectIfEmptyOrNotAlphaCharactes(Errors errors, String field, String errorCode, Object[] errorArgs, String defaultMessage) {

		Assert.notNull(errors, "Errors object must not be null");
		Object value = errors.getFieldValue(field);
		if (value == null ||!StringUtils.isAlpha(value.toString())) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
}
