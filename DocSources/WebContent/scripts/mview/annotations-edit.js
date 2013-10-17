/*
   IIPMooViewer 2.0 - Annotation Editing Extensions
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


/**
 *  Extend IIPMooViewer to handle annotations
 */
IIPMooViewer.implement({
	
	/**
	 *  Create a new annotation, add it to our list and edit it
	 */
	newAnnotation: function(canvas) {

		// Create new ID for annotation
		var id = String.uniqueID();
    
		this.canvas = canvas;

		// Create default annotation and insert into our annotation array
		var a = {
				id: id,
				x: (this.wid < this.view.w) ? 0.25 : (this.view.x + this.view.w / 4) / this.wid,
				y: (this.hei < this.view.h) ? 0.25 : (this.view.y + this.view.h / 4) / this.hei,
				w: (this.wid < this.view.w) ? 0.5 : (this.view.w / (2 * this.wid)),
				h: (this.hei < this.view.h) ? 0.5 : (this.view.h / (2 * this.hei)),
				type: '',
				title: '',
				text: '',
				deletable: true,
				updatable: true
		};

		// Create an array if we don't have one and push a new annotation to it
		if (!this.annotations) {
			this.annotations = {};
		}
		/** MEDICI ARCHIVE PROJECT START **/
		// RR we push the element in the array with push because we want to change array dimensions.
		// this.annonations[id] = a;
		this.annotations.push(a);
		/** MEDICI ARCHIVE PROJECT END **/

		var _this = this;

		// Now draw the annotation
		var annotation = new Element('div', {
			'id': id,
			'class': 'annotation edit',
			'styles': {
				left: Math.round(a.x * this.wid),
				top: Math.round(a.y * this.hei),
				width: Math.round(a.w * this.wid),
				height: Math.round(a.h * this.hei)
			}
		}).inject(this.canvas);

		this.editAnnotation(annotation);

	},



	/**
	 * Edit an existing annotation
	 */
	editAnnotation: function(annotation) {
	  
		// Disable key bindings on container
		this.container.removeEvents('keydown');
		if (this.annotationTip) {
			this.annotationTip.hide();
			this.annotationTip.detach('div.annotation');
		}

		// Get our annotation ID
		var id = annotation.get('id');

		// Remove the edit class from other annotations divs and assign to this one
		this.canvas.getChildren('div.annotation.edit').removeClass('edit');

		this.canvas.getChildren('div.annotation form').destroy();
		this.canvas.getChildren('div.annotation div.handle').destroy();

		annotation.addClass('edit');
		/** MEDICI ARCHIVE PROJECT START **/
		// we use the index of the annotations array instead of the id of the annotation
		var currentIndex;
		for (var count = 0; count < this.annotations.length; count++) {
			if (this.annotations[count].id == id	// new annotation
					|| "annotation_" + this.annotations[count].annotationId == id) {	// stored annotations 
				currentIndex = count;
				this.annotations[count].edit = true;
			} else {
				delete this.annotations[count].edit;
			}
		}
		/*for (var a in this.annotations) {
			if (a == id) {
				this.annotations[a].edit = true;
			} else {
				delete this.annotations[a].edit;
			}
		}*/
		/** MEDICI ARCHIVE PROJECT END **/

		if (this.editEnabled && typeof(currentIndex) != "null" && typeof(currentIndex) != "undefined") {
			var _this = this;
	
			// Check whether this annotation is already in edit mode. If so return
			if (annotation.getElement('div.handle') != null) {
				return;
			}
	
			// Create our edit infrastructure
			var handle = new Element('div', {
				'id': 'annotation handle',
				'class': 'annotation handle',
				'title': 'resize annotation'
			}).inject(annotation);
	
			var form = new Element('form', {
				'class': 'annotation form',
				'styles': {
					'top': annotation.getSize().y
				}
			}).inject(annotation);
			
			// Create our input fields
			var html = '<table><tr><td>Annotation Title</td><td><input type="text" name="title" autofocus';
			if (this.annotations[currentIndex].title ) {
				html += ' value="' + this.annotations[currentIndex].title + '"';
			}
			html += '/></td></tr>';
	
			/** MEDICI ARCHIVE PROJECT START **/
			// RR: It is not possible to modify the type of stored annotations!
			//     Stored annotations have "annotation_" at the beginning of the id. 
			var changeableType = id.lastIndexOf("annotation_", 0) != 0;
			
			if (changeableType) {
				/*html += '<tr><td>Annotation Type</td><td><input type="text" name="category"';
				html += '<tr><td>Annotation Type</td><td><select name="category"><option value="general">General Annotation</option><option value="paleography">Paleography Annotation</option><option value="personal">Personal Annotation</option>';*/
				html += '<tr><td>Annotation Type</td><td><input name="category" type="radio"  value="GENERAL"';
				if (this.annotations[currentIndex].type == 'GENERAL' || this.annotations[currentIndex].type == '') {
					html += 'checked="true"';
				}
				html +=	'>General<br><input name="category" type="radio" value="PALEOGRAPHY"';
				if (this.annotations[currentIndex].type == 'PALEOGRAPHY') {
					html += 'checked="true"';
				}
				html +=	'>Paleography<br><input name="category" type="radio" value="PERSONAL"';
				if (this.annotations[currentIndex].type == 'PERSONAL') {
					html += 'checked="true"';
				}
				html +=	'>Personal';
				
				/*if( this.annotations[id].type ) html += ' value="' + this.annotations[id].type + '"';
				html += '/></td></tr>';*/
				html += '</td></tr>';
			} else {
				html += '<tr><td>Annotation Type</td><td>' + this.annotations[currentIndex].type + '</td></tr>';
			}
			
	
			if (this.annotations[currentIndex].type == 'PERSONAL') {
				html += '<tr><td colspan="2"><textarea name="text" rows="5" id="annotationTextarea">' + (this.annotations[currentIndex].text || '') + '</textarea></td></tr></table>';
			} else {
				html += '<tr><td colspan="2"><textarea name="text" rows="5" style="display:none;" id="annotationTextarea">' + (this.annotations[currentIndex].text || '') + '</textarea></td></tr></table>';
			}
	
			html += '<input type="hidden" name="annotationId" value="' + this.annotations[currentIndex].annotationId + '">';
			html += '<input type="hidden" name="type" value="' + this.annotations[currentIndex].type + '">';
			/** MEDICI ARCHIVE PROJECT END **/
	
			form.set('html', html);
	
			new Element('input', {
				'type': 'submit',
				'class': 'button',
				'value': 'ok'
			}).inject(form);
	
			new Element('input', {
				'type': 'reset',
				'class': 'button',
				'value': 'cancel'
			}).inject(form);
	
			if (this.annotations[currentIndex].deletable) {
				var del = new Element('input', {
					'type': 'button',
					'class': 'button delAnnotation',
					'value': 'delete'
				}).inject(form);
	
				var question = new Element('div', {
					'id': 'question',
					'style': 'display:none; cursor: default; z-index: 999; ',
					'html': 'Are you sure?'
				}).inject(form);
	
				var yesQuestion = new Element('input', {
					'type': 'button',
					'class': 'button',
					'value': 'Yes'
				}).inject(question);
	
				var noQuestion = new Element('input', {
					'id':'noQuestion',
					'type': 'button',
					'class': 'button',
					'value': 'No'
				}).inject(question);
			} else {
				var del = new Element('input', {
					'type': 'button',
					'class': 'button delAnnotation',
					'value': 'delete',
					'disabled' : 'disabled',
					'title' : 'DELETE button is disabled: you cannot delete this annotation because there are some posts associated to it' 
				}).inject(form);
			}
	
			/** MEDICI ARCHIVE PROJECT START **/
			//To position the form
			if (annotation.getPosition().y + annotation.getSize().y + form.getSize().y > window.innerHeight) {
				form.setStyle('top',  - form.getSize().y - 3);
			}
			/** MEDICI ARCHIVE PROJECT END **/
	
			// Add update event for our list of annotations
			form.addEvents({
				'submit': function(e) {
					e.stop();
					/** MEDICI ARCHIVE PROJECT START **/
					// RR: now we use the index of the annotations array
					// _this.updateShape(this.getParent());
					_this.updateShape(this.getParent(),currentIndex);
					/** MEDICI ARCHIVE PROJECT END **/
					var selectedCategory;
					if (changeableType) {
						var categoryArray = e.target['category'];
						for (var i = 0; i < categoryArray.length; i++) {
							if (categoryArray[i].checked == true) {
								selectedCategory = categoryArray[i].value;
							}
						}
					} else {
						selectedCategory = _this.annotations[currentIndex].type;
					}
	
					/** MEDICI ARCHIVE PROJECT START **/
					// RR: now we use the index of the annotations array
					// _this.annotations[id].type = selectedCategory;
					// _this.annotations[id].title = e.target['title'].value;
					// _this.annotations[id].text = e.target['text'].value;
					// delete _this.annotations[id].edit;
					_this.annotations[currentIndex].type = selectedCategory;
					_this.annotations[currentIndex].title = e.target['title'].value;
					_this.annotations[currentIndex].text = e.target['text'].value;
					delete _this.annotations[currentIndex].edit;
					/** MEDICI ARCHIVE PROJECT END **/
					/** MEDICI ARCHIVE PROJECT START **/
					_this.updateAnnotations();
					_this.fireEvent('annotationChange', _this.annotations);
					_this.stopZoom = false;
					_this.container.getElement('div.navbuttons').getElement('img.zoomIn').style.opacity=1;
					_this.container.getElement('div.navbuttons').getElement('img.zoomOut').style.opacity=1;
					/** MEDICI ARCHIVE PROJECT END **/
				},
				'reset': function() {
					/** MEDICI ARCHIVE PROJECT START **/
					// RR: now we use the index of the annotations array
					// delete _this.annotations[id].edit;
					delete _this.annotations[currentIndex].edit;
					/** MEDICI ARCHIVE PROJECT END **/
					_this.updateAnnotations();
				}
			});
	
			/** MEDICI ARCHIVE PROJECT START **/
			// Add a delete event to our annotation
			/*del.addEvent('click', function() {
				delete _this.annotations[id];
				_this.updateAnnotations();
				_this.fireEvent('annotationChange', _this.annotations);
			});*/
	
			if (this.annotations[currentIndex].deletable) {
				/*del.addEvent('click', function() {
					delete _this.annotations[id];
					_this.updateAnnotations();
					_this.fireEvent('annotationChange', _this.annotations);
				});*/
	
				yesQuestion.addEvent('click', function() {
					/** MEDICI ARCHIVE PROJECT START **/
					// RR: now we use the index of the annotations array and we use splice to remove the current annotation correctly 
					// delete _this.annotations[id];
					_this.annotations.splice(currentIndex, 1);
					/** MEDICI ARCHIVE PROJECT END **/
					_this.updateAnnotations();
					_this.fireEvent('annotationChange', _this.annotations);
				});
			}
			/** MEDICI ARCHIVE PROJECT END **/
	
	
			/** MEDICI ARCHIVE PROJECT START **/
			// Make it draggable and resizable, but prevent this interfering with our canvas drag
			// Update on completion of movement
			/*var draggable = annotation.makeDraggable({
				stopPropagation: true,
				preventDefault: true,
				container: this.canvas,
			});
	
			var resizable = annotation.makeResizable({
				handle: handle,
				stopPropagation: true,
				preventDefault: true,
				// Keep our form attached to the annotation
				onDrag: function() {
					form.setStyle('top', this.element.getSize().y);
				}
			});*/
	
			// Make it draggable and resizable, but prevent this interfering with our canvas drag
			// Update on completion of movement
			var draggable = annotation.makeDraggable({
				stopPropagation: true,
				preventDefault: true,
				container: this.canvas,
				/*onDrop: function() {
					if (this.element.y > window.innerHeight) {
						form.setStyle('top', this.element.x);
					}
				}*/
				onDrop: function() {
					if (this.element.getPosition().y + this.element.getSize().y + form.getSize().y > window.innerHeight) {
						form.setStyle('top',  - form.getSize().y - 3);
					} else {
						form.setStyle('top', this.element.getSize().y);
					}
				}
			});
	
			var resizable = annotation.makeResizable({
				handle: handle,
				stopPropagation: true,
				preventDefault: true,
				// Keep our form attached to the annotation
				/*onDrag: function() {
					form.setStyle('top', this.element.getSize().y );
				}*/
				onDrag: function() {
					if (this.element.getPosition().y + this.element.getSize().y + form.getSize().y > window.innerHeight) {
						form.setStyle('top',  - form.getSize().y - 3);
					} else {
						form.setStyle('top', this.element.getSize().y);
					}
				}
			});
	
			/** MEDICI ARCHIVE PROJECT END **/
	
	
			// Set default focus on textarea
			annotation.addEvent( 'mouseenter', function() {
				form.getElement('textarea').focus();
				form.getElement('textarea').value = form.getElement('textarea').value;
			});
	
			// Add focus events and reset values to deactivate text selection
			form.getElements('input,textarea').addEvents({
				'click': function() {
					this.focus();
					this.value = this.value;
				},
				'dblclick': function(e) {
					e.stop;
				},
				'mousedown': function(e) {
					e.stop();
				},
				'mousemove': function(e) {
					e.stop();
				},
				'mouseup': function(e) {
					e.stop();
				}
			});
		}

	},



	/**
	 * Update the coordinates of the annotation
	 */
	/** MEDICI ARCHIVE PROJECT START **/
	// RR: we provide the index of the annotations array
	// updateShape: function(el) {
	updateShape: function(el, index) {

		//var id = el.get('id');

		// Update our list entry
		var parent = el.getParent();
		this.annotations[index].x = el.getPosition(parent).x / this.wid;
		this.annotations[index].y = el.getPosition(parent).y / this.hei;
		this.annotations[index].w = (el.getSize(parent).x - 2) / this.wid;
		this.annotations[index].h = (el.getSize(parent).y - 2) / this.hei;
	},


	updateAnnotations: function() {
		this.destroyAnnotations();
		this.createAnnotations();
		this.container.addEvent('keydown', this.key.bind(this));
		if (this.annotationTip) {
			this.annotationTip.attach('div.annotation');
		}
	},


	toggleEditFlat: function(id) {

	}


});
