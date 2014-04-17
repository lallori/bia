<%@page import="org.medici.bia.common.util.ForumUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bia" uri="http://bia.medici.org/jsp:jstl" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:url var="ShowMessagesURL" value="/community/ShowMessagesByCategory.do">
	<c:param name="userMessageCategory" value="INBOX" />
</c:url>

<c:url var="SendMessageURL" value="/community/ComposeMessage.do">
	<c:param name="account" value="${userProfile.account}"/>
	<c:param name="accountDescription" value="${userProfile.firstName} ${userProfile.lastName}"/>
</c:url>

<c:url var="ShowForumURL" value="/community/ShowForum.do">
	<c:param name="forumId" value="${mostActiveForum.forumId}"/>
</c:url>

<c:choose>
	<c:when test="${mostActiveDiscussion.forum.subType == 'COURSE'}">
		<c:url var="ShowTopicURL" value="/teaching/ShowCourseTranscription.do">
			<c:param name="entryId" value="${mostActiveDiscussion.document.entryId}"/>
			<c:param name="topicId" value="${mostActiveDiscussion.topicId}"/>
			<c:param name="completeDOM" value="true" />
		</c:url>
	</c:when>
	<c:otherwise>
		<c:url var="ShowTopicURL" value="/community/ShowTopicForum.do">
			<c:param name="topicId" value="${mostActiveDiscussion.topicId}"/>
		</c:url>
	</c:otherwise>
</c:choose>

<c:url var="ShowForumChronologyURL" value="/community/GetForumChronology.json">
	<c:param name="forumId" value="1"/>
</c:url>

<c:url var="ShowUserForumPostURL" value="/community/ShowUserForumPost.do">
	<c:param name="account" value="${userProfile.account}"/>
</c:url>

<div id="profileTable">
	<div id="online" class="visible"></div> <!-- Se l'utente è loggato in quel momento inserire la class "visible" a questo div -->
    
    <div id="profile">
    	<h3>Profile</h3>
        <div id="bgImgUserProfile">
			<div id="imgUserProfile">
				<c:if test="${userProfile.portrait}">
					<c:url var="ShowPortraitUserURL" value="/user/ShowPortraitUser.do">
						<c:param name="account" value="${userProfile.account}" />
						<c:param name="time" value="${time}" />
					</c:url>
					<img src="${ShowPortraitUserURL}" width="111" height="145"/>
				</c:if>
				<c:if test="${!userProfile.portrait}">
					<img src="<c:url value="/images/1024/img_user.png"/>" alt="User Portrait"/>
				</c:if>
			</div>
        </div>
        <div class="list">
        	<div class="row">
                <div class="item">Username</div> 
                <div class="value">${userProfile.firstName} ${userProfile.lastName}</div> 
            </div>
            <div class="row">
                <div class="item">Rank</div> 
                <div class="value">User</div> 
            </div>
            <c:if test="${!userProfile.mailHide}">
            <div class="row">
                <div class="item">Email</div> 
                <div class="value">${userProfile.mail}</div> 
            </div>
            </c:if>
            <div class="row">
                <div class="item">Address</div>
                <div class="value">${userProfile.address}</div>
            </div>
            <div class="row">
                <div class="item">Country</div>
                <div class="value">${userProfile.country}</div>
            </div>
            <div class="row">
                <div class="item">Group</div>
<%--                 <div class="value"><security:authentication property="principal.significantRoleDescription"/></div> --%>
				<div class="value">${userGroup}</div>
            </div>
            <div class="row">
                <div class="item">Title</div> 
                <div class="value">${userProfile.title}</div>
            </div>
            <div class="row">
                <div class="item">Organization</div> 
                <div class="value">${userProfile.organization}</div>
            </div>
            <div class="row">
                <div class="item">Location</div> 
                <div class="value">${userProfile.city}</div>
            </div>
            <div class="row">
                <div class="item">Interests</div> 
                <div class="value">${userProfile.interests}</div>
            </div>
            <div class="row">
                <div class="item">Resume</div> 
                <div class="value">no.</div>
            </div>
        </div>
	</div>
        
    <div id="statistics">
        <h3>Statistics</h3>
        <div class="list">
            <div class="row">
                <div class="item">Joined</div> 
                <div class="value">${userProfile.forumJoinedDate}</div> 
            </div>
            <div class="row">
                <div class="item">Last visited</div>
                <div class="value">${userProfile.lastLoginDate}</div>
            </div>
            <div class="row">
                <div class="item">Total posts</div>
                <div class="value">${userProfile.forumNumberOfPost} | <a href="${ShowUserForumPostURL}" id="userPost">Search user's posts</a><!-- Ti carica nella stessa pagina tutti i post scritti da l'utente --></div>
            </div>
            <div class="row">
                <div class="item">Most active forum</div>
                <div class="value"><a href="${ShowForumURL}" class="activeForum">${mostActiveForum.description}</a></div>
            </div>
            <div class="row">
                <div class="item">Most active Discussion</div> 
                <div class="value"><a href="${ShowTopicURL}" class="activeTopic">${mostActiveDiscussion.subject}</a></div>
            </div>
        </div>
    </div>
    
    <div id="contact">
        <h3>Contact</h3>
        <ul>
        	<c:if test="${userProfile.mail != null && userProfile.mail != ''}">
            	<li><img src="<c:url value="/images/forum/button_email.png"/>" alt="email" /> <a href="mailto:${userProfile.mail}">Send E-mail to <span>${userProfile.firstName} ${userProfile.lastName}</span></a></li>
            </c:if>
            <li><img src="<c:url value="/images/forum/button_privateMessage.png"/>" alt="private message" /> <a href="${SendMessageURL}" id="sendMessage">Send private message</a></li>
        </ul>
    </div>
