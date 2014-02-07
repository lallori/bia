/*
 * File:        teaching.commons.js
 * Version:     1.0
 * Description: common classes and functions for the teaching module
 * Author:      Ronny Rinaldi (www.medici.org)
 * Created:     Thu  6 Feb 2014 12:00:06 BST
 * Language:    Javascript
 * License:     LGPL
 * Project:     BIA
 * Contact:     rinaldi.ronny et gmail.com
 * 
 * Copyright 2009-2014 The Medici Archive Project, all rights reserved.
 *
 */

/**
 * QuotePostGenerator class.
 * This class provide only one method #getQuotePost to get the html content of a quoted post.
 * 
 * @param options options to pass to constructor. You can provide:
 * 		- maxLevel : the max quote inner level
 * 		- logActive : true to enable the console logging
 * 		- content : the content to quote
 * 		- contentSelector : the beginning of the content selector (if content is not provided)
 * 		- title : the content of the quote title
 * 		- userSelector : the beginning of the user name selector (if title is not provided)
 * If none of the previous option details are provided the generator works with the default 
 * configuration.
 * 
 * @returns an instance of {QuotePostGenerator}
 */
function QuotePostGenerator(options) {
	
	var defaultParams = {
		maxLevel: 4,
		logActive: false,
		contentSelector: "#postText_",
		content: null,
		userSelector: "#userName_postId_",
		title: null
	};
	
	if (typeof options === 'undefined') {
		options = {};
	}
	this.maxLevel = options.maxLevel || defaultParams.maxLevel;
	this.logActive = options.logActive || defaultParams.logActive;
	if (typeof options.content !== 'undefined') {
		this.content = options.content;
	} else {
		this.contentSelector = options.contentSelector || defaultParams.contentSelector;
	}
	if (typeof options.title !== 'undefined') {
		this.title = options.title;
	} else {
		this.userSelector = options.userSelector || defaultParams.userSelector;
	}
}

QuotePostGenerator.prototype = (function() {
	
	/**
	 * This private function creates the output quoted post.
	 */
	var eraseInnerQuoteLevels = function(maxLevel, content) {
		var outContent = '';
		var tmpSequence;
		var check = false;
		var level = 0;
		var copy = true;
		
		for (i = 0; i < content.length; i++) {
			var current = content.charAt(i);
			if (!check && '<' !== current) {
				if (copy) {
					outContent += current;
				}
			} else if (!check && '<' === current) {
				check = true;
				tmpSequence = '<';
			} else {
				tmpSequence += current;
				
				if (tmpSequence === '<blockquote') {
					level++;
					if (level > maxLevel) {
						copy = false;
						if (level == maxLevel + 1) {
							outContent += '<p>&#91;...&#93;</p>';
						}
					} else {
						outContent += tmpSequence;
						check = false;
					}
				} else if (tmpSequence === '</blockquote>') {
					level--;
					if (level <= maxLevel) {
						outContent += tmpSequence;
						copy = true;
					}
					check = false;
				} else if ('>' === current) {
					outContent += tmpSequence;
					check = false;
				}
			}
			
		}
		
		return outContent;
	};
	
	return {
		constructor: QuotePostGenerator,
		
		getQuotePost: function(id) {
			if (this.logActive) console.log('Quote: ' + id);
			
			var out = "<br><blockquote class='mceNonEditable'><cite class='postCite'>";
			if (typeof this.title !== 'undefined') {
				out += '<span class="quoteTitle">' + this.title + '</span>';
			} else {
				if (typeof jQuery !== 'undefined') {
					out += '<span class="quoteUser">' + $j('' + this.userSelector + id).text() + '</span>';
				} else {
					out += document.querySelector('' + this.userSelector + id).textContent;
				}
			}
			out += " wrote:</cite><div class='postContent'>";
			if (typeof this.content !== 'undefined') {
				out += eraseInnerQuoteLevels(this.maxLevel - 1, this.content);
			} else {
				if (typeof jQuery !== 'undefined') {
					out	+= eraseInnerQuoteLevels(this.maxLevel - 1, $j('' + this.contentSelector + id).html());
				} else {
					out	+= eraseInnerQuoteLevels(this.maxLevel - 1, document.querySelector('' + this.contentSelector + id).innerHTML);
				}
			}
			out += "</div></blockquote><br>";
			
			if (this.logActive) console.log('Output:\n' + out);
			return out;
		}
	}
	
})();
