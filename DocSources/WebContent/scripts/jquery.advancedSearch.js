/**
*  Share Button graphic effect for jQuery, version 1.0
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
 * Last Review: 02/18/2011
*/
(function ($) {

	$(".buttonShareLink").hover(function(){
		var iconName = $(this).find("img").attr("src");
		var origen =  $(this).find("img").attr("src");
		$(this).find("img").attr("src");
		$(this).find("span").attr({"style": 'display:inline'});
		$(this).find("span").animate({opacity: 1, top: "-60"}, {queue:false, duration:400});
	}, function(){
		var iconName = $(this).find("img").attr("src");
		var origen =  $(this).find("img").attr("src");
		$(this).find("img").attr("src");
		$(this).find("span").animate({opacity: 0, top: "-50"}, {queue:false, duration:400, complete: function(){
			$(this).attr({"style": 'display:none'});
		}});
	});

	return $;
})(jQuery);