</div>

<div id="topicActions">    
    <div id="jumpToDiv">
    	<input id="goBackTo" class="button_medium" value="Go Back" type="submit" />
<!--     	Jump to: -->
<%--         <form id="jumpToForm" action="/src/SimpleSearch.do" method="post"> --%>
<!--             <select id="selectForum" name="selectForum" selected"" class="selectform_long"> -->
<!--                 <option value="" selected="selected">Select a Forum</option> -->
<!--             </select> -->
<!--             <input id="go" type="submit" title="go" value="Go" class="buttonMini"/> -->
<%--         </form> --%>
    </div>
</div>

<div id="deletePostModal" title="Delete post" style="display:none"> 
	<p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 0 0;"></span>
		Are you sure you want to delete this post?
	</p>
</div>

<script>
	$j(document).ready(function() {
		$j.ajax({ url: '${ShowForumChronologyURL}', cache: false, success:function(json) {
			$j("#chronologyDiv").html(json.chronology);
			$j("#selectForum").append(json.selectChronology);
			$j("#chronologyDiv .arrowForum").css('display','');
			$j("#chronologyDiv .forum").css('display','');
			if($j("#prevUrl").val() != "")
				$j("#chronologyDiv").append("<span class='arrowForum'>&rarr; <a href='" + $j("#prevUrl").val() + "' class='forum'>Go Back</a></span>");
			else
				$j("#goBackTo").css("display","none");
			return false;
		}});
		
		$j("#goBackTo").click(function(){
			$j("#main").load($j("#prevUrl").val());
		});
		
		$j("#userPost").click(function(){
			$j("#main").load($j(this).attr('href'));
			return false;
		});
		
		$j(".forum").click(
			function(){
				$j("#mainContent").load($j(this).attr("href"));
				$j(".arrowTopic").css('visibility','hidden');
				$j(".topic").css('visibility','hidden');
				return false;});
		$j("#postReply").click(
			function(){
				$j("#mainContent").load($j(this).attr("href"));
				$j("#whoIsOnlineDiv").css('display','none');
				return false;});
		$j("#editPost").click(
			function(){
				$j("#mainContent").load($j(this).attr("href"));
				$j("#whoIsOnlineDiv").css('display','none');
				return false;});
		$j("#reportPost").click(
			function(){
				$j("#mainContent").load($j(this).attr("href"));
				$j("#whoIsOnlineDiv").css('display','none');
				return false;});
		
		$j(".activeForum").click(function(){
			$j("#main").load($j(this).attr("href"));
			return false;
		});
		
		$j(".activeTopic").click(function(){
			if (${mostActiveDiscussion.forum.subType == 'COURSE'}) {
				return true;
			}
			$j("#main").load($j(this).attr("href"));
			return false;
		});
		
		$j("#sendMessage").click(function(){
			var newHref = $j(this).attr("href");
			$j("#main").load("${ShowMessagesURL}", function(){
				var tab = $j( "#tabs" ).find( ".ui-tabs-nav li:eq(2)" );
				var panelId = tab.attr( "aria-controls" );
				$j( "#" + $j("#" + panelId).attr("aria-labelledby") ).attr("href", newHref);
				$j("#tabs").tabs("option", "active", 2);
// 				$j( "#tabs" ).tabs( "load", 2 );
				return false;
			});
			
// 			$j("#main").load($j(this).attr("href"));
			return false;
		});
	});
</script>

<script>
	$j(function() {
		$j('#deletePost').click(function(){
			$j('#deletePostModal').dialog('open');
			return false;
		});
		
		$j( "#deletePostModal" ).dialog({
			  autoOpen : false,
			  modal: true,
			  resizable: false,
			  width: 300,
			  height: 130, 
			  buttons: {
				  Yes: function() {
					  $j( this ).dialog( "close" );
				  },
				  No: function() {
					  $j( this ).dialog( "close" );
				  }
			  }
		  });
		});
</script>



<!-- Information button
<a href="#" id="informationPost" title="Information"></a>
-->