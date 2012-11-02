<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ForumCSSURL" value="/styles/forum/forum.css"/>
	
	<c:url var="EditForumPostURL" value="/community/EditPost.json"/>
	
	<c:url var="ShowPreviewForumPostURL" value="/community/ShowPreviewForumPost.do"/>

	<c:if test="${command.topicId == '0'}">
	<h1 style="margin-bottom:20px;">POST A NEW TOPIC</h1>
	</c:if>

	<c:if test="${command.topicId != '0'}">
	<h1 style="margin-bottom:20px;">POST REPLY</h1>
	</c:if>

	<form:form id="EditForumPost" method="POST" class="edit" action="${EditForumPostURL}">
		<div>
			<form:label id="subjectLabel" for="subject" path="subject" cssErrorClass="error">Subject</form:label>
	        <form:input id="subject" path="subject" cssClass="input_25c"></form:input>
	    </div>
	    <div>
			<form:textarea id="htmlbox" name="text" path="text" style="width:970px; height:300px"></form:textarea>
	    </div>
	    
	    <a href="#" id="preview" class="buttonSmall">Preview</a>
	    <a href="#" id="discard" class="buttonSmall">Discard</a>
	    <input type="submit" value="Submit" class="buttonSmall" id="submit" onclick="instance.post();">
	    <form:hidden path="parentPostId"/>
	    <form:hidden path="forumId"/>
	    <form:hidden path="topicId"/>
	    <form:hidden path="postId"/>

	</form:form>
<script type="text/javascript">
		tinyMCE.init({
			// General options
			mode : "textareas",
			theme : "advanced",
			skin : "o2k7",
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

			$j('#preview').click(function(){
				$j("#htmlbox").text(tinyMCE.get('htmlbox').getContent());
	 			$j.ajax({ type:"POST", url:"${ShowPreviewForumPostURL}", data:$j("#EditForumPost").serialize(), async:false, success:function(html) {
	 				$j("#postTable").html(html);
				}});

	 			$j('#postTable').css('display','inherit');
				$j.scrollTo({top:'300px',left:'0px'}, 800 );
				return false;
			});
			
			$j('#discard').click(function(){
				$j('#main').block({ message: $j('#question'),
					css: { 
						border: 'none', 
						padding: '5px',
						boxShadow: '1px 1px 10px #666',
						'-webkit-box-shadow': '1px 1px 10px #666'
						} ,
						overlayCSS: { backgroundColor: '#999' }	
				}); 
				return false;
			});
			
			//MD: Fix a problem with tinyMCE alert when change page.
			window.onbeforeunload = function() {};
		});
</script>

	<div id="postTable" title="Post" style="display:none; margin-top:45px">
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
	
	<div id="question" style="display:none; cursor: default"> 
		<p>Discard changes?</p> 
		<input type="button" id="yes" value="Yes" /> 
		<input type="button" id="no" value="No" /> 
	</div>
	
	<script type="text/javascript">
	$j(document).ready(function() {
		$j('#no').click(function() { 
			$j.unblockUI();
			$j(".blockUI").fadeOut("slow");
			$j("#question").hide();
			$j("#main").append($j("#question"));
			$j(".blockUI").remove();
			return false; 
		}); 
        
		$j('#yes').click(function() { 
			$j("#main").load($j("#prevUrl").val());				
			return false; 
		}); 
     
	});
</script>
