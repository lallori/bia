<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EditCourseTopicPostURL" value="/teaching/EditRoundRobinPost.json"/>
	
	<c:url var="ShowPreviewCourseTopicPostURL" value="/teaching/ShowPreviewCourseTopicPost.do"/>
	
	<c:url var="ShowCourseTopicActionsURL" value="/teaching/ShowCourseTopicActions.do">
		<c:param name="topicId" value="${command.topicId}" />
	</c:url>
	
	<h6 style="margin-bottom: 10px;">EDIT YOUR POST</h6>

	<form:form id="EditCourseTopicPost" method="POST" class="edit" action="${EditCourseTopicPostURL}" style="max-width: 1000px;">
		<div>
			<form:label id="subjectLabel" for="subject" path="subject" cssErrorClass="error">Post Subject*</form:label>
	        <form:input id="subject" path="subject" cssClass="input_25c"></form:input>
	    </div>
	    <div>
			<form:textarea id="htmlbox" name="text" path="text" style="width:95%; height:290px; max-width:1000px;"></form:textarea>
	    </div>
	    
	    <a href="#" id="preview" class="buttonSmall button_small">Preview</a>
	    <a href="#" id="discard" class="buttonSmall button_small">Discard</a>
	    <input type="submit" value="Submit" class="buttonSmall button_small" id="submit">
	    <form:hidden path="topicId"/>
	    <form:hidden id="formPostId" path="postId"/>

	</form:form>
	
	<div id="postTablePreview" title="Post" style="display:none; margin-top:45px">
	</div>

	<div id="messagePosted" title="Post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			This message has been posted successfully.
		</p>
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
		
		$j(document).ready(function() {
			$j('#postsContainer').css('height','50%');
			$j('#editPostContainer').css('height','45%');
			
			if (${not empty command.quote && command.quote}) {
				$j("#formPostId").val(0);
				var quotePost = new QuotePostGenerator().getQuotePost(${command.postId});
				$j('#htmlbox').html(quotePost);
			}
			
			$j('#submit').click(function() {
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
				//MD: In variable 'text' I control if the user has inserted no words in the textarea
				var text = $j("#htmlbox").val();
				var subject = $j("#subject").val();
				if (typeof subject !== 'undefined' && subject !== '' && typeof text !== 'undefined' && text !== '') {
					$j.ajax({
						type: "POST",
						url: "${EditCourseTopicPostURL}",
						data: $j("#EditCourseTopicPost").serialize(),
						async: false,
						success: function(data) {
			 				if (data.operation == 'OK') {
								$j("#messagePosted").css('display','inherit');
								$j("#messagePosted").data('url', data.topicUrl).dialog('open');
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
			

			$j('#preview').click(function() {
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
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
			
			$j("#messagePosted").dialog({
				autoOpen : false,
				modal: true,
				resizable: false,
				width: 300,
				height: 130, 
				buttons: {
					Ok: function() {
						var topicUrl = $j(this).data('url');
						$j(this).dialog("close");
						$j("#postsContainer").load(topicUrl);
						$j("#editPostContainer").load('${ShowCourseTopicActionsURL}', function(responseText, statusText, xhr) {
							if (statusText == 'error') {
								$j("#errorMsg").text('There was a server error during the page load: please refresh this page!');
								$j("#errorModal").dialog('open');
							}
						});
						$j('#postsContainer').css('height','85%');
						$j('#editPostContainer').css('height','10%');
					}
				}
			});
			
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
						$j("#editPostContainer").load('${ShowCourseTopicActionsURL}', function(responseText, statusText, xhr) {
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