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
	 *  Creates a new annotation, adds it to our list and edits it.
	 *  
	 *  @param canvas the canvas element
	 */
	newAnnotation: function(canvas) {
		
		// Create new ID for annotation
		var id = String.uniqueID();
    
		this.canvas = canvas;
		
		this.renderCommands(false);
		
		// Create default annotation and insert into our annotation array
		var a = {
				id: id,
				x: (this.wid < this.view.w) ? 0.25 : (this.view.x + this.view.w / 4) / this.wid,
				y: (this.hei < this.view.h) ? 0.25 : (this.view.y + this.view.h / 4) / this.hei,
				w: (this.wid < this.view.w) ? 0.5 : (this.view.w / (4 * this.wid)),
				h: (this.hei < this.view.h) ? 0.125 : (this.view.h / (8 * this.hei)),
				type: '',
				title: '',
				text: '',
				deletable: true,
				updatable: true,
				newAnnotation: true,
				visibility: true
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
	 * Edits an existing annotation.
	 * 
	 * @param annotation the edited annotation DOM element
	 */
	editAnnotation: function(annotation) {
		
		/** MEDICI ARCHIVE PROJECT START **/
		this.annotationEditing = true;
		this.setCommandButtonsOpacity(0.1);
		/** MEDICI ARCHIVE PROJECT END **/
		
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
			if (this.annotations[count].newAnnotation	// new annotation
					|| "annotation_" + this.annotations[count].annotationId == id) {	// stored annotations 
				currentIndex = count;
				this.annotations[count].edit = true;
			} else {
				delete this.annotations[count].edit;
			}
		}
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
			
			var titleEditable = this.annotations[currentIndex].newAnnotation || this.annotations[currentIndex].type == "PERSONAL";
			var title = this.editMode === 'teaching' ? 'Comment Title' : 'Annotation Title';
			// Create our input fields
			var html = '<table><tr><td>' + title + '</td><td><input id="annotationTitle" type="text" name="title" autofocus';
			if (this.annotations[currentIndex].title ) {
				html += ' value="' + this.annotations[currentIndex].title + '"';
				if (!titleEditable) {
					html += ' readonly="readonly" title="It is not possible to modify the title!"';
				}
			}
			html += '/></td></tr>';
			
			/** MEDICI ARCHIVE PROJECT START **/
			// RR: It is not possible to modify the type of stored annotations!
			
			var changeableType = this.annotations[currentIndex].newAnnotation;
			
			if (changeableType) {
				var count = 0;
				var defaultType = this.editMode === 'teaching' ? 'TEACHING' : 'PERSONAL';
				
				if (this.editMode !== 'teaching') {
					html += '<tr><td>Annotation Type</td><td>';
				}
				
				if (this.editMode === 'default' || this.editMode === 'all') {
					html += this.getHtmlRadio(this.annotations[currentIndex], 'GENERAL', 'General', count, defaultType);
					count++;
				}
				if (this.editMode === 'default' || this.editMode === 'all') {
					html += this.getHtmlRadio(this.annotations[currentIndex], 'PALEOGRAPHY', 'Paleography', count, defaultType);
					count++;
				}
				if (this.editMode === 'default' || this.editMode === 'all') {
					html += this.getHtmlRadio(this.annotations[currentIndex], 'PERSONAL', 'Personal', count, defaultType);
					count++;
				}
				if (this.editMode === 'teaching' || this.editMode === 'all') {
					html += this.getHtmlRadio(this.annotations[currentIndex], 'TEACHING', 'Course Question', count, defaultType, this.editMode !== 'teaching');
					count++;
				}
				html += '</td></tr>';
			} else {
				if (this.editMode !== 'teaching') {
					html += '<tr><td>Annotation Type</td><td>' + this.annotations[currentIndex].type + '</td></tr>';
				}
			}
			
			if (this.editMode === 'teaching') {
				if (this.adminPrivileges) {
					// RR: prepare the color palette container
					html += '<tr><td colspan="2"><div id="colorPalette"></div><td></tr>';
				}
				html += '<tr><td>Comment Text</td></tr>';
			}
			
			
			if (this.annotations[currentIndex].newAnnotation || this.annotations[currentIndex].type == 'PERSONAL') {
				html += '<tr><td colspan="2"><textarea name="text" rows="5" id="annotationTextarea">' + (this.annotations[currentIndex].text || '') + '</textarea></td></tr></table>';
			} else {
				html += '<tr><td colspan="2"><textarea name="text" rows="5" id="annotationTextarea" readonly="readonly">' + (this.annotations[currentIndex].text || '') + '</textarea></td></tr></table>';
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
			
			/** MEDICI ARCHIVE PROJECT START **/
			
			new Element('div', {
				'id': 'annotationError',
				'style': 'margin-left: 10px; color: #FF1C1C; display: none;',
				'text': 'Highlighted fields cannot be empty'
			}).inject(form);
			
			if (this.editMode === 'teaching' && this.adminPrivileges) {
				var colorPalette = form.getElementById('colorPalette');
				this.initColorPalette(colorPalette, this.annotations[currentIndex].color, "#FFFF00");
				
				colorPalette.getElements('span.colorButton').each(function(button) {
					button.addEvent('click', function(e) {
						var oldSelected = colorPalette.getElement('span.selected');
						if (oldSelected == null || this.getProperty('id') !== oldSelected.getProperty('id')) {
							// modify button status
							this.addClass('selected');
							this.setStyle('background-color', '#606060');
							if (oldSelected != null) {
								oldSelected.removeClass('selected');
								oldSelected.setStyle('background-color', '');
							}
							
							// modify annotation color
							var color = this.getProperty('value');
							_this.annotations[currentIndex].color = color;
							annotation.setStyle('background-color', IIPMooViewer.convertRgbColorToRGBA(color, '0.2'));
						}
					});
				});

				// perform red color button selection
				document.getElementById("colorButton_#FF0000").click();
			}
			
			// Place the form
			if (annotation.getPosition().y + annotation.getSize().y + form.getSize().y > window.innerHeight) {
				form.setStyle('top',  - form.getSize().y - 3);
			}
			/** MEDICI ARCHIVE PROJECT END **/
			
			// Add update event for our list of annotations
			form.addEvents({
				'submit': function(e) {
					e.stop();
					/** MEDICI ARCHIVE PROJECT START **/
					var title = e.target['title'].value;
					var text = e.target['text'].value;
					var empty = false;
					var onlySpaces = /^ * *$/;
					if (title.match(onlySpaces)) {
						this.getElement('#annotationTitle').style.backgroundColor = '#FFFAAD';
						empty = true;
					} else {
						this.getElement('#annotationTitle').style.backgroundColor = '#FFF';
					}
					// RR: in the teaching module is allowed to leave empty annotation (comment) text.
					// In that case comment text becomes the same as title
					if (text.match(onlySpaces) && _this.editMode !== 'teaching') {
						this.getElement('#annotationTextarea').style.backgroundColor = '#FFFAAD';
						empty = true;
					} else {
						if (text.match(onlySpaces) && _this.editMode === 'teaching' && !empty) {
							e.target['text'].value = e.target['title'].value;
						}
						this.getElement('#annotationTextarea').style.backgroundColor = '#FFF';
					}
					if (empty) {
						this.getElement('#annotationError').style.display = 'inline-block';
						return false;
					} else {
						this.getElement('#annotationError').style.display = 'none';
					}
					// RR: now we use the index of the annotations array
					_this.updateShape(this.getParent(), currentIndex);
					/** MEDICI ARCHIVE PROJECT END **/
					var selectedCategory;
					if (changeableType) {
						var categoryArray = e.target['category'];
						if (typeof categoryArray.length === 'number') {
							for (var i = 0; i < categoryArray.length; i++) {
								if (categoryArray[i].checked == true) {
									selectedCategory = categoryArray[i].value;
								}
							}
						} else {
							selectedCategory = categoryArray.value;
						}
					} else {
						selectedCategory = _this.annotations[currentIndex].type;
					}
	
					/** MEDICI ARCHIVE PROJECT START **/
					// RR: now we use the index of the annotations array
					_this.annotations[currentIndex].type = selectedCategory;
					_this.annotations[currentIndex].title = e.target['title'].value;
					_this.annotations[currentIndex].text = e.target['text'].value;
					delete _this.annotations[currentIndex].edit;
					_this.updateAnnotations();
					_this.fireEvent('annotationChange', _this.annotations);
					_this.annotationEditing = false;
					_this.setCommandButtonsOpacity(1);
					/** MEDICI ARCHIVE PROJECT END **/
				},
				'reset': function() {
					/** MEDICI ARCHIVE PROJECT START **/
					_this.stopEditClientAnnotation(currentIndex);
					/** MEDICI ARCHIVE PROJECT END **/
					_this.updateAnnotations();
				}
			});
	
			/** MEDICI ARCHIVE PROJECT START **/
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
			annotation.addEvent('mouseenter', function() {
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
					e.stop();
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
			
			/** MEDICI ARCHIVE PROJECT START **/
			// RR: The default annotation type is selected for new annotations
			if (this.annotations[currentIndex].newAnnotation) {
				document.getElementById('defaultAnnotation').click();
			}
			/** MEDICI ARCHIVE PROJECT END **/
		}

	},
	
	/** MEDICI ARCHIVE PROJECT START **/
	
	/**
	 * Inizializes the color palette buttons
	 * 
	 * @param container the buttons color container
	 * @param annotationColor the current annotation color 
	 * @param defaultColor the default color (if the annotation color is not defined)
	 */
	initColorPalette: function(container, annotationColor, defaultColor) {
		container.grab(this.getColorButton(annotationColor === "#FF0000" || (defaultColor === "#FF0000" && typeof annotationColor === 'undefined'), "#FF0000", "Red"));
		container.grab(this.getColorButton(annotationColor === "#991C00" || (defaultColor === "#991C00" && typeof annotationColor === 'undefined'), "#991C00", "Brown"));
		container.grab(this.getColorButton(annotationColor === "#FF8000" || (defaultColor === "#FF8000" && typeof annotationColor === 'undefined'), "#FF8000", "Orange"));
		//container.grab(this.getColorButton(annotationColor === "#FFFF00" || (defaultColor === "#FFFF00" && typeof annotationColor === 'undefined'), "#FFFF00", "Yellow"));
		container.grab(this.getColorButton(annotationColor === "#00FF00" || (defaultColor === "#00FF00" && typeof annotationColor === 'undefined'), "#00FF00", "Green"));
		container.grab(this.getColorButton(annotationColor === "#007F00" || (defaultColor === "#007F00" && typeof annotationColor === 'undefined'), "#007F00", "Dark Green"));
		container.grab(this.getColorButton(annotationColor === "#00FFFF" || (defaultColor === "#00FFFF" && typeof annotationColor === 'undefined'), "#00FFFF", "Cyan"));
		container.grab(this.getColorButton(annotationColor === "#007FFF" || (defaultColor === "#007FFF" && typeof annotationColor === 'undefined'), "#007FFF", "Light Blue"));
		container.grab(this.getColorButton(annotationColor === "#0000FF" || (defaultColor === "#0000FF" && typeof annotationColor === 'undefined'), "#0000FF", "Blue"));
		container.grab(this.getColorButton(annotationColor === "#7F00FF" || (defaultColor === "#7F00FF" && typeof annotationColor === 'undefined'), "#7F00FF", "Violet"));
		container.grab(this.getColorButton(annotationColor === "#FF007F" || (defaultColor === "#FF007F" && typeof annotationColor === 'undefined'), "#FF007F", "Lilac"));
		container.grab(this.getColorButton(annotationColor === "#FFC0CB" || (defaultColor === "#FFC0CB" && typeof annotationColor === 'undefined'), "#FFC0CB", "Pink"));
		container.grab(this.getColorButton(annotationColor === "#FFFFFF" || (defaultColor === "#FFFFFF" && typeof annotationColor === 'undefined'), "#FFFFFF", "White"));
	},
	
	/**
	 * Initializes a color button
	 * 
	 * @param selected true if this button is selected, false otherwise
	 * @param rgbColor the color of the button in the rgb format (#xxxxxx)
	 * @param text the title of the button
	 * @returns the button DOM element
	 */
	getColorButton: function(selected, rgbColor, text) {
		var style = 'display: inline-block; '
			+ 'margin-right: 1px; '
			+ (selected ? 'background-color: #606060; ' : '')
			+ 'width: 20px; '
			+ 'height: 20px; '
			+ 'cursor: pointer';
		
		var button = new Element('span', {
				'id': 'colorButton_' + rgbColor,
				'style': style,
				'class': 'colorButton' + (selected ? ' selected' : ''),
				'title': text,
				'value': rgbColor
			}
		);
		
		new Element('span', {
			'style': 'background-color: ' + rgbColor + '; display: block; width: 16px; height: 16px; margin: 2px;'
		}).inject(button);
		
		return button;
	},
	
	/**
	 * Gets the html radio button
	 * 
	 * @param annotation the current annotation
	 * @param type the value of the radio button
	 * @param label the label of the radio button
	 * @param level the radio button level (0 for first)
	 * @param defaultType the preselected type
	 * @param show if false it does not show the radio button
	 * @returns the html radio button
	 */
	getHtmlRadio: function(annotation, type, label, level, defaultType, show) {
		var html = '';
		if (level > 0 && (show === 'undefined' || show == true)) {
			html += '<br>';
		}
		html += '<input name="category" type="radio" value="' + type + '"';
		if (type === defaultType) {
			html += ' id="defaultAnnotation"';
		}
		if (typeof show === 'undefined' || show === true) {
			html += '>' + label;
		} else {
			if (annotation.type === type || (annotation.type === '' && type === defaultType)) {
				html += ' checked';
			}
			html += ' style="display: none;">';
		}
		return html;
	},
	
	/**
	 * Removes an annotation from the client
	 * 
	 * @param index the index of the annotation in the annotation array
	 */
	stopEditClientAnnotation: function(index) {
		if (typeof index === 'undefined') {
			for(i = 0; i < this.annotations.length; i++) {
				if (typeof this.annotations[i].newAnnotation !== 'undefined'
					|| typeof this.annotations[i].edit !== 'undefined') {
					index = i;
					break;
				}
			}
		}
		
		if (typeof index !== 'undefined') {
			if (this.annotations[index].newAnnotation) {
				// RR: if we remove a new annotation we have to remove it from the array
				this.annotations.splice(index, 1);
			} else {
				// RR: we remove the edit property for a stored annotation
				// delete _this.annotations[id].edit;
				delete this.annotations[index].edit;
			}
			this.annotationEditing = false;
			this.setCommandButtonsOpacity(1);
		}
	},
	/** MEDICI ARCHIVE PROJECT END **/



	/**
	 * Updates the annotation coordinates.
	 * 
	 * @param el the DOM element correspondent to the annotation
	 * @param index the annotation index in the annotation array
	 */
	updateShape: function(el, index) {
		// Update our list entry
		var parent = el.getParent();
		this.annotations[index].x = el.getPosition(parent).x / this.wid;
		this.annotations[index].y = el.getPosition(parent).y / this.hei;
		this.annotations[index].w = (el.getSize(parent).x - 2) / this.wid;
		this.annotations[index].h = (el.getSize(parent).y - 2) / this.hei;
	},


	/**
	 * Refreshes the annotations.
	 */
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
