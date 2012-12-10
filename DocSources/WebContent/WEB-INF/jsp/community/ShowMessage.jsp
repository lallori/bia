<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="ReplyMessageURL" value="/community/ComposeMessage.do">
		<c:param name="parentMessageId" value="${userMessage.messageId}"/>
	</c:url>
	
	<c:url var="InboxURL" value="/community/ShowMessagesByCategory.do">
		<c:param name="userMessageCategory"	value="INBOX"/>
	</c:url>

<%-- 	<c:url var="ReplyMessageURL" value="/community/ShowMessagesByCategory.do" /> --%>
	
	<c:if test="${userMessage.user.account == account}">
	<div id="message">
		<h3>Message</h3>
		<div class="list">
        	<div class="row">
            	<div class="item">From</div> 
            	<div class="value">${userMessage.sender}</div> 
       		</div>
        	<div class="row">
            	<div class="item">Subject</div> 
            	<div class="value">${userMessage.subject}</div> 
        	</div>
        	<div class="row">
        	    <div class="item">Message</div> 
        	    <div class="value" id="messageBody">${userMessage.body}</div> 
        	</div>
		</div>
	</div>
	</c:if>
	
	<a id="reply" class="buttonSmall" href="${ReplyMessageURL}">Reply</a>

					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; Return to <span>Board Index</span> Forum</a>

<div id="approveModal" title="Approve User" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Are you sure you want to approve this user?
	</p>
	
	<input type="hidden" value="" id="approveUrl"/>
</div>

<div id="notApproved" title="Approve User" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Error: Not approved
	</p>
</div>


<!-- <div id="deletePostModal" title="Delete post" style="display:none">  -->
<!-- 	<p> -->
<!-- 		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span> -->
<!-- 		Are you sure you want to delete this post? -->
<!-- 	</p> -->
<!-- </div> -->


	<script type="text/javascript">
		$j(document).ready(function() {
// 			$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
//    				$j("#chronologyDiv").html(json.chronology);
// 				$j(".arrowForum").css('visibility','visible');
// 				$j(".forum").css('visibility','visible');
//    			}});

			var block = $j("#messageBody").find("blockquote");
			$j("#messageBody > blockquote").remove();
			$j("#messageBody").append("<blockquote><p>" + $j(block).text() + "</p></blockquote>");
			

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
			
			$j('#reply').die();
			$j('#reply').live('click', function(){
				$j("#main").load($j(this).attr('href'));
				return false;
			});
						
			$j(".lnkLeft").die();
			$j(".lnkLeft").live('click', function(){
				window.opener.$j("#body_left").load($j(this).attr('href'));
				window.opener.alert("ok");
				return false;
			});
			
			$j( "#approveModal" ).dialog({
				  autoOpen : false,
				  modal: true,
				  resizable: false,
				  width: 300,
				  height: 130, 
				  buttons: {
					  Yes: function() {
						  $j.ajax({ type:"POST", url:$j("#approveUrl").val(), async:false, success:function(json) {
				 			   	if (json.operation == 'OK') {
				 					 $j("#main").load('${InboxURL}');
									 $j( "#approveModal" ).dialog('close');
									 return false;
				 				} else {
				 					$j( "#approveModal" ).dialog('close');
									$j("#notApproved").dialog({
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
									$j("#notApproved").dialog('open');
				 				}
							}});
							return false;
					  },
					  No: function() {
						  $j( "#approveModal" ).dialog('close');
					  }
				  }
			  });
			
			$j(".lnkApprove").die();
			$j(".lnkApprove").live('click', function(){
				$j("#approveUrl").val($j(this).attr('href'));
				$j('#approveModal').dialog('open');
				return false;
			});
		});
	</script>
