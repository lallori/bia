/*
   IIPMooViewer 2.0 - Annotation Extensions
   IIPImage Javascript Viewer <http://iipimage.sourceforge.net>

   Copyright (c) 2007-2012 Ruven Pillay <ruven@users.sourceforge.net>

   ---------------------------------------------------------------------------

   This program is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   ---------------------------------------------------------------------------

*/


/* Extend IIPMooViewer to handle annotations
 */
IIPMooViewer.implement({

	/**
	 * Initialize canvas events for our annotations
	 */
	initAnnotationTips: function() {

		this.annotationTip = null;
		this.annotationsVisible = true;

		// Use closure for mouseenter and mouseleave events
		var _this = this;

		/** MEDICI ARCHIVE PROJECT START **/
		// Display / hide our annotations if we have any
		/*if (this.annotations) {
			this.canvas.addEvent('mouseenter', function() {
				if (_this.annotationsVisible) {
					_this.canvas.getElements('div.annotation').removeClass('hidden');
				}
			});
			this.canvas.addEvent('mouseleave', function() {
				if (_this.annotationsVisible) {
					_this.canvas.getElements('div.annotation').addClass('hidden');
				}
			});
		}*/

		if (this.annotations && (this.annotationId == null || this.annotationId == '')) {
			this.canvas.addEvent('mouseenter', function() {
				if (_this.annotationsVisible ) {
					_this.canvas.getElements('div.annotation').removeClass('hidden');
				}
			});
			/*this.canvas.addEvent('mouseleave', function() {
				if (_this.annotationsVisible ) {
					_this.canvas.getElements('div.annotation').addClass('hidden');
				}
			});*/
		} else if (this.annotations) {
			this.canvas.addEvent('mouseenter', function() {
				if (_this.annotationsVisible ) {
					_this.canvas.getElements('div.annotation').removeClass('hidden');
				}
			});
		}
		/** MEDICI ARCHIVE PROJECT END **/
	},


	/**
	 * Create annotations if they are contained within our current view
	 */
	createAnnotations: function() {
		
		var _this = this;
		
		// If there are no annotations, simply return
		if (!this.annotations || this.annotations.length == 0) {
			return;
		}
	
	    // Convert our annotation object into an array - we'll need this for sorting later
		var annotation_array = new Array();
		
		/** MEDICI ARCHIVE PROJECT START **/
		// RR: We read annotations by index position
		/*for (var a in this.annotations) {
			this.annotations[a].id = a;
			annotation_array.push(this.annotations[a]);
		}*/
		for (var count = 0; count < this.annotations.length; count++) {
			this.annotations[count].id = count;
			if (typeof this.annotations[count].visibility !== 'undefined') {
				// RR: if annotation has visibility property it can be showed:
				// if true it can be showed in full color, if false it
				// can be showed in transparency (eg. for administrator users);
				// If annotation has not visibility property it cannot be showed
				// (eg. it has to be hidden for non admin users)
				annotation_array.push(this.annotations[count]);
			}
		}
		/** MEDICI ARCHIVE PROJECT END */
	
	    // Make sure we really have some content
		if (annotation_array.length == 0) {
			return;
		}
	
	    // Sort our annotations by size to make sure it's always possible to interact
	    // with annotations within annotations.
		annotation_array.sort(
			function(a,b) {
				return (b.w * b.h) - (a.w * a.h);
			}
		);
	
	    // Now go through our sorted list and display those within the view
		for (var i = 0; i < annotation_array.length; i++) {
	
			// Check whether this annotation is within our view
			if (this.wid * (annotation_array[i].x + annotation_array[i].w) > this.view.x
					&& this.wid * annotation_array[i].x < this.view.x + this.view.w
					&& this.hei * (annotation_array[i].y + annotation_array[i].h) > this.view.y
					&& this.hei * annotation_array[i].y < this.view.y + this.view.h
					// Also don't show annotations that entirely fill the screen
					/*	  (this.hei*annotation_array[i].x < this.view.x && this.hei*annotation_array[i].y < this.view.y &&
					this.wid*(annotation_array[i].x+annotation_array[i].w) > this.view.x+this.view.w */) {
	
				var _this = this;
				var cl = 'annotation';
				if (annotation_array[i].type) {
					cl += ' ' + annotation_array[i].type;
				}
				var annotId = annotation_array[i].annotationId ? annotation_array[i].annotationId : annotation_array[i].id;
				var annotation = new Element('div', {
					'id': 'annotation_' + annotId,
					'class': cl,
					'styles': {
						left: Math.round(this.wid * annotation_array[i].x),
						top: Math.round(this.hei * annotation_array[i].y),
						width: Math.round(this.wid * annotation_array[i].w),
						height: Math.round(this.hei * annotation_array[i].h),
						opacity: annotation_array[i].visibility ? 1.0 : 0.5  
					}
				}).inject(this.canvas);
				if (typeof annotation_array[i].color !== 'undefined') {
					annotation.setStyle('background-color', IIPMooViewer.convertRgbColorToRGBA(annotation_array[i].color, '0.2'));
				}
				
				/** MEDICI ARCHIVE PROJECT START **/
				// Redirect, by simple click on annotation area, to the forum topic associated to the current annotation
				if (typeof annotation_array[i].forumTopicURL !== 'undefined') {
					this.initAnnotationClickHandler(annotation, annotation_array[i].forumTopicURL);
					annotation.setStyle('cursor', 'pointer');
				}
				/** MEDICI ARCHIVE PROJECT END */
				
				var annotationCommandOpenClose = null;
				if (this.editEnabled && (typeof this.annotationEditing === 'undefined' || !this.annotationEditing)) {
					annotationCommandOpenClose = this.initAnnotationCommands(annotation, annotId);
				}
			
				if (this.annotationsVisible == false) {
					annotation.addClass('hidden');
					if (annotationCommandOpenClose != null) {
						annotationCommandOpenClose.addClass('hidden');
					}
				}
	
				// Add edit events to annotations if we have included the functions
				if (typeof(this.editAnnotation) == "function") {
					if (annotation_array[i].edit == true) { 
						this.editAnnotation(annotation);
					}
				}
			
				// Add our annotation text
				var text = annotation_array[i].text;
				/** MEDICI ARCHIVE PROJECT START **/
				if (text.length > 30) {
					text = text.substring(0, 29) + '&hellip;';
				}
				var title = annotation_array[i].title || 'No title';
				if (title.length > 15) {
					title = title.substring(0, 14) + '&hellip;';
				}
				annotation.store('tip:title', '<h1>'+title+'</h1>');
				/*if (annotation_array[i].title) {
					text = '<h1>'+annotation_array[i].title+'</h1>' + text;
				}*/
				/** MEDICI ARCHIVE PROJECT END */
				annotation.store('tip:text', text);
	    	}
	
		}
	
	
		if (!this.annotationTip) {
			var _this = this;
			this.annotationTip = new Tips('div.annotation', {
				className: 'tip', // We need this to force the tip in front of nav window
				fixed: true,
				offset: {
					x: 0,
					y: -60
				},
				hideDelay: 300,
				link: 'chain',
				onShow: function(tip, el) {
	
					// Fade from our current opacity to 0.9
					tip.setStyles({
						opacity: tip.getStyle('opacity'),
						display: 'block'
					}).fade(0.9);
	
					// Prevent the tip from fading when we are hovering on the tip itself and not
					// just when we leave the annotated zone
					tip.addEvents({
						'mouseleave':  function() {
							this.active = false;
							this.fade('out').get('tween').chain(
								function() {
									this.element.setStyle('display','none');
								}
							);
						},
						/** MEDICI ARCHIVE PROJECT START **/
						//'mouseenter': function(){ this.active = true; }
						'mouseenter': function() {
							this.active = true; 
							_this.canvas.getElements('div.annotation').removeClass('hidden');
						}
						/** MEDICI ARCHIVE PROJECT END **/
					})
				},
				onHide: function(tip, el) {
					if (!tip.active) {
						tip.fade('out').get('tween').chain(
							function() {
								this.element.setStyle('display','none');
							}
						);
						tip.removeEvents(['mouseenter','mouseleave']);
					}
				}
			});
		}
	},
	
	/** MEDICI ARCHIVE PROJECT START **/
	
	/**
	 * Initializes the click handler of the annotation area.
	 *  
	 * @param annotation the annotation 'div' element
	 * @param redirectUrl the url to open when someone clicks on the annotation div
	 */
	initAnnotationClickHandler: function(annotation, redirectUrl) {
		var _this = this;
		annotation.addEvent('click', function(e) {
			var event = new DOMEvent(e);
			if (_this.annotationEditing != true) {
				event.stop();
				window.open(redirectUrl, 'Forum', 'width=' + screen.width + ', height=' + screen.height + ', scrollbars=yes');
			}
		});
	},
	
	/**
	 * Initializes the annotation action commands.
	 * 
	 * @param annotation the annotation 'div' element
	 * @param annotationId the annotation identifier
	 * @returns the master annotation command (open/close command)
	 */
	initAnnotationCommands: function(annotation, annotationId) {
		var _this = this;
		var idx = 0;
		while (idx < this.annotations.length) {
			if ((typeof this.annotations[idx].annotationId === "undefined" && this.annotations[idx].id == annotationId)
					|| this.annotations[idx].annotationId == annotationId) {
				break;
			}
			idx++;
		}
		
		var isDeletable = this.annotations[idx].deletable == true;
		var isUpdatable = this.annotations[idx].updatable == true;
		var canChangeVisibility = typeof this.adminPrivileges !== 'undefined' && this.adminPrivileges &&
									typeof this.annotations[idx].visibility !== 'undefined' && this.annotations[idx].visibility != null;
		
		if (isDeletable || isUpdatable || canChangeVisibility) {
			var annotationCommandOpenClose = new Element('div', {
				'id': ('commandBtn_' + annotationId),
				'class': 'commandBtn moreBtn',
				'styles': {
					left: 0,
					top: 0
				},
				'title': 'open/close annotation commands'
			}).inject(annotation);
			
			var delta = 0;
			
			if (isDeletable) {
				var annotationDelete = new Element('div', {
					'id': ('deleteBtn_' + annotationId),
					'class': 'commandBtn deleteBtn hidden',
					'styles': {
						left: 20,
						top: delta * 22
					},
					'title': 'delete this annotation'
				}).inject(annotation);
				
				annotationDelete.addEvent('click', function(e) {
					var event = new DOMEvent(e);
					event.stop();
					if (confirm('Do you want to delete this annotation?')) {
						_this.annotations.splice(idx, 1);
						_this.updateAnnotations();
						_this.fireEvent('annotationChange', _this.annotations);
					}
				});
				delta++;
			}
			
			if (isUpdatable) {
				var annotationUpdate = new Element('div', {
					'id': ('updateBtn_' + annotationId),
					'class': 'commandBtn updateBtn hidden',
					'styles': {
						left: 20,
						top: delta * 22
					},
					'title': 'modify this annotation'
				}).inject(annotation);
				
				annotationUpdate.addEvent('click', function(e) {
					e.stop();
					_this.canvas.getElements('div.commandBtn').addClass('hidden');
					_this.editAnnotation(annotation);
				});
				delta++;
			}
			
			if (canChangeVisibility) {
				if (this.annotations[idx].visibility === true) {
					var annotationHide = new Element('div', {
						'id': ('hideBtn_' + annotationId),
						'class': 'commandBtn hideBtn hidden',
						'styles': {
							left: 20,
							top: delta * 22
						},
						'title': 'hide this annotation'
					}).inject(annotation);
					
					annotationHide.addEvent('click', function(e) {
						e.stop();
						_this.annotations[idx].visibility = false;
						_this.updateAnnotations();
						_this.fireEvent('annotationChange', _this.annotations);
					});
					delta++;
				} else {
					var annotationShow = new Element('div', {
						'id': ('showBtn_' + annotationId),
						'class': 'commandBtn showBtn hidden',
						'styles': {
							left: 20,
							top: delta * 22
						},
						'title': 'show this annotation'
					}).inject(annotation);
					
					annotationShow.addEvent('click', function(e) {
						e.stop();
						_this.annotations[idx].visibility = true;
						_this.updateAnnotations();
						_this.fireEvent('annotationChange', _this.annotations);
					});
					delta++;
				}
			}
			
			annotationCommandOpenClose.addEvent('click', function(e) {
				var event = new DOMEvent(e);
				event.stop();
				var annotationId = this.id.substring(11, this.id.length);
				if (this.hasClass('moreBtn')) {
					this.removeClass('moreBtn');
					this.addClass('lessBtn');
					_this.canvas.getElements('#updateBtn_' + annotationId + ',#deleteBtn_' + annotationId + ',#hideBtn_' + annotationId + ',#showBtn_' + annotationId).removeClass('hidden');
				} else if (this.hasClass('lessBtn')) {
					this.removeClass('lessBtn');
					this.addClass('moreBtn');
					_this.canvas.getElements('#updateBtn_' + annotationId + ',#deleteBtn_' + annotationId + ',#hideBtn_' + annotationId + ',#showBtn_' + annotationId).addClass('hidden');
				}
			});
			
			return annotationCommandOpenClose;
		}
		
		return null;
	},
	
	
	
	/**
	 * Renders or hides the commands associated to annotations
	 * 
	 * @param show true if you want to render commands
	 */
	renderCommands: function(show) {
		if (!show) {
			this.canvas.getElements('div.commandBtn').each(function(el) {
				el.addClass('hidden');
			}); 
		} else {
			this.canvas.getElements('div.lessBtn').each(function(el) {
				el.removeClass('lessBtn');
				el.addClass('moreBtn');
			});
			this.canvas.getElements('div.moreBtn,div.lessBtn').each(function(el) {
				el.removeClass('hidden');
			});
		}
	},
	
	/** MEDICI ARCHIVE PROJECT END **/
	
	/**
	 * Toggle visibility of any annotations
	 */
	toggleAnnotations: function() {
		var visible = this.annotationsVisible;
		if (visible) {
			this.canvas.getElements('div.annotation').each(function(el) {
				el.addClass('hidden');
			});
			this.renderCommands(true);
		} else if (!visible) {
			this.canvas.getElements('div.annotation').each(function(el) {
				el.removeClass('hidden');
			});
			this.renderCommands(false);
		}
		if (visible) {
			this.annotationsVisible = false;
			this.showPopUp(IIPMooViewer.lang.annotationsDisabled);
		} else {
			this.annotationsVisible = true;
		}
	},


	/** 
	 * Destroy our annotations
	 */
	destroyAnnotations: function() {
		if (this.annotationTip) {
			this.annotationTip.detach(this.canvas.getChildren('div.annotation'));
		}
		this.canvas.getChildren('div.annotation').each(function(el) {
			el.eliminate('tip:text');
			el.destroy();
		});
		this.canvas.getChildren('div.commandBtn').each(function(el) {
			el.destroy();
		});
	},


	/** MEDICI ARCHIVE PROJECT START **/
	retrieveAnnotations: function() {
		if (this.annotationsType == 'remote') {
			this.annotations = new Array();

			if (this.annotationId == null || this.annotationId == '') {
				
				new Request.JSON({
					method: 'get',
					async: false,
					url: this.retrieveAnnotationsUrl,
					noCache: true,
					onRequest: function() {
						// show some rotating loader gif...
					},

					onSuccess: function(responseJSON, responseText) {
						if (typeof responseJSON.operation === 'undefined' || responseJSON.operation === 'OK') {
							this.adminPrivileges = responseJSON.adminPrivileges;
							var data = new Array();  // temporary data array
							for (i = 0; i < responseJSON.annotations.length; i++) {
								data[i] = {
									annotationId: responseJSON.annotations[i].annotationId.toInt(),
									id: responseJSON.annotations[i].id,
									x: responseJSON.annotations[i].x.toFloat(), 
									y: responseJSON.annotations[i].y.toFloat(), 
									w: responseJSON.annotations[i].w.toFloat(), 
									h: responseJSON.annotations[i].h.toFloat(),
									type: responseJSON.annotations[i].type,
									title: responseJSON.annotations[i].title,
									text: responseJSON.annotations[i].text,
									deletable: responseJSON.annotations[i].deletable,
									updatable: responseJSON.annotations[i].updatable,
									forumTopicURL: responseJSON.annotations[i].forumTopicURL // Link To Forum
								};
								if (typeof responseJSON.annotations[i].color !== 'undefined') {
									data[i]["color"] = responseJSON.annotations[i].color;
								}
								if (typeof responseJSON.annotations[i].visibility !== 'undefined') {
									data[i]["visibility"] = responseJSON.annotations[i].visibility;
								}
								this.annotations.push(data[i]);
							}
						} else {
							console.log('error');
							alert('Error during annotations retrieving...');
						}
					}.bind(this),

					onError: function(text, error) {
						console.log('error!!!' + text + ' - error : ' + error);
						alert('Error: annotation retrieving server call failed!!!');
					}
					
				}).get('');
				
			} else {
				
				new Request.JSON({
					method: 'get',
					async: false,
					url: this.retrieveAnnotationsUrl + '&annotationId=' + this.annotationId,
					noCache: true,
					onRequest: function() {
						// show some rotating loader gif...
					},
					
					onSuccess: function(responseJSON, responseText) {
						this.adminPrivileges = responseJSON.adminPrivileges;
						for (i=0; i<responseJSON.annotations.length; i++) {
							this.annotations.push({
								annotationId: responseJSON.annotations[i].annotationId.toInt(),
								id: responseJSON.annotations[i].id,
								x: responseJSON.annotations[i].x.toFloat(), 
								y: responseJSON.annotations[i].y.toFloat(), 
								w: responseJSON.annotations[i].w.toFloat(), 
								h: responseJSON.annotations[i].h.toFloat(),
								type: responseJSON.annotations[i].type,
								title: responseJSON.annotations[i].title,
								text: responseJSON.annotations[i].text,
								deletable: responseJSON.annotations[i].deletable,
								updatable: responseJSON.annotations[i].updatable,
								visibility: true // for annotation forum topic visibility cannot be hidden
								// forumTopicURL: responseJSON.annotations[i].forumTopicURL 
								// Link To Forum: not enabled because this feature has not to be enabled in
								// the annotation forum
							});
						}
		        	}.bind(this),
		        	
					onError: function(text, error) {
						console.log('error!!!' + text + ' - error : ' + error);
					}
		        	
			    }).get('');
				
			}
		} else if (this.annotationsType == 'local') {
			// in case of local annotation, we do nothing
		}
	}
	/** MEDICI ARCHIVE PROJECT END **/

});
