<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<c:url var="EraseMessagesURL" value="/community/EraseMessages.json" />
	
	<div id="messagesTable">
		<div class="list">
        	<c:if test="${command.category == 'inbox'}">
	        	<div class="rowFirst">
	           		<div class="one">FROM</div>
	            	<div class="two">MESSAGE</div>
	            	<div class="three">DATE</div>
	        	</div>
        	</c:if>
        	<c:if test="${command.category == 'outbox'}">
        		<div class="rowFirst">
        			<div class="one">TO</div>
        			<div class="two">MESSAGE</div>
        			<div class="three">DATE</div>
        		</div>
        	</c:if>

<%-- <h2>${topic.subject }</h2> --%>

<!-- <div id="topicActions"> -->
<%-- 	<a href="${ReplyForumPostURL}" class="buttonMedium" id="postReply"><img src="<c:url value="/images/forum/img_reply.png"/>" alt="post a reply" width="17" height="15" /><span class="button_text">Post a <b>reply</b></span></a> --%>
<!--     <div id="searchThisForumFormDiv"> -->
<%--         <form id="SearchForm" action="/DocSources/src/SimpleSearch.do" method="post"> --%>
<!--             <input id="text" name="text" type="text" value="Search this forum..."> -->
<!--             <input id="search" type="submit" title="Search" value="Search"/> -->
<%--         </form> --%>
<!--     </div> -->
<!--     <a href="#" id="printButton" class="buttonMedium"><img src="/DocSources/images/forum/img_print.png" alt="Print discussion" width="17" height="15" /><span class="button_text">Print discussion</span></a> -->
<!-- </div> -->

			<c:forEach items="${messageboxPage.list}" var="currentMessage" varStatus="status">
			
			<c:if test="${command.category == 'inbox' && currentMessage.recipientStatus != 'READ'}">
				<div class="<c:if test="${not status.last}">rowNew</c:if><c:if test="${status.last}">rowLastNew</c:if>">
					<div class="one"><input type="checkbox" name="css" value="css" id="${currentMessage.messageId}"/><a class="messageLink" href="<c:url value="/community/ShowMessage.do?messageId=${currentMessage.messageId}"/>">${currentMessage.sender}<span class="subject" style="display:none">${currentMessage.subject}</span></a></div>
			</c:if>
			<c:if test="${command.category == 'inbox' && currentMessage.recipientStatus == 'READ'}">
				<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">
					<div class="one"><input type="checkbox" name="css" value="css" id="${currentMessage.messageId}"/><a class="messageLink" href="<c:url value="/community/ShowMessage.do?messageId=${currentMessage.messageId}"/>">${currentMessage.sender}<span class="subject" style="display:none">${currentMessage.subject}</span></a></div>
			</c:if>
			<c:if test="${command.category == 'outbox'}">
				<div class="<c:if test="${not status.last}">row</c:if><c:if test="${status.last}">rowLast</c:if>">	
					<div class="one"><input type="checkbox" name="css" value="css" id="${currentMessage.messageId}"/><a class="messageLink" href="<c:url value="/community/ShowMessage.do?messageId=${currentMessage.messageId}"/>">${currentMessage.recipient}<span class="subject" style="display:none">${currentMessage.subject}</span></a></div>
			</c:if>
				<div class="two"><a class="messageLink" href="<c:url value="/community/ShowMessage.do?messageId=${currentMessage.messageId}"/>"><span class="subject">${currentMessage.subject}</span> - <span class="message">${bia:abbreviateMessage(currentMessage.body)}</span></a></div>
				<div class="three"><a class="messageLink" href="<c:url value="/community/ShowMessage.do?messageId=${currentMessage.messageId}"/>">${currentMessage.sendedDate}</a></div>
			</div>
			</c:forEach>
		</div>
	</div>
	
	<a href="#" class="deleteMessages buttonSmall">Delete</a>

<div id="forumPaginate">
    <c:set var="paginationData">
		<bia:paginationForum page="${messageboxPage}"/>
	</c:set>
	
	${paginationData}
 
</div>
					
<a href="<c:url value="/community/ShowForum.do?forumId=1"/>" class="returnTo">&larr; Return to <span>Board Index</span> Forum</a>


<div id="deleteMessagesModal" title="Delete" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Are you sure you want to delete this/these message/s?
	</p>
</div>  


	<script type="text/javascript">
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
			
			$j(".messageLink").click(function(){
// 				$j( "<li><a href=" + $j(this).attr('href') + "'>Message</a></li>" ).appendTo( "#tabs .ui-tabs-nav" );
// 				$j( "#tabs" ).tabs( "refresh" );
				$j( "#tabs" ).tabs( "add" , $j(this).attr("href"), $j(this).find(".subject").text() + "</span></a><span class=\"ui-icon ui-icon-close\" title=\"Close Tab\">Remove Tab");
				$j("#tabs").tabs("option", "active", $j("#tabs").tabs("length")-1);
// 				$j("#main").load($j(this).attr("href"));
				return false;
			});
			
			$j(".row").die();
			
			var $toRemove = '';	
			$j(".deleteMessages").die();
			$j(".deleteMessages").live("click", function(){
				$toRemove = '';
				$j(".list > .row > .one > input:checked").each(function(){
					$toRemove += $j(this).attr("id") + "+";
				});
				if($toRemove != ''){
					$j( "#deleteMessagesModal" ).dialog({
						  autoOpen : false,
						  modal: true,
						  resizable: false,
						  width: 300,
						  height: 150, 
						  buttons: {
							  Yes: function() {
								  $j.ajax({type: 'POST', url: '${EraseMessagesURL}', async: false, data: {"idToErase" : $toRemove}, success: function(json){
									 if(json.operation == 'OK'){
										 $j("#tabs").tabs("load", $j("#tabs").tabs("option", "selected"));
									 }
								  }});
								  $j(this).dialog('close');
								  return false;
							  },
							  No: function() {
								  $j(this).dialog('close');
								  return false;
							  }
						  }
					  });
					$j("#deleteMessagesModal").dialog("open");
					return false;
				}
				return false;
			});
		});
	</script>
