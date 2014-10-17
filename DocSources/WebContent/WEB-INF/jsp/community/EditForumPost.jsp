<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ForumCSSURL" value="/styles/1024/forum.css"/>
	
	<c:url var="EditForumPostURL" value="/community/EditPost.json"/>
	
	<c:url var="ShowPreviewForumPostURL" value="/community/ShowPreviewForumPost.do"/>
	
	<c:if test="${not empty command.annotationId}">
		<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
			<c:param name="summaryId" value="${command.summaryId}"/>
			<c:param name="volNum" value="${command.volNum}"/>
			<c:param name="volLetExt" value="${command.volLetExt}"/>
			<c:param name="annotationId" value="${command.annotationId}" />
			<c:param name="imageOrder" value="${command.imageOrder}" />
			<c:param name="flashVersion"   value="false" />
			<c:param name="showHelp" value="true" />
			<c:param name="showThumbnail" value="true" />
		</c:url>
		
		<iframe class="iframeVolumeExplorer" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:100"></iframe>
		<div id="spacer" style="height: 15px;" />
	</c:if>
	
	<c:choose>
		<c:when test="${not empty documentExplorerError}">
			<div id="error" style="color: red;">It was not possible to retrieve the document view</div>
		</c:when>
		<c:when test="${not empty documentExplorer}">
			<c:url var="manuscriptViewerURL" value="/src/ShowManuscriptViewer.do">
				<c:param name="entryId" value="${documentExplorer.entryId}"/>
				<c:param name="imageOrder" value="${documentExplorer.image.imageOrder}" />
				<c:param name="flashVersion"   value="false" />
				<c:param name="showHelp" value="true" />
				<c:param name="showThumbnail" value="true" />
			</c:url>
			
			<iframe class="iframeVolumeExplorer" scrolling="no" marginheight="0" marginwidth="0" src="${manuscriptViewerURL}" style="z-index:100"></iframe>
			<div id="spacer" style="height: 15px;" />
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	
	<!-- Post to reply -->
	<c:if test="${not empty postToReply}">
		<div id="postTable">
			<div id="post">
				<h2>${postToReply.subject}</h2>
	            <p class="by">by <a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${postToReply.user.account}" id="userName" class="link">${postToReply.user.account}</a> &#xbb <span class="date">${postToReply.lastUpdate}</span></p>
	        	<p>${postToReply.text}</p>
	    	</div>
	    	<div id="postProfile">
	    		<ul>
	        		<li>
	        			<c:if test="${postToReply.user.portrait}">
	        				<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
								<c:param name="account" value="${postToReply.user.account}" />
								<c:param name="time" value="${time}" />
							</c:url>
	        				<img src="${ShowPortraitUserURL}" class="avatar"/>
	        			</c:if>
	        			<c:if test="${!postToReply.user.portrait}">
	        				<img class="avatar" src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
	        			</c:if>
	        			<a href="<c:url value="/community/ShowUserProfileForum.do"/>?account=${postToReply.user.account}" id="userName" class="link">${postToReply.user.account}</a>
	        		</li>
	            	<li>Community User</li>
	            	<li>Posts: <span>${postToReply.user.forumNumberOfPost}</span></li>
	            	<li>Joined: <span>${postToReply.user.forumJoinedDate}</span></li>
	        	</ul>
	    	</div>
	    	<div id="online" class="visible"></div> <!--  Se l'utente è loggato in quel momento inserire la class "visible" a questo div -->
		</div>
	</c:if>

	<c:choose>
		<c:when test="${command.topicId == 0}">
			<h1 style="margin-bottom:20px;">POST A NEW TOPIC</h1>
		</c:when>
		<c:otherwise>
			<h1 style="margin-bottom:20px;">POST REPLY</h1>
		</c:otherwise>
	</c:choose>

	<form:form id="EditForumPost" method="POST" class="edit" action="${EditForumPostURL}">
		<div>
			<form:label id="subjectLabel" for="subject" path="subject" cssErrorClass="error">Subject</form:label>
	        <form:input id="subject" path="subject" cssClass="input_25c"></form:input>
	    </div>
	    <div>
			<form:textarea id="htmlbox" name="text" path="text" style="width:970px; height:300px"></form:textarea>
	    </div>
	    
	    <a href="#" id="preview" class="buttonMedium button_medium">Preview</a>
	    <a href="#" id="discard" class="buttonMedium button_medium">Discard</a>
	    <a href="#" id="submit" class="buttonMedium button_medium">Submit</a>
	    <form:hidden path="parentPostId"/>
	    <form:hidden path="forumId"/>
	    <form:hidden path="topicId"/>
	    <form:hidden path="postId"/>

	</form:form>
	
	<div id="postTablePreviewContainer" title="Post" style="display:none; margin-top:45px">
		<h1>PREVIEW</h1>
		<div id="postTablePreview"></div>
		<a href="#" id="closePreview" class="buttonMedium button_medium">Close Preview</a>
	</div>

	<div id="messagePosted" title="Post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			This message has been posted successfully.
		</p>
	</div>

	<div id="messageNotPosted" title="Post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			This message has not been posted successfully.
		</p>
	</div>
	
	<div id="messageNotValid" title="Post" style="display:none"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			Write subject and text in this message!
		</p>
	</div>
	
	<div id="question" title="Discard" style="display:none; cursor: default"> 
		<p>
			<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
			Discard changes?
		</p> 
	</div>
	
	<script type="text/javascript">
		tinyMCE.init({
			// General options
			mode : "textareas",
			theme : "advanced",
			skin : "o2k7",
			content_css: "../styles/1024/forumMCE.css",
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
		
		$j(document).ready(function() {
			
			$j('#submit').click(function(){
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
				//MD: In variable 'text' I control if the user has inserted no words in the textarea
				var text = $j("#htmlbox").val();
				if($j("#subject").val() != '' && $j(text).text() != ''){
					$j.ajax({ type:"POST", url:"${EditForumPostURL}", data:$j("#EditForumPost").serialize(), async:false, success:function(json) {
		 				var topicUrl = json.topicUrl;
		 				if (json.operation == 'OK') {
							$j("#messagePosted").css('display','inherit');
							$j("#messagePosted").dialog({
								  autoOpen : false,
								  modal: true,
								  resizable: false,
								  width: 300,
								  height: 130, 
								  buttons: {
									  Ok: function() {
										  $j(this).dialog("close");
										  $j(this).dialog("destroy");
										  $j(this).appendTo("#main").css("display", "none");
										  $j("#main").load(topicUrl);
									  }
								  }
							  });
							$j("#messagePosted").dialog('open');
		 				} else {
							$j("#messageNotPosted").css('display','inherit');
							$j("#messageNotPosted").dialog({
								  autoOpen : false,
								  modal: true,
								  resizable: false,
								  width: 300,
								  height: 130, 
								  buttons: {
									  Ok: function() {
										  $j(this).dialog("close");
										  $j(this).appendTo("#main").css("display", "none");
									  }
								  }
							  });
							$j("#messageNotPosted").dialog('open');
		 				}
					}});
	
		 			/*$j('#postTable').css('display','inherit');
					$j.scrollTo({top:'300px',left:'0px'}, 800 );*/
					return false;
				}else{
					$j("#messageNotValid").css('display','inherit');
					$j("#messageNotValid").dialog({
						  autoOpen : false,
						  modal: true,
						  resizable: false,
						  width: 300,
						  height: 130, 
						  buttons: {
							  Ok: function() {
								  $j(this).dialog("close");
								  $j(this).appendTo("#main").css("display", "none");
							  }
						  }
					  });
					$j("#messageNotValid").dialog('open');
					return false;
				}
			});

			$j("#preview").click(function(){
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
	 			$j.ajax({ type:"POST", url:"${ShowPreviewForumPostURL}", data:$j("#EditForumPost").serialize(), async:false, success:function(html) {
	 				$j("#postTablePreview").html(html);
	 				setTimeout(function() {
						$j.scrollTo("#postTablePreview", 800);
		    		},200);
				}});
	 			$j('#postTablePreviewContainer').show();
				return false;
			});
			
			$j("#closePreview").click(function() {
				$j('#postTablePreview').html("");
				$j('#postTablePreviewContainer').hide();
				return false;
			});
			
			$j("#discard").click(function(){
				$j("#question").dialog({
					autoOpen : false,
					modal: true,
					resizable: false,
					width: 300,
					height: 130, 
					buttons: {
						Yes: function() {
							$j(this).dialog("close");
							$j("#main").load($j("#prevUrl").val());
						},
						No: function() {
							$j(this).dialog("close");
						}
					}
				});
				$j("#question").dialog('open');
				return false;
			});
			
			//MD: Fix a problem with tinyMCE alert when change page.
			window.onbeforeunload = function() {};
		});
	</script>