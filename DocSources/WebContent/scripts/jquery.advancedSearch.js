/**
*  Advanced	Search plugin for jQuery, version 1.0
 * 
 * Developed by	Medici Archive Project (2010-2012).
 * 
 * This	file is	part of	DocSources.
 * 
 * DocSources is free software;	you can redistribute it	and/or modify
 * it under the terms of the GNU General Public	License	as published by
 * the Free Software Foundation; either version	2 of the License, or
 * (at your option) any later version.
 * 
 * DocSources is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A	PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51	Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a	special	exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable	to be covered by the GNU General Public	License.
 * This	exception does not however invalidate any other reasons	why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Last	Review:	02/18/2011
*/
(function ($) {

	$.advancedSearchForm = {};

	$.advancedSearchForm.defaults =	{
	};

	$.fn.advancedSearchForm = function (options) {
		var options = $.extend($.advancedSearchForm.defaults, options);

		$j('.remove').die();
		$j('.remove').live('click', function() {
			// we search for next form condition 
			if ($j(this).parent().next().attr('class') == 'andOrNotAdvancedSearch') {
				$j(this).parent().next().remove();
			} else if ($j(this).parent().prev().attr('class') == 'andOrNotAdvancedSearch') {
				$j(this).parent().prev().remove();
			}
			// we remove parent	container element
			$j(this).parent().remove();
			return false;
		});
		
		// We manage submit	button to populate right frame
		$(this).submit(function	(event) {
			//Prevent the browser's	default	onClick	handler
			event.preventDefault();
			// Extract form	Name
			var formName = $(this).attr('id');
			//Extracting field on which	this form works	on.
			var fieldName =	formName.substring(0, formName.indexOf('SearchForm'));

			console.log("AdvancedSearchForm	started. Form Name : " + formName);
			console.log("Field Name	: " + fieldName);

			var searchType = "";
			if (formName.indexOf("date") >=	0) {
				searchType = $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').text();
			} else {
				searchType = $('#' + formName).find('#category').val();
				if ($('#' + formName).find('#' + fieldName + 'Type').length==1) {
					searchType += '	in ' + $('#' + formName).find('#' + fieldName + 'Type').find('option:selected').text();	 
				}
			}
			console.log("Search	Type : " + formName);

			var searchWord = "";
			var hiddenValue = "";

			// Date	form is	composed of	three fields.
			if ($('#' + formName).find('#' + fieldName + 'Type').size()	> 0) {
				if ($(this).find("option:selected").val() == 'Between') {
					if (formName.indexOf("volume") >= 0) {
						searchWord = $('#' + formName).find('#' + fieldName).val();
						searchWord += ', ' + $('#' + formName).find('#' + fieldName + 'Between').val();
						hiddenValue = $(this).find("option:selected").val() + "|" + $('#' + formName).find('#' + fieldName).val() + '|' + $('#' + formName).find('#' + fieldName + 'Between').val();
						//Reset	input fields 
						$('#' + formName).find('#' + fieldName).val("");
						$('#' + formName).find('#' + fieldName + 'Between').val("");
					} else if (formName.indexOf("date")	>= 0) {
						if (($('#' + formName).find('#dateYear').val() == 'yyyy') &&
							($('#' + formName).find('#dateMonth').find('option:selected').val() == 'mm') &&
							($('#' + formName).find('#dateDay').val() == 'dd')) {
							return false;
						}

						if (($('#' + formName).find('#dateYearBetween').val() == 'yyyy') &&
							($('#' + formName).find('#dateMonthBetween').find('option:selected').val() == 'mm')	&&
							($('#' + formName).find('#dateDayBetween').val() == 'dd')) {
							return false;
						}

						if ($('#' + formName).find('#dateYear').val() != 'yyyy')
							searchWord = $('#' + formName).find('#dateYear').val();
						if ( $('#' + formName).find('#dateMonth').find('option:selected').val()	!= 'mm')
							searchWord += '	' + $('#' + formName).find('#' + fieldName + 'Month').find('option:selected').text();
						if ($('#' + formName).find('#dateDay').val() != 'dd')
							searchWord += '	' + $('#' + formName).find('#dateDay').val();
						searchWord += '	and'; 
						if ($('#' + formName).find('#dateYearBetween').val() != 'yyyy')
							searchWord += '	 ' + $('#' + formName).find('#dateYearBetween').val();
						if ( $('#' + formName).find('#dateMonthBetween').find('option:selected').val() != 'mm')
							searchWord += '	' + $('#' + formName).find('#dateMonthBetween').find('option:selected').text();
						if ($('#' + formName).find('#dateDayBetween').val()	!= 'dd')
							searchWord += '	' + $('#' + formName).find('#dateDayBetween').val();

						hiddenValue =  $(this).find("option:selected").val() + "|" + 
									   $('#' + formName).find('#dateYear').val() + '|' + 
									   $('#' + formName).find('#dateMonth').find('option:selected').val() + '|' + 
									   $('#' + formName).find('#dateDay').val()+ "|" + 
									   $('#' + formName).find('#dateYearBetween').val() + '|' + 
									   $('#' + formName).find('#dateMonthBetween').find('option:selected').val() + '|' + 
									   $('#' + formName).find('#dateDayBetween').val();

						$('#' + formName).find('#dateYear').val("yyyy");
						$('#' + formName).find('#dateMonth').find('option:selected').val();
						$('#' + formName).find('#dateDay').val("dd");
						$('#' + formName).find('#dateYearBetween').val("yyyy");
						$('#' + formName).find('#dateMonthBetween').find('option:selected').val();
						$('#' + formName).find('#dateDayBetween').val("dd");
					}
				} else if ($(this).find("option:selected").val() == 'Exactly') {
					searchWord = $('#' + formName).find('#' + fieldName).val();
					hiddenValue = $(this).find("option:selected").text() + "|" + searchWord;
					$('#' + formName).find('#' + fieldName).val("");
				} else if (formName.indexOf("date")	>= 0) {
					if ($('#' + formName).find('#dateYear').val() != 'yyyy') {
						searchWord = $('#' + formName).find('#dateYear').val();
					}
					if ( $('#' + formName).find('#dateMonth').find('option:selected').val()	!= 'mm') {
						searchWord += '	' + $('#' + formName).find('#dateMonth').find('option:selected').text();
					}
					if ($('#' + formName).find('#dateDay').val() != 'dd') {
						searchWord += '	' + $('#' + formName).find('#dateDay').val();
					}
					hiddenValue =  $(this).find("option:selected").val() + "|" + $('#' + formName).find('#dateYear').val() + '|' + $('#' + formName).find('#dateMonth').find('option:selected').val() + '|' + $('#' + formName).find('#dateDay').val();
					$('#' + formName).find('#dateYear').val("yyyy");
					$('#' + formName).find('#dateMonth').find('option:selected').val();
					$('#' + formName).find('#dateDay').val("dd");
				} else {
					searchWord = $('#' + formName).find('#' + fieldName).val();
					hiddenValue = $(this).find("option:selected").val() + "|" + escape($('#' + formName).find('#' + fieldName).val());
					$('#' + formName).find('#' + fieldName).val("");
				}
			} else {
				searchWord = $('#' + formName).find('#' + fieldName).val();
				// If element has autocompleter, its hiddenValue is	a concatenation	of id + description
				if ($('#' + formName).find('#' + fieldName + 'Id').size() >	0) {
					console.log("Form has an autocompleter element")
					//Calculate	hidden value (escaping text	field to prevent field truncation
					hiddenValue = $('#' + formName).find('#' + fieldName + 'Id').val() + "|" + escape($('#' + formName).find('#' + fieldName).val());
					// we reset	autocompleterId
					$('#' + formName).find('#' + fieldName + 'Id').val("");
				} else {
					hiddenValue = searchWord;
				}
				$('#' + formName).find('#' + fieldName).val("");
			}
			
			console.log("Searching : " + searchWord);
			console.log("Final hidden parameter	(" + formName + ") value: " + hiddenValue);

			// We construct	new	html block with	condition and hidden field 
			var searchFilterDiv = $('<div class="searchFilterDiv">');
			$(searchFilterDiv).append('<span class="categorySearch">' + searchType + ': </span>');
			$(searchFilterDiv).append('<span class="wordSearch">' + searchWord + '</span>');
			$(searchFilterDiv).append('<a href="#" class="remove">(remove)</a>');
			$(searchFilterDiv).append('<input type="hidden"	name="' + fieldName + '" value="' + hiddenValue + '"/>');

			if ($("#" + fieldName + "SearchDiv").find(".searchFilterDiv").length >0) {
				$("#" + fieldName + "SearchDiv").find(".searchFilterDiv").last().after('<p class="andOrNotAdvancedSearch">And</p>');
			}
			
			//managing "and	conjunction" before	condition  
			//console.log("Number of div containing	search condition : " + $("#yourEasySearchFilterForm div[id$=SearchDiv]").length);
			var previousConditions = 0;
			var previousConjunctions = 0;
			//we calculate div position	of actual condition
			var conditionDivIndex =	$("#yourEasySearchFilterForm > div").index($("div[id='" + fieldName + "SearchDiv']"))
			$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
				if (index >= conditionDivIndex)
					return true;
				//count	previous filled	condition
				if ($(this).children().size()>0) {
					previousConditions++;
				}
				// count if how many and conjunctions are present
				if ($(this).next().is('hr')) {
					previousConjunctions++;
				}
			});
			if (previousConjunctions == (previousConditions-1)) {
				$("<hr><p class=andOrNotAdvancedSearchCenter>And</p><hr>").insertBefore($("#yourEasySearchFilterForm div[id='" + fieldName + "SearchDiv']"));
			}

			//managing "and	conjunction" after condition  
			var nextConditions = 0;
			var nextConjunctions = 0;

			$("#yourEasySearchFilterForm div[id$=SearchDiv]").each(function(index) {
				if (index <= conditionDivIndex) {
					return true;
				}
				//count	previous filled	condition
				if ($(this).children().size()>0) {
					nextConditions++;
				}
				// check if is present "and conjunction" next to this condition
				if ($(this).prev().is('hr')) {
					nextConjunctions++;
				}
			});
			if (nextConjunctions ==	(nextConditions-1)) {
				$("<hr><p class=andOrNotAdvancedSearchCenter>And</p><hr>").insertAfter($("#yourEasySearchFilterForm div[id='" + fieldName + "SearchDiv']"));
			}

			// We append new block at the end of "field" SearchDiv 
			$("#" + fieldName + "SearchDiv").append(searchFilterDiv);

			console.log("AdvancedSearchForm	" + formName + " completed.");
			
			return false;
		});

		return $;
	};

	return $;
})(jQuery);
