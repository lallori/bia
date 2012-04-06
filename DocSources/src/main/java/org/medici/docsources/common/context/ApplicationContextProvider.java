/*
 * a.java
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
package org.medici.docsources.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Lorenzo Pasquinelli (<a href=mailto:l.pasquinelli@gmail.com>l.pasquinelli@gmail.com</a>)
 *
 */
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext context;
 
    public static ApplicationContext getContext() {
        if ( context != null ) {
            return context;
        } else {
            throw new IllegalStateException( "The Spring application context is not yet available. " +
                                                     "The call to this method has been performed before the application context " +
                                                     "provider was initialized" );
        }
    }
 
    @Override
    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        if ( context == null ) {
            ApplicationContextProvider.context = applicationContext;
        } else {
            throw new IllegalStateException( "The application context provider was already initialized. " +
                                                     "It is illegal to place try to initialize the context provider twice or create " +
                                                     "two different beans of this type (even if the contexts are different)" );
        }
    }
}