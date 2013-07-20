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


/* Extend IIPMooViewer to handle annotations
 */
IIPMooViewer.implement({

  /* Create a new annotation, add it to our list and edit it
   */
  newAnnotation: function(){

    // Create new ID for annotation
    var id = String.uniqueID();

    // Create default annotation and insert into our annotation array
    var a = {
      id: id,
      x: (this.wid<this.view.w) ? 0.25 : (this.view.x+this.view.w/4)/this.wid,
      y: (this.hei<this.view.h) ? 0.25 : (this.view.y+this.view.h/4)/this.hei,
      w: (this.wid<this.view.w) ? 0.5 : (this.view.w/(2*this.wid)),
      h: (this.hei<this.view.h) ? 0.5 : (this.view.h/(2*this.hei)),
     type: '',
      title: '',
      text: '',
      deletable: true
    };

    // Create an array if we don't have one and push a new annotation to it
    if( !this.annotations ) this.annotations = {};
    this.annotations[id] = a;

    var _this = this;

    // Now draw the annotation
    var annotation = new Element('div', {
      'id': id,
      'class': 'annotation edit',
      'styles': {
        left: Math.round( a.x * this.wid ),
        top: Math.round( a.y * this.hei ),
	width: Math.round( a.w * this.wid ),
	height: Math.round( a.h * this.hei )
      }
    }).inject( this.canvas );

    this.editAnnotation( annotation );

  },



  /* Edit an existing annotation
   */
  editAnnotation: function(annotation){

    // Disable key bindings on container
    this.container.removeEvents('keydown');
    if( this.annotationTip ){
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
    for( var a in this.annotations ){
      if( a == id ) this.annotations[a].edit = true;
      else delete this.annotations[a].edit;
    }


    var _this = this;

    // Check whether this annotation is already in edit mode. If so return
    if( annotation.getElement('div.handle') != null ) return;

    // Create our edit infrastructure
    var handle = new Element('div', {
      'id': 'annotation handle',
      'class': 'annotation handle',
      'title': 'resize annotation'
    }).inject( annotation );

    var form = new Element('form', {
      'class': 'annotation form',
      'styles':{
        'top': annotation.getSize().y
      }
    }).inject( annotation );

    // Create our input fields
    var html = '<table><tr><td>Annotation Title</td><td><input type="text" name="title" autofocus';
    if( this.annotations[id].title ) html += ' value="' + this.annotations[id].title + '"';
    html += '/></td></tr>';

    // MEDICI ARCHIVE PROJECT START
    //html += '<tr><td>Annotation Type</td><td><input type="text" name="category"';
    //html += '<tr><td>Annotation Type</td><td><select name="category"><option value="general">General Annotation</option><option value="paleography">Paleography Annotation</option><option value="personal">Personal Annotation</option>';
    html += '<tr><td>Annotation Type</td><td><input name="category" type="radio"  value="GENERAL"';
    if( this.annotations[id].type == 'GENERAL' || this.annotations[id].type == '')
    	html += 'checked="true"';
    html +=	'>General<br><input name="category" type="radio" value="PALEOGRAPHY"';
    if( this.annotations[id].type == 'PALEOGRAPHY')
    	html += 'checked="true"';
    html +=	'>Paleography<br><input name="category" type="radio" value="PERSONAL"';
    if( this.annotations[id].type == 'PERSONAL')
    	html += 'checked="true"';
    html +=	'>Personal';
    // MEDICI ARCHIVE PROJECT END
    
    // MEDICI ARCHIVE PROJECT START 
//    if( this.annotations[id].type ) html += ' value="' + this.annotations[id].type + '"';
    
    //html += '/></td></tr>';
    html += '</td></tr>';

    if( this.annotations[id].type == 'PERSONAL'){
    	html += '<tr><td colspan="2"><textarea name="text" rows="5" id="annotationTextarea">' + (this.annotations[id].text||'') + '</textarea></td></tr></table>';
    }else{
    	html += '<tr><td colspan="2"><textarea name="text" rows="5" style="display:none;" id="annotationTextarea">' + (this.annotations[id].text||'') + '</textarea></td></tr></table>';
    }
    // MEDICI ARCHIVE PROJECT START
    html += '<input type="hidden" name="annotationId" value="' + this.annotations[id].annotationId + '">';
    html += '<input type="hidden" name="type" value="' + this.annotations[id].type + '">';
    // MEDICI ARCHIVE PROJECT END

    form.set( 'html', html );

    new Element('input', {
      'type': 'submit',
      'class': 'button',
      'value': 'ok'
    }).inject( form );

    new Element('input', {
      'type': 'reset',
      'class': 'button',
      'value': 'cancel'
    }).inject( form );

    if( this.annotations[id].deletable){
	    var del = new Element( 'input', {
	      'type': 'button',
	      'class': 'button delAnnotation',
	      'value': 'delete'
	    }).inject( form );
	    
	    var question = new Element( 'div', {
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
    }else{
    	var del;
    }
    
    //MEDICI ARCHIVE PROJECT START
    //To position the form
    if(annotation.getPosition().y + annotation.getSize().y + form.getSize().y > window.innerHeight){
    	form.setStyle('top',  - form.getSize().y - 3);
    }
    //MEDICI ARCHIVE PROJECT END

    // Add update event for our list of annotations
    form.addEvents({
      'submit': function(e){
        e.stop();
		_this.updateShape(this.getParent());
		var categoryArray = e.target['category'];
		var selectedCategory;
		for(var i = 0; i < categoryArray.length; i++){
			if(categoryArray[i].checked == true){
				selectedCategory = categoryArray[i].value;
			}
		}
		
		_this.annotations[id].type = selectedCategory;
		_this.annotations[id].title = e.target['title'].value;
		_this.annotations[id].text = e.target['text'].value;
		delete _this.annotations[id].edit;
		//MEDICI ARCHIVE PROJECT START
		_this.updateAnnotations();
		_this.fireEvent('annotationChange', _this.annotations);
	    //MEDICI ARCHIVE PROJECT END
		// MEDICI ARCHIVE PROJECT START
		_this.stopZoom = false;
		_this.container.getElement('div.navbuttons').getElement('img.zoomIn').style.opacity=1;
		_this.container.getElement('div.navbuttons').getElement('img.zoomOut').style.opacity=1;
		// MEDICI ARCHIVE PROJECT END
	 },
      'reset': function(){
		delete _this.annotations[id].edit;
		_this.updateAnnotations();
      }
    });

    //MEDICI ARCHIVE PROJECT START
    // Add a delete event to our annotation
//    del.addEvent('click', function(){
// 	   delete _this.annotations[id];
// 	   _this.updateAnnotations();
//	   _this.fireEvent('annotationChange', _this.annotations);
//	});
    
    if( this.annotations[id].deletable){
//	    del.addEvent('click', function(){
//	    	   delete _this.annotations[id];
//	    	   _this.updateAnnotations();
//			   _this.fireEvent('annotationChange', _this.annotations);
//			 });
    	
    	yesQuestion.addEvent('click', function(){
    		   delete _this.annotations[id];
	    	   _this.updateAnnotations();
			   _this.fireEvent('annotationChange', _this.annotations);
			 });
    }

    
    //MEDICI ARCHIVE PROJECT END


    //MEDICI ARCHIVE PROJECT START
    // Make it draggable and resizable, but prevent this interfering with our canvas drag
    // Update on completion of movement
//     var draggable = annotation.makeDraggable({
//      stopPropagation: true,
//      preventDefault: true,
//      container: this.canvas,
//    });

//    var resizable = annotation.makeResizable({
//      handle: handle,
//      stopPropagation: true,
//      preventDefault: true,
//      // Keep our form attached to the annotation
//      onDrag: function(){ form.setStyle('top', this.element.getSize().y ); }
//    });
    
    // Make it draggable and resizable, but prevent this interfering with our canvas drag
    // Update on completion of movement
    var draggable = annotation.makeDraggable({
      stopPropagation: true,
      preventDefault: true,
      container: this.canvas,
//      onDrop: function(){if(this.element.y > window.innerHeight) form.setStyle('top', this.element.x);}
      onDrop: function(){
    	  if(this.element.getPosition().y + this.element.getSize().y + form.getSize().y > window.innerHeight){
    		  form.setStyle('top',  - form.getSize().y - 3);
      	  }else{
      		  form.setStyle('top', this.element.getSize().y);
      	  }
      }
    });

    var resizable = annotation.makeResizable({
      handle: handle,
      stopPropagation: true,
      preventDefault: true,
      // Keep our form attached to the annotation
//      onDrag: function(){ form.setStyle('top', this.element.getSize().y ); }
      onDrag: function(){
    	  if(this.element.getPosition().y + this.element.getSize().y + form.getSize().y > window.innerHeight){
    		  form.setStyle('top',  - form.getSize().y - 3);
      	  }else{
      		  form.setStyle('top', this.element.getSize().y);
      	  }
      }
    });
    
    //MEDICI ARCHIVE PROJECT END


    // Set default focus on textarea
    annotation.addEvent( 'mouseenter', function(){
			   form.getElement('textarea').focus();
			   form.getElement('textarea').value = form.getElement('textarea').value;
			 });

    // Add focus events and reset values to deactivate text selection
    form.getElements('input,textarea').addEvents({
      'click': function(){
        this.focus();
        this.value = this.value;
       },
      'dblclick': function(e){ e.stop; },
      'mousedown': function(e){ e.stop(); },
      'mousemove': function(e){ e.stop(); },
      'mouseup': function(e){ e.stop(); }
    });

  },



  /* Update the coordinates of the annotation
   */
  updateShape: function(el){

    var id = el.get('id');

    // Update our list entry
    var parent = el.getParent();
    this.annotations[id].x = el.getPosition(parent).x / this.wid;
    this.annotations[id].y = el.getPosition(parent).y / this.hei;
    this.annotations[id].w = (el.getSize(parent).x-2) / this.wid;
    this.annotations[id].h = (el.getSize(parent).y-2) / this.hei;
  },


  updateAnnotations: function(){
    this.destroyAnnotations();
    this.createAnnotations();
    this.container.addEvent( 'keydown', this.key.bind(this) );
    if( this.annotationTip ) this.annotationTip.attach( 'div.annotation' );
  },


  toggleEditFlat: function(id){

  }


});
