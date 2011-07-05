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
 * Last Review: 11/26/2010
*/
(function ($) {

    $.pageTurnerForm = {};

    $.pageTurnerForm.defaultParams = {
        "searchUrl":  "",   // Show scrollbars?
        "IIPImageServer": "/mview/ReverseProxyIIPImage.do",
        "imagePrefix": "/DocSources/images/mview", 
        "status":      "no"     // Show the status bar?
    };

    $.fn.pageTurnerForm = function (params) {
    	var functionParams = $.extend($.pageTurnerForm.defaultParams, params);

        // Loop over all matching elements
        this.each(function (){

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
						var credit = 'Folio n. ' + data.imageProgTypeNum;
						
						if (data.imageRectoVerso == 'R') {
							credit += ' Recto';
						} else {
							credit += ' Verso';
						}

						iip = new IIP( "targetframe", {
							server: functionParams["IIPImageServer"],
							image: data.imageCompleteName,
							prefix: functionParams["imagePrefix"],
							credit: credit, 
							scale: 20.0,
							navigation: true,
							showNavWindow: true,
							showNavImage: true, // this property hide navigation image
							showNavButtons: true,
							winResize: true,
							zoom: 3
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
        });

        return $;
    };

    $.pageTurnerPage = {};

    $.pageTurnerPage.defaultParams = {
    	"targetFrame":  "targetframe",
    	"IIPImageServer": "/mview/ProxyIIPImage.do",
    	"imagePrefix": "/DocSources/images/mview"
    };

    $.fn.pageTurnerPage = function (params) {
        var functionParams = $.extend($.pageTurnerPage.defaultParams, params);
        
        // Loop over all matching elements
        this.each(function (){

            // Add an onClick behavior to this element
            $(this).click(function (event) {
                // Prevent the browser's default onClick handler
                event.preventDefault();
                
				$.ajax({ type:"GET", url:$j(this).attr("href"), async:false, success:function(data) {
					$("#targetframe").html('');
					var credit = 'Folio n. ' + data.imageProgTypeNum;
					if (data.imageRectoVerso == 'R') {
						credit += ' Recto';
					} else {
						credit += ' Verso';
					}
					
					iip = new IIP( "targetframe", {
						server: functionParams["IIPImageServer"],
						image: data.imageCompleteName,
						prefix: functionParams["imagePrefix"],
						credit: credit, 
						scale: 20.0,
						navigation: true,
						showNavWindow: true,
						showNavImage: true, // this property hide navigation image
						showNavButtons: true,
						winResize: true,
						zoom: 3
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
				});
            });
        });

        return $;
    };

})(jQuery);