<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ForumCSSURL" value="/styles/forum/forum.css"/>
	
	<c:url var="EditForumPostURL" value="/community/EditForumPost.do"/>
	
	<c:url var="ShowPreviewForumPostURL" value="/community/ShowPreviewForumPost.do"/>
							
	<h1 style="margin-bottom:20px;">POST A NEW TOPIC</h1>

	<form:form id="EditForumPost" method="POST" class="edit" action="${EditForumPostURL}">
		<div>
			<form:label id="subjectLabel" for="subject" path="subject" cssErrorClass="error">Subject</form:label>
	        <form:input id="subject" path="subject" cssClass="input_4c"></form:input>
	    </div>
	    <div>
	    	<label for="topic" id="topicLabel">Topic</label>
			<textarea id="text" name="text" style="width:1000px; height:300px"></textarea>
	    </div>
	    
	    <input type="submit" value="Submit" class="buttonSmall" id="submit" onclick="instance.post();">
	    <a href="#" id="preview" class="buttonSmall">Preview</a>
	    <form:hidden path="parentPostId"/>
	    <form:hidden path="forumId"/>
	    <form:hidden path="id"/>

<script type="text/javascript">
	$j("#text").htmlbox({
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
		about:false
	});

	$j(document).ready(function() {
		$j('#submit').click(function(){
 			$j.ajax({ type:"POST", url:"${EditForumPostURL}", data:$j("#EditForumPost").serialize(), async:false, success:function(json) {
				$("#messagePosted").empty().html(response).dialog('open');
			}});

 			$j('#postTable').css('display','inherit');
			$j.scrollTo({top:'300px',left:'0px'}, 800 );
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

	</form:form>

	<div id="postTable" title="Post" style="display:none; margin-top:15px">
	</div>

	<div id="messagePosted" title="Post" style="display:none"> 
	</div>
