update <%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<form id="composeMessageForm">
	<div>
        <label for="to" id="toLabel">To</label>
        <input id="to" name="subject" class="input_25c" type="text" value=""/><!-- Autocompleter members -->
    </div>
	<div>
        <label for="subject" id="subjectLabel">Subject</label>
        <input id="subject" name="subject" class="input_25c" type="text" value=""/>
    </div>
    <div>
		<textarea id="htmlbox" style="width:970px; height:300px"></textarea>
    </div>
    <input type="submit" value="Send" class="buttonSmall" id="send">
    <a href="#" id="preview" class="buttonSmall">Preview</a>
</form>

<div id="messageSent" title="Sent Message" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 0 0;"></span>
		This message has been sent successfully.
	</p>
</div>

<div id="messagePreview" style="display:none">
	<h1>PREVIEW</h1>
    <h2>Subject example</h2>
    <p>This is an example of a message preview.</p>
</div>
					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; Return to <span>Board Index</span> Forum</a>


<!-- <div id="deletePostModal" title="Delete post" style="display:none">  -->
<!-- 	<p> -->
<!-- 		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span> -->
<!-- 		Are you sure you want to delete this post? -->
<!-- 	</p> -->
<!-- </div> -->


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
// 			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
//    				$j("#chronologyDiv").html(json.chronology);
// 				$j(".arrowForum").css('visibility','visible');
// 				$j(".forum").css('visibility','visible');
//    			}});

			$j('.pageHref').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.pageHref').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
// 			$j('.boardIndex').die();
			// Result links have a specific class style on which we attach click live. 
// 			$j('.boardIndex').live('click', function() {
// 				$j("#main").load($j(this).attr("href"));
// 				return false;
// 			});

			$j('.returnTo').click(function(){
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('.paginateForumButton').die();
			// Result links have a specific class style on which we attach click live. 
			$j('.paginateForumButton').live('click', function() {
				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j('#preview').click(function(){
				$j('#messagePreview').css('display','inherit');
				$j.scrollTo({top:'250px',left:'0px'}, 800 );
	         });
			
		});
	</script>
