<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditRoundRobinPostURL" value="/teaching/EditRoundRobinPost.json"/>
	
	<c:url var="GetFolioFragmentsURL" value="/teaching/GetFolioFragments.json"/>
	
	<c:url var="ShowPostPreviewURL" value="/teaching/ShowRoundRobinPostPreview.do"/>
	
	<c:url var="ShowCourseTranscriptionActionsURL" value="/teaching/ShowCourseTranscriptionActions.do">
		<c:param name="topicId" value="${command.topicId}" />
		<c:param name="transcriptionMode" value="R" />
	</c:url>
	
	<h6 style="margin-bottom: 10px;">EDIT YOUR POST</h6>

	<form:form id="EditCourseTopicPost" method="POST" class="edit" action="${EditRoundRobinPostURL}" style="max-width: 1000px;">
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
	    	<span class="folioDetailsTitle">Folio Details</span>
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
	    <div id="postTextArea">
			<form:textarea id="htmlbox" name="text" path="text" style="width:95%; height:290px; max-width:1000px;"></form:textarea>
	    </div>
	    <div id="editPostFormCommands">
		    <a href="#" id="preview" class="buttonMedium button_medium">Preview</a>
		    <a href="#" id="discard" class="buttonMedium button_medium">Discard</a>
		    <a href="#" id="submit" class="buttonMedium button_medium">Submit</a>
	    </div>
	    <form:hidden path="topicId"/>
	    <form:hidden id="formPostId" path="postId"/>

	</form:form>
	
	<div id="postTablePreview" title="Post" style="display:none;">
	</div>

	<div id="genericWarning" title="Post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			<span id="genericWarningMsg"></span>
		</p>
	</div>
	
	<div id="discardChanges" style="display:none; cursor: default"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			Discard changes!
		</p>
	</div>
	
	
	<script type="text/javascript">
	
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
			
			if (${not empty command.quote && command.quote}) {
				$j("#formPostId").val(0);
				var quotePost = new QuotePostGenerator().getQuotePost(${command.postId});
				$j('#htmlbox').html(quotePost);
			}
			
			$j('#submit').click(function() {
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
				var text = $j("#htmlbox").val();
				var subject = $j("#subject").val();
				if (typeof subject !== 'undefined' && subject !== '' && typeof text !== 'undefined' && text !== '') {
					var postId = $j('#formPostId').val();
					$j.ajax({
						type: "POST",
						url: "${EditRoundRobinPostURL}",
						data: $j("#EditCourseTopicPost").serialize(),
						async: false,
						success: function(data) {
			 				if (data.operation == 'OK') {
								var redirectUrl;
								var isOldPost = typeof postId !== 'undefined' && postId > 0;
								var _postId = data.postId;
								if (isOldPost) {
									// existent post was updated -> get the current page url from paginator (page is not changed)
									redirectUrl = currentPageHref;
									_postId = postId;
								}
								if (typeof redirectUrl === 'undefined') {
									redirectUrl = data.topicUrl;
								}
								
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
					$j("#genericWarningMsg").text('Write subject and text in this message!');
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
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
	 			$j.ajax({
	 				type: "POST",
	 				url: "${ShowPostPreviewURL}",
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
								// from ShowRoundRobintranscriptionDOM
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
			theme : "advanced",
			skin : "o2k7",
			content_css: "../styles/1024/teachingMCE.css",
			plugins : "autolink,lists,pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template,inlinepopups,autosave",
			language : "en",
		
			// Theme options
			theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fontsizeselect,|,cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,|,undo,redo,|,link,unlink,image,|,forecolor,backcolor",
// 			theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,|,forecolor,backcolor",
// 			theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
// 			theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak,restoredraft",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_statusbar_location : "bottom",
			theme_advanced_resizing : true,
		
			// Example word content CSS (should be your site CSS) this one removes paragraph margins
// 			content_css : "css/word.css",
		
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