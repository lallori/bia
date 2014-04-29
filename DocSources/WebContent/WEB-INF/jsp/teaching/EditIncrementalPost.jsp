<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditIncrementalPostURL" value="/teaching/EditIncrementalPost.json"/>
	
	<c:url var="GetFolioFragmentsURL" value="/teaching/GetFolioFragments.json"/>
	
	<c:url var="ShowPreviewCourseTopicPostURL" value="/teaching/ShowIncrementalPostPreview.do"/>
	
	<c:url var="ShowCurrentTranscriptionURL" value="/teaching/ShowCurrentTranscription.json">
		<c:param name="topicId" value="${command.topicId}" />
	</c:url>
	
	<c:url var="ShowCourseTranscriptionActionsURL" value="/teaching/ShowCourseTranscriptionActions.do">
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="transcriptionMode" value="I" />
	</c:url>
	
	<security:authorize ifAnyGranted="ROLE_STUDENTS">
		<h6 style="margin-bottom: 10px;">EDIT YOUR TRANSCRIPTION</h6>
	</security:authorize>
	<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
		<h6 style="margin-bottom: 10px;">EDIT YOUR POST</h6>
	</security:authorize>

	<form:form id="EditCourseTopicPost" method="POST" class="edit" action="${EditIncrementalPostURL}" style="max-width: 1000px;">
		<div id="subjectSection">
			<form:label id="subjectLabel" for="subject" path="subject" cssErrorClass="error">Post Subject*</form:label>
	        <form:input id="subject" path="subject" cssClass="input_25c"></form:input>
	    </div>
	    <div id="folioDetailsSection" style="display: block;">
	    	<div id="hiddenFolioLocation" style="display: none;">
		    	<form:input id="volNum" path="volNum" cssClass="noStyle" readonly="true"></form:input>
		    	<form:input id="volLetExt" path="volLetExt" cssClass="noStyle" readonly="true"></form:input>
		    	<form:input id="insertNum" path="insertNum" cssClass="noStyle" readonly="true"></form:input>
		    	<form:input id="insertLet" path="insertLet" cssClass="noStyle" readonly="true"></form:input>
		    	<form:input id="folioNum" path="folioNum" cssClass="noStyle" readonly="true"></form:input>
		    	<form:input id="folioMod" path="folioMod" cssClass="noStyle" readonly="true"></form:input>
		    	<form:input id="folioRV" path="folioRV" cssClass="noStyle" readonly="true"></form:input>
		    </div>
	    	<span class="folioDetailsTitle">Folio Details </span>
	    	<span id="volumeFragment" style="display: none;" class="contentFragment">
	    		Volume
    			<span id="volume" class="fragmentDetail"></span>
	    	</span>
	    	<span id="insertFragment" style="display: none;" class="contentFragment">
    			Insert
	    		<span id="insert" class="fragmentDetail"></span>
	    	</span>
	    	<span id="folioFragment" style="display: none;" class="contentFragment">
	    		Folio
	    		<span id="folio" class="fragmentDetail"></span>
	    	</span>
	    	<a href="#" id="refreshLocation" class="buttonMedium button_medium"><span>Update</span></a>
	    </div>
	    <div id="transcriptionSection">
	    	<a href="#" id="showCurrentTranscription" class="buttonLarge button_large">Current Transcription</a>
	    	<form:label id="textboxLabel" for="textbox" path="transcription" cssErrorClass="error">Transcription Area</form:label>
	    	<form:textarea id="textbox" name="transcription" path="transcription" title="Edit your transcription"></form:textarea>
	    </div>
	    <security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
	    	<c:if test="${empty editingStudentPost or editingStudentPost == false}">
			    <div id="postTextArea">
			    	<form:label id="htmlboxLabel" for="htmlbox" path="text" cssErrorClass="error">Post Area</form:label>
					<form:textarea id="htmlbox" class="htmlbox" name="text" path="text" title="Edit your post"></form:textarea>
			    </div>
		    </c:if>
	    </security:authorize>
	    <div id="editPostFormCommands">
		    <a href="#" id="preview" class="buttonMedium button_medium">Preview</a>
		    <a href="#" id="discard" class="buttonMedium button_medium">Discard</a>
		    <input type="submit" value="Submit" class="buttonMedium button_medium" id="submit">
	    </div>
	    <form:hidden path="topicId"/>
	    <form:hidden id="formPostId" path="postId"/>

	</form:form>
	
	<div id="postTablePreview" title="Post" style="display: none;">
	</div>

	<div id="genericWarning" title="Post" style="display: none;"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>
			<span id="genericWarningMsg"></span>
		</p>
	</div>
	
	<div id="discardChanges" title="Discard" style="display: none;"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 0 0;"></span>
			Discard changes!
		</p>
	</div>
	
	
	<script type="text/javascript">
	
		<security:authorize ifAnyGranted="ROLE_ADMINISTRATORS, ROLE_TEACHERS">
			var isStudent = false;
		</security:authorize>
		<security:authorize ifAnyGranted="ROLE_STUDENTS">
			var isStudent = true;
		</security:authorize>
		
		// empty post text is allowed
		var cannotLeaveEmptyText = /*isStudent === false && ${empty editingStudentPost or editingStudentPost == false}*/ false;
		var cannotLeaveEmptyTranscription = isStudent === true;
	
		var getFolioFragmentsCallback = function() {
			var entryId = $j('#mainFrame').contents().find('#PageTurnerVerticalDiv #currentEntryId').val();
			var imageOrder = $j('#mainFrame').contents().find('#PageTurnerVerticalDiv #currentImageOrder').val();
			$j.ajax({
				type: "GET",
				url: "${GetFolioFragmentsURL}?&entryId=" + entryId + "&imageOrder=" + imageOrder,
				async: false,
				success: function(data) {
					if (data.operation == 'OK') {
		 				showFolioFragments(data);
					} else {
						// TODO: handle error condition
						console.log('SERVER ERROR');
					}
				},
				error: function(data) {
					// TODO: handle error condition
				}
			});
		}
		
		var showFolioFragments = function(data) {
			$j('#volNum').val(data.volNum);
			$j('#volume').text(' ' + data.volNum);
			if (typeof data.volLetExt !== 'undefined') {
				$j('#volLetExt').val(data.volLetExt);
				$j('#volume').append(' ' + data.volLetExt);
			}
			$j('#volumeFragment').css('display', 'inline');
			
			if (typeof data.insertNum !== 'undefined') {
				$j('#insertNum').val(data.insertNum);
				$j('#insert').text(data.insertNum);
				if (typeof data.insertLet !== 'undefined') {
					$j('#insertLet').val(data.insertLet);
					$j("#insert").append(' ' + data.insertLet);
				}
				$j('#insertFragment').css('display', 'inline');
			}
			
			$j('#folioNum').val(data.folioNum);
			$j('#folio').text('' + data.folioNum);
			if (typeof data.folioMod !== 'undefined') {
				$j('#folioMod').val(data.folioMod);
				$j('#folio').append(' ' + data.folioMod);
			}
			if (typeof data.folioRV !== 'undefined') {
				$j('#folioRV').val(data.folioRV);
				$j('#folio').append(' ' + data.folioRV);
			}
			$j('#folioFragment').css('display', 'inline');
		}
		
		$j(document).ready(function() {
			$j('#postsContainer').css('height','50%');
			$j('#editPostContainer').css('height','45%');
			var currentPageHref = $j('#postsContainer .paginateActive').first().attr('href');
			
			if (${empty command.volNum}) {
				// cannot read folio location from 'command'
				getFolioFragmentsCallback();
			} else {
				// read post folio location from 'command'
				var data = new Object();
				data.volNum = '${command.volNum}';
				if (${not empty command.volLetExt}) {
					data.volLetExt = '${command.volLetExt}';
				}
				if (${not empty command.insertNum}) {
					data.insertNum = '${command.insertNum}';
					if (${not empty command.insertLet}) {
						data.insertLet = '${command.insertLet}';
					}
				}
				if (${not empty command.folioNum}) {
					data.folioNum = '${command.folioNum}';
					if (${not empty command.folioMod}) {
						data.folioMod = '${command.folioMod}';
					}
					if (${not empty command.folioRV}) {
						data.folioRV = '${command.folioRV}';
					}
					showFolioFragments(data);
				}
			}
			
			$j('#submit').click(function() {
				if (cannotLeaveEmptyText) {
					$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
				}
				var text = $j("#htmlbox").val();
				var transcription = $j("#textbox").val();
				var subject = $j("#subject").val();
				
				var isNotEmptyText = typeof text !== 'undefined' && text !== '';
				var isNotEmptyTranscription = typeof transcription !== 'undefined' && transcription !== '';
				var isNotEmptySubject = typeof subject !== 'undefined' && subject !== '';
				
				if (isNotEmptySubject && (!cannotLeaveEmptyText || isNotEmptyText) && (!cannotLeaveEmptyTranscription || isNotEmptyTranscription)) {
					var postId = $j('#formPostId').val();
					$j.ajax({
						type: "POST",
						url: "${EditIncrementalPostURL}",
						data: $j("#EditCourseTopicPost").serialize(),
						async: false,
						success: function(data) {
			 				if (data.operation == 'OK') {
			 					// redirect to the last page because every post (added or modified) is the last in order
								var redirectUrl = data.topicUrl;
								var _postId = data.postId;
								
								$j("#postsContainer").load(redirectUrl, function(responseText, statusText, xhr) {
									if (statusText !== 'error') {
										setTimeout(function() {
											if (typeof $j('#postsContainer #postTable_' + _postId).get(0) !== 'undefined') {
												console.log('Scrolling to ' + postId);
												$j('#postsContainer').scrollTo("#postTable_" + _postId);
											} else {
												
											}
							    		},200);
									} else {
										// TODO: handle error condition
									}
								});
								$j("#editPostContainer").load('${ShowCourseTranscriptionActionsURL}', function(responseText, statusText, xhr) {
									if (statusText == 'error') {
										$j("#errorMsg").text('There was a server error during the page load: please refresh this page!');
										$j("#errorModal").dialog('open');
									}
								});
								$j('#postsContainer').css('height','85%');
								$j('#editPostContainer').css('height','10%');
			 				} else {
			 					$j("#genericWarningMsg").text('This message has not been posted successfully.');
								$j("#genericWarning").css('display','inherit');
								$j("#genericWarning").dialog('open');
			 				}
						},
						error: function(data) {
							
						}
					});
				} else {
					if (!isNotEmptySubject) {
						$j("#genericWarningMsg").text('You sholuld write the subject!');
					} else if (cannotLeaveEmptyText && !isNotEmptyText) {
						$j("#genericWarningMsg").text('You sholuld write the post text!');
					} else if (cannotLeaveEmptyTranscription && !isNotEmptyTranscription) {
						$j("#genericWarningMsg").text('You sholuld write the transcription!');
					}
					$j("#genericWarning").css('display','inherit');
					$j("#genericWarning").dialog('open');
				}
				return false;
			});
			

			$j('#refreshLocation').click(function() {
				getFolioFragmentsCallback();
				return false;
			});
			
			$j('#preview').click(function() {
				if (cannotLeaveEmptyText) {
					$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
				}
	 			$j.ajax({
	 				type: "POST",
	 				url: "${ShowPreviewCourseTopicPostURL}",
	 				data: $j("#EditCourseTopicPost").serialize(),
	 				async: false,
	 				success: function(html) {
	 					$j("#postTablePreview").html(html);
	 					$j("#postTablePreview").show();
					},
					error: function(data) {
						
					}
	 			});

	 			$j('#postTablePreview').css('display','inherit');
				return false;
			});
			
			$j("#showCurrentTranscription").click(function() {
				$j.ajax({
					type: "GET",
					url: "${ShowCurrentTranscriptionURL}",
					async: false,
					success: function(data) {
						if (data.operation === 'OK') {
							if (data.transcription !== null && data.transcription !== '') {
								$j("#transcriptionContent").html(data.transcription);
							} else {
								$j("#transcriptionContent").html("{ <b>Empty transcription</b> }");
							}
							$j("#transcriptionDialog").dialog('open');
						} else {
							$j("#errorMsg").text('There was a server error during this operation: please retry!');
							$j("#errorModal").dialog('open');
						}
					},
					error: function(data) {
						$j("#errorMsg").text('There was a server error during this operation: please retry!');
						$j("#errorModal").dialog('open');
					}
				});
				return false;
			});
			
			$j('#discard').click(function(){
				$j("#discardChanges").css('display','inherit');
				$j("#discardChanges").dialog('open');
				return false;
			});
			
			$j('#no').click(function() { 
				$j.unblockUI();
				$j(".blockUI").fadeOut("slow");
				$j("#question").hide();
				$j("#roundRobinPosts").append($j("#question"));
				$j(".blockUI").remove();
				return false;
			}); 
			
			/** Dialogs definitions **/
			
			$j("#genericWarning").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 300,
				height: 130, 
				buttons: {
					Ok: function() {
						$j(this).dialog("close");
					}
				}
			});
			
			$j("#discardChanges").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 300,
				height: 130, 
				buttons: {
					Yes: function() {
						$j(this).dialog("close");
						$j("#editPostContainer").load('${ShowCourseTranscriptionActionsURL}', function(responseText, statusText, xhr) {
							if (statusText == 'error') {
								$j("#errorMsg").text('There was a server error during the page load: please refresh this page!');
								$j("#errorModal").dialog('open');
							} else {
								// from ShowIncrementalCourseTranscriptionDOM
								setEditMode(false);
							}
						});
					},
					No: function() {
						$j(this).dialog("close");
					}
				}
			});
			
			//MD: Fix a problem with tinyMCE alert when change page.
			window.onbeforeunload = function() {};
		});
		
		// RR: if we try to modify an existent post we do not have to set the editor content
		// because this is read directly from the 'command'
	
		tinyMCE.init({
			// General options
			mode : "textareas",
			editor_selector : "htmlbox",
			theme : "advanced",
			skin : "o2k7",
			content_css: "../styles/1024/teachingMCE.css",
			plugins : "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups,autosave",
			language : "en",
		
			// Theme options
			theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fontsizeselect,|,cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,link,unlink,image,|,forecolor,backcolor",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			theme_advanced_resizing : true,
		
			// Drop lists for link/image/media/template dialogs
			template_external_list_url : "../../../scripts/forum/template_list.js",
			external_link_list_url : "../../../scripts/forum/link_list.js",
			external_image_list_url : "../../../scripts/forum/image_list.js",
			media_external_list_url : "../../../scripts/forum/media_list.js",
			
		
			// Replace values for the template plugin
			template_replace_values : {
				username : "Some User",
				staffid : "991234"
			}
		});
	</script>