<%@page import="org.medici.docsources.common.util.ForumUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bia" uri="http://docsources.medici.org/jsp:jstl" %>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div id="profileTable">
	<div id="online" class="visible"></div> <!-- Se l'utente � loggato in quel momento inserire la class "visible" a questo div -->
    
    <div id="profile">
    	<h3>Profile</h3>
        <div id="bgImgUserProfile">
			<div id="imgUserProfile"></div>
        </div>
        <div class="list">
        	<div class="row">
                <div class="firstItem">Username</div> 
                <div class="firstValue">${userProfile.firstName} ${userProfile.lastName}</div> 
            </div>
            <div class="row">
                <div class="item">Rank</div> 
                <div class="value">User</div> 
            </div>
            <div class="row">
                <div class="item">Email</div> 
                <div class="value">>${userProfile.mail}</div> 
            </div>
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
                <div class="value"><security:authentication property="principal.significantRoleDescription"/></div>
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
                <div class="firstItem">Joined</div> 
                <div class="firstValue">Tue Mar 02, 2010 12:31 pm</div> 
            </div>
            <div class="row">
                <div class="item">Last visited</div>
                <div class="value">Mon Apr 02, 2012 7:53 am</div>
            </div>
            <div class="row">
                <div class="item">Total posts</div>
                <div class="value">1 | <a href="#">Search user's posts</a><!-- Ti carica nella stessa pagina tutti i post scritti da l'utente --></div>
            </div>
            <div class="row">
                <div class="item">Most active forum</div>
                <div class="value"><a href="/DocSources/forum/viewForums.html" class="activeForum">People</a></div>
            </div>
            <div class="row">
                <div class="item">Most active Thread</div> 
                <div class="value"><a href="/DocSources/forum/viewThreads.html" class="activeTopic">Cosimo I</a></div>
            </div>
        </div>
    </div>
    
    <div id="contact">
        <h3>Contact</h3>
        <ul>
            <li><img src="/DocSources/images/forum/button_email.png" alt="email" /> <a href="mailto:lisakaborycha@gmail.com">Send E-mail to <span>Lisa Kaborycha</span></a></li>
            <li><img src="/DocSources/images/forum/button_privateMessage.png" alt="private message" /> <a href="#">Send private message</a></li><!-- Linka al compose message con la voce "To" gi� compilata -->
        </ul>
    </div>
</div>



<div id="topicActions">    
    <div id="jumpToDiv">
    	Jump to:
        <form id="jumpToForm" action="/DocSources/src/SimpleSearch.do" method="post">
            <select id="selectForum" name="selectForum" selected"" class="selectform_long">
                <option value="" selected="selected">Select a Forum</option>
            </select>
            <input id="go" type="submit" title="go" value="Go" class="buttonMini"/>
        </form>
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
		$j(".activeForum").click(
			function(){
				$j("#mainContent").load($j(this).attr("href"));
				$j("#whoIsOnlineDiv").css('display','inherit');
				return false;});
		$j(".activeTopic").click(
			function(){
				$j("#mainContent").load($j(this).attr("href"));
				$j("#whoIsOnlineDiv").css('display','inherit');
				return false;});
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