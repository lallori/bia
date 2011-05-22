/**
*  Ajax Page Turner for jQuery, version 1.0
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
 * Last Review: 02/27/2011
*/
(function ($) {

    $.documentExplorerForm = {};

    $.documentExplorerForm.defaultParams = {
        "searchUrl":  ""   // Show scrollbars?
    };

    $.fn.documentExplorerForm = function (params) {
    	var functionParams = $.extend($.documentExplorerForm.defaultParams, params);

        // Add an onClick behavior to this element
        $(this).submit(function (event) {
            // Prevent the browser's default onClick handler
            event.preventDefault();
            
            var parameters = '';
            $(this).find('input').each(function() {
            	parameters += $(this).attr('id') + '=' + $(this).val() + '&';
            });
			$.get(functionParams["searchUrl"], parameters,
				function(data){
					$("#targetframe").html('');
					
					iip = new IIP( "targetframe", {
						server: functionParams["proxyIIPImage"],
						image: data.imageCompleteName,
						credit: '&copy; copyright or information message', 
						zoom: 1,
						showNavButtons: true,
						render: 'random'
					});

					if (data.previousPage == '') {
						$("#previous").removeAttr('href');
					} else {
						$("#previous").attr('href', data.previousPage);
					}
					if (data.nextPage == '') {
						$("#next").removeAttr('href');
					} else {
						$("#next").attr('href', data.nextPage);
					}
				}
			);

        });
        return $;
    };

    /** DOCUMENT EXPLORER FOR DOM ELEMENT **/
    $.documentExplorer = {};

    $.documentExplorer.defaultParams = {
    	checkDocumentDigitizedUrl	: "",
    	showExplorerDocumentUrl     : "",
    };

    $.fn.documentExplorer = function (params) {
    	var options = $.extend($.documentExplorer.defaultParams, params);
    	var documentDigitized = false;
    	$.ajax({ type:"GET", url:options["checkDocumentDigitizedUrl"], async:false, success:function(data) {
    		if (data.digitized == "true") {
    			documentDigitized = true;
    		}
    	}
		});

    	if (documentDigitized == true) {
        	$( "#tabs" ).tabs( "add" , options["showExplorerDocumentUrl"], "Document Explorer</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
        	$("#tabs").tabs("select", $("#tabs").tabs("length")-1);
    	}
    	
    	return $;
    };

})(jQuery);