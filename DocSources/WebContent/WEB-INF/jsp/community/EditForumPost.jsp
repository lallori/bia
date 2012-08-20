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
			<textarea id="htmlbox" name="text" style="width:970px; height:300px"></textarea>
	    </div>
	    
	    <input type="submit" value="Submit" class="buttonSmall" id="submit" onclick="instance.post();">
	    <a href="#" id="preview" class="buttonSmall">Preview</a>
	    <form:hidden path="parentPostId"/>
	    <form:hidden path="forumId"/>
	    <form:hidden path="topicId"/>
	    <form:hidden path="postId"/>

	</form:form>
<script type="text/javascript">
		$j("#htmlbox").htmlbox({
		    toolbars:[[
		    // Cut, Copy, Paste
		    "separator","cut","copy","paste",
		    // Undo, Redo
		    "separator","undo","redo",
		    // Bold, Italic, Underline, Strikethrough, Sup, Sub
		    "separator","bold","italic","underline","strike","sup","sub",
		    // Left, Right, Center, Justify
		    "separator","justify","left","center","right",
		    // Ordered List, Unordered List, Indent, Outdent
		    "separator","ol","ul","indent","outdent",
		    // Hyperlink, Remove Hyperlink, Image
		    "separator","link","unlink","image"
		    
		    ]],
		    css:"body{font-family:georgia;font-size:12px;background:#FBF7F4;}",
		    about:false,
		    icons:"silk",
		    skin: "gray"
		});

		var delay = (function(){
		       var timer = 0;
		       return function(callback, ms){
		         clearTimeout (timer);
		         timer = setTimeout(callback, ms);
		       };
			 })();
		                                
		delay(function(){
		           $j("#htmlbox_wrap").css({
		                    "border-color":"#DCC0BA",
		                    "background":"#DCC0BA"
		           });
					}, 1 );		
		
		$j(document).ready(function() {
			$j('#submit').click(function(){
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
								  }
							  }
						  });
						$j("#messageNotPosted").dialog('open');
	 				}
				}});

	 			/*$j('#postTable').css('display','inherit');
				$j.scrollTo({top:'300px',left:'0px'}, 800 );*/
				return false;
			});

			$j('#preview').click(function(){
	 			$j.ajax({ type:"POST", url:"${ShowPreviewForumPostURL}", data:$j("#EditForumPost").serialize(), async:false, success:function(html) {
	 				$j("#postTable").html(html);
				}});

	 			$j('#postTable').css('display','inherit');
				$j.scrollTo({top:'300px',left:'0px'}, 800 );
				return false;
			});
		});
</script>

	<div id="postTable" title="Post" style="display:none; margin-top:15px">
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
