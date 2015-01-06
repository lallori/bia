<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ShowManageImagesForLessonsPageURL" value="/teaching/ShowManageImagesForLessons.do" />
	
	<c:url var="UploadImageURL" value="/teaching/UploadImage.do" />
	<c:url var="PrepareImageURL" value="/teaching/PrepareImage.do" />
	
	<div id="uploadNewImagesTitleSection">
		<h3>Upload Images</h3>
		<a href="#" class="button_large returnBtn">Back To Uploaded Images</a>
	</div>
	
	<div id="selectFileSection">
		<div class="title">Select images from your computer</div>
		<div class="commands">
			<a href="#" class="button_medium browseBtn">Browse...</a>
			<span id="selectedFiles">No files selected</span>
			<a href="#" class="button_small doUploadBtn" style="display: none;">Upload</a>
		</div>
		<input id="uploadFiles" type="file" multiple accept="image/jpeg,image/png" style="display: none;"/>
	</div>
	
	<div id="fileListSection">
		<table id="fileListTable" style="display: none;">
			<tr class="header">
				<th class="col0">#</th>
				<th class="col1">File Name</th>
				<th class="col2">Actions</th>
			</tr>
		</table>
	</div>
	
	<div id="uploadModal" title="[1 of 3] Prepare Upload Process" style="display: none;">
		<p>
			<span class="phase1">Please specify the images descriptions.</span>
			<span class="phase2" style="display: none;">Upload process <span class="phase2_little">[it can take a while]</span></span>
			<span class="phase3" style="display: none;">Conversion process <span class="phase3_little">[it can take a while]</span></span>
		</p> 
		<div class="theader">
			<table class="headerTable">
				<tr class="header">
					<th class="col0">#</th>
					<th class="col1">File Name</th>
					<th class="col2">Description</th>
				</tr>
			</table>
		</div>
		<div class="tbody">
			<table class="bodyTable">
			</table>
		</div>
		<div class="error" style="display: none;">
			<span id="descriptionMissing" style="display: none;">Images descriptions must be specified!</span>
			<span id="uploadError" style="display: none;">Some images have not been uploaded!</span>
			<span id="conversionError" style="display: none;">Some images have not been converted!</span>
		</div>
	</div>
	
	<div id="returnModal" title="Back to Uploaded Images list" style="display: none;">
		<p>If you go back to the uploaded images page you will loose the current data.<br/>Continue anyway?</p>
	</div>
	
	<div id="abortModal" title="Abort upload process" style="display: none;">
		<p>If you stop the upload process you'll loose the uploaded images.<br/>Continue anyway?</p>
	</div>
	
	<div id="completeModal" title="Upload Notification" style="display: none;">
		<p>The images upload and conversion processes have been completed successfully!</p>
	</div>
	
	
	<script type="text/javascript">
		$j(document).ready(function() {

			var inputs = [];
			var tableLength = 0;
			var uploadRunning = false;
			var decidingAbort = false;
			var aborted = false;

			$j(".doUploadBtn").die();
			$j(".doUploadBtn").click(function() {

				// XXX: check if modals were defined before
				if ($j(".uploadModal").length === 0) {
					initializeUploadModals();
				}
				
				// remove the close button in the upload-modal title toolbar
				$j(".uploadModal .ui-dialog-titlebar-close").hide();
				// hide the retry button
				$j("#retryNotUploadedBtn").hide();
				$j("#retryNotConvertedBtn").hide();
				$j("#leaveNotConvertedBtn").hide();

				$j("#uploadModal .tbody table").empty();
				
				for (var i = 0; i < inputs.length; i++) {
					$j("#uploadModal .tbody table").append(getModalTableRow(i, inputs[i].name, inputs[i].description));
				}
				$j("#uploadModal").dialog("open");
				
				var dialogHeight = tableLength < 7 ? (230 + tableLength * 30) : 420;
				$j("#uploadModal").dialog("option", "height", dialogHeight);
				
				return false;
			});
			
			$j(".browseBtn").die();
			$j(".browseBtn").click(function() {
				$j("#uploadFiles").click();
				return false;
			});
			
			$j("#uploadFiles").die();
			$j("#uploadFiles").change(function() {
				
				var files = $j("#uploadFiles").prop('files');
				
				for (var i = 0; i < files.length; i++) {

					if (files[i].name.match(/.jpg$/) || files[i].name.match(/.jpeg$/) || files[i].name.match(/.tiff$/)) {
						
						inputs[tableLength++] = {
							name: files[i].name,	// the image filename in the client
							description: '',		// the image description
							file: files[i],			// the image file
							fileName: '',			// the server image filename
							uploaded: false,		// true if image has been uploaded
							converted: false		// true if image has been converted
						};
						
						$j("#fileListTable").append(getNewTableRow(tableLength - 1, files[i].name));
						
					} else {
						alert('The file ' + files[i].name + ' is not supported!');
					}
				}
				
				redrawUpDownButtons();

				attachDeleteHandlers();
				attachMoveUpDownHandlers();
				
				
				if (tableLength > 0) {
					$j("#selectedFiles").html(tableLength + " files selected");
					$j("#fileListTable").show();
					$j(".doUploadBtn").show();
				}
			});
			
			$j(".returnBtn").die();
			$j(".returnBtn").click(function() {
				if (tableLength > 0) {
					if ($j(".returnModal").length === 0) {
						initializeReturnModal();
					}
					$j("#returnModal").dialog('open');
					return false;
				}
				if ($j(".returnModal").length > 0) {
					$j("#returnModal").dialog('destroy');
				}
				reloadUploadedImagesList();
				return false;
			});
			
			/* Modal definitions */
			
			function initializeReturnModal() {
				$j("#returnModal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 300,
					height: 130, 
					dialogClass: 'returnModal',
					buttons: [
						{
							id: 'returnOk',
							text: 'OK',
							click: function() {
								$j(this).dialog("close");
								$j(this).dialog("destroy");
								reloadUploadedImagesList();
								return false;
							}
						},
						{
							id: 'returnNo',
							text: 'No',
							click: function() {
								$j(this).dialog("close");
								return false;
							}
						}
					]
				});
			}
			
			function initializeUploadModals() {
			
				$j("#abortModal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 300,
					height: 130,
					dialogClass: 'abortModal',
					buttons: [
						{
							id: 'abortOk',
							text: 'OK',
							click: function() {
								$j(this).dialog("close");
								decidingAbort = false;
								aborted = true;
								prepareStep(1);
								return false;
							}
						},
						{
							id: 'abortNo',
							text: 'No',
							click: function() {
								$j(this).dialog("close");
								decidingAbort = false;
								$j("#uploadModal").dialog("open");
								return false;
							}
						}
					]
				});
				
				$j("#completeModal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 300,
					height: 130,
					dialogClass: 'completeModal',
					buttons: [
						{
							id: 'completeOk',
							text: 'OK',
							click: function() {
								$j(this).dialog("close");
								// remove all opened modals
								$j("#uploadModal").dialog('destroy');
								$j("#completeModal").dialog('destroy');
								// remove also the other modals
								$j(".ui-dialog").remove();
								reloadUploadedImagesList();
								return false;
							}
						}
					]
				});
			
				$j("#uploadModal").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 500,
					height: 150, 
					dialogClass: 'uploadModal',
					buttons: [
						{
							id: 'uploadBtn',
							text: 'Upload',
							click: function() {
								
								var errors = 0;
								$j("#uploadModal input.imageDescription").each(function() {
									if (typeof $j(this).val() === 'undefined' || $j(this).val().match(/^ * *$/)) {
										errors++;
										$j(this).addClass('emptyInput');
									} else {
										inputs[parseInt($j(this).attr('row'))].description = $j(this).val().trim();
										$j(this).removeClass('emptyInput');
									}
								});
								
								if (errors > 0) {
									showError('descriptionMissing');
									return false;
								} else {
									// now we can do the uploads
									removeError();
									prepareStep(2);
									
									$j("#uploadModal input.imageDescription").each(function() {
										var row = parseInt($j(this).attr('row'));
										if (!inputs[row].uploaded) {
											$j(this).replaceWith(nextStatusImage(row, 'start'));
										} else {
											$j(this).replaceWith(nextStatusImage(row, 'progress_upload'));
										}
									});
									
									uploadNext(0);
								}
								return false;
							} 
						},
						{
							id: 'retryNotUploadedBtn',
							text: 'Retry Not Uploaded',
							click: function() {
								removeError();
								$j("#retryNotUploadedBtn").hide();
								for (var i = 0; i < inputs.length; i++) {
									if (!inputs[i].uploaded) {
										nextStatusImage(i, 'error_upload');
									}
								}
								uploadNext(0);
								return false;
							}
						},
						{
							id: 'retryNotConvertedBtn',
							text: 'Retry Not Converted',
							click: function() {
								removeError();
								$j("#retryNotConvertedBtn").hide();
								$j("#leaveNotConvertedBtn").hide();
								for (var i = 0; i < inputs.length; i++) {
									if (!inputs[i].converted) {
										nextStatusImage(i, 'error_converted');
									}
								}
								convert(0);
								return false;
							}
						},
						{
							id: 'leaveNotConvertedBtn',
							text: 'Leave not Converted',
							click: function() {
								$j(this).dialog("close");
								// remove all the modal
								$j("#uploadModal").dialog('destroy');
								$j("#abortModal").dialog('destroy');
								$j("#returnModal").dialog('destroy');
								$j("#completeModal").dialog('destroy');
								reloadUploadedImagesList();
								return false;
							}
						},
						{
							id: 'abortBtn',
							text: 'Abort',
							click: function() {
								$j(this).dialog('close');
								if (uploadRunning) {
									decidingAbort = true;
									$j("#abortModal").dialog('open');
									return false;
								}
								prepareStep(1);
								return false;
							}
						}
					]
				});
			}
			
			/* functions */
			
			/**
			 * Attaches delete handlers to remove buttons in file list table.
			 */
			function attachDeleteHandlers() {
				$j("#fileListTable .deleteBtn").die();
				$j("#fileListTable .deleteBtn").click(function() {
					var row = parseInt($j(this).attr('row'));
					
					var removed = inputs.splice(row, 1);
					
					for (var i = row; i < inputs.length; i++) {
						$j("#fileListTable .row_" + i).next('td').html(inputs[i].name);
					}
					
					$j("#fileListTable .row_" + inputs.length).parent().remove();
					
					console.log(removed[0].name + " removed!");
					
					tableLength--;
					if (tableLength > 0) {
						$j("#selectedFiles").html(tableLength + " files selected");
					} else {
						$j("#selectedFiles").html("No files selected");
						$j("#fileListTable").hide();
						$j(".doUploadBtn").hide();
					}
					
					redrawUpDownButtons();
				});
			};
			
			/**
			 * Attaches move up/down handlers to move buttons in file list table.
			 */
			function attachMoveUpDownHandlers() {
				$j("#fileListTable .moveUpBtn, #fileListTable .moveDownBtn").die();
				$j("#fileListTable .moveUpBtn, #fileListTable .moveDownBtn").click(function() {
					if (!$j(this).hasClass('disabled')) {
						var direction = $j(this).hasClass('moveUpBtn') ? -1 : 1;
						var row = parseInt($j(this).attr('row'));
						$j("#fileListTable .row_" + row).next('td').html(inputs[row + direction].name);
						$j("#fileListTable .row_" + (row + direction)).next('td').html(inputs[row].name);
						
						var tmp = inputs[row];
						inputs[row] = inputs[row + direction];
						inputs[row + direction] = tmp;
					}
					return false;
				});
			};
			
			/**
			 * Fires the image conversion process.
			 *
			 * @param index the index of the image in the file list table
			 */
			function convert(index) {
				
				if (inputs.length <= index) {
					// conversion process has iterated over all the images
					var convertedNum = 0;
					for (var i = 0; i < inputs.length; i++) {
						if (inputs[i].converted) {
							convertedNum++;
						}
					}
					if (convertedNum == inputs.length) {
						// all it is OK
						$j("#uploadModal").dialog('close');
						$j("#completeModal").dialog('open');
					} else {
						$j("#retryNotConvertedBtn").show();
						$j("#leaveNotConvertedBtn").show();
						showError('conversionError');
					}
					return;
				}
				if (!inputs[index].uploaded) {
					convert(index + 1);
					return;
				}
				
				if (!inputs[index].converted) {
					nextStatusImage(index, 'scheduled_conversion');
					
					var data = new FormData();
					data.append('fileName', inputs[index].fileName);
					data.append('fileTitle', inputs[index].description);
					data.append('imageOrder', (index + 1));
					
					$j.ajax({
						url: '${PrepareImageURL}',
						data: data,
						cache: false,
						contentType: false,
						processData: false,
						type: "POST",
						success: function(json) {
							if (json.response == 'OK') {
								inputs[index].converted = true;
								nextStatusImage(index, 'progress_conversion');
							} else {
								nextStatusImage(index, 'progress_conversion', json.error);
							}
							convert(index + 1);
					    },
					    error: function() {
					    	nextStatusImage(index, 'progress_conversion', 'Server error...');
					    	convert(index + 1);
					    }
					});
				} else {
					convert(index + 1);
				}
				
			}
			
			/**
			 * Returns a table row to add to the modal file list table.
			 *
			 * @param row the row number of the table (first row --> 0)
			 * @param fileName the file name
			 * @param the file description
			 * @return the table row as HTML
			 */
			function getModalTableRow(row, fileName, fileDescription) {
				return "<tr class='listRow'>"
					+ "<td class='col0 row_" + row + "'>" + (row + 1) + "</td>"
					+ "<td class='col1'>" + fileName + "</td>"
					+ "<td class='col2'><input row='" + row + "' class='imageDescription' type='text' value='" + (fileDescription ? fileDescription : "") + "'/>" + "</td></tr>";					
			};
			
			/**
			 * Returns a table row to add to the file list table.
			 *
			 * @param row the row number of the table (first row --> 0)
			 * @param fileName the file name
			 * @return the table row as HTML
			 */
			function getNewTableRow(row, fileName) {
				
				var deleteBtn = "<img class='deleteBtn' row='" + row + "' />";
				var moveUp = "<img style='display: none;' class='moveUpBtn' row='" + row + "' />";
				var moveUpDisabled = "<img style='display: none;' class='moveUpBtn disabled' row='" + row + "' />";
				var moveDown = "<img style='display: none;' class='moveDownBtn' row='" + row + "' />";
				var moveDownDisabled = "<img style='display: none;' class='moveDownBtn disabled' row='" + row + "' />";
				
				return "<tr class='listRow'>"
					+ "<td class='col0 row_" + row + "'>" + (row + 1) + "</td>"
					+ "<td class='col1'>" + fileName + "</td>"
					+ "<td class='col2'>" + deleteBtn + moveUp + moveUpDisabled + moveDown + moveDownDisabled + "</td></tr>";
			};
			
			/**
			 * Changes the image status icon.
			 *
			 * @param row the row number of the table (first row --> 0) where the image has to be located
			 * @param status the previous image status
			 * @param error the error to show (if needed) as image title attribute
			 * @return the image handler
			 */
			function nextStatusImage(row, status, error) {
				var $image = $j(".currentStateImg[row=" + row + "]");
				if ($image.length === 0) {
					// status image is not in the view --> created here
					$image = $j("<img class='currentStateImg' row='" + row + "' />");
				}
				var newStatus = '';
				var newTitle = '';
				
				if (error) {
					newStatus = status.indexOf('upload') > -1 ? 'error_upload' : 'error_conversion';
					newTitle = error;
				} else {
					switch (status) {
						case 'start':
							newStatus = 'scheduled_upload';
							newTitle = 'Scheduled upload';
							break;
						case 'scheduled_upload':
							newStatus = 'progress_upload';
							newTitle = 'Upload in progress...';
							break;
						case 'progress_upload':
							newStatus = 'completed_upload';
							newTitle = 'Upload done';
							break;
						case 'error_upload':
							// this is for retry (upload) process
							newStatus = 'scheduled_upload';
							newTitle = 'Scheduled upload';
							break;
						case 'completed_upload':
							newStatus = 'scheduled_conversion';
							newTitle = 'Scheduled conversion';
							break;
						case 'scheduled_conversion':
							newStatus = 'progress_conversion';
							newTitle = 'Conversion in progress...';
							break;
						case 'progress_conversion':
							newStatus = 'completed_conversion';
							newTitle = 'Conversion done';
							break;
						case 'error_conversion':
							// this is for retry (conversion) process
							newStatus = 'scheduled_conversion';
							newTitle = 'Scheduled conversion';
							break;
					}
				}
				
				$j($image).removeClass(status);
				$j($image).addClass(newStatus);
				$j($image).attr('title', newTitle);
				return $image;
			}

			/**
			 * Initializes modal to the provided step.
			 *
			 * @param stepNo the step to initialize (one of 1, 2 or 3)
			 */
			function prepareStep(stepNo) {
				switch (stepNo) {
					case 1:
						$j(".uploadModal .ui-dialog-titlebar .ui-dialog-title").text("[1 of 3] Prepare Upload Process");
						$j("#uploadModal .phase1").show();
						$j("#uploadModal .phase2").hide();
						$j("#uploadModal .phase3").hide();
						$j("#uploadBtn").show();
						$j("#retryNotUploadedBtn").hide();
						$j("#retryNotConvertedBtn").hide();
						$j("#leaveNotConvertedBtn").hide();
						$j("#uploadModal .theader .headerTable th[class='col2']").html("Description");
						removeError();
						$j(".uploadModal .ui-dialog-titlebar-close").show();
						break;
					case 2:
						$j(".uploadModal .ui-dialog-titlebar .ui-dialog-title").text("[2 of 3] Uploading...");
						$j("#uploadModal .phase1").hide();
						$j("#uploadModal .phase2").show();
						$j("#uploadBtn").hide();
						$j("#uploadModal .theader .headerTable th[class='col2']").html("Progress");
						break;
					case 3:
						$j(".uploadModal .ui-dialog-titlebar .ui-dialog-title").text("[3 of 3] Converting...");
						$j("#uploadModal .phase2").hide();
						$j("#uploadModal .phase3").show();
						$j("#abortBtn").hide();
						break;
				}
			}
			
			/**
			 * Refreshes the up and down buttons status in the file list table.
			 */
			function redrawUpDownButtons() {
				$j("#fileListTable .moveUpBtn, #fileListTable .moveDownBtn").each(function() {
					var isDisabled = $j(this).hasClass('disabled');
					var isFirstRow = $j(this).attr('row') === '0';
					var isLastRow = parseInt($j(this).attr('row')) === tableLength - 1;
					
					if (($j(this).hasClass('moveUpBtn') && 
							(isDisabled && isFirstRow || !isDisabled && !isFirstRow)) ||
						($j(this).hasClass('moveDownBtn') &&
							(isDisabled && isLastRow || !isDisabled && !isLastRow))) {
						$j(this).show();
					} else {
						$j(this).hide();
					}
				});
			};
			
			/**
			 * Redirects to uploaded images list page.
			 */
			function reloadUploadedImagesList() {
				$j("#fileListTable .deleteBtn").die();
				$j("#fileListTable .moveUpBtn, #fileListTable .moveDownBtn").die();
				$j("#body_left").load('${ShowManageImagesForLessonsPageURL}', function(responseText, statusText, xhr) {
					if (statusText === 'error') {
						alert('Server error...if problem persists please contact the admin!');
					}
				});
			}

			/**
			 * Removes errors showed from the modal.
			 */
			function removeError() {
				showError();
			}
			
			/**
			 * Shows the error section in the 'upload modal'.
			 *
			 * @param errorId the error identifier to show, if none it hides the error section
			 */
			function showError(errorId) {
				$j("#uploadModal .error span").each(function() {
					if ($j(this).attr('id') === errorId) {
						$j(this).show();
					} else {
						$j(this).hide();
					}
				});
				if (typeof errorId !== 'undefined') {
					$j("#uploadModal .error").show();
				} else {
					$j("#uploadModal .error").hide();
				}
			}
			
			/**
			 * Fires the upload process of an image.
			 *
			 * @param index the index of the image in the modal file image list
			 */
			function uploadNext(index) {
				if (aborted || inputs.length <= index) {
					if (aborted) {
						// The upload process has been aborted by the user
						aborted = false;
					}
					uploadRunning = false;
					if (inputs.length <= index) {
						// upload process has iterated over all the images
						var someErrors = false;
						for (var i = 0; i < inputs.length; i++) {
							if (!inputs[i].uploaded) {
								someErrors = true;
								break;
							}
						}
						if (!someErrors) {
							// conversion process can be fired
							prepareStep(3);
							$j('.currentStateImg').each(function() {
								nextStatusImage($j(this).attr('row'), 'completed_upload');
							});
							convert(0);
						} else {
							showError('uploadError');
							$j("#retryNotUploadedBtn").show();
						}
					}
					return;
				}
				
				uploadRunning = true;
				
				if (!inputs[index].uploaded) {
					// the image has not been uploaded yet
					nextStatusImage(index, 'scheduled_upload');
	
					var f = inputs[index];
					var data = new FormData();
					
					data.append('name', f.name);
					data.append('title', f.description);
					data.append('file', f.file);
					
					$j.ajax({
						url: '${UploadImageURL}',
						data: data,
						cache: false,
						contentType: false,
						processData: false,
						type: 'POST',
						success: function(json) {
							if (json.response == 'OK') {
								inputs[index].fileName = json.fileName;
								inputs[index].uploaded = true;
								nextStatusImage(index, 'progress_upload');
							} else {
								nextStatusImage(index, 'progress_upload', json.error);
							}
							uploadNext(index + 1);
					    },
					    error: function() {
							nextStatusImage(index, 'progress_upload', 'Server error...');
					        uploadNext(index + 1);
					    }
					});
				} else {
					uploadNext(index + 1);
				}
			};
			
		});
	</script>