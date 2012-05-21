/**
*  CSS Loader for jQuery, version 1.0
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
 * Last Review: 03/06/2011
*/
(function ($) {

    $.cssLoader = {};

    $.cssLoader.defaultParams = {
        "contextPath" :  "", /** This is contextPath should be setted to /DocSources/                                      **/
        "stylePath"   :  "", /** This is the position of style sheets home.                                                **/
        "styleSheets" :  {}, /** This is stylesheets array to load. Every record should consider his relative to stylePath **/
        "forceResolution":  0 /** This parameter is used to force css loading to a specific resolution.                 **/ 
    };

    $.fn.cssLoader = function (params) {
    	var functionParams = $.extend($.cssLoader.defaultParams, params);
    	if (functionParams["forceResolution"] > 0) {
    		for (i=0; i<functionParams["styleSheets"].length; i++) {
	    		$('head').append('<link rel="stylesheet" href="' + functionParams["contextPath"] + 
	    		functionParams["stylePath"] + functionParams["forceResolution"] +'/' + functionParams["styleSheets"][i] + '" type="text/css" />');
    		}
    	} else if (screen.width>=1280) {
    		for (i=0; i<functionParams["styleSheets"].length; i++) {
	    		$('head').append('<link rel="stylesheet" href="' + functionParams["contextPath"] + 
	    		functionParams["stylePath"] + '1280/' + functionParams["styleSheets"][i] + '" type="text/css" />');
    		}
    	} else {
    		for (i=0; i<functionParams["styleSheets"].length; i++) {
	    		$('head').append('<link rel="stylesheet" href="' + functionParams["contextPath"] + 
	    		functionParams["stylePath"] + '/1024/' + functionParams["styleSheets"][i] + '" type="text/css" />');
    		}
    	}
        return $;
    };

})(jQuery);
